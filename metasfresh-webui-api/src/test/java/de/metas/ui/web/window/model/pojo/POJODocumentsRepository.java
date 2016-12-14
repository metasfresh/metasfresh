package de.metas.ui.web.window.model.pojo;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBMoreThenOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import com.google.common.base.Joiner;

import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.POJODocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.FieldValueSupplier;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentFieldView;

/*
 * #%L
 * metasfresh-webui-api
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

public final class POJODocumentsRepository implements DocumentsRepository
{
	public static final transient POJODocumentsRepository instance = new POJODocumentsRepository();
	
	private static final transient Logger logger = LogManager.getLogger(POJODocumentsRepository.class);

	private POJODocumentsRepository()
	{
		super();
	}

	private int getNextId(final DocumentEntityDescriptor entityDescriptor)
	{
		final POJODocumentEntityDataBindingDescriptor dataBinding = POJODocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());
		final String tableName = dataBinding.getTableName();
		return POJOLookupMap.get().nextId(tableName);
	}

	@Override
	public List<Document> retrieveDocuments(final DocumentQuery query)
	{
		final int limit = -1;
		return retriveDocuments(query, limit);
	}

	public List<Document> retriveDocuments(final DocumentQuery query, final int limit)
	{
		final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();
		final Document parentDocument = query.getParentDocument();

		return retrieveModels(query, limit)
				.stream()
				.map((model) -> retriveDocument(entityDescriptor, parentDocument, model))
				.collect(GuavaCollectors.toImmutableList());
	}

	private List<Object> retrieveModels(final DocumentQuery query, final int limit)
	{
		final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();
		final String tableName = entityDescriptor.getTableName();

		final List<Object> result = new ArrayList<>();
		for (final Object model : POJOLookupMap.get().getRawRecords(tableName))
		{
			final POJOWrapper modelWrapper = POJOWrapper.getWrapper(model);
			if (query.getRecordId() != null && modelWrapper.getId() != query.getRecordId())
			{
				continue;
			}

			if (query.isParentLinkIdSet())
			{
				throw new UnsupportedOperationException("Queries with parent link are not supported: " + query);
			}

			result.add(model);

			if (limit > 0 && result.size() >= limit)
			{
				break;
			}
		}

		return result;
	}

	@Override
	public Document retrieveDocument(final DocumentQuery query)
	{
		final int limit = 2;
		final List<Document> documents = retriveDocuments(query, limit);
		if (documents.isEmpty())
		{
			return null;
		}
		else if (documents.size() > 1)
		{
			throw new DBMoreThenOneRecordsFoundException("More than one record found for " + query + " on " + this
					+ "\n First " + limit + " records: " + Joiner.on("\n").join(documents));
		}
		else
		{
			return documents.get(0);
		}
	}

	@Override
	public Document createNewDocument(final DocumentEntityDescriptor entityDescriptor, final Document parentDocument)
	{
		final int documentId = getNextId(entityDescriptor);
		return Document.builder()
				.setEntityDescriptor(entityDescriptor)
				.setParentDocument(parentDocument)
				.setDocumentIdSupplier(() -> documentId)
				.initializeAsNewDocument()
				.build();
	}

	private Document retriveDocument(final DocumentEntityDescriptor entityDescriptor, final Document parentDocument, final Object fromModel)
	{
		return Document.builder()
				.setEntityDescriptor(entityDescriptor)
				.setParentDocument(parentDocument)
				.setDocumentIdSupplier(() -> retrieveDocumentId(entityDescriptor.getIdField(), fromModel))
				.initializeAsExistingRecord((fieldDescriptor) -> retrieveDocumentFieldValue(fieldDescriptor, fromModel))
				.build();
	}

	private int retrieveDocumentId(final DocumentFieldDescriptor idFieldDescriptor, final Object fromModel)
	{
		final Integer valueInt = (Integer)retrieveDocumentFieldValue(idFieldDescriptor, fromModel);
		return valueInt;
	}

	private Object retrieveDocumentFieldValue(final DocumentFieldDescriptor fieldDescriptor, final Object fromModel)
	{
		final DocumentFieldDataBindingDescriptor fieldDataBinding = fieldDescriptor.getDataBinding().orElse(null);
		if (fieldDataBinding == null)
		{
			logger.warn("No databinding provided for {}. Skip retrieving the value", fieldDescriptor);
			return FieldValueSupplier.NO_VALUE;
		}
		final String columnName = fieldDataBinding.getColumnName();

		final Object value = InterfaceWrapperHelper.getValueOrNull(fromModel, columnName);
		return value;
	}

	@Override
	public void refresh(final Document document)
	{
		refresh(document, document.getDocumentIdAsInt());
	}

	private void refresh(final Document document, final int documentId)
	{
		logger.debug("Refreshing: {}, using ID={}", document, documentId);

		final DocumentQuery query = DocumentQuery.ofRecordId(document.getEntityDescriptor(), documentId);
		final List<Object> models = retrieveModels(query, 2);
		if (models.isEmpty())
		{
			throw new AdempiereException("No data found while trying to reload the document: " + document);
		}
		else if (models.size() > 1)
		{
			throw new AdempiereException("More than one record found while trying to reload document: " + document);
		}

		final Object model = models.get(0);
		document.refresh((fieldDescriptor) -> retrieveDocumentFieldValue(fieldDescriptor, model));
	}

	@Override
	public void save(final Document document)
	{
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();

		//
		// Load the PO / Create new PO instance
		final Object model = retrieveOrCreateModel(document);

		//
		// Set values to PO
		for (final IDocumentFieldView documentField : document.getFieldViews())
		{
			setModelValue(model, documentField);
		}

		//
		// Actual save
		InterfaceWrapperHelper.save(model);

		// TODO: save included documents if any
		// * first, update their parent link if needed
		// * save them
		// * refresh them AFTER header document (this one) is refreshed

		//
		// Reload the document
		final int idNew = InterfaceWrapperHelper.getId(model);
		refresh(document, idNew);
	}

	private Object retrieveOrCreateModel(final Document document)
	{
		final Class<?> modelClass = POJODocumentEntityDataBindingDescriptor.getModelClass(document);

		//
		// Load the PO / Create new PO instance
		final Object model;
		if (document.isNew())
		{
			// new
			model = InterfaceWrapperHelper.create(document.getCtx(), modelClass, ITrx.TRXNAME_ThreadInherited);
		}
		else
		{
			model = InterfaceWrapperHelper.create(document.getCtx(), document.getDocumentIdAsInt(), modelClass, ITrx.TRXNAME_ThreadInherited);
		}
		Check.assumeNotNull(model, "model is not null");

		//
		// model.set_ManualUserAction(document.getWindowNo());

		return model;
	}

	private void setModelValue(final Object model, final IDocumentFieldView documentField)
	{
		final DocumentFieldDataBindingDescriptor dataBinding = documentField.getDescriptor().getDataBinding().orElse(null);
		if (dataBinding == null)
		{
			logger.trace("Skip setting model's column because it has no databinding: {}", documentField);
		}

		final String columnName = dataBinding.getColumnName();

		if (WindowConstants.FIELDNAMES_CreatedUpdated.contains(columnName))
		{
			logger.trace("Skip setting PO's created/updated column: {} -- model={}", columnName, model);
			return;
		}

		final Object valueOld = InterfaceWrapperHelper.getValueOrNull(model, columnName);
		final Object valueConv = convertValueToModel(documentField.getValue(), columnName, model);
		if (DataTypes.equals(valueConv, valueOld))
		{
			logger.trace("Skip setting PO's column because it was not changed: {}={} (old={}) -- model={}", columnName, valueConv, valueOld, model);
			return;
		}

		final boolean valueSet = InterfaceWrapperHelper.setValue(model, columnName, valueConv);
		if (!valueSet)
		{
			logger.warn("Failed setting model's column: {}={} (old={}) -- model={}", columnName, valueConv, valueOld, model);
			return;
		}

		logger.trace("Setting model value: {}={} (old={}) -- model={}", columnName, valueConv, valueOld, model);
	}

	private static Object convertValueToModel(final Object value, final String columnName, final Object model)
	{
		return value;
	}

	@Override
	public void delete(final Document document)
	{
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();

		if (document.isNew())
		{
			throw new IllegalArgumentException("Cannot delete new document: " + document);
		}

		final Object model = retrieveOrCreateModel(document);

		InterfaceWrapperHelper.delete(model);
	}
}
