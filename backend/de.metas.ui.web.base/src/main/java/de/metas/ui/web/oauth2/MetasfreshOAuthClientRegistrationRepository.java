package de.metas.ui.web.oauth2;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.ui.web.WebuiURLs;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MetasfreshOAuthClientRegistrationRepository implements ClientRegistrationRepository, Iterable<ClientRegistration>
{
	@NonNull private final WebuiURLs webuiURLs = WebuiURLs.newInstance();

	private final CCache<CacheKey, ClientRegistrations> cache = CCache.<CacheKey, ClientRegistrations>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.maximumSize(2)
			.build();

	@Override
	@Nullable
	public ClientRegistration findByRegistrationId(final String registrationId)
	{
		return getAll().getById(registrationId).orElse(null);
	}

	@Override
	@NotNull
	public Iterator<ClientRegistration> iterator() {return getAll().iterator();}

	public Stream<ClientRegistration> stream() {return getAll().stream();}

	private ClientRegistrations getAll()
	{
		final CacheKey cacheKey = createCacheKey();
		return cache.getOrLoad(cacheKey, this::retrieveAll);
	}

	private CacheKey createCacheKey()
	{
		return CacheKey.builder()
				.backendBaseUrl("https://myapp8080.loca.lt") // FIXME
				.frontendBaseUrl(webuiURLs.getFrontendURL())
				.build();

	}

	private ClientRegistrations retrieveAll(final CacheKey cacheKey)
	{
		return ClientRegistrations.ofList(ImmutableList.of(
				// TODO
		));
	}


	@Value
	@Builder
	private static class CacheKey
	{
		@NonNull String backendBaseUrl;
		@NonNull String frontendBaseUrl;
	}
}
