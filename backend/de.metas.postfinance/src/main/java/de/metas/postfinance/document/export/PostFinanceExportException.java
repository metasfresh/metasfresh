/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance.document.export;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.Nullable;

public class PostFinanceExportException extends AdempiereException
{

	public PostFinanceExportException(final String message)
	{
		super(message);
	}

	public PostFinanceExportException(final @NonNull ITranslatableString message)
	{
		super(message);
	}

	public PostFinanceExportException(final @NonNull AdMessageKey messageKey)
	{
		super(messageKey);
	}

	public PostFinanceExportException(final String adLanguage, final @NonNull AdMessageKey adMessage, final Object... params)
	{
		super(adLanguage, adMessage, params);
	}

	public PostFinanceExportException(final AdMessageKey adMessage, final Object... params)
	{
		super(adMessage, params);
	}

	public PostFinanceExportException(@Nullable final Throwable cause)
	{
		super(cause);
	}

	public PostFinanceExportException(final String plainMessage, @Nullable final Throwable cause)
	{
		super(plainMessage, cause);
	}

	public PostFinanceExportException(final @NonNull ITranslatableString message, @Nullable final Throwable cause)
	{
		super(message, cause);
	}
}
