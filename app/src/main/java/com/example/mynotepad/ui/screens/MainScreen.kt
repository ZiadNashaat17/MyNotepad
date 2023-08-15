package com.example.mynotepad.ui.screens

import android.icu.util.Calendar
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.mynotepad.Dest
import com.example.mynotepad.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: HomeViewModel) {
    viewModel.getAllNotes()
    Scaffold(
        topBar = { MainTopBar(navController) },
        floatingActionButton = { AddNoteButton(navController = navController) }
    ) {
        LazyColumn(modifier = Modifier.padding(it), contentPadding = PaddingValues(15.dp)) {
            if (viewModel.notes.isEmpty()) {
                item {
                    Text(
                        text = "No Notes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(items = viewModel.notes) { note ->
                    NoteCard(navController, note)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(navController: NavController) {
    TopAppBar(title = {
        Text(
            text = "My Notepad",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
        )
    }, actions = {
        IconButton(onClick = {
            navController.navigate(Dest.SEARCH_SCREEN.value) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }) {
            Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
        }
    }
    )
}

@Composable
fun AddNoteButton(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigate(Dest.ADD_NOTE_SCREEN.value) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(15.dp)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add note")
    }
}