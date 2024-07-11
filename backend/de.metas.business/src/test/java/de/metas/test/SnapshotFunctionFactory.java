package de.metas.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.metas.JsonObjectMapperHolder;
import de.metas.i18n.ITranslatableString;
import de.metas.quantity.Quantity;
import org.adempiere.exceptions.AdempiereException;

import java.io.IOException;
import java.util.function.Function;

public class SnapshotFunctionFactory
{
	private boolean humanReadableQuantity;
	private boolean translatableStringsUseDefaultValueOnly;

	public static SnapshotFunctionFactory newInstance()
	{
		return new SnapshotFunctionFactory();
	}

	public static Function<Object, String> newFunction()
	{
		return newInstance()
				.humanReadableQuantity(true)
				.translatableStringsUseDefaultValueOnly(true)
				.createFunction();
	}

	private SnapshotFunctionFactory() {}

	public Function<Object, String> createFunction()
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		jsonObjectMapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
		jsonObjectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);

		jsonObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		jsonObjectMapper.setVisibility(
				jsonObjectMapper
						.getSerializationConfig()
						.getDefaultVisibilityChecker()
						.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
						.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
						.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
						.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

		if (humanReadableQuantity)
		{
			jsonObjectMapper.addMixIn(Quantity.class, HumanReadableStringQuantityMixIn.class);
		}
		if (translatableStringsUseDefaultValueOnly)
		{
			jsonObjectMapper.addMixIn(ITranslatableString.class, TranslatableStringsUseDefaultValueOnlyMixIn.class);
		}

		final ObjectWriter writerWithDefaultPrettyPrinter = jsonObjectMapper.writerWithDefaultPrettyPrinter();
		return new SnapshotFunction(writerWithDefaultPrettyPrinter);
	}

	public SnapshotFunctionFactory humanReadableQuantity(final boolean humanReadableQuantity)
	{
		this.humanReadableQuantity = humanReadableQuantity;
		return this;
	}

	public SnapshotFunctionFactory translatableStringsUseDefaultValueOnly(final boolean translatableStringsUseDefaultValueOnly)
	{
		this.translatableStringsUseDefaultValueOnly = translatableStringsUseDefaultValueOnly;
		return this;
	}

	//
	//
	//

	private static class SnapshotFunction implements Function<Object, String>
	{
		private final ObjectWriter objectWriter;

		private SnapshotFunction(final ObjectWriter objectWriter)
		{
			this.objectWriter = objectWriter;
		}

		@Override
		public String apply(final Object object)
		{
			try
			{
				return objectWriter.writeValueAsString(object);
			}
			catch (final JsonProcessingException e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}
	}

	private static class HumanReadableStringQuantitySerializer extends StdSerializer<Quantity>
	{
		HumanReadableStringQuantitySerializer()
		{
			super(Quantity.class);
		}

		@Override
		public void serialize(final Quantity qty, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException
		{
			final String qtyAsString = qty.toBigDecimal().toPlainString() + " " + qty.getUOMSymbol();
			jsonGenerator.writeString(qtyAsString);
		}
	}

	@JsonSerialize(using = HumanReadableStringQuantitySerializer.class)
	private static abstract class HumanReadableStringQuantityMixIn {}

	private static class TranslatableStringsUseDefaultValueOnlySerializer extends StdSerializer<ITranslatableString>
	{

		TranslatableStringsUseDefaultValueOnlySerializer()
		{
			super(ITranslatableString.class);
		}

		@Override
		public void serialize(final ITranslatableString translatableString, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException
		{
			jsonGenerator.writeString(translatableString.getDefaultValue());
		}
	}

	@JsonSerialize(using = TranslatableStringsUseDefaultValueOnlySerializer.class)
	private static class TranslatableStringsUseDefaultValueOnlyMixIn {}
}
