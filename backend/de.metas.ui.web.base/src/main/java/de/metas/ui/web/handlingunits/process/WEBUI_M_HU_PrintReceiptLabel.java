package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.handlingunits.report.HUReportExecutor;
import de.metas.handlingunits.report.HUReportService;
import de.metas.handlingunits.report.HUToReport;
import de.metas.handlingunits.report.labels.HULabelConfig;
import de.metas.handlingunits.report.labels.HULabelConfigQuery;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.i18n.ExplainedOptional;
import de.metas.process.AdProcessId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import org.compiere.SpringContextHolder;
import org.springframework.context.annotation.Profile;

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
public class WEBUI_M_HU_PrintReceiptLabel
		extends HUEditorProcessTemplate
		implements IProcessPrecondition
{
	private final HUReportService huReportService = SpringContextHolder.instance.getBean(HUReportService.class);

	@Param(mandatory = true, parameterName = "Copies")
	private int p_copies = 1;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No (single) row selected");
		}

		final HUToReport hu = getSingleSelectedRow().getAsHUToReportOrNull();
		if (hu == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No (single) HU selected");
		}

		final ExplainedOptional<HULabelConfig> labelConfig = getLabelConfig(hu);
		if(!labelConfig.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(labelConfig.getExplanation());
		}

		final AdProcessId printFormatProcessId = labelConfig.get().getPrintFormatProcessId();
		final List<HUToReport> husToProcess = huReportService.getHUsToProcess(hu, printFormatProcessId);
		if (husToProcess.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("current HU's type does not match the receipt label process");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final HUToReport hu = getSingleSelectedRow().getAsHUToReport();

		final AdProcessId printFormatProcessId = getLabelConfig(hu)
				.get()
				.getPrintFormatProcessId();

		final List<HUToReport> husToProcess = huReportService.getHUsToProcess(hu, printFormatProcessId)
				.stream()
				.filter(HUToReport::isTopLevel) // issue https://github.com/metasfresh/metasfresh/issues/3851
				.collect(ImmutableList.toImmutableList());

		HUReportExecutor.newInstance(getCtx())
				.windowNo(getProcessInfo().getWindowNo())
				.numberOfCopies(p_copies)
				.executeHUReportAfterCommit(printFormatProcessId, husToProcess);

		return MSG_OK;
	}

	private ExplainedOptional<HULabelConfig> getLabelConfig(final HUToReport hu)
	{
		return huReportService.getLabelConfig(HULabelConfigQuery.builder()
				.sourceDocType(HULabelSourceDocType.MaterialReceipt)
				.huUnitType(hu.getHUUnitType())
				.bpartnerId(hu.getBPartnerId())
				.build());
	}

}
