package de.metas.util.lang;

import lombok.NonNull;

public final class SeqNoProvider
{
	private SeqNo value;

	private SeqNoProvider(@NonNull final SeqNo value) {this.value = value;}

	public static SeqNoProvider ofInt(final int value)
	{
		return new SeqNoProvider(SeqNo.ofInt(value));
	}

	public SeqNo getAndIncrement()
	{
		final SeqNo valueToReturn = value;
		value = value.next();
		return valueToReturn;
	}
}
