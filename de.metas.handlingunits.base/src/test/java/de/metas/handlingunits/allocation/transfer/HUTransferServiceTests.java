package de.metas.handlingunits.allocation.transfer;

import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import org.w3c.dom.Node;

import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@RunWith(Theories.class)
public class HUTransferServiceTests
{
	/**
	 * This dataPoint shall enable us to test with both values of {@code isOwnPackingMaterials}.
	 */
	@DataPoints()
	public static boolean[] isOwnPackingMaterials = { true, false };

	@DataPoints()
	public static boolean[] isAggregateCU = { true, false };

	private LUTUProducerDestinationTestSupport data;

	private IHandlingUnitsDAO handlingUnitsDAO;

	@Before
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	}

	@Test
	public void testSplitCU_To_NewCU_1Tomato()
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final I_M_HU sourceTU;
		final I_M_HU cuToSplit;
		{
			final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

			lutuProducer.setNoLU();
			lutuProducer.setTUPI(data.piTU_IFCO);

			data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("2"), data.helper.uomKg);
			final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();

			assertThat(createdTUs.size(), is(1));
			sourceTU = createdTUs.get(0);

			final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTUs.get(0));
			assertThat(createdCUs.size(), is(1));

			cuToSplit = createdCUs.get(0);
		}

		// invoke the method under test
		final List<I_M_HU> newCUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewCU(cuToSplit, data.helper.pTomato, data.helper.uomKg, BigDecimal.ONE);

		assertThat(newCUs.size(), is(1));

		Services.get(ITrxManager.class).commit(data.helper.trxName); // we need to do this to "persist" the changes. otherwise the following asserts will fail

		final Node sourceTUXML = HUXmlConverter.toXml(sourceTU);
		assertThat(sourceTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='P'])", is("1")));
		assertThat(sourceTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='P'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newCUXML = HUXmlConverter.toXml(newCUs.get(0));

		assertThat(newCUXML, hasXPath("count(HU-VirtualPI[@HUStatus='P'])", is("1")));
		assertThat(newCUXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	/**
	 * Similar to {@link #testSplitCU_To_NewCU_1Tomato()}, but here we split off both tomatoes, i.e. the full quantity of the source CU.<br>
	 * After this bold move we expect the source CU to be destroyed.
	 */
	@Test
	public void testSplitCU_To_NewCU_2Tomatoes()
	{

		final I_M_HU sourceTU;
		final I_M_HU cuToSplit;
		{
			final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

			lutuProducer.setNoLU();
			lutuProducer.setTUPI(data.piTU_IFCO);

			data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("2"), data.helper.uomKg);
			final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();

			assertThat(createdTUs.size(), is(1));
			sourceTU = createdTUs.get(0);

			final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTUs.get(0));
			assertThat(createdCUs.size(), is(1));

			cuToSplit = createdCUs.get(0);
		}

		// invoke the method under test

		final List<I_M_HU> newCUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewCU(cuToSplit, data.helper.pTomato, data.helper.uomKg, new BigDecimal("2"));

		assertThat(newCUs.size(), is(1));

		Services.get(ITrxManager.class).commit(data.helper.trxName); // we need to do this to "persist" the changes. otherwise the following asserts will fail
		// data.helper.commitAndDumpHU(sourceTU);

		// TODO source CU needs to be destroyed

		final Node sourceTUXML = HUXmlConverter.toXml(sourceTU);
		// assertThat(sourceTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='D'])", is("1")));
		assertThat(sourceTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		// assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='D'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newCUXML = HUXmlConverter.toXml(newCUs.get(0));

		assertThat(newCUXML, hasXPath("count(HU-VirtualPI[@HUStatus='P'])", is("1")));
		assertThat(newCUXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='2.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	@Theory
	public void testSplitRealCU_To_NewTUs_1Tomato_TU_Capacity_2(
			@TestedOn(ints = { 0, 1 }) final int isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = mkRealCUToSplit(new BigDecimal("40"));
		
		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewTUs(cuToSplit, data.helper.pTomato, data.helper.uomKg, BigDecimal.ONE, data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials == 1);

		assertThat(newTUs.size(), is(1));

		// data.helper.commitAndDumpHU(cuToSplit);

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='P'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='39.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		assertThat(newTUXML, hasXPath("count(HU-TU_Bag[@HUStatus='P'])", is("1")));
		assertThat(newTUXML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials == 1))));
		assertThat(newTUXML, hasXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	@Theory
	public void testSplitAggregateCU_To_NewTUs_1Tomato_TU_Capacity_2(
			@TestedOn(ints = { 0, 1 }) final int isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = mkAggregateCUToSplit(new BigDecimal("80")); // match the IFCOs capacity to get an aggregate HU

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewTUs(cuToSplit, data.helper.pTomato, data.helper.uomKg, BigDecimal.ONE, data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials == 1);

		assertThat(newTUs.size(), is(1));

		//data.helper.commitAndDumpHU(cuToSplit);

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='P'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));

		final I_M_HU parentOfCUToSplit = cuToSplit.getM_HU_Item_Parent().getM_HU();
		data.helper.commitAndDumpHU(parentOfCUToSplit);
		final Node parentOfCUToSplitXML = HUXmlConverter.toXml(parentOfCUToSplit);
		assertThat(parentOfCUToSplitXML, hasXPath("count(HU-LU_Palet[@HUStatus='P'])", is("1")));
		assertThat(parentOfCUToSplitXML, hasXPath("count(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @Qty='79.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(parentOfCUToSplitXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='39.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(parentOfCUToSplitXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));
				
		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		assertThat(newTUXML, hasXPath("count(HU-TU_Bag[@HUStatus='P'])", is("1")));
		assertThat(newTUXML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials == 1))));
		assertThat(newTUXML, hasXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
	}
	
	@Theory
	public void testSplitCU_To_NewTUs_1Tomato_TU_Capacity_40(final boolean isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = mkRealCUToSplit(new BigDecimal("2"));

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewTUs(cuToSplit, data.helper.pTomato, data.helper.uomKg, new BigDecimal("2"), data.piTU_Item_Product_IFCO_40KgTomatoes, isOwnPackingMaterials);

		assertThat(newTUs.size(), is(1));

		Services.get(ITrxManager.class).commit(data.helper.trxName); // we need to do this to "persist" the changes. otherwise the following asserts will fail
		// data.helper.commitAndDumpHU(newTUs.get(0));

		// TODO source CU needs to be destroyed

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		// assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='D'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		assertThat(newTUXML, hasXPath("count(HU-TU_IFCO[@HUStatus='P'])", is("1")));
		assertThat(newTUXML, hasXPath("string(HU-TU_IFCO/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(newTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='2.000' and @C_UOM_Name='Kg'])", is("1")));
	}

	/**
	 * Run {@link HUTransferService#action_SplitCU_To_NewTUs(I_M_HU, org.compiere.model.I_M_Product, org.compiere.model.I_C_UOM, BigDecimal, I_M_HU_PI_Item_Product, boolean)}
	 * by splitting a CU-quantity of two onto new TUs with a CU-capacity of one.
	 * 
	 * @param isOwnPackingMaterials
	 */
	@Theory
	public void testSplitCU_To_NewTUs_40Tomatoes_TU_Capacity_8(final boolean isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = mkRealCUToSplit(new BigDecimal("40"));

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransferService.get(data.helper.getHUContext())
				.action_SplitCU_To_NewTUs(cuToSplit, data.helper.pTomato, data.helper.uomKg, new BigDecimal("40"), data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);

		assertThat(newTUs.size(), is(5));

		// Services.get(ITrxManager.class).commit(data.helper.trxName); // we need to do this to "persist" the changes. otherwise the following asserts will fail
		// data.helper.commitAndDumpHU(newTUs.get(0));

		// TODO source CU needs to be destroyed

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		// assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='D'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='0.000' and @C_UOM_Name='Kg'])", is("1")));

		for (final I_M_HU newTU : newTUs)
		{
			final Node newTUXML = HUXmlConverter.toXml(newTU);

			assertThat(newTUXML, hasXPath("count(HU-TU_Bag[@HUStatus='P'])", is("1")));
			assertThat(newTUXML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
			assertThat(newTUXML, hasXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg'])", is("1")));
		}
	}

	private I_M_HU mkRealCUToSplit(final BigDecimal cuQty)
	{

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setNoLU();
		lutuProducer.setTUPI(data.piTU_IFCO);

		data.helper.load(lutuProducer, data.helper.pTomato, cuQty, data.helper.uomKg);
		final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();

		assertThat(createdTUs.size(), is(1));

		final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTUs.get(0));
		assertThat(createdCUs.size(), is(1));

		final I_M_HU cuToSplit = createdCUs.get(0);

		return cuToSplit;
	}

	private I_M_HU mkAggregateCUToSplit(final BigDecimal cuQty)
	{

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setTUPI(data.piTU_IFCO);

		data.helper.load(lutuProducer, data.helper.pTomato, cuQty, data.helper.uomKg);
		final List<I_M_HU> createdLUs = lutuProducer.getCreatedHUs();

		assertThat(createdLUs.size(), is(1));
		// data.helper.commitAndDumpHU(createdLUs.get(0));

		final List<I_M_HU> createdAggregateHUs = handlingUnitsDAO.retrieveIncludedHUs(createdLUs.get(0));
		assertThat(createdAggregateHUs.size(), is(1));

		final I_M_HU cuToSplit = createdAggregateHUs.get(0);
		assertThat(Services.get(IHandlingUnitsBL.class).isAggregateHU(cuToSplit), is(true));

		return cuToSplit;
	}
}
