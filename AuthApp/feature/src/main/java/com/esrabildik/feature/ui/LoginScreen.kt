package com.esrabildik.feature.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esrabildik.feature.R


@Composable
fun LoginUI(){

       var email = remember { mutableStateOf("") }
       var password = remember { mutableStateOf("") }

       Column(modifier = Modifier
              .fillMaxSize()
              .padding(24.dp),
              verticalArrangement = Arrangement.Center)
       {
              Text(
                     text = "Login",
                     fontSize = 32.sp,
                     modifier = Modifier.padding(bottom = 24.dp)
              )

              CustomTextField(
                     value = email.value,
                     onValueChange = {email.value = it},
                     label = "Email Address",
                     placeholder = "Enter your Email",
                     leadingIcon = Icons.Default.Email
              )

              Spacer(modifier = Modifier.height(17.dp))

              CustomTextField(
                     value = password.value,
                     onValueChange = {password.value = it},
                     label = "Password",
                     placeholder = "Enter your Password",
                     leadingIcon = Icons.Default.Email
              )

              Button(onClick = {}) {
                     Icon(imageVector = Icons.Default.Done, contentDescription = null)
                     Text("Login", Modifier.padding(start = 10.dp))}
       }

}




 @Composable
fun CustomTextField(
       value  : String,
       onValueChange : (String) -> Unit,
       label : String,
       placeholder: String,
       leadingIcon : ImageVector? = null,
       modifier: Modifier = Modifier
){
       OutlinedTextField(
               value = value,
               leadingIcon = leadingIcon?.let {
                      { Icon(imageVector = it, contentDescription = null) }
               },
               onValueChange = onValueChange,
               label = { Text(label)},
               placeholder = { Text(placeholder) },
              modifier = modifier.fillMaxWidth()
        )
}




