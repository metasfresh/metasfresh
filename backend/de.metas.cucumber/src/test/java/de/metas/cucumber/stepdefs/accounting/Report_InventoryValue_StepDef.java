package de.metas.cucumber.stepdefs.accounting;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.i18n.Language;
import de.metas.inventory.InventoryValuationRequest;
import de.metas.inventory.InventoryValuationResponse;
import de.metas.inventory.InventoryValuationService;
import de.metas.inventory.InventoryValue;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class Report_InventoryValue_StepDef
{
	@NonNull private final InventoryValuationService inventoryValuationService = SpringContextHolder.instance.getBean(InventoryValuationService.class);
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;

	@Then("expect inventory valuation report")
	public void expectInventoryValue(final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::expectInventoryValueRow);
	}

	private void expectInventoryValueRow(final DataTableRow row)
	{
		final InventoryValuationRequest request = extractInventoryValuationRequest(row);
		SharedTestContext.put("request", request);

		final InventoryValuationResponse response = inventoryValuationService.report(request);
		SharedTestContext.put("response", InventoryValuationResponseToTabularStringConverter.toTabularString(response));
		final InventoryValue inventoryValue = response.getSingleLine();

		row.getAsOptionalBigDecimal("Qty").ifPresent(qty -> {
			assertThat(inventoryValue.getQty()).as("Qty").isEqualByComparingTo(qty);
		});
		row.getAsOptionalBigDecimal("TotalAmt").ifPresent(totalAmt -> {
			assertThat(inventoryValue.getTotalAmt()).as("TotalAmt").isEqualByComparingTo(totalAmt);
		});
		row.getAsOptionalBigDecimal("CostPrice").ifPresent(costPrice -> {
			assertThat(inventoryValue.getCostPrice()).as("CostPrice").isEqualByComparingTo(costPrice);
		});
	}

	private InventoryValuationRequest extractInventoryValuationRequest(final DataTableRow row)
	{
		return InventoryValuationRequest.builder()
				.dateAcct(row.getAsLocalDate("Date"))
				.productId(row.getAsIdentifier("M_Product_ID").lookupIdIn(productTable))
				.warehouseId(row.getAsIdentifier("M_Warehouse_ID").lookupIdIn(warehouseTable))
				.adLanguage(Language.getBaseAD_Language())
				.build();
	}

}
