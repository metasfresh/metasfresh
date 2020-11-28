package de.metas.dataentry.data.impexp;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;

import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubTab;
import de.metas.dataentry.layout.DataEntryTab;
import de.metas.i18n.ITranslatableString;
import lombok.Getter;
import lombok.NonNull;

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

class NamePattern
{
	public static NamePattern ofStringOrAny(@Nullable final String pattern)
	{
		final String patternNorm = normalizeString(pattern);
		if (patternNorm == null)
		{
			return ANY;
		}
		else
		{
			return new NamePattern(patternNorm);
		}
	}

	public static NamePattern any()
	{
		return ANY;
	}

	private static final NamePattern ANY = new NamePattern();

	@Getter
	private final boolean any;
	private final String pattern;

	private NamePattern(@NonNull final String patternNormalized)
	{
		this.any = false;
		this.pattern = patternNormalized;
	}

	private static final String normalizeString(final String str)
	{
		if (str == null)
		{
			return null;
		}

		final String strNorm = str.trim();
		if (strNorm.isEmpty())
		{
			return null;
		}

		return strNorm;
	}

	private NamePattern()
	{
		this.any = true;
		this.pattern = null;
	}

	@Override
	public String toString()
	{
		if (any)
		{
			return MoreObjects.toStringHelper(this).addValue("ANY").toString();
		}
		else
		{
			return MoreObjects.toStringHelper(this).add("pattern", pattern).toString();
		}
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

	@VisibleForTesting
	boolean isMatching(final String name)
	{
		if (isAny())
		{
			return true;
		}

		final String nameNorm = normalizeString(name);
		if (nameNorm == null)
		{
			return false;
		}

		return pattern.equalsIgnoreCase(nameNorm);
	}
}
