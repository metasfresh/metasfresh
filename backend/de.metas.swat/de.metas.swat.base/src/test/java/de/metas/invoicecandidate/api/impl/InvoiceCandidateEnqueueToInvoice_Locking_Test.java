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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;

import com.google.common.collect.ImmutableList;

import de.metas.async.api.NOPWorkpackageLogsRepository;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.impl.BPartnerStatisticsUpdater;
import de.metas.currency.CurrencyRepository;
import de.metas.email.MailService;
import de.metas.email.mailboxes.MailboxRepository;
import de.metas.email.templates.MailTemplateRepository;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.util.Services;

/**
 * Makes sure the invoice candidates get the right locks along the "enqueue to invoice" process.
 */
public class InvoiceCandidateEnqueueToInvoice_Locking_Test extends InvoiceCandidateEnqueueToInvoiceTestBase
{
	@BeforeEach
	public void registerService()
	{
		SpringContextHolder.registerJUnitBean(new CurrencyRepository());
		
		final BPartnerStatisticsUpdater asyncBPartnerStatisticsUpdater = new BPartnerStatisticsUpdater();
		Services.registerService(IBPartnerStatisticsUpdater.class, asyncBPartnerStatisticsUpdater);
		SpringContextHolder.registerJUnitBean(new MailService(new MailboxRepository(), new MailTemplateRepository()));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
		NOPWorkpackageLogsRepository.registerToSpringContext();
	}

	@Override
	protected List<I_C_Invoice_Candidate> step10_createInvoiceCandidates()
	{
		final I_C_Invoice_Candidate ic1 = icTestSupport.createInvoiceCandidate()
				.setBillBPartner(bpartner1)
				.setPriceEntered(1)
				.setQtyOrdered(10)
				.setManual(true)
				.setSOTrx(true)
				.build();

		icTestSupport.updateInvalidCandidates();

		return ImmutableList.<I_C_Invoice_Candidate> of(ic1);
	}
}
