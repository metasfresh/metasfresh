package de.metas.cucumber.stepdefs.costing;

import de.metas.costing.CostingDocumentRef;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.inventory.M_InventoryLine_StepDefData;
import de.metas.cucumber.stepdefs.match_inv.M_MatchInv_StepDefData;
import de.metas.cucumber.stepdefs.match_po.M_MatchPO_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.inout.InOutLineId;
import de.metas.inventory.InventoryLineId;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.order.MatchPOId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_MatchPO;

import java.util.Optional;

@RequiredArgsConstructor
public class CostingDocumentRefResolver
{
	@NonNull private final M_MatchPO_StepDefData matchPOTable;
	@NonNull private final M_MatchInv_StepDefData matchInvTable;
	@NonNull private final M_InOutLine_StepDefData inoutLineTable;
	@NonNull private final M_InventoryLine_StepDefData inventoryLineTable;

	public CostingDocumentRef resolveToCostingDocumentRef(final DataTableRow row)
	{
		final String tableName = row.getAsString("TableName");
		final StepDefDataIdentifier recordIdIdentifier = row.getAsIdentifier("Record_ID");
		switch (tableName)
		{
			case I_M_MatchPO.Table_Name:
			{
				final MatchPOId recordId = matchPOTable.getId(recordIdIdentifier);
				return CostingDocumentRef.ofMatchPOId(recordId);
			}
			case I_M_MatchInv.Table_Name:
			{
				final MatchInvId recordId = matchInvTable.getId(recordIdIdentifier);
				return CostingDocumentRef.ofMatchInvoiceId(recordId);
			}
			case I_M_InOutLine.Table_Name:
			{
				final InOutLineId inoutLineId = inoutLineTable.getId(recordIdIdentifier);
				final boolean isSOTrx = row.getAsBoolean("IsSOTrx");
				return isSOTrx ? CostingDocumentRef.ofShipmentLineId(inoutLineId) : CostingDocumentRef.ofReceiptLineId(inoutLineId);
			}
			case I_M_InventoryLine.Table_Name:
			{
				final InventoryLineId inventoryLineId = inventoryLineTable.getId(recordIdIdentifier);
				return CostingDocumentRef.ofInventoryLineId(inventoryLineId);
			}
			default:
			{
				throw new AdempiereException("Table not handled: " + tableName);
			}
		}
	}

	public Optional<StepDefDataIdentifier> resolveToIdentifier(@NonNull final CostingDocumentRef costingDocumentRef)
	{
		final String tableName = costingDocumentRef.getTableName();
		switch (tableName)
		{
			case I_M_MatchPO.Table_Name:
			{
				return matchPOTable.getFirstIdentifierById(costingDocumentRef.getId(MatchPOId.class));
			}
			case I_M_MatchInv.Table_Name:
			{
				return matchInvTable.getFirstIdentifierById(costingDocumentRef.getId(MatchInvId.class));
			}
			case I_M_InOutLine.Table_Name:
			{
				return inoutLineTable.getFirstIdentifierById(costingDocumentRef.getId(InOutLineId.class));
			}
			case I_M_InventoryLine.Table_Name:
			{
				return inventoryLineTable.getFirstIdentifierById(costingDocumentRef.getId(InventoryLineId.class));
			}
			default:
			{
				throw new AdempiereException("Table not handled: " + tableName);
			}
		}
	}

	public TableRecordReferenceSet resolveToDocumentHeaderRefs(final TableRecordReferenceSet recordRefs)
	{
		return recordRefs.stream()
				.map(this::resolveToDocumentHeaderRef)
				.collect(TableRecordReferenceSet.collect());
	}

	private TableRecordReference resolveToDocumentHeaderRef(final TableRecordReference recordRef)
	{
		final String tableName = recordRef.getTableName();
		switch (tableName)
		{
			case I_M_MatchPO.Table_Name:
			case I_M_MatchInv.Table_Name:
			{
				return recordRef;
			}
			case I_M_InOutLine.Table_Name:
			{
				final InOutLineId lineId = recordRef.getIdAssumingTableName(I_M_InOutLine.Table_Name, InOutLineId::ofRepoId);
				final I_M_InOutLine line = inoutLineTable.getFirstById(lineId).orElseThrow(() -> new AdempiereException("No InOut line found for " + lineId));
				return TableRecordReference.of(I_M_InOut.Table_Name, line.getM_InOut_ID());
			}
			case I_M_InventoryLine.Table_Name:
			{
				final InventoryLineId lineId = recordRef.getIdAssumingTableName(I_M_InventoryLine.Table_Name, InventoryLineId::ofRepoId);
				final I_M_InventoryLine line = inventoryLineTable.getFirstById(lineId).orElseThrow(() -> new AdempiereException("No Inventory line found for " + lineId));
				return TableRecordReference.of(I_M_Inventory.Table_Name, line.getM_Inventory_ID());
			}
			default:
			{
				throw new AdempiereException("Table not handled: " + tableName);
			}
		}
	}

}
