/**
 * Copyright (C) 2014 Arnaud Piroelle (contact@arnaudpiroelle.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arnaudpiroelle.marvel.api.util;

import org.springframework.util.DigestUtils;

/**
 * Created by Arnaud Piroelle on 04/03/14.
 */
public class MD5Util {
    public static String hash(String publicApiKey, String privateApiKey, String timestamp) {
        String toHash = timestamp + privateApiKey + publicApiKey;
        return DigestUtils.md5DigestAsHex(toHash.getBytes());
    }
}
