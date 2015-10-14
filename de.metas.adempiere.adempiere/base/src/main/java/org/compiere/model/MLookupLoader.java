package org.compiere.model;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;

import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.INamePairIterator;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.util.Services;
import org.compiere.model.MLookup.ILookupData;
import org.compiere.util.CLogger;
import org.compiere.util.NamePair;
import org.compiere.util.Util.ArrayKey;

/**
 * Asynchronous lookup data loader
 * 
 * @author tsa
 * 
 */
/* package */class MLookupLoader implements Callable<ILookupData>, Serializable, ILookupData
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7868426685745727939L;
	private static final transient CLogger log = CLogger.getCLogger(MLookupLoader.class);

	//
	// Services
	private final transient ILookupDAO lookupDAO = Services.get(ILookupDAO.class);

	private final String threadName;
	private final IValidationContext validationCtx;
	private final MLookupInfo lookupInfo;

	private ArrayKey validationKey = null;
	private Map<String, NamePair> values = new LinkedHashMap<String, NamePair>();
	private boolean hasInactiveValues = false;
	private boolean allLoaded = false;
	private boolean wasInterrupted = false;

	private final long m_startTime = System.currentTimeMillis();

	/**
	 * MLoader Constructor
	 */
	public MLookupLoader(final IValidationContext validationCtx, final MLookupInfo lookupInfo)
	{
		super();

		threadName = "MLookupLoader-W" + lookupInfo.getWindowNo() + "-" + lookupInfo.getKeyColumnFQ();
		log.log(Level.FINE, "Loading: " + threadName);

		this.validationCtx = validationCtx;
		this.lookupInfo = lookupInfo;
	}	// Loader

	/**
	 * Load Lookup
	 */
	@Override
	public ILookupData call()
	{
		// Set thread name
		final Thread thread = Thread.currentThread();
		final String threadNameOld = thread.getName();
		thread.setName(threadNameOld + "-" + this.threadName);

		boolean success = false;
		try (final INamePairIterator values = lookupDAO.retrieveLookupValues(validationCtx, lookupInfo))
		{
			final ILookupData data = call0(values);
			success = true;
			return data;
		}
		finally
		{
			if (!success)
			{
				this.wasInterrupted = true;
			}
			thread.setName(threadNameOld);
		}

	}

	private final ILookupData call0(final INamePairIterator data)
	{

		final long startTime = System.currentTimeMillis();
		MLookupCache.loadStart(lookupInfo);

		if (!data.isValid())
		{
			this.validationKey = null;
			values.clear();
			return this;
		}

		this.validationKey = MLookup.createValidationKey(validationCtx, lookupInfo, data.getValidationKey());

		// check
		if (Thread.interrupted())
		{
			log.log(Level.WARNING, threadName + ": Loader interrupted");
			this.wasInterrupted = true;
			return this;
		}

		// Reset
		values.clear();

		hasInactiveValues = false;
		allLoaded = true;

		for (NamePair item = data.next(); item != null; item = data.next())
		{
			final int rows = values.size();
			if (rows >= MLookup.MAX_ROWS)
			{
				final String errmsg = lookupInfo.KeyColumn + ": Loader - Too many records. Please consider changing it to Search reference or use a (better) validation rule."
						+ "\n Fetched Rows: " + rows
						+ "\n Max rows allowed: " + MLookup.MAX_ROWS;
				log.warning(errmsg);
				allLoaded = false;
				break;
			}
			// check for interrupted every 20 rows
			if (rows % 20 == 0 && Thread.interrupted())
			{
				this.wasInterrupted = true;
				break;
			}

			if (!data.wasActive())
			{
				hasInactiveValues = true;
			}

			if (!lookupInfo.getValidationRule().accept(validationCtx, item))
			{
				continue;
			}

			values.put(item.getID(), item);
		}

		if (log.isLoggable(Level.FINER))
		{
			final int size = values.size();
			log.finer(lookupInfo.getKeyColumnFQ()
					+ " (" + lookupInfo.getAD_Column_ID() + "):"
					// + " ID=" + m_info.AD_Column_ID + " " +
					+ " - Loader complete #" + size + " - all=" + allLoaded
					+ " - ms=" + String.valueOf(System.currentTimeMillis() - m_startTime)
					+ " (" + String.valueOf(System.currentTimeMillis() - startTime) + ")");
		}

		MLookupCache.loadEnd(lookupInfo, values);

		return this;
	}	// run

	@Override
	public ArrayKey getValidationKey()
	{
		return validationKey;
	}

	@Override
	public boolean hasInactiveValues()
	{
		return this.hasInactiveValues;
	}

	@Override
	public boolean isAllLoaded()
	{
		return allLoaded;
	}

	@Override
	public boolean isDirty()
	{
		return wasInterrupted;
	}

	private final String normalizeKey(final Object key)
	{
		return key == null ? null : key.toString();
	}

	@Override
	public boolean containsKey(Object key)
	{
		final String keyStr = normalizeKey(key);
		return values.containsKey(keyStr);
	}

	@Override
	public NamePair getByKey(Object key)
	{
		final String keyStr = normalizeKey(key);
		return values.get(keyStr);
	}

	@Override
	public Collection<NamePair> getValues()
	{
		return values.values();
	}

	@Override
	public int size()
	{
		return values.size();
	}

}
