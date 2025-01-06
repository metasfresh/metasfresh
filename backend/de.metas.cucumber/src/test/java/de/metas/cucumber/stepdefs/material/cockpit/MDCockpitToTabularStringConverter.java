package de.metas.cucumber.stepdefs.material.cockpit;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.material.cockpit.CockpitId;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.product.ProductId;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import de.metas.util.text.tabular.Cell;
import de.metas.util.text.tabular.Row;
import de.metas.util.text.tabular.Table;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.List;

@Builder
class MDCockpitToTabularStringConverter
{
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final MD_Cockpit_StepDefData cockpitTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;

	public Table toTableFromRecords(@NonNull final List<I_MD_Cockpit> records)
	{
		final ImmutableList<Row> rows = records.stream().map(this::toRow).collect(ImmutableList.toImmutableList());
		return toTableFromRows(rows);
	}

	public Table toTableFromExpectedResults(@NonNull final ExpectedResults expectedResults)
	{
		final Row row = toRow(expectedResults);
		return toTableFromRows(ImmutableList.of(row));
	}

	private Row toRow(I_MD_Cockpit record)
	{
		final Row row = new Row();
		row.put("Identifier", toCell(record.getMD_Cockpit_ID(), CockpitId.class, cockpitTable));
		row.put(I_MD_Cockpit.COLUMNNAME_M_Product_ID, toCell(record.getM_Product_ID(), ProductId.class, productTable));
		row.put(I_MD_Cockpit.COLUMNNAME_M_Warehouse_ID, toCell(record.getM_Warehouse_ID(), WarehouseId.class, warehouseTable));
		row.put(I_MD_Cockpit.COLUMNNAME_AttributesKey, record.getAttributesKey());
		row.put(I_MD_Cockpit.COLUMNNAME_DateGeneral, record.getDateGeneral());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder_AtDate, record.getQtyDemand_SalesOrder_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyDemandSum_AtDate, record.getQtyDemandSum_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyStockCurrent_AtDate, record.getQtyStockCurrent_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus_AtDate, record.getQtyExpectedSurplus_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyInventoryCount_AtDate, record.getQtyInventoryCount_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder_AtDate, record.getQtySupply_PurchaseOrder_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtySupplySum_AtDate, record.getQtySupplySum_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtySupplyRequired_AtDate, record.getQtySupplyRequired_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtySupplyToSchedule_AtDate, record.getQtySupplyToSchedule_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_MDCandidateQtyStock_AtDate, record.getMDCandidateQtyStock_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyStockChange, record.getQtyStockChange());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyDemand_PP_Order_AtDate, record.getQtyDemand_PP_Order_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtySupply_PP_Order_AtDate, record.getQtySupply_PP_Order_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyDemand_DD_Order_AtDate, record.getQtyDemand_DD_Order_AtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtySupply_DD_Order_AtDate, record.getQtySupply_DD_Order_AtDate());

		return row;
	}

	private static Table toTableFromRows(@NonNull final List<Row> rows)
	{
		final Table table = new Table();
		table.addRows(rows);
		table.removeColumnsWithBlankValues();
		table.updateHeaderFromRows();
		return table;
	}

	public <T extends RepoIdAware> String toString(@Nullable ProductId productId)
	{
		return toCell(productId, productTable).getAsString();
	}

	private static <T extends RepoIdAware> Cell toCell(
			final int repoId,
			@NonNull final Class<T> idType,
			@NonNull StepDefDataGetIdAware<T, ?> lookupTable)
	{
		final T id = RepoIdAwares.ofObjectOrNull(repoId, idType);
		return toCell(id, lookupTable);
	}

	private static <T extends RepoIdAware> Cell toCell(
			@Nullable final T id,
			@NonNull StepDefDataGetIdAware<T, ?> lookupTable)
	{
		if (id == null)
		{
			return Cell.NULL;
		}

		final StepDefDataIdentifier identifier = lookupTable.getFirstIdentifierById(id).orElse(null);
		if (identifier != null)
		{
			return Cell.ofNullable(identifier + "(" + id.getRepoId() + ")");
		}
		else
		{
			return Cell.ofNullable(id.getRepoId());
		}
	}

	private Row toRow(final ExpectedResults expectedResults)
	{
		final Row row = new Row();
		row.put("Identifier", expectedResults.getIdentifier());
		row.put(I_MD_Cockpit.COLUMNNAME_M_Product_ID, toCell(expectedResults.getProductId(), productTable));
		row.put(I_MD_Cockpit.COLUMNNAME_AttributesKey, expectedResults.getStorageAttributesKey().getAsString());
		row.put(I_MD_Cockpit.COLUMNNAME_DateGeneral, expectedResults.getDateGeneral());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyDemandSum_AtDate, expectedResults.getQtyDemandSumAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder_AtDate, expectedResults.getQtyDemandSalesOrderAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyStockCurrent_AtDate, expectedResults.getQtyStockCurrentAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus_AtDate, expectedResults.getQtyExpectedSurplusAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyInventoryCount_AtDate, expectedResults.getQtyInventoryCountAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder_AtDate, expectedResults.getQtySupplyPurchaseOrderAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtySupplySum_AtDate, expectedResults.getQtySupplySumAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtySupplyRequired_AtDate, expectedResults.getQtySupplyRequiredAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtySupplyToSchedule_AtDate, expectedResults.getQtySupplyToScheduleAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_MDCandidateQtyStock_AtDate, expectedResults.getMdCandidateQtyStockAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyStockChange, expectedResults.getQtyStockChange());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyDemand_PP_Order_AtDate, expectedResults.getQtyDemandPPOrderAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtySupply_PP_Order_AtDate, expectedResults.getQtySupplyPPOrderAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtyDemand_DD_Order_AtDate, expectedResults.getQtyDemandDDOrderAtDate());
		row.put(I_MD_Cockpit.COLUMNNAME_QtySupply_DD_Order_AtDate, expectedResults.getQtySupplyDDOrderAtDate());

		return row;
	}

}
