package de.metas.ui.web.handlingunits.process;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.Profiles;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateForExistingHUsRequest;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateForExistingHUsResult;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUReportExecutor;
import de.metas.handlingunits.report.HUReportService;
import de.metas.handlingunits.report.HUToReport;
import de.metas.process.AdProcessId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.report.server.OutputType;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.filter.HUIdsFilterData;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.util.List;

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
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_M_HU_PrintJSONLabel
		extends HUEditorProcessTemplate
		implements IProcessPrecondition
{
	private final HUReportService huReportService = HUReportService.get();
	private HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final HUToReport hu = getSingleSelectedRow().getAsHUToReportOrNull();
		if (hu == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No (single) HU selected");
		}

		final List<HUToReport> hUsToProcess = getHuToReportList();
		if (hUsToProcess.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("current HU's type does not match the receipt label process");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final List<HUToReport> hUsToProcess = getHuToReportList();

		final List<HuId> huIds = hUsToProcess
				.stream()
				.filter(HUToReport::isTopLevel) // issue https://github.com/metasfresh/metasfresh/issues/3851
				.map(HUToReport::getHUId)
				.collect(ImmutableList.toImmutableList());

		final HUQRCodeGenerateForExistingHUsResult result = generateQrCodes(huIds);

		final ImmutableSetMultimap<HuId, HUQRCode>  qrCodeMap =  result.toSetMultimap();

		final ImmutableCollection<HUQRCode> qrCodes = qrCodeMap.values();
		final Resource pdf = huQRCodesService.createPDF(qrCodes.asList(), false);

		// preview
		getResult().setReportData(pdf, buildFilename(pdf), OutputType.PDF.getContentType());

		return MSG_OK;
	}

	private List<HUToReport> getHuToReportList()
	{
		final HUToReport huToReport = getSingleSelectedRow().getAsHUToReport();

		final List<HUToReport> hUsToProcess = huToReport.streamRecursively()
				.filter(currentHU -> currentHU.isTopLevel() )
				.collect(ImmutableList.toImmutableList());
		return hUsToProcess;
	}

	private String buildFilename(@NonNull final Resource pdf)
	{
		final String fileName = pdf.getFilename();
		if (fileName == null)
		{
			final HUToReport huToReport = getSingleSelectedRow().getAsHUToReport();
			return Joiner.on("_").skipNulls().join(huToReport.getHUId(), huToReport.getHUUnitType()) + ".pdf";
		}

		return fileName + ".pdf";
	}

	private HUQRCodeGenerateForExistingHUsResult generateQrCodes(@NonNull final List<HuId> huIds)
	{
		final HUQRCodeGenerateForExistingHUsRequest request = HUQRCodeGenerateForExistingHUsRequest.builder()
				.huIds(ImmutableSet.copyOf(huIds))
				.build();
		return huQRCodesService.generateForExistingHUs(request);
	}
}
