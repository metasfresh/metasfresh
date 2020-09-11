package de.metas.camel.test.endpoints;

import java.util.function.Supplier;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFileOperationFailedException;

import de.metas.camel.shipping.CommonUtil;
import de.metas.common.rest_api.JsonError;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class FailOnCall implements Processor
{
	public static FailOnCall withGenericFileOperationFailedException(@NonNull final String exceptionMessage)
	{
		return withException(() -> new GenericFileOperationFailedException(exceptionMessage));
	}

	public static FailOnCall withException(@NonNull final Supplier<Exception> exceptionSupplier)
	{
		return new FailOnCall(exceptionSupplier);
	}

	private final Supplier<Exception> exceptionSupplier;

	@Getter
	int called = 0;

	private JsonError jsonError;

	private FailOnCall(@NonNull final Supplier<Exception> exceptionSupplier)
	{
		this.exceptionSupplier = exceptionSupplier;
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final Exception exception = exceptionSupplier.get();
		if (jsonError == null)
		{
			jsonError = CommonUtil.createJsonError(exception);
		}

		called++;
		throw exception;
	}

	public JsonError getJsonError()
	{
		if (jsonError == null)
		{
			throw new IllegalStateException("jsonError is not available because no exception was thrown yet");
		}
		return jsonError;
	}
}
