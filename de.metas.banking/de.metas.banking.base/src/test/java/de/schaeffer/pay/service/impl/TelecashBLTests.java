package de.schaeffer.pay.service.impl;

/*
 * #%L
 * de.metas.banking.base
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


import static org.junit.Assert.assertEquals;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPayment;
import org.junit.Test;

import de.firstdata.ipgapi.client.IPGApiResult;
import de.firstdata.ipgapi.client.transaction.creditcard.CreditCard;
import de.metas.adempiere.model.I_C_Currency;
import de.metas.adempiere.test.POTest;
import de.schaeffer.pay.service.impl.TelecashBL.TelecashData;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;


public class TelecashBLTests {

	@Mocked
	MPayment payment;

	@Tested
	TelecashBL telecashBL;

	@Mocked
	TelecashData connectionData;

	@Mocked
	IPGApiResult ipgApiResult;

	@Mocked
	I_C_Currency currency;

	@Test
	public void mkCreditCard() {

		POTest.recordGenericExpectations(payment, 10);
		
		// @formatter:off
		new NonStrictExpectations() 
		{{
				payment.getCreditCardExpMM(); result = 9;
				payment.getCreditCardExpYY(); result = 8;
				
				// the values contain some spaces that we expect to be removed
				payment.getVolatileCreditCardNumber(); result = " 1234 12345 ";
				payment.getVolatileCreditCardVV(); result = " 543 ";
				
				// getCreditCardNumber() and getCreditCardVV() may not be called
				payment.getCreditCardNumber(); result = new AssertionError("payment.getCreditCardNumber() should not be called");;
				payment.getCreditCardVV(); result = new AssertionError("payment.getCreditCardVV() should not be called");
		}};
		// @formatter:on
		
		final CreditCard cc = telecashBL.mkCreditCard(payment);

		assertEquals("123412345", cc.cardNumber);
		assertEquals("09", cc.expMonth);
		assertEquals("08", cc.expYear);
		assertEquals("543", cc.cardCodeValue);
	}

	/**
	 * {@link TelecashBL#performSale(MPayment)} only works with payments that
	 * have EUR as currency. This is a workaround, because currently we don't
	 * have other currencies' ISO-4217 numerical codes.
	 */
	@Test(expected = AdempiereException.class)
	public void performSaleFailOnNonEUR() {

		// @formatter:off
		new NonStrictExpectations() 
		{{
				payment.getC_Currency();
				returns(currency);

				currency.getISO_Code();
				returns("ZWR");
		}};
		// @formatter:on
		
		telecashBL.performSale(payment);
	}
}
