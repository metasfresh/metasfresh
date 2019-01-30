package de.metas.ui.web.dataentry.window.descriptor.factory;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.dataentry.model.I_DataEntry_Record_Assignment;
import de.metas.logging.LogManager;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.OrderedDocumentsList;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class DataEntrySubGroupBindingRepository implements DocumentsRepository
{

	private static final Logger logger = LogManager.getLogger(DataEntrySubGroupBindingRepository.class);

	public static final DataEntrySubGroupBindingRepository INSTANCE = new DataEntrySubGroupBindingRepository();

	private DataEntrySubGroupBindingRepository()
	{

	}

	@Override
	public OrderedDocumentsList retrieveDocuments(
			@NonNull final DocumentQuery query,
			@NonNull final IDocumentChangesCollector changesCollector)
	{
		// TODO
		return OrderedDocumentsList.of(ImmutableList.of(), ImmutableList.of());
	}

	@Override
	public Document retrieveDocument(
			@NonNull final DocumentQuery query,
			@NonNull final IDocumentChangesCollector changesCollector)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentId retrieveParentDocumentId(DocumentEntityDescriptor parentEntityDescriptor, DocumentQuery childDocumentQuery)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document createNewDocument(
			@NonNull final DocumentEntityDescriptor entityDescriptor,
			Document parentDocument, IDocumentChangesCollector changesCollector)
	{
		final DocumentId documentId = retrieveNextDocumentId(entityDescriptor);

		return Document.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setChangesCollector(changesCollector)
				.initializeAsNewDocument(documentId, "0");

	}

	private static DocumentId retrieveNextDocumentId(@NonNull final DocumentEntityDescriptor entityDescriptor)
	{
		final int adClientId = UserSession.getCurrent().getAD_Client_ID();
		final int nextId = DB.getNextID(adClientId, I_DataEntry_Record_Assignment.Table_Name, ITrx.TRXNAME_ThreadInherited);
		if (nextId <= 0)
		{
			throw new DBException("Cannot retrieve next ID from database for " + entityDescriptor);
		}

		logger.trace("Acquired next ID={} for {}", nextId, entityDescriptor);
		return DocumentId.of(nextId);
	}

	@Override
	public void refresh(Document document)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public SaveResult save(Document document)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Document document)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String retrieveVersion(DocumentEntityDescriptor entityDescriptor, int documentIdAsInt)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int retrieveLastLineNo(DocumentQuery query)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
