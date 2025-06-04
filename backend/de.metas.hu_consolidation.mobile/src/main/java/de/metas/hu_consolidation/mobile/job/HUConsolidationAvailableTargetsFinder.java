package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HUConsolidationAvailableTargetsFinder
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);

	public List<HUConsolidationTarget> getAvailableTargets(@NonNull final BPartnerId customerId)
	{
		final ImmutableSet<HuPackingInstructionsItemId> tuPIItemIds = getTUPIItems(customerId);
		return handlingUnitsBL.getLUPIs(tuPIItemIds, customerId)
				.stream()
				.map(HUConsolidationTarget::ofNewLU)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImmutableSet<HuPackingInstructionsItemId> getTUPIItems(@NonNull final BPartnerId customerId)
	{
		return huPIItemProductDAO.retrieveForBPartner(customerId)
				.stream()
				.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_ID)
				.map(HuPackingInstructionsItemId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}


}
