package de.metas.acct.vatcode;

import com.google.common.base.MoreObjects;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

@EqualsAndHashCode
@ToString
public final class VATCode
{
	public static final VATCode NULL = new VATCode();

	public static VATCode of(final String code)
	{
		return new VATCode(code);
	}

	@Getter
	private final String code;

	/** null constructor */
	private VATCode()
	{
		code = null;
	}

	private VATCode(final String code)
	{
		Check.assumeNotEmpty(code, "code not empty");
		this.code = code;
	}
}
