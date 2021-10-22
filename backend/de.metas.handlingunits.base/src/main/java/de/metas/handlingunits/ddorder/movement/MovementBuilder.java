package de.metas.handlingunits.ddorder.movement;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.ddorder.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.picking.DDOrderPickFromService;
import de.metas.handlingunits.ddorder.picking.DDOrderPickSchedule;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.api.DDOrderId;
import org.eevolution.api.DDOrderLineId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Supporting class: Generate a movements for distribution order lines.
 */
final class MovementBuilder
{
	// services
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IMovementBL movementBL = Services.get(IMovementBL.class);
	private final IMovementDAO movementsRepo = Services.get(IMovementDAO.class);
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IHUDDOrderBL ddOrderBL = Services.get(IHUDDOrderBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final DDOrderPickFromService ddOrderPickFromService = SpringContextHolder.instance.getBean(DDOrderPickFromService.class);

	// parameters
	private final HuIdsWithPackingMaterialsTransferred huIdsWithPackingMaterialsTransferred;
	private final DDOrdersCache ddOrdersCache;
	private final DDOrderId ddOrderId;

	private Instant _movementDate;
	private LocatorId locatorToIdOverride;

	// state
	private I_M_Movement _movement;
	private int nextLineNo = 10;

	public MovementBuilder(
			@NonNull final DDOrdersCache ddOrdersCache,
			@NonNull final HuIdsWithPackingMaterialsTransferred huIdsWithPackingMaterialsTransferred,
			@NonNull final DDOrderId ddOrderId)
	{
		this.ddOrdersCache = ddOrdersCache;
		this.huIdsWithPackingMaterialsTransferred = huIdsWithPackingMaterialsTransferred;
		this.ddOrderId = ddOrderId;
	}

	private void assumeNoMovementHeaderCreated()
	{
		Check.assumeNull(getMovementOrNull(), LiberoException.class, "movement header shall not be created");
	}

	private LocatorId getInTransitLocatorId()
	{
		final I_DD_Order ddOrder = ddOrdersCache.getById(ddOrderId);
		final WarehouseId warehouseInTransitId = WarehouseId.ofRepoId(ddOrder.getM_Warehouse_ID());
		return warehouseBL.getDefaultLocatorId(warehouseInTransitId);
	}

	public MovementBuilder movementDate(@NonNull final Instant movementDate)
	{
		assumeNoMovementHeaderCreated();
		_movementDate = movementDate;
		return this;
	}

	private Instant getMovementDate()
	{
		Check.assumeNotNull(_movementDate, LiberoException.class, "movementDate not null");
		return _movementDate;
	}

	private I_M_Movement getMovementOrNull()
	{
		return _movement;
	}

	public MovementBuilder locatorToIdOverride(@Nullable final LocatorId locatorToIdOverride)
	{
		this.locatorToIdOverride = locatorToIdOverride;
		return this;
	}

	private I_M_Movement getCreateMovementHeader()
	{
		if (_movement == null)
		{
			_movement = createMovementHeader();
		}
		return _movement;
	}

	private I_M_Movement createMovementHeader()
	{
		final I_DD_Order ddOrder = ddOrdersCache.getById(ddOrderId);
		final Instant movementDate = getMovementDate();

		final I_M_Movement movement = InterfaceWrapperHelper.newInstance(I_M_Movement.class);
		movement.setAD_Org_ID(ddOrder.getAD_Org_ID());

		//
		// Document Type
		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_MaterialMovement)
				.adClientId(movement.getAD_Client_ID())
				.adOrgId(movement.getAD_Org_ID())
				.build());
		movement.setC_DocType_ID(docTypeId.getRepoId());

		//
		// Reference to DD Order
		movement.setDD_Order_ID(ddOrder.getDD_Order_ID());
		movement.setPOReference(ddOrder.getPOReference());
		movement.setDescription(ddOrder.getDescription());

		//
		// Internal contact user
		movement.setSalesRep_ID(ddOrder.getSalesRep_ID());

