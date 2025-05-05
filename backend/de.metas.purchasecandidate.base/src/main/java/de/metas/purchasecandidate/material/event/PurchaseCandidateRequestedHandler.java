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
import de.metas.document.dimension.Dimension;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateCreatedEvent;
import de.metas.material.event.purchase.PurchaseCandidateRequestedEvent;
import de.metas.mforecast.impl.ForecastLineId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_App) // we want only one component to bother itself with PurchaseCandidateRequestedEvent
public class PurchaseCandidateRequestedHandler implements MaterialEventHandler<PurchaseCandidateRequestedEvent>
{
	public static final ThreadLocal<Boolean> INTERCEPTOR_SHALL_POST_EVENT_FOR_PURCHASE_CANDIDATE_RECORD = ThreadLocal.withInitial(() -> false);
	private final IPurchaseCandidateBL purchaseCandidateBL = Services.get(IPurchaseCandidateBL.class);
	private final ProductRepository productRepository;
	private final PurchaseCandidateRepository purchaseCandidateRepository;
	private final PostMaterialEventService postMaterialEventService;

	private final VendorProductInfoService vendorProductInfosRepo;

	public PurchaseCandidateRequestedHandler(
			@NonNull final ProductRepository productRepository,
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepository,
			@NonNull final VendorProductInfoService vendorProductInfosRepo,
			@NonNull final PostMaterialEventService postMaterialEventService)
	{
		this.productRepository = productRepository;
		this.purchaseCandidateRepository = purchaseCandidateRepository;
		this.postMaterialEventService = postMaterialEventService;
		this.vendorProductInfosRepo = vendorProductInfosRepo;
	}

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
		final OrgId orgId = event.getEventDescriptor().getOrgId();

		final VendorProductInfo vendorProductInfos = vendorProductInfosRepo
				.getDefaultVendorProductInfo(product.getId(), orgId)
				.orElseThrow(() -> new AdempiereException("Missing vendorProductInfos for productId=" + product.getId() + " and orgId=" + orgId + ";"));

		final Dimension dimension = Dimension.builder()
				.activityId(ActivityId.ofRepoIdOrNull(event.getActivityId()))
				.salesOrderId(OrderId.ofRepoIdOrNull(event.getSalesOrderRepoId()))
				.campaignId(event.getCampaignId())
				.projectId(ProjectId.ofRepoIdOrNull(event.getProjectId()))
				.productId(product.getId())
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

		final PurchaseCandidate newPurchaseCandidate = PurchaseCandidate
				.builder()
				.groupReference(DemandGroupReference.EMPTY)
				.vendorId(vendorProductInfos.getVendorId()) // mandatory
				.vendorProductNo(vendorProductInfos.getVendorProductNo()) // mandatory
				.purchaseDatePromised(TimeUtil.asZonedDateTime(materialDescriptor.getDate())) // dateRequired

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
				.build();

		purchaseCandidateBL.updateCandidatePricingDiscount(newPurchaseCandidate);
		saveCandidateAndPostCreatedEvent(event, newPurchaseCandidate);
	}

	private void saveCandidateAndPostCreatedEvent(
			@NonNull final PurchaseCandidateRequestedEvent requestedEvent,
			@NonNull final PurchaseCandidate newPurchaseCandidate)
	{
		try
		{
			INTERCEPTOR_SHALL_POST_EVENT_FOR_PURCHASE_CANDIDATE_RECORD.set(false);

			final PurchaseCandidateId newPurchaseCandidateId = purchaseCandidateRepository.save(newPurchaseCandidate);

			final PurchaseCandidateCreatedEvent purchaseCandidateCreatedEvent = createCandidateCreatedEvent(requestedEvent,
																											newPurchaseCandidate.getVendorId(),
																											newPurchaseCandidateId);
			postMaterialEventService.enqueueEventAfterNextCommit(purchaseCandidateCreatedEvent);
		}
		finally
		{
			INTERCEPTOR_SHALL_POST_EVENT_FOR_PURCHASE_CANDIDATE_RECORD.set(true);
		}
	}

	@VisibleForTesting
	static PurchaseCandidateCreatedEvent createCandidateCreatedEvent(
			@NonNull final PurchaseCandidateRequestedEvent requestedEvent,
			@NonNull final BPartnerId vendorId,
			@NonNull final PurchaseCandidateId newPurchaseCandidateId)
	{
		return PurchaseCandidateCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofEventDescriptor(requestedEvent.getEventDescriptor()))
				.purchaseCandidateRepoId(newPurchaseCandidateId.getRepoId())
				.vendorId(vendorId.getRepoId())
				.purchaseMaterialDescriptor(requestedEvent.getPurchaseMaterialDescriptor())
				.supplyCandidateRepoId(requestedEvent.getSupplyCandidateRepoId())
				.build();
	}

}
