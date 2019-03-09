package de.metas.security.impl;

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


import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;

public class RolePermissionsNotFoundException extends AdempiereException
{
	private static final String MSG = "RolePermissionsNotFoundException";

	/**
	 * 
	 */
	private static final long serialVersionUID = -5635853326303323078L;

	public RolePermissionsNotFoundException(final String additionalInfo)
	{
		this(buildMsg(additionalInfo), (Throwable)null);
	}

	public RolePermissionsNotFoundException(final String additionalInfo, Throwable cause)
	{
		super(buildMsg(additionalInfo), cause);
	}

	private static String buildMsg(String additionalInfo)
	{
		final StringBuilder sb = new StringBuilder()
				.append("@").append(MSG).append("@");

		if (!Check.isEmpty(additionalInfo, true))
		{
			sb.append(": ").append(additionalInfo);
		}

		return sb.toString();
	}
}
