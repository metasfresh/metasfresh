package de.metas.gs1;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
@ToString(of = "elements")
public class GS1Elements
{
	private final ImmutableList<GS1Element> elements;
	private final ImmutableMap<String, GS1Element> byKey;
	private final ImmutableMap<GS1ApplicationIdentifier, GS1Element> byApplicationIdentifier;

	private GS1Elements(@NonNull final List<GS1Element> list)
	{
		this.elements = ImmutableList.copyOf(list);
		this.byKey = list.stream().collect(ImmutableMap.toImmutableMap(GS1Element::getKey, element -> element));
		this.byApplicationIdentifier = list.stream()
				.filter(element -> element.getIdentifier() != null)
				.collect(ImmutableMap.toImmutableMap(GS1Element::getIdentifier, element -> element));
	}

	public static GS1Elements ofList(final List<GS1Element> list)
	{
		return new GS1Elements(list);
	}

	public List<GS1Element> toList() {return elements;}

	public Optional<GTIN> getGTIN()
	{
		final GS1Element element = byApplicationIdentifier.get(GS1ApplicationIdentifier.GTIN);
		if (element == null)
		{
			return Optional.empty();
		}

		return GTIN.optionalOfNullableString(element.getValueAsString());
	}

	public Optional<BigDecimal> getWeightInKg()
	{
		final GS1Element element = byApplicationIdentifier.get(GS1ApplicationIdentifier.ITEM_NET_WEIGHT_KG);
		if (element == null)
		{
			return Optional.empty();
		}

		return Optional.of(element.getValueAsBigDecimal());
	}
}
