package de.metas.currency;

import lombok.NonNull;
import org.adempiere.util.lang.IMutable;

import javax.annotation.Nullable;

public class MutableAmount implements IMutable<Amount>
{
	private Amount value;

	public MutableAmount(@Nullable final Amount value)
	{
		this.value = value;
	}

	public static MutableAmount zero(@NonNull final CurrencyCode currencyCode) {return new MutableAmount(Amount.zero(currencyCode));}

	public static MutableAmount nullValue() {return new MutableAmount(null);}

	@Override
	public String toString() {return String.valueOf(value);}

	public Amount toAmount() {return value;}

	@Nullable
	@Override
	public Amount getValue() {return value;}

	@Override
	public void setValue(@Nullable final Amount value) {this.value = value;}

	public void add(@NonNull final Amount other)
	{
		this.value = value != null
				? value.add(other)
				: other;
	}
}
