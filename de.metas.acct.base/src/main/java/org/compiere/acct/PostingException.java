package org.compiere.acct;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_Fact_Acct;

import ch.qos.logback.classic.Level;

/**
 * Exception thrown by accounting engine on any document posting error.
 *
 * @author tsa
 *
 */
public final class PostingException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = -1591542147723525989L;

	//
	private Doc<?> _document;
	private DocLine<?> _docLine;
	private PostingStatus _postingStatus;
	private I_C_AcctSchema _acctSchema;
	private Fact _fact;
	private I_Fact_Acct _factLine;
	private String _detailMessage;
	private boolean _preserveDocumentPostedStatus = false;
	private Level _logLevel = Level.ERROR;

	public PostingException(final Doc<?> document)
	{
		this(document, (Throwable)null);
	}

	public PostingException(final Doc<?> document, final Throwable cause)
	{
		super(cause);

		setDocument(document);
		setDetailMessage(cause == null ? null : cause.getLocalizedMessage());
	}

	public PostingException(final String message)
	{
		super();
		setDetailMessage(message);
	}

	@Override
	protected String buildMessage()
	{
		final StringBuilder message = new StringBuilder();

		// Error message
		final String detailMessage = getDetailMessage();
		if (Check.isEmpty(detailMessage, true))
		{
			message.append("Posting error");
		}
		else
		{
			message.append(detailMessage);
		}

		// Posting Status
		final PostingStatus postingStatus = getPostingStatus();
		if (postingStatus != null)
		{
			message.append("\n @Posted@: @").append(postingStatus.getAD_Message()).append("@ (").append(postingStatus).append(")");
		}

		// Document
		final Doc<?> document = getDocument();
		if (document != null)
		{
			message.append("\n @Document@: ").append(document);
		}
		final DocLine<?> documentLine = getDocLine();
		if (documentLine != null)
		{
			message.append("\n @Line@: ").append(documentLine);
		}

		// Fact
		final Fact fact = getFact();
		if (fact != null)
		{
			message.append("\n Fact: ").append(fact);
		}

		final I_Fact_Acct factLine = getFactLine();
		if (factLine != null)
		{
			message.append("\n @Fact_Acct_ID@: ").append(factLine);
		}

		// Account Schema
		final I_C_AcctSchema acctSchema = getC_AcctSchema();
		if (acctSchema != null)
		{
			message.append("\n @C_AcctSchema_ID@: ").append(acctSchema);
		}

		message.append("\n Preserve Posted status: ").append(isPreserveDocumentPostedStatus());

		// Other parameters
		appendParameters(message);

		return message.toString();
	}

	public PostingException setDocument(final Doc<?> document)
	{
		this._document = document;
		return this;
	}

	public Doc<?> getDocument()
	{
		return _document;
	}

	public PostingStatus getPostingStatus()
	{
		return _postingStatus;
	}

	public PostingStatus getPostingStatus(final PostingStatus defaultStatus)
	{
		if (_postingStatus == null)
		{
			return defaultStatus;
		}
		return _postingStatus;
	}

	public PostingException setPostingStatus(final PostingStatus postingStatus)
	{
		_postingStatus = postingStatus;
		resetMessageBuilt();
		return this;
	}

	public I_C_AcctSchema getC_AcctSchema()
	{
		if (_acctSchema != null)
		{
			return _acctSchema;
		}
		
		if (_fact != null)
		{
			return _fact.getAcctSchema();
		}
		
		return null;
	}

	public PostingException setC_AcctSchema(final I_C_AcctSchema acctSchema)
	{
		_acctSchema = acctSchema;
		resetMessageBuilt();
		return this;
	}

	public String getDetailMessage()
	{
		return _detailMessage;
	}

	/**
	 * Sets the detail message.
	 * 
	 * NOTE: any other detail message which was appended by {@link #addDetailMessage(String)} will be lost.
	 * 
	 * @param detailMessage
	 * @see #addDetailMessage(String)
	 */
	public PostingException setDetailMessage(final String detailMessage)
	{
		_detailMessage = detailMessage;
		resetMessageBuilt();
		return this;
	}

	/**
	 * Appends given message to current detail message.
	 * 
	 * @param detailMessageToAppend
	 * @see #setDetailMessage(String)
	 */
	public PostingException addDetailMessage(final String detailMessageToAppend)
	{
		// If there is nothing to append, do nothing
		if (Check.isEmpty(detailMessageToAppend, true))
		{
			return this;
		}

		//
		// Set append the detail message
		if (Check.isEmpty(_detailMessage, true))
		{
			_detailMessage = detailMessageToAppend;
		}
		else
		{
			_detailMessage += "\n" + detailMessageToAppend;
		}

		resetMessageBuilt();
		return this;
	}

	public PostingException setFact(final Fact fact)
	{
		_fact = fact;
		resetMessageBuilt();
		return this;
	}

	public Fact getFact()
	{
		return _fact;
	}

	public I_Fact_Acct getFactLine()
	{
		return _factLine;
	}

	public PostingException setFactLine(final I_Fact_Acct factLine)
	{
		this._factLine = factLine;
		resetMessageBuilt();
		return this;
	}

	/**
	 * @return <code>true</code> if the document's "Posted" status shall not been changed.
	 */
	public boolean isPreserveDocumentPostedStatus()
	{
		return _preserveDocumentPostedStatus;
	}

	/**
	 * If set, the document's "Posted" status shall not been changed.
	 */
	public PostingException setPreserveDocumentPostedStatus()
	{
		_preserveDocumentPostedStatus = true;
		resetMessageBuilt();
		return this;
	}

	public PostingException setDocLine(final DocLine<?> docLine)
	{
		_docLine = docLine;
		resetMessageBuilt();
		return this;
	}

	public DocLine<?> getDocLine()
	{
		return _docLine;
	}

	public PostingException setLogLevel(final Level logLevel)
	{
		Check.assumeNotNull(logLevel, "logLevel not null");
		this._logLevel = logLevel;
		return this;
	}

	/** @return recomanded log level to be used when reporting this issue */
	public Level getLogLevel()
	{
		return _logLevel;
	}

	@Override
	public PostingException setParameter(final String parameterName, Object parameterValue)
	{
		super.setParameter(parameterName, parameterValue);
		return this;
	}
}
