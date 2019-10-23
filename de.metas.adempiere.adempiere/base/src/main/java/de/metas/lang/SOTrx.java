package de.metas.lang;

import java.util.Optional;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public enum SOTrx
{
	SALES, PURCHASE;

	public static SOTrx ofBoolean(@Nullable final Boolean isSOTrx)
	{
		if (isSOTrx == null)
		{
			return null;
		}
		return isSOTrx ? SALES : PURCHASE;
	}

	public static Optional<SOTrx> optionalOfBoolean(@Nullable final Boolean isSOTrx)
	{
		return isSOTrx != null
				? Optional.of(ofBoolean(isSOTrx))
				: Optional.empty();
	}

	public boolean toBoolean()
	{
		return isSales();
	}

	public static boolean toBoolean(final SOTrx soTrx)
	{
		if (soTrx == null)
		{
			return false;
		}
		return soTrx.toBoolean();
	}

	public boolean isSales()
	{
		return this == SALES;
	}

	public boolean isPurchase()
	{
		return this == PURCHASE;
	}

	public SOTrx invert()
	{
		return isSales() ? PURCHASE : SALES;
	}
}
