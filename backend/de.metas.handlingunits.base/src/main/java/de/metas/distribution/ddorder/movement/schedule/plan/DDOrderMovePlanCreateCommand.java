package de.metas.distribution.ddorder.movement.schedule.plan;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.HUsLoadingCache;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHU;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsGetRequest;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsSupplier;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

public class DDOrderMovePlanCreateCommand
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final DDOrderLowLevelDAO ddOrderLowLevelDAO;

	private static final AdMessageKey MSG_CannotFullAllocate = AdMessageKey.of("de.metas.handlingunits.ddorder.api.impl.HUDDOrderBL.NoHu_For_Product");

	//
	// Parameters
	@NonNull private final I_DD_Order ddOrder;
	private final boolean failIfNotFullAllocated;

	//
	// State
	private final PickFromHUsSupplier pickFromHUsSupplier;
	private final HashMap<AllocationGroupingKey, AllocableHUsList> allocableHUsLists = new HashMap<>();

	@Builder
	private DDOrderMovePlanCreateCommand(
			final @NonNull DDOrderLowLevelDAO ddOrderLowLevelDAO,
			final @NonNull HUReservationService huReservationService,
			//
			final @NonNull DDOrderMovePlanCreateRequest request)
	{
		this.ddOrderLowLevelDAO = ddOrderLowLevelDAO;
		this.ddOrder = request.getDdOrder();
		this.failIfNotFullAllocated = request.isFailIfNotFullAllocated();

		this.pickFromHUsSupplier = PickFromHUsSupplier.builder()
				.huReservationService(huReservationService)
				.build();
	}

	public DDOrderMovePlan execute()
	{
		return DDOrderMovePlan.builder()
				.ddOrderId(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()))
				.lines(ddOrderLowLevelDAO.retrieveLines(ddOrder)
						.stream()
						.map(this::createPlanStep)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public DDOrderMovePlanLine createPlanStep(final I_DD_OrderLine ddOrderLine)
	{
		final ProductId productId = ProductId.ofRepoId(ddOrderLine.getM_Product_ID());
		final LocatorId pickFromLocatorId = warehouseDAO.getLocatorIdByRepoId(ddOrderLine.getM_Locator_ID());
		final LocatorId dropToLocatorId = warehouseDAO.getLocatorIdByRepoId(ddOrderLine.getM_LocatorTo_ID());
		final Quantity targetQty = getQtyEntered(ddOrderLine);

		final AllocableHUsList availableHUs = getAvailableHUsToPick(AllocationGroupingKey.builder()
				.productId(productId)
				.pickFromLocatorId(pickFromLocatorId)
				.build());
		final ArrayList<DDOrderMovePlanStep> planLines = new ArrayList<>();
		Quantity allocatedQty = targetQty.toZero();

		for (final AllocableHU allocableHU : availableHUs)
		{
			final Quantity remainingQtyToAllocate = targetQty.subtract(allocatedQty);
			if (remainingQtyToAllocate.signum() <= 0)
			{
				// if we allocated entire qty, exit
				break;
			}
			else // if (remainingQtyToAllocate.signum() > 0)
			{
				final Quantity huQtyAvailable = allocableHU.getQtyAvailableToAllocate();
				// TODO: handle/convert when HU's UOM != DD_OrderLine's UOM

				final DDOrderMovePlanStep planLine;
				if (huQtyAvailable.isGreaterThan(remainingQtyToAllocate))
				{
					planLine = DDOrderMovePlanStep.builder()
							.productId(productId)
							.pickFromLocatorId(pickFromLocatorId)
							.dropToLocatorId(dropToLocatorId)
							.pickFromHU(allocableHU.getHu())
							.qtyToPick(remainingQtyToAllocate)
							.isPickWholeHU(false)
							.build();
				}
				else
				{
					planLine = DDOrderMovePlanStep.builder()
							.productId(productId)
							.pickFromLocatorId(pickFromLocatorId)
							.dropToLocatorId(dropToLocatorId)
							.pickFromHU(allocableHU.getHu())
							.qtyToPick(huQtyAvailable)
							.isPickWholeHU(true)
							.build();
				}

				planLines.add(planLine);
				allocableHU.addQtyAllocated(planLine.getQtyToPick());
				allocatedQty = allocatedQty.add(planLine.getQtyToPick());
			}
		}

		final DDOrderMovePlanLine linePlan = DDOrderMovePlanLine.builder()
				.ddOrderLineId(DDOrderLineId.ofRepoId(ddOrderLine.getDD_OrderLine_ID()))
				.qtyToPickTarget(targetQty)
				.steps(ImmutableList.copyOf(planLines))
				.build();

		if (failIfNotFullAllocated && !linePlan.isFullyAllocated())
		{
			throw new HUException(MSG_CannotFullAllocate)
					.appendParametersToMessage()
					.setParameter("Product", ddOrderLine.getM_Product_ID())
					.setParameter("Locator", pickFromLocatorId);

		}

		return linePlan;
	}

	@NonNull
	private Quantity getQtyEntered(final @NonNull I_DD_OrderLine ddOrderLine)
	{
		final I_C_UOM uom = uomDAO.getById(ddOrderLine.getC_UOM_ID());
		return Quantity.of(ddOrderLine.getQtyEntered(), uom);
	}

	private AllocableHUsList getAvailableHUsToPick(AllocationGroupingKey key)
	{
		return allocableHUsLists.computeIfAbsent(key, this::retrieveAvailableHUsToPick);
	}

	private AllocableHUsList retrieveAvailableHUsToPick(AllocationGroupingKey key)
	{
		final ProductId productId = key.getProductId();
		final ImmutableList<PickFromHU> husEligibleToPick = pickFromHUsSupplier.getEligiblePickFromHUs(
				PickFromHUsGetRequest.builder()
						.pickFromLocatorId(key.getPickFromLocatorId())
						.productId(productId)
						.asiId(AttributeSetInstanceId.NONE)
						.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.Expiring_First)
						.reservationRef(Optional.empty()) // TODO introduce some DD Order Step reservation
						.build());

		final ImmutableList<AllocableHU> hus = CollectionUtils.map(husEligibleToPick, pickFromHU -> toAllocableHU(pickFromHU, productId));
		return new AllocableHUsList(hus);
	}

	private AllocableHU toAllocableHU(@NonNull final PickFromHU pickFromHU, @NonNull final ProductId productId)
	{
		final HUsLoadingCache husCache = pickFromHUsSupplier.getHusCache();
		final I_M_HU hu = husCache.getHUById(pickFromHU.getHuId());
		return new AllocableHU(storageFactory, hu, productId);
	}

	//
	//
	//

	@Value
	@Builder
	private static class AllocationGroupingKey
	{
		@NonNull ProductId productId;
		@NonNull LocatorId pickFromLocatorId;
	}

	private static class AllocableHU
	{
		private final IHUStorageFactory storageFactory;

		@Getter
		private final I_M_HU hu;

		private final ProductId productId;

		private Quantity _storageQty;
		private Quantity qtyAllocated;

		public AllocableHU(
				final IHUStorageFactory storageFactory,
				final I_M_HU hu,
				final ProductId productId)
		{
			this.storageFactory = storageFactory;
			this.hu = hu;
			this.productId = productId;
		}

		public Quantity getQtyAvailableToAllocate()
		{
			final Quantity qtyStorage = getStorageQty();
			return qtyAllocated != null
					? qtyStorage.subtract(qtyAllocated)
					: qtyStorage;
		}

		private Quantity getStorageQty()
		{
			Quantity storageQty = this._storageQty;
			if (storageQty == null)
			{
				storageQty = this._storageQty = storageFactory.getStorage(hu).getProductStorage(productId).getQty();
			}
			return storageQty;
		}

		public void addQtyAllocated(@NonNull final Quantity qtyAllocatedToAdd)
		{
			final Quantity newQtyAllocated = this.qtyAllocated != null
					? this.qtyAllocated.add(qtyAllocatedToAdd)
					: qtyAllocatedToAdd;

			final Quantity storageQty = getStorageQty();
			if (newQtyAllocated.isGreaterThan(storageQty))
			{
				throw new AdempiereException("Over-allocating is not allowed")
						.appendParametersToMessage()
						.setParameter("this.qtyAllocated", this.qtyAllocated)
						.setParameter("newQtyAllocated", newQtyAllocated)
						.setParameter("storageQty", storageQty);
			}

			this.qtyAllocated = newQtyAllocated;
		}
	}

	private static class AllocableHUsList implements Iterable<AllocableHU>
	{
		private final ImmutableList<AllocableHU> hus;

		private AllocableHUsList(@NonNull final ImmutableList<AllocableHU> hus) {this.hus = hus;}

		@Override
		public @NonNull Iterator<AllocableHU> iterator() {return hus.iterator();}
	}

}
