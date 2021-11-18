package de.metas.manufacturing.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PPOrderAvailableHUToIssueMap
{
	public static final PPOrderAvailableHUToIssueMap EMPTY = new PPOrderAvailableHUToIssueMap(ImmutableList.of());

	private final ImmutableList<PPOrderAvailableHUToIssue> list;

	private PPOrderAvailableHUToIssueMap(@NonNull final List<PPOrderAvailableHUToIssue> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	public static PPOrderAvailableHUToIssueMap ofList(@NonNull final List<PPOrderAvailableHUToIssue> list)
	{
		return !list.isEmpty() ? new PPOrderAvailableHUToIssueMap(list) : EMPTY;
	}

	public static Collector<PPOrderAvailableHUToIssue, ?, PPOrderAvailableHUToIssueMap> collect() {return GuavaCollectors.collectUsingListAccumulator(PPOrderAvailableHUToIssueMap::ofList);}

	public Stream<PPOrderAvailableHUToIssue> stream() {return list.stream();}

	public ImmutableList<PPOrderAvailableHUToIssue> toList()
	{
		return list;
	}
}
