package org.adempiere.ad.trx.processor.api.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.impl.MockedTrx;
import org.adempiere.ad.trx.api.impl.MockedTrxManager;
import org.adempiere.ad.trx.api.impl.PredictableTrxNameGenerator;
import org.adempiere.ad.trx.processor.api.ITrxItemExecutorBuilder;
import org.adempiere.ad.trx.processor.api.ITrxItemExecutorBuilder.OnItemErrorPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;

public class TrxItemProcessorExecutorTest
{
	private MockedTrxManager trxManager = new MockedTrxManager();

	private PredictableTrxNameGenerator trxNameGenerator;

	private MockedItemProcessor processor;

	private List<Item> items;

	private void assertAllItemsNoError(final List<Item> items, final Item... excludeItem)
	{
		assertAllItemsWhatever(items, i -> !i.isOnItemError(), "error", excludeItem);
	}

	/**
	 * Assert that all items besides the excluded ones are processed. For the excluded items (if any), asserts that the were <b>not</b> processed.
	 *
	 * @param items
	 * @param excludeItem
	 */
	private void assertAllItemsProcessed(final List<Item> items, final Item... excludeItem)
	{
		assertAllItemsWhatever(items, i -> i.isProcessed(), "processed", excludeItem);
	}

	/**
	 * Asserts that the given <code>whatever</code> function returns <code>true</code> for all <code>items</code> that are not among the given <code>excludeItem</code> (equality is sufficient).
	 * For the given <code>excludeItem</code>, it asserts that the given <code>whatever</code> function returns <code>false</code>.
	 *
	 * @param items
	 * @param whatever
	 * @param whateverText
	 * @param excludeItem
	 */
	private void assertAllItemsWhatever(
			final List<Item> items,
			final Function<Item, Boolean> whatever,
			final String whateverText,
			final Item... excludeItem)
	{
		final List<Item> excludeItemsList = Arrays.asList(excludeItem);

		items
				.stream()
				.filter(i -> !excludeItemsList.contains(i))
				.forEach(i -> assertTrue("item " + i + "is/has not " + whateverText, whatever.apply(i)));

		excludeItemsList
				.forEach(i -> assertFalse("item " + i + "is/has " + whateverText, whatever.apply(i)));
	}

	/**
	 * Execute {@link #processor} on given items and check expectations.
	 *
	 * @param items
	 * @param onItemErrorPolicy
	 * @param resultExpected
	 * @param expectedExceptionClass
	 */
	private void assertProcessorResult(
			final List<Item> items,
			final ITrxItemExecutorBuilder.OnItemErrorPolicy onItemErrorPolicy,
			final ItemProcessorResult resultExpected,
			final Class<?> expectedExceptionClass)
	{
		final TrxItemProcessorExecutorRunExpectations<Item, ItemProcessorResult> expectations = new TrxItemProcessorExecutorRunExpectations<Item, ItemProcessorResult>()
				.setProcessor(processor)
				.setOnItemErrorPolicy(onItemErrorPolicy)
				.setItems(items)
				.setExpectedResult(resultExpected)
				.setExpectedExceptionClass(expectedExceptionClass);

		processor.reset();
		trxNameGenerator.resetIndex();

		expectations
				.setRunInTrx(false)
				.assertExpected();
	}

	private void assertProcessorResult_CancelChunkAndRollBack(
			final List<Item> items,
			final ItemProcessorResult resultExpected)
	{
		final Class<?> expectedException = null; // we don't expect any exception
		assertProcessorResult(items,
				OnItemErrorPolicy.CancelChunkAndRollBack,
				resultExpected,
				expectedException);
	}

	private void assertTrx(final String trxName, final int commits, final int rollbacks)
	{
		final MockedTrx trx = (MockedTrx)trxManager.getRemovedTransactionByName(trxName);

		assertThat("trx=" + trxName + " has a wrong number of commit() invocations", trx.getCommits().size(), is(commits));
		assertThat("trx=" + trxName + " has a wrong number of rollback() invocations", trx.getRollbacks().size(), is(rollbacks));
		assertThat("trx=" + trxName + " has a wrong isActive() status", trx.isActive(), is(false));
	}

	/**
	 * Returns the first {@link Item} from the given <code>itemsList</code> that has the given <code>groupKey</code> and <code>value</code>.
	 *
	 * @param items
	 * @param groupKey
	 * @param value
	 *
	 * @return
	 */
	private Item findItem(final List<Item> items, final String groupKey, final String value)
	{
		final Optional<Item> itemOrNull = items
				.stream()
				.filter(i -> i != null)
				.filter(i -> i.equals(new Item(groupKey, value)))
				.findFirst();

		return itemOrNull.orElse(null);
	}

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		trxManager = new MockedTrxManager();
		Services.registerService(ITrxManager.class, trxManager);

