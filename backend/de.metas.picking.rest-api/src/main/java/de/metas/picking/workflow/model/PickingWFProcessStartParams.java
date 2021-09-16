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

package de.metas.picking.workflow.model;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.api.Params;
import org.adempiere.warehouse.WarehouseTypeId;

import javax.annotation.Nullable;

@Value
@Builder
public class PickingWFProcessStartParams
{
	@NonNull OrderId salesOrderId;
	@NonNull BPartnerLocationId customerLocationId;
	@Nullable WarehouseTypeId warehouseTypeId;
	boolean locked;

	/**
	 * @implNote keep in sync with {@link #ofParams(Params)}
	 */
	public Params toParams()
	{
		return Params.builder()
				.value("salesOrderId", salesOrderId.getRepoId())
				.value("customerId", customerLocationId.getBpartnerId().getRepoId())
				.value("customerLocationId", customerLocationId.getRepoId())
				.value("warehouseTypeId", warehouseTypeId != null ? warehouseTypeId.getRepoId() : null)
				.value("locked", locked)
				.build();
	}

	/**
	 * @implNote keep in sync with {@link #toParams()}
	 */
	public static PickingWFProcessStartParams ofParams(@NonNull final Params params)
	{
		//noinspection ConstantConditions
		return builder()
				.salesOrderId(params.getParameterAsId("salesOrderId", OrderId.class))
				.customerLocationId(
						BPartnerLocationId.ofRepoIdOrNull(
								params.getParameterAsId("customerId", BPartnerId.class),
								params.getParameterAsInt("customerLocationId", -1)))
				.warehouseTypeId(params.getParameterAsId("warehouseTypeId", WarehouseTypeId.class))
				.locked(params.getParameterAsBool("locked"))
				.build();
	}

}
