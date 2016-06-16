package de.metas.rfq.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.MClient;
import org.compiere.print.ReportEngine;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Joiner;

import de.metas.logging.LogManager;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.IRfqTopicBL;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLine;
import de.metas.rfq.model.I_C_RfQLineQty;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;
import de.metas.rfq.model.I_C_RfQ_TopicSubscriber;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class RfqBL implements IRfqBL
{
	private static final Logger log = LogManager.getLogger(RfqBL.class);

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
	public void checkQuoteTotalAmtOnly(final I_C_RfQ rfq)
	{
		if (!isQuoteTotalAmtOnly(rfq))
		{
			return;
		}

		// Need to check Line Qty
		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
		for (final I_C_RfQLine line : rfqDAO.retrieveLines(rfq))
		{
			final List<I_C_RfQLineQty> qtys = rfqDAO.retrieveLineQtys(line);
			if (qtys.size() > 1)
			{
				throw new AdempiereException("@Line@ " + line.getLine() + ": #@C_RfQLineQty@=" + qtys.size() + " - @IsQuoteTotalAmt@");
			}
		}
	}

	@Override
	public boolean isValidAmt(final I_C_RfQResponseLineQty responseQty)
	{
		final BigDecimal price = responseQty.getPrice();
		if (price == null || price.signum() == 0)
		{
			log.warn("No Price - " + price);
			return false;
		}

		final BigDecimal discount = responseQty.getDiscount();
		if (discount != null)
		{
			if (discount.abs().compareTo(Env.ONEHUNDRED) > 0)
			{
				log.warn("Discount > 100 - " + discount);
				return false;
			}
		}

		final BigDecimal netAmt = calculateNetAmt(responseQty);
		if (netAmt == null)
		{
			log.warn("Net is null");
			return false;
		}
		if (netAmt.signum() <= 0)
		{
			log.warn("Net <= 0 - " + netAmt);
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
	public void complete(final I_C_RfQResponse response)
	{
		if (response.isComplete())
		{
			throw new AdempiereException("@IsComplete@");
		}
		
		final I_C_RfQ rfq = response.getC_RfQ();

		// Is RfQ Total valid
		checkQuoteTotalAmtOnly(rfq);

		// Do we have Total Amount ?
		if (rfq.isQuoteTotalAmt() || isQuoteTotalAmtOnly(rfq))
		{
			final BigDecimal amt = response.getPrice();
			if (amt == null || amt.signum() <= 0)
			{
				throw new AdempiereException("No Total Amount");
			}
		}

		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);

		//
		// If all lines and qtys shall be quoted, make sure this is respected
		if (isQuoteAllLines(rfq))
		{
			for (final I_C_RfQResponseLine responseLine : rfqDAO.retrieveResponseLines(response))
			{
				if (!responseLine.isActive())
				{
					throw new AdempiereException("Line " + responseLine.getC_RfQLine().getLine() + ": Not Active");
				}

				boolean validAmt = false;
				for (final I_C_RfQResponseLineQty responseLineQty : rfqDAO.retrieveResponseQtys(responseLine))
				{
					if (!responseLineQty.isActive())
					{
						continue;
					}

					final BigDecimal amt = calculateNetAmt(responseLineQty);
					if (amt != null && amt.signum() > 0)
					{
						validAmt = true;
						break;
					}
				}
				if (!validAmt)
				{
					throw new AdempiereException("Line " + responseLine.getC_RfQLine().getLine() + ": No amount or amount is not valid");
				}
			}
		}

		// Do we have an amount for all line qtys
		if (rfq.isQuoteAllQty())
		{
			for (final I_C_RfQResponseLine line : rfqDAO.retrieveResponseLines(response))
			{
				for (final I_C_RfQResponseLineQty qty : rfqDAO.retrieveResponseQtys(line))
				{
					if (!qty.isActive())
					{
						throw new AdempiereException("Line " + line.getC_RfQLine().getLine()
								+ " Qty=" + qty.getC_RfQLineQty().getQty()
								+ ": Not Active");
					}

					final BigDecimal amt = calculateNetAmt(qty);
					if (amt == null || amt.signum() <= 0)
					{
						throw new AdempiereException("Line " + line.getC_RfQLine().getLine()
								+ " Qty=" + qty.getC_RfQLineQty().getQty()
								+ ": No Amount");
					}
				}
			}
		}

		response.setIsComplete(true);
		InterfaceWrapperHelper.save(response);
	}

	@Override
	public I_C_RfQResponse createRfqResponse(final I_C_RfQ rfq, final I_C_RfQ_TopicSubscriber subscriber)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(rfq);
		final I_C_RfQResponse response = InterfaceWrapperHelper.create(ctx, I_C_RfQResponse.class, ITrx.TRXNAME_ThreadInherited);

		// Defaults
		response.setIsComplete(false);
		response.setIsSelectedWinner(false);
		response.setIsSelfService(false);
		response.setPrice(BigDecimal.ZERO);
		response.setProcessed(false);
		response.setProcessing(false);

		// From RfQ
		response.setAD_Org_ID(rfq.getAD_Org_ID());
		response.setC_RfQ(rfq);
		response.setC_Currency_ID(rfq.getC_Currency_ID());
		response.setName(rfq.getName());

		// Subscriber info
		response.setC_BPartner_ID(subscriber.getC_BPartner_ID());
		response.setC_BPartner_Location_ID(subscriber.getC_BPartner_Location_ID());
		response.setAD_User_ID(subscriber.getAD_User_ID());

		//
		// Create Lines
		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
		final IRfqTopicBL rfqTopicBL = Services.get(IRfqTopicBL.class);
		for (final I_C_RfQLine line : rfqDAO.retrieveLines(rfq))
		{
			if (!line.isActive())
			{
				continue;
			}

			//
			// Product on "Only" list
			if (subscriber != null && !rfqTopicBL.isProductIncluded(subscriber, line.getM_Product_ID()))
			{
				continue;
			}

			//
			// Save the response
			if (InterfaceWrapperHelper.isNew(response))
			{
				InterfaceWrapperHelper.save(response);
			}

			//
			// Create RfQ response line
			createRfqResponseLine(response, line);
		}

		if (InterfaceWrapperHelper.isNew(response))
		{
			return null;
		}

		return response;
	}

	private I_C_RfQResponseLine createRfqResponseLine(final I_C_RfQResponse response, final I_C_RfQLine line)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(response);
		final I_C_RfQResponseLine responseLine = InterfaceWrapperHelper.create(ctx, I_C_RfQResponseLine.class, ITrx.TRXNAME_ThreadInherited);
		responseLine.setAD_Org_ID(response.getAD_Org_ID());
		responseLine.setC_RfQResponse(response);
		//
		responseLine.setC_RfQLine(line);
		//
		responseLine.setIsSelectedWinner(false);
		responseLine.setIsSelfService(false);

		//
		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
		for (final I_C_RfQLineQty lineQty : rfqDAO.retrieveLineQtys(line))
		{
			if (lineQty.isActive() && lineQty.isRfQQty())
			{
				if (InterfaceWrapperHelper.isNew(responseLine))
				{
					InterfaceWrapperHelper.save(responseLine);
				}

				createRfQResponseLineQty(responseLine, lineQty);
			}
		}

		if (InterfaceWrapperHelper.isNew(responseLine))
		{
			return null;
		}
		return responseLine;
	}

	private I_C_RfQResponseLineQty createRfQResponseLineQty(final I_C_RfQResponseLine responseLine, final I_C_RfQLineQty lineQty)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(responseLine);
		final I_C_RfQResponseLineQty responseQty = InterfaceWrapperHelper.create(ctx, I_C_RfQResponseLineQty.class, ITrx.TRXNAME_ThreadInherited);
		responseQty.setAD_Org_ID(responseLine.getAD_Org_ID());
		responseQty.setC_RfQResponseLine(responseLine);
		responseQty.setC_RfQLineQty(lineQty);
		InterfaceWrapperHelper.save(responseQty);
		return responseQty;
	}

	@Override
	public boolean sendRfQ(final I_C_RfQResponse response)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(response);
		final I_AD_User userTo = response.getAD_User();
		if (userTo == null)
		{
			log.error("Skip sending RfQ response mail because there is no user for {}", response);
			return false;
		}
		final String userEmail = userTo.getEMail();
		if (Check.isEmpty(userEmail, true))
		{
			log.error("Skip sending RfQ response mail because user {} has no email: {}", response);
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

	@Override
	public void close(final I_C_RfQ rfq)
	{
		//
		// Close RfQ
		rfq.setProcessed(true);
		InterfaceWrapperHelper.save(rfq);

		//
		// Close RfQ Responses
		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
		for (final I_C_RfQResponse rfqResponse : rfqDAO.retrieveResponses(rfq, false, false))
		{
			close(rfqResponse);
		}
	}
	
	private void close(final I_C_RfQResponse rfqResponse)
	{
		rfqResponse.setProcessed(true);
		InterfaceWrapperHelper.save(rfqResponse);
	}
	
	@Override
	public boolean isClosed(final I_C_RfQResponse rfqResponse)
	{
		return rfqResponse.isProcessed();
	}

}
