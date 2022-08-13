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

import com.adekia.exchange.amazonsp.client.orders.api.ShipmentApi;
import com.adekia.exchange.amazonsp.client.shipments.api.ShippingApi;
import com.adekia.exchange.amazonsp.context.AmazonCtxHelper;
import com.amazon.SellingPartnerAPIAA.AWSAuthenticationCredentials;
import com.amazon.SellingPartnerAPIAA.AWSAuthenticationCredentialsProvider;
import com.amazon.SellingPartnerAPIAA.LWAAuthorizationCredentials;

public class AmazonShippingApiHelper
{

	public static ShippingApi getShippingAPI()
	{
		AWSAuthenticationCredentials awsAuthenticationCredentials = AWSAuthenticationCredentials.builder()
				.accessKeyId(AmazonCtxHelper.AmazonAuthProperties.get("accessKeyId"))
				.secretKey(AmazonCtxHelper.AmazonAuthProperties.get("secretKey"))
				.region(AmazonCtxHelper.AmazonAuthProperties.get("region"))
				.build();

		AWSAuthenticationCredentialsProvider awsAuthenticationCredentialsProvider = AWSAuthenticationCredentialsProvider.builder()
				.roleArn(AmazonCtxHelper.AmazonAuthProperties.get("roleArn"))
				.roleSessionName(AmazonCtxHelper.AmazonAuthProperties.get("roleSessionName"))
				.build();

		LWAAuthorizationCredentials lwaAuthorizationCredentials = LWAAuthorizationCredentials.builder()
				.clientId(AmazonCtxHelper.AmazonAuthProperties.get("clientId"))
				.clientSecret(AmazonCtxHelper.AmazonAuthProperties.get("clientSecret"))
				.refreshToken(AmazonCtxHelper.AmazonAuthProperties.get("refreshToken"))
				.endpoint("https://api.amazon.com/auth/o2/token")
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
