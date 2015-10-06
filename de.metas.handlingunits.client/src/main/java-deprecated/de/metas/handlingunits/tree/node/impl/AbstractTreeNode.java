package de.metas.handlingunits.tree.node.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;

import de.metas.handlingunits.tree.node.ITreeNode;

public abstract class AbstractTreeNode<T extends ITreeNode<T>> implements ITreeNode<T>
{
	private T parent;
	private final List<T> children = new ArrayList<T>();

	public AbstractTreeNode()
	{
		super();
	}

	@Override
	public List<T> getChildren()
	{
		return this.children;
	}

	@Override
	public T getParent()
	{
		return this.parent;
	}

	@Override
	public void setParent(final T parent)
	{
		this.parent = parent;
	}

	/**
	 * Gets parent casted to <code>parentClass</code>. If parent is null or parent does not implement given class, null is returned.
	 * 
	 * @param parentClass
	 * @return parent (casted) or null
	 */
	public <CT> CT getParentOrNull(final Class<CT> parentClass)
	{
		final T parent = getParent();
		if (parent == null)
		{
			return null;
		}

		Check.assumeInstanceOf(parent, parentClass, "parent");
		@SuppressWarnings("unchecked")
		final CT parentCasted = (CT)parent;
		return parentCasted;
	}

	@Override
	public void insertChild(final T child, final int index)
	{
		int localIndex = index;
		if (this.children.contains(child))
		{
			this.children.remove(child);
			localIndex--;
		}

		this.children.add(localIndex, child);

		if (child.getParent() != this)
		{
			@SuppressWarnings("unchecked")
			final T parent = (T)this; // parent will always be of T type
			child.setParent(parent);
		}
	}

	@Override
	public void addChild(final T child)
	{
		if (this.children.contains(child))
		{
			this.children.remove(child);
		}

		this.children.add(child);

		if (child.getParent() != this)
		{
			@SuppressWarnings("unchecked")
			final T parent = (T)this; // parent will always be of T type
			child.setParent(parent);
		}
	}

	@Override
	public int getChildIndex(final T node)
	{
		return this.children.indexOf(node);
	}

	@Override
	public T getChildAt(final int index)
	{
		return this.children.get(index);
	}

	@Override
	public boolean removeChild(final T child)
	{
		if (!this.children.remove(child))
		{
			return false;
		}

		child.setParent(null);
		return true;
	}
}
