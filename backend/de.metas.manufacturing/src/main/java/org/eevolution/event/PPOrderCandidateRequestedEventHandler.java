/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.event;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.inout.ShipmentScheduleId;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrderCandidateRequestedEvent;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.planning.ProductPlanningId;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.async.PPOrderCandidateEnqueuer;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.service.PPOrderCandidateCreateUpdateRequest;
import org.eevolution.productioncandidate.service.PPOrderCandidateService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class PPOrderCandidateRequestedEventHandler implements MaterialEventHandler<PPOrderCandidateRequestedEvent>
{
	private final IProductBL productBL = Services.get(IProductBL.class);

	private final PPOrderCandidateService ppOrderCandidateService;
	private final PPOrderCandidateEnqueuer ppOrderCandidateEnqueuer;

	public PPOrderCandidateRequestedEventHandler(
			@NonNull final PPOrderCandidateService ppOrderCandidateService,
			@NonNull final PPOrderCandidateEnqueuer ppOrderCandidateEnqueuer)
	{
		this.ppOrderCandidateService = ppOrderCandidateService;
		this.ppOrderCandidateEnqueuer = ppOrderCandidateEnqueuer;
	}

	@Override
	public Collection<Class<? extends PPOrderCandidateRequestedEvent>> getHandledEventType()
	{
		return ImmutableList.of(PPOrderCandidateRequestedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final PPOrderCandidateRequestedEvent event)
	{
		final I_PP_Order_Candidate createdCandidate = createProductionOrderCandidate(event);

		if (event.isDirectlyCreatePPOrder())
		{
			final ImmutableList<PPOrderCandidateId> ppOrderCandidateIds = ImmutableList.of(PPOrderCandidateId.ofRepoId(createdCandidate.getPP_Order_Candidate_ID()));

			final PPOrderCandidateEnqueuer.Result result = ppOrderCandidateEnqueuer.enqueueCandidateIds(ppOrderCandidateIds);

			Check.errorIf(result.getEnqueuedPackagesCount() != 1, "Couldn't enqueue workpackage for PPOrder creation!, event: {}", event);
		}
	}

	/**
	 * Creates a production order candidate.
	 */
	@VisibleForTesting
	I_PP_Order_Candidate createProductionOrderCandidate(@NonNull final PPOrderCandidateRequestedEvent ppOrderCandidateRequestedEvent)
	{
		final PPOrderData ppOrderData = ppOrderCandidateRequestedEvent.getPpOrderCandidate().getPpOrderData();
		final ProductId productId = ProductId.ofRepoId(ppOrderData.getProductDescriptor().getProductId());
		final Quantity qtyRequired = Quantity.of(ppOrderData.getQtyRequired(), productBL.getStockUOM(productId));
		final String traceId = ppOrderCandidateRequestedEvent.getEventDescriptor().getTraceId();
		final boolean isSimulated = ppOrderCandidateRequestedEvent.getPpOrderCandidate().isSimulated();

		return ppOrderCandidateService.createUpdateCandidate(PPOrderCandidateCreateUpdateRequest.builder()
															   .clientAndOrgId(ppOrderData.getClientAndOrgId())
															   .productPlanningId(ProductPlanningId.ofRepoIdOrNull(ppOrderData.getProductPlanningId()))
															   .materialDispoGroupId(ppOrderData.getMaterialDispoGroupId())
															   .plantId(ppOrderData.getPlantId())
															   .warehouseId(ppOrderData.getWarehouseId())
															   .productId(productId)
															   .attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(ppOrderData.getProductDescriptor().getAttributeSetInstanceId()))
															   .qtyRequired(qtyRequired)
															   .datePromised(ppOrderData.getDatePromised())
															   .dateStartSchedule(ppOrderData.getDateStartSchedule())
															   .salesOrderLineId(OrderLineId.ofRepoIdOrNull(ppOrderData.getOrderLineIdAsRepoId()))
															   .shipmentScheduleId(ShipmentScheduleId.ofRepoIdOrNull(ppOrderData.getShipmentScheduleIdAsRepoId()))
															   .simulated(isSimulated)
															   .traceId(traceId)
															   .packingMaterialId(ppOrderData.getPackingMaterialId())
															   .build());
	}
}