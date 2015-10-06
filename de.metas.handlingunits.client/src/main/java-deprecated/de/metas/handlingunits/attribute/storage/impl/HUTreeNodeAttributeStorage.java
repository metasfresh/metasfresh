package de.metas.handlingunits.attribute.storage.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.util.collections.FilterUtils;
import org.adempiere.util.collections.Predicate;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageAware;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUTreeNode;

/**
 * Implementation of {@link IAttributeStorage} which retrieves the attribute values from given {@link I_M_HU}
 * 
 * @author tsa
 */
public class HUTreeNodeAttributeStorage extends AbstractHUAttributeStorage
{
	private final HUTreeNode huTreeNode;

	/**
	 * @see #HUTreeNodeAttributeStorage(I_M_HU, boolean)
	 * 
	 * @param hu
	 */
	public HUTreeNodeAttributeStorage(final HUTreeNode huTreeNode)
	{
		this(huTreeNode, false);
	}

	/**
	 * @param huTreeNode
	 * @param usePlainValues
	 */
	public HUTreeNodeAttributeStorage(final HUTreeNode huTreeNode, final boolean usePlainValues)
	{
		super(huTreeNode.getM_HU(), usePlainValues);

		this.huTreeNode = huTreeNode;
	}

	@Override
	public IAttributeStorage getParentAttributeStorage()
	{
		final IHUTreeNode parentNode = huTreeNode.getParent();
		if (!(parentNode instanceof IAttributeStorageAware))
		{
			return NullAttributeStorage.instance;
		}
		return ((IAttributeStorageAware)parentNode).getAttributeStorage();
	}

	@Override
	public List<IAttributeStorage> getChildrenAttributeStorages()
	{
		final List<IAttributeStorage> childrenAttributeSetStorages = new ArrayList<IAttributeStorage>();

		final List<IHUTreeNode> itemTreeNodes = huTreeNode.getChildren(); // HUItemTreeNode
		for (final IHUTreeNode itemTreeNode : itemTreeNodes)
		{
			final List<IHUTreeNode> childrenHUTreeNodes = itemTreeNode.getChildren();
			for (final IHUTreeNode childHUTreeNode : childrenHUTreeNodes) // HUTreeNode
			{
				final HUTreeNode childTreeNode = (HUTreeNode)childHUTreeNode;
				final IAttributeStorage childAttributeSetStorage = childTreeNode.getAttributeStorage();

				childrenAttributeSetStorages.add(childAttributeSetStorage);
			}
		}

		return childrenAttributeSetStorages;
	}

	@Override
	public List<IAttributeStorage> getSameLevelAttributeStorages()
	{
		final HUTreeNodeAttributeStorage parentStorage = (HUTreeNodeAttributeStorage)getParentAttributeStorage();
		if (NullAttributeStorage.instance.equals(parentStorage))
		{
			return Collections.emptyList();
		}

		final IAttributeStorage currentStorage = this;
		final Predicate<IAttributeStorage> isAllExceptThisStorage = new Predicate<IAttributeStorage>()
		{
			@Override
			public boolean evaluate(final IAttributeStorage storage)
			{
				return !currentStorage.equals(storage);
			}
		};

		final List<IAttributeStorage> sameLevelASCandidates = parentStorage.getChildrenAttributeStorages();
		return FilterUtils.filter(sameLevelASCandidates, isAllExceptThisStorage);
	}
}
