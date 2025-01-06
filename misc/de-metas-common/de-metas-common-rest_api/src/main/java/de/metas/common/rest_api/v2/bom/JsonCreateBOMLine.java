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
import de.metas.common.rest_api.v2.JsonAttributeSetInstance;
import de.metas.common.rest_api.v2.JsonQuantity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.PRODUCT_IDENTIFIER_DOC;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Value
@Schema
public class JsonCreateBOMLine
{
	@Schema(description = PRODUCT_IDENTIFIER_DOC, requiredMode= REQUIRED)
	String productIdentifier;

	@Schema(description = "Corresponding to 'PP_Product_BOMLine.QtyBom' and 'PP_Product_BOMLine.C_UOM_ID' ", requiredMode= REQUIRED)
	JsonQuantity qtyBom;

	@Schema(description = "Corresponding to 'PP_Product_BOMLine.Line")
	Integer line;

	@Schema(description = "Corresponding to 'PP_Product_BOMLine.IsQtyPercentage")
	Boolean isQtyPercentage;

	@Schema(description = "Corresponding to 'PP_Product_BOMLine.Scrap")
	BigDecimal scrap;

	@Schema(description = "Corresponding to 'PP_Product_BOMLine.IssueMethod")
	String issueMethod;

	@Schema(description = "Corresponding to 'PP_Product_BOMLine.Help")
	String help;

	@Schema(description = "Corresponding to `M_AttributeSetInstance`")
	JsonAttributeSetInstance attributeSetInstance;

	@Builder
	@JsonCreator
	public JsonCreateBOMLine(
			@JsonProperty("productIdentifier") @NonNull final String productIdentifier,
			@JsonProperty("qtyBom") @NonNull final JsonQuantity qtyBom,
			@JsonProperty("line") @Nullable final Integer line,
			@JsonProperty("isQtyPercentage") @Nullable final Boolean isQtyPercentage,
			@JsonProperty("scrap") @Nullable final BigDecimal scrap,
			@JsonProperty("issueMethod") @Nullable final String issueMethod,
			@JsonProperty("help") @Nullable final String help,
			@JsonProperty("attributeSetInstance") @Nullable final JsonAttributeSetInstance attributeSetInstance)
	{

		this.productIdentifier = productIdentifier;
		this.qtyBom = qtyBom;
		this.line = line;
		this.isQtyPercentage = isQtyPercentage;
		this.scrap = scrap;
		this.issueMethod = issueMethod;
		this.help = help;
		this.attributeSetInstance = attributeSetInstance;
	}
}