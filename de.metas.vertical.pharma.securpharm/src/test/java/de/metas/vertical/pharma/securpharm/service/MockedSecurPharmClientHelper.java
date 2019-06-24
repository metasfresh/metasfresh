package de.metas.vertical.pharma.securpharm.service;

import static org.mockito.ArgumentMatchers.any;

import java.time.Instant;
import java.util.Optional;

import org.junit.Ignore;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;

import de.metas.user.UserId;
import de.metas.vertical.pharma.securpharm.client.DecodeDataMatrixClientResponse;
import de.metas.vertical.pharma.securpharm.client.DecommissionClientResponse;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClient;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClientFactory;
import de.metas.vertical.pharma.securpharm.client.UndoDecommissionClientResponse;
import de.metas.vertical.pharma.securpharm.client.VerifyProductClientResponse;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfigRespository;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLog;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLog.SecurPharmLogBuilder;
import de.metas.vertical.pharma.securpharm.product.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.product.ProductDetails;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.securpharm
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Ignore
public class MockedSecurPharmClientHelper
{
	private final SecurPharmClient client;
	@Getter
	private final SecurPharmConfigRespository configRespository;
	@Getter
	private final SecurPharmClientFactory clientFactory;

	@Builder
	private MockedSecurPharmClientHelper(
			@NonNull final UserId supportUserId)
	{
		//
		// Setup config & client
		configRespository = Mockito.mock(SecurPharmConfigRespository.class);
		clientFactory = Mockito.mock(SecurPharmClientFactory.class);
		client = Mockito.mock(SecurPharmClient.class);
		//
		final SecurPharmConfig config = createDummyConfig(supportUserId);
		Mockito.when(configRespository.getDefaultConfig()).thenReturn(Optional.of(config));
		Mockito.when(clientFactory.createClient(config)).thenReturn(client);
		Mockito.when(client.getConfig()).thenReturn(config);
		Mockito.when(client.getSupportUserId()).thenCallRealMethod();
	}

	private static SecurPharmConfig createDummyConfig(
			@NonNull final UserId supportUserId)
	{
		return SecurPharmConfig.builder()
				.applicationUUID("uuid")
				.authBaseUrl("url")
				.pharmaAPIBaseUrl("url")
				.certificatePath("path")
				.supportUserId(supportUserId)
				.keystorePassword("passw")
				.build();
	}

	private static SecurPharmLog generateDummyLogOK()
	{
		return prepareDummyLog()
				.error(false)
				.build();
	}

	private static SecurPharmLog generateDummyLogERROR()
	{
		return prepareDummyLog()
				.error(true)
				.build();
	}

	private static SecurPharmLogBuilder prepareDummyLog()
	{
		return SecurPharmLog.builder()
				.responseTime(Instant.now())
				.requestTime(Instant.now())
				.requestMethod(HttpMethod.GET)
				.requestUrl("requestUrl")
				.responseData("responseData")
				.clientTransactionId("dummyClientTransactionId")
				.serverTransactionId("dummyServerTransactionId")
				.error(false);
	}

	public void mockDecodeAndVerifyOK(
			final DataMatrixCode dataMatrix,
			final ProductDetails productDetails)
	{
		mockDecodeOK(dataMatrix, productDetails);
		mockVerifyOK(productDetails);
	}

	public void mockDecodeOK(final DataMatrixCode dataMatrix, final ProductDetails productDetailsToReturn)
	{
		Mockito.when(client.decodeDataMatrix(dataMatrix))
				.thenReturn(DecodeDataMatrixClientResponse.builder()
						.productDetails(productDetailsToReturn)
						.log(generateDummyLogOK())
						.build());
	}

	public void mockDecodeERROR(final DataMatrixCode dataMatrix)
	{
		Mockito.when(client.decodeDataMatrix(dataMatrix))
				.thenReturn(DecodeDataMatrixClientResponse.builder()
						.log(generateDummyLogERROR())
						.build());
	}

	public void mockVerifyOK(final ProductDetails productDetails)
	{
		Mockito.when(client.verifyProduct(productDetails))
				.thenReturn(VerifyProductClientResponse.builder()
						.resultCode("200")
						.productDetails(productDetails)
						.log(generateDummyLogOK())
						.build());
	}

	public void mockVerifyERROR(final ProductDetails productDetails)
	{
		Mockito.when(client.verifyProduct(productDetails))
				.thenReturn(VerifyProductClientResponse.builder()
						.resultCode("200")
						.productDetails(null)
						.log(generateDummyLogERROR())
						.build());
	}

	public void mockDecommissionOK(
			@NonNull final ProductDetails productDetails,
			@NonNull final String serverTransactionId)
	{
		Mockito.when(client.decommission(productDetails))
				.thenReturn(DecommissionClientResponse.builder()
						.productDetails(productDetails)
						.log(prepareDummyLog()
								.serverTransactionId(serverTransactionId)
								.build())
						.build());
	}

	public void mockDecommissionERROR(
			@NonNull final String serverTransactionId)
	{
		Mockito.when(client.decommission(any()))
				.thenReturn(DecommissionClientResponse.builder()
						.productDetails(null)
						.log(prepareDummyLog()
								.error(true)
								.serverTransactionId(serverTransactionId)
								.build())
						.build());
	}

	public void mockUndoDecommissionOK(@NonNull final ProductDetails productDetails)
	{
		final String serverTransactionId = productDetails.getDecommissionedServerTransactionId();

		Mockito.when(client.undoDecommission(productDetails, serverTransactionId))
				.thenReturn(UndoDecommissionClientResponse.builder()
						.productDetails(productDetails)
						.log(prepareDummyLog()
								.serverTransactionId(serverTransactionId)
								.build())
						.build());
	}

	public void mockUndoDecommissionError(@NonNull final ProductDetails productDetails)
	{
		final String serverTransactionId = productDetails.getDecommissionedServerTransactionId();

		Mockito.when(client.undoDecommission(productDetails, serverTransactionId))
				.thenReturn(UndoDecommissionClientResponse.builder()
						.productDetails(null)
						.log(prepareDummyLog()
								.error(true)
								.serverTransactionId(serverTransactionId)
								.build())
						.build());
	}

}
