package org.eevolution.api.impl;

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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IDDOrderMovementBuilder;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;

import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.material.planning.pporder.LiberoException;
import lombok.NonNull;

public class DD_Order_MovementBuilder implements IDDOrderMovementBuilder
{
	// services
	private final IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IMovementBL movementBL = Services.get(IMovementBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	// parameters
	private I_DD_Order _ddOrder;
	private Timestamp _movementDate;
	private int _locatorToId;

	// state
	private I_M_Movement _movement;

	private final void assumeNoMovementHeaderCreated()
	{
		Check.assumeNull(getMovementOrNull(), LiberoException.class, "movement header shall not be created");
	}

	@Override
	public void setDD_Order(final I_DD_Order ddOrder)
	{
		assumeNoMovementHeaderCreated();
		_ddOrder = ddOrder;
	}

	private I_DD_Order getDD_Order()
	{
		Check.assumeNotNull(_ddOrder, LiberoException.class, "ddOrder not null");
		return _ddOrder;
	}

	@Override
	public void setMovementDate(@NonNull final Date movementDate)
	{
		assumeNoMovementHeaderCreated();
		_movementDate = TimeUtil.asTimestamp((Date)movementDate.clone());
	}

	private Timestamp getMovementDate()
	{
		Check.assumeNotNull(_movementDate, LiberoException.class, "movementDate not null");
		return _movementDate;
	}

	private I_M_Movement getMovementOrNull()
	{
		return _movement;
	}

	@Override
	public void setLocatorToIdOverride(final int locatorToId)
	{
		_locatorToId = locatorToId;
	}

	private I_M_Movement getCreateMovementHeader()
	{
		if (_movement == null)
		{
			_movement = createMovement();
		}
		return _movement;
	}

	private I_M_Movement createMovement()
	{
		final I_DD_Order order = getDD_Order();
		final Timestamp movementDate = getMovementDate();

		final I_M_Movement movement = InterfaceWrapperHelper.newInstance(I_M_Movement.class);
		movement.setAD_Org_ID(order.getAD_Org_ID());

		//
		// Document Type
		final int docTypeId = docTypeDAO
				.getDocTypeId(DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_MaterialMovement)
						.adClientId(movement.getAD_Client_ID())
						.adOrgId(movement.getAD_Org_ID())
						.build());
		movement.setC_DocType_ID(docTypeId);

		//
		// Reference to DD Order
		movement.setDD_Order_ID(order.getDD_Order_ID());
		movement.setPOReference(order.getPOReference());
		movement.setDescription(order.getDescription());

		//
		// Internal contact user
		movement.setSalesRep_ID(order.getSalesRep_ID());

		//
		// BPartner (i.e. shipper BP)
		movement.setC_BPartner_ID(order.getC_BPartner_ID());
		movement.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());	// shipment address
		movement.setAD_User_ID(order.getAD_User_ID());

		//
		// Shipper
		movement.setM_Shipper_ID(order.getM_Shipper_ID());
		movement.setFreightCostRule(order.getFreightCostRule());
		movement.setFreightAmt(order.getFreightAmt());

		//
		// Delivery Rules & Priority
		movement.setDeliveryRule(order.getDeliveryRule());
		movement.setDeliveryViaRule(order.getDeliveryViaRule());
		movement.setPriorityRule(order.getPriorityRule());

		//
		// Dates
		movement.setMovementDate(movementDate);

		//
		// Charge (if any)
		movement.setC_Charge_ID(order.getC_Charge_ID());
		movement.setChargeAmt(order.getChargeAmt());

		//
		// Dimensions
		// 07689: This set of the activity is harmless, even though this column is currently hidden
		movement.setC_Activity_ID(order.getC_Activity_ID());
		movement.setC_Campaign_ID(order.getC_Campaign_ID());
		movement.setC_Project_ID(order.getC_Project_ID());
		movement.setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
		movement.setUser1_ID(order.getUser1_ID());
		movement.setUser2_ID(order.getUser2_ID());

