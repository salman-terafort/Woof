package com.example.woof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.woof.data.Dog
import com.example.woof.data.dogs
import com.example.woof.ui.theme.WoofTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            WoofTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    WoofApp()
                }


            }
        }
    }
}

@Composable
fun DogItem(
    dog: Dog,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer else
            MaterialTheme.colorScheme.primaryContainer
    )

    var extraBottomPadding by remember {
        mutableIntStateOf(0)
    }
    extraBottomPadding = if (expanded) 24 else 0

    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .background(color)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                DogIcon(dog.imageResourceId)
                DogInformation(dog.name, dog.age)
                Spacer(modifier = Modifier.weight(1f))
                DogItemButton(
                    expanded = expanded, onClick = { expanded = !expanded },
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

            }
            if (expanded) {
                DogHobby(
                    dog.hobbies, modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }


    }


}

@Composable
fun DogHobby(
    @StringRes dogHobby: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.about), modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = stringResource(id = dogHobby), modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }

}

@Composable
fun DogInformation(
    dogName: Int, dogAge: Int, modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(start = 8.dp)) {
        Text(
            text = stringResource(id = dogName),
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small)),
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = stringResource(id = R.string.years_old, dogAge),
            style = MaterialTheme.typography.bodyLarge
        )


    }

}

@Composable
fun DogIcon(
    imageResourceId: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = imageResourceId), contentDescription = null,
        modifier = modifier
            .size(dimensionResource(id = R.dimen.image_size))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}


@Composable
private fun DogItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = { onClick() }, modifier = modifier) {

        Icon(
            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDogItemButton() {
    DogItemButton(expanded = false, onClick = { })
}

@Composable
@Preview
fun PreviewDogItem() {
    DogItem(dog = Dog(R.drawable.nox, R.string.dog_name_1, 6, R.string.dog_description_1))
}

@Composable
fun WoofApp(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        WoofTopAppBar()
    }) {

        LazyColumn(contentPadding = it) {
            items(dogs) { dog ->
                DogItem(
                    dog = dog,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun WoofTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    painter = painterResource(id = R.drawable.ic_woof_logo),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )

}

@Preview(showBackground = true)
@Composable
fun WoofPreview() {
    WoofTheme {
        WoofApp()
    }
}