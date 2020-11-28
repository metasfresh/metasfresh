package de.metas.payment.paypal;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.payment.paypal
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public final class PayPalClientResponse<ResultType, ErrorType>
{
	public static <ResponseType, ErrorType> PayPalClientResponse<ResponseType, ErrorType> ofResult(@NonNull final ResponseType response)
	{
		final ErrorType error = null;
		Throwable errorCause = null;
		return new PayPalClientResponse<>(response, error, errorCause);
	}

	public static <ResultType, ErrorType> PayPalClientResponse<ResultType, ErrorType> ofError(
			@NonNull final ErrorType error,
			@Nullable Throwable errorCause)
	{
		final ResultType result = null;
		return new PayPalClientResponse<>(result, error, errorCause);
	}

	private final ResultType result;
	private final ErrorType error;
	private final Throwable errorCause;

	private PayPalClientResponse(
			final ResultType result,
			final ErrorType error,
			Throwable errorCause)
	{
		this.result = result;
		this.error = error;
		this.errorCause = errorCause;
	}

	public boolean isOK()
	{
		return error == null;
	}

	public boolean isError()
	{
		return error != null;
	}

	public ResultType getResult()
	{
		if (result == null)
		{
			throw toException();
		}
		return result;
	}

	public ErrorType getError()
	{
		if (error == null)
		{
			throw new AdempiereException("Not an error response: " + this);
		}
		return error;
	}

	public AdempiereException toException()
	{
		if (error == null)
		{
			throw new AdempiereException("Not an error response: " + this);
		}

		if (errorCause != null)
		{
			return AdempiereException.wrapIfNeeded(errorCause)
					.setParameter("error", error);
		}
		else
		{
			return new AdempiereException(error.toString());
		}
	}
}
