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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_AD_User;
import org.springframework.context.ApplicationContext;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AD_SysConfig_StepDef
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final AD_User_StepDefData userTable;

	/**
	 * Sysconfigs this scenario overwrote via {@link #point_sysconfig_at_own_servlet_url} or
	 * {@link #temporarily_set_sys_config_int_value}, mapped to their value from BEFORE the overwrite
	 * (possibly {@code null}, meaning the sysconfig had none). Restored by
	 * {@link #restoreRepointedSysConfigsAfterScenario()}.
	 */
	private final Map<String, String> priorValueBySysConfigName = new LinkedHashMap<>();

	public AD_SysConfig_StepDef(@NonNull final AD_User_StepDefData userTable)
	{
		this.userTable = userTable;
	}

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
	 * {@link #restoreRepointedSysConfigsAfterScenario()}, so this run's now-dead ephemeral port does not
	 * become the NEXT run's version of the exact "stale port" condition this step exists to compensate for.
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

		// captured once per sysconfig name per scenario: a second call in the same scenario (unlikely, but
		// not forbidden) must not overwrite the ALREADY-captured original with this scenario's own first write
		priorValueBySysConfigName.putIfAbsent(sysConfigName, sysConfigBL.getValue(sysConfigName, (String)null));

		final String ownServletUrl = "http://localhost:" + localServerPort + servletPath;
		sysConfigBL.setValue(sysConfigName, ownServletUrl, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);

		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
	}

	/**
	 * Sets a sys config to a scenario-local int value, capturing its PRIOR value (via
	 * {@link #priorValueBySysConfigName}, {@code putIfAbsent} so a second write in the same scenario never
	 * overwrites the already-captured original) so {@link #restoreRepointedSysConfigsAfterScenario()} restores
	 * it, never leaving a changed value in shared/global {@code AD_SysConfig}.
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
		priorValueBySysConfigName.putIfAbsent(sysConfigName, sysConfigBL.getValue(sysConfigName, (String)null));

		setSysConfigIntValue(sysConfigName, value);
	}

	/**
	 * Guaranteed-execution cleanup for {@link #point_sysconfig_at_own_servlet_url} and
	 * {@link #temporarily_set_sys_config_int_value} -- an {@code @After} hook rather than a trailing Gherkin
	 * step, since Cucumber skips remaining steps once one fails, i.e. on exactly the runs that need the
	 * restore. A no-op for every scenario that never called either step.
	 * <p>
	 * If the sysconfig had no prior value (a fresh key, {@code null}), there is nothing to restore it TO --
	 * {@link ISysConfigBL} exposes no delete, so this scenario's own written value is left in place. That
	 * matches every other sysconfig write in this class (none of which restore either) and does not create a
	 * new failure mode: the next run still overwrites it with ITS OWN ephemeral port before reading it.
	 */
	@After
	public void restoreRepointedSysConfigsAfterScenario()
	{
		if (priorValueBySysConfigName.isEmpty())
		{
			return;
		}

		for (final Map.Entry<String, String> entry : priorValueBySysConfigName.entrySet())
		{
			final String sysConfigName = entry.getKey();
			@Nullable final String priorValue = entry.getValue();
			if (priorValue == null)
			{
				continue;
			}

			sysConfigBL.setValue(sysConfigName, priorValue, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
		}

		priorValueBySysConfigName.clear();
		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
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
