package de.metas.invoicecandidate.async.spi.impl;

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


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.invoice.event.InvoiceGeneratedEventBus;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;
import de.metas.invoicecandidate.api.impl.ForwardingInvoiceGenerateResult;

/**
 * {@link ForwardingInvoiceGenerateResult} implementation which is also creating and sending evens to {@link InvoiceGeneratedEventBus}.
 * 
 * The events will be queued until current transaction is committed.
 * 
 * @author tsa
 *
 */
public class EventRecorderInvoiceGenerateResult extends ForwardingInvoiceGenerateResult
{
	private final InvoiceGeneratedEventBus invoiceGeneratedEventBus = InvoiceGeneratedEventBus.newInstance();

	private int recipientUserId = -1;

	public EventRecorderInvoiceGenerateResult(final IInvoiceGenerateResult delegate)
	{
		super(delegate);

		invoiceGeneratedEventBus.queueEventsUntilTrxCommit(ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * Sets where events shall be delivered
	 * 
	 * @param recipientUserId
	 */
	public EventRecorderInvoiceGenerateResult setEventRecipientUserId(final int recipientUserId)
	{
		this.recipientUserId = recipientUserId;
		return this;
	}

	@Override
	public void addInvoice(final I_C_Invoice invoice)
	{
		super.addInvoice(invoice);

		invoiceGeneratedEventBus.notify(invoice, recipientUserId);
	}
}
