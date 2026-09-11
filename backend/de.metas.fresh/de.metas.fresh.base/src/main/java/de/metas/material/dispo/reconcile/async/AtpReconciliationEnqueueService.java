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
 * <b>Why enqueued at all:</b> the real run needs {@code AtpReconciliationCommand}, which is
 * {@code @Profile(Profiles.PROFILE_MaterialDispo)} and so only lives in the app server - never the webapi, where
 * the WebUI-launched {@code AD_Process} actually executes. The queue is the established route between the two;
 * {@link AtpReconciliationWorkpackageProcessor} does the work once the app server drains it.
 * <p>
 * Parameters travel via {@code C_Queue_WorkPackage_Param} ({@link IWorkPackageBuilder#parameter(String, Object)}
 * out, {@link IParams} back in - the same mechanism as {@code DDOrderCandidateEnqueueService} and
 * {@code RecreateInvoiceWorkpackageProcessor}), as plain {@code int}s so
 * {@link IParams#getParameterAsInt(String, int)} is all the read-back needs. Parameter names match the
 * {@code AD_Process_Para} column names, and {@link #extractRunRequest(IParams)} is the only reader, so the wire
 * format can't drift.
 * <p>
 * <b>Construction must stay side-effect-free:</b> this bean is created while the app server's own spring context
 * is still refreshing, so it must not resolve anything reaching back into that context - see
 * {@link #workPackageQueueFactory()}.
 */
@Service
public class AtpReconciliationEnqueueService
{
	/**
	 * Resolved on first use by {@link #workPackageQueueFactory()} - the lazy-accessor shape
	 * {@code docs/coding-rules/service-injection.md} §3 prescribes; see that method for the concrete failure.
	 */
	@Nullable private IWorkPackageQueueFactory _workPackageQueueFactory;

	/** Work-package parameter names - deliberately the {@code AD_Process_Para} column names of the process. */
	private static final String WP_PARAM_M_Warehouse_ID = I_MD_Stock.COLUMNNAME_M_Warehouse_ID;
	private static final String WP_PARAM_M_Product_ID = I_MD_Stock.COLUMNNAME_M_Product_ID;
	private static final String WP_PARAM_M_Product_Category_ID = I_M_Product.COLUMNNAME_M_Product_Category_ID;
	private static final String WP_PARAM_LivenessCutoffDate = "LivenessCutoffDate";

	/**
	 * The run date - no {@code AD_Process_Para} counterpart, since the operator doesn't choose it: the process
	 * derives it at launch time and carries it so the app server reconciles at that point, not whenever it drains
	 * the queue.
	 */
	private static final String WP_PARAM_RunDate = "RunDate";

	/**
	 * Enqueues {@code request} for {@link AtpReconciliationWorkpackageProcessor}. An empty filter is not written as
	 * a parameter at all, so {@link #extractRunRequest(IParams)} reads back "not restricted" from its absence
	 * rather than a sentinel value.
	 *
	 * @return the enqueued work package - the run's result is reported there, not in the launching process's log
	 */
	public I_C_Queue_WorkPackage enqueue(@NonNull final AtpReconciliationRunRequest request)
	{
		final Properties ctx = Env.getCtx();
		final IWorkPackageQueue queue = workPackageQueueFactory()
				.getQueueForEnqueuing(ctx, AtpReconciliationWorkpackageProcessor.class);
		final AtpKeySelection selection = request.getSelection();

		final IWorkPackageBuilder workPackageBuilder = queue.newWorkPackage()
				// the operator who launched the process - the one who needs to hear if it fails in the app server,
				// where nobody is watching a process window
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

	/**
	 * @return the work-package queue factory, resolved on first use and cached for the life of this bean.
	 * <p>
	 * Concrete failure this prevents: the app server creates this {@code @Service} while its own spring context is
	 * still being refreshed, and {@code WorkPackageQueueFactory}'s constructor reaches back into that context
	 * ({@code QueueProcessorDescriptorIndex.getInstance()} -> {@code SpringContextHolder}). Resolving it from a
	 * field initializer aborted app-server startup with "SpringApplicationContext not configured yet" - reproduced
	 * by the cucumber suite (which boots {@code ServerBoot} for real; no unit test sees it). Deferring to first use
	 * puts the lookup after the refresh, where the context is live.
	 * <p>
	 * ({@code DDOrderCandidateEnqueueService} holds this factory in a field and happens to survive purely by
	 * bean-creation order - do not copy it. This is the {@code @Nullable}-field-plus-memoizing-accessor shape
	 * {@code docs/coding-rules/service-injection.md} §3 prescribes, not a bare per-call {@code Services.get}.)
	 */
	private IWorkPackageQueueFactory workPackageQueueFactory()
	{
		IWorkPackageQueueFactory result = _workPackageQueueFactory;
		if (result == null)
		{
			result = _workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
		}
		return result;
	}

	@Nullable
	private static Instant livenessCutoffOrNull(@NonNull final IParams params)
	{
		return params.hasParameter(WP_PARAM_LivenessCutoffDate)
				? params.getParameterAsInstant(WP_PARAM_LivenessCutoffDate)
				: null;
	}
}
