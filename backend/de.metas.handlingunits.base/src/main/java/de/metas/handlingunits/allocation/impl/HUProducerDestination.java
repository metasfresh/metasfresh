package de.metas.handlingunits.allocation.impl;

import org.compiere.SpringContextHolder;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.strategy.AllocationStrategyFactory;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * This producer is used if stuff needs to be allocated into a "flat" HU and no capacity constraints need to be taken into account.
 * I.e. don't invoke it with a palet and expect it to magically discover or create and allocate to included HUs.
 * <p>
 * For most real world use cases, you will probably want to use {@link LUTUProducerDestination} instead of this one.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUProducerDestination extends AbstractProducerDestination
{
	public static final HUProducerDestination of(final I_M_HU_PI huPI)
	{
		return new HUProducerDestination(huPI);
	}

	public static final HUProducerDestination of(@NonNull final HuPackingInstructionsId packingInstructionsId)
	{
		final I_M_HU_PI huPI = Services.get(IHandlingUnitsDAO.class).getPackingInstructionById(packingInstructionsId);
		return new HUProducerDestination(huPI);
	}

	/**
	 * @return producer which will create one VHU
	 */
	public static final HUProducerDestination ofVirtualPI()
	{
		return of(HuPackingInstructionsId.VIRTUAL)
				.setMaxHUsToCreate(1); // we want one VHU
	}

	private static final ArrayKey SHARED_CurrentHUKey = ArrayKey.of(0);

	private final AllocationStrategyFactory allocationStrategyFactory = SpringContextHolder.instance.getBean(AllocationStrategyFactory.class);

	private final I_M_HU_PI huPI;

	/**
	 * Maximum number of HUs allowed to be created
	 */
	private int maxHUsToCreate = Integer.MAX_VALUE;
	private I_M_HU_Item parentHUItem;

	private HUProducerDestination(@NonNull final I_M_HU_PI huPI)
	{
		this.huPI = huPI;
	}

	@Override
	protected I_M_HU_PI getM_HU_PI()
	{
		return huPI;
	}

	@Override
	protected IAllocationResult loadHU(final I_M_HU hu, final IAllocationRequest request)
	{
		final IAllocationStrategy allocationStrategy = allocationStrategyFactory.createAllocationStrategy(AllocationDirection.INBOUND_ALLOCATION);
		return allocationStrategy.execute(hu, request);
	}

	@Override
	protected ArrayKey extractCurrentHUKey(final IAllocationRequest request)
	{
		// NOTE: in case of maxHUsToCreate == 1 try to load all products in one HU
		return maxHUsToCreate == 1
				? SHARED_CurrentHUKey
				: super.extractCurrentHUKey(request);
	}

	@Override
	public boolean isAllowCreateNewHU()
	{
		// Check if we already reached the maximum number of HUs that we are allowed to create
		return getCreatedHUsCount() < maxHUsToCreate;
	}

	public HUProducerDestination setMaxHUsToCreate(final int maxHUsToCreate)
	{
		Check.assumeGreaterOrEqualToZero(maxHUsToCreate, "maxHUsToCreate");
		this.maxHUsToCreate = maxHUsToCreate;
		return this;
	}

	/**
	 * Then this producer creates a new HU, than i uses the given {@code parentHUItem} for the new HU's {@link I_M_HU#COLUMN_M_HU_Item_Parent_ID}.
	 *
	 * @param parentHUItem
	 */
	public HUProducerDestination setParent_HU_Item(final I_M_HU_Item parentHUItem)
	{
		this.parentHUItem = parentHUItem;
		return this;
	}

	@Override
	protected I_M_HU_Item getParent_HU_Item()
	{
		return parentHUItem;
	}
}
