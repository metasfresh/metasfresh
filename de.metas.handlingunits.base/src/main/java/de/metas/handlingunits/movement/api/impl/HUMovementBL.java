package de.metas.handlingunits.movement.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.process.DocAction;
import org.compiere.util.Env;

import de.metas.adempiere.docline.sort.api.IDocLineSortDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.interfaces.I_M_Movement;
import de.metas.product.acct.api.IProductAcctDAO;

public class HUMovementBL implements IHUMovementBL
{
	private I_M_Warehouse _directMoveWarehouse = null;
	private boolean _directMoveWarehouseLoaded = false;

	@Override
	public void createPackingMaterialMovementLines(final I_M_Movement movement)
	{
		final HUPackingMaterialMovementLineAggregateBuilder builder = new HUPackingMaterialMovementLineAggregateBuilder();
		builder.setM_Movement(movement);
		builder.createPackingMaterialMovementLines();
	}

	@Override
	public void setPackingMaterialCActivity(final I_M_MovementLine movementLine)
	{
		final IContextAware contextAwareMovement = InterfaceWrapperHelper.getContextAware(movementLine);
		final I_M_Product product = movementLine.getM_Product();

		final IProductAcctDAO productAcctDAO = Services.get(IProductAcctDAO.class);

		final I_C_Activity productActivity = productAcctDAO // have multiple lines to easier locate problems in the stackstrace
				.retrieveActivityForAcct(contextAwareMovement,
						movementLine.getAD_Org(),
						product);

		final int productActivityID = productActivity == null ? -1 : productActivity.getC_Activity_ID();

		movementLine.setC_ActivityFrom_ID(productActivityID);
		movementLine.setC_Activity_ID(productActivityID);

		InterfaceWrapperHelper.save(movementLine);

	}

	@Override
	public I_M_Movement generateMovementFromPackingMaterialCandidates(final I_M_Warehouse warehouse, final boolean direction, final List<HUPackingMaterialDocumentLineCandidate> lines)
	{
		Check.assume(!lines.isEmpty(), "lines not empty");

		final Properties ctx = InterfaceWrapperHelper.getCtx(warehouse);
		final String trxName = InterfaceWrapperHelper.getTrxName(warehouse);

		final IContextAware contextAwareWarehouse = InterfaceWrapperHelper.getContextAware(warehouse);
		final Timestamp movementDate = Env.getDate(contextAwareWarehouse.getCtx());	// use Login date (08306)
		final I_M_Movement movement = generateMovementHeader(contextAwareWarehouse, movementDate);

		//
		// Sort lines by M_Product_ID
		final Comparator<Integer> productIdsComparator = Services.get(IDocLineSortDAO.class).findDocLineSort()
				.setContext(ctx)
				.setC_BPartner_ID(movement.getC_BPartner_ID())
				.setC_DocType(movement.getC_DocType())
				.findProductIdsComparator();
		Collections.sort(lines, HUPackingMaterialDocumentLineCandidate.comparatorFromProductIds(productIdsComparator));

		final I_M_Warehouse emptiesWarehouse = Services.get(IHandlingUnitsBL.class).getEmptiesWarehouse(ctx, warehouse, trxName);
		final I_M_Locator emptiesLocator = Services.get(IWarehouseBL.class).getDefaultLocator(emptiesWarehouse);
		for (final HUPackingMaterialDocumentLineCandidate line : lines)
		{
			generateMovementLine(
					movement,
					emptiesLocator,
					direction,
					line.getM_Product(),
					line.getQty().abs(),
					line.getM_Locator());
		}
		Services.get(IDocActionBL.class).processEx(movement, DocAction.ACTION_Complete, DocAction.STATUS_Completed);

		return movement;
	}

	@Override
	public I_M_Warehouse getDirectMove_Warehouse(final Properties ctx, final boolean throwEx)
	{
		if (_directMoveWarehouseLoaded)
		{
			return _directMoveWarehouse;
		}

		// Flag as loaded because we don't want to check it again
		_directMoveWarehouseLoaded = true;

		//
		// Get the M_Warehouse_ID
		final int directMoveWarehouseId = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_DirectMove_Warehouse_ID,
				-1, // defaultValue,
				Env.getAD_Client_ID(ctx), // AD_Client_ID
				Env.getAD_Org_ID(ctx) // AD_Org_ID
		);
		if (directMoveWarehouseId <= 0)
		{
			_directMoveWarehouse = null;
			_directMoveWarehouseLoaded = true;

			if (throwEx)
			{
				Check.errorIf(true, "Missing AD_SysConfig record with Name = {} for AD_Client_ID={} and AD_Org_ID={}",
						SYSCONFIG_DirectMove_Warehouse_ID, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
			}
			return null;
		}

		//
		// Load the warehouse
		_directMoveWarehouse = InterfaceWrapperHelper.create(ctx, directMoveWarehouseId, I_M_Warehouse.class, ITrx.TRXNAME_None);
		if (_directMoveWarehouse != null && _directMoveWarehouse.getM_Warehouse_ID() <= 0)
		{
			_directMoveWarehouse = null;
		}
		_directMoveWarehouseLoaded = true;

		//
		// Return loaded warehouse (or null)
		return _directMoveWarehouse;
	}

