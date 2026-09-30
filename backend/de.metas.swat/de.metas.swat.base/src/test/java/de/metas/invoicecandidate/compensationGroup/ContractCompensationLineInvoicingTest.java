package de.metas.invoicecandidate.compensationGroup;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

class ContractCompensationLineInvoicingTest
{
	private static final BigDecimal TWO = new BigDecimal("2");

	private ContractCompensationLineInvoicing contractCompensationLineInvoicing;
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
		saveRecord(currency);

		product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);

		order = newInstance(I_C_Order.class);
		order.setDocumentNo("order"); // no generated document number, no log output about it
		saveRecord(order);

		contractCompensationLineInvoicing = new ContractCompensationLineInvoicing(
				new InvoiceCandidateGroupRepository(Mockito.mock(GroupCompensationLineCreateRequestFactory.class)));
	}

	private int createGroupHeader(final boolean contractCreated)
	{
		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		groupHeader.setC_Flatrate_Term_ID(contractCreated ? 1 : -1);
		saveRecord(groupHeader);
		return groupHeader.getC_Order_CompensationGroup_ID();
	}

	private void createGoodsCandidate(final int orderCompensationGroupId, final boolean processed, final BigDecimal qtyToInvoice)
	{
		final I_C_Invoice_Candidate goods = newInstance(I_C_Invoice_Candidate.class);
		goods.setC_Order_ID(order.getC_Order_ID());
		goods.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		goods.setM_Product_ID(product.getM_Product_ID());
		goods.setProcessed(processed);
		goods.setQtyToInvoice(qtyToInvoice);
		saveRecord(goods);
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
		@Test
		void notYetInvoiced_keepsOrderLineQty()
		{
			final int groupId = createGroupHeader(true);
			createGoodsCandidate(groupId, false, ONE);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, new BigDecimal("-30"), ONE, ZERO);

			contractCompensationLineInvoicing.updateQtyOrdered(discount);

			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(ONE);
		}

		@Test
		void invoicedOnceWithGoodsLeft_oneMoreUnit()
		{
			final int groupId = createGroupHeader(true);
			createGoodsCandidate(groupId, true, ZERO);
			createGoodsCandidate(groupId, false, ZERO);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, ONE);

			contractCompensationLineInvoicing.updateQtyOrdered(discount);

			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(TWO);
			assertThat(discount.getQtyEntered()).isEqualByComparingTo(TWO);
		}

		@Test
		void allGoodsProcessed_noFurtherUnit()
		{
			final int groupId = createGroupHeader(true);
			createGoodsCandidate(groupId, true, ZERO);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, TWO);

			contractCompensationLineInvoicing.updateQtyOrdered(discount);

			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(TWO);
		}

		@Test
		void groupNotCreatedByContract_untouched()
		{
			final int groupId = createGroupHeader(false);
			createGoodsCandidate(groupId, false, ZERO);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, ONE);

			contractCompensationLineInvoicing.updateQtyOrdered(discount);

			assertThat(discount.getQtyOrdered()).isEqualByComparingTo(ONE);
		}
	}

	@Nested
	class updateQtyToInvoice
	{
		@Test
		void priceZero_nothingToInvoice()
		{
			final int groupId = createGroupHeader(true);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, ZERO);

			contractCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo(ZERO);
			assertThat(discount.getQtyToInvoiceInUOM()).isEqualByComparingTo(ZERO);
		}

		@Test
		void notYetInvoiced_invoiceRuleDecides()
		{
			final int groupId = createGroupHeader(true);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, new BigDecimal("-30"), ONE, ZERO);

			contractCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo("7");
		}

		@Test
		void reopenedUnit_isInvoiceable()
		{
			final int groupId = createGroupHeader(true);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, new BigDecimal("-21"), TWO, ONE);

			contractCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo(ONE);
			assertThat(discount.getQtyToInvoiceInUOM()).isEqualByComparingTo(ONE);
		}

		@Test
		void reopenedUnit_cappedByQtyToInvoiceOverride()
		{
			final int groupId = createGroupHeader(true);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, new BigDecimal("-21"), TWO, ONE);
			discount.setQtyToInvoice_Override(ZERO);

			contractCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo(ZERO);
		}

		@Test
		void processed_untouched()
		{
			final int groupId = createGroupHeader(true);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, TWO, TWO);
			discount.setProcessed(true);

			contractCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo("7");
		}

		@Test
		void groupNotCreatedByContract_untouched()
		{
			final int groupId = createGroupHeader(false);
			final I_C_Invoice_Candidate discount = createDiscountCandidate(groupId, ZERO, ONE, ONE);

			contractCompensationLineInvoicing.updateQtyToInvoice(discount);

			assertThat(discount.getQtyToInvoice()).isEqualByComparingTo("7");
		}
	}
}
