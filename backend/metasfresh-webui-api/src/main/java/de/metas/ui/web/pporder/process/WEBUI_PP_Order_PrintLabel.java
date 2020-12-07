/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.pporder.process;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.PInstanceId;
import de.metas.process.PInstanceRequest;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.report.client.ReportsClient;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.util.Services;
import lombok.NonNull;

public class WEBUI_PP_Order_PrintLabel
		extends WEBUI_PP_Order_Template
		implements IProcessPrecondition
{
	final private static AdProcessId LabelPdf_AD_Process_ID = AdProcessId.ofRepoId(584768);
	protected static final AdMessageKey MSG_MustBe_TopLevel_HU = AdMessageKey.of("WEBUI_PP_Order_PrintLabel.MustBe_TopLevel_HU");

	final private IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	final private IMsgBL msgBL = Services.get(IMsgBL.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!getSingleSelectedRow().isTopLevelHU())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_MustBe_TopLevel_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{

		// print
		final PPOrderLineRow row = getSingleSelectedRow();
		final ReportResult label = printLabel(row);

		// preview
		getResult().setReportData(
				label.getReportContent(),
				buildFilename(row),
				OutputType.PDF.getContentType());

		return MSG_OK;

	}

	private ReportResult printLabel(@NonNull final PPOrderLineRow row)
	{
		final PInstanceRequest pinstanceRequest = createPInstanceRequest(row);
		final PInstanceId pinstanceId = adPInstanceDAO.createADPinstanceAndADPInstancePara(pinstanceRequest);

		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(getCtx())
				.setAD_Process_ID(LabelPdf_AD_Process_ID)
				.setAD_PInstance(adPInstanceDAO.getById(pinstanceId))
				.setReportLanguage(getProcessInfo().getReportLanguage())
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		final ReportsClient reportsClient = ReportsClient.get();
		return reportsClient.report(jasperProcessInfo);
	}

	private static PInstanceRequest createPInstanceRequest(@NonNull final PPOrderLineRow row)
	{
		return PInstanceRequest.builder()
				.processId(LabelPdf_AD_Process_ID)
				.processParams(ImmutableList.of(
						ProcessInfoParameter.of("RECORD_ID", row.getHuId())))
				.build();
	}

	private static String buildFilename(@NonNull final PPOrderLineRow row)
	{
		final String huValue = String.valueOf(row.getHuId().getRepoId());
		final String product = row.getPackingInfo();

		return Joiner.on("_")
				.skipNulls()
				.join(huValue, product)
				+ ".pdf";
	}

}