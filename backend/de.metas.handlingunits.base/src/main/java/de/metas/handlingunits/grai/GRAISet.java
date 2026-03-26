package de.metas.handlingunits.grai;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
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

	@NonNull
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
				.map(GRAI::ofNullableCanonicalString)
				.filter(Objects::nonNull)
				.collect(collect());
	}

	/**
	 * Parse strings in any supported format (canonical, GS1 AI 8003).
	 * Use this at system boundaries (REST API) where input format is not guaranteed.
	 */
	public static GRAISet parseStrings(@NonNull final Collection<String> values)
	{
		if (values.isEmpty())
		{
			return EMPTY;
		}

		return values.stream()
				.map(GRAI::parse)
				.filter(Objects::nonNull)
				.collect(collect());
	}

	@NonNull
	public static GRAISet ofNullableCommaSeparated(@Nullable final String csv)
	{
		final String csvNorm = StringUtils.trimBlankToNull(csv);
		if (csvNorm == null)
		{
			return EMPTY;
		}

		return ofStrings(COMMA_SPLITTER.splitToList(csvNorm));
	}

	public static Collector<GRAI, ?, GRAISet> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(GRAISet::ofCollection);
	}

	@Override
	@Deprecated
	public String toString() {return toCommaSeparatedString();}

	public String toCommaSeparatedString()
	{
		return grais.stream()
				.map(GRAI::toCanonicalString)
				.collect(Collectors.joining(","));
	}

	@Nullable
	public static String toCommaSeparatedStringOrNull(@Nullable final GRAISet graiSet)
	{
		return graiSet != null && !graiSet.isEmpty()
				? StringUtils.trimBlankToNull(graiSet.toCommaSeparatedString())
				: null;
	}

	/**
	 * Convert to a plain string list for use in JSON DTOs.
	 */
	public List<String> toStringList()
	{
		return grais.stream()
				.map(GRAI::toCanonicalString)
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

	@NonNull
	public GRAI singleElement()
	{
		return CollectionUtils.singleElement(grais);
	}

	@Nullable
	public GRAI noneOrSingleElement()
	{
		return grais.isEmpty()
				? null
				: CollectionUtils.singleElement(grais);
	}

}
