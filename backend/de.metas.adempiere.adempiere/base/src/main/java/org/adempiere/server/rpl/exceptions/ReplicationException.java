package org.adempiere.server.rpl.exceptions;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import org.adempiere.exceptions.AdempiereException;

/**
 * @author ad
 * task http://dewiki908/mediawiki/index.php/03000:_ADempiere_Replication_understandable_Errormessages_% 282012071810000029%29
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
	 */
	public ReplicationException(final String adMessage, final Throwable cause)
	{
		super("@" + adMessage + "@", cause);
		this.adMessage = adMessage;
		this.cause = cause;
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		final TranslatableStringBuilder message = TranslatableStrings.builder();
		if(Check.isNotBlank(adMessage)) // avoide NPE
		{
			message.appendADMessage(adMessage);
			appendParameters(message);
		}
		
		if (cause != null)
		{
			message.append("\nCause: ").append(AdempiereException.extractMessage(cause));
		}

		return message.build();
	}
}
