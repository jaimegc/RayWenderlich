package com.raywenderlich.android.petmed

import java.security.*
import java.security.spec.X509EncodedKeySpec

class Authenticator {

    private val publicKey: PublicKey
    private val privateKey: PrivateKey

    init {
        // Created a KeyPairGenerator instance for the Elliptic Curve (EC) type
        val keyPairGenerator = KeyPairGenerator.getInstance("EC")
        // Initialized the object with the recommended key size of 256 bits
        keyPairGenerator.initialize(256)
        // Generated a key pair, which contains both the public and private key
        val keyPair = keyPairGenerator.genKeyPair()

        publicKey = keyPair.public
        privateKey = keyPair.private
    }

    fun sign(data: ByteArray): ByteArray {
        val signature = Signature.getInstance("SHA1withECDSA")
        signature.initSign(privateKey)
        signature.update(data)

        return signature.sign()
    }

    fun verify(signature: ByteArray, data: ByteArray): Boolean {
        val verifySignature = Signature.getInstance("SHA1withECDSA")
        verifySignature.initVerify(publicKey)
        verifySignature.update(data)

        return verifySignature.verify(signature)
    }

    fun verify(signature: ByteArray, data: ByteArray, publicKeyString: String): Boolean {
        val verifySignature = Signature.getInstance("SHA1withECDSA")
        val bytes = android.util.Base64.decode(publicKeyString, android.util.Base64.DEFAULT)
        val publicKey = KeyFactory.getInstance("EC").generatePublic(X509EncodedKeySpec(bytes))
        verifySignature.initVerify(publicKey)
        verifySignature.update(data)
        
        return verifySignature.verify(signature)
    }
}