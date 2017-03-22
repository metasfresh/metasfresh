package org.adempiere.exceptions;

import org.adempiere.util.Check;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Exception thrown where C_TaxCategory_ID was not found for a given document line.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
public class TaxCategoryNotFoundException extends AdempiereException
{
	public TaxCategoryNotFoundException(final Object documentLine, final String additionalReason)
	{
		super(buildMsg(documentLine, additionalReason));
	}

	public TaxCategoryNotFoundException(final Object documentLine)
	{
		super(buildMsg(documentLine, (String)null));
	}

	private static String buildMsg(final Object documentLine, final String additionalReason)
	{
		final StringBuilder msg = new StringBuilder();
		msg.append("@NotFound@ @C_TaxCategory_ID@");

		if (!Check.isEmpty(additionalReason, true))
		{
			msg.append(": ").append(additionalReason);
		}

		if (documentLine != null)
		{
			msg.append("\nDocument: ").append(documentLine);
		}

		return msg.toString();
	}
}
