package org.adempiere.service.impl;

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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IValuePreferenceBL;
import org.compiere.model.I_AD_Preference;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;
import org.compiere.util.Util;

public class ValuePreferenceBL implements IValuePreferenceBL
{
	public static final ValuePreferenceBL instance = new ValuePreferenceBL();

	private final transient Logger logger = LogManager.getLogger(getClass());

	private ValuePreferenceBL()
	{
	}

	public String getContextKey(I_AD_Preference preference)
	{
		final String attribute = preference.getAttribute();
		Util.assume(!Util.isEmpty(attribute), "Attribute not empty for " + preference);

		final int adWindowId = preference.getAD_Window_ID();

		if (adWindowId > 0)
		{
			return "P" + adWindowId + "|" + attribute;
		}
		else
		{
			return "P|" + attribute;
		}
	}

	public I_AD_Preference fetch(Properties ctx, String attribute, int adClientId, int adOrgId, int adUserId, int adWindowId)
	{
		final String trxName = null;
		final List<Object> params = new ArrayList<Object>();
		final StringBuffer whereClause = new StringBuffer();

		whereClause.append(I_AD_Preference.COLUMNNAME_AD_Client_ID).append("=?");
		params.add(adClientId);

		whereClause.append(" AND ").append(I_AD_Preference.COLUMNNAME_AD_Org_ID).append("=?");
		params.add(adOrgId);

		if (adUserId >= 0)
		{
			whereClause.append(" AND ").append(I_AD_Preference.COLUMNNAME_AD_User_ID).append("=?");
			params.add(adUserId);
		}
		else
		{
			whereClause.append(" AND ").append(I_AD_Preference.COLUMNNAME_AD_User_ID).append(" IS NULL");
		}

		if (adWindowId > 0)
		{
			whereClause.append(" AND ").append(I_AD_Preference.COLUMNNAME_AD_Window_ID).append("=?");
			params.add(adWindowId);
		}
		else
		{
			whereClause.append(" AND ").append(I_AD_Preference.COLUMNNAME_AD_Window_ID).append(" IS NULL");
		}

		whereClause.append(" AND ").append(I_AD_Preference.COLUMNNAME_Attribute).append("=?");
		params.add(attribute);

		return new Query(ctx, I_AD_Preference.Table_Name, whereClause.toString(), trxName)
				.setParameters(params)
				.firstOnly(I_AD_Preference.class);

	}

	@Override
	public boolean remove(Properties ctx, String attribute, int adClientId, int adOrgId, int adUserId, int adWindowId)
	{
		logger.info("");

		final I_AD_Preference preference = fetch(ctx, attribute, adClientId, adOrgId, adUserId, adWindowId);
		if (preference == null)
		{
			return false;
		}

		final String contextName = getContextKey(preference);
		InterfaceWrapperHelper.delete(preference);
		Env.setContext(ctx, contextName, (String)null);
		return true;
	}

	@Override
	public void save(Properties ctx, String attribute, Object value, int adClientId, int adOrgId, int adUserId, int adWindowId)
	{
		logger.info("");

		final String trxName = null;

		I_AD_Preference preference = fetch(ctx, attribute, adClientId, adOrgId, adUserId, adWindowId);
		if (preference == null)
		{
			preference = InterfaceWrapperHelper.create(ctx, I_AD_Preference.class, trxName);
			preference.setAttribute(attribute);
			// preference.setAD_Client_ID(adClientId);
			preference.setAD_Org_ID(adOrgId);
			preference.setAD_User_ID(adUserId);
			preference.setAD_Window_ID(adWindowId);
		}

		final String valueStr = coerceToString(value);

		preference.setValue(valueStr);
		InterfaceWrapperHelper.save(preference);
		Env.setContext(ctx, getContextKey(preference), valueStr);
	}

	@Override
	public void save(Properties ctx, String attribute, Object value, int adWindowId)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		final int adUserId = Env.getAD_User_ID(ctx);
		save(ctx, attribute, value, adClientId, adOrgId, adUserId, adWindowId);
	}

	@Override
	public String getValueAsString(final Properties ctx, final String attribute, final int adWindowId)
	{
		final String valueStr = Env.getPreference(ctx, adWindowId, attribute, false); // system=false
		return valueStr;
	}

	@Override
	public <T> T getValue(final Properties ctx, final String attribute, final int adWindowId, final Class<T> clazz)
	{
		final String valueStr = getValueAsString(ctx, attribute, adWindowId);
		if (String.class.equals(clazz))
		{
			@SuppressWarnings("unchecked")
			final T value = (T)valueStr;
			return value;
		}

		if (Integer.class.equals(clazz))
		{
			try
			{
				@SuppressWarnings("unchecked")
				final T value = (T)(Integer)Integer.parseInt(valueStr);
				return value;
			}
			catch (NumberFormatException e)
			{
				@SuppressWarnings("unchecked")
				final T value = (T)Integer.valueOf(0);
				return value;
			}
		}

		if (Boolean.class.equals(clazz))
		{
			@SuppressWarnings("unchecked")
			final T value = (T)Boolean.valueOf("Y".equals(valueStr) || "true".equals(valueStr));
			return value;
		}

		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(clazz);
		if (tableName != null)
		{
			final int recordId;
			try
			{
				recordId = Integer.parseInt(valueStr);
			}
			catch (NumberFormatException e)
			{
				return null;
			}

			final String keyColumnName = tableName + "_ID";
			final T value = new Query(ctx, tableName, keyColumnName + "=?", null)
					.setParameters(recordId)
					.firstOnly(clazz);
			return value;
		}

		return null;
	}

	private String coerceToString(Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof String)
		{
			return (String)value;
		}
		else if (value instanceof Boolean)
		{
			return ((Boolean)value).booleanValue() ? "Y" : "N";
		}
		else
		{
			return value.toString();
		}
	}
}
