/*
 * #%L
 * de.metas.business
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

package de.metas.shipping;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.sscc18.SSCC18;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.sql.Timestamp;

@Value
@Builder(toBuilder = true)
public class PurchaseShippingPackageCreateRequest
{
	@NonNull OrderId orderId;
	@Nullable OrderLineId orderLineId;
	@NonNull ShipperTransportationId shipperTransportationIdl;
	@NonNull ShipperId shiperId;
	@NonNull OrgId orgId;
	@NonNull Timestamp datePromised;
	@NonNull BPartnerId bPartnerId;
	@NonNull BPartnerLocationId bPartnerLocationId;
	@Nullable SSCC18 sscc;

}
