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

import de.metas.uom.X12DE355;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@AllArgsConstructor
@Builder
public class JsonDhlDimension
{
	@NonNull String uom;
	@NonNull BigDecimal height;
	@NonNull BigDecimal length;
	@NonNull BigDecimal width;

	@Builder(builderMethodName = "_inCM", buildMethodName = "weightInCM")
	public JsonDhlDimension(@NonNull final BigDecimal heightInCM, @NonNull final BigDecimal lengthInCM, @NonNull final BigDecimal widthInCM)
	{
		this(X12DE355.CENTIMETRE.getCode().toLowerCase(), heightInCM, lengthInCM, widthInCM);
	}
}
