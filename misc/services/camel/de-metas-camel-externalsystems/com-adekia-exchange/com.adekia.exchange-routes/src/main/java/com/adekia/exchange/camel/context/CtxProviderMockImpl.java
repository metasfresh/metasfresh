/*
 * #%L
 * exchange-routes
 * %%
 * Copyright (C) 2022 metas GmbH
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

package com.adekia.exchange.camel.context;

import com.adekia.exchange.context.Ctx;
import com.adekia.exchange.context.CtxProvider;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Template, copy, set good values and uncomment next 2 lines in the copy
//@Component
//@ConditionalOnProperty(prefix = "ctx", name = "provider", havingValue = "mock")
public class CtxProviderMockImpl implements CtxProvider
{
    public static Map<String, String> AmazonAuthProperties = Stream.of(new String[][] {
            //AWSAuthenticationCredentials
            //            { "basePath", "World" },
            { "accessKeyId", "accessKeyId" },
            { "secretKey", "secretKey" },
            { "region", "eu-west-1" },
            //AWSAuthenticationCredentialsProvider
            { "roleArn", "roleArn" },
            //            { "roleSessionName", "apiaccess" },  # USING UUID now
            // LWAAuthorizationCredentials
            { "clientId", "clientId" },
            { "clientSecret", "clientSecret" },
            { "refreshToken", "refreshToken" },
            { "LwaEndpoint", "https://api.amazon.com/auth/o2/token" },
            //OrdersApi
            { "endpoint", "https://sellingpartnerapi-eu.amazon.com" }
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    @Override
    public Ctx getCtx(final Object ctx) throws Exception {
        return Ctx.builder()
                .properties(AmazonAuthProperties)
                .build();
    }
}
