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

import com.google.common.collect.ImmutableList;
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.IDesadvDAO;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;

@UtilityClass
public class ChangeEDI_ExportStatusHelper
{
	private final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);
	private final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

	@NonNull
	public List<EDIExportStatus> getAvailableTargetExportStatuses(@NonNull final EDIExportStatus fromStatus)
	{
		switch (fromStatus)
		{
			case DontSend:
				return ImmutableList.of(EDIExportStatus.Pending);
			case Pending:
				return ImmutableList.of(EDIExportStatus.DontSend);
			case Sent:
				return ImmutableList.of(EDIExportStatus.Pending, EDIExportStatus.DontSend);
			default:
				return ImmutableList.of();
		}
	}

	public boolean computeIsProcessedByTargetExportStatus(@NonNull final EDIExportStatus targetExportStatus)
	{
		switch (targetExportStatus)
		{
			case DontSend:
				return true;
			case Pending:
				return false;
			default:
				throw new AdempiereException("Cannot change Export Status to: " + targetExportStatus);
		}
	}

	public static void EDI_DesadvDoIt(@NonNull final EDIDesadvId desadvId, @NonNull final EDIExportStatus targetExportStatus, final boolean isProcessed)
	{
		final I_EDI_Desadv edi = desadvDAO.retrieveById(desadvId);
		edi.setEDI_ExportStatus(targetExportStatus.getCode());
		edi.setProcessed(isProcessed);
		desadvDAO.save(edi);
	}

	@NonNull
	public LookupValuesList computeTargetExportStatusLookupValues(final EDIExportStatus fromExportStatus)
	{
		final List<EDIExportStatus> availableTargetStatuses = ChangeEDI_ExportStatusHelper.getAvailableTargetExportStatuses(fromExportStatus);

		return availableTargetStatuses.stream()
				.map(s -> LookupValue.StringLookupValue.of(s.getCode(), adReferenceDAO.retrieveListNameTranslatableString(EDIExportStatus.AD_Reference_ID, s.getCode())))
				.collect(LookupValuesList.collect());
	}

	@Nullable
	public Object computeParameterDefaultValue(final EDIExportStatus fromExportStatus)
	{
		final List<EDIExportStatus> availableTargetStatuses = ChangeEDI_ExportStatusHelper.getAvailableTargetExportStatuses(fromExportStatus);
		if (!availableTargetStatuses.isEmpty())
		{
			final String code = availableTargetStatuses.get(0).getCode();
			return LookupValue.StringLookupValue.of(code, adReferenceDAO.retrieveListNameTranslatableString(EDIExportStatus.AD_Reference_ID, code));
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

}
