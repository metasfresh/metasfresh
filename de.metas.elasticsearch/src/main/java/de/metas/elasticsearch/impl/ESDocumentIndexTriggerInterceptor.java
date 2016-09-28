package de.metas.elasticsearch.impl;

import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.elasticsearch.IESModelIndexingService;
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

/*package*/final class ESDocumentIndexTriggerInterceptor extends AbstractModelInterceptor
{
	private static final Logger logger = LogManager.getLogger(ESDocumentIndexTriggerInterceptor.class);

	private final String modelTableName;

	public ESDocumentIndexTriggerInterceptor(final String modelTableName)
	{
		super();

		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
		this.modelTableName = modelTableName;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("modelTableName", modelTableName)
				.toString();
	}
	
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelChange(modelTableName, this);
		engine.addDocValidate(modelTableName, this);
	}

	private final void addToIndexes(final Object model)
	{
		Services.get(IESModelIndexingService.class).addToIndexes(model);
	}

	private final void removeFromIndexes(final Object model)
	{
		Services.get(IESModelIndexingService.class).removeFromIndexes(model);
	}

	@Override
	public void onDocValidate(final Object document, final DocTimingType timing) throws Exception
	{
		try
		{
			switch (timing)
			{
				case AFTER_COMPLETE:
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
	public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
	{
		try
		{
			switch (changeType)
			{
				case AFTER_DELETE:
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
}
