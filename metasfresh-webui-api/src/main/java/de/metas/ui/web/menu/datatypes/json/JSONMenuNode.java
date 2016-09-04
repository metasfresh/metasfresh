package de.metas.ui.web.menu.datatypes.json;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.menu.MenuNode;

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

@SuppressWarnings("serial")
public final class JSONMenuNode implements Serializable
{
	public static final List<JSONMenuNode> ofList(final Collection<MenuNode> nodes)
	{
		return nodes.stream()
				.map(JSONMenuNode::of)
				.collect(GuavaCollectors.toImmutableList());
	}

	private static final JSONMenuNode of(final MenuNode node)
	{
		return new JSONMenuNode(node);
	}

	@JsonProperty("nodeId")
	private final int nodeId;
	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("type")
	private final JSONMenuNodeType type;
	@JsonProperty("elementId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer elementId;

	private JSONMenuNode(final MenuNode node)
	{
		super();
		nodeId = node.getId();
		caption = node.getCaption();
		type = JSONMenuNodeType.fromNullable(node.getType());
		final int elementId = node.getElementId();
		this.elementId = elementId > 0 ? elementId : null;
	}

	@JsonCreator
	private JSONMenuNode(
			@JsonProperty("nodeId") final int nodeId //
			, @JsonProperty("caption") final String caption //
			, @JsonProperty("type") final JSONMenuNodeType type //
			, @JsonProperty("elementId") final Integer elementId //
	)
	{
		super();
		this.nodeId = nodeId;
		this.caption = caption;
		this.type = type;
		this.elementId = elementId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("nodeId", nodeId)
				.add("caption", caption)
				.add("type", type)
				.add("elementId", elementId)
				.toString();
	}

	public int getNodeId()
	{
		return nodeId;
	}

	public String getCaption()
	{
		return caption;
	}

	public JSONMenuNodeType getType()
	{
		return type;
	}

	public Integer getElementId()
	{
		return elementId;
	}
}
