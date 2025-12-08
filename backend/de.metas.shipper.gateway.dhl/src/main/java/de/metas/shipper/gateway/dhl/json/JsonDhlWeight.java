/*
 * #%L
 * de.metas.shipper.gateway.dhl
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.shipper.gateway.dhl.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.quantity.Quantity;
import de.metas.shipper.gateway.dhl.DhlConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
@Builder
public class JsonDhlWeight
{
	@NonNull String uom;
	@NonNull BigDecimal value;

	@JsonIgnore
	public static final MathContext MATH_CONTEXT = new MathContext(3, RoundingMode.UP);

	@Builder
	public JsonDhlWeight(@NonNull final Quantity quantity)
	{
		this(quantity.getUOMSymbol(), quantity.roundToUOMPrecision().toBigDecimal());
	}

	@Builder(builderMethodName = "_inKg",buildMethodName = "weightInKg")
	public JsonDhlWeight(@NonNull final BigDecimal qtyInKg)
	{
		this(DhlConstants.KILOGRAM_UOM, qtyInKg.round(MATH_CONTEXT));
	}

}
