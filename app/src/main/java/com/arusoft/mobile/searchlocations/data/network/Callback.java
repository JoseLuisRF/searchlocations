package com.arusoft.mobile.searchlocations.data.network;

import java.io.IOException;

/**
 * A substitute for {@link retrofit2.Callback} that has a typed error body as well as response body
 *
 * @param <R> the type of the response body
 * @param <E> the type of the error body
 */
public interface Callback<R, E> {
    /**
     * Invoked for a received HTTP response.
     * <p>
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call {@link Response#isSuccessful()} to determine if the response indicates success.
     */
    void onResponse(Response<R, E> response);

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     */
    void onFailure(IOException e);
}
