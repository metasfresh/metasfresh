package de.metas.handlingunits.grai;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.impl.HandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Builder
class HUGraiSnapshotLoader
{
	@NonNull private static final Logger logger = LogManager.getLogger(HUGraiSnapshotLoader.class);

	// Services (provided by caller)
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO;
	@NonNull private final IHUAttributesBL huAttributesBL;

	@NonNull
	HUGraiSnapshotsCollection loadByIds(@NonNull final Set<HuId> huIds)
	{
		return handlingUnitsDAO.getByIds(huIds)
				.stream()
				.map(hu -> load(hu).orElse(null))
				.filter(Objects::nonNull)
				.collect(HUGraiSnapshotsCollection.collect());
	}

	@NonNull
	ExplainedOptional<HUGraiSnapshot> loadById(@NonNull final HuId huId)
	{
		final I_M_HU hu = handlingUnitsDAO.getById(huId);

		return load(hu);
	}

	@NonNull
	private ExplainedOptional<HUGraiSnapshot> load(final I_M_HU hu)
	{
		if (handlingUnitsBL.isVirtual(hu))
		{
			return ExplainedOptional.emptyBecause("GRAI scanning not supported for virtual HU " + hu.getM_HU_ID());
		}

		if (handlingUnitsBL.isLoadingUnit(hu))
		{
			return ExplainedOptional.of(loadLU(hu));
		}
		else
		{
			return ExplainedOptional.of(loadTU(hu));
		}
	}

	@NonNull
	private HUGraiSnapshot loadLU(@NonNull final I_M_HU lu)
	{
		final ArrayList<HUGraiSnapshot.TU> tus = new ArrayList<>();
		final ArrayList<HUGraiSnapshot.AggregateBlock> aggregateBlocks = new ArrayList<>();

		for (final I_M_HU_Item item : handlingUnitsDAO.retrieveItems(lu))
		{
			final HUItemType itemType = HUItemType.ofNullableCode(item.getItemType());
			if (HUItemType.HandlingUnit.equals(itemType))
			{
				tus.addAll(loadLU_IncludedTU(item));
			}
			else if (HUItemType.HUAggregate.equals(itemType))
			{
				loadLU_IncludedAggregatedTUs(item).ifPresent(aggregateBlocks::add);
			}
		}

		final HuId luId = HuId.ofRepoId(lu.getM_HU_ID());

		return HUGraiSnapshot.builder()
				.huId(luId)
				.tus(ImmutableList.copyOf(tus))
				.aggregateBlocks(ImmutableList.copyOf(aggregateBlocks))
				.build();
	}

	private List<HUGraiSnapshot.TU> loadLU_IncludedTU(final I_M_HU_Item item)
	{
		final ArrayList<HUGraiSnapshot.TU> tus = new ArrayList<>();
		for (final I_M_HU childTU : handlingUnitsDAO.retrieveIncludedHUs(item))
		{
			final GRAI grai = readGRAI(childTU);
			tus.add(HUGraiSnapshot.TU.of(HuId.ofRepoId(childTU.getM_HU_ID()), grai));
		}

		return tus;
	}

	private Optional<HUGraiSnapshot.AggregateBlock> loadLU_IncludedAggregatedTUs(final I_M_HU_Item item)
	{
		final QtyTU tuCount = HandlingUnitsBL.getTUsCount(item);
		final List<I_M_HU> vhus = handlingUnitsDAO.retrieveIncludedHUs(item);
		if (vhus.isEmpty())
		{
			logger.warn("HA item {} of LU {} has no VHUs — skipping", item.getM_HU_Item_ID(), item.getM_HU_ID());
			return Optional.empty();
		}

		if (vhus.size() > 1)
		{
			logger.warn("HA item {} of LU {} has {} VHUs (expected 1) — merging GRAIs onto first VHU", item.getM_HU_Item_ID(), item.getM_HU_ID(), vhus.size());
		}
		final HuId vhuId = HuId.ofRepoId(vhus.get(0).getM_HU_ID());
		final ArrayList<GRAI> mergedGrais = new ArrayList<>();
		vhus.forEach(vhu -> readGRAISet(vhu).forEach(mergedGrais::add));

		return Optional.of(HUGraiSnapshot.AggregateBlock.of(vhuId, tuCount, GRAISet.ofCollection(mergedGrais)));
	}

	@NonNull
	private HUGraiSnapshot loadTU(@NonNull final I_M_HU tu)
	{
		final GRAI grai = readGRAI(tu);
		final HuId tuId = HuId.ofRepoId(tu.getM_HU_ID());

		return HUGraiSnapshot.builder()
				.huId(tuId)
				.tus(ImmutableList.of(HUGraiSnapshot.TU.of(tuId, grai)))
				.aggregateBlocks(ImmutableList.of())
				.build();
	}

	@javax.annotation.Nullable
	private GRAI readGRAI(@NonNull final I_M_HU hu)
	{
		return GRAI.ofNullableCanonicalString(huAttributesBL.getHUAttributeValue(hu, AttributeConstants.ATTR_GRAI));
	}

	@NonNull
	private GRAISet readGRAISet(@NonNull final I_M_HU hu)
	{
		return GRAISet.ofNullableCommaSeparated(huAttributesBL.getHUAttributeValue(hu, AttributeConstants.ATTR_GRAI));
	}
}
