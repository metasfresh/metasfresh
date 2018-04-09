package de.metas.invoicecandidate.api.impl;

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


import java.util.List;

import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater;
import org.adempiere.bpartner.service.impl.AsyncBPartnerStatisticsUpdater;
import org.adempiere.util.Services;
import org.junit.Before;

import com.google.common.collect.ImmutableList;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Makes sure the invoice candidates get the right locks along the "enqueue to invoice" process.
 *
 * @author tsa
 *
 */
public class InvoiceCandidateEnqueueToInvoice_Locking_Test extends InvoiceCandidateEnqueueToInvoiceTestBase
{
	@Before
	public void registerService()
	{
		final AsyncBPartnerStatisticsUpdater asyncBPartnerStatisticsUpdater = new AsyncBPartnerStatisticsUpdater();
		Services.registerService(IBPartnerStatisticsUpdater.class, asyncBPartnerStatisticsUpdater);
	}

	@Override
	protected List<I_C_Invoice_Candidate> step10_createInvoiceCandidates()
	{
		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setBillBPartner(bpartner1)
				.setPriceEntered(1)
				.setQty(10)
				.setManual(true)
				.setSOTrx(true)
				.build();

		updateInvalidCandidates();

		return ImmutableList.<I_C_Invoice_Candidate> of(ic1);
	}
}
