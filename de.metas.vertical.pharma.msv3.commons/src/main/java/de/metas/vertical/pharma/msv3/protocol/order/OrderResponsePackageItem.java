package de.metas.vertical.pharma.msv3.protocol.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
public class OrderResponsePackageItem
{
	@JsonProperty("requestId")
	OrderCreateRequestPackageItemId requestId;

	@JsonProperty("pzn")
	PZN pzn;

	@JsonProperty("qty")
	Quantity qty;

	@JsonProperty("deliverySpecifications")
	DeliverySpecifications deliverySpecifications;

	@JsonProperty("parts")
	List<OrderResponsePackageItemPart> parts;

	@JsonProperty("substitution")
	OrderResponsePackageItemSubstitution substitution;

	@JsonProperty("olCandId")
	int olCandId;
	
	@JsonProperty("purchaseCandidateId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	MSV3PurchaseCandidateId purchaseCandidateId;

	@Builder
	@JsonCreator
	private OrderResponsePackageItem(
			@JsonProperty("requestId") @NonNull final OrderCreateRequestPackageItemId requestId,
			@JsonProperty("pzn") @NonNull final PZN pzn,
			@JsonProperty("qty") @NonNull final Quantity qty,
			@JsonProperty("deliverySpecifications") @NonNull final DeliverySpecifications deliverySpecifications,
			@JsonProperty("parts") @NonNull @Singular List<OrderResponsePackageItemPart> parts,
			@JsonProperty("substitution") final OrderResponsePackageItemSubstitution substitution,
			@JsonProperty("olCandId") int olCandId,
			@JsonProperty("purchaseCandidateId") MSV3PurchaseCandidateId purchaseCandidateId)
	{
		this.requestId = requestId;
		this.pzn = pzn;
		this.qty = qty;
		this.deliverySpecifications = deliverySpecifications;
		this.parts = ImmutableList.copyOf(parts);
		this.substitution = substitution;
		this.olCandId = olCandId;
		this.purchaseCandidateId = purchaseCandidateId;
	}
}
