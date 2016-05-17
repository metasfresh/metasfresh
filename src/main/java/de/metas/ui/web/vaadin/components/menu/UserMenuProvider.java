package de.metas.ui.web.vaadin.components.menu;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.model.X_AD_Menu;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.vaadin.server.Resource;

import de.metas.ui.web.vaadin.components.menu.MainMenuItem.MenuItemType;
import de.metas.ui.web.vaadin.theme.Theme;

/*
 * #%L
 * metasfresh-webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class UserMenuProvider
{

	public List<MenuItem> getMenuItems()
	{
		final MTreeNode rootNodeModel = retrieveRootNodeModel();

		final List<MenuItem> menuGroups = new ArrayList<>();
		final MainMenuItem.Builder miscMenuGroupBuilder = MainMenuItem.builder()
				.setCaption("Misc");

		final Enumeration<?> groupModels = rootNodeModel.children();
		while (groupModels.hasMoreElements())
		{
			final MTreeNode groupModel = (MTreeNode)groupModels.nextElement();

			if (groupModel.isSummary())
			{
				final MenuItem menuGroup = createMenuGroup(groupModel);
				menuGroups.add(menuGroup);
			}
			else
			{
				final MenuItem item = createMenuItem(groupModel);
				miscMenuGroupBuilder.addChild(item);
			}
		}

		if (miscMenuGroupBuilder.hasChildren())
		{
			final MenuItem miscMenuGroup = miscMenuGroupBuilder.build();
			menuGroups.add(miscMenuGroup);
		}

		return menuGroups;
	}

	private MenuItem createMenuGroup(final MTreeNode groupModel)
	{
		final MainMenuItem.Builder builder = MainMenuItem.builder();
		builder.setCaption(groupModel.getName());

		iterateLeafs(groupModel, node -> builder.addChild(createMenuItem(node)));

		return builder.build();
	}

	private MenuItem createMenuItem(final MTreeNode node)
	{
		final MainMenuItem.Builder builder = MainMenuItem.builder()
				.setCaption(node.getName());
		
		final String iconName = node.getIconName();
		final Resource iconResource = Theme.getImageResourceForNameWithoutExt(iconName);
		builder.setIcon(iconResource);

		final String action = node.getImageIndiactor();
		if (X_AD_Menu.ACTION_Window.equals(action))
		{
			builder.setType(MenuItemType.Window, node.getAD_Window_ID());
		}
		else if (X_AD_Menu.ACTION_Process.equals(action))
		{
			builder.setType(MenuItemType.Process, node.getAD_Process_ID());
		}
		else if (X_AD_Menu.ACTION_Report.equals(action))
		{
			builder.setType(MenuItemType.Report, node.getAD_Process_ID());
		}

		return builder.build();
	}

	private final void iterateLeafs(final MTreeNode node, Consumer<MTreeNode> consumer)
	{
		if (!node.isSummary())
		{
			consumer.accept(node);
			return;
		}

		final Enumeration<?> children = node.children();
		while (children.hasMoreElements())
		{
			final MTreeNode childNode = (MTreeNode)children.nextElement();
			if (childNode.isSummary())
			{
				iterateLeafs(childNode, consumer);
			}
			else
			{
				consumer.accept(childNode);
			}
		}
	}

	private MTreeNode retrieveRootNodeModel()
	{
		final Properties ctx = Env.getCtx();
		final int adTreeId = retrieveAD_Tree_ID(ctx);
		if (adTreeId < 0)
		{
			throw new AdempiereException("Menu tree not found");
		}

		final MTree mTree = new MTree(ctx, adTreeId, false, true, ITrx.TRXNAME_None);
		final MTreeNode rootNodeModel = mTree.getRoot();
		return rootNodeModel;
	}

	private static int retrieveAD_Tree_ID(final Properties ctx)
	{
		// metas: 03019: begin
		final IUserRolePermissions userRolePermissions = Env.getUserRolePermissions(ctx);
		if (!userRolePermissions.hasPermission(IUserRolePermissions.PERMISSION_MenuAvailable))
		{
			return -1;
		}
		// metas: 03019: end

		int AD_Tree_ID = DB.getSQLValue(ITrx.TRXNAME_None,
				"SELECT COALESCE(r.AD_Tree_Menu_ID, ci.AD_Tree_Menu_ID)"
						+ "FROM AD_ClientInfo ci"
						+ " INNER JOIN AD_Role r ON (ci.AD_Client_ID=r.AD_Client_ID) "
						+ "WHERE AD_Role_ID=?",
				userRolePermissions.getAD_Role_ID());
		if (AD_Tree_ID <= 0)
		{
			AD_Tree_ID = 10;    // Menu // FIXME: hardcoded
		}
		return AD_Tree_ID;
	}
}
