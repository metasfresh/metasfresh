package de.metas.ui.web.window.model;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public final class DocumentValidStatus
{
	public static DocumentValidStatus documentInitiallyInvalid()
	{
		return STATE_InitialInvalid;
	}

	public static DocumentValidStatus fieldInitiallyInvalid()
	{
		return STATE_InitialInvalid;
	}

	public static DocumentValidStatus documentValid()
	{
		return STATE_Valid;
	}

	public static DocumentValidStatus validField(final String fieldName, final boolean isInitialValue)
	{
		return new DocumentValidStatus(true, null, null, fieldName, isInitialValue);
	}

	public static DocumentValidStatus invalidFieldMandatoryNotFilled(final String fieldName, final boolean isInitialValue)
	{
		return new DocumentValidStatus(false, FillMandatoryException.buildMessage(fieldName), null, fieldName, isInitialValue);
	}

	public static DocumentValidStatus invalid(@NonNull final Exception error)
	{
		return new DocumentValidStatus(false, AdempiereException.extractMessageTrl(error), error, null, null);
	}

	private static final DocumentValidStatus STATE_InitialInvalid = new DocumentValidStatus(false, TranslatableStrings.anyLanguage("not validated yet"), null, null, Boolean.TRUE);
	private static final DocumentValidStatus STATE_Valid = new DocumentValidStatus(true, null, null, null, null);

	@Getter private final boolean valid;
	@Nullable @Getter private final Boolean initialValue;
	@Nullable @Getter private final ITranslatableString reason;
	@Nullable @Getter private final Exception exception;
	@Nullable @Getter private final String fieldName;

	private transient Integer _hashcode; // lazy
	private transient String _toString; // lazy

	private DocumentValidStatus(
			final boolean valid,
			@Nullable final ITranslatableString reason,
			@Nullable final Exception exception,
			@Nullable final String fieldName,
			@Nullable final Boolean isInitialValue)
	{
		this.valid = valid;
		this.initialValue = isInitialValue;
		this.reason = reason;
		this.exception = exception;
		this.fieldName = fieldName;
	}

	@Override
	public String toString()
	{
		String toString = this._toString;
		if (toString == null)
		{
			final StringBuilder sb = new StringBuilder();

			sb.append(valid ? "Valid" : "Invalid");

			if (initialValue != null && initialValue)
			{
				sb.append("-Initial");
			}

			if (!TranslatableStrings.isBlank(reason))
			{
				sb.append("('").append(reason).append("')");
			}

			toString = this._toString = sb.toString();
		}
		return toString;
	}

	@Override
	public int hashCode()
	{
		Integer hashcode = this._hashcode;
		if (hashcode == null)
		{
			hashcode = this._hashcode = Objects.hash(valid, initialValue, reason, fieldName);
		}
		return hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (!(obj instanceof DocumentValidStatus))
		{
			return false;
		}

		final DocumentValidStatus other = (DocumentValidStatus)obj;
		return valid == other.valid
				&& Objects.equals(initialValue, other.initialValue)
				&& Objects.equals(reason, other.reason)
				&& Objects.equals(fieldName, other.fieldName);
	}

	public boolean isInitialInvalid()
	{
		return this == STATE_InitialInvalid;
	}

	public void throwIfInvalid()
	{
		if (isValid())
		{
			return;
		}

		if (exception != null)
		{
			throw AdempiereException.wrapIfNeeded(exception);
		}
		else
		{
			throw new AdempiereException(reason != null ? reason : TranslatableStrings.anyLanguage("Invalid"));
		}
	}

}
