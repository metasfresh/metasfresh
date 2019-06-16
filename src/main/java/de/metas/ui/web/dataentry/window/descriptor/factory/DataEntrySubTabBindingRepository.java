package de.metas.ui.web.dataentry.window.descriptor.factory;

import static de.metas.util.Check.assumeNotNull;

import java.util.Optional;
import java.util.function.Function;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.Env;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.data.DataEntryRecordQuery;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.model.I_DataEntry_SubTab;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.DocumentValuesSupplier;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.OrderedDocumentsList;
import de.metas.user.UserId;
import de.metas.util.Check;
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

public class DataEntrySubTabBindingRepository implements DocumentsRepository
{
	private static final String VERSION_DEFAULT = "0";

	private final DataEntryRecordRepository dataEntryRecordRepository;

	private final DataEntryWebuiTools dataEntryWebuiTools;

	public DataEntrySubTabBindingRepository(
			@NonNull final DataEntryRecordRepository dataEntryRecordRepository,
			@NonNull final DataEntryWebuiTools dataEntryWebuiTools)
	{
		this.dataEntryWebuiTools = dataEntryWebuiTools;
		this.dataEntryRecordRepository = dataEntryRecordRepository;
	}

	/** Retrieves *or* creates the single child document for the given query's parent document and entity descriptor */
	@Override
	public OrderedDocumentsList retrieveDocuments(
			@NonNull final DocumentQuery query,
			@NonNull final IDocumentChangesCollector changesCollector)
	{
		final OrderedDocumentsList documentsCollector = OrderedDocumentsList.newEmpty(query.getOrderBys());

		final Document document = retrieveDocumentIfExists(query, changesCollector)
				.orElseGet(() -> createNewDocument(query.getEntityDescriptor(), query.getParentDocument(), changesCollector));

		documentsCollector.addDocument(document);

		return documentsCollector;
	}

	/** Retrieves *or* creates the single child document for the given query's parent document and entity descriptor */
	@Override
	public Document retrieveDocument(
			@NonNull final DocumentQuery query,
			@NonNull final IDocumentChangesCollector changesCollector)
	{
		return retrieveDocumentIfExists(query, changesCollector)
				.orElseGet(() -> createNewDocument(query.getEntityDescriptor(), query.getParentDocument(), changesCollector));
	}

	private Optional<Document> retrieveDocumentIfExists(
			@NonNull final DocumentQuery query,
			@NonNull final IDocumentChangesCollector changesCollector)
	{
		final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();

		final Document parentDocument = query.getParentDocument();
		final Function<DocumentId, Document> existingDocumentsSupplier = query.getExistingDocumentsSupplier();

		final DetailId detailId = query.getEntityDescriptor().getDetailId();
		final DataEntrySubTabId dataEntrySubTabId = extractDataEntrySubGroupId(detailId);
		final TableRecordReference parentRecordReference = extractParentRecordReference(parentDocument);

		final DataEntryRecordQuery dataEntryRecordQuery = DataEntryRecordQuery.builder()
				.dataEntrySubTabId(dataEntrySubTabId)
				.recordId(parentRecordReference.getRecord_ID())
				.build();

		final Optional<DataEntryRecord> dataEntryRecord = dataEntryRecordRepository.getBy(dataEntryRecordQuery);
		if (!dataEntryRecord.isPresent())
		{
			return Optional.empty();
		}

		final DocumentValuesSupplier documentValuesSupplier = new DataEntryDocumentValuesSupplier(
				dataEntryRecord.get(),
				dataEntryWebuiTools);

		Document document = null;
		if (existingDocumentsSupplier != null)
		{
			final DocumentId documentId = documentValuesSupplier.getDocumentId();
			document = existingDocumentsSupplier.apply(documentId);
		}
		if (document == null)
		{
			document = Document.builder(entityDescriptor)
					.setParentDocument(parentDocument)
					.setChangesCollector(changesCollector)
					.initializeAsExistingRecord(documentValuesSupplier);
		}
		return Optional.ofNullable(document);
	}

	@Override
	public DocumentId retrieveParentDocumentId(
			@NonNull final DocumentEntityDescriptor parentEntityDescriptor,
			@NonNull final DocumentQuery childDocumentQuery)
	{
		return DocumentId.of(childDocumentQuery.getParentLinkIdAsInt());
	}

