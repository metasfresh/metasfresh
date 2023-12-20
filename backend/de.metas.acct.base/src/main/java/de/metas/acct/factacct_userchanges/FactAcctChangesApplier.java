package de.metas.acct.factacct_userchanges;

import com.google.common.collect.ArrayListMultimap;
import de.metas.acct.api.AcctSchemaId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactAcctChangesApplier
{
	private final ArrayListMultimap<AcctSchemaId, FactAcctChanges> linesToAddGroupedByAcctSchemaId;
	private final HashMap<FactLineMatchKey, FactAcctChanges> linesToChangeByKey;
	private final HashMap<FactLineMatchKey, FactAcctChanges> linesToRemoveByKey;

	public FactAcctChangesApplier(@NonNull final FactAcctChangesList changesList)
	{
		this.linesToAddGroupedByAcctSchemaId = ArrayListMultimap.create(changesList.getLinesToAddGroupedByAcctSchemaId());
		this.linesToChangeByKey = new HashMap<>(changesList.getLinesToChangeByKey());
		this.linesToRemoveByKey = new HashMap<>(changesList.getLinesToRemoveByKey());
	}

	public boolean hasChangesToApply()
	{
		return !linesToAddGroupedByAcctSchemaId.isEmpty()
				|| !linesToChangeByKey.isEmpty()
				|| !linesToRemoveByKey.isEmpty();
	}

	public void applyTo(final ArrayList<Fact> facts)
	{
		if (!hasChangesToApply())
		{
			return;
		}

		facts.forEach(this::applyTo);
	}

	public void applyTo(final Fact fact)
	{
		if (!hasChangesToApply())
		{
			return;
		}

		applyUpdatesTo(fact);
		addLinesTo(fact);
	}

	public void applyUpdatesTo(final Fact fact)
	{
		fact.mapEachLine(this::applyTo);
	}

	private FactLine applyTo(@NonNull final FactLine factLine)
	{
		final FactLineMatchKey matchKey = FactLineMatchKey.ofFactLine(factLine);

		//
		// Remove line
		final FactAcctChanges remove = linesToRemoveByKey.remove(matchKey);
		if (remove != null)
		{
			return null; // consider line removed
		}
		//
		// Change line
		else
		{
			final FactAcctChanges change = linesToChangeByKey.remove(matchKey);
			if (change != null)
			{
				factLine.updateFrom(change);
			}
			return factLine;
		}
	}

	private void addLinesTo(@NonNull final Fact fact)
	{
		final AcctSchemaId acctSchemaId = fact.getAcctSchemaId();
		final List<FactAcctChanges> additions = linesToAddGroupedByAcctSchemaId.removeAll(acctSchemaId);
		additions.forEach(addition -> addLine(fact, addition));
	}

	public void addLine(@NonNull final Fact fact, @NonNull final FactAcctChanges changesToAdd)
	{
		if (!changesToAdd.getType().isAdd())
		{
			throw new AdempiereException("Expected type `Add` but it was " + changesToAdd);
		}

		final FactLine factLine = fact.createLine()
				.alsoAddZeroLine()
				.setAccount(changesToAdd.getAccountIdNotNull())
				.setAmtSource(changesToAdd.getAmtSourceDr(), changesToAdd.getAmtSourceCr())
				.setAmtAcct(changesToAdd.getAmtAcctDr(), changesToAdd.getAmtAcctCr())
				.setC_Tax_ID(changesToAdd.getTaxId())
				.description(changesToAdd.getDescription())
				.sectionCodeId(changesToAdd.getSectionCodeId())
				.productId(changesToAdd.getProductId())
				.userElementString1(changesToAdd.getUserElementString1())
				.salesOrderId(changesToAdd.getSalesOrderId())
				.activityId(changesToAdd.getActivityId())
				.buildAndAdd();
		if (factLine != null)
		{
			factLine.updateFrom(changesToAdd); // just to have appliedUserChanges set
		}
	}

}
