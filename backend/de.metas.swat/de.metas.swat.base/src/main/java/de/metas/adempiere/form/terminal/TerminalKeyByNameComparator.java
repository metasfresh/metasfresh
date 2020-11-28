package de.metas.adempiere.form.terminal;

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


import java.util.Comparator;

/**
 * Compares {@link ITerminalKey}s by their name.
 * 
 * @author tsa
 *
 */
public final class TerminalKeyByNameComparator implements Comparator<ITerminalKey>
{
	public static final TerminalKeyByNameComparator instance = new TerminalKeyByNameComparator();

	private TerminalKeyByNameComparator()
	{
		super();
	}

	@Override
	public int compare(final ITerminalKey o1, final ITerminalKey o2)
	{
		if (o1 == o2)
		{
			return 0;
		}

		final Object nameObj1 = o1 == null ? "" : o1.getName();
		final Object nameObj2 = o2 == null ? "" : o2.getName();

		final String name1 = nameObj1 == null ? "" : nameObj1.toString();
		final String name2 = nameObj2 == null ? "" : nameObj2.toString();

		return name1.compareTo(name2);
	}

}
