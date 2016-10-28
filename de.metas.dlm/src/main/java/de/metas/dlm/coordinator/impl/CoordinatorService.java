package de.metas.dlm.coordinator.impl;

import java.util.ArrayList;
import java.util.List;

import de.metas.dlm.Partition;
import de.metas.dlm.coordinator.ICoordinatorService;
import de.metas.dlm.coordinator.IRecordInspector;
import de.metas.dlm.model.IDLMAware;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class CoordinatorService implements ICoordinatorService
{

	@Override
	public void validatePartition(Partition partition)
	{
		int minLevel = Integer.MAX_VALUE;

		final List<IDLMAware> records = partition.getRecords();

		outerForLoop: for (final IDLMAware record : records)
		{
			for (final IRecordInspector inspector : inspectors)
			{
				if (inspector.isApplicableFor(record))
				{
					minLevel = Math.min(minLevel, inspector.inspectRecord(record));
					if (minLevel <= 0)
					{
						break outerForLoop; // we are done, because we know that 0 is the absolute minimum
					}
				}
			}
		}

		//partition.setTargetDLMLevel(minLevel);
	}

	private final List<IRecordInspector> inspectors = new ArrayList<>();

	@Override
	public void registerInspector(IRecordInspector recordInspector)
	{
		inspectors.add(recordInspector);
	}

}
