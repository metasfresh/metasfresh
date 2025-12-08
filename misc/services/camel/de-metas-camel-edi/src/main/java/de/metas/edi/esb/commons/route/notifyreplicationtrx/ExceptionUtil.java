/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.edi.esb.commons.route.notifyreplicationtrx;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.Exchange;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

@UtilityClass
public class ExceptionUtil
{
	@NonNull
	public String extractErrorMessage(@NonNull final Exchange exchange)
	{
		final Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

		return Optional.ofNullable(exception)
				.map(ex -> {
					final StringWriter sw = new StringWriter();
					final PrintWriter pw = new PrintWriter(sw);
					ex.printStackTrace(pw);

					return ex.getMessage() + "\n" + " Error Stacktrace: " + sw;
				})
				.orElse("No error info available!");
	}
}
