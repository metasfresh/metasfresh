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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.PRODUCT_IDENTIFIER_DOC;

@Value
@ApiModel
public class JsonCreateBOMLine
{
	@ApiModelProperty(position = 10, value = PRODUCT_IDENTIFIER_DOC, required = true)
	String productIdentifier;

	@ApiModelProperty(position = 20, value = "Corresponding to 'PP_Product_BOMLine.QtyBom' and 'PP_Product_BOMLine.C_UOM_ID' ", required = true)
	JsonQuantity qtyBom;

	@ApiModelProperty(position = 30, value = "Corresponding to 'PP_Product_BOMLine.Line")
	Integer line;

	@ApiModelProperty(position = 40, value = "Corresponding to 'PP_Product_BOMLine.IsQtyPercentage")
	Boolean isQtyPercentage;

	@ApiModelProperty(position = 50, value = "Corresponding to 'PP_Product_BOMLine.Scrap")
	BigDecimal scrap;

	@ApiModelProperty(position = 60, value = "Corresponding to 'PP_Product_BOMLine.IssueMethod")
	String issueMethod;

	@ApiModelProperty(position = 70, value = "Corresponding to 'PP_Product_BOMLine.Help")
	String help;

	@ApiModelProperty(position = 80, value = "Corresponding to `M_AttributeSetInstance`")
	JsonAttributeSetInstance attributeSetInstance;

	@Builder
	@JsonCreator
	public JsonCreateBOMLine(
			@JsonProperty("productIdentifier") @NonNull final String productIdentifier,
			@JsonProperty("qtyBom") @NonNull final JsonQuantity qtyBom,
			@JsonProperty("line") @Nullable final Integer line,
			@JsonProperty("isQtyPercentage") @Nullable final Boolean isQtyPercentage,
			@JsonProperty("scrap") final BigDecimal scrap,
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