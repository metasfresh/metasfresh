package de.metas.ui.web.menu;

import java.util.Enumeration;
import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.model.X_AD_Menu;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;

import de.metas.logging.LogManager;
import de.metas.ui.web.menu.MenuNode.MenuNodeType;

/*
 * #%L
 * metasfresh-webui-api
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

public final class MenuTreeLoader
{
	/* package */static MenuTreeLoader newInstance()
	{
		return new MenuTreeLoader();
	}

	// services
	private static final transient Logger logger = LogManager.getLogger(MenuTreeLoader.class);
	private final transient IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);

	private static final int DEPTH_Root = 0;
	private static final int DEPTH_RootChildren = 1;

	private Properties _ctx;
	private UserRolePermissionsKey _userRolePermissionsKey;
	private IUserRolePermissions _userRolePermissions; // lazy
	private String _adLanguage;

	private MenuTreeLoader()
	{
		super();
	}

	public MenuTreeLoader setCtx(final Properties ctx)
	{
		_ctx = ctx;
		return this;
	}

	private Properties getCtx()
	{
		Preconditions.checkNotNull(_ctx, "ctx");
		return _ctx;
	}

	public MenuTreeLoader setUserRolePermissionsKey(final UserRolePermissionsKey userRolePermissionsKey)
	{
		_userRolePermissionsKey = userRolePermissionsKey;
		return this;
	}

	private IUserRolePermissions getUserRolePermissions()
	{
		if (_userRolePermissions == null)
		{
			final UserRolePermissionsKey userRolePermissionsKey = _userRolePermissionsKey != null ? _userRolePermissionsKey : UserRolePermissionsKey.of(getCtx());
			_userRolePermissions = userRolePermissionsDAO.retrieveUserRolePermissions(userRolePermissionsKey);
		}
		return _userRolePermissions;
	}

	public MenuTree load()
	{
		if (logger.isTraceEnabled())
		{
			logger.trace("Loading menu tree for {}", getUserRolePermissions());
		}

		final MTreeNode rootNodeModel = retrieveRootNodeModel();
		final MenuNode rootNode = createMenuNodeRecursivelly(rootNodeModel, DEPTH_Root);
		if (rootNode == null)
		{
			throw new IllegalStateException("No root menu node available"); // shall not happen
		}

		return MenuTree.of(rootNode);
	}

	private MenuNode createMenuNodeRecursivelly(final MTreeNode nodeModel, final int depth)
	{
		final MenuNode.Builder nodeBuilder = createMenuNodeBuilder(nodeModel, depth);
		if (nodeBuilder == null)
		{
			logger.trace("Skip creating menu node for {}", nodeModel);
			return null;
		}

		final Enumeration<?> childModels = nodeModel.children();
		while (childModels.hasMoreElements())
		{
			final MTreeNode childModel = (MTreeNode)childModels.nextElement();

			final MenuNode childNode = createMenuNodeRecursivelly(childModel, depth + 1);
			if (childNode == null)
			{
				continue;
			}

			if (childModel.isCreateNewRecord())
			{
				final MenuNode childNodeNewRecord = createNewRecordNode(childNode, childModel.getWEBUI_NameNew(), childModel.getWEBUI_NameNewBreadcrumb());
				if (childNodeNewRecord != null)
				{
					nodeBuilder.addChildToFirstsList(childNodeNewRecord);
				}
			}

			nodeBuilder.addChild(childNode);
		}

		return nodeBuilder.build();
	}

	private MenuNode.Builder createMenuNodeBuilder(final MTreeNode nodeModel, final int depth)
	{
		final String captionBreadcrumb = nodeModel.getName(); // shall not be empty

		String caption = nodeModel.getWEBUI_NameBrowse();
		if (Check.isEmpty(caption, true))
		{
			caption = captionBreadcrumb;
		}

		final MenuNode.Builder builder = MenuNode.builder()
				.setId(nodeModel.getNode_ID())
				.setCaption(caption)
				.setCaptionBreadcrumb(captionBreadcrumb);

		final String action = nodeModel.getImageIndiactor();
		if (nodeModel.isSummary())
		{
			builder.setType(MenuNodeType.Group, -1);
		}
		else if (depth == DEPTH_RootChildren)
		{
			logger.warn("Skip creating leaf nodes for root: {}", nodeModel);
			return null;
		}
		else if (X_AD_Menu.ACTION_Window.equals(action))
		{
			builder.setType(MenuNodeType.Window, nodeModel.getAD_Window_ID());
		}
		else if (X_AD_Menu.ACTION_Process.equals(action))
		{
			builder.setType(MenuNodeType.Process, nodeModel.getAD_Process_ID());
		}
		else if (X_AD_Menu.ACTION_Report.equals(action))
		{
			builder.setType(MenuNodeType.Report, nodeModel.getAD_Process_ID());
		}
		else
		{
			return null;
		}

		return builder;
	}

	private MenuNode createNewRecordNode(final MenuNode node, final String caption, String captionBreadcrumb)
	{
		if (node.getType() != MenuNodeType.Window)
		{
			return null;
		}

		//
		// Caption (in menu)
		String captionEffective = caption;
		if (Check.isEmpty(captionEffective, true))
		{
			captionEffective = "New " + node.getCaption();
		}

		//
		// Caption (breadcrumb)
		String captionBreadcrumbEffective = captionBreadcrumb;
		if (Check.isEmpty(captionBreadcrumbEffective, true))
		{
			captionBreadcrumbEffective = node.getCaptionBreadcrumb();
		}

		return MenuNode.builder()
				.setId(node.getId() + "-new")
				.setCaption(captionEffective)
				.setCaptionBreadcrumb(captionBreadcrumbEffective)
				.setType(MenuNodeType.NewRecord, node.getElementId())
				.build();
	}

	private MTreeNode retrieveRootNodeModel()
	{
		final int adTreeId = getMenuTree_ID();
		if (adTreeId < 0)
		{
			throw new AdempiereException("Menu tree not found");
		}

		final MTree mTree = MTree.builder()
				.setCtx(getCtx())
				.setTrxName(ITrx.TRXNAME_None)
				.setAD_Tree_ID(adTreeId)
				.setEditable(false)
				.setClientTree(true)
				.setLanguage(getAD_Language())
				.build();
		final MTreeNode rootNodeModel = mTree.getRoot();
		return rootNodeModel;
	}

	private int getMenuTree_ID()
	{
		final IUserRolePermissions userRolePermissions = getUserRolePermissions();
		if (!userRolePermissions.hasPermission(IUserRolePermissions.PERMISSION_MenuAvailable))
		{
			return -1;
		}
		
		return userRolePermissions.getMenu_Tree_ID();
	}

	public MenuTreeLoader setAD_Language(String adLanguage)
	{
		this._adLanguage = adLanguage;
		return this;
	}
	
	private String getAD_Language()
	{
		return _adLanguage;
	}
}
