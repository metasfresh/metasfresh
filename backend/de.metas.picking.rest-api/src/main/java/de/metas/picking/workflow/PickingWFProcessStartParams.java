/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.workflow;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.lang.RepoIdAwares;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.Params;
import org.adempiere.warehouse.WarehouseTypeId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class PickingWFProcessStartParams
{
	@NonNull PickingJobAggregationType aggregationType;
	@Nullable OrderId salesOrderId;
	@Nullable BPartnerLocationId deliveryBPLocationId;
	@Nullable WarehouseTypeId warehouseTypeId;
	@Nullable ProductId productId;
	@Nullable Quantity qtyToDeliver;
	@Nullable ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;

	public static PickingWFProcessStartParams of(@NonNull final PickingJobCandidate candidate)
	{
		return builder()
				.aggregationType(candidate.getAggregationType())
				.salesOrderId(candidate.getSalesOrderId())
				.deliveryBPLocationId(candidate.getDeliveryBPLocationId())
				.warehouseTypeId(candidate.getWarehouseTypeId())
				.productId(candidate.getProductId())
				.qtyToDeliver(candidate.getQtyToDeliver())
				.shipmentScheduleIds(candidate.getShipmentScheduleIds())
				.build();
	}

	public static PickingWFProcessStartParams of(@NonNull final PickingJobReference candidate)
	{
		return builder()
				.aggregationType(candidate.getAggregationType())
				.salesOrderId(candidate.getSalesOrderId())
				.deliveryBPLocationId(candidate.getDeliveryBPLocationId())
				//.warehouseTypeId(candidate.getWarehouseTypeId()) // N/A
				.productId(candidate.getProductId())
				.qtyToDeliver(candidate.getQtyToDeliver())
				.shipmentScheduleIds(candidate.getShipmentScheduleIds())
				.build();
	}


	/**
	 * @implNote keep in sync with {@link #ofParams(Params)}
	 */
	public Params toParams()
	{
		final BPartnerId customerId = getCustomerId();

		return Params.builder()
				.value("aggregationType", aggregationType.toJson())
				.value("salesOrderId", salesOrderId != null ? salesOrderId.getRepoId() : null)
				.value("customerId", customerId != null ? customerId.getRepoId() : null)
				.value("customerLocationId", deliveryBPLocationId != null ? deliveryBPLocationId.getRepoId() : null)
				.value("warehouseTypeId", warehouseTypeId != null ? warehouseTypeId.getRepoId() : null)
				.value("productId", productId != null ? productId.getRepoId() : null)
				.value("qtyToDeliver", qtyToDeliver != null ? qtyToDeliver.toBigDecimal() : null)
				.value("uomId", qtyToDeliver != null ? qtyToDeliver.getUomId().getRepoId() : null)
				.value("shipmentScheduleIds", shipmentScheduleIds != null ? RepoIdAwares.toCommaSeparatedString(shipmentScheduleIds) : null)
				.build();
	}

	/**
	 * @implNote keep in sync with {@link #toParams()}
	 */
	public static PickingWFProcessStartParams ofParams(@NonNull final Params params)
	{
		try
		{
			final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = RepoIdAwares.ofCommaSeparatedSet(params.getParameterAsString("shipmentScheduleIds"), ShipmentScheduleId.class);

			//noinspection ConstantConditions
			return builder()
					.aggregationType(PickingJobAggregationType.ofJson(params.getParameterAsString("aggregationType")))
					.salesOrderId(params.getParameterAsId("salesOrderId", OrderId.class))
					.deliveryBPLocationId(
							BPartnerLocationId.ofRepoIdOrNull(
									params.getParameterAsId("customerId", BPartnerId.class),
									params.getParameterAsInt("customerLocationId", -1)))
					.warehouseTypeId(params.getParameterAsId("warehouseTypeId", WarehouseTypeId.class))
					.productId(params.getParameterAsId("productId", ProductId.class))
					.qtyToDeliver(extractQtyToDeliver(params))
					.shipmentScheduleIds(shipmentScheduleIds != null && !shipmentScheduleIds.isEmpty() ? shipmentScheduleIds : null)
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid params: " + params, ex);
		}
	}

	@Nullable
	private static Quantity extractQtyToDeliver(@NonNull final Params params)
	{
		final BigDecimal qtyToDeliverBD = params.getParameterAsBigDecimal("qtyToDeliver");
		if (qtyToDeliverBD == null)
		{
			return null;
		}

		final UomId uomId = params.getParameterAsId("uomId", UomId.class);
		if (uomId == null)
		{
			return null;
		}

		return Quantitys.of(qtyToDeliverBD, uomId);
	}

	@Nullable
	public BPartnerId getCustomerId() {return this.deliveryBPLocationId != null ? this.deliveryBPLocationId.getBpartnerId() : null;}
}
