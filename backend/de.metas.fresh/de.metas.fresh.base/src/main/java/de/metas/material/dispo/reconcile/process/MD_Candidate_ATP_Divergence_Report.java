package de.metas.material.dispo.reconcile.process;

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

import de.metas.common.util.time.SystemTime;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.material.dispo.reconcile.AtpDivergence;
import de.metas.material.dispo.reconcile.AtpTargetCalculator;
import de.metas.material.dispo.reconcile.UncoveredSourceDocumentService;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/**
 * The read-only counterpart of {@code MD_Candidate_Reconcile_ATP}: reports, per reconciliation key, the
 * divergence {@link AtpTargetCalculator#computeDivergence} finds between the stored and the expected ATP,
 * and separately every open {@code M_ShipmentSchedule}/{@code M_ReceiptSchedule} that no candidate
 * references at all - the one gap a recompute cannot close, so it is surfaced here instead of silently
 * absorbed. Writes nothing: no {@code MD_Candidate}, no {@code MD_Stock}, no backup row, no run log -
 * the process log is the only output.
 * <p>
 * Structured after the sibling {@code MD_Candidate_Reconcile_ATP}: a plain {@link JavaProcess} with
 * {@link RunOutOfTrx} on {@link #doIt()}, and the selection drained in bounded batches of
 * {@link #BATCH_SIZE}, backstopped by {@link #MAX_LOOPS} against a pagination bug that never terminates.
 * <p>
 * Every collaborator here - {@link StockRepository}, {@link AtpTargetCalculator},
 * {@link UncoveredSourceDocumentService} - is a plain, un-{@code @Profile}-guarded bean (see
 * {@link AtpTargetCalculator}'s own package Javadoc), so unlike the write-side process this class needs no
 * profile-presence check: it is invocable from the WebUI (the webapi JVM) exactly as it is from the app
 * server.
 */
public class MD_Candidate_ATP_Divergence_Report extends JavaProcess
{
	/** How many rows are fetched and reported per round, for every one of the three selections below. */
	private static final int BATCH_SIZE = 500;

	/**
	 * Backstop against a pagination bug that never converges (e.g. an {@code OFFSET} that stops advancing): every
	 * selection this process reads is static, so a healthy run always drains it in a small, bounded number of
	 * rounds. {@link #BATCH_SIZE} * {@link #MAX_LOOPS} = 5,000,000 rows, far past any real selection size for this
	 * feature (the largest real candidate chain measured for this issue was 913 rows for a single product).
	 */
	private static final int MAX_LOOPS = 10_000;

	// Justification for >1 collaborator (docs/coding-rules/architecture.md §4 "at most one service"):
	// this process reports two functionally disjoint things in one run - the stored-vs-expected ATP
	// divergence (stockRepository + atpTargetCalculator) and the separate "uncovered open source document"
	// gap (uncoveredSourceDocumentService) - each needing its own collaborator subset, with no overlap
	// between the two that would justify merging them into a single service.
	private final StockRepository stockRepository = SpringContextHolder.getBeanOrSupply(StockRepository.class, StockRepository::new);
	private final AtpTargetCalculator atpTargetCalculator =
			SpringContextHolder.getBeanOrSupply(AtpTargetCalculator.class, AtpTargetCalculator::newInstanceForUnitTesting);
	private final UncoveredSourceDocumentService uncoveredSourceDocumentService =
			SpringContextHolder.getBeanOrSupply(UncoveredSourceDocumentService.class, UncoveredSourceDocumentService::newInstanceForUnitTesting);

	@Param(parameterName = I_MD_Stock.COLUMNNAME_M_Warehouse_ID, mandatory = false)
	private int p_M_Warehouse_ID;

	@Param(parameterName = I_MD_Stock.COLUMNNAME_M_Product_ID, mandatory = false)
	private int p_M_Product_ID;

	@Param(parameterName = I_M_Product.COLUMNNAME_M_Product_Category_ID, mandatory = false)
	private int p_M_Product_Category_ID;

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final Instant runDate = SystemTime.asInstant();
		final WarehouseId warehouseId = p_M_Warehouse_ID > 0 ? WarehouseId.ofRepoId(p_M_Warehouse_ID) : null;
		final ProductId productId = p_M_Product_ID > 0 ? ProductId.ofRepoId(p_M_Product_ID) : null;
		final ProductCategoryId productCategoryId = p_M_Product_Category_ID > 0 ? ProductCategoryId.ofRepoId(p_M_Product_Category_ID) : null;

