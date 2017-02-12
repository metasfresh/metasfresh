package de.metas.fresh.picking.service.impl;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.form.IPackingItem;
import de.metas.fresh.picking.form.IFreshPackingItem;
import de.metas.fresh.picking.service.IPackingContext;
import de.metas.fresh.picking.service.IPackingHandler;
import de.metas.fresh.picking.service.IPackingService;
import de.metas.fresh.picking.service.PackingHandlerAdapter;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.impl.AbstractShipmentScheduleQtyPickedBuilder;
import de.metas.handlingunits.shipmentschedule.api.impl.ShipmentScheduleQtyPickedProductStorage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;

/**
 * Class responsible for allocating given HUs to underlying shipment schedules from {@link IFreshPackingItem}.
 * 
 * As a result of an allocation, you shall get:
 * <ul>
 * <li>From {@link #getItemToPack()}'s Qty, the HU's Qtys will be subtracted
 * <li>to {@link IPackingContext#getPackingItemsMap()} we will have newly packed items and also current Item to Pack
 * <li>{@link I_M_ShipmentSchedule_QtyPicked} records will be created (shipment schedules are taken from Item to Pack)
 * </ul>
 * 
 * @author tsa
 * 
 */
public class HU2PackingItemsAllocator extends AbstractShipmentScheduleQtyPickedBuilder
{
	//
	// Services
	private final transient IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final transient IPackingService packingService = Services.get(IPackingService.class);
	private final transient IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

	/**
	 * Cannot fully load:
	 */
	private static final String ERR_CANNOT_FULLY_LOAD = "@CannotFullyLoad@ {}";

	//
	// Parameters
	private IPackingContext _packingContext;
	private IFreshPackingItem _itemToPack;

	public HU2PackingItemsAllocator()
	{
		super();
	}

	@Override
	protected IHUContext createHUContextInitial()
	{
		final IPackingContext packingContext = getPackingContext();
		final Properties ctx = packingContext.getCtx();
		final PlainContextAware contextProvider = new PlainContextAware(ctx, ITrx.TRXNAME_ThreadInherited);
		final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing(contextProvider);
		return huContext;
	}

	public void setPackingContext(final IPackingContext packingContext)
	{
		this._packingContext = packingContext;
	}

	private IPackingContext getPackingContext()
	{
		Check.assumeNotNull(_packingContext, "packingContext set");
		return _packingContext;
	}

	public void setItemToPack(final IFreshPackingItem itemToPack)
	{
		this._itemToPack = itemToPack;
	}

	private IFreshPackingItem getItemToPack()
	{
		Check.assumeNotNull(_itemToPack, "itemToPack set");
		return _itemToPack;
	}

	private I_M_Product getM_Product()
	{
		final IFreshPackingItem itemToPack = getItemToPack();
		return itemToPack.getM_Product();
	}

	@Override
	protected void allocateVHU(final I_M_HU vhu)
	{
		// Make sure we have remaining qty to pack
		if (!hasRemainingQtyToPack())
		{
			return;
		}

		final I_M_Product product = getM_Product();
		final IProductStorage vhuProductStorage = getProductStorage(vhu, product);
		if (vhuProductStorage == null)
		{
			return;
		}

		final BigDecimal qtyToPackSrc = vhuProductStorage.getQty();
		final I_C_UOM qtyToPackSrcUOM = vhuProductStorage.getC_UOM();

		//
		final IFreshPackingItem itemToPack = getItemToPack();
		final I_C_UOM qtyToPackUOM = itemToPack.getC_UOM();
		BigDecimal qtyToPack = uomConversionBL.convertQty(product, qtyToPackSrc, qtyToPackSrcUOM, qtyToPackUOM);
		qtyToPack = adjustQtyToPackConsideringRemaining(qtyToPack, qtyToPackUOM);
		if (qtyToPack.signum() <= 0)
		{
			// nothing to pack here
			return;
		}

		//
		// Pack the qtyToPack from VHU,
		// back allocate it to current shipment schedules,
		// and if configured, transfer those back-allocated quantities to the Target HU.
		final IPackingHandler itemPackedProcessor = new PackingHandlerAdapter()
		{
			@Override
			public void itemPacked(final IPackingItem itemPacked)
			{
				for (final I_M_ShipmentSchedule sched : itemPacked.getShipmentSchedules())
				{
					final BigDecimal schedQty = itemPacked.getQtyForSched(sched); // qty to pack, available on current shipment schedule
					final I_C_UOM uom = itemPacked.getC_UOM();
					onQtyAllocated(sched, schedQty, uom, vhu);
				}
			}
		};

		final IPackingContext packingContext = getPackingContext();
		packingService.packItem(packingContext, itemToPack, qtyToPack, itemPackedProcessor);
	}

