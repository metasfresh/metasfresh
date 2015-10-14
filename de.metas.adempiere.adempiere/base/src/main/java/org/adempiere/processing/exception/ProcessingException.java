package org.adempiere.processing.exception;

/*
 * #%L
 * ADempiere ERP - Base
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

public class ProcessingException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3863983572017503444L;

	private final int adProcessId;

	public ProcessingException(String message, Throwable cause, final int adProcessId)
	{
		super(message, cause);
		this.adProcessId = adProcessId;
	}

	public int getAdPInstanceId()
	{
		return adProcessId;
	}
}
