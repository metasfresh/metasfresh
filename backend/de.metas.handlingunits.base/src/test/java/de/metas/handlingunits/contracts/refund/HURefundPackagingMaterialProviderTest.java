package de.metas.handlingunits.contracts.refund;

import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.order.OrderLineId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class HURefundPackagingMaterialProviderTest
{
	private HURefundPackagingMaterialProvider provider;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		provider = new HURefundPackagingMaterialProvider();
	}

	@Test
	public void orderLineWithPackingInstruction_hasThePackingMaterialOfItsPackingInstruction()
	{
		final I_M_HU_PackingMaterial packingMaterial = newInstance(I_M_HU_PackingMaterial.class);
		saveRecord(packingMaterial);

		final I_M_HU_PI pi = newInstance(I_M_HU_PI.class);
		saveRecord(pi);
		final I_M_HU_PI_Version piVersion = newInstance(I_M_HU_PI_Version.class);
		piVersion.setM_HU_PI_ID(pi.getM_HU_PI_ID());
		piVersion.setHU_UnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		piVersion.setIsCurrent(true);
		saveRecord(piVersion);

		final I_M_HU_PI_Item packingMaterialItem = newInstance(I_M_HU_PI_Item.class);
		packingMaterialItem.setM_HU_PI_Version_ID(piVersion.getM_HU_PI_Version_ID());
		packingMaterialItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_PackingMaterial);
		packingMaterialItem.setM_HU_PackingMaterial_ID(packingMaterial.getM_HU_PackingMaterial_ID());
		saveRecord(packingMaterialItem);

		final I_M_HU_PI_Item materialItem = newInstance(I_M_HU_PI_Item.class);
		materialItem.setM_HU_PI_Version_ID(piVersion.getM_HU_PI_Version_ID());
		materialItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_Material);
		saveRecord(materialItem);

		final I_M_HU_PI_Item_Product piItemProduct = newInstance(I_M_HU_PI_Item_Product.class);
		piItemProduct.setM_HU_PI_Item_ID(materialItem.getM_HU_PI_Item_ID());
		saveRecord(piItemProduct);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setM_HU_PI_Item_Product_ID(piItemProduct.getM_HU_PI_Item_Product_ID());
		saveRecord(orderLine);

		assertThat(provider.getPackingMaterialId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID())))
				.contains(packingMaterial.getM_HU_PackingMaterial_ID());
	}

	@Test
	public void orderLineWithoutPackingInstruction_hasNoPackingMaterial()
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		saveRecord(orderLine);

		assertThat(provider.getPackingMaterialId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()))).isEmpty();
	}
}
