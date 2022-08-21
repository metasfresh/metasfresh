package de.metas.util.lang;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class SeqNo
{
	private static final ImmutableMap<Integer, SeqNo> cache = createCache(300);
	private static final int STEP = 10;
	private final int value;

	private SeqNo(final int value)
	{
		this.value = value;
	}

	@SuppressWarnings("SameParameterValue")
	private static ImmutableMap<Integer, SeqNo> createCache(final int lastSeqNoValue)
	{
		final ImmutableMap.Builder<Integer, SeqNo> builder = ImmutableMap.builder();
		for (int i = 0; i <= lastSeqNoValue; i += STEP)
		{
			builder.put(i, new SeqNo(i));
		}
		return builder.build();
	}

	@JsonCreator
	public static SeqNo ofInt(final int value)
	{
		final SeqNo seqNo = cache.get(value);
		return seqNo != null ? seqNo : new SeqNo(value);
	}

	@Deprecated
	@Override
	public String toString()
	{
		return String.valueOf(value);
	}

	@JsonValue
	public int toInt()
	{
		return value;
	}

	public SeqNo next()
	{
		return ofInt(value / STEP * STEP + STEP);
	}
}
