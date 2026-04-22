package com.ismartcoding.plain.ui.page.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ismartcoding.plain.R
import com.ismartcoding.plain.enums.ButtonSize
import com.ismartcoding.plain.enums.ButtonType
import com.ismartcoding.plain.ui.base.PFilledButton
import com.ismartcoding.plain.ui.base.VerticalSpace
import com.ismartcoding.plain.ui.models.MainViewModel
import com.ismartcoding.plain.ui.theme.cardBackgroundNormal
import com.ismartcoding.plain.tunnel.TunnelManager

enum class TunnelState { OFF, CONNECTING, ON }

@Composable
fun HomeTunnelSection(
    context: Context,
    mainVM: MainViewModel,
    tunnelEnabled: Boolean,
) {
    val tunnelState = when {
        TunnelManager.isTunnelRunning -> TunnelState.ON
        tunnelEnabled -> TunnelState.CONNECTING
        else -> TunnelState.OFF
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.cardBackgroundNormal,
    ) {
        Column(
            modifier = Modifier.padding(
                top = 16.dp,
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = when (tunnelState) {
                        TunnelState.OFF -> stringResource(R.string.remote_access_off)
                        TunnelState.CONNECTING -> stringResource(R.string.connecting)
                        TunnelState.ON -> stringResource(R.string.remote_access_on)
                    },
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold, lineHeight = 36.sp,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )
            }
            VerticalSpace(12.dp)
            Text(
                text = when (tunnelState) {
                    TunnelState.OFF -> stringResource(R.string.remote_access_desc_off)
                    TunnelState.CONNECTING -> stringResource(R.string.remote_access_desc_connecting)
                    TunnelState.ON -> stringResource(R.string.remote_access_desc_on)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            VerticalSpace(8.dp)
            Text(
                text = "Token: eyJhIjoiNzk4MDRjYzVhNTdhMGFjZTVkZDA4NmZhMDdkOTc2NTAiLCJ0IjoiNDI3MzQyYjMtODU4MS00ZjMxLThiYjctN2UzOGViYWEwMzI3IiwicyI6Ik5qUmtPVEk1TnpNdE9UVTNZUzAwWVRCaUxXSmlPV1F0T1RnNVpESXdaalU0WkdZMiJ9",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            VerticalSpace(8.dp)
            Text(
                text = "https://app.shakti.buzz?key=SECRET123",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
            )
            VerticalSpace(24.dp)
            when (tunnelState) {
                TunnelState.OFF -> PFilledButton(
                    text = stringResource(R.string.enable_remote_access),
                    onClick = { mainVM.enableTunnel(context, true) },
                    buttonSize = ButtonSize.LARGE,
                )

                TunnelState.CONNECTING -> PFilledButton(
                    text = stringResource(R.string.connecting),
                    onClick = {},
                    buttonSize = ButtonSize.LARGE,
                    enabled = false,
                )

                TunnelState.ON -> PFilledButton(
                    text = stringResource(R.string.disable_remote_access),
                    onClick = { mainVM.enableTunnel(context, false) },
                    type = ButtonType.DANGER,
                    buttonSize = ButtonSize.LARGE,
                )
            }
        }
    }
}