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


import java.util.Date;

import org.adempiere.util.lang.IContextAware;

/**
 * Implementations of the interface are able to create snapshots and restore from snapshots a particular model.
 * 
 * @author tsa
 *
 * @param <ModelType> model type which this implementation will be able to restore or to create snapshots
 * @param <SnapshotModelType> model type/table where the snapshot data is stored
 * @param <ParentModelType> parent model type (in snapshots hierachy)
 */
public interface ISnapshotHandler<ModelType, SnapshotModelType, ParentModelType>
{
	/**
	 * Sets context to be used.
	 * 
	 * @param context
	 */
	ISnapshotHandler<ModelType, SnapshotModelType, ParentModelType> setContext(IContextAware context);

	/**
	 * Sets transaction date to be used in case any kind of transactions are created.
	 * Mainly this is the document date which triggered the snapshot.
	 * 
	 * @param dateTrx
	 */
	ISnapshotHandler<ModelType, SnapshotModelType, ParentModelType> setDateTrx(Date dateTrx);

	/**
	 * Optionally, Set a referenced model to be used by transactions which would be created during the model restoration.
	 * 
	 * Mainly the referenced model is a document or a document line on which the transactions are linking to.
	 * 
	 * @param referencedModel may be {@code null}
	 * @return
	 */
	ISnapshotHandler<ModelType, SnapshotModelType, ParentModelType> setReferencedModel(Object referencedModel);

	/**
	 * Sets the Snapshot ID to be used. A Snapshot ID is mandatory.
	 * 
	 * @param snapshotId may not be {@code null} or empty.
	 */
	ISnapshotHandler<ModelType, SnapshotModelType, ParentModelType> setSnapshotId(String snapshotId);

	/**
	 * @return snapshot ID; never <code>null</code> or empty
	 */
	String getSnapshotId();
}
