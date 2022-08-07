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

package de.metas.cucumber.stepdefs.project.budget;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.project.ProjectTypeId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Project;
import org.compiere.util.Env;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class BudgetProject_StepDef
{
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final BudgetProject_StepDefData budgetProjectTable;

	public BudgetProject_StepDef(@NonNull final BudgetProject_StepDefData budgetProjectTable)
	{
		this.budgetProjectTable = budgetProjectTable;
	}

	@Then("validate the created budget project")
	public void validate_budget_project(@NonNull final DataTable table)
	{
		final List<Map<String, String>> tableRows = table.asMaps(String.class, String.class);

		for (final Map<String, String> row : tableRows)
		{
			validateBudgetProject(row);
		}
	}

	private void validateBudgetProject(@NonNull final Map<String, String> row)
	{
		final String budgetIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Project.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final BudgetProject budgetProject = budgetProjectTable.get(budgetIdentifier);
		final BudgetProjectData budgetProjectData = budgetProject.getBudgetProjectData();

		final String name = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Project.COLUMNNAME_Name);
		if (Check.isNotBlank(name))
		{
			assertThat(budgetProjectData.getName()).isEqualTo(name);
		}

		final String value = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Project.COLUMNNAME_Value);
		if (Check.isNotBlank(value))
		{
			assertThat(budgetProjectData.getValue()).isEqualTo(value);
		}

		final String currencyCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + "CurrencyCode");
		if (Check.isNotBlank(currencyCode))
		{
			final Currency currency = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyCode));
			assertThat(budgetProjectData.getCurrencyId()).isEqualTo(currency.getId());
		}

		final Boolean isActive = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_Project.COLUMNNAME_IsActive, true);
		assertThat(budgetProjectData.isActive()).isEqualTo(isActive);

		final String orgCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + "OrgCode");
		if (Check.isNotBlank(orgCode))
		{
			final I_AD_Org orgRecord = orgDAO.retrieveOrganizationByValue(Env.getCtx(), orgCode);

			final OrgId orgId = OrgId.ofRepoId(orgRecord.getAD_Org_ID());
			assertThat(budgetProjectData.getOrgId()).isEqualTo(orgId);
		}

		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Project.COLUMNNAME_Description);
		if (Check.isNotBlank(description))
		{
			assertThat(budgetProjectData.getDescription()).isEqualTo(description);
		}

		final Integer projectTypeId = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + I_C_Project.COLUMNNAME_C_ProjectType_ID);
		if (projectTypeId != null)
		{
			assertThat(budgetProjectData.getProjectTypeId()).isEqualTo(ProjectTypeId.ofRepoId(projectTypeId));
		}
	}
}
