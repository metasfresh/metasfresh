package de.metas.handlingunits.snapshot;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collection;
import java.util.Date;

import org.adempiere.util.lang.IContextAware;

/**
 * Implementations of this interface are restoring models from snapshots, for a given model type.<br>
 * Use {@link IHUSnapshotDAO#restoreHUs()} to get an instance.
 * <p>
 * <b>Important:</b> all the setters below are mandatory.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public interface ISnapshotRestorer<ModelType>
{
	/**
	 * Restore all enqueued models (recursivelly) from given snapshot.
	 * 
	 * After running this method, the models queue will be cleared so it's safe to add more models and then run again this method.
	 */
	void restoreFromSnapshot();

	/**
	 * Sets the Snapshot ID to be used.
	 * 
	 * @param snapshotId
	 */
	ISnapshotRestorer<ModelType> setSnapshotId(String snapshotId);

	/**
	 * Sets context to be used while restoring the models from their snapshots.
	 * 
	 * @param context
	 */
	ISnapshotRestorer<ModelType> setContext(final IContextAware context);

	/**
	 * Sets transaction date to be used when creating restore transactions.
	 * 
	 * @param dateTrx
	 */
	ISnapshotRestorer<ModelType> setDateTrx(Date dateTrx);

	/**
	 * Sets referenced model to be used when creating restore transactions.
	 * 
	 * @param referencedModel
	 */
	ISnapshotRestorer<ModelType> setReferencedModel(Object referencedModel);

	/**
	 * Add model to be restored.
	 * 
	 * @param model
	 */
	ISnapshotRestorer<ModelType> addModel(ModelType model);

	/**
	 * Add models to be restored.
	 * 
	 * @param models
	 */
	ISnapshotRestorer<ModelType> addModels(Collection<? extends ModelType> models);

	ISnapshotRestorer<ModelType> addModelId(int modelId);
	
	ISnapshotRestorer<ModelType> addModelIds(Collection<Integer> modelIds);
}
