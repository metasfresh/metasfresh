package de.metas.purchasecandidate.async;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Param;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.purchaseordercreation.PurchaseCandidateToOrderWorkflow;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseOrderFromItemsAggregator;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvokerFactory;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Debouncer workpackage processor that collects purchase candidates from a sales order
 * and creates aggregated purchase orders (one per vendor).
 * <p>
 * Uses {@link WorkpackageSkipRequestException} to defer processing until a quiet period passes
 * (no new candidates for {@code QuietPeriodMillis}) or a maximum wait time is reached.
 * <p>
 * This processor handles SO-driven candidates ({@code IsCreatePlan=Y} with a linked sales order).
 * Forecast-driven candidates (no SO) continue to use {@link C_PurchaseCandidates_GeneratePurchaseOrders}.
 */
public class C_PurchaseCandidates_GeneratePurchaseOrdersForSalesOrder extends WorkpackageProcessorAdapter
{
	// WP parameter names
	public static final String PARAM_C_ORDER_SO_ID = "C_OrderSO_ID";
	public static final String PARAM_IS_COMPLETE_DOC = "IsCompleteDoc";
	public static final String PARAM_LAST_CANDIDATE_TIMESTAMP = "LastCandidateTimestamp";

	// SysConfig names
	private static final String SYSCONFIG_QUIET_PERIOD_MS = "de.metas.purchasecandidate.POCreation.QuietPeriodMillis";
	private static final String SYSCONFIG_MAX_WAIT_MS = "de.metas.purchasecandidate.POCreation.MaxWaitMillis";
	private static final int DEFAULT_QUIET_PERIOD_MS = 10_000;
	private static final int DEFAULT_MAX_WAIT_MS = 60_000;

