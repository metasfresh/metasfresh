/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.report;

import de.metas.process.AdProcessId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.print.MPrintFormat;
import org.compiere.util.Env;

@UtilityClass
public class DocumentReportAdvisorUtil
{
	public static AdProcessId getReportProcessIdByPrintFormatId(@NonNull final PrintFormatId printFormatId)
	{
		final MPrintFormat adPrintFormat = MPrintFormat.get(Env.getCtx(), printFormatId.getRepoId(), false);
		if (adPrintFormat == null)
		{
			throw new AdempiereException("No print format found for AD_PrintFormat_ID=" + printFormatId);
		}

		final AdProcessId reportProcessId = AdProcessId.ofRepoIdOrNull(adPrintFormat.getJasperProcess_ID());
		if (reportProcessId == null)
		{
			throw new AdempiereException("No report process found");
		}

		return reportProcessId;
	}

	public static int getDocumentCopies(
			@NonNull final I_C_BPartner bpartner,
			@NonNull final I_C_DocType docType)
	{
		return getDocumentCopies(docType, 0) + getDocumentCopies(bpartner, 1); // for now, preserving the legacy logic
	}

	public static Integer getDocumentCopies(@NonNull final I_C_BPartner bpartner, final Integer defaultValue)
	{
		return !InterfaceWrapperHelper.isNull(bpartner, I_C_BPartner.COLUMNNAME_DocumentCopies)
				? bpartner.getDocumentCopies()
				: defaultValue;
	}

	public static Integer getDocumentCopies(@NonNull final I_C_DocType docType, final Integer defaultValue)
	{
		return !InterfaceWrapperHelper.isNull(docType, I_C_BPartner.COLUMNNAME_DocumentCopies)
				? docType.getDocumentCopies()
				: defaultValue;
	}
}
