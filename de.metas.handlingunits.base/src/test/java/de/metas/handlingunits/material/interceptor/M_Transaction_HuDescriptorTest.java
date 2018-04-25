package de.metas.handlingunits.material.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.ProductDescriptor;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class M_Transaction_HuDescriptorTest
{

	private static final BigDecimal THIRTY_IFCOS_PER_PALET = new BigDecimal("30");
	private static final BigDecimal FOURTY_TOMATOES_PER_IFCO = new BigDecimal("40");
	private static final BigDecimal TOTAL_CU_QTY = FOURTY_TOMATOES_PER_IFCO.multiply(THIRTY_IFCOS_PER_PALET);

	private I_M_HU_PI huDefPalet;
	private I_M_HU_PI huDefIFCO;

	private HUTestHelper helper;

	@Before
	public void init()
	{
		helper = new HUTestHelper();

		// HU PI: IFCO
		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
		helper.assignProduct(itemMA, helper.pTomato, FOURTY_TOMATOES_PER_IFCO, helper.uomEach);
		helper.createHU_PI_Item_PackingMaterial(huDefIFCO, helper.pmIFCO);

		// HU PI: Palet
		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, THIRTY_IFCOS_PER_PALET);
		helper.createHU_PI_Item_PackingMaterial(huDefPalet, helper.pmPalet);

		helper.attr_CountryMadeIn.setIsStorageRelevant(true);
		save(helper.attr_CountryMadeIn);
	}

	@Test
	public void createHuDescriptorsForInOutLine()
	{
		// create an inoutline and a transaction
		final I_M_InOutLine inOutLine = newInstance(I_M_InOutLine.class);
		save(inOutLine);

		final I_M_Transaction transaction = helper.createMTransaction(
				X_M_Transaction.MOVEMENTTYPE_VendorReceipts,
				helper.pTomato,
				TOTAL_CU_QTY);
		transaction.setM_InOutLine(inOutLine);
		save(transaction);

		// create a palet and assign it to our inout line
		final List<I_M_HU> huPalets = helper.createHUsFromSimplePI(transaction, huDefPalet);
		assertThat(huPalets).hasSize(1);

		Services.get(IHUAssignmentBL.class).assignHU(inOutLine, huPalets.get(0), ITrx.TRXNAME_ThreadInherited);

		final IAttributeStorageFactory attributeStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(huPalets.get(0));
		attributeStorage.setValue(helper.attr_CountryMadeIn, HUTestHelper.COUNTRYMADEIN_RO);
		attributeStorage.saveChangesIfNeeded();

		// retrieve our countryMadeIn attribute-value and make sure that the AttributeKeys tool will be able to work with it
		final I_M_AttributeValue attributeValue = Services.get(IAttributeDAO.class).retrieveAttributeValueOrNull(
				helper.attr_CountryMadeIn,
				HUTestHelper.COUNTRYMADEIN_RO);
		assertThat(attributeValue).isNotNull();
		assertThat(attributeValue.getM_AttributeValue_ID()).isGreaterThan(0);

		//
		// invoke the method under test
		final List<HUDescriptor> huDescriptorsForInOutLine = M_Transaction_HuDescriptor.INSTANCE
				.createHuDescriptorsForInOutLine(inOutLine, false);

		assertThat(huDescriptorsForInOutLine).hasSize(1);
		final HUDescriptor huDescriptor = huDescriptorsForInOutLine.get(0);

		assertThat(huDescriptor.getHuId()).isEqualTo(huPalets.get(0).getM_HU_ID()); // within the palet, everything is homogenous, that's why we expect the palet's ID (and not e.g. the IFCO's).
		assertThat(huDescriptor.getQuantity()).isEqualByComparingTo(TOTAL_CU_QTY);
		assertThat(huDescriptor.getQuantityDelta()).isEqualByComparingTo(TOTAL_CU_QTY);

		final ProductDescriptor productDescriptor = huDescriptor.getProductDescriptor();

		assertThat(productDescriptor.getProductId()).isEqualTo(helper.pTomato.getM_Product_ID());
		assertThat(productDescriptor.getStorageAttributesKey()).isNotEqualTo(AttributesKey.NONE);
		assertThat(productDescriptor.getStorageAttributesKey().getAttributeValueIds()).containsOnly(attributeValue.getM_AttributeValue_ID());
	}
}
