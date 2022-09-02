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

package de.metas.cucumber.stepdefs.edi;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static de.metas.esb.edi.model.I_EDI_Desadv_Pack.COLUMNNAME_EDI_Desadv_Pack_ID;
import static de.metas.esb.edi.model.I_EDI_Desadv_Pack.COLUMNNAME_IPA_SSCC18;
import static de.metas.esb.edi.model.I_EDI_Desadv_Pack.COLUMNNAME_IsManual_IPA_SSCC18;
import static de.metas.esb.edi.model.I_EDI_Desadv_Pack.COLUMNNAME_M_HU_ID;

public class EDI_Desadv_Pack_StepDef
{
	private final static Logger logger = LogManager.getLogger(EDI_Desadv_Pack_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final EDI_Desadv_Pack_StepDefData ediDesadvPackTable;
	private final M_HU_StepDefData huTable;

	public EDI_Desadv_Pack_StepDef(
			@NonNull final EDI_Desadv_Pack_StepDefData ediDesadvPackTable,
			@NonNull final M_HU_StepDefData huTable)
	{
		this.ediDesadvPackTable = ediDesadvPackTable;
		this.huTable = huTable;
	}

	@Given("metasfresh initially has no EDI_Desadv_Pack data")
	public void setupMD_Stock_Data()
	{
		truncateEDIDesadvPack();
	}

	@Then("^after not more than (.*)s, EDI_Desadv_Pack records are found:$")
	public void packsAreFound(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			packIsFound(row, timeoutSec);
		}
	}

	@Then("^after not more than (.*)s, there are no records in EDI_Desadv_Pack$")
	public void tableIsEmpty(final int timeoutSec) throws InterruptedException
	{
		final Supplier<Boolean> emptyTable = () -> queryBL.createQueryBuilder(I_EDI_Desadv_Pack.class)
				.create()
				.count() == 0;

		StepDefUtil.tryAndWait(timeoutSec, 500, emptyTable, this::logCurrentContext);
	}

	private void truncateEDIDesadvPack()
	{
		DB.executeUpdateEx("DELETE FROM EDI_Desadv_Pack", ITrx.TRXNAME_None);
	}

	private void packIsFound(
			@NonNull final Map<String, String> tableRow,
			final int timeoutSec) throws InterruptedException
	{
		final String packIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_Desadv_Pack.COLUMNNAME_EDI_Desadv_Pack_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final boolean isManualSSCC18 = DataTableUtil.extractBooleanForColumnNameOr(tableRow, COLUMNNAME_IsManual_IPA_SSCC18, false);

		final IQueryBuilder<I_EDI_Desadv_Pack> queryBuilder = queryBL.createQueryBuilder(I_EDI_Desadv_Pack.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(COLUMNNAME_IsManual_IPA_SSCC18, isManualSSCC18);

		final String huIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack.COLUMNNAME_M_HU_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(huIdentifier))
		{
			final Integer huID = getHuID(huIdentifier);

			queryBuilder.addEqualsFilter(I_EDI_Desadv_Pack.COLUMNNAME_M_HU_ID, huID);
		}
		else
		{
			queryBuilder.addEqualsFilter(I_EDI_Desadv_Pack.COLUMNNAME_M_HU_ID, null);
		}

		final Supplier<Boolean> packIsFound = () -> queryBuilder
				.create()
				.firstOnly(I_EDI_Desadv_Pack.class) != null;

		StepDefUtil.tryAndWait(timeoutSec, 500, packIsFound, () -> logCurrentContext(isManualSSCC18, huIdentifier));

		final I_EDI_Desadv_Pack desadvPack = queryBuilder
				.create()
				.firstOnlyNotNull(I_EDI_Desadv_Pack.class);

		ediDesadvPackTable.put(packIdentifier, desadvPack);
	}

	private void logCurrentContext(
			final boolean isManualSSCC18,
			@NonNull final String huIdentifier)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(COLUMNNAME_IsManual_IPA_SSCC18).append(" : ").append(isManualSSCC18).append("\n");

		if (Check.isNotBlank(huIdentifier))
		{
			final Integer huID = getHuID(huIdentifier);

			message.append(COLUMNNAME_M_HU_ID).append(" : ").append(huID).append("\n");
		}

		message.append("EDI_Desadv_Pack records:").append("\n");

		queryBL.createQueryBuilder(I_EDI_Desadv_Pack.class)
				.create()
				.stream(I_EDI_Desadv_Pack.class)
				.forEach(desadvPack -> message
						.append(COLUMNNAME_EDI_Desadv_Pack_ID).append(" : ").append(desadvPack.getEDI_Desadv_Pack_ID()).append(" ; ")
						.append(COLUMNNAME_IsManual_IPA_SSCC18).append(" : ").append(desadvPack.isManual_IPA_SSCC18()).append(" ; ")
						.append(COLUMNNAME_IPA_SSCC18).append(" : ").append(desadvPack.getIPA_SSCC18()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while looking for EDI_Desadv_Pack, see current context: \n" + message);
	}

	private void logCurrentContext()
	{
		final StringBuilder message = new StringBuilder();

		message.append("EDI_Desadv_Pack records:").append("\n");

		queryBL.createQueryBuilder(I_EDI_Desadv_Pack.class)
				.create()
				.stream(I_EDI_Desadv_Pack.class)
				.forEach(desadvPack -> message
						.append(COLUMNNAME_EDI_Desadv_Pack_ID).append(" : ").append(desadvPack.getEDI_Desadv_Pack_ID()).append(" ; ")
						.append(COLUMNNAME_IsManual_IPA_SSCC18).append(" : ").append(desadvPack.isManual_IPA_SSCC18()).append(" ; ")
						.append(COLUMNNAME_IPA_SSCC18).append(" : ").append(desadvPack.getIPA_SSCC18()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while looking for EDI_Desadv_Pack, see current context: \n" + message);
	}

	@NonNull
	private Integer getHuID(@NonNull final String huIdentifier)
	{
		return huTable.getOptional(huIdentifier)
				.map(I_M_HU::getM_HU_ID)
				.orElseGet(() -> Integer.parseInt(huIdentifier));
	}
}
