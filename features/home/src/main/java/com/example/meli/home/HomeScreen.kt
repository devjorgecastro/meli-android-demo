package com.example.meli.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.meli.core.ui.container.BoxContainer
import com.example.meli.core.ui.widget.SearchBarComponent
import com.example.meli.home.viewmodel.HomeContract
import com.example.meli.home.viewmodel.HomeViewModel

data class HomeEvents(
    val onSearch: (String) -> Unit = {},
    val onQueryChange: (String) -> Unit = {}
)

@Composable
fun HomeScreen(
    onNavToDetail: () -> Unit = {},
    viewModel: HomeViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state.value,
        events = HomeEvents(
            onSearch = { viewModel.dispatch(HomeContract.Intent.Search(it)) },
            onQueryChange = {
                viewModel.dispatch(HomeContract.Intent.RequestSuggestedQueries(it))
            }
        )
    )
}


@Composable
private fun HomeScreen(
    state: HomeContract.State,
    events: HomeEvents = HomeEvents()
) {
    BoxContainer(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val textFieldState = remember { TextFieldState("") }
            SearchBarComponent(
                textFieldState = textFieldState,
                searchResults = state.suggestedQueries?.queries?.map { it.description }.orEmpty(),
                onSearch = events.onSearch,
                onQueryChange = events.onQueryChange,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        state = HomeContract.State()
    )
}
