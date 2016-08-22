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
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;

import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.descriptor.DefaultDocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.POJODocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentRepository;
import de.metas.ui.web.window.model.DocumentRepositoryQuery;
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

@Repository
@Profile("test")
@Primary
public class POJODocumentRepository implements DocumentRepository
{
	private static final transient Logger logger = LogManager.getLogger(POJODocumentRepository.class);

	public POJODocumentRepository()
	{
		super();
	}

	private int getNextId(final DocumentEntityDescriptor entityDescriptor)
	{
		final String tableName = entityDescriptor.getDataBinding().getTableName();
		return POJOLookupMap.get().nextId(tableName);
	}

	@Override
	public List<Document> retriveDocuments(final DocumentRepositoryQuery query)
	{
		final int limit = -1;
		return retriveDocuments(query, limit);
	}

	public List<Document> retriveDocuments(final DocumentRepositoryQuery query, final int limit)
	{
		final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();
		final Document parentDocument = query.getParentDocument();

		return retrieveModels(query, limit)
				.stream()
				.map((model) -> retriveDocument(entityDescriptor, parentDocument, model))
				.collect(GuavaCollectors.toImmutableList());
	}

	private List<Object> retrieveModels(final DocumentRepositoryQuery query, final int limit)
	{
		final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();
		final String tableName = entityDescriptor.getDataBinding().getTableName();

		final List<Object> result = new ArrayList<>();
		for (final Object model : POJOLookupMap.get().getRawRecords(tableName))
		{
			final POJOWrapper modelWrapper = POJOWrapper.getWrapper(model);
			if (query.isRecordIdSet() && modelWrapper.getId() != query.getRecordId())
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
	public Document retriveDocument(final DocumentRepositoryQuery query)
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
				.setDocumentRepository(this)
				.setEntityDescriptor(entityDescriptor)
				.setParentDocument(parentDocument)
				.setDocumentIdSupplier(() -> documentId)
				.initializeAsNewDocument()
				.build();
	}

	private Document retriveDocument(final DocumentEntityDescriptor entityDescriptor, final Document parentDocument, final Object fromModel)
	{
		return Document.builder()
				.setDocumentRepository(this)
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
		final DocumentFieldDataBindingDescriptor fieldDataBinding = fieldDescriptor.getDataBinding();
		final String columnName = fieldDataBinding.getColumnName();

		final Object value = InterfaceWrapperHelper.getValueOrNull(fromModel, columnName);
		return value;
	}

	@Override
	public void refresh(final Document document)
	{
		refresh(document, document.getDocumentId());
	}

	private void refresh(final Document document, final int documentId)
	{
		logger.debug("Refreshing: {}, using ID={}", document, documentId);

		final DocumentRepositoryQuery query = DocumentRepositoryQuery.ofRecordId(document.getEntityDescriptor(), documentId);
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
			model = InterfaceWrapperHelper.create(document.getCtx(), document.getDocumentId(), modelClass, ITrx.TRXNAME_ThreadInherited);
		}
		Check.assumeNotNull(model, "model is not null");
		// model.set_ManualUserAction(document.getWindowNo());

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

	private void setModelValue(final Object model, final IDocumentFieldView documentField)
	{
		final String columnName = documentField.getDescriptor().getDataBinding().getColumnName();

		if (DefaultDocumentDescriptorFactory.COLUMNNAMES_CreatedUpdated.contains(columnName))
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

}
