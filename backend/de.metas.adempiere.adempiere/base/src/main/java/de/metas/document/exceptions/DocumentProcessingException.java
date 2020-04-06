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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.exceptions.AdempiereException;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Exception thrown when document processing failed.
 * 
 * @author tsa
 *
 */
@SuppressWarnings("serial")
public class DocumentProcessingException extends AdempiereException
{
	public DocumentProcessingException(final IDocument document, final String docAction, final Throwable cause)
	{
		super(buildMsg((String)null, document, docAction, cause), cause);
		putParametetersFrom(cause);
	}

	public DocumentProcessingException(final IDocument document, final String docAction)
	{
		super(buildMsg((String)null, document, docAction, (Throwable)null));
	}

	public DocumentProcessingException(final String message, final Object documentObj, final String docAction)
	{
		super(buildMsg(message, documentObj, docAction, (Throwable)null));
	}

	private static final ITranslatableString buildMsg(final String message, final Object documentObj, final String docAction, final Throwable cause)
	{
		final TranslatableStringBuilder msg = TranslatableStrings.builder();
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
		else
		{
			final IDocument document = Services.get(IDocumentBL.class).getDocumentOrNull(documentObj);
			if (document != null)
			{
				documentInfo = document.getDocumentInfo();
				processMsg = document.getProcessMsg();
			}
			else
			{
				documentInfo = documentObj.toString();
				processMsg = null;
			}
		}

		msg.append("\n").appendADElement("Document").append(": ").append(documentInfo);
		msg.append("\n").appendADElement("DocAction").append(": ").append(docAction);
		if (!Check.isEmpty(processMsg, true))
		{
			msg.append("\n").appendADElement("ProcessMsg").append(": ").append(processMsg);
		}

		if (cause != null)
		{
			final Throwable rootCause = AdempiereException.extractCause(cause);
			ITranslatableString rootCauseMessage = AdempiereException.extractMessageTrl(rootCause);

			msg.append("\n").appendADElement("Cause").append(": ").append(rootCauseMessage)
					.append(" (")
					.append(rootCause.getClass().getSimpleName())
					.append(")");
		}
		return msg.build();
	}
}
