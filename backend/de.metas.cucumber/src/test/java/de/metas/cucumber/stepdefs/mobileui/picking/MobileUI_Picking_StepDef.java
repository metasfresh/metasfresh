package de.metas.cucumber.stepdefs.mobileui.picking;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
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
import de.metas.cucumber.stepdefs.shipper.Carrier_Product_StepDefData;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.shipping.CarrierProductId;
import de.metas.workflow.rest_api.model.WFProcessId;
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
import de.metas.i18n.AdMessageKey;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

@RequiredArgsConstructor
public class MobileUI_Picking_StepDef
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	@NonNull private final HUGraiService huGraiService = SpringContextHolder.instance.getBean(HUGraiService.class);
	@NonNull private final PickingJobRestService pickingJobRestService = SpringContextHolder.instance.getBean(PickingJobRestService.class);
	@NonNull private final MobileUIPickingClient mobileUIPickingClient = new MobileUIPickingClient();

	@NonNull private final M_Product_StepDefData productsTable;
	@NonNull private final C_Order_StepDefData ordersTable;
	@NonNull private final PickingSlot_StepDefData pickingSlotsTable;
	@NonNull private final M_HU_StepDefData huTable;
	@NonNull private final M_HU_PI_StepDefData huPiTable;
	@NonNull private final Carrier_Product_StepDefData carrierProductsTable;

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

	/**
	 * Asserts the carrier-advise display flags the mobile picking UI renders for the current job line,
	 * across the pick-to shapes (LU target, TU target, or CU-direct / no-target pick) — exactly the
	 * line-level {@code carrierAdvise*} fields {@code SelectCurrentLUTUButtons} reads to render the advise button.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>target</b> — (required) names the pick-to shape being asserted (LU/TU/none); the flags are line-level<br>
	 *   <b>available</b> — (required) expected line carrierAdviseAvailable<br>
	 *   <b>readOnly</b> — (required) expected line carrierAdviseReadOnly<br>
	 *   <b>carrierProductCaption</b> — (optional) expected line carrier product caption<br>
	 *   <b>M_Product_ID</b> — (optional, identifier-ref) select the line by product when the job has several
	 *   lines with divergent carriers; omit for a single-line job (asserts the only line)<br>
	 */
	@Then("expect current picking job line carrier advise")
	public void expectCarrierAdvise(@NonNull final DataTable dataTable)
	{
		// Re-fetch the process freshly (as the mobile UI does) so the post-pick line-level carrier-advise
		// flags are reflected, not the stale post-event response.
		context.setWfProcess(mobileUIPickingClient.getWFProcessById(context.getWfProcessIdNotNull()));
		DataTableRows.of(dataTable).forEach(this::assertCarrierAdvise);
	}

	private void assertCarrierAdvise(@NonNull final DataTableRow row)
	{
		final String target = row.getAsString("target");
		final boolean expectedAvailable = row.getAsBoolean("available");
		final boolean expectedReadOnly = row.getAsBoolean("readOnly");
		final Optional<String> expectedCaption = row.getAsOptionalString("carrierProductCaption");

		// Flags are always at line level; `target` just names the pick-to shape being asserted. When a scenario
		// has several lines with divergent per-line carriers, select the line by M_Product_ID; otherwise (single
		// line) assert the only line.
		final JsonPickingJobLine line = row.getAsOptionalIdentifier("M_Product_ID")
				.map(productsTable::getId)
				.map(context::getPickingJobLineByProductId)
				.orElseGet(() -> CollectionUtils.singleElement(context.getPickingJobLines()));

		assertThat(line.isCarrierAdviseAvailable()).as("carrierAdviseAvailable for target %s", target).isEqualTo(expectedAvailable);
		assertThat(line.isCarrierAdviseReadOnly()).as("carrierAdviseReadOnly for target %s", target).isEqualTo(expectedReadOnly);
		expectedCaption.ifPresent(caption -> assertThat(line.getCarrierProductCaption())
				.as("carrierProductCaption for target %s", target).isEqualTo(caption));
	}

	/**
	 * Asserts the CURRENT picking job HEADER carrier-advise DISPLAY flags — the job-level flags the mobile UI
	 * reads for the header-view advise button (populated from {@code PackedHUCarrierAdviseService.resolveInfo}).
	 * Re-fetches the process freshly (as the mobile UI does) in a SINGLE shot — it does NOT poll. Assumes a
	 * preceding {@code expect current picking job:} / {@code expect current picking job lines:} step has
	 * already polled the async pick state to settle; do not use it as the first assertion after an async
	 * mutation, or it may race.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>available</b> — (required) expected header carrierAdviseAvailable<br>
	 *   <b>readOnly</b> — (required) expected header carrierAdviseReadOnly<br>
	 *   <b>carrierProductCaption</b> — (optional) expected header carrier product caption<br>
	 * @cucumber.example
	 * <pre>
	 * Then expect current picking job header carrier advise
	 *   | available | readOnly |
	 *   | true      | false    |
	 * </pre>
	 */
	@Then("expect current picking job header carrier advise")
	public void expectHeaderCarrierAdvise(@NonNull final DataTable dataTable)
	{
		context.setWfProcess(mobileUIPickingClient.getWFProcessById(context.getWfProcessIdNotNull()));
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final JsonPickingJob pickingJob = context.getPickingJob();

		assertThat(pickingJob.isCarrierAdviseAvailable())
				.as("carrierAdviseAvailable (job header)").isEqualTo(row.getAsBoolean("available"));
		assertThat(pickingJob.isCarrierAdviseReadOnly())
				.as("carrierAdviseReadOnly (job header)").isEqualTo(row.getAsBoolean("readOnly"));
		row.getAsOptionalString("carrierProductCaption").ifPresent(caption -> assertThat(pickingJob.getCarrierProductCaption())
				.as("carrierProductCaption (job header)").isEqualTo(caption));
	}

	/**
	 * Asserts the CURRENT picking job HEADER on the loaded {@link PickingJob} POJO — the exact input
	 * {@code PackedHUCarrierAdviseService.resolveInfo} and the JSON converter consume, not the raw model.
	 * Polls until the expected state settles, so an async pick commit cannot race the assertion; if the
	 * state never materialises the poll times out red (it does not mask the failure).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>HasLuTarget</b> — (optional) expected presence of the header LU pick target<br>
	 *   <b>HasTuTarget</b> — (optional) expected presence of the header TU pick target<br>
	 *   <b>Carrier_Product_ID</b> — (optional, identifier-ref, null-allowed) expected header carrier product<br>
	 *   <b>IsCarrierAdviseReadOnly</b> — (optional) expected header carrier-advise read-only flag<br>
	 * @cucumber.depends Carrier_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then expect current picking job:
	 *   | HasLuTarget | Carrier_Product_ID | IsCarrierAdviseReadOnly |
	 *   | Y           |                    | N                       |
	 * </pre>
	 */
	@Then("expect current picking job:")
	public void expectCurrentPickingJob(@NonNull final DataTable dataTable) throws InterruptedException
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final PickingJob[] settled = new PickingJob[1];
		StepDefUtil.tryAndWait(30, 500, () -> {
			settled[0] = loadCurrentPickingJob();
			return catchThrowable(() -> assertPickingJobHeader(settled[0], row)) == null;
		});
		assertPickingJobHeader(settled[0], row);
	}

	private void assertPickingJobHeader(@NonNull final PickingJob pickingJob, @NonNull final DataTableRow row)
	{
		if (row.getAsOptionalString("HasLuTarget").isPresent())
		{
			assertThat(pickingJob.getLuPickingTarget(null).isPresent())
					.as("header hasLuTarget").isEqualTo(row.getAsBoolean("HasLuTarget"));
		}
		if (row.getAsOptionalString("HasTuTarget").isPresent())
		{
			assertThat(pickingJob.getTuPickingTarget(null).isPresent())
					.as("header hasTuTarget").isEqualTo(row.getAsBoolean("HasTuTarget"));
		}
		row.getAsOptionalIdentifier("Carrier_Product_ID").ifPresent(identifier -> assertThat(pickingJob.getCarrierProductId())
				.as("header carrierProductId").isEqualTo(resolveCarrierProductIdOrNull(identifier)));
		if (row.getAsOptionalString("IsCarrierAdviseReadOnly").isPresent())
		{
			assertThat(pickingJob.isCarrierAdviseReadOnly())
					.as("header isCarrierAdviseReadOnly").isEqualTo(row.getAsBoolean("IsCarrierAdviseReadOnly"));
		}
	}

	/**
	 * Asserts the CURRENT picking job LINES on the loaded {@link PickingJob} POJO (matched by product) —
	 * the persisted per-line carrier state {@code resolveInfo} aggregates, not the raw model. Polls until
	 * the expected state settles (same barrier rationale as {@code expect current picking job:}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the line's product<br>
	 *   <b>Carrier_Product_ID</b> — (optional, identifier-ref, null-allowed) expected line carrier product<br>
	 *   <b>IsCarrierAdviseManual</b> — (optional) expected line manual flag<br>
	 *   <b>IsCarrierAdviseReadOnly</b> — (optional) expected line carrier-advise read-only flag<br>
	 * @cucumber.depends M_Product_StepDefData, Carrier_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then expect current picking job lines:
	 *   | M_Product_ID | Carrier_Product_ID | IsCarrierAdviseManual |
	 *   | product      | cp1                | N                     |
	 *   | product_2    | cp2                | N                     |
	 * </pre>
	 */
	@Then("expect current picking job lines:")
	public void expectCurrentPickingJobLines(@NonNull final DataTable dataTable) throws InterruptedException
	{
		final DataTableRows rows = DataTableRows.of(dataTable);
		final PickingJob[] settled = new PickingJob[1];
		StepDefUtil.tryAndWait(30, 500, () -> {
			settled[0] = loadCurrentPickingJob();
			return catchThrowable(() -> assertPickingJobLines(settled[0], rows)) == null;
		});
		assertPickingJobLines(settled[0], rows);
	}

	private void assertPickingJobLines(@NonNull final PickingJob pickingJob, @NonNull final DataTableRows rows)
	{
		rows.forEach(row -> {
			final ProductId productId = productsTable.getId(row.getAsIdentifier("M_Product_ID"));
			final ImmutableList<PickingJobLine> matching = pickingJob.getLines().stream()
					.filter(line -> productId.equals(line.getProductId()))
					.collect(ImmutableList.toImmutableList());
			final PickingJobLine line = CollectionUtils.singleElement(matching);

			row.getAsOptionalIdentifier("Carrier_Product_ID").ifPresent(identifier -> assertThat(line.getCarrierProductId())
					.as("line %s carrierProductId", productId).isEqualTo(resolveCarrierProductIdOrNull(identifier)));
			if (row.getAsOptionalString("IsCarrierAdviseManual").isPresent())
			{
				assertThat(line.isManual())
						.as("line %s isCarrierAdviseManual", productId).isEqualTo(row.getAsBoolean("IsCarrierAdviseManual"));
			}
			if (row.getAsOptionalString("IsCarrierAdviseReadOnly").isPresent())
			{
				assertThat(line.isCarrierAdviseReadOnly())
						.as("line %s isCarrierAdviseReadOnly", productId).isEqualTo(row.getAsBoolean("IsCarrierAdviseReadOnly"));
			}
		});
	}

	@NonNull
	private PickingJob loadCurrentPickingJob()
	{
		final PickingJobId pickingJobId = WFProcessId.ofString(context.getWfProcessIdNotNull()).getRepoId(PickingJobId::ofRepoId);
		return pickingJobRestService.getPickingJobById(pickingJobId);
	}

	@Nullable
	private CarrierProductId resolveCarrierProductIdOrNull(@NonNull final StepDefDataIdentifier identifier)
	{
		return identifier.isNullPlaceholder()
				? null
				: identifier.lookupNotNullIn(carrierProductsTable).getId();
	}

	/**
	 * Triggers the mobile packing re-advise for the current picking job (the "Advise carrier" button →
	 * {@code POST /job/{wfProcessId}/target/advise}). Re-advises the packed HUs' shipment schedules against
	 * the actually-packed HU, regardless of their current advising status (Manual schedules are skipped).
	 *
	 * @cucumber.stepdef {@code run carrier advise for the current picking job}
	 * @cucumber.example
	 * <pre>
	 * When run carrier advise for the current picking job
	 * </pre>
	 */
	@When("run carrier advise for the current picking job")
	public void runCarrierAdviseForCurrentPickingJob()
	{
		final JsonWFProcess wfProcess = mobileUIPickingClient.advisePackedHU(context.getWfProcessIdNotNull());
		context.setWfProcess(wfProcess);
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
	 * Attempts to complete the current picking job and asserts that it is rejected with the given AD_Message key.
	 * The assertion verifies the thrown exception's AD_Message <em>key</em> (not its rendered text), so it is
	 * robust to both translation and message-text changes.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>AD_Message</b> — (required) the AD_Message key (value) the rejection must carry<br>
	 * @cucumber.depends StepDefData: context (active WFProcess)
	 * @cucumber.example
	 * <pre>
	 * Then completing the picking job is rejected with AD_Message "de.metas.picking.CarrierAdvise_NonManualDivergentOnHU"
	 * </pre>
	 */
	@Then("completing the picking job is rejected with AD_Message {string}")
	public void completeExpectingRejection(@NonNull final String adMessageKey) throws InterruptedException
	{
		waitUntilPickingJobSchedulesValid();
		final AdMessageKey expectedMessageKey = AdMessageKey.of(adMessageKey);

		final Throwable thrown = catchThrowable(
				() -> mobileUIPickingClient.complete(context.getWfProcessIdNotNull()));

		assertThat(thrown)
				.as("Picking job completion must be rejected")
				.isInstanceOf(AdempiereException.class);

		// Assert on the AD_Message KEY carried by the exception (robust to text/translation changes),
		// rather than on the rendered message text.
		final Optional<AdMessageKey> actualMessageKey = AdempiereException.extractMessageTrl(thrown).getAdMessageKey();
		assertThat(actualMessageKey)
				.as("Picking job rejection must carry AD_Message key %s", adMessageKey)
				.contains(expectedMessageKey);
	}

	/**
	 * Attempts to close the current LU or TU pick target and asserts it is rejected with the given AD_Message key.
	 * Mirrors {@link #completeExpectingRejection}: the close path runs the same carrier-advise consistency guard,
	 * so a package carrying inconsistent carriers (e.g. two distinct manual carriers on one HU) must be rejected
	 * at close time too — not silently deferred to shipment generation. Asserts the AD_Message KEY (robust to
	 * text/translation changes). The target type (LU / TU) is carried in the step text.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then closing the TU picking target is rejected with AD_Message "de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"
	 * </pre>
	 */
	@Then("closing the {word} picking target is rejected with AD_Message {string}")
	public void closePickingTargetExpectingRejection(@NonNull final String targetType, @NonNull final String adMessageKey) throws InterruptedException
	{
		waitUntilPickingJobSchedulesValid();
		final AdMessageKey expectedMessageKey = AdMessageKey.of(adMessageKey);

		final Throwable thrown = catchThrowable(() -> closePickingTarget(targetType));

		assertThat(thrown)
				.as("Closing the %s pick target must be rejected", targetType)
				.isInstanceOf(AdempiereException.class);

		final Optional<AdMessageKey> actualMessageKey = AdempiereException.extractMessageTrl(thrown).getAdMessageKey();
		assertThat(actualMessageKey)
				.as("Close-%s rejection must carry AD_Message key %s", targetType, adMessageKey)
				.contains(expectedMessageKey);
	}

	private void closePickingTarget(@NonNull final String targetType)
	{
		final String wfProcessId = context.getWfProcessIdNotNull();
		final JsonWFProcess wfProcess;
		switch (targetType)
		{
			case "LU":
				wfProcess = mobileUIPickingClient.closeLUPickingTarget(wfProcessId);
				break;
			case "TU":
				wfProcess = mobileUIPickingClient.closeTUPickingTarget(wfProcessId);
				break;
			default:
				throw new AdempiereException("Unsupported pick target type `" + targetType + "` (expected LU or TU)");
		}
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

		public JsonPickingJobLine getPickingJobLineByProductId(@NonNull final ProductId productId)
		{
			final ImmutableList<JsonPickingJobLine> eligibleLines = getPickingJobLines().stream()
					.filter(pickingLine -> isMatching(pickingLine, productId))
					.collect(ImmutableList.toImmutableList());
			return CollectionUtils.singleElement(eligibleLines);
		}

		private static boolean isMatching(final JsonPickingJobLine pickingLine, final ProductId productId)
		{
			final String pickingLineProductId = pickingLine.getProductId();
			final String productIdStr = productId.getAsString();
			return Util.equals(pickingLineProductId, productIdStr);
		}

		private List<JsonPickingJobLine> getPickingJobLines()
		{
			return getPickingJob().getLines();
		}

		@NonNull
		public JsonPickingJob getPickingJob()
		{
			final JsonWFProcess wfProcess = getWfProcessNotNull();
			final JsonWFActivity activity = wfProcess.getActivityById(PickingMobileApplication.ACTIVITY_ID_PickLines.getAsString());
			return (JsonPickingJob)activity.getComponentProps().get(ActualPickingWFActivityHandler.PROP_pickingJob);
		}

	}
}
