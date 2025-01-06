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
import de.metas.currency.CurrencyRepository;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.InvoiceCandidateIds;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.expectations.InvoiceCandidateExpectation;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.material.MovementType;
import de.metas.money.MoneyService;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractMaterialReturnTests extends AbstractNewAggregationEngineTests
{

	protected I_C_Invoice_Candidate ic1;

	protected I_M_InOut inOut1;
	protected I_M_InOutLine iol11;

	@Override
	public void init()
	{
		super.init();
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
	}

	@Override
	protected List<I_C_Invoice_Candidate> step_createInvoiceCandidates()
	{
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());

		ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(FIFTY.negate())
				.setSOTrx(config_IsSOTrx())
				.setOrderDocNo("order1")
				.setOrderLineDescription("orderline1_1")
				.setInstanceName("ic1")
				.build();

		ic1.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery);
		ic1.setPOReference(IC_PO_REFERENCE);
		ic1.setDateAcct(TimeUtil.asTimestamp(IC_DATE_ACCT));
		InterfaceWrapperHelper.save(ic1);

		return Collections.singletonList(ic1);
	}

	@Override
	protected List<I_M_InOutLine> step_createInOutLines(final List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final StockQtyAndUOMQty qtysDelivered = StockQtyAndUOMQtys.create(FIFTY.negate(), productId, FIVE_HUNDRET.negate(), uomId);
		{
			final String inOutDocumentNo = "1";
			final MovementType movementType = config_IsSOTrx() ? MovementType.CustomerShipment : MovementType.VendorReceipts;
			inOut1 = createInOut(ic1.getBill_BPartner_ID(), ic1.getC_Order_ID(), inOutDocumentNo, movementType);
			iol11 = createInvoiceCandidateInOutLine(ic1, inOut1, qtysDelivered, inOutDocumentNo + "_1");
			completeInOut(inOut1);
		}
		return Collections.singletonList(iol11);
	}

	@Override
	protected void step_validate_before_aggregation(final @NonNull List<I_C_Invoice_Candidate> invoiceCandidates, final @NonNull List<I_M_InOutLine> inOutLines)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		InvoiceCandidateExpectation.newExpectation()
				.inDispute(false)
				.qtyOrdered(FIFTY.negate())
				.qtyDelivered(FIFTY.negate())
				.qtyToInvoice(FIFTY.negate())
				.qtyWithIssues(BigDecimal.ZERO)
				.qualityDiscountPercent(BigDecimal.ZERO)
				.assertExpected(ic1);

		assertThat(invoiceCandDAO.retrieveICIOLAssociationsExclRE(InvoiceCandidateIds.ofRecord(ic1))).hasSize(1);
	}

	@Override
	protected void step_validate_after_aggregation(final List<I_C_Invoice_Candidate> invoiceCandidates, final List<I_M_InOutLine> inOutLines, final List<IInvoiceHeader> invoices)
	{
		assertThat(invoices).as("We are expecting one invoice: " + invoices).hasSize(1);

		final IInvoiceHeader invoice1 = invoices.remove(0);

		assertThat(invoice1.getPOReference()).isEqualTo(IC_PO_REFERENCE);
		assertThat(invoice1.getDateAcct()).isEqualTo(IC_DATE_ACCT); // task 08437

		assertThat(invoice1.getDocBaseType().isCreditMemo()).isTrue(); // we are expecting a credit memo
		assertThat(invoice1.isSOTrx()).isEqualTo(config_IsSOTrx());
		final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
		assertThat(invoiceLines1).as("We are expecting one invoice line: " + invoiceLines1).hasSize(1);

		final IInvoiceLineRW il1 = getSingleForInOutLine(invoiceLines1, iol11);
		Assertions.assertThat(il1).as("Missing IInvoiceLineRW for iol11=" + iol11).isNotNull();

		assertThat(il1.getPriceActual().toBigDecimal()).isEqualByComparingTo("-1");
		assertThat(il1.getQtysToInvoice().getStockQty().toBigDecimal()).isEqualByComparingTo("-50");
		assertThat(il1.getQtysToInvoice().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("-500");
		assertThat(il1.getNetLineAmt().toBigDecimal()).isEqualTo("500"); /**/
	}

	protected abstract boolean config_IsSOTrx();
}
