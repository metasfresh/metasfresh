package de.metas.util.web.security;

import de.metas.security.UserNotAuthorizedException;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

public class UserAuthTokenFilter implements Filter
{
	public static final String HEADER_Authorization = "Authorization";
	public static final String QUERY_PARAM_API_KEY = "apiKey";

	private final UserAuthTokenService userAuthTokenService;
	private final UserAuthTokenFilterConfiguration configuration;

	public UserAuthTokenFilter(
			@NonNull final UserAuthTokenService userAuthTokenService,
			@NonNull final UserAuthTokenFilterConfiguration configuration)
	{
		this.userAuthTokenService = userAuthTokenService;
		this.configuration = configuration;
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException
	{
	}

	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException
	{
		final HttpServletRequest httpRequest = (HttpServletRequest)request;
		if (isExcludedFromSecurityChecking(httpRequest))
		{
			chain.doFilter(request, response);
		}
		else
		{
			final HttpServletResponse httpResponse = (HttpServletResponse)response;

			try
			{
				userAuthTokenService.run(
						() -> extractTokenString(httpRequest),
						() -> chain.doFilter(httpRequest, httpResponse));
			}
			catch (final UserNotAuthorizedException ex)
			{
				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getLocalizedMessage());
			}
		}
	}

	private boolean isExcludedFromSecurityChecking(@NonNull final HttpServletRequest httpRequest)
	{
		return "OPTIONS".equals(httpRequest.getMethod()) // don't check auth for OPTIONS method calls because this causes troubles on chrome preflight checks
				|| configuration.isExcludedFromSecurityChecking(httpRequest);
	}

	private static String extractTokenString(final HttpServletRequest httpRequest)
	{
		//
		// Check Authorization header first
		{
			final String authorizationString = StringUtils.trimBlankToNull(httpRequest.getHeader(HEADER_Authorization));
			if (authorizationString != null)
			{
				if (authorizationString.startsWith("Token "))
				{
					return authorizationString.substring(5).trim();
				}
				else if (authorizationString.startsWith("Basic "))
				{
					final String userAndTokenString = new String(DatatypeConverter.parseBase64Binary(authorizationString.substring(5).trim()));
					final int index = userAndTokenString.indexOf(':');

					return userAndTokenString.substring(index + 1);
				}
				else
				{
					return authorizationString;
				}
			}
		}

		//
		// Check apiKey query parameter
		{
			final String apiKey = StringUtils.trimBlankToNull(httpRequest.getParameter(QUERY_PARAM_API_KEY));
			if (apiKey != null)
			{
				return apiKey;
			}
		}

		throw new AdempiereException("Provide token in `" + HEADER_Authorization + "` HTTP header"
				+ " or `" + QUERY_PARAM_API_KEY + "` query parameter");
	}

}
