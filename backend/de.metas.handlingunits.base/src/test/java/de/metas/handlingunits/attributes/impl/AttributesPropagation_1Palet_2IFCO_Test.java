package de.metas.handlingunits.attributes.impl;



/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.impl.CopyAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullAggregationStrategy;
import de.metas.handlingunits.impl.HUTrxBLTests;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AttributesPropagation_1Palet_2IFCO_Test extends AbstractHUTest
{
	private static final BigDecimal COUNT_IFCOS_PER_PALET = BigDecimal.valueOf(2);
	private static final BigDecimal COUNT_TOMATOS_PER_IFCO = BigDecimal.valueOf(10);

	private I_M_HU_PI huDefPalet;
	private I_M_HU_PI huDefIFCO;

	//
	// Test work data
	private IAttributeStorageFactory attributeStorageFactory;
	private IAttributeStorage huPalet_Attrs;
	private IAttributeStorage aggregateHU_Attrs;

	@Override
	protected void initialize()
	{
		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(itemMA, pTomatoId, AttributesPropagation_1Palet_2IFCO_Test.COUNT_TOMATOS_PER_IFCO, uomEach);
			helper.createHU_PI_Item_PackingMaterial(huDefIFCO, pmIFCO);
		}

		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, AttributesPropagation_1Palet_2IFCO_Test.COUNT_IFCOS_PER_PALET);
			helper.createHU_PI_Item_PackingMaterial(huDefPalet, pmPallets);
		}
	}

	private void setupHU_PI_Attribute(final I_M_Attribute attribute,
			final String propagationType,
			final Class<? extends IAttributeSplitterStrategy> splitStrategyClass,
			final Class<? extends IAttributeAggregationStrategy> aggregationStrategyClass)
	{
		helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attribute)
				.setM_HU_PI(huDefIFCO)
				.setPropagationType(propagationType)
				.setSplitterStrategyClass(splitStrategyClass)
				.setAggregationStrategyClass(aggregationStrategyClass));

		helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attribute)
				.setM_HU_PI(huDefPalet)
				.setPropagationType(propagationType)
				.setSplitterStrategyClass(splitStrategyClass)
				.setAggregationStrategyClass(aggregationStrategyClass));
	}

	/**
	 * Create initial HUs and attributes. In case this fails, I recommend to take a look at {@link HUTrxBLTests}.
	 */
	private void setupData()
	{
		attributeStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();

		//
		// Create inital Palets
		final I_M_Transaction incomingTrxDoc = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts,
				pTomato, // product
				COUNT_IFCOS_PER_PALET.multiply(COUNT_TOMATOS_PER_IFCO).multiply(BigDecimal.valueOf(2)) // qty
				);
		final List<I_M_HU> huPalets = helper.createHUsFromSimplePI(incomingTrxDoc, huDefPalet);
		assertThat(huPalets).hasSize(2);
		System.out.println(HUXmlConverter.toString(HUXmlConverter.toXml("huPalets",huPalets)));

		//
		// Bind data to be able to access them in our tests
		final I_M_HU huPalet = huPalets.getFirst();
		huPalet_Attrs = attributeStorageFactory.getAttributeStorage(huPalet);

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final List<I_M_HU> huIncluded = handlingUnitsDAO.retrieveIncludedHUs(huPalet);

		assertThat(huIncluded).hasSize(1);

		final I_M_HU huAggregate = huIncluded.getFirst();
		assertThat(Services.get(IHandlingUnitsBL.class).isAggregateHU(huAggregate));
		aggregateHU_Attrs = attributeStorageFactory.getAttributeStorage(huAggregate);
	}

	/**
	 * Test {@link X_M_HU_PI_Attribute#PROPAGATIONTYPE_TopDown} with {@link CopyAttributeSplitterStrategy}
	 */
	@Test
	public void testPropagateTopBottom_Copy()
	{
		setupHU_PI_Attribute(attr_CountryMadeIn,
				X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown,
				CopyAttributeSplitterStrategy.class,
				NullAggregationStrategy.class);

		setupData();

		testPropagateTopDown(attr_CountryMadeIn, null, null);
		testPropagateTopDown(attr_CountryMadeIn, "DE", "DE");
		testPropagateTopDown(attr_CountryMadeIn, "RO", "RO");
	}

	/**
	 * Sets the values on IFCOs and assumes we get <code>qtyPaletStrExpected</code> on pallet level
	 *
	 * @param attribute
	 * @param qtyAggreagateHUStr
	 * @param qtyPaletStrExpected
	 */
	private void testPropagateBottomUp(final I_M_Attribute attribute,
			final String qtyAggreagateHUStr,
			final String qtyPaletStrExpected)
	{
		final BigDecimal qtyPaletExpected = new BigDecimal(qtyPaletStrExpected);

		final Object qtyAggregateHU_old = aggregateHU_Attrs.getValue(attribute);
		final Object qtyPalet_old = huPalet_Attrs.getValue(attribute);

		if (qtyAggreagateHUStr != null)
		{
			final BigDecimal qtyAggreagateHU = new BigDecimal(qtyAggreagateHUStr);
			aggregateHU_Attrs.setValue(attribute, qtyAggreagateHU);
		}

		//
		// Build up detailed info
		final StringBuilder info = new StringBuilder();
		info.append("AggregateHU=" + qtyAggregateHU_old + "->" + qtyAggreagateHUStr + "; ");
		info.append("Palet=" + qtyPalet_old + "->" + qtyPaletExpected + "; ");

		final IAttributeValue paletAttributeValue = huPalet_Attrs.getAttributeValue(attribute);
		final BigDecimal qtyPaletActual = paletAttributeValue.getValueAsBigDecimal();
		assertThat(qtyPaletActual).as("Invalid initial " + attribute.getName() + " (" + info + ")").isEqualByComparingTo(qtyPaletExpected);
	}

	private void testPropagateTopDown(final I_M_Attribute attribute,
			final Object valuePalet,
			final Object valueAggregateHUExpected)
	{
		final Object valueAggregateHU_old = aggregateHU_Attrs.getValue(attribute);
		final Object valuePalet_old = huPalet_Attrs.getValue(attribute);

		if (valuePalet != null)
		{
			huPalet_Attrs.setValue(attribute, valuePalet);
		}

		//
		// Build up detailed info
		final String info = "aggregate HU=" + valueAggregateHU_old + "->" + valueAggregateHUExpected + "; "
				+ "Palet=" + valuePalet_old + "->" + valuePalet + "; ";

		//
		// Check aggregated HU's value
		final Object valueIFCO1 = aggregateHU_Attrs.getValue(attribute);
		assertThat(valueIFCO1)
				.as("Invalid propagated " + attribute.getName() + " for aggregate HU (" + info + ")")
				.isEqualTo(valueAggregateHUExpected);
	}
}
