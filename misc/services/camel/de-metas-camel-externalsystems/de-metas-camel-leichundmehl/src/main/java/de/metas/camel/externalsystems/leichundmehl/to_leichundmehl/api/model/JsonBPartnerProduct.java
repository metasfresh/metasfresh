/*
 * #%L
 * de-metas-camel-leichundmehl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = JsonBPartnerProduct.JsonBPartnerProductBuilder.class)
public class JsonBPartnerProduct
{
	@NonNull
	@JsonProperty("bPartnerId")
	String bpartnerId;

	@JsonProperty("productNo")
	String productNo;

	@JsonProperty("productName")
	String productName;

	@JsonProperty("productDescription")
	String productDescription;

	@JsonProperty("productCategory")
	String productCategory;

	@JsonProperty("ean")
	String ean;

	@JsonProperty("vendor")
	boolean vendor;

	@JsonProperty("currentVendor")
	boolean currentVendor;

	@JsonProperty("customer")
	boolean customer;

	@JsonProperty("leadTimeInDays")
	int leadTimeInDays;

	@JsonProperty("excludedFromSale")
	boolean excludedFromSale;

	@JsonProperty("exclusionFromSaleReason")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String exclusionFromSaleReason;

	@JsonProperty("excludedFromPurchase")
	boolean excludedFromPurchase;

	@JsonProperty("exclusionFromPurchaseReason")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String exclusionFromPurchaseReason;
}
