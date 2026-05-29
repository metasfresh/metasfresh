package de.metas.inventory.mobileui;

import com.google.common.annotations.VisibleForTesting;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.i18n.TranslatableStrings;
import de.metas.inventory.InventoryId;
import de.metas.inventory.mobileui.deps.warehouse.WarehouseService;
import de.metas.inventory.mobileui.deps.warehouse.WarehousesLoadingCache;
import de.metas.inventory.mobileui.job.service.InventoryJobService;
import de.metas.inventory.mobileui.launchers.InventoryWFProcessStartParams;
import de.metas.inventory.mobileui.launchers.InventoryWorkflowLaunchersProvider;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperty;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.service.WorkflowBasedMobileApplication;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.UnaryOperator;

import static de.metas.inventory.mobileui.rest_api.mappers.WFProcessMapper.toInventoryId;
import static de.metas.inventory.mobileui.rest_api.mappers.WFProcessMapper.toWFProcess;

@Component
@RequiredArgsConstructor
public class InventoryMobileApplication implements WorkflowBasedMobileApplication
{
	@VisibleForTesting
	public static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("inventory");

	@NonNull private final InventoryWorkflowLaunchersProvider launchersProvider;
	@NonNull private final InventoryJobService jobService;
	@NonNull private final WarehouseService warehouseService;

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}

	@Override
	public WorkflowLaunchersList provideLaunchers(final WorkflowLaunchersQuery query) {return launchersProvider.provideLaunchers(query);}

	@Override
	public WFProcess startWorkflow(final WorkflowStartRequest request)
	{
		final UserId invokerId = request.getInvokerId();
		final InventoryId inventoryId = InventoryWFProcessStartParams.ofParams(request.getWfParameters()).getInventoryId();

		final Inventory inventory = jobService.startJob(inventoryId, invokerId);
		return toWFProcess(inventory);

	}

	@Override
	public WFProcess continueWorkflow(final WFProcessId wfProcessId, final UserId callerId)
	{
		final InventoryId inventoryId = toInventoryId(wfProcessId);
		final Inventory inventory = jobService.reassignJob(inventoryId, callerId);
		return toWFProcess(inventory);
	}

	@Override
	public void abort(final WFProcessId wfProcessId, final UserId callerId)
	{
		jobService.abort(wfProcessId, callerId);
	}

	@Override
	public void abortAll(final UserId callerId)
	{
		jobService.abortAll(callerId);
	}

	@Override
	public void logout(final @NonNull UserId userId)
	{
		abortAll(userId);
	}

	@Override
	public WFProcess getWFProcessById(final WFProcessId wfProcessId)
	{
		final Inventory inventory = jobService.getById(toInventoryId(wfProcessId));
		return toWFProcess(inventory);
	}

	@Override
	public WFProcessHeaderProperties getHeaderProperties(final @NonNull WFProcess wfProcess)
	{
		final WarehousesLoadingCache warehouses = warehouseService.newLoadingCache();

		final Inventory inventory = getInventory(wfProcess);

		return WFProcessHeaderProperties.builder()
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("DocumentNo"))
						.value(inventory.getDocumentNo())
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("MovementDate"))
						.value(TranslatableStrings.date(inventory.getMovementDate().toLocalDate()))
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("M_Warehouse_ID"))
						.value(inventory.getWarehouseId() != null
								? warehouses.getById(inventory.getWarehouseId()).getWarehouseName()
								: "")
						.build())
				.build();
	}

	@NonNull
	public static Inventory getInventory(final @NonNull WFProcess wfProcess)
	{
		return wfProcess.getDocumentAs(Inventory.class);
	}

	public static WFProcess mapJob(@NonNull final WFProcess wfProcess, @NonNull final UnaryOperator<Inventory> mapper)
	{
		final Inventory inventory = getInventory(wfProcess);
		final Inventory inventoryChanged = mapper.apply(inventory);
		return !Objects.equals(inventory, inventoryChanged) ? toWFProcess(inventoryChanged) : wfProcess;
	}

}
