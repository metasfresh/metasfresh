package org.adempiere.ad.callout.api.impl;

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



import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.exceptions.CalloutException;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalloutExecutorTest
{
	private MockedCalloutFactory calloutFactory;
	private CalloutExecutor calloutExecutor;
	private PlainCalloutField field;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		calloutFactory = new MockedCalloutFactory();

		calloutExecutor = new CalloutExecutor(Env.getCtx(), 0);
		calloutExecutor.setCalloutFactory(calloutFactory);

		this.field = new PlainCalloutField();
		field.setAD_Column_ID(POJOLookupMap.get().nextId(I_AD_Column.Table_Name));
	}

	@Test
	public void test_StandardCase()
	{
		final MockedCallout callout1 = createAndRegisterMockedCallout(field);
		final MockedCallout callout2 = createAndRegisterMockedCallout(field);
		final MockedCallout callout3 = createAndRegisterMockedCallout(field);

		calloutExecutor.execute(field);

		Assert.assertTrue("Callout1 was not called", callout1.isCalled());
		Assert.assertTrue("Callout2 was not called", callout2.isCalled());
		Assert.assertTrue("Callout3 was not called", callout3.isCalled());
	}

	@Test
	public void test_NoCallouts()
	{
		calloutExecutor.execute(field);
	}

	@Test
	public void test_FailingCallout_CalloutInitException()
	{
		final MockedCallout callout1 = createAndRegisterMockedCallout(field);
		callout1.setOnExecuteFailException(new CalloutInitException("test"));

		//
		// First run
		assertExceptionOnExecute(field, CalloutInitException.class);
		Assert.assertTrue("Callout1 was not called", callout1.isCalled());
		Assert.assertFalse("Callout shall be removed from active callouts list",
				calloutExecutor.hasCallouts(field));

		//
		// Second run - callout shall not be called again
		callout1.setCalled(false);
		calloutExecutor.execute(field);
		Assert.assertFalse("Callout1 shall NOT be called again", callout1.isCalled());
	}

	@Test
	public void test_FailingCallout_CalloutExecutionException()
	{
		final MockedCallout callout1 = createAndRegisterMockedCallout(field);
		callout1.setOnExecuteFailException(new CalloutExecutionException("test"));

		//
		// First run
		assertExceptionOnExecute(field, CalloutExecutionException.class);
		Assert.assertTrue("Callout1 was not called", callout1.isCalled());
		Assert.assertTrue("Callout shall NOT be removed from active callouts list",
				calloutExecutor.hasCallouts(field));

		//
		// Second run - callout shall not be called again
		callout1.setCalled(false);
		assertExceptionOnExecute(field, CalloutExecutionException.class);
		Assert.assertTrue("Callout1 shall be called again", callout1.isCalled());
	}

	@Test
	public void test_StopExecutionOnFirstFailingCallout()
	{
		final MockedCallout callout1 = createAndRegisterMockedCallout(field);
		final MockedCallout callout2 = createAndRegisterMockedCallout(field);
		callout2.setOnExecuteFailException(new CalloutExecutionException("test"));
		final MockedCallout callout3 = createAndRegisterMockedCallout(field);

		assertExceptionOnExecute(field, CalloutExecutionException.class);

		Assert.assertTrue("Callout1 shall be called again", callout1.isCalled());
		Assert.assertTrue("Callout2 shall be called again", callout2.isCalled());
		Assert.assertFalse("Callout3 shall be called again", callout3.isCalled());
	}

	private MockedCallout createAndRegisterMockedCallout(final ICalloutField field)
	{
		final MockedCallout callout = new MockedCallout();
		final DefaultCalloutInstance calloutInstance = new DefaultCalloutInstance(callout);
		calloutFactory.regiterCallout(field, calloutInstance);

		return callout;
	}

	private <T extends Exception> T assertExceptionOnExecute(final ICalloutField field, Class<T> expectedExceptionClass)
	{
		Exception exception = null;
		try
		{
			calloutExecutor.execute(field);
		}
		catch (Exception e)
		{
			exception = e;
		}

		Assert.assertNotNull("No exception was thrown", exception);

		Assert.assertTrue("Exception " + expectedExceptionClass + " was expected but we got " + exception,
				expectedExceptionClass.isAssignableFrom(exception.getClass()));

		@SuppressWarnings("unchecked")
		final T exceptionCasted = (T)exception;

		if (exceptionCasted instanceof CalloutException)
		{
			final CalloutException calloutException = (CalloutException)exceptionCasted;
			assertCalloutExceptionIsFilled(calloutException, field);
		}
		return exceptionCasted;
	}

	private void assertCalloutExceptionIsFilled(final CalloutException exception, final ICalloutField field)
	{
		Assert.assertSame("Invalid executor for " + exception, calloutExecutor, exception.getCalloutExecutor());
		Assert.assertSame("Invalid field for " + exception, field, exception.getCalloutField());
		// exception.getCalloutInstance();
	}
}
