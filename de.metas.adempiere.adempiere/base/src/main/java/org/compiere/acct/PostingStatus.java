package org.compiere.acct;

import de.metas.i18n.AdMessageKey;
import de.metas.util.Check;

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

	public AdMessageKey getAD_Message()
	{
		return AdMessageKey.of("PostingError-" + getStatusCode());
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
