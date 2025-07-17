package org.adempiere.context;

import de.metas.util.AbstractPropertiesProxy;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;

import java.io.Serial;
import java.util.Properties;

/* package */final class ThreadLocalServerContext extends AbstractPropertiesProxy
{
	@Serial
	private static final long serialVersionUID = 794823850355755679L;

	private IContextProviderListener listener = NullContextProviderListener.instance;

	private final InheritableThreadLocal<Properties> threadLocalContext = new InheritableThreadLocal<Properties>()
	{
		/** @return an empty properties instance */
		@Override
		protected Properties initialValue()
		{
			final Properties ctx = new Properties();
			listener.onContextCreated(ctx);
			return ctx;
		}

		/** @return a new properties instance, using the given <code>parentValue</code> for defaults */
		@Override
		protected Properties childValue(final Properties ctx)
		{
			final Properties childCtx = new Properties(ctx);
			listener.onChildContextCreated(ctx, childCtx);
			return childCtx;
		}

		@Override
		public Properties get()
		{
			final Properties ctx = super.get();
			listener.onContextCheckOut(ctx);
			return ctx;
		};

		@Override
		public void set(final Properties ctx)
		{
			final Properties ctxOld = super.get();
			super.set(ctx);
			listener.onContextCheckIn(ctx, ctxOld);
		};
	};

	@Override
	protected Properties getDelegate()
	{
		return threadLocalContext.get();
	}

	public ThreadLocalServerContext()
	{
		super();
	}

	/**
	 * Temporarily switches the context in the current thread.
	 */
	public IAutoCloseable switchContext(final Properties ctx)
	{
		Check.assumeNotNull(ctx, "ctx not null");

		// Avoid StackOverflowException caused by setting the same context
		if (ctx == this)
		{
			return NullAutoCloseable.instance;
		}

		final long threadIdOnSet = Thread.currentThread().getId();
		final Properties ctxOld = threadLocalContext.get();

		threadLocalContext.set(ctx);

		return new IAutoCloseable()
		{
			private boolean closed = false;

			@Override
			public void close()
			{
				// Do nothing if already closed
				if (closed)
				{
					return;
				}

				// Assert we are restoring the ctx in same thread.
				// Because else, we would set the context "back" in another thread which would lead to huge inconsistencies.
				if (Thread.currentThread().getId() != threadIdOnSet)
				{
					throw new IllegalStateException("Error: setting back the context shall be done in the same thread as it was set initially");
				}

				threadLocalContext.set(ctxOld);
				closed = true;
			}
		};
	}

	/**
	 * Dispose the context from current thread
	 */
	public void dispose()
	{
		final Properties ctx = threadLocalContext.get();
		ctx.clear();
		threadLocalContext.remove();
	}

	public void setListener(@NonNull final IContextProviderListener listener)
	{
		this.listener = listener;
	}
}
