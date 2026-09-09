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

import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.dispo.reconcile.AtpKeySelection;
import de.metas.material.dispo.reconcile.AtpReconciliationRunRequest;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IParams;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Properties;

/**
 * Puts one {@link AtpReconciliationRunRequest} on the async queue, and reads it back off again.
 * <p>
 * <b>Why the write path is enqueued at all.</b> {@code MD_Candidate_Reconcile_ATP}'s real run needs
 * {@code AtpReconciliationCommand}, which is {@code @Profile(Profiles.PROFILE_MaterialDispo)} because the whole
 * dispo engine behind it must live in exactly one JVM. A WebUI-launched {@code AD_Process} executes in the
 * <i>webapi</i>, which never activates that profile, so the reconciliation cannot happen there at any price -
 * {@code AD_Process.IsServerProcess} does not help, it only affects Swing class loading. The queue is the
 * established route from the webapi into the app server, so the process enqueues and
 * {@link AtpReconciliationWorkpackageProcessor} - drained by the app server, where the profile is active - does
 * the work.
 * <p>
 * <b>Parameter passing.</b> Via {@code C_Queue_WorkPackage_Param}, i.e.
 * {@code IWorkPackageBuilder#parameter(String, Object)} out and {@link IParams} back in - the house mechanism for
 * carrying scalars onto a work package (see {@code de.metas.distribution.ddordercandidate.async.DDOrderCandidateEnqueueService},
 * which uses the same builder method, and {@code RecreateInvoiceWorkpackageProcessor}, which reads its parameters
 * back the same way). The ids travel as plain {@code int}s rather than as their typed wrappers so that
 * {@link IParams#getParameterAsInt(String, int)} - the proven read-back for a work-package id parameter - is all
 * the consuming side needs.
 * <p>
 * The parameter names are the ones the operator sees on the {@code AD_Process}, which keeps a work package
 * readable against the process that produced it. {@link #extractRunRequest(IParams)} is the only reader, so the
 * two halves of that wire format cannot drift apart unnoticed.
 * <p>
 * <b>Construction must stay side-effect-free.</b> This bean is created by the app server while its own spring
 * context is still being refreshed, so it must not resolve anything that reaches back into that context - see
 * the comment in {@link #enqueue(AtpReconciliationRunRequest)} on why {@link IWorkPackageQueueFactory} is looked
 * up per call.
 */
@Service
public class AtpReconciliationEnqueueService
{
	/** Work-package parameter names - deliberately the {@code AD_Process_Para} column names of the process. */
	private static final String WP_PARAM_M_Warehouse_ID = I_MD_Stock.COLUMNNAME_M_Warehouse_ID;
	private static final String WP_PARAM_M_Product_ID = I_MD_Stock.COLUMNNAME_M_Product_ID;
	private static final String WP_PARAM_M_Product_Category_ID = I_M_Product.COLUMNNAME_M_Product_Category_ID;
	private static final String WP_PARAM_LivenessCutoffDate = "LivenessCutoffDate";

	/**
	 * The run date. Has no {@code AD_Process_Para} counterpart because the operator does not choose it - the
	 * process derives it from the system time when the run is launched, and it is carried so the app server
	 * reconciles at that point rather than at whatever time it drains the queue.
	 */
	private static final String WP_PARAM_RunDate = "RunDate";

