package com.example.mynotepad

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mynotepad.room.Note
import com.example.mynotepad.ui.screens.AddNoteScreen
import com.example.mynotepad.ui.screens.MainScreen
import com.example.mynotepad.ui.screens.SearchScreen
import com.example.mynotepad.ui.screens.UpdateNoteScreen
import com.example.mynotepad.viewmodel.HomeViewModel
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavHost(viewModel: HomeViewModel) {
    val navController = rememberNavController()
    val date = Calendar.getInstance()
    NavHost(navController = navController, startDestination = Dest.MAIN_SCREEN.value) {
        composable(Dest.MAIN_SCREEN.value) {
            MainScreen(navController, viewModel = viewModel)
        }

        composable(Dest.ADD_NOTE_SCREEN.value) {
            AddNoteScreen(navController = navController, viewModel = viewModel){
                viewModel.getAllNotes()
            }
        }
        composable(
            "${Dest.UPDATE_NOTE_SCREEN.value}/{note_id}", arguments = listOf(
                navArgument("note_id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("note_id") ?: 0
            UpdateNoteScreen(navController = navController, noteID = id, viewModel = viewModel) {
                viewModel.getAllNotes()
            }
        }
        composable(Dest.SEARCH_SCREEN.value) {
            SearchScreen(navController = navController, viewModel = viewModel)
        }
    }
}