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

package de.metas.cucumber.stepdefs.contract;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.PMM_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.pricing.M_PricingSystem_StepDefData;
import de.metas.process.PInstanceId;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID;
import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID;
import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_C_OrderLine_Term_ID;
import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_DropShip_BPartner_ID;
import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_M_Product_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.procurement.base.model.I_C_Flatrate_Term.COLUMNNAME_PMM_Product_ID;
import static org.assertj.core.api.Assertions.*;

public class C_Flatrate_Term_StepDef
{
	private final C_BPartner_StepDefData bpartnerTable;
	private final M_Product_StepDefData productTable;
	private final C_Flatrate_Conditions_StepDefData conditionsTable;
	private final PMM_Product_StepDefData pmmProductTable;
	private final C_Flatrate_Term_StepDefData contractTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_PricingSystem_StepDefData pricingSysTable;

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	public C_Flatrate_Term_StepDef(
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Flatrate_Conditions_StepDefData conditionsTable,
			@NonNull final PMM_Product_StepDefData pmmProductTable,
			@NonNull final C_Flatrate_Term_StepDefData contractTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_PricingSystem_StepDefData pricingSysTable)
	{
		this.bpartnerTable = bpartnerTable;
		this.productTable = productTable;
		this.conditionsTable = conditionsTable;
		this.pmmProductTable = pmmProductTable;
		this.contractTable = contractTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.pricingSysTable = pricingSysTable;
	}

	@Given("metasfresh contains C_Flatrate_Terms:")
	public void metasfresh_contains_c_flatrate_terms(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String billPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner billPartner = bpartnerTable.get(billPartnerIdentifier);

			final I_C_BPartner_Location billPartnerLocation = bPartnerDAO.retrieveBPartnerLocation(IBPartnerDAO.BPartnerLocationQuery.builder().bpartnerId(BPartnerId.ofRepoId(billPartner.getC_BPartner_ID()))
																										   .type(IBPartnerDAO.BPartnerLocationQuery.Type.BILL_TO)
																										   .build());
			assertThat(billPartnerLocation).isNotNull(); // guard

			final String conditionsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Flatrate_Conditions conditions = conditionsTable.get(conditionsIdentifier);

			final I_C_Flatrate_Data flatrateData = flatrateDAO.retrieveOrCreateFlatrateData(billPartner);

			final I_C_Flatrate_Term contractRecord = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class);
			contractRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			contractRecord.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
			contractRecord.setC_Flatrate_Data_ID(flatrateData.getC_Flatrate_Data_ID());
			contractRecord.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
			contractRecord.setProcessed(true);
			contractRecord.setBill_BPartner_ID(billPartner.getC_BPartner_ID());
			contractRecord.setBill_Location_ID(billPartnerLocation.getC_BPartner_Location_ID());

			final String dropshipPartnerIdentifier = tableRow.get("OPT." + COLUMNNAME_DropShip_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(dropshipPartnerIdentifier))
			{
				final I_C_BPartner dropshipPartner = bpartnerTable.get(dropshipPartnerIdentifier);
				contractRecord.setDropShip_BPartner_ID(dropshipPartner.getC_BPartner_ID());

				final I_C_BPartner_Location dropshipLocation = bPartnerDAO.retrieveBPartnerLocation(IBPartnerDAO.BPartnerLocationQuery.builder().bpartnerId(BPartnerId.ofRepoId(billPartner.getC_BPartner_ID()))
																											.type(IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO)
																											.build());
				assertThat(dropshipLocation).isNotNull(); // guard
				contractRecord.setDropShip_Location_ID(dropshipLocation.getC_BPartner_Location_ID());
			}

