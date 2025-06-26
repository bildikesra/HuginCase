package com.esrabildik.feature.login


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.esrabildik.domain.model.UserRequest
import com.esrabildik.feature.R
import com.esrabildik.feature.component.LoadingLottie
import com.esrabildik.feature.login.event.LoginEvent
import com.esrabildik.feature.login.viewmodel.LoginViewModel
import com.esrabildik.feature.navigation.NavGraph


@Composable
fun LoginUI(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {

    val signInState by viewModel.uiState.collectAsStateWithLifecycle()
    val password by viewModel.password
    val email by viewModel.email
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
            verticalArrangement = Arrangement.Center
        ) {
            LoadingLottie(resId = R.raw.loading_anim)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginEmailInput(
                email = email,
                onEmailChange = { viewModel.onEvent(LoginEvent.UpdateEmail(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoginPasswordInput(
                password = password,
                onPasswordChange = { viewModel.onEvent(LoginEvent.UpdatePassword(it)) },
                onLoginClick = {
                    viewModel.onEvent(LoginEvent.SignIn(UserRequest(email, password)))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "SIGN UP",
                onClick = { navController.navigate(NavGraph.RegisterScreen.route)}
                // navhost işlemi
            )

        }
    }
}


@Composable
fun LoginEmailInput(
    email: String,
    onEmailChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Login",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        CustomTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email Address",
            placeholder = "Enter your Email",
            leadingIcon = Icons.Default.Email
        )
    }
}


@Composable
fun LoginPasswordInput(
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CustomTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Password",
            placeholder = "Enter your Password",
            leadingIcon = Icons.Default.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = "Log In",
            onClick = onLoginClick
        )
    }
}


@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.DarkGray,
                containerColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(10.dp),
            contentPadding = PaddingValues(
                horizontal = 20.dp,
                vertical = 12.dp
            )
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LoginEmailInput(
            email = "test@example.com",
            onEmailChange = {}
        )
        LoginPasswordInput(
            password = "123456",
            onPasswordChange = {},
            onLoginClick = {}
        )
    }

}



