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
public class DistributionJobCaptionFormat
{
	@NonNull @Getter private final ImmutableList<DistributionJobCaptionFormatItem> items;

	private DistributionJobCaptionFormat(@NonNull final List<DistributionJobCaptionFormatItem> items)
	{
		Check.assumeNotEmpty(items, "list is not empty");
		this.items = ImmutableList.copyOf(items);
	}

	public static Optional<DistributionJobCaptionFormat> ofList(final @NonNull List<DistributionJobCaptionFormatItem> list)
	{
		return list.isEmpty() ? Optional.empty() : Optional.of(new DistributionJobCaptionFormat(list));
	}

	public static DistributionJobCaptionFormat ofNonEmptyList(final @NonNull List<DistributionJobCaptionFormatItem> list)
	{
		return new DistributionJobCaptionFormat(list);
	}

	public static Collector<DistributionJobCaptionFormatItem, ?, Optional<DistributionJobCaptionFormat>> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(DistributionJobCaptionFormat::ofList);
	}
}
