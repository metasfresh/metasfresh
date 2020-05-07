package de.metas.dlm.partitioner.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Iterator;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Color;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.dlm.Partition.WorkQueue;
import de.metas.dlm.partitioner.IIterateResult;

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
	@Before
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
		assertThat(iterateResult.isQueueEmpty(), is(true));
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
		final ITableRecordReference tableRecordReference = ITableRecordReference.FromModelConverter.convert(color);

		final Iterator<WorkQueue> initialQueue = ImmutableList.of(WorkQueue.of(tableRecordReference)).iterator();
		final IContextAware ctxAware = PlainContextAware.newWithThreadInheritedTrx();

		final IIterateResult iterateResult = new CreatePartitionIterateResult(initialQueue, ctxAware);

		assertThat(iterateResult.isQueueEmpty(), is(false));
		assertThat(iterateResult.nextFromQueue(), is(tableRecordReference));
		assertThat(iterateResult.isQueueEmpty(), is(true));
		assertThat(iterateResult.size(), is(1));
	}

	@Test
	public void testOneItemInStreamThenAnother()
	{
		final I_AD_Color color1 = InterfaceWrapperHelper.newInstance(I_AD_Color.class);
		InterfaceWrapperHelper.save(color1);

		final I_AD_Color color2 = InterfaceWrapperHelper.newInstance(I_AD_Color.class);
		InterfaceWrapperHelper.save(color2);

		// we don't care if it exists or not
		final ITableRecordReference tableRecordReference1 = ITableRecordReference.FromModelConverter.convert(color1);
		final ITableRecordReference tableRecordReference2 = ITableRecordReference.FromModelConverter.convert(color2);

		final Iterator<WorkQueue> initialQueue = ImmutableList.of(WorkQueue.of(tableRecordReference1)).iterator();
		final IContextAware ctxAware = PlainContextAware.newWithThreadInheritedTrx();

		final CreatePartitionIterateResult iterateResult = new CreatePartitionIterateResult(initialQueue, ctxAware);

		assertThat(iterateResult.isQueueEmpty(), is(false));
		assertThat(iterateResult.contains(tableRecordReference1), is(false)); // tableRecordReference1 was not yet accessed. in order for it to be contained, result would need to get its DLM_PArtiton_ID and therefore would need to get it from the iterator.

		assertThat(iterateResult.nextFromQueue(), is(tableRecordReference1));
		assertThat(iterateResult.contains(tableRecordReference1), is(true)); // now that tableRecordReference1 was returned from the iterator, it also needs to be officially contained be the result
		assertThat(iterateResult.isQueueEmpty(), is(true));
		assertThat(iterateResult.size(), is(1));

		iterateResult.addReferencedRecord(null, tableRecordReference2, 0);
		assertThat(iterateResult.size(), is(2));

		assertThat(iterateResult.isQueueEmpty(), is(false));
		assertThat(iterateResult.nextFromQueue(), is(tableRecordReference2));
		assertThat(iterateResult.isQueueEmpty(), is(true));
		assertThat(iterateResult.size(), is(2));
	}
}
