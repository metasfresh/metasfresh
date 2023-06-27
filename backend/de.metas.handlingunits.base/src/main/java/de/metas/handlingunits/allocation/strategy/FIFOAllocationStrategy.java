package de.metas.handlingunits.allocation.strategy;

import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationDirection;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.util.Check;
import lombok.NonNull;

import javax.annotation.Nullable;

class FIFOAllocationStrategy extends AbstractAllocationStrategy
{
	public FIFOAllocationStrategy(
			@NonNull final AllocationDirection direction,
			@NonNull final AllocationStrategySupportingServicesFacade services)
	{
		super(direction, services);
	}

	@Override
	protected IAllocationResult allocateRemainingOnIncludedHUItem(
			@NonNull final I_M_HU_Item item,
			@NonNull final IAllocationRequest request)
	{
		if (getDirection().isInboundAllocation())
		{
			return allocateRemainingOnNewHUs(item, request);
		}
		else
		{
			// Do nothing because when this is called, we already did a "regular" deallocate from the given {@code item},
			// so there is no "remaining" left.
			return AllocationUtils.nullResult();
		}
	}

	private IAllocationResult allocateRemainingOnNewHUs(
			@NonNull final I_M_HU_Item item,
			@NonNull final IAllocationRequest request)
	{
		//
		// Create initial result
		final IMutableAllocationResult result = AllocationUtils.createMutableAllocationResult(request);

		//
		// Start creating and loading new HUs as much as we can
		while (!result.isCompleted())
		{
			final I_M_HU includedHU = createNewIncludedHU(item, request);
			if (includedHU == null)
			{
				// we cannot create more HUs
				break;
			}

			final IAllocationRequest includedRequest = AllocationUtils.createQtyRequestForRemaining(request, result);
			final IAllocationResult includedResult = allocateOnIncludedHU(includedHU, includedRequest);
			if (includedResult.isZeroAllocated())
			{
				destroyIncludedHU(includedHU);
				// if we cannot allocate on this HU, we won't be able to allocate on any of these
				break;
			}

			AllocationUtils.mergeAllocationResult(result, includedResult);
		}

		return result;
	}

	/**
	 * Creates a new included HU
	 *
	 * @return newly created HU or null if we cannot create HUs anymore
	 */
	@Nullable
	private final I_M_HU createNewIncludedHU(
			@NonNull final I_M_HU_Item item,
			@NonNull final IAllocationRequest request)
	{
		final IHUItemStorage storage = getHUItemStorage(item, request);
		if (!storage.requestNewHU())
		{
			return null;
		}

		final I_M_HU_PI includedHUDef;
		if (X_M_HU_Item.ITEMTYPE_HUAggregate.equals(item.getItemType()))
		{
			// if we are to create an HU below an HUAggregate item, then we always create a VHU.
			includedHUDef = services.getVirtualPI(request.getHuContext().getCtx());
		}
		else
		{
			includedHUDef = services.getIncluded_HU_PI(item);
		}

		// we cannot create an instance which has no included handling unit definition
		Check.errorIf(includedHUDef == null, "Unable to get a M_HU_PI for the given request and item; request={}; item={}", request, item);

		return AllocationUtils.createHUBuilder(request)
				.setM_HU_Item_Parent(item)
				.create(includedHUDef);
	}

	private final void destroyIncludedHU(final I_M_HU hu)
	{
		services.deleteHU(hu);
	}
}
