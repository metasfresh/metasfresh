package de.metas.camel.externalsystems.scriptedadapter.oauth;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
@ToString(exclude = { "clientSecret", "password" })
public class OAuthAccessTokenRequest
{
	@NonNull OAuthIdentity identity;
	@Nullable String clientSecret;
	@Nullable String password;
}
