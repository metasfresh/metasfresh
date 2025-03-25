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

package de.metas.cucumber.stepdefs.activity;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Activity;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class C_Activity_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Activity_StepDefData activityTable;

	public C_Activity_StepDef(@NonNull final C_Activity_StepDefData activityTable)
	{
		this.activityTable = activityTable;
	}

	@And("metasfresh contains C_Activity:")
	public void add_C_Activity(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String value = DataTableUtil.extractStringForColumnName(row, I_C_Activity.COLUMNNAME_Value);

			final I_C_Activity activity = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_C_Activity.class)
							.addOnlyActiveRecordsFilter()
							.addEqualsFilter(I_C_Activity.COLUMNNAME_Value, value)
							.create()
							.firstOnlyOrNull(I_C_Activity.class),
					() -> InterfaceWrapperHelper.newInstance(I_C_Activity.class));

			assertThat(activity).isNotNull();

			final String name = DataTableUtil.extractStringForColumnName(row, I_C_Activity.COLUMNNAME_Name);

			activity.setName(name);
			activity.setValue(value);

			InterfaceWrapperHelper.saveRecord(activity);

			final String activityIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Activity.COLUMNNAME_C_Activity_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			activityTable.putOrReplace(activityIdentifier, activity);
		}
	}

	@And("validate C_Activity:$")
	public void retrieve_C_Activity(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String value = DataTableUtil.extractStringForColumnName(row, I_C_Activity.COLUMNNAME_Value);
			final String name = DataTableUtil.extractStringForColumnName(row, I_C_Activity.COLUMNNAME_Name);

			final String activityIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Activity.COLUMNNAME_C_Activity_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_Activity activity = activityTable.get(activityIdentifier);

			assertThat(activity.getName()).isEqualTo(name);
			assertThat(activity.getValue()).isEqualTo(value);
		}
	}
}
