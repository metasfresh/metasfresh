package de.metas.invoicecandidate.api.impl.aggregationEngine;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.InvoiceCandidateIds;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.expectations.InvoiceCandidateExpectation;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.material.MovementType;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * In this scenario we have two invoice candidates (also referencing two orders). The first ic has two iols, the 2nd ic has one iol.
 *
 * @author ts
 *
 */
public abstract class AbstractTwoOrdersTwoInOutsOneInvoiceTests extends AbstractNewAggregationEngineTests
{
	protected I_C_Invoice_Candidate ic1;

	protected I_M_InOut inOut11;
	protected I_M_InOutLine iol111;

	protected I_M_InOut inOut12;
	protected I_M_InOutLine iol121;

	protected I_C_Invoice_Candidate ic2;
	protected I_M_InOut inOut21;
	protected I_M_InOutLine iol211;
	protected I_M_InOutLine iol212;

	protected abstract boolean config_IsSOTrx();

	@Override
	protected List<I_C_Invoice_Candidate> step_createInvoiceCandidates()
	{
		final int qtyOrdered = 50;

		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());


		ic1 = createInvoiceCandidate()
				.setInstanceName("ic1")
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(qtyOrdered)
				.setSOTrx(config_IsSOTrx())
				.setOrderDocNo("order1")
				.setOrderLineDescription("orderline1_1")
				.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery)
				.setInvoiceRule_Override(null)
				.setPOReference(IC_PO_REFERENCE)
				.setDateAcct(IC_DATE_ACCT) // task 08437
				.build();

		ic2 = createInvoiceCandidate()
				.setInstanceName("ic2")
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(qtyOrdered)
				.setSOTrx(config_IsSOTrx())
				.setOrderDocNo("order2")
				.setOrderLineDescription("orderline2_1")
				.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery)
				.setInvoiceRule_Override(null)
				.setPOReference(IC_PO_REFERENCE)
				.setDateAcct(IC_DATE_ACCT) // task 08437
				.build();

		ic2.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery);
		ic2.setInvoiceRule_Override(null);
		ic2.setPOReference(IC_PO_REFERENCE);
		ic2.setDateAcct(TimeUtil.asTimestamp(IC_DATE_ACCT)); // task 08437
		InterfaceWrapperHelper.save(ic2);

		return Arrays.asList(ic1, ic2);
	}

	@Override
	protected List<I_M_InOutLine> step_createInOutLines(final List<I_C_Invoice_Candidate> ignored)
	{
		// Deliver 10 via Wareneingang pos
		final StockQtyAndUOMQty qtysDelivered_10 = StockQtyAndUOMQtys.create(TEN, productId, HUNDRET, uomId);
		{
			final String inOutDocumentNo = "11";
			inOut11 = createInOut(ic1.getBill_BPartner_ID(), ic1.getC_Order_ID(), inOutDocumentNo, MovementType.CustomerShipment);
			iol111 = createInvoiceCandidateInOutLine(ic1, inOut11, qtysDelivered_10, inOutDocumentNo + "_1");
			completeInOut(inOut11);
		}

		// Deliver 20 via Wareneingang pos
		final StockQtyAndUOMQty qtysDelivered_20 = StockQtyAndUOMQtys.create(TWENTY, productId, TWO_HUNDRET, uomId);
		{
			final String inOutDocumentNo = "12";
			inOut12 = createInOut(ic1.getBill_BPartner_ID(), ic1.getC_Order_ID(), inOutDocumentNo, MovementType.CustomerShipment);
			iol121 = createInvoiceCandidateInOutLine(ic1, inOut12, qtysDelivered_20, inOutDocumentNo + "_1");
			completeInOut(inOut12);
		}
		assertThat(Services.get(IInvoiceCandDAO.class).retrieveICIOLAssociationsExclRE(InvoiceCandidateIds.ofRecord(ic1)).size(), is(2));

		// Deliver everything via WP
		final StockQtyAndUOMQty qtysDelivered_50 = StockQtyAndUOMQtys.create(FIFTY, productId, FIVE_HUNDRET, uomId);
		{

			final String inOutDocumentNo = "21";
			inOut21 = createInOut(ic2.getBill_BPartner_ID(), ic2.getC_Order_ID(), inOutDocumentNo, MovementType.CustomerShipment);
			iol211 = createInvoiceCandidateInOutLine(ic2, inOut21, qtysDelivered_50, inOutDocumentNo + "_1");
			completeInOut(inOut21);
		}


		assertThat(Services.get(IInvoiceCandDAO.class).retrieveICIOLAssociationsExclRE(InvoiceCandidateIds.ofRecord(ic1)).size(), is(2));
		assertThat(Services.get(IInvoiceCandDAO.class).retrieveICIOLAssociationsExclRE(InvoiceCandidateIds.ofRecord(ic2)).size(), is(1));
		return Arrays.asList(iol111, iol121, iol211);

	}

	@Override
	protected void step_validate_before_aggregation(final @NonNull List<I_C_Invoice_Candidate> ignored, final @NonNull List<I_M_InOutLine> ignoredToo)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		InvoiceCandidateExpectation.newExpectation()
				.inDispute(false)
				.qtyOrdered(FIFTY)
				.qtyDelivered(THIRTY)
				.qtyToInvoice(THIRTY)
				.qtyWithIssues(BigDecimal.ZERO)
				.qualityDiscountPercent(BigDecimal.ZERO)
				.assertExpected(ic1);

		assertThat(invoiceCandDAO.retrieveICIOLAssociationsExclRE(InvoiceCandidateIds.ofRecord(ic1)).size(), is(2));

		InvoiceCandidateExpectation.newExpectation()
				.inDispute(false)
				.qtyOrdered(FIFTY)
				.qtyDelivered(FIFTY)
				.qtyToInvoice(FIFTY)
				.qtyWithIssues(BigDecimal.ZERO)
				.qualityDiscountPercent(BigDecimal.ZERO)
				.assertExpected(ic2);
		assertThat(Services.get(IInvoiceCandDAO.class).retrieveICIOLAssociationsExclRE(InvoiceCandidateIds.ofRecord(ic2)).size(), is(1));
	}
}
