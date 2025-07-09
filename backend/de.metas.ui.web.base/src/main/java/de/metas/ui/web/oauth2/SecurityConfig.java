package de.metas.ui.web.oauth2;

import de.metas.logging.LogManager;
import de.metas.ui.web.WebuiURLs;
import de.metas.ui.web.login.LoginRestController;
import de.metas.user.api.IUserBL;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.util.hash.HashableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_User;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig
{
	@NonNull private static final Logger logger = LogManager.getLogger(SecurityConfig.class);
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final IUserDAO userDAO = Services.get(IUserDAO.class);
	@NonNull private final WebuiURLs webuiURLs = WebuiURLs.newInstance();
	@NonNull private final OAuth2AuthorizedClientService authorizedClientService;
	@NonNull private final LoginRestController loginRestController;

	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception
	{
		http
				// Disable CSRF for API endpoints (common for token-based authentication)
				// If you are only using custom cookies, consider enabling CSRF for your custom session.
				.csrf(AbstractHttpConfigurer::disable)
				// Configure authorization for requests
				.authorizeHttpRequests(authorize -> authorize
						// All requests are permitted.
						// Spring Security's OAuth2 login filter will still intercept
						// requests to /oauth2/authorization/{registrationId} and /login/oauth2/code/{registrationId}
						// and handle the authentication flow.
						// For all other paths, your custom session logic (or lack thereof) will apply.
						.anyRequest().permitAll() // Changed to permitAll() to avoid Spring Security enforcing its own auth
				)
				// Configure OAuth2 Login
				.oauth2Login(oauth2Login -> oauth2Login
								// .defaultSuccessUrl(frontendBaseUrl + "?authStatus=success", true)
								.successHandler(oauth2AuthenticationSuccessHandler())
						// .failureUrl(frontendBaseUrl + "?authError=oauth_login_failed")
				)
		//
		// If you want to use Spring Security's session management alongside your custom one,
		// you might remove this. But if your custom session is the ONLY session, keep it stateless.
		// .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		;

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler()
	{
		return (request, response, authentication) -> {
			final AuthResponse authResponse;
			if (authentication instanceof OAuth2AuthenticationToken oauthToken)
			{
				authResponse = onAuthenticationSuccess(oauthToken, request);
			}
			else
			{
				logger.warn("Authentication object is not OAuth2AuthenticationToken.");
				authResponse = AuthResponse.builder().authError("unexpected_auth_type").build();
			}

			response.sendRedirect(authResponse.toRedirectUrl(webuiURLs));
		};
	}

	private AuthResponse onAuthenticationSuccess(final OAuth2AuthenticationToken oauthToken, final HttpServletRequest request)
	{
		try
		{
			final String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
			final OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
			if (authorizedClient == null)
			{
				logger.warn("OAuth2AuthorizedClient not found after successful authentication.");
				return AuthResponse.builder().authError("client_not_found").build();
			}

			// Access the ID Token (for OIDC flows)
			if (oauthToken.getPrincipal() instanceof OidcUser oidcUser)
			{
				final String idTokenValue = oidcUser.getIdToken().getTokenValue();
				logger.debug("ID Token received for {}: {}...", clientRegistrationId, idTokenValue);
			}

			// Access the Access Token
			String accessTokenValue = authorizedClient.getAccessToken().getTokenValue();
			logger.debug("Access Token received for {}: {}...", clientRegistrationId, accessTokenValue);

			// Access the Refresh Token (if granted and available)
			if (authorizedClient.getRefreshToken() != null)
			{
				final String refreshTokenValue = authorizedClient.getRefreshToken().getTokenValue();
				logger.debug("Refresh Token received for {}: {}...", clientRegistrationId, refreshTokenValue);
				// IMPORTANT: Store refresh token securely in your backend database, linked to the user.
				// NEVER send refresh tokens to the frontend.
			}

			// --- Extract User Info from ID Token (if available) ---
			final String userEmail;
			if (oauthToken.getPrincipal() instanceof OidcUser oidcUser)
			{
				// OidcUser provides direct access to claims from the ID Token
				final String userId = oidcUser.getSubject();
				userEmail = oidcUser.getEmail();
				logger.info("User ID from OidcUser: {}", userId);
				logger.info("User Email from OidcUser: {}", userEmail);
			}
			else
			{
				logger.warn("Principal object is not OidcUser: {}", oauthToken.getPrincipal());
				return AuthResponse.builder().authError("unexpected_auth_type").build();
			}

			RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
			try
			{
				request.getSession(true); // make sure the session is created
				authenticate(userEmail);
				return AuthResponse.builder().authStatus("success").provider(clientRegistrationId).build();
			}
			catch (Exception ex)
			{
				logger.warn("Failed authenticating user {}", userEmail, ex);
				return AuthResponse.builder().authError("login_failed").exception(ex).build();
			}
			finally
			{
				RequestContextHolder.resetRequestAttributes();
			}
		}
		catch (Exception ex)
		{
			logger.warn("Unexpected authentication error", ex);
			return AuthResponse.builder().authError("unexpected_error").exception(ex).build();
		}
	}

	private void authenticate(String userEmail)
	{
		logger.info("Custom session creation method called for: userEmail={}", userEmail);

		final I_AD_User user = userDAO.retrieveLoginUserByUserId(userEmail);
		final String login = userBL.extractUserLogin(user);
		final HashableString password = userBL.extractUserPassword(user);
		loginRestController.authenticate(login, password, null);
	}

	//
	//
	//
	//
	//

	@Value
	@Builder
	private static class AuthResponse
	{
		String authStatus;
		String authError;
		String authErrorMessage;
		String provider;

		public String toRedirectUrl(@NonNull final WebuiURLs webuiURLs)
		{
			final String frontendUrl = webuiURLs.getFrontendURL();
			if (frontendUrl == null)
			{
				return null;
			}

			final UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(frontendUrl).path("/login");

			if (authStatus != null)
			{
				urlBuilder.queryParam("authStatus", authStatus);
			}
			if (authError != null)
			{
				urlBuilder.queryParam("authError", authError);
			}
			if (authErrorMessage != null)
			{
				urlBuilder.queryParam("authErrorMessage", authErrorMessage);
			}
			if (provider != null)
			{
				urlBuilder.queryParam("token", provider);
			}

			return urlBuilder.build().toUriString();
		}

		//
		//
		//

		@SuppressWarnings("unused")
		public static class AuthResponseBuilder
		{
			public AuthResponseBuilder exception(@NonNull Exception exception)
			{
				final String message = AdempiereException.extractMessage(exception);
				return authErrorMessage(message);
			}
		}
	}
}
