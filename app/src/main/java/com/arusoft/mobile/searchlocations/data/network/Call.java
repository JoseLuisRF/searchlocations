package com.arusoft.mobile.searchlocations.data.network;


import java.io.IOException;

import okhttp3.Request;

/**
 * A substitute for {@link retrofit2.Call} that holds a typed response body and a typed error body.
 *
 * @param <R> the type of the response body
 * @param <E> the type of the error body
 */
public interface Call<R, E> {
    /**
     * Synchronously send the request and return its response.
     *
     * @throws IOException      if a problem occurred talking to the server.
     * @throws RuntimeException - (and subclasses) if an unexpected error occurs creating the request or
     *                          decoding the response.
     */
    Response<R, E> execute() throws IOException;

    /**
     * Asynchronously send the request and notify callback of its response or if an error occurred talking to
     * the server, creating the request, or processing the response.
     */
    void enqueue(Callback<R, E> callback);

    /**
     * Cancel this call. An attempt will be made to cancel in-flight calls, and if the call has not
     * yet been executed it never will be.
     */
    void cancel();


    /** see {@link retrofit2.Call#request()}
     * @return The original HTTP request.
     */
    Request request();
}