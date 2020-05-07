package de.metas.material.planning.ddorder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.junit.Before;
import org.junit.Test;

/*
 * #%L
 * metasfresh-material-planning
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

public class DDOrderUtilTests
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/1635
	 */
	@Test
	public void testCalculateDurationDays()
	{
		assertThat(DDOrderUtil.calculateDurationDays(null, null), is(0));
		
		final I_PP_Product_Planning productPlanning = newInstance(I_PP_Product_Planning.class);
		assertThat(DDOrderUtil.calculateDurationDays(productPlanning, null), is(0));
		
		productPlanning.setTransfertTime(new BigDecimal("3"));
		assertThat(DDOrderUtil.calculateDurationDays(productPlanning, null), is(3));
		
		final I_DD_NetworkDistributionLine networkDistributionLine = newInstance(I_DD_NetworkDistributionLine.class);
		assertThat(DDOrderUtil.calculateDurationDays(productPlanning, networkDistributionLine), is(3));
		assertThat(DDOrderUtil.calculateDurationDays(null, networkDistributionLine), is(0));
		
		networkDistributionLine.setTransfertTime(new BigDecimal("4"));
		assertThat(DDOrderUtil.calculateDurationDays(null, networkDistributionLine), is(4));
		
		// it the network distribution line has a transfer time, then the product planning's value shall be ignored
		networkDistributionLine.setTransfertTime(new BigDecimal("4"));
		assertThat(DDOrderUtil.calculateDurationDays(productPlanning, networkDistributionLine), is(4));
		
		// the product planning's delivery time and the network distribution line's transfer time shall be added up
		productPlanning.setDeliveryTime_Promised(new BigDecimal("2"));
		assertThat(DDOrderUtil.calculateDurationDays(productPlanning, networkDistributionLine), is(6));
		
		// if the network distribution line is missing, then the product planning's transfer and promised time shall be added up
		assertThat(DDOrderUtil.calculateDurationDays(productPlanning, null), is(5));
	}
}
