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


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Snapshot;

class M_HU_SnapshotHandler extends AbstractSnapshotHandler<I_M_HU, I_M_HU_Snapshot, I_M_HU_Item>
{
	public M_HU_SnapshotHandler()
	{
		this(null); // no parent handler, this is a top level handler
	}

	M_HU_SnapshotHandler(final AbstractSnapshotHandler<I_M_HU_Item, ?, ?> parentHandler)
	{
		super(parentHandler);
	}

	protected void createSnapshotsByIds(final Set<Integer> huIds)
	{
		query(I_M_HU.class)
				.addInArrayOrAllFilter(I_M_HU.COLUMN_M_HU_ID, huIds)
				.create()
				.insertDirectlyInto(I_M_HU_Snapshot.class)
				.mapCommonColumns()
				.mapColumnToConstant(I_M_HU_Snapshot.COLUMNNAME_Snapshot_UUID, getSnapshotId())
				.execute();
	}

	@Override
	protected void restoreChildrenFromSnapshots(final I_M_HU hu)
	{
		final M_HU_Item_SnapshotHandler huItemSnapshotHandler = new M_HU_Item_SnapshotHandler(this);
		huItemSnapshotHandler.restoreModelsFromSnapshotsByParent(hu);

		final M_HU_Storage_SnapshotHandler huStoargeSnapshotHandler = new M_HU_Storage_SnapshotHandler(this);
		huStoargeSnapshotHandler.restoreModelsFromSnapshotsByParent(hu);

		final M_HU_Attribute_SnapshotHandler huAttributesSnapshotHandler = new M_HU_Attribute_SnapshotHandler(this);
		huAttributesSnapshotHandler.restoreModelsFromSnapshotsByParent(hu);
	}

	@Override
	protected I_M_HU_Snapshot retrieveModelSnapshot(final I_M_HU hu)
	{
		return query(I_M_HU_Snapshot.class)
				.addEqualsFilter(I_M_HU_Snapshot.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.addEqualsFilter(I_M_HU_Snapshot.COLUMN_Snapshot_UUID, getSnapshotId())
				.create()
				.firstOnlyNotNull(I_M_HU_Snapshot.class);
	}

	@Override
	protected void restoreModelWhenSnapshotIsMissing(final I_M_HU model)
	{
		throw new HUException("Cannot restore " + model + " because snapshot is missing");
	}

	@Override
	protected int getModelId(final I_M_HU_Snapshot modelSnapshot)
	{
		return modelSnapshot.getM_HU_ID();
	}

	@Override
	protected I_M_HU getModel(final I_M_HU_Snapshot modelSnapshot)
	{
		return modelSnapshot.getM_HU();
	}

	@Override
	protected Map<Integer, I_M_HU_Snapshot> retrieveModelSnapshotsByParent(final I_M_HU_Item huItem)
	{
		return query(I_M_HU_Snapshot.class)
				.addEqualsFilter(I_M_HU_Snapshot.COLUMN_M_HU_Item_Parent_ID, huItem.getM_HU_Item_ID())
				.addEqualsFilter(I_M_HU_Snapshot.COLUMN_Snapshot_UUID, getSnapshotId())
				.create()
				.map(I_M_HU_Snapshot.class, snapshot2ModelIdFunction);
	}

	@Override
	protected Map<Integer, I_M_HU> retrieveModelsByParent(I_M_HU_Item huItem)
	{
		return query(I_M_HU.class)
				.addEqualsFilter(I_M_HU.COLUMN_M_HU_Item_Parent_ID, huItem.getM_HU_Item_ID())
				.create()
				.mapById(I_M_HU.class);
	}

	/**
	 * Recursivelly collect all M_HU_IDs and M_HU_Item_IDs starting from <code>startHUIds</code> to the bottom, including those too.
	 * 
	 * @param startHUIds
	 * @param huIdsCollector
	 * @param huItemIdsCollector
	 */
	protected final void collectHUAndItemIds(final Set<Integer> startHUIds, final Set<Integer> huIdsCollector, final Set<Integer> huItemIdsCollector)
	{
		Set<Integer> huIdsToCheck = new HashSet<>(startHUIds);
		while (!huIdsToCheck.isEmpty())
		{
			huIdsCollector.addAll(huIdsToCheck);
			final Set<Integer> huItemIds = retrieveM_HU_Item_Ids(huIdsToCheck);
			huItemIdsCollector.addAll(huItemIds);

			final Set<Integer> includedHUIds = retrieveIncludedM_HUIds(huItemIds);

			huIdsToCheck = new HashSet<>(includedHUIds);
			huIdsToCheck.removeAll(huIdsCollector);
		}
	}

	private final Set<Integer> retrieveM_HU_Item_Ids(final Set<Integer> huIds)
	{
		if (huIds.isEmpty())
		{
			return Collections.emptySet();
		}
		final List<Integer> huItemIdsList = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Item.class, getContext())
				.addInArrayOrAllFilter(I_M_HU_Item.COLUMN_M_HU_ID, huIds)
				.create()
				.listIds();
		return new HashSet<>(huItemIdsList);
	}

	private final Set<Integer> retrieveIncludedM_HUIds(final Set<Integer> huItemIds)
	{
		if (huItemIds.isEmpty())
		{
			return Collections.emptySet();
		}
		final List<Integer> huIdsList = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU.class, getContext())
				.addInArrayOrAllFilter(I_M_HU.COLUMN_M_HU_Item_Parent_ID, huItemIds)
				.create()
				.listIds();
		return new HashSet<>(huIdsList);
	}

}
