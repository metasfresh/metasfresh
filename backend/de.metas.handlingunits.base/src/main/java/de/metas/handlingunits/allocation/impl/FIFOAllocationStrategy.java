package de.metas.handlingunits.allocation.impl;

import javax.annotation.Nullable;

import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationStrategyFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.util.Check;
import lombok.NonNull;

public class FIFOAllocationStrategy extends AbstractFIFOStrategy
{
	public FIFOAllocationStrategy(
			@NonNull final AllocationStrategySupportingServicesFacade services,
			@Nullable final IAllocationStrategyFactory allocationStrategyFactory)
	{
		super(AllocationDirection.INBOUND_ALLOCATION,
				services,
				allocationStrategyFactory);
	}

	@Override
	protected IAllocationResult allocateRemainingOnIncludedHUItem(
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
		final IHUItemStorage storage = getHUStorageFactory(request).getStorage(item);
		if (!storage.requestNewHU())
		{
			return null;
		}

		final I_M_HU_PI includedHUDef;
		if (X_M_HU_Item.ITEMTYPE_HUAggregate.equals(item.getItemType()))
		{
			// if we are to create an HU below an HUAggregate item, then we always create a VHU.
			includedHUDef = services.getVirtualPI(request.getHUContext().getCtx());
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
