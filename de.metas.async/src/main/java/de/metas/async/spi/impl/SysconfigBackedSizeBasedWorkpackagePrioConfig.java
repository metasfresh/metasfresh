package de.metas.async.spi.impl;

/*
 * #%L
 * de.metas.async
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


import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Supplier;

import de.metas.logging.LogManager;

/**
 * This class uses <code>AD_Sysconfig</code> to obtain priorities for different workpackage-sizes.
 * The AD_SysConfig records need to be of the following form:
 * <p>
 * <code>"de.metas.async." + packageProcessorInternalName + ".SizeBasedPrio."+ position</code> where
 * <ul>
 * <li><code>packageProcessorInternalName</code> is {@link de.metas.async.model.I_C_Queue_PackageProcessor#COLUMN_InternalName C_Queue_PackageProcessor.InternalName}
 * <li><code>position</code> is the number of already enqueued workpackages plus 1. I.e. position=1 => first package.
 * </ul>
 * <code>AD_Sysconfig</code> records for the respective <code>packageProcessorInternalName</code> are iterated in the order of their <code>position</code> values, <b>greatest first</b>. <br>
 * The system picks the prio of the record with the smallest number that
 * <p>
 * Priorites need to be one of (upper/lower case doesn't matter):
 * <ul>
 * <li>urgent
 * <li>high
 * <li>medium
 * <li>low
 * <li>minor
 * </ul>
 * <p>
 * <code>AD_Sysconfig</code> with unparsable int positions or misspelled priority are ignored.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class SysconfigBackedSizeBasedWorkpackagePrioConfig implements Function<Integer, ConstantWorkpackagePrio>
{
	private static final transient Logger logger = LogManager.getLogger(SysconfigBackedSizeBasedWorkpackagePrioConfig.class);

	/**
	 * Internal cache for our sorted AD_SysConfig records. The cache is reset if the AD_SysConfig changes.
	 */
	private static CCache<String, SortedMap<Integer, ConstantWorkpackagePrio>> cache = new CCache<>(
			I_AD_SysConfig.Table_Name + "#" + SysconfigBackedSizeBasedWorkpackagePrioConfig.class.getName(),
			40, 0); // expire=never, but note that this cache will be invalidated when an AD_SysConfig record changes somewhere.

	private final ConstantWorkpackagePrio defaultPrio;

	private final Properties ctx;

	/**
	 * The prefix for the AD_SysConfig values that are evaluated by this instance.
	 */
	private final String sysConfigPrefix;

	/**
	 * 
	 * 
	 * @param queue the queue for which we create this function. Its internal name is used to construct the <code>AD_SysConfig</code> prefix.
	 * @param defaultPrio the priority to return in case that there is no proper <code>AD_SysConfig</code> info.
	 */
	/* package */SysconfigBackedSizeBasedWorkpackagePrioConfig(
			final Properties ctx,
			final String packageProcessorInternalName,
			final ConstantWorkpackagePrio defaultPrio)
	{
		Check.assumeNotEmpty(packageProcessorInternalName, "Param 'queue' is not null");
		Check.assumeNotNull(defaultPrio, "Param 'defaultPrio' is not null");

		this.ctx = ctx;

		// important: keep in sync with the class javadoc!
		this.sysConfigPrefix = "de.metas.async." + packageProcessorInternalName + ".SizeBasedPrio.";
		this.defaultPrio = defaultPrio;
	}

	/**
	 * Returns the AD_SysConfig.Name prefix of the AD_SysConfig records evaluated by this instance.
	 * 
	 * Note: package-visible so that we can use it to create AD_SysConfig records for automated tests.
	 * 
	 * @return
	 */
	@VisibleForTesting
	/* package */String getSysConfigPrefix()
	{
		return sysConfigPrefix;
	}

	/**
	 * See the {@link SysconfigBackedSizeBasedWorkpackagePrioConfig} class javadoc.
	 */
	@Override
	public ConstantWorkpackagePrio apply(final Integer size)
	{
		final SortedMap<Integer, ConstantWorkpackagePrio> sortedMap = retrieveParametersSortedMap();

		// if size = 1, the we look for a prio for the 2nd package, i.e. position=2
		final int position = size + 1;

		ConstantWorkpackagePrio result = null;
		for (final Entry<Integer, ConstantWorkpackagePrio> sortedMapEntry : sortedMap.entrySet())
		{
			result = sortedMapEntry.getValue();
			if (sortedMapEntry.getKey() > position)
			{
				continue;
			}
			break; // found it!
		}

		if (result != null)
		{
			return result;
		}

		logger.debug(
				"Found no priority for the given position {} (current queue size={}).\nThe {} AD_SysConfig-records which we checked are: {}.\nReturning the preset default prio: {}.",
				position, size, sortedMap.size(), sortedMap, defaultPrio.retrievePrioName());
		return defaultPrio;
	}

	/**
	 * See if we already have it cached, otherwise retrieve and sort the map.
	 * 
	 * @return a map, reverse-ordered (biggest first) by the size-portion of <code>AD_SysConfig.Name</code>.
	 */
	private SortedMap<Integer, ConstantWorkpackagePrio> retrieveParametersSortedMap()
	{
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final int AD_Org_ID = Env.getAD_Org_ID(ctx);

		final String chachingKey = "" + AD_Client_ID + "_" + AD_Org_ID + "_" + sysConfigPrefix;
		final SortedMap<Integer, ConstantWorkpackagePrio> sortedMap = cache.get(
				chachingKey,
				new Supplier<SortedMap<Integer, ConstantWorkpackagePrio>>()
				{
					@Override
					public SortedMap<Integer, ConstantWorkpackagePrio> get()
					{
						final Map<String, String> valuesForPrefix = Services.get(ISysConfigBL.class).getValuesForPrefix(sysConfigPrefix,
								AD_Client_ID,
								AD_Org_ID);

						// initialize reverse-sorted map (biggest number first)
						final TreeMap<Integer, ConstantWorkpackagePrio> sortedMap = new TreeMap<Integer, ConstantWorkpackagePrio>(new Comparator<Integer>()
						{
							@Override
							public int compare(Integer o1, Integer o2)
							{
								return o2 - o1;
							}
						});

						// add our values to the map
						for (final Entry<String, String> entry : valuesForPrefix.entrySet())
						{
							final int sizeInt = parseInt(entry.getKey(), sysConfigPrefix);
							if (sizeInt < 0)
							{
								// ignore it; note that we already logged a warning.
								continue;
							}

							final ConstantWorkpackagePrio constrantPrio = ConstantWorkpackagePrio.fromString(entry.getValue());
							if (constrantPrio == null)
							{
								logger.warn("Unable to parse the the priority string {}.\nPlease fix the value of the AD_SysConfig record with name={}", entry.getValue(), entry.getKey());
								continue;
							}
							sortedMap.put(sizeInt, constrantPrio);
						}
						return sortedMap;
					}
				});
		return sortedMap;
	}

	private int parseInt(final String completeString,
			final String prefix)
	{
		final String sizeStr = completeString.substring(prefix.length());
		int size = -1;
		try
		{
			size = Integer.parseInt(sizeStr);
		}
		catch (NumberFormatException e)
		{
			logger.warn("Unable to parse the prio int value from AD_SysConfig.Name={}. Ignoring its value.", completeString);
		}
		return size;
	}
}
