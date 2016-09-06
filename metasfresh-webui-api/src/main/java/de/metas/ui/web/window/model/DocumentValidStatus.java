package de.metas.ui.web.window.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class DocumentValidStatus
{
	public static final DocumentValidStatus inititalInvalid()
	{
		return STATE_InitialInvalid;
	}

	public static final DocumentValidStatus staled()
	{
		return STATE_Staled;
	}

	public static final DocumentValidStatus valid()
	{
		return STATE_Valid;
	}

	public static final DocumentValidStatus invalidMandatoryFieldNotFilled(final String fieldName)
	{
		return new DocumentValidStatus(false, "Mandatory field not filled: " + fieldName);
	}

	private static final DocumentValidStatus STATE_InitialInvalid = new DocumentValidStatus(false, "not validated yet");
	private static final DocumentValidStatus STATE_Staled = new DocumentValidStatus(false, "staled");
	private static final DocumentValidStatus STATE_Valid = new DocumentValidStatus(true, null);

	private final boolean valid;
	private final String reason;
	private transient Integer _hashcode;
	private transient String _toString;

	private DocumentValidStatus(final boolean valid, final String reason)
	{
		super();
		this.valid = valid;
		this.reason = reason;
	}

	@Override
	public String toString()
	{
		if (_toString == null)
		{
			final StringBuilder sb = new StringBuilder();
			sb.append(valid ? "Valid" : "Invalid");
			if (reason != null && !reason.isEmpty())
			{
				sb.append("('").append(reason).append("')");
			}
			_toString = sb.toString();
		}
		return _toString;
	}

	public String toJson()
	{
		return toString();
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(valid, reason);
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
				&& Objects.equals(reason, other.reason);
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

	public boolean isStaled()
	{
		return this == STATE_Staled;
	}

	public boolean isInvalidButNotInitial()
	{
		return !isValid() && !isInitialInvalid();
	}
}
