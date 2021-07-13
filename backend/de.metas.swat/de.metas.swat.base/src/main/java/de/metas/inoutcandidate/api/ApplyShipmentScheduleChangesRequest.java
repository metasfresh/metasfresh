/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.api;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.order.DeliveryRule;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Value
@Builder
public class ApplyShipmentScheduleChangesRequest
{
	@NonNull
	ShipmentScheduleId shipmentScheduleId;

	@Nullable
	BPartnerLocationId bPartnerLocationIdOverride;

	@Nullable
	BigDecimal qtyToDeliverStockingUOM;

	@Nullable
	List<CreateAttributeInstanceReq> attributes;

	@Nullable
	ZonedDateTime deliveryDate;

	@Nullable
	DeliveryRule deliveryRule;

	@Nullable
	ShipperId shipperId;

	/**
	 * If true, then don't invalidate the shipment candidate record when it's saved
	 */
	@Builder.Default
	boolean doNotInvalidateOnChange = false;

	public boolean isEmptyRequest()
	{
		return bPartnerLocationIdOverride == null
				&& qtyToDeliverStockingUOM == null
				&& Check.isEmpty(attributes)
				&& deliveryDate == null
				&& deliveryRule == null
				&& shipperId == null;
	}
}
