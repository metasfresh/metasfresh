/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v2.bom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v2.JsonAttributeSetInstance;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.PRODUCT_IDENTIFIER_DOC;
import static de.metas.common.util.CoalesceUtil.coalesce;

@Value
@ApiModel(description = "Contains a product external identifier and the actual bom formula to insert.")
public class JsonBOMCreateRequest
{
	@ApiModelProperty(position = 10, value = PRODUCT_IDENTIFIER_DOC, required = true)
	String productIdentifier;

	@ApiModelProperty(position = 20, value = "Corresponding to `PP_Product_BOM.C_UOM_ID`", required = true)
	String uomCode;

	@ApiModelProperty(position = 30, value = "Corresponding to `PP_Product_BOM.Name`", required = true)
	String name;

	@ApiModelProperty(position = 40, value = "Corresponding to `PP_Product_BOM.isActive`")
	Boolean isActive;

	@ApiModelProperty(position = 50, value = "Corresponding to `PP_Product_BOM.validFrom")
	Instant validFrom;

	@ApiModelProperty(position = 60, value = "Corresponding to `M_AttributeSetInstance`")
	JsonAttributeSetInstance attributeSetInstance;

	@ApiModelProperty(position = 70, required = true)
	List<JsonCreateBOMLine> bomLines;

	@Builder
	@JsonCreator
	public JsonBOMCreateRequest(
			@JsonProperty("uomCode") @NonNull final String uomCode,
			@JsonProperty("productIdentifier") @NonNull final String productIdentifier,
			@JsonProperty("name") @NonNull final String name,
			@JsonProperty("isActive") @NonNull final Boolean isActive,
			@JsonProperty("validFrom") @NonNull final Instant validFrom,
			@JsonProperty("attributeSetInstance") @Nullable final JsonAttributeSetInstance attributeSetInstance,
			@JsonProperty("bomLines") @Singular final List<JsonCreateBOMLine> bomLines)
	{

		this.uomCode = uomCode;
		this.productIdentifier = productIdentifier;
		this.name = name;
		this.isActive = isActive;
		this.validFrom = validFrom;
		this.attributeSetInstance = attributeSetInstance;
		this.bomLines = coalesce(bomLines, ImmutableList.of());
	}
}
