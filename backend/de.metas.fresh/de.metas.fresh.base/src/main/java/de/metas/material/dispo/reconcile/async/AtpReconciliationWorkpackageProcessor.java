package de.metas.material.dispo.reconcile.async;

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
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.dispo.reconcile.AtpDivergence;
import de.metas.material.dispo.reconcile.AtpKeySelectionDrainer;
import de.metas.material.dispo.reconcile.AtpReconciliationCommand;
import de.metas.material.dispo.reconcile.AtpReconciliationRunLog;
import de.metas.material.dispo.reconcile.AtpReconciliationRunRequest;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import javax.annotation.Nullable;

/**
 * Performs the real, data-writing ATP reconciliation that {@code MD_Candidate_Reconcile_ATP} enqueued - in the
 * app server, which is the only JVM that has the material disposition engine.
 * <p>
 * This is the write half of the process's split. The read half (the dry-run preview) stays synchronous in the
 * launching process, because it needs only the un-{@code @Profile}-guarded {@code AtpTargetCalculator}; see
 * {@link AtpReconciliationEnqueueService} for why the write half cannot stay there.
 * <p>
 * The selection is drained through the very same {@link AtpKeySelectionDrainer} the preview uses, so a preview
 * and the run it previews cannot walk different key sets. Per-key results are written to the work package's own
 * log via {@link Loggables}, which is where the operator finds them: the launching process returns as soon as the
 * work package is enqueued, so its process log cannot contain them.
 */
public class AtpReconciliationWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private final AtpKeySelectionDrainer keyDrainer =
			SpringContextHolder.getBeanOrSupply(AtpKeySelectionDrainer.class, AtpKeySelectionDrainer::newInstanceForUnitTesting);

	/**
	 * @return {@code false}. The reconciliation hands every correction to {@code CandidateChangeService}, i.e. to
	 * the engine's own candidate-change handling, which manages its transactions itself - which is also why the
	 * launching process is {@code @RunOutOfTrx}. Wrapping a drain of a possibly very large selection in one
	 * enclosing transaction would additionally make the whole run all-or-nothing, so a single bad key would
	 * discard every correct correction before it.
	 */
	@Override
	public boolean isRunInTransaction()
	{
		return false;
	}

	@Override
	public Result processWorkPackage(
			@NonNull final I_C_Queue_WorkPackage workPackage,
			@Nullable final String localTrxName)
	{
		final AtpReconciliationRunRequest request = AtpReconciliationEnqueueService.extractRunRequest(getParameters());
		final AtpReconciliationCommand reconciliationCommand = reconciliationCommand();

		final AtpKeySelectionDrainer.DrainSummary summary = keyDrainer.drain(
				request.getSelection(),
				key -> reconcileOneKey(reconciliationCommand, request, key));

		Loggables.addLog("Reconciled {} of {} matching key(s)", summary.getKeysChanged(), summary.getKeysProcessed());

		return Result.SUCCESS;
	}

	/**
	 * @return the {@link AtpReconciliationCommand} bean.
	 * <p>
	 * Concrete failure this prevents: {@link AtpReconciliationCommand} is
	 * {@code @Profile(Profiles.PROFILE_MaterialDispo)} - see its Javadoc for why it has to be. This work package
	 * is drained by the app server, which reads its active profiles from the
	 * {@code de.metas.spring.profiles.active} sysconfigs; a deployment whose app server does not list the material
	 * disposition profile there has no such bean, and without this method the work package would fail with
	 * Spring's bare {@code NoSuchBeanDefinitionException} naming only the type - buried in the queue, where no
	 * operator is watching a process window and nothing would say what to configure. Wrapping it names the
	 * missing profile, the sysconfig that activates it and the JVM that has to have it.
	 */
	private AtpReconciliationCommand reconciliationCommand()
	{
		try
		{
			return SpringContextHolder.instance.getBean(AtpReconciliationCommand.class);
		}
		catch (final NoSuchBeanDefinitionException e)
		{
			throw new AdempiereException("The ATP reconciliation work package needs the material disposition"
					+ " engine, which is not present in the application that picked it up: the spring profile "
					+ Profiles.PROFILE_MaterialDispo + " is not active here."
					+ " This work package is meant to be drained by the app server, which reads its active"
					+ " profiles from the de.metas.spring.profiles.active sysconfigs - add "
					+ Profiles.PROFILE_MaterialDispo + " there and restart the app server, then re-enqueue this"
					+ " work package.", e);
		}
	}

	/**
	 * Reconciles one key and writes what changed to the work package log.
	 *
	 * @return {@code true} when the key's stored projection diverged from the target, i.e. this call corrected it;
	 * {@code false} when the key was already correct, so there is nothing to report for it
	 */
	private static boolean reconcileOneKey(
			@NonNull final AtpReconciliationCommand reconciliationCommand,
			@NonNull final AtpReconciliationRunRequest request,
			@NonNull final StockDataRecordIdentifier key)
	{
		final AtpReconciliationRunLog runLog = reconciliationCommand.reconcileAndLog(
				key,
				request.getRunDate(),
				false, // never a dry run: a dry run is previewed synchronously and is never enqueued
				request.getLivenessCutoff());

		final AtpDivergence divergence = runLog.getDivergence();
		if (divergence.getDifference().signum() == 0)
		{
			return false;
		}

		for (final AtpReconciliationRunLog.Entry entry : runLog.getEntries())
		{
			Loggables.addLog("{}: STOCK candidate {} changed from {} to {}",
					key, entry.getCandidateId(), entry.getQtyBefore(), entry.getQtyAfter());
		}
		return true;
	}
}
