package de.metas.order.compensationGroup;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.compiere.util.Util;
import org.springframework.stereotype.Component;

import com.google.common.collect.Range;

import de.metas.order.model.I_C_CompensationGroup_SchemaLine;
import de.metas.order.model.X_C_CompensationGroup_SchemaLine;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.business
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

@Component
public final class GroupMatcherFactory_RevenueBreaks implements GroupMatcherFactory
{

	@Override
	public String getAppliesToLineType()
	{
		return X_C_CompensationGroup_SchemaLine.TYPE_Revenue;
	}

	@Override
	public Predicate<Group> createPredicate(final I_C_CompensationGroup_SchemaLine schemaLine, final List<I_C_CompensationGroup_SchemaLine> allSchemaLines)
	{
		final Optional<I_C_CompensationGroup_SchemaLine> nextSchemaLine = getNextLine(schemaLine, allSchemaLines);

		final BigDecimal valueMin = Util.coalesce(schemaLine.getBreakValue(), BigDecimal.ZERO);
		final BigDecimal valueMax = nextSchemaLine.map(I_C_CompensationGroup_SchemaLine::getBreakValue).orElse(null);
		final Range<BigDecimal> range = valueMax != null ? Range.closedOpen(valueMin, valueMax) : Range.atLeast(valueMin);

		return new RevenueRangeGroupMatcher(range);

	}

	private static Optional<I_C_CompensationGroup_SchemaLine> getNextLine(final I_C_CompensationGroup_SchemaLine schemaLine, final List<I_C_CompensationGroup_SchemaLine> allSchemaLines)
	{
		final int schemaLineId = schemaLine.getC_CompensationGroup_SchemaLine_ID();

		boolean returnNextRevenueLine = false;
		for (int i = 0, size = allSchemaLines.size(); i < size; i++)
		{
			final I_C_CompensationGroup_SchemaLine line = allSchemaLines.get(i);
			if (!X_C_CompensationGroup_SchemaLine.TYPE_Revenue.equals(line.getType()))
			{
				continue;
			}

			if (returnNextRevenueLine)
			{
				return Optional.of(line);
			}
			else if (line.getC_CompensationGroup_SchemaLine_ID() == schemaLineId)
			{
				returnNextRevenueLine = true;
			}
		}

		return Optional.empty();
	}

	@ToString
	private static final class RevenueRangeGroupMatcher implements Predicate<Group>
	{
		private final Range<BigDecimal> range;

		public RevenueRangeGroupMatcher(@NonNull final Range<BigDecimal> range)
		{
			this.range = range;
		}

		@Override
		public boolean test(final Group group)
		{
			final BigDecimal revenueAmt = group.getRegularLinesNetAmt();
			return range.contains(revenueAmt);
		}

	}
}
