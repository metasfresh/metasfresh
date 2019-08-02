package de.metas.quantity;

import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class Quantitys
{
	public static Quantity add(
			@NonNull final UOMConversionContext conversionCtx,
			@NonNull final Quantity firstAugent,
			@NonNull final Quantity secondAugent)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final Quantity secondAugentConverted = uomConversionBL.convertQuantityTo(secondAugent, conversionCtx, firstAugent.getUomId());

		return firstAugent.add(secondAugentConverted);
	}

	public static Quantity subtract(
			@NonNull final UOMConversionContext conversionCtx,
			@NonNull final Quantity minuend,
			@NonNull final Quantity subtrahend)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final Quantity subtrahendConverted = uomConversionBL.convertQuantityTo(subtrahend, conversionCtx, minuend.getUomId());

		return minuend.subtract(subtrahendConverted);
	}
}
