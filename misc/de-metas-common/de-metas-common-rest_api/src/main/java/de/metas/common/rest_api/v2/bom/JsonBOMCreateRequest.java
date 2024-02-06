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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.PRODUCT_IDENTIFIER_DOC;
import static de.metas.common.util.CoalesceUtil.coalesce;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Value
@Schema(description = "Contains a product external identifier and the actual bom formula to insert.")
public class JsonBOMCreateRequest
{
	@Schema(description = PRODUCT_IDENTIFIER_DOC, requiredMode= REQUIRED)
	String productIdentifier;

	@Schema(description = "Corresponding to `PP_Product_BOM.C_UOM_ID`", requiredMode= REQUIRED)
	String uomCode;

	@Schema(description = "Corresponding to `PP_Product_BOM.Name`", requiredMode= REQUIRED)
	String name;

	@Schema(description = "Corresponding to `PP_Product_BOM.isActive`", requiredMode= REQUIRED)
	Boolean isActive;

	@Schema(description = "Corresponding to `PP_Product_BOM.validFrom", requiredMode= REQUIRED)
	Instant validFrom;

	@Schema(description = "Corresponding to `M_AttributeSetInstance`")
	JsonAttributeSetInstance attributeSetInstance;

	@Schema(description = "Corresponding to `PP_Product_BOM.S_PreferredResource_ID`")
	String resourceCode;

	@Schema(requiredMode= REQUIRED)
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
			@JsonProperty("resourceCode") final String resourceCode,
			@JsonProperty("bomLines") @Singular final List<JsonCreateBOMLine> bomLines)
	{

		this.uomCode = uomCode;
		this.productIdentifier = productIdentifier;
		this.name = name;
		this.isActive = isActive;
		this.validFrom = validFrom;
		this.attributeSetInstance = attributeSetInstance;
		this.resourceCode = resourceCode;
		this.bomLines = coalesce(bomLines, ImmutableList.of());
	}
}
