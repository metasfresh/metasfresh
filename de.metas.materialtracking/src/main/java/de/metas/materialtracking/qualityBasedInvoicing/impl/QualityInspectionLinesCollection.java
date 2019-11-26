package de.metas.materialtracking.qualityBasedInvoicing.impl;

/*
 * #%L
 * de.metas.materialtracking
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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLine;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLinesCollection;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.QualityInspectionLineType;
import lombok.NonNull;

public class QualityInspectionLinesCollection implements IQualityInspectionLinesCollection
{
	private final ImmutableList<IQualityInspectionLine> lines;

	private final IQualityInspectionOrder qiOrder;

	/* package */ QualityInspectionLinesCollection(@NonNull final List<IQualityInspectionLine> lines, final IQualityInspectionOrder qiOrder)
	{
		this.lines = ImmutableList.copyOf(lines);
		this.qiOrder = qiOrder;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("QualityInspectionLinesCollection[");

		if (!lines.isEmpty())
		{
			for (final IQualityInspectionLine line : lines)
			{
				sb.append("\n\t").append(line);
			}
			sb.append("\n"); // new line after last one
		}
		else
		{
			sb.append("empty");
		}

		sb.append("]");
		return sb.toString();
	}

	@Override
	public IQualityInspectionLine getByType(final QualityInspectionLineType type)
	{
		final List<IQualityInspectionLine> linesFound = getAllByType(type);
		if (linesFound.isEmpty())
		{
			throw new AdempiereException("No line found for type: " + type);
		}
		else if (linesFound.size() > 1)
		{
			throw new AdempiereException("More then one line found for type " + type + ": " + linesFound);
		}

		return linesFound.get(0);
	}

	@Override
	public List<IQualityInspectionLine> getAllByType(final QualityInspectionLineType... types)
	{
		Check.assumeNotEmpty(types, "types not empty");
		final List<QualityInspectionLineType> typesList = Arrays.asList(types);

		final List<IQualityInspectionLine> linesFound = new ArrayList<>();
		for (final IQualityInspectionLine line : lines)
		{
			final QualityInspectionLineType lineType = line.getQualityInspectionLineType();
			if (typesList.contains(lineType))
			{
				linesFound.add(line);
			}
		}

		return linesFound;
	}

	@Override
	public IQualityInspectionOrder getQualityInspectionOrder()
	{
		return this.qiOrder;
	}
}
