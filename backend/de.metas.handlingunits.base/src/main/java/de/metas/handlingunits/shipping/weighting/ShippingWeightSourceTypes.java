package de.metas.handlingunits.shipping.weighting;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class ShippingWeightSourceTypes implements Iterable<ShippingWeightSourceType>
{
	public static final ShippingWeightSourceTypes DEFAULT = new ShippingWeightSourceTypes(ImmutableList.of(ShippingWeightSourceType.ProductWeight));
	
	private static final Splitter SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();

	private final ImmutableList<ShippingWeightSourceType> list;

	private ShippingWeightSourceTypes(@NonNull final ImmutableList<ShippingWeightSourceType> list)
	{
		Check.assumeNotEmpty(list, "list is not empty");
		this.list = list;
	}

	public static Optional<ShippingWeightSourceTypes> ofCommaSeparatedString(@Nullable final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		if (stringNorm == null)
		{
			return Optional.empty();
		}

		final ImmutableList<ShippingWeightSourceType> result = SPLITTER.splitToList(stringNorm)
				.stream()
				.map(ShippingWeightSourceType::valueOf)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		return !result.isEmpty()
				? Optional.of(new ShippingWeightSourceTypes(result))
				: Optional.empty();
	}

	@Override
	public @NotNull Iterator<ShippingWeightSourceType> iterator()
	{
		return list.iterator();
	}

	public Optional<Quantity> calculateWeight(final Function<ShippingWeightSourceType, Optional<Quantity>> calculateWeightFunc)
	{
		for (final ShippingWeightSourceType weightSourceType : list)
		{
			final Optional<Quantity> weight = calculateWeightFunc.apply(weightSourceType);
			if (weight.isPresent())
			{
				return weight;
			}
		}

		return Optional.empty();

	}
}
