package com.shoppingmall.config;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;

import feign.Response;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

import static feign.Util.UTF_8;


@Slf4j
public class FeignDecoder implements Decoder {

    private final ObjectMapper mapper;

    public FeignDecoder() {
        this(Collections.<Module>emptyList());
    }

    public FeignDecoder(Iterable<Module> modules) {
        this(new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModules(modules));
    }

    public FeignDecoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    /*
     *
     * // https://github.com/OpenFeign/feign/blob/master/jackson/src/main/java/feign/jackson/JacksonDecoder.java
     *
     * jackson decode 참고
     *
     * snake
     * */
    @Override
    public Object decode(Response response, Type type) throws IOException {

        if (response.status() == 404 || response.status() == 204){
            log.warn("status : {} , reason : {}",String.valueOf(response.status()),String.valueOf(response.reason()));
            return null;
        }
        if (response.body() == null) return null;

        Reader reader = response.body().asReader(UTF_8);

        if (!reader.markSupported()) {
            reader = new BufferedReader(reader, 1);
        }

        try {
            // Read the first byte to see if we have any data
            reader.mark(1);
            if (reader.read() == -1) {
                return null; // Eagerly returning null avoids "No content to map due to end-of-input"
            }
            reader.reset();
            return mapper.readValue(reader, mapper.constructType(type));
        } catch (RuntimeJsonMappingException e) {
            if (e.getCause() != null && e.getCause() instanceof IOException) {
                throw IOException.class.cast(e.getCause());
            }
            throw e;
        }
    }
}

