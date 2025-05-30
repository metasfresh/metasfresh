package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.text.tabular.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.SoftAssertions;

import java.util.List;
import java.util.Set;

public class FactAcctBalanceMatchers
{
	@NonNull private final ImmutableList<FactAcctBalanceMacher> balanceMatchers;
	@NonNull @Getter private final ImmutableSet<TableRecordReference> documentRefs;

	@Builder
	private FactAcctBalanceMatchers(
			@NonNull final List<FactAcctBalanceMacher> balanceMatchers,
			@NonNull final Set<TableRecordReference> documentRefs)
	{
		this.balanceMatchers = ImmutableList.copyOf(balanceMatchers);
		this.documentRefs = ImmutableSet.copyOf(documentRefs);
	}

	public void assertValid(@NonNull final FactAcctRecords records)
	{
		final SoftAssertions softly = new SoftAssertions();

		for (final FactAcctBalanceMacher macher : balanceMatchers)
		{
			macher.assertMatching(softly, records);
		}

		softly.assertAll();
	}

	@Override
	public String toString() {return toTabular().toTabularString();}

	private Table toTabular()
	{
		final Table table = new Table();
		balanceMatchers.forEach(balanceMatcher -> table.addRow(balanceMatcher.toTabularRow()));
		table.updateHeaderFromRows();
		return table;

	}

}
