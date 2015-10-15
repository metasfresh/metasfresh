package org.compiere.acct;

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


import org.adempiere.util.Check;

/**
 * Posting Status.
 * 
 * See AD_Reference_ID=234.
 * 
 * @author tsa
 *
 */
public enum PostingStatus
{
	NotPosted("N"),
	NotBalanced("b"),
	NotConvertible("c"),
	PeriodClosed("p"),
	InvalidAccount("i"),
	PostPrepared("y"),
	Posted("Y"),
	Error("E");

	private final String status;

	PostingStatus(final String status)
	{
		Check.assumeNotEmpty(status, "status not empty");
		this.status = status;
	}

	public String getStatusCode()
	{
		return status;
	}

	public String getAD_Message()
	{
		return "PostingError-" + getStatusCode();
	}

	public static PostingStatus forStatusCode(final String statusCode)
	{
		for (final PostingStatus status : values())
		{
			if (status.getStatusCode().equals(statusCode))
			{
				return status;
			}
		}

		throw new IllegalArgumentException("No " + PostingStatus.class + " found for status code: " + statusCode);
	}
}
