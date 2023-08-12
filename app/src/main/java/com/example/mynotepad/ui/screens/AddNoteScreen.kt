package com.example.mynotepad.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.mynotepad.room.Note
import com.example.mynotepad.viewmodel.HomeViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(navController: NavController, viewModel: HomeViewModel, onAddClick: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "New Note",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back button")
                }
            }
        )
    }) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val (titleField, contentField, saveField) = createRefs()

            TextField(
                value = title, onValueChange = { title = it },
                shape = RoundedCornerShape(10.dp),
                label = { Text(text = "Title") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(titleField) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .padding(top = 45.dp, start = 25.dp, end = 25.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            )
            TextField(
                value = content, onValueChange = { content = it },
                shape = RoundedCornerShape(10.dp),
                label = { Text(text = "Content") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()/*.height(200.dp)*/
                    .constrainAs(contentField) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(titleField.bottom)
                    }
                    .padding(top = 20.dp, start = 25.dp, end = 25.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            )
            Button(
                enabled = (title.isNotEmpty() && content.isNotEmpty()),
                onClick = {
                    val calendar = Calendar.getInstance()
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val month = calendar.get(Calendar.MONTH)
                    val year = calendar.get(Calendar.YEAR)
                    val date = "$day/${month + 1}/$year"
                    val note = Note(title = title, content = content, date = date)
                    viewModel.insertNote(note)
                    onAddClick.invoke()
                    navController.popBackStack()
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .constrainAs(saveField) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .padding(vertical = 70.dp, horizontal = 60.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}