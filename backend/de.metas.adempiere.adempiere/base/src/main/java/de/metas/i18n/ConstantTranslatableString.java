package de.metas.i18n;

import com.google.common.collect.ImmutableSet;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
/* package */ final class ConstantTranslatableString implements ITranslatableString
{
	@NonNull
	static ConstantTranslatableString of(@Nullable final String value)
	{
		final boolean anyLanguage = false;
		return of(value, anyLanguage);
	}

	@NonNull
	static ITranslatableString anyLanguage(@Nullable final String value)
	{
		final boolean anyLanguage = true;
		return of(value, anyLanguage);
	}

	@NonNull
	static ConstantTranslatableString of(@Nullable final String value, final boolean anyLanguage)
	{
		if (value == null || value.isEmpty())
		{
			return EMPTY;
		}
		else if (" ".equals(value))
		{
			return SPACE;
		}
		else
		{
			return new ConstantTranslatableString(value, anyLanguage);
		}
	}

	static final ConstantTranslatableString EMPTY = new ConstantTranslatableString("", true);
	private static final ConstantTranslatableString SPACE = new ConstantTranslatableString(" ", true);

	private final String value;
	private final boolean anyLanguage;

	private ConstantTranslatableString(@NonNull final String value, final boolean anyLanguage)
	{
		this.value = value;
		this.anyLanguage = anyLanguage;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return value;
	}

	@Override
	public String translate(final String adLanguage)
	{
		return value;
	}

	@Override
	public String getDefaultValue()
	{
		return value;
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return ImmutableSet.of();
	}

	@Override
	public boolean isTranslatedTo(final String adLanguage)
	{
		return anyLanguage;
	}

}
