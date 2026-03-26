package de.metas.handlingunits.rest_api.grai;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import java.util.ArrayList;
import java.util.List;

@Builder
class HUGraiSnapshotLoader
{
	private static final Logger logger = LogManager.getLogger(HUGraiSnapshotLoader.class);
	// Services
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

	// Parameter
	@NonNull private final HuId huId;

	@NonNull
	HUGraiSnapshot load()
	{
		final I_M_HU hu = handlingUnitsDAO.getById(huId);

		if (handlingUnitsBL.isVirtual(hu))
		{
			throw new AdempiereException("GRAI scanning not supported for virtual HUs");
		}

		if (handlingUnitsBL.isLoadingUnit(hu))
		{
			return loadLU(hu);
		}
		else
		{
			return loadTU(hu);
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
				for (final I_M_HU childTU : handlingUnitsDAO.retrieveIncludedHUs(item))
				{
					final GRAI grai = readGRAI(childTU);
					tus.add(HUGraiSnapshot.TU.of(HuId.ofRepoId(childTU.getM_HU_ID()), grai));
				}
			}
			else if (HUItemType.HUAggregate.equals(itemType))
			{
				final QtyTU tuCount = QtyTU.ofBigDecimal(item.getQty());
				final List<I_M_HU> vhus = handlingUnitsDAO.retrieveIncludedHUs(item);
				if (vhus.isEmpty())
				{
					logger.warn("HA item {} of LU {} has no VHUs — skipping", item.getM_HU_Item_ID(), huId);
				}
				else
				{
					if (vhus.size() > 1)
					{
						logger.warn("HA item {} of LU {} has {} VHUs (expected 1) — merging GRAIs onto first VHU", item.getM_HU_Item_ID(), huId, vhus.size());
					}
					final HuId vhuId = HuId.ofRepoId(vhus.get(0).getM_HU_ID());
					final ArrayList<GRAI> mergedGrais = new ArrayList<>();
					vhus.forEach(vhu -> readGRAISet(vhu).forEach(mergedGrais::add));
					aggregateBlocks.add(HUGraiSnapshot.AggregateBlock.of(vhuId, tuCount, GRAISet.ofCollection(mergedGrais)));
				}
			}
		}

		return HUGraiSnapshot.builder()
				.huId(huId)
				.tus(ImmutableList.copyOf(tus))
				.aggregateBlocks(ImmutableList.copyOf(aggregateBlocks))
				.build();
	}

	@NonNull
	private HUGraiSnapshot loadTU(@NonNull final I_M_HU tu)
	{
		final GRAI grai = readGRAI(tu);
		return HUGraiSnapshot.builder()
				.huId(huId)
				.tus(ImmutableList.of(HUGraiSnapshot.TU.of(huId, grai)))
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
