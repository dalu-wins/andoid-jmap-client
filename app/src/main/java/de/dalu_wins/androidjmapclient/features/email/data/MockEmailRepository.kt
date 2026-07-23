package de.dalu_wins.androidjmapclient.features.email.data

import de.dalu_wins.androidjmapclient.features.email.domain.Email
import de.dalu_wins.androidjmapclient.features.email.domain.Mailbox
import de.dalu_wins.androidjmapclient.features.email.domain.MailboxItem
import de.dalu_wins.androidjmapclient.features.email.domain.repository.EmailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockEmailRepository : EmailRepository {
    private fun createMockEmails(count: Int, offset: Int = 0): List<Email> {
        val senders = listOf(
            "Alice Smith",
            "Bob Johnson",
            "Charlie Brown",
            "Diana Prince",
            "Ethan Hunt",
            "Fiona Gallagher",
            "George Miller",
            "Hannah Abbott",
            "Ian Wright",
            "Julia Roberts"
        )
        val subjects = listOf(
            "Project Update",
            "Lunch?",
            "Quick Question",
            "Meeting Notes",
            "Hello!",
            "Draft Review",
            "Holiday Plans"
        )
        val loremIpsum = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
            
            Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
            
            Curabitur pretium tincidunt lacus. Nulla gravida orci a odio. Nullam varius, turpis et commodo pharetra, est eros bibendum elit, nec luctus magna felis sollicitudin mauris. Integer in mauris eu nibh euismod gravida. Duis ac tellus et risus vulputate vehicula. Donec lobortis risus a elit. Etiam tempor. Ut ullamcorper, ligula eu tempor congue, eros est euismod turpis, id tincidunt sapien risus a quam. Maecenas fermentum consequat mi. Donec fermentum. Pellentesque malesuada nulla a mi. Duis sapien sem, aliquet nec, commodo eget, consequat quis, neque. Aliquam faucibus, elit ut dictum aliquet, felis nisl adipiscing sapien, sed pretium diam eros tempus enim.
        """.trimIndent().split("\n\n")

        return (1..count).map { i ->
            val id = (i + offset).toString()
            val bodyLength = (i % 3) + 1 // 1 to 3 paragraphs
            val body = loremIpsum.take(bodyLength).joinToString("\n\n")
            
            Email(
                id = id,
                sender = senders[i % senders.size],
                receiver = "You",
                subject = subjects[i % subjects.size],
                body = body,
                timestamp = "${10 + (i % 12)}:${if (i % 2 == 0) "30" else "45"} ${if (i % 2 == 0) "AM" else "PM"}",
                isUnread = i % 3 == 0 // Every 3rd email is unread
            )
        }
    }

    val mockEmails = createMockEmails(50)

    val mockMailboxItems = listOf(
        Mailbox(
            id = "m1",
            name = "Work",
            items = listOf(
                Mailbox(
                    id = "m1_1",
                    name = "Active Projects",
                    items = listOf(
                        Mailbox(
                            id = "m1_1_1",
                            name = "Project Android",
                            items = createMockEmails(15, 100)
                        ),
                        Mailbox(
                            id = "m1_1_2",
                            name = "Project iOS",
                            items = createMockEmails(5, 120)
                        ),
                        mockEmails[0]
                    )
                ),
                Mailbox(
                    id = "m1_2",
                    name = "Archives",
                    items = listOf(
                        Mailbox(
                            id = "m1_2_1",
                            name = "2023",
                            items = createMockEmails(20, 200)
                        ),
                        Mailbox(
                            id = "m1_2_2",
                            name = "2022",
                            items = createMockEmails(10, 230)
                        )
                    )
                ),
                mockEmails[1],
                mockEmails[2]
            )
        ),
        Mailbox(
            id = "m2",
            name = "Social",
            items = listOf(
                Mailbox(
                    id = "m2_1",
                    name = "Promotions",
                    items = createMockEmails(30, 300)
                ),
                Mailbox(
                    id = "m2_2",
                    name = "Newsletters",
                    items = listOf(
                        Mailbox(
                            id = "m2_2_1",
                            name = "Tech Weekly",
                            items = createMockEmails(5, 340)
                        ),
                        Mailbox(
                            id = "m2_2_2",
                            name = "Cooking Tips",
                            items = createMockEmails(8, 350)
                        )
                    )
                ),
                mockEmails[3],
                mockEmails[4]
            )
        ),
        Mailbox(
            id = "m3",
            name = "Deep Nesting Test",
            items = listOf(
                Mailbox(
                    id = "level1",
                    name = "Level 1",
                    items = listOf(
                        Mailbox(
                            id = "level2",
                            name = "Level 2",
                            items = listOf(
                                Mailbox(
                                    id = "level3",
                                    name = "Level 3",
                                    items = listOf(
                                        Mailbox(
                                            id = "level4",
                                            name = "Level 4",
                                            items = listOf(
                                                Mailbox(
                                                    id = "level5",
                                                    name = "Level 5",
                                                    items = listOf(
                                                        Mailbox(
                                                            id = "level6",
                                                            name = "Level 6",
                                                            items = createMockEmails(3, 500)
                                                        )
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        ),
        Mailbox(
            id = "m4",
            name = "Empty Folder",
            items = emptyList()
        ),
        Mailbox(
            id = "m5",
            name = "Bulk Emails",
            items = createMockEmails(100, 1000)
        )
    )

    override fun getMailboxItems(): Flow<List<MailboxItem>> = flow {
        emit(mockMailboxItems)
    }
}
