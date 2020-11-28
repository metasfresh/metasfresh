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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Map;
import java.util.Set;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Snapshot;

class M_HU_Item_SnapshotHandler extends AbstractSnapshotHandler<I_M_HU_Item, I_M_HU_Item_Snapshot, I_M_HU>
{
	M_HU_Item_SnapshotHandler(final AbstractSnapshotHandler<?, ?, ?> parentHandler)
	{
		super(parentHandler);
	}

	@Override
	protected void createSnapshotsByParentIds(final Set<Integer> huIds)
	{
		query(I_M_HU_Item.class)
				.addInArrayOrAllFilter(I_M_HU_Item.COLUMN_M_HU_ID, huIds)
				.create()
				.insertDirectlyInto(I_M_HU_Item_Snapshot.class)
				.mapCommonColumns()
				.mapColumnToConstant(I_M_HU_Item_Snapshot.COLUMNNAME_Snapshot_UUID, getSnapshotId())
				.execute();
	}

	@Override
	protected Map<Integer, I_M_HU_Item_Snapshot> retrieveModelSnapshotsByParent(final I_M_HU hu)
	{
		return query(I_M_HU_Item_Snapshot.class)
				.addEqualsFilter(I_M_HU_Item_Snapshot.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.addEqualsFilter(I_M_HU_Item_Snapshot.COLUMN_Snapshot_UUID, getSnapshotId())
				.create()
				.map(I_M_HU_Item_Snapshot.class, snapshot2ModelIdFunction);
	}

	@Override
	protected Map<Integer, I_M_HU_Item> retrieveModelsByParent(final I_M_HU hu)
	{
		return query(I_M_HU_Item.class)
				.addEqualsFilter(I_M_HU_Item.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.create()
				.mapById(I_M_HU_Item.class);
	}

	@Override
	protected I_M_HU_Item_Snapshot retrieveModelSnapshot(final I_M_HU_Item huItem)
	{
		return query(I_M_HU_Item_Snapshot.class)
				.addEqualsFilter(I_M_HU_Item_Snapshot.COLUMN_M_HU_Item_ID, huItem.getM_HU_Item_ID())
				.addEqualsFilter(I_M_HU_Item_Snapshot.COLUMN_Snapshot_UUID, getSnapshotId())
				.create()
				.firstOnlyNotNull(I_M_HU_Item_Snapshot.class);
	}

	@Override
	protected void restoreChildrenFromSnapshots(I_M_HU_Item huItem)
	{
		final M_HU_SnapshotHandler includedHUSnapshotHandler = new M_HU_SnapshotHandler(this);
		includedHUSnapshotHandler.restoreModelsFromSnapshotsByParent(huItem);

		final M_HU_Item_Storage_SnapshotHandler huItemStorageSnapshotHandler = new M_HU_Item_Storage_SnapshotHandler(this);
		huItemStorageSnapshotHandler.restoreModelsFromSnapshotsByParent(huItem);
	}

	@Override
	protected void restoreModelWhenSnapshotIsMissing(final I_M_HU_Item model)
	{
		// shall not happen because we are NEVER deleting an HU Item
		throw new HUException("Cannot restore " + model + " because snapshot is missing");
	}

	@Override
	protected int getModelId(final I_M_HU_Item_Snapshot modelSnapshot)
	{
		return modelSnapshot.getM_HU_Item_ID();
	}

	@Override
	protected I_M_HU_Item getModel(I_M_HU_Item_Snapshot modelSnapshot)
	{
		return modelSnapshot.getM_HU_Item();
	}
}
