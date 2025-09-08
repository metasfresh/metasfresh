package org.eevolution.api;

import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.planning.ProductPlanningId;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.service.PPOrderCandidatePojoConverter;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class PPOrderCreateRequestBuilder
{
	private final ValueAggregator<ClientAndOrgId> clientAndOrgId = new ValueAggregator<>();
	private final ValueAggregator<ProductPlanningId> productPlanningId = new ValueAggregator<>();
	private final ValueAggregator<MaterialDispoGroupId> materialDispoGroupId = new ValueAggregator<>();
	private final ValueAggregator<ResourceId> plantId = new ValueAggregator<>();
	private final ValueAggregator<ResourceId> workstationId = new ValueAggregator<>();
	private final ValueAggregator<WarehouseId> warehouseId = new ValueAggregator<>();
	private final ValueAggregator<ProductId> productId = new ValueAggregator<>();
	private final ValueAggregator<AttributeSetInstanceId> attributeSetInstanceId = new ValueAggregator<>();
	private final ValueAggregator<Instant> datePromised = new ValueAggregator<>();
	private final ValueAggregator<Instant> dateStartSchedule = new ValueAggregator<>();
	private final ValueAggregator<OrderLineId> salesOrderLineId = new ValueAggregator<>();
	private final ValueAggregator<ShipmentScheduleId> shipmentScheduleId = new ValueAggregator<>();
	private final ValueAggregator<HUPIItemProductId> packingMaterialId = new ValueAggregator<>();

	public void addRecord(@NonNull final I_PP_Order_Candidate ppOrderCandidate)
	{
		this.clientAndOrgId.setValue(ClientAndOrgId.ofClientAndOrg(ppOrderCandidate.getAD_Client_ID(), ppOrderCandidate.getAD_Org_ID()));
		this.productPlanningId.setValue(ProductPlanningId.ofRepoIdOrNull(ppOrderCandidate.getPP_Product_Planning_ID()));
		this.materialDispoGroupId.setValue(PPOrderCandidatePojoConverter.getMaterialDispoGroupIdOrNull(ppOrderCandidate));
		this.plantId.setValue(ResourceId.ofRepoIdOrNull(ppOrderCandidate.getS_Resource_ID()));
		this.workstationId.setValue(ResourceId.ofRepoIdOrNull(ppOrderCandidate.getWorkStation_ID()));
		this.warehouseId.setValue(WarehouseId.ofRepoIdOrNull(ppOrderCandidate.getM_Warehouse_ID()));
		this.productId.setValue(ProductId.ofRepoIdOrNull(ppOrderCandidate.getM_Product_ID()));
		this.attributeSetInstanceId.setValue(AttributeSetInstanceId.ofRepoIdOrNone(ppOrderCandidate.getM_AttributeSetInstance_ID()));
		this.datePromised.setValue(TimeUtil.asInstant(ppOrderCandidate.getDatePromised()));
		this.dateStartSchedule.setValue(TimeUtil.asInstant(ppOrderCandidate.getDateStartSchedule()));
		this.salesOrderLineId.setValue(OrderLineId.ofRepoIdOrNull(ppOrderCandidate.getC_OrderLine_ID()));
		this.shipmentScheduleId.setValue(ShipmentScheduleId.ofRepoIdOrNull(ppOrderCandidate.getM_ShipmentSchedule_ID()));
		this.packingMaterialId.setValue(HUPIItemProductId.ofRepoIdOrNull(ppOrderCandidate.getM_HU_PI_Item_Product_ID()));
	}

	@NonNull
	public PPOrderCreateRequest build(@NonNull final Quantity qtyRequired)
	{
		return PPOrderCreateRequest.builder()
				.clientAndOrgId(clientAndOrgId.getAggregatedValue((list) -> throwErrorOnMoreThanOneUniqueValue("clientAndOrgId", list)))
				.productPlanningId(productPlanningId.getAggregatedValue((list) -> throwErrorOnMoreThanOneUniqueValue("productPlanningId", list)))
				.materialDispoGroupId(materialDispoGroupId.getAggregatedValue(PPOrderCreateRequestBuilder::returnNullOnMoreThanOneUniqueValue))
				.plantId(plantId.getAggregatedValue((list) -> throwErrorOnMoreThanOneUniqueValue("plantId", list)))
				.workstationId(workstationId.getAggregatedValue(PPOrderCreateRequestBuilder::returnNullOnMoreThanOneUniqueValue))
				.warehouseId(warehouseId.getAggregatedValue((list) -> throwErrorOnMoreThanOneUniqueValue("warehouseId", list)))
				//
				.productId(productId.getAggregatedValue((list) -> throwErrorOnMoreThanOneUniqueValue("productId", list)))
				.attributeSetInstanceId(attributeSetInstanceId.getAggregatedValue(PPOrderCreateRequestBuilder::returnNullOnMoreThanOneUniqueValue))
				//
				.dateOrdered(SystemTime.asInstant())
				.datePromised(datePromised.getAggregatedValue((list) -> throwErrorOnMoreThanOneUniqueValue("datePromised", list)))
				.dateStartSchedule(dateStartSchedule.getAggregatedValue((list) -> throwErrorOnMoreThanOneUniqueValue("dateStartSchedule", list)))
				//
				.salesOrderLineId(salesOrderLineId.getAggregatedValue(PPOrderCreateRequestBuilder::returnNullOnMoreThanOneUniqueValue))
				.shipmentScheduleId(shipmentScheduleId.getAggregatedValue(PPOrderCreateRequestBuilder::returnNullOnMoreThanOneUniqueValue))
				//
				.packingMaterialId(packingMaterialId.getAggregatedValue(PPOrderCreateRequestBuilder::returnNullOnMoreThanOneUniqueValue))
				.completeDocument(false)
				.qtyRequired(qtyRequired)
				.build();
	}

	private static <T> T throwErrorOnMoreThanOneUniqueValue(@NonNull final String fieldName, @NonNull final List<T> values)
	{
		throw new AdempiereException("More than one value found for fieldName=" + fieldName)
				.appendParametersToMessage()
				.setParameter("values", values.stream().map(String::valueOf).collect(Collectors.joining(",")));
	}

	@Nullable
	private static <T> T returnNullOnMoreThanOneUniqueValue(@NonNull final List<T> values)
	{
		return null;
	}

	private static class ValueAggregator<T>
	{
		@NonNull
		private final List<T> values = new ArrayList<>();

		public void setValue(@Nullable final T newValue)
		{
			values.add(newValue);
		}

		@Nullable
		public T getAggregatedValue(@NonNull final Function<List<T>, T> onMoreThanOneUniqueValue)
		{
			if (values.isEmpty())
			{
				return null;
			}

			final T initialValue = values.get(0);
			for (final T value : values)
			{
				if (!Objects.equals(initialValue, value))
				{
					return onMoreThanOneUniqueValue.apply(values);
				}
			}

			return initialValue;
		}
	}
}
