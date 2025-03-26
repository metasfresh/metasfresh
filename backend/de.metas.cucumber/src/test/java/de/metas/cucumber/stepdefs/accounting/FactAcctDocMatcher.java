package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.context.ContextAwareDescription;
import de.metas.util.text.tabular.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_Fact_Acct;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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

	public void assertValid(@NonNull final SoftAssertions softly, @NonNull final FactAcctRecords records)
	{
		if (partialMatch)
		{
			assertValid_PartialMatch(softly, records);
		}
		else
		{
			assertValid_FullyMatch(softly, records);
		}
	}

	private void assertValid_FullyMatch(@NonNull final SoftAssertions softly, @NonNull final FactAcctRecords records)
	{
		if (records.size() != matchers.size())
		{
			softly.fail("Actual records count is not equal to the number of expectation rows:"
					+ "\nExpectations: \n" + matchers
					+ "\nActual:\n" + records);
			return;
		}

		for (int i = 0; i < matchers.size(); i++)
		{
			final FactAcctLineMatcher matcher = matchers.get(i);
			final I_Fact_Acct record = records.get(i);

			final ContextAwareDescription description = ContextAwareDescription.newInstance();
			description.put("expectation", "\n" + matcher);
			description.put("actual", "\n" + records.toSingleRowTableString(i));

			matcher.assertMatching(record, softly, description);
		}
		softly.assertAll();
	}

	private void assertValid_PartialMatch(@NonNull final SoftAssertions softly, @NonNull final FactAcctRecords records)
	{
		final HashSet<Integer> matchedRecordIndexes = new HashSet<>();
		for (FactAcctLineMatcher matcher : matchers)
		{
			assertValid_PartialMatch(softly, records, matcher, matchedRecordIndexes);
		}
	}

	private static void assertValid_PartialMatch(
			final SoftAssertions softly,
			final FactAcctRecords records,
			final FactAcctLineMatcher matcher,
			final HashSet<Integer> matchedRecordIndexes)
	{
		for (int recordIndex = 0; recordIndex < records.size(); recordIndex++)
		{
			if (matchedRecordIndexes.contains(recordIndex))
			{
				continue;
			}

			final I_Fact_Acct record = records.get(recordIndex);

			final ContextAwareDescription description = ContextAwareDescription.newInstance();
			description.put("expectation", "\n" + matcher);
			description.put("actual", "\n" + records.toSingleRowTableString(recordIndex));

			final SoftAssertions recordSoftly = new SoftAssertions();
			matcher.assertMatching(record, recordSoftly, description);
			final List<AssertionError> errors = recordSoftly.assertionErrorsCollected();
			if (errors.isEmpty())
			{
				matchedRecordIndexes.add(recordIndex);
				return;
			}
		}

		softly.fail("No record matched the matcher\n" + matcher);
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
