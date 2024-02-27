package com.skybox.shopshowcase.presentation.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.skybox.shopshowcase.domain.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navigate: (route: String) -> Unit,viewModel: ProfileViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Profile") })
    }) { paddingValues ->
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            item {
                val user = state.user
                if (user != null) {
                    ProfileCard(user)
                }
            }
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ListItem(
                            headlineContent = { Text(text = "Orders") },
                            leadingContent = {
                                Icon(Icons.Filled.ShoppingBasket, contentDescription = "")
                            },
                            modifier = Modifier.clickable {
                                navigate("/orders")
                            })
                        HorizontalDivider()
                        ListItem(
                            headlineContent = { Text(text = "Logout") },
                            leadingContent = {
                                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "")
                            },
                            modifier = Modifier.clickable {
                                viewModel.logout()
                            })
                    }
                }
            }

        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileCard(user: User) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        GlideImage(
            model = user.profileImage,
            contentDescription = "",
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
                .clip(CircleShape)
        )
        Text(text = user.username, style = MaterialTheme.typography.headlineSmall)
    }
}