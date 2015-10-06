package de.metas.handlingunits.tree.factory.impl;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import de.metas.handlingunits.api.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.tree.factory.IHUTreeNodeFactory;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.AbstractHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemMITreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemPMTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUTreeNode;

public class HUTreeNodeFactory implements IHUTreeNodeFactory
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Override
	public IHUTreeNode createNodeRecursively(final Object contextProvider, final I_M_HU_PI huPI)
	{
		final I_M_HU hu = handlingUnitsDAO.createDecoupledInstance(contextProvider, huPI);

		final boolean readonly = false;
		return createNodeRecursively(hu, readonly);
	}

	@Override
	public IHUTreeNode createNodeRecursively(final Object contextProvider, final I_M_HU_PI huPI, final I_M_HU_Item parentItem)
	{
		final I_M_HU hu = handlingUnitsDAO.createDecoupledInstance(contextProvider, huPI, parentItem);

		final boolean readonly = false;
		return createNodeRecursively(hu, readonly);
	}

	@Override
	public IHUTreeNode createNodeRecursively(final I_M_HU hu, final boolean readonly)
	{
		final HUTreeNode huTreeNode = createNode(hu);
		huTreeNode.setReadonly(readonly);

		createAndAddChildren(huTreeNode);

		return huTreeNode;
	}

	@Override
	public HUTreeNode createNode(I_M_HU hu)
	{
		final HUTreeNode huTreeNode = new HUTreeNode(hu);
		return huTreeNode;
	}

	private void createAndAddChildren(final HUTreeNode node)
	{
		// create the node items if possible
		final List<I_M_HU_Item> items = handlingUnitsDAO.getCreateHUItemInstances(node.getM_HU_PI_Version(), node.getM_HU(), null);
		for (final I_M_HU_Item item : items)
		{
			final IHUTreeNode child = createNodeRecursively(item, node.isReadonly());
			addChild(node, child);
		}
	}

	@Override
	public void updateFromParent(final IHUTreeNode child)
	{
		if (child == null)
		{
			return;
		}
		
		final IHUTreeNode parent = child.getParent();
		if (parent == null)
		{
			return;
		}
		
		//
		// Propagate settings from parent to child
		child.setReadonly(parent.isReadonly());
	}

	@Override
	public void addChild(final IHUTreeNode parent, final IHUTreeNode child)
	{
		parent.addChild(child);
		updateFromParent(child);
	}

	@Override
	public IHUTreeNode createNodeRecursively(final I_M_HU_Item item, final boolean readonly)
	{
		final boolean recursive = true;
		return createNode(item, recursive, readonly);
	}

	@Override
	public IHUTreeNode createNode(final I_M_HU_Item item)
	{
		final boolean recursive = false;
		final boolean readonly = false;
		return createNode(item, recursive, readonly);
	}

	public IHUTreeNode createNode(final I_M_HU_Item item, final boolean recursive, final boolean readonly)
	{
		final AbstractHUTreeNode node;

		final String itemType = item.getM_HU_PI_Item().getItemType();
		if (X_M_HU_PI_Item.ITEMTYPE_Material.equals(itemType))
		{
			node = new HUItemMITreeNode(item);
			node.setReadonly(readonly);
		}
		else if (X_M_HU_PI_Item.ITEMTYPE_PackingMaterial.equals(itemType))
		{
			node = new HUItemPMTreeNode(item);
			node.setReadonly(readonly);
		}
		else if (X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(itemType))
		{
			node = new HUItemHUTreeNode(item);
			node.setReadonly(readonly);

			if (recursive)
			{
				final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(item);
				for (final I_M_HU includedHU : includedHUs)
				{
					final IHUTreeNode includedHUNode = createNodeRecursively(includedHU, node.isReadonly());
					addChild(node, includedHUNode);
				}
			}
		}
		else
		{
			throw new AdempiereException("Unknown item type: " + itemType);
		}

		
		return node;

	}
}
