package de.metas.handlingunits.client.editor.hu.model.context.menu.impl;

import java.util.Collections;
import java.util.List;

import org.adempiere.util.Check;

import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

/**
 * Wraps a given {@link ICMAction} into an {@link IHUTreeNodeCMActionProvider} with one action.
 * 
 * @author tsa
 * 
 */
public final class SingletonHUTreeNodeCMActionProvider implements IHUTreeNodeCMActionProvider
{
	private final List<ICMAction> actions;

	public SingletonHUTreeNodeCMActionProvider(final ICMAction action)
	{
		super();

		Check.assumeNotNull(action, "action not null");
		this.actions = Collections.singletonList(action);
	}

	@Override
	public List<ICMAction> retrieveCMActions(HUEditorModel model, IHUTreeNode node)
	{
		return actions;
	}

}
