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

package com.raywenderlich.android.petmed2

import android.annotation.TargetApi
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import java.security.KeyStore
import java.security.SecureRandom
import java.util.HashMap
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

internal class Encryption {

  fun encrypt(dataToEncrypt: ByteArray,
              password: CharArray): HashMap<String, ByteArray> {

    val map = HashMap<String, ByteArray>()

    // Here, you use the SecureRandom class, which makes sure that the output is difficult to predict.
    // That’s called a cryptographically strong random number generator.
    val random = SecureRandom()
    val salt = ByteArray(256)
    random.nextBytes(salt)

    // Put the salt and password into PBEKeySpec, a password-based encryption object. The constructor
    // takes an iteration count (1324). The higher the number, the longer it would take to operate on
    // a set of keys during a brute force attack.
    val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
    // Passed PBEKeySpec into the SecretKeyFactory
    val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
    val keySpec = SecretKeySpec(keyBytes, "AES")

    val ivRandom = SecureRandom() //not caching previous seeded instance of SecureRandom
    // Created 16 bytes of random data.
    val iv = ByteArray(16)
    ivRandom.nextBytes(iv)
    // Packaged it into an IvParameterSpec object.
    val ivSpec = IvParameterSpec(iv)

    // You passed in the specification string “AES/CBC/PKCS7Padding”. It chooses AES with cipher block
    // chaining mode. PKCS7Padding is a well-known standard for padding. Since you’re working with blocks,
    // not all data will fit perfectly into the block size, so you need to pad the remaining space. By
    // the way, blocks are 128 bits long and AES adds padding before encryption.
    val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
    val encrypted = cipher.doFinal(dataToEncrypt)

    // You packaged the encrypted data into a HashMap. You also added the salt and initialization vector
    // to the map. That’s because all those pieces are necessary to decrypt the data.
    // It’s OK to store salts and IVs, but reusing or sequentially incrementing them weakens the security.
    // But you should never store the key !!!
    map["salt"] = salt
    map["iv"] = iv
    map["encrypted"] = encrypted

    return map
  }

  fun decrypt(map: HashMap<String, ByteArray>, password: CharArray): ByteArray? {

    var decrypted: ByteArray? = null

    //TODO: Add code here

    return decrypted
  }

  fun keystoreEncrypt(dataToEncrypt: ByteArray): HashMap<String, ByteArray> {

    val map = HashMap<String, ByteArray>()

    //TODO: Add code here

    return map
  }

  fun keystoreDecrypt(map: HashMap<String, ByteArray>): ByteArray? {

    var decrypted: ByteArray? = null

    //TODO: Add code here

    return decrypted
  }

  @TargetApi(23)
  fun keystoreTest() {

    //TODO: Add code here

  }
}

