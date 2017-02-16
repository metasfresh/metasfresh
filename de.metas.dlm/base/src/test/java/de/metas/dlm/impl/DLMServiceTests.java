package de.metas.dlm.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_ChangeLog;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.dlm.Partition;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.model.I_DLM_Partition_Record_V;
import de.metas.dlm.partitioner.config.TableReferenceDescriptor;
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

	/**
	 * tests {@link DLMService#retrieveTableRecordReferences()}
	 * Creates two <code>AD_ChangeLog</code> records, one referencing an <code>AD_Element</code>, the other one referencing an <code>AD_Field</code>.
	 * Then verifies that the map returned by the method under test contains two entries. One for <code>AD_Element</code>, one for <code>AD_Field</code>.
	 */
	@Test
	public void testRetrieveTableRecordReferences()
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final I_AD_Element referencedElement = InterfaceWrapperHelper.newInstance(I_AD_Element.class);
		InterfaceWrapperHelper.save(referencedElement);
		final int adElementTableID = adTableDAO.retrieveTableId(I_AD_Element.Table_Name);

		final I_AD_Field referencedField = InterfaceWrapperHelper.newInstance(I_AD_Field.class);
		InterfaceWrapperHelper.save(referencedField);
		final int adfieldTableID = adTableDAO.retrieveTableId(I_AD_Field.Table_Name);

		final I_AD_ChangeLog referencingChangeLog = InterfaceWrapperHelper.newInstance(I_AD_ChangeLog.class);
		referencingChangeLog.setAD_Table_ID(adElementTableID);
		referencingChangeLog.setRecord_ID(referencedElement.getAD_Element_ID());
		InterfaceWrapperHelper.save(referencingChangeLog);

		final I_AD_ChangeLog referencingChangeLog2 = InterfaceWrapperHelper.newInstance(I_AD_ChangeLog.class);
		referencingChangeLog2.setAD_Table_ID(adfieldTableID);
		referencingChangeLog2.setRecord_ID(referencedField.getAD_Field_ID());
		InterfaceWrapperHelper.save(referencingChangeLog2);

		final int adChangeLogTableID = adTableDAO.retrieveTableId(I_AD_ChangeLog.Table_Name);

		// for the unit test need to explicitly create AD_Column records for the two AD_Changelog properties
		{
			final I_AD_Column recordIDColumn = InterfaceWrapperHelper.newInstance(I_AD_Column.class);
			recordIDColumn.setColumnName(I_AD_ChangeLog.COLUMNNAME_Record_ID);
			recordIDColumn.setAD_Table_ID(adChangeLogTableID);
			InterfaceWrapperHelper.save(recordIDColumn);

			final I_AD_Column tableIDIDColumn = InterfaceWrapperHelper.newInstance(I_AD_Column.class);
			tableIDIDColumn.setColumnName(I_AD_ChangeLog.COLUMNNAME_AD_Table_ID);
			tableIDIDColumn.setAD_Table_ID(adChangeLogTableID);
			InterfaceWrapperHelper.save(tableIDIDColumn);
		}

		final List<TableReferenceDescriptor> tableRecordReferences = dlmService.retrieveTableRecordReferences();

		// assert that there are two records, one about AD_Fleid, one about AD_Element
		assertThat(tableRecordReferences.size(), is(2));
		assertThat(tableRecordReferences.stream().anyMatch(d -> I_AD_Element.Table_Name.equals(d.getReferencedTableName())), is(true));
		assertThat(tableRecordReferences.stream().anyMatch(d -> I_AD_Field.Table_Name.equals(d.getReferencedTableName())), is(true));

		final TableReferenceDescriptor adElementReferenceDescriptor = tableRecordReferences.stream().filter(d -> I_AD_Element.Table_Name.equals(d.getReferencedTableName())).findFirst().get();
		assertThat(adElementReferenceDescriptor.getReferencingTableName(), is(I_AD_ChangeLog.Table_Name));
		assertThat(adElementReferenceDescriptor.getReferencingColumnName(), is(I_AD_ChangeLog.COLUMNNAME_Record_ID));

		final TableReferenceDescriptor adFieldReferenceDescriptor = tableRecordReferences.stream().filter(d -> I_AD_Field.Table_Name.equals(d.getReferencedTableName())).findFirst().get();
		assertThat(adFieldReferenceDescriptor.getReferencingTableName(), is(I_AD_ChangeLog.Table_Name));
		assertThat(adFieldReferenceDescriptor.getReferencingColumnName(), is(I_AD_ChangeLog.COLUMNNAME_Record_ID));
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
