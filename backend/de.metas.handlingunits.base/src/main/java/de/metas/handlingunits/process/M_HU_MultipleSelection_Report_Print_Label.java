package de.metas.handlingunits.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUToReport;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.handlingunits.report.labels.HULabelDirectPrintRequest;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.printing.IMassPrintingService;
import de.metas.printing.MergePdfByteArrays;
import de.metas.process.AdProcessId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.report.PrintCopies;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.springframework.core.io.ByteArrayResource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static de.metas.handlingunits.HuUnitType.LU;
import static de.metas.handlingunits.HuUnitType.VHU;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class M_HU_MultipleSelection_Report_Print_Label extends JavaProcess implements IProcessPrecondition
{
	@NonNull
	private final HULabelService labelService = SpringContextHolder.instance.getBean(HULabelService.class);
	@NonNull
	private final HUQRCodesService huqrCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	@NonNull
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Param(mandatory = true, parameterName = "AD_Process_ID")
	private AdProcessId p_AD_Process_ID;
	@Param(mandatory = true, parameterName = IMassPrintingService.PARAM_PrintCopies)
	private int p_PrintCopies;
	@Param(parameterName = "IsPrintPreview")
	private boolean p_isPrintPreview;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final List<HUToReport> topLevelHus = new ArrayList<>();
		final ImmutableList<HUToReport> hus = handlingUnitsDAO.streamByQuery(retrieveSelectedRecordsQueryBuilder(I_M_HU.class), HUToReportWrapper::of)
				.filter(hu -> hu.getHUUnitType() != VHU)
				.peek(topLevelHus::add)
				.flatMap(hu -> {
					if (hu.getHUUnitType() == LU)
					{
						return Stream.concat(Stream.of(hu), hu.getIncludedHUs().stream());
					}
					else
					{
						return Stream.of(hu);
					}
				})
				.filter(hu -> hu.getHUUnitType() != VHU)
				.collect(ImmutableList.toImmutableList());

		final Set<HuId> huIdSet = hus.stream().map(HUToReport::getHUId).collect(ImmutableSet.toImmutableSet());
		huqrCodesService.generateForExistingHUs(huIdSet);

		if (p_isPrintPreview)
		{
			printPreview(topLevelHus);
		}
		else
		{
			printNow(topLevelHus);
		}

		return MSG_OK;
	}

	private void printNow(@NonNull final List<HUToReport> topLevelHus)
	{
		topLevelHus.stream()
				.sorted(Comparator.comparing(hu -> hu.getHUId().getRepoId()))
				.forEach(topLevelHu -> {
					labelService.printNow(HULabelDirectPrintRequest.builder()
												  .onlyOneHUPerPrint(true)
												  .printCopies(PrintCopies.ofIntOrOne(p_PrintCopies))
												  .printFormatProcessId(p_AD_Process_ID)
												  .hu(topLevelHu)
												  .build());

					if (topLevelHu.getHUUnitType() == LU && !topLevelHu.getIncludedHUs().isEmpty())
					{
						final List<HUToReport> sortedIncludedHUs = topLevelHu.getIncludedHUs()
								.stream()
								.sorted(Comparator.comparing(hu -> hu.getHUId().getRepoId()))
								.collect(ImmutableList.toImmutableList());

						labelService.printNow(HULabelDirectPrintRequest.builder()
													  .onlyOneHUPerPrint(true)
													  .printCopies(PrintCopies.ofIntOrOne(p_PrintCopies))
													  .printFormatProcessId(p_AD_Process_ID)
													  .hus(sortedIncludedHUs)
													  .build());
					}
				});
	}

	private void printPreview(@NonNull final List<HUToReport> topLevelHus)
	{
		final MergePdfByteArrays merger = new MergePdfByteArrays();

		topLevelHus.stream()
				.sorted(Comparator.comparing(hu -> hu.getHUId().getRepoId()))
				.forEach(topLevelHu -> {
					merger.add(labelService.printNowAndGetReportData(
									HULabelDirectPrintRequest.builder()
											.onlyOneHUPerPrint(true)
											.printCopies(PrintCopies.ofIntOrOne(p_PrintCopies))
											.printFormatProcessId(p_AD_Process_ID)
											.hu(topLevelHu)
											.build())
									   .getReportDataByteArray());

					if (topLevelHu.getHUUnitType() == LU && !topLevelHu.getIncludedHUs().isEmpty())
					{
						final List<HUToReport> sortedIncludedHUs = topLevelHu.getIncludedHUs()
								.stream()
								.sorted(Comparator.comparing(hu -> hu.getHUId().getRepoId()))
								.collect(ImmutableList.toImmutableList());

						merger.add(labelService.printNowAndGetReportData(
										HULabelDirectPrintRequest.builder()
												.onlyOneHUPerPrint(true)
												.printCopies(PrintCopies.ofIntOrOne(p_PrintCopies))
												.printFormatProcessId(p_AD_Process_ID)
												.hus(sortedIncludedHUs)
												.build())
										   .getReportDataByteArray());
					}
				});

		getResult().setReportData(
				new ByteArrayResource(merger.getMergedPdfByteArray()),
				"labels.pdf",
				"application/pdf");
	}
}
