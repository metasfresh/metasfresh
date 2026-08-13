/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.util.web.exception;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class RestResponseEntityExceptionHandlerTest
{
	/**
	 * A rejected request is the caller's problem, not a server fault. Logging it at {@code ERROR} makes
	 * {@code MetasfreshIssueAppender} persist an {@code AD_Issue} — complete with the rejected payload and a Spring
	 * stacktrace — for every malformed call, which is how a client retry loop fills the table.
	 */
	@ParameterizedTest
	@EnumSource(value = HttpStatus.class, names = {
			"BAD_REQUEST",
			"UNAUTHORIZED",
			"FORBIDDEN",
			"NOT_FOUND",
			"CONFLICT",
			"UNPROCESSABLE_ENTITY",
			"I_AM_A_TEAPOT" })
	void logLevelForStatus_isWarnForClientErrors(final HttpStatus status)
	{
		assertThat(RestResponseEntityExceptionHandler.logLevelForStatus(status)).isEqualTo(Level.WARN);
	}

	/** A server fault stays at {@code ERROR}, so it still produces an {@code AD_Issue} record. */
	@ParameterizedTest
	@EnumSource(value = HttpStatus.class, names = {
			"INTERNAL_SERVER_ERROR",
			"NOT_IMPLEMENTED",
			"BAD_GATEWAY",
			"SERVICE_UNAVAILABLE",
			"GATEWAY_TIMEOUT" })
	void logLevelForStatus_isErrorForServerErrors(final HttpStatus status)
	{
		assertThat(RestResponseEntityExceptionHandler.logLevelForStatus(status)).isEqualTo(Level.ERROR);
	}

	/**
	 * Anything that is not an error at all must not be louder than a client error. The handler is only reached with
	 * error statuses today, but the mapping must not degrade into {@code ERROR} for an unexpected input.
	 */
	@Test
	void logLevelForStatus_isWarnForANonErrorStatus()
	{
		assertThat(RestResponseEntityExceptionHandler.logLevelForStatus(HttpStatus.OK)).isEqualTo(Level.WARN);
	}
}
