package de.metas.rfq.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLine;
import de.metas.rfq.model.I_C_RfQLineQty;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;
import de.metas.rfq.util.RfqResponseLineQtyByNetAmtComparator;

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

public class C_RfQ_RankResponses extends SvrProcess
{
	// services
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);

	private static final int RANK_Invalid = 999;

	/**
	 * Process.
	 *
	 * <pre>
	 * 	- ignore 0 or invalid responses
	 * 	- rank among qty
	 * 	- for selected PO qty select winner
	 * 	- if all lines are winner - select that
	 * </pre>
	 *
	 * @return message
	 */
	@Override
	protected String doIt() throws Exception
	{
		final I_C_RfQ rfq = getRecord(I_C_RfQ.class);
		log.info("{}", rfq);

		rfqBL.checkQuoteTotalAmtOnly(rfq);

		//
		// Get Completed, Active Responses
		final List<I_C_RfQResponse> responses = rfqDAO.retrieveResponses(rfq, true, true);
		log.debug("doIt - #Responses={}", responses.size());
		if (responses.isEmpty())
		{
			throw new IllegalArgumentException("No completed RfQ Responses found");
		}

		//
		// One response only => pick it as a winner
		if (responses.size() == 1)
		{
			final I_C_RfQResponse winner = responses.get(0);
			winner.setIsSelectedWinner(true);
			InterfaceWrapperHelper.save(winner);
			return "Only one completed RfQ Response found";
		}

		// Rank
		if (rfqBL.isQuoteTotalAmtOnly(rfq))
		{
			rankResponseHeaders(rfq, responses);
		}
		else
		{
			rankResponseLines(rfq, responses);
		}

		return "# " + responses.size();
	}

	/**
	 * Rank responses based on their lines.
	 */
	private void rankResponseLines(final I_C_RfQ rfq, final List<I_C_RfQResponse> responses)
	{
		final List<I_C_RfQLine> rfqLines = Services.get(IRfqDAO.class).retrieveLines(rfq);
		if (rfqLines.isEmpty())
		{
			throw new IllegalArgumentException("No RfQ Lines found");
		}

		// for all lines
		for (final I_C_RfQLine rfqLine : rfqLines)
		{
			// RfQ Line
			if (!rfqLine.isActive())
			{
				continue;
			}

			log.debug("rankLines - {}", rfqLine);
			for (final I_C_RfQLineQty rfqQty : rfqDAO.retrieveLineQtys(rfqLine))
			{
				// RfQ Line Qty
				if (!rfqQty.isActive() || !rfqQty.isRfQQty())
				{
					continue;
				}

				log.debug("rankLines Qty - {}", rfqQty);
				for (final I_C_RfQResponseLineQty respQty : rfqDAO.retrieveResponseQtys(rfqQty))
				{
					// Response Line Qty
					if (!respQty.isActive() || !rfqBL.isValidAmt(respQty))
					{
						respQty.setRanking(RANK_Invalid);
						InterfaceWrapperHelper.save(respQty);
						log.debug("  - ignored: {}", respQty);
					}
				}         	// for all respones line qtys

				// Rank RfQ Line Qtys
				final List<I_C_RfQResponseLineQty> respQtys = new ArrayList<>(rfqDAO.retrieveResponseQtys(rfqQty));
				if (respQtys.isEmpty())
				{
					log.debug("  - No Qtys with valid Amounts");
				}
				else
				{
					Collections.sort(respQtys, RfqResponseLineQtyByNetAmtComparator.instance);
					int lastRank = 1;		// multiple rank #1
					BigDecimal lastAmt = Env.ZERO;
					int rank = 0;
					for (final I_C_RfQResponseLineQty qty : respQtys)
					{
						if (!qty.isActive() || qty.getRanking() == RANK_Invalid)
						{
							continue;
						}

						final BigDecimal netAmt = rfqBL.calculateNetAmt(qty);
						if (netAmt == null)
						{
							qty.setRanking(RANK_Invalid);
							InterfaceWrapperHelper.save(qty);
							log.debug("  - Rank invalid: {}", qty);
							continue;
						}

						if (lastAmt.compareTo(netAmt) != 0)
						{
							lastRank = rank + 1;
							lastAmt = netAmt;
						}

						qty.setRanking(lastRank);
						log.debug("  - Rank {}: {}", lastRank, qty);
						InterfaceWrapperHelper.save(qty);

						//
						if (rank == 0)         	// Update RfQ
						{
							rfqQty.setBestResponseAmt(netAmt);
							InterfaceWrapperHelper.save(rfqQty);
						}
						rank++;
					}
				}
			}         	// for all rfq line qtys
		}         	// for all rfq lines

		//
		// Select Winner based on line ranking
		I_C_RfQResponse winner = null;
		for (final I_C_RfQResponse response : responses)
		{
			if (response.isSelectedWinner())
			{
				response.setIsSelectedWinner(false);
			}

			int ranking = 0;
			for (final I_C_RfQResponseLine respLine : rfqDAO.retrieveResponseLines(response))
			{
				if (!respLine.isActive())
				{
					continue;
				}
				if (respLine.isSelectedWinner())
				{
					respLine.setIsSelectedWinner(false);
				}

				for (final I_C_RfQResponseLineQty respQty : rfqDAO.retrieveResponseQtys(respLine))
				{
					if (!respQty.isActive())
					{
						continue;
					}

					ranking += respQty.getRanking();
					if (respQty.getRanking() == 1
							&& respQty.getC_RfQLineQty().isPurchaseQty())
					{
						respLine.setIsSelectedWinner(true);
						InterfaceWrapperHelper.save(respLine);
						break;
					}
				}
			}
			response.setRanking(ranking);
			InterfaceWrapperHelper.save(response);
			log.debug("- Response Ranking {}: {}", ranking, response);

			if (!rfqBL.isQuoteSelectedLines(rfq))          // no total selected winner if not all lines
			{
				if (winner == null && ranking > 0)
				{
					winner = response;
				}
				if (winner != null
						&& response.getRanking() > 0
						&& response.getRanking() < winner.getRanking())
				{
					winner = response;
				}
			}
		}
		if (winner != null)
		{
			winner.setIsSelectedWinner(true);
			InterfaceWrapperHelper.save(winner);
			log.debug("- Response Winner: {}", winner);
		}
	}

	/**
	 * Rank responses based on header informations (without looking at lines)
	 */
	private void rankResponseHeaders(final I_C_RfQ rfq, final List<I_C_RfQResponse> responses)
	{
		int ranking = 1;
		// Responses Ordered by Price
		for (final I_C_RfQResponse response : responses)
		{
			final BigDecimal reponsePrice = response.getPrice();
			if (reponsePrice != null && reponsePrice.signum() > 0)
			{
				if (response.isSelectedWinner() != (ranking == 1))
				{
					response.setIsSelectedWinner(ranking == 1);
				}
				response.setRanking(ranking);
				//
				ranking++;
			}
			else
			{
				response.setRanking(RANK_Invalid);
				if (response.isSelectedWinner())
				{
					response.setIsSelectedWinner(false);
				}
			}
			InterfaceWrapperHelper.save(response);
			log.debug("rankResponse - {}", response);
		}
	}
}
