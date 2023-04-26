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

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_CostRevaluation;

import java.io.File;
import java.math.BigDecimal;

class CostRevaluationDocumentHandler implements DocumentHandler
{
	private final CostRevaluationService costRevaluationService;

	CostRevaluationDocumentHandler(
			@NonNull final CostRevaluationService costRevaluationService)
	{
		this.costRevaluationService = costRevaluationService;
	}

	private static I_M_CostRevaluation extractRecord(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_M_CostRevaluation.class);
	}

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extractRecord(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractRecord(docFields).getCreatedBy();
	}

	@Override
	public InstantAndOrgId getDocumentDate(final DocumentTableFields docFields)
	{
		final I_M_CostRevaluation record = extractRecord(docFields);
		return InstantAndOrgId.ofTimestamp(record.getDateAcct(), OrgId.ofRepoId(record.getAD_Org_ID()));
	}

	@Override
	public String completeIt(final DocumentTableFields docFields)
	{
		final I_M_CostRevaluation costRevaluation = extractRecord(docFields);

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(costRevaluation.getDocStatus());
		if (!docStatus.isDraftedOrInProgress())
		{
			throw new AdempiereException("Invalid document status");
		}

		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(costRevaluation.getM_CostRevaluation_ID());
		if (!costRevaluationService.hasActiveLines(costRevaluationId))
		{
			throw new AdempiereException("@NoLines@");
		}

		// Make sure all lines are evaluated
		costRevaluationService.createDetails(costRevaluationId);

		costRevaluation.setDocAction(IDocument.ACTION_None);
		return IDocument.STATUS_Completed;
	}

	@Override
	public void reactivateIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
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
