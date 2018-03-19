package org.compiere.acct;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.compiere.acct.FactTrxLines.FactTrxLinesBuilder;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.acct.base
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

final class PerDocumentLineFactTrxStrategy implements FactTrxStrategy
{
	public static final transient PerDocumentLineFactTrxStrategy instance = new PerDocumentLineFactTrxStrategy();

	@Override
	public List<FactTrxLines> createFactTrxLines(final List<FactLine> factLines)
	{
		if (factLines.isEmpty())
		{
			return ImmutableList.of();
		}

		final Map<Integer, FactTrxLinesBuilder> factTrxLinesByKey = new LinkedHashMap<>();
		for (final FactLine factLine : factLines)
		{
			factTrxLinesByKey.computeIfAbsent(extractGrouppingKey(factLine), key -> FactTrxLines.builder())
					.factLine(factLine);
		}

		return factTrxLinesByKey.values()
				.stream()
				.map(FactTrxLinesBuilder::build)
				.collect(ImmutableList.toImmutableList());
	}

	private static int extractGrouppingKey(final FactLine factLine)
	{
		return factLine.getLine_ID();

	}

}
