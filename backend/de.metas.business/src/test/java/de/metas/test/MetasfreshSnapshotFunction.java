package de.metas.test;

import au.com.origin.snapshots.Snapshot;
import au.com.origin.snapshots.SnapshotSerializerContext;
import au.com.origin.snapshots.serializers.SerializerType;
import au.com.origin.snapshots.serializers.SnapshotSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Separators;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.metas.JsonObjectMapperHolder;
import de.metas.i18n.ITranslatableString;
import de.metas.quantity.Quantity;
import org.adempiere.exceptions.AdempiereException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * A deterministic JSON serializer, similar to {@link au.com.origin.snapshots.jackson.serializers.DeterministicJacksonSnapshotSerializer}
 * but which also handles types like {@link de.metas.quantity.Quantitys}, {@link ITranslatableString}.
 */
public class MetasfreshSnapshotFunction implements SnapshotSerializer
{
	private final PrettyPrinter prettyPrinter = new DefaultPrettyPrinter("")
	{
		{
			final Indenter lfOnlyIndenter = new DefaultIndenter("  ", "\n");
			this.indentArraysWith(lfOnlyIndenter);
			this.indentObjectsWith(lfOnlyIndenter);
		}

		// It's a requirement
		// @see https://github.com/FasterXML/jackson-databind/issues/2203
		public DefaultPrettyPrinter createInstance()
		{
			return new DefaultPrettyPrinter(this);
		}

		@Override
		public DefaultPrettyPrinter withSeparators(Separators separators)
		{
			this._separators = separators;
			this._objectFieldValueSeparatorWithSpaces = separators.getObjectFieldValueSeparator() + " ";
			return this;
		}
	};

	private final ObjectMapper objectMapper;

	public MetasfreshSnapshotFunction()
	{
		objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		objectMapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
		objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);

		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.setVisibility(
				objectMapper
						.getSerializationConfig()
						.getDefaultVisibilityChecker()
						.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
						.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
						.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
						.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

		objectMapper.addMixIn(Quantity.class, HumanReadableStringQuantityMixIn.class);
		objectMapper.addMixIn(ITranslatableString.class, TranslatableStringsUseDefaultValueOnlyMixIn.class);
	}

	@Override
	public String getOutputFormat()
	{
		return SerializerType.JSON.name();
	}

	public String toJson(final Object object)
	{
		try
		{
			return objectMapper.writer(prettyPrinter).writeValueAsString(object);
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@Override
	public Snapshot apply(Object object, SnapshotSerializerContext gen)
	{
		List<?> objects = Arrays.asList(object);
		String body = toJson(objects);
		return gen.toSnapshot(body);
	}

	//
	//
	//
	//
	//

	@JsonSerialize(using = HumanReadableStringQuantitySerializer.class)
	public static abstract class HumanReadableStringQuantityMixIn {}

	public static class HumanReadableStringQuantitySerializer extends StdSerializer<Quantity>
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

	//
	//
	//
	//
	//
	@JsonSerialize(using = TranslatableStringsUseDefaultValueOnlySerializer.class)
	public static class TranslatableStringsUseDefaultValueOnlyMixIn {}

	public static class TranslatableStringsUseDefaultValueOnlySerializer extends StdSerializer<ITranslatableString>
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
}
