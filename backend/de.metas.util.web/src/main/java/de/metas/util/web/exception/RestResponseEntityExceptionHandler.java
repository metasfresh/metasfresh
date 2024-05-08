/*
 * #%L
 * de.metas.util.web
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

package de.metas.util.web.exception;

import ch.qos.logback.classic.Level;
import de.metas.Profiles;
import de.metas.bpartner.service.BPartnerIdNotFoundException;
import de.metas.common.rest_api.v1.JsonError;
import de.metas.dao.selection.pagination.PageNotFoundException;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.security.permissions2.PermissionNotGrantedException;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Nullable;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;

@ControllerAdvice
@Profile(Profiles.PROFILE_App)
public class RestResponseEntityExceptionHandler
{
	private static final Logger logger = LogManager.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler(BPartnerIdNotFoundException.class)
	public ResponseEntity<JsonError> handleBPartnerIdNotFoundException(@NonNull final BPartnerIdNotFoundException e)
	{
		return logAndCreateError(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PageNotFoundException.class)
	public ResponseEntity<JsonError> handlePageNotFoundException(@NonNull final PageNotFoundException e)
	{
		return logAndCreateError(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PermissionNotGrantedException.class)
	public ResponseEntity<JsonError> handlePermissionNotGrantedException(@NonNull final PermissionNotGrantedException e)
	{
		return logAndCreateError(e, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(MissingPropertyException.class)
	public ResponseEntity<JsonError> handleMissingPropertyException(@NonNull final MissingPropertyException e)
	{
		return logAndCreateError(e, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(MissingResourceException.class)
	public ResponseEntity<JsonError> handleMissingResourceException(@NonNull final MissingResourceException e)
	{
		return logAndCreateError(e, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(InvalidIdentifierException.class)
	public ResponseEntity<JsonError> InvalidIdentifierException(@NonNull final InvalidIdentifierException e)
	{
		final String msg = "Invalid identifier: " + e.getMessage();

		final HttpStatus status = e.getMessage().startsWith("tea")
				? HttpStatus.I_AM_A_TEAPOT // whohoo, finally found a reason!
				: HttpStatus.NOT_FOUND;

		return logAndCreateError(
				e,
				msg,
				status);
	}

	@ExceptionHandler(DBUniqueConstraintException.class)
	public ResponseEntity<JsonError> handleDBUniqueConstraintException(@NonNull final DBUniqueConstraintException e)
	{
		return logAndCreateError(
				e,
				"At least one record already existed in the system:" + e.getMessage(),
				HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<JsonError> handleException(@NonNull final Exception e)
	{
		final ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);
		if (responseStatus != null)
		{
			return logAndCreateError(e, responseStatus.reason(), responseStatus.code());
		}
		return logAndCreateError(e, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	private ResponseEntity<JsonError> logAndCreateError(
			@NonNull final Exception e,
			@NonNull final HttpStatus status)
	{
		return logAndCreateError(e, null, status);
	}

	private ResponseEntity<JsonError> logAndCreateError(
			@NonNull final Exception e,
			@Nullable final String detail,
			@NonNull final HttpStatus status)
	{
		final String logMessage = coalesceSuppliers(
				() -> detail,
				e::getMessage,
				() -> e.getClass().getSimpleName());
		Loggables.withLogger(logger, Level.ERROR).addLog(logMessage, e);

		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		final JsonError error = JsonError.builder()
				.error(JsonErrors.ofThrowable(e, adLanguage, TranslatableStrings.constant(detail)))
				.build();
		return new ResponseEntity<>(error, status);
	}
}
