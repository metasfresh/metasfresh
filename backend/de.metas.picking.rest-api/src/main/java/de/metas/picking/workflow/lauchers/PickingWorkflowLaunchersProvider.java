package de.metas.picking.workflow.lauchers;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickingJobFieldType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateList;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateProducts;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceList;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceQuery;
import de.metas.handlingunits.picking.job.model.QtyAvailableStatus;
import de.metas.handlingunits.picking.job.model.facets.CollectingParameters;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetHandlers;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacets;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.hu.ProductAvailableStocks;
import de.metas.handlingunits.picking.job.service.external.warehouse.PickingJobWarehouseService;
import de.metas.i18n.AdMessageKey;
import de.metas.picking.workflow.DisplayValueProvider;
import de.metas.picking.workflow.DisplayValueProviderService;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.PickingWFProcessStartParams;
import de.metas.product.ResolvedScannedProductCodes;
import de.metas.product.ScannedProductCodeResolver;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetGroupList;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetQuery;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption.OrderBy;
import de.metas.workflow.rest_api.model.WorkflowLauncherIndicator;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.service.Constants;
import de.metas.workplace.Workplace;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.SynchronizedMutable;
import org.adempiere.warehouse.WarehouseId;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static de.metas.picking.workflow.handlers.PickingMobileApplication.APPLICATION_ID;

@Service
@RequiredArgsConstructor
public class PickingWorkflowLaunchersProvider
{
	private static final AdMessageKey INVALID_QR_CODE_ERROR_MSG = AdMessageKey.of("mobileui.picking.INVALID_QR_CODE_ERROR_MSG");

	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final MobileUIPickingUserProfileService configService;
	@NonNull private final PickingJobBPartnerService bpartnerService;
	@NonNull private final PickingJobRestService pickingJobRestService;
	@NonNull private final PickingJobWarehouseService warehouseService;
	@NonNull private final PickingJobHUService huService;
	@NonNull private final DisplayValueProviderService displayValueProviderService;
	@NonNull private final ScannedProductCodeResolver scannedProductCodeResolver;

	private final CCache<UserId, SynchronizedMutable<ComputedWorkflowLaunchers>> launchersCache = CCache.<UserId, SynchronizedMutable<ComputedWorkflowLaunchers>>builder().build();

	public WorkflowLaunchersList provideLaunchers(@NonNull final WorkflowLaunchersQuery query)
	{
		//noinspection DataFlowIssue
		return launchersCache.getOrLoad(query.getUserId(), SynchronizedMutable::empty)
				.compute(previousLaunchers -> checkStateAndComputeLaunchers(query, previousLaunchers))
				.getList();
	}

	private ComputedWorkflowLaunchers checkStateAndComputeLaunchers(
			@NonNull final WorkflowLaunchersQuery query,
			@Nullable final ComputedWorkflowLaunchers previousLaunchers)
	{
		if (previousLaunchers == null || !previousLaunchers.matches(query))
		{
			return computeLaunchers(query);
		}
		else
		{
			return previousLaunchers;
		}
	}

