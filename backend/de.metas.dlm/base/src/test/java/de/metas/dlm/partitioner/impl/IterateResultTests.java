package de.metas.dlm.partitioner.impl;

import com.google.common.collect.ImmutableList;
import de.metas.dlm.Partition.WorkQueue;
import de.metas.dlm.partitioner.IIterateResult;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

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

public class IterateResultTests
{
	@BeforeEach
	public void setup()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testEmpty()
	{
		final Iterator<WorkQueue> initialQueue = new ArrayList<WorkQueue>().iterator();
		final IContextAware ctxAware = PlainContextAware.newWithThreadInheritedTrx();

		final IIterateResult iterateResult = new CreatePartitionIterateResult(initialQueue, ctxAware);
		assertThat(iterateResult.isQueueEmpty()).isTrue();
	}

	/**
	 * <li>Stream with one item
	 * <li>removes the item
	 */
	@Test
	public void testOneItemInStream()
	{
		final I_AD_Color color = InterfaceWrapperHelper.newInstance(I_AD_Color.class);
		InterfaceWrapperHelper.save(color);

		// we don't care if it exists or not
		final ITableRecordReference tableRecordReference = TableRecordReference.ofOrNull(color);

		final Iterator<WorkQueue> initialQueue = ImmutableList.of(WorkQueue.of(tableRecordReference)).iterator();
		final IContextAware ctxAware = PlainContextAware.newWithThreadInheritedTrx();

		final IIterateResult iterateResult = new CreatePartitionIterateResult(initialQueue, ctxAware);

		assertThat(iterateResult.isQueueEmpty()).isFalse();
		assertThat(iterateResult.nextFromQueue()).isEqualTo(tableRecordReference);
		assertThat(iterateResult.isQueueEmpty()).isTrue();
		assertThat(iterateResult.size()).isEqualTo(1);
	}

	@Test
	public void testOneItemInStreamThenAnother()
	{
		final I_AD_Color color1 = InterfaceWrapperHelper.newInstance(I_AD_Color.class);
		InterfaceWrapperHelper.save(color1);

		final I_AD_Color color2 = InterfaceWrapperHelper.newInstance(I_AD_Color.class);
		InterfaceWrapperHelper.save(color2);

		// we don't care if it exists or not
		final ITableRecordReference tableRecordReference1 = TableRecordReference.ofOrNull(color1);
		final ITableRecordReference tableRecordReference2 = TableRecordReference.ofOrNull(color2);

		final Iterator<WorkQueue> initialQueue = ImmutableList.of(WorkQueue.of(tableRecordReference1)).iterator();
		final IContextAware ctxAware = PlainContextAware.newWithThreadInheritedTrx();

		final CreatePartitionIterateResult iterateResult = new CreatePartitionIterateResult(initialQueue, ctxAware);

		assertThat(iterateResult.isQueueEmpty()).isFalse();
		assertThat(iterateResult.contains(tableRecordReference1)).isFalse(); // tableRecordReference1 was not yet accessed. in order for it to be contained, result would need to get its DLM_PArtiton_ID and therefore would need to get it from the iterator.

		assertThat(iterateResult.nextFromQueue()).isEqualTo(tableRecordReference1);
		assertThat(iterateResult.contains(tableRecordReference1)).isTrue(); // now that tableRecordReference1 was returned from the iterator, it also needs to be officially contained be the result
		assertThat(iterateResult.isQueueEmpty()).isTrue();
		assertThat(iterateResult.size()).isEqualTo(1);

		iterateResult.addReferencedRecord(null, tableRecordReference2, 0);
		assertThat(iterateResult.size()).isEqualTo(2);

		assertThat(iterateResult.isQueueEmpty()).isFalse();
		assertThat(iterateResult.nextFromQueue()).isEqualTo(tableRecordReference2);
		assertThat(iterateResult.isQueueEmpty()).isTrue();
		assertThat(iterateResult.size()).isEqualTo(2);
	}
}