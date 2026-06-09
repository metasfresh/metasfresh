package de.metas.camel.externalsystems.scriptedadapter.oauth;

import de.metas.common.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class OAuthIdentity
{
	@NonNull String tokenUrl;
	@Nullable String clientId;
	@Nullable String username;

	@Builder
	private OAuthIdentity(
			@NonNull final String tokenUrl,
			@Nullable final String clientId,
			@Nullable final String username)
	{
		final String tokenUrlNorm = StringUtils.trimBlankToNull(tokenUrl);
		if (tokenUrlNorm == null)
		{
			throw new IllegalArgumentException("The given tokenUrl is null or blank!");
		}

		this.tokenUrl = tokenUrlNorm;
		this.clientId = StringUtils.trimBlankToNull(clientId);
		this.username = StringUtils.trimBlankToNull(username);
	}
}
