package de.metas.document.impl;

import de.metas.document.engine.IDocument;
import de.metas.document.spi.ICounterDocHandler;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Intended for package-internal use (but feel free to move it to a "public" package).
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class NullCounterDocumentHandler implements ICounterDocHandler
{
	public static final NullCounterDocumentHandler instance = new NullCounterDocumentHandler();

	private NullCounterDocumentHandler()
	{
	}

	@Override
	public boolean isCreateCounterDocument(IDocument document)
	{
		return false;
	}

	@Override
	public boolean isCounterDocument(IDocument document)
	{
		return false;
	}

	@Override
	public IDocument createCounterDocument(IDocument document)
	{
		return null;
	}

}
