package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

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
	private final int AD_Window_ID;
	private DocumentLayoutElementDescriptor documentNoElement;
	private DocumentLayoutElementDescriptor docActionElement;

	private final List<DocumentLayoutSectionDescriptor> sections;

	private final List<DocumentLayoutDetailDescriptor> details;

	private DocumentLayoutDescriptor(final Builder builder)
	{
		super();
		AD_Window_ID = builder.AD_Window_ID;
		documentNoElement = builder.documentNoElement;
		docActionElement = builder.docActionElement;

		sections = ImmutableList.copyOf(builder.buildSections());
		details = ImmutableList.copyOf(builder.buildDetails());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("AD_Window_ID", AD_Window_ID)
				.add("sections", sections.isEmpty() ? null : sections)
				.add("details", details.isEmpty() ? null : details)
				.toString();
	}

	public int getAD_Window_ID()
	{
		return AD_Window_ID;
	}

	public DocumentLayoutElementDescriptor getDocumentNoElement()
	{
		return documentNoElement;
	}

	public DocumentLayoutElementDescriptor getDocActionElement()
	{
		return docActionElement;
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
		private static final Logger logger = LogManager.getLogger(DocumentLayoutDescriptor.Builder.class);

		private int AD_Window_ID;
		private DocumentLayoutElementDescriptor documentNoElement;
		private DocumentLayoutElementDescriptor docActionElement;
		private final List<DocumentLayoutSectionDescriptor.Builder> sectionBuilders = new ArrayList<>();
		private final List<DocumentLayoutDetailDescriptor.Builder> detailsBuilders = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public DocumentLayoutDescriptor build()
		{
			return new DocumentLayoutDescriptor(this);
		}

		private List<DocumentLayoutSectionDescriptor> buildSections()
		{
			return sectionBuilders
					.stream()
					.map(sectionBuilder -> sectionBuilder.build())
					.filter(section -> section.hasColumns())
					.collect(GuavaCollectors.toImmutableList());
		}

		private List<DocumentLayoutDetailDescriptor> buildDetails()
		{
			return detailsBuilders
					.stream()
					.map(detailBuilder -> detailBuilder.build())
					.filter(detail -> detail.hasElements())
					.collect(GuavaCollectors.toImmutableList());
		}

		public Builder setAD_Window_ID(final int AD_Window_ID)
		{
			this.AD_Window_ID = AD_Window_ID;
			return this;
		}

		public Builder setDocumentNoElement(final DocumentLayoutElementDescriptor documentNoElement)
		{
			this.documentNoElement = documentNoElement;
			return this;
		}

		public Builder setDocActionElement(final DocumentLayoutElementDescriptor docActionElement)
		{
			this.docActionElement = docActionElement;
			return this;
		}

		public Builder addSection(final DocumentLayoutSectionDescriptor.Builder sectionBuilder)
		{
			Check.assumeNotNull(sectionBuilder, "Parameter sectionBuilder is not null");
			sectionBuilders.add(sectionBuilder);
			return this;
		}

		public Builder addSections(final Collection<DocumentLayoutSectionDescriptor.Builder> sectionsBuilders)
		{
			this.sectionBuilders.addAll(sectionsBuilders);
			return this;
		}

		/**
		 * Adds detail/tab if it's valid.
		 * 
		 * @param detailBuilder detail/tab builder
		 */
		public Builder addDetailIfValid(@Nullable final DocumentLayoutDetailDescriptor.Builder detailBuilder)
		{
			if (detailBuilder == null)
			{
				return this;
			}

			if (!detailBuilder.hasElements())
			{
				logger.trace("Skip adding detail tab to layout because it does not have elements: {}", detailBuilder);
				return this;
			}

			detailsBuilders.add(detailBuilder);
			return this;
		}
	}
}
