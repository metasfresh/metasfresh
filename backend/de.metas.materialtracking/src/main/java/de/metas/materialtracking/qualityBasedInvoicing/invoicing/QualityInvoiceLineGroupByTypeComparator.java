package de.metas.materialtracking.qualityBasedInvoicing.invoicing;

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
 * Used to sort a collection of {@link IQualityInvoiceLineGroup}s by a given order of {@link QualityInvoiceLineGroupType}s.
 *
 * @author tsa
 *
 */
public final class QualityInvoiceLineGroupByTypeComparator implements Comparator<IQualityInvoiceLineGroup>
{
	/**
	 * Maps {@link QualityInvoiceLineGroupType} to it's sorting priority.
	 */
	private final Map<QualityInvoiceLineGroupType, Integer> type2index = new HashMap<>();

	/** Index to be used when type was not found */
	private final static int INDEX_NotFound = 10000000;

	public QualityInvoiceLineGroupByTypeComparator(final QualityInvoiceLineGroupType... inspectionLineTypes)
	{
		this(Arrays.asList(inspectionLineTypes));
	}

	public QualityInvoiceLineGroupByTypeComparator(final List<QualityInvoiceLineGroupType> inspectionLineTypes)
	{
		super();

		Check.assumeNotEmpty(inspectionLineTypes, "inspectionLineTypes not empty");

		//
		// Build type to priority index map
		for (int index = 0; index < inspectionLineTypes.size(); index++)
		{
			final QualityInvoiceLineGroupType type = inspectionLineTypes.get(index);
			Check.assumeNotNull(type, "type not null");

			final Integer indexOld = type2index.put(type, index);
			if (indexOld != null)
			{
				throw new IllegalArgumentException("Duplicate type " + type + " found in " + inspectionLineTypes);
			}
		}
	}

	@Override
	public int compare(final IQualityInvoiceLineGroup line1, final IQualityInvoiceLineGroup line2)
	{
		final int index1 = getIndex(line1);
		final int index2 = getIndex(line2);

		return index1 - index2;
	}

	private final int getIndex(final IQualityInvoiceLineGroup line)
	{
		Check.assumeNotNull(line, "line not null");
		final QualityInvoiceLineGroupType type = line.getQualityInvoiceLineGroupType();
		final Integer index = type2index.get(type);
		if (index == null)
		{
			return INDEX_NotFound;
		}

		return index;
	}

	private final boolean hasType(final QualityInvoiceLineGroupType type)
	{
		return type2index.containsKey(type);
	}

	/**
	 * Remove from given lines those which their type is not specified in our list.
	 *
	 * NOTE: we assume the list is read-write.
	 *
	 * @param groups
	 */
	public void filter(final List<IQualityInvoiceLineGroup> groups)
	{
		for (final Iterator<IQualityInvoiceLineGroup> it = groups.iterator(); it.hasNext();)
		{
			final IQualityInvoiceLineGroup group = it.next();
			final QualityInvoiceLineGroupType type = group.getQualityInvoiceLineGroupType();
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
	public void sort(final List<IQualityInvoiceLineGroup> lines)
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
	public void filterAndSort(final List<IQualityInvoiceLineGroup> lines)
	{
		filter(lines);
		sort(lines);
	}
}
