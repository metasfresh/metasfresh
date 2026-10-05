package de.metas.handlingunits.contracts.refund;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.refund.packaging.RefundPackagingMaterialProvider;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Resolves the packing material of an order line from its packing instruction:
 * order line, packing instruction item product, packing instruction item, packing instruction version, packing material.
 */
@Service
public class HURefundPackagingMaterialProvider implements RefundPackagingMaterialProvider
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IHUPIItemProductDAO piItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Override
	@NonNull
	public Optional<Integer> getPackingMaterialId(@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId, I_C_OrderLine.class);

		final HUPIItemProductId piItemProductId = HUPIItemProductId.ofRepoIdOrNull(orderLine.getM_HU_PI_Item_Product_ID());
		if (piItemProductId == null)
		{
			return Optional.empty();
		}

		final I_M_HU_PI_Item_Product piItemProduct = piItemProductDAO.getRecordById(piItemProductId);
		final I_M_HU_PI_Item piItem = piItemProduct.getM_HU_PI_Item();
		if (piItem == null)
		{
			return Optional.empty();
		}

		final I_M_HU_PI_Version piVersion = handlingUnitsDAO.retrievePIVersionById(HuPackingInstructionsVersionId.ofRepoId(piItem.getM_HU_PI_Version_ID()));
		final ImmutableSet<Integer> packingMaterialIds = handlingUnitsDAO.retrievePIItems(piVersion, BPartnerId.ofRepoIdOrNull(orderLine.getC_BPartner_ID()))
				.stream()
				.filter(item -> X_M_HU_PI_Item.ITEMTYPE_PackingMaterial.equals(item.getItemType()))
				.map(I_M_HU_PI_Item::getM_HU_PackingMaterial_ID)
				.filter(packingMaterialId -> packingMaterialId > 0)
				.collect(ImmutableSet.toImmutableSet());

		// a packing instruction with more than one packing material is a masterdata error; it must not stop the invoice candidate update, so the line gets no bonus
		if (packingMaterialIds.size() > 1)
		{
			Loggables.addLog("Order line {} has no refund packaging, because M_HU_PI_Version_ID={} has more than one packing material: {}", orderLineId, piVersion.getM_HU_PI_Version_ID(), packingMaterialIds);
			return Optional.empty();
		}

		return packingMaterialIds.stream().findFirst();
	}
}
