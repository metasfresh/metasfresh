package org.compiere.grid.tree;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.MTreeNode;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CScrollPane;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

import de.metas.adempiere.form.IClientUI;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Container of Favorite shortcuts groups.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
final class FavoritesGroupContainer
{
	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);

	private final CScrollPane panelScrollPane = new CScrollPane();
	private final JXTaskPaneContainer panel = new JXTaskPaneContainer();
	private final Map<Integer, FavoritesGroup> topNodeId2group = new HashMap<>();
	private FavoritesListener listener = null;

	//
	// Popup
	private final JPopupMenu popupMenu = new JPopupMenu();
	private final CMenuItem mBarRemove = new CMenuItem();
	private FavoriteItem selectedItemByPopup = null;

	// Database repository
	private final FavoritesDAO favoritesDAO = new FavoritesDAO();
	private boolean loading = false;
	
	public FavoritesGroupContainer()
	{
		super();

		panelScrollPane.setVisible(false);
		panelScrollPane.setBorder(BorderFactory.createEmptyBorder());
		panelScrollPane.setViewportView(panel);

		mBarRemove.setText(msgBL.getMsg(Env.getCtx(), "BarRemove"));
		mBarRemove.setActionCommand("BarRemove");
		mBarRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				removeItem(selectedItemByPopup);
				selectedItemByPopup = null;
			}
		});

		popupMenu.setLightWeightPopupEnabled(false);
		popupMenu.add(mBarRemove);
	};

	void showPopup(final FavoriteItem item, final MouseEvent e)
	{
		if (popupMenu == null)
		{
			return;
		}

		selectedItemByPopup = item;

		if (item == null)
		{
			return;
		}

		final JComponent itemComp = item.getComponent();
		popupMenu.show(itemComp, e.getX(), e.getY());
	}
	
	public void setListener(final FavoritesListener listener)
	{
		this.listener = listener;
	}
	
	void fireItemClicked(FavoriteItem item)
	{
		if(listener == null)
		{
			return;
		}
		listener.onNodeClicked(item.getNode());
	}


	public JComponent getComponent()
	{
		return panelScrollPane;
	}

	public void loadAllFromRoot(final MTreeNode root)
	{
		loading = true;
		try
		{
			clearGroups();

			final Enumeration<?> nodes = root.preorderEnumeration();
			while (nodes.hasMoreElements())
			{
				final MTreeNode node = (MTreeNode)nodes.nextElement();
				if (node == root)
				{
					continue;
				}
				if (!node.isOnBar())
				{
					continue;
				}

				addItem0(node);
			}
		}
		finally
		{
			loading = false;

			updateUI();
		}
	}

	public void addItem(final MTreeNode node)
	{
		addItem0(node);
		updateUI();
	}

	private final void addItem0(final MTreeNode node)
	{
		if (node == null)
		{
			return;
		}

		// Database sync
		if (!loading)
		{
			try
			{
				favoritesDAO.add(node.getAD_Tree_ID(), node.getNode_ID());
			}
			catch (Exception e)
			{
				final int windowNo = Env.getWindowNo(getComponent());
				if (DBException.isUniqueContraintError(e))
				{
					clientUI.error(windowNo, "BookmarkExist");
				}
				else
				{
					clientUI.error(windowNo, e);
				}

				// stop here
				return;
			}
		}

		final FavoritesGroup favoritesGroup = getCreateGroup(node);
		favoritesGroup.addFavorite(node);
	}

	private void addGroup(final FavoritesGroup newGroup)
	{
		topNodeId2group.put(newGroup.getTopNodeId(), newGroup);

		JXTaskPane newGroupComp = newGroup.getComponent();
		final String newGroupTitle = newGroupComp.getTitle();

		int index = 0;
		for (index = 0; index < panel.getComponentCount(); index++)
		{
			final JXTaskPane comp = (JXTaskPane)panel.getComponent(index);
			final String compTitle = comp.getTitle();
			if (compTitle.compareTo(newGroupTitle) > 0)
			{
				break;
			}
		}
		panel.add(newGroupComp, null, index);

		updateUI();
	}

	private FavoritesGroup getCreateGroup(final MTreeNode node)
	{
		final MTreeNode topParent = getTopParent(node);
		if (topParent == null)
		{
			return null;
		}

		FavoritesGroup parentGroup = topNodeId2group.get(topParent.getNode_ID());
		if (parentGroup == null)
		{
			parentGroup = new FavoritesGroup(this, topParent);
			addGroup(parentGroup);
		}

		return parentGroup;
	}

	private void removeGroup(final FavoritesGroup group)
	{
		if (group == null)
		{
			return;
		}
		for (final Iterator<FavoritesGroup> it = topNodeId2group.values().iterator(); it.hasNext();)
		{
			final FavoritesGroup g = it.next();
			if (Check.equals(group, g))
			{
				it.remove();
			}
		}

		panel.remove(group.getComponent());

		updateUI();
	}

	private static MTreeNode getTopParent(final MTreeNode nd)
	{
		if (nd == null)
		{
			// shall not happen
			return null;
		}

		MTreeNode currentNode = nd;
		while (currentNode != null)
		{
			// If parent node is null or it has no ID (i.e. like the root node),
			// we consider current node as the top node
			final MTreeNode parent = (MTreeNode)currentNode.getParent();
			if (parent == null || parent.getNode_ID() <= 0)
			{
				return currentNode;
			}

			// navigate up to parent, and check
			currentNode = parent;
		}

		return null;
	}

	public void removeItem(final FavoriteItem item)
	{
		if (item == null)
		{
			return;
		}

		// Database sync
		final MTreeNode node = item.getNode();
		favoritesDAO.remove(node.getAD_Tree_ID(), node.getNode_ID());

		// UI
		final FavoritesGroup group = item.getGroup();
		group.removeItem(item);

		if (group.isEmpty())
		{
			removeGroup(group);
		}

		updateUI();
	}

	private void clearGroups()
	{
		for (final FavoritesGroup group : topNodeId2group.values())
		{
			group.removeAllItems();
		}
		topNodeId2group.clear();

		panel.removeAll();
	}

	public boolean isEmpty()
	{
		return topNodeId2group.isEmpty();
	}

	private final void updateUI()
	{
		final JComponent comp = getComponent();

		panel.invalidate();
		panel.repaint();

		final boolean visible = !isEmpty();
		final boolean visibleOld = comp.isVisible();
		if (visible == visibleOld)
		{
			return;
		}

		comp.setVisible(visible);

		//
		// If this group just became visible
		if (visible)
		{
			updateParentSplitPaneDividerLocation();
		}
	}

	private final void updateParentSplitPaneDividerLocation()
	{
		final JComponent comp = getComponent();
		if (!comp.isVisible())
		{
			return; // nothing to update
		}

		// Find parent split pane if any
		JSplitPane parentSplitPane = null;
		for (Component c = comp.getParent(); c != null; c = c.getParent())
		{
			if (c instanceof JSplitPane)
			{
				parentSplitPane = (JSplitPane)c;
				break;
			}
		}

		// Update it's divider location.
		// NOTE: if we would not do this, user would have to manually drag it when the first component is added.
		if (parentSplitPane != null)
		{
			if (parentSplitPane.getDividerLocation() <= 0)
			{
				parentSplitPane.setDividerLocation(Ini.getDividerLocation());
			}
		}

	}
}