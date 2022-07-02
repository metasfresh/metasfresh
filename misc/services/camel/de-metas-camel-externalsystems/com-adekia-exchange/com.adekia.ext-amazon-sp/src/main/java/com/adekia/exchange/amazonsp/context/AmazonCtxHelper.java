/*
 * #%L
 * ext-amazon-sp
 * %%
 * Copyright (C) 2022 Adekia
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package com.adekia.exchange.amazonsp.context;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AmazonCtxHelper {

    public static Map<String, String> AmazonAuthProperties = Stream.of(new String[][] {
            //AWSAuthenticationCredentials
//            { "basePath", "World" },
            { "accessKeyId", "accessKeyId" },
            { "secretKey", "secretKey" },
            { "region", "eu-west-1" },
            //AWSAuthenticationCredentialsProvider
            { "roleArn", "roleArn" },
            { "roleSessionName", "roleSessionName" },
            // LWAAuthorizationCredentials
            { "clientId", "clientId" },
            { "clientSecret", "clientSecret" },
//              { "refreshToken", "World" },
//              { "endpoint", "Doe" },
            //OrdersApi
 //           { "endpoint", "World" }
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

 }
