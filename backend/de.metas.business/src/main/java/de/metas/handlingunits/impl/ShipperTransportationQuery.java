/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collection;

@Value
@Builder
public class ShipperTransportationQuery
{
	private static final ShipperTransportationQuery ANY = builder().build();

	@Nullable OrgId orgId;
	@Nullable ShipperId shipperId;
	@Nullable BPartnerLocationId shipperBPartnerAndLocationId;
	@Nullable LocalDate shipDate;
	@Nullable ShipperTransportationId shipperTransportationToExclude;
	@NonNull @Singular Collection<OrderId> orderIds;
	@NonNull @Singular Collection<OrderLineId> orderLineIds;
	@Nullable Boolean processed;

	public boolean isAny()
	{
		return this.equals(ANY);
	}
}
