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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Builder(toBuilder = true)
@Value
@JsonDeserialize(builder = JsonProductInfo.JsonProductInfoBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonProductInfo
{
	@NonNull
	@JsonProperty("id")
	Integer id;

	@NonNull
	@JsonProperty("productCategoryId")
	Integer productCategoryId;

	@NonNull
	@JsonProperty("productNo")
	String productNo;

	@NonNull
	@JsonProperty("name")
	String name;

	@Nullable
	@JsonProperty("description")
	String description;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("ean")
	String ean;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("externalId")
	String externalId;

	@Nullable
	@JsonProperty("uomCode")
	String uomCode;

	@Nullable
	@JsonProperty("manufacturerId")
	Integer manufacturerId;

	@Nullable
	@JsonProperty("manufacturerName")
	String manufacturerName;

	@Nullable
	@JsonProperty("manufacturerNumber")
	String manufacturerNumber;

	@Nullable
	@JsonProperty("packageSize")
	String packageSize;

	@Nullable
	@JsonProperty("weight")
	BigDecimal weight;

	@Nullable
	@JsonProperty("documentNote")
	String documentNote;

	@Nullable
	@JsonProperty("commodityNumberValue")
	String commodityNumberValue;

	@JsonProperty("stocked")
	boolean stocked;

	@Nullable
	@JsonProperty("bpartnerProduct")
	JsonBPartnerProduct bPartnerProduct;

	@Nullable
	@With
	@JsonProperty("priceList")
	List<JsonPriceList> priceList;
}