	@Override
	public Document createNewDocument(
			@NonNull final DocumentEntityDescriptor entityDescriptor,
			@NonNull final Document parentDocument,
			@NonNull final IDocumentChangesCollector changesCollector)
	{
		final DocumentId documentId = createDocumentId(entityDescriptor, parentDocument);

		return Document
				.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setChangesCollector(changesCollector)
				.initializeAsNewDocument(documentId, VERSION_DEFAULT);
	}

	private static DocumentId createDocumentId(
			@NonNull final DocumentEntityDescriptor entityDescriptor,
			@NonNull final Document parentDocument)
	{
		final TableRecordReference parentRecordReference = extractParentRecordReference(parentDocument);
		final DataEntrySubTabId subGroupId = extractDataEntrySubGroupId(entityDescriptor.getDetailId());

		return createDocumentId(subGroupId, parentRecordReference);
	}

	private static DocumentId createDocumentId(
			@NonNull final DataEntrySubTabId subGroupId,
			@NonNull final ITableRecordReference parentRecordReference)
	{
		final String documentIdStr = new StringBuilder()
				.append("T").append(parentRecordReference.getTableName())
				.append("-R").append(parentRecordReference.getRecord_ID())
				.append("-SG").append(subGroupId.getRepoId())
				.toString();

		final DocumentId documentId = DocumentId.of(documentIdStr);
		return documentId;
	}

	@Override
	public void refresh(@NonNull final Document document)
	{
		assertValidState(document);

		final DataEntryRecordQuery dataEntryRecordQuery = extractDataEntryRecordQuery(document);
		final Optional<DataEntryRecord> dataEntryRecord = dataEntryRecordRepository.getBy(dataEntryRecordQuery);

		refreshFromDataEntryRecord(document, dataEntryRecord);
	}

	private static DataEntryRecordQuery extractDataEntryRecordQuery(@NonNull final Document document)
	{
		final DetailId detailId = document.getEntityDescriptor().getDetailId();
		final DataEntrySubTabId subGroupId = extractDataEntrySubGroupId(detailId);

		final Document parentDocument = document.getParentDocument();
		final TableRecordReference parentRecordReference = extractParentRecordReference(parentDocument);

		return DataEntryRecordQuery.builder()
				.dataEntrySubTabId(subGroupId)
				.recordId(parentRecordReference.getRecord_ID())
				.build();
	}

	private void refreshFromDataEntryRecord(
			@NonNull final Document document,
			@NonNull final Optional<DataEntryRecord> dataEntryRecord)
	{
		if (!dataEntryRecord.isPresent())
		{
			return;
		}

		final DocumentValuesSupplier documentValuesSupplier = new DataEntryDocumentValuesSupplier(
				dataEntryRecord.get(),
				dataEntryWebuiTools);
		document.refreshFromSupplier(documentValuesSupplier);
	}

	@Override
	public SaveResult save(@NonNull final Document document)
	{
		assertValidState(document);

		final DataEntryRecord dataEntryRecord;
		if (document.isNew())
		{
			dataEntryRecord = createDataEntryRecord(document);
		}
		else
		{
			final DataEntryRecordQuery dataEntryRecordQuery = extractDataEntryRecordQuery(document);

			dataEntryRecord = dataEntryRecordRepository
					.getBy(dataEntryRecordQuery)
					.orElseThrow(() -> new AdempiereException("Unable to retrieve dataEntryRecord for query=" + dataEntryRecordQuery));
		}

		boolean refreshNeeded = updateDataEntryRecord(document, dataEntryRecord);
		dataEntryRecordRepository.save(dataEntryRecord);

		if (refreshNeeded) // at least one value was changed
		{
			refreshFromDataEntryRecord(document, Optional.of(dataEntryRecord));
		}

		// Notify the parent document that one of it's children were saved (copied from SqlDocumentsRepository)
		document.getParentDocument().onChildSaved(document);

		return SaveResult.SAVED;

	}

