package de.metas.ui.web.menu;

import java.util.List;

import org.adempiere.ad.security.UserRolePermissionsKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.login.LoginService;
import de.metas.ui.web.menu.datatypes.json.JSONMenuNode;
import de.metas.ui.web.menu.datatypes.json.JSONMenuNodeType;
import de.metas.ui.web.session.UserSession;

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

@RestController
@RequestMapping(value = MenuRestController.ENDPOINT)
public class MenuRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/menu";

	@Autowired
	private LoginService loginService;

	@Autowired
	private UserSession userSession;

	@Autowired
	private MenuTreeRepository menuTreeRepository;

	private MenuTree getMenuTree()
	{
		final UserRolePermissionsKey userRolePermissionsKey = userSession.getUserRolePermissionsKey();
		return menuTreeRepository.getMenuTree(userRolePermissionsKey);
	}

	@RequestMapping(value = "/rootNodes", method = RequestMethod.GET)
	public List<JSONMenuNode> getRootNodes()
	{
		loginService.autologin();

		final List<MenuNode> nodes = getMenuTree()
				.getRootNode()
				.getChildren();

		return JSONMenuNode.ofList(nodes);
	}

	@RequestMapping(value = "/children", method = RequestMethod.GET)
	public List<JSONMenuNode> getChildren(
			@RequestParam(name = "nodeId", required = true) final int nodeId //
	)
	{
		loginService.autologin();

		final List<MenuNode> children = getMenuTree()
				.getNodeById(nodeId)
				.getChildren();

		return JSONMenuNode.ofList(children);
	}

	@RequestMapping(value = "/path", method = RequestMethod.GET)
	public List<JSONMenuNode> getPath(
			@RequestParam(name = "nodeId", required = true) final int nodeId //
	)
	{
		loginService.autologin();

		final List<MenuNode> children = getMenuTree()
				.getPath(nodeId);

		return JSONMenuNode.ofList(children);
	}

	@RequestMapping(value = "/elementPath", method = RequestMethod.GET)
	public List<JSONMenuNode> getPath(
			@RequestParam(name = "type", required = true) final JSONMenuNodeType jsonType //
			, @RequestParam(name = "elementId", required = true) final int elementId //
	)
	{
		loginService.autologin();

		final List<MenuNode> children = getMenuTree()
				.getPath(jsonType.toMenuNodeType(), elementId);

		return JSONMenuNode.ofList(children);
	}

}
