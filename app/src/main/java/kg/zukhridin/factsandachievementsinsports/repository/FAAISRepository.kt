package kg.zukhridin.factsandachievementsinsports.repository

import androidx.lifecycle.LiveData
import kg.zukhridin.factsandachievementsinsports.dto.FAAISDto

interface FAAISRepository {
    val faais: LiveData<List<FAAISDto>>
    suspend fun insertFAAIS(faaisDto: FAAISDto)
}