/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.purchasecandidate.material.event;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.dimension.Dimension;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateCreatedEvent;
import de.metas.material.event.purchase.PurchaseCandidateRequestedEvent;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders;
import de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrdersForSalesOrder;
import de.metas.mforecast.impl.ForecastLineId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.IPurchaseCandidateBL;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.PurchaseCandidateSource;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.VendorProductInfoService;
import de.metas.quantity.Quantitys;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_App) // we want only one component to bother itself with PurchaseCandidateRequestedEvent
@RequiredArgsConstructor
public class PurchaseCandidateRequestedHandler implements MaterialEventHandler<PurchaseCandidateRequestedEvent>
{
	public static final ThreadLocal<Boolean> INTERCEPTOR_SHALL_POST_EVENT_FOR_PURCHASE_CANDIDATE_RECORD = ThreadLocal.withInitial(() -> false);
	@NonNull private final ProductRepository productRepository;
	@NonNull private final PurchaseCandidateRepository purchaseCandidateRepository;
	@NonNull private final PostMaterialEventService postMaterialEventService;
	@NonNull private final VendorProductInfoService vendorProductInfosRepo;

	private final IPurchaseCandidateBL purchaseCandidateBL = Services.get(IPurchaseCandidateBL.class);
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@Override
	public Collection<Class<? extends PurchaseCandidateRequestedEvent>> getHandledEventType()
	{
		return ImmutableList.of(PurchaseCandidateRequestedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final PurchaseCandidateRequestedEvent event)
	{
		final MaterialDescriptor materialDescriptor = event.getPurchaseMaterialDescriptor();

		final OrderAndLineId orderAndLineIdOrNull = OrderAndLineId.ofRepoIdsOrNull(
				event.getSalesOrderRepoId(),
				event.getSalesOrderLineRepoId());

		final Product product = productRepository.getById(ProductId.ofRepoId(materialDescriptor.getProductId()));
		final OrgId orgId = event.getOrgId();

		final VendorProductInfo vendorProductInfos = vendorProductInfosRepo
				.getDefaultVendorProductInfo(product.getId(), orgId)
				.orElseThrow(() -> new AdempiereException("Missing vendorProductInfos for productId=" + product.getId() + " and orgId=" + orgId + ";"));

		final Dimension dimension = Dimension.builder()
				.activityId(ActivityId.ofRepoIdOrNull(event.getActivityId()))
				.salesOrderId(OrderId.ofRepoIdOrNull(event.getSalesOrderRepoId()))
				.campaignId(event.getCampaignId())
				.projectId(ProjectId.ofRepoIdOrNull(event.getProjectId()))
				.userElement1Id(event.getUserElementId1())
				.userElement2Id(event.getUserElementId2())
				.userElementString1(event.getUserElementString1())
				.userElementString2(event.getUserElementString2())
				.userElementString3(event.getUserElementString3())
				.userElementString4(event.getUserElementString4())
				.userElementString5(event.getUserElementString5())
				.userElementString6(event.getUserElementString6())
				.userElementString7(event.getUserElementString7())
				.build();

		// Extract dropship info from the sales order, if present
		boolean isDropShip = false;
		BPartnerId dropShipBPartnerId = null;
		BPartnerLocationId dropShipLocationId = null;
		UserId dropShipUserId = null;
		if (orderAndLineIdOrNull != null)
		{
			final org.compiere.model.I_C_Order salesOrder = orderDAO.getById(orderAndLineIdOrNull.getOrderId());
			if (salesOrder.isDropShip())
			{
				isDropShip = true;
				if (salesOrder.getDropShip_BPartner_ID() > 0)
				{
					dropShipBPartnerId = BPartnerId.ofRepoId(salesOrder.getDropShip_BPartner_ID());
					dropShipLocationId = BPartnerLocationId.ofRepoIdOrNull(dropShipBPartnerId, salesOrder.getDropShip_Location_ID() > 0 ? salesOrder.getDropShip_Location_ID() : null);
					dropShipUserId = UserId.ofRepoIdOrNull(salesOrder.getDropShip_User_ID());
				}
				else
				{
					// Dropship flag set but no explicit dropship partner => use the SO's ship-to address
					dropShipBPartnerId = BPartnerId.ofRepoId(salesOrder.getC_BPartner_ID());
					dropShipLocationId = BPartnerLocationId.ofRepoIdOrNull(dropShipBPartnerId, salesOrder.getC_BPartner_Location_ID() > 0 ? salesOrder.getC_BPartner_Location_ID() : null);
					dropShipUserId = UserId.ofRepoIdOrNull(salesOrder.getAD_User_ID());
				}
			}
		}

		final ZonedDateTime datePromised = TimeUtil.asZonedDateTime(materialDescriptor.getDate());
		final PurchaseCandidate newPurchaseCandidate = PurchaseCandidate
				.builder()
				.groupReference(DemandGroupReference.EMPTY)
				.vendorId(vendorProductInfos.getVendorId()) // mandatory
				.vendorProductNo(vendorProductInfos.getVendorProductNo()) // mandatory
				.purchaseDatePromised(datePromised)
				.purchaseDateOrdered(computePurchaseDateOrderedOrNull(datePromised, event.getProductPlanningId()))

				.dimension(dimension)
				.orgId(orgId)
				.processed(false)
				.productId(product.getId())
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(materialDescriptor.getAttributeSetInstanceId()))
				// .profitInfo(profitInfo)
				// .purchaseItem(purchaseItem) purchase items are only returned by the vendor gateway
				.qtyToPurchase(Quantitys.of(materialDescriptor.getQuantity(), product.getUomId()))
				.salesOrderAndLineIdOrNull(orderAndLineIdOrNull)
				.source(PurchaseCandidateSource.MaterialDisposition)
				.warehouseId(materialDescriptor.getWarehouseId())
				.forecastLineId(ForecastLineId.ofRepoIdOrNull(event.getForecastId(), event.getForecastLineId()))
				.simulated(event.isSimulated())
				.isDropShip(isDropShip)
				.dropShipBPartnerId(dropShipBPartnerId)
				.dropShipLocationId(dropShipLocationId)
				.dropShipUserId(dropShipUserId)
				.build();

		purchaseCandidateBL.updateCandidatePricingDiscount(newPurchaseCandidate);
		saveCandidateAndPostCreatedEvent(event, newPurchaseCandidate);
	}

