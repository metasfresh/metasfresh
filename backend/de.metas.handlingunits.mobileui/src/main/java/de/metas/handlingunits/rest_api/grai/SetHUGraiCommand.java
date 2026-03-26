package de.metas.handlingunits.rest_api.grai;

import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.HuId;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashSet;

@Builder
public class SetHUGraiCommand
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

	@NonNull private final HuId huId;
	@NonNull private final GRAISet graiSet;

	public void execute()
	{
		final I_M_HU hu = handlingUnitsDAO.getById(huId);

		if (handlingUnitsBL.isVirtual(hu))
		{
			throw new AdempiereException("GRAI scanning not supported for virtual HUs");
		}

		if (handlingUnitsBL.isTransportUnitOrAggregate(hu))
		{
			// TU: set single GRAI directly
			setGRAIAttribute(huId, graiSet.noneOrSingleElement());
		}
		else if (handlingUnitsBL.isLoadingUnit(hu))
		{
			// LU: distribute GRAIs to child TUs in two passes
			final LinkedHashSet<GRAI> unassignedGrais = new LinkedHashSet<>(graiSet.toSet());

			// Collect TUs that need GRAI assignment and aggregate items
			final ArrayList<I_M_HU> tusWithoutGrai = new ArrayList<>();
			final ArrayList<I_M_HU> aggregateVHUs = new ArrayList<>();

			// Pass 1: For disaggregated TUs, keep existing GRAIs that are in the new set, clear others
			for (final I_M_HU_Item item : handlingUnitsDAO.retrieveItems(hu))
			{
				final HUItemType itemType = HUItemType.ofNullableCode(item.getItemType());
				if (HUItemType.HandlingUnit.equals(itemType))
				{
					for (final I_M_HU childTU : handlingUnitsDAO.retrieveIncludedHUs(item))
					{
						final GRAI existingGrai = getGRAISet(childTU).noneOrSingleElement();
						if (existingGrai != null && graiSet.contains(existingGrai))
						{
							// Keep it — it's in the new set; mark as assigned
							unassignedGrais.remove(existingGrai);
						}
						else if (existingGrai != null)
						{
							// Clear it — it's NOT in the new set
							clearGRAIAttribute(childTU);
							tusWithoutGrai.add(childTU);
						}
						else
						{
							// No existing GRAI
							tusWithoutGrai.add(childTU);
						}
					}
				}
				else if (HUItemType.HUAggregate.equals(itemType))
				{
					for (final I_M_HU aggregateVHU : handlingUnitsDAO.retrieveIncludedHUs(item))
					{
						aggregateVHUs.add(aggregateVHU);
					}
				}
			}

			// Pass 2: Assign unassigned GRAIs to TUs without GRAI
			final ArrayList<GRAI> remainingGrais = new ArrayList<>(unassignedGrais);
			for (final I_M_HU tu : tusWithoutGrai)
			{
				if (remainingGrais.isEmpty()) {break;}

				final GRAI grai = remainingGrais.remove(0);
				setGRAIAttribute(tu, grai);
			}

			// Store remaining unassigned GRAIs as comma-separated on aggregate VHUs
			if (!remainingGrais.isEmpty() && !aggregateVHUs.isEmpty())
			{
				final I_M_HU firstAggregateVHU = aggregateVHUs.get(0);
				setGRAIAttribute(firstAggregateVHU, GRAISet.ofCollection(remainingGrais));
			}
			else
			{
				// Clear any remaining aggregate VHU GRAIs
				aggregateVHUs.forEach(this::clearGRAIAttribute);
			}
		}
		else
		{
			throw new AdempiereException("GRAI scanning not supported for this HU type");
		}
	}

	private void setGRAIAttribute(@NonNull final HuId huId, @Nullable final GRAISet graiSet)
	{
		huAttributesBL.updateHUAttribute(huId, AttributeConstants.ATTR_GRAI, GRAISet.toCommaSeparatedStringOrNull(graiSet));
	}

	private void setGRAIAttribute(@NonNull final HuId huId, @Nullable final GRAI grai)
	{
		huAttributesBL.updateHUAttribute(huId, AttributeConstants.ATTR_GRAI, grai != null ? grai.getValue() : null);
	}

	private void setGRAIAttribute(@NonNull final I_M_HU hu, @Nullable final GRAI grai)
	{
		// TODO optimize
		setGRAIAttribute(HuId.ofRepoId(hu.getM_HU_ID()), grai);
	}

	private void setGRAIAttribute(@NonNull final I_M_HU hu, @Nullable final GRAISet graiSet)
	{
		// TODO optimize
		setGRAIAttribute(HuId.ofRepoId(hu.getM_HU_ID()), graiSet);
	}

	private void clearGRAIAttribute(@NonNull final I_M_HU hu)
	{
		// TODO optimize
		setGRAIAttribute(HuId.ofRepoId(hu.getM_HU_ID()), (GRAI)null);
	}

	@NonNull
	private GRAISet getGRAISet(@NonNull final I_M_HU hu)
	{
		return GRAISet.ofNullableCommaSeparated(huAttributesBL.getHUAttributeValue(hu, AttributeConstants.ATTR_GRAI));
	}
}
