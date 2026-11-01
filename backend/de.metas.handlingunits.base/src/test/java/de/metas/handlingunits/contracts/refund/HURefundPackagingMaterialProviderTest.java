package de.metas.handlingunits.contracts.refund;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
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

	private HUPIItemProductId createPIItemProduct(final int... packingMaterialIds)
	{
		final I_M_HU_PI pi = newInstance(I_M_HU_PI.class);
		saveRecord(pi);
		final I_M_HU_PI_Version piVersion = newInstance(I_M_HU_PI_Version.class);
		piVersion.setM_HU_PI_ID(pi.getM_HU_PI_ID());
		piVersion.setHU_UnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		piVersion.setIsCurrent(true);
		saveRecord(piVersion);

		for (final int packingMaterialId : packingMaterialIds)
		{
			final I_M_HU_PI_Item packingMaterialItem = newInstance(I_M_HU_PI_Item.class);
			packingMaterialItem.setM_HU_PI_Version_ID(piVersion.getM_HU_PI_Version_ID());
			packingMaterialItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_PackingMaterial);
			packingMaterialItem.setM_HU_PackingMaterial_ID(packingMaterialId);
			saveRecord(packingMaterialItem);
		}

		final I_M_HU_PI_Item materialItem = newInstance(I_M_HU_PI_Item.class);
		materialItem.setM_HU_PI_Version_ID(piVersion.getM_HU_PI_Version_ID());
		materialItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_Material);
		saveRecord(materialItem);

		final I_M_HU_PI_Item_Product piItemProduct = newInstance(I_M_HU_PI_Item_Product.class);
		piItemProduct.setM_HU_PI_Item_ID(materialItem.getM_HU_PI_Item_ID());
		saveRecord(piItemProduct);

		return HUPIItemProductId.ofRepoId(piItemProduct.getM_HU_PI_Item_Product_ID());
	}

	private int createPackingMaterial()
	{
		final I_M_HU_PackingMaterial packingMaterial = newInstance(I_M_HU_PackingMaterial.class);
		saveRecord(packingMaterial);
		return packingMaterial.getM_HU_PackingMaterial_ID();
	}

	@Test
	public void piItemProductWithPackingInstruction_hasThePackingMaterialOfItsPackingInstruction()
	{
		final int packingMaterialId = createPackingMaterial();
		assertThat(provider.getPackingMaterialId(createPIItemProduct(packingMaterialId), null)).contains(packingMaterialId);
	}

	/** a masterdata error must not abort the caller (the invoice candidate update): no packing material, no bonus */
	@Test
	public void packingInstructionWithTwoPackingMaterials_hasNoPackingMaterial()
	{
		assertThat(provider.getPackingMaterialId(createPIItemProduct(createPackingMaterial(), createPackingMaterial()), null)).isEmpty();
	}

	@Test
	public void packingInstructionWithoutPackingMaterial_hasNoPackingMaterial()
	{
		assertThat(provider.getPackingMaterialId(createPIItemProduct(), null)).isEmpty();
	}
}
