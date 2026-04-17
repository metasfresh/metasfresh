package de.metas.handlingunits.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUToReport;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.printing.IMassPrintingService;
import de.metas.process.AdProcessId;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.report.PrintCopies;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;

import java.util.Set;

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

/**
 * Glue-code process to be called from M_HU_Report.
 * It takes M_HU_IDs from T_Selection, gets/generates QR-Codes for them
 * and then generate the PDF.
 */
public class M_HU_Report_QRCode extends JavaProcess
{
	@NonNull
	private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	@NonNull
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private static final String PARAM_AD_Process_ID = "AD_Process_ID";

	@Param(parameterName = PARAM_AD_Process_ID)
	private int processId;

	@Param(parameterName = "IsPrintPreview")
	private boolean isPrintPreview;

	@Param(parameterName = IMassPrintingService.PARAM_PrintCopies)
	private int p_PrintCopies;

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final PInstanceId selectionId = getPinstanceId();
		final AdProcessId qrCodeProcessId = AdProcessId.ofRepoIdOrNull(processId);

		final ImmutableList<HUToReport> hus = handlingUnitsDAO.streamByQuery(retrieveSelectedRecordsQueryBuilder(I_M_HU.class), HUToReportWrapper::of)
				.filter(hu -> hu.getHUUnitType() != VHU)
				.collect(ImmutableList.toImmutableList());

		final Set<HuId> huIdSet = hus.stream().map(HUToReport::getHUId).collect(ImmutableSet.toImmutableSet());

		DB.createT_Selection(selectionId, HuId.toRepoIds(huIdSet), ITrx.TRXNAME_None);

		if (isPrintPreview)
		{
			final QRCodePDFResource pdf = huQRCodesService.createPdfForSelectionOfHUIds(selectionId, qrCodeProcessId);
			getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());
		}
		else
		{
			huQRCodesService.printForSelectionOfHUIds(selectionId, qrCodeProcessId, PrintCopies.ofIntOrOne(p_PrintCopies));
		}

		return MSG_OK;
	}
}
