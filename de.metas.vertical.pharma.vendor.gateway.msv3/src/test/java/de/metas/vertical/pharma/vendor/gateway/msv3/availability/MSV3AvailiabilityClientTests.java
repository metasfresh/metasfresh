package de.metas.vertical.pharma.vendor.gateway.msv3.availability;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.v2.StockAvailabilityJAXBConvertersV2;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3TestingTools;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;

/*
 * #%L
 * de.metas.vendor.gateway.msv3
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MSV3AvailiabilityClientTests
{
	private static final int UOM_ID = 1;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	@Ignore
	public void manualTest()
	{
		final I_C_BPartner vendor = newInstance(I_C_BPartner.class);
		vendor.setAD_Org_ID(123);
		saveRecord(vendor);

		final ProductAndQuantity productAndQuantity = ProductAndQuantity.of("10055555", BigDecimal.TEN, UOM_ID);
		final AvailabilityRequestItem availabilityRequestItem = AvailabilityRequestItem.builder()
				.productAndQuantity(productAndQuantity)
				.build();

		final AvailabilityRequest request = AvailabilityRequest.builder()
				.vendorId(vendor.getC_BPartner_ID())
				.availabilityRequestItem(availabilityRequestItem)
				.build();

		final MSV3AvailiabilityClient msv3AvailiabilityClient = MSV3AvailiabilityClientImpl.builder()
				.connectionFactory(new MSV3ConnectionFactory())
				.config(MSV3TestingTools.createMSV3ClientConfig(MSV3ClientConfig.VERSION_2))
				.jaxbConverters(StockAvailabilityJAXBConvertersV2.instance)
				.build();

		final AvailabilityResponse response = msv3AvailiabilityClient.retrieveAvailability(request);
		assertThat(response).isNotNull();
	}
}
