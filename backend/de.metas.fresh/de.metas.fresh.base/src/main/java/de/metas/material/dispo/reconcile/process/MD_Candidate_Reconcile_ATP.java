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

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.common.util.time.SystemTime;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.dispo.reconcile.AtpDivergence;
import de.metas.material.dispo.reconcile.AtpKeySelection;
import de.metas.material.dispo.reconcile.AtpKeySelectionDrainer;
import de.metas.material.dispo.reconcile.AtpReconciliationCommand;
import de.metas.material.dispo.reconcile.AtpReconciliationRunRequest;
import de.metas.material.dispo.reconcile.AtpTargetCalculator;
import de.metas.material.dispo.reconcile.async.AtpReconciliationEnqueueService;
import de.metas.material.dispo.reconcile.async.AtpReconciliationWorkpackageProcessor;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * The operator-invocable entry point into the ATP reconciliation: selects reconciliation keys by an optional
 * warehouse/product/product-category filter and either previews or performs the reconciliation of each one.
 * <p>
 * <b>The two paths are deliberately different, and this is the class where they part.</b>
 * <ul>
 * <li><b>Dry run</b> ({@code IsDryRun = 'Y'}) is computed here, synchronously, and reported in this process's own
 * log. It needs only {@link AtpTargetCalculator#computeDivergence}, which is un-{@code @Profile}-guarded on
 * purpose (see that class), so the preview works in the webapi - the JVM a WebUI-launched {@code AD_Process}
 * actually executes in. Nothing is written, and {@link AtpReconciliationCommand} is never touched.</li>
 * <li><b>A real run</b> is <i>enqueued</i>: this process writes a {@code C_Queue_WorkPackage} carrying the whole
 * run and returns, and {@link AtpReconciliationWorkpackageProcessor} performs the reconciliation in the app
 * server. It has to: the reconciliation needs {@link AtpReconciliationCommand}, which is
 * {@code @Profile(Profiles.PROFILE_MaterialDispo)}, and that profile is active only in the app server. (
 * {@code AD_Process.IsServerProcess} is not an escape hatch - it only affects Swing class loading.) So a real
 * run's result is <b>asynchronous</b> and is reported on the work package, not in this process's log.</li>
 * </ul>
 * The consequence the two paths must not suffer is drifting apart. The batched key drain - page size, pagination
 * and the runaway backstop - therefore lives once, in {@link AtpKeySelectionDrainer}, and both paths call it.
 * <p>
 * {@code IsDryRun} defaults to {@code 'N'} (off) - matching the house convention for comparable process-level
 * preview/simulation parameters (e.g. {@code IsSimulation} on the Commission Overview process, {@code IsTest} on
 * {@code DLM_Partition_Migrate}, both default {@code 'N'}) - so an operator who launches this process and accepts
 * every default runs a real, data-writing reconciliation; the checkbox must be ticked explicitly to preview only.
 * <p>
 * <b>Liveness cutoff:</b> {@code LivenessCutoffDate}, when given, is converted to an {@link Instant} at the
 * start of that day in the system time zone and passed through as the liveness cutoff, so a candidate dated
 * strictly before it is treated as closed regardless of what its source document says.
 */
public class MD_Candidate_Reconcile_ATP extends JavaProcess
{
	private final AtpTargetCalculator atpTargetCalculator =
			SpringContextHolder.getBeanOrSupply(AtpTargetCalculator.class, AtpTargetCalculator::newInstanceForUnitTesting);
	private final AtpKeySelectionDrainer keyDrainer =
			SpringContextHolder.getBeanOrSupply(AtpKeySelectionDrainer.class, AtpKeySelectionDrainer::newInstanceForUnitTesting);
	private final AtpReconciliationEnqueueService enqueueService =
			SpringContextHolder.getBeanOrSupply(AtpReconciliationEnqueueService.class, AtpReconciliationEnqueueService::new);

	@Param(parameterName = I_MD_Stock.COLUMNNAME_M_Warehouse_ID, mandatory = false)
	private int p_M_Warehouse_ID;

	@Param(parameterName = I_MD_Stock.COLUMNNAME_M_Product_ID, mandatory = false)
	private int p_M_Product_ID;

	@Param(parameterName = I_M_Product.COLUMNNAME_M_Product_Category_ID, mandatory = false)
	private int p_M_Product_Category_ID;

	@Param(parameterName = "IsDryRun", mandatory = true)
	private boolean p_IsDryRun;

	@Param(parameterName = "LivenessCutoffDate", mandatory = false)
	private LocalDate p_LivenessCutoffDate;

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final AtpReconciliationRunRequest request = createRunRequest();

		if (p_IsDryRun)
		{
			previewInline(request);
		}
		else
		{
			enqueueForTheAppServer(request);
		}

		return MSG_OK;
	}

	/**
	 * Reports, per key of the selection, the divergence a real run would correct - and writes nothing.
	 * <p>
	 * Deliberately computed from {@link AtpTargetCalculator#computeDivergence} rather than from
	 * {@link AtpReconciliationCommand#reconcileAndLog} with its {@code dryRun} flag set: the two produce the same
	 * numbers (that flag makes {@code reconcileAndLog} return exactly the divergence and nothing else), but going
	 * through the command would require the command <i>bean</i>, which does not exist in the webapi - so the
	 * preview an operator asks for would fail there instead of previewing.
	 */
	private void previewInline(@NonNull final AtpReconciliationRunRequest request)
	{
		final AtpKeySelectionDrainer.DrainSummary summary = keyDrainer.drain(
				request.getSelection(),
				key -> previewOneKey(request, key));

		addLog("Dry run: {} of {} matching key(s) would change; nothing was written",
				summary.getKeysChanged(), summary.getKeysProcessed());
	}

	/**
	 * @return {@code true} when this key's stored projection diverges from the target, i.e. a real run would
	 * change it - and logs by how much; {@code false} when the key is already correct, so there is nothing to
	 * report for it
	 */
	private boolean previewOneKey(
			@NonNull final AtpReconciliationRunRequest request,
			@NonNull final StockDataRecordIdentifier key)
	{
		final AtpDivergence divergence = atpTargetCalculator.computeDivergence(
				key, request.getRunDate(), request.getLivenessCutoff());

		if (divergence.getDifference().signum() == 0)
		{
			return false;
		}

		addLog("{}: stored ATP {} would change to {} (difference {})",
				key, divergence.getStoredAtp(), divergence.getExpectedAtp(), divergence.getDifference());
		return true;
	}

	/**
	 * Hands the run to the async queue and tells the operator where its result will appear.
	 * <p>
	 * The selection is deliberately <i>not</i> drained here: one work package carries the filter itself, so the
	 * keys are read in the JVM that can also reconcile them. Reading them here would only produce a snapshot that
	 * is already stale by the time the app server picks the package up.
	 */
	private void enqueueForTheAppServer(@NonNull final AtpReconciliationRunRequest request)
	{
		final I_C_Queue_WorkPackage workPackage = enqueueService.enqueue(request);

		addLog("Enqueued work package {} for the reconciliation - it runs in the application server, where the"
						+ " material disposition engine is, and reports what it changed on that work package",
				workPackage.getC_Queue_WorkPackage_ID());
	}

	/** @return this run exactly as the operator parameterised it, with the run date pinned to now */
	private AtpReconciliationRunRequest createRunRequest()
	{
		return AtpReconciliationRunRequest.builder()
				.selection(AtpKeySelection.builder()
						.warehouseId(WarehouseId.ofRepoIdOrNull(p_M_Warehouse_ID))
						.productId(ProductId.ofRepoIdOrNull(p_M_Product_ID))
						.productCategoryId(ProductCategoryId.ofRepoIdOrNull(p_M_Product_Category_ID))
						.build())
				.runDate(SystemTime.asInstant())
				.livenessCutoff(toInstantOrNull(p_LivenessCutoffDate))
				.build();
	}

	@Nullable
	private static Instant toInstantOrNull(@Nullable final LocalDate date)
	{
		return date != null ? date.atStartOfDay(SystemTime.zoneId()).toInstant() : null;
	}
}
