package org.adempiere.ad.trx.processor.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.impl.PlainTrxManager;
import org.adempiere.ad.trx.api.impl.PredictableTrxNameGenerator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;

public class TrxItemProcessorExecutorTest
{
	private PlainTrxManager trxManager;
	private PredictableTrxNameGenerator trxNameGenerator;

	private MockedItemProcessor processor;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		trxManager = (PlainTrxManager)Services.get(ITrxManager.class);

		// Configure a predictable trxName generator.
		// We rely on this in tests, to check which item in which transaction was processed.
		this.trxNameGenerator = new PredictableTrxNameGenerator("trx", 1);
		trxManager.setTrxNameGenerator(trxNameGenerator);

		// NOTE: In production code, the commit and rollback silently ignores if transaction was never started before.
		// It seems that our trx TrxItemExecutor is relying on that feature.
		trxManager.setFailCommitIfTrxNotStarted(false);
		trxManager.setFailRollbackIfTrxNotStarted(false);

		processor = new MockedItemProcessor();
	}

	@Test
	public void test_execute_Success()
	{
		final List<Item> items = Arrays.asList(
				new Item("1", "1"),
				new Item("1", "2"),
				new Item("1", "3"),
				//
				new Item("2", "1"),
				//
				new Item("3", "1"),
				new Item("3", "2"),
				new Item("3", "3")
				);

		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				new AggregatedItem("1", "trx1",
						new Item("1", "1"),
						new Item("1", "2"),
						new Item("1", "3")
				),
				new AggregatedItem("2", "trx2",
						new Item("2", "1")
				),
				new AggregatedItem("3", "trx3",
						new Item("3", "1"),
						new Item("3", "2"),
						new Item("3", "3")
				)
				);

		assertProcessorResult(items, resultExpected);
	}

	@Test
	public void test_execute_FailingItem()
	{
		final List<Item> items = Arrays.asList(
				new Item("1", "1"),
				new Item("1", "2"),
				new Item("1", "3"),
				new Item("2", "1"),
				new Item("3", "1"),
				new Item("3", "2"),
				new Item("3", "3")
				);

		processor.setThrowExceptionIfItem(new Item("1", "2"));

		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				// NOTE: this chunk shall not be present because one of its items are failing
				// new AggregatedItem("1", "trx1",
				// new Item("1", "1"),
				// new Item("1", "2"),
				// new Item("1", "3")
				// ),
				new AggregatedItem("2", "trx2",
						new Item("2", "1")
				),
				new AggregatedItem("3", "trx3",
						new Item("3", "1"),
						new Item("3", "2"),
						new Item("3", "3")
				)
				);

		assertProcessorResult(items, resultExpected);
	}

	@Test
	public void test_execute_Failing_CompleteChunk()
	{
		final List<Item> items = Arrays.asList(
				new Item("1", "1"),
				new Item("1", "2"),
				new Item("1", "3"),
				new Item("2", "1"),
				new Item("3", "1"),
				new Item("3", "2"),
				new Item("3", "3")
				);

		processor.setThrowExceptionOnCompleteChunk("1");

		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				// NOTE: this chunk shall not be present because completeChunk fails
				// new AggregatedItem("1", "trx1",
				// new Item("1", "1"),
				// new Item("1", "2"),
				// new Item("1", "3")
				// ),
				new AggregatedItem("2", "trx2",
						new Item("2", "1")
				),
				new AggregatedItem("3", "trx3",
						new Item("3", "1"),
						new Item("3", "2"),
						new Item("3", "3")
				)
				);

		assertProcessorResult(items, resultExpected);
	}

	@Test
	public void test_execute_Failing_CompleteChunk_and_CancelChunk()
	{
		final List<Item> items = Arrays.asList(
				new Item("1", "1"),
				new Item("1", "2"),
				new Item("1", "3"),
				new Item("2", "1"),
				new Item("3", "1"),
				new Item("3", "2"),
				new Item("3", "3")
				);

		processor.setThrowExceptionOnCompleteChunk("2");
		processor.setThrowExceptionOnCancelChunk("2");

		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				new AggregatedItem("1", "trx1",
						new Item("1", "1"),
						new Item("1", "2"),
						new Item("1", "3")
				)
				// NOTE: following chunks shall not be present because cancelChunk fails on chunk "2"
				// which means that entire batch processing will be stopped

				// , new AggregatedItem("2", "trx2",
				// new Item("2", "1")
				// )
				// , new AggregatedItem("3", "trx3",
				// new Item("3", "1"),
				// new Item("3", "2"),
				// new Item("3", "3")
				// )
				);

		assertProcessorResult(items,
				resultExpected,
				AdempiereException.class // we are expecting processing to fail on cancel
		);
	}

	@Test
	public void test_execute_Failing_NewChunk()
	{
		final List<Item> items = Arrays.asList(
				new Item("1", "1"),
				new Item("1", "2"),
				new Item("1", "3"),
				new Item("2", "1"),
				new Item("3", "1"),
				new Item("3", "2"),
				new Item("3", "3")
				);

		processor.setThrowExceptionOnNewChunk("1");

		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				// NOTE: this chunk shall not be present because newChunk fails
				// NOTE: first 3 transactions will be lost on first 3 items
				// new AggregatedItem("1", "trx1",
				// new Item("1", "1"),
				// new Item("1", "2"),
				// new Item("1", "3")
				// ),
				new AggregatedItem("2", "trx4",
						new Item("2", "1")
				),
				new AggregatedItem("3", "trx5",
						new Item("3", "1"),
						new Item("3", "2"),
						new Item("3", "3")
				)
				);

		assertProcessorResult(items, resultExpected);
	}

	@Test
	public void test_DontUseTrxSavepoints()
	{
		final List<Item> items = Arrays.asList(new Item("1", "1"));
		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				new AggregatedItem("1", "trx1",
						new Item("1", "1")
				)
				);

		processor.setExpectTrxSavepoints(false);
		new TrxItemProcessorExecutorRunExpectations<Item, ItemProcessorResult>()
				.setProcessor(processor)
				.setItems(items)
				.setExpectedResult(resultExpected)
				.setRunInTrx(true)
				.setUseTrxSavepoints(false)
				.assertExpected();
	}

	@Test
	public void test_UseTrxSavepoints()
	{
		final List<Item> items = Arrays.asList(new Item("1", "1"));
		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				new AggregatedItem("1", "trx1",
						new Item("1", "1")
				)
				);

		processor.setExpectTrxSavepoints(true);
		new TrxItemProcessorExecutorRunExpectations<Item, ItemProcessorResult>()
				.setProcessor(processor)
				.setItems(items)
				.setExpectedResult(resultExpected)
				.setRunInTrx(true)
				.setUseTrxSavepoints(true)
				.assertExpected();
	}

	private void assertProcessorResult(final List<Item> items, final ItemProcessorResult resultExpected)
	{
		final Class<?> expectedException = null; // we don't expect any exception
		assertProcessorResult(items, resultExpected, expectedException);
	}

	/**
	 * Execute {@link #processor} on given items and check expectations.
	 *
	 * @param items
	 * @param resultExpected
	 * @param expectedExceptionClass
	 */
	private void assertProcessorResult(final List<Item> items, final ItemProcessorResult resultExpected, final Class<?> expectedExceptionClass)
	{
		final TrxItemProcessorExecutorRunExpectations<Item, ItemProcessorResult> expectations = new TrxItemProcessorExecutorRunExpectations<Item, ItemProcessorResult>()
				.setProcessor(processor)
				.setItems(items)
				.setExpectedResult(resultExpected)
				.setExpectedExceptionClass(expectedExceptionClass);

		processor.reset();
		trxNameGenerator.resetIndex();
		expectations
				.setRunInTrx(false)
				.assertExpected();
	}
}
