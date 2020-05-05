package org.adempiere.ad.callout.api.impl;

import org.adempiere.ad.callout.api.ICalloutExecutor;

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
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalloutExecutorTest
{
	private MockedCalloutProvider calloutProvider;
	private MockedCalloutField field;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.field = MockedCalloutField.createNewField();

		calloutProvider = new MockedCalloutProvider();
	}

	@Test
	public void test_StandardCase()
	{
		final MockedCallout callout1 = createAndRegisterMockedCallout(field);
		final MockedCallout callout2 = createAndRegisterMockedCallout(field);
		final MockedCallout callout3 = createAndRegisterMockedCallout(field);

		newExecutor().execute(field);

		Assert.assertTrue("Callout1 was not called", callout1.isCalled());
		Assert.assertTrue("Callout2 was not called", callout2.isCalled());
		Assert.assertTrue("Callout3 was not called", callout3.isCalled());
	}

	@Test
	public void test_NoCallouts()
	{
		final ICalloutExecutor calloutExecutor = newExecutor();
		
		// We expect the NullCalloutExecutor
		Assert.assertSame(NullCalloutExecutor.instance, calloutExecutor);
		
		calloutExecutor.execute(field); // shall do nothing
	}

	@Test
	public void test_FailingCallout_CalloutInitException()
	{
		final MockedCallout callout1 = createAndRegisterMockedCallout(field);
		callout1.setOnExecuteFailException(() -> new CalloutInitException("test"));

		final ICalloutExecutor calloutExecutor = newExecutor();

		//
		// First run
		assertExceptionOnExecute(calloutExecutor, field, CalloutInitException.class);
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
		final MockedCallout callout1 = createAndRegisterMockedCallout(field)
				.setOnExecuteFailException(() -> new CalloutExecutionException("test"));

		final ICalloutExecutor calloutExecutor = newExecutor();

		//
		// First run
		assertExceptionOnExecute(calloutExecutor, field, CalloutExecutionException.class);
		Assert.assertTrue("Callout1 was not called", callout1.isCalled());
		Assert.assertTrue("Callout shall NOT be removed from active callouts list",
				calloutExecutor.hasCallouts(field));

		//
		// Second run - callout shall BE called again
		callout1.setCalled(false);
		assertExceptionOnExecute(calloutExecutor, field, CalloutExecutionException.class);
		Assert.assertTrue("Callout1 shall be called again", callout1.isCalled());
	}

	@Test
	public void test_StopExecutionOnFirstFailingCallout()
	{
		final MockedCallout callout1 = createAndRegisterMockedCallout(field);
		final MockedCallout callout2 = createAndRegisterMockedCallout(field)
				.setOnExecuteFailException(() -> new RuntimeException("test"));
		final MockedCallout callout3 = createAndRegisterMockedCallout(field);

		final ICalloutExecutor calloutExecutor = newExecutor();
		assertExceptionOnExecute(calloutExecutor, field, CalloutExecutionException.class);

		Assert.assertTrue("Callout1 shall be called again", callout1.isCalled());
		Assert.assertTrue("Callout2 shall be called again", callout2.isCalled());
		Assert.assertFalse("Callout3 shall be called again", callout3.isCalled());
	}

	private ICalloutExecutor newExecutor()
	{
		return CalloutExecutor.builder()
				.setTableName(field.getTableName())
				.setCalloutProvider(calloutProvider)
				.build();
	}

	private MockedCallout createAndRegisterMockedCallout(final ICalloutField field)
	{
		final MockedCallout callout = new MockedCallout();
		calloutProvider.regiterCallout(field, callout);
		return callout;
	}

	private <T extends Exception> T assertExceptionOnExecute(final ICalloutExecutor calloutExecutor, final ICalloutField field, Class<T> expectedExceptionClass)
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

		final boolean gotExpectedException = expectedExceptionClass.isAssignableFrom(exception.getClass());
		if (!gotExpectedException)
		{
			exception.printStackTrace();
		}
		Assert.assertTrue("Exception " + expectedExceptionClass + " was expected but we got " + exception, gotExpectedException);

		@SuppressWarnings("unchecked")
		final T exceptionCasted = (T)exception;

		if (exceptionCasted instanceof CalloutException)
		{
			final CalloutException calloutException = (CalloutException)exceptionCasted;
			assertCalloutExceptionIsFilled(calloutException, calloutExecutor, field);
		}
		return exceptionCasted;
	}

	private void assertCalloutExceptionIsFilled(final CalloutException exception, final ICalloutExecutor calloutExecutor, final ICalloutField field)
	{
		Assert.assertSame("Invalid executor for " + exception, calloutExecutor, exception.getCalloutExecutor());
		Assert.assertSame("Invalid field for " + exception, field, exception.getCalloutField());
		// exception.getCalloutInstance();
	}
}
