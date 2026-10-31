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
import de.metas.interfaces.I_C_OrderLine;
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
	
	// ---- computed "delivery closed" rule of updateProcessedFlag -------------------------------------------------------------------
	// Fixture unless the name says otherwise: invoice rule D (After delivery), stock product, order line with QtyDelivered=0, IsDeliveryClosed=Y.

	@Test
	void updateProcessedFlag_deliveryClosed_nothingDeliveredOrInvoiced_processed()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder());

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed_Calc()).as("Processed_Calc").isTrue();
		assertThat(ic.isProcessed()).as("Processed").isTrue();
		assertThat(ic.getProcessed_Override()).as("Processed_Override").isNull();
		assertThat(ic.getQtyToInvoice()).as("QtyToInvoice").isZero();
	}

	/** Invoice rule O applies to any product, service products included. */
	@Test
	void updateProcessedFlag_invoiceRuleAfterOrderDelivered_serviceProduct_processed()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder()
				.invoiceRule(InvoiceRule.AfterOrderDelivered.getCode())
				.productType(ProductType.Service));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed_Calc()).as("Processed_Calc").isTrue();
		assertThat(ic.isProcessed()).as("Processed").isTrue();
	}

	/** The rule's processed state zeroes the quantities to invoice, but it must not reset an approval for invoicing that was set before shipping. */
	@Test
	void updateProcessedFlag_processedByRuleOnly_keepsApprovalForInvoicing()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder().approvalForInvoicing(true));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed()).as("Processed").isTrue();
		assertThat(ic.getQtyToInvoice()).as("QtyToInvoice").isZero();
		assertThat(ic.isApprovalForInvoicing()).as("ApprovalForInvoicing").isTrue();
	}

	/** A candidate without an order line (e.g. a manual one) is judged by its own quantities only. */
	@Test
	void updateProcessedFlag_noOrderLine_ruleUsesCandidateOnly()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder().withOrderLine(false));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.getC_OrderLine_ID()).as("C_OrderLine_ID").isLessThanOrEqualTo(0);
		assertThat(ic.isProcessed_Calc()).as("Processed_Calc").isTrue();
		assertThat(ic.isProcessed()).as("Processed").isTrue();
	}

	@Test
	void updateProcessedFlag_deliveryClosed_delivered_notProcessed()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder().icQtyDelivered(ONE));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed_Calc()).as("Processed_Calc").isFalse();
		assertThat(ic.isProcessed()).as("Processed").isFalse();
	}

	/** E.g. a shipment was invoiced and then reversed: the invoiced qty must stay open so that the credit memo is proposed. */
	@Test
	void updateProcessedFlag_deliveryClosed_invoicedButNothingDelivered_notProcessed()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder().qtyInvoiced(ONE));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed_Calc()).as("Processed_Calc").isFalse();
		assertThat(ic.isProcessed()).as("Processed").isFalse();
	}

	@Test
	void updateProcessedFlag_deliveryNotClosed_nothingDeliveredOrInvoiced_notProcessed()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder().deliveryClosed(false));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed_Calc()).as("Processed_Calc").isFalse();
		assertThat(ic.isProcessed()).as("Processed").isFalse();
	}

	/** Processed_Override wins over the computed flag. (Processed_Calc is not asserted, so this guard also holds while the rule is not implemented.) */
	@Test
	void updateProcessedFlag_deliveryClosed_nothingDeliveredOrInvoiced_processedOverrideNWins()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder().processedOverride("N"));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed()).as("Processed").isFalse();
		assertThat(ic.getProcessed_Override()).as("Processed_Override").isEqualTo("N");
	}

	/** An explicit close (Processed_Override=Y) keeps resetting the approval for invoicing, also when the rule would apply (delivery closed, nothing delivered). */
	@Test
	void updateProcessedFlag_processedOverrideY_resetsApprovalForInvoicing()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder().approvalForInvoicing(true).processedOverride("Y"));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed()).as("Processed").isTrue();
		assertThat(ic.isApprovalForInvoicing()).as("ApprovalForInvoicing").isFalse();
	}

	/** The completion raises the order line's QtyDelivered before the candidate's own QtyDelivered follows. */
	@Test
	void updateProcessedFlag_orderLineDelivered_icQtyDeliveredStillZero_notProcessed()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder().olQtyDelivered(ONE));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed_Calc()).as("Processed_Calc").isFalse();
		assertThat(ic.isProcessed()).as("Processed").isFalse();
	}

	@Test
	void updateProcessedFlag_invoiceRuleImmediate_notProcessed()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder().invoiceRule(InvoiceRule.Immediate.getCode()));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed()).as("Processed").isFalse();
	}

	@Test
	void updateProcessedFlag_invoiceRuleAfterPick_notProcessed()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder().invoiceRule(InvoiceRule.AfterPick.getCode()));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed()).as("Processed").isFalse();
	}

	/** Invoice rule D, but a service product: its quantity to invoice is delivered-or-ordered, so it is invoiceable without delivery. */
	@Test
	void updateProcessedFlag_serviceProductAfterDelivery_notProcessed()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder().productType(ProductType.Service));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed()).as("Processed").isFalse();
	}

	/** The effective rule is the override (Immediate) although the candidate's own rule is D. */
	@Test
	void updateProcessedFlag_invoiceRuleOverrideImmediate_notProcessed()
	{
		final I_C_Invoice_Candidate ic = createICForRuleTest(builder().invoiceRule(InvoiceRule.AfterDelivery.getCode()).invoiceRuleOverride(InvoiceRule.Immediate.getCode()));

		invoiceCandBL.updateProcessedFlag(ic);

		assertThat(ic.isProcessed()).as("Processed").isFalse();
	}

	private static RuleFixture builder()
	{
		return new RuleFixture();
	}

	/** Parameters of {@link #createICForRuleTest(RuleFixture)}; the defaults are the fixture of the "delivery closed" rule tests. */
	private static final class RuleFixture
	{
		private boolean deliveryClosed = true;
		private String invoiceRule = InvoiceRule.AfterDelivery.getCode();
		private String invoiceRuleOverride = null;
		private ProductType productType = ProductType.Item;
		private boolean withOrderLine = true;
		private BigDecimal icQtyDelivered = ZERO;
		private BigDecimal olQtyDelivered = ZERO;
		private BigDecimal qtyInvoiced = ZERO;
		private boolean approvalForInvoicing = false;
		private String processedOverride = null;

		RuleFixture deliveryClosed(final boolean v) { deliveryClosed = v; return this; }
		RuleFixture invoiceRule(final String v) { invoiceRule = v; return this; }
		RuleFixture invoiceRuleOverride(final String v) { invoiceRuleOverride = v; return this; }
		RuleFixture productType(final ProductType v) { productType = v; return this; }
		RuleFixture withOrderLine(final boolean v) { withOrderLine = v; return this; }
		RuleFixture icQtyDelivered(final BigDecimal v) { icQtyDelivered = v; return this; }
		RuleFixture olQtyDelivered(final BigDecimal v) { olQtyDelivered = v; return this; }
		RuleFixture qtyInvoiced(final BigDecimal v) { qtyInvoiced = v; return this; }
		RuleFixture approvalForInvoicing(final boolean v) { approvalForInvoicing = v; return this; }
		RuleFixture processedOverride(final String v) { processedOverride = v; return this; }
	}

	private I_C_Invoice_Candidate createICForRuleTest(@NonNull final RuleFixture f)
	{
		final I_M_Product product = BusinessTestHelper.createProduct("ruleproduct", uomId);
		product.setProductType(f.productType.getCode());
		InterfaceWrapperHelper.saveRecord(product);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setM_Product_ID(product.getM_Product_ID());
		ic.setInvoiceRule(f.invoiceRule);
		ic.setInvoiceRule_Override(f.invoiceRuleOverride);
		ic.setQtyOrdered(TEN);
		ic.setQtyDelivered(f.icQtyDelivered);
		ic.setQtyInvoiced(f.qtyInvoiced);
		ic.setQtyToInvoice(f.icQtyDelivered);
		ic.setIsDeliveryClosed(f.deliveryClosed);
		ic.setApprovalForInvoicing(f.approvalForInvoicing);
		ic.setProcessed_Override(f.processedOverride);
		if (f.withOrderLine)
		{
			final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
			orderLine.setQtyDelivered(f.olQtyDelivered);
			InterfaceWrapperHelper.saveRecord(orderLine);
			ic.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		}
		InterfaceWrapperHelper.saveRecord(ic);
		return ic;
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
