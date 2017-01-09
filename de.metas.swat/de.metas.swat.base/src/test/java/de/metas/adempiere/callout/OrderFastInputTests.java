package de.metas.adempiere.callout;

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

import org.compiere.model.MProduct;

import de.metas.adempiere.form.IClientUI;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

public class OrderFastInputTests
{
//	@Mocked(methods = { "checkWeight" }, inverse = true)
//	OrderFastInput fastInput;
	
	@Mocked
	MProduct product;
	
	@Mocked
	IClientUI uiMock;
	
	
//	@Test
//	public void checkNegativeWeight()
//	{
//		Services.registerService(IClientUI.class, uiMock);
//		
//		hasWeight(product, BigDecimal.ONE.negate());
//		fastInput.checkWeight(product, 2);
//
//		AssertWarningCalled(2, OrderFastInput.ZERO_WEIGHT_PRODUCT_ADDED, 1);
//	}
//
//	@Test
//	public void checkZeroWeight()
//	{
//		Services.registerService(IClientUI.class, uiMock);
//		
//		hasWeight(product, BigDecimal.ZERO);
//		fastInput.checkWeight(product, 2);
//
//		AssertWarningCalled(2, OrderFastInput.ZERO_WEIGHT_PRODUCT_ADDED, 1);
//	}

//	@Test
//	public void checkPositiveWeight()
//	{
//		Services.registerService(IClientUI.class, uiMock);
//		
//		hasWeight(product, BigDecimal.ONE);
//		fastInput.checkWeight(product, 2);
//		
//		AssertWarningCalled(2, OrderFastInput.ZERO_WEIGHT_PRODUCT_ADDED, 0);
//	}
	
	
	public void AssertWarningCalled(final int windowNo, final String AD_Message, final int timez)
	{
		new Verifications()
		{{
			uiMock.warn(windowNo, AD_Message); times = timez;
		}};
	}
	
	public void hasWeight(final MProduct prod, final BigDecimal weight)
	{
		new Expectations()
		{{
			prod.getWeight();	result = weight;
		}};
	}
}
