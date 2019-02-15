package de.metas.ui.web.dataentry.window.descriptor.factory;

import static de.metas.util.Check.fail;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.user.UserId;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.data.DataEntryRecordId;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.logging.LogManager;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.OrderedDocumentsList;
import de.metas.util.Services;
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
	private DataEntryRecordRepository dataEntryRecordRepository;

	private static final Logger logger = LogManager.getLogger(DataEntrySubGroupBindingRepository.class);

	public DataEntrySubGroupBindingRepository(@NonNull final DataEntryRecordRepository dataEntryRecordRepository)
	{
		this.dataEntryRecordRepository = dataEntryRecordRepository;
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

		// TODO Auto-generated method stub

		return Document.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setChangesCollector(changesCollector)
				.initializeAsNewDocument(documentId, "0");
	}

	private static DocumentId retrieveNextDocumentId(@NonNull final DocumentEntityDescriptor entityDescriptor)
	{
		final int adClientId = UserSession.getCurrent().getAD_Client_ID();
		final int nextId = DB.getNextID(adClientId, I_DataEntry_Record.Table_Name, ITrx.TRXNAME_ThreadInherited);
		if (nextId <= 0)
		{
			throw new DBException("Cannot retrieve next ID from database for " + entityDescriptor);
		}

		logger.trace("Acquired next ID={} for entityDescriptor={}", nextId, entityDescriptor);
		return DocumentId.of(nextId);
	}

	@Override
	public void refresh(@NonNull final Document document)
	{
		assertValidState(document);
		final DataEntryRecordId dataEntryRecordId = extractDataEntryRecordId(document);

		// TODO Auto-generated method stub

	}

	@Override
	public SaveResult save(@NonNull final Document document)
	{
		assertValidState(document);
		final DataEntryRecordId dataEntryRecordId = extractDataEntryRecordId(document);

		final DataEntryRecord dataEntryRecord = dataEntryRecordRepository.getBy(dataEntryRecordId);

		dataEntryRecord.clearRecordFields();

		final UserId userId = UserId.ofRepoId(Env.getAD_User_ID(document.getCtx()));

		for (final IDocumentFieldView fieldView : document.getFieldViews())
		{
			final DataEntryFieldBindingDescriptor dataBinding = fieldView.getDescriptor().getDataBindingNotNull(DataEntryFieldBindingDescriptor.class);

			final Object dataEntryFieldValue = extractFieldValue(fieldView.getValue(), dataBinding.getFieldType());

			final String fieldName = fieldView.getFieldName();
			final DataEntryFieldId dataEntryFieldId = DataEntryFieldId.ofRepoId(Integer.parseInt(fieldName)); // TODO extract this code and the code form DataEntryTabLoader into a common class

			dataEntryRecord.setRecordField(dataEntryFieldId, userId, dataEntryFieldValue);
		}

		dataEntryRecordRepository.save(dataEntryRecord);

		return SaveResult.SAVED;

	}

	private Object extractFieldValue(
			@Nullable final Object value,
			@NonNull final FieldType fieldType)
	{
		if (value == null)
		{
			return null;
		}

		final Object result;
		switch (fieldType)
		{
			case DATE:
				result = fieldType.getClass().cast(value);
				break;
			case LIST:
				result = fieldType.getClass().cast(value);
				break;
			case NUMBER:
				result = fieldType.getClass().cast(value);
				break;
			case STRING:
				result = fieldType.getClass().cast(value);
				break;
			case YESNO:
				result = fieldType.getClass().cast(value);
				break;
			default:
				fail("Unexpected fieldType={}", fieldType);
				result = null;
				break;
		}

		return result;
	}

	@Override
	public void delete(@NonNull final Document document)
	{
		assertValidState(document);
		final DataEntryRecordId dataEntryRecordId = extractDataEntryRecordId(document);

		dataEntryRecordRepository.delete(dataEntryRecordId);
	}

	private DataEntryRecordId extractDataEntryRecordId(@NonNull final Document document)
	{
		final DataEntryRecordId dataEntryRecordId = DataEntryRecordId.ofRepoId(document.getDocumentIdAsInt()); // TODO extract this code and the code form DataEntryTabLoader into a common class
		return dataEntryRecordId;
	}

	private void assertValidState(@NonNull final Document document)
	{
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();
		// assertThisRepository(document.getEntityDescriptor()); // TODO, like in de.metas.ui.web.window.model.sql.SqlDocumentsRepository
		DocumentPermissionsHelper.assertCanEdit(document);
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
