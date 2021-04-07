package de.metas.report.jasper.client;

import java.time.Duration;
import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.logging.LogManager;
import de.metas.report.server.IReportServer;
import de.metas.report.server.JsonReportError;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportConstants;
import de.metas.report.server.ReportResult;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.exceptions.ServiceConnectionException;
import groovy.transform.ToString;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import static de.metas.util.Check.assumeGreaterThanZero;
import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.assumeNotNull;

@ToString(includes = "reportsRootUrl")
public class RemoteServletInvoker implements IReportServer
{
	private static final Logger logger = LogManager.getLogger(RemoteServletInvoker.class);

	private static final String SYSCONFIG_ConnectTimeout = "reports.remoteServletInvoker.connectTimeout";
	private static final int SYSCONFIG_ConnectTimeout_DEFAULT = 15000; // 15 seconds

	private static final String SYSCONFIG_ReadTimeout = "reports.remoteServletInvoker.readTimeout";
	private static final int SYSCONFIG_ReadTimeout_DEFAULT = 20000; // 20 seconds

	private static final String SYSCONFIG_JRServerRetryMS = "de.metas.report.jasper.client.ServiceConnectionExceptionRetryAdvisedInMillis";

	private static final String PARAM_AD_Process_ID = "AD_Process_ID";
	private static final String PARAM_AD_PInstance_ID = "AD_PInstance_ID";
	private static final String PARAM_AD_Language = "AD_Language";
	private static final String PARAM_Output = "output";
	private final String reportsRootUrl;

	private final String mgtRootUrl;

	private final RestTemplate restTemplate;

	public RemoteServletInvoker()
	{
		final ISysConfigBL sysConfigs = Services.get(ISysConfigBL.class);

		reportsRootUrl = sysConfigs.getValue(
				ReportConstants.SYSCONFIG_ReportsServerServlet,
				ReportConstants.SYSCONFIG_ReportsServerServlet_DEFAULT);

		// Set MgtServlet
		{
			final int idx = reportsRootUrl.lastIndexOf('/');
			mgtRootUrl = reportsRootUrl.substring(0, idx) + ReportConstants.JRSERVERSERVLET_MGTSERVLET_SUFFIX;
		}

		final Duration connectTimeout = Duration.ofMillis(sysConfigs.getIntValue(SYSCONFIG_ConnectTimeout, SYSCONFIG_ConnectTimeout_DEFAULT));
		final Duration readTimeout = Duration.ofMillis(sysConfigs.getIntValue(SYSCONFIG_ReadTimeout, SYSCONFIG_ReadTimeout_DEFAULT));
		restTemplate = new RestTemplateBuilder()
				.setConnectTimeout(connectTimeout)
				.setReadTimeout(readTimeout)
				.build();
	}

	@Override
	public ReportResult report(
			final int AD_Process_ID,
			final int AD_PInstance_ID,
			@NonNull final String adLanguage,
			@NonNull final OutputType outputType)
	{
		assumeGreaterThanZero(AD_Process_ID, "AD_Process_ID");
		assumeGreaterThanZero(AD_PInstance_ID, "AD_PInstance_ID");
		assumeNotEmpty(adLanguage, "adLanguage");
		assumeNotNull(outputType, "outputType");

		final String reportsUrl = reportsRootUrl
				+ "?" + PARAM_AD_Process_ID + "=" + AD_Process_ID
				+ "&" + PARAM_AD_PInstance_ID + "=" + AD_PInstance_ID
				+ "&" + PARAM_AD_Language + "=" + adLanguage
				+ "&" + PARAM_Output + "=" + outputType;

		logger.debug("Calling URL {}", reportsUrl);

		try
		{
			final HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			// httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);

			final HttpEntity<?> request = new HttpEntity<>(httpHeaders);

			final ResponseEntity<ReportResult> response = restTemplate.exchange(
					reportsUrl,
					HttpMethod.GET,
					request,
					ReportResult.class);

			return response.getBody();
		}
		catch (final RestClientResponseException ex)
		{
			final JsonReportError error = extractJsonReportError(ex);
			throw new AdempiereException(error.getMessage());
		}
		catch (final Exception ex)
		{
			writeLog(reportsUrl, ex);

			if (ex instanceof ResourceAccessException)
			{
				final int retryInMillis = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_JRServerRetryMS, -1);
				throw new ServiceConnectionException(reportsUrl, retryInMillis, ex);
			}
			else
			{
				throw AdempiereException.wrapIfNeeded(ex)
						.setParameter("URL", reportsUrl);
			}
		}
	}

	private JsonReportError extractJsonReportError(final RestClientResponseException ex)
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		try
		{
			return jsonObjectMapper.readValue(ex.getResponseBodyAsString(), JsonReportError.class);
		}
		catch (final Exception ex2)
		{
			logger.warn("Error while decoding response exception. Will return a generic error", ex2);
			logger.warn("Original exception was... ", ex);

			return JsonReportError.builder()
					.message("Internal error")
					.build();
		}
	}

	private void writeLog(final String url, final Exception ex)
	{
		Loggables.withLogger(logger, Level.ERROR)
				.addLog("Caught {} trying to invoke URL {}; message: {}",
						ex.getClass(), url, ex.getMessage(), ex);
	}

	@Override
	public void cacheReset()
	{
		final String mgtUrl = mgtRootUrl + "?" + ReportConstants.MGTSERVLET_PARAM_Action + "=" + ReportConstants.MGTSERVLET_ACTION_CacheReset;
		logger.debug("Calling URL {}", mgtUrl);

		final String result = restTemplate.getForObject(mgtUrl, String.class);
		logger.debug("result: {}", result);
	}
}
