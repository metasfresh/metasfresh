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
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.ResourceReader;
import de.metas.edi.api.ZebraConfigRepository;
import de.metas.edi.api.ZebraPrinterService;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.edi.api.impl.pack.EDIDesadvPackRepository;
import de.metas.process.IADPInstanceDAO;
import de.metas.report.ReportResultData;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.compiere.SpringContextHolder;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class EDI_DesadvPackGenerateCSV_FileForSSCC_Labels_StepDef
{
	private final EDIDesadvPackRepository ediDesadvPackRepository = SpringContextHolder.instance.getBean(EDIDesadvPackRepository.class);
	private final ZebraPrinterService zebraPrinterService = SpringContextHolder.instance.getBean(ZebraPrinterService.class);
	private final ZebraConfigRepository zebraConfigRepository = SpringContextHolder.instance.getBean(ZebraConfigRepository.class);

	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);

	@NonNull private final EDI_Desadv_Pack_StepDefData ediDesadvPackTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final C_Order_StepDefData orderTable;

	@And("generate csv file for sscc labels for {string}")
	public void generateCSV_FileForSSCC_Labels(
			@NonNull final String packIdentifiersParam,
			@NonNull final DataTable dataTable)
	{
		final List<String> reportDataContentActual = generateCSVFileForSSCCLabels(ediDesadvPackTable.getIds(packIdentifiersParam));
		final List<String> reportDataContentExpected = getReportDataLines(dataTable);

		assertThat(normalizeReportLinesForComparing(reportDataContentActual))
				.containsExactlyInAnyOrderElementsOf(normalizeReportLinesForComparing(reportDataContentExpected));
	}

	@NonNull
	private List<String> generateCSVFileForSSCCLabels(@NonNull final ImmutableList<EDIDesadvPackId> desadvPackIds)
	{
		assertThat(desadvPackIds).isNotEmpty();

		final BPartnerId bPartnerId = ediDesadvPackRepository.retrieveBPartnerFromEdiDesadvPack(desadvPackIds.get(0));
		final ZebraConfigId zebraConfigId = zebraConfigRepository.retrieveZebraConfigId(bPartnerId, zebraConfigRepository.getDefaultZebraConfigId());
		final ReportResultData reportResultData = zebraPrinterService.createCSV_FileForSSCC18_Labels(desadvPackIds, zebraConfigId, adPInstanceDAO.createSelectionId());
		return ResourceReader.asStringLines(reportResultData.getReportData());
	}

	private List<String> getReportDataLines(final @NonNull DataTable dataTable)
	{
		final Evaluatee reportParsingContext = newReportParsingContext();
		final ArrayList<String> lines = new ArrayList<>();
		DataTableRows.of(dataTable).forEach(row -> {
			final IStringExpression lineExpr = StringExpressionCompiler.instance.compile(row.getAsString("ReportDataLine"));
			final String line = lineExpr.evaluate(reportParsingContext, IExpressionEvaluator.OnVariableNotFound.Fail);
			lines.add(line);
		});
		return lines;
	}

	private Evaluatee newReportParsingContext()
	{
		final HashMap<String, String> map = new HashMap<>();
		productTable.forEach((identifier, product) -> {
			map.put(identifier.getAsString() + ".productName", product.getName());
			map.put(identifier.getAsString(), product.getName());
		});
		orderTable.forEach((identifier, order) -> {
			map.put(identifier.getAsString() + ".orderPOReference", order.getPOReference());
			map.put(identifier.getAsString(), order.getPOReference());
		});
		return Evaluatees.ofMap(map);
	}

	private List<String> normalizeReportLinesForComparing(final List<String> lines)
	{
		return lines.stream()
				.map(StringUtils::trimBlankToNull)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}
}
