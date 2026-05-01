package de.metas.handlingunits.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUReportExecutor;
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
import de.metas.report.ReportResultData;
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
	@NonNull private final HULabelService labelService = SpringContextHolder.instance.getBean(HULabelService.class);
	@NonNull private final HUQRCodesService huqrCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

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
			previewLabels(topLevelHus);
		}
		else
		{
			printLabels(topLevelHus);
		}

		return MSG_OK;
	}

	/**
	 * Flattens {@code topLevelHus} into the order used for both preview and physical print:
	 * each top-level HU first, then its included HUs (LUs only), each group sorted by {@link HuId}.
	 * Both branches iterate the same sequence so the preview PDF matches the paper output page-for-page.
	 */
	private Stream<HUToReport> flattenForRendering(@NonNull final List<HUToReport> topLevelHus)
	{
		return topLevelHus.stream()
				.sorted(Comparator.comparing(hu -> hu.getHUId().getRepoId()))
				.flatMap(topLevelHu -> {
					if (topLevelHu.getHUUnitType() == LU && !topLevelHu.getIncludedHUs().isEmpty())
					{
						return Stream.concat(
								Stream.of(topLevelHu),
								topLevelHu.getIncludedHUs().stream()
										.sorted(Comparator.comparing(hu -> hu.getHUId().getRepoId())));
					}
					return Stream.of(topLevelHu);
				});
	}

	private void previewLabels(@NonNull final List<HUToReport> topLevelHus)
	{
		final MergePdfByteArrays merger = new MergePdfByteArrays();
		// Render each HU once, then add the resulting PDF bytes N times to the merger.
		// Kopien=N is a printer-routing concept (PARAM_PrintCopies → C_Printing_Queue.copies)
		// and the Jasper itself doesn't replicate pages for it. So we replicate at the merger
		// to make the preview PDF match what the printer will produce.
		final int copies = Math.max(1, p_PrintCopies);
		final HUReportExecutor executor = HUReportExecutor.newInstance()
				.printPreview(true); // suppress physical-printer routing — preview returns the PDF only

		flattenForRendering(topLevelHus).forEach(hu -> {
			final ReportResultData reportData = executor.executeNow(p_AD_Process_ID, ImmutableList.of(hu)).getReportData();
			if (reportData == null)
			{
				return; // skip HUs for which the Jasper produced no report data
			}
			final byte[] pdf = reportData.getReportDataByteArray();
			for (int i = 0; i < copies; i++)
			{
				merger.add(pdf);
			}
		});

		final byte[] merged = merger.getMergedPdfByteArray();
		if (merged == null)
		{
			return;
		}
		getResult().setReportData(new ByteArrayResource(merged), "labels.pdf", "application/pdf");
	}

	private void printLabels(@NonNull final List<HUToReport> topLevelHus)
	{
		// Loop in Java for Kopien — N separate printNow calls per HU. This guarantees that
		// the printer produces N labels per HU regardless of whether the printing queue
		// actually honors PARAM_PrintCopies on a single queue item (it does not, in practice
		// for HU label processes), and matches the per-HU page count in the preview PDF.
		final int copies = Math.max(1, p_PrintCopies);
		flattenForRendering(topLevelHus).forEach(hu -> {
			for (int i = 0; i < copies; i++)
			{
				labelService.printNow(HULabelDirectPrintRequest.builder()
						.onlyOneHUPerPrint(true)
						.printCopies(PrintCopies.ONE)
						.printFormatProcessId(p_AD_Process_ID)
						.hu(hu)
						.build());
			}
		});
	}
}
