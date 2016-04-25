package org.compiere.model;

/*
 * #%L
 * de.metas.swat.base
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


import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore // MProductPricing (i.e. PricingBL) is not longer database decoupled
public class MProductPricingTests
{

	/**
	 * Makes sure that the the construction checks if there are vendor break records.
	 */
	@Test
	public void constructor()
	{

		final int productId = 23;
		final int bPArtnerId = 24;
		final BigDecimal qty = new BigDecimal("25");

		final Map<String, Object> mocks = new HashMap<String, Object>();

		setupForConstructor(productId, bPArtnerId, mocks);

		replay(mocks.values().toArray());
		final MProductPricing productPricing = new MProductPricing(productId, bPArtnerId, qty, true);
		verify(mocks.values().toArray());

		Assert.assertEquals(productPricing.getM_Product_ID(), productId);
	}

	private void setupForConstructor(final int productId, final int bPArtnerId,
			final Map<String, Object> mocks)
	{
//		final IDBService db = serviceMock(IDBService.class, mocks);
//		expect(db.getSQLValueEx(null, MProductPricing.SQL_VENDOR_BREAKS, productId, bPArtnerId)).andReturn(0);
	}
}
