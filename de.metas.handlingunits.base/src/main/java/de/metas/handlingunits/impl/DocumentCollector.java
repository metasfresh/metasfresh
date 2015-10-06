package de.metas.handlingunits.impl;

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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import de.metas.handlingunits.IDocumentCollector;

/**
 * Default implementation of {@link IDocumentCollector} which collects all documents.
 *
 * @author tsa
 *
 */
public class DocumentCollector implements IDocumentCollector
{
	private List<Object> collectedDocuments = null;

	@Override
	public void addDocument(final Object document)
	{
		// Guard: skip null documents (shall not happen)
		if (document == null)
		{
			return;
		}

		if (collectedDocuments == null)
		{
			collectedDocuments = new ArrayList<Object>();
		}

		collectedDocuments.add(document);
	}

	@Override
	public <T> void addDocuments(final List<T> documents)
	{
		if (documents == null || documents.isEmpty())
		{
			return;
		}

		if (collectedDocuments == null)
		{
			collectedDocuments = new ArrayList<Object>();
		}

		collectedDocuments.addAll(documents);
	}

	@Override
	public <T> List<T> getDocuments(final Class<T> documentType)
	{
		if (collectedDocuments == null || collectedDocuments.isEmpty())
		{
			return Collections.emptyList();
		}

		Check.assumeNotNull(documentType, "documentType not null");

		//
		// Iterate all collected documents and get those which are of our required type.
		final List<T> result = new ArrayList<T>();
		for (final Object document : collectedDocuments)
		{
			if (!InterfaceWrapperHelper.isInstanceOf(document, documentType))
			{
				continue;
			}

			final T documentCasted = InterfaceWrapperHelper.create(document, documentType);
			result.add(documentCasted);
		}

		if (result.isEmpty())
		{
			return Collections.emptyList();
		}

		return Collections.unmodifiableList(result);
	}
}
