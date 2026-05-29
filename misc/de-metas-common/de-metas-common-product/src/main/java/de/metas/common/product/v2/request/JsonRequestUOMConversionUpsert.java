/*
 * #%L
 * de-metas-common-product
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.common.product.v2.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(description = "Contains the actual product uom conversion to insert or update.")
public class JsonRequestUOMConversionUpsert
{
	@ApiModelProperty(position = 10, required = true, value = "Corresponding to C_UOM_Conversion.C_UOM_ID")
	private String fromUomCode;

	@ApiModelProperty(position = 20, required = true, value = "Corresponding to C_UOM_Conversion.C_UOM_To_ID")
	private String toUomCode;

	@ApiModelProperty(position = 30, value = "Corresponding to C_UOM_Conversion.MultiplyRate")
	private BigDecimal fromToMultiplier;

	@ApiModelProperty(hidden = true)
	private boolean fromToMultiplierSet;

	@ApiModelProperty(position = 40, value = "Corresponding to C_UOM_Conversion.IsCatchUOMForProduct")
	private Boolean catchUOMForProduct;

	@ApiModelProperty(hidden = true)
	private boolean catchUOMForProductSet;

	public void setFromUomCode(final String fromUomCode)
	{
		this.fromUomCode = fromUomCode;
	}

	public void setToUomCode(final String toUomCode)
	{
		this.toUomCode = toUomCode;
	}

	public void setFromToMultiplier(final BigDecimal fromToMultiplier)
	{
		this.fromToMultiplier = fromToMultiplier;
		this.fromToMultiplierSet = true;
	}

	public void setCatchUOMForProduct(final Boolean catchUOMForProduct)
	{
		this.catchUOMForProduct = catchUOMForProduct;
		this.catchUOMForProductSet = true;
	}
}
