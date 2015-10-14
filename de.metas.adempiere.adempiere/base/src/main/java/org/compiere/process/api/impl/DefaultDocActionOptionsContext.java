package org.compiere.process.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.Check;
import org.compiere.process.api.IDocActionOptionsContext;

/**
 * Configuration for DocAction list retrieval
 *
 * @author al
 */
public class DefaultDocActionOptionsContext implements IDocActionOptionsContext
{
	public static DefaultDocActionMapperBuilder builder(final Properties ctx)
	{
		return new DefaultDocActionMapperBuilder(ctx);
	}

	private final Properties ctx;

	private final int AD_Table_ID;
	private final int Record_ID;
	private final String DocStatus;
	private final String DocAction;
	private final int C_DocType_ID;
	private final boolean Processing;
	private final String OrderType;
	private final boolean IsSOTrx;

	private final Set<String> _options = new LinkedHashSet<>();
	private final Set<String> optionsRO = Collections.unmodifiableSet(_options);

	private String DocActionToUse;

	private DefaultDocActionOptionsContext(final DefaultDocActionMapperBuilder builder)
	{
		super();

		ctx = builder.ctx;

		Check.assumeNotNull(builder.AD_Table_ID, "builder.AD_Table_ID not null");
		AD_Table_ID = builder.AD_Table_ID;

		Check.assumeNotNull(builder.Record_ID, "builder.Record_ID not null");
		Record_ID = builder.Record_ID;

		Check.assumeNotEmpty(builder.DocStatus, "builder.DocStatus not empty");
		DocStatus = builder.DocStatus;

		Check.assumeNotEmpty(builder.DocAction, "builder.DocAction not empty");
		DocAction = builder.DocAction;
		DocActionToUse = DocAction; // initialize with original doc action

		//
		// C_DocType_ID
		// NOTE: we are tollerating null/not set C_DocType_ID because not all of our documents have this column.
		// Check.assumeNotNull(builder.C_DocType_ID, "builder.C_DocType_ID not null");
		if (builder.C_DocType_ID == null || builder.C_DocType_ID <= 0)
		{
			C_DocType_ID = -1;
		}
		else
		{
			C_DocType_ID = builder.C_DocType_ID;
		}

		Check.assumeNotNull(builder.Processing, "builder.Processing not null");
		Processing = builder.Processing;

		OrderType = builder.OrderType;

		Check.assumeNotNull(builder.IsSOTrx, "builder.IsSOTrx not null");
		IsSOTrx = builder.IsSOTrx;
	}

	public static class DefaultDocActionMapperBuilder
	{
		private final Properties ctx;

		private Integer AD_Table_ID;
		private Integer Record_ID;
		private String DocStatus;
		private String DocAction;
		private Integer C_DocType_ID;
		private Boolean Processing;
		private String OrderType = null;
		private Boolean IsSOTrx;

		private DefaultDocActionMapperBuilder(final Properties ctx)
		{
			super();

			this.ctx = ctx;
		}

		public IDocActionOptionsContext build()
		{
			return new DefaultDocActionOptionsContext(this);
		}

		public DefaultDocActionMapperBuilder setAD_Table_ID(final int AD_Table_ID)
		{
			this.AD_Table_ID = AD_Table_ID;
			return this;
		}

		public DefaultDocActionMapperBuilder setRecord_ID(final int Record_ID)
		{
			this.Record_ID = Record_ID;
			return this;
		}

		public DefaultDocActionMapperBuilder setDocStatus(final String DocStatus)
		{
			this.DocStatus = DocStatus;
			return this;
		}

		public DefaultDocActionMapperBuilder setDocAction(final String DocAction)
		{
			this.DocAction = DocAction;
			return this;
		}

		public DefaultDocActionMapperBuilder setC_DocType_ID(final Integer C_DocType_ID)
		{
			this.C_DocType_ID = C_DocType_ID;
			return this;
		}

		public DefaultDocActionMapperBuilder setProcessing(final boolean Processing)
		{
			this.Processing = Processing;
			return this;
		}

		public DefaultDocActionMapperBuilder setOrderType(final String OrderType)
		{
			this.OrderType = OrderType;
			return this;
		}

		public DefaultDocActionMapperBuilder setIsSOTrx(final boolean IsSOTrx)
		{
			this.IsSOTrx = IsSOTrx;
			return this;
		}
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	@Override
	public int getRecord_ID()
	{
		return Record_ID;
	}

	@Override
	public String getDocStatus()
	{
		return DocStatus;
	}

	@Override
	public String getDocActionInitial()
	{
		return DocAction;
	}

	@Override
	public String getDocActionToUse()
	{
		return DocActionToUse;
	}

	@Override
	public void setDocActionToUse(final String DocAction)
	{
		DocActionToUse = DocAction;
	}

	@Override
	public int getC_DocType_ID()
	{
		return C_DocType_ID;
	}

	@Override
	public boolean isProcessing()
	{
		return Processing;
	}

	@Override
	public String getOrderType()
	{
		return OrderType;
	}

	@Override
	public boolean isSOTrx()
	{
		return IsSOTrx;
	}

	@Override
	public Set<String> getOptions()
	{
		return optionsRO;
	}

	@Override
	public void setOptions(final Collection<String> options)
	{
		_options.clear();
		_options.addAll(options);
	}
}
