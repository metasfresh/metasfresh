package org.compiere.apps.search.history.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.sql.Timestamp;

import org.compiere.apps.search.history.IInvoiceHistoryTabHandler;

/**
 * Context with which {@link InvoiceHistory} is loaded.
 *
 * @author al
 */
public class InvoiceHistoryContext
{
	@Override
	public String toString()
	{
		// preserve legacy
		return "C_BPartner_ID=" + getC_BPartner_ID()
				+ ", M_Product_ID=" + getM_Product_ID()
				+ ", M_Warehouse_ID=" + getM_Warehouse_ID()
				+ ", M_AttributeSetInstance_ID=" + getM_AttributeSetInstance_ID();
	}

	private final IInvoiceHistoryTabHandler ihTabHandler = new DefaultInvoiceHistoryTabHandler();

	//
	// Model configuration
	private final int C_BPartner_ID;
	private final int M_Product_ID;
	private final int M_Warehouse_ID;
	private final int M_AttributeSetInstance_ID;
	private final Timestamp DatePromised;

	//
	// GUI configuration
	private final boolean rowSelectionAllowed;

	private InvoiceHistoryContext(final InvoiceHistoryContextBuilder builder)
	{
		super();

		C_BPartner_ID = builder.C_BPartner_ID;
		M_Product_ID = builder.M_Product_ID;
		M_Warehouse_ID = builder.M_Warehouse_ID;
		M_AttributeSetInstance_ID = builder.M_AttributeSetInstance_ID;
		DatePromised = builder.DatePromised;

		rowSelectionAllowed = builder.rowSelectionAllowed;
	}

	public IInvoiceHistoryTabHandler getInvoiceHistoryTabHandler()
	{
		return ihTabHandler;
	}

	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}

	public int getM_Product_ID()
	{
		return M_Product_ID;
	}

	public int getM_Warehouse_ID()
	{
		return M_Warehouse_ID;
	}

	public int getM_AttributeSetInstance_ID()
	{
		return M_AttributeSetInstance_ID;
	}

	public Timestamp getDatePromisedOrNull()
	{
		return DatePromised;
	}

	public boolean isRowSelectionAllowed()
	{
		return rowSelectionAllowed;
	}

	public static InvoiceHistoryContextBuilder builder()
	{
		return new InvoiceHistoryContextBuilder();
	}

	public static class InvoiceHistoryContextBuilder
	{
		public InvoiceHistoryContext build()
		{
			return new InvoiceHistoryContext(this);
		}

		private int C_BPartner_ID = -1;
		private int M_Product_ID = -1;
		private int M_Warehouse_ID = -1;
		private int M_AttributeSetInstance_ID = -1;
		private Timestamp DatePromised = null;

		private boolean rowSelectionAllowed = false;

		public InvoiceHistoryContextBuilder setC_BPartner_ID(final int c_BPartner_ID)
		{
			C_BPartner_ID = c_BPartner_ID;
			return this;
		}

		public InvoiceHistoryContextBuilder setM_Product_ID(final int m_Product_ID)
		{
			M_Product_ID = m_Product_ID;
			return this;
		}

		public InvoiceHistoryContextBuilder setM_Warehouse_ID(final int m_Warehouse_ID)
		{
			M_Warehouse_ID = m_Warehouse_ID;
			return this;
		}

		public InvoiceHistoryContextBuilder setM_AttributeSetInstance_ID(final int m_AttributeSetInstance_ID)
		{
			M_AttributeSetInstance_ID = m_AttributeSetInstance_ID;
			return this;
		}

		public InvoiceHistoryContextBuilder setDatePromised(final Timestamp DatePromised)
		{
			this.DatePromised = DatePromised;
			return this;
		}

		public InvoiceHistoryContextBuilder setRowSelectionAllowed(final boolean rowSelectionAllowed)
		{
			this.rowSelectionAllowed = rowSelectionAllowed;
			return this;
		}
	}
}
