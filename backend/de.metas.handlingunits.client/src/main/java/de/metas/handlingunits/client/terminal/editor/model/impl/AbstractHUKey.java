package de.metas.handlingunits.client.terminal.editor.model.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.util.Check;
import org.adempiere.util.collections.IdentityHashSet;
import org.compiere.util.Util;

import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IWeightable;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.attribute.storage.impl.AttributeStorageListenerAdapter;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyChildrenFilter;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyNameBuilder;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyVisitor;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyVisitor.VisitResult;
import de.metas.handlingunits.model.I_M_HU;

/* package */abstract class AbstractHUKey extends TerminalKey implements IHUKey
{
	private final IHUKeyFactory keyFactory;

	private IHUKey parent = null;
	private boolean readonly = false;
	private final HUKeyChildrenList children = new HUKeyChildrenList(this);
	private IHUKeyChildrenFilter childrenFilter = null;
	/**
	 * Temporary list with {@link IHUKey}s which are about to be removed.
	 * It's used to avoid infinite recursion between {@link #setParent(IHUKey)} and {@link #removeChild(IHUKey)}.
	 */
	private final transient IdentityHashSet<IHUKey> _childrenToRemove = new IdentityHashSet<>();

	private final Map<String, Object> properties = new HashMap<String, Object>();

	private IHUKeyNameBuilder _nameBuilder;

	/** Listen of child's Name property and update this key's name when child was changed. */
	private final PropertyChangeListener childNameListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			final IHUKeyNameBuilder nameBuilder = getHUKeyNameBuilder();

			// Notify name builder that an attribute value was changed
			// and let it reset only those HTML parts which are affected by this attribute
			final IHUKey childKey = (IHUKey)evt.getSource();
			nameBuilder.notifyChildChanged(childKey);

			final boolean reset = false;
			final boolean buildNow = false;
			updateName(reset, buildNow);
		}

		// @formatter:off
		@Override public String toString() { return "AbstractHUKey[<anonymous PropertyChangeListener>]"; };
		// @formatter:on
	};

	/**
	 * Listens on {@link IAttributeStorage} events and
	 * <ul>
	 * <li>notifies the name builder that some attribute value has been changed and the displayed name shall be updated.
	 * <li>mark current display name as stale (the UI will react and it will reload it)
	 * </ul>
	 */
	private final IAttributeStorageListener attributeStorageListener = new AttributeStorageListenerAdapter()
	{
		@Override
		public void onAttributeValueChanged(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue, final Object valueOld)
		{
			final IHUKeyNameBuilder nameBuilder = getHUKeyNameBuilder();

			// Notify name builder that an attribute value was changed
			// and let it reset only those HTML parts which are affected by this attribute
			nameBuilder.notifyAttributeValueChanged(attributeValue);

			// Mark current name as stale. The UI shall react and shall reload the display name.
			final boolean reset = false;
			final boolean buildNow = false;
			updateName(reset, buildNow);
		}

		// @formatter:off
		@Override public String toString() { return "AbstractHUKey[<anonymous AttributeStorageListenerAdapter>]"; };
		// @formatter:on
	};

	public AbstractHUKey(final IHUKeyFactory keyFactory)
	{
		super(keyFactory.getTerminalContext());

		Check.assumeNotNull(keyFactory, "keyFactory not null");
		this.keyFactory = keyFactory;
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		super.dispose();

		_nameBuilder = NullHUKeyNameBuilder.instance;

		//
		// Clear properties
		properties.clear();

		//
		// Unlink from parent, but just unset the reference. setParent(null) causes a lot of listeners etc being fired and properties being update that we don't need.
		// this.parent is also an IHUKey (i.e. IDisposable), so be can assume that it will be disposed too, and we don't have to try to ensure it from here.
		// setParent(null);
		this.parent = null;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + getValue() + "]";
	}

	@Override
	public final IHUKeyFactory getKeyFactory()
	{
		return keyFactory;
	}

	@Override
	public final ITerminalFactory getTerminalFactory()
	{
		return getTerminalContext().getTerminalFactory(); // always use the factory's terminal context
	}

	private final IHUKeyNameBuilder getHUKeyNameBuilder()
	{
		if (_nameBuilder == null)
		{
			_nameBuilder = createHUKeyNameBuilder();
			Check.assumeNotNull(_nameBuilder, "nameBuilder not null");
		}
		return _nameBuilder;
	}

	/**
	 * Creates {@link IHUKeyNameBuilder} to be used when building HU Key's name/label
	 *
	 * @return name builder; null is not accepted
	 */
	protected abstract IHUKeyNameBuilder createHUKeyNameBuilder();

	@Override
	public final Object getName()
	{
		//
		// First make sure we have a fresh name (because this method is used by UI to display HU Key's button name)
		final String name = updateName(false, true); // reset=false, buildNow if needed

		//
		// Return last built name
		return name;
	}

	@Override
	public String getTableName()
	{
		return I_M_HU.Table_Name;
	}

	@Override
	public final void updateName()
	{
		final boolean reset = true; // reset => fully rebuild our name // backward compatibility
		final boolean buildNow = false; // not necessary to build it now
		updateName(reset, buildNow);
	}

	/**
	 *
	 * @param reset true if we shall reset current builder, so a new build is required
	 * @param buildNow if true the name is rebuild if staled, else the name will be rebuild only if there is a listener on it
	 * @return current name (new one if updated, old one if not updated)
	 */
	private final String updateName(final boolean reset, final boolean buildNow)
	{
		final IHUKeyNameBuilder nameBuilder = getHUKeyNameBuilder();

		//
		// If asked to recompute, we are reseting the name builder
		if (reset)
		{
			nameBuilder.reset();
		}

		//
		// We rebuild HUKey's Name only if "nameBuilder" is staled, else makes no sense
		if (nameBuilder.isStale())
		{
			//
			// We are actually rebuilding now it now only if:
			// * we were asked explicitly for this
			// * or if there is a listener on "Name" property, because else there is no point (nobody will use this information anyway)
			if (buildNow || listeners.hasListeners(ITerminalKey.PROPERTY_Name))
			{
				final String nameOld = nameOldFired;
				final String nameNew = nameBuilder.build();
				listeners.firePropertyChange(ITerminalKey.PROPERTY_Name, nameOld, nameNew);
				nameOldFired = nameNew;
			}
		}

		return nameBuilder.getLastBuiltName();
	}

	private String nameOldFired = null;

	@Override
	public final void addChild(final IHUKey child)
	{
		addChildren(Collections.singleton(child));
	}

	/**
	 * Add given child.
	 *
	 * NOTE: this is not firing any events, not calling {@link #updateName()}, it's just adding the child
	 *
	 * @param child
	 * @return true if child was really added
	 */
	private final boolean addChild0(final IHUKey child)
	{
		Check.assumeNotNull(child, "child not null");

		// Avoid recursion
		if (Util.same(this, child))
		{
			throw new IllegalArgumentException("child == this");
		}

		// already added
		if (hasChild(child))
		{
			return false;
		}

		// Make sure we can add this child
		assertCanAddChild(child);

		// Actually add this child and set it's parent
		children.add(child);
		child.setParent(this);

		// Add listener to this child
		child.addListener(PROPERTY_Name, childNameListener);

		// NOTE: don't fire any event; this method is for internal use
		// Firing events is the responsibility of caller

		return true;
	}

	/**
	 * Makes sure we can add this child
	 *
	 * @param child
	 */
	protected void assertCanAddChild(final IHUKey child)
	{
		// nothing at this level
	}

	@Override
	public final List<IHUKey> addChildren(final Collection<IHUKey> children)
	{
		if (children == null || children.isEmpty())
		{
			return Collections.emptyList();
		}

		final Collection<IHUKey> processedchildren = preprocessChildrenBeforeAdding(children);

		final IHUKeyNameBuilder nameBuilder = getHUKeyNameBuilder();

		boolean added = false;
		final List<IHUKey> childrenAdded = new ArrayList<>();
		for (final IHUKey child : processedchildren)
		{
			final boolean childAdded = addChild0(child);
			if (childAdded)
			{
				nameBuilder.notifyChildChanged(child);
				childrenAdded.add(child);
				added = true;
			}
		}

		// If at least one child was added, let others know something changed
		if (added)
		{
			fireChildrenChanged();
		}

		return childrenAdded;
	}

	private final void fireChildrenChanged()
	{
		updateName(false, false); // reset=false, buildNow=false

		// Notify listeners that our children structure changed
		listeners.firePropertyChange(ACTION_ChildrenChanged, false, true);
	}

	/**
	 * Do something with the children before adding them, and return the result.
	 *
	 * @param children
	 * @return the children which will actually be added to the model.
	 *         <p>
	 *         Note to implementor: If the method actually does anything such as sorting, the returned collection should be a different one, because we can't assume that the given collection is
	 *         mutable.
	 */
	protected abstract Collection<IHUKey> preprocessChildrenBeforeAdding(final Collection<IHUKey> children);

	@Override
	public final void removeChild(final IHUKey child)
	{
		if (child == null)
		{
			return; // false;
		}

		//
		// Add child to children to be removed list (to avoid recursion)
		// If already added, do nothing here because it's a recursive call.
		if (!_childrenToRemove.add(child))
		{
			return; // false;
		}

		try
		{
			children.remove(child);
			child.removeListener(PROPERTY_Name, childNameListener);
			child.setParent(null);
			//
			// NOTE: when a child is removed it most of the cases the Key Label shall be updated because the Label can be based on what child it has
			final IHUKeyNameBuilder nameBuilder = getHUKeyNameBuilder();
			nameBuilder.notifyChildChanged(child);
			updateName(false, false); // reset=false, buildNow=false

			// Notify parent if necessary that this grouping key is no longer necessary
			if (isGrouping())
			{
				final List<IHUKey> currentKeyChildren = new ArrayList<>(getChildren()); // copy children in new list
				if (currentKeyChildren.size() > 1)
				{
					// this grouping key still has more than one children.
					return;
				}

				// this grouping key only has one child left. move the child upwards to this grouping key's parent
				final IHUKey groupParent = getParent(); // note that during disposal, the parent might already be disposed and its reference be set to null
				if (groupParent != null)
				{
					for (final IHUKey groupChild : currentKeyChildren)
					{
						groupParent.addChild(groupChild);
					}
				}
				setParent(null);
				fireGroupingRemoved();
			}
			fireChildrenChanged();
		}
		finally
		{
			_childrenToRemove.remove(child);
		}
	}

	/**
	 * Notify parent that this grouping key is no longer necessary.
	 */
	private void fireGroupingRemoved()
	{
		listeners.firePropertyChange(ACTION_GroupingRemoved, false, true); // force always update
	}

	@Override
	public final List<IHUKey> getChildren()
	{
		return children.getChildren();
	}

	@Override
	public final List<IHUKey> getChildrenNoLoad()
	{
		return children.getChildrenNoLoad();
	}

	/**
	 * Retrieve/load children.
	 *
	 * IMPORTANT:
	 * <ul>
	 * <li>This method shall not modify the state of this object but it shall just retrieve the children and return them.
	 * <li>DON'T use caching at this level
	 * </ul>
	 *
	 * @return loaded children.
	 */
	protected List<IHUKey> retrieveChildren()
	{
		return Collections.emptyList();
	}

	/**
	 * Retrieve/Load children and then add common listeners to them (like they were added).
	 *
	 * @return loaded children
	 */
	protected final List<IHUKey> retrieveChildrenAndAddListeners()
	{
		final List<IHUKey> children = retrieveChildren();
		if (children == null || children.isEmpty())
		{
			return children;
		}
		return children;
	}

	@Override
	public final boolean hasChildren()
	{
		return !children.isEmpty();
	}

	@Override
	public final boolean hasChild(final IHUKey child)
	{
		return children.contains(child);
	}

	@Override
	public final boolean hasAnyChildOf(Collection<? extends IHUKey> children)
	{
		return this.children.containsAny(children);
	}

	@Override
	public final IHUKey getParent()
	{
		return parent;
	}

	@Override
	public final void setParent(final IHUKey parent)
	{
		// Avoid recursion
		if (Util.same(parent, this))
		{
			throw new IllegalArgumentException("parent == this");
		}

		final IHUKey parentOld = getParent();

		// Parent did not changed
		if (Util.same(parentOld, parent))
		{
			return;
		}

		// Make sure our parent has this node already
		// NOTE: user shall not call this method directly
		//
		// 07914: Allow setting directly; TODO see how this affects the API - this is a workaround!
		//
		// if (parent != null && !parent.hasChild(this))
		// {
		// throw new IllegalStateException("Parent does not have this node. Please use parent.addChild method instead");
		// }

		// Remove this node from old parent
		if (parentOld != null)
		{
			parentOld.removeChild(this);
		}

		this.parent = parent;
	}

	@Override
	public IAttributeStorage getAttributeSet()
	{
		assertNotDisposed();

		final IAttributeStorageFactory attributeStorageFactory = getKeyFactory().getAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(this);

		//
		// Make sure listener is added
		// NOTE: we assume that attributeStorage's listener makes sure it is not added twice.
		attributeStorage.addListener(attributeStorageListener);

		return attributeStorage;
	}

	@Override
	public final IWeightable getWeightOrNull()
	{
		return getKeyFactory().getWeightOrNull(this);
	}

	@Override
	public abstract boolean isGrouping();

	@Override
	public abstract boolean isVirtualPI();

	@Override
	public final void setProperty(final String propertyName, final Object value)
	{
		properties.put(propertyName, value);
	}

	@Override
	public final <T> T getProperty(final String propertyName)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)properties.get(propertyName);

		return value;
	}

	@Override
	public final IHUKey getRoot()
	{
		IHUKey parent = getParent();
		if (parent == null)
		{
			throw new AdempiereException("Key " + this + " has no root");
		}

		while (parent != null && parent.getParent() != null)
		{
			parent = parent.getParent();
		}

		return parent;
	}

	@Override
	public final IHUKeyVisitor.VisitResult iterate(final IHUKeyVisitor visitor)
	{
		//
		// Before visit
		final VisitResult beforeVisitResult = visitor.beforeVisit(this);
		boolean skipChildren = false;
		switch (beforeVisitResult)
		{
			case CONTINUE:
				break;
			case SKIP_DOWNSTREAM:
				skipChildren = true;
				break;
			case STOP:
				return VisitResult.STOP;
			default:
				throw new IllegalStateException("Result returned by beforeVisit is not supported: " + beforeVisitResult);
		}

		//
		// Visit children
		if (!skipChildren)
		{
			final List<IHUKey> children = new ArrayList<IHUKey>(getChildren());
			for (final IHUKey child : children)
			{
				final VisitResult childIterateResult = child.iterate(visitor);
				switch (childIterateResult)
				{
					case CONTINUE:
						break;
					case STOP:
						return VisitResult.STOP;
					default:
						throw new IllegalStateException("Result returned by child iterate is not supported: " + beforeVisitResult);
				}
			}
		}

		//
		// After visit
		final VisitResult afterVisitResult = visitor.afterVisit(this);
		switch (afterVisitResult)
		{
			case CONTINUE:
				return VisitResult.CONTINUE;
			case STOP:
				return VisitResult.STOP;
			default:
				throw new IllegalStateException("Result returned by afterVisit is not supported: " + beforeVisitResult);
		}
	}

	@Override
	public void notifyKeyPressed()
	{
		// nothing on this level
	}

	@Override
	public void refresh()
	{
		children.dispose(); // reset children, they need to be reloaded

		updateName(true, true); // reset, buildNow

		//
		// Recursivelly refresh all parents allong the chain
		final IHUKey parent = getParent();
		if (parent != null)
		{
			if (isDestroyed())
			{
				parent.removeChild(this);
			}
			parent.refresh();
		}
	}

	@Override
	public boolean isReadonly()
	{
		return readonly;
	}

	@Override
	public void setReadonly(final boolean readonly)
	{
		this.readonly = readonly;
	}

	@Override
	public void setChildrenFilter(IHUKeyChildrenFilter childrenFilter)
	{
		if (this.childrenFilter == childrenFilter)
		{
			return;
		}

		final IHUKeyChildrenFilter childrenFilterOld = this.childrenFilter;
		this.childrenFilter = childrenFilter;

		listeners.firePropertyChange(ACTION_ChildrenFilterChanged, childrenFilterOld, this.childrenFilter);
	}

	@Override
	public IHUKeyChildrenFilter getChildrenFilter()
	{
		return childrenFilter;
	}
}
