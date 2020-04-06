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

import org.adempiere.util.lang.IContextAware;

/**
 * Implementations of this interface are creating snapshots for a given model type.<br>
 * Use {@link IHUSnapshotDAO#createSnapshot()} to get an instance.
 * 
 * @author tsa
 *
 * @param <ModelType>
 */
public interface ISnapshotProducer<ModelType>
{
	/**
	 * Create snapshots (recursivelly) for all enqueued models.
	 * 
	 * After running this method, the models queue will be cleared so it's safe to add more models and then run again this method.
	 */
	ISnapshotProducer<ModelType> createSnapshots();

	/**
	 * Gets the snapshot ID which was used to create the snapshots.
	 * 
	 * NOTE: this method will never return null, even if there were no models to be snapshoted.
	 * 
	 * @return snapshot ID
	 */
	String getSnapshotId();

	/**
	 * Sets context to be used while doing the snapshots.
	 * 
	 * @param context
	 */
	ISnapshotProducer<ModelType> setContext(final IContextAware context);

	/**
	 * Add model to be snapshot-ed.
	 * 
	 * @param model
	 */
	ISnapshotProducer<ModelType> addModel(ModelType model);

	/**
	 * Add models to be snapshot-ed.
	 * 
	 * @param models
	 */
	ISnapshotProducer<ModelType> addModels(Collection<? extends ModelType> models);
}
