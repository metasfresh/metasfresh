/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.pricing.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_ProductPrice;

import javax.annotation.Nullable;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum ScalePriceQtyFrom implements ReferenceListAwareEnum
{
	Quantity(X_M_ProductPrice.SCALEPRICEQUANTITYFROM_Quantity),
	UserElementNumber1(X_M_ProductPrice.SCALEPRICEQUANTITYFROM_UserElementNumber1),
	UserElementNumber2(X_M_ProductPrice.SCALEPRICEQUANTITYFROM_UserElementNumber2);

	private static final ReferenceListAwareEnums.ValuesIndex<ScalePriceQtyFrom> index = ReferenceListAwareEnums.index(values());

	private final String code;

	@JsonCreator
	@NonNull
	public static ScalePriceQtyFrom ofCode(@NonNull final String code) {return index.ofCode(code);}

	public static Optional<ScalePriceQtyFrom> optionalOfNullableCode(@Nullable final String code) {return index.optionalOfNullableCode(code);}

	public boolean isQuantity()
	{
		return this == Quantity;
	}
}

