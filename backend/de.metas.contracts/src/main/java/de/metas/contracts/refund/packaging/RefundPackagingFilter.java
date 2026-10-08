package de.metas.contracts.refund.packaging;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig_PackingOption;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * Decides whether a sales line takes part in the refund of a contract condition that is restricted to some packaging options.
 */
@Service
public class RefundPackagingFilter
{
	/** The packing materials that a conditions' lines are restricted to; empty if they are not restricted, which includes a restriction without any option (an empty set means all packaging). Reset when a config or a packing option changes. */
	private static final CCache<ConditionsId, ImmutableSet<Integer>> PACKING_MATERIAL_IDS_CACHE = CCache.<ConditionsId, ImmutableSet<Integer>>builder()
			.cacheName(I_C_Flatrate_RefundConfig_PackingOption.Table_Name + "#by#" + I_C_Flatrate_RefundConfig_PackingOption.COLUMNNAME_C_Flatrate_Conditions_ID)
			.tableName(I_C_Flatrate_RefundConfig_PackingOption.Table_Name)
			.additionalTableNameToResetFor(I_C_Flatrate_RefundConfig.Table_Name)
			.build();

	private final RefundConfigRepository refundConfigRepository;
	private final ImmutableList<RefundPackagingMaterialProvider> providers;

	public RefundPackagingFilter(
			@NonNull final RefundConfigRepository refundConfigRepository,
			@NonNull final Optional<List<RefundPackagingMaterialProvider>> providers)
	{
		this.refundConfigRepository = refundConfigRepository;
		this.providers = ImmutableList.copyOf(providers.orElseGet(ImmutableList::of));
	}

	/**
	 * @return {@code true} if the conditions have no packaging options (every line is in; a config that is flagged but lists none accepts all packaging), or if the packing material of the packing instruction is one of them.
	 *         {@code false} otherwise, in particular if there is no packing instruction.
	 */
	public boolean isIncluded(@NonNull final ConditionsId conditionsId, @Nullable final HUPIItemProductId huPIItemProductId, @Nullable final BPartnerId bpartnerId)
	{
		final ImmutableSet<Integer> restrictedToPackingMaterialIds = PACKING_MATERIAL_IDS_CACHE.getOrLoad(conditionsId, refundConfigRepository::retrievePackingMaterialIdsOfPackingOptionFilteredConfigs);
		if (restrictedToPackingMaterialIds.isEmpty())
		{
			return true; // not restricted
		}
		if (huPIItemProductId == null)
		{
			return false;
		}

		return providers.stream()
				.map(provider -> provider.getPackingMaterialId(huPIItemProductId, bpartnerId))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findFirst()
				.map(restrictedToPackingMaterialIds::contains)
				.orElse(false);
	}
}
