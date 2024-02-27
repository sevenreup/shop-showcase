package com.skybox.shopshowcase.presentation.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.skybox.shopshowcase.R
import com.skybox.shopshowcase.presentation.ui.components.GoogleSignInButton
import com.skybox.shopshowcase.util.rememberFirebaseAuthLauncher

@Composable
fun AuthScreen(onComplete: () -> Unit) {
    Scaffold { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(paddingValues)
                .padding(vertical = 16.dp, horizontal = 24.dp)
        ) {
            Image(
                painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = ""
            )
            Text(text = "Welcome back", style = MaterialTheme.typography.displayMedium)
            Image(painterResource(id = R.drawable.login_image), contentDescription = "")
            Text(
                text = "Jump in to join us in the ultimate shopping experience",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            SignInGoogleButton(onComplete = onComplete)
        }
    }
}

@Composable
fun SignInGoogleButton(onComplete: () -> Unit) {
    val context = LocalContext.current
    val token = stringResource(R.string.default_web_client_id)
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { _ ->
            onComplete()
        },
        onAuthError = {
            Toast.makeText(context, "Failed to sign in", Toast.LENGTH_SHORT).show()
        }
    )

    GoogleSignInButton(
        onClick = {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()
            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            launcher.launch(googleSignInClient.signInIntent)
        }
    )
}