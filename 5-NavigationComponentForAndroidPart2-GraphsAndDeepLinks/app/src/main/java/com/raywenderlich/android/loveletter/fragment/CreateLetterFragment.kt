/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.loveletter.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.raywenderlich.android.loveletter.R
import com.raywenderlich.android.loveletter.databinding.FragmentCreateLetterBinding
import com.raywenderlich.android.loveletter.extension.Event
import com.raywenderlich.android.loveletter.extension.hideKeyboard
import com.raywenderlich.android.loveletter.viewmodel.LettersViewModel

class CreateLetterFragment : Fragment() {

  private val lettersViewModel: LettersViewModel? by navGraphViewModels(R.id.nav_graph)

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    setHasOptionsMenu(true)

    val binding: FragmentCreateLetterBinding = DataBindingUtil.inflate(
      inflater, R.layout.fragment_create_letter, container, false
    )
    binding.viewModel = lettersViewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    lettersViewModel?.toastLiveData?.observe(this, Observer<Event<String>> { it ->
      it.getContentIfNotHandled()?.let {
        Toast.makeText(this@CreateLetterFragment.context, it, Toast.LENGTH_LONG).show()
      }
    })
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.create_letter, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_send -> handleSend { lettersViewModel?.sendLetterWithDeeplink() }

      R.id.action_push -> handleSend { lettersViewModel?.sendPushNotification() }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun handleSend(toSend: () -> Unit) {
    if (lettersViewModel != null && lettersViewModel!!.hasFullProfile()) {
      toSend()
      // You can’t popBackStack directly to SentFragment because it might not be in the navigation stack
      // The work around here is to go back to InboxFragment, which is the home fragment, first
      findNavController().popBackStack(R.id.inboxFragment, false)
      findNavController().navigate(R.id.sentFragment)
    } else {
      findNavController().navigate(R.id.editProfileFragment)
    }
    hideKeyboard()
  }
}
