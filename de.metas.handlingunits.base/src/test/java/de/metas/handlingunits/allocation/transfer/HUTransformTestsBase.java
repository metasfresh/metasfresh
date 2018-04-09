package de.metas.handlingunits.allocation.transfer;


import static de.metas.business.BusinessTestHelper.createBPartner;
import static de.metas.business.BusinessTestHelper.createBPartnerLocation;
import static de.metas.business.BusinessTestHelper.createLocator;
import static de.metas.business.BusinessTestHelper.createWarehouse;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.w3c.dom.Node;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUTestHelper.TestHelperLoadRequest;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHUPackingMaterialsCollector;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.HUTransformTestsBase.TestHUs.TestHUsBuilder;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.validator.M_HU;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.trace.HUTransformTracingTests;



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

/**
 * This class contains common setup code and tests for {@link HUTransformServiceTests} and {@link HUTransformTracingTests}.<br>
 * Please move additional common code from {@link HUTransformServiceTests} to this class when it is needed by {@link HUTransformTracingTests}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUTransformTestsBase
{

	private LUTUProducerDestinationTestSupport data;

	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHandlingUnitsBL handlingUnitsBL;

	private final IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> noopPackingMaterialsCollector;

	public HUTransformTestsBase(IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> noopPackingMaterialsCollector)
	{
		this.noopPackingMaterialsCollector = noopPackingMaterialsCollector;

		data = new LUTUProducerDestinationTestSupport();
		data.helper.setContextPackingMaterialsCollector(noopPackingMaterialsCollector);

		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	}

	public final TestHUs testCU_To_NewCU_1Tomato_DoIt()
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final I_C_BPartner bpartner =createBPartner("testVendor");
		final I_C_BPartner_Location bPartnerLocation = createBPartnerLocation(bpartner);

		final I_M_Warehouse warehouse = createWarehouse("testWarehouse");
		final I_M_Locator locator = createLocator("testLocator", warehouse);

		final TestHUsBuilder testHUsBuilder = TestHUs.builder();

		final I_M_HU sourceTU;
		final I_M_HU cuToSplit;
		{
			final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

			lutuProducer.setNoLU();
			lutuProducer.setTUPI(data.piTU_IFCO);
			lutuProducer.setC_BPartner(bpartner);
			lutuProducer.setM_Locator(locator);
			lutuProducer.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());

			data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("2"), data.helper.uomKg);
			final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();

			assertThat(createdTUs.size(), is(1));
			sourceTU = createdTUs.get(0);

			handlingUnitsBL.setHUStatus(data.helper.getHUContext(), sourceTU, X_M_HU.HUSTATUS_Active);
			new M_HU().updateChildren(sourceTU);
			save(sourceTU);

			testHUsBuilder.inititalParent(sourceTU);

			// guard: verify that we have on IFCO with a two-tomato-CU in it
			final Node sourceTUBeforeSplitXML = HUXmlConverter.toXml(sourceTU);
			assertThat(sourceTUBeforeSplitXML, hasXPath("HU-TU_IFCO/@HUStatus", is("A")));
			assertThat(sourceTUBeforeSplitXML, hasXPath("string(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("2.000")));
			assertThat(sourceTUBeforeSplitXML, hasXPath("string(HU-TU_IFCO/Item[@ItemType='MI']/HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty)", is("2.000")));

			final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTUs.get(0));
			assertThat(createdCUs.size(), is(1));

			cuToSplit = createdCUs.get(0);
		}

		// invoke the method under test
		final List<I_M_HU> newCUs = HUTransformService
				.newInstance(data.helper.getHUContext())
				.cuToNewCU(cuToSplit, BigDecimal.ONE);

		assertThat(newCUs.size(), is(1));

		final Node sourceTUXML = HUXmlConverter.toXml(sourceTU);
		assertThat(sourceTUXML, hasXPath("HU-TU_IFCO/@HUStatus", is("A")));
		assertThat(sourceTUXML, hasXPath("count(HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI[@HUStatus='A'])", is("1")));
		assertThat(cuToSplitXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newCUXML = HUXmlConverter.toXml(newCUs.get(0));
		assertThat(newCUXML, not(hasXPath("HU-VirtualPI/M_HU_Item_Parent_ID"))); // verify that there is no parent HU
		assertThat(newCUXML, hasXPath("count(HU-VirtualPI[@HUStatus='A'])", is("1")));
		assertThat(newCUXML, hasXPath("count(HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(newCUXML, hasXPath("string(HU-VirtualPI/@C_BPartner_ID)", is(Integer.toString(bpartner.getC_BPartner_ID())))); // verify that the bpartner is propagated
		assertThat(newCUXML, hasXPath("string(HU-VirtualPI/@C_BPartner_Location_ID)", is(Integer.toString(bPartnerLocation.getC_BPartner_Location_ID())))); // verify that the bpartner location is propagated
		assertThat(newCUXML, hasXPath("string(HU-VirtualPI/@M_Locator_ID)", is(Integer.toString(locator.getM_Locator_ID())))); // verify that the locator is propagated

		return testHUsBuilder.input(cuToSplit).output(newCUs).build();
	}

	public final TestHUs testCU_To_NewCU_MaxValueNoParent_DoIt()
	{
		final I_M_HU cuToSplit = mkRealStandAloneCuWithCuQty("3");
		assertThat(cuToSplit.getM_HU_Item_Parent(), nullValue()); // this test makes no sense if the given CU has a parent

		// invoke the method under test
		final List<I_M_HU> newCUs = HUTransformService.newInstance(data.helper.getHUContext())
				.cuToNewCU(cuToSplit, new BigDecimal("3"));
		assertThat(newCUs.size(), is(0));

		return TestHUs.builder().input(cuToSplit).output(newCUs).build();
	}

	public final TestHUs testCU_To_NewCU_MaxValueParent_DoIt()
	{
		final I_M_HU cuToSplit = mkRealCUWithTUandQtyCU("3");
		final I_M_HU parentTU = cuToSplit.getM_HU_Item_Parent().getM_HU();

		// invoke the method under test
		final List<I_M_HU> newCUs = HUTransformService.newInstance(data.helper.getHUContext())
				.cuToNewCU(cuToSplit, new BigDecimal("3"));

		assertThat(newCUs.size(), is(1));
		assertThat(newCUs.get(0).getM_HU_ID(), is(cuToSplit.getM_HU_ID()));
		assertThat(handlingUnitsDAO.retrieveIncludedHUs(parentTU).isEmpty(), is(true));
		assertThat(cuToSplit.getM_HU_Item_Parent(), nullValue());

		return TestHUs.builder().inititalParent(parentTU).input(cuToSplit).output(newCUs).build();
	}

	public final TestHUs testAggregateCU_To_NewTUs_1Tomato_DoIt(final boolean isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = mkAggregateHUWithTotalQtyCU("80");
		assertThat(cuToSplit.getHUStatus(), is(X_M_HU.HUSTATUS_Active));
		assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU().getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.newInstance(data.helper.getHUContext())
				.cuToNewTUs(cuToSplit, BigDecimal.ONE, data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);

		assertThat(newTUs.size(), is(1));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("HU-VirtualPI/@HUStatus", is("A")));
		assertThat(cuToSplitXML, hasXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("40.000")));

		final I_M_HU parentOfCUToSplit = cuToSplit.getM_HU_Item_Parent().getM_HU();

		// the source TU now needs to contain one aggregate HU that represent the remaining "untouched" IFCO with a quantity of 40 and a new "real" IFCO with a quantity of 39.
		final Node parentOfCUToSplitXML = HUXmlConverter.toXml(parentOfCUToSplit);
		assertThat(parentOfCUToSplitXML, hasXPath("HU-LU_Palet/@HUStatus", is("A"))); // gh #1975: the newly created parent needs to have the child's HU status
		assertThat(parentOfCUToSplitXML, hasXPath("count(HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @Qty='79.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(parentOfCUToSplitXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @Qty='39.000' and @C_UOM_Name='Kg'])", is("1")));
		assertThat(parentOfCUToSplitXML, hasXPath("count(HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @Qty='40.000' and @C_UOM_Name='Kg'])", is("1")));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));

		assertThat(newTUXML, hasXPath("HU-TU_Bag/@HUStatus", is("A")));
		assertThat(newTUXML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(newTUXML, hasXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));

		return TestHUs.builder().inititalParent(parentOfCUToSplit).input(cuToSplit).output(newTUs).build();
	}

	/**
	 * Makes a stand alone CU with the given quantity and status "active".
	 *
	 * @param strCuQty
	 * @return
	 */
	public I_M_HU mkRealStandAloneCuWithCuQty(final String strCuQty)
	{
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();

		final TestHelperLoadRequest loadRequest = HUTestHelper.TestHelperLoadRequest.builder()
				.producer(producer)
				.cuProduct(data.helper.pTomato)
				.loadCuQty(new BigDecimal(strCuQty))
				.loadCuUOM(data.helper.uomKg)
				.huPackingMaterialsCollector(noopPackingMaterialsCollector)
				.build();

		data.helper.load(loadRequest);

		final List<I_M_HU> createdCUs = producer.getCreatedHUs();
		assertThat(createdCUs.size(), is(1));

		final I_M_HU cuToSplit = createdCUs.get(0);
		handlingUnitsBL.setHUStatus(data.helper.getHUContext(), cuToSplit, X_M_HU.HUSTATUS_Active);
		save(cuToSplit);

		return cuToSplit;
	}

	public I_M_HU mkRealCUWithTUandQtyCU(final String strCuQty)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setNoLU();
		lutuProducer.setTUPI(data.piTU_IFCO);

		final BigDecimal cuQty = new BigDecimal(strCuQty);
		data.helper.load(lutuProducer, data.helper.pTomato, cuQty, data.helper.uomKg);
		final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();
		assertThat(createdTUs.size(), is(1));

		final I_M_HU createdTU = createdTUs.get(0);
		handlingUnitsBL.setHUStatus(data.helper.getHUContext(), createdTU, X_M_HU.HUSTATUS_Active);
		new M_HU().updateChildren(createdTU);
		save(createdTU);

		final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTU);
		assertThat(createdCUs.size(), is(1));

		final I_M_HU cu = createdCUs.get(0);
		return cu;
	}

	/**
	 * Creates an LU with PI {@link LUTUProducerDestinationTestSupport#piLU} and an aggregate TU with PI {@link LUTUProducerDestinationTestSupport#piTU_IFCO}.
	 * 
	 * @param totalQtyCUStr
	 * @return
	 */
	public I_M_HU mkAggregateHUWithTotalQtyCU(final String totalQtyCUStr)
	{
		final int qtyCUsPerTU = -1; // N/A
		return mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU(totalQtyCUStr, qtyCUsPerTU);
	}

	/**
	 * Creates an LU with one aggregate HU. Both the LU's and aggregate HU's status is "active".
	 *
	 * @param totalQtyCUStr
	 * @param customQtyCUsPerTU
	 * @return
	 */
	public I_M_HU mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU(final String totalQtyCUStr, final int customQtyCUsPerTU)
	{
		final I_M_Product cuProduct = data.helper.pTomato;
		final I_C_UOM cuUOM = data.helper.uomKg;
		final BigDecimal totalQtyCU = new BigDecimal(totalQtyCUStr);

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.setMaxTUsPerLU(Integer.MAX_VALUE); // allow as many TUs on that one pallet as we want

		// Custom TU capacity (if specified)
		if (customQtyCUsPerTU > 0)
		{
			lutuProducer.addCUPerTU(cuProduct, BigDecimal.valueOf(customQtyCUsPerTU), cuUOM);
		}

		final TestHelperLoadRequest loadRequest = HUTestHelper.TestHelperLoadRequest.builder()
				.producer(lutuProducer)
				.cuProduct(cuProduct)
				.loadCuQty(totalQtyCU)
				.loadCuUOM(cuUOM)
				.huPackingMaterialsCollector(noopPackingMaterialsCollector)
				.build();

		data.helper.load(loadRequest);
		final List<I_M_HU> createdLUs = lutuProducer.getCreatedHUs();

		assertThat(createdLUs.size(), is(1));
		// data.helper.commitAndDumpHU(createdLUs.get(0));

		final I_M_HU createdLU = createdLUs.get(0);
		final IMutableHUContext huContext = data.helper.createMutableHUContextOutOfTransaction();
		handlingUnitsBL.setHUStatus(huContext, createdLU, X_M_HU.HUSTATUS_Active);
		assertThat(createdLU.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		new M_HU().updateChildren(createdLU);
		save(createdLU);

		final List<I_M_HU> createdAggregateHUs = handlingUnitsDAO.retrieveIncludedHUs(createdLUs.get(0));
		assertThat(createdAggregateHUs.size(), is(1));

		final I_M_HU cuToSplit = createdAggregateHUs.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(cuToSplit), is(true));
		assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU_PI_Item_ID(), is(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()));
		assertThat(cuToSplit.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		return cuToSplit;
	}

	public LUTUProducerDestinationTestSupport getData()
	{
		return data;
	}

	/**
	 * Simple POJO that contains instances the test code worked with. Needed for assertions.
	 * 
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	// need to fully qualify the lombok classes, not sure why; if I don't then it works with eclipse, but not with javac (lombok 1.16.16)
	@lombok.Data
	@lombok.Builder
	public static final class TestHUs
	{
		private final I_M_HU input;
		private final I_M_HU inititalParent;
		private final List<I_M_HU> output;
	}

	/**
	 * Delegates to {@link IHandlingUnitsDAO#retrieveParent(I_M_HU)} of the local {@code handlingUnitsDAO} member.<br>
	 * Our {@link IHandlingUnitsDAO} has some stuff with caching etc going on, so when working with HUs created by this class,<br>
	 * I recommend to use this delegating methods rather than obtaining your own instance of {@link IHandlingUnitsDAO}.
	 * 
	 * @param hu
	 * @return
	 */
	public I_M_HU retrieveParent(@lombok.NonNull final I_M_HU hu)
	{
		return handlingUnitsDAO.retrieveParent(hu);
	}

	/**
	 * Delegates to {@link IHandlingUnitsDAO#retrieveIncludedHUs(I_M_HU)} of the local {@code handlingUnitsDAO} member.<br>
	 * Our {@link IHandlingUnitsDAO} has some stuff with caching etc going on, so when working with HUs created by this class,<br>
	 * I recommend to use this delegating methods rather than obtaining your own instance of {@link IHandlingUnitsDAO}.
	 * 
	 * @param parentHU
	 * @return
	 */
	public List<I_M_HU> retrieveIncludedHUs(@lombok.NonNull final I_M_HU parentHU)
	{
		return handlingUnitsDAO.retrieveIncludedHUs(parentHU);
	}

	/**
	 * Delegates to {@link IHandlingUnitsDAO#retrieveParentItem(I_M_HU)} of the local {@code handlingUnitsDAO} member.<br>
	 * Our {@link IHandlingUnitsDAO} has some stuff with caching etc going on, so when working with HUs created by this class,<br>
	 * I recommend to use this delegating methods rather than obtaining your own instance of {@link IHandlingUnitsDAO}.
	 */
	public I_M_HU_Item retrieveParentItem(@lombok.NonNull final I_M_HU hu)
	{
		return handlingUnitsDAO.retrieveParentItem(hu);
	}
}
