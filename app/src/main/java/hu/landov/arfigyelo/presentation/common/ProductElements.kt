package hu.landov.arfigyelo.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import hu.landov.arfigyelo.R
import hu.landov.arfigyelo.data.domain.Price
import hu.landov.arfigyelo.data.domain.Product


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductImage(uri: String, contentScale: ContentScale = ContentScale.Fit){
    GlideImage(
        model = uri,
        contentDescription = null,
        contentScale = contentScale,
        modifier = Modifier.padding(8.dp).fillMaxSize(),

        ) {
        it.error(R.drawable.ic_error_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
    }

}

@Composable
fun ProductTitle(product: Product, style: TextStyle = MaterialTheme.typography.titleMedium){
    Text(
        text = product.name,
        style = style,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun ProducPrices(priceList : List<Price> ){
    Column {
        priceList.forEach { price ->

            Row(modifier = Modifier.padding(2.dp)) {
                Box(modifier = Modifier.weight(0.5f)) {
                    Text(
                        text = "${price.storeName}:",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                }
                Box(modifier = Modifier.weight(0.5f)) {
                    Column {
                        if (!price.discounted) {

                            Text(text = "${price.normalPrice.toInt().toString()} Ft")
                        } else {
                            Row {

                                Text(
                                    text = price.discountedPrice.toInt().toString(),
                                    color = MaterialTheme.colorScheme.error
                                )
                                Text(
                                    text = "(${price.normalPrice.toInt().toString()})",
                                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                                )
                                Text(text = " Ft")

                            }
                        }
                        val showUnitprice = if(!price.discounted) price.unitPrice != price.normalPrice else price.unitPrice != price.discountedPrice
                        if (showUnitprice) {
                            Text(text = "(${price.unitPrice.toInt()}/${price.unitTitle})", style = MaterialTheme.typography.bodySmall)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun FavoriteBar(productId : String, selected: Boolean, onFavoriteClick : (id : String) -> Unit){
    val icon = if(selected) Icons.Outlined.Star else Icons.Outlined.CheckCircle
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd){
        Image(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(24.dp).clickable { onFavoriteClick(productId) },
        )

    }
}


