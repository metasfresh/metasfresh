package de.metas.handlingunits.tree.factory;

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

public interface IHUTreeNodeFactory extends ISingletonService
{
	IHUTreeNode createNodeRecursively(Object contextProvider, I_M_HU_PI huPI);

	IHUTreeNode createNodeRecursively(Object contextProvider, I_M_HU_PI huPI, I_M_HU_Item parentItem);

	IHUTreeNode createNode(I_M_HU_Item item);

	IHUTreeNode createNodeRecursively(I_M_HU_Item item, final boolean readonly);

	IHUTreeNode createNode(I_M_HU hu);

	IHUTreeNode createNodeRecursively(I_M_HU hu, boolean readonly);

	void addChild(IHUTreeNode parent, IHUTreeNode child);

	void updateFromParent(IHUTreeNode child);

}
