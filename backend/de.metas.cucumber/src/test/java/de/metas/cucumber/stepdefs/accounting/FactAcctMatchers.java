package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.context.ContextAwareDescription;
import de.metas.util.text.tabular.Table;
import lombok.NonNull;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_Fact_Acct;

import static org.assertj.core.api.Assertions.assertThat;

public class FactAcctMatchers
{
	private final ImmutableList<FactAcctMatcher> matchers;

	public FactAcctMatchers(@NonNull final ImmutableList<FactAcctMatcher> matchers)
	{
		this.matchers = matchers;
	}

	public void assertValid(final FactAcctRecords records)
	{
		assertThat(records.size()).as("Fact_Acct records count").isEqualTo(matchers.size());

		final SoftAssertions softly = new SoftAssertions();
		for (int i = 0; i < matchers.size(); i++)
		{
			final FactAcctMatcher matcher = matchers.get(i);
			final I_Fact_Acct record = records.get(i);

			final ContextAwareDescription description = ContextAwareDescription.newInstance();
			description.put("expectation", "\n" + matcher);
			description.put("actual", "\n" + records.toSingleRowTableString(i));

			matcher.assertValid(record, softly, description);
		}
		softly.assertAll();
	}

	@Override
	public String toString() {return toTabular().toTabularString();}

	private Table toTabular()
	{
		final Table table = new Table();
		matchers.forEach(matcher -> table.addRow(matcher.toTabularRow()));
		table.updateHeaderFromRows();
		return table;

	}

}
