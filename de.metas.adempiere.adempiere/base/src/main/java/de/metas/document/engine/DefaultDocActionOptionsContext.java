package de.metas.document.engine;

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
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;

/**
 * Configuration for DocAction list retrieval
 *
 * @author al
 */
public class DefaultDocActionOptionsContext implements IDocActionOptionsContext
{
	public static Builder builder(final Properties ctx)
	{
		return new Builder(ctx);
	}

	private final Properties ctx;

	private final String tableName;
	private final String DocStatus;
	private final int C_DocType_ID;
	private final boolean Processing;
	private final String OrderType;
	private final boolean IsSOTrx;

	private final Set<String> _docActions = new LinkedHashSet<>();
	private final Set<String> docActionsRO = Collections.unmodifiableSet(_docActions);

	private String DocActionToUse;

	private DefaultDocActionOptionsContext(final Builder builder)
	{
		super();

		ctx = builder.ctx;

		Check.assumeNotEmpty(builder.tableName, "builder.tableName is not empty");
		tableName = builder.tableName;

		Check.assumeNotEmpty(builder.DocStatus, "builder.DocStatus not empty");
		DocStatus = builder.DocStatus;

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

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("DocStatus", DocStatus)
				.add("Options", _docActions)
				.add("DocActionToUse", DocActionToUse)
				.add("tableName", tableName)
				.add("C_DocType_ID", C_DocType_ID)
				.add("Processing", Processing)
				.add("OrderType", OrderType)
				.add("IsSOTrx", IsSOTrx)
				// .add("ctx", ctx) // to big
				.toString();
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public int getAD_Client_ID()
	{
		return Env.getAD_Client_ID(ctx);
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public String getDocStatus()
	{
		return DocStatus;
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
	public Set<String> getDocActions()
	{
		return docActionsRO;
	}

	@Override
	public void setDocActions(final Collection<String> docActions)
	{
		_docActions.clear();
		_docActions.addAll(docActions);
	}

	public static class Builder
	{
		private final Properties ctx;

		private String tableName;
		private String DocStatus;
		private Integer C_DocType_ID;
		private Boolean Processing;
		private String OrderType = null;
		private Boolean IsSOTrx;

		private Builder(final Properties ctx)
		{
			super();

			Check.assumeNotNull(ctx, "Parameter ctx is not null");
			this.ctx = ctx;
		}

		public IDocActionOptionsContext build()
		{
			return new DefaultDocActionOptionsContext(this);
		}

		public Builder setTableName(final String tableName)
		{
			this.tableName = tableName;
			return this;
		}

		public Builder setDocStatus(final String DocStatus)
		{
			this.DocStatus = DocStatus;
			return this;
		}

		public Builder setC_DocType_ID(final Integer C_DocType_ID)
		{
			this.C_DocType_ID = C_DocType_ID;
			return this;
		}

		public Builder setProcessing(final boolean Processing)
		{
			this.Processing = Processing;
			return this;
		}

		public Builder setOrderType(final String OrderType)
		{
			this.OrderType = OrderType;
			return this;
		}

		public Builder setIsSOTrx(final boolean IsSOTrx)
		{
			this.IsSOTrx = IsSOTrx;
			return this;
		}
	}
}
