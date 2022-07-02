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
            { "accessKeyId", "AKIAZVIDQXA3NBBTEUE4" },
            { "secretKey", "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" },
            { "region", "eu-west-1" },
            //AWSAuthenticationCredentialsProvider
            { "roleArn", "arn:aws:iam::664116705334:user/am187" },
            { "roleSessionName", "AWSMarketplaceSellerFullAccess" },
            // LWAAuthorizationCredentials
            { "clientId", "amzn1.application-oa2-client.2580e413e4244bf7b105191d077aa9a7" },
            { "clientSecret", "9dc66603b2b07d713a43b042798c790c370735eae5d30dee6f54ed28b0862c69" },
            { "refreshToken", "World" },
            { "endpoint", "Doe" },
            //OrdersApi
 //           { "endpoint", "World" }
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

 }
