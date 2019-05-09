package de.metas.ui.web.menu;

import java.util.Enumeration;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.tree.AdTreeId;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.model.X_AD_Menu;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.menu.AdMenuId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.UserRolePermissionsKey;
import de.metas.security.permissions.UserMenuInfo;
import de.metas.ui.web.menu.MenuNode.MenuNodeType;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

final class MenuTreeLoader
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

	private UserRolePermissionsKey _userRolePermissionsKey;
	private IUserRolePermissions _userRolePermissions; // lazy
	private String _adLanguage;
	private long _version = -1;

	private MenuTreeLoader()
	{
		super();
	}

	public MenuTreeLoader setUserRolePermissionsKey(final UserRolePermissionsKey userRolePermissionsKey)
	{
		_userRolePermissionsKey = userRolePermissionsKey;
		return this;
	}

	@NonNull
	private UserRolePermissionsKey getUserRolePermissionsKey()
	{
		return _userRolePermissionsKey;
	}

	private IUserRolePermissions getUserRolePermissions()
	{
		if (_userRolePermissions == null)
		{
			final UserRolePermissionsKey userRolePermissionsKey = getUserRolePermissionsKey();
			_userRolePermissions = userRolePermissionsDAO.getUserRolePermissions(userRolePermissionsKey);
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

		final long version = getVersion();
		return MenuTree.of(version, rootNode);
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
				.setAD_Menu_ID(nodeModel.getNode_ID())
				.setCaption(caption)
				.setCaptionBreadcrumb(captionBreadcrumb)
				.setMainTableName(nodeModel.getMainTableName());

		final String action = nodeModel.getImageIndiactor();
		if (nodeModel.isSummary())
		{
			builder.setTypeGroup();
		}
		else if (depth == DEPTH_RootChildren)
		{
			logger.warn("Skip creating leaf nodes for root: {}", nodeModel);
			return null;
		}
		else if (X_AD_Menu.ACTION_Window.equals(action))
		{
			final DocumentId elementId = DocumentId.of(nodeModel.getAD_Window_ID());
			builder.setType(MenuNodeType.Window, elementId);
		}
		else if (X_AD_Menu.ACTION_Process.equals(action))
		{
			final DocumentId elementId = ProcessId.ofAD_Process_ID(nodeModel.getAD_Process_ID()).toDocumentId();
			builder.setType(MenuNodeType.Process, elementId);
		}
		else if (X_AD_Menu.ACTION_Report.equals(action))
		{
			final DocumentId elementId = ProcessId.ofAD_Process_ID(nodeModel.getAD_Process_ID()).toDocumentId();
			builder.setType(MenuNodeType.Report, elementId);
		}
		else if (X_AD_Menu.ACTION_Board.equals(action))
		{
			final DocumentId elementId = DocumentId.of(nodeModel.getWEBUI_Board_ID());
			builder.setType(MenuNodeType.Board, elementId);
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
				.setAD_Menu_ID(node.getAD_Menu_ID())
				.setCaption(captionEffective)
				.setCaptionBreadcrumb(captionBreadcrumbEffective)
				.setType(MenuNodeType.NewRecord, node.getElementId())
				.setMainTableName(node.getMainTableName())
				.build();
	}

	private MTreeNode retrieveRootNodeModel()
	{
		final UserMenuInfo userMenuInfo = getUserMenuInfo();
		final AdTreeId adTreeId = userMenuInfo.getAdTreeId();
		if (adTreeId == null)
		{
			throw new AdempiereException("Menu tree not found");
		}

		final MTree mTree = MTree.builder()
				.setCtx(Env.getCtx())
				.setTrxName(ITrx.TRXNAME_None)
				.setAD_Tree_ID(adTreeId.getRepoId())
				.setEditable(false)
				.setClientTree(true)
				.setLanguage(getAD_Language())
				.build();

		final MTreeNode rootNodeModel = mTree.getRoot();
		AdMenuId rootMenuIdEffective = userMenuInfo.getRootMenuId();
		if (rootMenuIdEffective != null)
		{
			final MTreeNode rootNodeModelEffective = rootNodeModel.findNode(rootMenuIdEffective.getRepoId());
			if (rootNodeModelEffective != null)
			{
				return rootNodeModelEffective;
			}
			else
			{
				logger.warn("Cannot find Root_Menu_ID={} in {}", rootMenuIdEffective, mTree);
			}
		}

		return rootNodeModel;
	}

	private UserMenuInfo getUserMenuInfo()
	{
		final IUserRolePermissions userRolePermissions = getUserRolePermissions();
		if (!userRolePermissions.hasPermission(IUserRolePermissions.PERMISSION_MenuAvailable))
		{
			return UserMenuInfo.NONE;
		}

		return userRolePermissions.getMenuInfo();
	}

	public MenuTreeLoader setAD_Language(String adLanguage)
	{
		this._adLanguage = adLanguage;
		return this;
	}

	@NonNull
	private String getAD_Language()
	{
		return _adLanguage;
	}

	private long getVersion()
	{
		if (_version < 0)
		{
			_version = userRolePermissionsDAO.getCacheVersion();
		}
		return _version;
	}
}