	@Nullable
	private ZonedDateTime computePurchaseDateOrderedOrNull(@NonNull final ZonedDateTime datePromised, @Nullable final ProductPlanningId productPlanningId)
	{
		if (productPlanningId == null)
		{
			return null;
		}
		final int leadTimeDays = productPlanningDAO.getById(productPlanningId).getLeadTimeDays();
		return datePromised.minusDays(leadTimeDays);
	}

	private void saveCandidateAndPostCreatedEvent(
			@NonNull final PurchaseCandidateRequestedEvent requestedEvent,
			@NonNull final PurchaseCandidate newPurchaseCandidate)
	{
		try
		{
			INTERCEPTOR_SHALL_POST_EVENT_FOR_PURCHASE_CANDIDATE_RECORD.set(false);

			// Determine if this candidate should be marked ready for aggregated PO creation
			final OrderAndLineId salesOrderAndLineId = newPurchaseCandidate.getSalesOrderAndLineIdOrNull();
			final OrderId salesOrderId = salesOrderAndLineId != null ? salesOrderAndLineId.getOrderId() : null;
			final ProductPlanningId productPlanningId = requestedEvent.getProductPlanningId();
			if (productPlanningId != null && salesOrderId != null)
			{
				final ProductPlanning pp = productPlanningDAO.getById(productPlanningId);
				if (pp.isCreatePlan())
				{
					newPurchaseCandidate.setReadyForPOCreation(true);
				}
			}

			final PurchaseCandidateId newPurchaseCandidateId = purchaseCandidateRepository.save(newPurchaseCandidate);

			final PurchaseCandidateCreatedEvent purchaseCandidateCreatedEvent = createCandidateCreatedEvent(requestedEvent,
					newPurchaseCandidate.getVendorId(),
					newPurchaseCandidateId);
			postMaterialEventService.enqueueEventAfterNextCommit(purchaseCandidateCreatedEvent);

			if (!newPurchaseCandidate.isSimulated())
			{
				scheduleGeneratePurchaseOrderIfNeeded(productPlanningId, newPurchaseCandidateId, salesOrderId);
			}
		}
		finally
		{
			INTERCEPTOR_SHALL_POST_EVENT_FOR_PURCHASE_CANDIDATE_RECORD.set(true);
		}
	}

	/**
	 * If PP_Product_Planning.IsCreatePlan=Y, auto-enqueue the purchase candidate for C_Order generation.
	 * IsDocComplete controls whether the generated C_Order is completed (CO) or left as draft (DR).
	 * <p>
	 * For SO-driven candidates (salesOrderId != null): uses the debouncer processor that aggregates
	 * all candidates for the same SO into fewer purchase orders (one per vendor).
	 * <p>
	 * For forecast-driven candidates (salesOrderId == null): uses the existing per-candidate processor.
	 */
	private void scheduleGeneratePurchaseOrderIfNeeded(
			@Nullable final ProductPlanningId productPlanningId,
			@NonNull final PurchaseCandidateId purchaseCandidateId,
			@Nullable final OrderId salesOrderId)
	{
		if (productPlanningId == null)
		{
			return;
		}
		final ProductPlanning productPlanning = productPlanningDAO.getById(productPlanningId);
		if (!productPlanning.isCreatePlan())
		{
			return;
		}

		if (salesOrderId != null)
		{
			// SO-driven: debouncer creates WP or updates existing WP's timestamp
			C_PurchaseCandidates_GeneratePurchaseOrdersForSalesOrder
					.enqueueOrUpdateExisting(salesOrderId, productPlanning.isDocComplete());
		}
		else
		{
			// Forecast-driven: no natural grouping, use existing per-candidate behavior
			C_PurchaseCandidates_GeneratePurchaseOrders.enqueue(
					ImmutableList.of(purchaseCandidateId),
					/* docTypeId= */ null,
					productPlanning.isDocComplete());
		}
	}

	@VisibleForTesting
	static PurchaseCandidateCreatedEvent createCandidateCreatedEvent(
			@NonNull final PurchaseCandidateRequestedEvent requestedEvent,
			@NonNull final BPartnerId vendorId,
			@NonNull final PurchaseCandidateId newPurchaseCandidateId)
	{
		return PurchaseCandidateCreatedEvent.builder()
				.eventDescriptor(requestedEvent.getEventDescriptor().withNewEventId())
				.purchaseCandidateRepoId(newPurchaseCandidateId.getRepoId())
				.vendorId(vendorId.getRepoId())
				.purchaseMaterialDescriptor(requestedEvent.getPurchaseMaterialDescriptor())
				.supplyCandidateRepoId(requestedEvent.getSupplyCandidateRepoId())
				.build();
	}

}
