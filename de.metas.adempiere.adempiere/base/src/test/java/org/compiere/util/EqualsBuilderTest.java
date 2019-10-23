package org.compiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.util.lang.EqualsBuilder;
import org.junit.Assert;
import org.junit.Test;

public class EqualsBuilderTest
{
	/**
	 * Makes sure {@link EqualsBuilder#append(BigDecimal, BigDecimal)} method is used
	 * and {@link BigDecimal}s are compared using {@link BigDecimal#compareTo(BigDecimal)} instead of {@link BigDecimal#equals(Object)}.
	 */
	@Test
	public void test_BigDecimals()
	{
		BigDecimal bd1 = new BigDecimal("10.0");
		BigDecimal bd2 = new BigDecimal("10.00000");
		Assert.assertNotEquals(bd1, bd2);
		Assert.assertTrue(new EqualsBuilder()
				.append(bd1, bd2)
				.isEqual());
	}
}
