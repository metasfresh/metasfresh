/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2020 metas GmbH
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

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.util.Env;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_AD_Language;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_SalesRep_ID;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_InvoiceRule;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_IsCustomer;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_IsSalesRep;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_IsVendor;
import static org.compiere.model.X_C_BPartner.DELIVERYRULE_Force;

public class C_BPartner_StepDef
{
	public static final int BP_GROUP_ID = BPGroupId.ofRepoId(1000000).getRepoId();

	private final StepDefData<I_C_BPartner> bPartnerTable;
	private final StepDefData<I_M_PricingSystem> pricingSystemTable;
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public C_BPartner_StepDef(
			@NonNull final StepDefData<I_C_BPartner> bPartnerTable,
			@NonNull final StepDefData<I_M_PricingSystem> pricingSystemTable)
	{
		this.bPartnerTable = bPartnerTable;
		this.pricingSystemTable = pricingSystemTable;
	}

	@Given("metasfresh contains C_BPartners:")
	public void metasfresh_contains_c_bpartners(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createC_BPartner(tableRow);
		}
	}

	@And("the following c_bpartner is changed")
	public void change_bpartner(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> dataRows = dataTable.asMaps();

		for (final Map<String, String> row : dataRows)
		{
			changeBPartner(row);
		}
	}

	private void createC_BPartner(@NonNull final Map<String, String> tableRow)
	{
		final String bPartnerName = tableRow.get("Name");
		final String bPartnerValue = CoalesceUtil.coalesce(tableRow.get("Value"), bPartnerName);

		final I_C_BPartner bPartnerRecord =
				CoalesceUtil.coalesceSuppliers(
						() -> bpartnerDAO.retrieveBPartnerByValue(Env.getCtx(), bPartnerValue),
						() -> InterfaceWrapperHelper.newInstance(I_C_BPartner.class));

		bPartnerRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		bPartnerRecord.setName(bPartnerName);
		bPartnerRecord.setValue(bPartnerValue);
		bPartnerRecord.setC_BP_Group_ID(BP_GROUP_ID);
		bPartnerRecord.setIsVendor(StringUtils.toBoolean(tableRow.get("OPT." + COLUMNNAME_IsVendor), false));
		bPartnerRecord.setIsCustomer(StringUtils.toBoolean(tableRow.get("OPT." + COLUMNNAME_IsCustomer), false));
		bPartnerRecord.setIsSalesRep(StringUtils.toBoolean(tableRow.get("OPT." + COLUMNNAME_IsSalesRep), false));

		final String invoiceRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_InvoiceRule);

		if (EmptyUtil.isNotBlank(invoiceRule))
		{
			bPartnerRecord.setInvoiceRule(invoiceRule);
		}

		bPartnerRecord.setDeliveryRule(DELIVERYRULE_Force);

		final String pricingSystemIdentifier = tableRow.get(I_M_PricingSystem.COLUMNNAME_M_PricingSystem_ID + ".Identifier");
		if (EmptyUtil.isNotBlank(pricingSystemIdentifier))
		{
			final int pricingSystemId = pricingSystemTable.get(pricingSystemIdentifier).getM_PricingSystem_ID();
			bPartnerRecord.setM_PricingSystem_ID(pricingSystemId);
			bPartnerRecord.setPO_PricingSystem_ID(pricingSystemId);
		}

		bPartnerRecord.setAD_Language(tableRow.get("OPT." + COLUMNNAME_AD_Language));

		final String salesRepIdentifier = tableRow.get("OPT." + COLUMNNAME_C_BPartner_SalesRep_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		if (EmptyUtil.isNotBlank(salesRepIdentifier))
		{
			final I_C_BPartner salesRep = bPartnerTable.get(salesRepIdentifier);
			assertThat(salesRep).as("Missing salesrep C_BPartner record for identifier=" + salesRepIdentifier).isNotNull();

			bPartnerRecord.setC_BPartner_SalesRep_ID(salesRep.getC_BPartner_ID());
		}

		final boolean alsoCreateLocation = InterfaceWrapperHelper.isNew(bPartnerRecord);
		InterfaceWrapperHelper.saveRecord(bPartnerRecord);

		if (alsoCreateLocation)
		{
			final I_C_Location locationRecord = InterfaceWrapperHelper.newInstance(I_C_Location.class);
			locationRecord.setC_Country_ID(StepDefConstants.COUNTRY_ID.getRepoId());
			InterfaceWrapperHelper.saveRecord(locationRecord);

			final I_C_BPartner_Location bPartnerLocationRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
			bPartnerLocationRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
			bPartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
			bPartnerLocationRecord.setIsBillToDefault(true);
			bPartnerLocationRecord.setIsShipTo(true);
			InterfaceWrapperHelper.saveRecord(bPartnerLocationRecord);
		}

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "C_BPartner");
		bPartnerTable.putOrReplace(recordIdentifier, bPartnerRecord);
	}

	private void changeBPartner(@NonNull final Map<String, String> row)
	{
		final String bpartner = DataTableUtil.extractStringForColumnName(row, I_C_BPartner.COLUMNNAME_C_BPartner_ID + ".Identifier");
		final String name2 = DataTableUtil.extractStringOrNullForColumnName(row, "Name2");

		final I_C_BPartner bPartner = bPartnerTable.get(bpartner);

		bPartner.setName2(name2);

		InterfaceWrapperHelper.save(bPartner);
	}
}
