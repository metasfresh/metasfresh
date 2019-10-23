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
package org.adempiere.exceptions;

import java.util.Collection;

import de.metas.util.Check;

/**
 * Throwed when there are some fields that are mandatory but unfilled.
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class FillMandatoryException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9074980284529933724L;

	/**
	 * 
	 * @param translated if true we consider that fields are already translated
	 * @param fields
	 */
	public FillMandatoryException(final boolean translated, final String... fields)
	{
		super("@FillMandatory@ " + buildMessage(translated, fields));
	}

	/**
	 * 
	 * @param translated if true we consider that fields are already translated
	 * @param fields
	 */
	public FillMandatoryException(final boolean translated, final Collection<String> fields)
	{
		super("@FillMandatory@ " + buildMessage(translated, fields));
	}

	/**
	 * NOTE: fields are considered not translated and they will be translated
	 * 
	 * @param fields
	 */
	public FillMandatoryException(final String... fields)
	{
		this(false, fields);
	}

	private static final String buildMessage(final boolean translated, final String... fields)
	{
		if (fields == null || fields.length == 0)
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

			if (translated)
			{
				sb.append(f);
			}
			else
			{
				sb.append("@").append(f).append("@");
			}
		}
		return sb.toString();
	}

	private static final String buildMessage(final boolean translated, final Collection<String> fields)
	{
		if (fields == null || fields.isEmpty())
		{
			return "";
		}

		final String[] fieldsArr = fields.toArray(new String[fields.size()]);
		return buildMessage(translated, fieldsArr);
	}
}
