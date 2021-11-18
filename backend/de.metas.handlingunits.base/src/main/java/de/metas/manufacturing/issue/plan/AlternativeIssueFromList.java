package de.metas.manufacturing.issue.plan;

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
public class AlternativeIssueFromList
{
	public static final AlternativeIssueFromList EMPTY = new AlternativeIssueFromList(ImmutableList.of());

	private final ImmutableList<AlternativeIssueFrom> issueFroms;

	private AlternativeIssueFromList(@NonNull final List<AlternativeIssueFrom> issueFroms)
	{
		this.issueFroms = ImmutableList.copyOf(issueFroms);
	}

	public static AlternativeIssueFromList ofList(@NonNull final List<AlternativeIssueFrom> list)
	{
		return !list.isEmpty() ? new AlternativeIssueFromList(list) : EMPTY;
	}

	public static Collector<AlternativeIssueFrom, ?, AlternativeIssueFromList> collect() {return GuavaCollectors.collectUsingListAccumulator(AlternativeIssueFromList::ofList);}

	public Stream<AlternativeIssueFrom> stream() {return issueFroms.stream();}
}
