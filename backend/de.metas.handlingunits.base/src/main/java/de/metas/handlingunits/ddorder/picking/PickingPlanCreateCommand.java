package de.metas.handlingunits.ddorder.picking;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
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
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.DDOrderId;
import org.eevolution.api.DDOrderLineId;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PickingPlanCreateCommand
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);

	private static final AdMessageKey MSG_CannotFullAllocate = AdMessageKey.of("de.metas.handlingunits.ddorder.api.impl.HUDDOrderBL.NoHu_For_Product");

	//
	// Parameters
	@NonNull private final I_DD_Order ddOrder;
	private final boolean failIfNotFullAllocated;

	//
	// State
	private final HashMap<AllocationGroupingKey, AllocableHUsList> allocableHUsLists = new HashMap<>();

	@Builder
	private PickingPlanCreateCommand(final @NonNull PickingPlanCreateRequest request)
	{
		this.ddOrder = request.getDdOrder();
		this.failIfNotFullAllocated = request.isFailIfNotFullAllocated();
	}

	public DDOrderPickPlan execute()
	{
		return DDOrderPickPlan.builder()
				.ddOrderId(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()))
				.lines(ddOrderDAO.retrieveLines(ddOrder)
						.stream()
						.map(this::createLinePickPlan)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public DDOrderLinePickPlan createLinePickPlan(final I_DD_OrderLine ddOrderLine)
	{
		final ProductId productId = ProductId.ofRepoId(ddOrderLine.getM_Product_ID());
		final LocatorId locatorId = warehouseDAO.getLocatorIdByRepoId(ddOrderLine.getM_Locator_ID());
		final Quantity targetQty = getQtyEntered(ddOrderLine);

		final AllocableHUsList availableHUs = getAvailableHUsToPick(AllocationGroupingKey.builder()
				.productId(productId)
				.locatorId(locatorId)
				.build());
		final ArrayList<DDOrderLineStepPlan> planLines = new ArrayList<>();
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

				final DDOrderLineStepPlan planLine;
				if (huQtyAvailable.isGreaterThan(remainingQtyToAllocate))
				{
					//final List<I_M_HU> extractedCUs = splitToCUs(allocableHU, remainingQtyToAllocate);
					planLine = DDOrderLineStepPlan.builder()
							.pickFromHU(allocableHU.getHu())
							.qtyToPick(remainingQtyToAllocate)
							.isPickWholeHU(false)
							.build();
				}
				else
				{
					planLine = DDOrderLineStepPlan.builder()
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

		final DDOrderLinePickPlan linePlan = DDOrderLinePickPlan.builder()
				.ddOrderLineId(DDOrderLineId.ofRepoId(ddOrderLine.getDD_OrderLine_ID()))
				.qtyToPickTarget(targetQty)
				.steps(ImmutableList.copyOf(planLines))
				.build();

		if (failIfNotFullAllocated && !linePlan.isFullyAllocated())
		{
			throw new HUException(MSG_CannotFullAllocate)
					.appendParametersToMessage()
					.setParameter("Product", ddOrderLine.getM_Product_ID())
					.setParameter("Locator", locatorId);

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
		final LocatorId locatorId = key.getLocatorId();
		final ProductId productId = key.getProductId();

		final ImmutableList<AllocableHU> hus = handlingUnitsDAO.createHUQueryBuilder()
				.setOnlyTopLevelHUs()
				.addOnlyInWarehouseId(locatorId.getWarehouseId())
				.addOnlyInLocatorId(locatorId.getRepoId())
				.addOnlyWithProductId(productId)
				.addHUStatusesToInclude(huStatusBL.getQtyOnHandStatuses())
				.createQuery()
				.setOrderBy(queryBL.createQueryOrderByBuilder(I_M_HU.class)
						.addColumn(I_M_HU.COLUMNNAME_M_Locator_ID)
						.addColumn(I_M_HU.COLUMNNAME_M_HU_ID)
						.createQueryOrderBy())
				.stream()
				.map(hu -> new AllocableHU(storageFactory, hu, productId))
				.collect(ImmutableList.toImmutableList());

		return new AllocableHUsList(hus);
	}

	//
	//
	//

	@Value
	@Builder
	private static class AllocationGroupingKey
	{
		@NonNull ProductId productId;
		@NonNull LocatorId locatorId;
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
