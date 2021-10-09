package de.metas.common.product.v2.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.product.v2.response.alberta.JsonAlbertaProductInfo;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

/*
 * #%L
 * de.metas.business.rest-api-impl
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
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonDeserialize(builder = JsonProduct.JsonProductBuilder.class)
public class JsonProduct
{
	@ApiModelProperty( //
			allowEmptyValue = false, //
			dataType = "java.lang.Integer", //
			value = "This translates to `M_Product.M_Product_ID`.")
	@NonNull
	@JsonProperty("id")
	JsonMetasfreshId id;

	@ApiModelProperty("This translates to `M_Product.M_Product_Category_ID`.")
	@NonNull
	@JsonProperty("productCategoryId")
	JsonMetasfreshId productCategoryId;

	@ApiModelProperty("This translates to `M_Product.Value`.")
	@NonNull
	@JsonProperty("productNo")
	String productNo;

	@NonNull
	@JsonProperty("name")
	String name;

	@Nullable
	@JsonProperty("description")
	String description;

	@ApiModelProperty(value = "This translates to `M_Product.UPC`.<br>Note that different bPartners may assign different EANs to the same product")
	@Nullable
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("ean")
	String ean;

	@ApiModelProperty("This translates to `M_Product.ExternalId`.")
	@Nullable
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("externalId")
	String externalId;

	@ApiModelProperty("This is the `C_UOM.UOMSymbol` of the product's unit of measurement.")
	@NonNull
	@JsonProperty("uom")
	String uom;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			dataType = "java.lang.Integer", //
			value = "This translates to `M_Product.Manufacturer_ID`.")
	@Nullable
	@JsonProperty("manufacturerId")
	JsonMetasfreshId manufacturerId;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			dataType = "java.lang.String", //
			value = "This translates to `C_BPartner.Name` of the product's manufacturer.")
	@Nullable
	@JsonProperty("manufacturerName")
	String manufacturerName;
	
	@ApiModelProperty( //
			allowEmptyValue = true, //
			dataType = "java.lang.String", //
			value = "This translates to `M_Product.ManufacturerArticleNumber`.")
	@Nullable
	@JsonProperty("manufacturerNumber")
	String manufacturerNumber;
	
	@NonNull
	@Singular
	@JsonProperty("bpartners")
	List<JsonProductBPartner> bpartners;

	@Nullable
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("albertaProductInfo")
	JsonAlbertaProductInfo albertaProductInfo;
}
