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
import java.util.Properties;

import org.compiere.model.I_AD_Note;

import com.google.common.collect.ForwardingObject;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;
import de.metas.util.Check;

/**
 * {@link IInvoiceGenerateResult} implementation which is simply forwarding all methods to a given delegate.
 * 
 * @author tsa
 *
 */
public abstract class ForwardingInvoiceGenerateResult extends ForwardingObject implements IInvoiceGenerateResult
{
	private final IInvoiceGenerateResult delegate;

	public ForwardingInvoiceGenerateResult(final IInvoiceGenerateResult delegate)
	{
		super();
		Check.assumeNotNull(delegate, "delegate not null");
		this.delegate = delegate;
	}

	@Override
	protected final IInvoiceGenerateResult delegate()
	{
		return delegate;
	}

	@Override
	public int getInvoiceCount()
	{
		return delegate().getInvoiceCount();
	}

	@Override
	public List<I_C_Invoice> getC_Invoices()
	{
		return delegate().getC_Invoices();
	}

	@Override
	public int getNotificationCount()
	{
		return delegate().getNotificationCount();
	}

	@Override
	public List<I_AD_Note> getNotifications()
	{
		return delegate().getNotifications();
	}

	@Override
	public String getNotificationsWhereClause()
	{
		return delegate().getNotificationsWhereClause();
	}

	@Override
	public void addInvoice(final I_C_Invoice invoice)
	{
		delegate().addInvoice(invoice);
	}

	@Override
	public void addNotifications(final List<I_AD_Note> notifications)
	{
		delegate().addNotifications(notifications);
	}

	@Override
	public String getSummary(final Properties ctx)
	{
		return delegate().getSummary(ctx);
	}

}
