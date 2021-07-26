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

import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService
{
	private final ConcurrentHashMap<String, Authentication> restApiAuthToken = new ConcurrentHashMap<>();

	public void store(
			@NonNull final String token,
			@Nullable final List<GrantedAuthority> grantedAuthorities,
			@NonNull final TokenCredentials tokenCredentials)
	{
		final PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(token, tokenCredentials, grantedAuthorities);

		restApiAuthToken.put(token, authentication);
	}

	@NonNull
	public Optional<Authentication> retrieve(@Nullable final Object token)
	{
		if (token == null)
		{
			return Optional.empty();
		}

		return Optional.of(restApiAuthToken.get(String.valueOf(token)));
	}

	public void expiryToken(@NonNull final Object token)
	{
		restApiAuthToken.remove(String.valueOf(token));
	}

	public int getNumberOfAuthenticatedTokens(@NonNull final String authority)
	{
		final SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);

		return (int)restApiAuthToken.values()
				.stream()
				.map(Authentication::getAuthorities)
				.map(item -> item.contains(grantedAuthority))
				.count();
	}
}
