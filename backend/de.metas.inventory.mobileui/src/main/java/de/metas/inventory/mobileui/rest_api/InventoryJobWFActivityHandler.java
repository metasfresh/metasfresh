package de.metas.inventory.mobileui.rest_api;

import de.metas.handlingunits.inventory.Inventory;
import de.metas.inventory.mobileui.rest_api.mappers.JsonInventoryJobMapper;
import de.metas.inventory.mobileui.rest_api.mappers.Mappers;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.api.Params;
import org.springframework.stereotype.Component;

import static de.metas.inventory.mobileui.InventoryMobileApplication.getInventory;

@Component
@RequiredArgsConstructor
public class InventoryJobWFActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("inventory.inventory");
	public static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("inventory/inventory");

	@NonNull private final Mappers mappers;

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final Inventory inventory = getInventory(wfProcess);
		final JsonInventoryJobMapper jsonMapper = mappers.newJsonInventoryJobMapper(jsonOpts);

		return UIComponent.builderFrom(COMPONENT_TYPE, wfActivity)
				.properties(Params.builder()
						.valueObj("job", jsonMapper.toJson(inventory))
						.build())
				.build();
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return computeActivityState(getInventory(wfProcess));

	}

	public static WFActivityStatus computeActivityState(final Inventory inventory)
	{
		return inventory.getDocStatus().isCompleted() ? WFActivityStatus.COMPLETED : WFActivityStatus.NOT_STARTED;
	}

}
