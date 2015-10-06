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


import java.util.List;

import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentLine;

public class PlainHUDocument extends AbstractHUDocument
{
	private final String displayName;
	private final List<IHUDocumentLine> lines;

	public PlainHUDocument(final String displayName, final List<IHUDocumentLine> lines)
	{
		super();

		this.displayName = displayName;
		this.lines = lines;
	}

	@Override
	public String getDisplayName()
	{
		return displayName;
	}

	@Override
	public List<IHUDocumentLine> getLines()
	{
		return lines;
	}

	@Override
	public IHUDocument getReversal()
	{
		return new PlainHUDocument(getDisplayName(), getReversalLines());
	}
}
