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
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.product.ProductCategoryId;
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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product_Category;
import org.springframework.context.ApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
public class AD_SysConfig_StepDef
{
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final AD_User_StepDefData userTable;
	@NonNull private final M_Product_Category_StepDefData productCategoryTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;

	/**
	 * Sysconfigs this scenario overwrote via one of the "temporarily" steps or {@link #point_sysconfig_at_own_servlet_url}, mapped to
	 * their SYSTEM-level value from BEFORE the first overwrite (possibly {@code null}, meaning there was no such sysconfig).
	 * Restored by {@link #restoreTemporarySysConfigsAfterScenario()}.
	 */
	private final Map<String, String> priorValueBySysConfigName = new LinkedHashMap<>();

	/**
	 * Sets a SYSTEM-level AD_SysConfig to the given value — permanently, for the rest of the scenario
	 * (and, if the scenario doesn't restore it itself, for whatever runs after it on the same executor).
	 * Prefer {@link #temporarily_set_sys_config_boolean_value} instead whenever the override must not outlive this
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

		resetSysConfigCache();
	}

	/**
	 * Points a sysconfig holding a self-referencing servlet URL (e.g. the barcode servlet a Jasper report
	 * embeds as a live image) at THIS cucumber JVM's own embedded Tomcat, instead of whatever fixed value the
	 * scrambled test DB happens to carry (a stale port from wherever that dump's data originated).
	 * <p>
	 * The embedded server binds an ephemeral port per run ({@code CucumberLifeCycleSupport} starts
	 * {@code ServerBoot} inline), so the URL cannot be a literal Gherkin value -- it is read from the
	 * already-bound Spring {@code Environment} property {@code local.server.port}, which Spring Boot's
	 * embedded servlet container sets once the port is actually bound.
	 * <p>
	 * The prior value is captured before the overwrite and restored by
	 * {@link #restoreTemporarySysConfigsAfterScenario()} (or the sysconfig is deleted again, if there was none), so this run's
	 * now-dead ephemeral port does not become the NEXT run's version of the exact "stale port" condition this step exists to
	 * compensate for.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And set sys config 'de.metas.adempiere.report.barcode.BarcodeServlet' to this instance's own URL at '/adempiereJasper/BarcodeServlet'
	 * </pre>
	 */
	@And("set sys config {string} to this instance's own URL at {string}")
	public void point_sysconfig_at_own_servlet_url(@NonNull final String sysConfigName, @NonNull final String servletPath)
	{
		final ApplicationContext applicationContext = SpringContextHolder.instance.getApplicationContext();
		assertThat(applicationContext).as("Spring application context").isNotNull();

		final String localServerPort = applicationContext.getEnvironment().getProperty("local.server.port");
		assertThat(localServerPort).as("local.server.port (this instance's own embedded Tomcat port)").isNotBlank();

		rememberPriorValue(sysConfigName);

		final String ownServletUrl = "http://localhost:" + localServerPort + servletPath;
		sysConfigBL.setValue(sysConfigName, ownServletUrl, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);

		resetSysConfigCache();
	}

	/**
	 * Sets a sys config to an int value for the current scenario; its prior value is restored after the scenario, and a sys config that
	 * had no value before is deleted again.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Given temporarily set sys config int value 3 for sys config 'de.metas.fresh.ordercheckup_barcode.Copies'
	 * </pre>
	 */
	@And("temporarily set sys config int value {int} for sys config {string}")
	public void temporarily_set_sys_config_int_value(final int value, @NonNull final String sysConfigName)
	{
		rememberPriorValue(sysConfigName);
		setSysConfigIntValue(sysConfigName, value);
		resetSysConfigCache();
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

	/**
	 * Sets a sys config to a boolean value ({@code true} or {@code false}) for the current scenario; its prior value is restored after the scenario, and a sys config that had no value before is deleted again.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns (none — parameters are in the step text, not a DataTable)
	 * @cucumber.depends (none)
	 * @cucumber.example
	 * <pre>
	 * Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
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
	 * Sets a sys config to the repo id of a scenario-created {@code M_Product_Category}, for the current scenario; its prior value is restored after the scenario, and a sys config that had no value before is deleted again.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Name</b> — (required) the AD_SysConfig name to point<br>
	 *   <b>M_Product_Category_ID</b> — (required, identifier-ref) the product category whose repo id becomes the sysconfig's value<br>
	 * @cucumber.depends StepDefData: M_Product_Category_StepDefData
	 * @cucumber.example <pre>
	 * Given temporarily set AD_SysConfig to M_Product_Category_ID:
	 *   | Name                             | M_Product_Category_ID |
	 *   | PackingMaterialProductCategoryID | pm_category           |
	 * </pre>
	 */
	@And("temporarily set AD_SysConfig to M_Product_Category_ID:")
	public void temporarily_set_sysConfig_to_product_category(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String sysConfigName = row.getAsString(I_AD_SysConfig.COLUMNNAME_Name);
			final ProductCategoryId productCategoryId = row.getAsIdentifier(I_M_Product_Category.COLUMNNAME_M_Product_Category_ID)
					.lookupNotNullIdIn(productCategoryTable);

			rememberPriorValue(sysConfigName);
			setSysConfigIntValue(sysConfigName, productCategoryId.getRepoId());

			resetSysConfigCache();
		});
	}

	/**
	 * Setting a value also fires a cache invalidation event, but that event may not be processed before the next step runs.
	 */
	private static void resetSysConfigCache()
	{
		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
	}

	private void deleteSystemSysConfig(@NonNull final String sysConfigName)
	{
		queryBL.createQueryBuilder(I_AD_SysConfig.class)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_Name, sysConfigName)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Client_ID, ClientId.SYSTEM)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Org_ID, StepDefConstants.ORG_ID_SYSTEM)
				.create()
				.delete();
	}

	/**
	 * The temporary steps set and restore sysconfigs on SYSTEM level only (client 0, org 0).
	 * <p>
	 * Remembers the SYSTEM-level value a sysconfig had before this scenario first changed it; {@code containsKey} (not {@code putIfAbsent}),
	 * because {@code null} (no prior value) is a value too.
	 */
	private void rememberPriorValue(@NonNull final String sysConfigName)
	{
		if (!priorValueBySysConfigName.containsKey(sysConfigName))
		{
			priorValueBySysConfigName.put(sysConfigName, sysConfigBL.getValue(sysConfigName, (String)null));
		}
	}

	/**
	 * The only restore of the sysconfigs this scenario changed temporarily: an {@code @After} hook, so it also runs when a step failed.
	 * A sysconfig without prior value is deleted again.
	 */
	@After
	public void restoreTemporarySysConfigsAfterScenario()
	{
		if (priorValueBySysConfigName.isEmpty())
		{
			return;
		}

		for (final Map.Entry<String, String> entry : priorValueBySysConfigName.entrySet())
		{
			final String sysConfigName = entry.getKey();
			final String priorValue = entry.getValue();
			if (priorValue == null)
			{
				deleteSystemSysConfig(sysConfigName);
			}
			else
			{
				sysConfigBL.setValue(sysConfigName, priorValue, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
			}
		}

		priorValueBySysConfigName.clear();
		resetSysConfigCache();
	}
}
