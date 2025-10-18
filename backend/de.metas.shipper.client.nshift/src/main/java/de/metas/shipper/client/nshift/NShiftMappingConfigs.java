package de.metas.shipper.client.nshift;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.common.delivery.v1.json.request.JsonMappingConfig;
import de.metas.common.delivery.v1.json.request.JsonMappingConfigList;
import de.metas.common.util.Check;
import de.metas.shipper.client.nshift.json.JsonDetail;
import de.metas.shipper.client.nshift.json.JsonReference;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class NShiftMappingConfigs
{
	private final ImmutableListMultimap<String, JsonMappingConfig> orderedConfigsByAttributeType;

	private NShiftMappingConfigs(@NonNull final JsonMappingConfigList json)
	{
		this.orderedConfigsByAttributeType = json.getConfigs()
				.stream()
				.sorted(Comparator.comparingInt(JsonMappingConfig::getSeqNo))
				.collect(ImmutableListMultimap.toImmutableListMultimap(JsonMappingConfig::getAttributeType, Function.identity()));
	}

	public static NShiftMappingConfigs ofJson(@NonNull final JsonMappingConfigList json)
	{
		return new NShiftMappingConfigs(json);
	}

	public ImmutableListMultimap<String, String> getValuesByKeyAttributeKey(@NonNull final String attributeType,  @NonNull final Function<String, String> valueProvider)
	{
		return streamEligibleConfigs(attributeType, valueProvider)
				.filter(config -> Check.isNotBlank(config.getAttributeKey()))
				.map(config -> new AbstractMap.SimpleImmutableEntry<>(
						config.getAttributeKey(),
						valueProvider.apply(config.getAttributeValue())))
				.filter(entry -> Check.isNotBlank(entry.getValue()))
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						Map.Entry::getKey,
						Map.Entry::getValue));
	}

	private Stream<JsonMappingConfig> streamEligibleConfigs(final @NotNull String attributeType, @NonNull final Function<String, String> valueProvider )
	{
		return orderedConfigsByAttributeType.get(attributeType)
				.stream()
				.filter(config -> config.isConfigFor(valueProvider));
	}

	public List<JsonReference> getReferences(
			@NonNull final String attributeType,
			@NonNull final Function<String, String> valueProvider)
	{
		return getValuesByKeyAttributeKey(attributeType, valueProvider)
				.asMap().entrySet().stream()
				.map(entry -> JsonReference.builder()
						.kind(Integer.parseInt(entry.getKey()))
						.value(concatValues(entry.getValue()))
						.build())
				.collect(Collectors.toList());
	}

	private static String concatValues(@NonNull final Collection<String> values)
	{
		return String.join(" ", values);
	}

	@NonNull
	public String getSingleValue(@NonNull final String attributeType, @NonNull final Function<String, String> valueProvider)
	{
		return concatValues(getValueList(attributeType, valueProvider));
	}

	@NonNull
	private ImmutableList<String> getValueList(@NonNull final String attributeType, @NonNull final Function<String, String> valueProvider)
	{
		return streamEligibleConfigs(attributeType, valueProvider)
				.map(config -> valueProvider.apply(config.getAttributeValue()))
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableList<String> getDetailGroupKeysForType(@NonNull final String attributeType, @NonNull final Function<String, String> valueProvider)
	{
		return streamEligibleConfigs(attributeType, valueProvider)
				.map(JsonMappingConfig::getAttributeGroupKey)
				.filter(Check::isNotBlank)
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	public List<JsonDetail> getDetailsForGroupAndType(
			@NonNull final String attributeGroupKey,
			@NonNull final String attributeType,
			@NonNull final Function<String, String> valueProvider)
	{
		final Map<Integer, String> detailsByKindId = streamEligibleConfigs(attributeType, valueProvider)
				.filter(config -> attributeGroupKey.equals(config.getAttributeGroupKey()))
				.filter(config -> Check.isNotBlank(config.getAttributeKey()))
				.map(config -> new AbstractMap.SimpleImmutableEntry<>(
						Integer.parseInt(config.getAttributeKey()),
						valueProvider.apply(config.getAttributeValue())))
				.filter(entry -> Check.isNotBlank(entry.getValue()))
				.collect(Collectors.groupingBy(Map.Entry::getKey,
						Collectors.mapping(Map.Entry::getValue, Collectors.joining(" "))));

		return detailsByKindId.entrySet().stream()
				.map(entry -> JsonDetail.builder()
						.kindId(entry.getKey())
						.value(entry.getValue())
						.build())
				.collect(Collectors.toList());
	}
}