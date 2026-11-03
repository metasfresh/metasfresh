/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.model.I_M_InOut;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
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
import de.metas.util.Services;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * WebUI process to change EDI export status of a single M_InOut.
 * <p>
 * Allows users to manually reset a failing InOut to Pending (re-validates), or mark it as DontSend.
 * The DESADV status is automatically recomputed bottom-up after the InOut status changes.
 * <p>
 * Available transitions:
 * <ul>
 *   <li>Error/Sent/Invalid → Pending or DontSend</li>
 *   <li>Pending → DontSend</li>
 *   <li>DontSend/SendingStarted → Pending</li>
 * </ul>
 */
public class ChangeEDI_ExportStatus_M_InOut_SingleView
		extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	protected static final String PARAM_TargetExportStatus = I_M_InOut.COLUMNNAME_EDI_ExportStatus;
	@Param(parameterName = PARAM_TargetExportStatus, mandatory = true)
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

		final I_M_InOut inOut = inOutDAO.getById(InOutId.ofRepoId(context.getSingleSelectedRecordId()), I_M_InOut.class);
		if(!inOut.isSOTrx())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No Shipment");
		}

		final EDIExportStatus fromExportStatus = getFromEDIExportStatus(InOutId.ofRepoId(inOut.getM_InOut_ID()));
		if (ChangeEDI_ExportStatusHelper.getAvailableTargetExportStatuses(fromExportStatus).isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Cannot change ExportStatus from the current one: " + fromExportStatus);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_TargetExportStatus, numericKey = false, lookupSource = LookupSource.list)
	private LookupValuesList getTargetExportStatusLookupValues(final LookupDataSourceContext context)
	{
		return ChangeEDI_ExportStatusHelper.computeTargetExportStatusLookupValues(getFromEDIExportStatus(InOutId.ofRepoId(getRecord_ID())));
	}

	@Override
	@Nullable
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final EDIExportStatus fromExportStatus = getFromEDIExportStatus(InOutId.ofRepoId(getRecord_ID()));
		return ChangeEDI_ExportStatusHelper.computeParameterDefaultValue(fromExportStatus);
	}

	@Override
	protected String doIt() throws Exception
	{
		final EDIExportStatus targetExportStatus = getTargetEDIExportStatus();
		final I_M_InOut inOut = inOutDAO.getById(InOutId.ofRepoId(getRecord_ID()), I_M_InOut.class);

		ChangeEDI_ExportStatusHelper.M_InOutDoIt(inOut, targetExportStatus);

		return MSG_OK;
	}

	private EDIExportStatus getTargetEDIExportStatus()
	{
		return EDIExportStatus.ofCode(p_TargetExportStatus);
	}

	private EDIExportStatus getFromEDIExportStatus(@NonNull final InOutId inOutId)
	{
		return EDIExportStatus.ofCode(inOutDAO.getById(inOutId, I_M_InOut.class).getEDI_ExportStatus());
	}

}
