package de.metas.handlingunits.client.editor.hu.model.context.menu;

import java.util.List;

import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

public interface IHUTreeNodeCMActionProvider
{
	/**
	 * @param node
	 * @return {@link ICMAction}s for given node
	 */
	List<ICMAction> retrieveCMActions(final HUEditorModel model, final IHUTreeNode node);
}
