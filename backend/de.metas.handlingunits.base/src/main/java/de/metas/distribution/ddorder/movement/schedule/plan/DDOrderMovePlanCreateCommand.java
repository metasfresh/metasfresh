package de.metas.distribution.ddorder.movement.schedule.plan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.handlingunits.HuId;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public class DDOrderMovePlanCreateCommand
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final DDOrderLowLevelDAO ddOrderLowLevelDAO;
	private final HUReservationService huReservationService;

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
		this.huReservationService = huReservationService;
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
		final ArrayList<DDOrderMovePlanStep> planSteps = new ArrayList<>();
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

				// TODO 2: atm we are always considering top level HUs and never break them
				final DDOrderMovePlanStep planStep = DDOrderMovePlanStep.builder()
						.productId(productId)
						.pickFromLocatorId(pickFromLocatorId)
						.dropToLocatorId(dropToLocatorId)
						.pickFromHU(allocableHU.getTopLevelHU())
						.qtyToPick(huQtyAvailable)
						.isPickWholeHU(true)
						.build();

				planSteps.add(planStep);
				allocableHU.addQtyAllocated(planStep.getQtyToPick());
				allocatedQty = allocatedQty.add(planStep.getQtyToPick());
			}
		}

		final DDOrderMovePlanLine planLine = DDOrderMovePlanLine.builder()
				.ddOrderLineId(DDOrderLineId.ofRepoId(ddOrderLine.getDD_OrderLine_ID()))
				.qtyToPickTarget(targetQty)
				.steps(ImmutableList.copyOf(planSteps))
				.build();

		if (failIfNotFullAllocated && !planLine.isFullyAllocated())
		{
			throw new HUException(MSG_CannotFullAllocate)
					.appendParametersToMessage()
					.setParameter("Product", ddOrderLine.getM_Product_ID())
					.setParameter("Locator", pickFromLocatorId);

		}

		return planLine;
	}

	@NonNull
	private Quantity getQtyEntered(final @NonNull I_DD_OrderLine ddOrderLine)
	{
		final I_C_UOM uom = uomDAO.getById(ddOrderLine.getC_UOM_ID());
		return Quantity.of(ddOrderLine.getQtyEntered(), uom);
	}

	private AllocableHUsList getAvailableHUsToPick(final @NonNull AllocationGroupingKey key)
	{
		return allocableHUsLists.computeIfAbsent(key, this::retrieveAvailableHUsToPick);
	}

	private AllocableHUsList retrieveAvailableHUsToPick(final @NonNull AllocationGroupingKey key)
	{
		final ProductId productId = key.getProductId();
		final ImmutableList<PickFromHU> husEligibleToPick = pickFromHUsSupplier.getEligiblePickFromHUs(
				PickFromHUsGetRequest.builder()
						.pickFromLocatorId(key.getPickFromLocatorId())
						.productId(productId)
						.asiId(AttributeSetInstanceId.NONE)
						.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.Expiring_First)
						.reservationRef(Optional.empty()) // TODO introduce some DD Order Step reservation
						.enforceMandatoryAttributesOnPicking(false)
						.build());

		final ImmutableList<AllocableHU> hus = husEligibleToPick.stream()
				.map(pickFromHU -> toAllocableHU(pickFromHU, productId))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		return new AllocableHUsList(hus);
	}

	@Nullable
	private AllocableHU toAllocableHU(@NonNull final PickFromHU pickFromHU, @NonNull final ProductId productId)
	{
		final HUsLoadingCache husCache = pickFromHUsSupplier.getHusCache();
		final HuId topLevelHUId = pickFromHU.getTopLevelHUId();

		//
		// Make sure our top level HU is not already reserved to somebody else
		// TODO in future we shall check/accept if the VHU was already reserved for one of our DD_OrderLine(s)
		final ImmutableSet<HuId> vhuIds = husCache.getVHUIds(topLevelHUId);
		if (huReservationService.isAnyOfVHUIdsReserved(vhuIds))
		{
			return null;
		}

		final I_M_HU topLevelHU = husCache.getHUById(topLevelHUId);
		return new AllocableHU(storageFactory, topLevelHU, productId);
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
		private final I_M_HU topLevelHU;

		private final ProductId productId;

		private Quantity _storageQty;
		private Quantity qtyAllocated;

		public AllocableHU(
				final IHUStorageFactory storageFactory,
				final I_M_HU topLevelHU,
				final ProductId productId)
		{
			this.storageFactory = storageFactory;
			this.topLevelHU = topLevelHU;
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
				storageQty = this._storageQty = storageFactory.getStorage(topLevelHU).getProductStorage(productId).getQty();
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
