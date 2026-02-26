package de.metas.testsupport;

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

import de.metas.document.engine.DocStatus;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class AbstractTestSupportTests
{
	/**
	 * ts-2014-01-12: Currently doesn't work. I pinged teo
	 */
	@Test
	@Disabled 
	public void testOrder()
	{
		AdempiereTestHelper.get().staticInit();
		AdempiereTestHelper.get().init();
		
		final AbstractTestSupport testee = new AbstractTestSupport();
		final I_C_Order order1 = testee.order("1");
		
		// call the same method again, should be the same
		Assertions.assertEquals(order1, testee.order("1"));
		Assertions.assertSame(order1, testee.order("1")); // fails as of now
		
		// this is to clarify the practical problem we have with the order being not the same
		testee.order("1").setDocStatus(DocStatus.InProgress.getCode());
		InterfaceWrapperHelper.save(testee.order("1"));
		Assertions.assertEquals(DocStatus.InProgress.getCode(), testee.order("1").getDocStatus());
	}
}