		InterfaceWrapperHelper.save(movement);
		return movement;
	}

	@Override
	public I_M_MovementLine addMovementLineReceipt(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt)
	{
		final BigDecimal movementQtySrc = ddOrderBL.getQtyToReceive(ddOrderLineOrAlt);
		final I_C_UOM movementQtyUOM = ddOrderLineOrAlt.getC_UOM();
		return addMovementLineReceipt(ddOrderLineOrAlt, movementQtySrc, movementQtyUOM);
	}

	@Override
	public I_M_MovementLine addMovementLineReceipt(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt, final BigDecimal movementQtySrc, final I_C_UOM movementQtyUOM)
	{
		return addMovementLine(ddOrderLineOrAlt, MovementType.ReceiveFromTransit, movementQtySrc, movementQtyUOM);
	}

	@Override
	public I_M_MovementLine addMovementLineShipment(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt)
	{
		final BigDecimal movementQtySrc = ddOrderBL.getQtyToShip(ddOrderLineOrAlt);
		final I_C_UOM movementQtyUOM = ddOrderLineOrAlt.getC_UOM();
		return addMovementLineShipment(ddOrderLineOrAlt, movementQtySrc, movementQtyUOM);
	}

	@Override
	public I_M_MovementLine addMovementLineShipment(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt, final BigDecimal movementQtySrc, final I_C_UOM movementQtyUOM)
	{
		return addMovementLine(ddOrderLineOrAlt, MovementType.ShipToTransit, movementQtySrc, movementQtyUOM);
	}

	@Override
	public I_M_MovementLine addMovementLineDirect(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt, final BigDecimal movementQtySrc, final I_C_UOM movementQtyUOM)
	{
		return addMovementLine(ddOrderLineOrAlt, MovementType.Direct, movementQtySrc, movementQtyUOM);
	}

	private static enum MovementType
	{
		ShipToTransit, ReceiveFromTransit, Direct,
	};

	private I_M_MovementLine addMovementLine(final I_DD_OrderLine_Or_Alternative fromDDOrderLineOrAlt,
			final MovementType movementType,
			final BigDecimal movementQtySrc, final I_C_UOM movementQtyUOM)
	{
		final I_M_Movement movement = getCreateMovementHeader();

		final I_M_MovementLine movementLine = InterfaceWrapperHelper.newInstance(I_M_MovementLine.class);
		movementLine.setAD_Org_ID(movement.getAD_Org_ID());
		movementLine.setM_Movement(movement);

		//
		// Load actual interface which was passed on
		final I_DD_OrderLine ddOrderLine;
		final I_DD_OrderLine_Alternative ddOrderLineAlt;
		if (InterfaceWrapperHelper.isInstanceOf(fromDDOrderLineOrAlt, I_DD_OrderLine.class))
		{
			ddOrderLineAlt = null;
			ddOrderLine = InterfaceWrapperHelper.create(fromDDOrderLineOrAlt, I_DD_OrderLine.class);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(fromDDOrderLineOrAlt, I_DD_OrderLine_Alternative.class))
		{
			ddOrderLineAlt = InterfaceWrapperHelper.create(fromDDOrderLineOrAlt, I_DD_OrderLine_Alternative.class);
			ddOrderLine = ddOrderLineAlt.getDD_OrderLine();
		}
		else
		{
			//
			// Shall not happen; developer error
			throw new AdempiereException("Invalid I_DD_OrderLine_Or_Alternative implementation passed; Expected {} or {}, but was {}",
					new Object[] { I_DD_OrderLine.class, I_DD_OrderLine_Alternative.class, fromDDOrderLineOrAlt });
		}

		movementLine.setDD_OrderLine(ddOrderLine);
		movementLine.setDD_OrderLine_Alternative(ddOrderLineAlt);

		movementLine.setLine(ddOrderLine.getLine());
		movementLine.setDescription(ddOrderLine.getDescription());

		//
		// Product & ASI
		final I_M_Product product = fromDDOrderLineOrAlt.getM_Product();
		Check.assumeNotNull(product, LiberoException.class, "product not null");
		movementLine.setM_Product(product);
		movementLine.setM_AttributeSetInstance_ID(fromDDOrderLineOrAlt.getM_AttributeSetInstance_ID());
		movementLine.setM_AttributeSetInstanceTo_ID(ddOrderLine.getM_AttributeSetInstanceTo_ID());
		//

		final int locatorFromId;
		final int locatorToId;
		if (movementType == MovementType.ReceiveFromTransit)
		{
			locatorFromId = getInTransitLocatorId(ddOrderLine);
			locatorToId = getLocatorToId(ddOrderLine);
		}
		else if (movementType == MovementType.ShipToTransit)
		{
			locatorFromId = ddOrderLine.getM_Locator_ID();
			locatorToId = getInTransitLocatorId(ddOrderLine);
		}
		else if (movementType == MovementType.Direct)
		{
			locatorFromId = ddOrderLine.getM_Locator_ID();
			locatorToId = getLocatorToId(ddOrderLine);
		}
		else
		{
			throw new AdempiereException("Invalid movement type: " + movementType);
		}

		movementLine.setM_Locator_ID(locatorFromId);
		movementLine.setM_LocatorTo_ID(locatorToId);

		movementBL.setMovementQty(movementLine, movementQtySrc, movementQtyUOM);
		InterfaceWrapperHelper.save(movementLine);

		//
		// Set the activity from the warehouse of locator To
		// only in case it is different from the activity of the product acct (07629)
		movementBL.setC_Activities(movementLine);

		return movementLine;
	}

	private int getInTransitLocatorId(final I_DD_OrderLine ddOrderLine)
	{
		final I_DD_Order ddOrder = ddOrderLine.getDD_Order();
		final I_M_Warehouse warehouseInTransit = ddOrder.getM_Warehouse();
		final I_M_Locator locatorInTransit = warehouseBL.getDefaultLocator(warehouseInTransit);
		return locatorInTransit.getM_Locator_ID();
	}

	private int getLocatorToId(final I_DD_OrderLine ddOrderLine)
	{
		if (_locatorToId > 0)
		{
			return _locatorToId;
		}
		return ddOrderLine.getM_LocatorTo_ID();
	}

	@Override
	public I_M_Movement process()
	{
		final I_M_Movement movement = getMovementOrNull();
		Check.assumeNotNull(movement, LiberoException.class, "movement not null");

		documentBL.processEx(movement, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		return movement;
	}
}
