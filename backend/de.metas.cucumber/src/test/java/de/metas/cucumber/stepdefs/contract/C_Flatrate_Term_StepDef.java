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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.PMM_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.contracts.model.I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID;
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
	
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
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
			@NonNull final C_OrderLine_StepDefData orderLineTable)
	{
		this.bpartnerTable = bpartnerTable;
		this.productTable = productTable;
		this.conditionsTable = conditionsTable;
		this.pmmProductTable = pmmProductTable;
		this.contractTable = contractTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
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

			contractRecord.setStartDate(DataTableUtil.extractDateTimestampForColumnName(tableRow, "StartDate"));
			contractRecord.setEndDate(DataTableUtil.extractDateTimestampForColumnName(tableRow, "EndDate"));

			InterfaceWrapperHelper.saveRecord(contractRecord);

			contractTable.put(
					DataTableUtil.extractRecordIdentifier(tableRow, "C_FlatrateTerm"),
					contractRecord);
		}
	}

	@And("validate created C_Flatrate_Term:")
	public void validate_created_C_Flatrate_Term(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final String flatrateConditionsIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Flatrate_Conditions contractConditions = conditionsTable.get(flatrateConditionsIdentifier);
			assertThat(contractConditions).isNotNull();

			final I_C_Flatrate_Term contract = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(COLUMNNAME_C_Flatrate_Conditions_ID, contractConditions.getC_Flatrate_Conditions_ID())
					.create()
					.firstOnlyNotNull(I_C_Flatrate_Term.class);

			final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_C_OrderLine_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				assertThat(orderLine).isNotNull();
				assertThat(contract.getC_OrderLine_Term_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
			}

			final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_C_Order_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderIdentifier))
			{
				final I_C_Order order = orderTable.get(orderIdentifier);
				assertThat(order).isNotNull();
				assertThat(contract.getC_Order_Term_ID()).isEqualTo(order.getC_Order_ID());
			}

			final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(productIdentifier))
			{
				final I_M_Product product = productTable.get(productIdentifier);
				assertThat(product).isNotNull();
				assertThat(contract.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
			}

			final String x12de355Code = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
			if (Check.isNotBlank(x12de355Code))
			{
				final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));
				assertThat(contract.getC_UOM_ID()).isEqualTo(uomId.getRepoId());
			}

			final BigDecimal priceActual = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_PriceActual);
			if (priceActual != null)
			{
				assertThat(contract.getPriceActual()).isEqualTo(priceActual);
			}

			final BigDecimal plannedQtyPerUnit = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Flatrate_Term.COLUMNNAME_PlannedQtyPerUnit);
			if (plannedQtyPerUnit != null)
			{
				assertThat(contract.getPlannedQtyPerUnit()).isEqualTo(plannedQtyPerUnit);
			}

			final String flatrateTermIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			contractTable.put(flatrateTermIdentifier, contract);
		}
	}
}
