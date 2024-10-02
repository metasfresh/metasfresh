package de.metas.bpartner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * Global Location Number.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Global_Location_Number">GLN</a>
 */
@Value
public class GLN
{
	public static final int LENGTH = 13;

	@JsonCreator
	public static GLN ofString(@NonNull final String code)
	{
		return new GLN(code);
	}

	public static GLN ofNullableString(@Nullable final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		return codeNorm != null ? ofString(code) : null;
	}

	@NonNull String code;

	private GLN(@NonNull final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		if (codeNorm == null)
		{
			throw new AdempiereException("GLN code cannot be blank");
		}
		this.code = codeNorm;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getCode();
	}

	@JsonValue
	public String getCode()
	{
		return code;
	}

	public static Set<String> toStringSet(@Nullable final Collection<GLN> glns)
	{
		if (glns == null || glns.isEmpty())
		{
			return ImmutableSet.of();
		}

		return glns.stream().map(GLN::getCode).collect(ImmutableSet.toImmutableSet());
	}

	public static String toCode(@Nullable final GLN gln)
	{
		return gln != null ? gln.getCode() : null;
	}

	public static boolean equals(@Nullable final GLN gln1, @Nullable final GLN gln2)
	{
		return Objects.equals(gln1, gln2);
	}
}
