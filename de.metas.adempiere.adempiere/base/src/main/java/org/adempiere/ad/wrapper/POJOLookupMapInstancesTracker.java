package org.adempiere.ad.wrapper;

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


import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Util;

import de.metas.cache.CacheMgt;
import de.metas.util.Services;
import de.metas.util.WeakList;

/**
 * Tool which is plugged into {@link POJOLookupMap} and makes sure that only one object instance for a given table record is returned by system.
 * 
 * In case more then one instance is detected an exception is thrown.
 * 
 * It is used by developer when debugging caching problems.
 * 
 * NOTE: this tool can slow down all save/load operations on {@link POJOLookupMap} like hell, so keep it disabled (see {@link #ENABLED}) and enable it only when it's needed.
 * 
 * @author tsa
 *
 */
public class POJOLookupMapInstancesTracker
{
	public static boolean ENABLED = false;
	public static boolean FAIL_IF_MORE_THEN_ONE_INSTANCE_FOUND = true;

	private static final String DYNATTR_TrackingStackTrace = POJOLookupMapInstancesTracker.class.getName() + "#TrackingStackTrace";

	private final Map<Object, RecordInfo> key2records = new HashMap<>();

	public void track(List<?> records)
	{
		if (!ENABLED)
		{
			return;
		}

		if (records == null || records.isEmpty())
		{
			return;
		}

		for (final Object record : records)
		{
			if (record == null)
			{
				continue;
			}

			track(record);
		}
		
		assertUniqueIds(records);
	}

	private final void track(Object record)
	{
		final Object key = mkRecordKeyIfTrackable(record);
		if (key == null)
		{
			// not trackable
			return;
		}
		RecordInfo recordInfo = key2records.get(key);

		InterfaceWrapperHelper.setDynAttribute(record, DYNATTR_TrackingStackTrace, new Exception());

		if (recordInfo != null)
		{
			runGarbageCollector();

			recordInfo.add(record);
			final List<Object> records = recordInfo.getRecords();
			if (records.size() > 1 && FAIL_IF_MORE_THEN_ONE_INSTANCE_FOUND)
			{
				throw new RuntimeException("More then one instance found for " + record
						+ "\nFound instances are: "
						+ "\n" + toString(records));
			}
		}
		else
		{
			recordInfo = new RecordInfo();
			recordInfo.add(record);
			key2records.put(key, recordInfo);
		}
	}

	/**
	 * This method guarantees that garbage collection is done unlike <code>{@link System#gc()}</code>
	 * 
	 * @see http://stackoverflow.com/questions/1481178/forcing-garbage-collection-in-java
	 */
	private void runGarbageCollector()
	{
		// Clear cache first, because there we can also have some instances.
		CacheMgt.get().reset();

		// Create a weak reference to a newly created object
		gcTestObject = new Object();
		final WeakReference<Object> ref = new WeakReference<Object>(gcTestObject);
		gcTestObject = null;

		// Run system garbage collector until our newly created object is garbage collected.
		// In this way we make sure garbage collector was executed.
		while (ref.get() != null)
		{
			System.gc();
		}
	}

	private Object gcTestObject = null;

	private final Object mkRecordKeyIfTrackable(Object record)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(record);
		final int recordId = InterfaceWrapperHelper.getId(record);
		final String trxName = InterfaceWrapperHelper.getTrxName(record);

		// don't track out of transaction objects
		if (Services.get(ITrxManager.class).isNull(trxName))
		{
			return null;
		}

		return Util.mkKey(tableName, recordId, trxName);
	}
	
	private final void assertUniqueIds(final List<?> records)
	{
		if (records == null || records.isEmpty())
		{
			return;
		}
		if (records.size() == 1)
		{
			// ofc it's unique ID
			return;
		}

		final Set<Integer> ids = new HashSet<Integer>();
		for (final Object record : records)
		{
			final POJOWrapper wrapper = POJOWrapper.getWrapper(record);
			final int recordId = wrapper.getId();
			
			if (!ids.add(recordId))
			{
				throw new RuntimeException("Duplicate ID found when retrieving from database"
						+"\n ID="+recordId
						+"\n Records: "+records);
			}
		}
	}


	private final String toString(final List<Object> records)
	{
		final StringBuilder sb = new StringBuilder();
		for (final Object record : records)
		{
			if (sb.length() > 0)
			{
				sb.append("\n\n");
			}

			sb.append("Record: ").append(record);

			final Throwable trackingStackTrace = InterfaceWrapperHelper.getDynAttribute(record, DYNATTR_TrackingStackTrace);
			if (trackingStackTrace != null)
			{
				sb.append("\nTrack stacktrace: ").append(Util.dumpStackTraceToString(trackingStackTrace));
			}
		}

		return sb.toString();
	}

	private static final class RecordInfo
	{
		private final WeakList<Object> records = new WeakList<>();

		public void add(final Object recordToAdd)
		{
			for (final Object record : records.hardList())
			{
				if (record == recordToAdd)
				{
					return;
				}
			}

			records.add(recordToAdd);
		}

		public List<Object> getRecords()
		{
			return records.hardList();
		}
	}
}
