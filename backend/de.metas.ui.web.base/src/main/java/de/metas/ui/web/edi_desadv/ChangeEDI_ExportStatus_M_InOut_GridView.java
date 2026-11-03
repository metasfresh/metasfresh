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

import com.google.common.collect.ImmutableSet;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.model.I_M_InOut;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * WebUI process to change EDI export status of multiple M_InOuts (grid view).
 * <p>
 * Allows users to bulk-update InOut statuses. All selected InOuts must have the same current status
 * to ensure consistent transitions.
 */
public class ChangeEDI_ExportStatus_M_InOut_GridView
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	protected static final String PARAM_TargetExportStatus = I_M_InOut.COLUMNNAME_EDI_ExportStatus;
	@Param(parameterName = PARAM_TargetExportStatus, mandatory = true)
	private String p_TargetExportStatus;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ImmutableSet<InOutId> inOutIds = getSelectedInOutIds();
		if (inOutIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final Set<EDIExportStatus> statuses = inOutDAO.getByIds(inOutIds, I_M_InOut.class).stream()
				.filter(I_M_InOut::isSOTrx)
				.map(inout -> EDIExportStatus.ofCode(inout.getEDI_ExportStatus()))
				.collect(Collectors.toSet());

		if(statuses.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No Shipments");
		}

		if (statuses.size() > 1)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("All selected InOuts must have the same EDI export status");
		}

		final EDIExportStatus fromExportStatus = statuses.iterator().next();
		if (fromExportStatus == null || ChangeEDI_ExportStatusHelper.getAvailableTargetExportStatuses(fromExportStatus).isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Cannot change ExportStatus from the current one: " + fromExportStatus);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final EDIExportStatus targetExportStatus = EDIExportStatus.ofCode(p_TargetExportStatus);
		final ImmutableSet<InOutId> selectedIds = getSelectedInOutIds();
		if (selectedIds.isEmpty()) return MSG_OK;

		inOutDAO.getByIds(selectedIds, I_M_InOut.class).forEach(inOut -> ChangeEDI_ExportStatusHelper.M_InOutDoIt(inOut, targetExportStatus));

		return MSG_OK;
	}

	@Override
	@Nullable
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final I_M_InOut inout = inOutDAO.getById(getSelectedInOutIds().iterator().next(), I_M_InOut.class);

		final EDIExportStatus fromExportStatus = EDIExportStatus.ofCode(inout.getEDI_ExportStatus());

		return ChangeEDI_ExportStatusHelper.computeParameterDefaultValue(fromExportStatus);
	}

	private ImmutableSet<InOutId> getSelectedInOutIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		return selectedRowIds.toIds(InOutId::ofRepoId);
	}

}
