package de.metas.vertical.pharma.vendor.gateway.mvs3;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.vendor.gateway.api.model.AvailabilityRequest;
import de.metas.vendor.gateway.api.model.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.model.AvailabilityResponse;
import de.metas.vendor.gateway.msv3.model.I_MSV3_Vendor_Config;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ClientConfigRepository;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3VendorGatewayService;

/*
 * #%L
 * de.metas.vendor.gateway.mvs3
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

public class MSV3VendorGatewayServiceTests
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test()
	{
		final I_MSV3_Vendor_Config configRecord = newInstance(I_MSV3_Vendor_Config.class);
		configRecord.setMSV3_BaseUrl("http://localhost:8089/msv3/v2.0");
		configRecord.setUserID("PLA\\apotheke1");
		configRecord.setPassword("passwort");
		save(configRecord);

		final MSV3VendorGatewayService msv3VendorGatewayService = new MSV3VendorGatewayService(new MSV3ClientConfigRepository());

		final AvailabilityRequest request = AvailabilityRequest.builder()
				.vendorId(999)
				.availabilityRequestItem(new AvailabilityRequestItem("10055555", BigDecimal.TEN)).build();

		final AvailabilityResponse response = msv3VendorGatewayService.retrieveAvailability(request);
		assertThat(response).isNotNull();
	}
}
