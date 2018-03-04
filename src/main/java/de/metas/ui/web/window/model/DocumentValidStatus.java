package de.metas.ui.web.window.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NonNull;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class DocumentValidStatus
{
	public static final DocumentValidStatus documentInitiallyInvalid()
	{
		return STATE_InitialInvalid;
	}

	public static final DocumentValidStatus fieldInitiallyInvalid()
	{
		return STATE_InitialInvalid;
	}

	public static final DocumentValidStatus documentValid()
	{
		return STATE_Valid;
	}

	public static final DocumentValidStatus validField(final String fieldName, final boolean isInitialValue)
	{
		return new DocumentValidStatus(VALID_Yes, REASON_Null, fieldName, isInitialValue);
	}

	public static final DocumentValidStatus invalidIncludedDocument()
	{
		return STATE_InvalidIncludedDocument;
	}

	public static final DocumentValidStatus invalidFieldMandatoryNotFilled(final String fieldName, final boolean isInitialValue)
	{
		return new DocumentValidStatus(VALID_No, "Mandatory field " + fieldName + " not filled", fieldName, isInitialValue);
	}

	public static final DocumentValidStatus invalid(@NonNull final Throwable error)
	{
		return new DocumentValidStatus(VALID_No, error.getLocalizedMessage(), FIELDNAME_Null, INITIALVALUE_Unknown);
	}

	private static final boolean VALID_Yes = true;
	private static final boolean VALID_No = false;
	private static final Boolean INITIALVALUE_Yes = Boolean.TRUE;
	@SuppressWarnings("unused")
	private static final Boolean INITIALVALUE_No = Boolean.FALSE;
	private static final Boolean INITIALVALUE_Unknown = null;
	private static final String REASON_Null = null;
	private static final String FIELDNAME_Null = null;

	private static final DocumentValidStatus STATE_InitialInvalid = new DocumentValidStatus(VALID_No, "not validated yet", FIELDNAME_Null, INITIALVALUE_Yes);
	private static final DocumentValidStatus STATE_Valid = new DocumentValidStatus(VALID_Yes, REASON_Null, FIELDNAME_Null, INITIALVALUE_Unknown);
	private static final DocumentValidStatus STATE_InvalidIncludedDocument = new DocumentValidStatus(VALID_No, "child invalid", FIELDNAME_Null, INITIALVALUE_Unknown);

	@JsonProperty("valid")
	private final boolean valid;

	@JsonProperty("initialValue")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean initialValue;

	@JsonProperty("reason")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String reason;

	@JsonProperty("fieldName")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String fieldName;

	private transient Integer _hashcode;
	private transient String _toString;

	private DocumentValidStatus(final boolean valid, final String reason, final String fieldName, final Boolean isInitialValue)
	{
		super();
		this.valid = valid;
		initialValue = isInitialValue;
		this.reason = reason;
		this.fieldName = fieldName;
	}

	@Override
	public String toString()
	{
		if (_toString == null)
		{
			final StringBuilder sb = new StringBuilder();

			sb.append(valid ? "Valid" : "Invalid");

			if (initialValue != null && initialValue)
			{
				sb.append("-Initial");
			}

			if (reason != null && !reason.isEmpty())
			{
				sb.append("('").append(reason).append("')");
			}

			_toString = sb.toString();
		}
		return _toString;
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(valid, initialValue, reason, fieldName);
		}
		return _hashcode;
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

	public boolean isValid()
	{
		return valid;
	}

	public String getReason()
	{
		return reason;
	}
	
	public boolean isInitialInvalid()
	{
		return this == STATE_InitialInvalid;
	}
}
