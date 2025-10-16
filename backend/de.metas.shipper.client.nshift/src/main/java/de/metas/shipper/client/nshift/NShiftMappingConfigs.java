package de.metas.shipper.client.nshift;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.common.delivery.v1.json.request.JsonMappingConfig;
import de.metas.common.delivery.v1.json.request.JsonMappingConfigList;
import de.metas.common.util.Check;
import de.metas.shipper.client.nshift.json.JsonShipmentReference;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;
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

	public ImmutableList<String> getValuesAsList(@NonNull final String attributeType, @NonNull final UnaryOperator<String> valueMapper)
	{
		return streamValuesAsList(attributeType)
				.map(valueMapper)
				.filter(Check::isNotBlank)
				.collect(ImmutableList.toImmutableList());
	}

	private Stream<String> streamValuesAsList(final @NotNull String attributeType)
	{
		return orderedConfigsByAttributeType.get(attributeType)
				.stream()
				.map(JsonMappingConfig::getMappingRuleValue)
				//.filter(Check::isNotBlank) // don't filter out blank values
				;
	}

	public List<JsonShipmentReference> getShipmentReferences(
			@NonNull final String attributeKey,
			@NonNull final Function<String, String> valueProvider)
	{
		getValuesAsList()
		
		
		return mappingConfigs.stream()
				.filter(config -> attributeType.equals(config.getAttributeType()))
				.sorted(Comparator.comparingInt(JsonMappingConfig::getSeqNo))
				.map(config -> {
					final String value = valueProvider.apply(config.getAttributeValue());
					return Check.isNotBlank(value)
							? new AbstractMap.SimpleImmutableEntry<>(config.getAttributeKey(), value)
							: null;
				})
				.filter(Objects::nonNull)
				.collect(Collectors.groupingBy(
						Map.Entry::getKey,
						LinkedHashMap::new,
						Collectors.mapping(Map.Entry::getValue, Collectors.joining())))
				.entrySet().stream()
				.map(entry -> JsonShipmentReference.builder()
						.kind(Integer.parseInt(entry.getKey()))
						.value(entry.getValue())
						.build())
				.collect(ImmutableList.toImmutableList());
	}
}



// @NonNull String attributeType;
// @Nullable String attributeGroupKey;
// @NonNull String attributeKey;


// 