package com.arusoft.mobile.searchlocations.data.network;


import androidx.annotation.Nullable;
import okhttp3.Headers;

/**
 * A substitute for {@link retrofit2.Response} which has a typed error body in addition to the typed response
 * body.
 *
 * @param <R> the type of the response body
 * @param <E> the type of the error body
 */
public interface Response<R, E> {
    /**
     * @return the response body
     */
    R body();

    /**
     * @return the error; possibly {@code null}
     */
    @Nullable
    E error();

    /**
     * Checks whether this request was successful, by delegating to the underlying retrofit Response.
     * Retrofit considers response codes >= 200 and =< 300 as successful.
     *
     * @return true if the request was successful; false otherwise
     */
    boolean isSuccessful();


    /**
     * @return http code
     */
    int code();

    /**
     * @return headers
     */
    Headers headers();

    /**
     * gets the path of the endpoint.  This will mostly be used for analytics
     * @return the path of the endpoint with generic parameters
     */
    String endpointPath();

    /** wraps {@link retrofit2.Response#message()} */
    String message();
}
