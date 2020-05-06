package de.metas.acct.vatcode;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import com.google.common.base.MoreObjects;

import de.metas.util.Check;

/*
 * #%L
 * de.metas.acct.base
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

public final class VATCode
{
	public static final VATCode NULL = new VATCode();

	public static final VATCode of(final String code)
	{
		return new VATCode(code);
	}

	private final String code;

	/** null constructor */
	private VATCode()
	{
		super();
		code = null;
	}

	private VATCode(final String code)
	{
		super();
		Check.assumeNotEmpty(code, "code not empty");
		this.code = code;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("code", code)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(code)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final VATCode other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(code, other.code)
				.isEqual();
	}

	public String getCode()
	{
		return code;
	}
}
