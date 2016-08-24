package de.metas.rfq.exceptions;

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

public class RfQDocumentNotDraftException extends RfQException
{
	private static final long serialVersionUID = -1710586935241536340L;
	
	private static final String MSG = "RfQDocumentNotDraftException";

	public RfQDocumentNotDraftException(final String documentInfo)
	{
		super("@" + MSG + "@: " + documentInfo);
	}
}
