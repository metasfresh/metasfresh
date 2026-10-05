package de.metas.contracts.refund.packaging;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
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
	/** The packing materials that a conditions' lines are restricted to; empty if the conditions are not restricted. Reset when a config or a packing option changes. */
	private static final CCache<ConditionsId, Optional<ImmutableSet<Integer>>> PACKING_MATERIAL_IDS_CACHE = CCache.<ConditionsId, Optional<ImmutableSet<Integer>>>builder()
			.cacheName(I_C_Flatrate_RefundConfig_PackingOption.Table_Name + "#by#" + I_C_Flatrate_RefundConfig_PackingOption.COLUMNNAME_C_Flatrate_Conditions_ID)
			.tableName(I_C_Flatrate_RefundConfig_PackingOption.Table_Name)
			.additionalTableNameToResetFor(I_C_Flatrate_RefundConfig.Table_Name)
			.build();

	private final ImmutableList<RefundPackagingMaterialProvider> providers;

	public RefundPackagingFilter(@NonNull final Optional<List<RefundPackagingMaterialProvider>> providers)
	{
		this.providers = ImmutableList.copyOf(providers.orElseGet(ImmutableList::of));
	}

	/**
	 * @return {@code true} if no config of the conditions is restricted to packaging options (every line is in), or if the packing material of the order line is one of the restricted configs' options.
	 *         {@code false} otherwise, in particular if the line has no order line or no packing instruction.
	 */
	public boolean isIncluded(@NonNull final ConditionsId conditionsId, @Nullable final OrderLineId orderLineId)
	{
		final Optional<ImmutableSet<Integer>> restrictedToPackingMaterialIds = PACKING_MATERIAL_IDS_CACHE.getOrLoad(conditionsId, RefundPackagingFilter::retrieveRestrictedToPackingMaterialIds);
		if (!restrictedToPackingMaterialIds.isPresent())
		{
			return true; // not restricted
		}

		final ImmutableSet<Integer> packingMaterialIds = restrictedToPackingMaterialIds.get();
		if (orderLineId == null || packingMaterialIds.isEmpty())
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

	/** @return the packing options of the conditions' configs that are restricted to packaging options; empty if no config is */
	private static Optional<ImmutableSet<Integer>> retrieveRestrictedToPackingMaterialIds(@NonNull final ConditionsId conditionsId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ImmutableSet<Integer> restrictedConfigIds = queryBL.createQueryBuilder(I_C_Flatrate_RefundConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_RefundConfig.COLUMNNAME_C_Flatrate_Conditions_ID, conditionsId)
				.addEqualsFilter(I_C_Flatrate_RefundConfig.COLUMNNAME_IsPackingOptionFiltered, true)
				.create()
				.listIds()
				.stream()
				.collect(ImmutableSet.toImmutableSet());
		if (restrictedConfigIds.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(queryBL.createQueryBuilder(I_C_Flatrate_RefundConfig_PackingOption.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Flatrate_RefundConfig_PackingOption.COLUMNNAME_C_Flatrate_RefundConfig_ID, restrictedConfigIds)
				.create()
				.listDistinct(I_C_Flatrate_RefundConfig_PackingOption.COLUMNNAME_M_HU_PackingMaterial_ID, Integer.class)
				.stream()
				.collect(ImmutableSet.toImmutableSet()));
	}
}
