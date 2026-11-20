package de.metas.handlingunits.pporder.api.impl;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import org.eevolution.api.BOMComponentType;
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

		final I_PP_Order ppOrder = newInstance(I_PP_Order.class);
		ppOrder.setM_Product_ID(productId.getRepoId());
		ppOrder.setC_UOM_ID(huTestHelper.uomEach.getC_UOM_ID());
		save(ppOrder);

		bomLineWithStoredConfig = newInstance(I_PP_Order_BOMLine.class);
		bomLineWithStoredConfig.setPP_Order_ID(ppOrder.getPP_Order_ID());
		bomLineWithStoredConfig.setComponentType(BOMComponentType.CoProduct.getCode());
		bomLineWithStoredConfig.setC_UOM_ID(huTestHelper.uomEach.getC_UOM_ID());
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

		final I_M_HU_LUTU_Configuration newConfig = PPOrderBOMLineDocumentLUTUConfigurationHandler.instance.createNewLUTUConfiguration(bomLineWithStoredConfig);
		assertThat(newConfig.getM_LU_HU_PI_Item_ID()).isEqualTo(itemOfProduct.getM_HU_PI_Item_ID());
		assertThat(newConfig.getQtyTU()).isEqualByComparingTo("4");
	}

	@Test
	public void derived_config_stays_on_stored_LU_PI_when_another_LU_PI_also_has_an_item_for_the_product()
	{
		final I_M_HU_PI otherLU = huTestHelper.createHUDefinition("otherLU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI_Item itemOfOtherLU = huTestHelper.createHU_PI_Item_IncludedHU(otherLU, piTU, new BigDecimal("5"));
		itemOfOtherLU.setM_Product_ID(productId.getRepoId());
		save(itemOfOtherLU);

		final I_M_HU_PI_Item itemOfStoredLU = huTestHelper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("4"));
		itemOfStoredLU.setM_Product_ID(productId.getRepoId());
		save(itemOfStoredLU);

		final I_M_HU_LUTU_Configuration newConfig = PPOrderBOMLineDocumentLUTUConfigurationHandler.instance.createNewLUTUConfiguration(bomLineWithStoredConfig);

		assertThat(newConfig.getM_LU_HU_PI_ID()).isEqualTo(storedConfig.getM_LU_HU_PI_ID());
		assertThat(newConfig.getM_LU_HU_PI_Item_ID()).isEqualTo(itemOfStoredLU.getM_HU_PI_Item_ID());
	}

	@Test
	public void keeps_stored_config_when_it_already_uses_the_product_specific_LU_item()
	{
		final I_M_HU_PI_Item itemOfProduct = huTestHelper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("4"));
		itemOfProduct.setM_Product_ID(productId.getRepoId());
		save(itemOfProduct);
		final I_M_HU_LUTU_Configuration configOnProductItem = PPOrderBOMLineDocumentLUTUConfigurationHandler.instance.createNewLUTUConfiguration(bomLineWithStoredConfig);
		assertThat(configOnProductItem.getM_LU_HU_PI_Item_ID()).isEqualTo(itemOfProduct.getM_HU_PI_Item_ID());
		save(configOnProductItem);
		bomLineWithStoredConfig.setM_HU_LUTU_Configuration(configOnProductItem);
		save(bomLineWithStoredConfig);

		final I_M_HU_LUTU_Configuration result = PPOrderBOMLineDocumentLUTUConfigurationHandler.instance.getCurrentLUTUConfigurationOrNull(bomLineWithStoredConfig);

		assertThat(result).isNotNull();
		assertThat(result.getM_HU_LUTU_Configuration_ID()).isEqualTo(configOnProductItem.getM_HU_LUTU_Configuration_ID());
	}

	/** the partner of the BOM line's manufacturing order decides which product-specific LU items apply */
	@Test
	public void product_specific_LU_item_of_the_orders_partner_supersedes_only_for_that_partner()
	{
		final I_C_BPartner partnerA = BusinessTestHelper.createBPartner("A");
		final I_M_HU_PI_Item itemOfProductAndPartnerA = huTestHelper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("4"));
		itemOfProductAndPartnerA.setM_Product_ID(productId.getRepoId());
		itemOfProductAndPartnerA.setC_BPartner_ID(partnerA.getC_BPartner_ID());
		save(itemOfProductAndPartnerA);

		assertThat(PPOrderBOMLineDocumentLUTUConfigurationHandler.instance.getCurrentLUTUConfigurationOrNull(bomLineWithStoredConfig))
				.as("order without partner: the item of partner A does not apply").isNotNull();

		final I_PP_Order ppOrder = InterfaceWrapperHelper.create(bomLineWithStoredConfig.getPP_Order(), I_PP_Order.class);
		ppOrder.setC_BPartner_ID(partnerA.getC_BPartner_ID());
		save(ppOrder);

		assertThat(PPOrderBOMLineDocumentLUTUConfigurationHandler.instance.getCurrentLUTUConfigurationOrNull(bomLineWithStoredConfig)).isNull();
		final I_M_HU_LUTU_Configuration newConfig = PPOrderBOMLineDocumentLUTUConfigurationHandler.instance.createNewLUTUConfiguration(bomLineWithStoredConfig);
		assertThat(newConfig.getM_LU_HU_PI_ID()).isEqualTo(piLU.getM_HU_PI_ID());
		assertThat(newConfig.getM_LU_HU_PI_Item_ID()).isEqualTo(itemOfProductAndPartnerA.getM_HU_PI_Item_ID());
	}

	@Test
	public void keeps_stored_config_without_LU_when_product_specific_LU_item_exists()
	{
		final I_M_HU_LUTU_Configuration configWithoutLU = newInstance(I_M_HU_LUTU_Configuration.class);
		configWithoutLU.setM_HU_PI_Item_Product_ID(storedConfig.getM_HU_PI_Item_Product_ID());
		save(configWithoutLU);
		bomLineWithStoredConfig.setM_HU_LUTU_Configuration(configWithoutLU);
		save(bomLineWithStoredConfig);

		final I_M_HU_PI_Item itemOfProduct = huTestHelper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("4"));
		itemOfProduct.setM_Product_ID(productId.getRepoId());
		save(itemOfProduct);

		final I_M_HU_LUTU_Configuration result = PPOrderBOMLineDocumentLUTUConfigurationHandler.instance.getCurrentLUTUConfigurationOrNull(bomLineWithStoredConfig);

		assertThat(result).isNotNull();
		assertThat(result.getM_HU_LUTU_Configuration_ID()).isEqualTo(configWithoutLU.getM_HU_LUTU_Configuration_ID());
	}
}
