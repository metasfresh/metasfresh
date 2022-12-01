package de.metas.handlingunits.allocation.strategy;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.impl.AllocationDirection;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.quantity.Quantity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

abstract class AbstractAllocationStrategy implements IAllocationStrategy
{
	// Services
	protected final AllocationStrategySupportingServicesFacade services;

	@Getter(AccessLevel.PROTECTED)
	private final AllocationDirection direction;

	public AbstractAllocationStrategy(
			@NonNull final AllocationDirection direction,
			@NonNull final AllocationStrategySupportingServicesFacade services)
	{
		this.direction = direction;
		this.services = services;
	}

	@Override
	public IAllocationResult execute(
			@NonNull final I_M_HU hu,
			@NonNull final IAllocationRequest request)
	{
		//
		// Create Initial Result
		final IMutableAllocationResult allocationResult = AllocationUtils.createMutableAllocationResult(request);
		if (allocationResult.isCompleted())
		{
			return allocationResult; // happens if the request's qty is zero
		}

		// TODO: Check if request is about to deallocate the whole HU. If that's the case, don't create allocation requests for storages but consider moving the whole HU

		//
		// Iterate HU Items and try to allocate on them
		final List<I_M_HU_Item> huItems = services.retrieveItems(hu);
		for (final I_M_HU_Item item : huItems)
		{
			// Check: is allocation is completed, stop it here.
			if (allocationResult.isCompleted())
			{
				return allocationResult;
			}

			//
			// Gets ItemType
			final HUItemType itemType = services.getItemType(item);

			//
			// Allocate to/from material item
			if (HUItemType.Material.equals(itemType))
			{
				final IAllocationRequest materialItemRequest = AllocationUtils.createQtyRequestForRemaining(request, allocationResult);
				final IAllocationResult itemResult = allocateOnMaterialItem(item, materialItemRequest);
				AllocationUtils.mergeAllocationResult(allocationResult, itemResult);
			}
			//
			// Allocate to/from included handling units
			else if (HUItemType.HandlingUnit.equals(itemType)
					|| HUItemType.HUAggregate.equals(itemType))
			{
				final IAllocationRequest itemRequest = AllocationUtils.createQtyRequestForRemaining(request, allocationResult);

				// this doesn't do anything, unless there is a child HU attached to the item.
				final IAllocationResult itemResult = allocateOnIncludedHUItem(item, itemRequest);
				AllocationUtils.mergeAllocationResult(allocationResult, itemResult);

				// Try to allocate in newly created included HUs
				if (!allocationResult.isCompleted())
				{
					final IAllocationRequest requestRemaining = AllocationUtils.createQtyRequestForRemaining(request, allocationResult);
					final IAllocationResult resultRemaining = allocateRemainingOnIncludedHUItem(item, requestRemaining);
					AllocationUtils.mergeAllocationResult(allocationResult, resultRemaining);
				}
			}
			else
			{
				// other item types are skipped because we cannot allocate on them
			}
		}

		return allocationResult;
	}

	/**
	 * Allocate/Deallocate given request to given item of ItemType=Material.
	 */
	protected IAllocationResult allocateOnMaterialItem(
			@NonNull final I_M_HU_Item item,
			@NonNull final IAllocationRequest request)
	{
		// If there nothing requested to allocate, it's pointless to do something
		if (request.isZeroQty())
		{
			return AllocationUtils.nullResult();
		}

		//
		// If our item is from a Virtual HU, we shall allocate to or from it directly
		if (services.isVirtual(item))
		{
			final I_M_HU_Item vhuItem = item;
			return allocateOnVirtualMaterialItem(vhuItem, request);
		}

		//
		// Deallocation
		if (direction.isOutboundDeallocation())
		{
			// iterate all VHU linked to this item and try to deallocate from them
			return allocateOnIncludedHUItem(item, request);
		}
		//
		// Allocation
		else
		{
			return allocateOnNewVHU(request, item);
		}
	}

	/**
	 * Creates an new Virtual HU (linked to given parent item) and allocate the request on it.
	 */
	private IAllocationResult allocateOnNewVHU(
			@NonNull final IAllocationRequest request,
			@NonNull final I_M_HU_Item parentItem)
	{
		final IAllocationRequest requestActual = createActualRequest(parentItem, request);
		if (requestActual.isZeroQty())
		{
			return AllocationUtils.nullResult(); // might be that the actual request has zero qty because the parent item's storage is already "full"
		}

		final IHUContext huContext = request.getHuContext();
		final I_M_HU vhu = createVHU(huContext, requestActual, parentItem);
		return allocateOnIncludedHU(vhu, requestActual);
	}

	/**
	 * @return newly created Virtual Handling Unit (VHU), linked to <code>parentItem</code>
	 */
	private final I_M_HU createVHU(
			@NonNull final IHUContext huContext,
			@NonNull final IAllocationRequest request,
			@NonNull final I_M_HU_Item parentItem)
	{
		final I_M_HU_PI vhuPI = services.getVirtualPI(huContext.getCtx());

		return AllocationUtils.createHUBuilder(request)
				.setM_HU_Item_Parent(parentItem)
				.create(vhuPI);
	}

