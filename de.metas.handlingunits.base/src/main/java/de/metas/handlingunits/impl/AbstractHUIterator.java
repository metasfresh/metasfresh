package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUIterator;
import de.metas.handlingunits.IHUIteratorListener;
import de.metas.handlingunits.IHUIteratorListener.Result;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.NullHUIteratorListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;

public abstract class AbstractHUIterator implements IHUIterator
{
	//
	// Services
	protected final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	protected final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	//
	// Context
	private Properties ctx = null;
	private IHUContext _huContext = null;
	private Date _date = null;
	private IHUStorageFactory _storageFactory = null;

	//
	// Listeners
	private IHUIteratorListener activeListener = NullHUIteratorListener.instance;
	private IHUIteratorListener listener = NullHUIteratorListener.instance;

	//
	// Node Iterators
	private final Map<Class<?>, AbstractNodeIterator<?>> nodeIterators = new HashMap<Class<?>, AbstractNodeIterator<?>>();

	//
	// Status
	protected I_M_HU currentHU = null;
	protected I_M_HU_Item currentHUItem = null;
	protected IHUItemStorage currentHUItemStorage = null;

	protected static enum HUIteratorStatus
	{
		NeverStarted, Running, Stopped, Finished,
	}

	private HUIteratorStatus _status = HUIteratorStatus.NeverStarted;

	/**
	 * Current Depth
	 */
	private int depth = -1;

	/**
	 * Maximum Depth to navigate. When this depth is reached, we are not navigating downstream.
	 */
	private int depthMax = -1;

	public AbstractHUIterator()
	{
		super();
	}

	@Override
	public final void setListener(final IHUIteratorListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		this.listener = listener;
	}

	protected IHUIteratorListener getActiveListener()
	{
		return activeListener;
	}

	@Override
	public final void setHUContext(final IHUContext huContext)
	{
		_huContext = huContext;
	}

	@Override
	public IHUContext getHUContext()
	{
		if (_huContext == null)
		{
			final Properties ctxToUse = Env.coalesce(ctx);
			final IMutableHUContext huContextNew = handlingUnitsBL.createMutableHUContext(ctxToUse);

			Check.assumeNotNull(_date, "date not null");
			huContextNew.setDate(_date);

			if (_storageFactory != null)
			{
				huContextNew.setHUStorageFactory(_storageFactory);
			}
			_huContext = huContextNew;
		}
		return _huContext;
	}

	@Override
	public Properties getCtx()
	{
		Check.assumeNotNull(ctx, "ctx not null");
		return ctx;
	}

