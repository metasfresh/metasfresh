package de.metas.i18n;

import com.google.common.collect.ImmutableSet;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.compiere.util.DisplayType;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@EqualsAndHashCode
final class NumberTranslatableString implements ITranslatableString
{
	static NumberTranslatableString of(final BigDecimal valueBD, final int displayType)
	{
		return new NumberTranslatableString(valueBD, displayType);
	}

	static NumberTranslatableString of(final int valueInt)
	{
		return new NumberTranslatableString(BigDecimal.valueOf(valueInt), DisplayType.Integer);
	}

	static NumberTranslatableString of(final long valueLong)
	{
		return new NumberTranslatableString(BigDecimal.valueOf(valueLong), DisplayType.Integer);
	}

	private final BigDecimal valueBD;
	private final int displayType;

	private NumberTranslatableString(@NonNull final BigDecimal valueBD, final int displayType)
	{
		this.valueBD = valueBD;
		if (!DisplayType.isNumeric(displayType))
		{
			throw new IllegalArgumentException("displayType is not numeric: " + displayType);
		}
		this.displayType = displayType;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return valueBD.toString();
	}

	@Override
	public String translate(final String adLanguage)
	{
		final Language language = Language.getLanguage(adLanguage);
		final DecimalFormat numberFormat = DisplayType.getNumberFormat(displayType, language);
		return numberFormat.format(valueBD);
	}

	@Override
	public String getDefaultValue()
	{
		final DecimalFormat numberFormat = DisplayType.getNumberFormat(displayType);
		return numberFormat.format(valueBD);
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return ImmutableSet.of();
	}

	@Override
	public boolean isTranslatedTo(final String adLanguage)
	{
		return true;
	}

}
