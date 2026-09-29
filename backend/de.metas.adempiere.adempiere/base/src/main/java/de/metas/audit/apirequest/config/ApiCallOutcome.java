package de.metas.audit.apirequest.config;

/**
 * Outcome of an audited API call, derived from its HTTP status.
 */
public enum ApiCallOutcome
{
	SUCCESS,
	/** HTTP 207: the call was processed, but some of its parts failed. */
	PARTIAL_ERROR,
	ERROR;

	public static ApiCallOutcome ofHttpStatus(final int httpStatusCode)
	{
		if (httpStatusCode == 207)
		{
			return PARTIAL_ERROR;
		}
		return httpStatusCode / 100 == 2 ? SUCCESS : ERROR;
	}
}
