package org.adempiere.util.lang.impl;

import java.util.List;

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
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ITableRecordReference;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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
	 * Creates an {@link TableRecordReference} from the given model.
	 * <p>
	 * <b>IMPORTANT:</b> if this method does not inspect the given model's <code>AD_Table_ID</code> and <code>Record_ID</code> but instead returns just <code>model</code>, wrapped up as
	 * {@link TableRecordReference}.
	 *
	 * @param model model interface or {@link TableRecordReference}; <code>null</code> is NOT allowed
	 * @return {@link TableRecordReference}; never returns null
	 */
	public static final TableRecordReference of(final Object model)
	{
		if (model instanceof TableRecordReference)
		{
			return (TableRecordReference)model;
		}
		return new TableRecordReference(model);
	}

	/**
	 * Same as {@link #of(Object)} but in case <code>model</code> is null then it will return null.
	 *
	 * @param model
	 * @return {@link TableRecordReference} or null
	 */
	public static final TableRecordReference ofOrNull(final Object model)
	{
		if (model == null)
		{
			return null;
		}
		return of(model);
	}
	
	public static final TableRecordReference of(final int adTableId, final int recordId)
	{
		return new TableRecordReference(adTableId, recordId);
	}
	
	public static final TableRecordReference of(final String tableName, final int recordId)
	{
		return new TableRecordReference(tableName, recordId);
	}
	
	/**
	 * @return immutable list of {@link TableRecordReference}s
	 */
	public static final List<ITableRecordReference> ofRecordIds(final String tableName, final List<Integer> recordIds)
	{
		if(recordIds == null || recordIds.isEmpty())
		{
			return ImmutableList.of();
		}
		
		return recordIds
				.stream()
				.map(modelId -> of(tableName, modelId))
				.collect(GuavaCollectors.toImmutableList());
	}

	private final transient int adTableId;
	@JsonProperty("tableName")
	private final String tableName;
	@JsonProperty("recordId")
	private final int recordId;
	private transient Integer _hashcode;

	/** Cached model */
	private transient Object model = null;

	/**
	 * Creates an instance that will be loaded on demand and is specified by the given <code>adTableId</code> and <code>recordId</code>.
	 *
	 * @param adTableId
	 * @param recordId
	 */
	public TableRecordReference(final int adTableId, final int recordId)
	{
		super();

		Check.assume(adTableId > 0, "adTableId > 0");
		this.adTableId = adTableId;
		this.tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);

		Check.assume(recordId > 0, "recordId > 0");
		this.recordId = recordId;
	}

	/**
	 * Creates an instance that will be loaded on demand and is specified by the given <code>tableName</code> and <code>recordId</code>.
	 *
	 * @param tableName
	 * @param recordId
	 */
	@JsonCreator
	public TableRecordReference(@JsonProperty("tableName") final String tableName, @JsonProperty("recordId") final int recordId)
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
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", tableName)
				.add("recordId", recordId)
				.add("model", model)
				.toString();
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
	public String getTableName()
	{
		return tableName;
	}

	@Override
	@JsonIgnore
	public int getAD_Table_ID()
	{
		return adTableId;
	}

	@Override
	@JsonIgnore
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
}
