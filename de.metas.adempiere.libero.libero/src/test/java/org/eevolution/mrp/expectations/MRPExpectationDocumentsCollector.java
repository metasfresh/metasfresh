package org.eevolution.mrp.expectations;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.impl.PlainMRPDAO;

import de.metas.document.engine.IDocument;
import de.metas.util.Check;
import de.metas.util.Services;

public class MRPExpectationDocumentsCollector
{
	private final transient PlainMRPDAO mrpDAO = (PlainMRPDAO)Services.get(IMRPDAO.class);
	private final Map<Class<?>, List<?>> modelClass2documents = new HashMap<>();
	private final Map<String, Set<Class<? extends Object>>> tableName2modelClasses = new HashMap<>();

	public MRPExpectationDocumentsCollector()
	{
		super();
	}

	public <T> void collect(final Class<T> modelClass, List<T> documents)
	{
		Check.assumeNotNull(modelClass, "modelClass not null");

		this.modelClass2documents.put(modelClass, documents);

		//
		// Update tableId2modelClasses
		{
			final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
			Set<Class<?>> modelClasses = tableName2modelClasses.get(tableName);
			if (modelClasses == null)
			{
				modelClasses = new HashSet<>();
				tableName2modelClasses.put(tableName, modelClasses);
			}

			modelClasses.add(modelClass);
		}
	}

	public void add(final I_PP_MRP mrp)
	{
		// If no collectors registered => do nothing
		if (tableName2modelClasses.isEmpty())
		{
			return;
		}

		final IMRPBL mrpBL = Services.get(IMRPBL.class);
		
		// Skip QOH any kind of reservations
		if (mrpBL.isQtyOnHandAnyReservation(mrp))
		{
			return;
		}
		
		final IDocument document = mrpDAO.retrieveDocumentOrNull(mrp);
		if(document == null)
		{
			return;
		}
		
		final String documentTableName = InterfaceWrapperHelper.getModelTableName(document);

		final Set<Class<?>> modelClasses = tableName2modelClasses.get(documentTableName);
		if (modelClasses == null)
		{
			return;
		}

		final int documentId = InterfaceWrapperHelper.getId(document);
		for (final Class<?> modelClass : modelClasses)
		{
			add(document, modelClass, documentTableName, documentId);
		}
	}

	private final <T> void add(IDocument document, final Class<T> modelClass, final String documentTableName, final int documentId)
	{
		@SuppressWarnings("unchecked")
		final List<T> documents = (List<T>)modelClass2documents.get(modelClass);
		if (documents == null)
		{
			return;
		}

		// Avoid duplicates
		if (exists(documents, documentTableName, documentId))
		{
			return;
		}

		final T documentCasted = InterfaceWrapperHelper.create(document, modelClass);
		documents.add(documentCasted);
	}

	private final boolean exists(final List<?> documents, final String documentTableName, final int documentId)
	{
		// Avoid duplicates
		for (final Object existingDocument : documents)
		{
			if (existingDocument == null)
			{
				continue;
			}

			final String existingDocumentTableName = InterfaceWrapperHelper.getModelTableName(existingDocument);
			if (!Check.equals(documentTableName, existingDocumentTableName))
			{
				continue;
			}

			final int existingDocumentId = InterfaceWrapperHelper.getId(existingDocument);
			if (documentId != existingDocumentId)
			{
				continue;
			}

			// If we reach this point, we found out that our document already exist
			return true;
		}

		return false;
	}
}