	private final PurchaseCandidateRepository purchaseCandidateRepo = SpringContextHolder.instance.getBean(PurchaseCandidateRepository.class);
	private final VendorGatewayInvokerFactory vendorGatewayInvokerFactory = SpringContextHolder.instance.getBean(VendorGatewayInvokerFactory.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	/**
	 * Called when a new purchase candidate is created for a sales order.
	 * Either creates a new WP (first candidate) or updates the existing WP's
	 * LastCandidateTimestamp (subsequent candidates).
	 */
	public static void enqueueOrUpdateExisting(
			@NonNull final OrderId salesOrderId,
			final boolean isDocComplete)
	{
		final I_C_Queue_WorkPackage existingWP = findUnprocessedWPForSalesOrder(salesOrderId);
		if (existingWP != null)
		{
			// Update timestamp — the debouncer will see the new candidate on next retry
			final IWorkpackageParamDAO paramDAO = Services.get(IWorkpackageParamDAO.class);
			paramDAO.setParameterValue(existingWP, PARAM_LAST_CANDIDATE_TIMESTAMP, BigDecimal.valueOf(System.currentTimeMillis()));
			Loggables.addLog("Updated LastCandidateTimestamp on existing WP {} for SO {}", existingWP.getC_Queue_WorkPackage_ID(), salesOrderId);
			return;
		}

		// First candidate for this SO — create new WP
		Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(C_PurchaseCandidates_GeneratePurchaseOrdersForSalesOrder.class)
				.newWorkPackage()
				.bindToThreadInheritedTrx()
				.setUserInChargeId(Env.getLoggedUserIdIfExists().orElse(null))
				.parameter(PARAM_C_ORDER_SO_ID, salesOrderId.getRepoId())
				.parameter(PARAM_IS_COMPLETE_DOC, isDocComplete)
				.parameter(PARAM_LAST_CANDIDATE_TIMESTAMP, BigDecimal.valueOf(System.currentTimeMillis()))
				.buildAndEnqueue();
	}

	/**
	 * Finds an existing unprocessed workpackage for the given sales order.
	 * Returns {@code null} if none found (first candidate for this SO, or race condition with concurrent commit).
	 */
	@Nullable
	private static I_C_Queue_WorkPackage findUnprocessedWPForSalesOrder(@NonNull final OrderId salesOrderId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// Sub-query: find WP IDs from params where C_OrderSO_ID matches
		final IQuery<I_C_Queue_WorkPackage_Param> paramQuery = queryBL
				.createQueryBuilder(I_C_Queue_WorkPackage_Param.class)
				.addEqualsFilter(I_C_Queue_WorkPackage_Param.COLUMNNAME_ParameterName, PARAM_C_ORDER_SO_ID)
				.addEqualsFilter(I_C_Queue_WorkPackage_Param.COLUMNNAME_P_Number, BigDecimal.valueOf(salesOrderId.getRepoId()))
				.create();

		// Sub-query: restrict to our processor type
		final IQuery<I_C_Queue_PackageProcessor> processorQuery = queryBL
				.createQueryBuilder(I_C_Queue_PackageProcessor.class)
				.addEqualsFilter(
						I_C_Queue_PackageProcessor.COLUMNNAME_Classname,
						C_PurchaseCandidates_GeneratePurchaseOrdersForSalesOrder.class.getName())
				.create();

		// Main query: find unprocessed WP with matching parameter and our processor
		return queryBL
				.createQueryBuilder(I_C_Queue_WorkPackage.class)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_Processed, false)
				.addInSubQueryFilter(
						I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_PackageProcessor_ID,
						I_C_Queue_PackageProcessor.COLUMNNAME_C_Queue_PackageProcessor_ID,
						processorQuery)
				.addInSubQueryFilter(
						I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID,
						I_C_Queue_WorkPackage_Param.COLUMNNAME_C_Queue_WorkPackage_ID,
						paramQuery)
				.create()
				.first(I_C_Queue_WorkPackage.class);
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		final ILoggable loggable = Loggables.get();

		final OrderId salesOrderId = OrderId.ofRepoId(getParameters().getParameterAsInt(PARAM_C_ORDER_SO_ID, 0));
		// Default true for backward compatibility (same as C_PurchaseCandidates_GeneratePurchaseOrders)
		final boolean isCompleteDoc = getParameters().getParameterAsBoolean(PARAM_IS_COMPLETE_DOC, true);

		// Query all ready candidates for this SO
		final List<PurchaseCandidate> candidates = purchaseCandidateRepo
				.getReadyForPOCreationBySalesOrderId(salesOrderId);

		if (candidates.isEmpty())
		{
			loggable.addLog("No candidates found for SO {}. Already processed or none created yet.", salesOrderId);
			return Result.SUCCESS;
		}

		// --- Debounce check ---
		final BigDecimal lastCandidateTsBD = getParameters().getParameterAsBigDecimal(PARAM_LAST_CANDIDATE_TIMESTAMP);
		final long lastCandidateTs = lastCandidateTsBD != null ? lastCandidateTsBD.longValue() : 0;
		final long wpCreated = workPackage.getCreated().getTime();
		final long now = System.currentTimeMillis();
		final int quietPeriodMs = sysConfigBL.getIntValue(SYSCONFIG_QUIET_PERIOD_MS, DEFAULT_QUIET_PERIOD_MS);
		final int maxWaitMs = sysConfigBL.getIntValue(SYSCONFIG_MAX_WAIT_MS, DEFAULT_MAX_WAIT_MS);

		final long sinceLastCandidate = now - lastCandidateTs;
		final long sinceWPCreated = now - wpCreated;

		if (sinceLastCandidate < quietPeriodMs && sinceWPCreated < maxWaitMs)
		{
			final int deferMs = Math.max(1, (int)Math.min(quietPeriodMs - sinceLastCandidate, maxWaitMs - sinceWPCreated));
			throw WorkpackageSkipRequestException.createWithTimeout(
					"Debouncing: last candidate " + sinceLastCandidate + "ms ago, "
							+ "quiet period " + quietPeriodMs + "ms, "
							+ "max wait " + (maxWaitMs - sinceWPCreated) + "ms remaining. "
							+ "Candidates so far: " + candidates.size(),
					deferMs);
		}

		// --- Quiet period reached or max timeout — process ---
		loggable.addLog("Processing {} candidates for SO {} (waited {}ms)", candidates.size(), salesOrderId, sinceWPCreated);

		// Group by vendor (PurchaseCandidateToOrderWorkflow requires single vendor)
		final Multimap<BPartnerId, PurchaseCandidate> byVendor =
				Multimaps.index(candidates, PurchaseCandidate::getVendorId);

		for (final Map.Entry<BPartnerId, Collection<PurchaseCandidate>> entry : byVendor.asMap().entrySet())
		{
			final List<PurchaseCandidate> vendorCandidates = ImmutableList.copyOf(entry.getValue());
			loggable.addLog("Creating PO for vendor {} with {} lines", entry.getKey(), vendorCandidates.size());

			final PurchaseOrderFromItemsAggregator aggregator =
					PurchaseOrderFromItemsAggregator.newInstance(/* docTypeId */ null, isCompleteDoc);

			PurchaseCandidateToOrderWorkflow.builder()
					.purchaseCandidateRepo(purchaseCandidateRepo)
					.vendorGatewayInvokerFactory(vendorGatewayInvokerFactory)
					.purchaseOrderFromItemsAggregator(aggregator)
					.build()
					.executeForPurchaseCandidates(vendorCandidates);
		}

		return Result.SUCCESS;
	}
}
