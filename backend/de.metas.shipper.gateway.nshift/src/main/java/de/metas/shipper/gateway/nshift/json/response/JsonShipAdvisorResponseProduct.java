/*
 * #%L
 * de.metas.shipper.gateway.nshift
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

package de.metas.shipper.gateway.nshift.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.metas.shipper.gateway.nshift.json.BooleanToIntConverter;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonShipAdvisorResponseProduct
{
	@JsonProperty("ShippingRulesID")
	String shippingRulesID;

	@JsonProperty("ProdConceptID")
	int prodConceptID;

	@JsonProperty("ProdCSID")
	int prodCSID;

	@JsonProperty("ProdName")
	String prodName;

	@JsonProperty("ProductGoodsType")
	JsonShipAdvisorResponseGoodsType productGoodsType;

	@JsonProperty("Services")
	@Singular
	List<JsonShipAdvisorResponseService> services;

	@JsonProperty("Token")
	String token;

	@JsonProperty("SupportsDropPoint")
	@JsonSerialize(converter = BooleanToIntConverter.class)
	Boolean supportsDropPoint;
}