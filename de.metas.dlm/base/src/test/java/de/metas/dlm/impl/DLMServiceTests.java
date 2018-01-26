package de.metas.dlm.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Column;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.dlm.Partition;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.model.I_DLM_Partition_Record_V;
import de.metas.logging.LogManager;

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

public class DLMServiceTests
{
	private final DLMService dlmService = new DLMService(); // this is the class under test

	@Before
	public void before()
	{
		AdempiereTestHelper.get().init();
		LogManager.setLevel(Level.DEBUG);
	}

	@Test
	public void testLoadEmptyPartition()
	{
		final I_DLM_Partition partitionDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition.class);
		InterfaceWrapperHelper.save(partitionDB);

		final Partition partition = dlmService.loadPartition(partitionDB);
		assertNotNull(partition);

		assertThat(partition.getDLM_Partition_ID(), is(partitionDB.getDLM_Partition_ID()));
	}

	/**
	 * Verifies that {@link PartitionerServiceOld#loadPartition(I_DLM_Partition)} can load {@link IDLMAware}s that references a given partition via their {@link I_DLM_Partition_Record}s.
	 */
	@Test
	public void testLoadPartition()
	{
		final I_DLM_Partition partitionDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition.class);
		InterfaceWrapperHelper.save(partitionDB);

		//
		// create an AD_Table record and have it reference the0 partitionDB via a I_DLM_Partition_Record
		final I_AD_Table table = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		InterfaceWrapperHelper.save(table);

		final I_DLM_Partition_Record_V tablePartitionRecord = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Record_V.class);
		tablePartitionRecord.setDLM_Partition_ID(partitionDB.getDLM_Partition_ID());
		tablePartitionRecord.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_AD_Table.class));
		tablePartitionRecord.setRecord_ID(table.getAD_Table_ID());
		InterfaceWrapperHelper.save(tablePartitionRecord);

		//
		// create an AD_Column record and have it reference the0 partitionDB via another I_DLM_Partition_Record
		final I_AD_Column column = InterfaceWrapperHelper.newInstance(I_AD_Column.class);
		InterfaceWrapperHelper.save(column);

		final I_DLM_Partition_Record_V columnPartitionRecord = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Record_V.class);
		columnPartitionRecord.setDLM_Partition_ID(partitionDB.getDLM_Partition_ID());
		columnPartitionRecord.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_AD_Column.class));
		columnPartitionRecord.setRecord_ID(column.getAD_Column_ID());
		InterfaceWrapperHelper.save(columnPartitionRecord);

		// method under test
		final Partition partition = dlmService.loadPartition(partitionDB);

		assertNotNull(partition);
		assertThat(partition.getDLM_Partition_ID(), is(partitionDB.getDLM_Partition_ID()));

		// we do not attempt to load partitioned records anymore, because there might be too many
		assertThat(partition.getRecords().isEmpty(), is(true));
		// assertThat(partition.getRecordsFlat().size(), is(2));

		// verify that it's not the I_DLM_Partition_Records we got back, but the AD_table record and the AD_column record.
		// assertThat(partition.getRecordsFlat().stream().anyMatch(i -> InterfaceWrapperHelper.getModelTableName(i).equals(org.compiere.model.I_AD_Table.Table_Name)), is(true));
		// assertThat(partition.getRecordsFlat().stream().anyMatch(i -> InterfaceWrapperHelper.getModelTableName(i).equals(I_AD_Column.Table_Name)), is(true));
	}
}
