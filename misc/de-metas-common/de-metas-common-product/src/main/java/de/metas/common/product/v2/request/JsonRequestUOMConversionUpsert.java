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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode
public class JsonRequestUOMConversionUpsert
{
	@Schema(description = "Corresponding to C_UOM_Conversion.C_UOM_ID")
	private String fromUomCode;

	@Schema(description = "Corresponding to C_UOM_Conversion.C_UOM_To_ID")
	private String toUomCode;

	@Schema(description = "Corresponding to C_UOM_Conversion.MultiplyRate")
	private BigDecimal fromToMultiplier;

	@Schema(hidden = true)
	private boolean fromToMultiplierSet;

	@Schema(description = "Corresponding to C_UOM_Conversion.IsCatchUOMForProduct")
	private Boolean catchUOMForProduct;

	@Schema(hidden = true)
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
