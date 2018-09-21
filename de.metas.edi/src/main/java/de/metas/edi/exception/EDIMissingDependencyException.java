package de.metas.edi.exception;

/*
 * #%L
 * de.metas.edi
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

public class EDIMissingDependencyException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2710688915672646730L;

	public EDIMissingDependencyException(final String dependencyMessage, final String recordName, final String recordIdentifier)
	{
		super(buildMessage(dependencyMessage, recordName, recordIdentifier));
	}

	public EDIMissingDependencyException(final String dependencyMessage)
	{
		this(dependencyMessage, null, null);
	}

	private static String buildMessage(final String dependencyMessage, final String recordName, final String recordIdentifier)
	{
		final StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append("@").append(dependencyMessage).append("@");
		if (!Check.isEmpty(recordName))
		{
			messageBuilder.append(" [@").append(recordName).append("@");
			if (!Check.isEmpty(recordIdentifier))
			{
				messageBuilder.append(" (").append(recordIdentifier).append(")]");
			}
			else
			{
				messageBuilder.append("]");
			}
		}
		return messageBuilder.toString();
	}
}
