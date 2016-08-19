package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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

	@JsonProperty("docNoField")
	@JsonInclude(Include.NON_NULL)
	private final String docNoField;

	@JsonProperty("docStatusField")
	@JsonInclude(Include.NON_NULL)
	private final String docStatusField;

	@JsonProperty("docActionField")
	@JsonInclude(Include.NON_NULL)
	private final String docActionField;

	@JsonProperty("sections")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutSection> sections;

	@JsonProperty("tabs")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutTab> tabs;

	private JSONDocumentLayout(final DocumentLayoutDescriptor layout)
	{
		super();
		type = String.valueOf(layout.getAD_Window_ID());
		docNoField = layout.getDocNoField();
		docStatusField = layout.getDocStatusField();
		docActionField = layout.getDocActionField();
		sections = JSONDocumentLayoutSection.ofList(layout.getSections());
		tabs = JSONDocumentLayoutTab.ofList(layout.getDetails());
	}

	@JsonCreator
	private JSONDocumentLayout(
			@JsonProperty("type") final String type//
			, @JsonProperty("docNoField") final String docNoField//
			, @JsonProperty("docStatusField") final String docStatusField//
			, @JsonProperty("docActionField") final String docActionField//
			, @JsonProperty("sections") final List<JSONDocumentLayoutSection> sections //
			, @JsonProperty("tabs") final List<JSONDocumentLayoutTab> tabs //
	)
	{
		super();
		this.type = type;
		this.docNoField = docNoField;
		this.docStatusField = docStatusField;
		this.docActionField = docActionField;
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

	public String getDocNoField()
	{
		return docNoField;
	}

	public String getDocStatusField()
	{
		return docStatusField;
	}

	public String getDocActionField()
	{
		return docActionField;
	}

	public List<JSONDocumentLayoutSection> getSections()
	{
		return sections;
	}

	public List<JSONDocumentLayoutTab> getTabs()
	{
		return tabs;
	}
}
