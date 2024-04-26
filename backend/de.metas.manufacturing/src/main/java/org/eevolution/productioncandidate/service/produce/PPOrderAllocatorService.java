/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.service.produce;

import de.metas.common.util.time.SystemTime;
import de.metas.inout.ShipmentScheduleId;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.ProductPlanningId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_S_Resource;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.service.PPOrderCandidatePojoConverter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PPOrderAllocatorService
{
	@NonNull
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull
	private final IResourceProductService resourceDAO = Services.get(IResourceProductService.class);

	@NonNull
	private final PPOrderCandidatePojoConverter ppOrderCandidateConverter;

	public PPOrderAllocatorService(final @NonNull PPOrderCandidatePojoConverter ppOrderCandidateConverter)
	{
		this.ppOrderCandidateConverter = ppOrderCandidateConverter;
	}

	@NonNull
	public PPOrderAllocator buildAllocator(@NonNull final PPOrderCandidateToAllocate ppOrderCandidateToAllocate)
	{
		final Quantity capacity = getCapacityPerProductionCycle(ppOrderCandidateToAllocate.getPpOrderCandidate());

		final String headerAggKey = ppOrderCandidateToAllocate.getHeaderAggregationKey();

		final PPOrderCreateRequest.PPOrderCreateRequestBuilder requestBuilder = createRequestBuilder(ppOrderCandidateToAllocate.getPpOrderCandidate());

		return PPOrderAllocator.builder()
				.ppOrderCreateRequestBuilder(requestBuilder)
				.capacityPerProductionCycle(capacity)
				.headerAggKey(headerAggKey)
				.build();
	}

	@NonNull
	private PPOrderCreateRequest.PPOrderCreateRequestBuilder createRequestBuilder(@NonNull final I_PP_Order_Candidate candidateRecord)
	{
		final PPOrderCandidate ppOrderCandidatePojo = ppOrderCandidateConverter.toPPOrderCandidate(candidateRecord);

		final ProductId productId = ProductId.ofRepoId(ppOrderCandidatePojo.getPpOrderData().getProductDescriptor().getProductId());

		return PPOrderCreateRequest.builder()
				.clientAndOrgId(ppOrderCandidatePojo.getPpOrderData().getClientAndOrgId())
				.productPlanningId(ProductPlanningId.ofRepoId(ppOrderCandidatePojo.getPpOrderData().getProductPlanningId()))
				.materialDispoGroupId(ppOrderCandidatePojo.getPpOrderData().getMaterialDispoGroupId())
				.plantId(ppOrderCandidatePojo.getPpOrderData().getPlantId())
				.workstationId(ppOrderCandidatePojo.getPpOrderData().getWorkstationId())
				.warehouseId(ppOrderCandidatePojo.getPpOrderData().getWarehouseId())
				//
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(ppOrderCandidatePojo.getPpOrderData().getProductDescriptor().getAttributeSetInstanceId()))
				//
				.dateOrdered(SystemTime.asInstant())
				.datePromised(ppOrderCandidatePojo.getPpOrderData().getDatePromised())
				.dateStartSchedule(ppOrderCandidatePojo.getPpOrderData().getDateStartSchedule())
				//
				.salesOrderLineId(OrderLineId.ofRepoIdOrNull(ppOrderCandidatePojo.getPpOrderData().getOrderLineIdAsRepoId()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoIdOrNull(ppOrderCandidatePojo.getPpOrderData().getShipmentScheduleIdAsRepoId()))
				//
				.packingMaterialId(ppOrderCandidatePojo.getPpOrderData().getPackingMaterialId())
				//dev-note: there is a custom logic for completing PPOrder when created from PP_Order_Candidate (due to MD_Candidates interaction)
				//see: org.eevolution.productioncandidate.service.produce.PPOrderProducerFromCandidate.processPPOrderCandidates
				.completeDocument(false);
	}

	@NonNull
	private Quantity getCapacityPerProductionCycle(@NonNull final I_PP_Order_Candidate ppOrderCandidate)
	{
		final UomId candidateUomId = UomId.ofRepoId(ppOrderCandidate.getC_UOM_ID());
		final Quantity capacityOverride = Quantitys.of(ppOrderCandidate.getCapacityPerProductionCycleOverride(),
														   candidateUomId);
		if (capacityOverride.isPositive())
		{
			return capacityOverride;
		}

		final I_S_Resource resource = resourceDAO.getById(ResourceId.ofRepoId(ppOrderCandidate.getS_Resource_ID()));

		if (resource.getCapacityPerProductionCycle().signum() == 0)
		{
			return Quantitys.of(BigDecimal.ZERO, candidateUomId);
		}

		if (resource.getCapacityPerProductionCycle_UOM_ID() <= 0)
		{
			throw new AdempiereException("Unit of measurement for capacity per production cycle cannot be missing if capacity is provided!")
					.appendParametersToMessage()
					.setParameter("S_Resource_ID", resource.getS_Resource_ID());
		}

		final Quantity capacityQty = Quantitys.of(resource.getCapacityPerProductionCycle(),
												  UomId.ofRepoId(resource.getCapacityPerProductionCycle_UOM_ID()));

		return uomConversionBL
				.convertQuantityTo(
						capacityQty,
						ProductId.ofRepoId(ppOrderCandidate.getM_Product_ID()),
						candidateUomId);
	}
}
