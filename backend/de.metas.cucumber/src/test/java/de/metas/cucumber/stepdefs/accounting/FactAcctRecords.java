package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Fact_Acct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Builder(toBuilder = true)
public class FactAcctRecords
{
	@NonNull private final ImmutableList<I_Fact_Acct> list;
	@NonNull private final FactAcctToTabularStringConverter tabularStringConverter;

	@Override
	public String toString() {return tabularStringConverter.toTabular(list, 1).toTabularString();}

	public String toSingleRowTableString(final int index) {return tabularStringConverter.toTabular(list.get(index), index + 1).toTabularString();}

	public boolean isEmpty() {return list.isEmpty();}

	public int size() {return list.size();}

	public I_Fact_Acct get(final int index) {return list.get(index);}

	public HashMap<TableRecordReference, FactAcctRecords> indexedByDocumentRef()
	{
		final ImmutableListMultimap<TableRecordReference, I_Fact_Acct> listByDocumentRef = Multimaps.index(list, FactAcctRecords::extractDocumentRef);

		final HashMap<TableRecordReference, FactAcctRecords> result = new HashMap<>();
		listByDocumentRef.asMap().forEach((documentRef, subList) -> result.put(documentRef, subList(subList)));
		return result;
	}

	private static TableRecordReference extractDocumentRef(final I_Fact_Acct record)
	{
		return TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID());
	}

	private FactAcctRecords subList(Collection<I_Fact_Acct> subList) {return toBuilder().list(ImmutableList.copyOf(subList)).build();}

	public FactAcctRecords toEmpty()
	{
		return isEmpty() ? this : toBuilder().list(ImmutableList.of()).build();
	}

	public ArrayList<I_Fact_Acct> toArrayList() {return new ArrayList<>(list);}
}
