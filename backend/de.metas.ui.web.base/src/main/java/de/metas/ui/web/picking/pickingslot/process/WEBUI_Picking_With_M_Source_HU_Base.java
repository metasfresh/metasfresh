package de.metas.ui.web.picking.pickingslot.process;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.requests.AddQtyToHURequest;
import de.metas.handlingunits.picking.requests.RetrieveAvailableHUIdsToPickRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.order.DeliveryRule;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.PickingConfig;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.springframework.lang.Nullable;

import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * contains common code of the two fine picking process classes that we have.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
/* package */abstract class WEBUI_Picking_With_M_Source_HU_Base extends PickingSlotViewBasedProcess
{
	private final transient IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);

	private final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);
	private final PickingConfigRepository pickingConfigRepo = SpringContextHolder.instance.getBean(PickingConfigRepository.class);
	private final ModularContractProvider modularContractProvider = SpringContextHolder.instance.getBean(ModularContractProvider.class);
	private final InventoryService inventoryService = SpringContextHolder.instance.getBean(InventoryService.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

	protected final boolean noSourceHUAvailable()
	{
		final List<HuId> sourceHUs = getSourceHUIds();
		return sourceHUs.isEmpty();
	}

	@NonNull
	protected ImmutableList<HuId> getSourceHUIds()
	{
		final I_M_ShipmentSchedule shipmentSchedule = getCurrentShipmentSchedule();

		final PickingHUsQuery query = PickingHUsQuery.builder()
				.shipmentSchedule(shipmentSchedule)
				.onlyTopLevelHUs(true)
				.onlyIfAttributesMatchWithShipmentSchedules(true)
				.build();

		return huPickingSlotBL.retrieveAvailableSourceHUs(query)
				.stream()
				.map(I_M_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.collect(ImmutableList.toImmutableList());
	}

	protected final Quantity retrieveQtyToPick()
	{
		final ShipmentScheduleId shipmentScheduleId = getCurrentShipmentScheduleId();
		final I_M_ShipmentSchedule shipmentSchedule = getCurrentShipmentSchedule();

		final Quantity qtyToDeliverTarget = Services.get(IShipmentScheduleBL.class).getQtyToDeliver(shipmentSchedule);

		final Quantity qtyPickedPlanned = Services.get(IPackagingDAO.class).retrieveQtyPickedPlanned(shipmentScheduleId).orElse(null);
		if (qtyPickedPlanned == null)
		{
			return qtyToDeliverTarget.toZero();
		}

		return qtyToDeliverTarget.subtract(qtyPickedPlanned).toZeroIfNegative();
	}

	@NonNull
	protected ImmutableList<HuId> retrieveTopLevelHUIdsAvailableForPicking()
	{
		final RetrieveAvailableHUIdsToPickRequest request = RetrieveAvailableHUIdsToPickRequest
				.builder()
				.scheduleId(getCurrentShipmentScheduleId())
				.onlyTopLevel(true)
				.considerAttributes(true)
				.build();

		return huPickingSlotBL.retrieveAvailableHUIdsToPickForShipmentSchedule(request);
	}

	protected final boolean isForceDelivery()
	{
		final DeliveryRule deliveryRule = shipmentScheduleEffectiveBL.getDeliveryRule(getCurrentShipmentSchedule());
		return deliveryRule.isForce();
	}

	protected Quantity pickHUsAndPackTo(@NonNull final ImmutableList<HuId> huIdsToPick, @NonNull final Quantity qtyToPack, @NonNull final HuId packToHuId)
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		final PickingSlotId pickingSlotId = pickingSlotRow.getPickingSlotId();

		return pickingCandidateService.addQtyToHU(AddQtyToHURequest.builder()
														  .qtyToPack(qtyToPack)
														  .packToHuId(packToHuId)
														  .sourceHUIds(huIdsToPick)
														  .pickingSlotId(pickingSlotId)
														  .shipmentScheduleId(getCurrentShipmentScheduleId())
														  .allowOverDelivery(getPickingConfig().isAllowOverDelivery())
														  .isForbidAggCUsForDifferentOrders(getPickingConfig().isForbidAggCUsForDifferentOrders())
														  .build());
	}

	protected void forcePick(Quantity qtyToPack, final HuId packToHuId)
	{
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
				return;
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
				return;
			}
		}

		// 3. if the qtyToPack is still not met, supply the missing qty via a virtual inventory
		Loggables.withLogger(log, Level.DEBUG).addLog(" *** forcePick(): supplementing qty: {} via inventory! ", qtyToPack);

		final HuId suppliedHUId = createInventoryForMissingQty(qtyToPack);

		final Quantity qtyPickedFromSuppliedHU = pickHUsAndPackTo(ImmutableList.of(suppliedHUId), qtyToPack, packToHuId);

		qtyToPack = qtyToPack.subtract(qtyPickedFromSuppliedHU);

		Loggables.withLogger(log, Level.DEBUG).addLog(" *** forcePick(): packToHuId: {}, qtyLeftToBePicked: {}.", packToHuId, qtyToPack);
	}

	@NonNull
	protected PickingConfig getPickingConfig()
	{
		return pickingConfigRepo.getPickingConfig();
	}

	@Nullable
	protected OrderId getCurrentlyPickingOrderId()
	{
		final I_M_ShipmentSchedule shipmentSchedule = getCurrentShipmentSchedule();
		return OrderId.ofRepoIdOrNull(shipmentSchedule.getC_Order_ID());
	}

	private HuId createInventoryForMissingQty(@NonNull final Quantity qtyToBeAdded)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getCurrentShipmentSchedule();

		final WarehouseId warehouseId = WarehouseId.ofRepoId(shipmentSchedule.getM_Warehouse_ID());
		final OrgId orgId = OrgId.ofRepoId(shipmentSchedule.getAD_Org_ID());
		final ClientId clientId = ClientId.ofRepoId(shipmentSchedule.getAD_Client_ID());
		final ProductId productId = ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNull(shipmentSchedule.getM_AttributeSetInstance_ID());

		final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIdsOrNull(shipmentSchedule.getC_Order_ID(), shipmentSchedule.getC_OrderLine_ID());
		final FlatrateTermId contractId = modularContractProvider.getSinglePurchaseContractsForSalesOrderLineOrNull(orderAndLineId);

		final CreateVirtualInventoryWithQtyReq req = CreateVirtualInventoryWithQtyReq.builder()
				.clientId(clientId)
				.orgId(orgId)
				.warehouseId(warehouseId)
				.productId(productId)
				.qty(qtyToBeAdded)
				.movementDate(SystemTime.asZonedDateTime())
				.attributeSetInstanceId(attributeSetInstanceId)
				.modularContractId(contractId)
				.build();

		return inventoryService.createInventoryForMissingQty(req);
	}
}
