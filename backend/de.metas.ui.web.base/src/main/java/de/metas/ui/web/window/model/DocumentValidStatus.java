package de.metas.ui.web.window.model;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.util.Trace;

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
		return new DocumentValidStatus(VALID_Yes, REASON_Null, EXCEPTION_Null, fieldName, isInitialValue);
	}

	public static DocumentValidStatus invalidIncludedDocument()
	{
		return STATE_InvalidIncludedDocument;
	}

	public static DocumentValidStatus invalidFieldMandatoryNotFilled(final String fieldName, final boolean isInitialValue)
	{
		return new DocumentValidStatus(VALID_No, FillMandatoryException.buildMessage(fieldName), EXCEPTION_Null, fieldName, isInitialValue);
	}

	public static DocumentValidStatus invalid(@NonNull final Exception error)
	{
		return new DocumentValidStatus(VALID_No, AdempiereException.extractMessageTrl(error), error, FIELDNAME_Null, INITIALVALUE_Unknown);
	}

	private static final boolean VALID_Yes = true;
	private static final boolean VALID_No = false;
	private static final Boolean INITIALVALUE_Yes = Boolean.TRUE;
	@SuppressWarnings("unused")
	private static final Boolean INITIALVALUE_No = Boolean.FALSE;
	private static final Boolean INITIALVALUE_Unknown = null;
	private static final ITranslatableString REASON_Null = null;
	private static final Exception EXCEPTION_Null = null;
	private static final String FIELDNAME_Null = null;

	private static final DocumentValidStatus STATE_InitialInvalid = new DocumentValidStatus(VALID_No, TranslatableStrings.anyLanguage("not validated yet"), EXCEPTION_Null, FIELDNAME_Null, INITIALVALUE_Yes);
	private static final DocumentValidStatus STATE_Valid = new DocumentValidStatus(VALID_Yes, REASON_Null, EXCEPTION_Null, FIELDNAME_Null, INITIALVALUE_Unknown);
	private static final DocumentValidStatus STATE_InvalidIncludedDocument = new DocumentValidStatus(VALID_No, TranslatableStrings.anyLanguage("child invalid"), EXCEPTION_Null, FIELDNAME_Null, INITIALVALUE_Unknown);

	@Getter private final boolean valid;
	@Nullable @Getter private final Boolean initialValue;
	@Nullable @Getter private final ITranslatableString reason;
	@Nullable @Getter private final Exception exception;
	@Nullable @Getter private final String fieldName;

	private transient Integer _hashcode; // lazy
	private transient String _toString; // lazy
	private transient String _exceptionAsOneLineString; // lazy

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

	@Nullable
	public String getExceptionAsOneLineString()
	{
		if (exception == null)
		{
			return null;
		}

		String exceptionAsOneLineString = this._exceptionAsOneLineString;
		if (exceptionAsOneLineString == null)
		{
			exceptionAsOneLineString = this._exceptionAsOneLineString = Trace.toOneLineStackTraceString(exception);
		}
		return exceptionAsOneLineString;
	}

}
