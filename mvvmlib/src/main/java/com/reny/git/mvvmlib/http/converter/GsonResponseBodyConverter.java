/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.reny.git.mvvmlib.http.converter;

import com.google.gson.TypeAdapter;
import com.reny.git.mvvmlib.utils.LogUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String body = value.string().trim();

        if(LogUtils.isPrintResponseData) {
            LogUtils.w(body);
        }

        try {
            return adapter.fromJson(body);
        } finally {
            value.close();
        }
    }
}
