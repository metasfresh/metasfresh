package de.metas.ui.web.menu;

import java.util.LinkedHashMap;
import java.util.List;

import org.adempiere.ad.security.UserRolePermissionsKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.menu.MenuNode.MenuNodeType;
import de.metas.ui.web.menu.datatypes.json.JSONMenuNode;
import de.metas.ui.web.menu.datatypes.json.JSONMenuNodeType;
import de.metas.ui.web.menu.datatypes.json.JSONPatchMenuNodeRequest;
import de.metas.ui.web.menu.exception.NoMenuNodesFoundException;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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

@RestController
@RequestMapping(value = MenuRestController.ENDPOINT)
public class MenuRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/menu";

	private static final String PARAM_NodeId = "nodeId";
	private static final String PARAM_Depth = "depth";
	private static final String PARAM_Type = "type";
	private static final String PARAM_ElementId = "elementId";
	private static final String PARAM_NameQuery = "nameQuery";
	private static final String PARAM_IncludeLastNode = "inclusive";
	private static final String PARAM_ChildrenLimit = "childrenLimit";

	@Autowired
	private UserSession userSession;

	@Autowired
	private MenuTreeRepository menuTreeRepository;
	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;

	private MenuTree getMenuTree()
	{
		final UserRolePermissionsKey userRolePermissionsKey = userSession.getUserRolePermissionsKey();
		final String adLanguage = userSession.getAD_Language();
		return menuTreeRepository.getMenuTree(userRolePermissionsKey, adLanguage);
	}

	@GetMapping("/root")
	public JSONMenuNode getRoot(
			@RequestParam(name = PARAM_Depth, required = false, defaultValue = "1") final int depth,
			@RequestParam(name = PARAM_ChildrenLimit, required = false, defaultValue = "0") final int childrenLimit,
			@RequestParam(name = "favorites", required = false, defaultValue = "false") final boolean onlyFavorites)
	{
		userSession.assertLoggedIn();

		final MenuTree menuTree = getMenuTree();

		//
		// Get the root node with favorites only, if asked
		MenuNode rootNode;
		if (onlyFavorites)
		{
			rootNode = menuTree.getRootNodeWithFavoritesOnly(menuTreeRepository);
			if (rootNode.getChildren().isEmpty())
			{
				// If there were no favorites, return all
				rootNode = menuTree.getRootNode();
			}
		}
		else
		{
			rootNode = menuTree.getRootNode();
		}

		return JSONMenuNode.builder(rootNode)
				.setMaxDepth(depth)
				.setMaxChildrenPerNode(childrenLimit)
				.setIsFavoriteProvider(menuTreeRepository)
				.build();
	}

	@GetMapping("/node/{nodeId}")
	public JSONMenuNode getNode(
			@PathVariable(PARAM_NodeId) final String nodeId,
			@RequestParam(name = PARAM_Depth, required = false, defaultValue = "1") final int depth,
			@RequestParam(name = PARAM_ChildrenLimit, required = false, defaultValue = "0") final int childrenLimit)
	{
		userSession.assertLoggedIn();

		final MenuNode node = getMenuTree()
				.getNodeById(nodeId);

		return JSONMenuNode.builder(node)
				.setMaxDepth(depth)
				.setMaxChildrenPerNode(childrenLimit)
				.setIsFavoriteProvider(menuTreeRepository::isFavorite)
				.build();
	}

	@PatchMapping("/node/{nodeId}")
	public List<JSONMenuNode> patchNode(@PathVariable(PARAM_NodeId) final String nodeId, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		final JSONPatchMenuNodeRequest request = JSONPatchMenuNodeRequest.ofChangeEvents(events);

		final MenuTree menuTree = getMenuTree();
		final MenuNode node = menuTree.getNodeById(nodeId);

		final LinkedHashMap<String, MenuNode> changedMenuNodesById = new LinkedHashMap<>();

		if (request.getFavorite() != null)
		{
			menuTreeRepository.setFavorite(node, request.getFavorite());
			menuTree.streamNodesByAD_Menu_ID(node.getAD_Menu_ID())
					.forEach(changedNode -> changedMenuNodesById.put(changedNode.getId(), changedNode));
		}

		return JSONMenuNode.ofList(changedMenuNodesById.values(), menuTreeRepository);
	}

	@ApiOperation("Gets node's path (from root node) ")
	@GetMapping("/node/{nodeId}/path")
	public JSONMenuNode getPath(
			@PathVariable(PARAM_NodeId) final String nodeId,
			@RequestParam(name = PARAM_IncludeLastNode, required = false, defaultValue = "false") @ApiParam("Shall we include the last node") final boolean includeLastNode //
	)
	{
		userSession.assertLoggedIn();

		final List<MenuNode> path = getMenuTree()
				.getPath(nodeId);

		final boolean skipRootNode = true;
		return JSONMenuNode.ofPath(path, skipRootNode, includeLastNode, menuTreeRepository);
	}

	@ApiOperation("Gets breadcrumb menu to be displayed when user clicks on that node in the breadcrumb")
	@GetMapping("/node/{nodeId}/breadcrumbMenu")
	public List<JSONMenuNode> getNodeBreadcrumbMenu(@PathVariable(PARAM_NodeId) final String nodeId)
	{
		userSession.assertLoggedIn();

		final List<MenuNode> children = getMenuTree().getNodeById(nodeId)
				.getChildren()
				.stream()
				.filter(child -> child.isEffectiveLeafNode())
				.collect(ImmutableList.toImmutableList());

		return JSONMenuNode.ofList(children, menuTreeRepository);
	}

	@GetMapping("/elementPath")
	public JSONMenuNode getPath(
			@RequestParam(name = PARAM_Type, required = true) final JSONMenuNodeType jsonType,
			@RequestParam(name = PARAM_ElementId, required = true) final String elementIdStr,
			@RequestParam(name = PARAM_IncludeLastNode, required = false, defaultValue = "false") @ApiParam("Shall we include the last node") final boolean includeLastNode)
	{
		userSession.assertLoggedIn();

		final MenuNodeType menuNodeType = jsonType.toMenuNodeType();
		final DocumentId elementId = DocumentId.of(elementIdStr);
		final List<MenuNode> path = getMenuTree()
				.getPath(menuNodeType, elementId)
				.orElseGet(() -> getPathOfMissingElement(menuNodeType, elementId, userSession.getAD_Language()));

		final boolean skipRootNode = true;
		return JSONMenuNode.ofPath(path, skipRootNode, includeLastNode, menuTreeRepository);
	}

	private List<MenuNode> getPathOfMissingElement(final MenuNodeType type, final DocumentId elementId, final String adLanguage)
	{
		if (type == MenuNodeType.Window)
		{
			final String caption = documentDescriptorFactory.getDocumentDescriptor(WindowId.of(elementId))
					.getLayout()
					.getCaption(adLanguage);

			return ImmutableList.of(MenuNode.builder()
					.setType(type, elementId)
					.setCaption(caption)
					.setAD_Menu_ID_None()
					.build());
		}
		else
		{
			throw new NoMenuNodesFoundException("No menu node found for type=" + type + " and elementId=" + elementId);
		}
	}

	@GetMapping("/queryPaths")
	public JSONMenuNode query(
			@RequestParam(name = PARAM_NameQuery, required = true) final String nameQuery,
			@RequestParam(name = PARAM_ChildrenLimit, required = false, defaultValue = "0") final int childrenLimit,
			@RequestParam(name = "childrenInclusive", required = false, defaultValue = "false") @ApiParam("true if groups that were matched shall be populated with it's leafs, even if those leafs are not matching") final boolean includeLeafsIfGroupAccepted)
	{
		userSession.assertLoggedIn();

		final MenuNode rootFiltered = getMenuTree()
				.filter(nameQuery, includeLeafsIfGroupAccepted);

		if (rootFiltered == null)
		{
			throw new NoMenuNodesFoundException();
		}
		if (rootFiltered.getChildren().isEmpty())
		{
			throw new NoMenuNodesFoundException();
		}

		return JSONMenuNode.builder(rootFiltered)
				.setMaxLeafNodes(childrenLimit)
				.setIsFavoriteProvider(menuTreeRepository)
				.build();
	}

}
