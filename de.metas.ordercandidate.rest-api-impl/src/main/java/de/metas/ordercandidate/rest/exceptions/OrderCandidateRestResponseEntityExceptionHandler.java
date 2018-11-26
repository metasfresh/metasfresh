package de.metas.ordercandidate.rest.exceptions;

import lombok.NonNull;

import org.adempiere.exceptions.DBUniqueConstraintException;
import org.compiere.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
public class OrderCandidateRestResponseEntityExceptionHandler
{
	@ExceptionHandler(BPartnerInfoNotFoundException.class)
	public ResponseEntity<String> handleBPartnerInfoNotFoundException(@NonNull final BPartnerInfoNotFoundException e)
	{
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PermissionNotGrantedException.class)
	public ResponseEntity<String> handlePermissionNotGrantedException(@NonNull final PermissionNotGrantedException e)
	{
		return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(MissingPropertyException.class)
	public ResponseEntity<String> handleMissingPropertyException(@NonNull final MissingPropertyException e)
	{
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(DBUniqueConstraintException.class)
	public ResponseEntity<String> handleDBUniqueConstraintException(@NonNull final DBUniqueConstraintException e)
	{
		final String msg = "At least one record already existed in the system:" + e.getMessage();
		return new ResponseEntity<>(msg, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(@NonNull final Exception e)
	{
		final String msg = Util.coalesceSuppliers(() -> e.getMessage(), () -> e.getClass().getSimpleName());
		return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
