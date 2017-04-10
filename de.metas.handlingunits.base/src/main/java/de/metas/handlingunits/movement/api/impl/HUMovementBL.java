package de.metas.handlingunits.movement.api.impl;

import java.util.ArrayList;
import java.util.Collection;
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
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.interfaces.I_M_Movement;
import de.metas.product.acct.api.IProductAcctDAO;

public class HUMovementBL implements IHUMovementBL
{
	private I_M_Warehouse _directMoveWarehouse = null;
	private boolean _directMoveWarehouseLoaded = false;

	@Override
	public void createPackingMaterialMovementLines(final I_M_Movement movement)
	{
		HUPackingMaterialMovementLineAggregateBuilder.newInstance()
			.setM_Movement(movement)
			.createPackingMaterialMovementLines();
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
	public void assignHU(final org.compiere.model.I_M_MovementLine movementLine, final I_M_HU hu, final boolean isTransferPackingMaterials, final String trxName)
	{
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
		huAssignmentBL.assignHU(movementLine, hu, isTransferPackingMaterials, trxName);
	}

}
