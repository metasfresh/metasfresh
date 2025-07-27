package de.metas.dlm.partitioner.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Collections;
import java.util.Iterator;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.util.Env;


import de.metas.dlm.Partition.WorkQueue;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.model.I_DLM_Partition_Workqueue;
import de.metas.dlm.partitioner.config.PartitionConfig;
import de.metas.util.Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

public class RecordCrawlerServiceStoreITerateResultTests
{
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private I_DLM_Partition p1;

	private final RecordCrawlerService recordCrawlerService = new RecordCrawlerService(); // this is the class under test
	private final PlainContextAware ctxAware = PlainContextAware.newOutOfTrx(Env.getCtx());
	private final PartitionConfig config = PartitionConfig.builder().build();

	@BeforeEach
	public void before()
	{
		AdempiereTestHelper.get().init();

		p1 = InterfaceWrapperHelper.newInstance(I_DLM_Partition.class);
		InterfaceWrapperHelper.save(p1);
	}

	/**
	 * Scenario: {@link PartitionerService#storeIterateResult(de.metas.dlm.partitioner.config.PartitionConfig, CreatePartitionIterateResult, org.adempiere.model.IContextAware)} is called for the first time, after an initital queue was loaded from DB.
	 * <p>
	 * The records from that queue already have a <code>DLM_PArtition_ID</code>.
	 *
	 */
	@Test
	public void testStoreIterateResultWithInitialQueue()
	{
		testStoreIterateResultWithInitialQueue0();
	}

	/**
	 * Performs the actual test of {@link #testStoreIterateResultWithInitialQueue()}.
	 */
	private CreatePartitionIterateResult testStoreIterateResultWithInitialQueue0()
	{
		final I_AD_Tab tab = InterfaceWrapperHelper.newInstance(I_AD_Tab.class);
		final IDLMAware tabDLMAware = InterfaceWrapperHelper.create(tab, IDLMAware.class);
		tabDLMAware.setDLM_Partition_ID(p1.getDLM_Partition_ID());
		InterfaceWrapperHelper.save(tab);

		// inititalqueue has one item that references tab
		final I_DLM_Partition_Workqueue workQueueDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Workqueue.class);

		workQueueDB.setAD_Table_ID(adTableDAO.retrieveTableId(I_AD_Tab.Table_Name));
		workQueueDB.setRecord_ID(tab.getAD_Tab_ID());
		InterfaceWrapperHelper.save(workQueueDB);

		final Iterator<WorkQueue> initialQueue = Collections.singletonList(WorkQueue.of(workQueueDB)).iterator();

		final CreatePartitionIterateResult result = new CreatePartitionIterateResult(initialQueue, ctxAware);
		result.nextFromQueue(); //
		assertThat(result.size()).isEqualTo(1);

		// call the method under test
		recordCrawlerService.storeIterateResult0(config, result, ctxAware);

		assertThat(result.getPartition().getDLM_Partition_ID()).isEqualTo(p1.getDLM_Partition_ID());
		assertThat(result.getDlmPartitionId2Record()).isEmpty();

		return result;
	}

	/**
	 * Scenario: {@link PartitionerService#storeIterateResult(de.metas.dlm.partitioner.config.PartitionConfig, CreatePartitionIterateResult, org.adempiere.model.IContextAware)} was called once, so
	 * {@link CreatePartitionIterateResult#getPartition()} is a stored partition.<br>
	 * For this part we rerun the code of {@link #testStoreIterateResultWithInitialQueue()}
	 * <p>
	 * Now {@link PartitionerService} iterates further and finds only records that belong to a different partition.
	 * <p>
	 * So {@link PartitionerService#storeIterateResult(PartitionConfig, CreatePartitionIterateResult, org.adempiere.model.IContextAware)} is now called with two different <code>DLM_PArtition_ID</code>s, and none of them is 0.
	 * This shall be OK and the two parttions shall be merged into one.
	 *
	 *
	 */
	@Test
	public void testStoreIterateResultWithInitialQueue2()
	{
		final CreatePartitionIterateResult result = testStoreIterateResultWithInitialQueue0();

		final I_DLM_Partition p2 = InterfaceWrapperHelper.newInstance(I_DLM_Partition.class);
		InterfaceWrapperHelper.save(p2);

		// add another record with DLM_Partition_ID = 20
		final I_AD_Field field = InterfaceWrapperHelper.newInstance(I_AD_Field.class);
		final IDLMAware fieldDLMAware = InterfaceWrapperHelper.create(field, IDLMAware.class);
		fieldDLMAware.setDLM_Partition_ID(p2.getDLM_Partition_ID());

		InterfaceWrapperHelper.save(field);
		final ITableRecordReference tableRecordReference = TableRecordReference.ofOrNull(field);

		result.addReferencingRecord(tableRecordReference, null, p2.getDLM_Partition_ID());

		// invoke the method under test
		recordCrawlerService.storeIterateResult0(config, result, ctxAware);

		assertThat(result.getPartition().getDLM_Partition_ID()).isEqualTo(p1.getDLM_Partition_ID());
	}
}
