package org.adempiere.test;

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


import java.lang.reflect.Method;

import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.junit.Assert;

import de.metas.adempiere.form.AbstractClientUI;
import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.IClientUIInstance;
import de.metas.adempiere.form.IClientUIInvoker;

/**
 * Mocked {@link IClientUI} implementation used in JUnit tests.
 * 
 * @author tsa
 *
 */
public class TestClientUI extends AbstractClientUI implements IClientUIInstance
{
	private final Logger logger = LogManager.getLogger(getClass());

	public static interface IAskCallback
	{
		boolean getYesNoAnswer(int WindowNo, String AD_Message, String message);
	};

	public static class YesNoAskCallback implements IAskCallback
	{
		private final boolean answer;

		public YesNoAskCallback(final boolean answer)
		{
			this.answer = answer;
		}

		@Override
		public boolean getYesNoAnswer(final int WindowNo, final String AD_Message, final String message)
		{
			return answer;
		}
	};

	private static final IAskCallback RESPONSE_Yes = new YesNoAskCallback(true);
	private static final IAskCallback RESPONSE_No = new YesNoAskCallback(false);

	private IAskCallback askCallback;
	// private Boolean yesNoAnswer = null;

	private Method lastInvocation;

	public TestClientUI()
	{
		super();
	}

	public void setAskCallback(final IAskCallback callback)
	{
		askCallback = callback;
	}

	public void setYesNoAnswer(final Boolean answer)
	{
		final IAskCallback callback;
		if (answer == null)
		{
			callback = null;
		}
		else
		{
			callback = answer.booleanValue() ? RESPONSE_Yes : RESPONSE_No;
		}

		setAskCallback(callback);
	}

	public void reset()
	{
		askCallback = null;
		lastInvocation = null;
	}

	@Override
	public IClientUIInstance createInstance()
	{
		return this;
	}

	@Override
	protected IClientUIInstance getCurrentInstance()
	{
		return this;
	}

	@Override
	public void info(final int WindowNo, final String AD_Message)
	{
		info(WindowNo, AD_Message, null);
	}

	@Override
	public void info(final int WindowNo, final String AD_Message, final String message)
	{
		// nothing
	}

	@Override
	public boolean ask(final int WindowNo, final String AD_Message)
	{
		return ask(WindowNo, AD_Message, null);
	}

	@Override
	public boolean ask(final int WindowNo, final String AD_Message, final String message)
	{
		// ADAssert.assertMsgExists(AD_Message);

		final Boolean answer = getYesNoAnswer(WindowNo, AD_Message, message);
		Assert.assertNotNull("No default answer was specified for windowNo=" + WindowNo + ", AD_Message=" + AD_Message + ", message=" + message, answer);

		logger.info("Answering " + answer + " on windowNo=" + WindowNo + ", AD_Message=" + AD_Message + ", message=" + message);

		askMethodCalled();

		return answer;
	}

	@Override
	public void warn(final int WindowNo, final String AD_Message)
	{
		warn(WindowNo, AD_Message, null);
	}

	@Override
	public void warn(final int WindowNo, final String AD_Message, final String message)
	{
		// ADAssert.assertMsgExists(AD_Message);
		warnMethodCalled();
	}

	public void assertWarnDialogWasShown()
	{
		Assert.assertEquals("IClientUI.warn() was not called", "warn", getLastInvocation().getName());
	}

	public void assertAskDialogWasShown()
	{
		Assert.assertEquals("IClientUI.ask() was not called", "ask", getLastInvocation().getName());
	}

	private void askMethodCalled()
	{
		try
		{
			lastInvocation = this.getClass().getMethod("ask", int.class, String.class, String.class);
		}
		catch (final Exception e)
		{
			Assert.fail("Caught " + e);
		}
	}

	private void warnMethodCalled()
	{
		try
		{
			lastInvocation = this.getClass().getMethod("warn", int.class, String.class, String.class);
		}
		catch (final Exception e)
		{
			Assert.fail("Caught " + e);
		}
	}

	private Method getLastInvocation()
	{
		return lastInvocation;
	}

	private Boolean getYesNoAnswer(final int WindowNo, final String AD_Message, final String message)
	{
		if (askCallback == null)
		{
			return null;
		}
		return askCallback.getYesNoAnswer(WindowNo, AD_Message, message);
	}

	@Override
	public void invokeLater(final int windowNo, final Runnable runnable)
	{
		new Thread(runnable).start();
	}

	@Override
	public void error(final int WIndowNo, final String AD_Message)
	{
		try
		{
			lastInvocation = this.getClass().getMethod("error", int.class, String.class);
		}
		catch (final Exception e)
		{
			Assert.fail("Caught " + e);
		}
	}

	@Override
	public void error(final int WIndowNo, final String AD_Message, final String message)
	{
		try
		{
			lastInvocation = this.getClass().getMethod("error", int.class, String.class, String.class);
		}
		catch (final Exception e)
		{
			Assert.fail("Caught " + e);
		}
	}

	@Override
	public void download(final byte[] data, final String contentType, final String filename)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getClientInfo()
	{
		final String javaVersion = System.getProperty("java.version");
		return new StringBuilder("AIT, java.version=").append(javaVersion).toString();
	}

	@Override
	public void executeLongOperation(final Object component, final Runnable runnable)
	{
		runnable.run();
	}

	@Override
	public IClientUIInvoker invoke()
	{
		return new TestClientUIInvoker(getCurrentInstance());
	}

}
