package de.metas.camel.shipping;

import de.metas.common.MeasurableRequest;
import de.metas.common.filemaker.FIELD;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.filemaker.RESULTSET;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.Exchange;
import org.apache.camel.spi.PropertiesComponent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static de.metas.camel.shipping.shipment.SiroShipmentConstants.LOCALE_COUNTRY_LANGUAGE_SEPARATOR;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.SIRO_LOCALE_PROPERTY;

@UtilityClass
public class XmlToJsonProcessorUtil
{
	private final static Log log = LogFactory.getLog(XmlToJsonProcessorUtil.class);

	public void processExchange(
			final Exchange exchange,
			final Function<ProcessXmlToJsonRequest, ? extends MeasurableRequest> buildRequestFunction)
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

		final ProcessXmlToJsonRequest xmlToJsonRequest = ProcessXmlToJsonRequest.builder()
				.name2Index(name2Index)
				.resultset(resultset)
				.propertiesComponent(exchange.getContext().getPropertiesComponent())
				.build();

		final MeasurableRequest request = buildRequestFunction.apply(xmlToJsonRequest);

		exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, request.getSize());
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

	public Locale getLocale(@NonNull final PropertiesComponent propertiesComponent)
	{
		final String propertyValue = propertiesComponent.resolveProperty(SIRO_LOCALE_PROPERTY)
				.orElseThrow(() -> new RuntimeException("Missing property:" + SIRO_LOCALE_PROPERTY + "!"));

		final String[] localeParts = propertyValue.split(LOCALE_COUNTRY_LANGUAGE_SEPARATOR);

		if (localeParts.length != 2)
		{
			throw new RuntimeException("Unexpected value for property: [" + SIRO_LOCALE_PROPERTY + "]! value: [" + propertyValue + "] !");
		}

		return new Locale(localeParts[0], localeParts[1]);
	}

	public static BigDecimal toBigDecimalOrNull(final String valueStr, final Locale locale)
	{
		if (valueStr == null || valueStr.trim().isEmpty())
		{
			return null;
		}

		BigDecimal valueBigDecimal;
		final String valueToConvert = valueStr.trim();

		try
		{
			valueBigDecimal = BigDecimal.valueOf(NumberFormat.getNumberInstance(locale).parse(valueToConvert).doubleValue());
		}
		catch (final Exception e)
		{
			valueBigDecimal = null;
		}

		return valueBigDecimal;
	}
}
