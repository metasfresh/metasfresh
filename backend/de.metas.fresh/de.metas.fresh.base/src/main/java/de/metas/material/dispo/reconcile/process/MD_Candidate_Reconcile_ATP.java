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

import de.metas.Profiles;
import de.metas.common.util.time.SystemTime;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.dispo.reconcile.AtpDivergence;
import de.metas.material.dispo.reconcile.AtpKeySelection;
import de.metas.material.dispo.reconcile.AtpKeySelectionDrainer;
import de.metas.material.dispo.reconcile.AtpReconciliationCommand;
import de.metas.material.dispo.reconcile.AtpReconciliationRunLog;
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
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * The operator-invocable entry point for {@link AtpReconciliationCommand}: selects reconciliation keys by an
 * optional warehouse/product/product-category filter and reconciles each one, in dry-run mode when asked.
 * <p>
 * Structured after the sibling
 * {@code de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs}: a plain {@link JavaProcess} with
 * {@link RunOutOfTrx} on {@link #doIt()} and a batched drain of the matching keys - the drain itself living in
 * {@link AtpKeySelectionDrainer}, so its page size, pagination and runaway backstop are stated once rather than
 * per caller.
 * <p>
 * <b>Dry run:</b> {@code IsDryRun} is passed straight through to
 * {@link AtpReconciliationCommand#reconcileAndLog(StockDataRecordIdentifier, Instant, boolean, Instant)} - on a dry
 * run nothing is written (see that method's own contract), and this process reports the {@link AtpDivergence} it
 * would have corrected instead of the {@link AtpReconciliationRunLog.Entry} rows a real run actually changed.
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
	private final AtpKeySelectionDrainer keyDrainer =
			SpringContextHolder.getBeanOrSupply(AtpKeySelectionDrainer.class, AtpKeySelectionDrainer::newInstanceForUnitTesting);

	/** Resolved once, on first use, by {@link #reconciliationCommand()} - never in a field initializer. */
	@Nullable private AtpReconciliationCommand reconciliationCommand;

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
		final Instant runDate = SystemTime.asInstant();
		final Instant livenessCutoff = toInstantOrNull(p_LivenessCutoffDate);

		// Resolved up front, and deliberately not only inside the loop below: the concrete failure this
		// prevents is a run in a JVM without the material-disposition profile whose selection happens to
		// match no key at all. The loop body would then never execute, the command bean would never be
		// resolved, and the process would report "Reconciled 0 of 0 matching key(s)" as a success - telling
		// an operator the engine is present when it is not. Failing here makes that answer impossible.
		reconciliationCommand();

		final AtpKeySelectionDrainer.DrainSummary summary = keyDrainer.drain(
				createKeySelection(),
				key -> reconcileOneKey(key, runDate, livenessCutoff));

		if (p_IsDryRun)
		{
			addLog("Dry run: {} of {} matching key(s) would change; nothing was written",
					summary.getKeysChanged(), summary.getKeysProcessed());
		}
		else
		{
			addLog("Reconciled {} of {} matching key(s)", summary.getKeysChanged(), summary.getKeysProcessed());
		}

		return MSG_OK;
	}

	/**
	 * @return the {@link AtpReconciliationCommand} bean, resolved on first use and then cached for the rest of this
	 * run.
	 * <p>
	 * Concrete failure this prevents: {@link AtpReconciliationCommand} is
	 * {@code @Profile(Profiles.PROFILE_MaterialDispo)} - see its Javadoc for why it has to be - and that profile is
	 * active only in the app server, not in the webapi, which is where a process launched from the WebUI actually
	 * runs. Without this method the bean was resolved in a <i>field initializer</i>, so such a run died while the
	 * process object was still being constructed, with Spring's bare {@code NoSuchBeanDefinitionException} naming
	 * only the type - nothing about the profile, and nothing an operator or a support engineer could act on.
	 * Resolving here instead puts the failure inside {@link #doIt()}, on the process framework's ordinary
	 * error-reporting path, and names both the missing profile and the JVM that has it.
	 */
	private AtpReconciliationCommand reconciliationCommand()
	{
		if (reconciliationCommand == null)
		{
			try
			{
				reconciliationCommand = SpringContextHolder.instance.getBean(AtpReconciliationCommand.class);
			}
			catch (final NoSuchBeanDefinitionException e)
			{
				throw new AdempiereException("MD_Candidate_Reconcile_ATP needs the material disposition engine,"
						+ " which is not present in this application: the spring profile "
						+ Profiles.PROFILE_MaterialDispo + " is not active here."
						+ " This process is not invocable from the WebUI in this deployment: the app server reads its"
						+ " active profiles from the de.metas.spring.profiles.active sysconfigs, while the webapi -"
						+ " where a WebUI-launched process actually runs - reads a different prefix,"
						+ " de.metas.ui.web.spring.profiles.active.", e);
			}
		}
		return reconciliationCommand;
	}

	/**
	 * @return {@code true} when {@code key}'s stored projection diverged from the target - i.e. this call either
	 * reconciled it (real run) or found a value it would have reconciled (dry run) - and logged what changed/would
	 * change; {@code false} when the key was already correct, so there is nothing to report for it.
	 */
	boolean reconcileOneKey(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant runDate,
			@Nullable final Instant livenessCutoff)
	{
		final AtpReconciliationRunLog runLog = reconciliationCommand().reconcileAndLog(key, runDate, p_IsDryRun, livenessCutoff);
		final AtpDivergence divergence = runLog.getDivergence();
		if (divergence.getDifference().signum() == 0)
		{
			return false;
		}

		if (p_IsDryRun)
		{
			addLog("{}: stored ATP {} would change to {} (difference {})",
					key, divergence.getStoredAtp(), divergence.getExpectedAtp(), divergence.getDifference());
		}
		else
		{
			for (final AtpReconciliationRunLog.Entry entry : runLog.getEntries())
			{
				addLog("{}: STOCK candidate {} changed from {} to {}",
						key, entry.getCandidateId(), entry.getQtyBefore(), entry.getQtyAfter());
			}
		}
		return true;
	}

	/** @return this run's warehouse/product/product-category filter; an unset parameter is not restricted on */
	private AtpKeySelection createKeySelection()
	{
		return AtpKeySelection.builder()
				.warehouseId(WarehouseId.ofRepoIdOrNull(p_M_Warehouse_ID))
				.productId(ProductId.ofRepoIdOrNull(p_M_Product_ID))
				.productCategoryId(ProductCategoryId.ofRepoIdOrNull(p_M_Product_Category_ID))
				.build();
	}

	@Nullable
	private static Instant toInstantOrNull(@Nullable final LocalDate date)
	{
		return date != null ? date.atStartOfDay(SystemTime.zoneId()).toInstant() : null;
	}
}
