package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class PickingSlotSuggestions
{
	public static final PickingSlotSuggestions EMPTY = new PickingSlotSuggestions(ImmutableList.of());

	@NonNull private final ImmutableList<PickingSlotSuggestion> list;

	private PickingSlotSuggestions(@NonNull final List<PickingSlotSuggestion> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	public static PickingSlotSuggestions ofList(@NonNull final List<PickingSlotSuggestion> list)
	{
		return !list.isEmpty() ? new PickingSlotSuggestions(list) : EMPTY;
	}

	public static Collector<PickingSlotSuggestion, ?, PickingSlotSuggestions> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(PickingSlotSuggestions::ofList);
	}

	public boolean isEmpty() {return list.isEmpty();}

	public Stream<PickingSlotSuggestion> stream() {return list.stream();}
}
