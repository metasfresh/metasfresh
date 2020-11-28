/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.shipping;

import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.order.DeliveryRule;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Value
class UpdateShipmentScheduleRequest
{
	@NonNull
	ShipmentScheduleId shipmentScheduleId;

	@Nullable
	ZonedDateTime deliveryDate;

	@Nullable
	BigDecimal qtyToDeliverInStockingUOM;

	@Nullable
	LocationBasicInfo bPartnerLocation;

	@Nullable
	String bPartnerCode;

	@Nullable
	List<CreateAttributeInstanceReq> attributes;

	@Nullable
	DeliveryRule deliveryRule;

	@Nullable
	ShipperId shipperId;

	@Builder
	public UpdateShipmentScheduleRequest(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@Nullable final ZonedDateTime deliveryDate,
			@Nullable final BigDecimal qtyToDeliverInStockingUOM,
			@Nullable final LocationBasicInfo bPartnerLocation,
			@Nullable final String bPartnerCode,
			@Nullable final DeliveryRule deliveryRule,
			@Nullable final List<CreateAttributeInstanceReq> attributes,
			@Nullable final ShipperId shipperId)
	{
		if (Check.isNotBlank(bPartnerCode) && bPartnerLocation == null)
		{
			throw new AdempiereException("Invalid request! The bPartenr cannot be changed without changing the location!");
		}

		if (deliveryDate == null
				&& qtyToDeliverInStockingUOM == null
				&& bPartnerLocation == null
				&& Check.isBlank(bPartnerCode)
				&& Check.isEmpty(attributes)
				&& deliveryRule == null
		        && shipperId == null)
		{
			throw new AdempiereException("Empty request");
		}

		this.shipmentScheduleId = shipmentScheduleId;
		this.deliveryDate = deliveryDate;
		this.qtyToDeliverInStockingUOM = qtyToDeliverInStockingUOM;
		this.bPartnerLocation = bPartnerLocation;
		this.bPartnerCode = bPartnerCode;
		this.attributes = attributes;
		this.deliveryRule = deliveryRule;
		this.shipperId = shipperId;
	}
}
