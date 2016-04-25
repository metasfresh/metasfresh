package de.metas.fresh.mrp_productinfo.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_AttributeSetInstance;
import org.junit.Before;
import org.junit.Test;

import de.metas.fresh.mrp_productinfo.IMRPProductInfoSelector;
import de.metas.fresh.mrp_productinfo.IMRPProductInfoSelectorFactory;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class MRPProductInfoSelectorFactoryTests
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Tests if {@link MRPProductInfoSelectorFactory} correctly creates an {@link IMRPProductInfoSelector} from a {@link I_PMM_PurchaseCandidate}.
	 *
	 * @task https://metasfresh.atlassian.net/browse/FRESH-86
	 */
	@Test
	public void testPurchaseCandidate()
	{
		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
		InterfaceWrapperHelper.save(asi);

		final I_PMM_PurchaseCandidate pc = InterfaceWrapperHelper.newInstance(I_PMM_PurchaseCandidate.class);
		pc.setDatePromised(SystemTime.asDayTimestamp());
		pc.setM_Product_ID(23);
		pc.setM_AttributeSetInstance(asi);
		InterfaceWrapperHelper.save(pc);

		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = new MRPProductInfoSelectorFactory();
		final IMRPProductInfoSelector selector = mrpProductInfoSelectorFactory.createOrNull(pc);

		assertNotNull(selector);
		assertThat(selector.getDate(), is(pc.getDatePromised()));
		assertThat(selector.getM_Product_ID(), is(pc.getM_Product_ID()));
		assertThat(selector.getM_AttributeSetInstance_ID(), is(pc.getM_AttributeSetInstance_ID()));
	}
}
