package com.sample.test.ui.theme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sample.test.models.Profile
import com.sample.test.ui.theme.themes.TestTheme

@Composable
fun ProfileCard(profile: Profile) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .background(Color.Black, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .border(width = 2.dp, color = Color.White, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.Gray)
        ) {
            AsyncImage(
                model = profile.profilePic,
                contentDescription = profile.profilePic.toString()+System.currentTimeMillis(),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = profile.name,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = profile.job,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
            )
            ProfileRowScope {
                Text(
                    text = "Lives in United States",
                    color = Color.Red,
                    style = MaterialTheme.typography.labelSmall
                )
            }

        }


    }


}

@Composable
fun ProfileRowScope(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreview() {
    TestTheme {
        ProfileCard(Profile(name = "Jeev", job = "Android Developer", lastSeen = System.currentTimeMillis().toString(), ""))
    }
}