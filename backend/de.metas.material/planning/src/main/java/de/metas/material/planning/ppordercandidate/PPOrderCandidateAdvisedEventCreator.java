/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.planning.ppordercandidate;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderCandidateAdvisedEvent;
import de.metas.material.event.pporder.PPOrderCandidateAdvisedEvent.PPOrderCandidateAdvisedEventBuilder;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.event.MaterialRequest;
import de.metas.material.planning.event.SupplyRequiredAdvisor;
import de.metas.material.planning.event.SupplyRequiredHandlerUtils;
import de.metas.material.planning.pporder.PPOrderCandidateDemandMatcher;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.eevolution.model.X_PP_Order_Candidate.ISLOTFORLOT_No;
import static org.eevolution.model.X_PP_Order_Candidate.ISLOTFORLOT_Yes;

@Component
@RequiredArgsConstructor
public class PPOrderCandidateAdvisedEventCreator implements SupplyRequiredAdvisor
{
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final PPOrderCandidateDemandMatcher ppOrderCandidateDemandMatcher;
	@NonNull private final PPOrderCandidatePojoSupplier ppOrderCandidatePojoSupplier;

	@NonNull
	public ImmutableList<PPOrderCandidateAdvisedEvent> createAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context)
	{
		if (!ppOrderCandidateDemandMatcher.matches(context))
		{
			return ImmutableList.of();
		}

		final ProductPlanning productPlanning = context.getProductPlanning();
		final BigDecimal requiredQty = supplyRequiredDescriptor.getMaterialDescriptor().getQuantity();
		if (!productPlanning.isLotForLot() && requiredQty.signum() <= 0)
		{
			Loggables.addLog("Didn't create PPOrderCandidateAdvisedEvent because LotForLot=false and requiredQty={}", requiredQty);
			return ImmutableList.of();
		}

		final SupplyRequiredDescriptor supplyRequiredDescriptorToUse;
		if (productPlanning.isLotForLot())
		{
			final BigDecimal usedQty;
			final BigDecimal fullDemandQty = supplyRequiredDescriptor.getFullDemandQty();
			final BigDecimal deltaQty = supplyRequiredDescriptor.getDeltaQuantity() != null ? supplyRequiredDescriptor.getDeltaQuantity() : BigDecimal.ZERO;
			if (!supplyRequiredDescriptor.isUpdated())
			{
				usedQty = fullDemandQty;
				Loggables.addLog("Using fullDemandQty={}, because of LotForLot=true and updated=false", fullDemandQty);
			}
			// we don't reduce Quantity of PPOrders and PPOrderCandidates atm
			else if (deltaQty.signum() > 0 && deltaQty.compareTo(fullDemandQty) != 0)
			{
				usedQty = deltaQty;
				Loggables.addLog("Using deltaQty={}, because of LotForLot=true and updated=true", deltaQty);
			}
			else
			{
				Loggables.addLog("Didn't create PPOrderCandidateAdvisedEvent with LotForLot=true, updated=true and deltaQty={} because deltaQty is negative", deltaQty);
				return ImmutableList.of();
			}

			supplyRequiredDescriptorToUse = supplyRequiredDescriptor.toBuilder()
					.isLotForLot(ISLOTFORLOT_Yes)
					.materialDescriptor(supplyRequiredDescriptor.getMaterialDescriptor().withQuantity(usedQty))
					.build();
		}
		else
		{
			supplyRequiredDescriptorToUse = supplyRequiredDescriptor.toBuilder().isLotForLot(ISLOTFORLOT_No).build();
		}

		final MaterialRequest completeRequest = SupplyRequiredHandlerUtils.mkRequest(supplyRequiredDescriptorToUse, context);

		final Quantity maxQtyPerOrder = extractMaxQuantityPerOrder(productPlanning);
		final Quantity maxQtyPerOrderConv = convertQtyToRequestUOM(context, completeRequest, maxQtyPerOrder);
		final ImmutableList<MaterialRequest> partialRequests = createMaterialRequests(completeRequest, maxQtyPerOrderConv);

		final ImmutableList.Builder<PPOrderCandidateAdvisedEvent> result = ImmutableList.builder();
		boolean firstRequest = true;
		for (final MaterialRequest request : partialRequests)
		{
			final int parentPPOrderCandidateId = supplyRequiredDescriptor.getPpOrderCandidateId();

			// this is the PPOrderCandidate which we advise the system to create! 
			final PPOrderCandidate ppOrderCandidate = ppOrderCandidatePojoSupplier.supplyPPOrderCandidatePojoWithoutLines(request)
					.withParentPPOrderCandidateId(parentPPOrderCandidateId);

			final PPOrderCandidateAdvisedEventBuilder eventBuilder = PPOrderCandidateAdvisedEvent.builder()
					.supplyRequiredDescriptor(supplyRequiredDescriptorToUse)
					.eventDescriptor(supplyRequiredDescriptorToUse.getEventDescriptor().withNewEventId())
					.ppOrderCandidate(ppOrderCandidate)
					.directlyCreatePPOrder(productPlanning.isCreatePlan());

			if (firstRequest)
			{
				eventBuilder.tryUpdateExistingCandidate(true);
				firstRequest = false;
			}
			else
			{ // all further events need to get their respective new supply candidates, rather that updating ("overwriting") the existing one.
				eventBuilder.tryUpdateExistingCandidate(false);
			}

			result.add(eventBuilder.build());
			Loggables.addLog("Created PPOrderCandidateAdvisedEvent with quantity={}", request.getQtyToSupply());
		}

		return result.build();
	}
	
	@Nullable
	private static Quantity extractMaxQuantityPerOrder(@NonNull final ProductPlanning productPlanning)
	{
		return productPlanning.getMaxManufacturedQtyPerOrderDispo() != null && productPlanning.getMaxManufacturedQtyPerOrderDispo().signum() > 0
				? productPlanning.getMaxManufacturedQtyPerOrderDispo()
				: null;
	}

	@Nullable
	private Quantity convertQtyToRequestUOM(
			@NonNull final MaterialPlanningContext context,
			@NonNull final MaterialRequest completeRequest,
			@Nullable final Quantity maxQtyPerOrder)
	{
		final Quantity maxQtyPerOrderConv;
		if (maxQtyPerOrder != null)
		{
			maxQtyPerOrderConv = uomConversionBL.convertQuantityTo(
					maxQtyPerOrder,
					context.getProductId(),
					completeRequest.getQtyToSupply().getUomId());
		}
		else
		{
			maxQtyPerOrderConv = null;
		}
		return maxQtyPerOrderConv;
	}

	@VisibleForTesting
	@NonNull
	static ImmutableList<MaterialRequest> createMaterialRequests(
			@NonNull final MaterialRequest completeRequest,
			@Nullable final Quantity maxQtyPerOrder)
	{
		final ImmutableList<MaterialRequest> partialRequests;
		if (maxQtyPerOrder == null || maxQtyPerOrder.signum() <= 0)
		{
			partialRequests = ImmutableList.of(completeRequest);
		}
		else
		{
			final ImmutableList.Builder<MaterialRequest> partialRequestsBuilder = ImmutableList.builder();

			Quantity remainingQty = completeRequest.getQtyToSupply();
			while (remainingQty.signum() > 0)
			{
				final Quantity partialRequestQty = remainingQty.min(maxQtyPerOrder);
				partialRequestsBuilder.add(completeRequest.withQtyToSupply(partialRequestQty));

				remainingQty = remainingQty.subtract(maxQtyPerOrder);
			}
			partialRequests = partialRequestsBuilder.build();
		}
		return partialRequests;
	}
}
