package com.arusoft.mobile.searchlocations.data.network;

import androidx.annotation.Nullable;

import static com.arusoft.mobile.searchlocations.data.network.HttpErrorCodes.NOT_FOUND_ERROR;


/**
 * Common class used by API responses.
 *
 * @param <R, E>
 */
public class ApiResponse<R, E> {
    @Nullable
    public R body;
    @Nullable
    public E errorBody;
    public Throwable exception;
    public int code;

    public ApiResponse(Throwable error) {
        code = NOT_FOUND_ERROR;
        body = null;
        errorBody = null;
        exception = error;
    }

    public ApiResponse(Response<R, E> response) {
        code = response.code();
        body = response.body();
        exception = null;

        if (isSuccessful()) {
            errorBody = null;
        } else {
            try {
                errorBody = response.error();
            } catch (Exception ex) {
                errorBody = null;
                exception = ex;
            }
        }
    }

    public boolean isSuccessful() {
        return code >= HttpErrorCodes.SUCCESS && code < HttpErrorCodes.REDIRECTION;
    }
}

