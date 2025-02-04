package com.example.kkurent

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(navController: NavHostController) {

    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var post_detail = remember { mutableStateOf("") }
    var post_img by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        post_img = uri
        if (uri == null) {
            Toast.makeText(context, "คุณไม่ได้เลือกรูปภาพ", Toast.LENGTH_SHORT).show()
        }else {
            Log.d("DEBUG", "Selected Image: $uri")
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(text = "สร้างโพสของคุณ", color = Color.White)
            },
            navigationIcon = {
                IconButton(onClick = { /*  ใส่โค้ดกลับไปหน้าแรก */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF003366)
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                value = post_detail.value,
                onValueChange = { post_detail.value = it },
                label = { Text(text = "คุณต้องการหาอะไร....") },
                maxLines = 10
            )
            Spacer(modifier = Modifier.height(5.dp))

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(30.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = if (post_img != null) {
                        rememberAsyncImagePainter(post_img)
                    } else {
                        painterResource(id = R.drawable.image_search)
                    },
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
            // ปุ่มเปิดแกลเลอรี
            Button(
                modifier = Modifier.width(180.dp),
                onClick = {
                    galleryLauncher.launch("image/*")
                }
            ) {
                Text(text = "เปิดอัลบั้ม")
            }

            Spacer(modifier = Modifier.height(30.dp))
            Button(
                modifier = Modifier.width(180.dp),
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800)
                )
            ) {
                Text(text = "สร้างโพสต์", color = Color.White)
            }

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {  navController.navigate(Screen.Item.route)}
            ) {
                Text(text = "โพสต์เช่าสินค้า")
            }
        }
        }


    }
