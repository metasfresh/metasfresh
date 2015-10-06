package de.metas.handlingunits.client.editor.hu.view.context.menu;

import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

/**
 * Context Menu Action
 * 
 * @author
 * 
 */
public interface ICMAction
{
	/**
	 * 
	 * @return unique identifier of this action
	 */
	String getId();

	String getName();
	
	ICMActionGroup getCMActionGroup();

	boolean isAvailable(HUEditorModel model, IHUTreeNode node);

	void execute(HUEditorModel model, IHUTreeNode node);
}
