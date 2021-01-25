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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.PInstanceId;
import de.metas.process.PInstanceRequest;
import de.metas.process.Param;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.report.client.ReportsClient;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLineType;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;

public class WEBUI_PP_Order_PrintLabel extends WEBUI_PP_Order_Template implements IProcessPrecondition
{
	private static final String PARAM_PrintLabel = "PrintLabel";

	@Param(parameterName = PARAM_PrintLabel)
	private String reportTypeCode;

	private enum ReportType
	{
		Simple("S", AdProcessId.ofRepoId(584773)), //
		MultipleSegments("M", AdProcessId.ofRepoId(584768));

		private final String code;
		@Getter
		private final AdProcessId processId;

		ReportType(@NonNull final String code, @NonNull final AdProcessId processId)
		{
			this.code = code;
			this.processId = processId;
		}

		public static ReportType ofCode(@NonNull final String code)
		{
			if (Simple.code.equals(code))
			{
				return Simple;
			}
			else if (MultipleSegments.code.equals(code))
			{
				return MultipleSegments;
			}
			else
			{
				throw new AdempiereException("Unknown code: " + code);
			}
		}
	}

	final private IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{

		final Set<HuId> distinctHuIds = retrieveSelectedHuIds();
		if (distinctHuIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		// create selection
		final Set<HuId> distinctHuIds = retrieveSelectedHuIds();
		DB.createT_Selection(getPinstanceId(), distinctHuIds, ITrx.TRXNAME_None);

		// print
		final ReportResult label = printLabel();

		// preview
		getResult().setReportData(label.getReportContent(), buildFilename(), OutputType.PDF.getContentType());

		return MSG_OK;

	}

	private Set<HuId> retrieveSelectedHuIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();

		final ImmutableList<PPOrderLineRow> selectedRows = getView()
				.streamByIds(selectedRowIds)
				.collect(ImmutableList.toImmutableList());

		final Set<HuId> huIds = new HashSet<HuId>();

		for (final PPOrderLineRow row : selectedRows)
		{
			if (row.isReceipt())
			{

				final PPOrderLineType type = row.getType();
				if (type.isMainProduct())
				{
					final ImmutableList<PPOrderLineRow> includedRows = row.getIncludedRows();
					includedRows.stream()
							.filter(ppOrderLineRow -> ppOrderLineRow.getType().isHUOrHUStorage())
							.map(PPOrderLineRow::getHuId)
							.filter(Objects::nonNull)
							.forEach(huIds::add);
				}
				else if (row.getHuId() != null && type.isHUOrHUStorage())
				{
					huIds.add(row.getHuId());
				}
			}
		}

		return huIds;
	}

	private ReportResult printLabel()
	{
		final PInstanceRequest pinstanceRequest = createPInstanceRequest();
		final PInstanceId pinstanceId = adPInstanceDAO.createADPinstanceAndADPInstancePara(pinstanceRequest);

		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(getCtx())
				.setAD_Process_ID(getReportType().getProcessId())
				.setAD_PInstance(adPInstanceDAO.getById(pinstanceId))
				.setReportLanguage(getProcessInfo().getReportLanguage())
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		final ReportsClient reportsClient = ReportsClient.get();
		return reportsClient.report(jasperProcessInfo);
	}

	private PInstanceRequest createPInstanceRequest()
	{

		return PInstanceRequest.builder()
				.processId(getReportType().getProcessId())
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

	private ReportType getReportType()
	{
		return ReportType.ofCode(reportTypeCode);
	}
}
