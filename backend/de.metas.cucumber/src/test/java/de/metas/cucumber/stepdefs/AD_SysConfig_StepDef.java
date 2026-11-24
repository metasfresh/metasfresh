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
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_AD_User;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
public class AD_SysConfig_StepDef
{
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final AD_User_StepDefData userTable;

	/**
	 * Sysconfigs this scenario overwrote via a "temporarily" step, mapped to their SYSTEM-level value from BEFORE the first overwrite
	 * ({@code null} = there was no SYSTEM-level row). Restored by {@link #restoreTemporarySysConfigsAfterScenario()}.
	 */
	private final Map<String, String> priorValueBySysConfigName = new LinkedHashMap<>();

	@And("^set sys config (String|boolean|int) value (.*) for sys config (.*)$")
	public void enable_sys_config(@NonNull final String sysconfigType, @NonNull final String sysconfigValue, @NonNull final String sysConfigName)
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

	/**
	 * Sets a sys config to a boolean value ({@code true} or {@code false}) for the current scenario; its prior value is restored after the scenario, and a sys config that had no value before is deleted again.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns (none — parameters are in the step text, not a DataTable)
	 * @cucumber.depends (none)
	 * @cucumber.example
	 * <pre>
	 * Given temporarily set sys config boolean value true for sys config 'de.metas.payment.esr.Enabled'
	 * </pre>
	 */
	@And("temporarily set sys config boolean value {word} for sys config {string}")
	public void temporarily_set_sys_config_boolean_value(@NonNull final String valueStr, @NonNull final String sysConfigName)
	{
		if (!"true".equals(valueStr) && !"false".equals(valueStr))
		{
			throw new AdempiereException("Expected true or false but got: " + valueStr);
		}

		rememberPriorValue(sysConfigName);
		enable_sys_config("boolean", valueStr, sysConfigName);
	}

	/**
	 * Remembers the SYSTEM-level value (client 0, org 0) a sysconfig had before this scenario first changed it; {@code containsKey} (not {@code putIfAbsent}),
	 * because {@code null} (no prior value) is a value too.
	 */
	private void rememberPriorValue(@NonNull final String sysConfigName)
	{
		if (!priorValueBySysConfigName.containsKey(sysConfigName))
		{
			priorValueBySysConfigName.put(sysConfigName, sysConfigBL.getValue(sysConfigName, null, ClientId.SYSTEM.getRepoId(), StepDefConstants.ORG_ID_SYSTEM.getRepoId()));
		}
	}

	/**
	 * Restores the sysconfigs in {@link #priorValueBySysConfigName}: an {@code @After} hook, so it also runs when a step failed.
	 * A sysconfig without prior value is deleted again.
	 */
	@After
	public void restoreTemporarySysConfigsAfterScenario()
	{
		if (priorValueBySysConfigName.isEmpty())
		{
			return;
		}

		// every row is attempted, also after one failed, so a single failure does not leave the others changed for later scenarios
		final List<RuntimeException> restoreFailures = new ArrayList<>();
		try
		{
			for (final Map.Entry<String, String> entry : priorValueBySysConfigName.entrySet())
			{
				try
				{
					restoreSystemSysConfig(entry.getKey(), entry.getValue());
				}
				catch (final RuntimeException ex)
				{
					restoreFailures.add(ex);
				}
			}
		}
		finally
		{
			priorValueBySysConfigName.clear();
			CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
		}

		if (!restoreFailures.isEmpty())
		{
			final RuntimeException firstFailure = restoreFailures.get(0);
			restoreFailures.subList(1, restoreFailures.size()).forEach(firstFailure::addSuppressed);
			throw firstFailure;
		}
	}

	/**
	 * Puts the SYSTEM row back to its prior value, or deletes it if there was none.
	 */
	private void restoreSystemSysConfig(@NonNull final String sysConfigName, @Nullable final String priorValue)
	{
		if (priorValue == null)
		{
			queryBL.createQueryBuilder(I_AD_SysConfig.class)
					.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_Name, sysConfigName)
					.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Client_ID, ClientId.SYSTEM)
					.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Org_ID, StepDefConstants.ORG_ID_SYSTEM)
					.create()
					.delete();
		}
		else
		{
			sysConfigBL.setValue(sysConfigName, priorValue, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
		}
	}
}
