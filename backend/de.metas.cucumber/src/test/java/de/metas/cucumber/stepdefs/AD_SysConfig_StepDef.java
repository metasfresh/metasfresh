/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.cache.CacheMgt;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_AD_User;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AD_SysConfig_StepDef
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_User_StepDefData userTable;

	public AD_SysConfig_StepDef(@NonNull final AD_User_StepDefData userTable)
	{
		this.userTable = userTable;
	}

	/**
	 * Sys configs set with {@link #enable_sys_config_for_this_scenario_only}, mapped to the state of their AD_SysConfig row before.
	 * The first state seen per name wins, so that it is the one that gets restored.
	 */
	private final Map<String, SysConfigRowState> sysConfigRowsToRestore = new LinkedHashMap<>();

	@Value
	private static class SysConfigRowState
	{
		boolean existed;
		@Nullable String value;
	}

	@And("^set sys config (String|boolean|int) value (.*) for sys config (.*)$")
	public void enable_sys_config(@NonNull final String sysconfigType, @NonNull final String sysconfigValue, @NonNull final String sysConfigName)
	{
		setSysConfig(sysconfigType, sysconfigValue, sysConfigName);
	}

	/**
	 * For a sys config that is global state shared by every scenario on the executor: the previous value is put back by the {@code @After} hook below, also if the scenario or its Background fails.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And for this scenario only, set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
	 * </pre>
	 */
	@And("^for this scenario only, set sys config (String|boolean|int) value (.*) for sys config (.*)$")
	public void enable_sys_config_for_this_scenario_only(@NonNull final String sysconfigType, @NonNull final String sysconfigValue, @NonNull final String sysConfigName)
	{
		sysConfigRowsToRestore.computeIfAbsent(sysConfigName, this::retrieveRowState);
		setSysConfig(sysconfigType, sysconfigValue, sysConfigName);
	}

	/**
	 * Fires for every scenario, regardless of pass/fail; no-op when nothing was set with {@link #enable_sys_config_for_this_scenario_only}.
	 * A row that did not exist before is deleted; an existing row gets its previous value back as it was.
	 * Keeps restoring the remaining sys configs if one restore fails, and rethrows the first failure at the end.
	 */
	@After
	public void restoreSysConfigs()
	{
		if (sysConfigRowsToRestore.isEmpty())
		{
			return;
		}

		RuntimeException failure = null;
		for (final Map.Entry<String, SysConfigRowState> entry : sysConfigRowsToRestore.entrySet())
		{
			try
			{
				restoreRow(entry.getKey(), entry.getValue());
			}
			catch (final RuntimeException e)
			{
				failure = failure != null ? failure : e;
			}
		}
		sysConfigRowsToRestore.clear();

		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
		CacheMgt.get().reset(); // also drops caches derived from the sys config, e.g. the report server

		if (failure != null)
		{
			throw failure;
		}
	}

	private IQuery<I_AD_SysConfig> systemRowQuery(@NonNull final String sysConfigName)
	{
		return queryBL.createQueryBuilder(I_AD_SysConfig.class)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_Name, sysConfigName)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Client_ID, ClientId.SYSTEM.getRepoId())
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Org_ID, StepDefConstants.ORG_ID_SYSTEM.getRepoId())
				.create();
	}

	private SysConfigRowState retrieveRowState(@NonNull final String sysConfigName)
	{
		final I_AD_SysConfig row = systemRowQuery(sysConfigName).firstOnly(I_AD_SysConfig.class);
		return row != null ? new SysConfigRowState(true, row.getValue()) : new SysConfigRowState(false, null);
	}

	private void restoreRow(@NonNull final String sysConfigName, @NonNull final SysConfigRowState state)
	{
		final IQuery<I_AD_SysConfig> query = systemRowQuery(sysConfigName);
		if (!state.isExisted())
		{
			query.delete();
			return;
		}

		final I_AD_SysConfig row = query.firstOnly(I_AD_SysConfig.class);
		if (row == null) // someone deleted it meanwhile
		{
			throw new AdempiereException("AD_SysConfig row not found, cannot restore it").setParameter("name", sysConfigName);
		}
		row.setValue(state.getValue());
		InterfaceWrapperHelper.save(row);
	}

	private void setSysConfig(@NonNull final String sysconfigType, @NonNull final String sysconfigValue, @NonNull final String sysConfigName)
	{
		switch (sysconfigType)
		{
			case "String":
				final String value = DataTableUtil.nullToken2Null(sysconfigValue);
				sysConfigBL.setValue(sysConfigName, value, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
				break;
			case "boolean":
				final boolean booleanValue = Boolean.parseBoolean(sysconfigValue);
				sysConfigBL.setValue(sysConfigName, booleanValue, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
				break;
			case "int":
				final int intValue = Integer.parseInt(sysconfigValue);
				setSysConfigIntValue(sysConfigName, intValue);
				break;
			default:
				throw new AdempiereException("Unhandled sysConfig type")
						.appendParametersToMessage()
						.setParameter("type:", sysconfigType);
		}

		CacheMgt.get().reset(I_AD_SysConfig.Table_Name); // also without this, we fire a CacheInvalidation event, but that event may not be processed in time
	}

	@And("update AD_SysConfig with login AD_User_ID")
	public void set_sysConfig_login_user(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String name = DataTableUtil.extractStringForColumnName(row, I_AD_SysConfig.COLUMNNAME_Name);

			final String userIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_User.COLUMNNAME_AD_User_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_AD_User user = userTable.get(userIdentifier);
			assertThat(user).isNotNull();

			setSysConfigIntValue(name, user.getAD_User_ID());
		}
	}

	@And("reset all cache")
	public void reset_cache()
	{
		CacheMgt.get().reset();
	}

	private void setSysConfigIntValue(@NonNull final String name, final int value)
	{
		sysConfigBL.setValue(name, value, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
	}
}
