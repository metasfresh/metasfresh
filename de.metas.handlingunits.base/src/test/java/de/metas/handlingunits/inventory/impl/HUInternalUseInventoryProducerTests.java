package de.metas.handlingunits.inventory.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUTestHelper.TestHelperLoadRequest;
import de.metas.handlingunits.IHUPackingMaterialsCollector;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.validator.M_HU;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.inventory.impl.InventoryBL;
import lombok.NonNull;
import mockit.Mocked;

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
 * This test doesn'T really work as it tests nothing. See the {@link #test()} method.
 * It was added to identify bugs in a different person's issue, but the problems were solved by manual testing before I could get to finish this.
 * Feel free to fix and extend it.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUInternalUseInventoryProducerTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private LUTUProducerDestinationTestSupport data;

	@Mocked
	private IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> noopPackingMaterialsCollector;

	private IHandlingUnitsBL handlingUnitsBL;
	private IHandlingUnitsDAO handlingUnitsDAO;

	private I_M_Locator locator;

	@Before
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();

		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final I_C_DocType dt = newInstance(I_C_DocType.class);
		dt.setDocBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory);
		dt.setDocSubType(X_C_DocType.DOCSUBTYPE_InternalUseInventory);
		save(dt);

		final I_M_Warehouse wh = newInstance(I_M_Warehouse.class);
		save(wh);

		locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse(wh);
		save(locator);
		
		Services.get(ISysConfigBL.class).setValue(InventoryBL.SYSCONFIG_QuickInput_Charge_ID, 1234, 0);
	}

	
	@Test
	@Ignore // TODO: atm it fails because there is no receipt line found
	public void test()
	{
		final I_M_HU lu = mkAggregateCUs("50", 10);

		HUInternalUseInventoryProducer.newInstance()
				.addHUs(ImmutableList.of(lu))
				.createInventories();
	}

	/**
	 * Creates an LU with one aggregate HU. Both the LU's and aggregate HU's status is "active".
	 *
	 * @param totalQtyCUStr
	 * @param qtyCUsPerTU
	 * @return
	 */
	public I_M_HU mkAggregateCUs(
			@NonNull final String totalQtyCUStr,
			final int qtyCUsPerTU)
	{
		final I_M_Product cuProduct = data.helper.pTomato;
		final I_C_UOM cuUOM =data.helper.uomKg;
		final BigDecimal totalQtyCU = new BigDecimal(totalQtyCUStr);

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.setMaxTUsPerLU(Integer.MAX_VALUE); // allow as many TUs on that one pallet as we want

		// Custom TU capacity (if specified)
		if (qtyCUsPerTU > 0)
		{
			lutuProducer.addCUPerTU(cuProduct, BigDecimal.valueOf(qtyCUsPerTU), cuUOM);
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

		assertThat(createdLUs).hasSize(1);
		// data.helper.commitAndDumpHU(createdLUs.get(0));

		final I_M_HU createdLU = createdLUs.get(0);
		final IMutableHUContext huContext = data.helper.createMutableHUContextOutOfTransaction();
		handlingUnitsBL.setHUStatus(huContext, createdLU, X_M_HU.HUSTATUS_Active);
		assertThat(createdLU.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);
		createdLU.setM_Locator(locator);

		new M_HU().updateChildren(createdLU);
		save(createdLU);

		final List<I_M_HU> createdAggregateHUs = handlingUnitsDAO.retrieveIncludedHUs(createdLUs.get(0));
		assertThat(createdAggregateHUs).hasSize(1);

		final I_M_HU cuToSplit = createdAggregateHUs.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(cuToSplit)).isTrue();
		assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU_PI_Item_ID()).isEqualTo(data.piLU_Item_IFCO.getM_HU_PI_Item_ID());
		assertThat(cuToSplit.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);

		return createdLUs.get(0);
	}

	/**
	 * Makes a stand alone CU with the given quantity and status "active".
	 *
	 * @param strCuQty
	 * @return
	 */
	public I_M_HU mkRealStandAloneCUToSplit(final String strCuQty)
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

	public I_M_HU mkRealCUWithTUToSplit(final String strCuQty)
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
		createdTU.setM_Locator(locator);

		new M_HU().updateChildren(createdTU);
		save(createdTU);

		final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTU);
		assertThat(createdCUs.size(), is(1));

		final I_M_HU cuToSplit = createdCUs.get(0);

		return cuToSplit;
	}
}
