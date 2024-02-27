package com.example.testapplication.core.datasource

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.testapplication.core.api.ProductApi
import com.example.testapplication.core.pojos.NetworkState
import com.example.testapplication.core.pojos.ResponseProductGeneric
import com.example.testapplication.core.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsDataSource(private val context: Context, private val apiInterface: ProductApi) {
    val responseInfo = MutableLiveData<ResponseProductGeneric?>()
    val networkState = MutableLiveData<NetworkState>()
    private val scope by lazy {
        CoroutineScope(SupervisorJob())
    }
    private var callInfo: Call<ResponseProductGeneric>? = null

    fun getProducts() {
        scope.launch(Dispatchers.IO) {
            if (Utils.startGooglePing(context)) startService()
            else {
                responseInfo.postValue(
                    ResponseProductGeneric(
                        message = "Requiere conexión a internet",
                        listOf()
                    )
                )
                networkState.postValue(NetworkState.NO_INTERNET)
            }
        }
    }

    private fun startService() {
        networkState.postValue(NetworkState.LOAD)
        callInfo?.cancel()
        callInfo = apiInterface.getProductsFromServer()
        callInfo?.enqueue(object : Callback<ResponseProductGeneric> {
            override fun onResponse(
                call: Call<ResponseProductGeneric>,
                response: Response<ResponseProductGeneric>
            ) {
                try {
                    if (response.isSuccessful) showDataFromService(response.body())
                    else showResponseCodeError(response.code())
                } catch (exception: Exception) {
                    showAppExceptionError(exception)
                }
            }

            override fun onFailure(call: Call<ResponseProductGeneric>, t: Throwable) {
                if (!call.isCanceled) showErrorService(t)
            }

        })
    }

    private fun showDataFromService(info: ResponseProductGeneric?) {
        responseInfo.postValue(info)
        networkState.postValue(NetworkState.OK)
    }

    private fun showResponseCodeError(code: Int) {
        responseInfo.postValue(
            ResponseProductGeneric(
                message = "Código de error: $code",
                listOf()
            )
        )
        networkState.postValue(NetworkState.ERROR_SERVICE)
    }

    private fun showAppExceptionError(exception: Exception) {
        exception.printStackTrace()
        responseInfo.postValue(
            ResponseProductGeneric(
                message = "Excepción: ${exception.message ?: "Error de aplicación"}",
                listOf()
            )
        )
        networkState.postValue(NetworkState.ERROR_APP)
    }

    private fun showErrorService(t: Throwable) {
        responseInfo.postValue(
            ResponseProductGeneric(
                message = "Error: ${t.message}",
                listOf()
            )
        )
        networkState.postValue(NetworkState.FAIL)
    }

    fun cancelServiceProducts() =  callInfo?.cancel()
}