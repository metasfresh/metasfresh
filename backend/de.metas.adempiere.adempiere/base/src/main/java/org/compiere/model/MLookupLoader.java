package org.compiere.model;

import com.google.common.base.Stopwatch;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.INamePairIterator;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.IValidationContext;
import org.compiere.model.MLookup.ILookupData;
import org.compiere.util.NamePair;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

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
	@Serial
	private static final long serialVersionUID = -7868426685745727939L;
	private static final transient Logger log = LogManager.getLogger(MLookupLoader.class);

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

	/**
	 * MLoader Constructor
	 */
	public MLookupLoader(final IValidationContext validationCtx, final MLookupInfo lookupInfo)
	{
		super();

		threadName = "MLookupLoader-W" + lookupInfo.getWindowNo() + "-" + lookupInfo.getSqlQuery().getKeyColumn();
		log.debug("Loading: {}", threadName);

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
		final Stopwatch duration = Stopwatch.createStarted();
		
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
			log.warn("{}: Loader interrupted", threadName);
			this.wasInterrupted = true;
			return this;
		}

		// Reset
		values.clear();

		hasInactiveValues = false;
		allLoaded = true;

		final INamePairPredicate postQueryFilter = lookupInfo.getValidationRule().getPostQueryFilter();
		for (NamePair item = data.next(); item != null; item = data.next())
		{
			final int rows = values.size();
			if (rows >= MLookup.MAX_ROWS)
			{
				final String errmsg = lookupInfo.getSqlQuery().getKeyColumn() + ": Loader - Too many records. Please consider changing it to Search reference or use a (better) validation rule."
						+ "\n Fetched Rows: " + rows
						+ "\n Max rows allowed: " + MLookup.MAX_ROWS;
				log.warn(errmsg);
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

			if (!postQueryFilter.accept(validationCtx, item))
			{
				continue;
			}

			values.put(item.getID(), item);
		}
		
		duration.stop();

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