	@Override
	protected final void transferRemainingQtyToTargetHU()
	{
		if (!isQtyToPackEnforced())
		{
			return;
		}

		final BigDecimal qtyToPack = getQtyToPackRemaining(); // in itemToPack's UOM
		if (qtyToPack.signum() <= 0)
		{
			return;
		}

		final IFreshPackingItem itemToPack = getItemToPack();

		final IPackingContext packingContext = getPackingContext();
		final IPackingHandler itemPackedProcessor = new PackingHandlerAdapter()
		{
			@Override
			public boolean isPackingAllowedForShipmentSchedule(final I_M_ShipmentSchedule shipmentSchedule)
			{
				// We are accepting only those shipment schedules which have Force Delivery
				final String deliveryRule = shipmentScheduleEffectiveBL.getDeliveryRule(shipmentSchedule);
				final boolean forceDelivery = X_M_ShipmentSchedule.DELIVERYRULE_Force.equals(deliveryRule);
				if (!forceDelivery)
				{
					return false;
				}

				return true;
			}

			@Override
			public void itemPacked(final IPackingItem item)
			{
				transferQtyToTargetHU(item.getQtys());
			}
		};
		packingService.packItem(packingContext, itemToPack, qtyToPack, itemPackedProcessor);
	}

	private void transferQtyToTargetHU(final Map<I_M_ShipmentSchedule, BigDecimal> schedules2qty)
	{
		for (final Map.Entry<I_M_ShipmentSchedule, BigDecimal> e : schedules2qty.entrySet())
		{
			final I_M_ShipmentSchedule schedule = e.getKey();
			final BigDecimal qty = e.getValue();
			final I_C_UOM uom = Services.get(IShipmentScheduleBL.class).getC_UOM(schedule);

			transferQtyToTargetHU(schedule, qty, uom);
		}
	}

	private void transferQtyToTargetHU(final I_M_ShipmentSchedule schedule, final BigDecimal qty, final I_C_UOM uom)
	{
		//
		// Allocation Request
		final IAllocationRequest request = createShipmentScheduleAllocationRequest(schedule, qty, uom);

		//
		// Allocation Source
		final ShipmentScheduleQtyPickedProductStorage shipmentScheduleQtyPickedStorage = new ShipmentScheduleQtyPickedProductStorage(schedule);
		final GenericAllocationSourceDestination source = new GenericAllocationSourceDestination(shipmentScheduleQtyPickedStorage, schedule);

		//
		// Allocation Destination
		final I_M_HU targetHU = getTargetHU();
		final HUListAllocationSourceDestination destination = new HUListAllocationSourceDestination(targetHU);

		//
		// Move Qty from shipment schedules to current HU
		final HULoader loader = new HULoader(source, destination);
		final IAllocationResult result = loader.load(request);

		// Make sure result is completed
		// NOTE: this shall not happen because "forceQtyAllocation" is set to true
		if (!result.isCompleted())
		{
			final String errmsg = MessageFormat.format(ERR_CANNOT_FULLY_LOAD, targetHU);
			throw new AdempiereException(errmsg);
		}
	}

}
