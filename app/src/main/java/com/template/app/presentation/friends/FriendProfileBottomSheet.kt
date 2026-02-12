package com.template.app.presentation.friends

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.template.app.domain.model.Friend
import com.template.app.presentation.components.RoundedProfileImage

@Composable
fun FriendProfileBottomSheet(
    friend: Friend,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RoundedProfileImage(
            imageUrl = friend.avatarUrl,
            size = 80,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = friend.name,
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = friend.status,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
