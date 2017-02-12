package org.compiere.grid.tree;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.text.JTextComponent;

import org.adempiere.images.Images;
import org.compiere.model.MTreeNode;
import org.compiere.swing.autocomplete.JTextComponentAutoCompleter;
import org.compiere.swing.autocomplete.ResultItem;
import org.compiere.swing.autocomplete.ResultItemWrapper;

import com.google.common.collect.ImmutableList;

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
 * Auto-complete support for {@link MTreeNode}s.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class TreeSearchAutoCompleter extends JTextComponentAutoCompleter
{
	public TreeSearchAutoCompleter(final JTextComponent comp)
	{
		super(comp);
	}

	@Override
	protected final ListCellRenderer<ResultItem> createListCellRenderer()
	{
		return new ResultItemRenderer();
	}

	/**
	 * Sets the {@link MTreeNode} source.
	 * 
	 * @param root root node
	 */
	public final void setTreeNodes(final MTreeNode root)
	{
		setSource(MenuResultItemSource.forRootNode(root));
	}

	@Override
	protected boolean itemMatches_Filtering(final String itemText, final String searchText)
	{
		final String itemTextNorm = normalizeSearchString(itemText);
		final String searchTextNorm = normalizeSearchString(searchText);

		return itemTextNorm.indexOf(searchTextNorm) >= 0;
	}

	@Override
	protected final void onCurrentItemChanged(ResultItem currentItem, ResultItem currentItemOld)
	{
		if (currentItem instanceof MenuResultItem)
		{
			final MenuResultItem menuResultItem = (MenuResultItem)currentItem;
			onCurrentItemChanged(menuResultItem.getValue());
		}
	}

	protected void onCurrentItemChanged(MTreeNode node)
	{
		// nothing at this level. To be overridden by extending class.
	}

	/**
	 * Menu tree node as {@link ResultItem}.
	 */
	private static final class MenuResultItem extends ResultItemWrapper<MTreeNode>
	{
		public MenuResultItem(final MTreeNode node)
		{
			super(node);
		}

		@Override
		public String getText()
		{
			return getValue().getName();
		}

		public Icon getIcon()
		{
			final String iconName = getValue().getIconName();
			return Images.getImageIcon2(iconName);
		}

	}

	/**
	 * Source of {@link MenuResultItem}s.
	 */
	private static final class MenuResultItemSource implements org.compiere.swing.autocomplete.ResultItemSource
	{
		public static MenuResultItemSource forRootNode(final MTreeNode root)
		{
			final Enumeration<?> nodesEnum = root.preorderEnumeration();
			final List<MenuResultItem> items = new ArrayList<>();
			while (nodesEnum.hasMoreElements())
			{
				final MTreeNode node = (MTreeNode)nodesEnum.nextElement();
				if (node == root)
				{
					continue;
				}
				if (node.isSummary())
				{
					continue;
				}

				final MenuResultItem item = new MenuResultItem(node);
				items.add(item);
			}

			return new MenuResultItemSource(items);
		}

		private final ImmutableList<MenuResultItem> items;

		private MenuResultItemSource(final List<MenuResultItem> items)
		{
			super();
			this.items = ImmutableList.copyOf(items);
		}

		@Override
		public List<MenuResultItem> query(final String searchText, final int limit)
		{
			return items;
		}
	}

	/**
	 * {@link MenuResultItem} renderer.
	 */
	private static final class ResultItemRenderer implements ListCellRenderer<ResultItem>
	{
		private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends ResultItem> list, final ResultItem value, final int index, final boolean isSelected, final boolean cellHasFocus)
		{
			final String valueAsText;
			final Icon icon;
			boolean enabled = true;
			if (value == null)
			{
				// shall not happen
				valueAsText = "";
				icon = null;
			}
			else if (value == MORE_Marker)
			{
				valueAsText = "...";
				icon = null;
				enabled = false;
			}
			else if (value instanceof MenuResultItem)
			{
				final MenuResultItem menuItem = (MenuResultItem)value;
				valueAsText = menuItem.getText();
				icon = menuItem.getIcon();
			}
			else
			{
				valueAsText = value.toString();
				icon = null;
			}

			defaultRenderer.getListCellRendererComponent(list, valueAsText, index, isSelected, cellHasFocus);
			defaultRenderer.setIcon(icon);
			defaultRenderer.setEnabled(enabled);

			return defaultRenderer;
		}
	}
}
