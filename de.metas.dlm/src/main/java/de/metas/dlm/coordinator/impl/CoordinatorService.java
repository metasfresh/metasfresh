package de.metas.dlm.coordinator.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;

import de.metas.dlm.IDLMService;
import de.metas.dlm.Partition;
import de.metas.dlm.coordinator.ICoordinatorService;
import de.metas.dlm.coordinator.IRecordInspector;
import de.metas.dlm.migrator.IMigratorService;
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
	public Partition inspectPartition(final Partition partition)
	{
		int minLevel = Integer.MAX_VALUE;

		final PlainContextAware ctxAware = PlainContextAware.newWithThreadInheritedTrx();

		final List<Iterator<IDLMAware>> dlmAwareIterators = Services.get(IDLMService.class)
				.retrieveDLMTableNames(ctxAware, partition.getDLM_Partition_ID())
				.map(builder -> builder.create().iterate(IDLMAware.class))
				.collect(Collectors.toList());

		outerForLoop: for (final Iterator<IDLMAware> iterator : dlmAwareIterators)
		{
			while (iterator.hasNext())
			{
				final IDLMAware record = iterator.next();
				for (final IRecordInspector inspector : inspectors)
				{
					if (!inspector.isApplicableFor(record))
					{
						continue;
					}
					minLevel = Math.min(minLevel, inspector.inspectRecord(record));
					if (minLevel <= IMigratorService.DLM_Level_LIVE)
					{
						break outerForLoop; // we are done searching the minimum because we know that "live" is the absolute minimum
					}
				}
			}
		}

		final Partition partitioneWithTargetDLMLevel;
		if (minLevel != partition.getTargetDLMLevel())
		{
			// a new partition with the different target level
			partitioneWithTargetDLMLevel = partition.withTargetDLMLevel(minLevel);
		}
		else
		{
			// the same partition
			partitioneWithTargetDLMLevel = partition;
		}

		final Timestamp nextInspectionDate = findNextInspectionDate(partitioneWithTargetDLMLevel);
		return partitioneWithTargetDLMLevel.withNextInspectionDate(nextInspectionDate);
	}

	private Timestamp findNextInspectionDate(final Partition partition)
	{
		if (partition.getTargetDLMLevel() == IMigratorService.DLM_Level_LIVE)
		{
			final Timestamp nextWeek = TimeUtil.addWeeks(SystemTime.asTimestamp(), 1);
			return nextWeek;
		}

		return null;
	}

	private final List<IRecordInspector> inspectors = new ArrayList<>();

	@Override
	public void registerInspector(final IRecordInspector recordInspector)
	{
		inspectors.add(recordInspector);
	}

}
