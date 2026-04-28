/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.PREFIX_IMPORT_AUTHORITY;

@UtilityClass
public class ScriptedImportConversionDynamicAuthorizationManager
{
	public static final String PATTERN = "/interchange/import/**";

	@NonNull
	public AuthorizationDecision getAuthorizationDecision(
			@NonNull final Supplier<Authentication> auth,
			@NonNull final RequestAuthorizationContext ctx)
	{
		final boolean authorized = auth.get().getAuthorities()
				.stream()
				.anyMatch(a -> a.getAuthority().equals(PREFIX_IMPORT_AUTHORITY + getEndpointName(ctx)));

		return new AuthorizationDecision(authorized);
	}

	@NonNull
	private String getEndpointName(@NonNull final RequestAuthorizationContext ctx)
	{
		final String path = ctx.getRequest().getRequestURI();
		return path.substring(path.lastIndexOf('/') + 1);
	}
}
