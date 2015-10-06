package de.metas.handlingunits.client.editor.hu.view.swing.mouse.listener;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import org.adempiere.util.TypedAccessor;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.jdesktop.swingx.JXTreeTable;

import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMActionGroup;
import de.metas.handlingunits.client.editor.hu.view.swing.NodeActionAdapter;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

public class HUTreeTableMouseListener extends MouseAdapter
{
	private final JXTreeTable treeTable;
	private final HUEditorModel editorModel;

	public HUTreeTableMouseListener(final JXTreeTable treeTable)
	{
		super();

		this.treeTable = treeTable;
		editorModel = (HUEditorModel)treeTable.getTreeTableModel();
	}

	@Override
	public void mouseReleased(final MouseEvent e)
	{
		// on right-click
		if (SwingUtilities.isRightMouseButton(e))
		{
			showPopup(e);
		}
	}

	/**
	 * Pop-up logic, detects and displays proper context menu.
	 * 
	 * @param e
	 * @param actions
	 */
	private void showPopup(final MouseEvent e)
	{
		if (!e.isPopupTrigger())
		{
			return;
		}

		final TreePath selPath = treeTable.getPathForLocation(e.getX(), e.getY());

		// If we haven't clicked on a node, gtfo
		if (selPath == null)
		{
			return;
		}

		// Make sure we also add it to the selection
		treeTable.getTreeSelectionModel().addSelectionPath(selPath);

		final JPopupMenu popupMenu = buildPopup();
		if (popupMenu == null)
		{
			return;
		}

		if (popupMenu.getComponentCount() <= 0)
		{
			return;
		}

		popupMenu.show((Component)e.getSource(), e.getX(), e.getY());
	}

	private JPopupMenu buildPopup()
	{
		// Based on selection context, install actions...
		final JPopupMenu popupMenu = new JPopupMenu();
		addActions(popupMenu);
		return popupMenu;
	}

	private void addActions(final JPopupMenu popupMenu)
	{
		final IHUTreeNode selectedNode = editorModel.getSelectedNode();
		if (selectedNode == null)
		{
			return;
		}

		final IHUTreeNodeCMActionProvider provider = editorModel.getHUTreeNodeCMActionProvider();
		final List<ICMAction> actions = provider.retrieveCMActions(editorModel, selectedNode);

		//
		// Sort actions by Group
		Collections.sort(actions,
				new AccessorComparator<ICMAction, ICMActionGroup>(
						ComparableComparator.getInstance(ICMActionGroup.class),
						new TypedAccessor<ICMActionGroup>()
						{

							@Override
							public ICMActionGroup getValue(Object o)
							{
								if (o == null)
								{
									return null;
								}
								final ICMAction action = (ICMAction)o;
								return action.getCMActionGroup();
							}
						}
				));

		//
		// Add actions to popup
		ICMActionGroup lastActionGroup = null;
		final Set<String> actionIds = new HashSet<String>();
		List<ICMAction> currentGroupActions = new ArrayList<ICMAction>();
		for (final ICMAction action : actions)
		{
			final String actionId = action.getId();

			// If action was already added, don't add it twice
			if (!actionIds.add(actionId))
			{
				continue;
			}

			if (!action.isAvailable(editorModel, selectedNode))
			{
				continue;
			}

			final ICMActionGroup group = action.getCMActionGroup();

			// Group changed, we will draw a horizontal line here
			if (group != null && lastActionGroup != null && !group.equals(lastActionGroup))
			{
				addActionsToPopup(popupMenu, lastActionGroup, currentGroupActions);
				currentGroupActions.clear();
			}

			currentGroupActions.add(action);
			lastActionGroup = group;
		}

		addActionsToPopup(popupMenu, lastActionGroup, currentGroupActions);
	}

	private void addActionsToPopup(final JPopupMenu popupMenu,
			final ICMActionGroup group,
			final List<ICMAction> actions)
	{
		if (actions.isEmpty())
		{
			return;
		}

		if (popupMenu.getComponentCount() > 0)
		{
			popupMenu.addSeparator();
		}

		final int maxItemsPerGroup = 5;
		JMenu moreMenu = null;
		for (int i = 0; i < actions.size(); i++)
		{
			final ICMAction action = actions.get(i);

			if (i >= maxItemsPerGroup)
			{
				if (moreMenu == null)
				{
					moreMenu = new JMenu("More");
				}
				NodeActionAdapter.addTo(moreMenu, action, editorModel);
			}
			else
			{
				NodeActionAdapter.addTo(popupMenu, action, editorModel);
			}
		}

		if (moreMenu != null)
		{
			popupMenu.add(moreMenu);
		}
	}
}
