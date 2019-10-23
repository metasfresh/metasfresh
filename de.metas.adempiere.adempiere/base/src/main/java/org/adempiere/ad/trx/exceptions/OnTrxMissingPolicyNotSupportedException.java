package org.adempiere.ad.trx.exceptions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.ad.trx.api.OnTrxMissingPolicy;

import de.metas.util.Check;

/**
 * Exception thrown when an {@link OnTrxMissingPolicy} is not supported.
 * 
 * @author tsa
 *
 */
public class OnTrxMissingPolicyNotSupportedException extends TrxException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6457136836120178899L;

	/**
	 * Throws exceptions if given {@link OnTrxMissingPolicy}s are equal
	 * 
	 * @param onTrxMissingPolicyToCheck {@link OnTrxMissingPolicy} that we are validating
	 * @param forbiddenOnTrxMissingPolicyToCheck {@link OnTrxMissingPolicy} which will shall not be
	 */
	public static final void throwIf(final OnTrxMissingPolicy onTrxMissingPolicyToCheck, final OnTrxMissingPolicy forbiddenOnTrxMissingPolicyToCheck)
	{
		Check.assumeNotNull(forbiddenOnTrxMissingPolicyToCheck, "forbiddenOnTrxMissingPolicyToCheck not null");
		if (onTrxMissingPolicyToCheck == forbiddenOnTrxMissingPolicyToCheck)
		{
			throw new OnTrxMissingPolicyNotSupportedException(onTrxMissingPolicyToCheck);
		}
	}

	public OnTrxMissingPolicyNotSupportedException(final OnTrxMissingPolicy onTrxMissingPolicy)
	{
		super("Unsupported " + OnTrxMissingPolicy.class + " option: " + onTrxMissingPolicy);
	}

}
