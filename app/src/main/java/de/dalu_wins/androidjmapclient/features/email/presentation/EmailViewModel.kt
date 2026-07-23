package de.dalu_wins.androidjmapclient.features.email.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dalu_wins.androidjmapclient.features.email.domain.Mailbox
import de.dalu_wins.androidjmapclient.features.email.domain.MailboxItem
import de.dalu_wins.androidjmapclient.features.email.domain.repository.EmailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.util.Stack
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val repository: EmailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EmailListState())
    val state: StateFlow<EmailListState> = _state.asStateFlow()

    private val navigationStack = Stack<Triple<List<MailboxItem>, String, List<String>>>()

    init {
        loadItems()
    }

    private fun loadItems() {
        _state.update { it.copy(isLoading = true) }
        repository.getMailboxItems()
            .onEach { items ->
                _state.update {
                    it.copy(
                        items = items,
                        currentMailboxName = "Root",
                        mailboxPath = listOf("Root"),
                        canNavigateBack = false,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onMailboxClick(mailbox: Mailbox) {
        navigationStack.push(
            Triple(
                _state.value.items,
                _state.value.currentMailboxName,
                _state.value.mailboxPath
            )
        )
        _state.update {
            it.copy(
                items = mailbox.items,
                currentMailboxName = mailbox.name,
                mailboxPath = _state.value.mailboxPath + mailbox.name,
                canNavigateBack = true,
                showOnlyUnread = false
            )
        }
    }

    fun navigateBack(): Boolean {
        if (navigationStack.isNotEmpty()) {
            val (previousItems, previousName, previousPath) = navigationStack.pop()
            _state.update {
                it.copy(
                    items = previousItems,
                    currentMailboxName = previousName,
                    mailboxPath = previousPath,
                    canNavigateBack = navigationStack.isNotEmpty(),
                    showOnlyUnread = false
                )
            }
            return true
        }
        return false
    }

    fun navigateToPathIndex(index: Int) {
        val targetDepth = index + 1
        while (state.value.mailboxPath.size > targetDepth) {
            if (!navigateBack()) break
        }
    }

    fun toggleUnreadFilter() {
        _state.update { it.copy(showOnlyUnread = !it.showOnlyUnread) }
    }
}
