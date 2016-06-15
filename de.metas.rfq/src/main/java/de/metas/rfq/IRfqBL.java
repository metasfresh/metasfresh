package de.metas.rfq;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;

import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLineQty;
import de.metas.rfq.model.I_C_RfQ_TopicSubscriber;

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

public interface IRfqBL extends ISingletonService
{
	boolean isQuoteSelectedLines(I_C_RfQ rfq);

	boolean isQuoteTotalAmtOnly(I_C_RfQ rfq);

	/**
	 * Is "Quote Total Amt Only" Valid
	 * 
	 * @throws AdempiereException if not valid
	 */
	void checkQuoteTotalAmtOnly(I_C_RfQ rfq);

	boolean isValidAmt(I_C_RfQResponseLineQty responseQty);

	/**
	 * Get Net Amt (price minus discount in %)
	 * 
	 * @return net amount or null
	 */
	BigDecimal calculateNetAmt(I_C_RfQResponseLineQty responseQty);

	/**
	 * Complete the given response
	 * 
	 * @throws AdempiereException in case of any error
	 */
	void complete(I_C_RfQResponse response);

	I_C_RfQResponse createRfqResponse(I_C_RfQ rfq, I_C_RfQ_TopicSubscriber subscriber);

	boolean sendRfQ(I_C_RfQResponse response);

	void close(I_C_RfQ rfq);

	boolean isClosed(I_C_RfQResponse rfqResponse);

}
