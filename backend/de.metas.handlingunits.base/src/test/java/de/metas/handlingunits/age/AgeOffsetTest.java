/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.handlingunits.age;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.age.process.M_HU_UpdateHUAgeAttributeProcess;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.impl.CopyAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.impl.SumAggregationStrategy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AgeOffsetTest extends AbstractHUTest
{
	private I_M_HU_PI huDefPalet;
	private I_M_HU_PI huDefIFCO;
	private I_M_HU_PI huDefBag;
	private AttributesTestHelper attributesTestHelper;

	@Override
	protected void initialize()
	{
		attributesTestHelper = new AttributesTestHelper();

		//
		// Handling Units Definition
		huDefBag = helper.createHUDefinition(HUTestHelper.NAME_Bag_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefBag);
			helper.assignProduct(itemMA, pTomatoId, BigDecimal.TEN, uomEach);

			helper.createHU_PI_Item_PackingMaterial(huDefBag, pmBag);

			// value will be inherited
			helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_Age)
												   .setM_HU_PI(huDefBag)
												   .setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
												   .setSplitterStrategyClass(CopyAttributeSplitterStrategy.class));
			// value will be pulled up to parents using *sum* aggregation strategy
			helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_AgeOffset)
												   .setM_HU_PI(huDefBag)
												   .setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp)
												   .setAggregationStrategyClass(SumAggregationStrategy.class));
		}

		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			//helper.createHU_PI_Item_IncludedHU(huDefIFCO, huDefIFCO, BigDecimal.ONE);
			final I_M_HU_PI_Item piTU_Item_IFCO = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(piTU_Item_IFCO, pTomatoId, BigDecimal.TEN, uomEach);

			// value will not be propagated
			helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_Age)
												   .setM_HU_PI(huDefIFCO)
												   .setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
			// value will be inherited
			helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_AgeOffset)
												   .setM_HU_PI(huDefIFCO)
												   .setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
												   .setSplitterStrategyClass(CopyAttributeSplitterStrategy.class));

		}

		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, new BigDecimal("2"));
			helper.createHU_PI_Item_PackingMaterial(huDefPalet, pmPallets);

			// value will not be propagated
			helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_Age)
												   .setM_HU_PI(huDefPalet)
												   .setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
			// value will be inherited
			helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_AgeOffset)
												   .setM_HU_PI(huDefPalet)
												   .setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
												   .setSplitterStrategyClass(CopyAttributeSplitterStrategy.class));
		}
	}

	private List<I_M_HU> createIncomingPalets()
	{
		// assume that incomingTrxDoc is a material receipt of 23 tomatoes
		final I_M_Transaction incomingTrxDoc = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts, pTomato, new BigDecimal(23));

		// create and destroy instances only with a I_M_Transaction
		final List<I_M_HU> huPalets = helper.createHUsFromSimplePI(incomingTrxDoc, huDefPalet);

		return huPalets;
	}

	@Test
	public void testAgeOffset()
	{
		final List<I_M_HU> huPalets = createIncomingPalets();

		final I_M_HU palet1 = huPalets.get(0);

		final IAttributeStorageFactory storageFactory = helper.getHUContext().getHUAttributeStorageFactory();
		final IAttributeStorage attributesStorage = storageFactory.getAttributeStorage(palet1);
		attributesStorage.setSaveOnChange(true);

		attributesStorage.setValue(attr_AgeOffset, "3");

		M_HU_UpdateHUAgeAttributeProcess.updateAgeAttribute(attributesStorage, Age.ofAgeInMonths(1));

		final IAttributeValue agetAttributeValue = attributesStorage.getAttributeValue(attr_Age);

		System.out.println("agetAttributeValue: " + agetAttributeValue);
		assertThat(agetAttributeValue.getValueAsString()).isEqualTo("4");
	}

}
