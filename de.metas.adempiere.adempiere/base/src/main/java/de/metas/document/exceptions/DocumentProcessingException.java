package de.metas.document.exceptions;

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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.compiere.process.DocAction;

/**
 * Exception thrown when document processing failed.
 * 
 * @author tsa
 *
 */
@SuppressWarnings("serial")
public class DocumentProcessingException extends AdempiereException
{
	public DocumentProcessingException(final DocAction document, final String docAction, final Throwable cause)
	{
		super(buildMsg((String)null, document, docAction, cause), cause);
	}

	public DocumentProcessingException(final DocAction document, final String docAction)
	{
		super(buildMsg((String)null, document, docAction, (Throwable)null));
	}

	public DocumentProcessingException(final String message, final Object documentObj, final String docAction)
	{
		super(buildMsg(message, documentObj, docAction, (Throwable)null));
	}

	private static final String buildMsg(final String message, final Object documentObj, final String docAction, final Throwable cause)
	{
		final StringBuilder msg = new StringBuilder();
		if (Check.isEmpty(message, true))
		{
			msg.append("Error Processing Document");
		}
		else
		{
			msg.append(message.trim());
		}
		
		final String documentInfo;
		final String processMsg;
		if (documentObj == null)
		{
			// shall not happen
			documentInfo = "no document";
			processMsg = null;
		}
		else if(documentObj instanceof DocAction)
		{
			documentInfo = ((DocAction)documentObj).getDocumentInfo();
			processMsg = ((DocAction)documentObj).getProcessMsg();
		}
		else
		{
			documentInfo = documentObj.toString();
			processMsg = null;
		}
		
		msg.append("\n@Document@: ").append(documentInfo);
		msg.append("\n@DocAction@: ").append(docAction);
		if(!Check.isEmpty(processMsg, true))
		{
			msg.append("\n@ProcessMsg@: ").append(processMsg);
		}

		if (cause != null)
		{
			Throwable rootCause = ExceptionUtils.getRootCause(cause);
			String rooCauseMessage = ExceptionUtils.getRootCauseMessage(cause);
			if (rootCause == null)
			{ 
				// our version of ExceptionUtils might return null, so we need this if
				rootCause = cause;
				rooCauseMessage = cause.getMessage();
			}
			
			msg.append("\n@Cause@: ").append(rooCauseMessage)
					.append(" (")
					.append(rootCause.getClass().getSimpleName())
					.append(")");
		}
		return msg.toString();
	}
}
