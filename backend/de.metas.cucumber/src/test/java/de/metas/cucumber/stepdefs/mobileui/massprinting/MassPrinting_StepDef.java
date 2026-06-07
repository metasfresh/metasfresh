package de.metas.cucumber.stepdefs.mobileui.massprinting;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingResult;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingResult.ProductResult;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingScanRequest;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingService;
import de.metas.user.UserId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;

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
	 *   <li>{@code OPT.labelsPrinted} — expected labels printed (defaults to {@code boxesPacked} if omitted)</li>
	 *   <li>{@code OPT.unitsLeftOnLU} — expected leftover units on LU</li>
	 *   <li>{@code OPT.unitsOfOpenDemandRemaining} — expected remaining open demand</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then mass-printing result is
	 *   | boxesPacked | OPT.labelsPrinted | OPT.unitsLeftOnLU |
	 *   | 3           | 3                 | 0                 |
	 * </pre>
	 */
	@Then("mass-printing result is")
	public void massPrintingResultIs(@NonNull final DataTable dataTable)
	{
		assertThat(lastResult).as("mass-printing result shall be present").isNotNull();
		DataTableRows.of(dataTable).forEach(row -> assertProductResult(row, lastResult));
	}

	private void assertProductResult(
			@NonNull final DataTableRow row,
			@NonNull final MassPrintingResult result)
	{
		final int expectedBoxesPacked = row.getAsInt("boxesPacked");
		final int expectedLabelsPrinted = row.getAsOptionalInt("labelsPrinted").orElse(expectedBoxesPacked);
		final int expectedUnitsLeftOnLU = row.getAsOptionalInt("unitsLeftOnLU").orElse(-1);
		final int expectedUnitsOfOpenDemandRemaining = row.getAsOptionalInt("unitsOfOpenDemandRemaining").orElse(-1);

		// Find the single product result (for the first scenario we don't require matching by product id)
		assertThat(result.getProductResults()).as("product results").isNotEmpty();
		final ProductResult productResult = result.getProductResults().get(0);

		assertThat(productResult.getBoxesPacked()).as("boxesPacked").isEqualTo(expectedBoxesPacked);
		assertThat(productResult.getLabelsPrinted()).as("labelsPrinted").isEqualTo(expectedLabelsPrinted);

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