		//
		// BPartner (i.e. shipper BP)
		movement.setC_BPartner_ID(ddOrder.getC_BPartner_ID());
		movement.setC_BPartner_Location_ID(ddOrder.getC_BPartner_Location_ID());    // shipment address
		movement.setAD_User_ID(ddOrder.getAD_User_ID());

		//
		// Shipper
		movement.setM_Shipper_ID(ddOrder.getM_Shipper_ID());
		movement.setFreightCostRule(ddOrder.getFreightCostRule());
		movement.setFreightAmt(ddOrder.getFreightAmt());

		//
		// Delivery Rules & Priority
		movement.setDeliveryRule(ddOrder.getDeliveryRule());
		movement.setDeliveryViaRule(ddOrder.getDeliveryViaRule());
		movement.setPriorityRule(ddOrder.getPriorityRule());

		//
		// Dates
		movement.setMovementDate(TimeUtil.asTimestamp(movementDate));

		//
		// Charge (if any)
		movement.setC_Charge_ID(ddOrder.getC_Charge_ID());
		movement.setChargeAmt(ddOrder.getChargeAmt());

		//
		// Dimensions
		// 07689: This set of the activity is harmless, even though this column is currently hidden
		movement.setC_Activity_ID(ddOrder.getC_Activity_ID());
		movement.setC_Campaign_ID(ddOrder.getC_Campaign_ID());
		movement.setC_Project_ID(ddOrder.getC_Project_ID());
		movement.setAD_OrgTrx_ID(ddOrder.getAD_OrgTrx_ID());
		movement.setUser1_ID(ddOrder.getUser1_ID());
		movement.setUser2_ID(ddOrder.getUser2_ID());

		movementsRepo.save(movement);

