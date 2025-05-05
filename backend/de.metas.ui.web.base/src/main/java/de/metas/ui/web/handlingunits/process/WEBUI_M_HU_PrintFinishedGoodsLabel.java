package de.metas.ui.web.handlingunits.process;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.report.HUToReport;
import de.metas.handlingunits.report.labels.HULabelConfig;
import de.metas.handlingunits.report.labels.HULabelConfigQuery;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.i18n.ExplainedOptional;
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
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.report.HUReportAwareViewRowAsHUToReport;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;

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
public class WEBUI_M_HU_PrintFinishedGoodsLabel
		extends HUEditorProcessTemplate
		implements IProcessPrecondition
{
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final HULabelService huLabelService = SpringContextHolder.instance.getBean(HULabelService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		final ExplainedOptional<HULabelConfig> optionalLabelConfig = getLabelConfig();
		if (!optionalLabelConfig.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(optionalLabelConfig.getExplanation());
		}

		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (selectedRowIds.isMoreThanOneDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final HUToReport hu = getHuToReport();

		// create selection
		final List<HuId> distinctHuIds = ImmutableList.of(hu.getHUId());
		DB.createT_Selection(getPinstanceId(), distinctHuIds, ITrx.TRXNAME_None);

		// print
		final ReportResult label = printLabel();

		// preview
		getResult().setReportData(new ByteArrayResource(label.getReportContent()), buildFilename(), OutputType.PDF.getContentType());

		return MSG_OK;
	}

	private ExplainedOptional<HULabelConfig> getLabelConfig()
	{
		final HUToReport hu = getHuToReport();
		return huLabelService.getFirstMatching(HULabelConfigQuery.builder()
				.sourceDocType(HULabelSourceDocType.Manufacturing)
				.huUnitType(hu.getHUUnitType())
				.bpartnerId(hu.getBPartnerId())
				.build());
	}

	@NonNull
	private HUToReport getHuToReport()
	{
		return HUReportAwareViewRowAsHUToReport.of(getSingleSelectedRow());
	}

	private ReportResult printLabel()
	{
		final AdProcessId adProcessId = getLabelConfig()
				.get()
				.getPrintFormatProcessId();
		final PInstanceRequest pinstanceRequest = createPInstanceRequest(adProcessId);
		final PInstanceId pinstanceId = adPInstanceDAO.createADPinstanceAndADPInstancePara(pinstanceRequest);

		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(getCtx())
				.setAD_Process_ID(adProcessId)
				.setAD_PInstance(adPInstanceDAO.getById(pinstanceId))
				.setReportLanguage(getProcessInfo().getReportLanguage())
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		final ReportsClient reportsClient = ReportsClient.get();

		return reportsClient.report(jasperProcessInfo);
	}

	private PInstanceRequest createPInstanceRequest(@NonNull final AdProcessId adProcessId)
	{
		return PInstanceRequest.builder()
				.processId(adProcessId)
				.processParams(ImmutableList.of(
						ProcessInfoParameter.of("AD_PInstance_ID", getPinstanceId())))
				.build();
	}

	private String buildFilename()
	{
		final String instance = String.valueOf(getPinstanceId().getRepoId());
		final String title = getProcessInfo().getTitle();

		return Joiner.on("_").skipNulls().join(instance, title) + ".pdf";
	}
}
