package de.metas.handlingunits.contracts.refund;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.refund.packaging.RefundPackagingMaterialProvider;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
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

		final I_M_HU_PackingMaterial packingMaterial = handlingUnitsDAO.retrievePackingMaterialByPIVersionID(
				HuPackingInstructionsVersionId.ofRepoId(piItem.getM_HU_PI_Version_ID()),
				BPartnerId.ofRepoIdOrNull(orderLine.getC_BPartner_ID()));
		return packingMaterial != null
				? Optional.of(packingMaterial.getM_HU_PackingMaterial_ID())
				: Optional.empty();
	}
}
