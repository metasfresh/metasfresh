/*
 * #%L
 * de-metas-camel-externalsystems-common
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

package de.metas.camel.externalsystems.common;


import de.metas.common.rest_api.v2.JsonErrorItem;
import lombok.NonNull;
import org.apache.camel.Exchange;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;

public class ErrorBuilderHelper
{
	public static JsonErrorItem buildJsonErrorItem(@NonNull final Exchange exchange)
	{
		final JsonErrorItem.JsonErrorItemBuilder errorBuilder = JsonErrorItem
				.builder()
				.orgCode(exchange.getIn().getHeader(HEADER_ORG_CODE, String.class));

		final Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		if (exception == null)
		{
			errorBuilder.message("No error message available!");
		}
		else
		{
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);

			errorBuilder.message(exception.getLocalizedMessage());
			errorBuilder.stackTrace(sw.toString());

			final Optional<StackTraceElement> sourceStackTraceElem = exception.getStackTrace() != null
					? Optional.ofNullable(exception.getStackTrace()[0])
					: Optional.empty();

			sourceStackTraceElem.ifPresent(stackTraceElement -> {
				errorBuilder.sourceClassName(sourceStackTraceElem.get().getClassName());
				errorBuilder.sourceMethodName(sourceStackTraceElem.get().getMethodName());
			});
		}

		return errorBuilder.build();
	}
}
