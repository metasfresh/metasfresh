package de.metas.ui.web.vaadin.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.thirdparty.guava.common.base.Supplier;
import com.google.gwt.thirdparty.guava.common.base.Suppliers;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.vaadin.server.Resource;

import de.metas.ui.web.vaadin.data.util.HierarchicalBeanContainer;
import de.metas.ui.web.vaadin.data.util.TreeItem;
import de.metas.ui.web.vaadin.theme.Theme;

/*
 * #%L
 * test_vaadin
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

public class MenuTreeProvider
{
	public HierarchicalBeanContainer<MenuTreeNode> getMenuTree()
	{
		final Properties ctx = Env.getCtx();
		final int adTreeId = retrieveAD_Tree_ID(ctx);
		if (adTreeId < 0)
		{
			throw new AdempiereException("Menu tree not found");
		}
		
		final MTree mTree = new MTree(ctx, adTreeId, false, true, ITrx.TRXNAME_None);
		final MTreeNode rootNodeModel = mTree.getRoot();
		final MenuTreeNode rootNode = MenuTreeNode.of(rootNodeModel);

		final HierarchicalBeanContainer<MenuTreeNode> container = new HierarchicalBeanContainer<>(MenuTreeNode.class);
		container.addRoots(rootNode.getChildren());
		return container;
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
						+ "WHERE AD_Role_ID=?", userRolePermissions.getAD_Role_ID());
		if (AD_Tree_ID <= 0)
		{
			AD_Tree_ID = 10;    // Menu // FIXME: hardcoded
		}
		return AD_Tree_ID;
	}

	public static final class MenuTreeNode implements TreeItem<MenuTreeNode>
	{
		public static final MenuTreeNode of(final MTreeNode node)
		{
			return new MenuTreeNode(node);
		}

		private final MTreeNode nodeModel;
		//
		private final int id;
		private final int parentId;
		private final String caption;
		private final String iconName;
		private Resource iconResource;
		private boolean iconResourceSet;
		private final boolean summary;
		
		private Supplier<Map<Integer, MenuTreeNode>> childrenMapSupplier = Suppliers.memoize(new Supplier<Map<Integer, MenuTreeNode>>()
		{
			@Override
			public Map<Integer, MenuTreeNode> get()
			{
				final List<MenuTreeNode> childrenList = createChildrenList();
				final ImmutableMap.Builder<Integer, MenuTreeNode> childrenMap = ImmutableMap.builder();
				for (final MenuTreeNode child : childrenList)
				{
					childrenMap.put(child.getId(), child);
				}
				
				return childrenMap.build();
			}
		});

		private I_AD_Menu adMenu;

		private MenuTreeNode(final MTreeNode node)
		{
			super();
			Check.assumeNotNull(node, "node not null");
			this.nodeModel = node;
			
			id = node.getNode_ID();
			parentId = node.getParent_ID();
			caption = node.getName();
			iconName = node.getIconName();
			summary = node.isSummary();
		}

		@Override
		public String toString()
		{
			return getCaption();
		}
		
		@Override
		public int getId()
		{
			return id;
		}
		@Override
		public int getParentId()
		{
			return parentId;
		}

		@Override
		public String getCaption()
		{
			return caption;
		}
		
		public boolean isSummary()
		{
			return summary;
		}

		@Override
		public Collection<MenuTreeNode> getChildren()
		{
			return childrenMapSupplier.get().values();
		}

		@Override
		public Collection<Integer> getChildrenIds()
		{
			return childrenMapSupplier.get().keySet();
		}

		private final List<MenuTreeNode> createChildrenList()
		{
			if (!isSummary())
			{
				return ImmutableList.of();
			}
			
			final List<MenuTreeNode> children = new ArrayList<>();
			final Enumeration<?> childModels = nodeModel.children();
			while (childModels.hasMoreElements())
			{
				final MTreeNode childNodeModel = (MTreeNode)childModels.nextElement();
				final MenuTreeNode child = MenuTreeNode.of(childNodeModel);
				children.add(child);
			}
			
			return children;
		}

		@Override
		public Resource getIcon()
		{
			if (!iconResourceSet)
			{
				iconResource = Theme.getImageResourceForNameWithoutExt(iconName);
				iconResourceSet = true;
			}
			return iconResource;
		}
		
		private final I_AD_Menu getAD_Menu()
		{
			if (adMenu == null)
			{
				adMenu = InterfaceWrapperHelper.create(Env.getCtx(), id, I_AD_Menu.class, ITrx.TRXNAME_None);
			}
			return adMenu;
		}
		
		public String getAction()
		{
			return getAD_Menu().getAction();
		}

		public int getAD_Window_ID()
		{
			return getAD_Menu().getAD_Window_ID();
		}
	}
}
