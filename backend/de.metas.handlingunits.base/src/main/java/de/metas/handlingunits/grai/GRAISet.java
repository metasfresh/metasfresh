package de.metas.handlingunits.grai;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class GRAISet implements Iterable<GRAI>
{
	public static final GRAISet EMPTY = new GRAISet(ImmutableSet.of());

	private static final Splitter COMMA_SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();

	@NonNull private final ImmutableSet<GRAI> grais;

	private GRAISet(@NonNull final ImmutableSet<GRAI> grais)
	{
		this.grais = grais;
	}

	public static GRAISet ofCollection(@NonNull final Collection<GRAI> grais)
	{
		return grais.isEmpty() ? EMPTY : new GRAISet(ImmutableSet.copyOf(grais));
	}

	public static GRAISet of(@NonNull final GRAI grai)
	{
		return new GRAISet(ImmutableSet.of(grai));
	}

	public static GRAISet ofStrings(@NonNull final Collection<String> values)
	{
		if (values.isEmpty())
		{
			return EMPTY;
		}
		return values.stream()
				.map(GRAI::of)
				.collect(collect());
	}

	/**
	 * Parse a comma-separated attribute value (as stored in HU attributes) into a GRAISet.
	 */
	public static GRAISet ofNullableCommaSeparated(@Nullable final String csv)
	{
		final String csvNorm = StringUtils.trimBlankToNull(csv);
		if (csvNorm == null)
		{
			return EMPTY;
		}
		
		return COMMA_SPLITTER.splitToList(csvNorm)
				.stream()
				.map(GRAI::of)
				.collect(collect());
	}

	public static Collector<GRAI, ?, GRAISet> collect()
	{
		return GuavaCollectors.collectUsingHashSetAccumulator(GRAISet::ofCollection);
	}

	/**
	 * Serialize to a comma-separated string for HU attribute storage.
	 */
	public String toCommaSeparatedString()
	{
		return grais.stream()
				.map(GRAI::getValue)
				.collect(Collectors.joining(","));
	}

	/**
	 * Convert to a plain string list for use in JSON DTOs.
	 */
	public List<String> toStringList()
	{
		return grais.stream()
				.map(GRAI::getValue)
				.collect(ImmutableList.toImmutableList());
	}

	public boolean isEmpty() {return grais.isEmpty();}

	public int size() {return grais.size();}

	public boolean contains(@NonNull final GRAI grai) {return grais.contains(grai);}

	@Override
	@NonNull
	public Iterator<GRAI> iterator() {return grais.iterator();}

	public Stream<GRAI> stream() {return grais.stream();}

	public ImmutableSet<GRAI> toSet() {return grais;}
}
