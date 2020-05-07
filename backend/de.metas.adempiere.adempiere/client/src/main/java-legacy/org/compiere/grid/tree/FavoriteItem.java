package org.compiere.grid.tree;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import org.adempiere.images.Images;
import org.adempiere.util.Check;
import org.compiere.model.MTreeNode;
import org.compiere.swing.CButton;

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
 * A favorite shortcut item in {@link FavoritesGroup}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class FavoriteItem
{
	private final FavoritesGroup group;
	private final CButton button;
	private final MTreeNode node;

	public FavoriteItem(final FavoritesGroup group, final MTreeNode node)
	{
		super();

		Check.assumeNotNull(group, "group not null");
		this.group = group;

		Check.assumeNotNull(node, "node not null");
		this.node = node;

		final String label = node.getName().trim();
		final String iconName = node.getIconName();
		final Icon icon = Images.getImageIcon2(iconName);

		button = new CButton(label);
		button.setOpaque(false);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setIcon(icon);
		button.setRequestFocusEnabled(false);
		button.setToolTipText(node.getDescription());
		button.setActionCommand(String.valueOf(node.getNode_ID()));
		button.setName("menuShortcut." + node.getInternalName()); // me16
	}

	public JComponent getComponent()
	{
		return button;
	}

	public void addMouseListener(final MouseListener l)
	{
		button.addMouseListener(l);
	}
	
	public void addActionListener(final ActionListener l)
	{
		button.addActionListener(l);
	}

	public FavoritesGroup getGroup()
	{
		return group;
	}

	public MTreeNode getNode()
	{
		return node;
	}

	public int getNode_ID()
	{
		return node.getNode_ID();
	}
}
