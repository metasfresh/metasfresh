/**
 * 
 */
package org.adempiere.util.email;

/**
 * @author cg
 *
 */
public class EmailValidator
{

	/**
	 * Validate email using apache commons validator
	 * 
	 * @param hex
	 *            hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public static boolean validate(final String hex)
	{
		return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(hex);
	}
}
