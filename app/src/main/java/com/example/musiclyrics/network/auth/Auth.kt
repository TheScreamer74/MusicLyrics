package com.example.musiclyrics.network.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*

object Auth {

    var jwt: String? = null
        private set

    fun generateJWT(onlyIfNull: Boolean = true) {
        if (onlyIfNull && jwt != null) return

        val key = checkNotNull(Credentials.privateKey, { "private key is missing" })
        val team = checkNotNull(Credentials.teamID, { "team ID is missing" })

        jwt = Jwts.builder()
            .setIssuer(team)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + 15777000.toLong() * 1000))
            .signWith(
                SignatureAlgorithm.ES256, KeyFactory.getInstance("EC")
                .generatePrivate(PKCS8EncodedKeySpec(key.toByteArray())))
            .compact()
    }

}