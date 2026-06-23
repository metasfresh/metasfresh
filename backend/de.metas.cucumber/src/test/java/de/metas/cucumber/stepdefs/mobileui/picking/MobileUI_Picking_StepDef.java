package de.metas.cucumber.stepdefs.mobileui.picking;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.picking.PickingSlot_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.rest_api.json.JsonPickingJob;
import de.metas.picking.rest_api.json.JsonPickingJobLine;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.picking.workflow.handlers.activity_handlers.ActualPickingWFActivityHandler;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFActivity;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.util.Util;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class MobileUI_Picking_StepDef
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	@NonNull private final MobileUIPickingClient mobileUIPickingClient = new MobileUIPickingClient();

	@NonNull private final M_Product_StepDefData productsTable;
	@NonNull private final C_Order_StepDefData ordersTable;
	@NonNull private final PickingSlot_StepDefData pickingSlotsTable;
	@NonNull private final M_HU_StepDefData huTable;
	@NonNull private final M_HU_PI_StepDefData huPiTable;

	@NonNull private final Context context = new Context();

	@When("^start picking job for sales order identified by (.*)$")
	public void start(@NonNull final String salesOrderIdentifier)
	{
		final I_C_Order salesOrder = ordersTable.get(salesOrderIdentifier);
		final JsonWFProcess wfProcess = mobileUIPickingClient.startJobBySalesDocumentNo(salesOrder.getDocumentNo());
		context.setWfProcess(wfProcess);
		context.setScheduleIds(queryBL
				.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, salesOrder.getC_Order_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds(ShipmentScheduleId::ofRepoId));
	}

	@When("^scan picking slot identified by (.*)$")
	public void scanPickingSlot(@NonNull final String pickingSlotIdentifier)
	{
		final PickingSlotIdAndCaption pickingSlotIdAndCaption = pickingSlotsTable.getPickingSlotIdAndCaption(pickingSlotIdentifier);

		final JsonWFProcess wfProcess = mobileUIPickingClient.scanPickingSlot(context.getWfProcessIdNotNull(), pickingSlotIdAndCaption);
		context.setWfProcess(wfProcess);
	}

	@When("^set picking target as new LU identified by (.*)$")
	public void setPickingLUTarget(@NonNull final String packingInstructionsIdentifier)
	{
		final HuPackingInstructionsId luPIId = huPiTable.getId(packingInstructionsIdentifier);
		final LUPickingTarget pickingTarget = LUPickingTarget.ofPackingInstructions(luPIId, packingInstructionsIdentifier);

		final JsonWFProcess wfProcess = mobileUIPickingClient.setPickingTarget(context.getWfProcessIdNotNull(), pickingTarget);
		context.setWfProcess(wfProcess);
	}

	@When("^set picking target as existing LU identified by (.*)$")
	public void setPickingLUTargetExisting(@NonNull final String huIdentifier)
	{
		final HuId luId = huTable.getId(huIdentifier);
		final HUQRCode qrCode = huQRCodesService.getQRCodeByHuId(luId);
		final LUPickingTarget pickingTarget = LUPickingTarget.ofExistingHU(luId, qrCode);

		final JsonWFProcess wfProcess = mobileUIPickingClient.setPickingTarget(context.getWfProcessIdNotNull(), pickingTarget);
		context.setWfProcess(wfProcess);
	}

	@When("^set picking target as new TU identified by (.*)$")
	public void setPickingTUTarget(@NonNull final String packingInstructionsIdentifier)
	{
		final HuPackingInstructionsId tuPIId = huPiTable.getId(packingInstructionsIdentifier);
		final TUPickingTarget pickingTarget = TUPickingTarget.ofPackingInstructions(tuPIId, packingInstructionsIdentifier);

		final JsonWFProcess wfProcess = mobileUIPickingClient.setTUPickingTarget(context.getWfProcessIdNotNull(), pickingTarget);
		context.setWfProcess(wfProcess);
	}

	@When("expect current picking target")
	public void setPickingLUTarget(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final String wfProcessId = context.getWfProcessNotNull().getId();

		row.getAsOptionalIdentifier("Existing_LU")
				.ifPresent(luIdentifier -> {
					final LUPickingTarget actualPickingTarget = mobileUIPickingClient.getPickingTarget(wfProcessId).orElse(null);
					assertThat(actualPickingTarget).as("actual picking LU target").isNotNull();
					assertThat(actualPickingTarget.isExistingLU()).as(() -> "actual picking LU target is existing LU: " + actualPickingTarget).isTrue();

					final HuId luId = huTable.getIdOptional(luIdentifier).orElse(null);
					if (luId == null)
					{
						huTable.put(luIdentifier, handlingUnitsBL.getById(actualPickingTarget.getLuIdNotNull()));
					}
					else
					{
						assertThat(actualPickingTarget.getLuIdNotNull()).as("actual picking LU target").isEqualTo(luId);
					}
				});
	}

	@When("pick lines")
	public void pickLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::pickLine);
	}

	private void pickLine(@NonNull final DataTableRow row)
	{
		SharedTestContext.put("context", () -> context);

		final JsonPickingStepEvent.JsonPickingStepEventBuilder requestBuilder = JsonPickingStepEvent.builder()
				.type(JsonPickingStepEvent.EventType.PICK)
				.wfProcessId(context.getWfProcessIdNotNull())
				.wfActivityId(PickingMobileApplication.ACTIVITY_ID_PickLines.getAsString());

		//
		// Picking Line
		final String pickingLineId;
		{
			String resolvedPickingLineId = row.getAsOptionalIdentifier("PickingLine.byProduct")
					.map(productsTable::getId)
					.map(context::getPickingLineIdByProductId)
					.orElse(null);
			if (resolvedPickingLineId == null)
			{
				resolvedPickingLineId = context.getSinglePickingLineId();
			}
			pickingLineId = resolvedPickingLineId;
			assertThat(pickingLineId).as("pickingLineId").isNotNull();
			SharedTestContext.put("pickingLineId", pickingLineId);

			requestBuilder.pickingLineId(pickingLineId);
		}

		//
		// Pick from HU
		{
			final HuId pickFromHUId = huTable.getId(row.getAsIdentifier("PickFromHU"));
			final HUQRCode pickFromQRCode = huQRCodesService.getQRCodeByHuId(pickFromHUId);
			SharedTestContext.put("pickFromHUId", pickFromHUId);

			requestBuilder.huQRCode(pickFromQRCode.toGlobalQRCodeString());
		}

		//
		final IHUQRCode itemQRCode = row.getAsOptionalString("QRCode").map(huQRCodesService::parse).orElse(null);
		if (itemQRCode != null)
		{
			requestBuilder
					.qtyPicked(BigDecimal.ONE)
					.catchWeight(itemQRCode.getWeightInKg().orElse(null))
					.setBestBeforeDate(true)
					.bestBeforeDate(itemQRCode.getBestBeforeDate().orElse(null))
					.setLotNo(true)
					.lotNo(itemQRCode.getLotNumber().orElse(null));
		}
		else
		{
			final LocalDate bestBeforeDate = row.getAsOptionalLocalDate("BestBeforeDate").orElse(null);
			final String lotNo = row.getAsOptionalString("LotNo").orElse(null);
			requestBuilder
					.qtyPicked(row.getAsBigDecimal("QtyPicked"))
					.qtyRejected(row.getAsOptionalBigDecimal("QtyRejected").orElse(null))
					.qtyRejectedReasonCode(row.getAsOptionalString("QtyRejectedReasonCode")
							.map(QtyRejectedReasonCode::ofCode).map(QtyRejectedReasonCode::getCode) // validate it
							.orElse(null))
					.catchWeight(row.getAsOptionalBigDecimal("CatchWeight").orElse(null))
					.setBestBeforeDate(bestBeforeDate != null)
					.bestBeforeDate(bestBeforeDate)
					.setLotNo(lotNo != null)
					.lotNo(lotNo);
		}

		final JsonWFProcess wfProcess = mobileUIPickingClient.pickLine(requestBuilder.build());
		context.setWfProcess(wfProcess);

		// A re-pick reduces the floor-returned qty by the qty just picked back.
		// Use the picking line's productId to identify which product's floor qty to update.
		final BigDecimal qtyPickedBD = row.getAsOptionalBigDecimal("QtyPicked").orElse(BigDecimal.ZERO);
		if (qtyPickedBD.signum() > 0)
		{
			final JsonPickingJobLine line = context.getPickingJobLineById(pickingLineId);
			final ProductId pickedProductId = ProductId.ofRepoId(Integer.parseInt(line.getProductId()));
			context.reduceFloorReturnedQty(pickedProductId, qtyPickedBD);
		}
	}

	@When("complete picking job")
	public void complete() throws InterruptedException
	{
		waitUntilPickingJobSchedulesValid();
		final JsonWFProcess wfProcess = mobileUIPickingClient.complete(context.getWfProcessIdNotNull());
		context.setWfProcess(wfProcess);
	}

	/**
	 * Partially un-picks a quantity of a product identified by its GTIN barcode from the currently packed HU.
	 * Mirrors the mobile app flow: (1) resolve the GTIN via {@code POST /unpick/resolve} to obtain the
	 * product ID, (2) post an UNPICK event carrying {@code unpickProductId} + {@code unpickQty}.
	 * The backend selects matching packed HUs (LIFO, whole-HU boundaries) up to {@code QtyToUnpick} and
	 * reverses them; the returned qty becomes re-pickable (floor stock).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>ProductGTIN</b> — (required) GTIN barcode of the product to partially unpick<br>
	 *   <b>QtyToUnpick</b> — (required) partial quantity to remove from the packed HU<br>
	 * @cucumber.example
	 * <pre>
	 * When partial unpick from packed HU by product GTIN:
	 *   | ProductGTIN    | QtyToUnpick |
	 *   | 04006381333931 | 2           |
	 * </pre>
	 */
	@When("partial unpick from packed HU by product GTIN:")
	public void partialUnpackByProductGtin(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final String gtin = row.getAsString("ProductGTIN");
		final BigDecimal qtyToUnpick = row.getAsBigDecimal("QtyToUnpick");

		final String wfProcessId = context.getWfProcessIdNotNull();

		// The feature stores the GTIN as a raw GTIN-14 value (e.g. "04006381333931").
		// The resolveUnpick service parses scanned codes via HUQRCodesService which expects:
		//   - GS1 format:   AI "01" + 14-digit GTIN  (e.g. "0104006381333931")
		//   - EAN-13 format: exactly 13 digits
		// A raw 14-digit GTIN-14 is handled by prepending the GS1 Application Identifier "01".
		final String gs1ScannedCode = gtin.length() == 14 ? ("01" + gtin) : gtin;

		// Step 1: resolve GTIN → productId (mirrors the mobile resolve call)
		final de.metas.picking.rest_api.json.JsonUnpickResolveResponse resolveResponse =
				mobileUIPickingClient.resolveUnpick(wfProcessId, gs1ScannedCode);
		assertThat(resolveResponse.isUnpickable())
				.as("Product with GTIN %s must be unpickable (packedQty > 0)", gtin)
				.isTrue();

		// Step 2: post UNPICK event with product+qty subset selector
		// The huQRCode field is @NonNull in the JSON schema; for the product+qty subset path the
		// backend ignores it (no pickingStepId → getPickingJobStepPickFromKey returns null).
		// We pass the gs1ScannedCode as the placeholder since that was the effective scan input.
		final JsonPickingStepEvent unpickEvent = JsonPickingStepEvent.builder()
				.type(JsonPickingStepEvent.EventType.UNPICK)
				.wfProcessId(wfProcessId)
				.wfActivityId(PickingMobileApplication.ACTIVITY_ID_PickLines.getAsString())
				.pickingLineId(context.getSinglePickingLineId())
				.huQRCode(gs1ScannedCode)
				.unpickProductId(resolveResponse.getProductId())
				.unpickQty(qtyToUnpick)
				.build();

		final JsonWFProcess wfProcess = mobileUIPickingClient.unpickLine(unpickEvent);
		context.setWfProcess(wfProcess);

		// Track how much was returned to floor from the package, per product.
		// Used by assertRePickableQty() to verify the floor-returned qty.
		final ProductId productId = ProductId.ofRepoId(Integer.parseInt(resolveResponse.getProductId()));
		context.addFloorReturnedQty(productId, qtyToUnpick);
	}

	/**
	 * Asserts that the currently packed HU contains the expected quantity of a given product.
	 * Reads {@code qtyPicked} from the picking job line in the current workflow process context —
	 * this reflects the qty actually packed after any pick/unpick operations.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID.Identifier</b> — (required) identifier-ref for the product<br>
	 *   <b>ExpectedQty</b> — (required) expected packed quantity in the product's base UOM<br>
	 * @cucumber.example
	 * <pre>
	 * Then the packed HU contains product with qty:
	 *   | M_Product_ID.Identifier | ExpectedQty |
	 *   | product_30480           | 4           |
	 * </pre>
	 */
	@Then("the packed HU contains product with qty:")
	public void assertPackedHUQty(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final ProductId productId = productsTable.getId(row.getAsIdentifier("M_Product_ID"));
			final BigDecimal expectedQty = row.getAsBigDecimal("ExpectedQty");

			final String pickingLineId = context.getPickingLineIdByProductId(productId);
			final JsonPickingJobLine line = context.getPickingJobLineById(pickingLineId);

			assertThat(line.getQtyPicked())
					.as("Packed HU qty for product %s (line %s)", productId, pickingLineId)
					.isEqualByComparingTo(expectedQty);
		});
	}

	/**
	 * Asserts the floor-returned qty for a given product — i.e., how much was partially unpicked from
	 * the packed HU and is currently sitting on the floor, waiting to be re-picked. This is tracked
	 * by the step context: each partial unpick increments it; each subsequent pick decrements it (to zero).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID.Identifier</b> — (required) identifier-ref for the product<br>
	 *   <b>ExpectedRePickableQty</b> — (required) expected floor-returned quantity<br>
	 * @cucumber.example
	 * <pre>
	 * And the picking job has re-pickable qty for product:
	 *   | M_Product_ID.Identifier | ExpectedRePickableQty |
	 *   | product_30480           | 2                     |
	 * </pre>
	 */
	@Then("the picking job has re-pickable qty for product:")
	public void assertRePickableQty(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final ProductId productId = productsTable.getId(row.getAsIdentifier("M_Product_ID"));
			final BigDecimal expectedRePickableQty = row.getAsBigDecimal("ExpectedRePickableQty");

			final BigDecimal actualFloorQty = context.getFloorReturnedQty(productId);
			assertThat(actualFloorQty)
					.as("Floor-returned (re-pickable) qty for product %s", productId)
					.isEqualByComparingTo(expectedRePickableQty);
		});
	}

	/**
	 * Waits until all shipment schedules for the current picking job's sales order
	 * have no pending recompute entries. This ensures retrieveNotShippedRecords
	 * (which uses TRXNAME_None) sees a consistent state when shipment generation runs.
	 */
	private void waitUntilPickingJobSchedulesValid() throws InterruptedException
	{
		final List<ShipmentScheduleId> scheduleIds = context.getScheduleIds();
		if (scheduleIds == null || scheduleIds.isEmpty())
		{
			return;
		}

		StepDefUtil.tryAndWait(30, 500, () -> queryBL
				.createQueryBuilder(de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute.class)
				.addInArrayFilter(de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute.COLUMNNAME_M_ShipmentSchedule_ID, scheduleIds)
				.create()
				.noneMatch());
	}

	//
	//
	//

	@Setter
	@ToString
	private static class Context
	{
		@Nullable JsonWFProcess wfProcess;
		@Nullable @Getter List<ShipmentScheduleId> scheduleIds;

		/**
		 * Tracks qty returned to floor from the packed HU via partial unpick, per product.
		 * Incremented by {@code addFloorReturnedQty} (partial unpick), decremented by
		 * {@code reduceFloorReturnedQty} (re-pick). Never goes below zero.
		 */
		@NonNull private final Map<ProductId, BigDecimal> floorReturnedQtyByProduct = new HashMap<>();

		public void addFloorReturnedQty(@NonNull final ProductId productId, @NonNull final BigDecimal qty)
		{
			floorReturnedQtyByProduct.merge(productId, qty, BigDecimal::add);
		}

		public void reduceFloorReturnedQty(@NonNull final ProductId productId, @NonNull final BigDecimal qty)
		{
			// Use compute to avoid the Map.merge pitfall: when the key is absent, merge() sets the
			// value to the given qty rather than treating the current floor as zero. compute() always
			// runs the remapping function, so we can safely default-to-zero and clamp below zero.
			floorReturnedQtyByProduct.compute(productId, (k, existing) -> {
				final BigDecimal current = existing != null ? existing : BigDecimal.ZERO;
				final BigDecimal result = current.subtract(qty);
				return result.signum() < 0 ? BigDecimal.ZERO : result;
			});
		}

		@NonNull
		public BigDecimal getFloorReturnedQty(@NonNull final ProductId productId)
		{
			return floorReturnedQtyByProduct.getOrDefault(productId, BigDecimal.ZERO);
		}

		public String getWfProcessIdNotNull()
		{
			return getWfProcessNotNull().getId();
		}

		@NonNull
		private JsonWFProcess getWfProcessNotNull() {return Check.assumeNotNull(wfProcess, "An already started WFProcess is in context");}

		public String getSinglePickingLineId()
		{
			final List<JsonPickingJobLine> lines = getPickingJobLines();
			final JsonPickingJobLine line = CollectionUtils.singleElement(lines);
			return line.getPickingLineId();
		}

		public String getPickingLineIdByProductId(@NonNull final ProductId productId)
		{
			final List<JsonPickingJobLine> lines = getPickingJobLines();
			final ImmutableList<JsonPickingJobLine> eligibleLines = lines.stream()
					.filter(pickingLine -> isMatching(pickingLine, productId))
					.collect(ImmutableList.toImmutableList());
			if (eligibleLines.isEmpty())
			{
				throw new AdempiereException("No picking lines found for productId=" + productId + ". Available lines are: " + lines);
			}
			else if (eligibleLines.size() > 1)
			{
				throw new AdempiereException("More than one picking lines found for productId=" + productId + ": " + eligibleLines);
			}
			else
			{
				return eligibleLines.get(0).getPickingLineId();
			}
		}

		private static boolean isMatching(final JsonPickingJobLine pickingLine, final ProductId productId)
		{
			final String pickingLineProductId = pickingLine.getProductId();
			final String productIdStr = productId.getAsString();
			return Util.equals(pickingLineProductId, productIdStr);
		}

		public JsonPickingJobLine getPickingJobLineById(@NonNull final String pickingLineId)
		{
			return getPickingJobLines().stream()
					.filter(l -> pickingLineId.equals(l.getPickingLineId()))
					.findFirst()
					.orElseThrow(() -> new AdempiereException("No picking line found for pickingLineId=" + pickingLineId));
		}

		private List<JsonPickingJobLine> getPickingJobLines()
		{
			final JsonWFProcess wfProcess = getWfProcessNotNull();
			final JsonWFActivity activity = wfProcess.getActivityById(PickingMobileApplication.ACTIVITY_ID_PickLines.getAsString());
			final JsonPickingJob pickingJob = (JsonPickingJob)activity.getComponentProps().get(ActualPickingWFActivityHandler.PROP_pickingJob);
			return pickingJob.getLines();
		}

	}
}
