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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * The general scenario if these tests is a duplicated receipt, i.e. 50 were ordered and then two inouts of 50 each were received. In this scenario, the user wants to set
 * <code>QtyToInvoice_Override</code> to make sure that only the desired Qty of 50 is actually invoiced.
 */
public abstract class AbstractDoubleReceiptQtyOverride extends AbstractNewAggregationEngineTests
{
	protected I_M_InOut inOut11;
	protected I_M_InOutLine iol111;

	protected I_M_InOut inOut12;
	protected I_M_InOutLine iol121;

	I_C_Invoice_Candidate ic1;

	@Override
	protected List<I_C_Invoice_Candidate> step_createInvoiceCandidates()
	{
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());

		ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(50)
				.setManual(false)
				.setSOTrx(config_IsSOTrx())
				.setOrderDocNo("order1")
				.setOrderLineDescription("orderline1_1")
				.build();
		POJOWrapper.setInstanceName(ic1, "ic1");
		ic1.setQtyToInvoice_Override(config_GetQtyToInvoice_Override());

		ic1.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Immediate); // need this for tests where we have _Override > Delivered
		ic1.setInvoiceRule_Override(null);
		ic1.setPOReference(IC_PO_REFERENCE);
		ic1.setDateAcct(TimeUtil.asTimestamp(IC_DATE_ACCT));
		InterfaceWrapperHelper.save(ic1);

		return Collections.singletonList(ic1);
	}

	final boolean config_IsSOTrx() {return false;}

	private MovementType config_MovementType() {return config_IsSOTrx() ? MovementType.CustomerShipment : MovementType.VendorReceipts;}

	abstract BigDecimal config_GetQtyToInvoice_Override();

	@Override
	protected List<I_M_InOutLine> step_createInOutLines(List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final StockQtyAndUOMQty qtysDelivered = StockQtyAndUOMQtys.create(FIFTY, productId, FIVE_HUNDRET, uomId);

		// Deliver 50 via Wareneingang pos
		{
			final String inOutDocumentNo = "11";
			inOut11 = createInOut(ic1.getBill_BPartner_ID(), ic1.getC_Order_ID(), inOutDocumentNo, config_MovementType());
			iol111 = createInvoiceCandidateInOutLine(ic1, inOut11, qtysDelivered, inOutDocumentNo + "_1");
			completeInOut(inOut11);
		}

		// ..and now, accidentally deliver another 50
		{
			final String inOutDocumentNo = "12";
			inOut12 = createInOut(ic1.getBill_BPartner_ID(), ic1.getC_Order_ID(), inOutDocumentNo, config_MovementType());
			iol121 = createInvoiceCandidateInOutLine(ic1, inOut12, qtysDelivered, inOutDocumentNo + "_1");
			completeInOut(inOut12);
		}
		assertThat(Services.get(IInvoiceCandDAO.class).retrieveICIOLAssociationsExclRE(InvoiceCandidateIds.ofRecord(ic1)).size(), is(2));

		return Arrays.asList(iol111, iol121);
	}

	@Override
	protected void step_validate_before_aggregation(@NonNull List<I_C_Invoice_Candidate> invoiceCandidates, @NonNull List<I_M_InOutLine> inOutLines)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		InvoiceCandidateExpectation.newExpectation()
				.inDispute(false)
				.qtyOrdered(FIFTY)
				.qtyDelivered(FIFTY.add(FIFTY))
				.qtyToInvoice(config_GetQtyToInvoice_Override()) // ..because we set QtyToInvoice_Override to this value
				.qtyWithIssues(BigDecimal.ZERO)
				.qualityDiscountPercent(BigDecimal.ZERO)
				.assertExpected(ic1);

		assertThat(invoiceCandDAO.retrieveICIOLAssociationsExclRE(InvoiceCandidateIds.ofRecord(ic1)).size(), is(2));
	}
}
