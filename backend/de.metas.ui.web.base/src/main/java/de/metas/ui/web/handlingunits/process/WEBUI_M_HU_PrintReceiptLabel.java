package de.metas.ui.web.handlingunits.process;

import de.metas.Profiles;
import de.metas.handlingunits.report.HUToReport;
import de.metas.handlingunits.report.labels.HULabelPrintRequest;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.report.PrintCopies;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.report.HUReportAwareViewRowAsHUToReport;
import org.compiere.SpringContextHolder;
import org.springframework.context.annotation.Profile;

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
	private final HULabelService huLabelService = SpringContextHolder.instance.getBean(HULabelService.class);

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

		final HUToReport hu = HUReportAwareViewRowAsHUToReport.of(getSingleSelectedRow());
		if (hu == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No (single) HU selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final HUToReport hu = HUReportAwareViewRowAsHUToReport.of(getSingleSelectedRow());

		huLabelService.print(HULabelPrintRequest.builder()
				.sourceDocType(HULabelSourceDocType.MaterialReceipt)
				.hu(hu)
				.printCopiesOverride(PrintCopies.ofInt(p_copies))
				.failOnMissingLabelConfig(true)
				.build());

		return MSG_OK;
	}
}
