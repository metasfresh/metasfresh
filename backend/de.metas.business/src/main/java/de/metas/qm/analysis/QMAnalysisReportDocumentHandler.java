/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.qm.analysis;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_QM_Analysis_Report;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;

public class QMAnalysisReportDocumentHandler implements DocumentHandler
{

	@Override
	public String getSummary(@NonNull final DocumentTableFields docFields)
	{
		return extractQMAnalysisReport(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(@NonNull final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(@NonNull final DocumentTableFields docFields)
	{
		return extractQMAnalysisReport(docFields).getCreatedBy();
	}

	@Override
	public LocalDate getDocumentDate(@NonNull final DocumentTableFields docFields)
	{
		return TimeUtil.asLocalDate(extractQMAnalysisReport(docFields).getDateDoc());
	}

	/**
	 * Sets the <code>Processed</code> flag of the given <code>dunningDoc</code> lines.
	 * <p>
	 * Assumes that the given doc is not yet processed.
	 */
	@Override
	public String completeIt(@NonNull final DocumentTableFields docFields)
	{
		final I_QM_Analysis_Report analysisReport = extractQMAnalysisReport(docFields);
		analysisReport.setDocAction(IDocument.ACTION_ReActivate);

		if (analysisReport.isProcessed())
		{
			throw new AdempiereException("@Processed@=@Y@");
		}

		analysisReport.setProcessed(true);

		return IDocument.STATUS_Completed;
	}

	@Override
	public void voidIt(final DocumentTableFields docFields)
	{
		final I_QM_Analysis_Report analysisReport = extractQMAnalysisReport(docFields);

		analysisReport.setProcessed(true);
		analysisReport.setDocAction(IDocument.ACTION_None);
	}

	@Override
	public void reactivateIt(@NonNull final DocumentTableFields docFields)
	{
		final I_QM_Analysis_Report analysisReport = extractQMAnalysisReport(docFields);
		analysisReport.setProcessed(false);
		analysisReport.setDocAction(IDocument.ACTION_Complete);
	}

	private static I_QM_Analysis_Report extractQMAnalysisReport(@NonNull final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_QM_Analysis_Report.class);
	}
}
