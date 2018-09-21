/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
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


import java.util.Collection;

import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;

/**
 * Throwed when there are some fields that are mandatory but unfilled.
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class EDIFillMandatoryException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9074980284529933724L;

	/**
	 * NOTE: fields are considered not translated and they will be translated
	 * 
	 * @param fields
	 */
	public EDIFillMandatoryException(final Collection<String> fields)
	{
		this(null, null, fields.toArray(new String[fields.size()]));
	}

	/**
	 * NOTE: fields are considered not translated and they will be translated
	 * 
	 * @param recordName
	 * @param recordIdentifier
	 * @param fields
	 */
	public EDIFillMandatoryException(final String recordName, final String recordIdentifier, final String... fields)
	{
		super("@FillMandatory@ " + buildSubRecordIdentifier(recordName, recordIdentifier) + buildMessage(fields));
	}

	/**
	 * NOTE: fields are considered not translated and they will be translated
	 * 
	 * @param recordName
	 * @param recordIdentifier
	 * @param fields
	 */
	public EDIFillMandatoryException(final String recordName, final String recordIdentifier, final Collection<String> fields)
	{
		this(recordName, recordIdentifier, fields.toArray(new String[fields.size()]));
	}

	private static String buildSubRecordIdentifier(final String recordName, final String recordIdentifier)
	{
		if (Check.isEmpty(recordName))
		{
			return "";
		}
		final StringBuilder sb = new StringBuilder()
				.append("[@").append(recordName).append("@");

		if (!Check.isEmpty(recordIdentifier))
		{
			sb.append(" (").append(recordIdentifier).append(")");
		}

		sb.append("] ");
		return sb.toString();
	}

	private static final String buildMessage(final String... fields)
	{
		if (Check.isEmpty(fields))
		{
			return "";
		}

		final StringBuilder sb = new StringBuilder();
		for (final String f : fields)
		{
			if (Check.isEmpty(f, true))
			{
				continue;
			}

			if (sb.length() > 0)
			{
				sb.append(", ");
			}

			sb.append("@").append(f).append("@");
		}
		return sb.toString();
	}
}
