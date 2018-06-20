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
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.w3c.dom.Node;

import de.metas.bpartner.model.I_C_BPartner_Location;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformTestsBase.TestHUs.TestHUsBuilder;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.validator.M_HU;
import de.metas.handlingunits.trace.HUTransformTracingTests;
import de.metas.quantity.Quantity;

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

	public HUTransformTestsBase()
	{
		data = new LUTUProducerDestinationTestSupport();

		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	}

	public final TestHUs testCU_To_NewCU_1Tomato_DoIt()
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final I_C_BPartner bpartner = createBPartner("testVendor");
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
				.cuToNewCU(cuToSplit, Quantity.of(BigDecimal.ONE, data.helper.uomKg));

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
		final I_M_HU cuToSplit = data.mkRealStandAloneCuWithCuQty("3");
		assertThat(cuToSplit.getM_HU_Item_Parent(), nullValue()); // this test makes no sense if the given CU has a parent

		// invoke the method under test
		final List<I_M_HU> newCUs = HUTransformService.newInstance(data.helper.getHUContext())
				.cuToNewCU(cuToSplit, Quantity.of(new BigDecimal("3"), data.helper.uomKg));
		assertThat(newCUs.size(), is(0));

		return TestHUs.builder().input(cuToSplit).output(newCUs).build();
	}

	public final TestHUs testCU_To_NewCU_MaxValueParent_DoIt()
	{
		final I_M_HU cuToSplit = data.mkRealCUWithTUandQtyCU("3");
		final I_M_HU parentTU = cuToSplit.getM_HU_Item_Parent().getM_HU();

		// when cuToSplit is added, then parentTU is destroyed, but that's not part of this test's scope
		data.helper.getHUContext()
				.getHUPackingMaterialsCollector()
				.disable();

		// invoke the method under test
		final List<I_M_HU> newCUs = HUTransformService
				.newInstance(data.helper.getHUContext())
				.cuToNewCU(
						cuToSplit,
						Quantity.of(new BigDecimal("3"),
								data.helper.uomKg));

		assertThat(newCUs.size(), is(1));
		assertThat(newCUs.get(0).getM_HU_ID(), is(cuToSplit.getM_HU_ID()));
		assertThat(handlingUnitsDAO.retrieveIncludedHUs(parentTU).isEmpty(), is(true));
		assertThat(cuToSplit.getM_HU_Item_Parent(), nullValue());

		return TestHUs.builder().inititalParent(parentTU).input(cuToSplit).output(newCUs).build();
	}

	public final TestHUs testAggregateCU_To_NewTUs_1Tomato_DoIt(final boolean isOwnPackingMaterials)
	{
		final I_M_HU cuToSplit = data.mkAggregateHUWithTotalQtyCU("80");
		assertThat(cuToSplit.getHUStatus(), is(X_M_HU.HUSTATUS_Active));
		assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU().getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		// invoke the method under test
		final List<I_M_HU> newTUs = HUTransformService.newInstance(data.helper.getHUContext())
				.cuToNewTUs(cuToSplit, Quantity.of(BigDecimal.ONE, data.helper.uomKg), data.piTU_Item_Product_Bag_8KgTomatoes, isOwnPackingMaterials);

		assertThat(newTUs.size(), is(1));

		final Node cuToSplitXML = HUXmlConverter.toXml(cuToSplit);
		assertThat(cuToSplitXML, hasXPath("HU-VirtualPI/@HUStatus", is("A")));
		assertThat(cuToSplitXML, hasXPath("HU-VirtualPI/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("40.000")));

		final I_M_HU parentOfCUToSplit = cuToSplit.getM_HU_Item_Parent().getM_HU();

		// the source TU now needs to contain one aggregate HU that represents the remaining "untouched" IFCO with a quantity of 40 and a new "real" IFCO with a quantity of 39.
		final Node parentOfCUToSplitXML = HUXmlConverter.toXml(parentOfCUToSplit);
		assertThat(parentOfCUToSplitXML, hasXPath("HU-LU_Palet/@HUStatus", is("A"))); // gh #1975: the newly created parent needs to have the child's HU status
		assertThat(parentOfCUToSplitXML, hasXPath("HU-LU_Palet/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("79.000")));
		assertThat(parentOfCUToSplitXML, hasXPath("HU-LU_Palet/Item[@ItemType='HU']/HU-TU_IFCO/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("39.000")));
		assertThat(parentOfCUToSplitXML, hasXPath("HU-LU_Palet/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @C_UOM_Name='Kg']/@Qty", is("40.000")));

		final Node newTUXML = HUXmlConverter.toXml(newTUs.get(0));
		assertThat(newTUXML, hasXPath("HU-TU_Bag/@HUStatus", is("A")));
		assertThat(newTUXML, hasXPath("string(HU-TU_Bag/@HUPlanningReceiptOwnerPM)", is(Boolean.toString(isOwnPackingMaterials))));
		assertThat(newTUXML, hasXPath("count(HU-TU_Bag/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg'])", is("1")));

		return TestHUs.builder().inititalParent(parentOfCUToSplit).input(cuToSplit).output(newTUs).build();
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
