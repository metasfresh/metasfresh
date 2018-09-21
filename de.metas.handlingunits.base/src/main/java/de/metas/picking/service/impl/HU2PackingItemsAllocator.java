package de.metas.picking.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.UOMConversionContext;
import org.compiere.model.I_C_UOM;

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
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.picking.legacy.form.IPackingItem;
import de.metas.picking.service.IFreshPackingItem;
import de.metas.picking.service.IPackingHandler;
import de.metas.picking.service.IPackingService;
import de.metas.picking.service.PackingContext;
import de.metas.picking.service.PackingHandlerAdapter;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/**
 * Class responsible for allocating given HUs to underlying shipment schedules from {@link IFreshPackingItem}.
 *
 * As a result of an allocation, you shall get:
 * <ul>
 * <li>From {@link #getItemToPack()}'s Qty, the HU's Qtys will be subtracted
 * <li>to {@link PackingContext#getPackingItemsMap()} we will have newly packed items and also current Item to Pack
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
	private final PackingContext _packingContext;
	private final IFreshPackingItem _itemToPack;
	private final boolean _allowOverDelivery;

	@Builder
	private HU2PackingItemsAllocator(
			@NonNull final PackingContext packingContext,
			@NonNull final IFreshPackingItem itemToPack,
			final boolean allowOverDelivery)
	{
		this._packingContext = packingContext;
		this._itemToPack = itemToPack;
		this._allowOverDelivery = allowOverDelivery;

	}

	@Override
	protected IHUContext createHUContextInitial()
	{
		final PackingContext packingContext = getPackingContext();
		final Properties ctx = packingContext.getCtx();
		final PlainContextAware contextProvider = PlainContextAware.newWithThreadInheritedTrx(ctx);
		final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing(contextProvider);
		return huContext;
	}

	private PackingContext getPackingContext()
	{
		return _packingContext;
	}

	private IFreshPackingItem getItemToPack()
	{
		return _itemToPack;
	}

	private boolean isAllowOverdelivery()
	{
		return _allowOverDelivery;
	}

	private ProductId getProductId()
	{
		return getItemToPack().getProductId();
	}

	@Override
	protected void allocateVHU(@NonNull final I_M_HU vhu)
	{
		// Make sure we have remaining qty to pack
		if (!hasRemainingQtyToPack())
		{
			return;
		}

		final ProductId productId = getProductId();
		final IProductStorage vhuProductStorage = getProductStorage(vhu, productId);
		if (vhuProductStorage == null)
		{
			return;
		}

		final Quantity qtyToPackSrc = Quantity.of(
				vhuProductStorage.getQty(),
				vhuProductStorage.getC_UOM());

		final IFreshPackingItem itemToPack = getItemToPack();
		final I_C_UOM qtyToPackUOM = itemToPack.getC_UOM();
		Quantity qtyToPack = uomConversionBL.convertQuantityTo(qtyToPackSrc, UOMConversionContext.of(productId), qtyToPackUOM);

		//
		qtyToPack = adjustQtyToPackConsideringRemaining(qtyToPack);
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
					final Quantity schedQty = itemPacked.getQtyForSched(sched); // qty to pack, available on current shipment schedule

					if (!isAllowOverdelivery())
					{
						validateQtyPicked(sched, schedQty);
					}
					if (schedQty.signum() != 0)
					{
						// gh #1712: only create M_ShipmentSchedule_QtyPicked etc etc for 'sched' if there is an actual quantity.
						onQtyAllocated(sched, schedQty, vhu);
					}
				}
			}
		};

		final PackingContext packingContext = getPackingContext();
		packingService.packItem(packingContext, itemToPack, qtyToPack, itemPackedProcessor);
	}

	private void validateQtyPicked(final I_M_ShipmentSchedule schedule, final Quantity qtyPickCandidate)
	{
		final BigDecimal currentQtyToDeliver = schedule.getQtyToDeliver();

		if (currentQtyToDeliver.compareTo(qtyPickCandidate.getAsBigDecimal()) < 0)
		{
			throw new AdempiereException("@" + PickingConfigRepository.MSG_WEBUI_Picking_OverdeliveryNotAllowed + "@");
		}
	}

	@Override
	protected final void transferRemainingQtyToTargetHU()
	{
		if (!isQtyToPackEnforced())
		{
			return;
		}

		final Quantity qtyToPack = getQtyToPackRemaining();
		if (qtyToPack.signum() <= 0)
		{
			return;
		}

		final IPackingHandler itemPackedProcessor = new PackingHandlerAdapter()
		{
			@Override
			public boolean isPackingAllowedForShipmentSchedule(final I_M_ShipmentSchedule shipmentSchedule)
			{
				// We are accepting only those shipment schedules which have Force Delivery
				final String deliveryRule = shipmentScheduleEffectiveBL.getDeliveryRule(shipmentSchedule);
				return X_M_ShipmentSchedule.DELIVERYRULE_Force.equals(deliveryRule);
			}

			@Override
			public void itemPacked(final IPackingItem item)
			{
				transferQtyToTargetHU(item.getQtys());
			}
		};

		final PackingContext packingContext = getPackingContext();
		final IFreshPackingItem itemToPack = getItemToPack();
		packingService.packItem(packingContext, itemToPack, qtyToPack, itemPackedProcessor);
	}

	private void transferQtyToTargetHU(final Map<I_M_ShipmentSchedule, Quantity> schedules2qty)
	{
		schedules2qty.forEach(this::transferQtyToTargetHU);
	}

	private void transferQtyToTargetHU(final I_M_ShipmentSchedule schedule, final Quantity qty)
	{
		//
		// Allocation Request
		final IAllocationRequest request = createShipmentScheduleAllocationRequest(schedule, qty);

		//
		// Allocation Source
		final ShipmentScheduleQtyPickedProductStorage shipmentScheduleQtyPickedStorage = new ShipmentScheduleQtyPickedProductStorage(schedule);
		final GenericAllocationSourceDestination source = new GenericAllocationSourceDestination(shipmentScheduleQtyPickedStorage, schedule);

		//
		// Allocation Destination
		final I_M_HU targetHU = getTargetHU();
		final HUListAllocationSourceDestination destination = HUListAllocationSourceDestination.of(targetHU);

		//
		// Move Qty from shipment schedules to current HU
		final IAllocationResult result = HULoader.of(source, destination)
				.load(request);

		// Make sure result is completed
		// NOTE: this shall not happen because "forceQtyAllocation" is set to true
		if (!result.isCompleted())
		{
			final String errmsg = MessageFormat.format(ERR_CANNOT_FULLY_LOAD, targetHU);
			throw new AdempiereException(errmsg);
		}
	}

}
