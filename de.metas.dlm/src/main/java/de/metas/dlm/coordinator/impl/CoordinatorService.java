package de.metas.dlm.coordinator.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

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

		final List<ITableRecordReference> records = partition.getRecords();

		outerForLoop: for (final ITableRecordReference recordReference : records)
		{
			for (final IRecordInspector inspector : inspectors)
			{
				final IDLMAware record = recordReference.getModel(new PlainContextAware(Env.getCtx()), IDLMAware.class);

				if (inspector.isApplicableFor(record))
				{
					minLevel = Math.min(minLevel, inspector.inspectRecord(record));
					if (minLevel <= IMigratorService.DLM_Level_LIVE)
					{
						break outerForLoop; // we are done, because we know that 0 is the absolute minimum
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
