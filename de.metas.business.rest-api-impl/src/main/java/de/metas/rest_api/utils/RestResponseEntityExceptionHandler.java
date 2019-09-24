package de.metas.rest_api.utils;

import static de.metas.util.lang.CoalesceUtil.coalesceSuppliers;

import org.adempiere.exceptions.DBUniqueConstraintException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import de.metas.bpartner.service.BPartnerIdNotFoundException;
import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ControllerAdvice
public class RestResponseEntityExceptionHandler
{

	private static final Logger logger = LogManager.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler(BPartnerIdNotFoundException.class)
	public ResponseEntity<String> handleBPartnerInfoNotFoundException(@NonNull final BPartnerIdNotFoundException e)
	{
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PermissionNotGrantedException.class)
	public ResponseEntity<String> handlePermissionNotGrantedException(@NonNull final PermissionNotGrantedException e)
	{
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(MissingPropertyException.class)
	public ResponseEntity<String> handleMissingPropertyException(@NonNull final MissingPropertyException e)
	{
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(MissingResourceException.class)
	public ResponseEntity<String> handleMissingPropertyException(@NonNull final MissingResourceException e)
	{
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(InvalidIdentifierException.class)
	public ResponseEntity<String> InvalidIdentifierException(@NonNull final InvalidIdentifierException e)
	{
		final String msg = "Invalid identifier: " + e.getMessage();
		logger.error(msg, e);
		if (e.getMessage().startsWith("tea"))
		{
			return new ResponseEntity<>(msg, HttpStatus.I_AM_A_TEAPOT); // whohoo, finally!
		}
		return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DBUniqueConstraintException.class)
	public ResponseEntity<String> handleDBUniqueConstraintException(@NonNull final DBUniqueConstraintException e)
	{
		final String msg = "At least one record already existed in the system:" + e.getMessage();
		logger.error(msg, e);
		return new ResponseEntity<>(msg, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(@NonNull final Exception e)
	{
		final String msg = coalesceSuppliers(() -> e.getMessage(), () -> e.getClass().getSimpleName());
		logger.error(msg, e);
		return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
