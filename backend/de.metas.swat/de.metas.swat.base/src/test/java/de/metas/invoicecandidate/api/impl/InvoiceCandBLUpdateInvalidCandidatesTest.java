package de.metas.invoicecandidate.api.impl;

import static java.math.BigDecimal.ONE;

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

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.currency.CurrencyRepository;
import de.metas.inout.model.I_M_InOut;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
public class InvoiceCandBLUpdateInvalidCandidatesTest extends AbstractICTestSupport
{
	@Test
	public void testQtyToInvoice_AfterDelivery()
	{
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setInstanceName("ic1")
				.setBillBPartnerId(bPartner.getC_BPartner_ID())
				.setPriceEntered(1)
				.setQtyOrdered(3)
				.setSOTrx(false)
				.setOrderDocNo("order1")
				.setOrderLineDescription("orderline1_1")
				.build();

		assertThat("Invalid QtyDelivered on the IC level", ic1.getQtyOrdered(), comparesEqualTo(new BigDecimal("3")));
		assertThat("Invalid QtyDelivered on the IC level", ic1.getQtyDelivered(), comparesEqualTo(BigDecimal.ZERO));
		assertThat("Invalid QtyToInvoice on the IC level", ic1.getQtyToInvoice(), comparesEqualTo(BigDecimal.ZERO));

		final I_M_InOut inOut1;
		final StockQtyAndUOMQty partialQty1 = StockQtyAndUOMQtys.create(ONE, productId, ONE, uomId);
		{
			inOut1 = createInOut(ic1.getBill_BPartner_ID(), ic1.getC_Order_ID(), "1"); // DocumentNo
			createInvoiceCandidateInOutLine(ic1, inOut1, partialQty1, "1"); // inOutLineDescription
			completeInOut(inOut1);
		}

		ic1.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery);
		InterfaceWrapperHelper.save(ic1);

		updateInvalidCandidates();

		InterfaceWrapperHelper.refresh(ic1);
		assertThat("Invalid QtyDelivered on the IC level", ic1.getQtyOrdered(), comparesEqualTo(new BigDecimal("3")));
		assertThat("Invalid QtyDelivered on the IC level", ic1.getQtyDelivered(), comparesEqualTo(partialQty1.getStockQty().toBigDecimal()));
		assertThat("Invalid QtyToInvoice on the IC level", ic1.getQtyToInvoice(), comparesEqualTo(partialQty1.getStockQty().toBigDecimal()));

	}
}
