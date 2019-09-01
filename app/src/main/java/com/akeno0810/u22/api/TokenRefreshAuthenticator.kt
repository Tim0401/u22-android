package com.akeno0810.u22.api
import android.util.Log
import com.akeno0810.u22.AppState


import java.io.IOException
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenRefreshAuthenticator : Authenticator {

    private var login = ApiUserClientManager.apiUserClient
        .login(
            LoginUser(AppState.instance.email,
            AppState.instance.password))
        .publish().refCount()

    @Throws(IOException::class)
    override fun authenticate(route: Route, response: Response): Request? {

        // Authorizationヘッダーがない（ログイン時）は一回で終了
        if (response.request().header("Authorization") == null) {
            return null // Give up, we've already failed to authenticate.
        }

        if (responseCount(response) < 3) {
            login = ApiUserClientManager.apiUserClient
                .login(
                    LoginUser(AppState.instance.email,
                        AppState.instance.password))
                .publish().refCount()
            val jwt = login.toBlocking().first()
            AppState.instance.token = jwt.token ?: ""
            Log.d("api", AppState.instance.token)
            return response.request()
                .newBuilder()
                .header("Authorization", "Bearer " + AppState.instance.token)
                .build()
        }

        return null

    }

    private fun responseCount(response: Response?): Int {
        var result = 1
        var res = response
        while ({res = res?.priorResponse(); res }() != null) {
            result++
        }
        return result
    }
}
