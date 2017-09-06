package de.metas.inoutcandidate.spi.impl;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.spi.impl.HUPackingMaterialsCollector;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class HUPackingMaterialsCollectorTest
{
	private LUTUProducerDestinationTestSupport data;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHandlingUnitsBL handlingUnitsBL;


	@Before
	public void Init()
	{
		data = new LUTUProducerDestinationTestSupport();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	}

	/**
	 * Task https://github.com/metasfresh/metasfresh/issues/1164
	 */
	@Test
	public void test_AggregatedHU()
	{
		//
		// Create an aggregated LU with more than one TU inside
		final I_M_HU luHU = createLU("1000");
		final int countTUs = extractAggregatedTUsCount(luHU);
		assertThat(countTUs, greaterThan(1));

		//
		// Create a packing materials collector and collect the LU we just create it
		final HUPackingMaterialsCollector collector = new HUPackingMaterialsCollector(data.helper.createMutableHUContext());
		final IHUPackingMaterialCollectorSource source = null; // N/A
		collector.addHURecursively(luHU, source);

		//
		// Assert collected TUs counter is OK.
		final int collectedCountTUs = collector.getAndResetCountTUs();
		assertThat(collectedCountTUs, is(countTUs));
	}
	
	private I_M_HU createLU(final String totalQtyCU)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.setMaxTUsPerLU(Integer.MAX_VALUE); // allow as many TUs on that one palette as we want
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal(totalQtyCU), data.helper.uomKg);
		final List<I_M_HU> createdLUs = lutuProducer.getCreatedHUs();

		assertThat(createdLUs.size(), is(1));
		return createdLUs.get(0);
	}
	
	private int extractAggregatedTUsCount(final I_M_HU luHU)
	{
		final List<I_M_HU> aggregatedHUs = handlingUnitsDAO.retrieveIncludedHUs(luHU);
		assertThat(aggregatedHUs.size(), is(1));

		final I_M_HU aggregatedHU = aggregatedHUs.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(aggregatedHU), is(true));
		assertThat(aggregatedHU.getM_HU_Item_Parent().getM_HU_PI_Item_ID(), is(data.piLU_Item_IFCO.getM_HU_PI_Item_ID()));
		
		return handlingUnitsDAO.retrieveParentItem(aggregatedHU).getQty().intValueExact();
	}

}
