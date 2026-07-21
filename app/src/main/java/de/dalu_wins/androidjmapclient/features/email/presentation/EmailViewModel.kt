package de.dalu_wins.androidjmapclient.features.email.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dalu_wins.androidjmapclient.features.email.domain.EmailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val repository: EmailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EmailListState())
    val state: StateFlow<EmailListState> = _state.asStateFlow()

    init {
        loadEmails()
    }

    private fun loadEmails() {
        _state.update { it.copy(isLoading = true) }
        repository.getEmails()
            .onEach { emails ->
                _state.update {
                    it.copy(
                        emails = emails,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
