package de.metas.acct.factacct_userchanges;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class FactAcctChangesList implements Iterable<FactAcctChanges>
{
	public static final FactAcctChangesList EMPTY = new FactAcctChangesList(ImmutableList.of());

	private final ImmutableList<FactAcctChanges> list;
	private final ImmutableMap<FactLineMatchKey, FactAcctChanges> byMatchKey;

	private FactAcctChangesList(@NonNull final List<FactAcctChanges> list)
	{
		this.list = ImmutableList.copyOf(list);
		this.byMatchKey = Maps.uniqueIndex(list, FactAcctChanges::getMatchKey);
	}

	public static FactAcctChangesList ofList(@NonNull final List<FactAcctChanges> list)
	{
		return !list.isEmpty() ? new FactAcctChangesList(list) : EMPTY;
	}

	public static Collector<FactAcctChanges, ?, FactAcctChangesList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(FactAcctChangesList::ofList);
	}

	@Override
	public Iterator<FactAcctChanges> iterator() {return list.iterator();}

	public Optional<FactAcctChanges> getByMatchKey(@NonNull final FactLineMatchKey matchKey)
	{
		return Optional.ofNullable(byMatchKey.get(matchKey));
	}
}
