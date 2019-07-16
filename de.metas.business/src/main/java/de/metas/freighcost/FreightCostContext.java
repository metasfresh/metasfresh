package de.metas.freighcost;

import java.time.LocalDate;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import de.metas.location.CountryId;
import de.metas.money.Money;
import de.metas.order.DeliveryViaRule;
import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class FreightCostContext
{
	@NonNull
	OrgId shipFromOrgId;

	BPartnerId shipToBPartnerId;
	CountryId shipToCountryId;

	ShipperId shipperId;

	@NonNull
	LocalDate date;

	@NonNull
	FreightCostRule freightCostRule;

	@NonNull
	DeliveryViaRule deliveryViaRule;

	@Nullable
	Money manualFreightAmt;

	@Builder
	private FreightCostContext(
			@NonNull final OrgId shipFromOrgId,
			@Nullable final BPartnerId shipToBPartnerId,
			@Nullable final CountryId shipToCountryId,
			@Nullable final ShipperId shipperId,
			@NonNull final LocalDate date,
			@NonNull final FreightCostRule freightCostRule,
			@NonNull final DeliveryViaRule deliveryViaRule,
			@Nullable final Money manualFreightAmt)
	{
		this.shipFromOrgId = shipFromOrgId;
		this.shipToBPartnerId = shipToBPartnerId;
		this.shipToCountryId = shipToCountryId;
		this.shipperId = shipperId;
		this.date = date;
		this.freightCostRule = freightCostRule;
		this.deliveryViaRule = deliveryViaRule;

		if(freightCostRule.isFixPrice())
		{
			// Check.assumeNotNull(manualFreightAmt, "Parameter manualFreightAmt is not null"); // null is OK
			this.manualFreightAmt = manualFreightAmt;
		}
		else
		{
			this.manualFreightAmt = null;
		}
	}

}
