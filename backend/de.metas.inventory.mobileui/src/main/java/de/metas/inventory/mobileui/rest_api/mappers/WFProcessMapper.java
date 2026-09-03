package de.metas.inventory.mobileui.rest_api.mappers;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.IDocument;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.i18n.TranslatableStrings;
import de.metas.inventory.InventoryId;
import de.metas.inventory.mobileui.InventoryMobileApplication;
import de.metas.inventory.mobileui.rest_api.CompleteWFActivityHandler;
import de.metas.inventory.mobileui.rest_api.InventoryJobWFActivityHandler;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessId;

public class WFProcessMapper
{
	public static InventoryId toInventoryId(final WFProcessId wfProcessId)
	{
		return wfProcessId.getRepoIdAssumingApplicationId(InventoryMobileApplication.APPLICATION_ID, InventoryId::ofRepoId);
	}

	public static WFProcessId toWFProcessId(final InventoryId inventoryId)
	{
		return WFProcessId.ofIdPart(InventoryMobileApplication.APPLICATION_ID, inventoryId);
	}

	public static WFProcess toWFProcess(final Inventory inventory)
	{
		return WFProcess.builder()
				.id(toWFProcessId(inventory.getId()))
				.responsibleId(inventory.getResponsibleId())
				.document(inventory)
				.activities(ImmutableList.of(
						WFActivity.builder()
								.id(WFActivityId.ofString("A1"))
								.caption(TranslatableStrings.empty())
								.wfActivityType(InventoryJobWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.status(InventoryJobWFActivityHandler.computeActivityState(inventory))
								.build(),
						WFActivity.builder()
								.id(WFActivityId.ofString("A2"))
								.caption(TranslatableStrings.adRefList(IDocument.ACTION_AD_Reference_ID, IDocument.ACTION_Complete))
								.wfActivityType(CompleteWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.status(CompleteWFActivityHandler.computeActivityState(inventory))
								.build()
				))
				.build();
	}
}
