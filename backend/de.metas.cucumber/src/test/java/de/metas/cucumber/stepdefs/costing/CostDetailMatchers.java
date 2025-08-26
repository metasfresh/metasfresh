package de.metas.cucumber.stepdefs.costing;

import com.google.common.collect.ImmutableList;
import de.metas.costing.CostDetail;
import de.metas.i18n.BooleanWithReason;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.util.Env;

import java.util.ArrayList;

class CostDetailMatchers
{
	private final ImmutableList<CostDetailMatcher> matchers;

	public CostDetailMatchers(final ImmutableList<CostDetailMatcher> matchers)
	{
		this.matchers = Check.assumeNotEmpty(matchers, "no matchers");
	}

	public BooleanWithReason checkValid(@NonNull CostDetailRecords costDetails)
	{
		if (costDetails.size() != matchers.size())
		{
			return BooleanWithReason.falseBecause("Invalid number of cost details: " + costDetails.size() + " != " + matchers.size());
		}

		final ArrayList<String> notValidMessages = new ArrayList<>();

		for (int i = 0; i < matchers.size(); i++)
		{
			final int lineNo = i + 1;
			final CostDetailMatcher matcher = matchers.get(i);
			final CostDetail costDetail = costDetails.getBy(matcher.getDocumentRef(), matcher.getAmtType()).orElse(null);
			if (costDetail == null)
			{
				notValidMessages.add("line " + lineNo + ": no cost detail found for " + matcher.getRow());
				continue;
			}

			final BooleanWithReason valid = matcher.checkValid(costDetail);
			if (valid.isFalse())
			{
				notValidMessages.add("line " + lineNo + ": " + valid.getReason().translate(Env.getADLanguageOrBaseLanguage()));
			}
		}

		if (!notValidMessages.isEmpty())
		{
			return BooleanWithReason.falseBecause("Invalid cost details:\n" + String.join("\n", notValidMessages));
		}
		else
		{
			return BooleanWithReason.TRUE;
		}
	}

	public TableRecordReferenceSet getTableRecordReferences()
	{
		return matchers.stream()
				.map(CostDetailMatcher::getTableRecordReference)
				.collect(TableRecordReferenceSet.collect());
	}
}
