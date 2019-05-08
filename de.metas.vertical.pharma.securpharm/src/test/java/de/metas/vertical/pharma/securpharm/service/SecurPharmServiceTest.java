/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.service;

import de.metas.handlingunits.HuId;
import de.metas.vertical.pharma.securpharm.SecurPharmClient;
import de.metas.vertical.pharma.securpharm.SecurPharmClientFactory;
import de.metas.vertical.pharma.securpharm.model.*;
import de.metas.vertical.pharma.securpharm.repository.SecurPharmConfigRespository;
import org.adempiere.user.UserId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

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
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAndSaveProductData() throws Exception
	{
		final String dataMatrix = "datamatrix";
		final HuId huId = HuId.ofRepoId(1);
		Mockito.when(clientFactory.createClient()).thenReturn(client);

		SecurPharmRequestLogData requestLogData = SecurPharmRequestLogData.builder()
				.responseTime(LocalDateTime.now())
				.requestTime(LocalDateTime.now())
				.requestUrl("url")
				.responseData("data")
				.clientTransactionID("id")
				.clientTransactionID("clientid")
				.build();
		SecurPharmProductDataResult productDataResult = new SecurPharmProductDataResult();
		productDataResult.setError(false);
		productDataResult.setRequestLogData(requestLogData);
		ProductData productData = ProductData.builder()
				.isActive(true)
				.expirationDate(LocalDate.now())
				.lot("lot")
				.productCode("product code")
				.productCodeType(ProductCodeType.GTIN)
				.serialNumber("serial nr")
				.build();
		productDataResult.setProductData(productData);
		Mockito.when(client.decodeDataMatrix(dataMatrix)).thenReturn(productDataResult);

		productDataResult.setHuId(HuId.ofRepoId(1));
		productDataResult.setResultId(SecurPharmProductDataResultId.ofRepoId(1));
		Mockito.when(resultService.createAndSaveResult(productDataResult)).thenReturn(productDataResult);

		SecurPharmConfig config = SecurPharmConfig.builder()
				.securPharmConfigId(SecurPharmConfigId.ofRepoId(1))
				.applicationUUID("uuid")
				.authBaseUrl("url")
				.pharmaAPIBaseUrl("url")
				.certificatePath("path")
				.supportUserId(UserId.METASFRESH)
				.keystorePassword("passw").build();
		Mockito.when(client.getConfig()).thenReturn(config);

		SecurPharmProductDataResult result = underTest.getAndSaveProductData(dataMatrix, huId);
		assertEquals(result, productDataResult);


	}
}
