package de.metas.inventory.mobileui.job.service;

import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLineCountRequest;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.InventoryQuery;
import de.metas.inventory.mobileui.deps.handlingunits.HandlingUnitsService;
import de.metas.inventory.mobileui.deps.products.ProductService;
import de.metas.inventory.mobileui.deps.warehouse.WarehouseService;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUCommand;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUCommand.ResolveHUCommandBuilder;
import de.metas.inventory.mobileui.job.qrcode.ResolveHURequest;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUResponse;
import de.metas.inventory.mobileui.rest_api.json.JsonCountRequest;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.resolver.LocatorGlobalQRCodeResolverKey;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolveContext;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverRequest;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Set;

import static de.metas.inventory.mobileui.rest_api.mappers.WFProcessMapper.toInventoryId;

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
		inventory.assertHasAccess(calledId);
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
		inventoryService.updateById(
				toInventoryId(wfProcessId),
				inventory -> {
					inventory.assertHasAccess(callerId);
					return inventory.unassign();
				}
		);
	}

	public void abortAll(@NonNull final UserId callerId)
	{
		inventoryService.updateByQuery(
				InventoryQuery.builder().onlyDraftOrInProgress().onlyResponsibleId(callerId).build(),
				Inventory::unassign
		);
	}

	public Inventory complete(Inventory inventory)
	{
		final InventoryId inventoryId = inventory.getId();
		inventoryService.completeDocument(inventoryId);
		return inventoryService.getById(inventory.getId());
	}

	public LocatorScannedCodeResolverResult resolveLocator(
			@NonNull ScannedCode scannedCode,
			@NonNull WFProcessId wfProcessId,
			@Nullable InventoryLineId lineId,
			@NonNull UserId callerId)
	{
		final Inventory inventory = getById(wfProcessId, callerId);

		final Set<LocatorId> eligibleLocatorIds = inventory.getLocatorIdsEligibleForCounting(lineId);
		if (eligibleLocatorIds.isEmpty())
		{
			return LocatorScannedCodeResolverResult.notFound(LocatorGlobalQRCodeResolverKey.ofString("InventoryJobService"), "no such locator in current inventory");
		}

		return warehouseService.resolveLocator(
				LocatorScannedCodeResolverRequest.builder()
						.scannedCode(scannedCode)
						.context(LocatorScannedCodeResolveContext.builder()
								.eligibleLocatorIds(eligibleLocatorIds)
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

	public Inventory reportCounting(@NonNull final JsonCountRequest request, final UserId callerId)
	{
		final InventoryId inventoryId = toInventoryId(request.getWfProcessId());
		return inventoryService.updateById(inventoryId, inventory -> {
			inventory.assertHasAccess(callerId);
			return inventory.updatingLineById(request.getLineId(), line -> {
				// TODO handle the case when huId is null
				final Quantity qtyBook = huService.getQty(request.getHuId(), line.getProductId());
				final Quantity qtyCount = Quantity.of(request.getQtyCount(), qtyBook.getUOM());
				return line.withCounting(InventoryLineCountRequest.builder()
						.huId(request.getHuId())
						.scannedCode(request.getScannedCode())
						.qtyBook(qtyBook)
						.qtyCount(qtyCount)
						.asiId(productService.createASI(line.getProductId(), request.getAttributesAsMap()))
						.build());
			});
		});
	}
}
