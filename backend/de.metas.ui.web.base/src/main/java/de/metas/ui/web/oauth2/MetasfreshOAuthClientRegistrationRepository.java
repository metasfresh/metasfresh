package de.metas.ui.web.oauth2;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.ui.web.WebuiURLs;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.compiere.model.I_OAuth2_Client;
import org.slf4j.Logger;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MetasfreshOAuthClientRegistrationRepository implements ClientRegistrationRepository
{
	@NonNull private static final Logger logger = LogManager.getLogger(MetasfreshOAuthClientRegistrationRepository.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final WebuiURLs webuiURLs = WebuiURLs.newInstance();

	private final CCache<CacheKey, MetasfreshClientRegistrations> cache = CCache.<CacheKey, MetasfreshClientRegistrations>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.tableName(I_OAuth2_Client.Table_Name)
			.maximumSize(2)
			.build();

	@Override
	@Nullable
	public ClientRegistration findByRegistrationId(final String registrationId)
	{
		return getByRegistrationId(registrationId)
				.map(MetasfreshClientRegistration::getSpringClientRegistration)
				.orElse(null);
	}

	private Optional<MetasfreshClientRegistration> getByRegistrationId(final String registrationId)
	{
		return getAll().getById(registrationId);
	}

	public Stream<MetasfreshClientRegistration> stream() {return getAll().stream();}

	private MetasfreshClientRegistrations getAll()
	{
		return cache.getOrLoad(createCacheKey(), this::retrieveAll);
	}

	@NonNull
	private CacheKey createCacheKey()
	{
		return CacheKey.builder()
				.backendBaseUrl(webuiURLs.getBackendURL())
				.build();

	}

	private MetasfreshClientRegistrations retrieveAll(final CacheKey cacheKey)
	{
		if (cacheKey.getBackendBaseUrl() == null)
		{
			logger.warn("No backend URL configured; considering no oauth2 client registrations");
			return MetasfreshClientRegistrations.EMPTY;
		}

		return queryBL.createQueryBuilder(I_OAuth2_Client.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_OAuth2_Client.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH, ClientId.SYSTEM)
				.stream()
				.map(record -> toClientRegistration(record, cacheKey))
				.collect(MetasfreshClientRegistrations.collect());
	}

	private MetasfreshClientRegistration toClientRegistration(@NonNull final I_OAuth2_Client record, final CacheKey cacheKey)
	{
		final String backendBaseUrl = cacheKey.getBackendBaseUrl();
		final String clientSecret = StringUtils.trimBlankToNull(record.getOAuth2_Client_Secret());

		return MetasfreshClientRegistration.builder()
				.springClientRegistration(
						ClientRegistration.withRegistrationId(StringUtils.trimBlankToNull(record.getInternalName()))
								.clientName(StringUtils.trimBlankToNull(record.getName()))
								.clientId(StringUtils.trimBlankToNull(record.getOAuth2_ClientId()))
								.clientSecret(clientSecret)
								.clientAuthenticationMethod(clientSecret != null ? ClientAuthenticationMethod.CLIENT_SECRET_POST : ClientAuthenticationMethod.NONE)
								.issuerUri(StringUtils.trimBlankToNull(record.getOAuth2_Issuer_URI()))
								.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
								.authorizationUri(StringUtils.trimBlankToNull(record.getOAuth2_Authorization_URI()))
								.scope("openid", "profile", "email")
								.tokenUri(StringUtils.trimBlankToNull(record.getOAuth2_Token_URI()))
								.userInfoUri(StringUtils.trimBlankToNull(record.getOAuth2_UserInfo_URI()))
								.jwkSetUri(StringUtils.trimBlankToNull(record.getOAuth2_JWKS_URI()))
								.userNameAttributeName(IdTokenClaimNames.SUB)
								.redirectUri(backendBaseUrl + "/login/oauth2/code/{registrationId}") // Spring's default callback URI
								.build()
				)
				.logoUri(StringUtils.trimBlankToNull(record.getOAuth2_Logo_URI()))
				.build();
	}

	@Value
	@Builder
	private static class CacheKey
	{
		@Nullable String backendBaseUrl;
	}
}
