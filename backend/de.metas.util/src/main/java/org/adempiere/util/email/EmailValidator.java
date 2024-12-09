/**
 *
 */
package org.adempiere.util.email;

<<<<<<< HEAD
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;
=======
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

public class EmailValidator
{

	/**
	 * Validate email using apache commons validator
	 *
<<<<<<< HEAD
	 * @param email
	 *            email addresse for validation
	 * @return
=======
	 * @param email email addresse for validation
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	public static boolean validate(final String email)
	{
		return validate(email, null);
	}

	/**
<<<<<<< HEAD
	 *
	 * @param email
	 * @param clazz optional, may be {@code null}. If a class is given and the given {@code email} is not valid,
	 *            then this method instantiates and throws an exception with message {@code "@EmailNotValid@"}.
	 * @return
=======
	 * @param clazz optional, may be {@code null}. If a class is given and the given {@code email} is not valid,
	 *              then this method instantiates and throws an exception with message {@code "@EmailNotValid@"}.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	public static boolean validate(final String email, final Class<? extends RuntimeException> clazz)
	{
		final boolean emailValid = isValid(email);
		if (!emailValid && clazz != null)
		{
			// initiate and throw our exception
			try
			{
<<<<<<< HEAD
				RuntimeException ex = clazz.getConstructor(String.class).newInstance("@EmailNotValid@");
				throw ex;
=======
				throw clazz.getConstructor(String.class).newInstance("@EmailNotValid@");
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		final boolean emailValid = org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email);
		return emailValid;
=======
		return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
