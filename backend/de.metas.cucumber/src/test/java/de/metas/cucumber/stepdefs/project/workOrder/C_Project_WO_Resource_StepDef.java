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

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class C_Project_WO_Resource_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final C_Project_WO_Step_StepDefData projectWOStepTable;
	private final C_Project_WO_Resource_StepDefData projectWOResourceTable;

	public C_Project_WO_Resource_StepDef(
			@NonNull final C_Project_WO_Step_StepDefData projectWOStepTable,
			@NonNull final C_Project_WO_Resource_StepDefData projectWOResourceTable)
	{
		this.projectWOStepTable = projectWOStepTable;
		this.projectWOResourceTable = projectWOResourceTable;
	}

	@And("load C_Project_WO_Resources")
	public void load_C_Project_WO_Resources(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			loadProjectResources(tableRow);
		}
	}

	@And("validate C_Project_WO_Resource")
	public void validate_C_Project_WO_Resource(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			validateProjectWoResource(tableRow);
		}
	}

	private void validateProjectWoResource(@NonNull final Map<String, String> tableRow)
	{
		final String resourceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Project_WO_Resource projectWoResourceRecord = projectWOResourceTable.get(resourceIdentifier);
		assertThat(projectWoResourceRecord).isNotNull();

		InterfaceWrapperHelper.refresh(projectWoResourceRecord);
		
		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(projectWoResourceRecord.getAD_Org_ID()));
		
		final LocalDate assignDateFrom = DataTableUtil.extractLocalDateOrNullForColumnName(tableRow, "OPT." + I_C_Project_WO_Resource.COLUMNNAME_AssignDateFrom);
		if (assignDateFrom != null)
		{
			assertThat(TimeUtil.asLocalDate(projectWoResourceRecord.getAssignDateFrom(), zoneId)).isEqualTo(assignDateFrom);
		}

		final LocalDate assignDateTo = DataTableUtil.extractLocalDateOrNullForColumnName(tableRow, "OPT." + I_C_Project_WO_Resource.COLUMNNAME_AssignDateTo);
		if (assignDateTo != null)
		{
			assertThat(TimeUtil.asLocalDate(projectWoResourceRecord.getAssignDateTo(), zoneId)).isEqualTo(assignDateTo);
		}
	}

	private void loadProjectResources(@NonNull final Map<String, String> tableRow)
	{
		final String woProjectStepIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Step_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Project_WO_Step woProjectStepRecord = projectWOStepTable.get(woProjectStepIdentifier);
		assertThat(woProjectStepRecord).isNotNull();

		final String resourceIdentifiers = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(resourceIdentifiers))
		{
			final ImmutableList<String> resourceIdentifierList = StepDefUtil.extractIdentifiers(resourceIdentifiers);
			final ImmutableList<I_C_Project_WO_Resource> resourceRecords = queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Step_ID, woProjectStepRecord.getC_Project_WO_Step_ID())
					.orderBy(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID)
					.create()
					.listImmutable(I_C_Project_WO_Resource.class);

			assertThat(resourceIdentifierList.size()).isEqualTo(resourceRecords.size());

			for (int index = 0; index < resourceIdentifierList.size(); index++)
			{
				projectWOResourceTable.putOrReplace(resourceIdentifierList.get(index), resourceRecords.get(index));
			}
		}
	}
}
