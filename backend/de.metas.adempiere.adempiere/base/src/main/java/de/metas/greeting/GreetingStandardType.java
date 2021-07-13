/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.greeting;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Greeting;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode
public class GreetingStandardType
{
	public static final GreetingStandardType MR = new GreetingStandardType(X_C_Greeting.GREETINGSTANDARDTYPE_MR);
	public static final GreetingStandardType MRS = new GreetingStandardType(X_C_Greeting.GREETINGSTANDARDTYPE_MRS);
	public static final GreetingStandardType MR_AND_MRS = new GreetingStandardType(X_C_Greeting.GREETINGSTANDARDTYPE_MRPlusMRS);
	public static final GreetingStandardType MRS_AND_MR = new GreetingStandardType(X_C_Greeting.GREETINGSTANDARDTYPE_MRSPlusMR);

	private static final ConcurrentHashMap<String, GreetingStandardType> cache = new ConcurrentHashMap<>();

	static
	{
		cache.put(MR.getCode(), MR);
		cache.put(MRS.getCode(), MRS);
		cache.put(MR_AND_MRS.getCode(), MR_AND_MRS);
		cache.put(MRS_AND_MR.getCode(), MRS_AND_MR);
	}

	private final String code;

	private GreetingStandardType(@NonNull final String code)
	{
		this.code = StringUtils.trimBlankToNull(code);
		if (this.code == null)
		{
			throw new AdempiereException("Invalid code: " + code);
		}
	}

	@Nullable
	public static GreetingStandardType ofNullableCode(@Nullable final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		return codeNorm != null ? ofCode(codeNorm) : null;
	}

	@JsonCreator
	public static GreetingStandardType ofCode(@NonNull final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		if (codeNorm == null)
		{
			throw new AdempiereException("Invalid code: `" + code + "`");
		}
		return cache.computeIfAbsent(codeNorm, GreetingStandardType::new);
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

	@Nullable
	public static String toCode(@Nullable final GreetingStandardType type)
	{
		return type != null ? type.getCode() : null;
	}

	public GreetingStandardType composeWith(@NonNull final GreetingStandardType other)
	{
		return ofCode(code + "+" + other.code);
	}
}
