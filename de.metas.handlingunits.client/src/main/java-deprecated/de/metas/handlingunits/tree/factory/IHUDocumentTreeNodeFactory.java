package de.metas.handlingunits.tree.factory;

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;

public interface IHUDocumentTreeNodeFactory extends ISingletonService
{
	IHUDocumentTreeNode createNode(final IHUDocument source);
}