		// Configure a predictable trxName generator.
		// We rely on this in tests, to check which item in which transaction was processed.
		trxNameGenerator = new PredictableTrxNameGenerator("trx", 1);
		trxManager.setTrxNameGenerator(trxNameGenerator);

		// // NOTE: In production code, the commit and rollback silently ignores if transaction was never started before.
		// // It seems that our trx TrxItemExecutor is relying on that feature.
		trxManager.setFailCommitIfTrxNotStarted(false);
		trxManager.setFailRollbackIfTrxNotStarted(false);

		processor = new MockedItemProcessor();

		// reset our items
		items = mkItems();

	}

	private List<Item> mkItems()
	{
		return Arrays.asList(
				new Item("1", "1"),
				new Item("1", "2"),
				new Item("1", "3"),
				new Item("2", "1"),
				new Item("3", "1"),
				new Item("3", "2"),
				new Item("3", "3"));
	}

	@Test
	public void test_DontUseTrxSavepoints()
	{
		final List<Item> items = Arrays.asList(new Item("1", "1"));
		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				new AggregatedItem("1", "trx1",
						new Item("1", "1")));

		processor.setExpectTrxSavepoints(false);
		new TrxItemProcessorExecutorRunExpectations<Item, ItemProcessorResult>()
				.setProcessor(processor)
				.setItems(items)
				.setExpectedResult(resultExpected)
				.setRunInTrx(true)
				.setUseTrxSavepoints(false)
				.assertExpected();

		assertAllItemsProcessed(items);
	}

	/**
	 * Tests a fail when the executor tries to complete a chunk.
	 */
	@Test
	public void test_execute_Failing_CompleteChunk()
	{
		processor.setThrowExceptionOnCompleteChunk("1");

		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				// NOTE: this chunk shall not be present because completeChunk fails
				// new AggregatedItem("1", "trx1",
				// new Item("1", "1"),
				// new Item("1", "2"),
				// new Item("1", "3")
				// ),
				new AggregatedItem("2", "trx2",
						new Item("2", "1")),
				new AggregatedItem("3", "trx3",
						new Item("3", "1"),
						new Item("3", "2"),
						new Item("3", "3")));

		assertProcessorResult(items,
				OnItemErrorPolicy.CancelChunkAndRollBack,
				resultExpected,
				null);

		// *every* item shall be processed, because the exception was thrown at the end of a chunk
		assertAllItemsProcessed(items);
		assertAllItemsNoError(items);

		assertTrx("trx1", 0, 1); // the failed trx shall be rolled back
		assertTrx("trx2", 1, 0);
		assertTrx("trx3", 1, 0);

	}

	/**
	 * Tests the behavior if an exception is thrown on an chunk's complete <b>and</b> on the successive cancel attempt.
	 * In short, we expect the executor to stop in that case.
	 */
	@Test
	public void test_execute_Failing_CompleteChunk_and_CancelChunk()
	{
		List<Item> items = mkItems();

		processor.setThrowExceptionOnCompleteChunk("2");
		processor.setThrowExceptionOnCancelChunk("2");

		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				new AggregatedItem("1", "trx1",
						new Item("1", "1"),
						new Item("1", "2"),
						new Item("1", "3"))
		// NOTE: following chunks shall not be present because cancelChunk fails on chunk "2"
		// which means that entire batch processing will be stopped
		);

		// the items that shall remain unprocessed
		final Item[] notProcessed = {
				new Item("2", "1"),
				new Item("3", "1"),
				new Item("3", "2"),
				new Item("3", "3") };

		assertProcessorResult(items,
				OnItemErrorPolicy.CancelChunkAndRollBack,
				resultExpected,
				AdempiereException.class // we are expecting processing to fail on cancel
		);
		assertAllItemsProcessed(items, notProcessed);

		assertTrx("trx1", 1, 0); // the failed trx shall be rolled back
		assertTrx("trx2", 0, 1);
		// assertTrx("trx3", 0, 0); // doesn't exist

		items = mkItems();

		//
		// we expect the same result for CancelChunkAndCommit
		assertProcessorResult(items,
				OnItemErrorPolicy.CancelChunkAndCommit,
				resultExpected,
				AdempiereException.class // we are expecting processing to fail on cancel
		);
		assertAllItemsProcessed(items, notProcessed);
	}

	/**
	 * Verifies that if a chunk can't be opened, then none of its items shall be processed.
	 * However, the testee shall try to open a new chunk for each item.
	 */
	@Test
	public void test_execute_Failing_NewChunk()
	{
		final List<Item> items = mkItems();

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
						new Item("2", "1")),
				new AggregatedItem("3", "trx5",
						new Item("3", "1"),
						new Item("3", "2"),
						new Item("3", "3")));

		assertProcessorResult_CancelChunkAndRollBack(items, resultExpected);

		assertAllItemsNoError(items); // none of the items shall have an error (but not all are processed)

		// the first chunk's items shall not be processed, because an exception prevented that chunk
		assertAllItemsProcessed(items,
				new Item("1", "1"),
				new Item("1", "2"),
				new Item("1", "3"));

		assertTrx("trx1", 0, 1); // even if creating the chunk failed, there is already a trx to roll back
		assertTrx("trx2", 0, 1);
		assertTrx("trx3", 0, 1);
		assertTrx("trx4", 1, 0);
		assertTrx("trx5", 1, 0);
	}

	@Test
	public void test_execute_FailingItem_CancelAndRollBack()
	{
		processor.setThrowExceptionIfItem(new Item("1", "2"));

		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				// NOTE: this chunk shall not be present because one of its items are failing
				// new AggregatedItem("1", "trx1",
				// new Item("1", "1"),
				// new Item("1", "2"),
				// new Item("1", "3")
				// ),
				new AggregatedItem("2", "trx2",
						new Item("2", "1")),
				new AggregatedItem("3", "trx3",
						new Item("3", "1"),
						new Item("3", "2"),
						new Item("3", "3")));

		assertProcessorResult_CancelChunkAndRollBack(items, resultExpected);

		assertAllItemsProcessed(items,
				new Item("1", "3") // not this one, because the chunk was canceled after 1-2
		);

		assertTrx("trx1", 0, 1);
		assertTrx("trx2", 1, 0);
		assertTrx("trx3", 1, 0);

	}

	@Test
	public void test_execute_FailingItem_ContinueChunkAndCommit()
	{
		final Item errorItem = findItem(items, "1", "2");
		processor.setThrowExceptionIfItem(errorItem);

		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				new AggregatedItem("1", "trx1",
						new Item("1", "1"),
						// new Item("1", "2"), // the failing item will not make it into the result, because the mock processor throws an exception instead of adding it
						new Item("1", "3")),
				new AggregatedItem("2", "trx2",
						new Item("2", "1")),
				new AggregatedItem("3", "trx3",
						new Item("3", "1"),
						new Item("3", "2"),
						new Item("3", "3")));

		assertProcessorResult(items,
				OnItemErrorPolicy.ContinueChunkAndCommit,
				resultExpected,
				null);

		assertAllItemsNoError(items, errorItem);

		// *every* item shall be processed, because of OnItemErrorPolicy.ContinueChunkAndCommit
		assertAllItemsProcessed(items);

		assertTrx("trx1", 1, 0);
		assertTrx("trx2", 1, 0);
		assertTrx("trx3", 1, 0);
	}

	@Test
	public void test_execute_Success()
	{
		final List<Item> items = mkItems();

		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				new AggregatedItem("1", "trx1",
						new Item("1", "1"),
						new Item("1", "2"),
						new Item("1", "3")),
				new AggregatedItem("2", "trx2",
						new Item("2", "1")),
				new AggregatedItem("3", "trx3",
						new Item("3", "1"),
						new Item("3", "2"),
						new Item("3", "3")));

		assertProcessorResult_CancelChunkAndRollBack(items, resultExpected);

		assertAllItemsProcessed(items); // because all went fine

		assertTrx("trx1", 1, 0);
		assertTrx("trx2", 1, 0);
		assertTrx("trx3", 1, 0);
	}

	@Test
	public void test_UseTrxSavepoints()
	{
		final List<Item> items = Arrays.asList(new Item("1", "1"));

		final ItemProcessorResult resultExpected = new ItemProcessorResult(
				new AggregatedItem("1", "trx1",
						new Item("1", "1")));

		processor.setExpectTrxSavepoints(true);
		new TrxItemProcessorExecutorRunExpectations<Item, ItemProcessorResult>()
				.setProcessor(processor)
				.setItems(items)
				.setExpectedResult(resultExpected)
				.setRunInTrx(true)
				.setUseTrxSavepoints(true)
				.assertExpected();

		assertAllItemsProcessed(items);
	}
}
