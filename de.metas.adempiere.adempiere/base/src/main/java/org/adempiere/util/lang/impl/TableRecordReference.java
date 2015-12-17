package org.adempiere.util.lang.impl;

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

import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.util.EqualsBuilder;
import org.compiere.util.HashcodeBuilder;

/**
 * Simple implementation of {@link ITableRecordReference} which can:
 * <ul>
 * <li>wrap an already loaded model (if you use {@link #TableRecordReference(Object)} constructor)
 * <li>start from known AD_Table_ID/Record_ID and will load the underlying model only when it's needed (if you use {@link #TableRecordReference(int, int)} constructor)
 * </ul>
 *
 * TODO: merge logic with {@link org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal}.
 * 
 * @author tsa
 *
 */
public final class TableRecordReference implements ITableRecordReference
{
	/**
	 * Creates an {@link ITableRecordReference}.
	 * 
	 * @param model model interface or {@link ITableRecordReference}; null is NOT allowed
	 * @return {@link ITableRecordReference}; never returns null
	 */
	public static final ITableRecordReference of(final Object model)
	{
		if (model instanceof ITableRecordReference)
		{
			return (ITableRecordReference)model;
		}
		return new TableRecordReference(model);
	}

	/**
	 * Same as {@link #of(Object)} but in case <code>model</code> is null then it will return null.
	 * 
	 * @param model
	 * @return {@link ITableRecordReference} or null
	 */
	public static final ITableRecordReference ofOrNull(final Object model)
	{
		if (model == null)
		{
			return null;
		}
		return of(model);
	}

	private final int adTableId;
	private final String tableName;
	private final int recordId;
	@ToStringBuilder(skip = true)
	private Integer _hashcode;

	/** Cached model */
	private transient Object model = null;

	public TableRecordReference(final int adTableId, final int recordId)
	{
		super();

		Check.assume(adTableId > 0, "adTableId > 0");
		this.adTableId = adTableId;
		this.tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);

		Check.assume(recordId > 0, "recordId > 0");
		this.recordId = recordId;
	}

	public TableRecordReference(final String tableName, final int recordId)
	{
		super();

		Check.assumeNotEmpty(tableName, "tableName not empty");
		this.tableName = tableName;
		this.adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		Check.assume(recordId > 0, "recordId > 0");
		this.recordId = recordId;
	}

	private TableRecordReference(final Object model)
	{
		super();

		Check.assumeNotNull(model, "model not null");
		this.adTableId = InterfaceWrapperHelper.getModelTableId(model);
		this.tableName = InterfaceWrapperHelper.getModelTableName(model);
		this.recordId = InterfaceWrapperHelper.getId(model);
		this.model = model;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final TableRecordReference other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.adTableId, other.adTableId)
				.append(this.tableName, other.tableName)
				.append(this.recordId, other.recordId)
				.isEqual();
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = new HashcodeBuilder()
					.append(this.adTableId)
					.append(this.tableName)
					.append(this.recordId)
					.toHashcode();
		}
		return _hashcode;
	}

	@Override
	public int getAD_Table_ID()
	{
		return adTableId;
	}

	@Override
	public int getRecord_ID()
	{
		return recordId;
	}

	@Override
	public Object getModel(final IContextAware context)
	{
		checkModelStaled(context);

		//
		// Load the model now
		if (model == null)
		{
			final Properties ctx = context.getCtx();
			final String trxName = context.getTrxName();
			model = InterfaceWrapperHelper.create(ctx, tableName, getRecord_ID(), Object.class, trxName);
		}

		return model;
	}

	@Override
	public <T> T getModel(final IContextAware context, final Class<T> modelClass)
	{
		return InterfaceWrapperHelper.create(getModel(context), modelClass);
	}

	/**
	 * Checks if underlying (and cached) model is still valid in given context. In case is no longer valid, it will be set to <code>null</code>.
	 * 
	 * @param context
	 */
	private void checkModelStaled(final IContextAware context)
	{
		if (model == null)
		{
			return;
		}

		final String modelTrxName = InterfaceWrapperHelper.getTrxName(model);
		if (!Services.get(ITrxManager.class).isSameTrxName(modelTrxName, context.getTrxName()))
		{
			model = null;
			return;
		}

		// TODO: why the ctx is not validated, like org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal.getValue(Class<RT>) does?
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("TableRecordReference [tableName=").append(tableName);
		builder.append(", recordId=").append(recordId);
		builder.append(", model=").append(model);
		builder.append("]");
		return builder.toString();
	}

}
