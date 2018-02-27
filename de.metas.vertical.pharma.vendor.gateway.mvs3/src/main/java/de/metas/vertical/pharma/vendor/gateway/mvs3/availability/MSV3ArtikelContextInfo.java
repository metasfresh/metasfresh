package de.metas.vertical.pharma.vendor.gateway.mvs3.availability;

import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class MSV3ArtikelContextInfo
{
	public static MSV3ArtikelContextInfo forRequestItem(
			@NonNull final AvailabilityRequestItem availabilityRequestItem)
	{
		return new MSV3ArtikelContextInfo(
				availabilityRequestItem.getSalesOrderLineId(),
				availabilityRequestItem.getPurchaseCandidateId());
	}

	int salesOrderLineId;
	int purchaseCandidateId;

	private MSV3ArtikelContextInfo(
			final int salesOrderLineId,
			final int purchaseCandidateId)
	{
		this.salesOrderLineId = salesOrderLineId;
		this.purchaseCandidateId = purchaseCandidateId;
	}

}
