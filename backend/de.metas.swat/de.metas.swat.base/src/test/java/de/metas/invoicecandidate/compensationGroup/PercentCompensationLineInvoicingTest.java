package de.metas.invoicecandidate.compensationGroup;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Line_Alloc;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

class PercentCompensationLineInvoicingTest
{
	private static final BigDecimal TWO = new BigDecimal("2");

	private PercentCompensationLineInvoicing percentCompensationLineInvoicing;
	private I_C_Order order;
	private I_M_Product product;
	private I_C_UOM uom;
	private I_C_Currency currency;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		currency = newInstance(I_C_Currency.class);
		currency.setStdPrecision(2);
		currency.setISO_Code("EUR");
		currency.setCurSymbol("€");
		currency.setDescription("euro");
		saveRecord(currency);

		product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);

		order = newInstance(I_C_Order.class);
		order.setDocumentNo("order"); // no generated document number, no log output about it
		saveRecord(order);

		percentCompensationLineInvoicing = new PercentCompensationLineInvoicing(
				new InvoiceCandidateGroupRepository(Mockito.mock(GroupCompensationLineCreateRequestFactory.class), new GroupTemplateRepository(Optional.empty()), Optional.empty()));
	}

	private int createGroupHeader()
	{
		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		saveRecord(groupHeader);
		return groupHeader.getC_Order_CompensationGroup_ID();
	}

	private I_C_Invoice_Candidate createGoodsCandidate(final int orderCompensationGroupId, final boolean processed, final BigDecimal qtyToInvoice)
	{
		final I_C_Invoice_Candidate goods = newInstance(I_C_Invoice_Candidate.class);
		goods.setC_Order_ID(order.getC_Order_ID());
		goods.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		goods.setM_Product_ID(product.getM_Product_ID());
		goods.setProcessed(processed);
		goods.setQtyOrdered(ONE);
		goods.setQtyToInvoice(qtyToInvoice);
		saveRecord(goods);
		return goods;
	}

	private I_C_Invoice createInvoice()
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setDocumentNo("invoice"); // no generated document number, no log output about it
		saveRecord(invoice);
		return invoice;
	}

	/** Allocates one unit of the given candidate to a new line of the given invoice. */
	private static void allocate(final I_C_Invoice_Candidate ic, final I_C_Invoice invoice)
	{
		final I_C_InvoiceLine invoiceLine = newInstance(I_C_InvoiceLine.class);
		invoiceLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
		saveRecord(invoiceLine);

		final I_C_Invoice_Line_Alloc ila = newInstance(I_C_Invoice_Line_Alloc.class);
		ila.setC_Invoice_Candidate_ID(ic.getC_Invoice_Candidate_ID());
		ila.setC_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
		ila.setQtyInvoiced(ONE);
		ila.setDocStatus(X_C_Invoice_Line_Alloc.DOCSTATUS_Completed);
		saveRecord(ila);
	}

	/** A discount that is invoiced 1 of 1 and processed, with the given (calculated) amount still open. */
	private I_C_Invoice_Candidate createSettledDiscountCandidate(final int orderCompensationGroupId, final String openAmount)
	{
		final I_C_Invoice_Candidate discount = createDiscountCandidate(orderCompensationGroupId, new BigDecimal(openAmount), ONE, ONE);
		discount.setNetAmtInvoiced(new BigDecimal("-30"));
		discount.setProcessed(true);
		saveRecord(discount);
		return discount;
	}

	private I_C_Invoice_Candidate createDiscountCandidate(
			final int orderCompensationGroupId,
			final BigDecimal priceActual,
			final BigDecimal qtyOrdered,
			final BigDecimal qtyInvoiced)
	{
		final I_C_Invoice_Candidate discount = newInstance(I_C_Invoice_Candidate.class);
		discount.setC_Order_ID(order.getC_Order_ID());
		discount.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		discount.setIsGroupCompensationLine(true);
		discount.setGroupCompensationAmtType(X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_Percent);
		discount.setM_Product_ID(product.getM_Product_ID());
		discount.setC_UOM_ID(uom.getC_UOM_ID());
		discount.setC_Currency_ID(currency.getC_Currency_ID());
		discount.setPriceActual(priceActual);
		discount.setQtyOrdered(qtyOrdered);
		discount.setQtyInvoiced(qtyInvoiced);
		discount.setQtyToInvoice(new BigDecimal("7")); // what the invoice rule computed; a marker to see whether it was replaced
		saveRecord(discount);
		return discount;
	}

	@Nested
	class updateQtyOrdered
	{
		/**
		 * An overridden discount does not follow partially invoiced goods, so it keeps no unit open for them.
		 */
		@Test
		void priceOverride_noOpenUnit()
		{
			final int groupId = createGroupHeader();
			createGoodsCandidate(groupId, false, ZERO);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, new BigDecimal("-30"), ONE, ONE);
			discount.setPriceEntered_Override(new BigDecimal("-40"));

			percentCompensationLineInvoicing.updateQtyOrdered(discount);

			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(ONE);
		}

		@Test
		void notYetInvoiced_keepsOrderLineQty()
		{
			final int groupId = createGroupHeader();
			createGoodsCandidate(groupId, false, ONE);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, new BigDecimal("-30"), ONE, ZERO);

			percentCompensationLineInvoicing.updateQtyOrdered(discount);

			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(ONE);
		}

		@Test
		void invoicedOnceWithGoodsLeft_oneMoreUnit()
		{
			final int groupId = createGroupHeader();
			createGoodsCandidate(groupId, true, ZERO);
			createGoodsCandidate(groupId, false, ZERO);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, ONE);

			percentCompensationLineInvoicing.updateQtyOrdered(discount);

			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(TWO);
			assertThat(discount.getQtyEntered()).isEqualByComparingTo(TWO);
		}

		@Test
		void allGoodsProcessed_noFurtherUnit()
		{
			final int groupId = createGroupHeader();
			createGoodsCandidate(groupId, true, ZERO);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, TWO);

			percentCompensationLineInvoicing.updateQtyOrdered(discount);

			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(TWO);
		}

		@Test
		void priceAndQuantityCompensationLine_untouched()
		{
			final int groupId = createGroupHeader();
			createGoodsCandidate(groupId, false, ZERO);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, ONE);
			discount.setGroupCompensationAmtType(X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_PriceAndQty);

			percentCompensationLineInvoicing.updateQtyOrdered(discount);

			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(ONE);
		}
	}

	@Nested
	class updateQtyToInvoice
	{
		@Test
		void priceZero_goodsStillToCome_nothingToInvoice()
		{
			final int groupId = createGroupHeader();
			createGoodsCandidate(groupId, false, ZERO);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, ZERO);

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo(ZERO);
			assertThat(discount.getQtyToInvoiceInUOM()).isEqualByComparingTo(ZERO);
		}

		/**
		 * E.g. a 0 % discount: it goes with the last goods, so that it ends processed and the order completely invoiced.
		 */
		@Test
		void priceZero_lastGoodsToInvoice_invoiceRuleDecides()
		{
			final int groupId = createGroupHeader();
			createGoodsCandidate(groupId, true, ZERO);
			createGoodsCandidate(groupId, false, ONE);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, ZERO);

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo("7");
		}

		@Test
		void priceZero_alreadyInvoiced_nothingToInvoice()
		{
			final int groupId = createGroupHeader();
			createGoodsCandidate(groupId, false, ONE);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, TWO, ONE);

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo(ZERO);
		}

		@Test
		void notYetInvoiced_invoiceRuleDecides()
		{
			final int groupId = createGroupHeader();
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, new BigDecimal("-30"), ONE, ZERO);

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo("7");
		}

		@Test
		void reopenedUnit_isInvoiceable()
		{
			final int groupId = createGroupHeader();
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, new BigDecimal("-21"), TWO, ONE);

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo(ONE);
			assertThat(discount.getQtyToInvoiceInUOM()).isEqualByComparingTo(ONE);
		}

		@Test
		void reopenedUnit_cappedByQtyToInvoiceOverride()
		{
			final int groupId = createGroupHeader();
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, new BigDecimal("-21"), TWO, ONE);
			discount.setQtyToInvoice_Override(ZERO);

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo(ZERO);
		}

		@Test
		void closedByUser_untouched()
		{
			final int groupId = createGroupHeader();
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, new BigDecimal("-15"), ONE, ONE);
			discount.setProcessed_Override("Y");

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(ONE);
			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo("7");
		}

		/**
		 * E.g. the discount was held back from the last goods' invoice: all goods are processed, but a discount amount is still open.
		 */
		@Test
		void openAmount_goodsInvoicedWithoutDiscount_reopensAndIsInvoiceable()
		{
			final int groupId = createGroupHeader();
			final I_C_Invoice_Candidate goods = createGoodsCandidate(groupId, true, ZERO);
			final I_C_Invoice_Candidate discount = createSettledDiscountCandidate(groupId, "-15");
			allocate(discount, createInvoice());
			allocate(goods, createInvoice());

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.isProcessed()).isFalse();
			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(TWO);
			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo(ONE);
		}

		@Test
		void settled_noOpenAmount_staysProcessed()
		{
			final int groupId = createGroupHeader();
			final I_C_Invoice_Candidate goods = createGoodsCandidate(groupId, true, ZERO);
			final I_C_Invoice_Candidate discount = createSettledDiscountCandidate(groupId, "0");
			final I_C_Invoice invoice = createInvoice();
			allocate(goods, invoice);
			allocate(discount, invoice);

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.isProcessed()).isTrue();
			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(ONE);
			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo(ZERO);
		}

		/**
		 * E.g. an order invoiced before the discount followed partially invoiced goods, with a rounding difference against the cumulative basis.
		 */
		@Test
		void settled_roundingDifference_staysProcessed()
		{
			final int groupId = createGroupHeader();
			final I_C_Invoice_Candidate goods = createGoodsCandidate(groupId, true, ZERO);
			final I_C_Invoice_Candidate discount = createSettledDiscountCandidate(groupId, "-0.01");
			allocate(discount, createInvoice());
			allocate(goods, createInvoice());

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.isProcessed()).isTrue();
			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(ONE);
		}

		/**
		 * E.g. the percentage was changed after goods and discount were invoiced together.
		 */
		@Test
		void settled_manualDifference_goodsInvoicedWithDiscount_staysProcessed()
		{
			final int groupId = createGroupHeader();
			final I_C_Invoice_Candidate goods = createGoodsCandidate(groupId, true, ZERO);
			final I_C_Invoice_Candidate discount = createSettledDiscountCandidate(groupId, "-5");
			final I_C_Invoice invoice = createInvoice();
			allocate(goods, invoice);
			allocate(discount, invoice);

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.isProcessed()).isTrue();
			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(ONE);
		}

		/**
		 * A price override is invoiced once: after that, a calculated amount still open (here -5) and goods invoiced without the discount
		 * do not reopen it.
		 */
		@Test
		void priceOverride_invoiced_allGoodsProcessed_staysProcessed()
		{
			final int groupId = createGroupHeader();
			final I_C_Invoice_Candidate goods = createGoodsCandidate(groupId, true, ZERO);
			final I_C_Invoice_Candidate discount = createSettledDiscountCandidate(groupId, "-5");
			discount.setPriceEntered_Override(new BigDecimal("-40"));
			discount.setPriceActual_Override(new BigDecimal("-40"));
			discount.setNetAmtInvoiced(new BigDecimal("-40"));
			allocate(discount, createInvoice());
			allocate(goods, createInvoice());

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.isProcessed()).isTrue();
			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(ONE);
			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo(ZERO);
		}

		@Test
		void priceOverride_notYetInvoiced_invoiceRuleDecides()
		{
			final int groupId = createGroupHeader();
			createGoodsCandidate(groupId, false, ONE);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, ZERO);
			discount.setPriceEntered_Override(new BigDecimal("-40"));
			discount.setPriceActual_Override(new BigDecimal("-40"));

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo("7");
		}

		@Test
		void priceZero_zeroPercent_goesWithFirstGoodsToInvoice()
		{
			final int groupId = createGroupHeader();
			createGoodsCandidate(groupId, false, ONE);
			createGoodsCandidate(groupId, false, ZERO);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, ZERO);

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo("7");
		}

		/**
		 * E.g. only goods outside the discount's product category are to invoice now.
		 */
		@Test
		void priceZero_nonZeroPercent_goodsStillToCome_nothingToInvoice()
		{
			final int groupId = createGroupHeader();
			createGoodsCandidate(groupId, false, ONE);
			createGoodsCandidate(groupId, false, ZERO);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, ZERO);
			discount.setGroupCompensationPercentage(new BigDecimal("3"));

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo(ZERO);
		}

		@Test
		void priceAndQuantityCompensationLine_untouched()
		{
			final int groupId = createGroupHeader();
			createGoodsCandidate(groupId, false, ONE);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, new BigDecimal("-30"), ONE, ONE);
			discount.setGroupCompensationAmtType(X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_PriceAndQty);

			percentCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo("7");
		}
	}
}
