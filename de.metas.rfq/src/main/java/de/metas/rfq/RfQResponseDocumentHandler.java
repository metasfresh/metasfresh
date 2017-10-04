package de.metas.rfq;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.rfq.event.IRfQEventDispacher;
import de.metas.rfq.exceptions.RfQResponseInvalidException;
import de.metas.rfq.exceptions.RfQResponseLineInvalidException;
import de.metas.rfq.exceptions.RfQResponseLineQtyInvalidException;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;
import de.metas.rfq.model.X_C_RfQResponse;

/*
 * #%L
 * de.metas.rfq
 * %%
 * Copyright (C) 2017 metas GmbH
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

class RfQResponseDocumentHandler implements DocumentHandler
{
	// services
	private final transient IRfQEventDispacher rfqEventDispacher = Services.get(IRfQEventDispacher.class);
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);

	private static final I_C_RfQResponse extractRfQResponse(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_C_RfQResponse.class);
	}

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extractRfQResponse(docFields).getName();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractRfQResponse(docFields).getC_RfQ().getSalesRep_ID();
	}

	@Override
	public int getC_Currency_ID(final DocumentTableFields docFields)
	{
		return extractRfQResponse(docFields).getC_Currency_ID();
	}

	@Override
	public BigDecimal getApprovalAmt(final DocumentTableFields docFields)
	{
		return BigDecimal.ZERO;
	}

	@Override
	public File createPDF(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String prepareIt(final DocumentTableFields docFields)
	{
		return IDocument.STATUS_InProgress;
	}

	@Override
	public String completeIt(final DocumentTableFields docFields)
	{
		final I_C_RfQResponse rfqResponse = extractRfQResponse(docFields);
		final List<I_C_RfQResponseLine> rfqResponseLines = rfqDAO.retrieveResponseLines(rfqResponse);

		validateBeforeComplete(rfqResponse, rfqResponseLines);

		//
		// Fire event: before complete
		rfqEventDispacher.fireBeforeComplete(rfqResponse);

		//
		// Mark as complete
		rfqResponse.setDocStatus(X_C_RfQResponse.DOCSTATUS_Completed);
		rfqResponse.setDocAction(X_C_RfQResponse.DOCACTION_Close);
		rfqResponse.setProcessed(true);
		InterfaceWrapperHelper.save(rfqResponse);
		updateRfQResponseLinesStatus(rfqResponse, rfqResponseLines);

		//
		// Fire event: after complete
		rfqEventDispacher.fireAfterComplete(rfqResponse);

		// Make sure everything was saved
		InterfaceWrapperHelper.save(rfqResponse);

		return X_C_RfQResponse.DOCSTATUS_Completed;
	}

	private void validateBeforeComplete(final I_C_RfQResponse rfqResponse, final List<I_C_RfQResponseLine> rfqResponseLines)
	{
		final I_C_RfQ rfq = rfqResponse.getC_RfQ();
		rfqBL.assertClosed(rfq);

		//
		// Fire event: before complete
		rfqEventDispacher.fireBeforeComplete(rfqResponse);

		//
		// Validate Quote Total Amt
		if (rfq.isQuoteTotalAmt() || rfqBL.isQuoteTotalAmtOnly(rfq))
		{
			final BigDecimal totalAmt = rfqResponse.getPrice();
			if (totalAmt == null || totalAmt.signum() <= 0)
			{
				throw new RfQResponseInvalidException(rfqResponse, "@Price@ <= 0");
			}
		}

		//
		// Validate response lines
		final boolean isQuoteAllLines = rfqBL.isQuoteAllLines(rfq);
		final boolean isQuoteAllQty = rfq.isQuoteAllQty();
		for (final I_C_RfQResponseLine rfqResponseLine : rfqResponseLines)
		{
			//
			// If all lines and qtys shall be quoted, make sure this is respected
			if (isQuoteAllLines)
			{
				if (!rfqResponseLine.isActive())
				{
					throw new RfQResponseLineInvalidException(rfqResponseLine, "@IsActive@=@N@");
				}

				final List<I_C_RfQResponseLineQty> rfqResponseLineQtys = rfqDAO.retrieveResponseQtys(rfqResponseLine);
				if (!isAnyValidAmt(rfqResponseLineQtys))
				{
					throw new RfQResponseLineInvalidException(rfqResponseLine, "No amount or amount is not valid");
				}
			}

			//
			// Do we have an amount for all line qtys
			if (isQuoteAllQty)
			{
				final List<I_C_RfQResponseLineQty> rfqResponseLineQtys = rfqDAO.retrieveResponseQtys(rfqResponseLine);
				for (final I_C_RfQResponseLineQty rfqResponseLineQty : rfqResponseLineQtys)
				{
					if (!rfqResponseLineQty.isActive())
					{
						throw new RfQResponseLineQtyInvalidException(rfqResponseLineQty, "@IsActive@=@N@");
					}

					if (!rfqBL.isValidAmt(rfqResponseLineQty))
					{
						throw new RfQResponseLineQtyInvalidException(rfqResponseLineQty, "@Invalid@ @Amount@");
					}
				}
			}
		}
	}

	private boolean isAnyValidAmt(final List<I_C_RfQResponseLineQty> rfqResponseLineQtys)
	{
		for (final I_C_RfQResponseLineQty rfqResponseLineQty : rfqResponseLineQtys)
		{
			if (rfqBL.isValidAmt(rfqResponseLineQty))
			{
				return true;
			}
		}

		return false;
	}

	private final void updateRfQResponseLinesStatus(final I_C_RfQResponse rfqResponse)
	{
		final List<I_C_RfQResponseLine> rfqResponseLines = rfqDAO.retrieveResponseLines(rfqResponse);
		updateRfQResponseLinesStatus(rfqResponse, rfqResponseLines);
	}

	private static final void updateRfQResponseLinesStatus(final I_C_RfQResponse rfqResponse, final List<I_C_RfQResponseLine> rfqResponseLines)
	{
		for (final I_C_RfQResponseLine rfqResponseLine : rfqResponseLines)
		{
			rfqResponseLine.setDocStatus(rfqResponse.getDocStatus());
			rfqResponseLine.setProcessed(rfqResponse.isProcessed());
			InterfaceWrapperHelper.save(rfqResponseLine);
		}
	}

	@Override
	public void approveIt(final DocumentTableFields docFields)
	{
	}

	@Override
	public void rejectIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void voidIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void closeIt(final DocumentTableFields docFields)
	{
		final I_C_RfQResponse rfqResponse = extractRfQResponse(docFields);

		rfqEventDispacher.fireBeforeClose(rfqResponse);

		//
		// Mark as closed
		rfqResponse.setDocStatus(X_C_RfQResponse.DOCSTATUS_Closed);
		rfqResponse.setDocAction(X_C_RfQResponse.DOCACTION_None);
		rfqResponse.setProcessed(true);
		InterfaceWrapperHelper.save(rfqResponse);
		updateRfQResponseLinesStatus(rfqResponse);

		//
		rfqEventDispacher.fireAfterClose(rfqResponse);

		// Make sure it's saved
		InterfaceWrapperHelper.save(rfqResponse);
	}

	@Override
	public void unCloseIt(final DocumentTableFields docFields)
	{
		final I_C_RfQResponse rfqResponse = extractRfQResponse(docFields);

		//
		rfqEventDispacher.fireBeforeUnClose(rfqResponse);

		//
		// Mark as NOT closed
		rfqResponse.setDocStatus(X_C_RfQResponse.DOCSTATUS_Completed);
		InterfaceWrapperHelper.save(rfqResponse);
		updateRfQResponseLinesStatus(rfqResponse);

		//
		rfqEventDispacher.fireAfterUnClose(rfqResponse);

		// Make sure it's saved
		InterfaceWrapperHelper.save(rfqResponse);
	}

	@Override
	public void reverseCorrectIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void reverseAccrualIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void reactivateIt(final DocumentTableFields docFields)
	{
		final I_C_RfQResponse rfqResponse = extractRfQResponse(docFields);

		rfqResponse.setDocAction(IDocument.ACTION_Complete);
		rfqResponse.setDocAction(IDocument.ACTION_Complete);
		rfqResponse.setProcessed(false);
		updateRfQResponseLinesStatus(rfqResponse);
	}
}
