package de.metas.purchasecandidate.material.event;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateAdvisedEvent;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.VendorProductInfoService;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class PurchaseCandidateAdvisedEventCreator
{
	private final PurchaseOrderDemandMatcher purchaseOrderDemandMatcher;
	private final VendorProductInfoService vendorProductInfoService;

	public PurchaseCandidateAdvisedEventCreator(
			@NonNull final PurchaseOrderDemandMatcher purchaseOrderDemandMatcher,
			@NonNull final VendorProductInfoService vendorProductInfoService)
	{
		this.vendorProductInfoService = vendorProductInfoService;
		this.purchaseOrderDemandMatcher = purchaseOrderDemandMatcher;
	}

	public Optional<PurchaseCandidateAdvisedEvent> createPurchaseAdvisedEvent(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final IMutableMRPContext mrpContext)
	{
		if (!purchaseOrderDemandMatcher.matches(mrpContext))
		{
			return Optional.empty();
		}

		final ProductId productId = ProductId.ofRepoId(supplyRequiredDescriptor.getMaterialDescriptor().getProductId());
		final OrgId orgId = supplyRequiredDescriptor.getEventDescriptor().getOrgId();

		final Optional<VendorProductInfo> defaultVendorProductInfo = vendorProductInfoService.getDefaultVendorProductInfo(productId, orgId);
		if (!defaultVendorProductInfo.isPresent())
		{
			Loggables.addLog("Found no VendorProductInfo for productId={} and orgId={}", productId.getRepoId(), orgId.getRepoId());
			return Optional.empty();
		}

		final ProductPlanning productPlanning = mrpContext.getProductPlanning();

		final PurchaseCandidateAdvisedEvent event = PurchaseCandidateAdvisedEvent
				.builder()
				.eventDescriptor(EventDescriptor.ofEventDescriptor(supplyRequiredDescriptor.getEventDescriptor()))
				.supplyRequiredDescriptor(supplyRequiredDescriptor)
				.directlyCreatePurchaseCandidate(productPlanning.isCreatePlan())
				.productPlanningId(ProductPlanningId.toRepoId(productPlanning.getId()))
				.vendorId(defaultVendorProductInfo.get().getVendorId().getRepoId())
				.build();

		Loggables.addLog("Created PurchaseCandidateAdvisedEvent");
		return Optional.of(event);
	}
}
