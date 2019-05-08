package de.metas.ui.web.window.descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.util.GuavaCollectors;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DocumentLayoutColumnDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String internalName;
	private final List<DocumentLayoutElementTabDescriptor> elementTabs;

	private DocumentLayoutColumnDescriptor(final Builder builder)
	{
		internalName = builder.internalName;
		elementTabs = ImmutableList.copyOf(builder.buildElementTabs());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("internalName", internalName)
				.add("elementTabs", elementTabs.isEmpty() ? null : elementTabs)
				.toString();
	}

	public List<DocumentLayoutElementTabDescriptor> getElementTabs()
	{
		return elementTabs;
	}

	public boolean hasElementTabs()
	{
		return !elementTabs.isEmpty();
	}

	public static final class Builder
	{
		private static final Logger logger = LogManager.getLogger(DocumentLayoutColumnDescriptor.Builder.class);

		private String internalName;
		private final List<DocumentLayoutElementTabDescriptor.Builder> elementTabsBuilders = new ArrayList<>();

		private Builder()
		{
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("internalName", internalName)
					.add("elementTabs-count", elementTabsBuilders.size())
					.toString();
		}

		public DocumentLayoutColumnDescriptor build()
		{
			final DocumentLayoutColumnDescriptor result = new DocumentLayoutColumnDescriptor(this);

			logger.trace("Built {} for {}", result, this);
			return result;
		}

		private List<DocumentLayoutElementTabDescriptor> buildElementTabs()
		{
			return elementTabsBuilders
					.stream()
					.map(elementTabBuilder -> elementTabBuilder.build())
					.filter(elementTab -> checkValid(elementTab))
					.collect(GuavaCollectors.toImmutableList());
		}

		private boolean checkValid(final DocumentLayoutElementTabDescriptor elementTab)
		{
			if(!elementTab.hasElementLines())
			{
				logger.trace("Skip adding {} to {} because it does not have element line", elementTab, this);
				return false;
			}

			return true;
		}

		public Builder setInternalName(String internalName)
		{
			this.internalName = internalName;
			return this;
		}

		public Builder addElementTabs(@NonNull final List<DocumentLayoutElementTabDescriptor.Builder> elementTabBuilders)
		{
			elementTabsBuilders.addAll(elementTabBuilders);
			return this;
		}

		public Builder addElementTab(@NonNull final DocumentLayoutElementTabDescriptor.Builder elementTabBuilder)
		{
			elementTabsBuilders.add(elementTabBuilder);
			return this;
		}

		public Stream<DocumentLayoutElementDescriptor.Builder> streamElementBuilders()
		{
			return elementTabsBuilders.stream().flatMap(DocumentLayoutElementTabDescriptor.Builder::streamElementBuilders);
		}
	}
}
