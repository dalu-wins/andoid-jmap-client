package de.dalu_wins.androidjmapclient.main.structures

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.ReportGmailerrorred
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val icon: ImageVector,
    val label: String
) {
    companion object {
        val NAV_ITEMS = listOf(
            NavItem(Icons.Default.Inbox, "Inbox"),
            NavItem(Icons.Default.Draw, "Concepts"),
            NavItem(Icons.AutoMirrored.Filled.Send, "Sent"),
            NavItem(Icons.Default.Star, "Marked"),
            NavItem(Icons.Default.ReportGmailerrorred, "Spam"),
            NavItem(Icons.Default.Archive, "Archived"),
            NavItem(Icons.Filled.Delete, "Trash"),
            NavItem(Icons.Default.AllInbox, "All")
        )
    }
}