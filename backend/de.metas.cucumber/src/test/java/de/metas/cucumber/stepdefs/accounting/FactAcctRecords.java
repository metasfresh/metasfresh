package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_Fact_Acct;

@Builder
public class FactAcctRecords
{
	@NonNull private final ImmutableList<I_Fact_Acct> list;
	@NonNull private final FactAcctToTabularStringConverter tabularStringConverter;

	@Override
	public String toString() {return tabularStringConverter.toTabular(list, 1).toTabularString();}

	public String toSingleRowTableString(final int index) {return tabularStringConverter.toTabular(list.get(index), index + 1).toTabularString();}

	public int size() {return list.size();}

	public I_Fact_Acct get(final int index) {return list.get(index);}
}
