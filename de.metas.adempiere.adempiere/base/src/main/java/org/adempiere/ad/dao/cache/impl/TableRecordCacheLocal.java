package org.adempiere.ad.dao.cache.impl;

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


import java.lang.ref.WeakReference;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.google.common.base.Optional;

import lombok.NonNull;

/**
 * Local cache used to store model references on records which have AD_Table_ID, Record_ID.
 *
 * TODO: merge logic with {@link org.adempiere.util.lang.impl.TableRecordReference}.
 *
 * @author tsa
 *
 * @param <ParentModelType>
 */
public class TableRecordCacheLocal<ParentModelType>
{
	private static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";
	private static final String COLUMNNAME_Record_ID = "Record_ID";
	private static final String DYNATTR_TableRecord = TableRecordCacheLocal.class.getName();

	public static <ParentModelType, ChildModelType> void setReferencedValue(final ParentModelType parentModel, final ChildModelType value)
	{
		final TableRecordCacheLocal<ParentModelType> cache = getTableRecordCacheLocal(parentModel);
		cache.setValue(value);
	}

	/**
	 * Gets/Retrieves the model referenced by parentModel's {@link #getAD_Table_ID()} and {@link #getRecord_ID()} and wraps it to <code>childModelClass</code>.
	 *
	 * If childModelClass's table does not match {@link #getAD_Table_ID()} then <code>null</code> will be returned.
	 *
	 * @param parentModel
	 * @param modelClass
	 * @return referenced model or <code>null</code> if is not set or it's not matching given childModelClass.
	 */
	public static <ParentModelType, ChildModelType> ChildModelType getReferencedValue(final ParentModelType parentModel, final Class<ChildModelType> childModelClass)
	{
		final TableRecordCacheLocal<ParentModelType> cache = getTableRecordCacheLocal(parentModel);
		return cache.getValue(childModelClass);
	}

	/**
	 * Checks if the underlying referenced record is of given type.
	 *
	 * NOTE: this method is actually checking only the underlying "AD_Table_ID" and not if the referenced record exists.
	 *
	 * @param parentModel
	 * @param childModelClass
	 * @return true if referenced (child) model is of given type
	 */
	public static <ParentModelType, ChildModelType> boolean isChildModelType(final ParentModelType parentModel, final Class<ChildModelType> childModelClass)
	{
		Check.assumeNotNull(childModelClass, "childModelClass not null");
		final int adTableId = InterfaceWrapperHelper.getTableId(childModelClass);

		final TableRecordCacheLocal<ParentModelType> cache = getTableRecordCacheLocal(parentModel);
		return adTableId == cache.getModelTableId();
	}

	private static final <ParentModelType> TableRecordCacheLocal<ParentModelType> getTableRecordCacheLocal(final ParentModelType parentModel)
	{
		TableRecordCacheLocal<ParentModelType> cache = InterfaceWrapperHelper.getDynAttribute(parentModel, DYNATTR_TableRecord);
		if (cache == null || !cache.hasParentModel())
		{
			cache = new TableRecordCacheLocal<ParentModelType>(parentModel);
			InterfaceWrapperHelper.setDynAttribute(parentModel, DYNATTR_TableRecord, cache);
		}
		return cache;
	}

	private final WeakReference<ParentModelType> _parentModelRef;
	private Object model;
	private int modelTableId = -1;
	private int modelRecordId = -1;

	public TableRecordCacheLocal(final ParentModelType parentModel)
	{
		super();

		Check.assumeNotNull(parentModel, "parentModel not null");
		this._parentModelRef = new WeakReference<ParentModelType>(parentModel);
	}

	/**
	 *
	 * @return parent model (never null)
	 */
	protected final ParentModelType getParentModel()
	{
		final ParentModelType parentModel = _parentModelRef.get();
		if (parentModel == null)
		{
			throw new AdempiereException("parent model expired");
		}
		return parentModel;
	}

	private boolean hasParentModel()
	{
		return _parentModelRef.get() != null;
	}

	protected Properties getCtx()
	{
		final ParentModelType parentModel = getParentModel();
		return InterfaceWrapperHelper.getCtx(parentModel);
	}

	protected String getTrxName()
	{
		final ParentModelType parentModel = getParentModel();
		return InterfaceWrapperHelper.getTrxName(parentModel);
	}

	protected int getAD_Table_ID()
	{
		final ParentModelType parentModel = getParentModel();
		final Optional<Integer> adTableId = InterfaceWrapperHelper.getValue(parentModel, COLUMNNAME_AD_Table_ID);
		if (!adTableId.isPresent())
		{
			return -1;
		}
		if (adTableId.get() <= 0)
		{
			return -1;
		}

		return adTableId.get();
	}

	protected int getRecord_ID()
	{
		final ParentModelType parentModel = getParentModel();
		final Optional<Integer> recordId = InterfaceWrapperHelper.getValue(parentModel, COLUMNNAME_Record_ID);
		if (!recordId.isPresent())
		{
			return -1;
		}
		if (recordId.get() < 0)
		{
			return -1;
		}

		return recordId.get();
	}

