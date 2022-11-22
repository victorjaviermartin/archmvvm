package com.victormartin.archmvvm.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.victormartin.archmvvm.data.model.DataStatsSeasonAverageNetworkModel
import com.victormartin.archmvvm.domain.GetPlayerStatsUseCase
import com.victormartin.archmvvm.domain.GetPlayersByPageUseCase
import com.victormartin.archmvvm.domain.GetPlayersUseCase
import com.victormartin.archmvvm.domain.model.DataDomainModel
import com.victormartin.archmvvm.domain.model.PlayersDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val getPlayersUseCase: GetPlayersUseCase,
    private val getPlayerStatsUseCase: GetPlayerStatsUseCase,
    private val getPlayersByPageUseCase: GetPlayersByPageUseCase
    ) : ViewModel() {

    val playersModel = MutableLiveData<PlayersDomainModel>()
    val playerStats = MutableLiveData<DataStatsSeasonAverageNetworkModel>()
    val isLoading = MutableLiveData<Boolean>()
    val playersLoaded = MutableLiveData<Boolean>()
    val eastWestIconsLoaded = MutableLiveData<Boolean>()

    val latestSelectedPlayer = MutableLiveData<DataDomainModel>()
    val isLoadedMoreData = MutableLiveData<Boolean>()

    lateinit var westIcon: Bitmap
    lateinit var eastIcon: Bitmap

    fun getAllPlayers(context: Context) {
        viewModelScope.launch {

            isLoading.postValue(true)
            val result = getPlayersUseCase()

            if (result.data.size > 0) {
                playersModel.postValue(result)
                playersLoaded.postValue(true)
                isLoading.postValue(false)
            }else{
                playersLoaded.postValue(false)
                isLoading.postValue(false)
            }
        }
    }

    fun getAllPlayers(page: Int) {
        viewModelScope.launch {

            isLoading.postValue(true)
            val result = getPlayersByPageUseCase(page)

            if (result.data.size > 0) {
                playersModel.postValue(result)
                isLoadedMoreData.postValue(true)
                playersLoaded.postValue(true)
                isLoading.postValue(false)
            }else{
                isLoadedMoreData.postValue(false)
                playersLoaded.postValue(false)
                isLoading.postValue(false)
            }
        }
    }

    fun getEastWestIcons(context: Context){
        viewModelScope.launch(Dispatchers.IO) {

            val imageLoader = ImageLoader.Builder(context)
                .memoryCache {
                    MemoryCache.Builder(context)
                        .maxSizePercent(0.25)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir.resolve("image_cache"))
                        .maxSizePercent(0.02)
                        .build()
                }
                .components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()

            val requestEastImg = ImageRequest.Builder(context)
                .data("https://content.sportslogos.net/logos/6/999/full/2995.gif")
                .allowHardware(false) // Disable hardware bitmaps.
                .build()

            val requestWestImg = ImageRequest.Builder(context)
                .data("https://content.sportslogos.net/logos/6/1001/full/2996.gif")
                .allowHardware(false) // Disable hardware bitmaps.
                .build()

            val resultRequestEastImg = (imageLoader.execute(requestEastImg) as SuccessResult).drawable
            eastIcon = (resultRequestEastImg as BitmapDrawable).bitmap

            val resultRequestWestImg = (imageLoader.execute(requestWestImg) as SuccessResult).drawable
            westIcon = (resultRequestWestImg as BitmapDrawable).bitmap

            if(eastIcon != null && westIcon != null){
                eastWestIconsLoaded.postValue(true)
            }else{
                eastWestIconsLoaded.postValue(false)
            }
        }
    }

    fun getStatsByPlayerId(season: Int, player_ids: List<Int>) {
        viewModelScope.launch {

            isLoading.postValue(true)
            val result = getPlayerStatsUseCase(season, player_ids)

            if (result != null) {
                isLoading.postValue(false)
                playerStats.postValue(result)
            }
        }
    }

}