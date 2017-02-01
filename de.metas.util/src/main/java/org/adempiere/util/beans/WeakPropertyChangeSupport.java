package org.adempiere.util.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;
import java.lang.ref.WeakReference;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

/**
 * An {@link PropertyChangeSupport} which makes weak reference to source bean and which is able to register the listeners weakly.
 * <p>
 * This means that both a registered listener and source bean can be reclaimed and removed by the garbage collector without the need to explicitly unregister them.
 *
 * @author tsa
 * @see WeakReference
 *
 */
public class WeakPropertyChangeSupport extends PropertyChangeSupport
{
	private static final long serialVersionUID = 309861519819203221L;

	/**
	 * i.e. use weak default option
	 */
	public static final Boolean WEAK_AUTO = null;

	protected static enum WeakListenerCreationScope
	{
		ForAdding, ForRemoving,
	};

	private final boolean _weakDefault;

	/**
	 * This member is here only such that we have a minimum toString() for debugging, since our super class <code>PropertyChangeSupport</code> makes it hard to take a look at the source bean.
	 */
	private final WeakReference<Object> debugSourceBeanRef;

	public WeakPropertyChangeSupport(final Object sourceBean)
	{
		this(sourceBean, false); // weakDefault=false
	}

	/**
	 *
	 * @param sourceBean
	 * @param weakDefault
	 */
	public WeakPropertyChangeSupport(final Object sourceBean, final boolean weakDefault)
	{
		this(new WeakReference<>(sourceBean), weakDefault);
	}

	private WeakPropertyChangeSupport(final WeakReference<Object> sourceBeanRef, final boolean weakDefault)
	{
		super(sourceBeanRef);
		this._weakDefault = weakDefault;
		this.debugSourceBeanRef = sourceBeanRef;
	}

	public final boolean isWeakDefault()
	{
		return _weakDefault;
	}

	/**
	 * Make sure that all listeners are removed from this instance.
	 *
	 * @throws IllegalStateException if the method failed to remove them all.
	 */
	public void clear()
	{
		final PropertyChangeListener[] listeners = getPropertyChangeListeners();
		if (listeners == null || listeners.length == 0)
		{
			return;
		}

		for (final PropertyChangeListener listener : listeners)
		{
			super.removePropertyChangeListener(listener);
		}

		// Make sure everything was removed
		final PropertyChangeListener[] listeners2 = getPropertyChangeListeners();
		if (listeners2 != null && listeners2.length > 0)
		{
			throw new IllegalStateException("Could not remove all listeners from " + this + "."
					+ "\nRemaining ones are: " + listeners2);
		}
	}

	private final PropertyChangeListener createWeakPropertyChangeListener(final PropertyChangeListener listener,
			final Boolean weak,
			final WeakListenerCreationScope scope)
	{
		//
		// Gets actual Weak option to use
		final boolean weakActual;
		if (weak == null || weak == WEAK_AUTO)
		{
			weakActual = isWeakDefault();
		}
		else
		{
			weakActual = weak;
		}

		final PropertyChangeListener listenerToWrap;
		if (listener instanceof WeakPropertyChangeListener)
		{
			final WeakPropertyChangeListener weakListener = (WeakPropertyChangeListener)listener;
			if (weakListener.isWeak() == weakActual)
			{
				return weakListener;
			}
			else
			{
				listenerToWrap = weakListener.getDelegate();
				Check.assumeNotNull(listenerToWrap, "Listener already expired: {}", weakListener);
			}
		}
		else if (listener instanceof PropertyChangeListenerProxy)
		{
			final PropertyChangeListenerProxy listenerProxy = (PropertyChangeListenerProxy)listener;
			final String propertyName = listenerProxy.getPropertyName();
			final PropertyChangeListener listenerActual = listenerProxy.getListener();
			final PropertyChangeListener listenerActualWrapped = createWeakPropertyChangeListener(listenerActual, weakActual, scope);
			return new PropertyChangeListenerProxy(propertyName, listenerActualWrapped);
		}
		else
		{
			listenerToWrap = listener;
		}

		final WeakPropertyChangeListener weakListener = new WeakPropertyChangeListener(listenerToWrap, weakActual);
		notifyWeakPropertyChangeListenerCreated(listenerToWrap, weak, scope);
		return weakListener;
	}

	/**
	 * Called when a {@link WeakPropertyChangeSupport} instance is created.
	 *
	 * @param listenerToWrap
	 * @param weak
	 * @param scope
	 */
	protected void notifyWeakPropertyChangeListenerCreated(PropertyChangeListener listener, Boolean weak, WeakListenerCreationScope scope)
	{
		// nothing at this level
	}

	@Override
	public final void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		addPropertyChangeListener(listener, WEAK_AUTO);
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener, final Boolean weak)
	{
		final PropertyChangeListener weakListener = createWeakPropertyChangeListener(listener, weak, WeakListenerCreationScope.ForAdding);
		super.addPropertyChangeListener(weakListener);
	}

	@Override
	public final void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		addPropertyChangeListener(propertyName, listener, WEAK_AUTO);
	}

	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener, final Boolean weak)
	{
		final PropertyChangeListener weakListener = createWeakPropertyChangeListener(listener, weak, WeakListenerCreationScope.ForAdding);
		super.addPropertyChangeListener(propertyName, weakListener);
	}

	@Override
	public void removePropertyChangeListener(final PropertyChangeListener listener)
	{
		super.removePropertyChangeListener(listener);

		final PropertyChangeListener weakListener = createWeakPropertyChangeListener(listener, true, WeakListenerCreationScope.ForRemoving);
		super.removePropertyChangeListener(weakListener);
	}

	@Override
	public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		super.removePropertyChangeListener(propertyName, listener);

		final PropertyChangeListener weakListener = createWeakPropertyChangeListener(listener, true, WeakListenerCreationScope.ForRemoving);
		super.removePropertyChangeListener(propertyName, weakListener);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				// it's dangerous to output the source, because sometimes the source also holds a reference to this listener, and if it also has this listener in its toString(), then we get a StackOverflow
				// .add("source (weakly referenced)", debugSourceBeanRef.get()) // debugSourceBeanRef can't be null, otherwise the constructor would have failed
				.add("listeners", getPropertyChangeListeners()) // i know there should be no method but only fields in toString(), but don't see how else to output this
				.toString();
	}
}
