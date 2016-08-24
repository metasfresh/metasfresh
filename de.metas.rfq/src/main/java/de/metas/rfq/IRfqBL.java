package de.metas.rfq;

import java.math.BigDecimal;

import org.adempiere.util.ISingletonService;

import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;

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

public interface IRfqBL extends ISingletonService
{
	//
	// C_RfQ related methods
	//
	//@formatter:off
	String getSummary(I_C_RfQ rfq);
	boolean isQuoteSelectedLines(I_C_RfQ rfq);
	boolean isQuoteTotalAmtOnly(I_C_RfQ rfq);
	
	boolean isDraft(I_C_RfQ rfq);
	void assertDraft(I_C_RfQ rfq);
	
	boolean isCompleted(I_C_RfQ rfq);
	void assertComplete(I_C_RfQ rfq);
	void complete(I_C_RfQ rfq);
	
	boolean isClosed(I_C_RfQ rfq);
	void close(I_C_RfQ rfq);
	void unclose(I_C_RfQ rfq);
	
	void reActivate(I_C_RfQ rfq);
	//@formatter:on

	//
	// C_RfQResponse related methods
	//
	//@formatter:off
	String getSummary(I_C_RfQResponse rfqResponse);
	
	boolean isDraft(I_C_RfQResponse rfqResponse);
	boolean isDraft(I_C_RfQResponseLine rfqResponseLine);
	void assertDraft(I_C_RfQResponse rfqResponse);
	//
	void complete(I_C_RfQResponse response);
	boolean isCompleted(I_C_RfQResponse rfqResponse);
	boolean isCompleted(I_C_RfQResponseLine rfqResponseLine);
	//
	void close(I_C_RfQResponse rfqResponse);
	boolean isClosed(I_C_RfQResponse rfqResponse);
	boolean isClosed(I_C_RfQResponseLine rfqResponseLine);
	//@formatter:on

	//
	// C_RfQReponseLine[Qty] related methods
	//
	//@formatter:off
	void updateQtyPromisedAndSave(I_C_RfQResponseLine rfqResponseLine);
	boolean isValidAmt(I_C_RfQResponseLineQty responseQty);
	
	/**
	 * Get price minus discount%.
	 * 
	 * @return price without discount% or null if price or discount is not valid.
	 */
	BigDecimal calculatePriceWithoutDiscount(I_C_RfQResponseLineQty responseQty);
	//@formatter:on
}
