package de.metas.async.exceptions;

/*
 * #%L
 * de.metas.async
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

import de.metas.async.Async_Constants;
import de.metas.async.processor.IWorkpackageSkipRequest;
import de.metas.async.spi.IWorkpackageProcessor;
import org.adempiere.exceptions.AdempiereException;

import java.util.Random;

/**
 * Exception to be thrown from {@link IWorkpackageProcessor} implementation if we want current workpackage to be skipped this time.
 *
 * @author tsa
 *
 */
public class WorkpackageSkipRequestException extends AdempiereException implements IWorkpackageSkipRequest
{
	private static final long serialVersionUID = 5950712616746434839L;

	private final int skipTimeoutMillis;

	private final static Random RANDOM = new Random();

	public static WorkpackageSkipRequestException create(final String message)
	{
		return new WorkpackageSkipRequestException(message,
				Async_Constants.DEFAULT_RETRY_TIMEOUT_MILLIS,
				null);
	}

	public static WorkpackageSkipRequestException createWithThrowable(final String message, final Throwable cause)
	{
		return new WorkpackageSkipRequestException(message,
				Async_Constants.DEFAULT_RETRY_TIMEOUT_MILLIS,
				cause);
	}

	public static WorkpackageSkipRequestException createWithTimeoutAndThrowable(
			final String message,
			final int skipTimeoutMillis,
			final Throwable cause)
	{
		return new WorkpackageSkipRequestException(message,
				skipTimeoutMillis,
				cause);
	}

	public static WorkpackageSkipRequestException createWithTimeout(
			final String message,
			final int skipTimeoutMillis)
	{
		return new WorkpackageSkipRequestException(message,
				skipTimeoutMillis,
				null);
	}

	/**
	 * A random int between 0 and 5000 is added to the {@link Async_Constants#DEFAULT_RETRY_TIMEOUT_MILLIS} timeout. Use this if you want workpackages that are postponed at the same time to be
	 * retried at different times.
	 */
	public static WorkpackageSkipRequestException createWithRandomTimeout(final String message)
	{
		return new WorkpackageSkipRequestException(message,
				Async_Constants.DEFAULT_RETRY_TIMEOUT_MILLIS + RANDOM.nextInt(5001),
				null);
	}

	private WorkpackageSkipRequestException(final String message, final int skipTimeoutMillis, final Throwable cause)
	{
		super(message, cause);
		this.skipTimeoutMillis = skipTimeoutMillis;
	}

	@Override
	public String getSkipReason()
	{
		return getLocalizedMessage();
	}

	@Override
	public boolean isSkip()
	{
		return true;
	}

	@Override
	public int getSkipTimeoutMillis()
	{
		return skipTimeoutMillis;
	}

	@Override
	public Exception getException()
	{
		return this;
	}

	/**
	 * No need to fill the log if this exception is thrown.
	 */
	protected boolean isLoggedInTrxManager()
	{
		return false;
	}
}
