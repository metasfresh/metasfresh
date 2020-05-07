package org.compiere.grid.tree;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.IdentityHashMap;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.adempiere.util.Check;
import org.compiere.model.MTreeNode;
import org.jdesktop.swingx.JXTaskPane;

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
 * A favorite shortcut group in {@link FavoritesGroupContainer}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
final class FavoritesGroup
{
	private final FavoritesGroupContainer container;
	private final int topNodeId;
	private final IdentityHashMap<MTreeNode, FavoriteItem> node2item = new IdentityHashMap<>();
	private final IdentityHashMap<JComponent, FavoriteItem> component2item = new IdentityHashMap<>();

	private final JXTaskPane panel = new JXTaskPane();
	private final JToolBar toolbar;

	private final MouseAdapter itemMouseListener = new MouseAdapter()
	{
		@Override
		public void mouseClicked(final MouseEvent e)
		{
			if (SwingUtilities.isRightMouseButton(e))
			{
				final FavoriteItem item = component2item.get(e.getSource());
				container.showPopup(item, e);
			}
		};
	};
	
	private ActionListener itemActionListener = new ActionListener()
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			final FavoriteItem item = component2item.get(e.getSource());
			if(item == null)
			{
				return;
			}
			container.fireItemClicked(item);
		}
	};

	public FavoritesGroup(final FavoritesGroupContainer container, final MTreeNode ndTop)
	{
		super();

		Check.assumeNotNull(container, "container not null");
		this.container = container;

		Check.assumeNotNull(ndTop, "ndTop not null");
		topNodeId = ndTop.getNode_ID();

		// barPart.setAnimated(true); // use Theme setting
		panel.setLayout(new BorderLayout());
		panel.setTitle(ndTop.getName().trim());

		toolbar = new JToolBar(SwingConstants.VERTICAL);
		toolbar.setOpaque(false);
		toolbar.setFloatable(false);
		toolbar.setRollover(true);
		toolbar.setBorder(BorderFactory.createEmptyBorder());
		panel.add(toolbar, BorderLayout.NORTH);
	}

	public JXTaskPane getComponent()
	{
		return panel;
	}

	public int getTopNodeId()
	{
		return topNodeId;
	}

	public void removeAllItems()
	{
		toolbar.removeAll();
	}

	public void addFavorite(final MTreeNode node)
	{
		if (node2item.get(node) != null)
		{
			// already added
			return;
		}

		final FavoriteItem item = new FavoriteItem(this, node);
		item.addMouseListener(itemMouseListener);
		item.addActionListener(itemActionListener);

		final JComponent itemComp = item.getComponent();

		node2item.put(node, item);
		component2item.put(itemComp, item);

		node.setOnBar(true);

		toolbar.add(itemComp);
	}

	public boolean isEmpty()
	{
		return node2item.isEmpty();
	}

	public void removeItem(final FavoriteItem item)
	{
		Check.assumeNotNull(item, "item not null");
		final MTreeNode node = item.getNode();
		final JComponent itemComp = item.getComponent();

		node2item.remove(node);
		component2item.remove(itemComp);
		node.setOnBar(false);

		toolbar.remove(itemComp);
	}
}