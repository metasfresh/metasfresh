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
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.grai.HUGraiSnapshot;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
public class MobileUI_Picking_StepDef
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	@NonNull private final HUGraiService huGraiService = SpringContextHolder.instance.getBean(HUGraiService.class);
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

	/**
	 * Scans the source HU for a PRODUCT-aggregation picking job.
	 * <p>
	 * This step drives the {@code scanPickFromHU} workflow activity, which is the first activity in the
	 * {@code PRODUCT} aggregation flow ({@code ScanPickFromHU → ScanPickingSlot → PickLines → Complete}).
	 * It is absent from {@code SALES_ORDER}/{@code DELIVERY_LOCATION} flows.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>huIdentifier</b> — (required, identifier-ref) HU to scan as the pick-from source
	 * @cucumber.example
	 * <pre>
	 * When scan pick from HU identified by pickFromLU
	 * </pre>
	 */
	@When("^scan pick from HU identified by (.*)$")
	public void scanPickFromHU(@NonNull final String huIdentifier)
	{
		final HuId huId = huTable.getId(huIdentifier);
		final HUQRCode qrCode = huQRCodesService.getQRCodeByHuId(huId);

		final JsonWFProcess wfProcess = mobileUIPickingClient.scanPickFromHU(context.getWfProcessIdNotNull(), qrCode);
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

	/**
	 * Finds the materialised LU picking target from the first picking job line and registers it in the
	 * HU step-def data under the given identifier. Used for PRODUCT-aggregation jobs where the LU
	 * target is stored at line level (not header level).
	 * <p>
	 * For SALES_ORDER / DELIVERY_LOCATION aggregation, use {@code expect current picking target} instead.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Existing_LU</b> — (required) identifier to register the materialised line-level LU under
	 * @cucumber.example
	 * <pre>
	 * And expect line picking target
	 *   | Existing_LU |
	 *   | pickedLU    |
	 * </pre>
	 */
	@When("expect line picking target")
	public void expectLinePickingTarget(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final String wfProcessId = context.getWfProcessNotNull().getId();

		row.getAsOptionalIdentifier("Existing_LU")
				.ifPresent(luIdentifier -> {
					final LUPickingTarget actualPickingTarget = mobileUIPickingClient.getFirstLineLuPickingTarget(wfProcessId).orElse(null);
					assertThat(actualPickingTarget).as("line-level LU picking target").isNotNull();
					assertThat(actualPickingTarget.isExistingLU()).as(() -> "line-level LU picking target is existing LU: " + actualPickingTarget).isTrue();

					final HuId luId = huTable.getIdOptional(luIdentifier).orElse(null);
					if (luId == null)
					{
						huTable.put(luIdentifier, handlingUnitsBL.getById(actualPickingTarget.getLuIdNotNull()));
					}
					else
					{
						assertThat(actualPickingTarget.getLuIdNotNull()).as("line-level LU picking target").isEqualTo(luId);
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
		{
			String pickingLineId = row.getAsOptionalIdentifier("PickingLine.byProduct")
					.map(productsTable::getId)
					.map(context::getPickingLineIdByProductId)
					.orElse(null);
			if (pickingLineId == null)
			{
				pickingLineId = context.getSinglePickingLineId();
			}
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
	}

	/**
	 * Atomic pick: sends a single PICK event whose {@code setGrais=true} and {@code graiCodes} are
	 * populated with the GRAIs listed in the DataTable. The GRAIs are stamped onto the picked TUs
	 * inside the same pick transaction.
	 * <p>
	 * Each DataTable row represents one GRAI to capture. All rows must share the same
	 * {@code PickingLine.byProduct} and {@code PickFromHU} values (they are all sent in one
	 * atomic pick event); {@code QtyPicked} is taken from the first row.
	 *
	 * <b>@cucumber.columns</b>
	 * <ul>
	 *   <li><b>PickingLine.byProduct</b> — (optional) product identifier to resolve the picking line</li>
	 *   <li><b>PickFromHU</b> — (required) HU identifier to scan</li>
	 *   <li><b>QtyPicked</b> — (required, first row only) number of TUs to pick</li>
	 *   <li><b>GRAI</b> — (required) the GRAI code for this row; one row per GRAI</li>
	 * </ul>
	 *
	 * <b>@cucumber.example</b>
	 * <pre>
	 * And pick line with GRAIs:
	 *   | PickingLine.byProduct | PickFromHU | QtyPicked | GRAI                 |
	 *   | product               | pickFromLU | 3         | 7613204.00307.000001 |
	 *   |                       |            |           | 7613204.00307.000002 |
	 *   |                       |            |           | 7613204.00307.000003 |
	 * </pre>
	 *
	 * @param dataTable each row: PickingLine.byProduct (opt), PickFromHU (req), QtyPicked (req on row 1), GRAI (req)
	 */
	@When("pick line with GRAIs:")
	public void pickLinesWithGrais(@NonNull final DataTable dataTable)
	{
		SharedTestContext.put("context", () -> context);

		final List<DataTableRow> rows = DataTableRows.of(dataTable).toList();
		assertThat(rows).as("pick line with GRAIs: at least one row expected").isNotEmpty();

		final DataTableRow firstRow = rows.get(0);

		final ImmutableList<String> graiCodes = rows.stream()
				.map(row -> row.getAsString("GRAI"))
				.collect(ImmutableList.toImmutableList());

		final JsonPickingStepEvent.JsonPickingStepEventBuilder requestBuilder = JsonPickingStepEvent.builder()
				.type(JsonPickingStepEvent.EventType.PICK)
				.wfProcessId(context.getWfProcessIdNotNull())
				.wfActivityId(PickingMobileApplication.ACTIVITY_ID_PickLines.getAsString());

		//
		// Picking Line (from first row)
		{
			String pickingLineId = firstRow.getAsOptionalIdentifier("PickingLine.byProduct")
					.map(productsTable::getId)
					.map(context::getPickingLineIdByProductId)
					.orElse(null);
			if (pickingLineId == null)
			{
				pickingLineId = context.getSinglePickingLineId();
			}
			assertThat(pickingLineId).as("pickingLineId").isNotNull();
			requestBuilder.pickingLineId(pickingLineId);
		}

		//
		// Pick from HU (from first row)
		{
			final HuId pickFromHUId = huTable.getId(firstRow.getAsIdentifier("PickFromHU"));
			final HUQRCode pickFromQRCode = huQRCodesService.getQRCodeByHuId(pickFromHUId);
			requestBuilder.huQRCode(pickFromQRCode.toGlobalQRCodeString());
		}

		//
		// Qty + GRAIs (qty from first row)
		requestBuilder
				.qtyPicked(firstRow.getAsBigDecimal("QtyPicked"))
				.setGrais(true)
				.graiCodes(graiCodes);

		final JsonWFProcess wfProcess = mobileUIPickingClient.pickLineWithGrais(requestBuilder.build());
		context.setWfProcess(wfProcess);
	}

	/**
	 * Assert that the TUs on the given LU carry exactly the expected GRAIs.
	 * Verifies that the atomic pick event's {@code graiCodes} were stamped onto the picked TUs
	 * inside the pick transaction.
	 *
	 * <b>@cucumber.columns</b>
	 * <ul>
	 *   <li><b>GRAI</b> — (required) expected GRAI code; one row per expected GRAI</li>
	 * </ul>
	 *
	 * @param luIdentifier identifier of the picked LU (resolved via the HU step-def table)
	 * @param dataTable    one {@code GRAI} column, one row per expected GRAI on the LU's TUs
	 */
	@Then("^the TUs on picked LU identified by (.*) carry GRAIs$")
	public void assertPickedLUTusCarryGrais(@NonNull final String luIdentifier, @NonNull final DataTable dataTable)
	{
		final HuId luId = huTable.getId(luIdentifier);
		final ImmutableList<String> expectedGraiCodes = DataTableRows.of(dataTable).stream()
				.map(row -> row.getAsString("GRAI"))
				.collect(ImmutableList.toImmutableList());

		final HUGraiSnapshot snapshot = huGraiService.getSnapshot(luId).orElseThrow();
		final GRAISet actualGrais = snapshot.getAllGrais();
		final ImmutableList<String> actualGraiStrings = actualGrais.stream()
				.map(Object::toString)
				.collect(ImmutableList.toImmutableList());

		assertThat(actualGraiStrings)
				.as("GRAIs on TUs of picked LU %s", luId)
				.containsExactlyInAnyOrderElementsOf(expectedGraiCodes);
	}

	@When("complete picking job")
	public void complete() throws InterruptedException
	{
		waitUntilPickingJobSchedulesValid();
		final JsonWFProcess wfProcess = mobileUIPickingClient.complete(context.getWfProcessIdNotNull());
		context.setWfProcess(wfProcess);
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

	/**
	 * Complete the picking job and assert it is BLOCKED by the completion-time GRAI validator.
	 * <p>
	 * <b>@cucumber.columns</b>
	 * <ul>
	 *   <li><b>ErrorCode</b> — (required) the expected {@link AdempiereException#getErrorCode()} the completion
	 *       must fail with (e.g. {@code GRAI_COUNT_MISMATCH}).</li>
	 * </ul>
	 *
	 * @param dataTable a single row with the expected {@code ErrorCode}
	 */
	@Then("^complete picking job expecting error$")
	public void completePickingJobExpectingError(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final String expectedErrorCode = row.getAsString("ErrorCode");

		assertThatThrownBy(() -> mobileUIPickingClient.complete(context.getWfProcessIdNotNull()))
				.as("completing a GRAIRequired picking job with fewer GRAIs than TUs must be blocked")
				.isInstanceOfSatisfying(AdempiereException.class, ex ->
						assertThat(ex.getErrorCode()).isEqualTo(expectedErrorCode));
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

		private List<JsonPickingJobLine> getPickingJobLines()
		{
			final JsonWFProcess wfProcess = getWfProcessNotNull();
			final JsonWFActivity activity = wfProcess.getActivityById(PickingMobileApplication.ACTIVITY_ID_PickLines.getAsString());
			final JsonPickingJob pickingJob = (JsonPickingJob)activity.getComponentProps().get(ActualPickingWFActivityHandler.PROP_pickingJob);
			return pickingJob.getLines();
		}

	}
}
