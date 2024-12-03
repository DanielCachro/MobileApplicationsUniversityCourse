package pl.edu.ansnt.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pl.edu.ansnt.todo.ui.screens.TasksViewModel
import pl.edu.ansnt.todo.ui.theme.ToDoTheme

class MainActivity : ComponentActivity() {
    val viewModel: TasksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
                ToDoApp(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Box(modifier = Modifier.fillMaxWidth())
    {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(
                    "ToDoApp",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            scrollBehavior = scrollBehavior,
        )
    }
}

@Composable
fun MyContent(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Spacer(modifier = Modifier.height(2.dp))
        repeat(15) {
            Card(
                modifier = Modifier
                    .padding(8.dp, 0.dp, 8.dp, 0.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Lorem ipsum dolor sit amet consectetur adipiscing elit",
                    modifier = Modifier
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
        Text(
            text = "https://developer.android.com/develop/ui/compose/documentation",
            modifier = Modifier.padding(7.dp),
            textAlign = TextAlign.Center,
        )
    }
}


@Composable
fun ToDoApp(viewModel: TasksViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        MyTopBar()
    },) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(tasks) { task ->
                if(task.title.isNotEmpty()){
                    Card(
                        modifier = Modifier
                            .padding(8.dp, 4.dp, 8.dp, 4.dp)
                            .fillMaxWidth()
                    )
                    {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = task.title,
                                    modifier = Modifier
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center,
                                    textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
                                )
                                Spacer(modifier = Modifier.height((8.dp)))
                                Button(onClick = {
                                    viewModel.toggleTaskDone(task)
                                }) {
                                    Icon(Icons.Filled.Check, contentDescription = "")
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}