package de.metas.handlingunits.contracts.refund;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.refund.packaging.RefundPackagingMaterialProvider;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Resolves the packing material of a packing instruction:
 * packing instruction item product, packing instruction item, packing instruction version, packing material.
 */
@Service
public class HURefundPackagingMaterialProvider implements RefundPackagingMaterialProvider
{
	private final IHUPIItemProductDAO piItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Override
	@NonNull
	public Optional<Integer> getPackingMaterialId(@NonNull final HUPIItemProductId piItemProductId, @Nullable final BPartnerId bpartnerId)
	{
		final I_M_HU_PI_Item_Product piItemProduct = piItemProductDAO.getRecordById(piItemProductId);
		final I_M_HU_PI_Item piItem = piItemProduct.getM_HU_PI_Item();
		if (piItem == null)
		{
			return Optional.empty();
		}

		final I_M_HU_PI_Version piVersion = handlingUnitsDAO.retrievePIVersionById(HuPackingInstructionsVersionId.ofRepoId(piItem.getM_HU_PI_Version_ID()));
		final ImmutableSet<Integer> packingMaterialIds = handlingUnitsDAO.retrievePIItems(piVersion, bpartnerId)
				.stream()
				.filter(item -> X_M_HU_PI_Item.ITEMTYPE_PackingMaterial.equals(item.getItemType()))
				.map(I_M_HU_PI_Item::getM_HU_PackingMaterial_ID)
				.filter(packingMaterialId -> packingMaterialId > 0)
				.collect(ImmutableSet.toImmutableSet());

		// a packing instruction with more than one packing material is a masterdata error; it must not stop the invoice candidate update, so the line gets no bonus
		if (packingMaterialIds.size() > 1)
		{
			Loggables.addLog("M_HU_PI_Item_Product_ID={} has no refund packaging, because M_HU_PI_Version_ID={} has more than one packing material: {}", piItemProductId.getRepoId(), piVersion.getM_HU_PI_Version_ID(), packingMaterialIds);
			return Optional.empty();
		}

		return packingMaterialIds.stream().findFirst();
	}
}
