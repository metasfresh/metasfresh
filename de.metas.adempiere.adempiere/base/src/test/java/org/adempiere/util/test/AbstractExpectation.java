package org.adempiere.util.test;

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


import static org.hamcrest.Matchers.is;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;

import java.math.BigDecimal;
import java.util.Collection;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.util.Env;
import org.hamcrest.Matchers;
import org.junit.Assert;

/**
 * This class is the mother and the father, at same time, of all our expectations ;)
 *
 * @author tsa
 *
 * @param <ParentExpectationType>
 */
public class AbstractExpectation<ParentExpectationType>
{
	@ToStringBuilder(skip = true)
	private final ParentExpectationType _parentExpectation;

	@ToStringBuilder(skip = true)
	private IContextAware _context;
	@ToStringBuilder(skip = true)
	private Runnable endExpectationCallback;

	/**
	 * Will be in the tostring method
	 */
	@SuppressWarnings("unused")
	private String expectationName= null;

	/**
	 * BigDecimal comparation with margin used by {@link #assertCloseToExpected(ErrorMessage, BigDecimal, BigDecimal)} methods.
	 *
	 * NOTE: by default is not set (i.e. margin will be 0). Please let it like this because generally we shall have zero tolerance.
	 */
	private BigDecimal _errorMargin;

	public AbstractExpectation(final ParentExpectationType parentExpectation)
	{
		super();
		this._parentExpectation = parentExpectation;
	}

