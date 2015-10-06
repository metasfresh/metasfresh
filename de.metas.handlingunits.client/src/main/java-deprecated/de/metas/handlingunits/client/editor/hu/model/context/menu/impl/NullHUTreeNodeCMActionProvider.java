package de.metas.handlingunits.client.editor.hu.model.context.menu.impl;

import java.util.Collections;
import java.util.List;

import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

public final class NullHUTreeNodeCMActionProvider implements IHUTreeNodeCMActionProvider
{
	public static final transient NullHUTreeNodeCMActionProvider instance = new NullHUTreeNodeCMActionProvider();

	private NullHUTreeNodeCMActionProvider()
	{
		super();
	}

	@Override
	public List<ICMAction> retrieveCMActions(HUEditorModel model, IHUTreeNode node)
	{
		return Collections.emptyList();
	}

}