	private boolean updateDataEntryRecord(@NonNull final Document document, @NonNull final DataEntryRecord dataEntryRecord)
	{
		boolean refreshNeeded = false;

		final UserId userId = UserId.ofRepoId(Env.getAD_User_ID(document.getCtx()));

		for (final IDocumentFieldView fieldView : document.getFieldViews())
		{
			final DataEntryFieldBindingDescriptor dataBinding = fieldView.getDescriptor().getDataBindingNotNull(DataEntryFieldBindingDescriptor.class);
			final FieldType fieldType = dataBinding.getFieldType();
			if (fieldType.equals(FieldType.SUB_TAB_ID)
					|| fieldType.equals(FieldType.PARENT_LINK_ID)
					|| fieldType.equals(FieldType.CREATED_UPDATED_INFO))
			{
				continue;
			}

			final Object dataEntryFieldValue = dataEntryWebuiTools.extractFieldValueForDataEntry(fieldView);

			final DataEntryFieldId dataEntryFieldId = dataEntryWebuiTools.computeDataEntryFieldId(fieldView);

			final boolean valueChanged = dataEntryRecord.setRecordField(dataEntryFieldId, userId, dataEntryFieldValue);
			refreshNeeded = refreshNeeded || valueChanged;
		}
		return refreshNeeded;
	}

	private static DataEntryRecord createDataEntryRecord(@NonNull final Document document)
	{

		final TableRecordReference parentReference = extractParentRecordReference(document.getParentDocument());

		final DetailId detailId = document.getEntityDescriptor().getDetailId();
		final DataEntrySubTabId dataEntrySubTabId = extractDataEntrySubGroupId(detailId);

		return DataEntryRecord.builder()
				.mainRecord(parentReference)
				.dataEntrySubTabId(dataEntrySubTabId)
				.build();
	}

	private static TableRecordReference extractParentRecordReference(@NonNull final Document parentDocument)
	{
		final String tableName = assumeNotNull(
				parentDocument.getEntityDescriptor().getTableNameOrNull(),
				"The parent of dataEntry a document needs to have a table name; parentDocument={}", parentDocument);

		final TableRecordReference parentReference = TableRecordReference.of(tableName, parentDocument.getDocumentIdAsInt());
		return parentReference;
	}

	private static DataEntrySubTabId extractDataEntrySubGroupId(@NonNull final DetailId detailId)
	{
		final int subGroupId = detailId.getIdInt();
		Check.assume(detailId.getIdPrefix().equals(I_DataEntry_SubTab.Table_Name), "The given document.entityDescriptor.detailId needs to have prefix={}", I_DataEntry_SubTab.Table_Name);

		final DataEntrySubTabId dataEntrySubTabId = DataEntrySubTabId.ofRepoId(subGroupId);
		return dataEntrySubTabId;
	}

	@Override
	public void delete(@NonNull final Document document)
	{
		assertValidState(document);

		final DataEntryRecordQuery dataEntryRecordQuery = extractDataEntryRecordQuery(document);

		dataEntryRecordRepository.deleteBy(dataEntryRecordQuery);
	}

	private void assertValidState(@NonNull final Document document)
	{
		assertThisRepository(document.getEntityDescriptor());
		if (Adempiere.isUnitTestMode())
		{
			return;
		}
		DocumentPermissionsHelper.assertCanEdit(document);
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();
	}

	@Override
	public String retrieveVersion(DocumentEntityDescriptor entityDescriptor, int documentIdAsInt)
	{
		return VERSION_DEFAULT;
	}

	@Override
	public int retrieveLastLineNo(DocumentQuery query)
	{
		return 0;
	}

	private static final class DataEntryDocumentValuesSupplier implements DocumentValuesSupplier
	{
		private final DataEntryRecord dataEntryRecord;
		private final DataEntryWebuiTools dataEntryWebuiTools;

		private DataEntryDocumentValuesSupplier(
				@NonNull final DataEntryRecord dataEntryRecord,
				@NonNull DataEntryWebuiTools dataEntryWebuiTools)
		{
			this.dataEntryWebuiTools = dataEntryWebuiTools;
			this.dataEntryRecord = dataEntryRecord;
		}

		@Override
		public DocumentId getDocumentId()
		{
			final DocumentId documentId = DataEntrySubTabBindingRepository.createDocumentId(
					dataEntryRecord.getDataEntrySubTabId(),
					dataEntryRecord.getMainRecord());

			return documentId;
		}

		@Override
		public String getVersion()
		{
			return VERSION_DEFAULT;
		}

		@Override
		public Object getValue(@NonNull final DocumentFieldDescriptor fieldDescriptor)
		{
			return dataEntryWebuiTools.extractDataEntryValueForField(dataEntryRecord, fieldDescriptor);
		}

	}
}
