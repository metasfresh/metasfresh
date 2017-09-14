package org.adempiere.util.concurrent;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Nullable;

import org.slf4j.Logger;

import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Helper class used to run a given {@link AsyncIOStreamProducer} in a separate thread, pipeing it's output to an {@link InputStream}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Builder
public final class AsyncIOStreamProducerExecutor
{
	@NonNull
	private final ExecutorService executor;
	@Nullable
	private final Logger exceptionLogger;

	public InputStream execute(final AsyncIOStreamProducer producer)
	{
		final PipedInputStream in = new PipedInputStream();
		final InputStreamWithFinalExceptionCheck inWithException = InputStreamWithFinalExceptionCheck.of(in);

		executor.execute(() -> {
			try (final PipedOutputStream out = new PipedOutputStream(in))
			{
				producer.produce(out);
			}
			catch (final Exception ex)
			{
				if (exceptionLogger != null)
				{
					exceptionLogger.warn("Failed running {}", producer, ex);
				}
				inWithException.fail(ex);
			}
			finally
			{
				inWithException.countDown();
			}
		});

		return in;
	}

	@FunctionalInterface
	public static interface AsyncIOStreamProducer
	{
		public void produce(final OutputStream out) throws Exception;
	}

	/**
	 * @author https://stackoverflow.com/a/33698661/2410079
	 */
	private static final class InputStreamWithFinalExceptionCheck extends FilterInputStream
	{
		public static InputStreamWithFinalExceptionCheck of(final InputStream stream)
		{
			return new InputStreamWithFinalExceptionCheck(stream);
		}

		private final AtomicReference<IOException> exception = new AtomicReference<>(null);
		private final CountDownLatch complete = new CountDownLatch(1);

		private InputStreamWithFinalExceptionCheck(@NonNull final InputStream stream)
		{
			super(stream);
		}

		@Override
		public void close() throws IOException
		{
			try
			{
				complete.await();

				final IOException e = exception.getAndSet(null);
				if (e != null)
				{
					throw e;
				}
			}
			catch (final InterruptedException e)
			{
				throw new IOException("Interrupted while waiting for synchronised closure");
			}
			finally
			{
				in.close();
			}
		}

		public void fail(@NonNull final Exception ex)
		{
			if (ex instanceof IOException)
			{
				exception.set((IOException)ex);
			}
			else
			{
				exception.set(new IOException(ex.getLocalizedMessage(), ex));
			}
		}

		public void countDown()
		{
			complete.countDown();
		}
	}
}
