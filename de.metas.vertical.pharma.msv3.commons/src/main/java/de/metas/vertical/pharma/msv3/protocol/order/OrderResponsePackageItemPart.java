package de.metas.vertical.pharma.msv3.protocol.order;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.commons
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class OrderResponsePackageItemPart
{
	@JsonProperty("id")
	@NonNull
	OrderResponsePackageItemPartId id;

	@JsonProperty("qty")
	@NonNull
	Quantity qty;

	@JsonProperty("type")
	String type;

	@JsonProperty("deliveryDate")
	LocalDateTime deliveryDate;

	@JsonProperty("defectReason")
	OrderDefectReason defectReason;

	@JsonProperty("tour")
	String tour;

	@JsonProperty("tourId")
	String tourId;

	@JsonProperty("tourDeviation")
	boolean tourDeviation;

	@Builder
	@JsonCreator
	private OrderResponsePackageItemPart(
			@JsonProperty("id") final OrderResponsePackageItemPartId id,
			@JsonProperty("qty") @NonNull final Quantity qty,
			@JsonProperty("type") final String type,
			@JsonProperty("deliveryDate") final LocalDateTime deliveryDate,
			@JsonProperty("defectReason") final OrderDefectReason defectReason,
			@JsonProperty("tour") final String tour,
			@JsonProperty("tourId") final String tourId,
			@JsonProperty("tourDeviation") final boolean tourDeviation)
	{
		this.id = id != null ? id : OrderResponsePackageItemPartId.random();
		this.qty = qty;
		this.type = type;
		this.deliveryDate = deliveryDate;
		this.defectReason = defectReason;
		this.tour = tour;
		this.tourId = tourId;
		this.tourDeviation = tourDeviation;
	}
}
