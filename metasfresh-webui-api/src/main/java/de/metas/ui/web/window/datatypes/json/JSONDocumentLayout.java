package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import io.swagger.annotations.ApiModel;

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

@ApiModel("layout")
@SuppressWarnings("serial")
public final class JSONDocumentLayout implements Serializable
{
	public static final JSONDocumentLayout of(final DocumentLayoutDescriptor layout)
	{
		return new JSONDocumentLayout(layout);
	}

	/** i.e. AD_Window_ID */
	@JsonProperty("type")
	private final String type;

	@JsonProperty("documentNoElement")
	@JsonInclude(Include.NON_NULL)
	private final JSONDocumentLayoutElement documentNoElement;

	@JsonProperty("docActionElement")
	@JsonInclude(Include.NON_NULL)
	private final JSONDocumentLayoutElement docActionElement;

	@JsonProperty("sections")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutSection> sections;

	@JsonProperty("tabs")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutTab> tabs;

	/** Other properties */
	private final Map<String, Object> otherProperties = new LinkedHashMap<>();

	private JSONDocumentLayout(final DocumentLayoutDescriptor layout)
	{
		super();
		type = String.valueOf(layout.getAD_Window_ID());
		documentNoElement = JSONDocumentLayoutElement.fromNullable(layout.getDocumentNoElement());
		docActionElement = JSONDocumentLayoutElement.fromNullable(layout.getDocActionElement());
		sections = JSONDocumentLayoutSection.ofList(layout.getSections());
		tabs = JSONDocumentLayoutTab.ofList(layout.getDetails());

		if (WindowConstants.isProtocolDebugging())
		{
			putDebugProperties(layout.getDebugProperties());
		}
	}

	@JsonCreator
	private JSONDocumentLayout(
			@JsonProperty("type") final String type//
			, @JsonProperty("documentNoElement") final JSONDocumentLayoutElement documentNoElement//
			, @JsonProperty("docActionElement") final JSONDocumentLayoutElement docActionElement//
			, @JsonProperty("sections") final List<JSONDocumentLayoutSection> sections //
			, @JsonProperty("tabs") final List<JSONDocumentLayoutTab> tabs //
	)
	{
		super();
		this.type = type;
		this.documentNoElement = documentNoElement;
		this.docActionElement = docActionElement;
		this.sections = sections == null ? ImmutableList.of() : ImmutableList.copyOf(sections);
		this.tabs = tabs == null ? ImmutableList.of() : ImmutableList.copyOf(tabs);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("type", type)
				.add("sections", sections.isEmpty() ? null : sections)
				.add("tabs", tabs.isEmpty() ? null : tabs)
				.toString();
	}

	public String getType()
	{
		return type;
	}

	public JSONDocumentLayoutElement getDocumentNoElement()
	{
		return documentNoElement;
	}

	public JSONDocumentLayoutElement getDocActionElement()
	{
		return docActionElement;
	}

	public List<JSONDocumentLayoutSection> getSections()
	{
		return sections;
	}

	public List<JSONDocumentLayoutTab> getTabs()
	{
		return tabs;
	}

	@JsonAnyGetter
	public Map<String, Object> getOtherProperties()
	{
		return otherProperties;
	}

	@JsonAnySetter
	/* package */void putOtherProperty(final String name, final Object jsonValue)
	{
		otherProperties.put(name, jsonValue);
	}

	private JSONDocumentLayout putDebugProperty(final String name, final Object jsonValue)
	{
		otherProperties.put("debug-" + name, jsonValue);
		return this;
	}

	private void putDebugProperties(final Map<String, ?> debugProperties)
	{
		if (debugProperties == null || debugProperties.isEmpty())
		{
			return;
		}

		for (final Map.Entry<String, ?> e : debugProperties.entrySet())
		{
			putDebugProperty(e.getKey(), e.getValue());
		}
	}

}
