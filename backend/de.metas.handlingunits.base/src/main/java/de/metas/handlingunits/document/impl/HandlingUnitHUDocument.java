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
import java.util.List;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Handling unit as {@link IHUDocument}
 *
 * @author tsa
 *
 */
/* package */class HandlingUnitHUDocument extends AbstractHUDocument
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	// private final I_M_HU hu;
	private final I_M_HU innerHU;
	private final String displayName;
	private final List<IHUDocumentLine> documentLines;
	private final List<IHUDocumentLine> documentLinesRO;

	public HandlingUnitHUDocument(final I_M_HU hu, final I_M_HU innerHU, final List<IHUDocumentLine> documentLines)
	{
		super();

		Check.assumeNotNull(hu, "hu not null");
		// this.hu = hu;
		this.innerHU = hu;

		displayName = createDisplayName(hu);

		Check.assumeNotNull(documentLines, "documentLines not null");
		// Check.assume(!documentLines.isEmpty(), "documentLines not empty");
		this.documentLines = new ArrayList<IHUDocumentLine>(documentLines);
		documentLinesRO = Collections.unmodifiableList(this.documentLines);
	}

	private final String createDisplayName(final I_M_HU hu)
	{
		final StringBuilder sb = new StringBuilder();

		final String huDisplayName = handlingUnitsBL.getDisplayName(hu);
		sb.append(huDisplayName);

		final I_M_HU parentHU = handlingUnitsDAO.retrieveParent(hu);
		if (parentHU != null)
		{
			final String parentHUDisplayName = handlingUnitsBL.getDisplayName(parentHU);
			sb.append(" - ");
			sb.append(parentHUDisplayName);
		}

		return sb.toString();
	}

	@Override
	public String getDisplayName()
	{
		return displayName;
	}

	@Override
	public List<IHUDocumentLine> getLines()
	{
		return documentLinesRO;
	}

	@Override
	public IHUDocument getReversal()
	{
		// Always null, there is no reversal
		return null;
	}

	@Override
	public I_M_HU getInnerHandlingUnit()
	{
		return innerHU;
	}

}
