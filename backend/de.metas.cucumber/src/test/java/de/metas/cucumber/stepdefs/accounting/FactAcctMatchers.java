package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.text.tabular.Table;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.SoftAssertions;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class FactAcctMatchers
{
	@NonNull private final ImmutableList<FactAcctDocMatcher> documentMatchers;
	@NonNull @Getter private final ImmutableSet<TableRecordReference> documentRefs;

	public FactAcctMatchers(@NonNull final List<FactAcctDocMatcher> documentMatchers)
	{
		this.documentMatchers = ImmutableList.copyOf(documentMatchers);
		this.documentRefs = documentMatchers.stream()
				.map(FactAcctDocMatcher::getDocumentRef)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static FactAcctMatchers noRecords(@NonNull final Collection<TableRecordReference> documentRefs)
	{
		final ImmutableList<FactAcctDocMatcher> documentMatchers = documentRefs.stream()
				.distinct()
				.map(FactAcctDocMatcher::noRecords)
				.collect(ImmutableList.toImmutableList());
		return new FactAcctMatchers(documentMatchers);
	}

	public void assertValid(@NonNull final FactAcctRecords records)
	{
		final SoftAssertions softly = new SoftAssertions();

		final HashMap<TableRecordReference, FactAcctRecords> recordsByDocumentRef = records.indexedByDocumentRef();
		for (final FactAcctDocMatcher docMatcher : documentMatchers)
		{
			FactAcctRecords documentRecords = recordsByDocumentRef.remove(docMatcher.getDocumentRef());
			if (documentRecords == null)
			{
				documentRecords = records.toEmpty();
			}

			docMatcher.assertValid(softly, documentRecords);
		}

		softly.assertThat(recordsByDocumentRef.values()).as("All records were matched").isEmpty();

		softly.assertAll();
	}

	@Override
	public String toString() {return toTabular().toTabularString();}

	private Table toTabular()
	{
		final Table table = new Table();
		documentMatchers.forEach(docMatcher -> table.addRows(docMatcher.toTabular()));
		table.updateHeaderFromRows();
		return table;

	}

}
