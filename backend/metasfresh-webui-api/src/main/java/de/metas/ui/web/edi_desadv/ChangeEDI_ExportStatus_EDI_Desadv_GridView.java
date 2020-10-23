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

import com.google.common.collect.ImmutableSet;
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.IDesadvDAO;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.Services;
import org.adempiere.ad.service.IADReferenceDAO;

import javax.annotation.Nullable;
import java.util.List;

public class ChangeEDI_ExportStatus_EDI_Desadv_GridView
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);
	private final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

	protected static final String PARAM_TargetExportStatus = I_EDI_Desadv.COLUMNNAME_EDI_ExportStatus;
	@Param(parameterName = PARAM_TargetExportStatus)
	private String p_TargetExportStatus;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ImmutableSet<EDIDesadvId> desadvIds = getSelectedEdiDesadvIds();
		if (desadvIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		// TODO tbp: this is N+1. how to fix?
		EDIExportStatus commonFromStatus = null;
		for (final EDIDesadvId id : desadvIds)
		{
			final I_EDI_Desadv desadv = desadvDAO.retrieveById(id);
			final EDIExportStatus fromExportStatus = EDIExportStatus.ofCode(desadv.getEDI_ExportStatus());

			if (ChangeEDI_ExportStatusHelper.getAvailableStatuses(fromExportStatus).isEmpty())
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("Cannot change ExportStatus from the current one: " + fromExportStatus);
			}

			if (commonFromStatus == null)
			{
				commonFromStatus = fromExportStatus;
			}

			if (!commonFromStatus.equals(fromExportStatus))
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("All rows must have the same EDI ExportStatus");
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_TargetExportStatus, numericKey = false, lookupSource = LookupSource.list)
	private LookupValuesList getTargetExportStatusLookupValues(final LookupDataSourceContext context)
	{
		final I_EDI_Desadv desadv = desadvDAO.retrieveById(getSelectedEdiDesadvIds().iterator().next());

		final EDIExportStatus fromExportStatus = EDIExportStatus.ofCode(desadv.getEDI_ExportStatus());

		// // TODO tbp: remove hardcoded
		// final EDIExportStatus fromExportStatus = EDIExportStatus.Sent;

		final List<EDIExportStatus> availableTargetStatuses = ChangeEDI_ExportStatusHelper.getAvailableStatuses(fromExportStatus);

		return availableTargetStatuses.stream()
				.map(s -> LookupValue.StringLookupValue.of(s.getCode(), adReferenceDAO.retrieveListNameTranslatableString(EDIExportStatus.AD_Reference_ID, s.getCode())))
				.collect(LookupValuesList.collect());
	}

	@Override
	@Nullable
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final I_EDI_Desadv desadv = desadvDAO.retrieveById(getSelectedEdiDesadvIds().iterator().next());

		final EDIExportStatus fromExportStatus = EDIExportStatus.ofCode(desadv.getEDI_ExportStatus());

		// // TODO tbp: remove hardcoded
		// final EDIExportStatus fromExportStatus = EDIExportStatus.Sent;

		final List<EDIExportStatus> availableTargetStatuses = ChangeEDI_ExportStatusHelper.getAvailableStatuses(fromExportStatus);
		if (!availableTargetStatuses.isEmpty())
		{
			final String code = availableTargetStatuses.get(0).getCode();
			return LookupValue.StringLookupValue.of(code, adReferenceDAO.retrieveListNameTranslatableString(EDIExportStatus.AD_Reference_ID, code));
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	protected String doIt() throws Exception
	{
		for (final EDIDesadvId desadvId : getSelectedEdiDesadvIds())
		{
			ChangeEDI_ExportStatusHelper.save(desadvId, EDIExportStatus.ofCode(p_TargetExportStatus));
		}

		return MSG_OK;
	}

	private ImmutableSet<EDIDesadvId> getSelectedEdiDesadvIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		return selectedRowIds.toIds(EDIDesadvId::ofRepoId);
	}
}
