package de.metas.elasticsearch.trigger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.elasticsearch.IESSystem;
import de.metas.elasticsearch.config.ESModelIndexerId;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Client;
import org.slf4j.Logger;

import java.util.List;

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
 * @param <DocumentType> type of document that will trigger indexing
 * @author metas-dev <dev@metasfresh.com>
 */
public final class ESDocumentIndexTriggerInterceptor<DocumentType> extends AbstractModelInterceptor implements IESModelIndexerTrigger
{
	// services
	private static final Logger logger = LogManager.getLogger(ESDocumentIndexTriggerInterceptor.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IESSystem esSystem = Services.get(IESSystem.class);

	private final String triggeringTableName;
	private final String triggeringKeyColumnName;
	//
	private final String modelTableName;
	private final String modelParentColumnName;
	//
	private final ESModelIndexerId modelIndexerId;

	private boolean triggerInstalled = false;

	/**
	 * @param documentClass  class of document which will trigger indexing
	 * @param modelTableName table name of models that we will index
	 */
	public ESDocumentIndexTriggerInterceptor(
			@NonNull final Class<DocumentType> documentClass,
			final String modelTableName,
			final String modelParentColumnName,
			@NonNull final ESModelIndexerId modelIndexerId)
	{

		triggeringTableName = InterfaceWrapperHelper.getTableName(documentClass);
		if (!Services.get(IDocumentBL.class).isDocumentTable(triggeringTableName))
		{
			throw new IllegalArgumentException("Table " + triggeringTableName + " must be a document table");
		}

		triggeringKeyColumnName = InterfaceWrapperHelper.getKeyColumnName(triggeringTableName);

		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
		this.modelTableName = modelTableName;

		Check.assumeNotEmpty(modelParentColumnName, "modelParentColumnName is not empty");
		this.modelParentColumnName = modelParentColumnName;

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
			if (changeType == ModelChangeType.BEFORE_DELETE)
			{
				// NOTE: triggering on BEFORE because on AFTER we won't be able to fetch the model IDs
				removeFromIndexes(model);
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed indexing: {} ({})", model, changeType, ex);
		}
	}

	private ImmutableSet<Integer> retrieveModelIds(final Object triggeringModel)
	{
		final int documentId = InterfaceWrapperHelper.getId(triggeringModel);
		final List<Integer> modelIds = queryBL
				.createQueryBuilder(modelTableName, triggeringModel)
				.addEqualsFilter(modelParentColumnName, documentId)
				.create()
				.listIds();
		return ImmutableSet.copyOf(modelIds);
	}

	private void addToIndexes(final Object triggeringModelObj)
	{
		final ImmutableSet<Integer> modelIdsToIndex = retrieveModelIds(triggeringModelObj);
		esSystem.indexingQueue()
				.addToIndex(modelIndexerId, modelTableName, modelIdsToIndex);
	}

	private void removeFromIndexes(final Object triggeringModelObj)
	{
		final ImmutableSet<Integer> modelIdsToRemove = retrieveModelIds(triggeringModelObj);
		esSystem.indexingQueue()
				.removeToIndex(modelIndexerId, modelTableName, modelIdsToRemove);
	}

	@Override
	public IQueryFilter<Object> getMatchingModelsFilter()
	{
		// Document(triggering) filter
		final IQuery<Object> documentsQuery = queryBL.createQueryBuilder(triggeringTableName, PlainContextAware.newWithThreadInheritedTrx())
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter("DocStatus", IDocument.STATUS_Completed, IDocument.STATUS_Closed, IDocument.STATUS_Reversed)
				.create();

		return queryBL.createCompositeQueryFilter(modelTableName)
				.addInSubQueryFilter(modelParentColumnName, triggeringKeyColumnName, documentsQuery)
				.addOnlyActiveRecordsFilter();
	}
}
