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

package de.metas.sysconfig.interceptor;

import de.metas.cache.CacheMgt;
import de.metas.organization.OrgId;
import de.metas.sysconfig.SysConfigListenersRegistry;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_AD_SysConfig;
import org.compiere.util.DB;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

@Interceptor(I_AD_SysConfig.class)
@Component
public class AD_SysConfig
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final SysConfigListenersRegistry listeners;

	public AD_SysConfig(
			@NonNull final SysConfigListenersRegistry listeners)
	{
		this.listeners = listeners;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_AD_SysConfig record)
	{
		listeners.assertValid(record.getName(), record.getValue());

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
			String newConfigLevel = retrieveConfigLevel(name, ClientId.SYSTEM);

			if (newConfigLevel == null)
			{
				// not found for system
				// if saving an org parameter - look config in client
				if (orgId.isRegular())
				{
					newConfigLevel = retrieveConfigLevel(name, clientId);
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
			@NonNull final ClientId clientId)
	{
		final String sql = "SELECT ConfigurationLevel FROM AD_SysConfig WHERE Name=? AND AD_Client_ID=? AND AD_Org_ID=?";
		final List<Object> sqlParams = Arrays.asList(name, clientId, OrgId.ANY);
		return DB.getSQLValueStringEx(ITrx.TRXNAME_ThreadInherited, sql, sqlParams);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_AD_SysConfig record)
	{
		listeners.fireValueChanged(record.getName(), record.getValue());

		// IMPORTANT: we have to reset the SysConfigs cache once again
		// because cache is invalidated while saving, and it's also accessed before the trx which changed a give sysconfig gets committed,
		// so at that time the sysconfigs cache is still stale.
		// To make sure we have fresh sysconfigs, we are resetting the cache again after trx commit.
		final CacheMgt cacheMgt = CacheMgt.get();
		final int recordId = record.getAD_SysConfig_ID();
		trxManager.runAfterCommit(() -> cacheMgt.reset(I_AD_SysConfig.Table_Name, recordId));
	}

}
