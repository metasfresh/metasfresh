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
import de.metas.i18n.BooleanWithReason;
import lombok.NonNull;

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
	protected BooleanWithReason computeHasChanges(@NonNull final I_M_HU_Item model, @NonNull final I_M_HU_Item_Snapshot modelSnapshot)
	{
		if(model.getQty().compareTo(modelSnapshot.getQty()) != 0)
		{
			return BooleanWithReason.trueBecause("M_HU_Item.Qty changed");
		}
		else
		{
			return BooleanWithReason.FALSE;
		}
	}

	@Override
	protected BooleanWithReason computeChildrenHasChanges(@NonNull final I_M_HU_Item huItem)
	{
		return childHandlers().computeHasChangesByParent(huItem);
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

	private CompositeSnapshotHandlers<I_M_HU_Item> childHandlers()
	{
		return CompositeSnapshotHandlers.<I_M_HU_Item>builder()
				.handler(new M_HU_SnapshotHandler(this))
				.handler(new M_HU_Item_Storage_SnapshotHandler(this))
				.build();
	}

	@Override
	protected void restoreChildrenFromSnapshots(final I_M_HU_Item huItem)
	{
		childHandlers().restoreModelsFromSnapshotsByParent(huItem);
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
	protected I_M_HU_Item getModel(final I_M_HU_Item_Snapshot modelSnapshot)
	{
		return modelSnapshot.getM_HU_Item();
	}
}
