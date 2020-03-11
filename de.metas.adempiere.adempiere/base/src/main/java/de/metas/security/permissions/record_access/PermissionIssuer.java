package de.metas.security.permissions.record_access;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PermissionIssuer
{
	public static PermissionIssuer MANUAL = new PermissionIssuer("MANUAL");
	public static PermissionIssuer AUTO_BP_HIERARCHY = new PermissionIssuer("AUTO_BP_HIERARCHY");

	@JsonCreator
	public static PermissionIssuer ofCode(final String code)
	{
		final PermissionIssuer permissionIssuer = cacheByCode.get(code);
		if (permissionIssuer != null)
		{
			return permissionIssuer;
		}

		return new PermissionIssuer(code);
	}

	private static ImmutableMap<String, PermissionIssuer> cacheByCode = ImmutableMap.<String, PermissionIssuer> builder()
			.put(MANUAL.getCode(), MANUAL)
			.put(AUTO_BP_HIERARCHY.getCode(), AUTO_BP_HIERARCHY)
			.build();

	private final String code;

	private PermissionIssuer(@NonNull final String code)
	{
		Check.assumeNotEmpty(code, "code is not empty");
		this.code = code;
	}

	/**
	 * @deprecated use {@link #getCode()}
	 */
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
}
