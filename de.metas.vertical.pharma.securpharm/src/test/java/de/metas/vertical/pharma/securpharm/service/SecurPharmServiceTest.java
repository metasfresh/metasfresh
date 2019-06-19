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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryLineRepository;
import de.metas.user.UserId;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmaActionRepository;
import de.metas.vertical.pharma.securpharm.client.DecodeDataMatrixClientResponse;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClient;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClientFactory;
import de.metas.vertical.pharma.securpharm.client.VerifyProductClientResponse;
import de.metas.vertical.pharma.securpharm.client.schema.JsonExpirationDate;
import de.metas.vertical.pharma.securpharm.client.schema.JsonProductPackageState;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfigRespository;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLog;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLogRepository;
import de.metas.vertical.pharma.securpharm.product.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.product.ProductCodeType;
import de.metas.vertical.pharma.securpharm.product.ProductDetails;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductRepository;

public class SecurPharmServiceTest
{
	private SecurPharmService underTest;

	@Mock
	private SecurPharmClientFactory clientFactory;
	@Mock
	private SecurPharmClient client;
	@Mock
	private SecurPharmConfigRespository configRespository;
	@Mock
	private SecurPharmProductRepository productsRepo;
	@Mock
	private SecurPharmaActionRepository actionsRepo;
	@Mock
	private SecurPharmLogRepository logsRepo;
	@Mock
	private InventoryLineRepository inventoryRepo;

	@Before
	public void initMocks()
	{
		MockitoAnnotations.initMocks(this);

		underTest = new SecurPharmService(
				clientFactory,
				configRespository,
				productsRepo,
				actionsRepo,
				logsRepo,
				inventoryRepo);
	}

	@Test
	public void testGetAndSaveProductData() throws Exception
	{
		final DataMatrixCode dataMatrix = DataMatrixCode.ofString("dummy datamatrix");
		Mockito.when(clientFactory.createClient()).thenReturn(client);

		final ProductDetails productDetails = ProductDetails.builder()
				.activeStatus(JsonProductPackageState.ACTIVE)
				.expirationDate(JsonExpirationDate.ofLocalDate(LocalDate.now()))
				.lot("lot")
				.productCode("product code")
				.productCodeType(ProductCodeType.GTIN)
				.serialNumber("serial nr")
				.build();

		Mockito.when(client.decodeDataMatrix(dataMatrix))
				.thenReturn(DecodeDataMatrixClientResponse.builder()
						.productDetails(productDetails)
						.log(generateDummyLog())
						.build());

		Mockito.when(client.verifyProduct(productDetails))
				.thenReturn(VerifyProductClientResponse.builder()
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
