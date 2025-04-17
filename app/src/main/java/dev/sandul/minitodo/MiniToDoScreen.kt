package dev.sandul.minitodo

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MiniToDoScreen(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    val tasks = remember { mutableStateListOf<String>() }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Нове завдання") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                maxLines = 1,
                placeholder = { Text("Введіть завдання") },
                shape = RoundedCornerShape(8.dp),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (text.isNotBlank()) {
                            tasks.add(0, text.trim())
                            text = ""
                            focusManager.clearFocus()
                        }
                    }
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (text.isNotBlank()) {
                    tasks.add(0, text.trim())
                    text = ""
                    focusManager.clearFocus()
                }
            }) { Text(text = "+") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(tasks.size) { index ->
                var visible by remember { mutableStateOf(true) }
                AnimatedVisibility(visible = visible) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                scope.launch {
                                    Log.e("ERROR", index.toString())
                                    delay(300)
                                    tasks.removeAt(index)
                                }
                                visible = false
                            }) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = tasks[index]
                        )
                    }
                }
            }
        }
    }
}
