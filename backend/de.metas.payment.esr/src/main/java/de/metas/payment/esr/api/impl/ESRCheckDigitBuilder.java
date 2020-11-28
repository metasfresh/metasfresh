package de.metas.payment.esr.api.impl;

import de.metas.payment.esr.ESRConstants;
import de.metas.util.Check;
import lombok.NonNull;

/**
 * Contains a method for calculating the check digit.
 */
// metas-ts: moved to a dedicated class, because it's very different from the rest of ESRImportBL and its tests fill a test class of its own.
public final class ESRCheckDigitBuilder
{
	/**
	 * @return checksum digit (between 0 and 9)
	 * @see <a href="http://w2.syronex.com/jmr/programming/mod11chk">ISBNs & The Modulo 11 Checksum Algorithm</a>
	 */
	public int calculateESRCheckDigit(@NonNull final String text)
	{
		int carry = 0;
		try
		{
			for (int i = 0; i < text.length(); i++)
			{
				final int intAtIdx = Integer.parseInt(Character.toString(text.charAt(i)));
				carry = ESRConstants.CHECK_String[(intAtIdx + carry) % 10];
				// System.out.println("Idx=" + i + "\tNumber[i]=" + intAtIdx + "\tCarry=" + carry);
			}

		}
		catch (final NumberFormatException e)
		{
			// NumberFormatException can be thrown from Integer.parseInt
			Check.errorIf(true, "Failed to compute ESR check digit; invalid ESR-String \"{}\"", text); // will throw an exception

		}

		final int result = (10 - carry) % 10;
		// System.out.println("Result=" + result);
		return result;
	}
}
