package de.metas.dlm;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.lang.ITableRecordReference;

import com.google.common.collect.ImmutableMap;

import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_DLM_Partition_Workqueue;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.config.PartitionConfig;

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

/**
 * A partition is a set of {@link IDLMAware} records that can be DLM'ed. Each an {@link IDLMAware} is only part of one partition.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public class Partition
{
	private final Map<String, Collection<ITableRecordReference>> records;

	private final boolean recordsChanged;

	/**
	 * the method is final, but as of now, the config is *not* immutable.
	 */
	private final PartitionConfig config;

	private final boolean configChanged;

	private final int targetDLMLevel;

	private final int currentDLMLevel;

	private final Timestamp nextInspectionDate;

	private final int DLM_Partition_ID;

	private final boolean complete;

	private final boolean aborted;

	/**
	 * Can be used to create a new instance when records were just be discovered and still need to be saved.
	 *
	 * The new instance's {@link #isRecordsChanged()} method will return <code>true</code>.
	 *
	 * @param config
	 * @param records
	 * @return
	 */

	/**
	 * Can be used to create a new instance when data was loaded from the DB.
	 *
	 * The new instance's {@link #isRecordsChanged()} method will return <code>false</code>.
	 *
	 * @param config
	 * @param records
	 * @param currentDLMLevel
	 * @param targetDLMLevel
	 * @param nextInspectionDate
	 * @param DLM_Partition_ID
	 * @return
	 */
	public static Partition loadedPartition(final PartitionConfig config,
			final Map<String, Collection<ITableRecordReference>> records,
			final boolean complete,
			final int currentDLMLevel,
			final int targetDLMLevel,
			final Timestamp nextInspectionDate,
			final int DLM_Partition_ID)
	{
		final boolean configChanged = false;
		final boolean recordsChanged = false;
		final boolean aborted = false; // a partitin can only be "aborted" while new records are added to it
		return new Partition(config, configChanged, records, recordsChanged, complete, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID, aborted);
	}

	private Partition(
			final PartitionConfig config,
			final boolean configChanged,
			final Map<String, Collection<ITableRecordReference>> records,
			final boolean recordsChanged,
			final boolean complete,
			final int currentDLMLevel,
			final int targetDLMLevel,
			final Timestamp nextInspectionDate,
			final int DLM_Partition_ID,
			final boolean aborted)
	{
		this.config = config;
		this.configChanged = configChanged;

		this.records = ImmutableMap.copyOf(records); // will fail if the given 'records' or any of its elements is null
		this.recordsChanged = recordsChanged;

		this.complete = complete;

		this.currentDLMLevel = currentDLMLevel;
		this.targetDLMLevel = targetDLMLevel;
		this.nextInspectionDate = nextInspectionDate;
		this.DLM_Partition_ID = DLM_Partition_ID;

		this.aborted = aborted;
	}

	/**
	 * Creates a new instance with config, records and recordsChanged taken from this instance, and the given <code>targetDLMLevel</code>.
	 *
	 * @param targetDLMLevel
	 * @return
	 */
	public Partition withTargetDLMLevel(final int targetDLMLevel)
	{
		return new Partition(config, configChanged, records, recordsChanged, complete, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID, aborted);
	}

	public Partition withCurrentDLMLevel(final int currentDLMLevel)
	{
		return new Partition(config, configChanged, records, recordsChanged, complete, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID, aborted);
	}

	/**
	 * Creates a new instance with config etc taken from this instance, and the given <code>records</code>.
	 * The new instance's {@link #isRecordsChanged()} method will return <code>true</code>.
	 *
	 * @param records
	 * @return
	 */
	public Partition withRecords(final Map<String, Collection<ITableRecordReference>> records)
	{
		final boolean recordsChanged = true;
		return new Partition(config, configChanged, records, recordsChanged, complete, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID, aborted);
	}

	public Partition withNextInspectionDate(final Timestamp nextInspectionDate)
	{
		return new Partition(config, configChanged, records, recordsChanged, complete, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID, aborted);
	}

	public Partition withConfig(final PartitionConfig config)
	{
		final boolean configChanged = true;
		return new Partition(config, configChanged, records, recordsChanged, complete, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID, aborted);
	}

	/**
	 * Use this method if the underlying data was just stored.
	 *
	 * @param DLM_Partition_ID
	 * @return
	 */
	public Partition withJustStored(final int DLM_Partition_ID)
	{
		final boolean configChanged = false;
		final boolean recordsChanged = false;

		return new Partition(config, configChanged, records, recordsChanged, complete, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID, aborted);
	}

	public Partition withComplete(final boolean complete)
	{
		return new Partition(config, configChanged, records, recordsChanged, complete, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID, aborted);
	}

	public Partition withDLM_Partition_ID(final int DLM_Partition_ID)
	{
		return new Partition(config, configChanged, records, recordsChanged, complete, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID, aborted);
	}

	public Partition withAborted(final boolean aborted)
	{
		return new Partition(config, configChanged, records, recordsChanged, complete, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID, aborted);
	}

	/**
	 * Creates a new "empty" partition instance.
	 */
	public Partition()
	{
		this(
				PartitionConfig.builder().build(),
				false,
				Collections.emptyMap(),
				false,
				true, // complete
				IMigratorService.DLM_Level_NOT_SET,
				IMigratorService.DLM_Level_NOT_SET,
				null,
				0,
				false);
	}

	/**
	 * Gets the records that were added via {@link #withRecords(Map)} as a list.
	 * <p>
	 * <b>IMPORTANT:</b> the records of this partition are <b>not</b> loaded from DB via {@link IPartitionerService#loadPartition(de.metas.dlm.model.I_DLM_Partition)}.
	 * There might be too many records on disk.
	 *
	 * @return the records of this partition.
	 */
	public List<ITableRecordReference> getRecordsFlat()
	{
		return records.values().stream().flatMap(v -> v.stream()).collect(Collectors.toList());
	}

	/**
	 * Gets the records that were added via {@link #withRecords(Map)}.
	 * <p>
	 * <b>IMPORTANT:</b> please see the note at {@link #getRecordsFlat()}.
	 *
	 * @return the records of this partition.
	 */
	public Map<String, Collection<ITableRecordReference>> getRecords()
	{
		return records;
	}

	public Collection<ITableRecordReference> getRecordsWithTable(final String referencedTableName)
	{
		return records.getOrDefault(referencedTableName, Collections.emptyList());
	}

	public boolean isRecordsChanged()
	{
		return recordsChanged;
	}

	public boolean isComplete()
	{
		return complete;
	}

	public PartitionConfig getConfig()
	{
		return config;
	}

	public boolean isConfigChanged()
	{
		return configChanged;
	}

	public int getTargetDLMLevel()
	{
		return targetDLMLevel;
	}

	public int getCurrentDLMLevel()
	{
		return currentDLMLevel;
	}

	public Timestamp getNextInspectionDate()
	{
		return nextInspectionDate;
	}

	public int getDLM_Partition_ID()
	{
		return DLM_Partition_ID;
	}

	public boolean isAborted()
	{
		return aborted;
	}

	@Override
	public String toString()
	{
		return "Partition [DLM_Partition_ID=" + DLM_Partition_ID + ", records.size()=" + records.size() + ", recordsChanged=" + recordsChanged + ", configChanged=" + configChanged + ", targetDLMLevel=" + targetDLMLevel + ", currentDLMLevel=" + currentDLMLevel + ", nextInspectionDate=" + nextInspectionDate + "]";
	}

	public static class WorkQueue
	{
		public static WorkQueue of(final I_DLM_Partition_Workqueue workqueueDB)
		{
			final ITableRecordReference tableRecordRef = ITableRecordReference.FromReferencedModelConverter.convert(workqueueDB);

			final WorkQueue result = new WorkQueue(tableRecordRef);
			result.setDLM_Partition_Workqueue_ID(workqueueDB.getDLM_Partition_Workqueue_ID());
			return result;
		}

		public static WorkQueue of(final ITableRecordReference tableRecordRef)
		{
			return new WorkQueue(tableRecordRef);
		}

		private final ITableRecordReference tableRecordReference;

		private int dlmPartitionWorkqueueId;

		private WorkQueue(final ITableRecordReference tableRecordReference)
		{
			this.tableRecordReference = tableRecordReference;
		}

		public ITableRecordReference getTableRecordReference()
		{
			return tableRecordReference;
		}

		public int getDLM_Partition_Workqueue_ID()
		{
			return dlmPartitionWorkqueueId;

		}

		public void setDLM_Partition_Workqueue_ID(final int dlm_Partition_Workqueue_ID)
		{
			dlmPartitionWorkqueueId = dlm_Partition_Workqueue_ID;
		}

		@Override
		public String toString()
		{
			return "Partition.WorkQueue [DLM_Partition_Workqueue_ID=" + dlmPartitionWorkqueueId + ", tableRecordReference=" + tableRecordReference + "]";
		}
	}
}
