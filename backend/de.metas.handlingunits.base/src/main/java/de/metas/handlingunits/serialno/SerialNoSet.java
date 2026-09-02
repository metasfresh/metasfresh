package de.metas.handlingunits.serialno;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Immutable, insertion-ordered, deduplicated set of {@link SerialNo}s captured for one picked HU.
 * <p>
 * Persisted comma-separated in {@code M_HU_Attribute.Value} for the {@code SerialNo} attribute.
 * Mirrors {@link de.metas.handlingunits.grai.GRAISet} for the multi-value case, but without GS1 parsing.
 */
@EqualsAndHashCode
public class SerialNoSet implements Iterable<SerialNo>
{
	public static final SerialNoSet EMPTY = new SerialNoSet(ImmutableSet.of());

	private static final Splitter COMMA_SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();

	@NonNull private final ImmutableSet<SerialNo> serialNos;

	private SerialNoSet(@NonNull final ImmutableSet<SerialNo> serialNos)
	{
		this.serialNos = serialNos;
	}

	@NonNull
	public static SerialNoSet ofCollection(@NonNull final Collection<SerialNo> serialNos)
	{
		return serialNos.isEmpty() ? EMPTY : new SerialNoSet(ImmutableSet.copyOf(serialNos));
	}

	public static SerialNoSet of(@NonNull final SerialNo serialNo)
	{
		return new SerialNoSet(ImmutableSet.of(serialNo));
	}

	public static SerialNoSet parseStrings(@NonNull final Collection<String> values)
	{
		if (values.isEmpty())
		{
			return EMPTY;
		}

		return values.stream()
				.map(SerialNo::ofNullableString)
				.filter(Objects::nonNull)
				.collect(collect());
	}

	@NonNull
	public static SerialNoSet ofNullableCommaSeparated(@Nullable final String csv)
	{
		final String csvNorm = StringUtils.trimBlankToNull(csv);
		if (csvNorm == null)
		{
			return EMPTY;
		}

		return parseStrings(COMMA_SPLITTER.splitToList(csvNorm));
	}

	public static Collector<SerialNo, ?, SerialNoSet> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(SerialNoSet::ofCollection);
	}

	@Override
	@Deprecated
	public String toString() {return toCommaSeparatedString();}

	public String toCommaSeparatedString()
	{
		return serialNos.stream()
				.map(SerialNo::getValueAsString)
				.collect(Collectors.joining(","));
	}

	@Nullable
	public static String toCommaSeparatedStringOrNull(@Nullable final SerialNoSet serialNoSet)
	{
		return serialNoSet != null && !serialNoSet.isEmpty()
				? StringUtils.trimBlankToNull(serialNoSet.toCommaSeparatedString())
				: null;
	}

	/**
	 * Convert to a plain string list for use in JSON DTOs.
	 */
	public List<String> toStringList()
	{
		return serialNos.stream()
				.map(SerialNo::getValueAsString)
				.collect(ImmutableList.toImmutableList());
	}

	public boolean isEmpty() {return serialNos.isEmpty();}

	public int size() {return serialNos.size();}

	public boolean contains(@NonNull final SerialNo serialNo) {return serialNos.contains(serialNo);}

	@Override
	@NonNull
	public Iterator<SerialNo> iterator() {return serialNos.iterator();}

	public Stream<SerialNo> stream() {return serialNos.stream();}

	public ImmutableSet<SerialNo> toSet() {return serialNos;}
}
