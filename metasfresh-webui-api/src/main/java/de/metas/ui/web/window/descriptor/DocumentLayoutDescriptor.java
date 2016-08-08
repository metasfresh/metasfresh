package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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
public final class DocumentLayoutDescriptor implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	/** i.e. AD_Window_ID */
	private final String type;
	@JsonInclude(Include.NON_NULL)
	private final String docNoField;
	@JsonInclude(Include.NON_NULL)
	private final String docStatusField;
	@JsonInclude(Include.NON_NULL)
	private final String docActionField;

	@JsonInclude(Include.NON_EMPTY)
	private final List<DocumentLayoutSectionDescriptor> sections;

	@JsonInclude(Include.NON_EMPTY)
	private final List<DocumentLayoutDetailDescriptor> details;

	private DocumentLayoutDescriptor(final Builder builder)
	{
		super();
		type = String.valueOf(builder.AD_Window_ID);
		docNoField = builder.docNoField;
		docStatusField = builder.docStatusField;
		docActionField = builder.docActionField;

		sections = ImmutableList.copyOf(builder.sections);
		details = ImmutableList.copyOf(builder.details);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("type", type)
				.add("sections", sections.isEmpty() ? null : sections)
				.add("details", details.isEmpty() ? null : details)
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

	public List<DocumentLayoutSectionDescriptor> getSections()
	{
		return sections;
	}

	public List<DocumentLayoutDetailDescriptor> getDetails()
	{
		return details;
	}

	public static final class Builder
	{
		private int AD_Window_ID;
		private String docNoField;
		private String docStatusField;
		private String docActionField;
		private final List<DocumentLayoutSectionDescriptor> sections = new ArrayList<>();
		private final List<DocumentLayoutDetailDescriptor> details = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public DocumentLayoutDescriptor build()
		{
			return new DocumentLayoutDescriptor(this);
		}

		public Builder setAD_Window_ID(final int AD_Window_ID)
		{
			this.AD_Window_ID = AD_Window_ID;
			return this;
		}

		public Builder setDocNoField(final String docNoField)
		{
			this.docNoField = docNoField;
			return this;
		}

		public Builder setDocStatusField(final String docStatusField)
		{
			this.docStatusField = docStatusField;
			return this;
		}

		public Builder setDocActionField(final String docActionField)
		{
			this.docActionField = docActionField;
			return this;
		}

		public Builder addSection(final DocumentLayoutSectionDescriptor section)
		{
			sections.add(section);
			return this;
		}

		public Builder addSections(final Collection<DocumentLayoutSectionDescriptor> sections)
		{
			this.sections.addAll(sections);
			return this;
		}

		public Builder addDetail(final DocumentLayoutDetailDescriptor detail)
		{
			details.add(detail);
			return this;
		}
	}
}
