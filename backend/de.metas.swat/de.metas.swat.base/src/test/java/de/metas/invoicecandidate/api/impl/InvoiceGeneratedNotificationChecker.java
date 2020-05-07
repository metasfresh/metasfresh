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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.invoice.event.InvoiceGeneratedEventBus;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.junit.Assert;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;

/**
 * Listens to InvoiceGenerated topic, collects the invoices which were notified and later can compare with a given list.
 * 
 * @author tsa
 *
 */
public class InvoiceGeneratedNotificationChecker implements IEventListener
{
	public static final InvoiceGeneratedNotificationChecker createAnSubscribe()
	{
		final InvoiceGeneratedNotificationChecker notificationsChecker = new InvoiceGeneratedNotificationChecker();
		InvoiceGeneratedEventBus.newInstance().subscribe(notificationsChecker);

		return notificationsChecker;
	}

	private final Set<Integer> notifiedInvoiceIds = new HashSet<>();

	private InvoiceGeneratedNotificationChecker()
	{
		super();
	}

	@Override
	public void onEvent(final IEventBus eventBus, final Event event)
	{
		final ITableRecordReference invoiceRecord = event.getRecord();
		final int invoiceId = invoiceRecord.getRecord_ID();

		notifiedInvoiceIds.add(invoiceId);
	}

	/**
	 * Asserts that all the invoices from given list were notified on the bus.
	 *
	 * @param invoices
	 */
	public void assertAllNotified(final Iterable<? extends I_C_Invoice> invoices)
	{
		final List<I_C_Invoice> invoicesNotNotified = new ArrayList<>();

		for (final I_C_Invoice invoice : invoices)
		{
			final int invoiceId = invoice.getC_Invoice_ID();
			if (!notifiedInvoiceIds.contains(invoiceId))
			{
				invoicesNotNotified.add(invoice);
			}
		}

		if (invoicesNotNotified.isEmpty())
		{
			return;
		}

		Assert.fail("Following invoices were expected to be notified by they were not: " + invoicesNotNotified);
	}
}
