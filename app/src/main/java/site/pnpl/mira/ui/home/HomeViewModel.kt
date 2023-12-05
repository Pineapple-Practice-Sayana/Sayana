package site.pnpl.mira.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import site.pnpl.mira.App
import site.pnpl.mira.data.CheckInRepository
import site.pnpl.mira.data.entity.CheckIn
import site.pnpl.mira.data.entity.asCheckInUI
import site.pnpl.mira.model.CheckInUI
import site.pnpl.mira.model.EmotionsList
import site.pnpl.mira.model.FactorsList
import site.pnpl.mira.model.asCheckIn
import site.pnpl.mira.utils.Event
import site.pnpl.mira.utils.MiraDateFormat
import java.util.Calendar
import javax.inject.Inject
import kotlin.random.Random

class HomeViewModel : ViewModel() {

    @Inject
    lateinit var repository: CheckInRepository

    init {
        App.instance.appComponent.inject(this)
    }

    private fun newSaveEvent(checkIns: List<CheckInUI>) {
        saveEvent.postValue(Event(checkIns))
    }

    private val saveEvent: MutableLiveData<Event<List<CheckInUI>>> = MutableLiveData<Event<List<CheckInUI>>>()

    fun onSaveEvent(): LiveData<Event<List<CheckInUI>>> {
        return saveEvent
    }

    fun getAllCheckIns() {
        viewModelScope.launch {
            repository.getAllCheckIns()
        }
    }

//    fun getCheckInForPeriod(startPeriod: Long, endPeriod: Long) {
//
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                val checkIns = repository.getCheckInForPeriod(startPeriod, endPeriod)
//                val checkInsUIMap = checkIns.map {
//                    it.asCheckInUI()
//                }
//                val checkInSorted = checkInsUIMap.sortedByDescending { it.createdAtLong }
//                newSaveEvent(checkInSorted)
//            }
//        }
//    }

    fun deleteListOfCheckIns(checkInsUI: List<CheckInUI>){
        viewModelScope.launch {
            val checkIns = checkInsUI.map { it.asCheckIn() }
            repository.deleteListOfCheckIns(checkIns)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun insertListOfCheckIns() {
        val list = mutableListOf<CheckIn>()
        viewModelScope.launch {
            repeat(100) {
                val date = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    add(Calendar.DAY_OF_YEAR, -it)
                }.timeInMillis

                val checkIn = CheckIn(
                    id = 0,
                    emotionId = EmotionsList.emotions[Random.nextInt(0, EmotionsList.emotions.size - 1)].id,
                    factorId = FactorsList.factors[Random.nextInt(0, FactorsList.factors.size - 1)].id,
                    exercisesId = 0,
                    note = "",
                    createdAt = MiraDateFormat(date).convertToDataTimeISO8601(),
                    createdAtLong = date,
                    editedAt = "",
                    isSynchronized = 0
                )
                list.add(checkIn)
            }
            repository.insertListOfCheckIns(list)
        }
    }

    private val _isLoaded = MutableLiveData<Boolean>()
    val isLoaded: LiveData<Boolean> = _isLoaded

    fun getCheckInForPeriod(startPeriod: Long, endPeriod: Long): Flow<PagingData<CheckInUI>> {
        return Pager(PagingConfig(
            pageSize = 20,
            maxSize = 60)) {
            repository.getCheckInForPeriodP(startPeriod, endPeriod)
        }.flow
            .map {
                _isLoaded.postValue(false)
                it.map { checkIn ->
                    checkIn.asCheckInUI()
                }
            }
            .cachedIn(viewModelScope)
    }
}