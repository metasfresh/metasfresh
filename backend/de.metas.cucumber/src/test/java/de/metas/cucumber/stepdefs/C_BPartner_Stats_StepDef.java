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

package de.metas.cucumber.stepdefs;

import de.metas.bpartner.process.SetCreditStatusEnum;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerStatsBL;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.CODE;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Order.COLUMNNAME_C_BPartner_ID;

public class C_BPartner_Stats_StepDef
{
	private final C_BPartner_StepDefData bPartnerTable;

	private final IBPartnerStatsBL bpartnerStatsBL = Services.get(IBPartnerStatsBL.class);
	private final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

	public C_BPartner_Stats_StepDef(@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.bPartnerTable = bPartnerTable;
	}

	@And("upsert C_BPartner_Stats")
	public void upsert_c_bpartner_stats(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			upsertCBPartnerStats(tableRow);
		}
	}

	private void upsertCBPartnerStats(@NonNull final Map<String, String> tableRow)
	{
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartner = bPartnerTable.get(bpartnerIdentifier);

		final String creditStatusCode = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_Stats.COLUMNNAME_SOCreditStatus + "." + CODE);

		final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(bPartner);

		final String creditStatus = getCreditStatus(creditStatusCode, stats);

		assertThat(creditStatus).isNotNull();
		bpartnerStatsDAO.setSOCreditStatus(stats, creditStatus);
	}

	@NonNull
	private String getCreditStatus(@NonNull final String creditStatusCode, @NonNull final BPartnerStats stats)
	{
		if (SetCreditStatusEnum.Calculate.getCode().equals(creditStatusCode))
		{
			final IBPartnerStatsBL.CalculateSOCreditStatusRequest request = IBPartnerStatsBL.CalculateSOCreditStatusRequest.builder()
					.stat(stats)
					.forceCheckCreditStatus(true)
					.date(SystemTime.asDayTimestamp())
					.build();

			return bpartnerStatsBL.calculateProjectedSOCreditStatus(request);
		}

		return creditStatusCode;
	}
}