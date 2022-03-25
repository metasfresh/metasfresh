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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
 import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.Params;
import org.adempiere.warehouse.WarehouseTypeId;

import javax.annotation.Nullable;

@Value
@Builder
public class PickingWFProcessStartParams
{
	@NonNull OrderId salesOrderId;
	@NonNull BPartnerLocationId deliveryBPLocationId;
	@Nullable WarehouseTypeId warehouseTypeId;

	public static PickingWFProcessStartParams of(@NonNull final PickingJobCandidate candidate)
	{
		return builder()
				.salesOrderId(candidate.getSalesOrderId())
				.deliveryBPLocationId(candidate.getDeliveryBPLocationId())
				.warehouseTypeId(candidate.getWarehouseTypeId())
				.build();
	}

	/**
	 * @implNote keep in sync with {@link #ofParams(Params)}
	 */
	public Params toParams()
	{
		return Params.builder()
				.value("salesOrderId", salesOrderId.getRepoId())
				.value("customerId", deliveryBPLocationId.getBpartnerId().getRepoId())
				.value("customerLocationId", deliveryBPLocationId.getRepoId())
				.value("warehouseTypeId", warehouseTypeId != null ? warehouseTypeId.getRepoId() : null)
				.build();
	}

	/**
	 * @implNote keep in sync with {@link #toParams()}
	 */
	public static PickingWFProcessStartParams ofParams(@NonNull final Params params)
	{
		try
		{
			//noinspection ConstantConditions
			return builder()
					.salesOrderId(params.getParameterAsId("salesOrderId", OrderId.class))
					.deliveryBPLocationId(
							BPartnerLocationId.ofRepoIdOrNull(
									params.getParameterAsId("customerId", BPartnerId.class),
									params.getParameterAsInt("customerLocationId", -1)))
					.warehouseTypeId(params.getParameterAsId("warehouseTypeId", WarehouseTypeId.class))
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid params: " + params, ex);
		}
	}

}