	protected void setTableAndRecordId(int adTableId, int recordId)
	{
		final ParentModelType parentModel = getParentModel();
		InterfaceWrapperHelper.setValue(parentModel, COLUMNNAME_AD_Table_ID, adTableId);
		InterfaceWrapperHelper.setValue(parentModel, COLUMNNAME_Record_ID, recordId);
	}

	/**
	 * Gets/Retrieves the model referenced by {@link #getAD_Table_ID()} and {@link #getRecord_ID()} and wraps it to <code>modelClass</code>.
	 *
	 * If modelClass's table does not match {@link #getAD_Table_ID()} then <code>null</code> will be returned.
	 *
	 * @param modelClass
	 * @return referenced model or null
	 */
	public final <RT> RT getValue(@NonNull Class<RT> modelClass)
	{
		//
		// If parent's AD_Table_ID/Record_ID are not set
		// => reset model
		// => return null
		final int requiredTableId = getAD_Table_ID();
		if (requiredTableId <= 0)
		{
			resetModel();
			return null;
		}
		final int requiredRecordId = getRecord_ID();
		if (requiredRecordId <= 0)
		{
			resetModel();
			return null;
		}

		//
		// Check if referenced object is from expected class
		// If not, return null.
		{
			final int modelClassTableId = InterfaceWrapperHelper.getTableIdOrNull(modelClass);
			if (modelClassTableId <= 0)
			{
				// model class does not contain the AD_Table_ID.
				// This can be a correct case (e.g. modelClass=Object.class).
				// => ignore the validation
			}
			else if (modelClassTableId != requiredTableId)
			{
				// NOTE: don't reset the model because model could be fine, but we were just asked for something else
				return null;
			}
		}

		//
		// Model is not loaded yet
		// => load it
		// => return it
		if (model == null)
		{
			final Properties ctx = getCtx();
			final String trxName = getTrxName();
			return loadModel(ctx, requiredTableId, requiredRecordId, modelClass, trxName);
		}

		//
		// Make sure model has the right tableId
		final int modelTableId = getModelTableId();
		if (modelTableId != requiredTableId)
		{
			final Properties ctx = getCtx();
			final String trxName = getTrxName();
			return loadModel(ctx, requiredTableId, requiredRecordId, modelClass, trxName);
		}

		//
		// Make sure model has the right ID
		final int modelRecordId = getModelRecordId();
		if (modelRecordId != requiredRecordId)
		{
			final Properties ctx = getCtx();
			final String trxName = getTrxName();
			return loadModel(ctx, requiredTableId, requiredRecordId, modelClass, trxName);
		}

		//
		// Make sure we are running in same transaction
		final String trxName = getTrxName();
		final String modelTrxName = getModelTrxName();
		if (!Services.get(ITrxManager.class).isSameTrxName(trxName, modelTrxName))
		{
			final Properties ctx = getCtx();
			return loadModel(ctx, requiredTableId, requiredRecordId, modelClass, trxName);
		}

		//
		// Make sure we are running in same context
		final Properties ctx = getCtx();
		final Properties modelCtx = getModelCtx();
		if (!Env.isSameSession(ctx, modelCtx))
		{
			return loadModel(ctx, requiredTableId, requiredRecordId, modelClass, trxName);
		}

		return InterfaceWrapperHelper.create(model, modelClass);
	}

	public void setValue(Object model)
	{
		final int adTableId;
		final int recordId;
		if (model == null)
		{
			adTableId = -1;
			recordId = -1;
		}
		else
		{
			adTableId = InterfaceWrapperHelper.getModelTableId(model);
			recordId = InterfaceWrapperHelper.getId(model);
		}

		setTableAndRecordId(adTableId, recordId);
		setModel(model, adTableId, recordId);
	}

	private Properties getModelCtx()
	{
		return InterfaceWrapperHelper.getCtx(model);
	}

	private String getModelTrxName()
	{
		return InterfaceWrapperHelper.getTrxName(model);
	}

	private int getModelTableId()
	{
		return modelTableId;
	}

	private int getModelRecordId()
	{
		return modelRecordId;
	}

	private final <RT> RT loadModel(final Properties ctx, final int adTableId, final int recordId, final Class<RT> modelClass, final String trxName)
	{
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);
		final RT modelCasted = InterfaceWrapperHelper.create(ctx, tableName, recordId, modelClass, trxName);
		setModel(modelCasted, modelTableId, modelRecordId);

		return modelCasted;
	}

	private final void setModel(final Object model, int adTableId, int recordId)
	{
		this.model = model;
		this.modelTableId = adTableId;
		this.modelRecordId = recordId;
	}

	private final void resetModel()
	{
		this.model = null;
		this.modelTableId = -1;
		this.modelRecordId = -1;
	}
}