	/**
	 * Enqueues {@code request} for {@link AtpReconciliationWorkpackageProcessor}.
	 * <p>
	 * A filter the operator left empty is not written as a parameter at all, so
	 * {@link #extractRunRequest(IParams)} reads back "not restricted" from the parameter's plain absence rather
	 * than from a sentinel value.
	 *
	 * @return the enqueued work package, so the caller can name it to the operator - the run's result is reported
	 * there and not in the launching process's own log
	 */
	public I_C_Queue_WorkPackage enqueue(@NonNull final AtpReconciliationRunRequest request)
	{
		final Properties ctx = Env.getCtx();
		// Resolved here and NEVER in a field initializer. Concrete failure that prevents: the app server
		// creates this @Service while its own spring context is still being refreshed, and
		// WorkPackageQueueFactory's constructor reaches back into that context
		// (QueueProcessorDescriptorIndex.getInstance() -> SpringContextHolder). Resolving the factory at
		// construction time therefore aborts app-server startup outright with "SpringApplicationContext not
		// configured yet" - the whole application, not just this feature. Reproduced by the cucumber suite,
		// which boots ServerBoot for real; no unit test can see it, because SpringContextHolder is satisfied
		// differently in unit-test mode. (DDOrderCandidateEnqueueService does hold the factory in a field and
		// happens to survive - it depends purely on bean-creation order, so do not copy it.)
		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, AtpReconciliationWorkpackageProcessor.class);
		final AtpKeySelection selection = request.getSelection();

		final IWorkPackageBuilder workPackageBuilder = queue.newWorkPackage()
				// the user in charge is the operator who launched the process: the one who has to hear about it if
				// the reconciliation fails in the app server, where nobody is watching a process window
				.setUserInChargeId(Env.getLoggedUserIdIfExists(ctx).orElse(null))
				.parameter(WP_PARAM_RunDate, request.getRunDate());

		if (selection.getWarehouseId() != null)
		{
			workPackageBuilder.parameter(WP_PARAM_M_Warehouse_ID, selection.getWarehouseId().getRepoId());
		}
		if (selection.getProductId() != null)
		{
			workPackageBuilder.parameter(WP_PARAM_M_Product_ID, selection.getProductId().getRepoId());
		}
		if (selection.getProductCategoryId() != null)
		{
			workPackageBuilder.parameter(WP_PARAM_M_Product_Category_ID, selection.getProductCategoryId().getRepoId());
		}
		if (request.getLivenessCutoff() != null)
		{
			workPackageBuilder.parameter(WP_PARAM_LivenessCutoffDate, request.getLivenessCutoff());
		}

		return workPackageBuilder.buildAndEnqueue();
	}

	/**
	 * The inverse of {@link #enqueue(AtpReconciliationRunRequest)}: rebuilds the run exactly as it was launched,
	 * from the work package's own parameters.
	 *
	 * @throws AdempiereException when the work package carries no run date - it was not
	 * produced by {@link #enqueue(AtpReconciliationRunRequest)}, and reconciling at a guessed date would write a
	 * correction at the wrong reconciliation point
	 */
	public static AtpReconciliationRunRequest extractRunRequest(@NonNull final IParams params)
	{
		final Instant runDate = params.getParameterAsInstant(WP_PARAM_RunDate);
		if (runDate == null)
		{
			throw new AdempiereException(
					"The ATP reconciliation work package carries no " + WP_PARAM_RunDate + " parameter")
					.appendParametersToMessage()
					.setParameter("parameterNames", params.getParameterNames());
		}

		return AtpReconciliationRunRequest.builder()
				.selection(AtpKeySelection.builder()
						.warehouseId(WarehouseId.ofRepoIdOrNull(params.getParameterAsInt(WP_PARAM_M_Warehouse_ID, -1)))
						.productId(ProductId.ofRepoIdOrNull(params.getParameterAsInt(WP_PARAM_M_Product_ID, -1)))
						.productCategoryId(ProductCategoryId.ofRepoIdOrNull(params.getParameterAsInt(WP_PARAM_M_Product_Category_ID, -1)))
						.build())
				.runDate(runDate)
				.livenessCutoff(livenessCutoffOrNull(params))
				.build();
	}

	@Nullable
	private static Instant livenessCutoffOrNull(@NonNull final IParams params)
	{
		return params.hasParameter(WP_PARAM_LivenessCutoffDate)
				? params.getParameterAsInstant(WP_PARAM_LivenessCutoffDate)
				: null;
	}
}
