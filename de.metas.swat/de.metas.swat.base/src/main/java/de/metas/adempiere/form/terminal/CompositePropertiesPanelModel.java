package de.metas.adempiere.form.terminal;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.SwingUtilities;

import org.compiere.util.NamePair;
import org.slf4j.Logger;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.form.IInputMethod;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.field.constraint.ITerminalFieldConstraint;
import de.metas.logging.LogManager;

/**
 * Class used to aggregate together {@link IPropertiesPanelModel}s and behave like on single {@link IPropertiesPanelModel}.
 * <p>
 * Note that there is just one child model per property name.<br>
 * E.g. when the method {@link #setPropertyValue(String, Object)} is called, then there is one child {@link IPropertiesPanel} instance for the given <code>propertyName</code>.
 *
 * @author tsa
 *
 */
public final class CompositePropertiesPanelModel extends AbstractPropertiesPanelModel
{
	private static final transient Logger logger = LogManager.getLogger(CompositePropertiesPanelModel.class);

	/** Current children (indexed) */
	private IndexedChildren _children = IndexedChildren.NULL;
	private final ReentrantLock _childrenLock = new ReentrantLock();

	//
	// Config options
	private boolean saveChildrenOnRemove = false;
	private boolean disposeChildrenOnRemove = false;

	/**
	 * Listen child models and forward all events to listeners registered at this level
	 */
	private final PropertyChangeListener childModelsListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			final String propertyName = evt.getPropertyName();
			final Object valueOld = evt.getOldValue();
			final Object valueNew = evt.getNewValue();

			// In case one of the child content has changed, we also need to rebuild our indexes.
			if (PROPERTY_ContentChanged.equals(propertyName))
			{
				rebuildIndexedChildren();
			}

