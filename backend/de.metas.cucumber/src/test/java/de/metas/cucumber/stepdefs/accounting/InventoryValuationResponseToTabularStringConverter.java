package de.metas.cucumber.stepdefs.accounting;

import de.metas.inventory.InventoryValuationResponse;
import de.metas.inventory.InventoryValue;
import de.metas.util.text.tabular.Row;
import de.metas.util.text.tabular.Table;
import lombok.experimental.UtilityClass;

@UtilityClass
class InventoryValuationResponseToTabularStringConverter
{
	public static String toTabularString(final InventoryValuationResponse response)
	{
		return toTabular(response).toTabularString();
	}

	public static Table toTabular(final InventoryValuationResponse response)
	{
		final Table table = new Table();
		response.getLines().forEach(line -> table.addRow(toRow(line)));
		table.updateHeaderFromRows();
		//table.removeColumnsWithBlankValues(); // to be decided by the caller
		return table;
	}

	public static Row toRow(final InventoryValue inventoryValue)
	{
		final Row row = new Row();
		row.put("Combination", inventoryValue.getCombination());
		row.put("Description", inventoryValue.getDescription());
		row.put("ActivityName", inventoryValue.getActivityName());
		row.put("WarehouseName", inventoryValue.getWarehouseName());
		row.put("ProductValue", inventoryValue.getProductValue());
		row.put("ProductName", inventoryValue.getProductName());
		row.put("Qty", inventoryValue.getQty());
		row.put("UOMSymbol", inventoryValue.getUomSymbol());
		row.put("CostPrice", inventoryValue.getCostPrice());
		row.put("TotalAmt", inventoryValue.getTotalAmt());
		return row;
	}
}
