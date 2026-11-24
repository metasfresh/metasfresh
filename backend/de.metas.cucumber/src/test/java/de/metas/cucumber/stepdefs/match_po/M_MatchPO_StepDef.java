package de.metas.cucumber.stepdefs.match_po;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.inout.InOutLineId;
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
import java.util.stream.Collectors;

/**
 * Steps for {@code M_MatchPO}: loading the matches of an order line and waiting until they are posted.
 */
@RequiredArgsConstructor
public class M_MatchPO_StepDef
{
	@NonNull private final IMatchPODAO matchPODAO = Services.get(IMatchPODAO.class);

	@NonNull private final M_MatchPO_StepDefData matchPOTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_InOutLine_StepDefData inoutLineTable;

	/**
	 * Loads the {@code M_MatchPO} of an order line into the step data (exactly one must be found).
	 * <p>
	 * Columns: required {@code C_OrderLine_ID} (identifier); optional {@code M_MatchPO_ID} (identifier to register the match under),
	 * optional {@code M_InOutLine_ID} (identifier of a receipt line: only the match of that line is considered, needed when
	 * the order line was received in several parts).
	 * <pre>
	 * And M_MatchPO are found
	 *   | M_MatchPO_ID | C_OrderLine_ID | M_InOutLine_ID |
	 *   | mpo1         | po_l1          | receipt1_l1    |
	 * </pre>
	 */
	@And("^M_MatchPO are found$")
	public void loadMatchPOs(@NonNull final DataTable table)
	{
		DataTableRows.of(table)
				.setAdditionalRowIdentifierColumnName(I_M_MatchPO.COLUMNNAME_M_MatchPO_ID)
				.forEach(this::loadMatchPO);
	}

	public void loadMatchPO(@NonNull final DataTableRow row)
	{
		final OrderLineId orderLineId = row.getAsIdentifier(I_M_MatchPO.COLUMNNAME_C_OrderLine_ID).lookupIdIn(orderLineTable);
		final InOutLineId inoutLineId = row.getAsOptionalIdentifier(I_M_MatchPO.COLUMNNAME_M_InOutLine_ID)
				.map(inoutLineTable::getId)
				.orElse(null);
		// when the row names the receipt line, keep only that line's match (an order line can be received in several parts)
		final List<I_M_MatchPO> matchPOs = matchPODAO.getByOrderLineId(orderLineId).stream()
				.filter(matchPO -> inoutLineId == null || InOutLineId.equals(InOutLineId.ofRepoIdOrNull(matchPO.getM_InOutLine_ID()), inoutLineId))
				.collect(Collectors.toList());
		if (matchPOs.isEmpty())
		{
			throw new AdempiereException("No M_MatchPO found for " + orderLineId + (inoutLineId != null ? " and " + inoutLineId : ""));
		}
		else if (matchPOs.size() > 1)
		{
			throw new AdempiereException("More than one M_MatchPOs found for " + orderLineId + ": " + matchPOs);
		}
		final I_M_MatchPO matchPO = CollectionUtils.singleElement(matchPOs);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> matchPOTable.put(identifier, matchPO));
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