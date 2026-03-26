package de.metas.handlingunits.rest_api.grai;

import de.metas.common.handlingunits.JsonGRAICodesResponse;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;

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

	@NonNull
	public JsonGRAICodesResponse execute()
	{
		final I_M_HU hu = handlingUnitsDAO.getById(huId);

		if (handlingUnitsBL.isVirtual(hu))
		{
			throw new AdempiereException("GRAI scanning not supported for virtual HUs");
		}

		if (handlingUnitsBL.isTransportUnitOrAggregate(hu))
		{
			// TU: set single GRAI directly
			final String value = graiSet.stream().findFirst().map(GRAI::getValue).orElse(null);
			huAttributesBL.updateHUAttribute(huId, AttributeConstants.ATTR_GRAI, value);
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
				final String itemType = item.getItemType();
				if (X_M_HU_Item.ITEMTYPE_HandlingUnit.equals(itemType))
				{
					for (final I_M_HU childTU : handlingUnitsDAO.retrieveIncludedHUs(item))
					{
						final GRAI existingGrai = GRAI.ofNullable(huAttributesBL.getHUAttributeValue(childTU, AttributeConstants.ATTR_GRAI));
						if (existingGrai != null && graiSet.contains(existingGrai))
						{
							// Keep it — it's in the new set; mark as assigned
							unassignedGrais.remove(existingGrai);
						}
						else if (existingGrai != null)
						{
							// Clear it — it's NOT in the new set
							huAttributesBL.updateHUAttribute(HuId.ofRepoId(childTU.getM_HU_ID()), AttributeConstants.ATTR_GRAI, null);
							tusWithoutGrai.add(childTU);
						}
						else
						{
							// No existing GRAI
							tusWithoutGrai.add(childTU);
						}
					}
				}
				else if (X_M_HU_Item.ITEMTYPE_HUAggregate.equals(itemType))
				{
					for (final I_M_HU aggregateVHU : handlingUnitsDAO.retrieveIncludedHUs(item))
					{
						aggregateVHUs.add(aggregateVHU);
					}
				}
			}

			// Pass 2: Assign unassigned GRAIs to TUs without GRAI
			final ArrayList<GRAI> remainingGrais = new ArrayList<>(unassignedGrais);
			int graiIndex = 0;
			for (final I_M_HU tu : tusWithoutGrai)
			{
				if (graiIndex < remainingGrais.size())
				{
					huAttributesBL.updateHUAttribute(HuId.ofRepoId(tu.getM_HU_ID()), AttributeConstants.ATTR_GRAI, remainingGrais.get(graiIndex).getValue());
					graiIndex++;
				}
			}

			// Store remaining unassigned GRAIs as comma-separated on aggregate VHUs
			if (graiIndex < remainingGrais.size() && !aggregateVHUs.isEmpty())
			{
				final String commaSeparated = GRAISet.ofCollection(remainingGrais.subList(graiIndex, remainingGrais.size())).toCommaSeparatedString();
				for (final I_M_HU aggregateVHU : aggregateVHUs)
				{
					huAttributesBL.updateHUAttribute(HuId.ofRepoId(aggregateVHU.getM_HU_ID()), AttributeConstants.ATTR_GRAI, commaSeparated);
					break; // store on first aggregate VHU only
				}
			}
			else
			{
				// Clear any remaining aggregate VHU GRAIs
				for (final I_M_HU aggregateVHU : aggregateVHUs)
				{
					huAttributesBL.updateHUAttribute(HuId.ofRepoId(aggregateVHU.getM_HU_ID()), AttributeConstants.ATTR_GRAI, null);
				}
			}
		}
		else
		{
			throw new AdempiereException("GRAI scanning not supported for this HU type");
		}

		// Re-read fresh state
		return GetHUGraiCommand.builder().huId(huId).build().execute();
	}
}