	@Override
	public void setCtx(final Properties ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public final void setStorageFactory(final IHUStorageFactory storageFactory)
	{
		_storageFactory = storageFactory;
	}

	@Override
	public final IHUStorageFactory getStorageFactoryToUse()
	{
		return getHUContext().getHUStorageFactory();
	}

	@Override
	public final void setDate(final Date date)
	{
		_date = (Date)date.clone();
	}

	@Override
	public final Date getDate()
	{
		final Date date = getHUContext().getDate();
		Check.assumeNotNull(date, "date not null");
		return date;
	}

	private final Set<Object> seenObjects = new HashSet<Object>();

	/**
	 * Checks if given node was already visited. If node was not already visited, add it to internal visited queue
	 *
	 * @param handler node handler
	 * @param node
	 * @return true if node was already visited
	 */
	private final <T> boolean checkSeen(final AbstractNodeIterator<T> handler, final T node)
	{
		Check.assumeNotNull(handler, "handler not null");
		Check.assumeNotNull(node, "node not null");

		final ArrayKey key = handler.getKey(node);
		final boolean added = seenObjects.add(key);

		if (added)
		{
			// node not visited yet
			return false;
		}
		else
		{
			// node already visited
			return true;
		}
	}

	@Override
	public final I_M_HU getCurrentHU()
	{
		return currentHU;
	}

	@Override
	public final I_M_HU_Item getCurrentHUItem()
	{
		return currentHUItem;
	}

	@Override
	public final IHUItemStorage getCurrentHUItemStorage()
	{
		return currentHUItemStorage;
	}

	public final boolean isRunning()
	{
		return _status == HUIteratorStatus.Running;
	}

	protected HUIteratorStatus getStatus()
	{
		return _status;
	}

	/**
	 * Sets the iterator's status and also activates/deactivates the internal {@link IHUIteratorListener} which was set by {@link #setListener(IHUIteratorListener)}.
	 *
	 * @param statusNew
	 */
	protected final void setStatus(final HUIteratorStatus statusNew)
	{
		Check.assumeNotNull(statusNew, "statusNew not null");
		if (_status == statusNew)
		{
			return;
		}

		switch (statusNew)
		{
			//
			// Change status to Running
			case Running:
				Check.errorIf(listener == null, "listener member of {} may not be null", this);
				_status = HUIteratorStatus.Running;
				activeListener = listener;
				activeListener.setHUIterator(this);
				break;
			//
			// Change status to Stopped/Finished
			case Stopped:
			case Finished:
				_status = HUIteratorStatus.Stopped;

				// Make sure that after we stop, the listener is not called anymore
				activeListener = NullHUIteratorListener.instance;
				break;
			//
			// Invalid states:
			case NeverStarted:
			default:
				throw new IllegalArgumentException("Invalid status transition: " + _status + "->" + statusNew);
		}

		_status = statusNew;
	}

	@Override
	public final int getDepth()
	{
		return depth;
	}

	protected final <T> AbstractNodeIterator<T> getNodeIteratorOrNull(final Class<T> nodeType)
	{
		@SuppressWarnings("unchecked")
		final AbstractNodeIterator<T> nodeIterator = (AbstractNodeIterator<T>)nodeIterators.get(nodeType);
		return nodeIterator;
	}

	protected final <T> AbstractNodeIterator<T> getNodeIterator(final Class<T> nodeType)
	{
		final AbstractNodeIterator<T> nodeIterator = getNodeIteratorOrNull(nodeType);
		if (nodeIterator == null)
		{
			throw new IllegalArgumentException("No Node Iterator found for type: " + nodeType);
		}

		return nodeIterator;
	}

	protected final <T> void registerNodeIterator(final Class<T> nodeType, final AbstractNodeIterator<T> nodeIterator)
	{
		Check.assumeNotNull(nodeType, "nodeType not null");
		Check.assumeNotNull(nodeIterator, "nodeIterator not null");

		nodeIterators.put(nodeType, nodeIterator);
	}

	protected final <T> void unregisterNodeIterator(final Class<T> nodeType)
	{
		Check.assumeNotNull(nodeType, "nodeType not null");
		if (nodeIterators.containsKey(nodeType))
		{
			nodeIterators.remove(nodeType);
		}
	}

	public static final AbstractNodeIterator<?> NULL_NODE_ITERATOR = null;

	protected abstract class AbstractNodeIterator<T>
	{
		public final void iterateNode(final Object nodeObj)
		{
			@SuppressWarnings("unchecked")
			final T node = (T)nodeObj;
			iterate(node);
		}

		public final void iterate(final T node)
		{
			// Check if we were asked to STOP. If yes, stop right now
			if (!isRunning())
			{
				return;
			}

			//
			// Before iterate
			Check.assumeNotNull(node, "node not null");
			if (checkSeen(this, node))
			{
				// node was already visited. nothing to do
				return;
			}
			setCurrentNode(node);

			depth++;
			final IMutable<T> nodeMutable = new Mutable<T>(node);
			final Result beforeResult = beforeIterate(nodeMutable);

			final T nodeToUse = nodeMutable.getValue();
			if (!Util.same(node, nodeToUse) && checkSeen(this, nodeToUse))
			{
				// node was changed by beforeIterate method and new node was already visited
				return;
			}
			// we are setting the current node again in case it was changed
			setCurrentNode(nodeToUse);

			boolean skipDownstream = false;
			switch (beforeResult)
			{
				case STOP:
					setStatus(HUIteratorStatus.Stopped);
					return;
				// break;
				case SKIP_DOWNSTREAM:
					skipDownstream = true;
					break;
				case CONTINUE:
					// nothing
					break;
				default:
					throw new AdempiereException("Unsupported before result: " + beforeResult);
					// break;
			}
			Check.assumeNotNull(nodeToUse, "Mutable node was set to null but returned result is {}", beforeResult);

			//
			// Iterate downstream
			if (!skipDownstream)
			{
				iterateDownstream(nodeToUse);
			}

			//
			// After iterate
			final Result afterResult = afterIterate(nodeToUse);
			depth--;
			switch (afterResult)
			{
				case STOP:
					setStatus(HUIteratorStatus.Stopped);
					return;
				// break;
				case SKIP_DOWNSTREAM:
					throw new AdempiereException("Unexpected after result: " + afterResult);
					// break;
				case CONTINUE:
					// nothing
					break;
				default:
					throw new AdempiereException("Unsupported after result: " + afterResult);
					// break;
			}
			setCurrentNode(null);
		}

		/**
		 * Iterate downstream nodes of given <code>node</code>
		 *
		 * @param node
		 */
		private final void iterateDownstream(final T node)
		{
			//
			// If we reached maximum allowed depth, don't iterate downstream
			if (depthMax > 0 && depth >= depthMax)
			{
				return;
			}

			//
			// Gets downstream iterator for given node
			// If downstream iterator is null, do nothing, don't go forward.
			final AbstractNodeIterator<?> downstreamNodeIterator = getDownstreamNodeIterator(node);
			if (downstreamNodeIterator == null)
			{
				return;
			}

			//
			// Get downstream nodes
			final List<? extends Object> children = retrieveDownstreamNodes(node);
			if (children == null || children.isEmpty())
			{
				return;
			}

			//
			// Iterate downstream nodes
			for (final Object child : children)
			{
				downstreamNodeIterator.iterateNode(child);

				// Check if we were asked to STOP. If yes, stop right now
				if (!isRunning())
				{
					return;
				}
			}
		}

		/**
		 * Creates a unique key of given node. This key will be used to check if we already navigated this node (avoid cycles).
		 *
		 * @param node
		 * @return key
		 */
		public abstract ArrayKey getKey(T node);

		public abstract void setCurrentNode(T node);

		public abstract Result beforeIterate(IMutable<T> node);

		public abstract Result afterIterate(T node);

		/**
		 * Get iterator used to navigate downstream nodes.
		 *
		 * If returned iterator is <code>null</code>, downstream nodes will not be iterated.
		 *
		 * @param node
		 * @return downstream nodes iterator or <code>null</code>
		 */
		public abstract AbstractNodeIterator<?> getDownstreamNodeIterator(T node);

		/**
		 * Retrieves downstream nodes (i.e. child nodes of given <code>node</code>).
		 *
		 * If {@link #getDownstreamNodeIterator(Object)} returns <code>null</code>, this method won't be called.
		 *
		 * @param node
		 * @return downstream nodes.
		 */
		public abstract List<?> retrieveDownstreamNodes(T node);
	}

	protected class HUNodeIterator extends AbstractNodeIterator<I_M_HU>
	{
		@Override
		public final ArrayKey getKey(final I_M_HU node)
		{
			return Util.mkKey(I_M_HU.Table_Name, node.getM_HU_ID());
		}

		@Override
		public final Result beforeIterate(final IMutable<I_M_HU> node)
		{
			return getActiveListener().beforeHU(node);
		}

		@Override
		public final Result afterIterate(final I_M_HU hu)
		{
			return getActiveListener().afterHU(hu);
		}

		@Override
		public final void setCurrentNode(final I_M_HU node)
		{
			currentHU = node;
		}

		@Override
		public List<?> retrieveDownstreamNodes(final I_M_HU node)
		{
			return new ArrayList<Object>(handlingUnitsDAO.retrieveItems(node));
		}

		@Override
		public AbstractNodeIterator<I_M_HU_Item> getDownstreamNodeIterator(final I_M_HU node)
		{
			return getNodeIteratorOrNull(I_M_HU_Item.class);
		}
	};

	protected class HUItemNodeIterator extends AbstractNodeIterator<I_M_HU_Item>
	{
		@Override
		public final ArrayKey getKey(final I_M_HU_Item node)
		{
			return Util.mkKey(I_M_HU_Item.Table_Name, node.getM_HU_Item_ID());
		}

		@Override
		public final Result beforeIterate(final IMutable<I_M_HU_Item> node)
		{
			return getActiveListener().beforeHUItem(node);
		}

		@Override
		public final Result afterIterate(final I_M_HU_Item node)
		{
			return getActiveListener().afterHUItem(node);
		}

		@Override
		public final void setCurrentNode(final I_M_HU_Item node)
		{
			currentHUItem = node;
		}

		@Override
		public AbstractNodeIterator<?> getDownstreamNodeIterator(final I_M_HU_Item node)
		{

			final String itemType = handlingUnitsBL.getItemType(node);
			switch (itemType)
			{
				case X_M_HU_Item.ITEMTYPE_HandlingUnit:
					return getNodeIteratorOrNull(I_M_HU.class);
					
				case X_M_HU_Item.ITEMTYPE_HUAggregate:
					return getNodeIteratorOrNull(I_M_HU.class); // same as ITEMTYPE_HandlingUnit because in the end it's just a special kind of M_HU

				case X_M_HU_PI_Item.ITEMTYPE_Material:

					// If this item is part of a Virtual HU, then we can navigate through it's Item Storages
					if (handlingUnitsBL.isVirtual(node))
					{
						return getNodeIteratorOrNull(IHUItemStorage.class);
					}
					// If this item is NOT part of a virtual HU it means that under this item we have Virtual HUs
					// so instead of navigating through Item Storages (which is just an aggregation of VHUs storages)
					// better navigate through its VHUs and then on VHU level we will navigate through it's Item Storages
					return getNodeIteratorOrNull(I_M_HU.class);
	
				case X_M_HU_PI_Item.ITEMTYPE_PackingMaterial:
					// nothing to navigate downstream of this node
					return NULL_NODE_ITERATOR;

				default:
					throw new IllegalArgumentException("No downstream iterator for " + node + " (type=" + itemType + ")");
			}
		}

		@Override
		public List<Object> retrieveDownstreamNodes(final I_M_HU_Item node)
		{
			final String itemType = handlingUnitsBL.getItemType(node);
			if (X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(itemType))
			{
				final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(node);
				return new ArrayList<Object>(includedHUs);
			}
			else if (X_M_HU_PI_Item.ITEMTYPE_Material.equals(itemType))
			{
				if (handlingUnitsBL.isVirtual(node))
				{
					final IHUItemStorage itemStorage = getStorageFactoryToUse().getStorage(node);
					return Collections.singletonList((Object)itemStorage);
				}
				else
				{
					// Navigate included Virtual HUs
					final List<I_M_HU> vhus = handlingUnitsDAO.retrieveVirtualHUs(node);
					return new ArrayList<Object>(vhus);
				}
			}
			else
			{
				return Collections.emptyList();
			}
		}
	};

	protected class HUItemStorageNodeIterator extends AbstractNodeIterator<IHUItemStorage>
	{
		@Override
		public final ArrayKey getKey(final IHUItemStorage node)
		{
			return Util.mkKey(IHUItemStorage.class, node.getM_HU_Item().getM_HU_Item_ID());
		}

		@Override
		public final void setCurrentNode(final IHUItemStorage node)
		{
			currentHUItemStorage = node;
		}

		@Override
		public final Result beforeIterate(final IMutable<IHUItemStorage> node)
		{
			return getActiveListener().beforeHUItemStorage(node);
		}

		@Override
		public final Result afterIterate(final IHUItemStorage node)
		{
			return getActiveListener().afterHUItemStorage(node);
		}

		@Override
		public AbstractNodeIterator<?> getDownstreamNodeIterator(final IHUItemStorage node)
		{
			return null;
		}

		@Override
		public List<Object> retrieveDownstreamNodes(final IHUItemStorage node)
		{
			throw new IllegalStateException("Shall not be called because we don't have a downstream node iterator");
		}
	};

	@Override
	public void setDepthMax(final int depthMax)
	{
		this.depthMax = depthMax;
	}

	@Override
	public int getDepthMax()
	{
		return depthMax;
	}
}
