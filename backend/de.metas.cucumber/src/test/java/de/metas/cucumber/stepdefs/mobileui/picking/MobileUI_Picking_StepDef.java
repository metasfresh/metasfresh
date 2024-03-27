package de.metas.cucumber.stepdefs.mobileui.picking;

import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.picking.PickingSlot_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.leich_und_mehl.LMQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.rest_api.json.JsonPickingJobLine;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFActivity;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;
import java.util.List;

@RequiredArgsConstructor
public class MobileUI_Picking_StepDef
{
	@NonNull private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	@NonNull private final MobileUIPickingClient mobileUIPickingClient = new MobileUIPickingClient();

	@NonNull private final C_Order_StepDefData ordersTable;
	@NonNull private final PickingSlot_StepDefData pickingSlotsTable;
	@NonNull private final M_HU_StepDefData huTable;

	@NonNull private final Context context = new Context();

	@When("start picking job")
	public void start(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final I_C_Order salesOrder = row.getAsIdentifier("C_Order_ID").lookupIn(ordersTable);

		final JsonWFProcess wfProcess = mobileUIPickingClient.startJobBySalesDocumentNo(salesOrder.getDocumentNo());
		context.setWfProcess(wfProcess);
	}

	@When("scan picking slot")
	public void scanPickingSlot(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final PickingSlotIdAndCaption pickingSlotIdAndCaption = pickingSlotsTable.getPickingSlotIdAndCaption(row.getAsIdentifier("PickingSlot"));

		final JsonWFProcess wfProcess = mobileUIPickingClient.scanPickingSlot(context.getWfProcessIdNotNull(), pickingSlotIdAndCaption);
		context.setWfProcess(wfProcess);
	}

	@When("pick lines")
	public void pickLines(@NonNull final DataTable dataTable)
	{
		DataTableRow.toRows(dataTable).forEach(this::pickLine);
	}

	private void pickLine(@NonNull final DataTableRow row)
	{
		final HuId pickFromHUId = huTable.getId(row.getAsIdentifier("PickFromHU"));
		final HUQRCode pickFromQRCode = huQRCodesService.getQRCodeByHuId(pickFromHUId);

		final LMQRCode itemQRCode = LMQRCode.fromGlobalQRCodeJsonString(row.getAsString("LMQRCode"));

		final JsonWFProcess wfProcess = mobileUIPickingClient.pickLine(
				context.getWfProcessIdNotNull(),
				PickingMobileApplication.ACTIVITY_ID_PickLines.getAsString(),
				context.getSinglePickingLineId(),
				pickFromQRCode,
				itemQRCode);
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
			final JsonWFProcess wfProcess = getWfProcessNotNull();
			final JsonWFActivity activity = wfProcess.getActivityById(PickingMobileApplication.ACTIVITY_ID_PickLines.getAsString());
			//noinspection unchecked
			final List<JsonPickingJobLine> lines = (List<JsonPickingJobLine>)activity.getComponentProps().get("lines");
			final JsonPickingJobLine line = CollectionUtils.singleElement(lines);
			return line.getPickingLineId();
		}
	}
}
