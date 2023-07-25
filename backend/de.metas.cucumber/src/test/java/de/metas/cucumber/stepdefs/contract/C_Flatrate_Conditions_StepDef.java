/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.contract;

import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettings;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettings;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.cucumber.stepdefs.contract.commission.hierarchy.C_HierarchyCommissionSettings_StepDefData;
import de.metas.cucumber.stepdefs.contract.commission.licensefee.C_LicenseFeeSettings_StepDefData;
import de.metas.cucumber.stepdefs.contract.commission.margin.C_Customer_Trade_Margin_StepDefData;
import de.metas.cucumber.stepdefs.contract.commission.mediated.C_MediatedCommissionSettings_StepDefData;
import de.metas.cucumber.stepdefs.interiminvoice.settings.C_Interim_Invoice_Settings_StepDefData;
import de.metas.cucumber.stepdefs.pricing.M_PricingSystem_StepDefData;
import de.metas.order.InvoiceRule;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Interim_Invoice_Settings;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static de.metas.contracts.commission.model.I_C_Flatrate_Conditions.COLUMNNAME_C_HierarchyCommissionSettings_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_C_Customer_Trade_Margin_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_C_Flatrate_Conditions_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_C_LicenseFeeSettings_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_C_MediatedCommissionSettings_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_DocStatus;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_InvoiceRule;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_Name;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_OnFlatrateTermExtend;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_Flatrate_Conditions_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_HierarchyCommissionSettings_StepDefData hierarchyCommissionSettingsTable;
	private final C_LicenseFeeSettings_StepDefData licenseFeeSettingsTable;
	private final C_Customer_Trade_Margin_StepDefData customerTradeMarginTable;
	private final C_MediatedCommissionSettings_StepDefData mediatedCommissionSettingsTable;
	private final C_Flatrate_Conditions_StepDefData conditionsTable;
	private final M_PricingSystem_StepDefData pricingSysTable;
	private final C_Interim_Invoice_Settings_StepDefData interimInvoiceSettingsTable;
	private final ModCntr_Settings_StepDefData modCntrSettingsTable;
	private final C_Year_StepDefData yearTable;
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	public C_Flatrate_Conditions_StepDef(
			@NonNull final C_HierarchyCommissionSettings_StepDefData hierarchyCommissionSettingsTable,
			@NonNull final C_LicenseFeeSettings_StepDefData licenseFeeSettingsTable,
			@NonNull final C_Customer_Trade_Margin_StepDefData customerTradeMarginTable,
			@NonNull final C_MediatedCommissionSettings_StepDefData mediatedCommissionSettingsTable,
			@NonNull final C_Flatrate_Conditions_StepDefData conditionsTable,
			@NonNull final M_PricingSystem_StepDefData pricingSysTable,
			@NonNull final C_Interim_Invoice_Settings_StepDefData interimInvoiceSettingsTable,
			@NonNull final ModCntr_Settings_StepDefData modCntrSettingsTable, final C_Year_StepDefData yearTable)
	{
		this.hierarchyCommissionSettingsTable = hierarchyCommissionSettingsTable;
		this.licenseFeeSettingsTable = licenseFeeSettingsTable;
		this.customerTradeMarginTable = customerTradeMarginTable;
		this.mediatedCommissionSettingsTable = mediatedCommissionSettingsTable;
		this.conditionsTable = conditionsTable;
		this.pricingSysTable = pricingSysTable;
		this.interimInvoiceSettingsTable = interimInvoiceSettingsTable;
		this.modCntrSettingsTable = modCntrSettingsTable;
		this.yearTable = yearTable;
	}

	@Given("metasfresh contains C_Flatrate_Conditions:")
	public void metasfresh_contains_c_flatrate_conditions(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String name = tableRow.get(COLUMNNAME_Name);
			assertThat(name).as(COLUMNNAME_Name + " is mandatory").isNotBlank();

			final String type = tableRow.get(COLUMNNAME_Type_Conditions);
			assertThat(type).as(COLUMNNAME_Type_Conditions + " is mandatory").isNotBlank();

			final I_C_Flatrate_Conditions flatrateConditions = CoalesceUtil.coalesceSuppliersNotNull(
					() -> queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class)
							.addEqualsFilter(COLUMNNAME_Name, name)
							.create()
							.firstOnly(I_C_Flatrate_Conditions.class),
					() -> InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class));

			final String commissionHierarchySettingsIdentifier = tableRow.get("OPT." + COLUMNNAME_C_HierarchyCommissionSettings_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(commissionHierarchySettingsIdentifier))
			{
				final I_C_HierarchyCommissionSettings hierarchyCommissionSettings = hierarchyCommissionSettingsTable.get(commissionHierarchySettingsIdentifier);
				assertThat(hierarchyCommissionSettings).as("Missing C_HierarchyCommissionSettings record for identifier " + commissionHierarchySettingsIdentifier).isNotNull();
				InterfaceWrapperHelper
						.create(flatrateConditions, de.metas.contracts.commission.model.I_C_Flatrate_Conditions.class)
						.setC_HierarchyCommissionSettings_ID(hierarchyCommissionSettings.getC_HierarchyCommissionSettings_ID());
			}

			final String commissionLicenseFeeSettingsIdentifier = tableRow.get("OPT." + COLUMNNAME_C_LicenseFeeSettings_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(commissionLicenseFeeSettingsIdentifier))
			{
				final I_C_LicenseFeeSettings licenseFeeSettings = licenseFeeSettingsTable.get(commissionLicenseFeeSettingsIdentifier);
				assertThat(licenseFeeSettings).as("Missing C_LicenseFeeSettings record for identifier " + commissionLicenseFeeSettingsIdentifier).isNotNull();
				InterfaceWrapperHelper
						.create(flatrateConditions, de.metas.contracts.commission.model.I_C_Flatrate_Conditions.class)
						.setC_LicenseFeeSettings_ID(licenseFeeSettings.getC_LicenseFeeSettings_ID());
			}

			final String customerTradeMarginSettingsIdentifier = tableRow.get("OPT." + COLUMNNAME_C_Customer_Trade_Margin_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(customerTradeMarginSettingsIdentifier))
			{
				final I_C_Customer_Trade_Margin customerTradeMargin = customerTradeMarginTable.get(customerTradeMarginSettingsIdentifier);
				assertThat(customerTradeMargin).as("Missing I_C_Customer_Trade_Margin record for identifier " + customerTradeMarginSettingsIdentifier).isNotNull();
				InterfaceWrapperHelper
						.create(flatrateConditions, de.metas.contracts.commission.model.I_C_Flatrate_Conditions.class)
						.setC_Customer_Trade_Margin_ID(customerTradeMargin.getC_Customer_Trade_Margin_ID());
			}

			final String mediatedCommissionSettingsIdentifier = tableRow.get("OPT." + COLUMNNAME_C_MediatedCommissionSettings_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(mediatedCommissionSettingsIdentifier))
			{
				final I_C_MediatedCommissionSettings mediatedCommissionSettings = mediatedCommissionSettingsTable.get(mediatedCommissionSettingsIdentifier);
				assertThat(mediatedCommissionSettings).as("Missing I_C_MediatedCommissionSettings record for identifier " + mediatedCommissionSettingsIdentifier).isNotNull();
				InterfaceWrapperHelper
						.create(flatrateConditions, de.metas.contracts.commission.model.I_C_Flatrate_Conditions.class)
						.setC_MediatedCommissionSettings_ID(mediatedCommissionSettings.getC_MediatedCommissionSettings_ID());
			}

			final String docStatus = tableRow.getOrDefault("OPT." + COLUMNNAME_DocStatus, X_C_Flatrate_Conditions.DOCSTATUS_Completed);

			final InvoiceRule invoiceRule = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_InvoiceRule))
					.map(InvoiceRule::ofCode)
					.orElse(InvoiceRule.AfterDelivery);

			flatrateConditions.setName(name + UUID.randomUUID().toString()); //dev-note: random UUID for constraint "name_unique"
			flatrateConditions.setType_Conditions(type);
			flatrateConditions.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			flatrateConditions.setC_Flatrate_Transition_ID(StepDefConstants.FLATRATE_TRANSITION_ID.getRepoId());
			flatrateConditions.setInvoiceRule(invoiceRule.getCode());
			flatrateConditions.setDocStatus(docStatus);
			flatrateConditions.setProcessed(true);
			flatrateConditions.setIsActive(true);

			final String pricingSystemIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Conditions.COLUMNNAME_M_PricingSystem_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(pricingSystemIdentifier))
			{
				final I_M_PricingSystem pricingSystem = pricingSysTable.get(pricingSystemIdentifier);
				flatrateConditions.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
			}

			final String onFlatrateTermExtend = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Conditions.COLUMNNAME_OnFlatrateTermExtend);
			if (Check.isNotBlank(onFlatrateTermExtend))
			{
				flatrateConditions.setOnFlatrateTermExtend(onFlatrateTermExtend);
			}

			final String interimInvoiceSettingsIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Interim_Invoice_Settings.COLUMNNAME_C_Interim_Invoice_Settings_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(interimInvoiceSettingsIdentifier))
			{
				final I_C_Interim_Invoice_Settings interimInvoiceSettings = interimInvoiceSettingsTable.get(interimInvoiceSettingsIdentifier);
				flatrateConditions.setC_Interim_Invoice_Settings_ID(interimInvoiceSettings.getC_Interim_Invoice_Settings_ID());
			}

			final String modCntrSetttingsIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Conditions.COLUMNNAME_ModCntr_Settings_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(modCntrSetttingsIdentifier))
			{
				final I_ModCntr_Settings modCntrSettingsRecord = modCntrSettingsTable.get(modCntrSetttingsIdentifier);
				flatrateConditions.setModCntr_Settings_ID(modCntrSettingsRecord.getModCntr_Settings_ID());
			}

			InterfaceWrapperHelper.saveRecord(flatrateConditions);

			final String conditionsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);

			conditionsTable.put(conditionsIdentifier, flatrateConditions);
		}
	}

	@When("^clone C_Flatrate_Conditions identified by (.*) for year identified by (.*)$")
	public void clone_c_flatrate_conditions_identified_by_modular_contract_term_for_year_identified_by_y2023(@NonNull final String flatrateConditionsIdentifier, @NonNull final String yearIdentifier)
	{
		final I_C_Flatrate_Conditions flatrateConditions = conditionsTable.get(flatrateConditionsIdentifier);
		final I_C_Year year = yearTable.get(yearIdentifier);

		assertThat(flatrateConditions).isNotNull();
		assertThat(year).isNotNull();

		//
		// calling the BL called in the process -- only method used
		final I_C_Flatrate_Conditions clonedFlatrate_conditions = flatrateBL.cloneConditionsToNewYear(flatrateConditions, year);

		conditionsTable.put("cloned_flatrate_conditions", clonedFlatrate_conditions);
	}

	@Then("validate cloned C_Flatrate_Conditions:")
	public void validate_cloned_C_Flatrate_Conditions(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		final Map<String, String> tableRow = tableRows.get(0);

		//
		// check definition
		assertThat(tableRow.get(COLUMNNAME_Name)).as(COLUMNNAME_Name + " is mandatory").isNotBlank();
		assertThat(tableRow.get(COLUMNNAME_Type_Conditions)).as(COLUMNNAME_Type_Conditions + " is mandatory").isNotBlank();
		assertThat(tableRow.get("OPT." + COLUMNNAME_OnFlatrateTermExtend)).as(COLUMNNAME_OnFlatrateTermExtend + " is mandatory in this context").isNotBlank();
		assertThat(tableRow.get("OPT." + COLUMNNAME_DocStatus)).as(COLUMNNAME_DocStatus + " is mandatory in this context").isNotBlank();

		final I_C_Flatrate_Conditions clonedConditions = conditionsTable.get("cloned_flatrate_conditions");
		final I_C_Year harvestYear = yearTable.get("y2023");

		assertThat(clonedConditions).isNotNull();

		assertThat(clonedConditions.getName()).endsWith("-" + harvestYear.getFiscalYear()); // UUID is added to the name to avoid unique name constraint

		final String type_Conditions = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Type_Conditions);
		assertThat(clonedConditions.getType_Conditions()).isEqualTo(type_Conditions);

		final String onFlatrateTermExtend = DataTableUtil.extractStringForColumnName(tableRow, "OPT." + COLUMNNAME_OnFlatrateTermExtend);
		assertThat(clonedConditions.getOnFlatrateTermExtend()).isEqualTo(onFlatrateTermExtend);

		final String docStatus = DataTableUtil.extractStringForColumnName(tableRow, "OPT." + COLUMNNAME_DocStatus);
		assertThat(clonedConditions.getDocStatus()).isEqualTo(docStatus);

	}

	@And("^validate cloned ModCntr_Settings had harvest year (.*)$")
	public void validate_cloned_ModCntr_Settings(@NonNull final String harvestYearIdentifier)
	{
		final I_C_Flatrate_Conditions clonedConditions = conditionsTable.get("cloned_flatrate_conditions");
		assertThat(clonedConditions).isNotNull();

		final I_C_Year harvestYear = yearTable.get(harvestYearIdentifier);
		assertThat(harvestYear).isNotNull();
		//
		// validate the cloned settings
		final I_ModCntr_Settings clonedSettings = clonedConditions.getModCntr_Settings();
		assertThat(clonedSettings).isNotNull();

		final I_C_Year year = clonedSettings.getC_Year();
		assertThat(year).isNotNull();
		assertThat(year.getC_Calendar_ID()).isEqualTo(harvestYear.getC_Calendar_ID());
		assertThat(year.getC_Year_ID()).isEqualTo(harvestYear.getC_Year_ID());
	}


	@Then("^fail with message (.*) when clonning C_Flatrate_Conditions identified by (.*) for year identified by (.*)$")
	public void failWithMessageWhenClonningC_Flatrate_ConditionsIdentifiedBy_ForYearIdentifiedBy(@NonNull final  String message, String flatrateConditionsIdentifier, String yearIdentifier)
	{
		final I_C_Flatrate_Conditions flatrateConditions = conditionsTable.get(flatrateConditionsIdentifier);
		final I_C_Year year = yearTable.get(yearIdentifier);

		assertThat(flatrateConditions).isNotNull();
		assertThat(year).isNotNull();
		assertThat(message).isNotNull();

		try
		{
			final I_C_Flatrate_Conditions clonedFlatrate_conditions = flatrateBL.cloneConditionsToNewYear(flatrateConditions, year);
		}
		catch (final Exception e)
		{
			assertThat(e).isExactlyInstanceOf(AdempiereException.class);
			assertThat(e.getMessage()).isEqualTo("Einstellungen mit demselben Jahr sind bereits vorhanden"); // dev note : MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS
		}
	}
}
