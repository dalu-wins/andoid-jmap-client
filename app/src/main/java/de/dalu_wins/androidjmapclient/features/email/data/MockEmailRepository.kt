package de.dalu_wins.androidjmapclient.features.email.data

import de.dalu_wins.androidjmapclient.features.email.domain.Email
import de.dalu_wins.androidjmapclient.features.email.domain.EmailRepository
import de.dalu_wins.androidjmapclient.features.email.domain.mockEmails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockEmailRepository : EmailRepository {
    override fun getEmails(): Flow<List<Email>> = flow {
        emit(mockEmails)
    }
}