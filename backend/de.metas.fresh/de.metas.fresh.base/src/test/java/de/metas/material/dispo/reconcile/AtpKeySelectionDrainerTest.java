package de.metas.material.dispo.reconcile;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pins the batched key drain both reconciliation paths share - the operator's synchronous dry-run preview in
 * {@code MD_Candidate_Reconcile_ATP} and the real, enqueued run in
 * {@code AtpReconciliationWorkpackageProcessor}.
 * <p>
 * These two behaviours - pagination advancing across rounds, and the {@link AtpKeySelectionDrainer#MAX_LOOPS}
 * backstop - were covered by {@code MD_Candidate_Reconcile_ATPTest} while the drain lived inside that process.
 * They moved here with the drain itself, unchanged in substance: they are now asserted once, on the one
 * collaborator, instead of once per path. Their original motivation still holds - the cucumber scenarios for
 * this feature never approach 500 rows, so a selection spanning more than one page, and the backstop, are
 * exercised at no other layer.
 */
class AtpKeySelectionDrainerTest
{
	private static final ClientId CLIENT_ID = ClientId.ofRepoId(1000000);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1000001);
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(1000002);

	private static final int BATCH_SIZE = AtpKeySelectionDrainer.BATCH_SIZE;
	private static final int MAX_LOOPS = AtpKeySelectionDrainer.MAX_LOOPS;

	private StockRepository stockRepository;
	private AtpKeySelectionDrainer drainer;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		stockRepository = Mockito.mock(StockRepository.class);
		drainer = new AtpKeySelectionDrainer(stockRepository);
	}

	@Test
	void selectionLargerThanOneBatch_handsOverEveryKeyExactlyOnce()
	{
		// 750 = one full batch (BATCH_SIZE) plus a partial second page, so pagination MUST advance for the second
		// page's 250 keys to be seen at all
		final List<StockDataRecordIdentifier> allKeys = distinctKeys(BATCH_SIZE + 250);

		when(stockRepository.retrieveKeys(any(), any(), any(), anyInt(), anyInt()))
				.thenAnswer(invocation -> {
					final int limit = invocation.getArgument(3);
					final int offset = invocation.getArgument(4);
					final int from = Math.min(offset, allKeys.size());
					final int to = Math.min(offset + limit, allKeys.size());
					return ImmutableList.copyOf(allKeys.subList(from, to));
				});

		final List<StockDataRecordIdentifier> keysSeen = new ArrayList<>();

		final AtpKeySelectionDrainer.DrainSummary summary = drainer.drain(
				AtpKeySelection.ALL,
				key -> {
					keysSeen.add(key);
					return false; // nothing changed, so keysChanged must stay 0
				});

		// retrieveKeys must have been called for offset 0 (full batch) and offset BATCH_SIZE (the trailing partial
		// batch, which is what actually stops the loop) - no more, no fewer
		final ArgumentCaptor<Integer> offsetCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(stockRepository, times(2)).retrieveKeys(any(), any(), any(), anyInt(), offsetCaptor.capture());
		assertThat(offsetCaptor.getAllValues()).containsExactly(0, BATCH_SIZE);

		// every key handed out by the fake repository must have reached the processor exactly once: nothing
		// silently truncated (all 750 seen) and nothing double-processed (no key seen twice)
		assertThat(keysSeen).hasSize(allKeys.size());
		assertThat(new HashSet<>(keysSeen)).hasSize(allKeys.size()); // no duplicates
		assertThat(keysSeen).containsExactlyInAnyOrderElementsOf(allKeys); // nothing missing, nothing foreign

		assertThat(summary.getKeysProcessed()).isEqualTo(allKeys.size());
		assertThat(summary.getKeysChanged()).isZero();
	}

	@Test
	void selectionThatNeverShrinksBelowAFullBatch_abortsAfterMaxLoops()
	{
		// a pagination bug that never advances (e.g. a stuck OFFSET): every page comes back full, so the drain
		// loop never sees the batch.size() < BATCH_SIZE signal that would end it normally
		final List<StockDataRecordIdentifier> fullBatch = distinctKeys(BATCH_SIZE);
		when(stockRepository.retrieveKeys(any(), any(), any(), anyInt(), anyInt()))
				.thenReturn(ImmutableList.copyOf(fullBatch));

		// only a counter, no key recording: 10,000 rounds * 500 keys/round = 5,000,000 calls, and retaining that
		// many keys would itself blow the test heap
		final AtomicLong processorCalls = new AtomicLong();

		assertThatThrownBy(() -> drainer.drain(AtpKeySelection.ALL, key -> {
			processorCalls.incrementAndGet();
			return false;
		}))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("aborted after " + MAX_LOOPS)
				.hasMessageContaining("never shrank below a full batch");

		// exactly MAX_LOOPS rounds must have been drawn before the backstop fires - not one more, not one fewer
		verify(stockRepository, times(MAX_LOOPS)).retrieveKeys(any(), any(), any(), anyInt(), anyInt());
		assertThat(processorCalls.get()).isEqualTo(((long)MAX_LOOPS) * BATCH_SIZE);
	}

	/**
	 * The selection is what makes the drain restrictable, so it must reach the repository verbatim - a filter
	 * silently dropped here would widen a run the operator restricted on purpose to the whole database.
	 */
	@Test
	void selectionFilters_areHandedToTheRepositoryVerbatim()
	{
		when(stockRepository.retrieveKeys(any(), any(), any(), anyInt(), anyInt()))
				.thenReturn(ImmutableList.of());

		final ProductId productId = ProductId.ofRepoId(1000003);
		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(1000004);

		drainer.drain(
				AtpKeySelection.builder()
						.warehouseId(WAREHOUSE_ID)
						.productId(productId)
						.productCategoryId(productCategoryId)
						.build(),
				key -> false);

		verify(stockRepository).retrieveKeys(WAREHOUSE_ID, productId, productCategoryId, BATCH_SIZE, 0);
	}

	private static List<StockDataRecordIdentifier> distinctKeys(final int count)
	{
		final List<StockDataRecordIdentifier> keys = new ArrayList<>(count);
		for (int i = 0; i < count; i++)
		{
			keys.add(StockDataRecordIdentifier.builder()
					.clientId(CLIENT_ID)
					.orgId(ORG_ID)
					.warehouseId(WAREHOUSE_ID)
					.productId(ProductId.ofRepoId(2_000_000 + i))
					.storageAttributesKey(AttributesKey.NONE)
					.build());
		}
		return keys;
	}
}
