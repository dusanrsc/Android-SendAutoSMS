package com.infinitysoftware.sendautosms

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.infinitysoftware.sendautosms.ui.theme.defaultBackgroundColor

class MainActivity : ComponentActivity() {

    private val smsPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            sendSms("+756465646464", "This Is Automatic Message!!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            smsPermissionLauncher.launch(Manifest.permission.SEND_SMS)
        } else {
            sendSms("+756465646464", "This Is Automatic Message!!")
        }

        setContent {
            MaterialTheme {
                Surface {
                    GUI()
                }
            }
        }
    }

    private fun sendSms(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@Composable
fun GUI() {

    // Composable Remember Variable Section.
    var phoneNumber by remember { mutableStateOf("") }
    var textMessage by remember { mutableStateOf("") }

    // Actual GUI/UI Section.
    Column(modifier = Modifier.fillMaxSize().background(defaultBackgroundColor), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        // Enter Phone Number Text Field.
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text(text = "Enter Phone Number") }
        )

        Spacer(modifier = Modifier.size(10.dp))

        // Enter Text Content Of The Message.
        TextField(
            value = textMessage,
            onValueChange = {textMessage = it},
            label = { Text(text = "Enter Message") }
        )

        Spacer(modifier = Modifier.size(20.dp))

        // Proceed Button - If Phone Number And Text Message Are Valid Send Message!
        Button(onClick = { /* TODO */ }){
            Text(text = "Proceed!")
        }
    }
}