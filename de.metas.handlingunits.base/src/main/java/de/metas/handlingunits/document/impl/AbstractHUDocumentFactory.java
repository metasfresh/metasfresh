package de.metas.handlingunits.document.impl;

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
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.collections.SingletonIterator;

import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentFactory;

public abstract class AbstractHUDocumentFactory<T> implements IHUDocumentFactory
{
	protected final transient Logger logger = LogManager.getLogger(getClass());
	private final Class<T> modelClass;
	private final String tableName;

	/**
	 * An collector object which is passed to all building methods.
	 *
	 * It's aim is to collect {@link IHUDocument}s, {@link IHUCapacityDefinition} etc.
	 *
	 * @author tsa
	 *
	 */
	protected class HUDocumentsCollector
	{
		private final List<IHUDocument> documents = new ArrayList<IHUDocument>();
		private final List<IHUCapacityDefinition> targetCapacities = new ArrayList<IHUCapacityDefinition>();

		public HUDocumentsCollector()
		{
			super();
		}

		public List<IHUDocument> getHUDocuments()
		{
			return documents;
		}

		public List<IHUCapacityDefinition> getTargetCapacities()
		{
			return targetCapacities;
		}
	}

	/**
	 * Creates proper {@link IHUDocument}s and related stuff and adds them to given <code>documentsCollector</code>.
	 *
	 * @param documentsCollector
	 * @param model
	 */
	protected abstract void createHUDocumentsFromTypedModel(final HUDocumentsCollector documentsCollector, final T model);

	public AbstractHUDocumentFactory(final Class<T> modelClass)
	{
		super();

		Check.assumeNotNull(modelClass, "modelClass not null");
		this.modelClass = modelClass;

		this.tableName = InterfaceWrapperHelper.getTableName(modelClass);
		Check.assumeNotNull(tableName, "tableName");
	}

	protected final void assumeTableName(final String tableName)
	{
		Check.assume(this.tableName.equals(tableName),
				"Invalid tableName {}. Expected {}.", tableName, this.tableName);
	}

	@Override
	public final List<IHUDocument> createHUDocuments(final Properties ctx, final String tableName, final int recordId)
	{
		assumeTableName(tableName);

		final T model = InterfaceWrapperHelper.create(ctx, recordId, modelClass, ITrx.TRXNAME_None);

		final HUDocumentsCollector documentsCollector = new HUDocumentsCollector();
		createHUDocumentsFromTypedModel(documentsCollector, model);
		return documentsCollector.getHUDocuments();
	}

	@Override
	public final <IT> List<IHUDocument> createHUDocuments(final Properties ctx, final Class<IT> modelClass, final Iterator<IT> records)
	{
		final HUDocumentsCollector documentsCollector = new HUDocumentsCollector();
		createHUDocuments(documentsCollector, modelClass, records);
		return documentsCollector.getHUDocuments();
	}

	protected <IT> void createHUDocuments(final HUDocumentsCollector documentsCollector, final Class<IT> modelClass, final Iterator<IT> records)
	{
		assumeTableName(InterfaceWrapperHelper.getTableName(modelClass));

		while (records.hasNext())
		{
			final IT record = records.next();
			final T model = InterfaceWrapperHelper.create(record, this.modelClass);
			try
			{
				createHUDocumentsFromTypedModel(documentsCollector, model);
			}
			catch (final Exception e)
			{
				logger.warn("Error while creating source from " + model + ". Skipping.", e);
			}
		}
	}

	@Override
	public final List<IHUDocument> createHUDocuments(final ProcessInfo pi)
	{
		final HUDocumentsCollector documentsCollector = new HUDocumentsCollector();
		createHUDocuments(documentsCollector, pi);
		return documentsCollector.getHUDocuments();
	}

	protected final void createHUDocuments(final HUDocumentsCollector documentsCollector, final ProcessInfo pi)
	{
		Check.assumeNotNull(pi, "process info not null");

		final String tableName = pi.getTableNameOrNull();
		assumeTableName(tableName);

		final Iterator<T> models = retrieveModelsFromProcessInfo(pi);
		createHUDocuments(documentsCollector, modelClass, models);
	}

	/**
	 * Method used to retrieve relevant models from given ProcessInfo.
	 *
	 * @param pi
	 * @return
	 */
	protected Iterator<T> retrieveModelsFromProcessInfo(final ProcessInfo pi)
	{
		final Properties ctx = pi.getCtx();

		final int recordId = pi.getRecord_ID();
		Check.assume(recordId > 0, "No Record_ID found in {}", pi);

		final T model = InterfaceWrapperHelper.create(ctx, recordId, modelClass, ITrx.TRXNAME_None);
		return new SingletonIterator<T>(model);
	}

	@Override
	public final List<IHUDocument> createHUDocumentsFromModel(final Object modelObj)
	{
		final HUDocumentsCollector documentsCollector = new HUDocumentsCollector();
		final T model = InterfaceWrapperHelper.create(modelObj, modelClass);
		createHUDocumentsFromTypedModel(documentsCollector, model);

		return documentsCollector.getHUDocuments();
	}

}
