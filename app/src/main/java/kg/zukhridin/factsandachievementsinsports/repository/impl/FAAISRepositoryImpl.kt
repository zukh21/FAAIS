package kg.zukhridin.factsandachievementsinsports.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kg.zukhridin.factsandachievementsinsports.dto.FAAISDto
import kg.zukhridin.factsandachievementsinsports.repository.FAAISRepository
import javax.inject.Inject

class FAAISRepositoryImpl @Inject constructor(db: FirebaseDatabase) : FAAISRepository {
    private val ref = db.getReference("faais")
    private var data = MutableLiveData<List<FAAISDto>>()
    override val faais: LiveData<List<FAAISDto>> = data
    override suspend fun insertFAAIS(faaisDto: FAAISDto) {
        ref.child("faais-1").setValue(faaisDto)

    }

    init {
        ref.child("faais-1").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(FAAISDto::class.java)
                if (value != null) {
                    data.postValue(listOf(value))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                error(error)
            }
        })
    }
}