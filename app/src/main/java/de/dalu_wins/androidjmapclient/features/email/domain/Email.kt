package de.dalu_wins.androidjmapclient.features.email.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Email(
    val id: String,
    val sender: String,
    val receiver: String,
    val subject: String,
    val body: String,
    val timestamp: String
) : Parcelable

val mockEmails = listOf(
    Email(
        id = "1",
        sender = "Alice Smith",
        receiver = "You",
        subject = "Project Update",
        body = "Hi team, just wanted to give you a quick update on the project status. We are on track for the release.",
        timestamp = "10:30 AM"
    ),
    Email(
        id = "2",
        sender = "Bob Johnson",
        receiver = "You",
        subject = "Lunch Meeting",
        body = "Are we still on for lunch today? I was thinking about that new Italian place down the street.",
        timestamp = "Yesterday"
    ),
    Email(
        id = "3",
        sender = "Charlie Brown",
        receiver = "You",
        subject = "Question about JMAP",
        body = "I was reading the JMAP spec and had a few questions about how handles push notifications.",
        timestamp = "Monday"
    ),
    Email(
        id = "4",
        sender = "Diana Prince",
        receiver = "You",
        subject = "Weekend Plans",
        body = "Hey! Do you have any plans for the weekend? There's a concert on Saturday if you're interested.",
        timestamp = "Oct 12"
    ),
    Email(
        id = "5",
        sender = "Ethan Hunt",
        receiver = "You",
        subject = "Mission Briefing",
        body = "The documents are ready for your review. Please check the secure folder.",
        timestamp = "Oct 10"
    ),
    Email(
        id = "6",
        sender = "Fiona Gallagher",
        receiver = "You",
        subject = "Coffee?",
        body = "Long time no see! Would you like to catch up over coffee sometime this week?",
        timestamp = "Oct 9"
    ),
    Email(
        id = "7",
        sender = "George Miller",
        receiver = "You",
        subject = "Budget Review",
        body = "Please take a look at the attached budget proposal for the next quarter and let me know your thoughts.",
        timestamp = "Oct 8"
    ),
    Email(
        id = "8",
        sender = "Hannah Abbott",
        receiver = "You",
        subject = "Book Club Selection",
        body = "The votes are in! This month we'll be reading 'The Midnight Library'. Hope you can join the discussion.",
        timestamp = "Oct 7"
    ),
    Email(
        id = "9",
        sender = "Ian Wright",
        receiver = "You",
        subject = "Football Match",
        body = "Got the tickets for the game on Sunday. Kick-off is at 3 PM. See you there!",
        timestamp = "Oct 5"
    ),
    Email(
        id = "10",
        sender = "Julia Roberts",
        receiver = "You",
        subject = "Interview Confirmation",
        body = "We've scheduled your interview for Thursday at 2 PM. Please let us know if this works for you.",
        timestamp = "Oct 3"
    ),
    Email(
        id = "11",
        sender = "Kevin Hart",
        receiver = "You",
        subject = "New Comedy Special",
        body = "Just released my new special! Hope you enjoy it. Let me know what you think.",
        timestamp = "Oct 1"
    ),
    Email(
        id = "12",
        sender = "Laura Palmer",
        receiver = "You",
        subject = "The Owls are not what they seem",
        body = "Meet me by the waterfall tonight. I have something important to tell you.",
        timestamp = "Sep 30"
    ),
    Email(
        id = "13",
        sender = "Michael Scott",
        receiver = "You",
        subject = "Dundies Awards",
        body = "The Dundies are happening this year at Chili's! Don't forget to RSVP.",
        timestamp = "Sep 28"
    ),
    Email(
        id = "14",
        sender = "Nina Simone",
        receiver = "You",
        subject = "Jazz Festival",
        body = "The lineup for the summer festival has been announced. Check out the headliners!",
        timestamp = "Sep 25"
    ),
    Email(
        id = "15",
        sender = "Oscar Wilde",
        receiver = "You",
        subject = "The Importance of Being Earnest",
        body = "To love oneself is the beginning of a lifelong romance.",
        timestamp = "Sep 20"
    )
)
