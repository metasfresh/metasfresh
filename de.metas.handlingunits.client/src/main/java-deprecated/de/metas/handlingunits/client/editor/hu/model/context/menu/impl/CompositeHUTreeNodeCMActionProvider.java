package de.metas.handlingunits.client.editor.hu.model.context.menu.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Check;

import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

/**
 * Groups together multiple {@link IHUTreeNodeCMActionProvider}s and behave like one.
 * 
 * @author tsa
 * 
 */
public class CompositeHUTreeNodeCMActionProvider implements IHUTreeNodeCMActionProvider
{
	private final List<IHUTreeNodeCMActionProvider> providers = new ArrayList<IHUTreeNodeCMActionProvider>();

	public void addProvider(IHUTreeNodeCMActionProvider provider)
	{
		Check.assumeNotNull(provider, "provider not null");

		if (providers.contains(provider))
		{
			return;
		}

		providers.add(provider);
	}

	@Override
	public List<ICMAction> retrieveCMActions(final HUEditorModel model, final IHUTreeNode node)
	{
		final List<ICMAction> actions = new ArrayList<ICMAction>();

		final Set<String> actionIds = new HashSet<String>();
		for (IHUTreeNodeCMActionProvider provider : providers)
		{
			final List<ICMAction> providerActions = provider.retrieveCMActions(model, node);
			for (final ICMAction action : providerActions)
			{
				final String actionId = action.getId();

				// If action was already added, don't add it twice
				if (!actionIds.add(actionId))
				{
					continue;
				}

				// Checks if action is available
				if (!action.isAvailable(model, node))
				{
					continue;
				}

				actions.add(action);
			}
		}

		return actions;
	}

}
