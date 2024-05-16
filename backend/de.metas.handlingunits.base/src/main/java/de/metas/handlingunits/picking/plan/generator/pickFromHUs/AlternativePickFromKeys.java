package de.metas.handlingunits.picking.plan.generator.pickFromHUs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import de.metas.handlingunits.HuId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class AlternativePickFromKeys implements Iterable<AlternativePickFromKey>
{
	public static final AlternativePickFromKeys EMPTY = new AlternativePickFromKeys(ImmutableSet.of());

	private final ImmutableSet<AlternativePickFromKey> keys;

	private AlternativePickFromKeys(@NonNull final Set<AlternativePickFromKey> keys)
	{
		this.keys = ImmutableSet.copyOf(keys);
	}

	public static AlternativePickFromKeys ofSet(final Set<AlternativePickFromKey> keys)
	{
		return !keys.isEmpty() ? new AlternativePickFromKeys(keys) : EMPTY;
	}

	public static Collector<AlternativePickFromKey, ?, AlternativePickFromKeys> collect()
	{
		return GuavaCollectors.collectUsingHashSetAccumulator(AlternativePickFromKeys::ofSet);
	}

	public boolean isEmpty() {return keys.isEmpty();}

	public AlternativePickFromKeys filter(@NonNull final Predicate<AlternativePickFromKey> predicate)
	{
		final ImmutableSet<AlternativePickFromKey> keysNew = keys.stream().filter(predicate).collect(ImmutableSet.toImmutableSet());
		return keys.size() != keysNew.size() ? ofSet(keysNew) : this;
	}

	@Override
	public UnmodifiableIterator<AlternativePickFromKey> iterator() {return keys.iterator();}

	public Stream<AlternativePickFromKey> stream() {return keys.stream();}

	public ImmutableSet<HuId> getHuIds() {return keys.stream().map(AlternativePickFromKey::getHuId).collect(ImmutableSet.toImmutableSet());}
}
