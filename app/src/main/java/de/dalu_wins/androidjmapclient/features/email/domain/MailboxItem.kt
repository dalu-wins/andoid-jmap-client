package de.dalu_wins.androidjmapclient.features.email.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class MailboxItem : Parcelable {
    abstract val id: String
}