	/** No parent constructor */
	protected AbstractExpectation()
	{
		this((ParentExpectationType)null);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	protected final IContextAware getContext()
	{
		if (_context != null)
		{
			return _context;
		}

		final ParentExpectationType parent = getParentExpectation();
		if (parent instanceof AbstractExpectation<?>)
		{
			return ((AbstractExpectation<?>)parent).getContext();
		}

		return new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
		// throw new IllegalStateException("Cannot find context");
	}

	/**
	 * Note: method is final because we want to call it from subclasses' constructors without having to guess which implementation it will pick.
	 * @param context
	 */
	public final void setContext(final IContextAware context)
	{
		this._context = context;
	}

	/**
	 * Convenient method to mark that you finished defining this expectation and you want to go back to parent expectation.
	 *
	 * @return parent expectation
	 */
	public ParentExpectationType endExpectation()
	{
		if (endExpectationCallback != null)
		{
			endExpectationCallback.run();
		}

		return getParentExpectation();
	}

	public final ParentExpectationType getParentExpectation()
	{
		return this._parentExpectation;
	}

	public AbstractExpectation<ParentExpectationType> endExpectationCallback(final Runnable endExpectationCallback)
	{
		if (this.endExpectationCallback == endExpectationCallback)
		{
			return this;
		}

		if (this.endExpectationCallback != null)
		{
			throw new IllegalStateException("Cannot set endExpectationCallback bacause it was already configured"
					+ "\n Expection: " + this
					+ "\n Current callback: " + this.endExpectationCallback
					+ "\n New callback: " + endExpectationCallback);
		}

		this.endExpectationCallback = endExpectationCallback;
		return this;
	}

	/**
	 * Copy values from given expectation to this expectation.
	 *
	 * To be extended by actual expectations. At this level it throws {@link UnsupportedOperationException}.
	 *
	 * @param from
	 */
	public <FromExpectationType extends AbstractExpectation<?>> void copyFromExpectation(FromExpectationType from)
	{
		throw new UnsupportedOperationException("copyFrom method was not implemented for " + this);
	}

	protected void assertEquals(final String message, final BigDecimal expected, final BigDecimal actual)
	{
		assertEquals(newErrorMessage(message), expected, actual);
	}

	protected void assertEquals(final ErrorMessage message, final BigDecimal expected, final BigDecimal actual)
	{
		BigDecimal expectedToUse = expected;
		if (expectedToUse == null)
		{
			expectedToUse = BigDecimal.ZERO;
		}

		BigDecimal actualToUse = actual;
		if (actualToUse == null)
		{
			actualToUse = BigDecimal.ZERO;
		}

		Assert.assertThat(ErrorMessage.toString(message),
				actualToUse,
				Matchers.comparesEqualTo(expectedToUse));
	}

	/**
	 * Asserts <code>actual</code> is close to <code>expected</code>, considering the defined error margin ({@link #getErrorMargin()}).
	 *
	 * @param message
	 * @param expected
	 * @param actual
	 */
	protected final void assertCloseToExpected(final String message, final BigDecimal expected, final BigDecimal actual)
	{
		assertCloseToExpected(newErrorMessage(message), expected, actual);
	}

	protected final void assertCloseToExpected(final ErrorMessage message, final BigDecimal expected, final BigDecimal actual)
	{
		BigDecimal expectedToUse = expected;
		if (expectedToUse == null)
		{
			expectedToUse = BigDecimal.ZERO;
		}

		BigDecimal actualToUse = actual;
		if (actualToUse == null)
		{
			actualToUse = BigDecimal.ZERO;
		}

		BigDecimal errorMargin = getErrorMargin();
		if (errorMargin == null)
		{
			errorMargin = BigDecimal.ZERO;
		}

		Assert.assertThat(
				message.toString(),
				actualToUse, // actual
				is(closeTo(expectedToUse, errorMargin)) // expected
		);
	}

	protected <T> void assertModelEquals(final String message, final T expected, final T actual)
	{
		assertModelEquals(newErrorMessage(message), expected, actual);
	}

	protected <T> void assertModelEquals(final ErrorMessage message, final T expected, final T actual)
	{
		int expectedId = -1;
		if (expected != null)
		{
			expectedId = InterfaceWrapperHelper.getId(expected);
		}
		expectedId = expectedId <= 0 ? -1 : expectedId;

		int actualId = -1;
		if (actual != null)
		{
			actualId = InterfaceWrapperHelper.getId(actual);
		}
		actualId = actualId <= 0 ? -1 : actualId;

		final ErrorMessage messageToUse = derive(message)
				.addContextInfo("Expected model", expected)
				.addContextInfo("Actual model", actual);

		Assert.assertEquals(messageToUse.expect("same IDs").toString(), expectedId, actualId);
	}

	public final AbstractExpectation<ParentExpectationType> setErrorMargin(final BigDecimal errorMargin)
	{
		this._errorMargin = errorMargin;
		return this;
	}

	public final BigDecimal getErrorMargin()
	{
		return _errorMargin;
	}

	protected ErrorMessage newErrorMessage()
	{
		return init(ErrorMessage.newInstance());
	}

	protected ErrorMessage derive(ErrorMessage message)
	{
		return init(ErrorMessage.derive(message));
	}

	protected ErrorMessage newErrorMessage(String message)
	{
		return init(ErrorMessage.newErrorMessage(message));
	}

	protected ErrorMessage init(ErrorMessage message)
	{
		return message.addContextInfo("Expectation", this);
	}

	protected void assertNotNull(final ErrorMessage message, final Object object)
	{
		Assert.assertNotNull(ErrorMessage.toString(message), object);
	}

	protected void assertTrue(final ErrorMessage message, final boolean condition)
	{
		Assert.assertTrue(ErrorMessage.toString(message), condition);
	}

	protected void assertEquals(final ErrorMessage message, final Object expected, final Object actual)
	{
		Assert.assertEquals(ErrorMessage.toString(message), expected, actual);
	}

	protected void assertNotEmpty(final ErrorMessage message, final Collection<?> collection)
	{
		assertNotNull(message, collection);
		assertTrue(message, !collection.isEmpty());
	}

	/**
	 * Set this expectation's name. Can be useful to keep the overview.
	 * @param expectationName
	 * @return
	 */
	public AbstractExpectation<ParentExpectationType> expectationName(final String expectationName)
	{
		this.expectationName=expectationName;
		return this;
	}
}
