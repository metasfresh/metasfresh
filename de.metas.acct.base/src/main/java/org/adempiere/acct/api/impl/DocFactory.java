package org.adempiere.acct.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.acct.api.IDocFactory;
import org.adempiere.acct.api.IDocMetaInfo;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.acct.Doc;
import org.compiere.acct.IDocBuilder;
import org.compiere.acct.PostingExecutionException;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MAcctSchema;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public class DocFactory implements IDocFactory
{
	/** Static Log */
	private final transient Logger logger = LogManager.getLogger(getClass());

	/** Map of AD_Table_ID to {@link IDocMetaInfo} */
	private transient Map<Integer, IDocMetaInfo> _tableId2docMetaInfo = null;

	/** {@link Doc} instance builder */
	private final class DocBuilder implements IDocBuilder
	{
		private Object documentModel;
		private MAcctSchema[] acctSchemas;
		private IDocMetaInfo docMetaInfo;

		public DocBuilder()
		{
			super();
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		@Override
		public Doc<?> build()
		{
			try
			{
				final IDocMetaInfo docMetaInfo = getDocMetaInfo();
				// Get the accountable document instance
				final Doc<?> doc = docMetaInfo.getDocConstructor().newInstance(this);

				// Return it
				return doc;
			}
			catch (final Exception e)
			{
				throw PostingExecutionException.wrapIfNeeded(e);
			}

		}

		@Override
		public MAcctSchema[] getAcctSchemas()
		{
			return acctSchemas;
		}

		@Override
		public DocBuilder setAcctSchemas(final MAcctSchema[] acctSchemas)
		{
			this.acctSchemas = acctSchemas;
			return this;
		}

		@Override
		public Object getDocumentModel()
		{
			return documentModel;
		}

		@Override
		public DocBuilder setDocumentModel(final Object documentModel)
		{
			Check.assumeNotNull(documentModel, "documentModel not null");
			this.documentModel = documentModel;
			return this;
		}

		public DocBuilder setDocMetaInfo(final IDocMetaInfo docMetaInfo)
		{
			this.docMetaInfo = docMetaInfo;
			return this;
		}

		public IDocMetaInfo getDocMetaInfo()
		{
			Check.assumeNotNull(docMetaInfo, "docMetaInfo not null");
			return docMetaInfo;
		}

		@Override
		public IDocFactory getDocFactory()
		{
			return DocFactory.this;
		}
	}

	public DocFactory()
	{
		super();
	}

	@Override
	public Doc<?> getOrNull(final Properties ctx, final MAcctSchema[] ass, final int AD_Table_ID, final int Record_ID, final String trxName)
	{
		final IDocMetaInfo docMetaInfo = getDocMetaInfoOrNull(AD_Table_ID);
		if (docMetaInfo == null)
		{
			// metas: tsa: use info instead of severe because this issue can happen on automatic posting too
			logger.info("Not found AD_Table_ID=" + AD_Table_ID);
			return null;
		}

		final String TableName = docMetaInfo.getTableName();

		final PO documentModel = TableModelLoader.instance.getPO(ctx, TableName, Record_ID, trxName);
		if (documentModel == null)
		{
			logger.error("Not Found: " + TableName + "_ID=" + Record_ID + " (Processed=Y, trxName=" + trxName + ")");
			return null;
		}

		return new DocBuilder()
				.setDocMetaInfo(docMetaInfo)
				.setDocumentModel(documentModel)
				.setAcctSchemas(ass)
				.build();
	}

	@Override
	public final Doc<?> get(final Properties ctx, final IDocMetaInfo docMetaInfo, final MAcctSchema[] ass, final ResultSet rs, final String trxName)
	{
		Check.assumeNotNull(docMetaInfo, "docMetaInfo not null");

		try
		{

			final String tableName = docMetaInfo.getTableName();
			final PO documentModel = TableModelLoader.instance.getPO(ctx, tableName, rs, trxName);

			return new DocBuilder()
					.setDocMetaInfo(docMetaInfo)
					.setAcctSchemas(ass)
					.setDocumentModel(documentModel)
					.build();
		}
		catch (final Exception e)
		{
			throw PostingExecutionException.wrapIfNeeded(e);
		}
	}

	private final synchronized Map<Integer, IDocMetaInfo> getDocMetaInfoMap()
	{
		if (_tableId2docMetaInfo == null)
		{
			_tableId2docMetaInfo = Collections.synchronizedMap(loadDocMetaInfo());
		}
		return _tableId2docMetaInfo;
	}

	@Override
	public List<IDocMetaInfo> getDocMetaInfoList()
	{
		return new ArrayList<>(getDocMetaInfoMap().values());
	}

	/** @return accountable document meta-info for given AD_Table_ID */
	private final IDocMetaInfo getDocMetaInfoOrNull(final int adTableId)
	{
		return getDocMetaInfoMap().get(adTableId);
	}

	/** Retries all accountable document meta-info from system */
	private final Map<Integer, IDocMetaInfo> loadDocMetaInfo()
	{
		//
		// Finds all AD_Table_IDs for tables which are not Views and have a column called "Posted"
		final List<Integer> tableIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Column.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Column.COLUMN_ColumnName, "Posted")
				.andCollect(I_AD_Column.COLUMN_AD_Table_ID)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Table.COLUMNNAME_IsView, false) // skip tables which are views
				.create()
				.listIds();

		final Map<Integer, IDocMetaInfo> tableId2docMetaInfo = new HashMap<>(tableIds.size());
		for (final int adTableId : tableIds)
		{
			final IDocMetaInfo docMetaData = createDocMetaInfoOrNull(adTableId);
			if (docMetaData == null)
			{
				continue;
			}

			tableId2docMetaInfo.put(adTableId, docMetaData);
		}

		return tableId2docMetaInfo;
	}

	/**
	 * Creates accountable document meta-info for given AD_Table_ID
	 * 
	 * @param adTableId
	 * @return document meta-info or <code>null</code> if document is not accountable
	 */
	private IDocMetaInfo createDocMetaInfoOrNull(final int adTableId)
	{
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);

		//
		// Build classname based on tableName.
		// Classname of the Doc class follows this convention:
		// * if the prefix (letters before the first underscore _) is 1 character, then the class is Doc_TableWithoutPrefixWithoutUnderscores
		// * otherwise Doc_WholeTableWithoutUnderscores
		final String packageName = "org.compiere.acct";
		final String className;
		final int firstUnderscore = tableName.indexOf("_");
		if (firstUnderscore == 1)
		{
			className = packageName + ".Doc_" + tableName.substring(2).replaceAll("_", "");
		}
		else
		{
			className = packageName + ".Doc_" + tableName.replaceAll("_", "");
		}

		//
		// Load class and constructor
		try
		{
			// Get the class loader to be used
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null)
			{
				classLoader = getClass().getClassLoader();
			}

			@SuppressWarnings("unchecked")
			final Class<? extends Doc<?>> docClass = (Class<? extends Doc<?>>)classLoader.loadClass(className);
			final Constructor<? extends Doc<?>> docConstructor = docClass.getConstructor(new Class[] { IDocBuilder.class });
			docConstructor.setAccessible(true);

			final DocMetaInfo docMetaInfo = new DocMetaInfo(adTableId, tableName, docClass, docConstructor);
			return docMetaInfo;
		}
		catch (final ClassNotFoundException e)
		{
			// NOTE: it could be that some Doc classes are missing (e.g. Doc_HRProcess),
			// and we don't want to pollute the log.
			final AdempiereException ex = new AdempiereException("No Doc class found for " + className, e);
			logger.info(ex.getLocalizedMessage(), ex);
		}
		catch (final Exception e)
		{
			final AdempiereException ex = new AdempiereException("Error while loading Doc class found for " + className, e);
			logger.warn(ex.getLocalizedMessage(), ex);
		}

		// No meta-info found
		return null;
	}
}
