package de.metas.payment.esr.api.impl;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRLineMatcher;
import de.metas.payment.esr.model.I_ESR_ImportLine;

public class ESRLineMatcher extends AbstractESRLineMatcher
{
	private static final IESRLineMatcher regularLineMatcher = new ESRRegularLineMatcher();

	private static final Map<String, IESRLineMatcher> matchers = new ConcurrentHashMap<String, IESRLineMatcher>();
	static
	{
		matchers.put(ESRConstants.ESRTRXTYPE_Receipt, new ESRReceiptLineMatcher());
		matchers.put(ESRConstants.ESRTRXTYPE_Payment, new ESRPaymentLineMatcher());
		matchers.put(ESRConstants.ESRTRXTYPE_ReverseBooking, new ESRReverseBookingMatcher());
	}

	/**
	 * Sets the ESR transaction type and calls a type-specific matcher to to the rest of the work.
	 */
	@Override
	public void match(final I_ESR_ImportLine importLine)
	{
		final String esrImportLineText = importLine.getESRLineText().trim();
		importLine.setESRLineText(esrImportLineText); // make sure line text is trimmed

		// Make sure errorMsg is reset
		importLine.setErrorMsg(null);

		// first 3 digits: transaction type
		final String trxType = esrImportLineText.substring(0, 3);
		importLine.setESRTrxType(trxType);

		IESRLineMatcher matcher = matchers.get(trxType);
		if (matcher == null)
		{
			matcher = regularLineMatcher;
		}

		matcher.match(importLine);
	}
}