			final String productIdentifier = tableRow.get("OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(productIdentifier))
			{
				final I_M_Product product = productTable.get(productIdentifier);
				contractRecord.setM_Product_ID(product.getM_Product_ID());
			}

			final String pmmProductIdentifier = tableRow.get("OPT." + COLUMNNAME_PMM_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(pmmProductIdentifier))
			{
				final I_PMM_Product pmmProductRecord = pmmProductTable.get(pmmProductIdentifier);

				final de.metas.procurement.base.model.I_C_Flatrate_Term prodcurementContractRecord = InterfaceWrapperHelper.create(contractRecord, de.metas.procurement.base.model.I_C_Flatrate_Term.class);
				prodcurementContractRecord.setPMM_Product_ID(pmmProductRecord.getPMM_Product_ID());
			}

			final Integer nrOfDaysFromNow = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT.NrOfDaysFromNow");

			if (nrOfDaysFromNow != null)
			{
				final Instant today = SystemTime.asLocalDate(SystemTime.zoneId()).atStartOfDay(SystemTime.zoneId()).toInstant();
				contractRecord.setStartDate(Timestamp.from(today.minus(1, ChronoUnit.DAYS)));
				contractRecord.setEndDate(Timestamp.from(today.plus(nrOfDaysFromNow, ChronoUnit.DAYS)));
			}
			else
			{
				contractRecord.setStartDate(DataTableUtil.extractDateTimestampForColumnName(tableRow, "StartDate"));
				contractRecord.setEndDate(DataTableUtil.extractDateTimestampForColumnName(tableRow, "EndDate"));
			}

			final String orderLineIdentifier = tableRow.get("OPT." + COLUMNNAME_C_OrderLine_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				contractRecord.setC_OrderLine_Term_ID(orderLine.getC_OrderLine_ID());
			}

			InterfaceWrapperHelper.saveRecord(contractRecord);

			contractTable.put(
					DataTableUtil.extractRecordIdentifier(tableRow, "C_FlatrateTerm"),
					contractRecord);
		}
	}

	@And("validate created C_Flatrate_Term:")
	public void validate_created_C_Flatrate_Term(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();

		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final I_C_Flatrate_Term contract = retrieveContract(row);

			InterfaceWrapperHelper.refresh(contract);

			final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_C_OrderLine_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				softly.assertThat(orderLine).isNotNull();
				softly.assertThat(contract.getC_OrderLine_Term_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
			}

			final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_C_Order_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderIdentifier))
			{
				final I_C_Order order = orderTable.get(orderIdentifier);
				softly.assertThat(order).isNotNull();
				softly.assertThat(contract.getC_Order_Term_ID()).isEqualTo(order.getC_Order_ID());
			}

			final String x12de355Code = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
			if (Check.isNotBlank(x12de355Code))
			{
				final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));
				softly.assertThat(contract.getC_UOM_ID()).isEqualTo(uomId.getRepoId());
			}

			final BigDecimal priceActual = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_PriceActual);
			if (priceActual != null)
			{
				softly.assertThat(contract.getPriceActual()).isEqualTo(priceActual);
			}

			final BigDecimal plannedQtyPerUnit = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_PlannedQtyPerUnit);
			if (plannedQtyPerUnit != null)
			{
				softly.assertThat(contract.getPlannedQtyPerUnit()).isEqualTo(plannedQtyPerUnit);
			}

			final String pricingSystemIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Flatrate_Conditions.COLUMNNAME_M_PricingSystem_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(pricingSystemIdentifier))
			{
				final I_M_PricingSystem pricingSystem = pricingSysTable.get(pricingSystemIdentifier);
				softly.assertThat(contract.getM_PricingSystem_ID()).isEqualTo(pricingSystem.getM_PricingSystem_ID());
			}

			final String typeConditions = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_Type_Conditions);
			if (Check.isNotBlank(typeConditions))
			{
				softly.assertThat(contract.getType_Conditions()).isEqualTo(typeConditions);
			}

			final String contractStatus = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_ContractStatus);
			if (Check.isNotBlank(contractStatus))
			{
				softly.assertThat(contract.getContractStatus()).isEqualTo(contractStatus);
			}

			final String docStatus = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_DocStatus);
			if (Check.isNotBlank(docStatus))
			{
				softly.assertThat(contract.getDocStatus()).isEqualTo(docStatus);
			}

			final String flatrateTermIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			contractTable.putOrReplace(flatrateTermIdentifier, contract);
		}
	}

	@And("retrieve C_Flatrate_Term:")
	public void retrieve_C_Flatrate_Term(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String flatrateConditionsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Flatrate_Conditions_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_Flatrate_Conditions flatrateConditions = conditionsTable.get(flatrateConditionsIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final IQueryBuilder<I_C_Flatrate_Term> queryBuilder = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
					.addEqualsFilter(COLUMNNAME_C_Flatrate_Conditions_ID, flatrateConditions.getC_Flatrate_Conditions_ID())
					.addEqualsFilter(COLUMNNAME_M_Product_ID, product.getM_Product_ID());

			final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Term.COLUMNNAME_C_Order_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderIdentifier))
			{
				final I_C_Order order = orderTable.get(orderIdentifier);
				assertThat(order).isNotNull();
				queryBuilder.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Order_Term_ID, order.getC_Order_ID());
			}

			final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_C_OrderLine_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				assertThat(orderLine).isNotNull();
				queryBuilder.addEqualsFilter(COLUMNNAME_C_OrderLine_Term_ID, orderLine.getC_OrderLine_ID());
			}

			final I_C_Flatrate_Term flatrateTerm = queryBuilder.orderByDescending(COLUMNNAME_C_Flatrate_Term_ID)
					.create()
					.first(I_C_Flatrate_Term.class);

			assertThat(flatrateTerm).isNotNull();

			final String flatrateTermIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Flatrate_Term_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			contractTable.putOrReplace(flatrateTermIdentifier, flatrateTerm);
		}
	}

	@NonNull
	private I_C_Flatrate_Term retrieveContract(@NonNull final Map<String, String> row)
	{
		final String flatrateConditionsIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Flatrate_Conditions contractConditions = conditionsTable.get(flatrateConditionsIdentifier);
		assertThat(contractConditions).isNotNull();

		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);
		assertThat(bPartner).isNotNull();

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(product).isNotNull();

		final String flatrateTermIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);

		return contractTable.getOptional(flatrateTermIdentifier)
				.orElseGet(() -> queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(COLUMNNAME_C_Flatrate_Conditions_ID, contractConditions.getC_Flatrate_Conditions_ID())
						.addEqualsFilter(COLUMNNAME_Bill_BPartner_ID, bPartner.getC_BPartner_ID())
						.addEqualsFilter(COLUMNNAME_M_Product_ID, product.getM_Product_ID())
						.create()
						.firstOnlyNotNull(I_C_Flatrate_Term.class));
	}

	@When("^extend C_Flatrate_Term identified by (.*) starting from (.*) will fail with message (.*)$")
	public void failWithMessageWhenExtendingC_Flatrate_TermsStartingFromWithMessage(@NonNull final String contractIdentifier, @NonNull final String startingDate, @NonNull final String message)
	{
		assertThat(message).isNotBlank();
		assertThat(contractIdentifier).isNotBlank();
		assertThat(startingDate).isNotBlank();

		final I_C_Flatrate_Term contract = contractTable.get(contractIdentifier);
		assertThat(contract).isNotNull();

		final Timestamp nextStartDate = TimeUtil.parseTimestamp(startingDate);
		assertThat(nextStartDate).isNotNull();

		final IFlatrateBL.ContractExtendingRequest contractExtendingRequest = IFlatrateBL.ContractExtendingRequest.builder()
				.AD_PInstance_ID(PInstanceId.ofRepoId(1))
				.contract(contract)
				.forceExtend(true)
				.forceComplete(false)
				.nextTermStartDate(nextStartDate)
				.build();

		try
		{
			flatrateBL.extendContractAndNotifyUser(contractExtendingRequest);
		}
		catch (final Exception e)
		{
			assertThat(e).isNotNull();
			assertThat(e).isExactlyInstanceOf(AdempiereException.class);
			assertThat(e.getMessage()).isEqualTo(message); // "Extension Not Allowed"
		}

	}

	@Then("^extend C_Flatrate_Term identified by (.*) starting from (.*) with PInstanceID (.*)$")
	public void extendFlatrateTermStartingFromIsPossible(@NonNull final String contractIdentifier, @NonNull final String startDate, @NonNull final String pInstanceID)
	{
		assertThat(contractIdentifier).isNotBlank();
		assertThat(startDate).isNotBlank();
		assertThat(pInstanceID).isNotBlank();

		final I_C_Flatrate_Term contract = contractTable.get(contractIdentifier);
		assertThat(contract).isNotNull();

		assertThat(contract.getC_FlatrateTerm_Next()).isNull();  // contract not extended yet

		final Timestamp nextStartDate = TimeUtil.parseTimestamp(startDate);
		assertThat(nextStartDate).isNotNull();

		final Integer pinstance_ID = Integer.valueOf(pInstanceID);
		assertThat(pinstance_ID).isNotNull();

		final IFlatrateBL.ContractExtendingRequest contractExtendingRequest = IFlatrateBL.ContractExtendingRequest.builder()
				.AD_PInstance_ID(PInstanceId.ofRepoId(pinstance_ID))
				.contract(contract)
				.forceExtend(true)
				.forceComplete(false)
				.nextTermStartDate(nextStartDate)
				.build();

		try
		{
			flatrateBL.extendContractAndNotifyUser(contractExtendingRequest);
		}
		catch (final Exception exception)
		{
			fail(exception.getMessage(), exception);
		}

	}

	@Then("^C_Flatrate_Term identified by (.*) is extended$")
	public void c_flatrate_termIsExtended(@NonNull final String contractIdentifier)
	{
		assertThat(contractIdentifier).isNotBlank();

		final I_C_Flatrate_Term contract = contractTable.get(contractIdentifier);
		assertThat(contract).isNotNull();

		final I_C_Flatrate_Term nextContract = contract.getC_FlatrateTerm_Next();
		assertThat(nextContract).isNotNull(); // next term created & contract extended

	}
}
