package de.metas.dlm;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.lang.ITableRecordReference;

import com.google.common.collect.ImmutableList;

import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.config.PartitionerConfig;

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
	private final List<ITableRecordReference> records;

	private final boolean recordsChanged;

	/**
	 * the method is final, but as of now, the config is *not* immutable.
	 */
	private final PartitionerConfig config;

	private final boolean configChanged;

	private final int targetDLMLevel;

	private final int currentDLMLevel;

	private final Timestamp nextInspectionDate;

	private final int DLM_Partition_ID;

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
	 * @param targetDLMLevel
	 * @return
	 */
	public static Partition loadedPartition(final PartitionerConfig config,
			final Collection<ITableRecordReference> records,
			final int currentDLMLevel,
			final int targetDLMLevel,
			final Timestamp nextInspectionDate,
			final int DLM_Partition_ID)
	{
		final boolean configChanged = false;
		final boolean recordsChanged = false;
		return new Partition(config, configChanged, records, recordsChanged, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID);
	}

	private Partition(
			final PartitionerConfig config,
			final boolean configChanged,
			final Collection<ITableRecordReference> records,
			final boolean recordsChanged,
			final int currentDLMLevel,
			final int targetDLMLevel,
			final Timestamp nextInspectionDate,
			final int DLM_Partition_ID)
	{
		this.config = config;
		this.configChanged = configChanged;

		this.records = ImmutableList.copyOf(records); // will fail if the given 'records' or any of its elements is null
		this.recordsChanged = recordsChanged;

		this.currentDLMLevel = currentDLMLevel;
		this.targetDLMLevel = targetDLMLevel;
		this.nextInspectionDate = nextInspectionDate;
		this.DLM_Partition_ID = DLM_Partition_ID;
	}

	/**
	 * Creates a new instance with config, records and recordsChanged taken from this instance, and the given <code>targetDLMLevel</code>.
	 *
	 * @param targetDLMLevel
	 * @return
	 */
	public Partition withTargetDLMLevel(final int targetDLMLevel)
	{
		return new Partition(config, configChanged, records, recordsChanged, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID);
	}

	public Partition withCurrentDLMLevel(final int currentDLMLevel)
	{
		return new Partition(config, configChanged, records, recordsChanged, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID);
	}

	/**
	 * Creates a new instance with config etc taken from this instance, and the given <code>records</code>.
	 * The new instance's {@link #isRecordsChanged()} method will return <code>true</code>.
	 *
	 * @param records
	 * @return
	 */
	public Partition withRecords(final Collection<ITableRecordReference> records)
	{
		final boolean recordsChanged = true;
		return new Partition(config, configChanged, records, recordsChanged, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID);
	}

	public Partition withNextInspectionDate(final Timestamp nextInspectionDate)
	{
		return new Partition(config, configChanged, records, recordsChanged, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID);
	}

	public Partition withConfig(final PartitionerConfig config)
	{
		final boolean configChanged = true;
		return new Partition(config, configChanged, records, recordsChanged, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID);
	}

	/**
	 * Us this method if the underlying data was just stored.
	 *
	 * @param DLM_Partition_ID
	 * @return
	 */
	public Partition withJustStored(final int DLM_Partition_ID)
	{
		final boolean configChanged = false;
		final boolean recordsChanged = false;
		return new Partition(config, configChanged, records, recordsChanged, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID);
	}

	public Partition withDLM_Partition_ID(int DLM_Partition_ID)
	{
		return new Partition(config, configChanged, records, recordsChanged, currentDLMLevel, targetDLMLevel, nextInspectionDate, DLM_Partition_ID);
	}

	/**
	 * Creates a new "empty" partition instance.
	 */
	public Partition()
	{
		this(PartitionerConfig.builder().build(),
				false,
				Collections.emptyList(),
				false,
				IMigratorService.DLM_Level_NOT_SET,
				IMigratorService.DLM_Level_NOT_SET,
				null,
				0);
	}

	/**
	 *
	 * @return the records of this partition. In order to be sure to have <i>all</i> records for this partition, invoke {@link IPartitionerService#loadPartition(de.metas.dlm.model.I_DLM_Partition)}.
	 */
	public List<ITableRecordReference> getRecords()
	{
		return records;
	}

	public boolean isRecordsChanged()
	{
		return recordsChanged;
	}

	public PartitionerConfig getConfig()
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

	@Override
	public String toString()
	{
		return "Partition [records.size()=" + records.size() + ", recordsChanged=" + recordsChanged + ", configChanged=" + configChanged + ", targetDLMLevel=" + targetDLMLevel + ", currentDLMLevel=" + currentDLMLevel + ", nextInspectionDate=" + nextInspectionDate + "]";
	}

	public int getDLM_Partition_ID()
	{
		return DLM_Partition_ID;
	}
}
