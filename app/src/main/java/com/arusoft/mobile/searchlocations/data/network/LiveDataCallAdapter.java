package com.arusoft.mobile.searchlocations.data.network;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
 *
 * @param <R> Response class when success
 * @param <E> Response class when error
 */
public class LiveDataCallAdapter<R, E> implements CallAdapter<R, LiveData<ApiResponse<R, E>>> {
    private final Type responseType;
    private final Type errorType;
    private final Annotation[] annotations;
    private final Retrofit retrofit;

    public LiveDataCallAdapter(Type responseType,
                               Type errorType,
                               Annotation[] annotations,
                               Retrofit retrofit) {
        this.responseType = responseType;
        this.errorType = errorType;
        this.annotations = annotations;
        this.retrofit = retrofit;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R, E>> adapt(final Call<R> call) {
        final CustomCallAdapter customCallAdapter = new CustomCallAdapter<R, E>(
                call,
                retrofit,
                errorType,
                annotations);

        return new LiveData<ApiResponse<R, E>>() {
            AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    customCallAdapter.enqueue(new Callback() {
                        @Override
                        public void onResponse(Response response) {
                            postValue(new ApiResponse<R, E>(response));
                        }

                        @Override
                        public void onFailure(IOException e) {
                            postValue(new ApiResponse<R, E>(e));
                        }
                    });
                }
            }
        };
    }
}
