package de.metas.rfq.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.MClient;
import org.compiere.print.ReportEngine;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import com.google.common.base.Joiner;

import de.metas.logging.LogManager;
import de.metas.rfq.IRfQConfiguration;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.event.IRfQEventDispacher;
import de.metas.rfq.exceptions.NoRfQLinesFoundException;
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
	private static final Logger logger = LogManager.getLogger(RfqBL.class);

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

	@Override
	public boolean isValidAmt(final I_C_RfQResponseLineQty responseQty)
	{
		final BigDecimal price = responseQty.getPrice();
		if (price == null || price.signum() == 0)
		{
			logger.warn("No Price - " + price);
			return false;
		}

		final BigDecimal discount = responseQty.getDiscount();
		if (discount != null)
		{
			if (discount.abs().compareTo(Env.ONEHUNDRED) > 0)
			{
				logger.warn("Discount > 100 - " + discount);
				return false;
			}
		}

		final BigDecimal netAmt = calculateNetAmt(responseQty);
		if (netAmt == null)
		{
			logger.warn("Net is null");
			return false;
		}
		if (netAmt.signum() <= 0)
		{
			logger.warn("Net <= 0 - " + netAmt);
			return false;
		}
		return true;
	}	// isValidAmt

	@Override
	public BigDecimal calculateNetAmt(final I_C_RfQResponseLineQty responseQty)
	{
		final BigDecimal price = responseQty.getPrice();
		if (price == null || price.signum() <= 0)
		{
			// invalid price
			return null;
		}

		//
		// Apply discount
		final BigDecimal discount = responseQty.getDiscount();
		if (discount == null || discount.signum() == 0)
		{
			return price;
		}
		// Calculate
		// double result = price.doubleValue() * (100.0 - discount.doubleValue()) / 100.0;
		final BigDecimal factor = Env.ONEHUNDRED.subtract(discount);
		return price.multiply(factor).divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
	}

	@Override
	public void complete(final I_C_RfQResponse rfqResponse)
	{
		assertDraft(rfqResponse);

		final I_C_RfQ rfq = rfqResponse.getC_RfQ();
		assertComplete(rfq);

		// Do we have Total Amount ?
		if (rfq.isQuoteTotalAmt() || isQuoteTotalAmtOnly(rfq))
		{
			final BigDecimal totalAmt = rfqResponse.getPrice();
			if (totalAmt == null || totalAmt.signum() <= 0)
			{
				throw new RfQResponseInvalidException(rfqResponse, "@Price@ <= 0");
			}
		}

		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);

		//
		// If all lines and qtys shall be quoted, make sure this is respected
		if (isQuoteAllLines(rfq))
		{
			for (final I_C_RfQResponseLine rfqResponseLine : rfqDAO.retrieveResponseLines(rfqResponse))
			{
				if (!rfqResponseLine.isActive())
				{
					throw new RfQResponseLineInvalidException(rfqResponseLine, "@IsActive@=@N@");
				}

				boolean validAmt = false;
				for (final I_C_RfQResponseLineQty rfqResponseLineQty : rfqDAO.retrieveResponseQtys(rfqResponseLine))
				{
					if (!rfqResponseLineQty.isActive())
					{
						continue;
					}

					final BigDecimal amt = calculateNetAmt(rfqResponseLineQty);
					if (amt != null && amt.signum() > 0)
					{
						validAmt = true;
						break;
					}
				}
				if (!validAmt)
				{
					throw new RfQResponseLineInvalidException(rfqResponseLine, "No amount or amount is not valid");
				}
			}
		}

		// Do we have an amount for all line qtys
		if (rfq.isQuoteAllQty())
		{
			for (final I_C_RfQResponseLine line : rfqDAO.retrieveResponseLines(rfqResponse))
			{
				for (final I_C_RfQResponseLineQty qty : rfqDAO.retrieveResponseQtys(line))
				{
					if (!qty.isActive())
					{
						throw new RfQResponseLineQtyInvalidException(qty, "@IsActive@=@N@");
					}

					final BigDecimal amt = calculateNetAmt(qty);
					if (amt == null || amt.signum() <= 0)
					{
						throw new RfQResponseLineQtyInvalidException(qty, "@Amount@ <= 0");
					}
				}
			}
		}

		rfqResponse.setProcessed(true);
		rfqResponse.setIsComplete(true);
		InterfaceWrapperHelper.save(rfqResponse);
	}

	@Override
	public boolean sendRfQResponseToVendor(final I_C_RfQResponse response)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(response);
		final I_AD_User userTo = response.getAD_User();
		if (userTo == null)
		{
			logger.error("Skip sending RfQ response mail because there is no user for {}", response);
			return false;
		}
		final String userEmail = userTo.getEMail();
		if (Check.isEmpty(userEmail, true))
		{
			logger.error("Skip sending RfQ response mail because user {} has no email: {}", response);
			return false;
		}

		//
		final String subject = "RfQ: " + response.getName();

		//
		String message = Joiner.on("\n")
				.skipNulls()
				.join(response.getDescription(), response.getHelp());
		if (message == null)
		{
			message = response.getName();
		}

		//
		// Send it
		final MClient client = MClient.get(ctx);
		final EMail email = client.createEMail(userEmail, subject, message);
		email.addAttachment(createPDF(response));
		if (EMail.SENT_OK.equals(email.send()))
		{
			response.setDateInvited(new Timestamp(System.currentTimeMillis()));
			InterfaceWrapperHelper.save(response);
			return true;
		}
		return false;
	}

	private File createPDF(final I_C_RfQResponse response)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(response);
		final ReportEngine re = ReportEngine.get(ctx, ReportEngine.RFQ, response.getC_RfQResponse_ID());
		if (re == null)
		{
			return null;
		}
		return re.getPDF();
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
			public void run(String localTrxName) throws Exception
			{
				InterfaceWrapperHelper.setTrxName(rfq, ITrx.TRXNAME_ThreadInherited);
				completeInTrx(rfq);
			}
		});
	}

	private void completeInTrx(final I_C_RfQ rfq)
	{
		assertDraft(rfq);

		getRfQEventDispacher().fireBeforeComplete(rfq);

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
				.setSendToVendor(true)
				.create();

		getRfQEventDispacher().fireAfterComplete(rfq);

		InterfaceWrapperHelper.save(rfq);
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
			public void run(String localTrxName) throws Exception
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

	private String getSummary(final I_C_RfQ rfq)
	{
		return "@C_RfQ_ID@ #" + rfq.getDocumentNo();
	}

	private String getSummary(final I_C_RfQResponse rfqResponse)
	{
		return "@C_RfQResponse_ID@ #" + rfqResponse.getName();
	}

}
