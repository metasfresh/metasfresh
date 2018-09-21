package de.metas.inoutcandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import de.metas.util.Check;

/**
 * Quality Notices Collection.
 *
 * This class is able to collect quality notices uniquely, transform them to string ({@link #asQualityNoticesString()}) and retrieve them from string ({@link #parseAndAddQualityNoticesString(String)}
 * ).
 *
 * Collected quality notices will always be in their natural order (i.e. alphabetically).
 *
 * @author tsa
 *
 */
public final class QualityNoticesCollection
{
	public static final String SEPARATOR_String = ". ";
	private static final String SEPARATOR_String_Trimmed = SEPARATOR_String.trim();
	private static final Pattern SEPARATOR_Pattern = Pattern.compile("\\. ");

	public static QualityNoticesCollection valueOfQualityNote(final String qualityNote)
	{
		final QualityNoticesCollection notices = new QualityNoticesCollection();
		notices.addQualityNotice(qualityNote);
		return notices;
	}

	public static QualityNoticesCollection valueOfQualityNoticesString(final String qualityNoticesString)
	{
		final QualityNoticesCollection notices = new QualityNoticesCollection();
		notices.parseAndAddQualityNoticesString(qualityNoticesString);
		return notices;
	}

	private final Set<String> qualityNoticesSet = new TreeSet<String>();

	public QualityNoticesCollection()
	{
		super();
	}

	@Override
	public String toString()
	{
		return asQualityNoticesString();
	}

	public QualityNoticesCollection copy()
	{
		final QualityNoticesCollection copy = new QualityNoticesCollection();
		copy.addQualityNotices(this);
		return copy;
	}

	@Override
	public QualityNoticesCollection clone()
	{
		return copy();
	}

	/**
	 * Add quality notice to this collection.
	 *
	 * @param qualityNote
	 * @return true if added
	 */
	public boolean addQualityNotice(final String qualityNote)
	{
		if (Check.isEmpty(qualityNote, true))
		{
			return false;
		}

		return qualityNoticesSet.add(qualityNote.trim());
	}

	public void addQualityNotices(final QualityNoticesCollection notices)
	{
		addQualityNotices(notices.qualityNoticesSet);
	}

	public void addQualityNotices(final Set<String> notices)
	{
		Check.assumeNotNull(notices, "notices not null");
		qualityNoticesSet.addAll(notices);
	}

	/**
	 * Parses quality notices string (which we assume it was built with {@link #asQualityNoticesString()}) and then adds them to current collection.
	 * 
	 * @param qualityNoticesStr
	 */
	public void parseAndAddQualityNoticesString(final String qualityNoticesStr)
	{
		if (Check.isEmpty(qualityNoticesStr, true))
		{
			return;
		}

		String qualityNoticesStrPrepared = qualityNoticesStr.trim();

		// If our string ends with our separator (trimmed), we need to take it out because splitter will not detect it
		if (qualityNoticesStrPrepared.endsWith(SEPARATOR_String_Trimmed))
		{
			qualityNoticesStrPrepared = qualityNoticesStrPrepared.substring(0, qualityNoticesStrPrepared.length() - SEPARATOR_String_Trimmed.length());
		}

		final String[] qualityNoticesArray = SEPARATOR_Pattern.split(qualityNoticesStrPrepared.trim());
		if (qualityNoticesArray == null || qualityNoticesArray.length == 0)
		{
			return;
		}

		for (final String qualityNote : qualityNoticesArray)
		{
			addQualityNotice(qualityNote);
		}
	}

	/**
	 *
	 * @return string representation of quality notices from this collection
	 */
	public String asQualityNoticesString()
	{
		if (qualityNoticesSet == null || qualityNoticesSet.isEmpty())
		{
			return "";
		}

		final StringBuilder qualityNoticesStr = new StringBuilder();
		for (final String qualityNotice : qualityNoticesSet)
		{
			// skip empty notes
			if (Check.isEmpty(qualityNotice, true))
			{
				continue;
			}

			if (qualityNoticesStr.length() > 0)
			{
				qualityNoticesStr.append(SEPARATOR_String);
			}

			qualityNoticesStr.append(qualityNotice.trim());
		}

		return qualityNoticesStr.toString();
	}

	public Set<String> getQualityNoticesSet()
	{
		// always return a copy!!!
		return new TreeSet<>(this.qualityNoticesSet);
	}
}
