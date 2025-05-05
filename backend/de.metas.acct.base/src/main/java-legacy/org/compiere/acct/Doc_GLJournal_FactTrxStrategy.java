package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import org.adempiere.exceptions.AdempiereException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class Doc_GLJournal_FactTrxStrategy implements FactTrxStrategy
{
	public static final Doc_GLJournal_FactTrxStrategy instance = new Doc_GLJournal_FactTrxStrategy();

	private Doc_GLJournal_FactTrxStrategy() {}

	@Override
	public List<FactTrxLines> createFactTrxLines(final List<FactLine> factLines)
	{
		if (factLines.isEmpty())
		{
			return ImmutableList.of();
		}

		final Map<Integer, FactTrxLines.FactTrxLinesBuilder> factTrxLinesByKey = new LinkedHashMap<>();
		for (final FactLine factLine : factLines)
		{
			factTrxLinesByKey.computeIfAbsent(extractGroupNo(factLine), key -> FactTrxLines.builder())
					.factLine(factLine);
		}

		return factTrxLinesByKey.values()
				.stream()
				.map(FactTrxLines.FactTrxLinesBuilder::build)
				.collect(ImmutableList.toImmutableList());
	}

	private static int extractGroupNo(final FactLine factLine)
	{
		final DocLine<?> docLine = factLine.getDocLine();
		if (docLine instanceof DocLine_GLJournal)
		{
			return ((DocLine_GLJournal)docLine).getGroupNo();
		}
		else
		{
			throw new AdempiereException("Expected a DocLine_GLJournal: " + docLine);
		}
	}

}
