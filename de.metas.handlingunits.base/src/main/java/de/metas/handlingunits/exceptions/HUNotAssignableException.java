package de.metas.handlingunits.exceptions;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.util.Check;

import de.metas.handlingunits.model.I_M_HU;

/**
 * Exception thrown when an HU is not assignable to a given document line
 *
 * @author tsa
 *
 */
public class HUNotAssignableException extends HUException
{
	/**
	 *
	 */
	private static final long serialVersionUID = -438979286865114772L;

	/**
	 *
	 * @param message reason why is not assignable
	 * @param documentLineModel document line on which HU was tried to be assigned
	 * @param hu HU that wanted to be assigned
	 */
	public HUNotAssignableException(final String message, final Object documentLineModel, final I_M_HU hu)
	{
		super(buildMsg(message, documentLineModel, hu));
	}

	private static final String buildMsg(final String message, final Object documentLineModel, final I_M_HU hu)
	{
		final StringBuilder sb = new StringBuilder();

		if (!Check.isEmpty(message, true))
		{
			sb.append(message.trim());
		}

		//
		// Document Line Info
		if (documentLineModel != null)
		{
			if (sb.length() > 0)
			{
				sb.append("\n");
			}
			sb.append("@Line@: ").append(documentLineModel);
		}

		//
		// HU Info
		if (hu != null)
		{
			if (sb.length() > 0)
			{
				sb.append("\n");
			}
			sb.append("@M_HU_ID@: ").append(hu.getValue()).append(" (ID=").append(hu.getM_HU_ID()).append(")");
		}

		return sb.toString();
	}
}
