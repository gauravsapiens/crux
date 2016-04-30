package com.example.crux.api;

import com.example.crux.api.dto.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public interface SampleService {

    @GET("products")
    Call<ProductResponse> listProducts(@Query("category") String category, @Query("page") int page);

}
