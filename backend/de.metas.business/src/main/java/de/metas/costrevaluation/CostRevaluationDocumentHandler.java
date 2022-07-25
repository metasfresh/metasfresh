/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.costrevaluation;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.I_M_CostRevaluationLine;
import org.compiere.util.TimeUtil;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CostRevaluationDocumentHandler implements DocumentHandler
{

	private static I_M_CostRevaluation extractForecast(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_M_CostRevaluation.class);
	}

	private final ICostRevaluationDAO costRevaluationDAO = Services.get(ICostRevaluationDAO.class);

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extractForecast(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractForecast(docFields).getCreatedBy();
	}

	@Override
	public LocalDate getDocumentDate(final DocumentTableFields docFields)
	{
		final I_M_CostRevaluation costRevaluation = extractForecast(docFields);
		return TimeUtil.asLocalDate(costRevaluation.getDateAcct());
	}

	@Override
	public String completeIt(final DocumentTableFields docFields)
	{
		final I_M_CostRevaluation costRevaluation = extractForecast(docFields);

		final int costRevaluationId = costRevaluation.getM_CostRevaluation_ID();
		final List<I_M_CostRevaluationLine> lines = costRevaluationDAO.retrieveLinesByCostRevaluationId(costRevaluationId);
		if (lines.size() <= 0)
		{
			throw new AdempiereException("@NoLines@");
		}

		costRevaluation.setDocAction(IDocument.ACTION_None);
		return IDocument.STATUS_Completed;
	}

	@Override
	public void reactivateIt(final DocumentTableFields docFields)
	{
		final I_M_CostRevaluation costRevaluation = extractForecast(docFields);
		costRevaluation.setProcessed(false);
		costRevaluation.setDocAction(IDocument.ACTION_Complete);
	}

	@Override
	public void unCloseIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void closeIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void voidIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void rejectIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void approveIt(final DocumentTableFields docFields)
	{

	}

	@Override
	public File createPDF(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getApprovalAmt(final DocumentTableFields docFields)
	{
		return BigDecimal.ZERO;
	}

	@Override
	public int getC_Currency_ID(final DocumentTableFields docFields)
	{
		return -1;
	}

}
