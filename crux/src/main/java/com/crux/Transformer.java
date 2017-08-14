package com.crux;

/**
 * Created by gauravarora on 14/08/17.
 */

public interface Transformer<From, To> {

    To transform(From from);

}
