package de.metas.cucumber.stepdefs.mobileui.massprinting;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingResult;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingResult.ProductResult;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingScanRequest;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingService;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for the mass-printing flow (https://github.com/metasfresh/me03/issues/29942).
 *
 * <p>Lifecycle:
 * <ul>
 *   <li>{@code When} steps invoke {@link MassPrintingService#scan(MassPrintingScanRequest)}</li>
 *   <li>{@code Then} steps assert the returned {@link MassPrintingResult}</li>
 * </ul>
 */
@RequiredArgsConstructor
public class MassPrinting_StepDef
{
	@NonNull private final M_HU_StepDefData huTable;

	@NonNull private final MassPrintingService massPrintingService = SpringContextHolder.instance.getBean(MassPrintingService.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	/** Populated by the scan step; consumed by assertion steps. */
	@Nullable
	private MassPrintingResult lastResult;

	/**
	 * Invokes the mass-printing scan for an LU.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code LU} — identifier of the LU to scan</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * When mass-printing scans LU
	 *   | LU  |
	 *   | lu1 |
	 * </pre>
	 */
	@When("mass-printing scans LU")
	public void massPrintingScansLU(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final HuId luId = huTable.getId(row.getAsIdentifier("LU"));

		lastResult = massPrintingService.scan(
				MassPrintingScanRequest.builder()
						.luId(luId)
						.pickerId(UserId.METASFRESH)
						.build());
	}

	/**
	 * Asserts boxes packed and labels printed for the first product result.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code boxesPacked} — expected number of boxes</li>
	 * </ul>
	 * <p>Optional columns:
	 * <ul>
	 *   <li>{@code OPT.labelsPrinted} — expected labels successfully printed</li>
	 *   <li>{@code OPT.labelPrintFailures} — expected label print failures</li>
	 *   <li>{@code OPT.unitsLeftOnLU} — expected leftover units on LU</li>
	 *   <li>{@code OPT.unitsOfOpenDemandRemaining} — expected remaining open demand</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then mass-printing result is
	 *   | boxesPacked | OPT.labelsPrinted | OPT.labelPrintFailures | OPT.unitsLeftOnLU |
	 *   | 3           | 0                 | 3                      | 0                 |
	 * </pre>
	 */
	@Then("mass-printing result is")
	public void massPrintingResultIs(@NonNull final DataTable dataTable)
	{
		assertThat(lastResult).as("mass-printing result shall be present").isNotNull();
		DataTableRows.of(dataTable).forEach(row -> assertProductResult(row, lastResult));
	}

	/**
	 * Asserts the box-HU shape produced by the scan (Task 2.4 — one HU per box). For the first product
	 * result it verifies the produced box-HU count equals the expected value and that each box HU is a
	 * transport unit holding exactly the expected per-box quantity.
	 *
	 * <p>This is the must-prove that in-memory JUnit cannot cover — only the running cucumber stack
	 * materialises the real HU shape.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code boxHUCount} — expected number of box HUs (one per unit packed)</li>
	 *   <li>{@code qtyPerBoxHU} — expected storage quantity in each box HU</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then mass-printing produced box HUs
	 *   | boxHUCount | qtyPerBoxHU |
	 *   | 3          | 1           |
	 * </pre>
	 */
	@Then("mass-printing produced box HUs")
	public void massPrintingProducedBoxHUs(@NonNull final DataTable dataTable)
	{
		assertThat(lastResult).as("mass-printing result shall be present").isNotNull();
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final int expectedBoxHUCount = row.getAsInt("boxHUCount");
		final BigDecimal expectedQtyPerBoxHU = row.getAsBigDecimal("qtyPerBoxHU");

		assertThat(lastResult.getProductResults()).as("product results").isNotEmpty();
		final ProductResult productResult = lastResult.getProductResults().get(0);
		final ProductId productId = productResult.getProductId();

		assertThat(productResult.getPackedHUIds()).as("packed box HU count").hasSize(expectedBoxHUCount);

		// Each box HU is the product-holding unit for exactly one box; assert it carries the expected qty.
		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		for (final HuId huId : productResult.getPackedHUIds())
		{
			final I_M_HU hu = handlingUnitsBL.getById(huId);
			final BigDecimal qty = storageFactory.getStorage(hu).getProductStorageOrNull(productId).getQtyInStockingUOM().toBigDecimal();
			assertThat(qty).as("storage qty in box HU %s", huId).isEqualByComparingTo(expectedQtyPerBoxHU);
		}
	}

	private void assertProductResult(
			@NonNull final DataTableRow row,
			@NonNull final MassPrintingResult result)
	{
		final int expectedBoxesPacked = row.getAsInt("boxesPacked");
		final int expectedUnitsLeftOnLU = row.getAsOptionalInt("unitsLeftOnLU").orElse(-1);
		final int expectedUnitsOfOpenDemandRemaining = row.getAsOptionalInt("unitsOfOpenDemandRemaining").orElse(-1);

		// Find the single product result (for the first scenario we don't require matching by product id)
		assertThat(result.getProductResults()).as("product results").isNotEmpty();
		final ProductResult productResult = result.getProductResults().get(0);

		assertThat(productResult.getBoxesPacked()).as("boxesPacked").isEqualTo(expectedBoxesPacked);

		// labelsPrinted and labelPrintFailures are asserted only when explicitly supplied.
		row.getAsOptionalInt("labelsPrinted")
				.ifPresent(expectedLabelsPrinted -> assertThat(productResult.getLabelsPrinted())
						.as("labelsPrinted").isEqualTo(expectedLabelsPrinted));
		row.getAsOptionalInt("labelPrintFailures")
				.ifPresent(expectedLabelPrintFailures -> assertThat(productResult.getLabelPrintFailures())
						.as("labelPrintFailures").isEqualTo(expectedLabelPrintFailures));

		if (expectedUnitsLeftOnLU >= 0)
		{
			assertThat(productResult.getUnitsLeftOnLU()).as("unitsLeftOnLU").isEqualTo(expectedUnitsLeftOnLU);
		}
		if (expectedUnitsOfOpenDemandRemaining >= 0)
		{
			assertThat(productResult.getUnitsOfOpenDemandRemaining()).as("unitsOfOpenDemandRemaining").isEqualTo(expectedUnitsOfOpenDemandRemaining);
		}
	}
}
