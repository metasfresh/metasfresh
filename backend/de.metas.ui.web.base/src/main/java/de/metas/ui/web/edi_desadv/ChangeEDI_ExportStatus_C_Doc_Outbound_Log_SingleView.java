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

package de.metas.ui.web.edi_desadv;

import de.metas.document.archive.DocOutboundLogId;
import de.metas.edi.api.EDIDocOutBoundLogService;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.model.I_C_Doc_Outbound_Log;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;

public class ChangeEDI_ExportStatus_C_Doc_Outbound_Log_SingleView
		extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final EDIDocOutBoundLogService ediDocOutBoundLogService = SpringContextHolder.instance.getBean(EDIDocOutBoundLogService.class);

	protected static final String PARAM_TargetExportStatus = I_EDI_Desadv.COLUMNNAME_EDI_ExportStatus;
	@Param(parameterName = PARAM_TargetExportStatus)
	private String p_TargetExportStatus;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_C_Doc_Outbound_Log docOutboundLog = ediDocOutBoundLogService.retreiveById(DocOutboundLogId.ofRepoId(context.getSingleSelectedRecordId()));

		if (ChangeEDI_ExportStatusHelper.checkIsNotInvoiceWithEDI(docOutboundLog))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Selected record is not an EDI Invoice: " + docOutboundLog);
		}

		final EDIExportStatus fromExportStatus = EDIExportStatus.ofNullableCode(docOutboundLog.getEDI_ExportStatus());
		if (fromExportStatus == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Selected record is not an EDI Invoice: " + docOutboundLog);
		}

		if (ChangeEDI_ExportStatusHelper.getAvailableTargetExportStatuses(fromExportStatus).isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Cannot change ExportStatus from the current one: " + fromExportStatus);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_TargetExportStatus, numericKey = false, lookupSource = LookupSource.list)
	private LookupValuesList getTargetExportStatusLookupValues(final LookupDataSourceContext context)
	{
		final I_C_Doc_Outbound_Log docOutboundLog = ediDocOutBoundLogService.retreiveById(DocOutboundLogId.ofRepoId(getRecord_ID()));

		final EDIExportStatus fromExportStatus = EDIExportStatus.ofCode(docOutboundLog.getEDI_ExportStatus());

		return ChangeEDI_ExportStatusHelper.computeTargetExportStatusLookupValues(fromExportStatus);
	}

	@Override
	@Nullable
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final I_C_Doc_Outbound_Log docOutboundLog = ediDocOutBoundLogService.retreiveById(DocOutboundLogId.ofRepoId(getRecord_ID()));

		final EDIExportStatus fromExportStatus = EDIExportStatus.ofCode(docOutboundLog.getEDI_ExportStatus());

		return ChangeEDI_ExportStatusHelper.computeParameterDefaultValue(fromExportStatus);
	}

	@Override
	protected String doIt() throws Exception
	{
		final EDIExportStatus targetExportStatus = EDIExportStatus.ofCode(p_TargetExportStatus);

		ChangeEDI_ExportStatusHelper.C_DocOutbound_LogDoIt(targetExportStatus, DocOutboundLogId.ofRepoId(getRecord_ID()));
		return MSG_OK;
	}
}
