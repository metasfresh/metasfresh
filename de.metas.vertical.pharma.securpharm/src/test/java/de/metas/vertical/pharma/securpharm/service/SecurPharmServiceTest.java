/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;

import de.metas.handlingunits.HuId;
import de.metas.user.UserId;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClient;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClientFactory;
import de.metas.vertical.pharma.securpharm.model.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.model.DecodeDataMatrixResponse;
import de.metas.vertical.pharma.securpharm.model.ProductCodeType;
import de.metas.vertical.pharma.securpharm.model.ProductDetails;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.model.SecurPharmLog;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.model.VerifyProductResponse;
import de.metas.vertical.pharma.securpharm.model.schema.ExpirationDate;
import de.metas.vertical.pharma.securpharm.model.schema.ProductPackageState;
import de.metas.vertical.pharma.securpharm.repository.SecurPharmConfigRespository;

public class SecurPharmServiceTest
{

	@InjectMocks
	private SecurPharmService underTest;

	@Mock
	private SecurPharmClientFactory clientFactory;

	@Mock
	private SecurPharmResultService resultService;

	@Mock
	private SecurPharmConfigRespository configRespository;

	@Mock
	private SecurPharmClient client;

	@Before
	public void initMocks()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAndSaveProductData() throws Exception
	{
		final DataMatrixCode dataMatrix = DataMatrixCode.ofString("dummy datamatrix");
		Mockito.when(clientFactory.createClient()).thenReturn(client);

		final ProductDetails productDetails = ProductDetails.builder()
				.activeStatus(ProductPackageState.ACTIVE)
				.expirationDate(ExpirationDate.ofLocalDate(LocalDate.now()))
				.lot("lot")
				.productCode("product code")
				.productCodeType(ProductCodeType.GTIN)
				.serialNumber("serial nr")
				.build();

		Mockito.when(client.decodeDataMatrix(dataMatrix))
				.thenReturn(DecodeDataMatrixResponse.builder()
						.productDetails(productDetails)
						.log(generateDummyLog())
						.build());

		Mockito.when(client.verifyProduct(productDetails))
				.thenReturn(VerifyProductResponse.builder()
						.resultCode("200")
						.productDetails(productDetails)
						.log(generateDummyLog())
						.build());

		Mockito.when(client.getConfig())
				.thenReturn(SecurPharmConfig.builder()
						.applicationUUID("uuid")
						.authBaseUrl("url")
						.pharmaAPIBaseUrl("url")
						.certificatePath("path")
						.supportUserId(UserId.METASFRESH)
						.keystorePassword("passw")
						.build());

		final HuId huId = HuId.ofRepoId(1);
		final SecurPharmProduct actualResult = underTest.getAndSaveProduct(dataMatrix, huId);
		assertThat(actualResult)
				.isEqualTo(SecurPharmProduct.builder()
						.productDetails(productDetails)
						.huId(huId)
						.build());
	}

	private SecurPharmLog generateDummyLog()
	{
		return SecurPharmLog.builder()
				.responseTime(Instant.now())
				.requestTime(Instant.now())
				.requestMethod(HttpMethod.GET)
				.requestUrl("url")
				.responseData("data")
				.clientTransactionId("id")
				.clientTransactionId("clientid")
				.error(false)
				.build();
	}
}
