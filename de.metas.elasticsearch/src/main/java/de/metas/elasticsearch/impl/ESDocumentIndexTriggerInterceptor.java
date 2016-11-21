package de.metas.elasticsearch.impl;

import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Client;
import org.compiere.process.DocAction;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import de.metas.document.engine.IDocActionBL;
import de.metas.elasticsearch.IESIndexerResult;
import de.metas.elasticsearch.IESModelIndexerTrigger;
import de.metas.elasticsearch.IESModelIndexingService;
import de.metas.elasticsearch.async.AsyncAddToIndexProcessor;
import de.metas.elasticsearch.async.AsyncRemoveFromIndexProcessor;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.elasticsearch
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
 * Model interceptor which triggers indexing when the given document type changes
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <DocumentType> type of document that will trigger indexing
 */
/* package */final class ESDocumentIndexTriggerInterceptor<DocumentType> extends AbstractModelInterceptor implements IESModelIndexerTrigger
{
	// services
	private static final Logger logger = LogManager.getLogger(ESDocumentIndexTriggerInterceptor.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private final Class<DocumentType> triggeringDocumentClass;
	private final String triggeringTableName;
	private final String triggeringKeyColumnName;
	//
	private final String modelTableName;
	private final String modelParentColumnName;
	//
	private final String modelIndexerId;

	private boolean triggerInstalled = false;

	/**
	 * @param documentClass class of document which will trigger indexing
	 * @param modelTableName table name of models that we will index
	 * @param modelIdsExtractor function which returns the model IDs for a given document
	 */
	public ESDocumentIndexTriggerInterceptor(
			final Class<DocumentType> documentClass //
	//
	, final String modelTableName //
	, final String modelParentColumnName //
	//
	, final String modelIndexerId)
	{
		super();

		Check.assumeNotNull(documentClass, "Parameter documentClass is not null");
		this.triggeringDocumentClass = documentClass;

		triggeringTableName = InterfaceWrapperHelper.getTableName(triggeringDocumentClass);
		if (!Services.get(IDocActionBL.class).isDocumentTable(triggeringTableName))
		{
			throw new IllegalArgumentException("Table " + triggeringTableName + " must be a document table");
		}

		triggeringKeyColumnName = InterfaceWrapperHelper.getKeyColumnName(triggeringTableName);

		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
		this.modelTableName = modelTableName;

		Check.assumeNotEmpty(modelParentColumnName, "modelParentColumnName is not empty");
		this.modelParentColumnName = modelParentColumnName;

		Check.assumeNotEmpty(modelIndexerId, "modelIndexerId is not empty");
		this.modelIndexerId = modelIndexerId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("triggeringTableName", triggeringTableName)
				.add("modelTableName", modelTableName)
				.add("modelParentColumnName", modelParentColumnName)
				.add("modelIndexerId", modelIndexerId)
				.add("installed", triggerInstalled)
				.toString();
	}

	@Override
	public synchronized void install()
	{
		if (!triggerInstalled)
		{
			Services.get(IModelInterceptorRegistry.class).addModelInterceptor(this);
		}
		triggerInstalled = true;
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelChange(triggeringTableName, this);
		engine.addDocValidate(triggeringTableName, this);
	}

	@Override
	public void onDocValidate(final Object document, final DocTimingType timing)
	{
		try
		{
			switch (timing)
			{
				case AFTER_COMPLETE:
				case AFTER_CLOSE:
					addToIndexes(document);
					break;
				case AFTER_REVERSECORRECT:
				case AFTER_REVERSEACCRUAL:
					addToIndexes(document);
					break;
				case AFTER_REACTIVATE:
				case AFTER_VOID:
					removeFromIndexes(document);
					break;
				default:
					// nothing
					break;
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed indexing: {} ({})", document, timing, ex);
		}
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType)
	{
		try
		{
			switch (changeType)
			{
				case BEFORE_DELETE:
					// NOTE: triggering on BEFORE because on AFTER we won't be able to fetch the model IDs
					removeFromIndexes(model);
					break;
				default:
					// nothing
					break;
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed indexing: {} ({})", model, changeType, ex);
		}
	}

	private final List<Integer> retriveModelIds(final Object triggeringModel)
	{
		final int documentId = InterfaceWrapperHelper.getId(triggeringModel);
		return queryBL
				.createQueryBuilder(modelTableName, triggeringModel)
				.addEqualsFilter(modelParentColumnName, documentId)
				.create()
				.listIds();
	}
	
	private final Iterator<Object> retriveModels(final Object triggeringModel)
	{
		final int documentId = InterfaceWrapperHelper.getId(triggeringModel);
		return queryBL
				.createQueryBuilder(modelTableName, triggeringModel)
				.addEqualsFilter(modelParentColumnName, documentId)
				.create()
				.iterate(Object.class);
	}


	private final void addToIndexes(final Object triggeringModel)
	{
		addToIndexesSync(triggeringModel);
	}

	private final void addToIndexesSync(final Object triggeringModelObj)
	{
		final Iterator<Object> modelsToIndex = retriveModels(triggeringModelObj);

		final IESIndexerResult result = Services.get(IESModelIndexingService.class)
				.getModelIndexerById(modelIndexerId)
				.addToIndex(modelsToIndex);
		if (result.hasFailures())
		{
			logger.error("Failed indexing {}", result);
		}
	}

	private final void addToIndexesAsync(final Object triggeringModelObj)
	{
		final List<Integer> modelIdsToIndex = retriveModelIds(triggeringModelObj);
		if (modelIdsToIndex.isEmpty())
		{
			return;
		}
		AsyncAddToIndexProcessor.enqueue(modelIndexerId, modelTableName, modelIdsToIndex);
	}

	private final void removeFromIndexes(final Object triggeringModel)
	{
		removeFromIndexesSync(triggeringModel);
	}

	private final void removeFromIndexesSync(final Object triggeringModelObj)
	{
		final List<String> modelIdsToRemove = Lists.transform(retriveModelIds(triggeringModelObj), modelId -> modelId.toString());

		final IESIndexerResult result = Services.get(IESModelIndexingService.class)
				.getModelIndexerById(modelIndexerId)
				.removeFromIndexByIds(modelIdsToRemove);
		if (result.hasFailures())
		{
			logger.error("Failed indexing {}", result);
		}
	}

	private final void removeFromIndexesAsync(final Object triggeringModelObj)
	{
		final List<Integer> modelIdsToRemove = retriveModelIds(triggeringModelObj);
		if (modelIdsToRemove.isEmpty())
		{
			return;
		}

		AsyncRemoveFromIndexProcessor.enqueue(modelIndexerId, modelTableName, modelIdsToRemove);
	}

	@Override
	public IQueryFilter<Object> getMatchingModelsFilter()
	{
		// Document(triggering) filter
		final IQuery<Object> documentsQuery = queryBL.createQueryBuilder(triggeringTableName, PlainContextAware.createUsingThreadInheritedTransaction())
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter("DocStatus", DocAction.STATUS_Completed, DocAction.STATUS_Closed, DocAction.STATUS_Reversed)
				.create();

		return queryBL.createCompositeQueryFilter(modelTableName)
				.addInSubQueryFilter(modelParentColumnName, triggeringKeyColumnName, documentsQuery)
				.addOnlyActiveRecordsFilter();
	}
}
