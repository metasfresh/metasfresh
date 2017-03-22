package org.adempiere.invoice.event;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;

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


import org.slf4j.Logger;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.QueueableForwardingEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;

/**
 * {@link IEventBus} wrapper implementation tailored for sending events about generated invoices.
 *
 * @author tsa
 *
 */
public class InvoiceGeneratedEventBus extends QueueableForwardingEventBus
{
	public static final InvoiceGeneratedEventBus newInstance()
	{
		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(EVENTBUS_TOPIC);
		return new InvoiceGeneratedEventBus(eventBus);
	}

	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.setName("de.metas.invoicecandidate.InvoiceCandWorkpackageProcessor")
			.setType(Type.REMOTE)
			.build();

	private static final transient Logger logger = LogManager.getLogger(InvoiceGeneratedEventBus.class);

	private static final String MSG_Event_InvoiceGenerated = "Event_InvoiceGenerated";

	private InvoiceGeneratedEventBus(IEventBus delegate)
	{
		super(delegate);
	}

	@Override
	public InvoiceGeneratedEventBus queueEvents()
	{
		super.queueEvents();
		return this;
	}

	@Override
	public InvoiceGeneratedEventBus queueEventsUntilTrxCommit(final String trxName)
	{
		super.queueEventsUntilTrxCommit(trxName);
		return this;
	}

	/**
	 * Post events about given invoice that was generated.
	 *
	 * @param inouts
	 */
	public InvoiceGeneratedEventBus notify(final I_C_Invoice invoice, final int recipientUserId)
	{
		if (invoice == null)
		{
			return this;
		}

		try
		{
			final Event event = createInvoiceGeneratedEvent(invoice, recipientUserId);
			postEvent(event);
		}
		catch (Exception e)
		{
			logger.warn("Failed creating event for invoice " + invoice + ". Ignored.", e);
		}

		return this;
	}

	private final Event createInvoiceGeneratedEvent(final I_C_Invoice invoice, final int recipientUserId)
	{
		Check.assumeNotNull(invoice, "invoice not null");

		final I_C_BPartner bpartner = invoice.getC_BPartner();
		final String bpValue = bpartner.getValue();
		final String bpName = bpartner.getName();

		final Event event = Event.builder()
				.setDetailADMessage(MSG_Event_InvoiceGenerated, TableRecordReference.of(invoice), bpValue, bpName)
				.addRecipient_User_ID(recipientUserId <= 0 ? invoice.getCreatedBy() : recipientUserId)
				.setRecord(TableRecordReference.of(invoice))
				.build();
		return event;
	}

}
