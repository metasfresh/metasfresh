/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.client.schema;

import java.time.Duration;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class JsonAuthResponse
{
	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expires_in")
	private long expiresInSeconds;

	@JsonProperty("refresh_expires_in")
	private long refreshExpiresIn;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("not-before-policy")
	private String notBeforePolicy;

	@JsonProperty("session_stat")
	private String sessionState;

	@JsonProperty("error")
	private String error;

	@JsonProperty("error_description")
	private String errorDescription;

	@JsonIgnore
	private final Instant acquiredTimestamp = Instant.now();

	@JsonIgnore
	public boolean isExpired()
	{
		return Duration.between(acquiredTimestamp, Instant.now()).getSeconds() >= getExpiresInSeconds();
	}
}
