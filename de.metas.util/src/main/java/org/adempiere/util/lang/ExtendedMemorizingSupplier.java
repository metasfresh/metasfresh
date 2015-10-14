package org.adempiere.util.lang;

import java.io.Serializable;

import com.google.common.base.Supplier;

/**
 * An memorizing supplier which also allows to forget the value or to peek current value.
 * 
 * @param <T>
 */
public final class ExtendedMemorizingSupplier<T> implements Supplier<T>, Serializable
{
	public static final <T> ExtendedMemorizingSupplier<T> of(final Supplier<T> supplier)
	{
		return new ExtendedMemorizingSupplier<T>(supplier);
	}

	private final Supplier<T> delegate;
	private transient volatile boolean initialized;
	// "value" does not need to be volatile; visibility piggy-backs
	// on volatile read of "initialized".
	private transient T value;

	private ExtendedMemorizingSupplier(final Supplier<T> delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public T get()
	{
		// A 2-field variant of Double Checked Locking.
		if (!initialized)
		{
			synchronized (this)
			{
				if (!initialized)
				{
					final T t = delegate.get();
					value = t;
					initialized = true;
					return t;
				}
			}
		}
		return value;
	}

	/** @return memorized value or <code>null</code> if not initialized */
	public T peek()
	{
		synchronized (this)
		{
			return value;
		}
	}

	/**
	 * Forget memorized value
	 * @return 
	 * @return current value if any
	 */
	public T forget()
	{
		synchronized (this)
		{
			final T valueOld = this.value;
			initialized = false;
			value = null;
			return valueOld;
		}
	}

	/** @return true if this supplier has a value memorized */
	public boolean isInitialized()
	{
		return initialized;
	}

	@Override
	public String toString()
	{
		return "ExtendedMemorizingSupplier[" + delegate + "]";
	}

	private static final long serialVersionUID = 0;
}
