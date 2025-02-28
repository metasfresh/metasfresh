package de.metas.i18n;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.util
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
final class CompositeTranslatableString implements ITranslatableString
{
	private final ImmutableList<ITranslatableString> list;
	private final String joinString;

	private transient String defaultValue; // lazy
	private transient ImmutableSet<String> adLanguages; // lazy

	CompositeTranslatableString(final List<ITranslatableString> list, final String joinString)
	{
		this.list = ImmutableList.copyOf(list);
		this.joinString = joinString != null ? joinString : "";
	}

	@Override
	@Deprecated
	public String toString()
	{
		return list.stream().map(ITranslatableString::toString).collect(Collectors.joining(joinString));
	}

	@Override
	public String translate(final String adLanguage)
	{
		return list.stream().map(trl -> trl.translate(adLanguage)).collect(Collectors.joining(joinString));
	}

	@Override
	public String getDefaultValue()
	{
		String defaultValue = this.defaultValue;
		if (defaultValue == null)
		{
			this.defaultValue = defaultValue = list.stream().map(trl -> trl.getDefaultValue()).collect(Collectors.joining(joinString));

		}
		return defaultValue;
	}

	@Override
	public Set<String> getAD_Languages()
	{
		ImmutableSet<String> adLanguages = this.adLanguages;
		if (adLanguages == null)
		{
			this.adLanguages = adLanguages = list.stream().flatMap(trl -> trl.getAD_Languages().stream()).collect(ImmutableSet.toImmutableSet());
		}
		return adLanguages;
	}

	@Override
	public boolean isTranslatedTo(final String adLanguage)
	{
		return list.stream().anyMatch(trl -> trl.isTranslatedTo(adLanguage));
	}

	@Override
	@Nullable
	public String getErrorCode()
	{
		final ImmutableSet<String> collect = list.stream().map(ITranslatableString::getErrorCode)
				.filter(Check::isNotBlank)
				.collect(ImmutableSet.toImmutableSet());
		return collect.size() != 1 ? null : collect.iterator().next();
	}
}
