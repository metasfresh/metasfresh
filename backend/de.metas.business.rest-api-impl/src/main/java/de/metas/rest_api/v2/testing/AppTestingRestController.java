/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.rest_api.v2.testing;

import de.metas.cache.CacheMgt;
import de.metas.common.rest_api.common.JsonTestResponse;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryStatisticsLogger;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/test" })
public class AppTestingRestController
{
	private static final Logger logger = LogManager.getLogger(AppTestingRestController.class);

	private final AtomicLong nextNotificationId = new AtomicLong(1);

	private final IQueryStatisticsLogger statisticsLogger;

	public AppTestingRestController(@NonNull final IQueryStatisticsLogger statisticsLogger)
	{
		this.statisticsLogger = statisticsLogger;
	}

	/* when adding additional parameters, please also update https://github.com/metasfresh/metasfresh/issues/1577#issue-229774302 */
	@GetMapping("/ping/notifications")
	public String pingNotifications(@RequestParam(value = "noEmail", defaultValue = "false") final String noEmail)
	{
		final long id = nextNotificationId.getAndIncrement();

		final UserNotificationRequest request = UserNotificationRequest.builder()
				.recipient(Recipient.allUsers())
				.subjectPlain("Notifications system test")
				.contentPlain("Please ignore this message. It was issued by server to check the notifications system (#" + id + ").")
				.noEmail(Boolean.parseBoolean(noEmail))
				.build();
		Services.get(INotificationBL.class).send(request);

		final String message = "sent: " + request;
		logger.info("pingNotifications: {}", message);
		return message;
	}

	@PutMapping(produces = "application/json")
	public ResponseEntity<?> putMethod(
			@Parameter(description = "Response code the endpoint should return")
			@RequestParam(name = "responseCode") final int responseCode,
			@Parameter(description = "Response body the endpoint should return")
			@RequestParam(name = "responseBody") final String responseBody,
			@Parameter(description = "Milliseconds to delay the response")
			@RequestParam(name = "delaymillis", required = false) final Integer delaymillis) throws InterruptedException
	{
		return executeMethod(responseCode, responseBody, delaymillis);
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<?> getMethod(
			@Parameter(description = "Response code the endpoint should return")
			@RequestParam(name = "responseCode") final int responseCode,
			@Parameter(description = "Response body the endpoint should return")
			@RequestParam(name = "responseBody") final String responseBody,
			@Parameter(description = "Milliseconds to delay the response")
			@RequestParam(name = "delaymillis", required = false) final Integer delaymillis) throws InterruptedException
	{
		return executeMethod(responseCode, responseBody, delaymillis);
	}

	@PostMapping(produces = "application/json")
	public ResponseEntity<?> postMethod(
			@Parameter(description = "Response code the endpoint should return")
			@RequestParam(name = "responseCode") final int responseCode,
			@Parameter(description = "Response body the endpoint should return")
			@RequestParam(name = "responseBody", required = false) final String responseBody,
			@Parameter(description = "Milliseconds to delay the response")
			@RequestParam(name = "delaymillis", required = false) final Integer delaymillis,
			@Parameter(description = "Exception thrown in metas API")
			@RequestParam(name = "throwException", required = false) final boolean throwException,
			@Parameter(description = "Return non-json body")
			@RequestParam(name = "nonJsonBody", required = false) final boolean nonJsonBody) throws InterruptedException
	{
		if (throwException)
		{
			final String errorString = "Exception thrown";
			throw new AdempiereException(errorString);
		}
		else if (nonJsonBody)
		{
			final String nonJsonBodyString = Check.isNotBlank(responseBody) ? responseBody : "notDeserializable";

			return ResponseEntity.status(responseCode).body(nonJsonBodyString);
		}
		else
		{
			return executeMethod(responseCode, responseBody, delaymillis);
		}
	}

	@DeleteMapping(produces = "application/json")
	public ResponseEntity<?> deleteMethod(
			@Parameter(description = "Response code the endpoint should return")
			@RequestParam(name = "responseCode") final int responseCode,
			@Parameter(description = "Response body the endpoint should return")
			@RequestParam(name = "responseBody") final String responseBody,
			@Parameter(description = "Milliseconds to delay the response")
			@RequestParam(name = "delaymillis", required = false) final Integer delaymillis) throws InterruptedException
	{
		return executeMethod(responseCode, responseBody, delaymillis);
	}

	private ResponseEntity<?> executeMethod(
			final int responseCode,
			@Nullable final String responseBody,
			@Nullable final Integer delaymillis) throws InterruptedException

	{
		Loggables.get().addLog("Endpoint invoked; returning httpCode: " + responseCode);

		if (delaymillis != null && delaymillis > 0)
		{
			Thread.sleep(delaymillis);
		}

		if (responseCode == 404)
		{
			final String errorString = "Endpoint invoked; log ad_issue";
			Loggables.get().addLog(errorString, new AdempiereException(errorString));
		}

		final JsonTestResponse response = responseBody == null
				? null
				: JsonTestResponse.builder()
				.messageBody(responseBody)
				.build();

		return ResponseEntity.status(responseCode).body(response);
	}

	@GetMapping("/cacheReset")
	public void cacheReset()
	{
		CacheMgt.get().reset();
	}

	@GetMapping("/recordSqlQueriesWithMicrometer")
	public void setRecordSqlQueriesWithMicrometer(
			@Parameter(description = "If Enabled, all SQL queries execution times are recorded with micrometer")
			@RequestParam("enabled") final boolean enabled)
	{
		if (enabled)
		{
			statisticsLogger.enableRecordWithMicrometer();
		}
		else
		{
			statisticsLogger.disableRecordWithMicrometer();
		}
	}
}
