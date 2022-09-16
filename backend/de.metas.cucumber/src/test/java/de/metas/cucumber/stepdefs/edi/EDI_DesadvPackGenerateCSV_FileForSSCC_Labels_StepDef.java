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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ZebraConfigId;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ResourceReader;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.edi.api.ZebraConfigRepository;
import de.metas.edi.api.ZebraPrinterService;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.edi.api.impl.pack.EDIDesadvPackRepository;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack;
import de.metas.process.IADPInstanceDAO;
import de.metas.report.ReportResultData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.esb.edi.model.I_EDI_Desadv_Pack.COLUMNNAME_EDI_Desadv_Pack_ID;
import static org.assertj.core.api.Assertions.*;

public class EDI_DesadvPackGenerateCSV_FileForSSCC_Labels_StepDef
{
	private final EDIDesadvPackRepository EDIDesadvPackRepository = SpringContextHolder.instance.getBean(EDIDesadvPackRepository.class);
	private final ZebraPrinterService zebraPrinterService = SpringContextHolder.instance.getBean(ZebraPrinterService.class);
	private final ZebraConfigRepository zebraConfigRepository = SpringContextHolder.instance.getBean(ZebraConfigRepository.class);

	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);

	private final EDI_Desadv_Pack_StepDefData ediDesadvPackTable;

	public EDI_DesadvPackGenerateCSV_FileForSSCC_Labels_StepDef(@NonNull final EDI_Desadv_Pack_StepDefData ediDesadvPackTable)
	{
		this.ediDesadvPackTable = ediDesadvPackTable;
	}

	@And("generate csv file for sscc labels for {string}")
	public void generateCSV_FileForSSCC_Labels(
			@NonNull final String packIdentifiersParam,
			@NonNull final DataTable dataTable)
	{
		final ImmutableList<EDIDesadvPackId> desadvPackIDsToPrint = getDesadvPackIDSToPrint(packIdentifiersParam);
		final String reportDataContent = generateCSVFileForSSCCLabels(desadvPackIDsToPrint);

		dataTable.asMaps()
				.forEach(tableRow -> assertThat(reportDataContent).contains(DataTableUtil.extractStringForColumnName(tableRow, "ReportDataLine")));
	}

	@NonNull
	private String generateCSVFileForSSCCLabels(@NonNull final ImmutableList<EDIDesadvPackId> desadvPackIDsToPrint)
	{
		final ReportResultData reportResultData = getReportResultDataFor(desadvPackIDsToPrint);

		return ResourceReader.asString(reportResultData.getReportData());
	}

	@NonNull
	private ImmutableList<EDIDesadvPackId> getDesadvPackIDSToPrint(@NonNull final String packIdentifierCandidate)
	{
		final ImmutableList<EDIDesadvPackId> desadvPackIDsToPrint = StepDefUtil.extractIdentifiers(packIdentifierCandidate)
				.stream()
				.map(ediDesadvPackTable::get)
				.map(I_EDI_Desadv_Pack::getEDI_Desadv_Pack_ID)
				.map(EDIDesadvPackId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		if (desadvPackIDsToPrint.isEmpty())
		{
			throw new RuntimeException("No pack identifier present for column: " + COLUMNNAME_EDI_Desadv_Pack_ID + "." + TABLECOLUMN_IDENTIFIER);
		}

		return desadvPackIDsToPrint;
	}

	@NonNull
	private ReportResultData getReportResultDataFor(@NonNull final ImmutableList<EDIDesadvPackId> desadvPackIDsToPrint)
	{
		final BPartnerId bPartnerId = EDIDesadvPackRepository.retrieveBPartnerFromEdiDesadvPack(desadvPackIDsToPrint.get(0));
		final ZebraConfigId zebraConfigId = zebraConfigRepository.retrieveZebraConfigId(bPartnerId, zebraConfigRepository.getDefaultZebraConfigId());

		return zebraPrinterService
				.createCSV_FileForSSCC18_Labels(desadvPackIDsToPrint, zebraConfigId, adPInstanceDAO.createSelectionId());
	}
}
