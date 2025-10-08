package de.metas.inventory.mobileui.job.service;

import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.mobileui.deps.handlingunits.HandlingUnitsService;
import de.metas.inventory.mobileui.deps.products.ProductService;
import de.metas.inventory.mobileui.deps.warehouse.WarehouseService;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUCommand;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUCommand.ResolveHUCommandBuilder;
import de.metas.inventory.mobileui.job.qrcode.ResolveHURequest;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUResponse;
import de.metas.inventory.mobileui.rest_api.json.JsonCountRequest;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolveContext;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverRequest;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

import static de.metas.inventory.mobileui.mappers.InventoryWFProcessMapper.toInventoryId;

@Service
@RequiredArgsConstructor
public class InventoryJobService
{
	@NonNull private final ProductService productService;
	@NonNull private final WarehouseService warehouseService;
	@NonNull private final HandlingUnitsService huService;
	@NonNull private final InventoryService inventoryService;

	public Inventory getById(final WFProcessId wfProcessId, @NonNull final UserId calledId)
	{
		final Inventory inventory = getById(toInventoryId(wfProcessId));
		inventory.assertHasAccess(Env.getLoggedUserId());
		return inventory;
	}

	public Inventory getById(final InventoryId inventoryId)
	{
		return inventoryService.getById(inventoryId);
	}

	public Inventory startJob(@NonNull final InventoryId inventoryId, @NonNull final UserId responsibleId)
	{
		return inventoryService.updateById(inventoryId, inventory -> inventory.assigningTo(responsibleId));
	}

	public Inventory reassignJob(final InventoryId inventoryId, final UserId newResponsibleId)
	{
		return inventoryService.updateById(inventoryId, inventory -> inventory.reassigningTo(newResponsibleId));
	}

	public void abort(final WFProcessId wfProcessId, final UserId callerId)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	public void abortAll(final UserId callerId)
	{
		// TODO
	}

	public Inventory complete(Inventory inventory)
	{
		// TODO
		return inventory;
	}

	public LocatorScannedCodeResolverResult resolveLocator(
			@NonNull ScannedCode scannedCode,
			@NonNull WFProcessId wfProcessId,
			@Nullable InventoryLineId lineId,
			@NonNull UserId callerId)
	{
		final Inventory inventory = getById(wfProcessId, callerId);

		return warehouseService.resolveLocator(
				LocatorScannedCodeResolverRequest.builder()
						.scannedCode(scannedCode)
						.context(LocatorScannedCodeResolveContext.builder()
								.eligibleLocatorIds(inventory.getLocatorIdsEligibleForCounting(lineId))
								.build())
						.build()
		);
	}

	public ResolveHUResponse resolveHU(@NonNull final ResolveHURequest request)
	{
		return newHUScannedCodeResolveCommand()
				.scannedCode(request.getScannedCode())
				.inventory(getById(request.getWfProcessId(), request.getCallerId()))
				.lineId(request.getLineId())
				.locatorId(request.getLocatorId())
				.build()
				.execute();
	}

	private ResolveHUCommandBuilder newHUScannedCodeResolveCommand()
	{
		return ResolveHUCommand.builder()
				.productService(productService)
				.huService(huService);
	}

	public Inventory count(@NonNull final JsonCountRequest request, final UserId callerId)
	{
		final Inventory inventory = getById(toInventoryId(request.getWfProcessId()));
		inventory.assertHasAccess(callerId);

		// TODO

		return inventory;
	}
}
