package de.metas.ui.web;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import org.slf4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSFilter implements Filter
{
	private static final Logger logger = LogManager.getLogger(CORSFilter.class);

	private final WebuiURLs webuiURLs = WebuiURLs.newInstance();

	@Override
	public void init(final FilterConfig filterConfig)
	{
		logger.info("CORS filter initialized");
	}

	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException
	{
		if (response instanceof HttpServletResponse)
		{
			final HttpServletRequest httpRequest = (HttpServletRequest)request;
			final HttpServletResponse httpResponse = (HttpServletResponse)response;

			setCORSHeaders(httpRequest, httpResponse);
		}

		chain.doFilter(request, response);
	}

	private void setCORSHeaders(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse)
	{
		final String requestOrigin = httpRequest.getHeader("Origin");
		final String accessControlAllowOrigin = getAccessControlAllowOrigin(requestOrigin);
		httpResponse.setHeader("Access-Control-Allow-Origin", accessControlAllowOrigin);
		logger.trace("Set Access-Control-Allow-Origin={} (request's Origin={})", accessControlAllowOrigin, requestOrigin);

		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH, PUT");

		// NOTE: Access-Control-Max-Age is browser dependent and each browser as a different max value.
		// e.g. chrome allows max 600sec=10min and it might be that everything above that is ignored.
		// see http://stackoverflow.com/questions/23543719/cors-access-control-max-age-is-ignored
		httpResponse.setHeader("Access-Control-Max-Age", "600");

		httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, Origin, Accept-Language, Accept, Authorization");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true"); // allow cookies
	}

	private String getAccessControlAllowOrigin(final String requestOrigin)
	{
		final boolean corsEnabled = !webuiURLs.isCrossSiteUsageAllowed();
		if (corsEnabled)
		{
			final String frontendURL = webuiURLs.getFrontendURL();
			if (!Check.isBlank(frontendURL))
			{
				return frontendURL;
			}
			else
			{
				logger.warn("Accepting any CORS Origin because even though CORS are enabled, the FrontendURL is not set."
								+ "\n Please and set `{}` SysConfig or allow cross-site-usage (`{}` sysconfig).",
						WebuiURLs.SYSCONFIG_FRONTEND_URL, WebuiURLs.SYSCONFIG_IsCrossSiteUsageAllowed);
			}
		}

		// Fallback
		return Check.isBlank(requestOrigin) ? "*" : requestOrigin;
	}
}
