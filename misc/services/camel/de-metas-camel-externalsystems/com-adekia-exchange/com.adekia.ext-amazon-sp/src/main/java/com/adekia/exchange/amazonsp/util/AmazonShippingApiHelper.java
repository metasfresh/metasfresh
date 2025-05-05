/*
 * #%L
 * ext-amazon-sp
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

package com.adekia.exchange.amazonsp.util;

import com.adekia.exchange.amazonsp.client.shipments.api.ShippingApi;
import com.adekia.exchange.context.Ctx;
import com.amazon.SellingPartnerAPIAA.AWSAuthenticationCredentials;
import com.amazon.SellingPartnerAPIAA.AWSAuthenticationCredentialsProvider;
import com.amazon.SellingPartnerAPIAA.LWAAuthorizationCredentials;

import java.util.UUID;

public class AmazonShippingApiHelper
{

	public static ShippingApi getShippingAPI(Ctx ctx)
	{
		AWSAuthenticationCredentials awsAuthenticationCredentials = AWSAuthenticationCredentials.builder()
				.accessKeyId(ctx.getProperties().get("accessKeyId"))
				.secretKey(ctx.getProperties().get("secretKey"))
				.region(ctx.getProperties().get("region"))
				.build();

		AWSAuthenticationCredentialsProvider awsAuthenticationCredentialsProvider = AWSAuthenticationCredentialsProvider.builder()
				.roleArn(ctx.getProperties().get("roleArn"))
				.roleSessionName(UUID.randomUUID().toString())
				.build();

		LWAAuthorizationCredentials lwaAuthorizationCredentials = LWAAuthorizationCredentials.builder()
				.clientId(ctx.getProperties().get("clientId"))
				.clientSecret(ctx.getProperties().get("clientSecret"))
				.refreshToken(ctx.getProperties().get("refreshToken"))
				.endpoint(ctx.getProperties().get("LwaEndpoint")) // https://api.amazon.com/auth/o2/token
				.build();

	/*
		LWAAuthorizationCredentials lwaAuthorizationCredentials =
				LWAAuthorizationCredentials.builder()
						.clientId(AmazonCtxHelper.AmazonAuthProperties.get("clientId"))
						.clientSecret(AmazonCtxHelper.AmazonAuthProperties.get("clientSecret"))
						.withScopes(SCOPE_NOTIFICATIONS_API, SCOPE_MIGRATION_API)
						.endpoint("https://api.amazon.com/auth/o2/token")
						.build();
*/
		ShippingApi shippingApi = new ShippingApi.Builder()
				.awsAuthenticationCredentials(awsAuthenticationCredentials)
				.lwaAuthorizationCredentials(lwaAuthorizationCredentials)
				.awsAuthenticationCredentialsProvider(awsAuthenticationCredentialsProvider)
				.endpoint("https://sellingpartnerapi-na.amazon.com")
				.build();

		return shippingApi;

	}

}
