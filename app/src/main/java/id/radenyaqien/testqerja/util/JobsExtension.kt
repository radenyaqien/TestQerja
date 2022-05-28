package id.radenyaqien.testqerja.util

import id.radenyaqien.testqerja.data.remote.Dto.JobsDto
import id.radenyaqien.testqerja.domain.model.Job


fun JobsDto.toJob(): Job = Job(
    id = this.id,
    title = this.title,
    description = this.description,
    company = this.company,
    companyLogo = this.companyLogo,
    location = this.location,
    howToApply = this.howToApply,
    companyUrl = this.companyUrl ?: "-",
    url = this.url,
    createdAt = this.createdAt,
    type = this.type,
)