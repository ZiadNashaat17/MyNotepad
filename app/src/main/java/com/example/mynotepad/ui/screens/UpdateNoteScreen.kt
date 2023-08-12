package com.example.mynotepad.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNoteScreen(
    navController: NavController,
    noteID: Int,
    viewModel: HomeViewModel,
    onBackClick: () -> Unit
) {
    val note = viewModel.getSelectedNote(noteID)
    var updatedTitle by remember { mutableStateOf(note.title) }
    var updatedContent by remember { mutableStateOf(note.content) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Edit Note",
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
            val (titleField, contentField, saveField, deleteField) = createRefs()

            TextField(
                value = updatedTitle, onValueChange = { updatedTitle = it },
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
                value = updatedContent, onValueChange = { updatedContent = it },
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
                onClick = {
                    val updatedNote =
                        Note(
                            id = note.id,
                            title = updatedTitle,
                            content = updatedContent,
                            date = note.date
                        )
                    viewModel.updateSelectedNote(updatedNote)
                    navController.popBackStack()
                },
                shape = RoundedCornerShape(10.dp),
                enabled = (updatedTitle != note.title || updatedContent != note.content),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .constrainAs(saveField) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(deleteField.top)
                    }
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 60.dp)
            ) {
                Text(text = "Save")
            }
            Button(
                onClick = {
                    showDeleteDialog = true
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .constrainAs(deleteField) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .padding(start = 60.dp, end = 60.dp, bottom = 70.dp)
            ) {
                Text(text = "Delete")
            }
            AnimatedVisibility(visible = showDeleteDialog) {
                DeleteNoteDialog(note = note) {
                    it?.let {
                        viewModel.deleteSelectedNote(it)
                        onBackClick.invoke()
                        navController.popBackStack()
                    }
                    showDeleteDialog = false
                }
            }
            BackHandler {
                onBackClick.invoke()
            }
        }
    }
}

@Composable
fun DeleteNoteDialog(note: Note, onConfirmClick: (Note?) -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = {
                onConfirmClick(note)
            }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onConfirmClick(null) }) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Delete Note") },
        text = { Text(text = "Are you sure you want to delete this note?") }
    )
}