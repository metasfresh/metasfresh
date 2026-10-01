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
import de.metas.organization.ClientAndOrgId;
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
import org.adempiere.service.ISysConfigDAO;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
public class AD_SysConfig_StepDef
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final ISysConfigDAO sysConfigDAO = Services.get(ISysConfigDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final AD_User_StepDefData userTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;

	/** Populated only by {@link #temporarily_set_sys_config}; drained and restored by {@link #restoreTemporarilySetSysConfigs}. */
	private final Map<String, Optional<String>> temporarilySetSysConfigPriorValues = new LinkedHashMap<>();

	/**
	 * Sets a SYSTEM-level AD_SysConfig to the given value — permanently, for the rest of the scenario
	 * (and, if the scenario doesn't restore it itself, for whatever runs after it on the same executor).
	 * Prefer {@link #temporarily_set_sys_config} instead whenever the override must not outlive this
	 * scenario, per the self-contained-global-state rule (de.metas.cucumber/CLAUDE.md rules 12/13).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Given set sys config boolean value true for sys config de.metas.pos.Return.SomeFlag
	 * </pre>
	 */
	@And("^set sys config (String|boolean|int) value (.*) for sys config (.*)$")
	public void enable_sys_config(@NonNull final String sysconfigType, @NonNull final String sysconfigValue, @NonNull final String sysConfigName)
	{
		applySysConfigValue(sysconfigType, sysconfigValue, sysConfigName);
	}

	/**
	 * Same as {@link #enable_sys_config}, but captures the value the given SYSTEM-level AD_SysConfig had
	 * BEFORE this call (present or absent) and restores exactly that — via {@link #restoreTemporarilySetSysConfigs},
	 * a real {@code @After} hook, so the restore runs unconditionally at scenario end even if the scenario
	 * itself fails — never a plain Gherkin step, which Cucumber skips once an earlier step in the same
	 * scenario has already failed. Use this instead of {@code set sys config ...} whenever a scenario
	 * needs a SHORT-LIVED override of a value other scenarios/features on the same executor also read,
	 * per the self-contained-global-state rule (de.metas.cucumber/CLAUDE.md rules 12/13).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Given temporarily set sys config int value 2000 for sys config de.metas.pos.Return.LockTimeoutMillis
	 * </pre>
	 */
	@And("^temporarily set sys config (String|boolean|int) value (.*) for sys config (.*)$")
	public void temporarily_set_sys_config(@NonNull final String sysconfigType, @NonNull final String sysconfigValue, @NonNull final String sysConfigName)
	{
		temporarilySetSysConfigPriorValues.computeIfAbsent(sysConfigName, name -> sysConfigDAO.getValue(name, ClientAndOrgId.SYSTEM));
		applySysConfigValue(sysconfigType, sysconfigValue, sysConfigName);
	}

	/**
	 * Restores every AD_SysConfig temporarily overridden via {@link #temporarily_set_sys_config} back to its
	 * value from immediately before this scenario touched it — the prior value if one existed, or deletes the
	 * row entirely if it didn't — so a scenario's short-lived override never leaks into a later scenario/feature
	 * on the same executor, even when THIS scenario itself fails partway through (an ordinary Gherkin restore
	 * step would simply never run in that case). Mirrors {@code M_ShipmentSchedule_StepDef#deleteSeededRecomputeSchedules}.
	 */
	@After
	public void restoreTemporarilySetSysConfigs()
	{
		if (temporarilySetSysConfigPriorValues.isEmpty())
		{
			return;
		}

		temporarilySetSysConfigPriorValues.forEach((name, priorValue) -> {
			if (priorValue.isPresent())
			{
				sysConfigBL.setValue(name, priorValue.get(), ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
			}
			else
			{
				queryBL.createQueryBuilder(I_AD_SysConfig.class)
						.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_Name, name)
						.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Client_ID, ClientId.SYSTEM.getRepoId())
						.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Org_ID, StepDefConstants.ORG_ID_SYSTEM.getRepoId())
						.create()
						.deleteDirectly();
			}
		});
		temporarilySetSysConfigPriorValues.clear();

		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
	}

	private void applySysConfigValue(@NonNull final String sysconfigType, @NonNull final String sysconfigValue, @NonNull final String sysConfigName)
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

	/**
	 * Sets an AD_SysConfig value to a C_BPartner's repo id — for sysconfig keys that resolve a business
	 * partner (e.g. the customer-return REST path's "unknown customer" fallback).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Name</b> — (required) the AD_SysConfig name<br>
	 *   <b>C_BPartner_ID</b> — (required, identifier-ref) business partner whose repo id is stored<br>
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update AD_SysConfig with C_BPartner_ID:
	 *   | Name                                      | C_BPartner_ID |
	 *   | sysconfig.customerReturn.unknownBpartner  | bpartner_1    |
	 * </pre>
	 */
	@And("update AD_SysConfig with C_BPartner_ID:")
	public void set_sysConfig_bpartner(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String name = row.getAsString(I_AD_SysConfig.COLUMNNAME_Name);
			final I_C_BPartner bpartner = row.getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID).lookupNotNullIn(bpartnerTable);

			setSysConfigIntValue(name, bpartner.getC_BPartner_ID());
		});

		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
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
