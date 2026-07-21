package de.dalu_wins.androidjmapclient.features.settings.presentation.structures

data class RadioOption<T>(
    val value: T,
    val label: String
)

sealed interface SettingType {
    val id: String

    data class RadioGroup<T>(
        override val id: String,
        val title: String? = null,
        val options: List<RadioOption<T>>,
        val selectedValue: T,
        val onOptionSelected: (T) -> Unit
    ) : SettingType

    data class Toggle(
        override val id: String,
        val title: String,
        val subtitle: String? = null,
        val isChecked: Boolean,
        val onToggle: (Boolean) -> Unit
    ) : SettingType

    data class Action(
        override val id: String,
        val title: String,
        val subtitle: String? = null,
        val onClick: () -> Unit
    ) : SettingType
    
}