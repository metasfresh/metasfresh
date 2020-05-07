package org.adempiere.server.rpl.exceptions;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import de.metas.i18n.IMsgBL;

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
	protected String buildMessage()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(Services.get(IMsgBL.class).translate(getADLanguage(), adMessage));
		
		appendParameters(sb);
		
		if (cause != null)
		{
			sb.append("\nCause: ").append(cause.getLocalizedMessage());
		}

		return sb.toString();
	}
}