	/**
	 * Allocate/Deallocate on HUs which are linked to given HU Item.
	 *
	 * The ItemType of given <code>item</code> can be anything (and it's not checked).<br>
	 * So, this method can be used to allocate/deallocate on real included HUs or to allocate/deallocate to VHUs linked
	 * to this material HU item.
	 */
	protected IAllocationResult allocateOnIncludedHUItem(
			@NonNull final I_M_HU_Item item,
			@NonNull final IAllocationRequest request)
	{
		//
		// Create initial result
		final IMutableAllocationResult result = AllocationUtils.createMutableAllocationResult(request);

		//
		// Try to allocate on existing included HUs
		// TODO: consider retrieving an iterator of included HUs, load only what you need and after each included HU allocation check if you need to go forward
		final List<I_M_HU> includedHUs = services.retrieveIncludedHUs(item);
		for (final I_M_HU includedHU : includedHUs)
		{
			final IAllocationRequest includedRequest = AllocationUtils.createQtyRequestForRemaining(request, result);
			final IAllocationResult includedResult = allocateOnIncludedHU(includedHU, includedRequest);
			AllocationUtils.mergeAllocationResult(result, includedResult);
		}

		return result;
	}

	/**
	 * If after {@link #allocateOnIncludedHUItem(I_M_HU_Item, IAllocationRequest)} there is more to allocate
	 * then this method will be called to allocate remaining qty.<br>
	 */
	protected abstract IAllocationResult allocateRemainingOnIncludedHUItem(
			final I_M_HU_Item item,
			final IAllocationRequest request);

	/**
	 * Allocate/Deallocate <code>request</code> on included HU.
	 *
	 * @param includedHU handling unit to allocate on; this handling unit is included in HU on which we were allocating until this call
	 */
	protected final IAllocationResult allocateOnIncludedHU(
			@NonNull final I_M_HU includedHU,
			@NonNull final IAllocationRequest request)
	{
		return execute(includedHU, request);
	}

	/**
	 * Return an item storage to be used to derive the "actual" allocation request.
	 * See {@link #createActualRequest(I_M_HU_Item, IAllocationRequest)}.
	 * This method uses {@link IHUStorageFactory#getStorage(I_M_HU_Item)}, but can be overridden.
	 */
	protected IHUItemStorage getHUItemStorage(
			@NonNull final I_M_HU_Item item,
			@NonNull final IAllocationRequest request)
	{
		final IHUContext huContext = request.getHuContext();
		final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
		return huStorageFactory.getStorage(item);
	}

	/**
	 * Creates the actual request for allocation/deallocation that shall be used on given HU Item.
	 * The actual request is computed based on item's current capacity and load.
	 *
	 * @return actual request to use used for allocation/deallocation be the {@link #allocateOnVirtualMaterialItem(I_M_HU_Item, IAllocationRequest)}.
	 */
	private final IAllocationRequest createActualRequest(
			@NonNull final I_M_HU_Item item,
			@NonNull final IAllocationRequest request)
	{
		final IHUItemStorage storage = getHUItemStorage(item, request);

		return direction.isOutboundDeallocation()
				? storage.requestQtyToDeallocate(request)
				: storage.requestQtyToAllocate(request);
	}

	private final IAllocationResult allocateOnVirtualMaterialItem(
			@NonNull final I_M_HU_Item vhuItem,
			@NonNull final IAllocationRequest request)
	{
		//
		// Create Actual Allocation Request depending on vhuItem's (remaining) capacity
		final IAllocationRequest requestActual = createActualRequest(vhuItem, request);
		if (requestActual.isZeroQty())
		{
			return AllocationUtils.nullResult();
		}

		final BigDecimal qtyToAllocate = request.getQty();
		final BigDecimal qtyAllocated = requestActual.getQty();

		//
		// Create HU Transaction Candidate
		final IHUTransactionCandidate trx = createHUTransaction(requestActual, vhuItem);

		//
		// Create Allocation Result
		return AllocationUtils.createQtyAllocationResult(
				qtyToAllocate,
				qtyAllocated,
				Arrays.asList(trx), // trxs
				ImmutableList.of()); // attributeTrxs
	}

	/**
	 * Create transaction candidate which when it will be processed it will change the storage quantity.
	 *
	 * @param requestActual request (used to get Product, UOM, Date, Qty etc)
	 * @param vhuItem Virtual HU item on which we actually allocated/deallocated
	 */
	@NonNull
	private final IHUTransactionCandidate createHUTransaction(
			@NonNull final IAllocationRequest requestActual,
			@Nullable final I_M_HU_Item vhuItem)
	{
		final I_M_HU_Item itemFirstNotPureVirtual = services.getFirstNotPureVirtualItem(vhuItem);

		final Object referencedModel = AllocationUtils.getReferencedModel(requestActual);
		final Quantity qtyTrx = AllocationUtils.getQuantity(requestActual, direction);

		return new HUTransactionCandidate(
				referencedModel,
				itemFirstNotPureVirtual, // HU Item
				vhuItem, // VHU Item
				requestActual.getProductId(), // Product
				qtyTrx, // Qty/UOM
				requestActual.getDate()); // Date
	}
}
