package de.metas.handlingunits.generichumodel;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.impl.SSCC18CodeBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Properties;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

class HURepositoryTest
{
	private int sscc18SerialNo = 0;
	private I_M_HU_PI_Item_Product huPIItemProductRecord;
	private I_C_UOM uomRecord;
	private I_M_HU_PI_Item huPIItemPallet;
	private HUTestHelper huTestHelper;
	private SSCC18CodeBL sscc18CodeBL;
	private HURepository huRepository;
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		// AdempiereTestHelper.get().init(); // this is done by huTestHelper
		huTestHelper = HUTestHelper.newInstanceOutOfTrx(); // we need to do this before registering our custom SSCC18CodeBL

		sscc18SerialNo = 0;
		sscc18CodeBL = new SSCC18CodeBL(orgId -> ++sscc18SerialNo);
		Services.registerService(ISSCC18CodeBL.class, sscc18CodeBL);

		Services.get(ISysConfigBL.class).setValue(SSCC18CodeBL.SYSCONFIG_ManufacturerCode, "111111", ClientId.METASFRESH, OrgId.ANY);

		// setup HU packing instructions
		uomRecord = newInstance(I_C_UOM.class);
		uomRecord.setX12DE355("X12DE355");
		saveRecord(uomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(productRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final I_M_HU_PI huDefPalet = huTestHelper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_PackingMaterial(huDefPalet, huTestHelper.pmPalet);

		final I_M_HU_PI huDefIFCO = huTestHelper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		huTestHelper.createHU_PI_Item_PackingMaterial(huDefIFCO, huTestHelper.pmIFCO);
		final I_M_HU_PI_Item maItemIFCO = huTestHelper.createHU_PI_Item_Material(huDefIFCO);
		huPIItemPallet = huTestHelper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, TEN);

		huPIItemProductRecord = huTestHelper.assignProduct(maItemIFCO, productId, new BigDecimal("5"), uomRecord);

		huRepository = new HURepository();
	}

	@Test
	void getById()
	{
		// given
		final Properties ctx = Env.getCtx();
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext(ctx, ClientAndOrgId.ofClientAndOrg(Env.getAD_Client_ID(), Env.getAD_Org_ID(ctx)));

		final I_M_Attribute attrRecord = newInstance(I_M_Attribute.class);
		attrRecord.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		attrRecord.setValue(HUAttributeConstants.ATTR_SSCC18_Value.getCode());
		saveRecord(attrRecord);

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final I_M_HU_PI_Attribute huPIAttributerecord = huTestHelper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attrRecord)
				.setM_HU_PI(handlingUnitsDAO.getIncludedPI(huPIItemPallet)));

		setupPackagingGTINs();

		final I_M_HU lu = huTestHelper.createLU(
				huContext,
				huPIItemPallet,
				huPIItemProductRecord,
				new BigDecimal("49"));

		final String sscc18String = sscc18CodeBL.generate(OrgId.ANY).toString();
		final I_M_HU_Attribute huAttrRecord = newInstance(I_M_HU_Attribute.class);
		huAttrRecord.setM_Attribute_ID(attrRecord.getM_Attribute_ID());
		huAttrRecord.setM_HU_ID(lu.getM_HU_ID());
		huAttrRecord.setValue(sscc18String);
		huAttrRecord.setM_HU_PI_Attribute_ID(huPIAttributerecord.getM_HU_PI_Attribute_ID());
		saveRecord(huAttrRecord);

		// when
		final HU result = huRepository.getById(HuId.ofRepoId(lu.getM_HU_ID()));

		// then
		assertThat(result.getProductQtysInStockUOM()).hasSize(1);
		assertThat(result.getProductQtysInStockUOM().get(productId).toBigDecimal()).isEqualByComparingTo("49");
		assertThat(result.getAttributes().getValueAsString(HUAttributeConstants.ATTR_SSCC18_Value)).isEqualTo(sscc18String);

		assertThat(result.getPackagingGTINs()).containsExactly(
				entry(BPartnerId.ofRepoId(10), "LU-GTIN1"),
				entry(BPartnerId.ofRepoId(20), "LU-GTIN2"));

		assertThat(result.getChildHUs())
				.hasSize(10)
				.extracting(childHU -> childHU.getProductQtysInStockUOM().get(productId))
				.containsOnly(
						new Quantity(new BigDecimal("5"), uomRecord),
						new Quantity(new BigDecimal("5"), uomRecord),
						new Quantity(new BigDecimal("5"), uomRecord),
						new Quantity(new BigDecimal("5"), uomRecord),
						new Quantity(new BigDecimal("5"), uomRecord),
						new Quantity(new BigDecimal("5"), uomRecord),
						new Quantity(new BigDecimal("5"), uomRecord),
						new Quantity(new BigDecimal("5"), uomRecord),
						new Quantity(new BigDecimal("5"), uomRecord),
						new Quantity(new BigDecimal("4"), uomRecord));

		assertThat(result.getChildHUs()).allSatisfy(childHU -> {
			assertThat(childHU.getPackagingGTINs()).containsExactly(entry(BPartnerId.ofRepoId(10), "TU-GTIN1"));
		});
	}

	private void setupPackagingGTINs()
	{
		final I_C_BPartner_Product bPartnerProductLURecord1 = newInstance(I_C_BPartner_Product.class);
		bPartnerProductLURecord1.setM_Product_ID(huTestHelper.pmPalet.getM_Product_ID());
		bPartnerProductLURecord1.setC_BPartner_ID(10);
		bPartnerProductLURecord1.setGTIN("LU-GTIN1");
		saveRecord(bPartnerProductLURecord1);

		final I_C_BPartner_Product bPartnerProductLURecord2 = newInstance(I_C_BPartner_Product.class);
		bPartnerProductLURecord2.setM_Product_ID(huTestHelper.pmPalet.getM_Product_ID());
		bPartnerProductLURecord2.setC_BPartner_ID(20);
		bPartnerProductLURecord2.setGTIN("LU-GTIN2");
		saveRecord(bPartnerProductLURecord2);

		final I_C_BPartner_Product bPartnerProductTURecord1 = newInstance(I_C_BPartner_Product.class);
		bPartnerProductTURecord1.setM_Product_ID(huTestHelper.pmIFCO.getM_Product_ID());
		bPartnerProductTURecord1.setC_BPartner_ID(10);
		bPartnerProductTURecord1.setGTIN("TU-GTIN1");
		saveRecord(bPartnerProductTURecord1);
	}
}
