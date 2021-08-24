/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.product.v2.response.alberta;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = JsonAlbertaProductInfo.JsonAlbertaProductInfoBuilder.class)
public class JsonAlbertaProductInfo
{
	@JsonProperty("albertaProductId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String albertaProductId;

	@JsonProperty("productGroupId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable
	String productGroupId;

	@JsonProperty("additionalDescription")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String additionalDescription;

	@JsonProperty("size")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String size;

	@JsonProperty("medicalAidPositionNumber")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String medicalAidPositionNumber;

	@JsonProperty("inventoryType")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String inventoryType;

	@JsonProperty("status")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String status;

	@JsonProperty("assortmentType")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String assortmentType;

	@JsonProperty("purchaseRating")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String purchaseRating;

	@JsonProperty("stars")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	BigDecimal stars;

	@JsonProperty("pharmacyPrice")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	BigDecimal pharmacyPrice;

	@JsonProperty("fixedPrice")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	BigDecimal fixedPrice;

	@JsonProperty("therapyIds")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	List<String> therapyIds;

	@JsonProperty("billableTherapies")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	List<String> billableTherapies;

	@JsonProperty("packagingUnits")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	List<JsonAlbertaPackagingUnit> packagingUnits;
}
