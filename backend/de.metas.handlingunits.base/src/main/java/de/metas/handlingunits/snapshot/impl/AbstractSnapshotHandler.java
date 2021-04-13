package de.metas.handlingunits.snapshot.impl;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.snapshot.ISnapshotHandler;
import de.metas.i18n.BooleanWithReason;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@ToString
abstract class AbstractSnapshotHandler<ModelType, SnapshotModelType, ParentModelType>
		implements ISnapshotHandler<ModelType, SnapshotModelType, ParentModelType>
{
	// services
	@ToString.Exclude
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	@ToString.Exclude
	protected final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	// Parameters
	@ToString.Exclude
	private final AbstractSnapshotHandler<?, ?, ?> _parentHandler;
	private IContextAware _context;
	private ZonedDateTime _dateTrx;
	private Object _referencedModel;
	private String _snapshotId;

	protected final Function<SnapshotModelType, Integer> snapshot2ModelIdFunction = this::getModelId;

	AbstractSnapshotHandler(@Nullable final AbstractSnapshotHandler<?, ?, ?> parentHandler)
	{
		// null is also accepted for top level handlers
		this._parentHandler = parentHandler;
	}

	@Override
	public ISnapshotHandler<ModelType, SnapshotModelType, ParentModelType> setDateTrx(final Date dateTrx)
	{
		this._dateTrx = TimeUtil.asZonedDateTime(dateTrx);
		return this;
	}

	/**
	 * @return transaction date; never null
	 */
	protected final ZonedDateTime getDateTrx()
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
	@Nullable
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
		else
		{
			return null;
		}
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
	 */
	protected void createSnapshotsByParentIds(final Set<Integer> modelParentIds)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Restores the model from given snapshot.
	 */
	protected final void restoreModelFromSnapshot(@Nullable final ModelType model, @Nullable final SnapshotModelType modelSnapshot)
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
				throw new HUException("Restoring model from ashes is not supported for " + modelSnapshot);
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
	 * @param modelSnapshot model snapshot that was used to restore the model. It could be null.
	 */
	protected void saveRestoredModel(final ModelType model, final SnapshotModelType modelSnapshot)
	{
		trxManager.assertThreadInheritedTrxExists();

		InterfaceWrapperHelper.save(model, ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * Restore model's values from snapshot values.
	 * <p>
	 * NOTE: when this method is called, both model and modelSnapshot are not null.
	 */
	private void restoreModelValuesFromSnapshot(final ModelType model, final SnapshotModelType modelSnapshot)
	{
		InterfaceWrapperHelper.copy()
				.setFrom(modelSnapshot)
				.setTo(model)
				.setSkipCalculatedColumns(false) // copy everything
				.copy();
	}

	/**
	 * Restore model when it snapshot is missing because the model was created after the snapshot was taken.
	 * <p>
	 * Suggestion for implementors: maybe in this case you want to delete the model or you want to set it's relevant values to ZERO/<code>null</code>.
	 */
	protected abstract void restoreModelWhenSnapshotIsMissing(final ModelType model);

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
	 * @return Model's ID from given snapshot
	 */
	protected abstract int getModelId(final SnapshotModelType modelSnapshot);

	/**
	 * @return model retrieved using {@link #getModelId(Object)}.
	 */
	protected abstract ModelType getModel(final SnapshotModelType modelSnapshot);

	/**
	 * @return "Model ID" to ModelSnapshot map
	 */
	protected abstract Map<Integer, SnapshotModelType> retrieveModelSnapshotsByParent(final ParentModelType parentModel);

	/**
	 * @return "Model ID" to Model map
	 */
	protected abstract Map<Integer, ModelType> retrieveModelsByParent(final ParentModelType parentModel);

	protected final <T> IQueryBuilder<T> query(final Class<T> modelClass)
	{
		return queryBL.createQueryBuilder(modelClass, getContext());
	}

	protected final BooleanWithReason checkHasChanges(@Nullable final ModelType model, @Nullable final SnapshotModelType modelSnapshot)
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
			// => model was deleted
			else
			{
				return BooleanWithReason.trueBecause("model was deleted");
			}
		}
		else
		{
			//
			// Case: we have a model but we don't have a snapshot
			// => model was created after our snapshot
			if (modelSnapshot == null)
			{
				return BooleanWithReason.trueBecause("model was created after our snapshot");
			}
			//
			// Case: we have a model and we also have a snapshot
			// => we need to restore it
			else
			{
				final BooleanWithReason hasChanges = computeHasChanges(model, modelSnapshot);
				if (hasChanges.isTrue())
				{
					return hasChanges;
				}
			}
		}

		return computeChildrenHasChanges(model);
	}

	protected abstract BooleanWithReason computeHasChanges(@NonNull ModelType model, @NonNull SnapshotModelType modelSnapshot);

	protected abstract BooleanWithReason computeChildrenHasChanges(@NonNull ModelType model);

	protected final BooleanWithReason computeHasChangesByParent(@NonNull ParentModelType parentModel)
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

			final BooleanWithReason hasChanges = computeHasChanges(model, modelSnapshot);
			if (hasChanges.isTrue())
			{
				return hasChanges;
			}
		}

		// Iterate remaining models and delete/inactivate them.
		// NOTE: those are the models which were created after our snapshot.
		for (final ModelType model : models.values())
		{
			final SnapshotModelType modelSnapshot = null; // no snapshot
			final BooleanWithReason hasChanges = computeHasChanges(model, modelSnapshot);
			if (hasChanges.isTrue())
			{
				return hasChanges;
			}
		}

		return BooleanWithReason.FALSE;
	}
}
