package com.example.moodtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.moodtracker.data.AppDatabase
import com.example.moodtracker.data.MoodRepository
import com.example.moodtracker.ui.theme.MoodTheme
import com.example.moodtracker.ui.today.TodayScreen
import com.example.moodtracker.ui.trends.TrendsScreen
import com.example.moodtracker.viewmodel.MoodViewModel

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val db = Room.databaseBuilder(
			applicationContext,
			AppDatabase::class.java,
			"mood-db"
		).build()
		val repository = MoodRepository(db.moodDao())

		setContent {
			MoodTheme {
				val navController = rememberNavController()
				val viewModel: MoodViewModel = viewModel(factory = MoodViewModelFactory(repository))
				Scaffold(
					bottomBar = {
						BottomBar(navController = navController)
					}
				) { padding ->
					NavHost(
						navController = navController,
						startDestination = "today"
					) {
						composable("today") { TodayScreen(viewModel) }
						composable("trends") { TrendsScreen(viewModel) }
					}
				}
			}
		}
	}
}

@Composable
private fun BottomBar(navController: androidx.navigation.NavHostController) {
	val items = listOf(
		BottomNavItem("today", R.string.nav_today),
		BottomNavItem("trends", R.string.nav_trends)
	)
	NavigationBar {
		val navBackStackEntry by navController.currentBackStackEntryAsState()
		val currentDestination = navBackStackEntry?.destination
		items.forEach { item ->
			NavigationBarItem(
				selected = currentDestination?.route == item.route,
				onClick = {
					navController.navigate(item.route) {
						popUpTo(navController.graph.findStartDestination().id) { saveState = true }
						launchSingleTop = true
						restoreState = true
					}
				},
				icon = { },
				label = { Text(text = stringResource(id = item.labelRes)) }
			)
		}
	}
}

data class BottomNavItem(val route: String, val labelRes: Int)

class MoodViewModelFactory(private val repository: MoodRepository) : androidx.lifecycle.ViewModelProvider.Factory {
	override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(MoodViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return MoodViewModel(repository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}