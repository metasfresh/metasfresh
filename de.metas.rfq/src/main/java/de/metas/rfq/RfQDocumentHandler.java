package de.metas.rfq;

import java.io.File;
import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.rfq.event.IRfQEventDispacher;
import de.metas.rfq.exceptions.NoRfQLinesFoundException;
import de.metas.rfq.exceptions.RfQDocumentClosedException;
import de.metas.rfq.exceptions.RfQLineInvalidException;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLine;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.X_C_RfQ;

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

class RfQDocumentHandler implements DocumentHandler
{
	// services
	private final transient IRfQEventDispacher rfqEventDispacher = Services.get(IRfQEventDispacher.class);
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);
	private final transient IRfQConfiguration rfqConfiguration = Services.get(IRfQConfiguration.class);

	private static final I_C_RfQ extractRfQ(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_C_RfQ.class);
	}

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extractRfQ(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractRfQ(docFields).getSalesRep_ID();
	}

	@Override
	public int getC_Currency_ID(final DocumentTableFields docFields)
	{
		return extractRfQ(docFields).getC_Currency_ID();
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
		final I_C_RfQ rfq = extractRfQ(docFields);

		//
		// Fire event: before complete
		rfqEventDispacher.fireBeforeComplete(rfq);

		//
		// If we require quoting only the total amount then:
		// * make sure there are no C_RfQLineQty records
		if (rfqBL.isQuoteTotalAmtOnly(rfq))
		{
			// Need to check Line Qty
			for (final I_C_RfQLine rfqLine : rfqDAO.retrieveLines(rfq))
			{
				final int rfqLineQtyCount = rfqDAO.retrieveLineQtysCount(rfqLine);
				if (rfqLineQtyCount > 1)
				{
					throw new RfQLineInvalidException(rfqLine, "@C_RfQLineQty_ID@ #" + rfqLineQtyCount + " - @IsQuoteTotalAmt@");
				}
			}
		}
		else
		{
			final int rfqLinesCount = rfqDAO.retrieveLinesCount(rfq);
			if (rfqLinesCount <= 0)
			{
				throw new NoRfQLinesFoundException(rfq);
			}
		}

		// Mark completed
		rfq.setDocStatus(X_C_RfQ.DOCSTATUS_Completed);
		rfq.setDocAction(X_C_RfQ.DOCACTION_Close);
		rfq.setProcessed(true);
		rfq.setIsRfQResponseAccepted(true);
		InterfaceWrapperHelper.save(rfq);

		//
		// Generate RfQ Responses
		rfqConfiguration.newRfQResponsesProducerFor(rfq)
				.setC_RfQ(rfq)
				.setPublish(false) // do not publish them by default
				.create();

		//
		// Fire event: after complete
		rfqEventDispacher.fireAfterComplete(rfq);

		// Make sure everything was saved
		InterfaceWrapperHelper.save(rfq);

		return rfq.getDocStatus();
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
		final I_C_RfQ rfq = extractRfQ(docFields);

		//
		rfqEventDispacher.fireBeforeClose(rfq);

		//
		// Mark as closed
		rfq.setDocStatus(X_C_RfQ.DOCSTATUS_Closed);
		rfq.setDocAction(X_C_RfQ.DOCACTION_None);
		rfq.setProcessed(true);
		rfq.setIsRfQResponseAccepted(false);
		InterfaceWrapperHelper.save(rfq);

		//
		// Close RfQ Responses
		for (final I_C_RfQResponse rfqResponse : rfqDAO.retrieveAllResponses(rfq))
		{
			if (!rfqBL.isDraft(rfqResponse))
			{
				continue;
			}

			rfqBL.complete(rfqResponse);
		}

		//
		rfqEventDispacher.fireAfterClose(rfq);

		// Make sure it's saved
		InterfaceWrapperHelper.save(rfq);
	}

	@Override
	public void unCloseIt(final DocumentTableFields docFields)
	{
		final I_C_RfQ rfq = extractRfQ(docFields);

		//
		rfqEventDispacher.fireBeforeUnClose(rfq);

		//
		// Mark as completed
		rfq.setDocStatus(X_C_RfQ.DOCSTATUS_Completed);
		rfq.setDocAction(X_C_RfQ.DOCACTION_Close);
		InterfaceWrapperHelper.save(rfq);

		//
		// UnClose RfQ Responses
		for (final I_C_RfQResponse rfqResponse : rfqDAO.retrieveAllResponses(rfq))
		{
			if (!rfqBL.isClosed(rfqResponse))
			{
				continue;
			}

			rfqBL.unclose(rfqResponse);
		}

		//
		rfqEventDispacher.fireAfterUnClose(rfq);

		// Make sure it's saved
		InterfaceWrapperHelper.save(rfq);
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
		final I_C_RfQ rfq = extractRfQ(docFields);

		//
		// Void and delete all responses
		for (final I_C_RfQResponse rfqResponse : rfqDAO.retrieveAllResponses(rfq))
		{
			voidAndDelete(rfqResponse);
		}

		rfq.setIsRfQResponseAccepted(false);
		rfq.setDocAction(IDocument.ACTION_Complete);
		rfq.setProcessed(false);
	}

	private void voidAndDelete(final I_C_RfQResponse rfqResponse)
	{
		// Prevent deleting/voiding an already closed RfQ response
		if (rfqBL.isClosed(rfqResponse))
		{
			throw new RfQDocumentClosedException(rfqBL.getSummary(rfqResponse));
		}

		// TODO: FRESH-402 shall we throw exception if the rfqResponse was published?

		rfqResponse.setProcessed(false);
		InterfaceWrapperHelper.delete(rfqResponse);
	}

}
