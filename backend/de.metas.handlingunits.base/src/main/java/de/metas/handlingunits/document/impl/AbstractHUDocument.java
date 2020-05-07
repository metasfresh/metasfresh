package de.metas.handlingunits.document.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;

import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.logging.LogManager;

public abstract class AbstractHUDocument implements IHUDocument
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	public AbstractHUDocument()
	{
		super();
	}

	private List<I_M_HU> assignedHandlingUnits = null;

	@Override
	public List<I_M_HU> getAssignedHandlingUnits()
	{
		if (assignedHandlingUnits != null)
		{
			return assignedHandlingUnits;
		}

		final List<I_M_HU> result = new ArrayList<I_M_HU>();
		final Set<Integer> seenHuIds = new HashSet<Integer>();

		for (final IHUDocumentLine sourceLine : getLines())
		{
			if (sourceLine.getHUAllocations() == null)
			{
				continue; // task 07711: avoid NPE
			}
			for (final I_M_HU hu : sourceLine.getHUAllocations().getAssignedHUs())
			{
				final int huId = hu.getM_HU_ID();
				if (!seenHuIds.add(huId))
				{
					// already added
					continue;
				}

				result.add(hu);
			}
		}

		assignedHandlingUnits = Collections.unmodifiableList(result);
		return assignedHandlingUnits;
	}

	protected List<IHUDocumentLine> getReversalLines()
	{
		final List<IHUDocumentLine> lines = getLines();
		final List<IHUDocumentLine> reversalLines = new ArrayList<IHUDocumentLine>(lines.size());
		for (final IHUDocumentLine line : lines)
		{
			final IHUDocumentLine reversalLine = line.getReversal();
			if (reversalLine == null)
			{
				continue;
			}
			reversalLines.add(reversalLine);
		}

		return reversalLines;
	}

	@Override
	public I_M_HU getInnerHandlingUnit()
	{
		// this is the default implementation, not an "emtpy method" as the sonar issue thinks
		return null;
	}

}