	private ComputedWorkflowLaunchers computeLaunchers(@NonNull final WorkflowLaunchersQuery query)
	{
		final MobileUIPickingUserProfile profile = configService.getProfile();
		final UserId userId = query.getUserId();
		final Workplace workplace = warehouseService.getWorkplaceByUserId(userId).orElse(null);
		boolean returnNoResult = false;

		//noinspection RedundantIfStatement
		if (profile.isConsiderOnlyJobScheduledToWorkplace() && workplace == null)
		{
			returnNoResult = true;
		}

		ResolvedScannedProductCodes scannedProductCodes = null;
		if (!returnNoResult && query.getFilterByQRCode() != null)
		{
			scannedProductCodes = scannedProductCodeResolver.resolve(query.getFilterByQRCode()).orElse(null);
			if (scannedProductCodes == null)
			{
				returnNoResult = true;
			}
		}

		final ProductAvailableStocks productsAvailableStocks = workplace != null ? huService.newAvailableStocksProvider(workplace) : null;

		final ArrayList<WorkflowLauncher> currentResult = new ArrayList<>();

		//
		// Already started launchers
		final DisplayValueProvider displayValueProvider = displayValueProviderService.newDisplayValueProvider(profile);
		final PickingJobQuery.Facets facets = PickingJobFacetHandlers.toPickingJobFacetsQuery(query.getFacetIds());
		PickingJobReferenceList existingPickingJobs;
		if (workplace != null || !profile.isConsiderOnlyJobScheduledToWorkplace())
		{
			final WarehouseId warehouseId = workplace != null ? workplace.getWarehouseId() : null;
			existingPickingJobs = pickingJobRestService.streamDraftPickingJobReferences(
							PickingJobReferenceQuery.builder()
									.pickerId(userId)
									.onlyCustomerIds(profile.getPickOnlyCustomerIds())
									.warehouseId(warehouseId)
									.salesOrderDocumentNo(query.getFilterByDocumentNo())
									.build())
					.filter(facets::isMatching)
					.filter(pickingJobReference -> !pickingJobReference.isShipmentSchedulesLocked())
					.collect(PickingJobReferenceList.collect());

			if (productsAvailableStocks != null)
			{
				productsAvailableStocks.warmUpByProductIds(existingPickingJobs.getProductIds());
				existingPickingJobs = existingPickingJobs.updateEach(productsAvailableStocks::allocate);
			}

			displayValueProvider.cacheWarmUpForPickingJobReferences(existingPickingJobs);
			existingPickingJobs.stream()
					.map(pickingJobReference -> toExistingWorkflowLauncher(pickingJobReference, displayValueProvider))
					.forEach(currentResult::add);
		}
		else
		{
			existingPickingJobs = PickingJobReferenceList.EMPTY;
		}

		//
		// New launchers
		final QueryLimit limit = query.getLimit().orElseGet(this::getLaunchersLimit);
		if (!returnNoResult)
		{
			PickingJobCandidateList newPickingJobCandidates = pickingJobRestService.streamPickingJobCandidates(
							PickingJobQuery.builder()
									.userId(userId)
									.excludeScheduleIds(existingPickingJobs.getScheduleIds())
									.facets(facets)
									.onlyCustomerIds(profile.getPickOnlyCustomerIds())
									.scheduledForWorkplaceId(profile.isConsiderOnlyJobScheduledToWorkplace() ? workplace.getId() : null)
									.warehouseId(workplace != null ? workplace.getWarehouseId() : null)
									.salesOrderDocumentNo(query.getFilterByDocumentNo())
									.scannedProductCodes(scannedProductCodes)
									.build()
					)
					.limit(limit.toIntOrInfinit())
					.collect(PickingJobCandidateList.collect());

			if (productsAvailableStocks != null)
			{
				productsAvailableStocks.warmUpByProductIds(newPickingJobCandidates.getProductIds());
				newPickingJobCandidates = newPickingJobCandidates.updateEach(productsAvailableStocks::allocate);

				if (query.isFilterByQtyAvailableAtPickFromLocator())
				{
					newPickingJobCandidates = newPickingJobCandidates.removeIf(job -> job.hasQtyAvailableToPick().isFalse());
				}
			}

			displayValueProvider.cacheWarmUpForPickingJobCandidates(newPickingJobCandidates);
			newPickingJobCandidates.stream()
					.map(pickingJobCandidate -> toNewWorkflowLauncher(pickingJobCandidate, displayValueProvider))
					.forEach(currentResult::add);
		}

		return toComputedWorkflowLaunchers(query, currentResult);
	}

	@NotNull
	private static ComputedWorkflowLaunchers toComputedWorkflowLaunchers(@NotNull final WorkflowLaunchersQuery query, @NonNull final List<WorkflowLauncher> launchers)
	{
		return ComputedWorkflowLaunchers.ofListAndQuery(
				WorkflowLaunchersList.builder()
						.launchers(ImmutableList.copyOf(launchers))
						.orderByField(OrderBy.descending(PickingJobFieldType.RUESTPLATZ_NR))
						.filterByQRCode(query.getFilterByQRCode() != null ? query.getFilterByQRCode().toPrintableScannedCode() : null)
						.timestamp(SystemTime.asInstant())
						.build(),
				query);
	}

	@NonNull
	private static WorkflowLauncher toNewWorkflowLauncher(
			@NonNull final PickingJobCandidate pickingJobCandidate,
			@NonNull final DisplayValueProvider displayValueProvider)
	{
		return WorkflowLauncher.builder()
				.applicationId(APPLICATION_ID)
				.caption(displayValueProvider.computeLauncherCaption(pickingJobCandidate))
				.startedWFProcessId(null)
				.wfParameters(PickingWFProcessStartParams.of(pickingJobCandidate).toParams())
				.indicator(computeQtyAvailableIndicator(pickingJobCandidate.getProducts()))
				.partiallyHandledBefore(pickingJobCandidate.isPartiallyPickedBefore())
				.build();
	}

