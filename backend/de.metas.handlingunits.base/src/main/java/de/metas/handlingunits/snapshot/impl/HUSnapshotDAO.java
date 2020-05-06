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


import java.util.UUID;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.snapshot.ISnapshotProducer;
import de.metas.handlingunits.snapshot.ISnapshotRestorer;

public class HUSnapshotDAO implements IHUSnapshotDAO
{
	private final String createSnapshotId()
	{
		return UUID.randomUUID().toString();
	}

	@Override
	public ISnapshotProducer<I_M_HU> createSnapshot()
	{
		final M_HU_Snapshot_ProducerAndRestorer snapshotProducer = new M_HU_Snapshot_ProducerAndRestorer();

		//
		// Create a new Snapshot ID
		final String snapshotId = createSnapshotId();
		snapshotProducer.setSnapshotId(snapshotId);

		return snapshotProducer;
	}

	@Override
	public ISnapshotRestorer<I_M_HU> restoreHUs()
	{
		return new M_HU_Snapshot_ProducerAndRestorer();
	}

}
