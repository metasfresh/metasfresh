package de.metas.product;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.function.UnaryOperator;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public final class IssuingToleranceSpec
{
	@NonNull private final IssuingToleranceValueType valueType;
	@Nullable private final Percent percent;
	@Nullable private final Quantity qty;

	@Builder(toBuilder = true)
	private IssuingToleranceSpec(
			@NonNull final IssuingToleranceValueType valueType,
			@Nullable final Percent percent,
			@Nullable final Quantity qty)
	{
		this.valueType = valueType;
		if (valueType == IssuingToleranceValueType.PERCENTAGE)
		{
			Check.assumeNotNull(percent, "percent is provided");
			this.percent = percent;
			this.qty = null;
		}
		else if (valueType == IssuingToleranceValueType.QUANTITY)
		{
			Check.assumeNotNull(qty, "qty is provided");
			this.percent = null;
			this.qty = qty;
		}
		else
		{
			throw new AdempiereException("Unknown valueType: " + valueType);
		}
	}

	public static IssuingToleranceSpec ofPercent(@NonNull final Percent percent)
	{
		return builder().valueType(IssuingToleranceValueType.PERCENTAGE).percent(percent).build();
	}

	public static IssuingToleranceSpec ofQuantity(@NonNull final Quantity qty)
	{
		return builder().valueType(IssuingToleranceValueType.QUANTITY).qty(qty).build();
	}

	public @NonNull IssuingToleranceValueType getValueType() {return valueType;}

	private void assertValueType(@NonNull IssuingToleranceValueType expectedValueType)
	{
		if (this.valueType != expectedValueType)
		{
			throw new AdempiereException("Expected " + this + " to be of type: " + expectedValueType);
		}
	}

	@NonNull
	public Percent getPercent()
	{
		assertValueType(IssuingToleranceValueType.PERCENTAGE);
		return Check.assumeNotNull(percent, "percent not null");
	}

	@NonNull
	public Quantity getQty()
	{
		assertValueType(IssuingToleranceValueType.QUANTITY);
		return Check.assumeNotNull(qty, "qty not null");
	}

	public ITranslatableString toTranslatableString()
	{
		if (valueType == IssuingToleranceValueType.PERCENTAGE)
		{
			final Percent percent = getPercent();
			return TranslatableStrings.builder().appendPercent(percent).build();
		}
		else if (valueType == IssuingToleranceValueType.QUANTITY)
		{
			final Quantity qty = getQty();
			return TranslatableStrings.builder().appendQty(qty.toBigDecimal(), qty.getUOMSymbol()).build();
		}
		else
		{
			// shall not happen
			return TranslatableStrings.empty();
		}
	}

	public Quantity addTo(@NonNull final Quantity qtyBase)
	{
		if (valueType == IssuingToleranceValueType.PERCENTAGE)
		{
			final Percent percent = getPercent();
			return qtyBase.add(percent);
		}
		else if (valueType == IssuingToleranceValueType.QUANTITY)
		{
			final Quantity qty = getQty();
			return qtyBase.add(qty);
		}
		else
		{
			// shall not happen
			throw new AdempiereException("Unknown valueType: " + valueType);
		}
	}

	public Quantity subtractFrom(@NonNull final Quantity qtyBase)
	{
		if (valueType == IssuingToleranceValueType.PERCENTAGE)
		{
			final Percent percent = getPercent();
			return qtyBase.subtract(percent);
		}
		else if (valueType == IssuingToleranceValueType.QUANTITY)
		{
			final Quantity qty = getQty();
			return qtyBase.subtract(qty);
		}
		else
		{
			// shall not happen
			throw new AdempiereException("Unknown valueType: " + valueType);
		}
	}

	public IssuingToleranceSpec convertQty(@NonNull final UnaryOperator<Quantity> qtyConverter)
	{
		if (valueType == IssuingToleranceValueType.QUANTITY)
		{
			final Quantity qtyConv = qtyConverter.apply(qty);
			if (qtyConv.equals(qty))
			{
				return this;
			}

			return toBuilder().qty(qtyConv).build();
		}
		else
		{
			return this;
		}
	}
}
