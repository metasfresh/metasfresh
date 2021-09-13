/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.adempiere.model.validator;

import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_AD_SysConfig;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

@Interceptor(I_AD_SysConfig.class)
public class AD_SysConfig
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_AD_SysConfig record)
	{
		final String configurationLevelEffective = computeEffectiveConfigLevel(
				record.getName(),
				ClientId.ofRepoId(record.getAD_Client_ID()),
				OrgId.ofRepoId(record.getAD_Org_ID()),
				record.getConfigurationLevel());

		record.setConfigurationLevel(configurationLevelEffective);
	}

	private static String computeEffectiveConfigLevel(
			@NonNull final String name,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@Nullable final String currentConfigLevel)
	{
		if (clientId.isRegular() || orgId.isRegular())
		{
			// Get the configuration level from the System Record
			String newConfigLevel = retrieveConfigLevel(name, ClientId.SYSTEM, OrgId.ANY);

			if (newConfigLevel == null)
			{
				// not found for system
				// if saving an org parameter - look config in client
				if (orgId.isRegular())
				{
					newConfigLevel = retrieveConfigLevel(name, clientId, OrgId.ANY);
				}
			}

			if (newConfigLevel != null)
			{
				// Disallow saving org parameter if the system parameter is marked as 'S' or 'C'
				if (orgId.isRegular()
						&& (newConfigLevel.equals(X_AD_SysConfig.CONFIGURATIONLEVEL_System) || newConfigLevel.equals(X_AD_SysConfig.CONFIGURATIONLEVEL_Client)))
				{
					throw new AdempiereException("Can't Save Org Level. This is a system or client parameter, you can't save it as organization parameter");
				}

				// Disallow saving client parameter if the system parameter is marked as 'S'
				if (clientId.isRegular() && newConfigLevel.equals(X_AD_SysConfig.CONFIGURATIONLEVEL_System))
				{
					throw new AdempiereException("Can't Save Client Level. This is a system parameter, you can't save it as client parameter");
				}

				return newConfigLevel;
			}
			else
			{
				// fix possible wrong config level
				if (orgId.isRegular())
				{
					return X_AD_SysConfig.CONFIGURATIONLEVEL_Organization;
				}
				else if (clientId.isRegular() && X_AD_SysConfig.CONFIGURATIONLEVEL_System.equals(currentConfigLevel))
				{
					return X_AD_SysConfig.CONFIGURATIONLEVEL_Client;
				}
			}
		}

		return currentConfigLevel;
	}

	@Nullable
	private static String retrieveConfigLevel(
			@NonNull final String name,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final String sql = "SELECT ConfigurationLevel FROM AD_SysConfig WHERE Name=? AND AD_Client_ID=? AND AD_Org_ID=?";
		final List<Object> sqlParams = Arrays.asList(name, clientId, orgId);
		return DB.getSQLValueStringEx(ITrx.TRXNAME_ThreadInherited, sql, sqlParams);
	}
}
