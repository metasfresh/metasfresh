package de.metas.rfq.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.rfq.IRfQConfiguration;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.event.IRfQEventDispacher;
import de.metas.rfq.exceptions.NoRfQLinesFoundException;
import de.metas.rfq.exceptions.RfQDocumentClosedException;
import de.metas.rfq.exceptions.RfQDocumentNotCompleteException;
import de.metas.rfq.exceptions.RfQDocumentNotDraftException;
import de.metas.rfq.exceptions.RfQLineInvalidException;
import de.metas.rfq.exceptions.RfQResponseInvalidException;
import de.metas.rfq.exceptions.RfQResponseLineInvalidException;
import de.metas.rfq.exceptions.RfQResponseLineQtyInvalidException;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLine;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;
import de.metas.rfq.model.X_C_RfQ;

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
	// private static final Logger logger = LogManager.getLogger(RfqBL.class);

	private boolean isQuoteAllLines(final I_C_RfQ rfq)
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

	public boolean isAnyValidAmt(final List<I_C_RfQResponseLineQty> rfqResponseLineQtys)
	{
		for (final I_C_RfQResponseLineQty rfqResponseLineQty : rfqResponseLineQtys)
		{
			if (isValidAmt(rfqResponseLineQty))
			{
				return true;
			}
		}

		return false;
	}

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
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(ITrx.TRXNAME_ThreadInherited, new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				InterfaceWrapperHelper.setTrxName(rfqResponse, ITrx.TRXNAME_ThreadInherited);
				completeInTrx(rfqResponse);
			}
		});
	}

	private void completeInTrx(final I_C_RfQResponse rfqResponse)
	{
		assertDraft(rfqResponse);

		final I_C_RfQ rfq = rfqResponse.getC_RfQ();
		assertComplete(rfq);

		//
		// Fire event: before complete
		final IRfQEventDispacher rfqEventDispacher = Services.get(IRfQEventDispacher.class);
		rfqEventDispacher.fireBeforeComplete(rfqResponse);

		//
		// Validate Quote Total Amt
		if (rfq.isQuoteTotalAmt() || isQuoteTotalAmtOnly(rfq))
		{
			final BigDecimal totalAmt = rfqResponse.getPrice();
			if (totalAmt == null || totalAmt.signum() <= 0)
			{
				throw new RfQResponseInvalidException(rfqResponse, "@Price@ <= 0");
			}
		}

		//
		// Validate response lines
		final boolean isQuoteAllLines = isQuoteAllLines(rfq);
		final boolean isQuoteAllQty = rfq.isQuoteAllQty();
		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
		final List<I_C_RfQResponseLine> rfqResponseLines = rfqDAO.retrieveResponseLines(rfqResponse);
		for (final I_C_RfQResponseLine rfqResponseLine : rfqResponseLines)
		{
			final List<I_C_RfQResponseLineQty> rfqResponseLineQtys = rfqDAO.retrieveResponseQtys(rfqResponseLine);

			//
			// If all lines and qtys shall be quoted, make sure this is respected
			if (isQuoteAllLines)
			{
				if (!rfqResponseLine.isActive())
				{
					throw new RfQResponseLineInvalidException(rfqResponseLine, "@IsActive@=@N@");
				}
				if (!isAnyValidAmt(rfqResponseLineQtys))
				{
					throw new RfQResponseLineInvalidException(rfqResponseLine, "No amount or amount is not valid");
				}
			}

			//
			// Do we have an amount for all line qtys
			if (isQuoteAllQty)
			{
				for (final I_C_RfQResponseLineQty rfqResponseLineQty : rfqResponseLineQtys)
				{
					if (!rfqResponseLineQty.isActive())
					{
						throw new RfQResponseLineQtyInvalidException(rfqResponseLineQty, "@IsActive@=@N@");
					}

					if (!isValidAmt(rfqResponseLineQty))
					{
						throw new RfQResponseLineQtyInvalidException(rfqResponseLineQty, "@Invalid@ @Amount@");
					}
				}
			}
		}

		//
		// Mark as complete
		rfqResponse.setProcessed(true);
		rfqResponse.setIsComplete(true);
		InterfaceWrapperHelper.save(rfqResponse);

		//
		// Mark lines as complete
		for (final I_C_RfQResponseLine rfqResponseLine : rfqResponseLines)
		{
			rfqResponseLine.setProcessed(true);
			InterfaceWrapperHelper.save(rfqResponseLine);
		}

		//
		// Fire event: after complete
		rfqEventDispacher.fireAfterComplete(rfqResponse);

		// Make sure everything was saved
		InterfaceWrapperHelper.save(rfqResponse);
	}

	private final IRfQEventDispacher getRfQEventDispacher()
	{
		return Services.get(IRfQEventDispacher.class);
	}

	@Override
	public void complete(final I_C_RfQ rfq)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(ITrx.TRXNAME_ThreadInherited, new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				InterfaceWrapperHelper.setTrxName(rfq, ITrx.TRXNAME_ThreadInherited);
				completeInTrx(rfq);
			}
		});
	}

	private void completeInTrx(final I_C_RfQ rfq)
	{
		assertDraft(rfq);

		//
		// Fire event: before complete
		final IRfQEventDispacher rfqEventDispacher = getRfQEventDispacher();
		rfqEventDispacher.fireBeforeComplete(rfq);

		// services
		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);

		//
		// If we require quoting only the total amount then:
		// * make sure there are no C_RfQLineQty records
		if (isQuoteTotalAmtOnly(rfq))
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
		rfq.setProcessed(true);
		rfq.setIsClosed(false);
		InterfaceWrapperHelper.save(rfq);

		//
		// Generate RfQ Responses
		Services.get(IRfQConfiguration.class).newRfQResponsesProducerFor(rfq)
				.setC_RfQ(rfq)
				.setPublish(false) // do not publish them by default
				.create();

		//
		// Fire event: after complete
		rfqEventDispacher.fireAfterComplete(rfq);

		// Make sure everything was saved
		InterfaceWrapperHelper.save(rfq);
	}

	@Override
	public void reActivate(final I_C_RfQ rfq)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(ITrx.TRXNAME_ThreadInherited, new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				InterfaceWrapperHelper.setTrxName(rfq, ITrx.TRXNAME_ThreadInherited);
				reActivateInTrx(rfq);
			}
		});
	}

	private void reActivateInTrx(final I_C_RfQ rfq)
	{
		assertComplete(rfq);

		// services
		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);

		//
		// Void and delete all responses
		for (final I_C_RfQResponse rfqResponse : rfqDAO.retrieveAllResponses(rfq))
		{
			voidAndDelete(rfqResponse);
		}

		//
		// Mark as not processed
		rfq.setProcessed(false);
		rfq.setIsClosed(false);
		InterfaceWrapperHelper.save(rfq);
	}

	private void voidAndDelete(final I_C_RfQResponse rfqResponse)
	{
		// Prevent deleting/voiding an already closed RfQ response
		if (isClosed(rfqResponse))
		{
			throw new RfQDocumentClosedException(getSummary(rfqResponse));
		}
		
		// TODO: FRESH-402 shall we throw exception if the rfqResponse was published?

		rfqResponse.setProcessed(false);
		InterfaceWrapperHelper.delete(rfqResponse);
	}

	@Override
	public void assertDraft(final I_C_RfQ rfq)
	{
		if (!isDraft(rfq))
		{
			throw new RfQDocumentNotDraftException(getSummary(rfq));
		}
	}

	@Override
	public boolean isDraft(final I_C_RfQ rfq)
	{
		return !rfq.isProcessed();
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
	public boolean isCompleted(final I_C_RfQ rfq)
	{
		return rfq.isProcessed() && !rfq.isClosed();
	}

	@Override
	public boolean isClosed(final I_C_RfQ rfq)
	{
		return rfq.isClosed();
	}

	@Override
	public void close(final I_C_RfQ rfq)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(ITrx.TRXNAME_ThreadInherited, new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				InterfaceWrapperHelper.setTrxName(rfq, ITrx.TRXNAME_ThreadInherited);
				closeInTrx(rfq);
			}
		});
	}

	private void closeInTrx(final I_C_RfQ rfq)
	{
		assertComplete(rfq);

		getRfQEventDispacher().fireBeforeClose(rfq);

		//
		// Close RfQ
		rfq.setProcessed(true);
		rfq.setIsClosed(true);
		InterfaceWrapperHelper.save(rfq);

		//
		// Close RfQ Responses
		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
		for (final I_C_RfQResponse rfqResponse : rfqDAO.retrieveAllResponses(rfq))
		{
			close(rfqResponse);
		}

		getRfQEventDispacher().fireAfterClose(rfq);

		InterfaceWrapperHelper.save(rfq);
	}

	private void close(final I_C_RfQResponse rfqResponse)
	{
		if (!isDraft(rfqResponse) && !isCompleted(rfqResponse))
		{
			throw new RfQDocumentNotCompleteException(getSummary(rfqResponse));
		}

		rfqResponse.setProcessed(true);
		rfqResponse.setIsClosed(true);
		InterfaceWrapperHelper.save(rfqResponse);

		//
		// Close lines
		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
		final List<I_C_RfQResponseLine> rfqResponseLines = rfqDAO.retrieveResponseLines(rfqResponse);
		for (final I_C_RfQResponseLine rfqReponseLine : rfqResponseLines)
		{
			rfqReponseLine.setProcessed(true);
			rfqReponseLine.setIsClosed(true);
			InterfaceWrapperHelper.save(rfqReponseLine);
		}
	}

	@Override
	public void assertDraft(final I_C_RfQResponse rfqResponse)
	{
		if (!isDraft(rfqResponse))
		{
			throw new RfQDocumentNotDraftException(getSummary(rfqResponse));
		}
	}

	public boolean isDraft(final I_C_RfQResponse rfqResponse)
	{
		return !rfqResponse.isProcessed();
	}

	public boolean isCompleted(final I_C_RfQResponse rfqResponse)
	{
		return rfqResponse.isComplete() && !rfqResponse.isClosed();
	}

	@Override
	public boolean isClosed(final I_C_RfQResponse rfqResponse)
	{
		return rfqResponse.isClosed();
	}

	@Override
	public boolean isClosed(final I_C_RfQResponseLine rfqResponseLine)
	{
		return rfqResponseLine.isClosed();
	}

	private String getSummary(final I_C_RfQ rfq)
	{
		return "@C_RfQ_ID@ #" + rfq.getDocumentNo();
	}

	private String getSummary(final I_C_RfQResponse rfqResponse)
	{
		return "@C_RfQResponse_ID@ #" + rfqResponse.getName();
	}

	@Override
	public void updateQtyPromisedAndSave(final I_C_RfQResponseLine rfqResponseLine)
	{
		final BigDecimal qtyPromised = Services.get(IRfqDAO.class).calculateQtyPromised(rfqResponseLine);
		rfqResponseLine.setQtyPromised(qtyPromised);

		InterfaceWrapperHelper.save(rfqResponseLine);
	}

}
