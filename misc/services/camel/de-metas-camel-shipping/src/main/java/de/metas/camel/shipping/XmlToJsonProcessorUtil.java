package de.metas.camel.shipping;

import de.metas.common.filemaker.FIELD;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.filemaker.RESULTSET;
import de.metas.common.filemaker.ROW;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

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

@UtilityClass
public class XmlToJsonProcessorUtil
{
	private final static Log log = LogFactory.getLog(XmlToJsonProcessorUtil.class);

	public static <T, R> void processExchange(
			final Exchange exchange,
			final BiFunction<ROW, Map<String, Integer>, T> buildRequestItemFunction,
			final Function<List<T>, R> buildRequestFunction)
	{
		final FMPXMLRESULT fmpxmlresult = exchange.getIn().getBody(FMPXMLRESULT.class);

		if (fmpxmlresult == null || fmpxmlresult.isEmpty())
		{
			log.debug("exchange.body is empty! -> nothing to do!");

			exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, 0);

			return; // nothing to do
		}

		final Map<String, Integer> name2Index = new HashMap<>();

		final List<FIELD> fields = fmpxmlresult.getMetadata().getFields();

		for (int i = 0; i < fields.size(); i++)
		{
			name2Index.put(fields.get(i).getName(), i);
		}

		final RESULTSET resultset = fmpxmlresult.getResultset();

		final List<T> requestItems = resultset.getRows()
				.stream()
				.map(row -> buildRequestItemFunction.apply(row, name2Index))
				.collect(Collectors.toList());

		final R request = buildRequestFunction.apply(requestItems);

		exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, requestItems.size());
		exchange.getIn().setBody(request);
	}

	public static Optional<LocalDate> asLocalDate(
			@Nullable final String value,
			@NonNull final Set<String> localDatePatterns,
			@NonNull final String fieldName)
	{
		if (value == null || value.trim().isEmpty())
		{
			return Optional.empty();
		}

		Optional<LocalDate> localDateValue = Optional.empty();

		for (final String datePattern : localDatePatterns)
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

	public static Optional<LocalDateTime> asLocalDateTime(
			@Nullable final String value,
			@NonNull final Set<String> localDateTimePatterns,
			@NonNull final String fieldName)
	{
		if (value == null || value.trim().isEmpty())
		{
			return Optional.empty();
		}

		Optional<LocalDateTime> localDateTime = Optional.empty();

		for (final String datePattern : localDateTimePatterns)
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
