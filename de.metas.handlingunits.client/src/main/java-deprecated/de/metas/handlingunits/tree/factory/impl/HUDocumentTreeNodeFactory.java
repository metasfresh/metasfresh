package de.metas.handlingunits.tree.factory.impl;

import org.adempiere.util.Check;

import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.tree.factory.IHUDocumentTreeNodeFactory;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentTreeNode;

public class HUDocumentTreeNodeFactory implements IHUDocumentTreeNodeFactory
{
	@Override
	public IHUDocumentTreeNode createNode(final IHUDocument source)
	{
		Check.assumeNotNull(source, "source not null");

		final IHUDocumentTreeNode treeNode = new HUDocumentTreeNode(source);
		return treeNode;
	}
}
