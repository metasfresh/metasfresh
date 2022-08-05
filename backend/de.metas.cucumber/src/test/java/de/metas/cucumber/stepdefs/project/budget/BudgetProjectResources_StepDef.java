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
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.product.ResourceId;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.budget.BudgetProjectResourceData;
import de.metas.project.budget.BudgetProjectResources;
import de.metas.resource.ResourceGroupId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.compiere.model.I_C_Project_Resource_Budget;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class BudgetProjectResources_StepDef
{
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

	private final BudgetProjectResources_StepDefData budgetProjectResourcesTable;

	public BudgetProjectResources_StepDef(@NonNull final BudgetProjectResources_StepDefData budgetProjectResourcesTable)
	{
		this.budgetProjectResourcesTable = budgetProjectResourcesTable;
	}

	@Then("validate the created budget project resources for identifier {string}")
	public void validate_budget_project_resources(
			@NonNull final String resourcesIdentifier,
			@NonNull final DataTable table)
	{
		final List<Map<String, String>> tableRows = table.asMaps(String.class, String.class);
		final BudgetProjectResources budgetProjectResources = budgetProjectResourcesTable.get(resourcesIdentifier);

		assertThat(budgetProjectResources.getBudgets().size()).isEqualByComparingTo(tableRows.size());

		for (final Map<String, String> row : tableRows)
		{
			validateBudgetProjectResource(row, budgetProjectResources);
		}
	}

	private void validateBudgetProjectResource(
			@NonNull final Map<String, String> row,
			@NonNull final BudgetProjectResources budgetProjectResources)
	{
		final String externalIdStr = DataTableUtil.extractStringForColumnName(row, I_C_Project_Resource_Budget.COLUMNNAME_ExternalId);
		final ExternalId expectedExternalId = ExternalId.of(externalIdStr);

		final Optional<BudgetProjectResourceData> budgetProjectResourceDataOpt = budgetProjectResources.getBudgets()
				.stream()
				.map(BudgetProjectResource::getBudgetProjectResourceData)
				.filter(projectResourceData -> {
					final ExternalId externalId = projectResourceData.getExternalId();

					return expectedExternalId.equals(externalId);
				})
				.findFirst();

		assertThat(budgetProjectResourceDataOpt).isPresent();

		final BudgetProjectResourceData budgetProjectResourceData = budgetProjectResourceDataOpt.get();

		final Integer uomTimeId = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + I_C_Project_Resource_Budget.COLUMNNAME_C_UOM_Time_ID);
		if (uomTimeId != null)
		{
			assertThat(budgetProjectResourceData.getDurationUomId()).isEqualTo(UomId.ofRepoId(uomTimeId));
		}

		final Timestamp dateStartPlan = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_C_Project_Resource_Budget.COLUMNNAME_DateStartPlan);
		if (dateStartPlan != null)
		{
			assertThat(budgetProjectResourceData.getDateRange().getStartDate()).isEqualTo(dateStartPlan.toInstant());
		}

		final Timestamp dateFinishPlan = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_C_Project_Resource_Budget.COLUMNNAME_DateFinishPlan);
		if (dateFinishPlan != null)
		{
			assertThat(budgetProjectResourceData.getDateRange().getEndDate()).isEqualTo(dateFinishPlan.toInstant());
		}

		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Project_Resource_Budget.COLUMNNAME_Description);
		if (Check.isBlank(description))
		{
			assertThat(budgetProjectResourceData.getDescription()).isEqualTo(description);
		}

		final BigDecimal plannedAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Project_Resource_Budget.COLUMNNAME_PlannedAmt);
		if (plannedAmt != null)
		{
			assertThat(budgetProjectResourceData.getPlannedAmount().toBigDecimal()).isEqualTo(plannedAmt);
		}

		final String currencyCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + "CurrencyCode");
		if (Check.isNotBlank(currencyCode))
		{
			final Currency currency = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyCode));
			assertThat(budgetProjectResourceData.getPlannedAmount().getCurrencyId()).isEqualTo(currency.getId());
		}

		final BigDecimal plannedDuration = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Project_Resource_Budget.COLUMNNAME_PlannedDuration);
		if (plannedDuration != null)
		{
			assertThat(budgetProjectResourceData.getPlannedDuration().toBigDecimal()).isEqualTo(plannedDuration);
		}

		final BigDecimal pricePerTimeUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Project_Resource_Budget.COLUMNNAME_PricePerTimeUOM);
		if (pricePerTimeUOM != null)
		{
			assertThat(budgetProjectResourceData.getPricePerDurationUnit().toBigDecimal()).isEqualTo(pricePerTimeUOM);
		}

		final Integer resourceGroupId = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + I_C_Project_Resource_Budget.COLUMNNAME_S_Resource_Group_ID);
		if (resourceGroupId != null)
		{
			assertThat(budgetProjectResourceData.getResourceGroupId()).isEqualTo(ResourceGroupId.ofRepoId(resourceGroupId));
		}

		final Integer resourceId = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + I_C_Project_Resource_Budget.COLUMNNAME_S_Resource_ID);
		if (resourceId != null)
		{
			assertThat(budgetProjectResourceData.getResourceId()).isEqualTo(ResourceId.ofRepoId(resourceId));
		}

		final String externalId = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Project_Resource_Budget.COLUMNNAME_ExternalId);
		if (Check.isNotBlank(externalId))
		{
			assertThat(budgetProjectResourceData.getExternalId()).isEqualTo(ExternalId.of(externalId));
		}

		final Boolean isActive = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_Project_Resource_Budget.COLUMNNAME_IsActive, true);
		assertThat(budgetProjectResourceData.getIsActive()).isEqualTo(isActive);
	}
}
