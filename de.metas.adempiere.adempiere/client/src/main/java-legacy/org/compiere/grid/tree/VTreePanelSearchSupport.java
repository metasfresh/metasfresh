package org.compiere.grid.tree;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.MTreeNode;
import org.compiere.swing.CLabel;
import org.compiere.swing.CTextField;
import org.compiere.util.Env;

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
 * {@link VTreePanel}'s search support: allows user to search in tree menu.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class VTreePanelSearchSupport
{
	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	// UI
	private final CLabel treeSearchLabel = new CLabel();
	private final CTextField treeSearch = new CTextField(10);
	private final TreeSearchAutoCompleter treeSearchAutoCompleter;

	public VTreePanelSearchSupport()
	{
		super();

		treeSearchAutoCompleter = new TreeSearchAutoCompleter(treeSearch)
		{
			@Override
			protected void onCurrentItemChanged(MTreeNode node)
			{
				onTreeNodeSelected(node);
			}
		};

		treeSearchLabel.setText(msgBL.getMsg(Env.getCtx(), "TreeSearch") + " ");
		treeSearchLabel.setLabelFor(treeSearch);
		treeSearchLabel.setToolTipText(msgBL.getMsg(Env.getCtx(), "TreeSearchText"));

		treeSearch.setBackground(AdempierePLAF.getInfoBackground());
	}

	/**
	 * Executed when a node is selected by the user.
	 * 
	 * @param node
	 */
	protected void onTreeNodeSelected(final MTreeNode node)
	{
		// nothing
	}

	/**
	 * Loads the auto-complete suggestions.
	 * 
	 * @param root
	 */
	public final void setTreeNodesFromRoot(final MTreeNode root)
	{
		treeSearchAutoCompleter.setTreeNodes(root);
	}

	/** @return the search label of {@link #getSearchField()} */
	public final CLabel getSearchFieldLabel()
	{
		return treeSearchLabel;
	}

	/** @return the search field (with auto-complete support) */
	public final CTextField getSearchField()
	{
		return treeSearch;
	}

	public final void requestFocus()
	{
		treeSearch.requestFocus();
	}

	public final boolean requestFocusInWindow()
	{
		return treeSearch.requestFocusInWindow();
	}
}
