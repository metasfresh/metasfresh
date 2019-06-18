package de.metas.dataentry.data.impexp;

import javax.annotation.Nullable;

import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubTab;
import de.metas.dataentry.layout.DataEntryTab;
import de.metas.i18n.ITranslatableString;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@ToString
class NamePattern
{
	public static NamePattern ofStringOrAny(@Nullable final String pattern)
	{
		final String patternNorm = pattern != null ? pattern.trim() : null;
		if (patternNorm == null || patternNorm.isEmpty())
		{
			return ANY;
		}
		else
		{
			return new NamePattern(pattern);
		}
	}

	private static final NamePattern ANY = new NamePattern();

	@Getter
	private final boolean any;
	private final String pattern;

	private NamePattern(@NonNull final String pattern)
	{
		this.any = false;
		this.pattern = pattern;
	}

	private NamePattern()
	{
		this.any = true;
		this.pattern = null;
	}

	public boolean isMatching(@NonNull final DataEntryTab tab)
	{
		return isMatching(tab.getInternalName())
				|| isMatching(tab.getCaption());
	}

	public boolean isMatching(@NonNull final DataEntrySubTab subTab)
	{
		return isMatching(subTab.getInternalName())
				|| isMatching(subTab.getCaption());
	}

	public boolean isMatching(@NonNull final DataEntrySection section)
	{
		return isMatching(section.getInternalName())
				|| isMatching(section.getCaption());
	}

	public boolean isMatching(@NonNull final DataEntryLine line)
	{
		return isMatching(String.valueOf(line.getSeqNo()));
	}

	public boolean isMatching(@NonNull final DataEntryField field)
	{
		return isAny()
				|| isMatching(field.getCaption());
	}

	private boolean isMatching(final ITranslatableString trl)
	{
		if (isAny())
		{
			return true;
		}

		if (isMatching(trl.getDefaultValue()))
		{
			return true;
		}

		for (final String adLanguage : trl.getAD_Languages())
		{
			if (isMatching(trl.translate(adLanguage)))
			{
				return true;
			}
		}

		return false;
	}

	private boolean isMatching(final String name)
	{
		if (isAny())
		{
			return true;
		}
		else
		{
			return pattern.equalsIgnoreCase(name);
		}
	}
}
