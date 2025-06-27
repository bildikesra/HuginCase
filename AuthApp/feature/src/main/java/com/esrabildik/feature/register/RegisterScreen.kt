package com.esrabildik.feature.register

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.esrabildik.domain.model.UserRequest
import com.esrabildik.feature.R
import com.esrabildik.feature.component.LoadingLottie
import com.esrabildik.feature.register.event.RegisterEvent
import com.esrabildik.feature.register.viewmodel.RegisterViewModel

@Composable
fun RegisterUI(
    registerViewModel : RegisterViewModel = hiltViewModel(),
    navigationToLogin : () -> Unit = {}
){
    val signUpState by registerViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val email by registerViewModel.email
    val password by registerViewModel.password



    LaunchedEffect(signUpState.error){
        signUpState.error?.let { error ->
            Toast.makeText(context,error, Toast.LENGTH_SHORT).show()
        }
    }

    if (signUpState.isLoading) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoadingLottie(resId = R.raw.loading_anim)
        }
    } else {
        Column (modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegisterEmailInput(
                email = email,
                onEmailChange = { registerViewModel.onEvent(RegisterEvent.UpdateEmail(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RegisterPasswordInput(
                password = password,
                onPasswordChange = {
                     registerViewModel.onEvent(RegisterEvent.UpdatePassword(it))
                },
                onRegisterClick = { registerViewModel.onEvent(RegisterEvent.SignUp(UserRequest(email,password)))
                }
            )
        }
    }
}

@Composable
fun RegisterEmailInput(
    email: String,
    onEmailChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Register",
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
fun RegisterPasswordInput(
    password: String,
    onPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit
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
            onClick = onRegisterClick
        )
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
