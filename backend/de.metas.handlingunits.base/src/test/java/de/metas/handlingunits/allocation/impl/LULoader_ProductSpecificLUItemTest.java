package de.metas.handlingunits.allocation.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link LULoader} builds LUs with the LU->TU item that fits the given product: the product-specific item for that product,
 * the generic item for every other product (or none).
 */
class LULoader_ProductSpecificLUItemTest extends AbstractHUTest
{
	private ProductId productXY;
	private ProductId otherProduct;
	private I_M_HU_PI tuPI;
	private I_M_HU_PI_Item genericItem2;
	private I_M_HU_PI_Item productItem3;

	@Override
	protected void initialize()
	{
		productXY = pTomatoId;
		otherProduct = pSaladId;

		tuPI = helper.createHUDefinition("IFCO", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI luPI = helper.createHUDefinition("Pallet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);

		genericItem2 = createLUItem(luPI, new BigDecimal("2"), null);
		productItem3 = createLUItem(luPI, new BigDecimal("3"), productXY);
	}

	private I_M_HU_PI_Item createLUItem(final I_M_HU_PI luPI, final BigDecimal qty, final ProductId productId)
	{
		final I_M_HU_PI_Item item = helper.createHU_PI_Item_IncludedHU(luPI, tuPI, qty, (BPartnerId)null);
		InterfaceWrapperHelper.setValue(item, I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID, null);
		InterfaceWrapperHelper.setValue(item, I_M_HU_PI_Item.COLUMNNAME_M_Product_ID, productId != null ? productId.getRepoId() : null);
		InterfaceWrapperHelper.save(item);
		return item;
	}

	@Test
	void productXY_fillsLUsWithTheProductSpecificItem()
	{
		final LULoader luLoader = new LULoader(helper.getHUContext());
		final I_M_HU tu1 = createTU();
		final I_M_HU tu2 = createTU();
		final I_M_HU tu3 = createTU();
		final I_M_HU tu4 = createTU();
		luLoader.addTU(tu1, productXY);
		luLoader.addTU(tu2, productXY);
		luLoader.addTU(tu3, productXY);
		luLoader.addTU(tu4, productXY);

		assertThat(luLoader.getLU_HUs()).as("3 TUs per LU: 4 TUs need 2 LUs").hasSize(2);
		assertThat(parentPIItemOf(tu1)).isEqualTo(productItem3);
		assertThat(parentPIItemOf(tu3)).isEqualTo(productItem3);
		assertThat(parentPIItemOf(tu4)).isEqualTo(productItem3);
	}

	@Test
	void otherProduct_keepsTheGenericItem()
	{
		final LULoader luLoader = new LULoader(helper.getHUContext());
		final I_M_HU tu1 = createTU();
		final I_M_HU tu2 = createTU();
		final I_M_HU tu3 = createTU();
		luLoader.addTU(tu1, otherProduct);
		luLoader.addTU(tu2, otherProduct);
		luLoader.addTU(tu3, otherProduct);

		assertThat(luLoader.getLU_HUs()).as("2 TUs per LU: 3 TUs need 2 LUs").hasSize(2);
		assertThat(parentPIItemOf(tu1)).isEqualTo(genericItem2);
		assertThat(parentPIItemOf(tu3)).isEqualTo(genericItem2);
	}

	@Test
	void noProduct_keepsTheGenericItem()
	{
		final LULoader luLoader = new LULoader(helper.getHUContext());
		final I_M_HU tu1 = createTU();
		luLoader.addTU(tu1);

		assertThat(parentPIItemOf(tu1)).isEqualTo(genericItem2);
	}

	private I_M_HU createTU()
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, helper.contextProvider);
		hu.setM_HU_PI_Version(Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(tuPI));
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		InterfaceWrapperHelper.save(hu);
		return hu;
	}

	private I_M_HU_PI_Item parentPIItemOf(final I_M_HU tuHU)
	{
		InterfaceWrapperHelper.refresh(tuHU);
		return Services.get(IHandlingUnitsBL.class).getPIItem(tuHU.getM_HU_Item_Parent());
	}
}
