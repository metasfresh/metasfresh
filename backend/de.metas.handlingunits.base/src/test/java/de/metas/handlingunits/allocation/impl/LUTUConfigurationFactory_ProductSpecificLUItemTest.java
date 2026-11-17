package de.metas.handlingunits.allocation.impl;

import de.metas.bpartner.BPartnerId;
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
 * An LU->TU item carrying a product is used for that product only; every other product keeps the generic item.
 */
public class LUTUConfigurationFactory_ProductSpecificLUItemTest extends AbstractHUTest
{
	private LUTUConfigurationFactory lutuFactory;

	private ProductId productXY;
	private ProductId otherProduct;
	private I_M_HU_PI luPI;
	private I_M_HU_PI_Item_Product tuPIItemProductXY;
	private I_M_HU_PI_Item_Product tuPIItemProductOther;
	private I_M_HU_PI_Item genericItem30;
	private I_M_HU_PI_Item productItem40;

	@Override
	protected void initialize()
	{
		lutuFactory = (LUTUConfigurationFactory)Services.get(ILUTUConfigurationFactory.class);
		productXY = pTomatoId;
		otherProduct = pSaladId;

		final I_M_HU_PI tuPI = helper.createHUDefinition("IFCO", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuMaterialItem = helper.createHU_PI_Item_Material(tuPI);
		tuPIItemProductXY = helper.assignProduct(tuMaterialItem, productXY, BigDecimal.TEN, uomEach);
		tuPIItemProductOther = helper.assignProduct(tuMaterialItem, otherProduct, BigDecimal.TEN, uomEach);

		luPI = helper.createHUDefinition("Pallet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		genericItem30 = helper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("30"), (BPartnerId)null);
		productItem40 = helper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("40"), (BPartnerId)null);
		productItem40.setM_Product_ID(productXY.getRepoId());
		InterfaceWrapperHelper.save(productItem40);
	}

	private I_M_HU_LUTU_Configuration create(final ProductId cuProductId, final HuPackingInstructionsId luPIId)
	{
		return lutuFactory.createLUTUConfiguration(
				cuProductId.equals(productXY) ? tuPIItemProductXY : tuPIItemProductOther,
				cuProductId,
				UomId.ofRepoId(uomEach.getC_UOM_ID()),
				null,
				false,
				luPIId,
				BigDecimal.ZERO);
	}

	@Test
	void productXY_gets40()
	{
		final I_M_HU_LUTU_Configuration config = create(productXY, null);
		assertThat(config.getM_LU_HU_PI_Item_ID()).isEqualTo(productItem40.getM_HU_PI_Item_ID());
		assertThat(config.getQtyTU()).isEqualByComparingTo("40");
	}

	@Test
	void otherProduct_gets30()
	{
		final I_M_HU_LUTU_Configuration config = create(otherProduct, null);
		assertThat(config.getM_LU_HU_PI_Item_ID()).isEqualTo(genericItem30.getM_HU_PI_Item_ID());
		assertThat(config.getQtyTU()).isEqualByComparingTo("30");
	}

	@Test
	void productXY_withExplicitLUPI_gets40()
	{
		final I_M_HU_LUTU_Configuration config = create(productXY, HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()));
		assertThat(config.getM_LU_HU_PI_Item_ID()).isEqualTo(productItem40.getM_HU_PI_Item_ID());
		assertThat(config.getQtyTU()).isEqualByComparingTo("40");
	}

	@Test
	void otherProduct_withExplicitLUPI_gets30()
	{
		final I_M_HU_LUTU_Configuration config = create(otherProduct, HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()));
		assertThat(config.getM_LU_HU_PI_Item_ID()).isEqualTo(genericItem30.getM_HU_PI_Item_ID());
		assertThat(config.getQtyTU()).isEqualByComparingTo("30");
	}
}
