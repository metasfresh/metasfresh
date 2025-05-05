package de.metas.purchasecandidate.grossprofit;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.quantity.Quantity;

import java.sql.Timestamp;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

public class PurchaseProfitInfoRequestTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void builder_works_with_null_salesOrderAndLineId()
	{
		final VendorProductInfo vendorProductInfo = VendorProductInfo.builder()
				.defaultVendor(false)
				.vendorId(BPartnerId.ofRepoId(20))
				.vendorProductName("vendorProductName")
				.vendorProductNo("vendorProductNo")
				.product(ProductAndCategoryAndManufacturerId.of(30, 40, 50))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNull(60))
				.pricingConditions(PricingConditions.builder()
										   .validFrom(TimeUtil.asInstant(Timestamp.valueOf("2017-01-01 10:10:10.0")))
										   .build())
				.build();

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);

		// invoke the builder under test
		final PurchaseProfitInfoRequest result = PurchaseProfitInfoRequest.builder()
				.salesOrderAndLineId(null)
				.qtyToPurchase(Quantity.of(TEN, uomRecord))
				.vendorProductInfo(vendorProductInfo)
				.build();
		assertThat(result.getSalesOrderAndLineIds()).isNotNull();
		assertThat(result.getSalesOrderAndLineIds()).isEmpty();
	}

}
