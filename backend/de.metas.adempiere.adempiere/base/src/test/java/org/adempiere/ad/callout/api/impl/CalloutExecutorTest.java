package org.adempiere.ad.callout.api.impl;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.exceptions.CalloutException;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalloutExecutorTest
{
	private MockedCalloutProvider calloutProvider;
	private MockedCalloutField field;

	@BeforeEach
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

		Assertions.assertTrue(callout1.isCalled(),"Callout1 was not called");
		Assertions.assertTrue(callout2.isCalled(), "Callout2 was not called");
		Assertions.assertTrue(callout3.isCalled(), "Callout3 was not called");
	}

	@Test
	public void test_NoCallouts()
	{
		final ICalloutExecutor calloutExecutor = newExecutor();

		// We expect the NullCalloutExecutor
		Assertions.assertSame(NullCalloutExecutor.instance, calloutExecutor);

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
		Assertions.assertTrue(callout1.isCalled(), "Callout1 was not called");
		Assertions.assertFalse(calloutExecutor.hasCallouts(field), "Callout shall be removed from active callouts list");

		//
		// Second run - callout shall not be called again
		callout1.setCalled(false);
		calloutExecutor.execute(field);
		Assertions.assertFalse(callout1.isCalled(), "Callout1 shall NOT be called again");
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
		Assertions.assertTrue(callout1.isCalled(), "Callout1 was not called");
		Assertions.assertTrue(calloutExecutor.hasCallouts(field), "Callout shall NOT be removed from active callouts list");

		//
		// Second run - callout shall BE called again
		callout1.setCalled(false);
		assertExceptionOnExecute(calloutExecutor, field, CalloutExecutionException.class);
		Assertions.assertTrue(callout1.isCalled(), "Callout1 shall be called again");
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

		Assertions.assertTrue(callout1.isCalled(), "Callout1 shall be called again");
		Assertions.assertTrue(callout2.isCalled(), "Callout2 shall be called again");
		Assertions.assertFalse(callout3.isCalled(), "Callout3 shall be called again");
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

		Assertions.assertNotNull(exception, "No exception was thrown");

		final boolean gotExpectedException = expectedExceptionClass.isAssignableFrom(exception.getClass());
		if (!gotExpectedException)
		{
			exception.printStackTrace();
		}
		Assertions.assertTrue(gotExpectedException, "Exception " + expectedExceptionClass + " was expected but we got " + exception);

		@SuppressWarnings("unchecked") final T exceptionCasted = (T)exception;

		if (exceptionCasted instanceof CalloutException calloutException)
		{
			assertCalloutExceptionIsFilled(calloutException, calloutExecutor, field);
		}
		return exceptionCasted;
	}

	private void assertCalloutExceptionIsFilled(final CalloutException exception, final ICalloutExecutor calloutExecutor, final ICalloutField field)
	{
		Assertions.assertSame(calloutExecutor, exception.getCalloutExecutor(), "Invalid executor for " + exception);
		Assertions.assertSame(field, exception.getCalloutField(), "Invalid field for " + exception);
		// exception.getCalloutInstance();
	}
}
