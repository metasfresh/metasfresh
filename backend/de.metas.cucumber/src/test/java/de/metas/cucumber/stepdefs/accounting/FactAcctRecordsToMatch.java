package de.metas.cucumber.stepdefs.accounting;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_Fact_Acct;

import java.util.HashSet;

@RequiredArgsConstructor
class FactAcctRecordsToMatch
{
	@NonNull private final HashSet<Integer> matchedRecordIndexes = new HashSet<>();
	@NonNull private final FactAcctRecords records;

	public int size() {return records.size();}

	public boolean isMatched(final int index) {return matchedRecordIndexes.contains(index);}

	public I_Fact_Acct get(final int index) {return records.get(index);}

	public void markAsMatched(final int recordIndex) {matchedRecordIndexes.add(recordIndex);}

	public String toSingleRowTableString(final int index) {return records.toSingleRowTableString(index);}
}
