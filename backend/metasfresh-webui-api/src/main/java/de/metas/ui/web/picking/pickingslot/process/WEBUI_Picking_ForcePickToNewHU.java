/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.ui.web.picking.pickingslot.process;

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryHeaderCreateRequest;
import de.metas.handlingunits.inventory.InventoryLineCreateRequest;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inventory.InventoryDocSubType;
import de.metas.inventory.InventoryId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

public class WEBUI_Picking_ForcePickToNewHU extends WEBUI_Picking_PickQtyToNewHU
		implements IProcessPrecondition
{
	private static final Logger log = LogManager.getLogger(WEBUI_Picking_ForcePickToNewHU.class);

	private final InventoryService inventoryService = SpringContextHolder.instance.getBean(InventoryService.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final Optional<ProcessPreconditionsResolution> preconditionsResolution = checkValidSelection();

		if (preconditionsResolution.isPresent())
		{
			return preconditionsResolution.get();
		}

		if (!isForceDelivery())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(" Use 'WEBUI_Picking_PickQtyToNewHU' for non force shipping records!");
		}

		return ProcessPreconditionsResolution.accept();
	}

	protected String doIt()
	{
		final HuId packToHuId = forcePick();

		printPickingLabel(packToHuId);

		invalidatePackablesView();
		invalidatePickingSlotsView();
		return MSG_OK;
	}

	private HuId forcePick()
	{
		final HuId packToHuId = createNewHuId();

		Quantity qtyToPack = getQtyToPack();
		if (qtyToPack.signum() <= 0)
		{
			throw new AdempiereException("@QtyCU@ > 0");
		}

		// 1. try to pick from source HUs if any are available
		final ImmutableList<HuId> sourceHUIds = getSourceHUIds();

		Loggables.withLogger(log, Level.DEBUG).addLog(" *** forcePick(): qtyLeftToBePicked: {} sourceHUIds: {}", qtyToPack, sourceHUIds);

		if (!sourceHUIds.isEmpty())
		{
			final Quantity qtyPickedFromSourceHUs = pickHUsAndPackTo(sourceHUIds, qtyToPack, packToHuId);

			qtyToPack = qtyToPack.subtract(qtyPickedFromSourceHUs);

			if (qtyToPack.signum() <= 0)
			{
				return packToHuId;
			}
		}

		// 2. if the qtyToPack couldn't be fulfilled from the available source HUs, try to allocate from the existing HUs
		final ImmutableList<HuId> availableHUIds = retrieveTopLevelHUIdsAvailableForPicking();

		Loggables.withLogger(log, Level.DEBUG).addLog(" *** forcePick(): qtyLeftToBePicked: {} availableHUsForPicking: {}", qtyToPack, availableHUIds);

		if (!availableHUIds.isEmpty())
		{

			final Quantity qtyPickedFromAvailableHus = pickHUsAndPackTo(availableHUIds, qtyToPack, packToHuId);

			qtyToPack = qtyToPack.subtract(qtyPickedFromAvailableHus);

			if (qtyToPack.signum() <= 0)
			{
				return packToHuId;
			}
		}

		// 3. if the qtyToPack is still not met, supply the missing qty via a virtual inventory
		Loggables.withLogger(log, Level.DEBUG).addLog(" *** forcePick(): supplementing qty: {} via inventory! ", qtyToPack);

		final HuId suppliedHUId = createInventoryForMissingQty(qtyToPack);

		final Quantity qtyPickedFromSuppliedHU = pickHUsAndPackTo(ImmutableList.of(suppliedHUId), qtyToPack, packToHuId);

		qtyToPack = qtyToPack.subtract(qtyPickedFromSuppliedHU);

		Loggables.withLogger(log, Level.DEBUG).addLog(" *** forcePick(): packToHuId: {}, qtyLeftToBePicked: {}.", packToHuId, qtyToPack);

		return packToHuId;
	}

	private HuId createInventoryForMissingQty(@NonNull final Quantity qtyToBeAdded)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getCurrentShipmentSchedule();

		final WarehouseId warehouseId = WarehouseId.ofRepoId(shipmentSchedule.getM_Warehouse_ID());
		final LocatorId locatorId = warehouseBL.getDefaultLocatorId(warehouseId);
		final OrgId orgId = OrgId.ofRepoId(shipmentSchedule.getAD_Org_ID());
		final ClientId clientId = ClientId.ofRepoId(shipmentSchedule.getAD_Client_ID());
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNull(shipmentSchedule.getM_AttributeSetInstance_ID());

		final InventoryHeaderCreateRequest createHeaderRequest = InventoryHeaderCreateRequest
				.builder()
				.orgId(orgId)
				.docTypeId(getVirtualInventoryDocTypeId(clientId, orgId))
				.movementDate(SystemTime.asZonedDateTime())
				.warehouseId(warehouseId)
				.build();

		final InventoryId inventoryId = inventoryService.createInventoryHeader(createHeaderRequest).getId();

		final InventoryLineCreateRequest createLineRequest = InventoryLineCreateRequest
				.builder()
				.inventoryId(inventoryId)
				.productId(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()))
				.qtyBooked(Quantity.zero(qtyToBeAdded.getUOM()))
				.qtyCount(qtyToBeAdded)
				.attributeSetId(attributeSetInstanceId)
				.locatorId(locatorId)
				.build();

		inventoryService.createInventoryLine(createLineRequest);

		inventoryService.completeDocument(inventoryId);

		final Inventory inventory = inventoryService.getById(inventoryId);

		return CollectionUtils.singleElement(inventory.getHuIds());
	}

	private DocTypeId getVirtualInventoryDocTypeId(@NonNull final ClientId clientId, @NonNull final OrgId orgId)
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(InventoryDocSubType.VirtualInventory.getDocBaseType())
				.docSubType(InventoryDocSubType.VirtualInventory.getCode())
				.adClientId(clientId.getRepoId())
				.adOrgId(orgId.getRepoId())
				.build());
	}
}
