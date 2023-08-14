/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.contract.interim;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.model.I_C_BPartner_InterimContract;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractService;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractUpsertRequest;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.calendar.C_Calendar_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Year;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_BPartner_InterimContract_StepDef
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final BPartnerInterimContractService bPartnerInterimContractService = SpringContextHolder.instance.getBean(BPartnerInterimContractService.class);

	private final C_BPartner_InterimContract_StepDefData partnerInterimTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_Calendar_StepDefData calendarTable;
	private final C_Year_StepDefData yearTable;

	public C_BPartner_InterimContract_StepDef(
			@NonNull final C_BPartner_InterimContract_StepDefData partnerInterimTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_Calendar_StepDefData calendarTable,
			@NonNull final C_Year_StepDefData yearTable)
	{
		this.partnerInterimTable = partnerInterimTable;
		this.bpartnerTable = bpartnerTable;
		this.calendarTable = calendarTable;
		this.yearTable = yearTable;
	}

	@And("invoke \"C_BPartner_InterimContract_Upsert\" process:")
	public void processC_BPartner_InterimContract_Upsert(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_InterimContract.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);

			final String calendarIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_InterimContract.COLUMNNAME_C_Harvesting_Calendar_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Calendar calendar = calendarTable.get(calendarIdentifier);

			final String yearIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_InterimContract.COLUMNNAME_Harvesting_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Year year = yearTable.get(yearIdentifier);

			final Boolean isInterimContract = DataTableUtil.extractBooleanForColumnNameOr(row, I_C_BPartner_InterimContract.COLUMNNAME_IsInterimContract, true);

			final BPartnerInterimContractUpsertRequest request = BPartnerInterimContractUpsertRequest.builder()
					.bPartnerId(BPartnerId.ofRepoId(bPartner.getC_BPartner_ID()))
					.yearAndCalendarId(YearAndCalendarId.ofRepoId(year.getC_Year_ID(), calendar.getC_Calendar_ID()))
					.isInterimContract(isInterimContract)
					.build();
			bPartnerInterimContractService.upsert(request);
		}
	}

	@And("metasfresh contains C_BPartner_InterimContract:")
	public void metasfresh_contains_C_BPartner_InterimContract_Upsert(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_InterimContract.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);

			final String calendarIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_InterimContract.COLUMNNAME_C_Harvesting_Calendar_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Calendar calendar = calendarTable.get(calendarIdentifier);

			final String yearIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_InterimContract.COLUMNNAME_Harvesting_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Year year = yearTable.get(yearIdentifier);

			final Boolean isInterimContract = DataTableUtil.extractBooleanForColumnNameOr(row, I_C_BPartner_InterimContract.COLUMNNAME_IsInterimContract, true);

			final I_C_BPartner_InterimContract record = queryBL.createQueryBuilder(I_C_BPartner_InterimContract.class)
					.addEqualsFilter(I_C_BPartner_InterimContract.COLUMNNAME_C_BPartner_ID, bPartner.getC_BPartner_ID())
					.addEqualsFilter(I_C_BPartner_InterimContract.COLUMNNAME_Harvesting_Year_ID, year.getC_Year_ID())
					.addEqualsFilter(I_C_BPartner_InterimContract.COLUMNNAME_C_Harvesting_Calendar_ID, calendar.getC_Calendar_ID())
					.addEqualsFilter(I_C_BPartner_InterimContract.COLUMNNAME_IsInterimContract, isInterimContract)
					.create()
					.firstNotNull(I_C_BPartner_InterimContract.class);

			final String bpartnerInterimContractIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_InterimContract.COLUMNNAME_C_BPartner_InterimContract_ID + "." + TABLECOLUMN_IDENTIFIER);
			partnerInterimTable.putOrReplace(bpartnerInterimContractIdentifier, record);
		}
	}
}