	@Override
	public List<I_M_Movement> generateMovementsToWarehouse(final I_M_Warehouse destinationWarehouse, final Collection<I_M_HU> hus, final IContextAware ctxAware)
	{
		//
		// iterate the HUs,
		// create one builder per source warehouse
		// add each HU to one builder
		final Map<Integer, HUMovementBuilder> warehouseId2builder = new HashMap<Integer, HUMovementBuilder>();

		for (final I_M_HU hu : hus)
		{
			final I_M_Locator huLocator = hu.getM_Locator();
			final int sourceWarehouseId = huLocator.getM_Warehouse_ID();
			HUMovementBuilder movementBuilder = warehouseId2builder.get(sourceWarehouseId);
			if (movementBuilder == null)
			{
				movementBuilder = new HUMovementBuilder();
				movementBuilder.setContextInitial(ctxAware);
				movementBuilder.setWarehouseFrom(huLocator.getM_Warehouse());
				movementBuilder.setWarehouseTo(destinationWarehouse);

				warehouseId2builder.put(sourceWarehouseId, movementBuilder);
			}
			movementBuilder.addHU(hu);
		}

		//
		// create the movements
		final List<I_M_Movement> result = new ArrayList<I_M_Movement>();
		for (final HUMovementBuilder builder : warehouseId2builder.values())
		{
			final I_M_Movement movement = builder.createMovement();
			if (movement != null)
			{
				result.add(movement);
			}
		}
		return result;
	}

	@Override
	public I_M_Movement generateMovementFromEmptiesInout(final I_M_InOut inout)
	{
		Check.assumeNotNull(inout, " Inout not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(inout);
		final String trxName = InterfaceWrapperHelper.getTrxName(inout);

		final IContextAware contextAwareInOut = InterfaceWrapperHelper.getContextAware(inout);
		final Timestamp movementDate = inout.getMovementDate();
		final I_M_Movement movement = generateMovementHeader(contextAwareInOut, movementDate);

		final I_M_Warehouse warehouse = inout.getM_Warehouse();

		final I_M_Warehouse emptiesWarehouse = Services.get(IHandlingUnitsBL.class).getEmptiesWarehouse(ctx, warehouse, trxName);

		final I_M_Locator emptiesLocator = Services.get(IWarehouseBL.class).getDefaultLocator(emptiesWarehouse);

		final boolean direction = inout.isSOTrx() ? true : false;

		final List<I_M_InOutLine> lines = Services.get(IInOutDAO.class).retrieveLines(inout);

		for (final I_M_InOutLine line : lines)
		{
			generateMovementLine(
					movement,
					emptiesLocator,
					direction,
					line.getM_Product(),
					line.getMovementQty(),
					line.getM_Locator());
		}

		Services.get(IDocActionBL.class).processEx(movement, DocAction.ACTION_Complete, DocAction.STATUS_Completed);

		return movement;
	}

	private I_M_Movement generateMovementHeader(final IContextAware contextAware, final Timestamp movementDate)
	{
		Check.assumeNotNull(movementDate, "movementDate not null");

		final I_M_Movement movement = InterfaceWrapperHelper.newInstance(I_M_Movement.class, contextAware);
		movement.setMovementDate(movementDate);
		movement.setDocStatus(DocAction.STATUS_Drafted);
		movement.setDocAction(DocAction.ACTION_Complete);

		InterfaceWrapperHelper.save(movement);

		return movement;
	}

	private I_M_MovementLine generateMovementLine(
			final I_M_Movement movement,
			final I_M_Locator emptiesLocator,
			final boolean direction,
			final I_M_Product product,
			final BigDecimal qty,
			final I_M_Locator locator)
	{
		final I_M_MovementLine movementLine = InterfaceWrapperHelper.newInstance(I_M_MovementLine.class, movement);
		movementLine.setAD_Org_ID(movement.getAD_Org_ID());
		movementLine.setM_Movement_ID(movement.getM_Movement_ID());

		movementLine.setM_Product(product);

		movementLine.setMovementQty(qty);

		final I_M_Locator locatorFrom;
		final I_M_Locator locatorTo;

		// in case the direction is on true, we have to move from the current warehouse to the gebinde one
		if (direction)
		{
			locatorFrom = locator;
			locatorTo = emptiesLocator;
		}

		else
		{
			locatorFrom = emptiesLocator;
			locatorTo = locator;
		}

		movementLine.setM_Locator(locatorFrom);
		movementLine.setM_LocatorTo(locatorTo);

		// the movement lines will be packing material ones
		movementLine.setIsPackagingMaterial(true);

		InterfaceWrapperHelper.save(movementLine);

		Services.get(IHUMovementBL.class).setPackingMaterialCActivity(movementLine);
		return movementLine;
	}

	@Override
	public void assignHU(final org.compiere.model.I_M_MovementLine movementLine, final I_M_HU hu, final boolean isTransferPackingMaterials, final String trxName)
	{
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
		huAssignmentBL.assignHU(movementLine, hu, isTransferPackingMaterials, trxName);
	}

}
