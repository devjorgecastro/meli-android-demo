package com.example.meli.core.ui.widget

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.meli.core.ui.R
import com.example.meli.core.ui.theme.MeliTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.search_placeholder),
    expanded: Boolean = false,
    textFieldState: TextFieldState,
    searchResults: List<String>,
    onSearch: (String) -> Unit = {},
    onQueryChange: (String) -> Unit = {}
) {
    var expanded by rememberSaveable { mutableStateOf(expanded) }
    val animatedPadding by animateDpAsState(
        targetValue = if (expanded) 0.dp else 8.dp,
        animationSpec = tween(durationMillis = 700)
    )

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .padding(bottom = animatedPadding)
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = {
                        textFieldState.edit { replace(0, length, it) }
                        onQueryChange(it)
                    },
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                        expanded = false
                    },
                    expanded = expanded,
                    leadingIcon = {
                        if (expanded) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    },
                    onExpandedChange = { expanded = it },
                    placeholder = { Text(placeholder) }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(top = 16.dp)
            ) {
                items(searchResults) { item ->
                    SuggestedQueryItem(
                        title = item,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            textFieldState.edit { replace(0, length, item) }
                            expanded = false
                            onSearch(textFieldState.text.toString())
                        }
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SearchBarComponentPreview() {

    MeliTheme(dynamicColor = false) {
        val textFieldState = remember { TextFieldState("") }
        SearchBarComponent(
            textFieldState = textFieldState,
            searchResults = listOf("Option 1", "Option 2", "Option 3")
        )
    }
}