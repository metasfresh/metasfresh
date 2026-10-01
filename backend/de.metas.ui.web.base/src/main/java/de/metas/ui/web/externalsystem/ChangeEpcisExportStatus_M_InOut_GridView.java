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

import com.google.common.collect.ImmutableSet;
import de.metas.edi.api.impl.EpcisExportStatusChangeService;
import de.metas.externalsystem.ExternalSystemExportStatus;
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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * WebUI process to change the EPCIS scripted-export status of multiple shipments (M_InOut grid view).
 * Mirrors {@code ChangeEDI_ExportStatus_M_InOut_GridView}. All selected shipments must share the same
 * current EPCIS status so the offered transitions are consistent.
 */
public class ChangeEpcisExportStatus_M_InOut_GridView
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final EpcisExportStatusChangeService changeService = SpringContextHolder.instance.getBean(EpcisExportStatusChangeService.class);

	protected static final String PARAM_TargetExportStatus = "ExportStatus";
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

		// getFromStatus issues one status query per selected shipment. Acceptable for a view action
		// (operators multi-select a handful of shipments); if bulk multi-selects appear, add a batched
		// getLatestStatusesBySourceRecords accessor. getFromStatus already dedups to the latest attempt
		// per config, so there is no per-attempt-row query here.
		final Set<ExternalSystemExportStatus> statuses = inOutDAO.getByIds(inOutIds, I_M_InOut.class).stream()
				.filter(I_M_InOut::isSOTrx)
				.map(inOut -> changeService.getFromStatus(InOutId.ofRepoId(inOut.getM_InOut_ID())))
				.collect(Collectors.toSet());

		if (statuses.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No Shipments");
		}
		if (statuses.size() > 1)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("All selected shipments must have the same EPCIS export status");
		}

		final ExternalSystemExportStatus fromStatus = statuses.iterator().next();
		if (ChangeEpcisExportStatusHelper.getAvailableTargetExportStatuses(fromStatus).isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Cannot change EPCIS export status from the current one: " + fromStatus);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final ExternalSystemExportStatus targetStatus = ExternalSystemExportStatus.ofCode(p_TargetExportStatus);
		final ImmutableSet<InOutId> selectedIds = getSelectedInOutIds();
		if (selectedIds.isEmpty())
		{
			return MSG_OK;
		}
		selectedIds.forEach(inOutId -> changeService.changeStatus(inOutId, targetStatus, getPinstanceId()));
		return MSG_OK;
	}

	@Override
	@Nullable
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return ChangeEpcisExportStatusHelper.computeParameterDefaultValue(
				changeService.getFromStatus(getSelectedInOutIds().iterator().next()));
	}

	private ImmutableSet<InOutId> getSelectedInOutIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		return selectedRowIds.toIds(InOutId::ofRepoId);
	}
}
