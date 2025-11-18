package de.metas.distribution.config;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class CaptionFormat
{
	@NonNull @Getter private final ImmutableList<CaptionFormatItem> items;

	private CaptionFormat(@NonNull final List<CaptionFormatItem> items)
	{
		Check.assumeNotEmpty(items, "list is not empty");
		this.items = ImmutableList.copyOf(items);
	}

	public static Optional<CaptionFormat> ofList(final @NonNull List<CaptionFormatItem> list)
	{
		return list.isEmpty() ? Optional.empty() : Optional.of(new CaptionFormat(list));
	}

	public static CaptionFormat ofNonEmptyList(final @NonNull List<CaptionFormatItem> list)
	{
		return new CaptionFormat(list);
	}

	public static Collector<CaptionFormatItem, ?, Optional<CaptionFormat>> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(CaptionFormat::ofList);
	}
}
