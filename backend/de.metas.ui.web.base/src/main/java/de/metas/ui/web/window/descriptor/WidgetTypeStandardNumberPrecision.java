package de.metas.ui.web.window.descriptor;

import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.OptionalInt;

@EqualsAndHashCode
@ToString
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class WidgetTypeStandardNumberPrecision
{
	public static final WidgetTypeStandardNumberPrecision DEFAULT = builder()
			.amountPrecision(OptionalInt.of(2))
			.costPricePrecision(OptionalInt.of(2))
			.build();
	@NonNull private final OptionalInt amountPrecision;
	@NonNull private final OptionalInt costPricePrecision;
	@NonNull private final OptionalInt quantityPrecision;

	private static final OptionalInt ZERO = OptionalInt.of(0);

	@Builder
	private WidgetTypeStandardNumberPrecision(
			final @Nullable OptionalInt amountPrecision,
			final @Nullable OptionalInt costPricePrecision,
			final @Nullable OptionalInt quantityPrecision)
	{
		this.amountPrecision = CoalesceUtil.coalesceNotNull(amountPrecision, OptionalInt::empty);
		this.costPricePrecision = CoalesceUtil.coalesceNotNull(costPricePrecision, OptionalInt::empty);
		this.quantityPrecision = CoalesceUtil.coalesceNotNull(quantityPrecision, OptionalInt::empty);
	}

	public OptionalInt getMinPrecision(@NonNull DocumentFieldWidgetType widgetType)
	{
		switch (widgetType)
		{
			case Integer:
				return ZERO;
			case CostPrice:
				return costPricePrecision;
			case Amount:
				return amountPrecision;
			case Quantity:
				return quantityPrecision;
			default:
				return OptionalInt.empty();
		}
	}

	public WidgetTypeStandardNumberPrecision fallbackTo(@NonNull WidgetTypeStandardNumberPrecision other)
	{
		return builder()
				.amountPrecision(firstPresent(this.amountPrecision, other.amountPrecision))
				.costPricePrecision(firstPresent(this.costPricePrecision, other.costPricePrecision))
				.quantityPrecision(firstPresent(this.quantityPrecision, other.quantityPrecision))
				.build();
	}

	private static OptionalInt firstPresent(final OptionalInt optionalInt1, final OptionalInt optionalInt2)
	{
		if (optionalInt1 != null && optionalInt1.isPresent())
		{
			return optionalInt1;
		}
		else if (optionalInt2 != null && optionalInt2.isPresent())
		{
			return optionalInt2;
		}
		else
		{
			return OptionalInt.empty();
		}
	}
}
