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

package de.metas.camel.externalsystems.core.restapi.auth.preauthenticated;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.Check;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;

@Component
public class ActuatorIdentity implements PreAuthenticatedIdentity
{
	public static final String ACTUATOR_AUTHORITY = "ACTUATOR";

	@Nullable
	@Value(value = "${de.metas.camel.externalsystems.actuator.auth:#{null}}")
	private String authToken;

	@Override
	public Optional<PreAuthenticatedAuthenticationToken> getPreAuthenticatedToken()
	{
		if (Check.isBlank(authToken))
		{
			return Optional.empty();
		}

		final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ACTUATOR_AUTHORITY);

		final PreAuthenticatedAuthenticationToken preAuthToken = new PreAuthenticatedAuthenticationToken(authToken, null, ImmutableList.of(authority));

		return Optional.of(preAuthToken);
	}
}
