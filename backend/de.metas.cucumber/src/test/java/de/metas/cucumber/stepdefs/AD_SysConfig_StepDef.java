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
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_M_Product_Category;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AD_SysConfig_StepDef
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final AD_User_StepDefData userTable;
	private final M_Product_Category_StepDefData productCategoryTable;

	/**
	 * Prior values ({@code null} = none) of the sysconfigs this scenario changed temporarily; restored after the scenario.
	 */
	private final Map<String, String> priorValueBySysConfigName = new LinkedHashMap<>();

	public AD_SysConfig_StepDef(@NonNull final AD_User_StepDefData userTable, @NonNull final M_Product_Category_StepDefData productCategoryTable)
	{
		this.userTable = userTable;
		this.productCategoryTable = productCategoryTable;
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
	 * Sets a sys config to a boolean value for the current scenario; its prior value is restored after the scenario (a sys config that had no row before keeps the new value).
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
		rememberPriorValue(sysConfigName);

		final boolean booleanValue = Boolean.parseBoolean(valueStr);
		sysConfigBL.setValue(sysConfigName, booleanValue, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);

		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
	}

	/**
	 * Sets a sys config to the repo id of a scenario-created {@code M_Product_Category}; its prior value is restored after the scenario (a sys config that had no row before keeps the new value).
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
			final I_M_Product_Category productCategory = row.getAsIdentifier(I_M_Product_Category.COLUMNNAME_M_Product_Category_ID)
					.lookupNotNullIn(productCategoryTable);

			rememberPriorValue(sysConfigName);
			setSysConfigIntValue(sysConfigName, productCategory.getM_Product_Category_ID());

			CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
		});
	}

	/**
	 * Remembers the value a sysconfig had before this scenario first changed it; {@code containsKey}, because {@code null} (no prior value) is a value too.
	 */
	private void rememberPriorValue(@NonNull final String sysConfigName)
	{
		if (!priorValueBySysConfigName.containsKey(sysConfigName))
		{
			priorValueBySysConfigName.put(sysConfigName, sysConfigBL.getValue(sysConfigName, (String)null));
		}
	}

	/**
	 * An {@code @After} hook so the restore also runs when a step failed. A sysconfig without prior value is left as set, since there is no delete.
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
				continue;
			}

			sysConfigBL.setValue(sysConfigName, priorValue, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
		}

		priorValueBySysConfigName.clear();
		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
	}
}
