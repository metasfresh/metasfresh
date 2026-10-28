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

package de.metas.ui.web.externalsystem;

import de.metas.edi.api.impl.EpcisExportStatusChangeService;
import de.metas.externalsystem.ExternalSystemExportStatus;
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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;

/**
 * WebUI process to change the EPCIS scripted-export status of a single shipment (M_InOut). Mirrors
 * {@code ChangeEDI_ExportStatus_M_InOut_SingleView}. Writes a new, process-instance-stamped status
 * attempt row per EPCIS config of the shipment (who/when audit).
 */
public class ChangeEpcisExportStatus_M_InOut_SingleView
		extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final EpcisExportStatusChangeService changeService = SpringContextHolder.instance.getBean(EpcisExportStatusChangeService.class);

	protected static final String PARAM_TargetExportStatus = "ExportStatus";
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
		if (!inOut.isSOTrx())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No Shipment");
		}

		final ExternalSystemExportStatus fromStatus = changeService.getFromStatus(InOutId.ofRepoId(inOut.getM_InOut_ID()));
		if (ChangeEpcisExportStatusHelper.getAvailableTargetExportStatuses(fromStatus).isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Cannot change EPCIS export status from the current one: " + fromStatus);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_TargetExportStatus, numericKey = false, lookupSource = LookupSource.list)
	private LookupValuesList getTargetExportStatusLookupValues(final LookupDataSourceContext context)
	{
		return ChangeEpcisExportStatusHelper.computeTargetExportStatusLookupValues(changeService.getFromStatus(InOutId.ofRepoId(getRecord_ID())));
	}

	@Override
	@Nullable
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return ChangeEpcisExportStatusHelper.computeParameterDefaultValue(changeService.getFromStatus(InOutId.ofRepoId(getRecord_ID())));
	}

	@Override
	protected String doIt() throws Exception
	{
		changeService.changeStatus(
				InOutId.ofRepoId(getRecord_ID()),
				ExternalSystemExportStatus.ofCode(p_TargetExportStatus),
				getPinstanceId());
		return MSG_OK;
	}
}