		reportDivergences(runDate, warehouseId, productId, productCategoryId);
		reportUncoveredOpenDocuments(warehouseId, productId, productCategoryId);

		return MSG_OK;
	}

	/**
	 * Reports every key whose stored ATP diverges from {@link AtpTargetCalculator#computeDivergence}'s target -
	 * a key that is already correct produces no log line at all, only the summary count.
	 */
	private void reportDivergences(
			@NonNull final Instant runDate,
			@Nullable final WarehouseId warehouseId,
			@Nullable final ProductId productId,
			@Nullable final ProductCategoryId productCategoryId)
	{
		int keysChecked = 0;
		int keysDiverged = 0;
		int offset = 0;
		int loops = 0;

		List<StockDataRecordIdentifier> batch;
		do
		{
			loops++;
			if (loops > MAX_LOOPS)
			{
				// concrete failure this prevents: a pagination bug (e.g. an OFFSET that never advances) turning
				// this into an infinite loop instead of a bounded, reportable failure
				throw new AdempiereException("MD_Candidate_ATP_Divergence_Report aborted after " + MAX_LOOPS
						+ " rounds of " + BATCH_SIZE + " keys each - the selection never shrank below a full batch");
			}

			batch = stockRepository.retrieveKeys(warehouseId, productId, productCategoryId, BATCH_SIZE, offset);
			for (final StockDataRecordIdentifier key : batch)
			{
				keysChecked++;
				final AtpDivergence divergence = atpTargetCalculator.computeDivergence(key, runDate);
				if (divergence.getDifference().signum() != 0)
				{
					keysDiverged++;
					addLog("{}: expectedAtp={}, storedAtp={}, difference={}",
							key, divergence.getExpectedAtp(), divergence.getStoredAtp(), divergence.getDifference());
				}
			}
			offset += BATCH_SIZE;
		}
		while (batch.size() == BATCH_SIZE);

		addLog("Checked {} key(s); {} diverged", keysChecked, keysDiverged);
	}

	/**
	 * Reports every open source document - {@code M_ShipmentSchedule} and {@code M_ReceiptSchedule} - that no
	 * candidate references at all: the one gap {@link AtpTargetCalculator} cannot close by recomputing, since
	 * there is no candidate to correct.
	 */
	private void reportUncoveredOpenDocuments(
			@Nullable final WarehouseId warehouseId,
			@Nullable final ProductId productId,
			@Nullable final ProductCategoryId productCategoryId)
	{
		final int shipmentScheduleCount = drainAndLog(
				offset -> uncoveredSourceDocumentService.retrieveOpenShipmentScheduleIdsWithoutCandidate(
						warehouseId, productId, productCategoryId, BATCH_SIZE, offset),
				id -> addLog("Uncovered open M_ShipmentSchedule {}", id));

		final int receiptScheduleCount = drainAndLog(
				offset -> uncoveredSourceDocumentService.retrieveOpenReceiptScheduleIdsWithoutCandidate(
						warehouseId, productId, productCategoryId, BATCH_SIZE, offset),
				id -> addLog("Uncovered open M_ReceiptSchedule {}", id));

		addLog("{} open source document(s) with no candidate at all", shipmentScheduleCount + receiptScheduleCount);
	}

	/**
	 * Drains a paginated {@code (offset) -> page} source in rounds of {@link #BATCH_SIZE}, logging every item via
	 * {@code perItemLogger} - the shape shared by {@link #reportUncoveredOpenDocuments}'s two selections, extracted
	 * so the {@link #MAX_LOOPS} backstop exists exactly once instead of being copy-pasted per selection.
	 *
	 * @return the total number of items drained
	 */
	private int drainAndLog(
			@NonNull final IntFunction<List<Integer>> pageFetcher,
			@NonNull final Consumer<Integer> perItemLogger)
	{
		int count = 0;
		int offset = 0;
		int loops = 0;

		List<Integer> batch;
		do
		{
			loops++;
			if (loops > MAX_LOOPS)
			{
				// same concrete failure as reportDivergences' own backstop: a pagination bug must abort visibly
				// instead of looping forever
				throw new AdempiereException("MD_Candidate_ATP_Divergence_Report aborted after " + MAX_LOOPS
						+ " rounds of " + BATCH_SIZE + " rows each - the selection never shrank below a full batch");
			}

			batch = pageFetcher.apply(offset);
			for (final Integer id : batch)
			{
				count++;
				perItemLogger.accept(id);
			}
			offset += BATCH_SIZE;
		}
		while (batch.size() == BATCH_SIZE);

		return count;
	}
}
