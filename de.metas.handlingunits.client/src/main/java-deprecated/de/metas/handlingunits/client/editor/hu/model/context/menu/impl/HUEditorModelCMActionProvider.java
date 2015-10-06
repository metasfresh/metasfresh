package de.metas.handlingunits.client.editor.hu.model.context.menu.impl;

import java.util.List;

import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

public final class HUEditorModelCMActionProvider implements IHUTreeNodeCMActionProvider
{
	public static final transient HUEditorModelCMActionProvider instance = new HUEditorModelCMActionProvider();

	private HUEditorModelCMActionProvider()
	{
		super();
	}

	@Override
	public List<ICMAction> retrieveCMActions(final HUEditorModel model, final IHUTreeNode node)
	{
		final CompositeHUTreeNodeCMActionProvider providers = new CompositeHUTreeNodeCMActionProvider();

		//
		// Add providers from HU Documents
		for (final IHUDocumentTreeNode documentNode : model.getHUDocumentsModel().getAllNodes())
		{
			final IHUTreeNodeCMActionProvider provider = documentNode.getHUTreeNodeCMActionProvider();
			providers.addProvider(provider);
		}

		//
		// Add action provider from given Node
		{
			final IHUTreeNodeCMActionProvider provider = node.getHUTreeNodeCMActionProvider();
			providers.addProvider(provider);
		}

		final List<ICMAction> actions = providers.retrieveCMActions(model, node);
		return actions;
	}
}
