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

@Builder
public class GetHUGraiCommand
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

	@NonNull private final HuId huId;

	@NonNull
	public JsonGRAICodesResponse execute()
	{
		final I_M_HU hu = handlingUnitsDAO.getById(huId);

		if (handlingUnitsBL.isVirtual(hu))
		{
			throw new AdempiereException("GRAI scanning not supported for virtual HUs");
		}

		final ArrayList<GRAI> grais = new ArrayList<>();
		final int tuCount;

		if (handlingUnitsBL.isLoadingUnit(hu))
		{
			// LU: collect GRAIs from all child TUs
			int tuCounter = 0;
			for (final I_M_HU_Item item : handlingUnitsDAO.retrieveItems(hu))
			{
				final String itemType = item.getItemType();
				if (X_M_HU_Item.ITEMTYPE_HandlingUnit.equals(itemType))
				{
					// Disaggregated TUs
					for (final I_M_HU childTU : handlingUnitsDAO.retrieveIncludedHUs(item))
					{
						tuCounter++;
						final GRAI grai = GRAI.ofNullable(huAttributesBL.getHUAttributeValue(childTU, AttributeConstants.ATTR_GRAI));
						if (grai != null)
						{
							grais.add(grai);
						}
					}
				}
				else if (X_M_HU_Item.ITEMTYPE_HUAggregate.equals(itemType))
				{
					// Aggregate: item.getQty() = number of TUs represented
					tuCounter += item.getQty().intValue();

					// Read comma-separated GRAIs from the aggregate VHU's attribute
					for (final I_M_HU aggregateVHU : handlingUnitsDAO.retrieveIncludedHUs(item))
					{
						GRAISet.ofNullableCommaSeparated(huAttributesBL.getHUAttributeValue(aggregateVHU, AttributeConstants.ATTR_GRAI))
								.stream()
								.forEach(grais::add);
					}
				}
			}
			tuCount = tuCounter;
		}
		else
		{
			// TU: single GRAI
			tuCount = 1;
			final GRAI grai = GRAI.ofNullable(huAttributesBL.getHUAttributeValue(hu, AttributeConstants.ATTR_GRAI));
			if (grai != null)
			{
				grais.add(grai);
			}
		}

		return JsonGRAICodesResponse.builder()
				.huId(huId.getRepoId())
				.graiCodes(GRAISet.ofCollection(grais).toStringList())
				.tuCount(tuCount)
				.build();
	}

}
