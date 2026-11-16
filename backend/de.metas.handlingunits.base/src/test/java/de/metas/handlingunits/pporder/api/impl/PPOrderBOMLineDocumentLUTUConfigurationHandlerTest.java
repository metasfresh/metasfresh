package de.metas.handlingunits.pporder.api.impl;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;

public class PPOrderBOMLineDocumentLUTUConfigurationHandlerTest
{
	private HUTestHelper huTestHelper;
	private ProductId productId;
	private I_M_HU_PI piTU;
	private I_M_HU_PI piLU;
	private I_PP_Order_BOMLine bomLineWithStoredConfig;
	private I_M_HU_LUTU_Configuration storedConfig;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		huTestHelper = new HUTestHelper();
		productId = huTestHelper.pTomatoProductId;

		piTU = huTestHelper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item piTU_Item = huTestHelper.createHU_PI_Item_Material(piTU);
		final I_M_HU_PI_Item_Product piTU_Item_Product = huTestHelper.assignProduct(piTU_Item, productId, TEN, huTestHelper.uomEach);

		// 3 TUs fit onto 1 LU
		piLU = huTestHelper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("3"));

		storedConfig = Services.get(ILUTUConfigurationFactory.class).createLUTUConfiguration(piTU_Item_Product, productId, UomId.ofRepoId(huTestHelper.uomEach.getC_UOM_ID()), null, true);
		save(storedConfig);

		bomLineWithStoredConfig = newInstance(I_PP_Order_BOMLine.class);
		bomLineWithStoredConfig.setM_Product_ID(productId.getRepoId());
		bomLineWithStoredConfig.setM_HU_LUTU_Configuration(storedConfig);
		save(bomLineWithStoredConfig);
	}

	@Test
	public void keeps_stored_config_when_no_product_specific_LU_item_exists()
	{
		final I_M_HU_LUTU_Configuration result = PPOrderBOMLineDocumentLUTUConfigurationHandler.instance.getCurrentLUTUConfigurationOrNull(bomLineWithStoredConfig);

		assertThat(result).isNotNull();
		assertThat(result.getM_HU_LUTU_Configuration_ID()).isEqualTo(storedConfig.getM_HU_LUTU_Configuration_ID());
	}

	@Test
	public void drops_stored_config_when_product_specific_LU_item_exists()
	{
		final I_M_HU_PI_Item itemOfProduct = huTestHelper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("4"));
		itemOfProduct.setM_Product_ID(productId.getRepoId());
		save(itemOfProduct);

		assertThat(PPOrderBOMLineDocumentLUTUConfigurationHandler.instance.getCurrentLUTUConfigurationOrNull(bomLineWithStoredConfig)).isNull();
	}
}
