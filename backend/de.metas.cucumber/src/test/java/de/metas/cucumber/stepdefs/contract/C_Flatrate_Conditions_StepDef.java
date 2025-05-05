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

import de.metas.calendar.standard.YearId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettings;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettings;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.cucumber.stepdefs.contract.commission.hierarchy.C_HierarchyCommissionSettings_StepDefData;
import de.metas.cucumber.stepdefs.contract.commission.licensefee.C_LicenseFeeSettings_StepDefData;
import de.metas.cucumber.stepdefs.contract.commission.margin.C_Customer_Trade_Margin_StepDefData;
import de.metas.cucumber.stepdefs.contract.commission.mediated.C_MediatedCommissionSettings_StepDefData;
import de.metas.cucumber.stepdefs.message.AD_Message_StepDefData;
import de.metas.cucumber.stepdefs.pricing.M_PricingSystem_StepDefData;
import de.metas.cucumber.stepdefs.transition.C_Flatrate_Transition_StepDefData;
import de.metas.cucumber.stepdefs.uom.C_UOM_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.order.InvoiceRule;
import de.metas.util.Services;
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
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.contracts.commission.model.I_C_Flatrate_Conditions.COLUMNNAME_C_HierarchyCommissionSettings_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_C_Customer_Trade_Margin_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_C_Flatrate_Conditions_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_C_Flatrate_Transition_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_C_LicenseFeeSettings_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_C_MediatedCommissionSettings_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_C_UOM_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_DocStatus;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_InvoiceRule;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Flatrate_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_ModCntr_Settings_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_Name;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_OnFlatrateTermExtend;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_Type_Flatrate;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.compiere.model.I_AD_Message.COLUMNNAME_AD_Message_ID;

