package de.metas.ui.web.login.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Collection;
import java.util.List;

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
@Value
@Builder
@Jacksonized
public class JSONLoginAuthResponse
{
	private static final JSONLoginAuthResponse REQUIRES_2FA = builder().requires2FA(true).roles(ImmutableList.of()).loginComplete(false).build();

	boolean requires2FA;
	List<JSONLoginRole> roles;
	boolean loginComplete;

	public static JSONLoginAuthResponse requires2FA() {return REQUIRES_2FA;}

	public static JSONLoginAuthResponse of(@NonNull final Collection<JSONLoginRole> roles)
	{
		Check.assumeNotEmpty(roles, "roles is not empty");
		return builder().roles(ImmutableList.copyOf(roles)).loginComplete(false).build();
	}

	public static JSONLoginAuthResponse loginComplete(@NonNull final JSONLoginRole role)
	{
		return builder().roles(ImmutableList.of(role)).loginComplete(true).build();
	}
}
