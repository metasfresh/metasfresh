package de.metas.ui.web.window.datatypes.json;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor.CaptionMode;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor.ClosableMode;
import io.swagger.annotations.ApiModel;
import lombok.NonNull;

import javax.annotation.Nullable;

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

@ApiModel("section")
public final class JSONDocumentLayoutSection
{
	static List<JSONDocumentLayoutSection> ofSectionsList(final List<DocumentLayoutSectionDescriptor> sections, final JSONDocumentLayoutOptions jsonOpts)
	{
		return sections.stream()
				.map(section -> new JSONDocumentLayoutSection(section, jsonOpts))
				.collect(ImmutableList.toImmutableList());
	}

	public enum JSONClosableMode
	{
		ALWAYS_OPEN,

		INITIALLY_OPEN,

		INITIALLY_CLOSED;

		public static JSONClosableMode ofClosableMode(@NonNull final ClosableMode closableMode)
		{
			switch (closableMode)
			{
				case ALWAYS_OPEN:
					return ALWAYS_OPEN;
				case INITIALLY_CLOSED:
					return INITIALLY_CLOSED;
				case INITIALLY_OPEN:
					return INITIALLY_OPEN;
				default:
					throw new AdempiereException("Unexpected closableMode=" + closableMode);
			}
		}
	}

	@JsonProperty("title")
	@JsonInclude(Include.NON_EMPTY)
	private final String title;

	@JsonProperty("description")
	@JsonInclude(Include.NON_EMPTY)
	private final String description;

	@JsonProperty("uiStyle")
	@JsonInclude(Include.NON_EMPTY)
	private final String uiStyle;

	@JsonProperty("columns")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutColumn> columns;

	@JsonProperty("closableMode")
	@JsonInclude(Include.NON_EMPTY)
	private final JSONClosableMode closableMode;

	private JSONDocumentLayoutSection(
			final DocumentLayoutSectionDescriptor section,
			final JSONDocumentLayoutOptions options)
	{
		this.title = extractTitle(section, options);
		this.uiStyle = section.getUiStyle();

		this.description = section.getDescription(options.getAdLanguage()).trim();
		this.columns = JSONDocumentLayoutColumn.ofList(section.getColumns(), options);
		this.closableMode = JSONClosableMode.ofClosableMode(section.getClosableMode());
	}

	@Nullable
	private static String extractTitle(
			@NonNull final DocumentLayoutSectionDescriptor section,
			@NonNull final JSONDocumentLayoutOptions options)
	{
		if (CaptionMode.DISPLAY.equals(section.getCaptionMode()))
		{
			return section.getCaption(options.getAdLanguage()).trim();
		}
		else if (CaptionMode.DISPLAY_IN_ADV_EDIT.equals(section.getCaptionMode()))
		{
			if (options.isShowAdvancedFields())
			{
				return section.getCaption(options.getAdLanguage()).trim();
			}
			else
			{
				return null;
			}
		}
		else if (CaptionMode.DONT_DISPLAY.equals(section.getCaptionMode()))
		{
			return null;
		}

		throw new AdempiereException("Unexpected captionMode=" + section.getCaptionMode())
				.appendParametersToMessage()
				.setParameter("documentLayoutSectionDescriptor", section);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("columns", columns)
				.toString();
	}

	public List<JSONDocumentLayoutColumn> getColumns()
	{
		return columns;
	}
}
