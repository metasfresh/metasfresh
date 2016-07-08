package de.metas.rfq.exceptions;

import de.metas.rfq.model.I_C_RfQResponseLine;

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

public class RfQDocumentClosedException extends RfQException
{
	private static final long serialVersionUID = 5180293816353339678L;

	private static final String MSG = "RfQDocumentClosedException";

	public RfQDocumentClosedException(final String documentInfo)
	{
		super("@" + MSG + "@: " + documentInfo);
	}

	public RfQDocumentClosedException(final I_C_RfQResponseLine rfqResponseLine)
	{
		this(buildInfoString(rfqResponseLine));
	}
}
