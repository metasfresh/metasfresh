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
import de.metas.i18n.AdMessageKey;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;

class CostRevaluationDocumentHandler implements DocumentHandler
{
	static final AdMessageKey MSG_CannotVoidBookedRevaluation = AdMessageKey.of("M_CostRevaluation.CannotVoidBookedRevaluation");
	static final AdMessageKey MSG_CopyFromCostElementCannotBeVoided = AdMessageKey.of("M_CostRevaluation.CopyFromCostElementCannotBeVoided");

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
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
	public LocalDate getDocumentDate(final DocumentTableFields docFields)
	{
		final I_M_CostRevaluation record = extractRecord(docFields);
		return TimeUtil.asLocalDate(record.getDateAcct(), orgDAO.getTimeZone(OrgId.ofRepoId(record.getAD_Org_ID())));
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

		// Evaluate all lines again, also those already evaluated by "Run": the stock, the current cost price or the new cost price may have changed since then
		costRevaluationService.reevaluateAllLines(costRevaluationId);

		costRevaluation.setDocAction(IDocument.ACTION_None);
		return DocStatus.Completed.getCode();
	}

	/**
	 * Voids a {@code Manual} revaluation none of whose lines has its revaluation cost detail (nothing was booked, e.g. its posting failed).
	 * A {@code CopyFromCostElement} revaluation cannot be voided; it is corrected by Reverse.
	 */
	@Override
	public void voidIt(final DocumentTableFields docFields)
	{
		final I_M_CostRevaluation costRevaluation = extractRecord(docFields);
		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(costRevaluation.getM_CostRevaluation_ID());
		if (!costRevaluationService.getById(costRevaluationId).getRevaluationSource().isManual())
		{
			throw new AdempiereException(MSG_CopyFromCostElementCannotBeVoided);
		}

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(costRevaluation.getDocStatus());
		if (docStatus.isClosedReversedOrVoided())
		{
			throw new AdempiereException("Invalid document status: " + docStatus);
		}

		if (costRevaluationService.hasAnyLineWithCostDetail(costRevaluationId))
		{
			throw new AdempiereException(MSG_CannotVoidBookedRevaluation);
		}

		costRevaluation.setProcessed(true);
		costRevaluation.setDocAction(IDocument.ACTION_None);
	}

	@Override
	public void reverseCorrectIt(final DocumentTableFields docFields)
	{
		final I_M_CostRevaluation costRevaluation = extractRecord(docFields);

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(costRevaluation.getDocStatus());
		if (!docStatus.isCompleted())
		{
			throw new AdempiereException("Only completed documents can be reversed. Current status: " + docStatus);
		}

		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(costRevaluation.getM_CostRevaluation_ID());
		costRevaluationService.reverseDetails(costRevaluationId);

		costRevaluation.setDocAction(IDocument.ACTION_None);
	}
}
