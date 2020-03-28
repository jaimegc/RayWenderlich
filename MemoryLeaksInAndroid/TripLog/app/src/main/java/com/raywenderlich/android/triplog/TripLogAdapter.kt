package com.raywenderlich.android.triplog

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.triplog.model.TripLog
import com.raywenderlich.android.triplog.utils.CoordinatesFormatter
import com.raywenderlich.android.triplog.utils.DateFormatter
import kotlinx.android.synthetic.main.view_log_item.view.*


class TripLogAdapter(context: Context,
                     private val logs: List<TripLog>,
                     private val dateFormatter: DateFormatter,
                     private val coordinatesFormatter: CoordinatesFormatter
) : RecyclerView.Adapter<TripLogAdapter.TripLogViewHolder>() {

  private val happyBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bg_basic_happy_big)
  private val sadBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bg_basic_sad_big)

  var listener: Listener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripLogViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val itemView = inflater.inflate(R.layout.view_log_item, parent, false)
    return TripLogViewHolder(itemView)
  }

  override fun getItemCount() = logs.size

  override fun onBindViewHolder(holder: TripLogViewHolder, position: Int) {
    val tripLog = logs[position]
    holder.bind(tripLog)
  }

  inner class TripLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textViewLog = itemView.textViewLog
    private val textViewDate = itemView.textViewDate
    private val textViewLocation = itemView.textViewLocation
    private val imageView = itemView.imageView

    fun bind(tripLog: TripLog) {
      imageView.setImageBitmap(if (tripLog.happyMood) happyBitmap else sadBitmap)

      textViewLog.text = tripLog.log
      textViewDate.text = dateFormatter.format(tripLog.date)
      textViewLocation.text = coordinatesFormatter.format(tripLog.coordinates)
      itemView.setOnClickListener {
        listener?.showDetailLog(tripLog)
      }
    }

  }

  interface Listener {
    fun showDetailLog(tripLog: TripLog)
  }

}