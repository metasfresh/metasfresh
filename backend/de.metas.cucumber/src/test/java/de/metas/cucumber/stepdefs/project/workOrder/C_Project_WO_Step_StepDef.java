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

package de.metas.cucumber.stepdefs.project.workOrder;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class C_Project_WO_Step_StepDef
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final C_Project_WO_Step_StepDefData projectWOStepTable;

	public C_Project_WO_Step_StepDef(@NonNull final C_Project_WO_Step_StepDefData projectWOStepTable)
	{
		this.projectWOStepTable = projectWOStepTable;
	}

	@And("validate C_Project_WO_Step")
	public void validate_C_Project_WO_Step(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			validateProjectWoStep(tableRow);
		}
	}

	private void validateProjectWoStep(@NonNull final Map<String, String> tableRow)
	{
		final String woStepIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Step.COLUMNNAME_C_Project_WO_Step_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Project_WO_Step woStepRecord = projectWOStepTable.get(woStepIdentifier);
		assertThat(woStepRecord).isNotNull();

		InterfaceWrapperHelper.refresh(woStepRecord);

		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(woStepRecord.getAD_Org_ID()));

		final LocalDate dateStart = DataTableUtil.extractLocalDateOrNullForColumnName(tableRow, "OPT." + I_C_Project_WO_Step.COLUMNNAME_DateStart);
		if (dateStart != null)
		{
			assertThat(TimeUtil.asLocalDate(woStepRecord.getDateStart(), zoneId)).isEqualTo(dateStart);
		}

		final LocalDate dateEnd = DataTableUtil.extractLocalDateOrNullForColumnName(tableRow, "OPT." + I_C_Project_WO_Step.COLUMNNAME_DateEnd);
		if (dateEnd != null)
		{
			assertThat(TimeUtil.asLocalDate(woStepRecord.getDateEnd(), zoneId)).isEqualTo(dateEnd);
		}
	}
}
