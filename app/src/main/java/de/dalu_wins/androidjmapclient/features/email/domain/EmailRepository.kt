package de.dalu_wins.androidjmapclient.features.email.domain

import kotlinx.coroutines.flow.Flow

interface EmailRepository {
    fun getEmails(): Flow<List<Email>>
}