package de.metas.camel.externalsystems.scriptedadapter.oauth;

import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value(staticConstructor = "of")
public class OAuthAccessToken
{
	@NonNull String accessToken;
	@NonNull Instant expiresAt;

	public boolean isExpired(@NonNull final Instant now)
	{
		return now.isAfter(expiresAt);
	}
}
