package de.metas.contracts.refund.packaging;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig_PackingOption;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
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
	/** Reset whenever a packing option changes, because the cache's name starts with the options' table name. */
	private static final CCache<ConditionsId, ImmutableSet<Integer>> PACKING_MATERIAL_IDS_CACHE = CCache.newCache(
			I_C_Flatrate_RefundConfig_PackingOption.Table_Name + "#by#" + I_C_Flatrate_RefundConfig_PackingOption.COLUMNNAME_C_Flatrate_Conditions_ID,
			0,
			CCache.EXPIREMINUTES_Never);

	private final ImmutableList<RefundPackagingMaterialProvider> providers;

	public RefundPackagingFilter(@NonNull final Optional<List<RefundPackagingMaterialProvider>> providers)
	{
		this.providers = ImmutableList.copyOf(providers.orElseGet(ImmutableList::of));
	}

	/**
	 * @return {@code true} if the conditions have no packaging options (every line is in), or if the packing material of the order line is one of them.
	 *         {@code false} otherwise, in particular if the line has no order line or no packing instruction.
	 */
	public boolean isIncluded(@NonNull final ConditionsId conditionsId, @Nullable final OrderLineId orderLineId)
	{
		final ImmutableSet<Integer> packingMaterialIds = PACKING_MATERIAL_IDS_CACHE.getOrLoad(conditionsId, RefundPackagingFilter::retrievePackingMaterialIds);
		if (packingMaterialIds.isEmpty())
		{
			return true;
		}

		if (orderLineId == null)
		{
			return false;
		}

		return providers.stream()
				.map(provider -> provider.getPackingMaterialId(orderLineId))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findFirst()
				.map(packingMaterialIds::contains)
				.orElse(false);
	}

	private static ImmutableSet<Integer> retrievePackingMaterialIds(@NonNull final ConditionsId conditionsId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_RefundConfig_PackingOption.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_RefundConfig_PackingOption.COLUMNNAME_C_Flatrate_Conditions_ID, conditionsId)
				.create()
				.listDistinct(I_C_Flatrate_RefundConfig_PackingOption.COLUMNNAME_M_HU_PackingMaterial_ID, Integer.class)
				.stream()
				.collect(ImmutableSet.toImmutableSet());
	}
}
