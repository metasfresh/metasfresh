package de.metas.rfq.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.rfq.IRfQResponseRankingStrategy;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.exceptions.NoCompletedRfQResponsesFoundException;
import de.metas.rfq.exceptions.NoRfQLinesFoundException;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLine;
import de.metas.rfq.model.I_C_RfQLineQty;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;

/*
 * #%L
 * de.metas.rfq
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

/**
 * Default {@link I_C_RfQResponse}s ranking strategy.
 *
 * Basically, it does:
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
public class DefaultRfQResponseRankingStrategy implements IRfQResponseRankingStrategy
{
	// services
	private static final Logger logger = LogManager.getLogger(DefaultRfQResponseRankingStrategy.class);
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);
	
	private final transient RfqResponseLineQtyByNetAmtComparator rfqResponseLineComparator = new RfqResponseLineQtyByNetAmtComparator();

	@Override
	public void rank(final I_C_RfQ rfq)
	{
		rfqBL.assertComplete(rfq);

		//
		// Get Completed, Active Responses
		final List<I_C_RfQResponse> rfqResponses = rfqDAO.retrieveCompletedResponses(rfq);

		//
		// No responses
		if (rfqResponses.isEmpty())
		{
			throw new NoCompletedRfQResponsesFoundException(rfq);
		}

		//
		// One response only => pick it as a winner
		if (rfqResponses.size() == 1)
		{
			final I_C_RfQResponse winner = rfqResponses.get(0);
			winner.setIsSelectedWinner(true);
			InterfaceWrapperHelper.save(winner);
			return;
		}

		// Rank
		if (rfqBL.isQuoteTotalAmtOnly(rfq))
		{
			rankResponseHeaders(rfq, rfqResponses);
		}
		else
		{
			rankResponseLines(rfq, rfqResponses);
		}
	}

	/**
	 * Rank responses based on their lines.
	 */
	private void rankResponseLines(final I_C_RfQ rfq, final List<I_C_RfQResponse> rfqResponses)
	{
		final List<I_C_RfQLine> rfqLines = Services.get(IRfqDAO.class).retrieveLines(rfq);
		if (rfqLines.isEmpty())
		{
			throw new NoRfQLinesFoundException(rfq);
		}

		// for all lines
		for (final I_C_RfQLine rfqLine : rfqLines)
		{
			// RfQ Line
			if (!rfqLine.isActive())
			{
				continue;
			}

			logger.debug("rankLines - {}", rfqLine);
			for (final I_C_RfQLineQty rfqLineQty : rfqDAO.retrieveLineQtys(rfqLine))
			{
				// RfQ Line Qty
				if (!rfqLineQty.isActive() || !rfqLineQty.isRfQQty())
				{
					continue;
				}

				logger.debug("rankLines Qty - {}", rfqLineQty);
				for (final I_C_RfQResponseLineQty rfqReponseLineQty : rfqDAO.retrieveResponseQtys(rfqLineQty))
				{
					// Response Line Qty
					if (!rfqReponseLineQty.isActive() || !rfqBL.isValidAmt(rfqReponseLineQty))
					{
						rfqReponseLineQty.setRanking(RANK_Invalid);
						InterfaceWrapperHelper.save(rfqReponseLineQty);
						logger.debug("  - ignored: {}", rfqReponseLineQty);
					}
				}            	// for all respones line qtys

				// Rank RfQ Line Qtys
				final List<I_C_RfQResponseLineQty> rfqResponseQtys = new ArrayList<>(rfqDAO.retrieveResponseQtys(rfqLineQty));
				if (rfqResponseQtys.isEmpty())
				{
					logger.debug("  - No Qtys with valid Amounts");
				}
				else
				{
					Collections.sort(rfqResponseQtys, rfqResponseLineComparator);
					int lastRank = 1;		// multiple rank #1
					BigDecimal lastAmt = BigDecimal.ZERO;
					int rank = 0;
					for (final I_C_RfQResponseLineQty qty : rfqResponseQtys)
					{
						if (!qty.isActive() || qty.getRanking() == RANK_Invalid)
						{
							continue;
						}

						final BigDecimal netAmt = rfqBL.calculatePriceWithoutDiscount(qty);
						if (netAmt == null)
						{
							qty.setRanking(RANK_Invalid);
							InterfaceWrapperHelper.save(qty);
							logger.debug("  - Rank invalid: {}", qty);
							continue;
						}

						if (lastAmt.compareTo(netAmt) != 0)
						{
							lastRank = rank + 1;
							lastAmt = netAmt;
						}

						qty.setRanking(lastRank);
						logger.debug("  - Rank {}: {}", lastRank, qty);
						InterfaceWrapperHelper.save(qty);

						//
						if (rank == 0)            	// Update RfQ
						{
							rfqLineQty.setBestResponseAmt(netAmt);
							InterfaceWrapperHelper.save(rfqLineQty);
						}
						rank++;
					}
				}
			}            	// for all rfq line qtys
		}            	// for all rfq lines

		//
		// Select Winner based on line ranking
		I_C_RfQResponse winner = null;
		for (final I_C_RfQResponse rfqResponse : rfqResponses)
		{
			if (rfqResponse.isSelectedWinner())
			{
				rfqResponse.setIsSelectedWinner(false);
			}

			int ranking = 0;
			for (final I_C_RfQResponseLine rfqResponseLine : rfqDAO.retrieveResponseLines(rfqResponse))
			{
				if (!rfqResponseLine.isActive())
				{
					continue;
				}
				if (rfqResponseLine.isSelectedWinner())
				{
					rfqResponseLine.setIsSelectedWinner(false);
				}

				for (final I_C_RfQResponseLineQty respQty : rfqDAO.retrieveResponseQtys(rfqResponseLine))
				{
					if (!respQty.isActive())
					{
						continue;
					}

					ranking += respQty.getRanking();
					if (respQty.getRanking() == 1
							&& respQty.getC_RfQLineQty().isPurchaseQty())
					{
						rfqResponseLine.setIsSelectedWinner(true);
						InterfaceWrapperHelper.save(rfqResponseLine);
						break;
					}
				}
			}
			rfqResponse.setRanking(ranking);
			InterfaceWrapperHelper.save(rfqResponse);
			logger.debug("- Response Ranking {}: {}", ranking, rfqResponse);

			if (!rfqBL.isQuoteSelectedLines(rfq))             // no total selected winner if not all lines
			{
				if (winner == null && ranking > 0)
				{
					winner = rfqResponse;
				}
				if (winner != null
						&& rfqResponse.getRanking() > 0
						&& rfqResponse.getRanking() < winner.getRanking())
				{
					winner = rfqResponse;
				}
			}
		}
		if (winner != null)
		{
			winner.setIsSelectedWinner(true);
			InterfaceWrapperHelper.save(winner);
			logger.debug("- Response Winner: {}", winner);
		}
	}

	/**
	 * Rank responses based on header informations (without looking at lines)
	 */
	private void rankResponseHeaders(final I_C_RfQ rfq, final List<I_C_RfQResponse> rfqResponses)
	{
		int ranking = 1;
		// Responses Ordered by Price
		for (final I_C_RfQResponse rfqResponse : rfqResponses)
		{
			final BigDecimal reponsePrice = rfqResponse.getPrice();
			if (reponsePrice != null && reponsePrice.signum() > 0)
			{
				if (rfqResponse.isSelectedWinner() != (ranking == 1))
				{
					rfqResponse.setIsSelectedWinner(ranking == 1);
				}
				rfqResponse.setRanking(ranking);
				//
				ranking++;
			}
			else
			{
				rfqResponse.setRanking(RANK_Invalid);
				if (rfqResponse.isSelectedWinner())
				{
					rfqResponse.setIsSelectedWinner(false);
				}
			}
			InterfaceWrapperHelper.save(rfqResponse);
			logger.debug("rankResponse - {}", rfqResponse);
		}
	}
	
	private final class RfqResponseLineQtyByNetAmtComparator implements Comparator<I_C_RfQResponseLineQty>
	{
		public RfqResponseLineQtyByNetAmtComparator()
		{
			super();
		}

		@Override
		public int compare(final I_C_RfQResponseLineQty q1, final I_C_RfQResponseLineQty q2)
		{
			if (q1 == null)
			{
				throw new IllegalArgumentException("o1 = null");
			}
			if (q2 == null)
			{
				throw new IllegalArgumentException("o2 = null");
			}

			//
			if (!rfqBL.isValidAmt(q1))
			{
				return -99;
			}
			if (!rfqBL.isValidAmt(q2))
			{
				return +99;
			}

			final BigDecimal net1 = rfqBL.calculatePriceWithoutDiscount(q1);
			if (net1 == null)
			{
				return -9;
			}

			final BigDecimal net2 = rfqBL.calculatePriceWithoutDiscount(q2);
			if (net2 == null)
			{
				return +9;
			}

			return net1.compareTo(net2);
		}
	}

}
