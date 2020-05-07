package de.metas.vertical.pharma.vendor.gateway.msv3.availability;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3TestingTools;
import de.metas.vertical.pharma.vendor.gateway.msv3.availability.MSV3AvailiabilityClient;

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
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	@Ignore
	public void manualTest()
	{
		MSV3TestingTools.setDBVersion(MSV3AvailiabilityClientTests.class.getSimpleName());

		final ProductAndQuantity productAndQuantity = new ProductAndQuantity("10055555", BigDecimal.TEN);
		final AvailabilityRequestItem availabilityRequestItem = AvailabilityRequestItem.builder()
				.productAndQuantity(productAndQuantity).build();

		final AvailabilityRequest request = AvailabilityRequest.builder()
				.vendorId(999)
				.availabilityRequestItem(availabilityRequestItem).build();

		final MSV3AvailiabilityClient msv3AvailiabilityClient = new MSV3AvailiabilityClient(
				new MSV3ConnectionFactory(),
				MSV3TestingTools.createMSV3ClientConfig());

		final AvailabilityResponse response = msv3AvailiabilityClient.retrieveAvailability(request);
		assertThat(response).isNotNull();
	}
}
