package de.metas.rfq.exceptions;

import de.metas.rfq.model.I_C_RfQLine;

/*
 * #%L
 * de.metas.rfq
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class RfQLineInvalidException extends RfQException
{
	private static final long serialVersionUID = -8896351276080334946L;

	public RfQLineInvalidException(final I_C_RfQLine rfqLine, final String message)
	{
		super(buildMsg(rfqLine, message));
	}

	private static String buildMsg(final I_C_RfQLine rfqLine, final String message)
	{
		return new StringBuilder()
				.append(buildInfoString(rfqLine))
				.append(": ").append(message)
				.toString();
	}

}
