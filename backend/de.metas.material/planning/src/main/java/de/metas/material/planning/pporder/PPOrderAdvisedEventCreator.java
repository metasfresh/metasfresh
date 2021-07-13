package de.metas.material.planning.pporder;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedEvent;
import de.metas.material.event.pporder.PPOrderAdvisedEvent.PPOrderAdvisedEventBuilder;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.event.MaterialRequest;
import de.metas.material.planning.event.SupplyRequiredHandlerUtils;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class PPOrderAdvisedEventCreator
{
	private final PPOrderDemandMatcher ppOrderDemandMatcher;

	private final PPOrderPojoSupplier ppOrderPojoSupplier;
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	public PPOrderAdvisedEventCreator(
			@NonNull final PPOrderDemandMatcher ppOrderDemandMatcher,
			@NonNull final PPOrderPojoSupplier ppOrderPojoSupplier)
	{
		this.ppOrderDemandMatcher = ppOrderDemandMatcher;
		this.ppOrderPojoSupplier = ppOrderPojoSupplier;
	}

	@NonNull
	public ImmutableList<PPOrderAdvisedEvent> createPPOrderAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final IMutableMRPContext mrpContext)
	{
		if (!ppOrderDemandMatcher.matches(mrpContext))
		{
			return ImmutableList.of();
		}

		final I_PP_Product_Planning productPlanning = mrpContext.getProductPlanning();

		final MaterialRequest completeRequest = SupplyRequiredHandlerUtils.mkRequest(supplyRequiredDescriptor, mrpContext);

		final Quantity maxQtyPerOrder = extractMaxQuantityPerOrder(productPlanning);
		final Quantity maxQtyPerOrderConv = convertQtyToRequestUOM(mrpContext, completeRequest, maxQtyPerOrder);
		final ImmutableList<MaterialRequest> partialRequests = createMaterialRequests(completeRequest, maxQtyPerOrderConv);

		final ImmutableList.Builder<PPOrderAdvisedEvent> result = ImmutableList.builder();
		boolean firstRequest = true;
		for (final MaterialRequest request : partialRequests)
		{
			final PPOrder ppOrder = ppOrderPojoSupplier.supplyPPOrderPojoWithLines(request);

			final PPOrderAdvisedEventBuilder eventBuilder = PPOrderAdvisedEvent.builder()
					.supplyRequiredDescriptor(supplyRequiredDescriptor)
					.eventDescriptor(supplyRequiredDescriptor.getEventDescriptor())
					.ppOrder(ppOrder)
					.directlyCreatePPOrder(productPlanning.isCreatePlan())
					.directlyPickSupply(productPlanning.isPickDirectlyIfFeasible());

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
			Loggables.addLog("Created PPOrderAdvisedEvent with quantity={}", request.getQtyToSupply());
		}

		return result.build();
	}

	@Nullable
	private Quantity extractMaxQuantityPerOrder(@NonNull final I_PP_Product_Planning productPlanning)
	{
		final Quantity maxQtyPerOrder;
		if (productPlanning.getMaxManufacturedQtyPerOrder().signum() > 0 && productPlanning.getMaxManufacturedQtyPerOrder_UOM_ID() > 0)
		{
			maxQtyPerOrder = Quantitys.create(
					productPlanning.getMaxManufacturedQtyPerOrder(),
					UomId.ofRepoId(productPlanning.getMaxManufacturedQtyPerOrder_UOM_ID()));
		}
		else
		{
			maxQtyPerOrder = null;
		}
		return maxQtyPerOrder;
	}

	@Nullable
	private Quantity convertQtyToRequestUOM(
			@NonNull final IMutableMRPContext mrpContext,
			@NonNull final MaterialRequest completeRequest,
			@Nullable final Quantity maxQtyPerOrder)
	{
		final Quantity maxQtyPerOrderConv;
		if (maxQtyPerOrder != null)
		{
			maxQtyPerOrderConv = uomConversionBL.convertQuantityTo(
					maxQtyPerOrder,
					mrpContext.getProductId(),
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
