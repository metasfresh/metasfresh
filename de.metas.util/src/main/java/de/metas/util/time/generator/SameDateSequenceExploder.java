package de.metas.util.time.generator;

import java.time.LocalDateTime;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.NonNull;
import lombok.Value;

@Value
public final class SameDateSequenceExploder implements IDateSequenceExploder
{
	public static final SameDateSequenceExploder instance = new SameDateSequenceExploder();

	private SameDateSequenceExploder()
	{
	}

	@Override
	public Set<LocalDateTime> explodeForward(@NonNull final LocalDateTime date)
	{
		return ImmutableSet.of(date);
	}

	@Override
	public Set<LocalDateTime> explodeBackward(@NonNull final LocalDateTime date)
	{
		return ImmutableSet.of(date);
	}
}
