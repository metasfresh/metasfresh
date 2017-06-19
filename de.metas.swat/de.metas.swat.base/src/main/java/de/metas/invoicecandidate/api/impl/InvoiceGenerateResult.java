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
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Accessor;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Note;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;

/* package */class InvoiceGenerateResult implements IInvoiceGenerateResult
{
	private static final String MSG_Summary = "InvoiceGenerateResult";

	private List<I_C_Invoice> invoices = new ArrayList<>();
	private List<I_AD_Note> notifications = new ArrayList<>();
	private String notificationsWhereClause = null;

	private final boolean storeInvoices;
	private int invoiceCount = 0;

	/**
	 * 
	 * @param storeInvoices if true, the generated invoices themselves are stored. If false, only their number is stored.
	 */
	public InvoiceGenerateResult(boolean storeInvoices)
	{
		this.storeInvoices = storeInvoices;
	}

	@Override
	public int getInvoiceCount()
	{
		if (storeInvoices)
		{
			return this.invoices.size();
		}
		return invoiceCount;
	}

	@Override
	public List<I_C_Invoice> getC_Invoices()
	{
		return this.invoices;
	}

	@Override
	public void addInvoice(I_C_Invoice invoice)
	{
		if (invoice == null)
		{
			return;
		}
		if (storeInvoices)
		{
			this.invoices.add(invoice);
		}
		else
		{
			invoiceCount++;
		}
	}

	@Override
	public int getNotificationCount()
	{
		return this.notifications.size();
	}

	@Override
	public List<I_AD_Note> getNotifications()
	{
		return this.notifications;
	}

	@Override
	public void addNotifications(final List<I_AD_Note> list)
	{
		if (list != null && !list.isEmpty())
		{
			this.notifications.addAll(list);
			this.notificationsWhereClause = null;
		}
	}

	@Override
	public String getNotificationsWhereClause()
	{
		if (this.notificationsWhereClause == null)
		{
			final Accessor accessor = new Accessor()
			{
				@Override
				public Object getValue(Object o)
				{
					return ((I_AD_Note)o).getAD_Note_ID();
				}
			};
			this.notificationsWhereClause = buildSqlInArrayWhereClause(
					I_AD_Note.COLUMNNAME_AD_Note_ID,
					this.notifications,
					accessor,
					null // params
			);
		}
		return this.notificationsWhereClause;
	}

	private String buildSqlInArrayWhereClause(String columnName, List<?> values, Accessor accessor, List<Object> params)
	{
		if (values == null || values.isEmpty())
			return "";

		boolean hasNull = false;
		StringBuffer whereClause = new StringBuffer();
		StringBuffer whereClauseCurrent = new StringBuffer();
		int count = 0;
		for (Object v : values)
		{
			final Object value;
			if (accessor != null)
				value = accessor.getValue(v);
			else
				value = v;
			if (value == null)
			{
				hasNull = true;
				continue;
			}
			if (whereClauseCurrent.length() > 0)
				whereClauseCurrent.append(",");
			if (params == null)
			{
				whereClauseCurrent.append(value);
			}
			else
			{
				whereClauseCurrent.append("?");
				params.add(value);
			}

			if (count >= 30)
			{
				if (whereClause.length() > 0)
					whereClause.append(" OR ");
				whereClause.append(columnName).append(" IN (").append(whereClauseCurrent).append(")");
				whereClauseCurrent = new StringBuffer();
				count = 0;
			}
		}
		if (whereClauseCurrent.length() > 0)
		{
			if (whereClause.length() > 0)
				whereClause.append(" OR ");
			whereClause.append(columnName).append(" IN (").append(whereClauseCurrent).append(")");
			whereClauseCurrent = null;
		}

		if (hasNull)
		{
			if (whereClause.length() > 0)
				whereClause.append(" OR ");
			whereClause.append(columnName).append(" IS NULL");
		}

		if (whereClause.length() > 0)
			whereClause.insert(0, "(").append(")");

		return whereClause.toString();
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("InvoiceGenerateResult [");

		sb.append("invoiceCount=").append(getInvoiceCount());
		if (storeInvoices)
		{
			sb.append(", invoices=").append(invoices);
		}

		if (notifications != null && !notifications.isEmpty())
		{
			sb.append(", notifications=").append(notifications);
		}
		sb.append("]");

		return sb.toString();
	}

	@Override
	public String getSummary(final Properties ctx)
	{
		return Services.get(IMsgBL.class).getMsg(ctx, MSG_Summary, new Object[] { getInvoiceCount(), getNotificationCount() });
	}

}
