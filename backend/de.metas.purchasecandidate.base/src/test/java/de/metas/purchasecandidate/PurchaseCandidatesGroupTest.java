package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.quantity.Quantity;
import lombok.Builder;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

public class PurchaseCandidatesGroupTest
{
	private I_C_UOM uom;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
	}

	@Test
	public void test_default_isAllowPOAggregation_is_True()
	{
		final PurchaseCandidatesGroup purchaseCandidatesGroup = preparePurchaseCandidatesGroup()
				.build();

		assertThat(purchaseCandidatesGroup.isAllowPOAggregation()).isTrue();
	}

	@Test
	public void test_isAllowPOAggregation_and_VendorAggregatePOs()
	{
		assertThat(preparePurchaseCandidatesGroup().allowPOAggregation(true).vendorAggregatePOs(true).build().isAggregatePOs())
				.isTrue();
		assertThat(preparePurchaseCandidatesGroup().allowPOAggregation(true).vendorAggregatePOs(false).build().isAggregatePOs())
				.isFalse();

		assertThat(preparePurchaseCandidatesGroup().allowPOAggregation(false).vendorAggregatePOs(true).build().isAggregatePOs())
				.isFalse();
		assertThat(preparePurchaseCandidatesGroup().allowPOAggregation(false).vendorAggregatePOs(false).build().isAggregatePOs())
				.isFalse();
	}

	@Builder(builderClassName = "PurchaseCandidatesGroupBuilder", builderMethodName = "preparePurchaseCandidatesGroup")
	private PurchaseCandidatesGroup createPurchaseCandidatesGroup(
			final boolean vendorAggregatePOs,
			final Boolean allowPOAggregation)
	{
		final VendorProductInfo vendorProductInfo = VendorProductInfo.builder()
				.vendorId(BPartnerId.ofRepoId(123))
				.defaultVendor(true)
				.product(ProductAndCategoryAndManufacturerId.of(123, 123, 123))
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.vendorProductNo("123")
				.vendorProductName("product name")
				.pricingConditions(PricingConditions.builder()
										   .validFrom(TimeUtil.asInstant(Timestamp.valueOf("2017-01-01 10:10:10.0")))
										   .build())
				.aggregatePOs(vendorAggregatePOs)
				.build();

		final PurchaseCandidatesGroup.PurchaseCandidatesGroupBuilder builder = PurchaseCandidatesGroup.builder()
				.demandGroupReferences(ImmutableList.of(DemandGroupReference.EMPTY))
				.purchaseDemandId(PurchaseDemandId.ofId(123))
				.orgId(OrgId.ofRepoId(123))
				.warehouseId(WarehouseId.ofRepoId(123))
				.vendorProductInfo(vendorProductInfo)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.qtyToPurchase(Quantity.zero(uom))
				.purchasedQty(Quantity.zero(uom))
				.purchaseDatePromised(ZonedDateTime.now());
		if (allowPOAggregation != null)
		{
			builder.allowPOAggregation(allowPOAggregation);
		}
		return builder.build();
	}
}
