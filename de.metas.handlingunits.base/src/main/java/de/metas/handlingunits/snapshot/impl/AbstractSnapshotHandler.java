package de.metas.handlingunits.snapshot.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;

import com.google.common.base.Function;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.snapshot.ISnapshotHandler;

abstract class AbstractSnapshotHandler<ModelType, SnapshotModelType, ParentModelType>
		implements ISnapshotHandler<ModelType, SnapshotModelType, ParentModelType>
{
	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	protected final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	// Parameters
	@ToStringBuilder(skip = true)
	private final AbstractSnapshotHandler<?, ?, ?> _parentHandler;
	private IContextAware _context;
	private Date _dateTrx;
	private Object _referencedModel;
	private String _snapshotId;

	protected final Function<SnapshotModelType, Integer> snapshot2ModelIdFunction = new Function<SnapshotModelType, Integer>()
	{
		@Override
		public Integer apply(final SnapshotModelType huItemSnapshot)
		{
			return getModelId(huItemSnapshot);
		}
	};

	AbstractSnapshotHandler(final AbstractSnapshotHandler<?, ?, ?> parentHandler)
	{
		super();

		// null is also accepted for top level handlers
		this._parentHandler = parentHandler;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public ISnapshotHandler<ModelType, SnapshotModelType, ParentModelType> setDateTrx(final Date dateTrx)
	{
		this._dateTrx = dateTrx;
		return this;
	}

	/** @return transaction date; never null */
	protected final Date getDateTrx()
	{
		if (_dateTrx != null)
		{
			return _dateTrx;
		}
		else if (_parentHandler != null)
		{
			return _parentHandler.getDateTrx();
		}

		throw new AdempiereException("DateTrx is not configured to " + this);
	}

	@Override
	public ISnapshotHandler<ModelType, SnapshotModelType, ParentModelType> setContext(final IContextAware context)
	{
		this._context = context;
		return this;
	}

	/**
	 * @return context; never null
	 */
	protected final IContextAware getContext()
	{
		if (_context != null)
		{
			return _context;
		}
		else if (_parentHandler != null)
		{
			return _parentHandler.getContext();
		}

		return PlainContextAware.newWithThreadInheritedTrx();
	}

	@Override
	public ISnapshotHandler<ModelType, SnapshotModelType, ParentModelType> setReferencedModel(final Object referencedModel)
	{
		this._referencedModel = referencedModel;
		return this;
	}

	/**
	 * @return referenced model, if one was set earlier.
	 */
	protected final Object getReferencedModelOrNull()
	{
		if (_referencedModel != null)
		{
			return _referencedModel;
		}
		else if (_parentHandler != null)
		{
			return _parentHandler.getReferencedModelOrNull();
		}

		return null;
	}

	@Override
	public ISnapshotHandler<ModelType, SnapshotModelType, ParentModelType> setSnapshotId(final String snapshotId)
	{
		this._snapshotId = snapshotId;
		return this;
	}

	@Override
	public final String getSnapshotId()
	{
		if (!Check.isEmpty(_snapshotId, true))
		{
			return _snapshotId;
		}
		else if (_parentHandler != null)
		{
			return _parentHandler.getSnapshotId();
		}
		throw new AdempiereException("SnapshotId is not configured to " + this);
	}

	/**
	 * Create models snapshot records for all models that are identified by parent IDs.
	 *
	 * @param context
	 * @param modelParentIds
	 * @param snapshotId
	 */
	protected void createSnapshotsByParentIds(final Set<Integer> modelParentIds)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Restores the model from given snapshot.
	 *
	 * @param model
	 * @param modelSnapshot
	 */
	protected final void restoreModelFromSnapshot(final ModelType model, final SnapshotModelType modelSnapshot)
	{
		if (model == null)
		{
			//
			// Case: both model and the model snapshot are missing => shall not happen
			if (modelSnapshot == null)
			{
				throw new HUException("Both model and model snapshots are null"); // shall not happen, never!
			}
			//
			// Case: model is missing but we have a snapshot
			// => model was deleted so we need to restore it from ashes
			else
			{
				restoreModelAsNew(modelSnapshot);
			}
		}
		else
		{
			//
			// Case: we have a model but we don't have a snapshot
			// => model was created after our snapshot => we need to delete/inactivate it
			if (modelSnapshot == null)
			{
				restoreModelWhenSnapshotIsMissing(model);
			}
			//
			// Case: we have a model and we also have a snapshot
			// => we need to restore it
			else
			{
				restoreModelValuesFromSnapshot(model, modelSnapshot);
			}
		}

		//
		// Save the restored model
		saveRestoredModel(model, modelSnapshot);

		//
		// Restore children (if any)
		restoreChildrenFromSnapshots(model);
	}

	protected void restoreChildrenFromSnapshots(final ModelType model)
	{
		// nothing at this level
	}

	/**
	 * Save the model after it was restored
	 *
	 * @param model
	 * @param modelSnapshot model snapshot that was used to restore the model. It could be null.
	 */
	protected void saveRestoredModel(final ModelType model, final SnapshotModelType modelSnapshot)
	{
		trxManager.assertThreadInheritedTrxExists();

		InterfaceWrapperHelper.save(model, ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * Restore model's values from snapshot values.
	 *
	 * NOTE: when this method is called, both model and modelSnapshot are not null.
	 *
	 * @param model
	 * @param modelSnapshot
	 */
	@OverridingMethodsMustInvokeSuper
	protected void restoreModelValuesFromSnapshot(final ModelType model, final SnapshotModelType modelSnapshot)
	{
		InterfaceWrapperHelper.copy()
				.setFrom(modelSnapshot)
				.setTo(model)
				.setSkipCalculatedColumns(false) // copy everything
				.copy();
	}

	/**
	 * Restore model when it snapshot is missing because the model was created after the snapshot was taken.
	 *
	 * Suggestion for implementors: maybe in this case you want to delete the model or you want to set it's relevant values to ZERO/<code>null</code>.
	 *
	 * @param model
	 */
	protected abstract void restoreModelWhenSnapshotIsMissing(final ModelType model);

	/**
	 * Completelly restore the model from snapshot because the model is missing at all.
	 *
	 * @param modelSnapshot
	 */
	protected void restoreModelAsNew(final SnapshotModelType modelSnapshot)
	{
		throw new HUException("Restoring model from ashes is not supported for " + modelSnapshot);
	}

	protected abstract SnapshotModelType retrieveModelSnapshot(final ModelType model);

	protected final void restoreModelsFromSnapshotsByParent(final ParentModelType parentModel)
	{
		final Map<Integer, SnapshotModelType> modelSnapshots = retrieveModelSnapshotsByParent(parentModel);
		final Map<Integer, ModelType> models = new HashMap<>(retrieveModelsByParent(parentModel));

		//
		// Iterate model snapshots and try to restore the models from them
		for (final SnapshotModelType modelSnapshot : modelSnapshots.values())
		{
			final int modelId = getModelId(modelSnapshot);
			ModelType model = models.remove(modelId);

			//
			// Handle the case when the model's parent was changed
			// In this case we retrieve the model directly from snapshot's link.
			if (model == null)
			{
				model = getModel(modelSnapshot);
			}

			restoreModelFromSnapshot(model, modelSnapshot);
		}

		// Iterate remaining models and delete/inactivate them.
		// NOTE: those are the models which were created after our snapshot.
		for (final ModelType model : models.values())
		{
			final SnapshotModelType modelSnapshot = null; // no snapshot
			restoreModelFromSnapshot(model, modelSnapshot);
		}
	}

	/**
	 * @param modelSnapshot
	 * @return Model's ID from given snapshot
	 */
	protected abstract int getModelId(final SnapshotModelType modelSnapshot);

	/**
	 * @param modelSnapshot
	 * @return model retrieved using {@link #getModelId(Object)}.
	 */
	protected abstract ModelType getModel(final SnapshotModelType modelSnapshot);

	/**
	 *
	 * @param parentModel
	 * @param snapshotId
	 * @return "Model ID" to ModelSnapshot map
	 */
	protected abstract Map<Integer, SnapshotModelType> retrieveModelSnapshotsByParent(final ParentModelType parentModel);

	/**
	 *
	 * @param model
	 * @return "Model ID" to Model map
	 */
	protected abstract Map<Integer, ModelType> retrieveModelsByParent(final ParentModelType parentModel);

	protected final <T> IQueryBuilder<T> query(final Class<T> modelClass)
	{
		return queryBL.createQueryBuilder(modelClass, getContext());
	}
}
