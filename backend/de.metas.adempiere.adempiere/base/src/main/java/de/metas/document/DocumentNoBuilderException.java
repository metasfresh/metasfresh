package de.metas.document;

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

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

/**
 * Exception thrown by {@link DocumentNoBuilderException} when building the document number failed.
 *
 * @author tsa
 *
 */
public class DocumentNoBuilderException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 2107154047622830909L;

	/**
	 * Wraps given <code>throwable</code> as {@link DocumentNoBuilderException}, if it's not already an {@link DocumentNoBuilderException}.
	 *
	 * @param throwable
	 * @return {@link DocumentNoBuilderException} or <code>null</code> if the throwable was null.
	 */
	public static DocumentNoBuilderException wrapIfNeeded(final Throwable throwable)
	{
		if (throwable == null)
		{
			return null;
		}
		else if (throwable instanceof DocumentNoBuilderException)
		{
			return (DocumentNoBuilderException)throwable;
		}
		else
		{
			return new DocumentNoBuilderException(throwable.getLocalizedMessage(), throwable);
		}
	}

	private boolean skipGenerateDocumentNo = false;

	public DocumentNoBuilderException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public DocumentNoBuilderException(final AdMessageKey adMessage, final Object... params)
	{
		super(adMessage, params);
	}

	// NOTE: please keep this constructor because it's used in Check.assume methods
	public DocumentNoBuilderException(final String message)
	{
		super(message);
	}

	public DocumentNoBuilderException(@NonNull final ITranslatableString message)
	{
		super(message);
	}

	public DocumentNoBuilderException setSkipGenerateDocumentNo(final boolean skipGenerateDocumentNo)
	{
		this.skipGenerateDocumentNo = skipGenerateDocumentNo;
		return this;
	}

	public boolean isSkipGenerateDocumentNo()
	{
		return skipGenerateDocumentNo;
	}

}
