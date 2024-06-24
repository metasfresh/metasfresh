package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PackToHUsProducer
{
	private final IHandlingUnitsBL handlingUnitsBL;
	private final IHUPIItemProductBL huPIItemProductBL;
	private final IUOMConversionBL uomConversionBL;
	private final InventoryService inventoryService;

	// Params
	private final boolean alwaysPackEachCandidateInItsOwnHU;
	private final PickingJobId contextPickingJobId;

	// State
	private static final int PACKAGE_NO_ZERO = 0;
	private final AtomicInteger nextPackageNo = new AtomicInteger(100);
	private final HashMap<PackToInfo, IHUProducerAllocationDestination> packToDestinations = new HashMap<>();

	@Builder
	private PackToHUsProducer(
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final IHUPIItemProductBL huPIItemProductBL,
			@NonNull final IUOMConversionBL uomConversionBL,
			@NonNull InventoryService inventoryService,
			//
			final boolean alwaysPackEachCandidateInItsOwnHU,
			@Nullable final PickingJobId contextPickingJobId)
	{
		this.handlingUnitsBL = handlingUnitsBL;
		this.huPIItemProductBL = huPIItemProductBL;
		this.uomConversionBL = uomConversionBL;
		this.inventoryService = inventoryService;

		this.alwaysPackEachCandidateInItsOwnHU = alwaysPackEachCandidateInItsOwnHU;
		this.contextPickingJobId = contextPickingJobId;
	}

	public List<I_M_HU> packToHU(
			@NonNull final IHUContext huContext,
			@NonNull final HuId pickFromHUId,
			@NonNull final PackToInfo packToInfo,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyPicked,
			@Nullable final Quantity catchWeight,
			@NonNull final TableRecordReference documentRef,
			final boolean checkIfAlreadyPacked,
			final boolean createInventoryForMissingQty)
	{
		final PackedHUWeightNetUpdater weightUpdater = new PackedHUWeightNetUpdater(uomConversionBL, huContext, productId, catchWeight);

		final PickFromHUsList pickFromHUs;
		if (createInventoryForMissingQty)
		{
			final PickFromHU pickFromHU = newPickFromHU()
					.huContext(huContext)
					.hu(handlingUnitsBL.getById(pickFromHUId))
					.build();
			weightUpdater.capturePickFromHUBeforeTransfer(pickFromHU.toM_HU());

			final Quantity huQty = pickFromHU.getQuantity(productId, qtyPicked.getUOM());

			pickFromHUs = new PickFromHUsList();
			final Quantity qtyMissing;
			if (huQty.signum() <= 0)
			{
				// pickFromHU is destroyed/empty
				qtyMissing = qtyPicked;
			}
			else
			{
				qtyMissing = qtyPicked.subtract(huQty).toZeroIfNegative();
				pickFromHUs.add(pickFromHU);
			}

			if (qtyMissing.signum() > 0)
			{
				// pickFromHU contains partial quantity of what we need,
				// so we will do an inventory+ to get the missing qty

				final HuId newHuId = inventoryService.createInventoryForMissingQty(CreateVirtualInventoryWithQtyReq.builder()
						.clientId(pickFromHU.getClientId())
						.orgId(pickFromHU.getOrgId())
						.warehouseId(packToInfo.getShipFromLocatorId().getWarehouseId())
						.productId(productId)
						.qty(qtyMissing)
						.movementDate(SystemTime.asZonedDateTime())
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.pickingJobId(contextPickingJobId)
						.build());

				pickFromHUs.add(newPickFromHU()
						.huContext(huContext)
						.hu(handlingUnitsBL.getById(newHuId))
						.isGeneratedFromInventory(true)
						.build());
			}
		}
		else
		{
			final PickFromHU pickFromHU = newPickFromHU()
					.huContext(huContext)
					.hu(handlingUnitsBL.getById(pickFromHUId))
					.build();
			weightUpdater.capturePickFromHUBeforeTransfer(pickFromHU.toM_HU());

			pickFromHUs = new PickFromHUsList();
			pickFromHUs.add(pickFromHU);
		}

		//
		// Case: the PickFrom HU can be considered already packed
		// i.e. it's an HU with exactly required qty and same packing instructions
		if (pickFromHUs.isSingleHUAlreadyPacked(checkIfAlreadyPacked, productId, qtyPicked, packToInfo.getPackingInstructionsId()))
		{
			final I_M_HU pickFromHU = pickFromHUs.getSingleHU().toM_HU();
			handlingUnitsBL.setHUStatus(pickFromHU, PlainContextAware.newWithThreadInheritedTrx(), X_M_HU.HUSTATUS_Picked);

			final List<I_M_HU> packedHUs = ImmutableList.of(pickFromHU);
			weightUpdater.updatePackToHUs(packedHUs);

			return packedHUs;
		}
		//
		// Case: We have to split out and pack our HU
		else
		{
			final IHUProducerAllocationDestination packToDestination;
			HULoader.builder()
					.source(HUListAllocationSourceDestination.of(pickFromHUs.toHUsList()).setDestroyEmptyHUs(true))
					.destination(packToDestination = getPackToDestination(packToInfo))
					.load(AllocationUtils.builder()
							.setHUContext(huContext)
							.setProduct(productId)
							.setQuantity(qtyPicked)
							.setFromReferencedTableRecord(documentRef)
							.setForceQtyAllocation(true)
							.create());

			weightUpdater.updatePickFromHUs(pickFromHUs.toHUsList());

			final List<I_M_HU> packedHUs = packToDestination.getCreatedHUs();
			weightUpdater.updatePackToHUs(packedHUs);
			return packedHUs;
		}
	}

	private PickFromHU.PickFromHUBuilder newPickFromHU()
	{
		return PickFromHU.builder()
				.handlingUnitsBL(handlingUnitsBL);
	}

	public PackToInfo extractPackToInfo(
			@NonNull final PackToSpec packToSpec,
			@NonNull final BPartnerLocationId shipToBPLocationId,
			@NonNull final LocatorId shipFromLocatorId)
	{
		final HuPackingInstructionsId packingInstructionsId;
		final Capacity tuCapacity;
		if (packToSpec.isVirtual())
		{
			packingInstructionsId = HuPackingInstructionsId.VIRTUAL;
			tuCapacity = null;
		}
		else if (packToSpec.getTuPackingInstructionsId() != null)
		{
			final HUPIItemProduct tuPIItemProduct = huPIItemProductBL.getById(packToSpec.getTuPackingInstructionsId());
			packingInstructionsId = handlingUnitsBL.getPackingInstructionsId(tuPIItemProduct.getPiItemId());
			tuCapacity = tuPIItemProduct.toCapacity();
		}
		else if (packToSpec.getGenericPackingInstructionsId() != null)
		{
			packingInstructionsId = packToSpec.getGenericPackingInstructionsId();
			tuCapacity = null;
		}
		else
		{
			throw new AdempiereException("Unsupported pack to spec: " + packToSpec);
		}

		final int packageNo = isPackEachCandidateInItsOwnHU(packingInstructionsId) ? nextPackageNo.getAndIncrement() : PACKAGE_NO_ZERO;

		return PackToInfo.builder()
				.shipToBPLocationId(shipToBPLocationId)
				.shipFromLocatorId(shipFromLocatorId)
				.packingInstructionsId(packingInstructionsId)
				.tuCapacity(tuCapacity)
				.packageNo(packageNo)
				.build();
	}

	private boolean isPackEachCandidateInItsOwnHU(final HuPackingInstructionsId packingInstructionsId)
	{
		return this.alwaysPackEachCandidateInItsOwnHU
				|| packingInstructionsId.isVirtual(); // if we deal with virtual handling units, pack each candidate individually (in a new VHU).
	}

	public IHUProducerAllocationDestination getPackToDestination(final PackToInfo packToInfo)
	{
		return packToDestinations.computeIfAbsent(packToInfo, this::createPackToDestination);
	}

	private IHUProducerAllocationDestination createPackToDestination(final PackToInfo packToInfo)
	{
		@NonNull final HuPackingInstructionsId packingInstructionsId = packToInfo.getPackingInstructionsId();
		if (packingInstructionsId.isVirtual())
		{
			final HUProducerDestination vhuProducer = HUProducerDestination.ofVirtualPI();
			setupPackToDestinationCommonOptions(vhuProducer, packToInfo);
			vhuProducer.setMaxHUsToCreate(1);
			return vhuProducer;
		}
		else if (packToInfo.getTuCapacity() != null)
		{
			final LUTUProducerDestination tuProducer = new LUTUProducerDestination();
			setupPackToDestinationCommonOptions(tuProducer, packToInfo);

			tuProducer.setNoLU();
			tuProducer.setTUPI(handlingUnitsBL.getPI(packingInstructionsId));
			tuProducer.addCUPerTU(packToInfo.getTuCapacity());

			return tuProducer;
		}
		else
		{
			final HUProducerDestination huProducer = HUProducerDestination.of(packingInstructionsId);
			setupPackToDestinationCommonOptions(huProducer, packToInfo);
			huProducer.setMaxHUsToCreate(1);
			return huProducer;
		}
	}

	private static void setupPackToDestinationCommonOptions(@NonNull final IHUProducerAllocationDestination producer, @NonNull final PackToInfo packToInfo)
	{
		producer.setHUStatus(X_M_HU.HUSTATUS_Picked);
		producer.setBPartnerAndLocationId(packToInfo.getShipToBPLocationId());
		producer.setLocatorId(packToInfo.getShipFromLocatorId());
	}

	//
	//
	// ------------------------------------
	//
	//

	@Value
	@Builder
	public static class PackToInfo
	{
		@NonNull BPartnerLocationId shipToBPLocationId;
		@NonNull LocatorId shipFromLocatorId;

		@NonNull HuPackingInstructionsId packingInstructionsId;
		@Nullable Capacity tuCapacity;
		int packageNo;
	}

	@EqualsAndHashCode
	private static class PickFromHU
	{
		@NonNull private final IHandlingUnitsBL handlingUnitsBL;
		@NonNull private final IHUContext huContext;
		@NonNull private final I_M_HU hu;
		@Getter private final boolean isGeneratedFromInventory;
		private IHUStorage _huStorage = null; // lazy

		@Builder
		private PickFromHU(
				@NonNull IHandlingUnitsBL handlingUnitsBL,
				@NonNull final IHUContext huContext,
				@NonNull final I_M_HU hu,
				final boolean isGeneratedFromInventory)
		{
			this.handlingUnitsBL = handlingUnitsBL;
			this.huContext = huContext;
			this.hu = hu;
			this.isGeneratedFromInventory = isGeneratedFromInventory;
		}

		public I_M_HU toM_HU() {return hu;}

		public ClientId getClientId() {return ClientId.ofRepoId(hu.getAD_Client_ID());}

		public OrgId getOrgId() {return OrgId.ofRepoId(hu.getAD_Org_ID());}

		public Quantity getQuantity(final ProductId productId, final I_C_UOM uom) {return getStorage().getQuantity(productId, uom);}

		public IHUStorage getStorage()
		{
			IHUStorage huStorage = _huStorage;
			if (huStorage == null)
			{
				huStorage = _huStorage = huContext.getHUStorageFactory().getStorage(hu);
			}
			return huStorage;
		}

		public boolean isAlreadyPacked(@NonNull final ProductId productId, @NonNull final Quantity qty, @Nullable final HuPackingInstructionsId packingInstructionsId)
		{
			return isSingleProductWithQtyEqualsTo(productId, qty)
					&& HuPackingInstructionsId.equals(packingInstructionsId, getPackingInstructionsId());
		}

		public boolean isSingleProductWithQtyEqualsTo(@NonNull final ProductId productId, @NonNull final Quantity qty)
		{
			return getStorage().isSingleProductWithQtyEqualsTo(productId, qty);
		}

		@NonNull
		public HuPackingInstructionsId getPackingInstructionsId() {return handlingUnitsBL.getPackingInstructionsId(hu);}
	}

	private static class PickFromHUsList
	{
		private final ArrayList<PickFromHU> list = new ArrayList<>();

		PickFromHUsList() {}

		public void add(@NonNull final PickFromHU pickFromHU) {list.add(pickFromHU);}

		public List<I_M_HU> toHUsList() {return list.stream().map(PickFromHU::toM_HU).collect(ImmutableList.toImmutableList());}

		public PickFromHU getSingleHU() {return CollectionUtils.singleElement(list);}

		public boolean isSingleHUAlreadyPacked(
				final boolean checkIfAlreadyPacked,
				@NonNull final ProductId productId,
				@NonNull final Quantity qty,
				@Nullable final HuPackingInstructionsId packingInstructionsId)
		{
			if (list.size() != 1)
			{
				return false;
			}
			final PickFromHU hu = list.get(0);

			// NOTE we check isGeneratedFromInventory because we want to avoid splitting an HU that we just generated it, even if checkIfAlreadyPacked=false
			if (checkIfAlreadyPacked || hu.isGeneratedFromInventory())
			{
				return hu.isAlreadyPacked(productId, qty, packingInstructionsId);
			}
			else
			{
				return false;
			}
		}
	}

}
