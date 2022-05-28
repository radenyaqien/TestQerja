package id.radenyaqien.testqerja.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import id.radenyaqien.testqerja.R
import id.radenyaqien.testqerja.domain.model.Job

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val data = viewModel.pagingdata.collectAsLazyPagingItems()
    ListData(data) {
        viewModel.onEvent(HomeEvent.Navigate(it))
    }
}

@Composable
fun ListData(data: LazyPagingItems<Job>, onNavigate: (String) -> Unit) {
    LazyColumn(modifier = Modifier) {
        items(items = data, key = {
            it.id
        }) { item ->
            item?.let {
                ItemJob(it, onNavigate)
            }

        }

        data.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = data.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = data.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemJob(job: Job, onclickItem: (String) -> Unit) {
    val painter = rememberImagePainter(
        data = job.companyLogo,
    ) {
        crossfade(durationMillis = 1000)
        error(R.drawable.no_image)
        transformations(CircleCropTransformation())

    }

    Card(
        modifier = Modifier
            .fillMaxWidth()

            .clickable {
                onclickItem(job.id)
            },
        elevation = 10.dp
    ) {
        Row(

            modifier = Modifier
                .padding(4.dp)
                .height(200.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painter,
                contentDescription = job.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier.weight(1f),
                alignment = Alignment.Center

            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = job.title,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = job.type,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = job.location,
                    fontStyle = FontStyle.Italic
                )
            }

        }
    }
}

