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

import com.google.common.annotations.VisibleForTesting;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Walks the reconciliation keys matching an {@link AtpKeySelection} in bounded pages and hands each one to a
 * caller-supplied {@link KeyProcessor}.
 * <p>
 * <b>Why this is its own collaborator.</b> The reconciliation now runs on two paths - the operator's synchronous
 * dry-run preview in the webapi ({@code MD_Candidate_Reconcile_ATP}) and the real, writing run in the app server
 * ({@code de.metas.material.dispo.reconcile.async.AtpReconciliationWorkpackageProcessor}) - and the recorded
 * downside of that split is exactly "two execution paths to keep in step". The page size, the pagination
 * arithmetic and the runaway backstop are the part that would silently drift apart, so they live here once and
 * both paths call in; a preview that walked a different key set from the run it previews would be worse than no
 * preview at all.
 * <p>
 * Not folded in (yet): {@code MD_Candidate_ATP_Divergence_Report} carries a third, structurally identical key
 * drain. It is a separate process with its own pagination and backstop tests, so unifying it is a change to
 * <i>that</i> process's covering tests rather than part of this split.
 * <p>
 * The actual persistence query stays in {@link StockRepository} - see {@code docs/REVIEW.md} on keeping
 * {@code IQueryBL}/{@code IQueryBuilder} out of a {@code @Service}.
 */
@Service
@RequiredArgsConstructor
public class AtpKeySelectionDrainer
{
	/** How many matching keys are fetched and handed to the {@link KeyProcessor} per round. */
	@VisibleForTesting
	public static final int BATCH_SIZE = 500;

	/**
	 * Backstop against a pagination bug that never converges (e.g. an {@code OFFSET} that stops advancing): the
	 * selection drained here is static, so a healthy run always empties it in a small, bounded number of rounds.
	 * {@link #BATCH_SIZE} * {@link #MAX_LOOPS} = 5,000,000 keys, far past any real selection size for this feature
	 * (the largest real candidate chain measured for this issue was 913 rows for a single product).
	 */
	@VisibleForTesting
	public static final int MAX_LOOPS = 10_000;

	@NonNull private final StockRepository stockRepository;

	/**
	 * @return an instance for a plain JUnit test, wired the same way the divergence-report process wires its own
	 * collaborators: whatever is registered on {@link SpringContextHolder} wins, so a test that registered a mocked
	 * {@link StockRepository} gets a drainer paging through that mock.
	 */
	@VisibleForTesting
	public static AtpKeySelectionDrainer newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				AtpKeySelectionDrainer.class,
				() -> new AtpKeySelectionDrainer(SpringContextHolder.getBeanOrSupply(StockRepository.class, StockRepository::new)));
	}

	/**
	 * Hands every key of {@code selection} to {@code keyProcessor} exactly once, one page of at most
	 * {@link #BATCH_SIZE} keys at a time.
	 * <p>
	 * Pages are ordered by {@code MD_Stock_ID} (see {@link StockRepository#retrieveKeys}), so successive rounds
	 * walk the same static selection without gaps or repeats. The loop ends on the first page that comes back
	 * shorter than a full batch.
	 *
	 * @return how many keys were seen and how many of them the processor reported as changed
	 * @throws AdempiereException when the selection never shrinks below a full batch within {@link #MAX_LOOPS}
	 * rounds - see that constant's Javadoc
	 */
	public DrainSummary drain(
			@NonNull final AtpKeySelection selection,
			@NonNull final KeyProcessor keyProcessor)
	{
		int offset = 0;
		int loops = 0;
		int keysProcessed = 0;
		int keysChanged = 0;

		List<StockDataRecordIdentifier> batch;
		do
		{
			loops++;
			if (loops > MAX_LOOPS)
			{
				// concrete failure this prevents: a pagination bug (e.g. an OFFSET that never advances) turning
				// this into an infinite loop instead of a bounded, reportable failure
				throw new AdempiereException("ATP reconciliation key drain aborted after " + MAX_LOOPS
						+ " rounds of " + BATCH_SIZE + " keys each - the selection never shrank below a full batch");
			}

			batch = retrieveKeys(selection, offset);
			for (final StockDataRecordIdentifier key : batch)
			{
				keysProcessed++;
				if (keyProcessor.process(key))
				{
					keysChanged++;
				}
			}
			offset += BATCH_SIZE;
		}
		while (batch.size() == BATCH_SIZE);

		return DrainSummary.of(keysProcessed, keysChanged);
	}

	private List<StockDataRecordIdentifier> retrieveKeys(
			@NonNull final AtpKeySelection selection,
			final int offset)
	{
		return stockRepository.retrieveKeys(
				selection.getWarehouseId(),
				selection.getProductId(),
				selection.getProductCategoryId(),
				BATCH_SIZE,
				offset);
	}

	/**
	 * What a caller does with one key. Deliberately not a {@code java.util.function.Predicate}: the return value
	 * is not a test on the key, it is the processor <i>reporting</i> whether that key turned out to need a change,
	 * which is what {@link DrainSummary#getKeysChanged()} counts.
	 */
	@FunctionalInterface
	public interface KeyProcessor
	{
		/**
		 * @return {@code true} when this key diverged - i.e. the caller reconciled it, or (previewing) found a
		 * value it would have reconciled; {@code false} when the key was already correct and there is nothing to
		 * report for it
		 */
		boolean process(@NonNull StockDataRecordIdentifier key);
	}

	/** The tally of one {@link #drain} call, which is all either path needs for its summary log line. */
	@Value(staticConstructor = "of")
	public static class DrainSummary
	{
		/** How many keys of the selection were handed to the processor. */
		int keysProcessed;

		/** How many of those the processor reported as diverging. */
		int keysChanged;
	}
}
