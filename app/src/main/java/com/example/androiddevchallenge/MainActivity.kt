/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.outlinedBorder
import androidx.compose.material.ButtonDefaults.outlinedButtonColors
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.theme.Icons
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.green
import com.example.androiddevchallenge.ui.theme.red
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Turn off the decor fitting system windows,
        // which means we need to through handling insets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

object Route {
    const val Welcome = "Welcome"
    const val Login = "Login"
    const val Home = "Home"
}

// Start building your app here!
@Composable
fun MyApp() {
    ProvideWindowInsets {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Route.Welcome
            ) {
                composable(Route.Welcome) {
                    WelcomeScreen(onLoginClick = { navController.navigate(Route.Login) })
                }
                composable(Route.Login) {
                    LoginScreen(onLoginClick = { navController.navigate(Route.Home) })
                }
                composable(Route.Home) {
                    HomeScreen()
                }
            }
        }
    }
}

data class Position(
    val value: String,
    val gain: Float,
    val symbol: String,
    val name: String,
    @DrawableRes val graph: Int,
)

val positions = arrayListOf(
    Position("$7,918", -0.54f, "ALK", "Alaska Air Group Inc.", R.drawable.ic_home_alk),
    Position("$1,293", 4.18f, "BA", "Boeing Co.", R.drawable.ic_home_ba),
    Position("$893.50", -0.54f, "DAL", "Delta Airlines Inc.", R.drawable.ic_home_dal),
    Position("$12,301", 2.51f, "EXPE", "Expedia Group Inc.", R.drawable.ic_home_exp),
    Position("$12,301", 1.38f, "EADSY", "Airbus SE", R.drawable.ic_home_eadsy),
    Position("$8,521", 1.56f, "JBLU", "Jetblue Airways Corp.", R.drawable.ic_home_jblu),
    Position("$521", 2.75f, "MAR", "Marriot International Inc.", R.drawable.ic_home_mar),
    Position("$5,481", 0.14f, "CCL", "Carnical Corp", R.drawable.ic_home_ccl),
    Position("$9,184", 1.69f, "RCL", "Royal Caribbean Cruises", R.drawable.ic_home_rcl),
    Position("$654", 3.23f, "TRVL", "Travelocity Inc.", R.drawable.ic_home_trvl),
)

@Composable
fun HomeScreen() {
    val tabTitles = arrayListOf(
        stringResource(R.string.account),
        stringResource(R.string.watchlist),
        stringResource(R.string.profile)
    )
    val selectedTabIndex by remember { mutableStateOf(0) }
    val graphTitles = arrayListOf(
        stringResource(R.string.week),
        stringResource(R.string.etfs),
        stringResource(R.string.stocks),
        stringResource(R.string.funds),
        stringResource(R.string.extra)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding(bottom = true),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tabTitles.forEachIndexed { i, tabTitle ->
                TextButton(
                    onClick = {},
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (i == selectedTabIndex) {
                            MaterialTheme.colors.onBackground
                        } else {
                            MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                        }
                    )
                ) {
                    Text(
                        modifier = Modifier.paddingFromBaseline(top = 64.dp, bottom = 8.dp),
                        text = tabTitle.toUpperCase(Locale.getDefault())
                    )
                }
            }
        }

        Text(
            stringResource(id = R.string.balance),
            modifier = Modifier.paddingFromBaseline(top = 32.dp, bottom = 8.dp),
            style = MaterialTheme.typography.subtitle1
        )

        Text(
            "$73,589.01",
            modifier = Modifier.paddingFromBaseline(top = 48.dp, bottom = 24.dp),
            style = MaterialTheme.typography.h1
        )

        Text(
            "+412.35 today",
            modifier = Modifier.paddingFromBaseline(top = 16.dp, bottom = 32.dp),
            style = MaterialTheme.typography.subtitle1.copy(color = green)
        )

        Button(
            onClick = {},
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .requiredHeight(48.dp)
        ) {
            Text(text = stringResource(R.string.transact).toUpperCase(Locale.getDefault()))
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(graphTitles) {
                OutlinedButton(
                    onClick = {},
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.requiredHeight(40.dp),
                    colors = outlinedButtonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = MaterialTheme.colors.onBackground
                    ),
                    border = outlinedBorder.copy(
                        brush = SolidColor(MaterialTheme.colors.onBackground)
                    ),
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body1,
                    )

                    if (it == graphTitles[0]) {
                        Icon(imageVector = Icons.expandMore, contentDescription = "Expand")
                    }
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_home_illos),
            contentDescription = "Graph",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.positions),
                modifier = Modifier.paddingFromBaseline(top = 40.dp, bottom = 24.dp),
                style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onSurface)
            )

            // LazyColumn() {
            //     items(positions) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                positions.forEach {
                    Divider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(56.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .requiredWidth(64.dp)
                        ) {
                            Text(
                                it.value,
                                style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface),
                                modifier = Modifier.paddingFromBaseline(top = 24.dp)
                            )
                            val gainColor = if (it.gain > 0) green else red
                            Text(
                                "${it.gain}%",
                                style = MaterialTheme.typography.body1.copy(color = gainColor),
                                modifier = Modifier.paddingFromBaseline(top = 16.dp, bottom = 16.dp)
                            )
                        }

                        Column {
                            Text(
                                it.symbol,
                                style = MaterialTheme.typography.h3.copy(color = MaterialTheme.colors.onSurface),
                                modifier = Modifier.paddingFromBaseline(top = 24.dp)
                            )
                            Text(
                                it.name,
                                style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface),
                                modifier = Modifier.paddingFromBaseline(top = 16.dp, bottom = 16.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Image(
                                painter = painterResource(id = it.graph),
                                contentDescription = "Graph for ${it.name}",
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
    ) {
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.login_bg),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = stringResource(id = R.string.welcome_back),
                style = MaterialTheme.typography.h2,
                modifier = Modifier.paddingFromBaseline(top = 152.dp),
                textAlign = TextAlign.Center
            )
        }
        var emailTextValue by remember { mutableStateOf(TextFieldValue()) }
        var passwordTextValue by remember { mutableStateOf(TextFieldValue()) }

        OutlinedTextField(
            value = emailTextValue,
            onValueChange = { emailTextValue = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
            label = {
                Text(text = stringResource(R.string.email_address))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.mailOutline,
                    contentDescription = null,
                    modifier = Modifier.requiredSize(24.dp)
                )
            }
        )
        OutlinedTextField(
            value = passwordTextValue,
            onValueChange = { passwordTextValue = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            label = {
                Text(text = stringResource(R.string.password))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.password,
                    contentDescription = null,
                    modifier = Modifier.requiredSize(24.dp)
                )
            }
        )
        Button(
            onClick = onLoginClick,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .requiredHeight(48.dp)
        ) {
            Text(text = stringResource(R.string.login).toUpperCase(Locale.getDefault()))
        }
    }
}

@Composable
fun WelcomeScreen(onLoginClick: () -> Unit = {}) {
    Box(contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.welcome_bg),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.statusBarsPadding())
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.app_name)
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding(bottom = true)
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {},
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .requiredHeight(48.dp)
                    .weight(1f)
            ) {
                Text(text = stringResource(R.string.get_started).toUpperCase(Locale.getDefault()))
            }

            OutlinedButton(
                onClick = onLoginClick,
                colors = outlinedButtonColors(backgroundColor = Color.Transparent),
                border = outlinedBorder.copy(brush = SolidColor(MaterialTheme.colors.primary)),
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .requiredHeight(48.dp)
                    .weight(1f)
            ) {
                Text(text = stringResource(R.string.login).toUpperCase(Locale.getDefault()))
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