			firePropertyChanged(propertyName, valueOld, valueNew);
		}

		// @formatter:off
		@Override public String toString() { return "CompositePropertiesPanelModel[ childModelsListener ]";};
		// @formatter:on
	};

	/**
	 * Note: the super-constructor registers itself as disposable component.
	 *
	 * @param terminalContext
	 */
	public CompositePropertiesPanelModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);
	}

	/**
	 * Sets if {@link IPropertiesPanelModel#commitEdit()} shall be called automatically when a child is removed from this composite.
	 *
	 * NOTE: child models won't be commited when disposing.
	 *
	 * @param saveChildrenOnRemove
	 */
	public void setSaveChildrenOnRemove(final boolean saveChildrenOnRemove)
	{
		this.saveChildrenOnRemove = saveChildrenOnRemove;
	}

	/**
	 * Sets if we shall automatically dispose the child model when it's removed from this composite.
	 *
	 * @param disposeChildrenOnRemove
	 */
	public void setDisposeChildrenOnRemove(final boolean disposeChildrenOnRemove)
	{
		this.disposeChildrenOnRemove = disposeChildrenOnRemove;
	}

	/**
	 * Replace all existing child models (if any) with given ones instead.
	 *
	 * @param childModels
	 */
	public void setChildModels(final Supplier<Collection<IPropertiesPanelModel>> childModelsSupplier)
	{
		_childrenLock.lock();
		try
		{
			//
			// Remove old models
			removeAllChildModelsNoFire();

			final IndexedChildren childrenNew = IndexedChildren.of(childModelsSupplier == null ? null : childModelsSupplier.get());

			// Prepare child models to be added to our composite
			int counter = 0;
			for (final IPropertiesPanelModel childModel : childrenNew.getChildModels())
			{
				logger.debug("this-ID={}, isEventDispatchThread={}; within setChildModels() with childModel #{} = {}; this={}",
						System.identityHashCode(this), SwingUtilities.isEventDispatchThread(), counter++, childModel, this);
				onBeforeChildAdd(childModel);
			}

			this._children = childrenNew;
		}
		finally
		{
			_childrenLock.unlock();
		}

		// Notify listeners that our model content has fully changed
		// NOTE: we do this outside of the lock because the listeners would access this object's content.
		fireContentChanged();
	}

	/**
	 * Remove all underlying child models, but don't fire any event.
	 */
	private void removeAllChildModelsNoFire()
	{
		_childrenLock.lock();
		try
		{
			final IndexedChildren children = _children;
			for (final IPropertiesPanelModel childModel : children.getChildModels())
			{
				onBeforeChildRemove(childModel);
			}

			_children = IndexedChildren.NULL;
		}
		finally
		{
			_childrenLock.unlock();
		}
	}

	/**
	 * Rebuids current children index.
	 * To be used in case the content of one of the children changed.
	 */
	private void rebuildIndexedChildren()
	{
		_childrenLock.lock();
		try
		{
			_children = _children.rebuild();
		}
		finally
		{
			_childrenLock.unlock();
		}
	}

	private final void onBeforeChildAdd(final IPropertiesPanelModel childModel)
	{
		childModel.removePropertyChangeListener(childModelsListener);
		childModel.addPropertyChangeListener(childModelsListener);
	}

	private IndexedChildren getChildren()
	{
		_childrenLock.lock();
		try
		{
			return _children;
		}
		finally
		{
			_childrenLock.unlock();
		}
	}

	/**
	 * Called before a child is removed.
	 *
	 * It is removing the listeners, commit the changes (if asked), dispose the child (if asked).
	 *
	 * Please keep in mind that after calling this method it might be that the child is disposed.
	 *
	 * @param childModel
	 */
	private final void onBeforeChildRemove(final IPropertiesPanelModel childModel)
	{
		// First of all we are removing the listner
		childModel.removePropertyChangeListener(childModelsListener);

		//
		// Commit changes on child model.
		// NOTE: we are not committing the models automatically in case this component is disposed or disposing
		// because it could lead to other problems
		// (e.g. this removal runs in Finalizer thread and could produce delayed saves in DB of old/staled values).
		// Developer needs to commit values explicitly.
		if (!isDisposed() && saveChildrenOnRemove)
		{
			childModel.commitEdit();
		}

		//
		// Dispose the child model if we were asked to
		if (disposeChildrenOnRemove)
		{
			DisposableHelper.disposeAllObjects(childModel);
		}
	}

	private final Collection<IPropertiesPanelModel> getChildModels()
	{
		return getChildren().getChildModels();
	}

	@Override
	public final List<String> getPropertyNames()
	{
		return getChildren().getPropertyNames();
	}

	private IPropertiesPanelModel getChildModel(final String propertyName)
	{
		return getChildren().getChildModel(propertyName);
	}

	@Override
	public void dispose()
	{
		// Invoke super first to also make sure the isDisposed() flag is set.
		super.dispose();

		removeAllChildModelsNoFire();
	}

	@Override
	public String getPropertyDisplayName(String propertyName)
	{
		return getChildModel(propertyName).getPropertyDisplayName(propertyName);
	}

	@Override
	public int getDisplayType(String propertyName)
	{
		return getChildModel(propertyName).getDisplayType(propertyName);
	}

	@Override
	public List<IInputMethod<?>> getAdditionalInputMethods(String propertyName)
	{
		return getChildModel(propertyName).getAdditionalInputMethods(propertyName);
	}

	@Override
	public Object getPropertyValue(String propertyName)
	{
		return getChildModel(propertyName).getPropertyValue(propertyName);
	}

	@Override
	public List<? extends NamePair> getPropertyAvailableValues(String propertyName)
	{
		return getChildModel(propertyName).getPropertyAvailableValues(propertyName);
	}

	@Override
	public ITerminalLookup getPropertyLookup(String propertyName)
	{
		return getChildModel(propertyName).getPropertyLookup(propertyName);
	}

	@Override
	public void setPropertyValue(String propertyName, Object value)
	{
		getChildModel(propertyName).setPropertyValue(propertyName, value);
	}

	@Override
	public boolean isEditable(String propertyName)
	{
		return getChildModel(propertyName).isEditable(propertyName);
	}

	@Override
	public ITerminalFieldConstraint<Object> getConstraint(String propertyName)
	{
		return getChildModel(propertyName).getConstraint(propertyName);
	}

	@Override
	public void commitEdit()
	{
		for (final IPropertiesPanelModel childModel : getChildModels())
		{
			childModel.commitEdit();
		}
	}

	/**
	 * Contains a collection of {@link IPropertiesPanelModel} children which is indexed by PropertyName.
	 *
	 * @author tsa
	 *
	 */
	private static final class IndexedChildren
	{
		public static final IndexedChildren of(final Collection<IPropertiesPanelModel> childModelsToAdd)
		{
			if (childModelsToAdd != null && !childModelsToAdd.isEmpty())
			{
				return new IndexedChildren(childModelsToAdd);
			}
			else
			{
				return NULL;
			}
		}

		public static final transient IndexedChildren NULL = new IndexedChildren();

		private final List<IPropertiesPanelModel> _childModels;
		private final List<String> _propertyNames;
		private final Map<String, IPropertiesPanelModel> _propertyName2model;

		/** Null constructor */
		private IndexedChildren()
		{
			super();
			this._childModels = ImmutableList.of();
			this._propertyNames = ImmutableList.of();
			this._propertyName2model = ImmutableMap.of();
		}

		private IndexedChildren(final Collection<IPropertiesPanelModel> childModelsToAdd)
		{
			super();

			// Build the propertyName to model map
			final List<IPropertiesPanelModel> childModels = ImmutableList.copyOf(childModelsToAdd);
			final LinkedHashMap<String, IPropertiesPanelModel> propertyName2model = new LinkedHashMap<>(childModels.size());

			for (final IPropertiesPanelModel childModel : childModels)
			{
				for (final String propertyName : childModel.getPropertyNames())
				{
					if (propertyName2model.containsKey(propertyName))
					{
						throw new TerminalException("Cannot add PropertyName for model " + childModel + " because it was already registered for model " + propertyName2model.get(propertyName));
					}
					propertyName2model.put(propertyName, childModel);
				}
			}

			//
			// Actually set the new values
			this._childModels = childModels;
			this._propertyName2model = ImmutableMap.copyOf(propertyName2model);
			this._propertyNames = ImmutableList.copyOf(propertyName2model.keySet());
		}

		public final List<String> getPropertyNames()
		{
			return _propertyNames;
		}

		public final Collection<IPropertiesPanelModel> getChildModels()
		{
			return _childModels;
		}

		public final IPropertiesPanelModel getChildModel(final String propertyName)
		{
			final IPropertiesPanelModel childModel = _propertyName2model.get(propertyName);
			if (childModel == null)
			{
				throw new TerminalException("No child model was found for property " + propertyName);
			}
			return childModel;
		}

		/**
		 * @return a new instance having all children re-indexed
		 */
		public final IndexedChildren rebuild()
		{
			if (this == NULL)
			{
				return NULL;
			}
			return new IndexedChildren(this._childModels);
		}

	}

	@Override
	public String toString()
	{
		return "CompositePropertiesPanelModel [_children=" + _children + ", _childrenLock=" + _childrenLock + ", saveChildrenOnRemove=" + saveChildrenOnRemove + ", disposeChildrenOnRemove=" + disposeChildrenOnRemove + ", childModelsListener=" + childModelsListener + "]";
	}
}
