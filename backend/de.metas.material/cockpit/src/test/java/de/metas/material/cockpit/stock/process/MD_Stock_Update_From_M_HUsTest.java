package de.metas.material.cockpit.stock.process;

import com.google.common.collect.ImmutableList;
import de.metas.material.cockpit.model.I_MD_Stock_From_HUs_V;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * Loop-contract tests for the batched drain in {@link MD_Stock_Update_From_M_HUs}.
 * <p>
 * The {@code MD_Stock_From_HUs_V} view is DB-backed and therefore out of scope for these POJO tests.
 * Instead the new batch-loop logic is exercised through two seams:
 * <ul>
 *     <li>a {@link MD_Stock_Update_From_M_HUs.BatchSource} that returns the (fake) next batch, and</li>
 *     <li>a {@link MD_Stock_Update_From_M_HUs.BatchProcessor} that captures the rows forwarded per batch.</li>
 * </ul>
 */
class MD_Stock_Update_From_M_HUsTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private static I_MD_Stock_From_HUs_V newRow()
	{
		return InterfaceWrapperHelper.newInstance(I_MD_Stock_From_HUs_V.class);
	}

	/** A fake batch source that yields the given batches in order, then empties forever. */
	private static MD_Stock_Update_From_M_HUs.BatchSource sourceOf(final List<List<I_MD_Stock_From_HUs_V>> batches)
	{
		final Deque<List<I_MD_Stock_From_HUs_V>> queue = new ArrayDeque<>(batches);
		return batchSize -> queue.isEmpty() ? ImmutableList.of() : queue.poll();
	}

	@Test
	void drains_threeNonEmptyBatchesThenEmpty_forwardsEveryRowAndStops()
	{
		final List<List<I_MD_Stock_From_HUs_V>> batches = ImmutableList.of(
				ImmutableList.of(newRow(), newRow(), newRow()),  // 3 rows
				ImmutableList.of(newRow(), newRow()),             // 2 rows
				ImmutableList.of(newRow()));                      // 1 row -> total 6

		final AtomicInteger forwardedRows = new AtomicInteger();
		final AtomicInteger batchCount = new AtomicInteger();
		final MD_Stock_Update_From_M_HUs.BatchProcessor processor = batch -> {
			batchCount.incrementAndGet();
			forwardedRows.addAndGet(batch.size());
		};

		final MD_Stock_Update_From_M_HUs process = new MD_Stock_Update_From_M_HUs(
				sourceOf(batches),
				processor,
				100_000 /*maxLoops*/);

		final int total = process.drainInBatches();

		assertThat(total).isEqualTo(6);
		assertThat(forwardedRows.get()).isEqualTo(6);
		assertThat(batchCount.get()).isEqualTo(3); // empty batch is not forwarded
	}

	@Test
	void backstop_neverEmpties_terminatesAfterMaxLoops()
	{
		final int maxLoops = 5;
		final AtomicInteger batchCount = new AtomicInteger();
		// source that NEVER returns empty
		final MD_Stock_Update_From_M_HUs.BatchSource neverEmpty = batchSize -> {
			final List<I_MD_Stock_From_HUs_V> batch = new ArrayList<>();
			batch.add(newRow());
			return batch;
		};
		final MD_Stock_Update_From_M_HUs.BatchProcessor processor = batch -> batchCount.incrementAndGet();

		final MD_Stock_Update_From_M_HUs process = new MD_Stock_Update_From_M_HUs(
				neverEmpty,
				processor,
				maxLoops);

		final int total = process.drainInBatches();

		// the loop must terminate (not spin forever) after exactly maxLoops iterations
		assertThat(batchCount.get()).isEqualTo(maxLoops);
		assertThat(total).isEqualTo(maxLoops); // 1 row per loop
	}

	@Test
	void emptyFromTheStart_returnsZeroAndNeverCallsProcessor()
	{
		final AtomicInteger batchCount = new AtomicInteger();
		final MD_Stock_Update_From_M_HUs.BatchProcessor processor = batch -> batchCount.incrementAndGet();

		final MD_Stock_Update_From_M_HUs process = new MD_Stock_Update_From_M_HUs(
				batchSize -> ImmutableList.of(),
				processor,
				100_000 /*maxLoops*/);

		final int total = process.drainInBatches();

		assertThat(total).isZero();
		assertThat(batchCount.get()).isZero();
	}
}
