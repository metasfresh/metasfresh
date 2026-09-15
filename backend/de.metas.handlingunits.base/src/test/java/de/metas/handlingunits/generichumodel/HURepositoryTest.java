package de.metas.handlingunits.generichumodel;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
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
import de.metas.product.asidata.ProductASIDataRepository;
import de.metas.quantity.Quantity;
import de.metas.sscc18.ISSCC18CodeBL;
import de.metas.sscc18.impl.SSCC18CodeBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product_ASI_Data;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

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
		sscc18CodeBL = new SSCC18CodeBL();
		sscc18CodeBL.setOverrideNextSerialNumberProvider(orgId -> ++sscc18SerialNo);
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

		huRepository = new HURepository(new ProductASIDataRepository(Services.get(IQueryBL.class)));
	}

	@Test
	void getById()
	{
		// given
		final Properties ctx = Env.getCtx();
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext(ctx, ClientAndOrgId.ofClientAndOrg(Env.getAD_Client_ID(), Env.getAD_Org_ID(ctx)));

		final I_M_Attribute attrRecord = newInstance(I_M_Attribute.class);
		attrRecord.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		attrRecord.setValue(AttributeConstants.ATTR_SSCC18_Value.getCode());
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

		final String sscc18String = sscc18CodeBL.generate(OrgId.ANY).asString();
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
		assertThat(result.getAttributes().getValueAsString(AttributeConstants.ATTR_SSCC18_Value)).isEqualTo(sscc18String);

		assertThat(result.getAllPackaginGTINs()).containsExactly(
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

		assertThat(result.getChildHUs()).allSatisfy(childHU -> assertThat(childHU.getAllPackaginGTINs()).containsExactly(entry(BPartnerId.ofRepoId(10), "TU-GTIN1")));
	}

	private void setupPackagingGTINs()
	{
		final I_M_Product_ASI_Data asiDataLU1 = newInstance(I_M_Product_ASI_Data.class);
		asiDataLU1.setM_Product_ID(huTestHelper.pmPalet.getM_Product_ID());
		asiDataLU1.setC_BPartner_ID(10);
		asiDataLU1.setGTIN("LU-GTIN1");
		saveRecord(asiDataLU1);

		final I_M_Product_ASI_Data asiDataLU2 = newInstance(I_M_Product_ASI_Data.class);
		asiDataLU2.setM_Product_ID(huTestHelper.pmPalet.getM_Product_ID());
		asiDataLU2.setC_BPartner_ID(20);
		asiDataLU2.setGTIN("LU-GTIN2");
		saveRecord(asiDataLU2);

		final I_M_Product_ASI_Data asiDataTU1 = newInstance(I_M_Product_ASI_Data.class);
		asiDataTU1.setM_Product_ID(huTestHelper.pmIFCO.getM_Product_ID());
		asiDataTU1.setC_BPartner_ID(10);
		asiDataTU1.setGTIN("TU-GTIN1");
		saveRecord(asiDataTU1);
	}

	/**
	 * Verifies the HU-attribute-aware GTIN lookup in {@code HURepository.extractPackagingGTINs}:
	 * <ul>
	 *   <li>A record whose ASI matches the HU's attributes is used (BPartner 10: {@code Country=DE} matches HU {@code Country=DE}).</li>
	 *   <li>For the same BPartner, among matching records the lowest SeqNo wins.</li>
	 *   <li>A wildcard record (no ASI) always matches (BPartner 30).</li>
	 *   <li>A record whose ASI does NOT match is skipped (BPartner 20: {@code Country=IT} vs HU {@code Country=DE}).</li>
	 * </ul>
	 */
	@Test
	void getById_asiAwareGTINResolution()
	{
		// given
		final Properties ctx = Env.getCtx();
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext(ctx, ClientAndOrgId.ofClientAndOrg(Env.getAD_Client_ID(), Env.getAD_Org_ID(ctx)));
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		// Create a CountryOfOrigin string attribute and attach it to the LU's PI so the HU carries it.
		final I_M_Attribute countryAttr = newInstance(I_M_Attribute.class);
		countryAttr.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		countryAttr.setValue("CountryOfOrigin");
		saveRecord(countryAttr);

		final I_M_HU_PI_Attribute luCountryPIAttr = huTestHelper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(countryAttr)
				.setM_HU_PI(handlingUnitsDAO.getIncludedPI(huPIItemPallet)));

		// BPartner 10: matches HU's Country=DE (ASI subset match) — lowest SeqNo wins between this (10) and a wildcard record (20).
		final I_M_Product_ASI_Data bp10_asiDE = newInstance(I_M_Product_ASI_Data.class);
		bp10_asiDE.setM_Product_ID(huTestHelper.pmPalet.getM_Product_ID());
		bp10_asiDE.setC_BPartner_ID(10);
		bp10_asiDE.setM_AttributeSetInstance_ID(createASI(countryAttr, "DE").getM_AttributeSetInstance_ID());
		bp10_asiDE.setSeqNo(10);
		bp10_asiDE.setGTIN("GTIN-BP10-DE");
		saveRecord(bp10_asiDE);

		final I_M_Product_ASI_Data bp10_wildcard = newInstance(I_M_Product_ASI_Data.class);
		bp10_wildcard.setM_Product_ID(huTestHelper.pmPalet.getM_Product_ID());
		bp10_wildcard.setC_BPartner_ID(10);
		bp10_wildcard.setSeqNo(20);
		bp10_wildcard.setGTIN("GTIN-BP10-ANY");
		saveRecord(bp10_wildcard);

		// BPartner 20: ASI Country=IT does NOT match HU's Country=DE → skipped entirely.
		final I_M_Product_ASI_Data bp20_asiIT = newInstance(I_M_Product_ASI_Data.class);
		bp20_asiIT.setM_Product_ID(huTestHelper.pmPalet.getM_Product_ID());
		bp20_asiIT.setC_BPartner_ID(20);
		bp20_asiIT.setM_AttributeSetInstance_ID(createASI(countryAttr, "IT").getM_AttributeSetInstance_ID());
		bp20_asiIT.setSeqNo(10);
		bp20_asiIT.setGTIN("GTIN-BP20-IT");
		saveRecord(bp20_asiIT);

		// BPartner 30: wildcard — always matches.
		final I_M_Product_ASI_Data bp30_wildcard = newInstance(I_M_Product_ASI_Data.class);
		bp30_wildcard.setM_Product_ID(huTestHelper.pmPalet.getM_Product_ID());
		bp30_wildcard.setC_BPartner_ID(30);
		bp30_wildcard.setSeqNo(10);
		bp30_wildcard.setGTIN("GTIN-BP30-ANY");
		saveRecord(bp30_wildcard);

		final I_M_HU lu = huTestHelper.createLU(
				huContext,
				huPIItemPallet,
				huPIItemProductRecord,
				new BigDecimal("10"));

		final I_M_HU_Attribute countryHUAttr = newInstance(I_M_HU_Attribute.class);
		countryHUAttr.setM_Attribute_ID(countryAttr.getM_Attribute_ID());
		countryHUAttr.setM_HU_ID(lu.getM_HU_ID());
		countryHUAttr.setValue("DE");
		countryHUAttr.setM_HU_PI_Attribute_ID(luCountryPIAttr.getM_HU_PI_Attribute_ID());
		saveRecord(countryHUAttr);

		// when
		final HU result = huRepository.getById(HuId.ofRepoId(lu.getM_HU_ID()));

		// then
		assertThat(result.getAllPackaginGTINs()).containsExactly(
				entry(BPartnerId.ofRepoId(10), "GTIN-BP10-DE"),
				entry(BPartnerId.ofRepoId(30), "GTIN-BP30-ANY"));
	}

	private I_M_AttributeSetInstance createASI(@NonNull final I_M_Attribute attribute, @NonNull final String value)
	{
		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		saveRecord(asi);

		final I_M_AttributeInstance ai = newInstance(I_M_AttributeInstance.class);
		ai.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		ai.setM_Attribute_ID(attribute.getM_Attribute_ID());
		ai.setValue(value);
		saveRecord(ai);

		return asi;
	}
}
