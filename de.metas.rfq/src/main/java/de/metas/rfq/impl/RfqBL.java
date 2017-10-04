package de.metas.rfq.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.exceptions.RfQDocumentNotClosedException;
import de.metas.rfq.exceptions.RfQDocumentNotCompleteException;
import de.metas.rfq.exceptions.RfQDocumentNotDraftException;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;
import de.metas.rfq.model.X_C_RfQ;
import de.metas.rfq.model.X_C_RfQResponse;
import de.metas.rfq.model.X_C_RfQResponseLine;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class RfqBL implements IRfqBL
{
	@Override
	public boolean isQuoteAllLines(final I_C_RfQ rfq)
	{
		return X_C_RfQ.QUOTETYPE_QuoteAllLines.equals(rfq.getQuoteType());
	}	// isQuoteAllLines

	@Override
	public boolean isQuoteSelectedLines(final I_C_RfQ rfq)
	{
		return X_C_RfQ.QUOTETYPE_QuoteSelectedLines.equals(rfq.getQuoteType());
	}

	@Override
	public boolean isQuoteTotalAmtOnly(final I_C_RfQ rfq)
	{
		return X_C_RfQ.QUOTETYPE_QuoteTotalOnly.equals(rfq.getQuoteType());
	}	// isQuoteTotalAmtOnly


	@Override
	public boolean isValidAmt(final I_C_RfQResponseLineQty rfqResponseLineQty)
	{
		final BigDecimal priceWithoutDiscount = calculatePriceWithoutDiscount(rfqResponseLineQty);
		if (priceWithoutDiscount == null || priceWithoutDiscount.signum() <= 0)
		{
			return false;
		}

		return true;
	}

	@Override
	public BigDecimal calculatePriceWithoutDiscount(final I_C_RfQResponseLineQty rfqResponseLineQty)
	{
		final BigDecimal price = rfqResponseLineQty.getPrice();
		final BigDecimal discount = rfqResponseLineQty.getDiscount();

		return calculatePriceWithoutDiscount(price, discount);
	}

	private static final BigDecimal calculatePriceWithoutDiscount(final BigDecimal price, final BigDecimal discount)
	{
		// Validate price
		if (price == null || price.signum() <= 0)
		{
			return null;
		}

		// Validate discount
		if (discount != null && discount.abs().compareTo(Env.ONEHUNDRED) > 0)
		{
			return null;
		}

		BigDecimal priceWithoutDiscount = price;

		//
		// Subtract discount:
		// i.e. price = price * (100 - discount) / 100;
		if (discount != null && discount.signum() != 0)
		{
			final BigDecimal factor = Env.ONEHUNDRED.subtract(discount);
			priceWithoutDiscount = price.multiply(factor).divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
		}

		return priceWithoutDiscount;
	}

	@Override
	public void complete(final I_C_RfQResponse rfqResponse)
	{
		Services.get(IDocumentBL.class).processEx(rfqResponse, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	@Override
	public void assertComplete(final I_C_RfQ rfq)
	{
		if (!isCompleted(rfq))
		{
			throw new RfQDocumentNotCompleteException(getSummary(rfq));
		}
	}

	@Override
	public void assertClosed(final I_C_RfQ rfq)
	{
		if (!isClosed(rfq))
		{
			throw new RfQDocumentNotClosedException(getSummary(rfq));
		}
	}

	@Override
	public boolean isCompleted(final I_C_RfQ rfq)
	{
		return X_C_RfQ.DOCSTATUS_Completed.equals(rfq.getDocStatus());
	}

	@Override
	public boolean isClosed(final I_C_RfQ rfq)
	{
		return X_C_RfQ.DOCSTATUS_Closed.equals(rfq.getDocStatus());
	}
	
	@Override
	public void close(final I_C_RfQResponse rfqResponse)
	{
		Services.get(IDocumentBL.class).processEx(rfqResponse, IDocument.ACTION_Close, IDocument.STATUS_Closed);
	}

	@Override
	public void unclose(final I_C_RfQResponse rfqResponse)
	{
		Services.get(IDocumentBL.class).processEx(rfqResponse, IDocument.ACTION_UnClose, IDocument.STATUS_Completed);
	}

	@Override
	public void assertDraft(final I_C_RfQResponse rfqResponse)
	{
		if (!isDraft(rfqResponse))
		{
			throw new RfQDocumentNotDraftException(getSummary(rfqResponse));
		}
	}

	@Override
	public boolean isDraft(final I_C_RfQResponse rfqResponse)
	{
		final String docStatus = rfqResponse.getDocStatus();
		return X_C_RfQResponse.DOCSTATUS_Drafted.equals(docStatus)
				|| X_C_RfQResponse.DOCSTATUS_InProgress.equals(docStatus);
	}

	@Override
	public boolean isDraft(final I_C_RfQResponseLine rfqResponseLine)
	{
		final String docStatus = rfqResponseLine.getDocStatus();
		return X_C_RfQResponseLine.DOCSTATUS_Drafted.equals(docStatus)
				|| X_C_RfQResponseLine.DOCSTATUS_InProgress.equals(docStatus);
	}

	@Override
	public boolean isCompleted(final I_C_RfQResponse rfqResponse)
	{
		return X_C_RfQResponse.DOCSTATUS_Completed.equals(rfqResponse.getDocStatus());
	}

	@Override
	public boolean isCompleted(final I_C_RfQResponseLine rfqResponseLine)
	{
		return X_C_RfQResponseLine.DOCSTATUS_Completed.equals(rfqResponseLine.getDocStatus());
	}

	@Override
	public boolean isClosed(final I_C_RfQResponse rfqResponse)
	{
		return X_C_RfQResponse.DOCSTATUS_Closed.equals(rfqResponse.getDocStatus());
	}

	@Override
	public boolean isClosed(final I_C_RfQResponseLine rfqResponseLine)
	{
		return X_C_RfQResponseLine.DOCSTATUS_Closed.equals(rfqResponseLine.getDocStatus());
	}

	@Override
	public String getSummary(final I_C_RfQ rfq)
	{
		// NOTE: nulls shall be tolerated because the method is used for building exception error messages
		if (rfq == null)
		{
			return "@C_RfQ_ID@ ?";
		}
		return "@C_RfQ_ID@ #" + rfq.getDocumentNo();
	}

	@Override
	public String getSummary(final I_C_RfQResponse rfqResponse)
	{
		// NOTE: nulls shall be tolerated because the method is used for building exception error messages
		if (rfqResponse == null)
		{
			return "@C_RfQResponse_ID@ ?";
		}
		return "@C_RfQResponse_ID@ #" + rfqResponse.getName();
	}

	@Override
	public void updateQtyPromisedAndSave(final I_C_RfQResponseLine rfqResponseLine)
	{
		final BigDecimal qtyPromised = Services.get(IRfqDAO.class).calculateQtyPromised(rfqResponseLine);
		rfqResponseLine.setQtyPromised(qtyPromised);

		InterfaceWrapperHelper.save(rfqResponseLine);
	}

//	@Override
//	public void uncloseInTrx(final I_C_RfQResponse rfqResponse)
//	{
//		if (!isClosed(rfqResponse))
//		{
//			throw new RfQDocumentNotClosedException(getSummary(rfqResponse));
//		}
//
//		//
//		final IRfQEventDispacher rfQEventDispacher = getRfQEventDispacher();
//		rfQEventDispacher.fireBeforeUnClose(rfqResponse);
//
//		//
//		// Mark as NOT closed
//		rfqResponse.setDocStatus(X_C_RfQResponse.DOCSTATUS_Completed);
//		InterfaceWrapperHelper.save(rfqResponse);
//		updateRfQResponseLinesStatus(rfqResponse);
//
//		//
//		rfQEventDispacher.fireAfterUnClose(rfqResponse);
//
//		// Make sure it's saved
//		InterfaceWrapperHelper.save(rfqResponse);
//	}
}
