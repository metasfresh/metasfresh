package de.metas.handlingunits.receiptschedule.impl;

import de.metas.business.BusinessTestHelper;
import de.metas.greeting.GreetingRepository;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ReceiptScheduleDocumentLUTUConfigurationHandlerTest
{
	private ReceiptScheduleDocumentLUTUConfigurationHandler handler;

	private HUTestHelper huTestHelper;
	private I_C_UOM productUOM;
	private ProductId productId;
	private I_M_HU_PI_Item_Product piTU_Item_Product;
	private I_M_HU_PI piTU;
	private I_M_HU_PI piLU;

	private I_C_BPartner bpartner;
	private I_C_BPartner_Location bpartnerLocation;
	private I_M_ReceiptSchedule receiptSchedule;
	private I_M_HU_LUTU_Configuration storedConfig;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handler = ReceiptScheduleDocumentLUTUConfigurationHandler.instance; // after init: the singleton looks up services on class load
		huTestHelper = new HUTestHelper();
		SpringContextHolder.registerJUnitBean(new GreetingRepository()); // needed by the M_ReceiptSchedule address interceptor

		productId = huTestHelper.pTomatoProductId;
		productUOM = huTestHelper.uomEach;

		// 10 CUs fit into one TU
		piTU = huTestHelper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item piTU_Item = huTestHelper.createHU_PI_Item_Material(piTU);
		piTU_Item_Product = huTestHelper.assignProduct(piTU_Item, productId, BigDecimal.TEN, productUOM);

		// 3 TUs fit onto 1 LU (generic item)
		piLU = huTestHelper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("3"));

		bpartner = BusinessTestHelper.createBPartner("vendor");
		bpartnerLocation = BusinessTestHelper.createBPartnerLocation(bpartner);

		// receipt schedule with a configuration stored on the generic LU item
		receiptSchedule = createReceiptSchedule(null);
		storedConfig = handler.createNewLUTUConfiguration(receiptSchedule);
		save(storedConfig);
		receiptSchedule.setM_HU_LUTU_Configuration(storedConfig);
		save(receiptSchedule);
	}

	@Test
	public void keeps_stored_config_when_no_product_specific_LU_item_exists()
	{
		assertThat(handler.getCurrentLUTUConfigurationOrNull(receiptSchedule)).isNotNull()
				.extracting(I_M_HU_LUTU_Configuration::getM_HU_LUTU_Configuration_ID)
				.isEqualTo(storedConfig.getM_HU_LUTU_Configuration_ID());
	}

	@Test
	public void keeps_stored_config_when_product_specific_LU_item_is_for_another_product()
	{
		createLUItemOfProduct(piLU, huTestHelper.pSaladProductId, "4");

		assertThat(handler.getCurrentLUTUConfigurationOrNull(receiptSchedule)).isNotNull()
				.extracting(I_M_HU_LUTU_Configuration::getM_HU_LUTU_Configuration_ID)
				.isEqualTo(storedConfig.getM_HU_LUTU_Configuration_ID());
	}

	@Test
	public void drops_stored_config_and_derives_product_specific_one_when_product_specific_LU_item_exists()
	{
		final I_M_HU_PI_Item itemOfProduct = createLUItemOfProduct(piLU, productId, "4");

		assertThat(handler.getCurrentLUTUConfigurationOrNull(receiptSchedule)).isNull();

		final I_M_HU_LUTU_Configuration newConfig = handler.createNewLUTUConfiguration(receiptSchedule);
		assertThat(newConfig.getM_LU_HU_PI_ID()).as("stays on the stored LU packing instruction").isEqualTo(piLU.getM_HU_PI_ID());
		assertThat(newConfig.getM_LU_HU_PI_Item_ID()).isEqualTo(itemOfProduct.getM_HU_PI_Item_ID());
		assertThat(newConfig.getQtyTU()).isEqualByComparingTo("4");
	}

	@Test
	public void keeps_stored_config_when_it_already_uses_the_product_specific_LU_item()
	{
		createLUItemOfProduct(piLU, productId, "4");
		final I_M_HU_LUTU_Configuration configOnProductItem = handler.createNewLUTUConfiguration(receiptSchedule);
		save(configOnProductItem);
		receiptSchedule.setM_HU_LUTU_Configuration(configOnProductItem);
		save(receiptSchedule);

		assertThat(handler.getCurrentLUTUConfigurationOrNull(receiptSchedule)).isNotNull()
				.extracting(I_M_HU_LUTU_Configuration::getM_HU_LUTU_Configuration_ID)
				.isEqualTo(configOnProductItem.getM_HU_LUTU_Configuration_ID());
	}

	@Test
	public void keeps_stored_config_without_LU_when_product_specific_LU_item_exists()
	{
		final I_M_HU_LUTU_Configuration configWithoutLU = newInstance(I_M_HU_LUTU_Configuration.class);
		configWithoutLU.setM_HU_PI_Item_Product_ID(piTU_Item_Product.getM_HU_PI_Item_Product_ID());
		save(configWithoutLU);
		receiptSchedule.setM_HU_LUTU_Configuration(configWithoutLU);
		save(receiptSchedule);
		createLUItemOfProduct(piLU, productId, "4");

		assertThat(handler.getCurrentLUTUConfigurationOrNull(receiptSchedule)).isNotNull()
				.extracting(I_M_HU_LUTU_Configuration::getM_HU_LUTU_Configuration_ID)
				.isEqualTo(configWithoutLU.getM_HU_LUTU_Configuration_ID());
	}

	@Test
	public void order_line_LU_PI_takes_priority_over_the_stored_LU_PI()
	{
		final I_M_HU_PI otherLU = huTestHelper.createHUDefinition("otherLU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI_Item otherLUItem = huTestHelper.createHU_PI_Item_IncludedHU(otherLU, piTU, new BigDecimal("5"));
		createLUItemOfProduct(piLU, productId, "4"); // makes the stored config (on piLU) superseded

		final I_M_ReceiptSchedule rsWithOrderLineLU = createReceiptSchedule(otherLU);
		rsWithOrderLineLU.setM_HU_LUTU_Configuration(storedConfig);
		save(rsWithOrderLineLU);

		final I_M_HU_LUTU_Configuration newConfig = handler.createNewLUTUConfiguration(rsWithOrderLineLU);

		assertThat(newConfig.getM_LU_HU_PI_ID()).isEqualTo(otherLU.getM_HU_PI_ID());
		assertThat(newConfig.getM_LU_HU_PI_Item_ID()).isEqualTo(otherLUItem.getM_HU_PI_Item_ID());
	}

	/**
	 * The schedule's TU packing instruction changed after the configuration was stored; the new TU has no LU item in the stored LU packing instruction.
	 * The derivation would use the schedule's TU and so could not reach the product-specific LU item => the stored configuration (with its LU) is kept.
	 */
	@Test
	public void keeps_stored_config_when_product_specific_LU_item_is_not_reachable_with_the_schedules_current_TU()
	{
		createLUItemOfProduct(piLU, productId, "4"); // product-specific item for the stored config's TU
		final I_M_HU_PI_Item_Product otherTUItemProduct = createOtherTUItemProduct();
		receiptSchedule.setM_HU_PI_Item_Product_ID(otherTUItemProduct.getM_HU_PI_Item_Product_ID());
		save(receiptSchedule);

		assertThat(handler.getCurrentLUTUConfigurationOrNull(receiptSchedule)).isNotNull()
				.extracting(I_M_HU_LUTU_Configuration::getM_HU_LUTU_Configuration_ID)
				.isEqualTo(storedConfig.getM_HU_LUTU_Configuration_ID());
	}

	/**
	 * The schedule's TU packing instruction changed after the configuration was stored; the new TU has a product-specific LU item in the stored LU packing instruction.
	 * The check uses the TU the derivation uses => stale, and the derived configuration is on that product-specific item, in the stored LU packing instruction.
	 */
	@Test
	public void drops_stored_config_when_product_specific_LU_item_is_reachable_with_the_schedules_current_TU()
	{
		final I_M_HU_PI_Item_Product otherTUItemProduct = createOtherTUItemProduct();
		final I_M_HU_PI otherTU = otherTUItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
		huTestHelper.createHU_PI_Item_IncludedHU(piLU, otherTU, new BigDecimal("6")); // generic item for the other TU
		final I_M_HU_PI_Item otherTUItemOfProduct = huTestHelper.createHU_PI_Item_IncludedHU(piLU, otherTU, new BigDecimal("8"));
		otherTUItemOfProduct.setM_Product_ID(productId.getRepoId());
		save(otherTUItemOfProduct);

		receiptSchedule.setM_HU_PI_Item_Product_ID(otherTUItemProduct.getM_HU_PI_Item_Product_ID());
		save(receiptSchedule);

		assertThat(handler.getCurrentLUTUConfigurationOrNull(receiptSchedule)).isNull();

		final I_M_HU_LUTU_Configuration newConfig = handler.createNewLUTUConfiguration(receiptSchedule);
		assertThat(newConfig.getM_HU_PI_Item_Product_ID()).isEqualTo(otherTUItemProduct.getM_HU_PI_Item_Product_ID());
		assertThat(newConfig.getM_LU_HU_PI_ID()).isEqualTo(piLU.getM_HU_PI_ID());
		assertThat(newConfig.getM_LU_HU_PI_Item_ID()).isEqualTo(otherTUItemOfProduct.getM_HU_PI_Item_ID());
	}

	private I_M_HU_PI_Item_Product createOtherTUItemProduct()
	{
		final I_M_HU_PI otherTU = huTestHelper.createHUDefinition("otherTU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item otherTUMaterialItem = huTestHelper.createHU_PI_Item_Material(otherTU);
		return huTestHelper.assignProduct(otherTUMaterialItem, productId, new BigDecimal("5"), productUOM);
	}

	private I_M_HU_PI_Item createLUItemOfProduct(final I_M_HU_PI lu, final ProductId itemProductId, final String qtyTUs)
	{
		final I_M_HU_PI_Item item = huTestHelper.createHU_PI_Item_IncludedHU(lu, piTU, new BigDecimal(qtyTUs));
		item.setM_Product_ID(itemProductId.getRepoId());
		save(item);
		return item;
	}

	private I_M_ReceiptSchedule createReceiptSchedule(final I_M_HU_PI orderLineLU)
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setM_HU_PI_Item_Product(piTU_Item_Product);
		orderLine.setM_Product_ID(productId.getRepoId());
		if (orderLineLU != null)
		{
			orderLine.setM_LU_HU_PI_ID(orderLineLU.getM_HU_PI_ID());
		}
		save(orderLine);

		final I_M_ReceiptSchedule rs = newInstance(I_M_ReceiptSchedule.class);
		rs.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		rs.setM_Product_ID(productId.getRepoId());
		rs.setC_UOM_ID(productUOM.getC_UOM_ID());
		rs.setM_HU_PI_Item_Product_ID(piTU_Item_Product.getM_HU_PI_Item_Product_ID());
		rs.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		rs.setC_BPartner_Location_ID(bpartnerLocation.getC_BPartner_Location_ID());
		rs.setM_Warehouse_ID(huTestHelper.defaultWarehouse.getM_Warehouse_ID());
		save(rs);
		return rs;
	}
}
