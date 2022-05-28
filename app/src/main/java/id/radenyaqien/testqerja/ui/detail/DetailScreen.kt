package id.radenyaqien.testqerja.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.radenyaqien.testqerja.domain.model.Job
import id.radenyaqien.testqerja.ui.home.ItemJob

@Composable
fun DetailScreen(viewModel: DetailViewModel) {
    val model = viewModel.model
    if (model != null) {
        CompanySection(model = model)
    }
}

@Composable
fun CompanySection(model: Job) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Company", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(16.dp))
        ItemJob(job = model, onclickItem = {

        })
    }
}