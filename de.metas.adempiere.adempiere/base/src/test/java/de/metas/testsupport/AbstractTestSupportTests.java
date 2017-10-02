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


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.document.engine.IDocument;

public class AbstractTestSupportTests
{
	/**
	 * ts-2014-01-12: Currently doesn't work. I pinged teo
	 */
	@Test
	@Ignore 
	public void testOrder()
	{
		AdempiereTestHelper.get().staticInit();
		AdempiereTestHelper.get().init();
		
		final AbstractTestSupport testee = new AbstractTestSupport();
		final I_C_Order order1 = testee.order("1");
		
		// call the same method again, should be the same
		assertThat(testee.order("1"), is(order1)); 
		assertThat(testee.order("1"), sameInstance(order1)); // fails as of now
		
		// this is to clarify the practical problem we have with the order being not the same
		testee.order("1").setDocStatus(IDocument.STATUS_InProgress);
		InterfaceWrapperHelper.save(testee.order("1"));
		assertThat(testee.order("1").getDocStatus(), equalTo(IDocument.STATUS_InProgress));
	}
}
