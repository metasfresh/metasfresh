/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.order.createFrom.po_from_so.impl;

import de.metas.adempiere.model.I_M_Product;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.metas.order.createFrom.po_from_so.impl.CreatePOFromSOsAggregationKeyBuilder.ON_MISSING_C_B_PARTNER_PRODUCT_ERROR;
import static de.metas.order.createFrom.po_from_so.impl.CreatePOFromSOsAggregationKeyBuilder.SYSCONFIG_ON_MISSING_C_B_PARTNER_PRODUCT;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class CreatePOFromSOsAggregationKeyBuilderTest
{
	private CreatePOFromSOsAggregationKeyBuilder createPOFromSOsAggregationKeyBuilder;
	private I_M_Product productRecord;
	private I_C_BPartner customerBPartner;
	private I_C_Order salesOrderRecord;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		productRecord = newInstance(I_M_Product.class);
		productRecord.setAD_Org_ID(100);
		saveRecord(productRecord);

		customerBPartner = newInstance(I_C_BPartner.class);
		saveRecord(customerBPartner);

		salesOrderRecord = newInstance(I_C_Order.class);
		saveRecord(salesOrderRecord);
	}

	@Test
	public void givenOrderLineVendorPresent_whenBuildKey_thenReturnOrderLineVendorValue()
	{
		//given
		final I_C_BPartner vendorRecord = newInstance(I_C_BPartner.class);
		vendorRecord.setValue("VendorValue");
		saveRecord(vendorRecord);

		final boolean isVendorInOrderLinesRequired = false;
		createPOFromSOsAggregationKeyBuilder = new CreatePOFromSOsAggregationKeyBuilder(0, null, isVendorInOrderLinesRequired);

		final I_C_OrderLine salesOrderLineRecord = newInstance(I_C_OrderLine.class);
		salesOrderLineRecord.setC_BPartner_ID(customerBPartner.getC_BPartner_ID());
		salesOrderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		salesOrderLineRecord.setC_BPartner_Vendor_ID(vendorRecord.getC_BPartner_ID());
		salesOrderLineRecord.setC_Order(salesOrderRecord);
		saveRecord(salesOrderLineRecord);

		//when
		final String vendorValue = createPOFromSOsAggregationKeyBuilder.buildKey(salesOrderLineRecord);

		//then
		assertThat(vendorValue).isEqualTo(vendorRecord.getValue());
	}

	@Test
	public void givenOrderLineVendorMissing_andIsVendorRequired_whenBuildKey_thenThrowExeception()
	{
		//given
		final boolean isVendorInOrderLinesRequired = true;
		createPOFromSOsAggregationKeyBuilder = new CreatePOFromSOsAggregationKeyBuilder(0, null, isVendorInOrderLinesRequired);

		final I_C_OrderLine salesOrderLineRecord = newInstance(I_C_OrderLine.class);
		salesOrderLineRecord.setC_BPartner_ID(customerBPartner.getC_BPartner_ID());
		salesOrderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		salesOrderLineRecord.setC_Order(salesOrderRecord);
		saveRecord(salesOrderLineRecord);

		//when
		Assertions.assertThrows(AdempiereException.class, () -> createPOFromSOsAggregationKeyBuilder.buildKey(salesOrderLineRecord));
	}

	@Test
	public void givenOrderLineVendorMissing_whenBuildKey_thenReturnProductAssignedVendorValue()
	{
		//given
		final I_C_BPartner vendorRecord = newInstance(I_C_BPartner.class);
		vendorRecord.setValue("VendorValue");
		saveRecord(vendorRecord);

		final boolean isVendorInOrderLinesRequired = false;
		createPOFromSOsAggregationKeyBuilder = new CreatePOFromSOsAggregationKeyBuilder(0, null, isVendorInOrderLinesRequired);

		final I_C_OrderLine salesOrderLineRecord = newInstance(I_C_OrderLine.class);
		salesOrderLineRecord.setC_BPartner_ID(customerBPartner.getC_BPartner_ID());
		salesOrderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		salesOrderLineRecord.setC_Order(salesOrderRecord);
		saveRecord(salesOrderLineRecord);

		final I_C_BPartner_Product bpProductRecord = newInstance(I_C_BPartner_Product.class);
		bpProductRecord.setC_BPartner_ID(customerBPartner.getC_BPartner_ID());
		bpProductRecord.setM_Product_ID(productRecord.getM_Product_ID());
		bpProductRecord.setAD_Org_ID(productRecord.getAD_Org_ID());
		bpProductRecord.setUsedForCustomer(true);
		bpProductRecord.setC_BPartner_Vendor_ID(vendorRecord.getC_BPartner_ID());
		saveRecord(bpProductRecord);

		//when
		final String vendorValue = createPOFromSOsAggregationKeyBuilder.buildKey(salesOrderLineRecord);

		//then
		assertThat(vendorValue).isEqualTo(vendorRecord.getValue());
	}

	@Test
	public void givenOrderLineVendorMissing_andMissingBPProduct_whenBuildKey_thenThrowException()
	{
		//given
		final I_C_BPartner vendorRecord = newInstance(I_C_BPartner.class);
		vendorRecord.setValue("VendorValue");
		saveRecord(vendorRecord);

		final boolean isVendorInOrderLinesRequired = false;
		createPOFromSOsAggregationKeyBuilder = new CreatePOFromSOsAggregationKeyBuilder(0, PlainContextAware.newOutOfTrx(Env.getCtx()), isVendorInOrderLinesRequired);

		final I_C_OrderLine salesOrderLineRecord = newInstance(I_C_OrderLine.class);
		salesOrderLineRecord.setC_BPartner_ID(customerBPartner.getC_BPartner_ID());
		salesOrderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		salesOrderLineRecord.setC_Order(salesOrderRecord);
		saveRecord(salesOrderLineRecord);

		final I_AD_SysConfig sysConfigRecord = newInstance(I_AD_SysConfig.class);
		sysConfigRecord.setName(SYSCONFIG_ON_MISSING_C_B_PARTNER_PRODUCT);
		sysConfigRecord.setValue(ON_MISSING_C_B_PARTNER_PRODUCT_ERROR);
		saveRecord(sysConfigRecord);

		//when then
		Assertions.assertThrows(AdempiereException.class, () -> createPOFromSOsAggregationKeyBuilder.buildKey(salesOrderLineRecord));
	}
}
