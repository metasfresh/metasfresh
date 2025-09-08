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

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.notification.UserNotification;
import de.metas.notification.UserNotificationUtils;
import de.metas.util.Services;
import org.adempiere.invoice.event.InvoiceUserNotificationsProducer;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Listens to InvoiceGenerated topic, collects the invoices which were notified and later can compare with a given list.
 * 
 * @author tsa
 *
 */
public class InvoiceGeneratedNotificationChecker implements IEventListener
{
	public static InvoiceGeneratedNotificationChecker createAnSubscribe()
	{
		final InvoiceGeneratedNotificationChecker notificationsChecker = new InvoiceGeneratedNotificationChecker();
		Services.get(IEventBusFactory.class)
				.getEventBus(InvoiceUserNotificationsProducer.EVENTBUS_TOPIC)
				.subscribe(notificationsChecker);

		return notificationsChecker;
	}

	private final Set<Integer> notifiedInvoiceIds = new HashSet<>();

	private InvoiceGeneratedNotificationChecker()
	{
	}

	@Override
	public void onEvent(final IEventBus eventBus, final Event event)
	{
		final UserNotification notification = UserNotificationUtils.toUserNotification(event);
		final TableRecordReference invoiceRecord = notification.getTargetRecord();
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

		Assertions.fail("Following invoices were expected to be notified by they were not: " + invoicesNotNotified);
	}
}
