/**
 * 
 */
package de.metas.payment.sepa.api;

/*
 * #%L
 * de.metas.payment.sepa
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


import org.adempiere.util.ISingletonService;

/**
 * @author cg
 *
 */
public interface IIBANValidationBL  extends ISingletonService
{

	/**
	 * Validates iban.
	 *
	 * @param iban to be validated.
	 */
	void validate(String iban);

	/**
	 * Calculates Iban <a href="http://en.wikipedia.org/wiki/ISO_13616#Generating_IBAN_check_digits">Check Digit</a>.
	 *
	 * @param iban string value
	 * @throws IbanFormatException if iban contains invalid character.
	 *
	 * @return check digit as String
	 */
	String calculateCheckDigit(String iban);

	/**
	 * Validates IBAN's checkDigit against expected check digit
	 * 
	 * @param iban
	 */
	void validateCheckDigit(String iban);

}
