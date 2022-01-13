package de.metas.invoicecandidate.api.impl;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;

import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.product.ProductType;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.InvoiceRule;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

/**
 * Simple tests for {@link InvoiceCandBL} that don't have a lot of dependencies.
 */
class InvoiceCandBLSimpleTest
{

	private InvoiceCandBL invoiceCandBL;

	private UomId uomId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uomrecord = BusinessTestHelper.createUOM("testUom");
		uomId = UomId.ofRepoId(uomrecord.getC_UOM_ID());
		invoiceCandBL = new InvoiceCandBL();
	}

	@Test
	void setQtyAndDateForFreightCost_noOtherICToWaitFor()
	{
		final I_C_Invoice_Candidate freightCostICRecord = newInstance(I_C_Invoice_Candidate.class);
		freightCostICRecord.setC_Order_ID(1);
		freightCostICRecord.setIsFreightCost(true);
		freightCostICRecord.setQtyToInvoice(TEN);
		saveRecord(freightCostICRecord);

		// invoke the method under test
		invoiceCandBL.setQtyAndDateForFreightCost(freightCostICRecord);

		assertThat(freightCostICRecord.getQtyToInvoice()).isEqualByComparingTo(TEN);
	}

	@Test
	void setQtyAndDateForFreightCost_otherICToWaitFor()
	{
		final Timestamp dateOrdered = de.metas.common.util.time.SystemTime.asTimestamp();

		final I_C_Invoice_Candidate freightCostICRecord = newInstance(I_C_Invoice_Candidate.class);
		freightCostICRecord.setC_Order_ID(1);
		freightCostICRecord.setIsFreightCost(true);
		freightCostICRecord.setQtyToInvoice(TEN);
		freightCostICRecord.setDateOrdered(dateOrdered);
		freightCostICRecord.setInvoiceRule(InvoiceRule.Immediate.getCode());
		saveRecord(freightCostICRecord);

		final I_C_Invoice_Candidate otherICRecord = newInstance(I_C_Invoice_Candidate.class);
		otherICRecord.setC_Order_ID(1);
		otherICRecord.setIsFreightCost(false);
		otherICRecord.setQtyToInvoice(ZERO);
		saveRecord(otherICRecord);

		// invoke the method under test
		invoiceCandBL.setQtyAndDateForFreightCost(freightCostICRecord);

		assertThat(freightCostICRecord.getQtyToInvoice()).isEqualByComparingTo(ZERO); // set to zero, in order to have freightCostICRecord wait for otherICRecord
	}

	@Test
	void setQtyAndDateForFreightCost_otherIC()
	{
		final Timestamp dateOrdered = SystemTime.asTimestamp();

		final Timestamp freightCostICDeliveryDate = TimeUtil.parseTimestamp("2020-02-15");
		final Timestamp otherICDeliveryDate = TimeUtil.parseTimestamp("2020-02-17");

		final I_C_Invoice_Candidate freightCostICRecord = newInstance(I_C_Invoice_Candidate.class);
		freightCostICRecord.setC_Order_ID(1);
		freightCostICRecord.setIsFreightCost(true);
		freightCostICRecord.setQtyToInvoice(TEN);
		freightCostICRecord.setDateOrdered(dateOrdered);
		freightCostICRecord.setInvoiceRule(InvoiceRule.Immediate.getCode());
		freightCostICRecord.setDeliveryDate(freightCostICDeliveryDate);
		freightCostICRecord.setC_OrderLine_ID(10);
		saveRecord(freightCostICRecord);

		final I_C_Invoice_Candidate otherICRecord = newInstance(I_C_Invoice_Candidate.class);
		otherICRecord.setC_Order_ID(1);
		otherICRecord.setIsFreightCost(false);
		otherICRecord.setQtyToInvoice(ONE);
		otherICRecord.setDeliveryDate(otherICDeliveryDate);
		saveRecord(otherICRecord);

		// invoke the method under test
		invoiceCandBL.setQtyAndDateForFreightCost(freightCostICRecord);

		assertThat(freightCostICRecord.getQtyToInvoice()).isEqualByComparingTo(TEN); // unchanged
		assertThat(freightCostICRecord.getDeliveryDate()).isEqualTo(otherICDeliveryDate); // was synched to the other IC
	}

	@Test
	void computeDateToInvoice_OrderCompletelyDelivered_two_services()
	{
		final Timestamp deliveryDate = Timestamp.valueOf("2021-06-01 11:39:00");

		// under or over delivery should not matter with service products
		final I_C_Invoice_Candidate overDelivered = createSchedForOrderCompletelyDeliveredTest("8", ProductType.Service, deliveryDate);
		final I_C_Invoice_Candidate correctlyDelivered = createSchedForOrderCompletelyDeliveredTest("8", ProductType.Service, deliveryDate);

		assertThat(new InvoiceCandBL().computeDateToInvoice(correctlyDelivered)).as("underDelivered").isEqualTo(deliveryDate);
		assertThat(new InvoiceCandBL().computeDateToInvoice(overDelivered)).as("overDelivered").isEqualTo(deliveryDate);
	}
	
	@Test
	void computeDateToInvoice_OrderCompletelyDelivered_two_items_one_underdelivered()
	{
		final Timestamp deliveryDate = Timestamp.valueOf("2021-06-01 11:39:00");

		final I_C_Invoice_Candidate overDelivered = createSchedForOrderCompletelyDeliveredTest("12", ProductType.Item, deliveryDate);
		final I_C_Invoice_Candidate underDelivered = createSchedForOrderCompletelyDeliveredTest("8", ProductType.Item, deliveryDate);

		assertThat(new InvoiceCandBL().computeDateToInvoice(underDelivered)).as("underDelivered").isEqualTo(Env.MAX_DATE);
		assertThat(new InvoiceCandBL().computeDateToInvoice(overDelivered)).as("overDelivered").isEqualTo(Env.MAX_DATE);
	}

	@Test
	void computeDateToInvoice_OrderCompletelyDelivered_two_items()
	{
		final Timestamp deliveryDate = Timestamp.valueOf("2021-06-01 11:39:00");

		final I_C_Invoice_Candidate overDelivered = createSchedForOrderCompletelyDeliveredTest("12", ProductType.Item, deliveryDate);
		final I_C_Invoice_Candidate correctlyDelivered = createSchedForOrderCompletelyDeliveredTest("10", ProductType.Item, deliveryDate);

		assertThat(new InvoiceCandBL().computeDateToInvoice(correctlyDelivered)).as("underDelivered").isEqualTo(deliveryDate);
		assertThat(new InvoiceCandBL().computeDateToInvoice(overDelivered)).as("overDelivered").isEqualTo(deliveryDate);
	}

	@Test
	void computeDateToInvoice_OrderCompletelyDelivered_service_item_underdelivered()
	{
		final Timestamp deliveryDate = Timestamp.valueOf("2021-06-01 11:39:00");

		final I_C_Invoice_Candidate overDelivered = createSchedForOrderCompletelyDeliveredTest("12", ProductType.Service, deliveryDate);
		final I_C_Invoice_Candidate underDelivered = createSchedForOrderCompletelyDeliveredTest("8", ProductType.Item, deliveryDate);

		assertThat(new InvoiceCandBL().computeDateToInvoice(underDelivered)).as("underDelivered").isEqualTo(Env.MAX_DATE);
		assertThat(new InvoiceCandBL().computeDateToInvoice(overDelivered)).as("overDelivered").isEqualTo(Env.MAX_DATE);
	}

	@Test
	void computeDateToInvoice_OrderCompletelyDelivered_service_item()
	{
		final Timestamp deliveryDate = Timestamp.valueOf("2021-06-01 11:39:00");

		final I_C_Invoice_Candidate overDelivered = createSchedForOrderCompletelyDeliveredTest("12", ProductType.Service, deliveryDate);
		final I_C_Invoice_Candidate correctlyDelivered = createSchedForOrderCompletelyDeliveredTest("10", ProductType.Item, deliveryDate);

		assertThat(new InvoiceCandBL().computeDateToInvoice(correctlyDelivered)).as("underDelivered").isEqualTo(deliveryDate);
		assertThat(new InvoiceCandBL().computeDateToInvoice(overDelivered)).as("overDelivered").isEqualTo(deliveryDate);
	}
	
	private I_C_Invoice_Candidate createSchedForOrderCompletelyDeliveredTest(
			@NonNull final String qtyDelivered,
			@NonNull final ProductType productType,
			@NonNull final Timestamp deliveryDate)
	{
		final I_M_Product product = BusinessTestHelper.createProduct("testproduct_" + qtyDelivered, uomId);
		product.setProductType(productType.getCode());
		InterfaceWrapperHelper.saveRecord(product);

		final I_C_Invoice_Candidate overDelivered = newInstance(I_C_Invoice_Candidate.class);
		overDelivered.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_OrderCompletelyDelivered);
		overDelivered.setQtyDelivered(new BigDecimal(qtyDelivered));
		overDelivered.setQtyOrdered(BigDecimal.TEN);

		overDelivered.setDeliveryDate(deliveryDate);
		overDelivered.setHeaderAggregationKey("headerAggregationKey");
		overDelivered.setM_Product_ID(product.getM_Product_ID());
		InterfaceWrapperHelper.saveRecord(overDelivered);
		return overDelivered;
	}

}
