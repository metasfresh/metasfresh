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


import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.Check;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_HU_Storage_Snapshot;

class M_HU_Storage_SnapshotHandler extends AbstractSnapshotHandler<I_M_HU_Storage, I_M_HU_Storage_Snapshot, I_M_HU>
{
	M_HU_Storage_SnapshotHandler(final AbstractSnapshotHandler<I_M_HU, ?, ?> parentHandler)
	{
		super(parentHandler);
	}

	@Override
	protected void createSnapshotsByParentIds(final Set<Integer> huIds)
	{
		Check.assumeNotEmpty(huIds, "huIds not empty");

		query(I_M_HU_Storage.class)
				.addInArrayOrAllFilter(I_M_HU_Storage.COLUMN_M_HU_ID, huIds)
				.create()
				.insertDirectlyInto(I_M_HU_Storage_Snapshot.class)
				.mapCommonColumns()
				.mapColumnToConstant(I_M_HU_Storage_Snapshot.COLUMNNAME_Snapshot_UUID, getSnapshotId())
				.execute();
	}

	@Override
	protected Map<Integer, I_M_HU_Storage_Snapshot> retrieveModelSnapshotsByParent(final I_M_HU model)
	{
		return query(I_M_HU_Storage_Snapshot.class)
				.addEqualsFilter(I_M_HU_Storage_Snapshot.COLUMN_M_HU_ID, model.getM_HU_ID())
				.addEqualsFilter(I_M_HU_Storage_Snapshot.COLUMN_Snapshot_UUID, getSnapshotId())
				.create()
				.map(I_M_HU_Storage_Snapshot.class, snapshot2ModelIdFunction);
	}

	@Override
	protected final Map<Integer, I_M_HU_Storage> retrieveModelsByParent(final I_M_HU hu)
	{
		return query(I_M_HU_Storage.class)
				.addEqualsFilter(I_M_HU_Storage.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.create()
				.mapById(I_M_HU_Storage.class);
	}

	@Override
	protected I_M_HU_Storage_Snapshot retrieveModelSnapshot(final I_M_HU_Storage model)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected void restoreModelWhenSnapshotIsMissing(final I_M_HU_Storage model)
	{
		model.setQty(BigDecimal.ZERO);
	}

	@Override
	protected int getModelId(final I_M_HU_Storage_Snapshot modelSnapshot)
	{
		return modelSnapshot.getM_HU_Storage_ID();
	}

	@Override
	protected I_M_HU_Storage getModel(final I_M_HU_Storage_Snapshot modelSnapshot)
	{
		return modelSnapshot.getM_HU_Storage();
	}

}