		return movement;
	}

	public void addMovementLineReceipt(final DDOrderPickSchedule schedule)
	{
		addMovementLine(schedule, MovementType.ReceiveFromTransit);
	}

	public void addMovementLineShipment(final DDOrderPickSchedule schedule)
	{
		addMovementLine(schedule, MovementType.ShipToTransit);
	}

	public void addMovementLineDirect(final DDOrderPickSchedule schedule)
	{
		addMovementLine(schedule, MovementType.Direct);
	}

	private enum MovementType
	{
		ShipToTransit, ReceiveFromTransit, Direct,
	}

	private void addMovementLine(
			@NonNull final DDOrderPickSchedule schedule,
			@NonNull final MovementType movementType)
	{
		ddOrderPickFromService.splitFromPickHUIfNeeded(schedule);
		final HuId actualHUIdPicked = Objects.requireNonNull(schedule.getActualHUIdPicked());

		final I_DD_OrderLine ddOrderLine = ddOrdersCache.getLineById(schedule.getDdOrderLineId());

		final I_M_Movement movement = getCreateMovementHeader();

		final I_M_MovementLine movementLine = InterfaceWrapperHelper.newInstance(I_M_MovementLine.class);
		movementLine.setAD_Org_ID(movement.getAD_Org_ID());
		movementLine.setM_Movement(movement);

		movementLine.setDD_OrderLine_ID(schedule.getDdOrderLineId().getRepoId());
		// TODO movementLine.setDD_OrderLine_Alternative_ID(schedule.getDdOrderLineAlternativeId());

		movementLine.setLine(getNextLineNoAndIncrement());
		movementLine.setDescription(ddOrderLine.getDescription());

		//
		// Product & ASI
		movementLine.setM_Product_ID(ddOrderLine.getM_Product_ID());
		movementLine.setM_AttributeSetInstance_ID(ddOrderLine.getM_AttributeSetInstance_ID());
		movementLine.setM_AttributeSetInstanceTo_ID(ddOrderLine.getM_AttributeSetInstanceTo_ID());
		//

		final LocatorId fromLocatorId;
		final LocatorId toLocatorId;
		if (movementType == MovementType.ReceiveFromTransit)
		{
			fromLocatorId = getInTransitLocatorId();
			toLocatorId = getDropToLocatorId(ddOrderLine);
		}
		else if (movementType == MovementType.ShipToTransit)
		{
			fromLocatorId = getPickFromLocatorId(ddOrderLine);
			toLocatorId = getInTransitLocatorId();
		}
		else if (movementType == MovementType.Direct)
		{
			fromLocatorId = getPickFromLocatorId(ddOrderLine);
			toLocatorId = getDropToLocatorId(ddOrderLine);
		}
		else
		{
			throw new AdempiereException("Invalid movement type: " + movementType);
		}

		movementLine.setM_Locator_ID(fromLocatorId.getRepoId());
		movementLine.setM_LocatorTo_ID(toLocatorId.getRepoId());

		movementBL.setMovementQty(movementLine, schedule.getQtyToPick());
		movementsRepo.save(movementLine);

		//
		// Set the activity from the warehouse of locator To
		// only in case it is different from the activity of the product acct (07629)
		movementBL.setC_Activities(movementLine);

		assignHUToMovementLine(movementType, actualHUIdPicked, movementLine, schedule.getDdOrderLineId());
	}

	private void assignHUToMovementLine(
			@NonNull final MovementType movementType,
			@NonNull final HuId huId,
			@NonNull final I_M_MovementLine movementLine,
			@NonNull final DDOrderLineId fromDDOrderLineId)
	{
		final boolean isTransferPackingMaterials = this.huIdsWithPackingMaterialsTransferred.addHuId(huId, movementType);

		final I_M_HU hu = handlingUnitsBL.getById(huId);
		huMovementBL.assignHU(movementLine, hu, isTransferPackingMaterials, ITrx.TRXNAME_ThreadInherited);

		// Make sure given HUs are no longer assigned to this DD Order Line
		ddOrderBL.unassignHUs(fromDDOrderLineId, ImmutableSet.of(huId));
	}

	private LocatorId getPickFromLocatorId(final I_DD_OrderLine ddOrderLine)
	{
		return warehouseBL.getLocatorIdByRepoId(ddOrderLine.getM_Locator_ID());
	}

	private LocatorId getDropToLocatorId(final I_DD_OrderLine ddOrderLine)
	{
		return locatorToIdOverride != null
				? locatorToIdOverride
				: warehouseBL.getLocatorIdByRepoId(ddOrderLine.getM_LocatorTo_ID());
	}

	private int getNextLineNoAndIncrement()
	{
		final int lineNo = nextLineNo;
		nextLineNo += 10;
		return lineNo;
	}

	public I_M_Movement process()
	{
		final I_M_Movement movement = getMovementOrNull();
		Check.assumeNotNull(movement, LiberoException.class, "movement not null");

		documentBL.processEx(movement, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		return movement;
	}

	//
	//
	// ---------------------------------------------------------------------------
	//
	//

	@Value
	@Builder
	public static class DDOrderLineSnapshot
	{
		@NonNull DDOrderLineId ddOrderLineId;
		int ddOrderLineAlternativeId;

		@Nullable String description;
		@NonNull ProductId productId;
		@NonNull Quantity movementQty;
		@NonNull I_M_HU hu;

		@Builder.Default
		@NonNull AttributeSetInstanceId fromAsiId = AttributeSetInstanceId.NONE;

		@Builder.Default
		@NonNull AttributeSetInstanceId toAsiId = AttributeSetInstanceId.NONE;

		@NonNull LocatorId pickFromLocatorId;
		@NonNull LocatorId dropToLocatorId;
	}

	public static class HuIdsWithPackingMaterialsTransferred
	{
		private final HashSet<HuId> shipment = new HashSet<>();
		private final HashSet<HuId> receipt = new HashSet<>();

		public void clear()
		{
			shipment.clear();
			receipt.clear();
		}

		/**
		 * @return true if first time added
		 */
		private boolean addHuId(final @NonNull HuId huId, final @NonNull MovementType movementType)
		{
			return getHuIds(movementType).add(huId);
		}

		private Set<HuId> getHuIds(final @NonNull MovementType movementType)
		{
			switch (movementType)
			{
				case ShipToTransit:
					return this.shipment;
				case ReceiveFromTransit:
				case Direct:
					return this.receipt;
				default:
					throw new AdempiereException("Unknown movement type: " + movementType);
			}
		}
	}
}
