package de.metas.purchasecandidate.material.event;

import com.google.common.collect.ImmutableList;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateAdvisedEvent;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.event.SupplyRequiredAdvisor;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.VendorProductInfoService;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
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

@Component
@RequiredArgsConstructor
public class PurchaseCandidateAdvisedEventCreator implements SupplyRequiredAdvisor
{
	@NonNull private final PurchaseOrderDemandMatcher purchaseOrderDemandMatcher;
	@NonNull private final VendorProductInfoService vendorProductInfoService;

	public List<PurchaseCandidateAdvisedEvent> createAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context)
	{
		if (!purchaseOrderDemandMatcher.matches(context))
		{
			return ImmutableList.of();
		}

		final ProductId productId = ProductId.ofRepoId(supplyRequiredDescriptor.getProductId());
		final OrgId orgId = supplyRequiredDescriptor.getOrgId();

		final Optional<VendorProductInfo> defaultVendorProductInfo = vendorProductInfoService.getDefaultVendorProductInfo(productId, orgId);
		if (!defaultVendorProductInfo.isPresent())
		{
			Loggables.addLog("Found no VendorProductInfo for productId={} and orgId={}", productId.getRepoId(), orgId.getRepoId());
			return ImmutableList.of();
		}

		final ProductPlanning productPlanning = context.getProductPlanning();

		final PurchaseCandidateAdvisedEvent event = PurchaseCandidateAdvisedEvent.builder()
				.eventDescriptor(supplyRequiredDescriptor.newEventDescriptor())
				.supplyRequiredDescriptor(supplyRequiredDescriptor)
				.directlyCreatePurchaseCandidate(productPlanning.isCreatePlan())
				.productPlanningId(ProductPlanningId.toRepoId(productPlanning.getId()))
				.vendorId(defaultVendorProductInfo.get().getVendorId().getRepoId())
				.build();

		Loggables.addLog("Created PurchaseCandidateAdvisedEvent");
		return ImmutableList.of(event);
	}
}
