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
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

@UtilityClass
public class ChangeEDI_ExportStatusHelper
{
	private final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);

	@NonNull
	public List<EDIExportStatus> getAvailableStatuses(@NonNull final EDIExportStatus fromStatus)
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

	public static void save(@NonNull final EDIDesadvId desadvId, @NonNull final EDIExportStatus targetExportStatus)
	{
		final I_EDI_Desadv edi = desadvDAO.retrieveById(desadvId);
		edi.setEDI_ExportStatus(targetExportStatus.getCode());
		edi.setProcessed(ChangeEDI_ExportStatusHelper.computeIsProcessedByTargetExportStatus(EDIExportStatus.ofCode(targetExportStatus.getCode())));
		desadvDAO.save(edi);
	}

}
