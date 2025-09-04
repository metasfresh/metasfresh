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
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = JsonProductUOMConversion.JsonProductUOMConversionBuilder.class)
public class JsonProductUOMConversion
{

	@JsonProperty("productId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	JsonMetasfreshId productId;

	@ApiModelProperty("This is the `C_UOM.X12DE355` of the C_UOM_Conversion.C_UOM_ID.")
	@NonNull
	@JsonProperty("uomX12DE355From")
	String uomX12DE355From;

	@ApiModelProperty("This is the `C_UOM.X12DE355` of the C_UOM_Conversion.C_UOM_To_ID.")
	@NonNull
	@JsonProperty("uomX12DE355To")
	String uomX12DE355To;

	@ApiModelProperty("Corresponding to C_UOM_Conversion.MultiplyRate")
	@NonNull
	@JsonProperty("fromToMultiplier")
	BigDecimal fromToMultiplier;

	@ApiModelProperty("Corresponding to C_UOM_Conversion.IsCatchUOMForProduct")
	@NonNull
	@JsonProperty("catchUOMForProduct")
	Boolean catchUOMForProduct;

}