	@Nullable
	private static WorkflowLauncherIndicator computeQtyAvailableIndicator(@NonNull final PickingJobCandidateProducts products)
	{
		final QtyAvailableStatus qtyAvailableStatus = products.getQtyAvailableStatus().orElse(null);
		if (qtyAvailableStatus == null) {return null;}

		switch (qtyAvailableStatus)
		{
			case NOT_AVAILABLE:
				return WorkflowLauncherIndicator.STOCK_NOT_AVAILABLE;
			case PARTIALLY_AVAILABLE:
				return WorkflowLauncherIndicator.STOCK_PARTIALLY_AVAILABLE;
			case FULLY_AVAILABLE:
				return WorkflowLauncherIndicator.STOCK_FULLY_AVAILABLE;
			default:
				return null; // not handled status; shall not happen
		}
	}

	@NonNull
	private static WorkflowLauncher toExistingWorkflowLauncher(
			@NonNull final PickingJobReference pickingJobReference,
			@NonNull final DisplayValueProvider displayValueProvider)
	{
		final WorkflowLauncherCaption caption = displayValueProvider.computeLauncherCaption(pickingJobReference);

		return WorkflowLauncher.builder()
				.applicationId(APPLICATION_ID)
				.caption(caption)
				.startedWFProcessId(WFProcessId.ofIdPart(APPLICATION_ID, pickingJobReference.getPickingJobId()))
				.wfParameters(PickingWFProcessStartParams.of(pickingJobReference).toParams())
				.indicator(computeQtyAvailableIndicator(pickingJobReference.getProducts()))
				.build();
	}

	public WorkflowLaunchersFacetGroupList getFacets(@NonNull final WorkflowLaunchersFacetQuery query)
	{
		final UserId userId = query.getUserId();
		final PickingJobQuery.Facets activeFacets = PickingJobFacetHandlers.toPickingJobFacetsQuery(query.getActiveFacetIds());

		final MobileUIPickingUserProfile profile = configService.getProfile();
		final ImmutableList<PickingJobFacetGroup> groups = profile.getFilterGroupsInOrder();
		if (groups.isEmpty())
		{
			return WorkflowLaunchersFacetGroupList.EMPTY;
		}

		final PickingJobFacets pickingFacets = pickingJobRestService.getFacets(
				PickingJobQuery.builder()
						.userId(userId)
						.onlyCustomerIds(profile.getPickOnlyCustomerIds())
						.warehouseId(warehouseService.getWarehouseIdByUserId(userId).orElse(null))
						.salesOrderDocumentNo(query.getFilterByDocumentNo())
						//.facets(activeFacets) // IMPORTANT: don't filter by active facets because we want to collect all facets, not only the active ones
						.build(),
				CollectingParameters.builder()
						.addressProvider(bpartnerService.newRenderedAddressProvider())
						.groupsInOrder(groups)
						.activeFacets(activeFacets)
						.build());

		return PickingJobFacetHandlers.toWorkflowLaunchersFacetGroupList(pickingFacets, profile);
	}

	public void invalidateCacheByUserId(@NonNull final UserId invokerId)
	{
		final SynchronizedMutable<ComputedWorkflowLaunchers> userCachedWorkflows = launchersCache.get(invokerId);
		if (userCachedWorkflows != null)
		{
			userCachedWorkflows.setValue(null);
		}
	}

	public QueryLimit getLaunchersLimit()
	{
		final int limitInt = sysConfigBL.getIntValue(Constants.SYSCONFIG_LaunchersLimit, -100);
		return limitInt == -100
				? Constants.DEFAULT_LaunchersLimit
				: QueryLimit.ofInt(limitInt);
	}

	//
	//
	//
	//
	//
	//

	@Value
	@Builder
	private static class ComputedWorkflowLaunchers
	{
		@NonNull WorkflowLaunchersList list;
		@NonNull WorkflowLaunchersQuery validationKey;

		private ComputedWorkflowLaunchers(@NonNull final WorkflowLaunchersList list, @NonNull final WorkflowLaunchersQuery validationKey)
		{
			this.list = list;
			this.validationKey = validationKey;
		}

		public static ComputedWorkflowLaunchers ofListAndQuery(@NonNull final WorkflowLaunchersList list, @NonNull final WorkflowLaunchersQuery query)
		{
			return new ComputedWorkflowLaunchers(list, toValidationKey(query));
		}

		private static WorkflowLaunchersQuery toValidationKey(@NonNull final WorkflowLaunchersQuery query)
		{
			return query.toBuilder()
					.maxStaleAccepted(Duration.ZERO)
					.build();
		}

		public boolean matches(@NonNull final WorkflowLaunchersQuery query)
		{
			if (list.isStaled(query.getMaxStaleAccepted()))
			{
				return false;
			}

			final WorkflowLaunchersQuery validationKeyExpected = toValidationKey(query);
			return validationKey.equals(validationKeyExpected);
		}
	}
}
