package org.adempiere.model;

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


import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.concurrent.ThreadSafe;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;

/**
 * {@link CopyRecordSupport} factory.
 * 
 * @author Cristina Ghita, METAS.RO
 */
@ThreadSafe
public class CopyRecordFactory
{
	private static final String SYSCONFIG_ENABLE_COPY_WITH_DETAILS = "ENABLE_COPY_WITH_DETAILS";

	private static final Map<String, Class<? extends CopyRecordSupport>> tableName2copyRecordSupportClass = new ConcurrentHashMap<>();

	/** List of table names for whom Copy With Details button is activated in Window toolbar */
	private static final Set<String> enabledTableNames = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

	/**
	 * Creates a new instance of {@link CopyRecordSupport}, used to assist while copying records.
	 * 
	 * @param tableName
	 * @return {@link CopyRecordSupport}; never returns null
	 */
	public static CopyRecordSupport getCopyRecordSupport(final String tableName)
	{
		final Class<? extends CopyRecordSupport> copyRecordSupportClass = tableName2copyRecordSupportClass.get(tableName);
		if (copyRecordSupportClass == null)
		{
			return new GeneralCopyRecordSupport();
		}

		try
		{
			return copyRecordSupportClass.newInstance();
		}
		catch (Exception e)
		{
			throw new AdempiereException("Failed creating " + CopyRecordSupport.class + " instance for " + tableName, e);
		}
	}

	public static void registerCopyRecordSupport(final String tableName, final Class<? extends CopyRecordSupport> copyRecordSupportClass)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotNull(copyRecordSupportClass, "copyRecordSupportClass not null");
		tableName2copyRecordSupportClass.put(tableName, copyRecordSupportClass);
	}

	/**
	 * @return true if copy-with-details functionality is enabled
	 */
	public static boolean isEnabled()
	{
		final boolean copyWithDetailsEnabledDefault = false;
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_ENABLE_COPY_WITH_DETAILS, copyWithDetailsEnabledDefault);
	}

	/**
	 * 
	 * @param gridTab
	 * @return true if copy-with-details functionality is enabled for given {@link GridTab}'s table name
	 * @see #isEnabledForTableName(String)
	 */
	public static boolean isEnabled(final GridTab gridTab)
	{
		final String tableName = gridTab.getTableName();
		if (!isEnabledForTableName(tableName))
		{
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param tableName
	 * @return true if copy-with-details functionality is enabled for given <code>tableName</code>
	 */
	public static boolean isEnabledForTableName(final String tableName)
	{
		return enabledTableNames.contains(tableName);
	}

	public static void enableForTableName(final String tableName)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		enabledTableNames.add(tableName);
	}
}
