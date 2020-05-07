package de.metas.materialtracking.qualityBasedInvoicing;

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


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.metas.util.Check;

/**
 * Used to sort a collection of {@link IQualityInspectionLine}s by a given order of {@link QualityInspectionLineType}s.
 *
 * @author tsa
 *
 */
public final class QualityInspectionLineByTypeComparator implements Comparator<IQualityInspectionLine>
{
	/**
	 * Maps {@link QualityInspectionLineType} to it's sorting priority.
	 */
	private final Map<QualityInspectionLineType, Integer> type2index = new HashMap<>();

	/** Index to be used when type was not found */
	private final static int INDEX_NotFound = 10000000;

	public QualityInspectionLineByTypeComparator(final QualityInspectionLineType... inspectionLineTypes)
	{
		this(Arrays.asList(inspectionLineTypes));
	}

	public QualityInspectionLineByTypeComparator(final List<QualityInspectionLineType> inspectionLineTypes)
	{
		super();

		Check.assumeNotEmpty(inspectionLineTypes, "inspectionLineTypes not empty");

		//
		// Build type to priority index map
		for (int index = 0; index < inspectionLineTypes.size(); index++)
		{
			final QualityInspectionLineType type = inspectionLineTypes.get(index);
			Check.assumeNotNull(type, "type not null");

			final Integer indexOld = type2index.put(type, index);
			if (indexOld != null)
			{
				throw new IllegalArgumentException("Duplicate type " + type + " found in " + inspectionLineTypes);
			}
		}
	}

	@Override
	public int compare(final IQualityInspectionLine line1, final IQualityInspectionLine line2)
	{
		final int index1 = getIndex(line1);
		final int index2 = getIndex(line2);

		return index1 - index2;
	}

	private final int getIndex(final IQualityInspectionLine line)
	{
		Check.assumeNotNull(line, "line not null");
		final QualityInspectionLineType type = line.getQualityInspectionLineType();
		final Integer index = type2index.get(type);
		if (index == null)
		{
			return INDEX_NotFound;
		}

		return index;
	}

	private final boolean hasType(final QualityInspectionLineType type)
	{
		return type2index.containsKey(type);
	}

	/**
	 * Remove from given lines those which their type is not specified in our list.
	 *
	 * NOTE: we assume the list is read-write.
	 *
	 * @param lines
	 */
	public void filter(final List<IQualityInspectionLine> lines)
	{
		for (final Iterator<IQualityInspectionLine> it = lines.iterator(); it.hasNext();)
		{
			final IQualityInspectionLine line = it.next();
			final QualityInspectionLineType type = line.getQualityInspectionLineType();
			if (!hasType(type))
			{
				it.remove();
			}
		}
	}

	/**
	 * Sort given lines with this comparator.
	 *
	 * NOTE: we assume the list is read-write.
	 *
	 * @param lines
	 */
	public void sort(final List<IQualityInspectionLine> lines)
	{
		Collections.sort(lines, this);
	}

	/**
	 * Remove from given lines those which their type is not specified in our list. Then sort the result.
	 *
	 * NOTE: we assume the list is read-write.
	 *
	 * @param lines
	 */
	public void filterAndSort(final List<IQualityInspectionLine> lines)
	{
		filter(lines);
		sort(lines);
	}
}
