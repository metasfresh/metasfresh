/**
 *
 */
package org.adempiere.util.email;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

public class EmailValidator
{

	/**
	 * Validate email using apache commons validator
	 *
	 * @param email
	 *            email addresse for validation
	 * @return
	 */
	public static boolean validate(final String email)
	{
		return validate(email, null);
	}

	/**
	 *
	 * @param email
	 * @param clazz optional, may be {@code null}. If a class is given and the given {@code email} is not valid,
	 *            then this method instantiates and throws an exception with message {@code "@EmailNotValid@"}.
	 * @return
	 */
	public static boolean validate(final String email, final Class<? extends RuntimeException> clazz)
	{
		final boolean emailValid = isValid(email);
		if (!emailValid && clazz != null)
		{
			// initiate and throw our exception
			try
			{
				RuntimeException ex = clazz.getConstructor(String.class).newInstance("@EmailNotValid@");
				throw ex;
			}
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
			{
				throw new RuntimeException("Unable to instantiate a " + clazz + " exception", e);
			}
		}
		return emailValid;
	}

	public static boolean isValid(@Nullable final String email)
	{
		final boolean emailValid = org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email);
		return emailValid;
	}
}
