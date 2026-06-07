package de.metas.cucumber.stepdefs.mobileui.massprinting;

import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.hu.HUQRCode_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingResult;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingResult.ProductResult;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingScanRequest;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.picking.rest_api.PickingRestController;
import de.metas.picking.rest_api.json.massprinting.JsonMassPrintingProductResult;
import de.metas.picking.rest_api.json.massprinting.JsonMassPrintingResult;
import de.metas.picking.rest_api.json.massprinting.JsonMassPrintingScanRequest;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for the mass-printing scan flow.
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
	@NonNull private final AD_User_StepDefData userTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final HUQRCode_StepDefData huQRCodeStorage;

	@NonNull private final MassPrintingService massPrintingService = SpringContextHolder.instance.getBean(MassPrintingService.class);
	@NonNull private final PickingRestController pickingRestController = SpringContextHolder.instance.getBean(PickingRestController.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	/** Populated by the scan step; consumed by assertion steps. */
	@Nullable
	private MassPrintingResult lastResult;

	/** Populated by the REST scan step; consumed by JSON assertion steps. */
	@Nullable
	private JsonMassPrintingResult lastJsonResult;

	/**
	 * Invokes the mass-printing scan for an LU, executed as a given picker user.
	 *
	 * <p>The picker must have a workplace (and picking slot) assigned via {@code assign C_Workplace to
	 * user}, since the PRODUCT picking job auto-allocates the picker's slot. Tests use a dedicated
	 * picker user (rather than the shared {@code metasfresh} user) so the committed
	 * {@code C_Workplace_User_Assign} does not leak into later scenarios running in the same JVM.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code LU} — identifier of the LU to scan</li>
	 *   <li>{@code Picker} — identifier of the picker {@code AD_User} (created via {@code metasfresh
	 *       contains AD_Users:}) the scan runs as</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * When mass-printing scans LU
	 *   | LU  | Picker             |
	 *   | lu1 | massPrintingPicker |
	 * </pre>
	 */
	@When("mass-printing scans LU")
	public void massPrintingScansLU(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final HuId luId = huTable.getId(row.getAsIdentifier("LU"));
		final UserId pickerId = userTable.getId(row.getAsIdentifier("Picker"));

		lastResult = massPrintingService.scan(
				MassPrintingScanRequest.builder()
						.luId(luId)
						.pickerId(pickerId)
						.build());
	}

	/**
	 * Calls {@link PickingRestController#massPrintingScan} directly via Spring bean (bypassing
	 * the HTTP layer) for an LU identified by a previously generated HU QR code.
	 *
	 * <p>The picker is the currently authenticated user (set by the preceding
	 * {@code the existing user with login '…' receives a random a API token} step, which calls
	 * {@code Env.setLoggedUserId}). This mirrors the production code-path exactly:
	 * {@code PickingRestController.massPrintingScan} reads the picker from {@code Env.getLoggedUserId()}.
	 *
	 * <p>The direct call avoids JSON serialisation/deserialisation of the QR code string, which
	 * contains {@code #} and {@code "} characters that break the HTTP {@code @variable@}
	 * interpolation approach.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code HUQRCode} — identifier of the HU QR code (stored by
	 *       {@code generate QR Codes for HUs} using the {@code HUQRCode} column)</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * When mass-printing REST scans LU
	 *   | HUQRCode |
	 *   | lu_qr    |
	 * </pre>
	 */
	@When("mass-printing REST scans LU")
	public void massPrintingRESTScansLU(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final HUQRCode huQRCode = row.getAsIdentifier("HUQRCode").lookupIn(huQRCodeStorage);
		final String scannedCode = huQRCode.toGlobalQRCodeString();

		final JsonMassPrintingScanRequest request = JsonMassPrintingScanRequest.builder()
				.scannedCode(scannedCode)
				.build();

		lastJsonResult = pickingRestController.massPrintingScan(request);
	}

	/**
	 * Asserts per-product fields returned by {@link PickingRestController#massPrintingScan}.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code boxesPacked} — expected boxes packed for the first product result</li>
	 * </ul>
	 * <p>Optional columns:
	 * <ul>
	 *   <li>{@code OPT.unitsLeftOnLU} — expected leftover units on the LU</li>
	 *   <li>{@code OPT.unitsOfOpenDemandRemaining} — expected remaining open demand</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then mass-printing REST result is
	 *   | boxesPacked | OPT.unitsLeftOnLU | OPT.unitsOfOpenDemandRemaining |
	 *   | 1           | 2                 | 0                              |
	 * </pre>
	 */
	@Then("mass-printing REST result is")
	public void massPrintingRESTResultIs(@NonNull final DataTable dataTable)
	{
		assertThat(lastJsonResult).as("mass-printing REST result shall be present").isNotNull();
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		assertThat(lastJsonResult.getProductResults()).as("productResults").isNotEmpty();
		final JsonMassPrintingProductResult productResult = lastJsonResult.getProductResults().get(0);

		final int expectedBoxesPacked = row.getAsInt("boxesPacked");
		assertThat(productResult.getBoxesPacked()).as("boxesPacked").isEqualTo(expectedBoxesPacked);

		row.getAsOptionalInt("unitsLeftOnLU")
				.ifPresent(expected -> assertThat(productResult.getUnitsLeftOnLU())
						.as("unitsLeftOnLU").isEqualTo(expected));
		row.getAsOptionalInt("unitsOfOpenDemandRemaining")
				.ifPresent(expected -> assertThat(productResult.getUnitsOfOpenDemandRemaining())
						.as("unitsOfOpenDemandRemaining").isEqualTo(expected));
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
	 *   <li>{@code OPT.labelPrintAttempts} — expected number of label print attempts, i.e.
	 *       {@code labelsPrinted + labelPrintFailures}. One print call is issued per packed box
	 *       regardless of whether rendering succeeds (CI, where Jasper is available) or fails (local,
	 *       where it is not), so this is the environment-robust label assertion — it stays correct
	 *       whether the labels actually render or not.</li>
	 *   <li>{@code OPT.labelsPrinted} — expected labels successfully printed (environment-dependent:
	 *       0 locally where Jasper is unavailable; use {@code labelPrintAttempts} for robustness)</li>
	 *   <li>{@code OPT.labelPrintFailures} — expected label print failures (environment-dependent;
	 *       use {@code labelPrintAttempts} for robustness)</li>
	 *   <li>{@code OPT.unitsLeftOnLU} — expected leftover units on LU</li>
	 *   <li>{@code OPT.unitsOfOpenDemandRemaining} — expected remaining open demand</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then mass-printing result is
	 *   | boxesPacked | OPT.labelPrintAttempts | OPT.unitsLeftOnLU |
	 *   | 3           | 3                      | 0                 |
	 * </pre>
	 */
	@Then("mass-printing result is")
	public void massPrintingResultIs(@NonNull final DataTable dataTable)
	{
		assertThat(lastResult).as("mass-printing result shall be present").isNotNull();
		DataTableRows.of(dataTable).forEach(row -> assertProductResult(row, lastResult));
	}

	/**
	 * Asserts the box-HU shape produced by the scan — one packed HU per box. For the first product
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
			final IHUProductStorage productStorage = storageFactory.getStorage(hu).getProductStorageOrNull(productId);
			assertThat(productStorage).as("product storage in box HU %s for product %s", huId, productId).isNotNull();
			final BigDecimal qty = productStorage.getQtyInStockingUOM().toBigDecimal();
			assertThat(qty).as("storage qty in box HU %s", huId).isEqualByComparingTo(expectedQtyPerBoxHU);
		}
	}

	/**
	 * Asserts that the scan produced no product results (i.e., nothing was packed).
	 *
	 * <p>Use this step when the scanned LU has no self-packed products with open demand, so the
	 * expectation is that the result contains an empty {@code productResults} list. The complementary
	 * step {@code mass-printing skipped non-self-packed products:} can be used in the same scenario to
	 * additionally assert which products were present on the LU but skipped.
	 *
	 * <p>Example:
	 * <pre>
	 * Then mass-printing result has no product results
	 * </pre>
	 */
	@Then("mass-printing result has no product results")
	public void massPrintingResultHasNoProductResults()
	{
		assertThat(lastResult).as("mass-printing result shall be present").isNotNull();
		assertThat(lastResult.getProductResults()).as("product results shall be empty (nothing was packed)").isEmpty();
	}

	/**
	 * Asserts that the listed products were skipped during the scan because they are not
	 * {@code IsSelfPacked}.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code skippedProduct} — identifier of the {@code M_Product} (created via {@code
	 *       metasfresh contains M_Products:}) that is expected in the skipped list</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then mass-printing skipped non-self-packed products:
	 *   | skippedProduct    |
	 *   | nonSelfPackedPrd  |
	 * </pre>
	 */
	@Then("mass-printing skipped non-self-packed products:")
	public void massPrintingSkippedNonSelfPackedProducts(@NonNull final DataTable dataTable)
	{
		assertThat(lastResult).as("mass-printing result shall be present").isNotNull();

		final List<ProductId> expectedSkippedIds = DataTableRows.of(dataTable)
				.stream()
				.map(row -> productTable.getId(row.getAsIdentifier("skippedProduct")))
				.collect(Collectors.toList());

		assertThat(lastResult.getSkippedNonSelfPackedProductIds())
				.as("skippedNonSelfPackedProductIds")
				.containsExactlyInAnyOrderElementsOf(expectedSkippedIds);
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

		// labelPrintAttempts is the environment-robust assertion: exactly one print call is issued per
		// packed box, whether the render succeeds (Jasper available in CI) or fails (not available
		// locally). Assert the total attempts (printed + failures) rather than either side alone.
		row.getAsOptionalInt("labelPrintAttempts")
				.ifPresent(expectedLabelPrintAttempts -> assertThat(productResult.getLabelsPrinted() + productResult.getLabelPrintFailures())
						.as("labelPrintAttempts (labelsPrinted + labelPrintFailures)").isEqualTo(expectedLabelPrintAttempts));

		// labelsPrinted and labelPrintFailures are environment-dependent; asserted only when explicitly supplied.
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
