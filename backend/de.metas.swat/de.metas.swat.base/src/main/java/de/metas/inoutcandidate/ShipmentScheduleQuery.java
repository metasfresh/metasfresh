/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.inoutcandidate;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;

@Value
@Builder
public class ShipmentScheduleQuery
{
	private static final ShipmentScheduleQuery ANY = builder().build();

	@Builder.Default
	@NonNull QueryLimit limit = QueryLimit.NO_LIMIT;
	@Nullable IQueryFilter<I_M_ShipmentSchedule> queryFilter;

	@NonNull @Singular ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;
	@Nullable Instant canBeExportedFrom;
	@Nullable APIExportStatus exportStatus;
	@Nullable OrgId orgId;
	@Nullable AttributesKey attributesKey;
	@Nullable ProductId productId;
	@Nullable WarehouseId warehouseId;
	@Nullable ShipperId shipperId;
	@Builder.Default boolean includeWithQtyToDeliverZero = true;
	@Builder.Default boolean includeInvalid = true;
	@Builder.Default boolean includeProcessed = true;
	boolean fromCompleteOrderOrNullOrder;
	boolean orderByOrderId;
	boolean onlyNonZeroReservedQty;
	@Nullable LocalDate preparationDate;

	/**
	 * Only export a shipment schedule if its order does not have any schedule which is not yet ready to be exported.
	 */
	boolean onlyIfAllFromOrderExportable;

	public boolean isAny() {return this.equals(ANY);}
}
