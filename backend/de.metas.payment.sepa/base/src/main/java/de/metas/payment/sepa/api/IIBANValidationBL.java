/**
 * 
 */
package de.metas.payment.sepa.api;

import de.metas.util.ISingletonService;

/**
 * @author cg
 *
 */
public interface IIBANValidationBL extends ISingletonService
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
