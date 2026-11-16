package de.metas.handlingunits.allocation.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A stored LU/TU configuration is superseded if it uses the generic LU item although the product has an LU item of its own.
 */
public class LUTUConfigurationFactory_getLUPIIdSupersededByProductSpecificLUItem_Test extends AbstractHUTest
{
	private ILUTUConfigurationFactory lutuFactory;

	private ProductId productXY;
	private ProductId otherProduct;
	private I_M_HU_PI luPI;
	private I_M_HU_PI_Item tuMaterialItem;
	private I_M_HU_PI_Item_Product tuPIItemProductXY;
	private I_M_HU_PI_Item_Product tuPIItemProductOther;
	private I_M_HU_PI_Item productItem40;

	@Override
	protected void initialize()
	{
		lutuFactory = Services.get(ILUTUConfigurationFactory.class);
		productXY = pTomatoId;
		otherProduct = pSaladId;

		final I_M_HU_PI tuPI = helper.createHUDefinition("IFCO", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		tuMaterialItem = helper.createHU_PI_Item_Material(tuPI);
		tuPIItemProductXY = helper.assignProduct(tuMaterialItem, productXY, BigDecimal.TEN, uomEach);
		tuPIItemProductOther = helper.assignProduct(tuMaterialItem, otherProduct, BigDecimal.TEN, uomEach);

		luPI = helper.createHUDefinition("Pallet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		helper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("30"), (BPartnerId)null);
		productItem40 = helper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("40"), (BPartnerId)null);
		productItem40.setM_Product_ID(productXY.getRepoId());
		InterfaceWrapperHelper.save(productItem40);
	}

	/** a configuration that uses the generic LU item, as it was stored before the product-specific item existed */
	private I_M_HU_LUTU_Configuration createStoredGenericConfig()
	{
		return lutuFactory.createLUTUConfiguration(
				tuPIItemProductOther,
				otherProduct,
				UomId.ofRepoId(uomEach.getC_UOM_ID()),
				null,
				false,
				HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()),
				BigDecimal.ZERO);
	}

	@Test
	void nullConfig_notSuperseded()
	{
		assertThat(lutuFactory.getLUPIIdSupersededByProductSpecificLUItem(null, productXY, null)).isNull();
	}

	@Test
	void genericConfig_productWithOwnItem_returnsStoredLUPI()
	{
		final I_M_HU_LUTU_Configuration stored = createStoredGenericConfig();
		stored.setM_HU_PI_Item_Product_ID(tuPIItemProductXY.getM_HU_PI_Item_Product_ID());

		assertThat(lutuFactory.getLUPIIdSupersededByProductSpecificLUItem(stored, productXY, null))
				.isEqualTo(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()));
	}

	@Test
	void genericConfig_productWithoutOwnItem_notSuperseded()
	{
		assertThat(lutuFactory.getLUPIIdSupersededByProductSpecificLUItem(createStoredGenericConfig(), otherProduct, null)).isNull();
	}

	@Test
	void configAlreadyOnProductItem_notSuperseded()
	{
		final I_M_HU_LUTU_Configuration stored = createStoredGenericConfig();
		stored.setM_HU_PI_Item_Product_ID(tuPIItemProductXY.getM_HU_PI_Item_Product_ID());
		stored.setM_LU_HU_PI_Item_ID(productItem40.getM_HU_PI_Item_ID());

		assertThat(lutuFactory.getLUPIIdSupersededByProductSpecificLUItem(stored, productXY, null)).isNull();
	}

	@Test
	void configWithoutLU_notSuperseded()
	{
		final I_M_HU_LUTU_Configuration stored = createStoredGenericConfig();
		stored.setM_LU_HU_PI_ID(-1);
		stored.setM_LU_HU_PI_Item_ID(-1);

		assertThat(lutuFactory.getLUPIIdSupersededByProductSpecificLUItem(stored, productXY, null)).isNull();
	}

	/** the passed partner (the one the caller derives for), not the stored configuration's own partner, decides */
	@Test
	void usesPassedPartner_notTheStoredConfigsPartner()
	{
		final BPartnerId partnerA = BPartnerId.ofRepoId(BusinessTestHelper.createBPartner("A").getC_BPartner_ID());
		final BPartnerId partnerB = BPartnerId.ofRepoId(BusinessTestHelper.createBPartner("B").getC_BPartner_ID());

		final I_M_HU_LUTU_Configuration stored = createStoredGenericConfig();
		stored.setM_HU_PI_Item_Product_ID(tuPIItemProductXY.getM_HU_PI_Item_Product_ID());
		stored.setC_BPartner_ID(partnerB.getRepoId());

		tuMaterialItem.setC_BPartner_ID(partnerA.getRepoId()); // the TU item is visible for partner A only
		InterfaceWrapperHelper.save(tuMaterialItem);

		assertThat(lutuFactory.getLUPIIdSupersededByProductSpecificLUItem(stored, productXY, partnerA)).isNotNull();
		assertThat(lutuFactory.getLUPIIdSupersededByProductSpecificLUItem(stored, productXY, partnerB)).isNull();
	}
}
