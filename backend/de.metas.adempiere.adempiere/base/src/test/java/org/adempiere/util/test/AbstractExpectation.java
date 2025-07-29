/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package org.adempiere.util.test;

import de.metas.util.lang.RepoIdAware;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

/**
 * This class is the mother and the father, at same time, of all our expectations ;)
 *
 * @param <ParentExpectationType>
 * @author tsa
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
	private String expectationName = null;

	/**
	 * BigDecimal comparation with margin used by {@link #assertCloseToExpected(ErrorMessage, BigDecimal, BigDecimal)} methods.
	 * <p>
	 * NOTE: by default is not set (i.e. margin will be 0). Please let it like this because generally we shall have zero tolerance.
	 */
	private BigDecimal _errorMargin;

	public AbstractExpectation(final ParentExpectationType parentExpectation)
	{
		super();
		this._parentExpectation = parentExpectation;
	}

	/**
	 * No parent constructor
	 */
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
		else
		{
			return new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
			// throw new IllegalStateException("Cannot find context");
		}
	}

	/**
	 * Note: method is final because we want to call it from subclasses' constructors without having to guess which implementation it will pick.
	 *
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

	private final ParentExpectationType getParentExpectation()
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
	 * <p>
	 * To be extended by actual expectations. At this level it throws {@link UnsupportedOperationException}.
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

		assertThat(actualToUse).as(ErrorMessage.toString(message)).isEqualByComparingTo(expectedToUse);
	}

	/**
	 * Asserts <code>actual</code> is close to <code>expected</code>, considering the defined error margin ({@link #getErrorMargin()}).
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

		assertThat(actualToUse)
				.as(message.toString())
				.isCloseTo(expectedToUse, within(errorMargin)); // expected
	}

	protected <T> void assertModelEquals(final String message, final T expected, final T actual)
	{
		assertModelEquals(newErrorMessage(message), expected, actual);
	}

	@Deprecated
	protected <T extends RepoIdAware> void assertModelEquals(final String message, final T expected, final T actual)
	{
		throw new AdempiereException("Avoid using assertModelEquals() for RepoIdAwares. Use assertEquals.");
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

		Assertions.assertEquals(expectedId, actualId, messageToUse.expect("same IDs").toString());
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
		Assertions.assertNotNull(object, ErrorMessage.toString(message));
	}

	protected void assertTrue(final ErrorMessage message, final boolean condition)
	{
		Assertions.assertTrue(condition, ErrorMessage.toString(message));
	}

	protected void assertEquals(final String message, final Object expected, final Object actual)
	{
		Assertions.assertEquals(expected, actual, message);
	}

	protected void assertEquals(final ErrorMessage message, final Object expected, final Object actual)
	{
		Assertions.assertEquals(expected, actual, ErrorMessage.toString(message));
	}

	protected void assertNotEmpty(final ErrorMessage message, final Collection<?> collection)
	{
		assertNotNull(message, collection);
		assertTrue(message, !collection.isEmpty());
	}

	/**
	 * Set this expectation's name. Can be useful to keep the overview.
	 *
	 * @param expectationName
	 * @return
	 */
	public AbstractExpectation<ParentExpectationType> expectationName(final String expectationName)
	{
		this.expectationName = expectationName;
		return this;
	}
}
