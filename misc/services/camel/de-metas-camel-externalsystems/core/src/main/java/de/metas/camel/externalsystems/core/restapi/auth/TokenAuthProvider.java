/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core.restapi.auth;

import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthProvider implements AuthenticationProvider
{
	private final TokenService tokenService;

	public TokenAuthProvider(@NonNull final TokenService tokenService)
	{
		this.tokenService = tokenService;
	}

	@Override
	@NonNull
	public Authentication authenticate(@NonNull final Authentication authentication) throws AuthenticationException
	{
		final Object token = authentication.getPrincipal();

		return tokenService.retrieve(token)
				.orElseThrow(() -> new BadCredentialsException("Not authenticated!"));
	}

	@Override
	public boolean supports(@NonNull final Class<?> authentication)
	{
		return authentication.equals(PreAuthenticatedAuthenticationToken.class);
	}
}
