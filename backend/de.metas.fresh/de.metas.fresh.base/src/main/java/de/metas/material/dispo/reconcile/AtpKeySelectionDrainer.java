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
 * <b>Why its own collaborator:</b> the reconciliation runs on two paths - the webapi's synchronous dry-run preview
 * and the app server's real, writing run ({@code AtpReconciliationWorkpackageProcessor}) - and the page size,
 * pagination arithmetic and runaway backstop must not drift apart between them; a preview walking a different key
 * set than the run it previews would be worse than none.
 * <p>
 * Not folded in (yet): {@code MD_Candidate_ATP_Divergence_Report} carries a third, structurally identical drain -
 * unifying it is a change to that process's own covering tests, not part of this split.
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
	 * Backstop against a pagination bug that never converges (e.g. an {@code OFFSET} that stops advancing): a
	 * healthy run always empties the (static) selection in a bounded number of rounds. {@link #BATCH_SIZE} *
	 * {@link #MAX_LOOPS} = 5,000,000 keys, far past any real selection size (the largest measured candidate chain
	 * for this feature was 913 rows for a single product).
	 */
	@VisibleForTesting
	public static final int MAX_LOOPS = 10_000;

	@NonNull private final StockRepository stockRepository;

	/**
	 * @return an instance for a plain JUnit test - whatever is registered on {@link SpringContextHolder} wins, so a
	 * test that registered a mocked {@link StockRepository} gets a drainer paging through that mock.
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
				// a bounded, reportable failure instead of an infinite loop from a stuck OFFSET
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
	 * isn't a test on the key, it's the processor reporting whether that key needed a change - what
	 * {@link DrainSummary#getKeysChanged()} counts.
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
