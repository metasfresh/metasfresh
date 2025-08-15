/*
 * #%L
 * de-metas-common-product
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

package de.metas.common.product.v2.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = JsonMHUPIItemProduct.JsonMHUPIItemProductBuilder.class)
public class JsonMHUPIItemProduct
{
	@Schema(
			type = "integer",
			format = "int32",
			description = "Corresponding to `M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID`."
	)
	@NonNull
	@JsonProperty("mHUPIItemProductId")
	JsonMetasfreshId mHUPIItemProductId;

	@Schema(
			type = "integer",
			format = "int32",
			description = "Corresponding to `M_HU_PI_Item_Product.C_BPartnerId`.",
			nullable = true
	)
	@Nullable
	@JsonProperty("bpartnerId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	JsonMetasfreshId bpartnerId;

	@Schema(
			type = "string",
			description = "Corresponding to `M_HU_PI_Item_Product.Name`.",
			nullable = true
	)
	@Nullable
	@JsonProperty("name")
	String name;

	@Schema(
			description = "Corresponding to M_HU_PI_Item_Product.Qty",
			required = true
	)
	@NonNull
	@JsonProperty("qty")
	BigDecimal qty;

	@Schema(
			description = "This is the `C_UOM.UOMSymbol` of the M_HU_PI_Item_Product.C_UOM_ID.",
			nullable = true
	)
	@Nullable
	@JsonProperty("uom")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String uom;
}
