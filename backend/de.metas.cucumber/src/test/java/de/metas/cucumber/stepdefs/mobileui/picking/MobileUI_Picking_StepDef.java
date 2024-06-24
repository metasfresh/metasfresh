package de.metas.cucumber.stepdefs.mobileui.picking;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.picking.PickingSlot_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.qrcodes.leich_und_mehl.LMQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.rest_api.json.JsonPickingJobLine;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFActivity;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import io.cucumber.datatable.DataTable;
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

import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
public class MobileUI_Picking_StepDef
{
	@NonNull private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	@NonNull private final MobileUIPickingClient mobileUIPickingClient = new MobileUIPickingClient();

	@NonNull private final M_Product_StepDefData productsTable;
	@NonNull private final C_Order_StepDefData ordersTable;
	@NonNull private final PickingSlot_StepDefData pickingSlotsTable;
	@NonNull private final M_HU_StepDefData huTable;

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
		final LMQRCode itemQRCode = row.getAsOptionalString("LMQRCode").map(LMQRCode::fromGlobalQRCodeJsonString).orElse(null);
		if (itemQRCode != null)
		{
			requestBuilder
					.qtyPicked(BigDecimal.ONE)
					.catchWeight(itemQRCode.getWeightInKg())
					.setBestBeforeDate(true)
					.bestBeforeDate(itemQRCode.getBestBeforeDate())
					.setLotNo(true)
					.lotNo(itemQRCode.getLotNumber());
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
			//noinspection unchecked
			return (List<JsonPickingJobLine>)activity.getComponentProps().get("lines");
		}

	}
}
