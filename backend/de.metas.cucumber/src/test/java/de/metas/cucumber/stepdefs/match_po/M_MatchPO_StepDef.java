package de.metas.cucumber.stepdefs.match_po;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper;
import de.metas.order.IMatchPODAO;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_MatchPO;

import java.util.List;

@RequiredArgsConstructor
public class M_MatchPO_StepDef
{
	@NonNull private final M_MatchPO_StepDefData matchPOTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;

	@And("^M_MatchPO are found$")
	public void loadMatchPOs(@NonNull final DataTable table)
	{
		DataTableRows.of(table)
				.setAdditionalRowIdentifierColumnName(I_M_MatchPO.COLUMNNAME_M_MatchPO_ID)
				.forEach(this::loadMatchPO);
	}

	public void loadMatchPO(@NonNull final DataTableRow row)
	{
		@NonNull final StepDefDataIdentifier identifier = row.getAsIdentifier();

		final OrderLineId orderLineId = row.getAsIdentifier(I_M_MatchPO.COLUMNNAME_C_OrderLine_ID).lookupIdIn(orderLineTable);
		final List<I_M_MatchPO> matchPOs = Services.get(IMatchPODAO.class).getByOrderLineId(orderLineId);
		if (matchPOs.isEmpty())
		{
			throw new AdempiereException("No M_MatchPO found for " + orderLineId);
		}
		else if (matchPOs.size() > 1)
		{
			throw new AdempiereException("More than one M_MatchPOs found for " + orderLineId + ": " + matchPOs);
		}
		final I_M_MatchPO matchPO = CollectionUtils.singleElement(matchPOs);
		matchPOTable.put(identifier, matchPO);
	}

	@And("^Wait until M_MatchPO (.*) (is|are) posted$")
	public void waitUntilPosted(
			@NonNull final String commaSeparatedIdentifiers,
			@SuppressWarnings("unused") final String isOrAre) throws InterruptedException
	{
		final ImmutableSet<TableRecordReference> refs = StepDefDataIdentifier.ofCommaSeparatedString(commaSeparatedIdentifiers)
				.stream()
				.map(matchPOTable::getId)
				.map(inoutId -> TableRecordReference.of(I_M_MatchPO.Table_Name, inoutId))
				.collect(ImmutableSet.toImmutableSet());

		AccountingCucumberHelper.waitUtilPosted(refs);
	}

}