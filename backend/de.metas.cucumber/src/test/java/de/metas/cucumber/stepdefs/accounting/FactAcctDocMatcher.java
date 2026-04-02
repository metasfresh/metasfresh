package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import de.metas.util.text.tabular.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_Fact_Acct;

import java.util.Collection;

public class FactAcctDocMatcher
{
	@NonNull @Getter private final TableRecordReference documentRef;
	private final boolean partialMatch;
	@NonNull private final ImmutableList<FactAcctLineMatcher> matchers;

	@Builder
	private FactAcctDocMatcher(
			@NonNull final TableRecordReference documentRef,
			boolean partialMatch,
			@NonNull final Collection<FactAcctLineMatcher> matchers)
	{
		this.documentRef = documentRef;
		this.partialMatch = partialMatch;
		this.matchers = ImmutableList.copyOf(matchers);
	}

	public static FactAcctDocMatcher noRecords(@NonNull final TableRecordReference documentRef)
	{
		return FactAcctDocMatcher.builder()
				.documentRef(documentRef)
				.partialMatch(false)
				.matchers(ImmutableList.of())
				.build();
	}

	public void assertMatching(@NonNull final SoftAssertions softly, @NonNull final FactAcctRecords records)
	{
		final FactAcctRecordsToMatch recordsToMatch = new FactAcctRecordsToMatch(records);
		assertNonWildcardRulesAreMatching(softly, recordsToMatch);
		assertRemainingRecordsAreMatchingWildcardRules(softly, recordsToMatch);
	}

	private void assertNonWildcardRulesAreMatching(final @NonNull SoftAssertions softly, final @NonNull FactAcctRecordsToMatch records)
	{
		for (FactAcctLineMatcher matcher : matchers)
		{
			if (matcher.isWildcardMatcher())
			{
				continue;
			}

			matcher.assertMatching(softly, records);
		}
	}

	private void assertRemainingRecordsAreMatchingWildcardRules(@NonNull final SoftAssertions softly, @NonNull final FactAcctRecordsToMatch records)
	{
		for (int recordIndex = 0; recordIndex < records.size(); recordIndex++)
		{
			if (records.isMatched(recordIndex))
			{
				continue;
			}

			matchByWildcardRules(records, recordIndex);

			if (!records.isMatched(recordIndex))
			{
				softly.fail("Fact_Acct record was not expected:"
						+ "\n" + records.toSingleRowTableString(recordIndex));
			}
		}
	}

	private void matchByWildcardRules(final @NonNull FactAcctRecordsToMatch records, final int recordIndex)
	{
		final I_Fact_Acct record = records.get(recordIndex);

		for (FactAcctLineMatcher matcher : matchers)
		{
			if (!matcher.isWildcardMatcher())
			{
				continue;
			}

			if (matcher.isMatching(record))
			{
				records.markAsMatched(recordIndex);
				break;
			}
		}
	}

	@Override
	public String toString()
	{
		return "# Document: " + documentRef + ", partialMatch: " + partialMatch
				+ "\n" + toTabular().toTabularString();
	}

	Table toTabular()
	{
		final Table table = new Table();
		matchers.forEach(matcher -> table.addRow(matcher.toTabularRow()));
		table.updateHeaderFromRows();
		return table;
	}
}
