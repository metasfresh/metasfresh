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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.annotations.VisibleForTesting;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.util.HUByIdComparator;
import de.metas.interfaces.I_M_Movement;
import de.metas.product.IProductBL;
import lombok.NonNull;

/**
 * Generate {@link I_M_Movement} to move given {@link I_M_HU}s
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08205_HU_Pos_Inventory_move_Button_%28105838505937%29
 */
public class HUMovementBuilder
{
	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final transient IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);

	//
	// Parameters
	private IContextAware _contextInitial;
	private Date _movementDate;
	private I_M_Warehouse _warehouseFrom;
	private I_M_Locator _locatorFrom;
	private I_M_Warehouse _warehouseTo;
	private I_M_Locator _locatorTo;
	private String _description;
	private final Set<I_M_HU> _husToMove = new TreeSet<>(HUByIdComparator.instance);
	private final List<I_M_HU> _husMoved = new ArrayList<>();

	//
	// Status
	private IHUContext _huContext;
	private I_M_Movement _movement;
	private final Map<ArrayKey, I_M_MovementLine> _movementLines = new HashMap<>();

	public HUMovementBuilder()
	{
		super();
	}

	public HUMovementBuilder setContextInitial(final IContextAware context)
	{
		_contextInitial = context;
		return this;
	}

	public HUMovementBuilder setContextInitial(final Properties ctx)
	{
		_contextInitial = PlainContextAware.newOutOfTrxAllowThreadInherited(ctx);
		return this;
	}

	private final IContextAware getContextInitial()
	{
		Check.assumeNotNull(_contextInitial, "_contextInitial not null");
		return _contextInitial;
	}

	private IHUContext getHUContext()
	{
		Check.assumeNotNull(_huContext, "_huContext not null");
		return _huContext;
	}

	private final void setHUContext(final IHUContext huContext)
	{
		Check.assumeNotNull(huContext, "huContext not null");
		Check.assumeNull(_huContext, "huContext not already configured");
		_huContext = huContext;
	}

	public final Date getMovementDate()
	{
		return _movementDate;
	}

	public final HUMovementBuilder setMovementDate(final Date movementDate)
	{
		_movementDate = movementDate;
		return this;
	}

	public HUMovementBuilder setWarehouseFrom(final I_M_Warehouse warehouseFrom)
	{
		_warehouseFrom = warehouseFrom;
		_locatorFrom = null;
		return this;
	}

	public HUMovementBuilder setLocatorFrom(@NonNull final I_M_Locator locatorFrom)
	{
		_warehouseFrom = locatorFrom.getM_Warehouse();
		_locatorFrom = locatorFrom;
		return this;
	}

	private final I_M_Warehouse getWarehouseFrom()
	{
		Check.assumeNotNull(_warehouseFrom, "_warehouseFrom not null");
		return _warehouseFrom;
	}

	private final I_M_Locator getLocatorFrom()
	{
		if (_locatorFrom == null)
		{
			final I_M_Warehouse warehouseFrom = getWarehouseFrom();
			_locatorFrom = warehouseBL.getDefaultLocator(warehouseFrom);
		}
		return _locatorFrom;
	}

	public HUMovementBuilder setWarehouseTo(final I_M_Warehouse warehouseTo)
	{
		_warehouseTo = warehouseTo;
		_locatorTo = null;
		return this;
	}

	private final I_M_Warehouse getWarehouseTo()
	{
		Check.assumeNotNull(_warehouseTo, "_warehouseTo not null");
		return _warehouseTo;
	}
	
	public HUMovementBuilder setLocatorTo(@NonNull final I_M_Locator locatorTo)
	{
		_warehouseTo = locatorTo.getM_Warehouse();
		_locatorTo = locatorTo;
		return this;
	}

	private final I_M_Locator getLocatorTo()
	{
		if (_locatorTo == null)
		{
			final I_M_Warehouse warehouseTo = getWarehouseTo();
			_locatorTo = warehouseBL.getDefaultLocator(warehouseTo);
		}
		return _locatorTo;
	}

	private final String getDescription()
	{
		return _description;
	}

	public HUMovementBuilder setDescription(final String description)
	{
		_description = description;
		return this;
	}

	public HUMovementBuilder addHU(@NonNull final I_M_HU hu)
	{
		// Only top level HUs can be moved
		if (!handlingUnitsBL.isTopLevel(hu))
		{
			throw new HUException("Only top level HUs can be moved")
					.setParameter("hu", hu);
		}

		//
		// HU's locator shall match movement's From Locator
		final I_M_Locator locatorFrom = getLocatorFrom();
		if (locatorFrom.getM_Locator_ID() != hu.getM_Locator_ID())
		{
			throw new HUException("HU's locator does not match movement's locator from."
					+ "\n Movement Locator From: " + locatorFrom
					+ "\n HU's Locator: " + hu.getM_Locator());
		}

		_husToMove.add(hu);
		return this;
	}

	private final Set<I_M_HU> getHUsToMove()
	{
		return _husToMove;
	}

	final List<I_M_HU> getHUsMoved()
	{
		return _husMoved;
	}

	private final void addHUMoved(final I_M_HU hu)
	{
		_husMoved.add(hu);
	}

	/**
	 * Create and process the movement. Note that this BL only creates lines for the goods within the HUs,
	 * but there is a model interceptor that creates the packing material lines was soon as the M_Movement is prepared.
	 *
	 * @return movement
	 */
	public I_M_Movement createMovement()
	{
		final IContextAware contextInitial = getContextInitial();
		huTrxBL.createHUContextProcessorExecutor(contextInitial)
				.run(new IHUContextProcessor()
				{
					@Override
					public IMutableAllocationResult process(final IHUContext huContext)
					{
						setHUContext(huContext);
						createMovement0();
						return NULL_RESULT; // we don't care about the result
					}
				});

		//
		// Get the generated movement (if any)
		// * set it to initial transaction
		// * return the mother fucker
		final I_M_Movement movement = getMovementHeaderOrNull();
		if (movement == null)
		{
			return null;
		}
		InterfaceWrapperHelper.setTrxName(movement, contextInitial.getTrxName());
		return movement;
	}

	@VisibleForTesting
	/* package */ void createMovement0()
	{
		//
		// Get the HUs to move
		final Set<I_M_HU> husToMove = getHUsToMove();
		if (Check.isEmpty(husToMove))
		{
			throw new HUException("@NoSelection@ (@M_HU_ID@)");
		}

		final IHUContext huContext = getHUContext();
		final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();

		//
		// Iterate the HUs to move and create the movement lines for them
		for (final I_M_HU hu : husToMove)
		{
			//
			// Take out the HU from it's parent if needed
			extractHUFromParentIfNeeded(hu);

			// At this point we assume our HU is top level
			if (!handlingUnitsBL.isTopLevel(hu))
			{
				throw new HUException("@M_HU_ID@ @TopLevel@=@N@: " + hu);
			}

			//
			// Iterate the product storages of this HU and create/update the movement lines
			final IHUStorage huStorage = huStorageFactory.getStorage(hu);
			final List<IHUProductStorage> productStorages = huStorage.getProductStorages();
			if (!productStorages.isEmpty())
			{
				for (final IHUProductStorage productStorage : productStorages)
				{
					updateMovementLine(productStorage);
				}
				
				addHUMoved(hu);
			}
		}

		//
		// Complete the movement (if any)
		final I_M_Movement movement = getMovementHeaderOrNull();
		if (movement != null)
		{
			docActionBL.processEx(movement, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}
	}

	/**
	 * Take out the given HU from it's parent (if it's not already a top level HU)
	 *
	 * @param hu
	 */
	private void extractHUFromParentIfNeeded(final I_M_HU hu)
	{
		if (handlingUnitsBL.isTopLevel(hu))
		{
			return;
		}

		final IHUContext huContext = getHUContext();
		final I_M_HU_Item parentHUItem = null; // no parent
		huTrxBL.setParentHU(huContext, parentHUItem, hu);
	}

	private final I_M_Movement getCreateMovementHeader()
	{
		if (_movement != null)
		{
			return _movement;
		}

		final IHUContext huContext = getHUContext();
		_movement = InterfaceWrapperHelper.newInstance(I_M_Movement.class, huContext);

		_movement.setDocStatus(IDocument.STATUS_Drafted);
		_movement.setDocAction(IDocument.ACTION_Complete);

		final Date movementDate = getMovementDate();
		if (movementDate != null)
		{
			_movement.setMovementDate(TimeUtil.asTimestamp(movementDate));
		}

		_movement.setDescription(getDescription());

		InterfaceWrapperHelper.save(_movement);

		return _movement;
	}

	private final I_M_Movement getMovementHeaderOrNull()
	{
		return _movement;
	}

	private void updateMovementLine(final IHUProductStorage productStorage)
	{
		// Skip it if product storage is empty
		if (productStorage.isEmpty())
		{
			return;
		}

		final I_M_Product product = productStorage.getM_Product();
		final I_M_MovementLine movementLine = getCreateMovementLine(product);

		final I_C_UOM productUOM = productBL.getStockingUOM(product);
		final BigDecimal qtyToMove = productStorage.getQty(productUOM);

		//
		// Adjust movement line's qty to move
		final BigDecimal movementLine_Qty_Old = movementLine.getMovementQty();
		final BigDecimal movementLine_Qty_New = movementLine_Qty_Old.add(qtyToMove);
		movementLine.setMovementQty(movementLine_Qty_New);

		// Make sure movement line it's saved
		InterfaceWrapperHelper.save(movementLine);

		// Assign the HU to movement line
		{
			final I_M_HU hu = productStorage.getM_HU();
			final boolean isTransferPackingMaterials = true;
			final String trxName = ITrx.TRXNAME_ThreadInherited;
			huAssignmentBL.assignHU(movementLine, hu, isTransferPackingMaterials, trxName);
		}
	}

	private I_M_MovementLine getCreateMovementLine(final I_M_Product product)
	{
		//
		// Check if we already have a movement line for our key
		final ArrayKey movementLineKey = mkMovementLineKey(product);
		I_M_MovementLine movementLine = _movementLines.get(movementLineKey);
		if (movementLine != null)
		{
			return movementLine;
		}

		//
		// Create a new movement line
		final I_M_Movement movement = getCreateMovementHeader();
		movementLine = InterfaceWrapperHelper.newInstance(I_M_MovementLine.class, movement);
		movementLine.setAD_Org_ID(movement.getAD_Org_ID());
		movementLine.setM_Movement_ID(movement.getM_Movement_ID());

		movementLine.setIsPackagingMaterial(false);

		movementLine.setM_Product(product);
		// movementLine.setMovementQty(qty);

		final I_M_Locator locatorFrom = getLocatorFrom();
		final I_M_Locator locatorTo = getLocatorTo();

		movementLine.setM_Locator(locatorFrom);
		movementLine.setM_LocatorTo(locatorTo);

		// NOTE: we are not saving the movement line

		//
		// Add the movement line to our map (to not created it again)
		_movementLines.put(movementLineKey, movementLine);

		return movementLine;
	}

	private ArrayKey mkMovementLineKey(final I_M_Product product)
	{
		return Util.mkKey(product.getM_Product_ID());
	}

}
