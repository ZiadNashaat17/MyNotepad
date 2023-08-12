package com.example.mynotepad.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.mynotepad.Dest
import com.example.mynotepad.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: HomeViewModel) {
    var search by remember { mutableStateOf("") }



    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (searchField, dataField) = createRefs()

        TextField(
            value = search, onValueChange = { search = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search"
                )
            },
            label = { Text(text = "Search") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .constrainAs(searchField) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 25.dp)
        )
        viewModel.getStartingWith(search)
        LazyColumn(
            contentPadding = PaddingValues(15.dp),
            modifier = Modifier
                .constrainAs(dataField) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(searchField.bottom)
                }
                .padding(vertical = 10.dp)
        ) {
            if (search.isNotEmpty()) {
                items(items = viewModel.notes) { note ->
                    NoteCard(navController = navController, note = note)
                }
            }
        }
    }
}