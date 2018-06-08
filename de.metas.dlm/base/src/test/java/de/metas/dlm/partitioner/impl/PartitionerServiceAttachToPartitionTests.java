package de.metas.dlm.partitioner.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_PInstance;
import org.junit.Before;
import org.junit.Test;

import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.dlm.Partition;
import de.metas.dlm.Partition.WorkQueue;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.model.I_DLM_Partition_Record_V;
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
 * This class tests with a number of records that a re always connected in the same manner (see {@link #setup()}).
 * The differences between the tests are about which records are partitioned and which records aren't.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PartitionerServiceAttachToPartitionTests
{
	private I_DLM_Partition partitionDB;
	private I_DLM_Partition partitionDB2;
	private I_AD_PInstance pinstance;
	private I_C_Queue_Block block;
	private I_C_Queue_WorkPackage workpackage;
	private I_C_Queue_Element element;
	private PartitionConfig config;

	@Before
	public void setup()
	{
		AdempiereTestHelper.get().init();

		partitionDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition.class);
		InterfaceWrapperHelper.save(partitionDB);

		partitionDB2 = InterfaceWrapperHelper.newInstance(I_DLM_Partition.class);
		InterfaceWrapperHelper.save(partitionDB2);

		pinstance = InterfaceWrapperHelper.newInstance(I_AD_PInstance.class);
		InterfaceWrapperHelper.save(pinstance);

		block = InterfaceWrapperHelper.newInstance(I_C_Queue_Block.class);
		block.setAD_PInstance_Creator(pinstance);
		InterfaceWrapperHelper.save(block);

		workpackage = InterfaceWrapperHelper.newInstance(I_C_Queue_WorkPackage.class);
		workpackage.setC_Queue_Block(block);
		InterfaceWrapperHelper.save(workpackage);

		element = InterfaceWrapperHelper.newInstance(I_C_Queue_Element.class);
		element.setC_Queue_WorkPackage(workpackage);
		InterfaceWrapperHelper.save(element);

		config = PartitionConfig.builder()
				.line(I_AD_PInstance.Table_Name)
				.line(I_C_Queue_Block.Table_Name)
				.ref().setReferencedTableName(I_AD_PInstance.Table_Name).setReferencingColumnName(I_C_Queue_Block.COLUMNNAME_AD_PInstance_Creator_ID).endRef()
				.line(I_C_Queue_WorkPackage.Table_Name)
				.ref().setReferencedTableName(I_C_Queue_Block.Table_Name).setReferencingColumnName(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_Block_ID).endRef()
				.line(I_C_Queue_Element.Table_Name)
				.ref().setReferencedTableName(I_C_Queue_WorkPackage.Table_Name).setReferencingColumnName(I_C_Queue_Element.COLUMNNAME_C_Queue_WorkPackage_ID).endRef()
				.endLine()
				.build();
	}

	@Test
	public void testNoPartitionedRecords()
	{
		final IDLMAware workpackageDLMAware = InterfaceWrapperHelper.create(workpackage, IDLMAware.class);

		final Partition result = new PartitionerService()
				.attachToPartition(mkMethodParam(workpackageDLMAware), config)
				.getPartition();

		// no records are partitioned. return them all.
		assertThat(result.getRecordsFlat().size(), is(4));
	}

	/**
	 * Generally, if you find a record that is already part of a partition, include that record in the result, but never search further
	 */
	@Test
	public void testNoAdjacentPartitionedRecords()
	{
		addToPartition(pinstance, partitionDB);

		final IDLMAware workpackageDLMAware = InterfaceWrapperHelper.create(workpackage, IDLMAware.class);

		final Partition result = new PartitionerService()
				.attachToPartition(mkMethodParam(workpackageDLMAware), config)
				.getPartition();

		// no adjacent records are partitioned. return them all.
		assertThat(result.getRecordsFlat().size(), is(4));
		assertThat(result.getDLM_Partition_ID(), is(partitionDB.getDLM_Partition_ID()));
	}

	@Test
	public void testAdjacentBlockPartitioned()
	{
		addToPartition(block, partitionDB);

		final IDLMAware workpackageDLMAware = InterfaceWrapperHelper.create(workpackage, IDLMAware.class);

		final Partition result = new PartitionerService()
				.attachToPartition(mkMethodParam(workpackageDLMAware), config)
				.getPartition();

		// the result contains the workpackage itself and also the block and the element, because on finding the partitioned block, the system shall search in each direction.
		// however the pinstance is not included, because after having found the block, the system shall not search further
		assertThat(result.getRecordsFlat().size(), is(3));
		assertThat(result.getRecordsFlat().stream().anyMatch(r -> InterfaceWrapperHelper.getModelTableName(r).equals(I_AD_PInstance.Table_Name)), is(false));
		assertThat(result.getDLM_Partition_ID(), is(partitionDB.getDLM_Partition_ID()));
	}

	@Test
	public void testAdjacentElementPartitioned()
	{
		addToPartition(element, partitionDB);

		final IDLMAware workpackageDLMAware = InterfaceWrapperHelper.create(workpackage, IDLMAware.class);

		final Partition result = new PartitionerService()
				.attachToPartition(mkMethodParam(workpackageDLMAware), config)
				.getPartition();

		// the result contains the workpackage itself the element, the block and the pinstance, because on finding the partitioned element, the system shall search in each direction.
		assertThat(result.getRecordsFlat().size(), is(4));
		assertThat(result.getDLM_Partition_ID(), is(partitionDB.getDLM_Partition_ID()));
	}

	@Test
	public void testTwoAdjacentrecordsPartitioned()
	{
		addToPartition(element, partitionDB);
		addToPartition(pinstance, partitionDB2);

		final IDLMAware workpackageDLMAware = InterfaceWrapperHelper.create(workpackage, IDLMAware.class);

		final Partition result = new PartitionerService()
				.attachToPartition(mkMethodParam(workpackageDLMAware), config)
				.getPartition();

		// the result contains the workpackage itself the element, the block and the pinstance, because on finding the partitioned element, the system shall search in each direction.
		assertThat(result.getRecordsFlat().size(), is(4));
		assertThat(result.getDLM_Partition_ID(), is(partitionDB.getDLM_Partition_ID()));
	}

	private void addToPartition(final Object record, final I_DLM_Partition partitionDB)
	{
		final IDLMAware recordDLMAware = InterfaceWrapperHelper.create(record, IDLMAware.class);
		recordDLMAware.setDLM_Partition_ID(partitionDB.getDLM_Partition_ID());
		InterfaceWrapperHelper.save(recordDLMAware);

		final ITableRecordReference tableRecordReverence = ITableRecordReference.FromModelConverter.convert(record);

		final I_DLM_Partition_Record_V partitionRecord = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Record_V.class);
		partitionRecord.setAD_Table_ID(tableRecordReverence.getAD_Table_ID());
		partitionRecord.setRecord_ID(tableRecordReverence.getRecord_ID());
		partitionRecord.setDLM_Partition_ID(partitionDB.getDLM_Partition_ID());
		InterfaceWrapperHelper.save(partitionRecord);
	}

	/**
	 * truns the given <code>model</code> into a table reference singleton list.
	 *
	 * @param model
	 * @return
	 */
	private CreatePartitionIterateResult mkMethodParam(final IDLMAware model)
	{
		return new CreatePartitionIterateResult(
				Collections.singletonList(
						WorkQueue.of(ITableRecordReference.FromModelConverter.convert(model))).iterator(),
				PlainContextAware.newWithThreadInheritedTrx());
	}

}
