package com.hdz.serialization.converter

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import org.springframework.http.MediaType
import java.nio.charset.Charset

class YamlJackson2HttpMessageConverter: AbstractJackson2HttpMessageConverter(
    YAMLMapper().apply {
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
    },
    MediaType("application", "x-yaml"),
    MediaType("text", "yaml"),
    MediaType("application", "x-yaml", Charset.forName("UTF-8")),
    MediaType("application", "x-yaml", Charset.forName("ISO-8859-1")),
    MediaType("application", "yml"),
    MediaType("text", "yml")
)