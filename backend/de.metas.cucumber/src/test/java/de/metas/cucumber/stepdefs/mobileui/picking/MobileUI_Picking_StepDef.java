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
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.rest_api.json.JsonPickingJob;
import de.metas.picking.rest_api.json.JsonPickingJobLine;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.picking.workflow.handlers.activity_handlers.ActualPickingWFActivityHandler;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFActivity;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

	@When("complete picking job")
	public void complete()
	{
		final JsonWFProcess wfProcess = mobileUIPickingClient.complete(context.getWfProcessIdNotNull());
		context.setWfProcess(wfProcess);
	}

	/**
	 * Capture N GRAIs (one per TU) on the picked LU via the picking-scoped set-GRAIs endpoint.
	 * <p>
	 * The LU identifier is the existing-LU registered by {@code expect current picking target} (column
	 * {@code Existing_LU}); this step resolves it from {@link #huTable} and binds the scanned GRAIs to it.
	 * <p>
	 * <b>@cucumber.columns</b>
	 * <ul>
	 *   <li><b>GRAI</b> — (required) one row per GRAI code to assign to the LU (e.g. {@code 7613204.00307.000001}).</li>
	 * </ul>
	 *
	 * @param luIdentifier identifier of the picked LU (resolved via the HU step-def table)
	 * @param dataTable    one {@code GRAI} column, one row per GRAI to assign
	 */
	@When("^set picking GRAIs on LU identified by (.*)$")
	public void setPickingGraisOnLU(@NonNull final String luIdentifier, @NonNull final DataTable dataTable)
	{
		final HuId luId = huTable.getId(luIdentifier);
		final ImmutableList<String> graiCodes = DataTableRows.of(dataTable).stream()
				.map(row -> row.getAsString("GRAI"))
				.collect(ImmutableList.toImmutableList());

		final JsonWFProcess wfProcess = mobileUIPickingClient.setPickingGrais(context.getWfProcessIdNotNull(), luId, graiCodes);
		context.setWfProcess(wfProcess);
	}

	/**
	 * Complete the picking job and assert it is BLOCKED by the completion-time GRAI validator.
	 * <p>
	 * {@link MobileUIPickingClient#complete(String)} runs {@code WorkflowRestController.setUserConfirmation}
	 * in-process, so the {@code PickingJobCompleteCommand -> PickingJobGRAIValidator -> HUGraiSnapshot.assertAllGraisAssigned}
	 * {@link AdempiereException} (carrying the AD_Message key) propagates here and is asserted directly.
	 * <p>
	 * <b>@cucumber.columns</b>
	 * <ul>
	 *   <li><b>AD_Message</b> — (required) the expected AD_Message key the completion must fail with
	 *       (e.g. {@code de.metas.handlingunits.picking.GRAICountMismatch}).</li>
	 * </ul>
	 *
	 * @param dataTable a single row with the expected {@code AD_Message} key
	 */
	@Then("^complete picking job expecting error$")
	public void completePickingJobExpectingError(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final String expectedAdMessage = row.getAsString("AD_Message");

		assertThatThrownBy(() -> mobileUIPickingClient.complete(context.getWfProcessIdNotNull()))
				.as("completing a GRAIRequired picking job with fewer GRAIs than TUs must be blocked")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(expectedAdMessage);
	}

	//
	//
	//

	@Setter
	@ToString
	private static class Context
	{
		@Nullable JsonWFProcess wfProcess;

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
