package com.jerlendds.moblab.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    presenter: HomeContract.Presenter,
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf(HomeContract.State()) }
    val view = remember {
        object : HomeContract.View {
            override fun render(nextState: HomeContract.State) {
                state = nextState
            }
        }
    }

    DisposableEffect(presenter) {
        presenter.attach(view)
        onDispose { presenter.detach() }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "MobLab",
                style = MaterialTheme.typography.headlineMedium,
            )
            Button(
                onClick = presenter::refresh,
                enabled = !state.isLoading,
            ) {
                Text("Refresh")
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.users, key = { user -> user.id }) { user ->
                ListItem(
                    headlineContent = { Text(user.login) },
                    supportingContent = { Text(user.avatarUrl) },
                )
                Divider()
            }
        }
    }
}
