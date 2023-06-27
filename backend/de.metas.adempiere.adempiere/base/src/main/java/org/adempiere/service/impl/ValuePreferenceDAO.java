package org.adempiere.service.impl;

import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.IValuePreferenceDAO;
import org.compiere.model.I_AD_Preference;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.Properties;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ValuePreferenceDAO implements IValuePreferenceDAO
{
	private final transient Logger logger = LogManager.getLogger(getClass());
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public boolean remove(final Properties ctx, final String attribute, final int adClientId, final int adOrgId, final int adUserId, final int adWindowId)
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
	public void save(final Properties ctx, final String attribute, final Object value, final int adClientId, final int adOrgId, final int adUserId, final int adWindowId)
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

	private I_AD_Preference fetch(final Properties ctx, final String attribute, final int adClientId, final int adOrgId, final int adUserId, final int adWindowId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Preference.class, PlainContextAware.newOutOfTrx(ctx))
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Preference.COLUMNNAME_AD_Client_ID, adClientId)
				.addEqualsFilter(I_AD_Preference.COLUMNNAME_AD_Org_ID, adOrgId)
				.addEqualsFilter(I_AD_Preference.COLUMNNAME_AD_User_ID, adUserId >= 0 ? adUserId : null)
				.addEqualsFilter(I_AD_Preference.COLUMNNAME_AD_Window_ID, adWindowId >= 0 ? adWindowId : null)
				.addEqualsFilter(I_AD_Preference.COLUMNNAME_Attribute, attribute)
				.create().firstOnly(I_AD_Preference.class);
	}

	public String getContextKey(final I_AD_Preference preference)
	{
		final String attribute = preference.getAttribute();
		Check.assume(!Check.isEmpty(attribute), "Attribute not empty for " + preference);

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

	private String coerceToString(final Object value)
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
			return DisplayType.toBooleanString((Boolean)value);
		}
		else
		{
			return value.toString();
		}
	}

	@Override
	public void deleteUserPreferenceByUserId(final UserId userId)
	{
		queryBL.createQueryBuilder(I_AD_Preference.class)
				.addEqualsFilter(I_AD_Preference.COLUMNNAME_AD_User_ID, userId)
				.create()
				.delete();
	}
}
