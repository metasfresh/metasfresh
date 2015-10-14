package org.adempiere.acct.api;

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


/**
 * Status of Client Accounting configuration.
 * 
 * @author tsa
 *
 */
public enum ClientAccountingStatus
{
	/**
	 * Client accounting is disabled
	 */
	Disabled("D"),
	/**
	 * Client accounting is enabled but posting will be enqueued instead of done immediate.
	 * 
	 * To process the client accounting enqueued documents, there is a process in menu.
	 */
	Queue("Q"),
	/**
	 * Client accounting is enabled and posting will be done immediate
	 */
	Immediate("I")
	//
	;

	private final String statusCode;

	private ClientAccountingStatus(final String statusCode)
	{
		this.statusCode = statusCode;
	}

	public String getStatusCode()
	{
		return statusCode;
	}

	public static final ClientAccountingStatus forStatusCode(final String statusCode)
	{
		for (ClientAccountingStatus status : values())
		{
			if (statusCode.equals(status.getStatusCode()))
			{
				return status;
			}
		}

		throw new IllegalArgumentException("No " + ClientAccountingStatus.class + " found for status code: " + statusCode);
	}
}
