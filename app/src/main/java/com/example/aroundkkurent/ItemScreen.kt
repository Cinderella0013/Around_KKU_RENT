package com.example.kkurent

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(navController: NavHostController) {

    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var item_name = remember { mutableStateOf("") }
    var item_detail = remember { mutableStateOf("") }
    var item_img by remember { mutableStateOf<Uri?>(null) }
    var price = remember { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        item_img = uri
        if (uri == null) {
            Toast.makeText(context, "คุณไม่ได้เลือกรูปภาพ", Toast.LENGTH_SHORT).show()
        }else {
            Log.d("DEBUG", "Selected Image: $uri")
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(text = "เพิ่มสินค้าใช้เช่า", color = Color.White)
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
                    .padding(5.dp),
                value = item_name.value,
                onValueChange = { item_name.value = it },
                label = { Text(text = "ชื่อสินค้า") },
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(5.dp),
                value = item_detail.value,
                onValueChange = { item_detail.value = it },
                label = { Text(text = "คำอธิบายเพิ่มเติม") },
                maxLines = 4
            )
//            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
//                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(30.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = if (item_img != null) {
                            rememberAsyncImagePainter(item_img)
                        } else {
                            painterResource(id = R.drawable.image_search)
                        },
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    modifier = Modifier.width(180.dp),
                    onClick = {
                        galleryLauncher.launch("image/*")
                    }
                ) {
                    Text(text = "เปิดอัลบั้ม")
                }

            }

            Spacer(modifier = Modifier.height(5.dp))

            Column(modifier = Modifier
                .fillMaxSize()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(Color.LightGray.copy(alpha = 0.3f))
                .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "รายละเอียดสินค้า",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black)

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = price.value,
                    onValueChange = { price.value = it },
                    label = { Text(text = "ราคา") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions.Default,
                    shape = RoundedCornerShape(12.dp)
                )

                location = LocationDropdown()
                category = CategoryDropdown()

                Button(
                    modifier = Modifier.width(180.dp),
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800)
                    )
                ) {
                    Text(text = "เพิ่มสินค้า", color = Color.White)
                }
            }



        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDropdown(): String {
    val keyboardController = LocalSoftwareKeyboardController.current
    val locationList = listOf(
        "กังสดาล",
        "หลังมอ",
        "โคลัมโบ",

        )
    var expanded by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf(locationList[0]) }

    ExposedDropdownMenuBox(modifier = Modifier
        .clickable { keyboardController?.hide() },
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded })
    {
        OutlinedTextField(
            modifier = Modifier
                .width(340.dp)
                .menuAnchor()
                .clickable { keyboardController?.hide() },
            readOnly = true,
            value = selectedLocation,
            onValueChange = {},
            label = { Text("สถานที่") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            shape = RoundedCornerShape(12.dp)
        )
        // Dropdown menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false }
        )
        {
            // Show items
            locationList.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedLocation = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
    return selectedLocation
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(): String {
    val keyboardController = LocalSoftwareKeyboardController.current
    val CategoryList = listOf(
        "ของใช้ทั่วไป",
        "เสื้อผ้า",
        "อุปกรณ์อิเล็กทรอนิกส์",

        )
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(CategoryList[0]) }

    ExposedDropdownMenuBox(modifier = Modifier
        .clickable { keyboardController?.hide() },
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded })
    {
        OutlinedTextField(
            modifier = Modifier
                .width(340.dp)
                .menuAnchor()
                .clickable { keyboardController?.hide() },
            readOnly = true,
            value = selectedCategory,
            onValueChange = {},
            label = { Text("หมวดหมู๋") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            shape = RoundedCornerShape(12.dp)
        )
        // Dropdown menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false }
        )
        {
            // Show items
            CategoryList.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedCategory = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
    return selectedCategory
}








