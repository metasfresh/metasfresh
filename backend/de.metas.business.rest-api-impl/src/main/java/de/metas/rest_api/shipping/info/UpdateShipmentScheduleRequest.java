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

package de.metas.rest_api.shipping.info;

import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
public class UpdateShipmentScheduleRequest
{
	@NonNull
	ShipmentScheduleId shipmentScheduleId;

	@Nullable
	LocalDateTime deliveryDate;

	@Nullable
	BigDecimal qtyToDeliver;

	@Nullable
	LocationBasicInfo bPartnerLocation;

	@Nullable
	String bPartnerCode;

	@Nullable
	List<JsonAttributeInstance> attributes;

	@Nullable
	String deliveryRuleCode;

	@Builder
	public UpdateShipmentScheduleRequest(@NonNull final ShipmentScheduleId shipmentScheduleId,
			@Nullable final LocalDateTime deliveryDate,
			@Nullable final BigDecimal qtyToDeliver,
			@Nullable final LocationBasicInfo bPartnerLocation,
			@Nullable final String bPartnerCode,
			@Nullable final String deliveryRuleCode,
			@Nullable final List<JsonAttributeInstance> attributes)
	{
		if (Check.isNotBlank(bPartnerCode) && bPartnerLocation == null)
		{
			throw new AdempiereException("Invalid request!");
		}

		this.shipmentScheduleId = shipmentScheduleId;
		this.deliveryDate = deliveryDate;
		this.qtyToDeliver = qtyToDeliver;
		this.bPartnerLocation = bPartnerLocation;
		this.bPartnerCode = bPartnerCode;
		this.attributes = attributes;
		this.deliveryRuleCode = deliveryRuleCode;
	}

	public boolean emptyRequest()
	{
		return deliveryDate == null
				&& qtyToDeliver == null
				&& bPartnerLocation == null
				&& Check.isBlank(bPartnerCode)
				&& Check.isEmpty(attributes)
				&& Check.isBlank(deliveryRuleCode);
	}
}
