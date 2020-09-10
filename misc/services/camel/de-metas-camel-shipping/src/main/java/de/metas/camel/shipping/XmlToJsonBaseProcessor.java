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

package de.metas.camel.shipping;

import de.metas.common.MeasurableRequest;
import de.metas.common.filemaker.FIELD;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.filemaker.RESULTSET;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class XmlToJsonBaseProcessor
{
	private final static Log log = LogFactory.getLog(XmlToJsonBaseProcessor.class);

	protected void processExchange(
			final Exchange exchange,
			final Function<ProcessXmlToJsonRequest, ? extends MeasurableRequest> buildRequestFunction)
	{
		final FMPXMLRESULT fmpxmlresult = exchange.getIn().getBody(FMPXMLRESULT.class);

		if (fmpxmlresult == null || fmpxmlresult.isEmpty() )
		{
			log.debug("exchange.body is empty! -> nothing to do!");

			exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, 0);

			return; //nothing to do
		}

		final Map<String, Integer> name2Index = new HashMap<>();

		final List<FIELD> fields = fmpxmlresult.getMetadata().getFields();

		for (int i = 0; i < fields.size(); i++)
		{
			name2Index.put(fields.get(i).getName(), i);
		}

		final RESULTSET resultset = fmpxmlresult.getResultset();

		final ProcessXmlToJsonRequest xmlToJsonRequest = ProcessXmlToJsonRequest.builder()
				.name2Index(name2Index)
				.resultset(resultset)
				.propertiesComponent(exchange.getContext().getPropertiesComponent())
				.build();

		final MeasurableRequest request = buildRequestFunction.apply(xmlToJsonRequest);

		exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, request.getSize());
		exchange.getIn().setBody(request);
	}

	protected Optional<LocalDate> asLocalDate(
			@Nullable final String value,
			@NonNull final Set<String> localDatePatterns,
			@NonNull final String fieldName)
	{
		if (value == null || value.trim().isEmpty())
		{
			return Optional.empty();
		}

		Optional<LocalDate> localDateValue = Optional.empty();

		for (final String datePattern: localDatePatterns)
		{
			try
			{
				final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);

				localDateValue = Optional.of(LocalDate.parse(value, dateTimeFormatter));

				return localDateValue;
			}
			catch (final Exception e)
			{
				log.debug("*** Error caught when trying to parse " + fieldName + " ! Value: " + value + " Pattern: " + datePattern, e);
				localDateValue = Optional.empty();
			}
		}

		return localDateValue;
	}

	protected Optional<LocalDateTime> asLocalDateTime(
			@Nullable final String value,
			@NonNull final Set<String> localDateTimePatterns,
			@NonNull final String fieldName)
	{
		if (value == null || value.trim().isEmpty())
		{
			return Optional.empty();
		}

		Optional<LocalDateTime> localDateTime = Optional.empty();

		for (final String datePattern: localDateTimePatterns)
		{
			try
			{
				final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);

				localDateTime = Optional.of(LocalDateTime.parse(value, dateTimeFormatter));

				return localDateTime;
			}
			catch (final Exception e)
			{
				log.debug("*** Error caught when trying to parse " + fieldName + " ! Value: " + value + " Pattern: " + datePattern, e);
				localDateTime = Optional.empty();
			}
		}

		return localDateTime;
	}
}
