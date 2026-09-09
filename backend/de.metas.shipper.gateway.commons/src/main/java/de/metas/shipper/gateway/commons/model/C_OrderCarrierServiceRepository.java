/*
 * #%L
 * de.metas.shipper.gateway.commons
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

package de.metas.shipper.gateway.commons.model;

import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Order_Carrier_Service;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link I_C_Order_Carrier_Service} bridge rows.
 * Repository Tables: C_Order_Carrier_Service
 * Repository Cluster: C_OrderCarrierServiceRepository, ShipmentScheduleCarrierServiceRepository
 *
 * <p>The read is mirrored in {@code de.metas.swat.base}'s {@link de.metas.inoutcandidate.ShipmentScheduleCarrierServiceRepository}
 * because that module cannot depend on this one (no de.metas.swat.base → de.metas.shipper.gateway.commons dependency; adding it
 * would risk a module cycle). Same cluster ⇒ the overlap is deliberate, not a split-ownership bug.
 */
@Repository
public class C_OrderCarrierServiceRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Returns the {@link CarrierServiceId}s assigned to the given order via {@link I_C_Order_Carrier_Service},
	 * excluding soft-deleted (inactive) rows.
	 */
	public ImmutableSet<CarrierServiceId> getCarrierServiceIdsByOrderId(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_Order_Carrier_Service.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order_Carrier_Service.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.stream()
				.map(row -> CarrierServiceId.ofRepoId(row.getCarrier_Service_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Deletes all {@link I_C_Order_Carrier_Service} rows for the given order.
	 * No-op for new (not-yet-saved) orders (orderId ≤ 0).
	 */
	public void deleteByOrderId(@NonNull final OrderId orderId)
	{
		queryBL.createQueryBuilder(I_C_Order_Carrier_Service.class)
				.addEqualsFilter(I_C_Order_Carrier_Service.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.delete();
	}
}
