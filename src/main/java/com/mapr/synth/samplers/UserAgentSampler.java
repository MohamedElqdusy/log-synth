/*
 * Licensed to the Ted Dunning under one or more contributor license
 * agreements.  See the NOTICE file that may be
 * distributed with this work for additional information
 * regarding copyright ownership.  Ted Dunning licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.mapr.synth.samplers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * Sample a list of headers in the style of those that might accompany a web request
 */
public class UserAgentSampler extends FieldSampler {
    private BrowserSampler browser = new BrowserSampler();
    private Map<String, StringSampler> headers = ImmutableMap.<String, StringSampler>builder()
            .put("chrome", new StringSampler("user-agents/chrome"))
            .put("firefox", new StringSampler("user-agents/firefox"))
            .put("ie", new StringSampler("user-agents/ie"))
            .put("mobile", new StringSampler("user-agents/mobile"))
            .put("opera", new StringSampler("user-agents/opera"))
            .put("safari", new StringSampler("user-agents/safari"))
            .build();

    String userAgent() {
        String br = browser.sample().asText();
        StringSampler s = headers.get(br.toLowerCase());
        if (s == null) {
            throw new IllegalStateException("Illegal browser");
        }
        return s.sample().asText();
    }

    @Override
    public JsonNode sample() {
        return new TextNode(userAgent());
    }
}
