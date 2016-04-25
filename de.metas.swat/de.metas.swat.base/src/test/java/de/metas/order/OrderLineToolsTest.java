package de.metas.order;

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


import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import de.metas.order.OrderLineTools;

public final class OrderLineToolsTest {

	@Test
	public void lineDiscount() {

		assertCorrectLineDiscount("5", "2.5", "0", "50.00");
		assertCorrectLineDiscount("5", "2.5", "50", "0.00");
		
		assertCorrectLineDiscount("0", "0", "0", "0.00");
		assertCorrectLineDiscount("5", "5", "0", "0.00");
		
		assertCorrectLineDiscount("10", "4.5", "10", "50.00");
	}

	private void assertCorrectLineDiscount(final String listPrice,
			final String actualPrice, final String orderDiscount,
			final String expectedOrderLineDiscount) {

		final BigDecimal orderLineDiscount = OrderLineTools
				.inactComputeOrderLineDiscount(new BigDecimal(listPrice),
						new BigDecimal(actualPrice), new BigDecimal(
								orderDiscount), 2);

		Assert.assertEquals(orderLineDiscount, new BigDecimal(
				expectedOrderLineDiscount));

	}
}
