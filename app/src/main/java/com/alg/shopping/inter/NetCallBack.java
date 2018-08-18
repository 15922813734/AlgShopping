package com.alg.shopping.inter;

/**
 * Created by Lenovo on 2017/6/5.
 */

public interface NetCallBack<T> {
    void backSuccess(T result);

    void backError(String ex, T result);
}
