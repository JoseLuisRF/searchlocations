package com.arusoft.mobile.searchlocations.data.network;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * An Adapter that adapts retrofit's Call object custom call interface
 */
class CustomCallAdapter<R, E> implements Call {

    private final retrofit2.Call<R> call;
    private final Retrofit retrofit;
    private final Type errorType;
    private final Annotation[] annotations;
    private final Executor executor;

    CustomCallAdapter(retrofit2.Call<R> call, Retrofit retrofit, Type errorType, Annotation[] annotations) {
        this.call = call;
        this.retrofit = retrofit;
        this.errorType = errorType;
        this.annotations = annotations;
        executor = getExecutor();
    }

    @Override
    public Response execute() throws IOException {
        final retrofit2.Response<R> rResponse = call.execute();
        return new Response<R, E>() {
            @Override
            public R body() {
                return rResponse.body();
            }

            @Nullable
            @Override
            public E error() {
                Converter<ResponseBody, E> converter = retrofit.responseBodyConverter(errorType, annotations);
                try {
                    ResponseBody errorBody = rResponse.errorBody();
                    if (errorBody != null) {
                        return converter.convert(errorBody);
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            public boolean isSuccessful() {
                return rResponse.isSuccessful();
            }

            @Override
            public int code() {
                return rResponse.code();
            }

            @Override
            public Headers headers() {
                return rResponse.headers();
            }

            //This is going to be used to get a common analytics endpoint without dynamic parameters.
            @Override
            public String endpointPath() {
                return rResponse.raw().request().url().toString();
            }

            @Override
            public String message() {
                return rResponse.message();
            }
        };
    }

    @Override
    public void enqueue(final Callback callback) {
        call.enqueue(new retrofit2.Callback<R>() {
            @Override
            public void onResponse(retrofit2.Call<R> call, final retrofit2.Response<R> response) {
                //noinspection unchecked
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(new Response<R, E>() {
                            @Override
                            public R body() {
                                return response.body();
                            }

                            @Nullable
                            @Override
                            public E error() {
                                try {
                                    Converter<ResponseBody, E> converter = retrofit.responseBodyConverter(errorType, annotations);
                                    ResponseBody errorBody = response.errorBody();
                                    if (errorBody != null) {
                                        return converter.convert(errorBody);
                                    } else {
                                        return null;
                                    }
                                } catch (IOException e) {
                                    //TODO return null as execute till we have discussion what is the best way to deal with it
                                    return null;
                                }
                            }

                            @Override
                            public boolean isSuccessful() {
                                return response.isSuccessful();
                            }

                            @Override
                            public int code() {
                                return response.code();
                            }

                            @Override
                            public Headers headers() {
                                return response.headers();
                            }

                            @Override
                            public String endpointPath() {
                                return response.raw().request().url().toString();
                            }

                            @Override
                            public String message() {
                                return response.message();
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(retrofit2.Call<R> call, final Throwable t) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(new IOException(t));
                    }
                });
            }
        });
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public Request request() {
        return call.request();
    }

    @NonNull
    private Executor getExecutor() {
        Executor executor = retrofit.callbackExecutor();
        if (executor == null) {
            executor = new Executor() {
                @Override
                public void execute(@NonNull Runnable command) {
                    command.run();
                }
            };
        }
        return executor;
    }
}