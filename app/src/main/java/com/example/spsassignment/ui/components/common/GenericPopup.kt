package com.example.spsassignment.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.spsassignment.R


@Composable
fun GenericPopup(
    title: String? = null,
    message: String? = null,
    buttonTitle: String? = null, onPositiveButtonClickAction: () -> Unit,
) {

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.LightGray
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .padding(7.dp),
            )
            Spacer(modifier = Modifier.padding(5.dp))
            if (title != null) {
                Text(
                    text = title,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (message != null) {
                    Text(
                        text = message,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                if (buttonTitle != null) {
                    Button(
                        onClick = { onPositiveButtonClickAction.invoke() },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color.Red
                        )
                    ) {
                        Text(
                            text = buttonTitle,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun GenericPopUPPreview() {
    GenericPopup(
        title = "Error",
        message = "Error Message",
        buttonTitle = "Okay",
    ) {}
}

@Composable
fun GenericDialog(
    title: String? = "Error",
    message: String? = null,
    buttonTitle: String? = "Okay",
    popUpVisibleState: (Boolean) -> Unit,
    onPositiveButtonClickAction: () -> Unit = {}, shouldCloseOnTouchOutside: Boolean
) {
    Dialog(
        onDismissRequest = { popUpVisibleState.invoke(false) },
        properties = DialogProperties(dismissOnClickOutside = shouldCloseOnTouchOutside)
    ) {
        GenericPopup(
            title = title,
            message = message,
            buttonTitle = buttonTitle,
            onPositiveButtonClickAction = {
                popUpVisibleState(false)
                onPositiveButtonClickAction.invoke()
            },
        )
    }
}

