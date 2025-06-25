package com.esrabildik.feature.ui


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.esrabildik.feature.R
import com.esrabildik.feature.component.LoadingLottie
import com.esrabildik.feature.viewmodel.AuthViewModel


@Composable
fun LoginUI(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val signInState by viewModel.uiState.collectAsState()

    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    val context = LocalContext.current


    LaunchedEffect(signInState.error) {
        signInState.error?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }

    }

    if (signInState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            LoadingLottie(resId = R.raw.loading_anim)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        )
        {
            Text(
                text = "Login",
                fontSize = 32.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            CustomTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = "Email Address",
                placeholder = "Enter your Email",
                leadingIcon = Icons.Default.Email
            )

            Spacer(modifier = Modifier.height(17.dp))

            CustomTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = "Password",
                placeholder = "Enter your Password",
                leadingIcon = Icons.Default.Email
            )

            Button(onClick = {
                viewModel.signIn(email.value, password.value)
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = null)
                Text("Login", Modifier.padding(start = 10.dp))
            }
        }
    }


}


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        leadingIcon = leadingIcon?.let {
            { Icon(imageVector = it, contentDescription = null) }
        },
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    CustomTextField("value",{},"label","placeholder")
}



