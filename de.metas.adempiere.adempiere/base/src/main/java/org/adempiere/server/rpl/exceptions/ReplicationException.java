package org.adempiere.server.rpl.exceptions;

import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;

/**
 * 
 * @author ad
 * @task http://dewiki908/mediawiki/index.php/03000:_ADempiere_Replication_understandable_Errormessages_% 282012071810000029%29
 */
public class ReplicationException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8608172237424907859L;

	private final String adMessage;
	private final Throwable cause;

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param adMessage the detail message.
	 */
	public ReplicationException(final String adMessage)
	{
		super("@" + adMessage + "@");
		this.adMessage = adMessage;
		cause = null;
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * @param adMessage
	 * @param cause
	 */
	public ReplicationException(final String adMessage, final Throwable cause)
	{
		super("@" + adMessage + "@", cause);
		this.adMessage = adMessage;
		this.cause = cause;
	}

	@Override
	public ReplicationException setParameter(final String parameterName, final Object value)
	{
		super.setParameter(parameterName, value);
		return this;
	}

	public ReplicationException addParameter(final String parameterName, final Object value)
	{
		return setParameter(parameterName, value);
	}

	@Override
	protected String buildMessage()
	{
		final StringBuilder sbParams = new StringBuilder();
		for (final Map.Entry<String, Object> e : getParameters().entrySet())
		{
			final String name = Services.get(IMsgBL.class).translate(getCtx(), e.getKey());
			final Object value = e.getValue();

			if (sbParams.length() > 0)
			{
				sbParams.append(", ");
			}

			sbParams.append(name).append(':').append(value);
		}

		final StringBuilder sb = new StringBuilder();

		sb.append(Services.get(IMsgBL.class).translate(getCtx(), adMessage));
		if (sbParams.length() > 0)
		{
			sb.append(" (").append(sbParams).append(")");
		}
		
		if (cause != null)
		{
			sb.append(" ").append(cause.getLocalizedMessage());
		}

		return sb.toString();
	}
}
