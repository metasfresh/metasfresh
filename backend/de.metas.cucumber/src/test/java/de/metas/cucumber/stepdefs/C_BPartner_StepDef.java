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
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;

import java.util.List;
import java.util.Map;

import static org.compiere.model.I_C_BPartner.COLUMNNAME_AD_Language;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_IsCustomer;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_IsVendor;

public class C_BPartner_StepDef
{
	public static final int BP_GROUP_ID = BPGroupId.ofRepoId(1000000).getRepoId();

	private final StepDefData<I_C_BPartner> bPartnerTable;
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public C_BPartner_StepDef(@NonNull final StepDefData<I_C_BPartner> bPartnerTable)
	{
		this.bPartnerTable = bPartnerTable;
	}

	@Given("metasfresh contains C_BPartners:")
	public void metasfresh_contains_c_bpartners(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
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

			bPartnerRecord.setAD_Language(tableRow.get("OPT." + COLUMNNAME_AD_Language));

			final boolean alsoCreateLoction = InterfaceWrapperHelper.isNew(bPartnerRecord);
			InterfaceWrapperHelper.saveRecord(bPartnerRecord);

			if (alsoCreateLoction)
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
			bPartnerTable.put(recordIdentifier, bPartnerRecord);
		}
	}
}
