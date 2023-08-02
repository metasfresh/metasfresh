package de.metas.acct.factacct_userchanges;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AcctSchemaId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class FactAcctChangesList
{
	public static final FactAcctChangesList EMPTY = new FactAcctChangesList(ImmutableList.of());

	@Getter private final ImmutableList<FactAcctChanges> allLines;
	@Getter private final ImmutableListMultimap<AcctSchemaId, FactAcctChanges> linesToAddGroupedByAcctSchemaId;
	private final ImmutableMap<FactLineMatchKey, FactAcctChanges> linesToChangeByKey;
	private final ImmutableMap<FactLineMatchKey, FactAcctChanges> linesToRemoveByKey;

	private FactAcctChangesList(@NonNull final List<FactAcctChanges> list)
	{
		this.allLines = ImmutableList.copyOf(list);
		this.linesToAddGroupedByAcctSchemaId = list.stream()
				.filter(changes -> changes.getType().isAdd())
				.collect(ImmutableListMultimap.toImmutableListMultimap(FactAcctChanges::getAcctSchemaId, changes -> changes));
		this.linesToChangeByKey = list.stream()
				.filter(changes -> changes.getType().isChange())
				.collect(ImmutableMap.toImmutableMap(FactAcctChanges::getMatchKey, changes -> changes));
		this.linesToRemoveByKey = list.stream()
				.filter(changes -> changes.getType().isDelete())
				.collect(ImmutableMap.toImmutableMap(FactAcctChanges::getMatchKey, changes -> changes));
	}

	public static FactAcctChangesList ofList(@NonNull final List<FactAcctChanges> list)
	{
		return !list.isEmpty() ? new FactAcctChangesList(list) : EMPTY;
	}

	public static Collector<FactAcctChanges, ?, FactAcctChangesList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(FactAcctChangesList::ofList);
	}

	public Optional<FactAcctChanges> getChangeByKey(@NonNull final FactLineMatchKey matchKey)
	{
		return Optional.ofNullable(linesToChangeByKey.get(matchKey));
	}

	public boolean isLineRemoved(@NonNull final FactLineMatchKey matchKey) {return linesToRemoveByKey.containsKey(matchKey);}

	public FactAcctChangesList removingIf(@NonNull final Predicate<FactAcctChanges> predicate)
	{
		if (allLines.isEmpty())
		{
			return this;
		}

		final ArrayList<FactAcctChanges> allLinesAfterRemove = new ArrayList<>(allLines.size());
		for (final FactAcctChanges line : allLines)
		{
			final boolean remove = predicate.test(line);
			if (!remove)
			{
				allLinesAfterRemove.add(line);
			}
		}

		if (allLines.size() == allLinesAfterRemove.size())
		{
			return this; // no changes
		}

		return ofList(allLinesAfterRemove);
	}
}