public class C_Flatrate_Conditions_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	private final C_HierarchyCommissionSettings_StepDefData hierarchyCommissionSettingsTable;
	private final C_LicenseFeeSettings_StepDefData licenseFeeSettingsTable;
	private final C_Customer_Trade_Margin_StepDefData customerTradeMarginTable;
	private final C_MediatedCommissionSettings_StepDefData mediatedCommissionSettingsTable;
	private final C_Flatrate_Conditions_StepDefData conditionsTable;
	private final M_PricingSystem_StepDefData pricingSysTable;
	private final ModCntr_Settings_StepDefData modCntrSettingsTable;
	private final C_Year_StepDefData yearTable;
	private final M_Product_StepDefData productTable;
	private final C_UOM_StepDefData uomTable;
	private final AD_Message_StepDefData messageTable;
	private final C_Flatrate_Transition_StepDefData transitionTable;

	public C_Flatrate_Conditions_StepDef(
			@NonNull final C_HierarchyCommissionSettings_StepDefData hierarchyCommissionSettingsTable,
			@NonNull final C_LicenseFeeSettings_StepDefData licenseFeeSettingsTable,
			@NonNull final C_Customer_Trade_Margin_StepDefData customerTradeMarginTable,
			@NonNull final C_MediatedCommissionSettings_StepDefData mediatedCommissionSettingsTable,
			@NonNull final C_Flatrate_Conditions_StepDefData conditionsTable,
			@NonNull final M_PricingSystem_StepDefData pricingSysTable,
			@NonNull final ModCntr_Settings_StepDefData modCntrSettingsTable,
			@NonNull final C_Year_StepDefData yearTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_UOM_StepDefData uomTable,
			@NonNull final AD_Message_StepDefData messageTable,
			@NonNull final C_Flatrate_Transition_StepDefData transitionTable)
	{
		this.hierarchyCommissionSettingsTable = hierarchyCommissionSettingsTable;
		this.licenseFeeSettingsTable = licenseFeeSettingsTable;
		this.customerTradeMarginTable = customerTradeMarginTable;
		this.mediatedCommissionSettingsTable = mediatedCommissionSettingsTable;
		this.conditionsTable = conditionsTable;
		this.pricingSysTable = pricingSysTable;
		this.modCntrSettingsTable = modCntrSettingsTable;
		this.yearTable = yearTable;
		this.productTable = productTable;
		this.uomTable = uomTable;
		this.messageTable = messageTable;
		this.transitionTable = transitionTable;
	}

	@Given("metasfresh contains C_Flatrate_Conditions:")
	public void metasfresh_contains_c_flatrate_conditions(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String name = tableRow.get(COLUMNNAME_Name);
			assertThat(name).as(COLUMNNAME_Name + " is mandatory").isNotBlank();

			final String conditionsType = tableRow.get(COLUMNNAME_Type_Conditions);
			assertThat(conditionsType).as(COLUMNNAME_Type_Conditions + " is mandatory").isNotBlank();

			final I_C_Flatrate_Conditions flatrateConditions = CoalesceUtil.coalesceSuppliersNotNull(
					() -> queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class)
							.addEqualsFilter(COLUMNNAME_Name, name)
							.create()
							.firstOnly(I_C_Flatrate_Conditions.class),
					() -> InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class));

			final String flatRateType = tableRow.get("OPT." + COLUMNNAME_Type_Flatrate);
			if (EmptyUtil.isNotBlank(flatRateType))
			{
				flatrateConditions.setType_Flatrate(flatRateType);
			}
			final String flatrateProductIdentifier = tableRow.get("OPT." + COLUMNNAME_M_Product_Flatrate_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(flatrateProductIdentifier))
			{
				final I_M_Product flatrateProduct = productTable.get(flatrateProductIdentifier);
				assertThat(flatrateProduct).as("Missing M_Product record for identifier " + flatrateProductIdentifier).isNotNull();

				flatrateConditions.setM_Product_Flatrate_ID(flatrateProduct.getM_Product_ID());
			}

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

			final String uomIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_C_UOM_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(uomIdentifier))
			{
				final I_C_UOM uomRecord = uomTable.get(uomIdentifier);
				assertThat(uomRecord).as("Missing C_UOM for Identifier=%s", uomIdentifier).isNotNull();
				flatrateConditions.setC_UOM_ID(uomRecord.getC_UOM_ID());
			}

			flatrateConditions.setName(name);
			flatrateConditions.setType_Conditions(conditionsType);
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

			final String modCntrSetttingsIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Conditions.COLUMNNAME_ModCntr_Settings_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(modCntrSetttingsIdentifier))
			{
				final I_ModCntr_Settings modCntrSettingsRecord = modCntrSettingsTable.get(modCntrSetttingsIdentifier);
				flatrateConditions.setModCntr_Settings_ID(modCntrSettingsRecord.getModCntr_Settings_ID());
			}

			InterfaceWrapperHelper.saveRecord(flatrateConditions);

			final String conditionsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, TABLECOLUMN_IDENTIFIER);

			conditionsTable.put(conditionsIdentifier, flatrateConditions);
		}
	}

	@When("clone C_Flatrate_Conditions:")
	public void clone_c_flatrate_conditions(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		final Map<String, String> tableRow = tableRows.get(0);

		// validate columns definition
		final String flatrateConditionsIdentifier = tableRow.get(COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);
		assertThat(flatrateConditionsIdentifier).isNotBlank();

		final I_C_Flatrate_Conditions condition = conditionsTable.get(flatrateConditionsIdentifier);
		assertThat(condition).isNotNull();

		final String yearIdentifier = tableRow.get(I_C_Year.COLUMNNAME_C_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
		assertThat(yearIdentifier).isNotBlank();

		final I_C_Year year = yearTable.get(yearIdentifier);
		assertThat(year).isNotNull();

		final String clonedConditionsIdentifier = tableRow.get("CLONE." + COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);
		assertThat(clonedConditionsIdentifier).isNotBlank();

		try
		{
			//
			// calling the BL called in the process -- only method used
			final ConditionsId conditionsId = ConditionsId.ofRepoId(condition.getC_Flatrate_Conditions_ID());
			final YearId yearId = YearId.ofRepoId(year.getC_Year_ID());

			final ConditionsId clonedConditionId = flatrateBL.cloneConditionsToNewYear(conditionsId, yearId);

			final I_C_Flatrate_Conditions clonedCondition = InterfaceWrapperHelper.load(clonedConditionId, I_C_Flatrate_Conditions.class);
			conditionsTable.put(clonedConditionsIdentifier, clonedCondition);

		}
		catch (final Exception e)
		{
			final String errorMessageIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_AD_Message_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (errorMessageIdentifier != null)
			{
				final I_AD_Message errorMessage = messageTable.get(errorMessageIdentifier);
				assertThat(e.getMessage()).contains(msgBL.getMsg(Env.getCtx(), AdMessageKey.of(errorMessage.getValue())));
			}
			else
			{
				fail(e.getMessage(), e);
			}
		}

	}

	@Then("validate cloned C_Flatrate_Conditions:")
	public void validate_cloned_C_Flatrate_Conditions(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		final Map<String, String> tableRow = tableRows.get(0);

		final String clonedConditionsIdentifier = tableRow.get(COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);
		assertThat(clonedConditionsIdentifier).as(COLUMNNAME_C_Flatrate_Conditions_ID + " is mandatory").isNotBlank();

		final String name = tableRow.get(COLUMNNAME_Name);
		assertThat(name).as(COLUMNNAME_Name + " is mandatory").isNotBlank();

		final String type_Conditions = tableRow.get(COLUMNNAME_Type_Conditions);
		assertThat(type_Conditions).as(COLUMNNAME_Type_Conditions + " is mandatory").isNotBlank();

		final String onFlatrateTermExtend = tableRow.get("OPT." + COLUMNNAME_OnFlatrateTermExtend);
		assertThat(onFlatrateTermExtend).as(COLUMNNAME_OnFlatrateTermExtend + " is mandatory in this context").isNotBlank();

		final String docStatus = tableRow.get("OPT." + COLUMNNAME_DocStatus);
		assertThat(docStatus).as(COLUMNNAME_DocStatus + " is mandatory in this context").isNotBlank();

		final I_C_Flatrate_Conditions clonedConditions = conditionsTable.get(clonedConditionsIdentifier);
		assertThat(clonedConditions).isNotNull();

		final I_ModCntr_Settings clonedSettings = clonedConditions.getModCntr_Settings();
		assertThat(clonedSettings).isNotNull();

		final String clonedSettingsIDentifier = tableRow.get("CLONE." + COLUMNNAME_ModCntr_Settings_ID + "." + TABLECOLUMN_IDENTIFIER);
		assertThat(clonedSettingsIDentifier).as("CLONE." + COLUMNNAME_ModCntr_Settings_ID + "." + TABLECOLUMN_IDENTIFIER + " is mandatory in this context").isNotBlank();

		modCntrSettingsTable.put(clonedSettingsIDentifier, clonedSettings);

		final I_C_Year harvestYear = clonedSettings.getC_Year();
		assertThat(harvestYear).isNotNull();

		assertThat(clonedConditions.getName()).endsWith("-" + harvestYear.getFiscalYear()); // UUID is added to the name to avoid unique name constraint
		assertThat(clonedConditions.getType_Conditions()).isEqualTo(type_Conditions);
		assertThat(clonedConditions.getOnFlatrateTermExtend()).isEqualTo(onFlatrateTermExtend);
		assertThat(clonedConditions.getDocStatus()).isEqualTo(docStatus);

	}

	@And("validate cloned ModCntr_Settings:")
	public void validate_cloned_ModCntr_Settings(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		final SoftAssertions assertions = new SoftAssertions();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String clonedSettingsIdentifier = tableRow.get(COLUMNNAME_ModCntr_Settings_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertions.assertThat(clonedSettingsIdentifier).as(COLUMNNAME_ModCntr_Settings_ID + " is mandatory").isNotBlank();

			final I_ModCntr_Settings clonedSettings = modCntrSettingsTable.get(clonedSettingsIdentifier);
			assertions.assertThat(clonedSettings).isNotNull();

			//name
			final String name = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Name);
			assertions.assertThat(clonedSettings.getName()).isEqualTo(name);

			// product
			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertions.assertThat(productIdentifier).as(I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID + " is a mandatory column").isNotBlank();

			final I_M_Product product = productTable.get(productIdentifier);
			assertions.assertThat(product).isNotNull();
			assertions.assertThat(clonedSettings.getM_Raw_Product_ID()).isEqualTo(product.getM_Product_ID());

			// year & calendar
			final String yearIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Settings.COLUMNNAME_C_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertions.assertThat(yearIdentifier).as(I_ModCntr_Settings.COLUMNNAME_C_Year_ID + " is a mandatory column").isNotBlank();

			final I_C_Year year = yearTable.get(yearIdentifier);
			assertions.assertThat(year).isNotNull();
			assertions.assertThat(clonedSettings.getC_Year_ID()).isEqualTo(year.getC_Year_ID());
			assertions.assertThat(clonedSettings.getC_Calendar_ID()).isEqualTo(year.getC_Calendar_ID());

			// Pricing system
			final String pricingSystemIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Conditions.COLUMNNAME_M_PricingSystem_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(pricingSystemIdentifier))
			{
				final I_M_PricingSystem pricingSystem = pricingSysTable.get(pricingSystemIdentifier);
				assertions.assertThat(clonedSettings.getM_PricingSystem_ID()).isEqualTo(pricingSystem.getM_PricingSystem_ID());
			}

			// ModCntr_Modules
			final int clonedModulesCounter = queryBL.createQueryBuilder(I_ModCntr_Module.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_ModCntr_Module.COLUMNNAME_ModCntr_Settings_ID, clonedSettings.getModCntr_Settings_ID())
					.create()
					.count();

			assertions.assertThat(clonedModulesCounter).isEqualTo(1);
			assertions.assertAll();
		}
	}

	@And("^the C_Flatrate_Conditions identified by (.*) is (completed)$")
	public void conditions_action(@NonNull final String conditionsIdentifier, @NonNull final String action)
	{
		final I_C_Flatrate_Conditions conditions = conditionsTable.get(conditionsIdentifier);

		if (StepDefDocAction.valueOf(action) == StepDefDocAction.completed)
		{
			documentBL.processEx(conditions, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}
		else
		{
			throw new AdempiereException("Unhandled C_Flatrate_Conditions action")
					.appendParametersToMessage()
					.setParameter("action:", action);
		}
	}

	@And("validate C_Flatrate_Conditions")
	public void validate_C_Flatrate_Conditions(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateFlatrateConditions(tableRow);
		}
	}

	private void validateFlatrateConditions(@NonNull final Map<String, String> tableRow)
	{
		final String flatrateConditionsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Flatrate_Conditions conditionsRecord = conditionsTable.get(flatrateConditionsIdentifier);

		final String transitionIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "OPT." + COLUMNNAME_C_Flatrate_Transition_ID + "." + TABLECOLUMN_IDENTIFIER);
		final SoftAssertions softly = new SoftAssertions();
		if (Check.isNotBlank(transitionIdentifier))
		{
			final I_C_Flatrate_Transition transitionRecord = transitionTable.get(transitionIdentifier);

			softly.assertThat(conditionsRecord.getC_Flatrate_Transition_ID()).as(COLUMNNAME_C_Flatrate_Transition_ID).isEqualTo(transitionRecord.getC_Flatrate_Transition_ID());
		}

		softly.assertAll();
	}
}
