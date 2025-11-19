package de.metas.distribution.config;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@EqualsAndHashCode
public class DistributionJobSorting
{
	public static DistributionJobSorting DEFAULT = new DistributionJobSorting(ImmutableList.of(
			DistributionJobSortingItem.of(DistributionJobSortingField.Priority, Direction.Ascending),
			DistributionJobSortingItem.of(DistributionJobSortingField.DatePromised, Direction.Ascending)
	));

	@NonNull @Getter private final ImmutableList<DistributionJobSortingItem> items;
	@NonNull private final ImmutableList<DDOrderQuery.OrderBy> ddOrderQueryOrderBys;

	private DistributionJobSorting(@NonNull final List<DistributionJobSortingItem> items)
	{
		Check.assumeNotEmpty(items, "list is not empty");
		this.items = ImmutableList.copyOf(items);

		this.ddOrderQueryOrderBys = items.stream()
				.map(DistributionJobSortingItem::toDDOrderQueryOrderBy)
				.collect(ImmutableList.toImmutableList());

	}

	public static Optional<DistributionJobSorting> ofList(final @NonNull List<DistributionJobSortingItem> list)
	{
		return list.isEmpty() ? Optional.empty() : Optional.of(new DistributionJobSorting(list));
	}

	public static Collector<DistributionJobSortingItem, ?, Optional<DistributionJobSorting>> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(DistributionJobSorting::ofList);
	}

	public ImmutableList<DDOrderQuery.OrderBy> toDDOrderQueryOrderBys() {return ddOrderQueryOrderBys;}
}
