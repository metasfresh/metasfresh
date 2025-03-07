package de.metas.lang;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;

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

	private static final String ISSOTRX_Yes = "Y";

	private static final String ISSOTRX_No = "N";

	/**
	 * For backward compatibility we are accepting null parameter, so we are calling {@link #ofNullableBoolean(Boolean)}.
	 * <p>
	 * But in the future, instead of calling this method,
	 * pls call {@link #ofBooleanNotNull(Boolean)} if you know that the parameter is not null,
	 * or pls call {@link #ofNullableBoolean(Boolean)} if you know the parameter might be null (so the return value).
	 */
	@Nullable
	public static SOTrx ofBoolean(@Nullable final Boolean isSOTrx)
	{
		return ofNullableBoolean(isSOTrx);
	}

	@Nullable
	public static SOTrx ofNullableBoolean(@Nullable final Boolean isSOTrx)
	{
		return isSOTrx != null ? ofBooleanNotNull(isSOTrx) : null;
	}

	@NonNull
	public static SOTrx ofBooleanNotNull(@NonNull final Boolean isSOTrx)
	{
		return isSOTrx ? SALES : PURCHASE;
	}

	public static Optional<SOTrx> optionalOfBoolean(@Nullable final Boolean isSOTrx)
	{
		return isSOTrx != null
				? Optional.of(ofBooleanNotNull(isSOTrx))
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

	@NonNull
	public static SOTrx ofNameNotNull(@NonNull final String soTrx)
	{
		try
		{
			return SOTrx.valueOf(soTrx);
		}
		catch (final Exception exception)
		{
			throw new AdempiereException("Invalid SOTrx!")
					.appendParametersToMessage()
					.setParameter("SOTrx", soTrx)
					.setParameter("Known values", values());
		}
	}

	@NonNull
	public static SOTrx ofYesNoString(@NonNull final String soTrx)
	{
		if(soTrx.equals(ISSOTRX_Yes))
		{
			return SALES;
		}
		else if(soTrx.equals(ISSOTRX_No))
		{
			return PURCHASE;
		}
		else
		{
			throw new AdempiereException("Invalid SOTrx!")
					.appendParametersToMessage()
					.setParameter("SOTrx", soTrx)
					.setParameter("Known values", ISSOTRX_Yes + "/" + ISSOTRX_No);
		}
	}

	@NonNull
	public String toYesNoString()
	{
		return isSales() ? ISSOTRX_Yes : ISSOTRX_No;
	}
}
