package de.metas.ui.web.window.descriptor;

<<<<<<< HEAD
=======
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.util.GuavaCollectors;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

<<<<<<< HEAD
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public static final Builder builder()
=======
	public static Builder builder()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return new Builder();
	}

	private final String internalName;
<<<<<<< HEAD
	private final List<DocumentLayoutElementGroupDescriptor> elementGroups;
=======
	@Getter private final List<DocumentLayoutElementGroupDescriptor> elementGroups;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	private DocumentLayoutColumnDescriptor(final Builder builder)
	{
		internalName = builder.internalName;
		elementGroups = ImmutableList.copyOf(builder.buildElementGroups());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("internalName", internalName)
				.add("elementGroups", elementGroups.isEmpty() ? null : elementGroups)
				.toString();
	}

<<<<<<< HEAD
	public List<DocumentLayoutElementGroupDescriptor> getElementGroups()
	{
		return elementGroups;
	}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public boolean hasElementGroups()
	{
		return !elementGroups.isEmpty();
	}

	public static final class Builder
	{
		private static final Logger logger = LogManager.getLogger(DocumentLayoutColumnDescriptor.Builder.class);

		private String internalName;
		private final List<DocumentLayoutElementGroupDescriptor.Builder> elementGroupsBuilders = new ArrayList<>();

		private Builder()
		{
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("internalName", internalName)
					.add("elementGroups-count", elementGroupsBuilders.size())
					.toString();
		}

		public DocumentLayoutColumnDescriptor build()
		{
			final DocumentLayoutColumnDescriptor result = new DocumentLayoutColumnDescriptor(this);

			logger.trace("Built {} for {}", result, this);
			return result;
		}

		private List<DocumentLayoutElementGroupDescriptor> buildElementGroups()
		{
			return elementGroupsBuilders
					.stream()
<<<<<<< HEAD
					.map(elementGroupBuilder -> elementGroupBuilder.build())
					.filter(elementGroup -> checkValid(elementGroup))
=======
					.map(DocumentLayoutElementGroupDescriptor.Builder::build)
					.filter(this::checkValid)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					.collect(GuavaCollectors.toImmutableList());
		}

		private boolean checkValid(final DocumentLayoutElementGroupDescriptor elementGroup)
		{
			if(!elementGroup.hasElementLines())
			{
				logger.trace("Skip adding {} to {} because it does not have element line", elementGroup, this);
				return false;
			}

			return true;
		}

		public Builder setInternalName(String internalName)
		{
			this.internalName = internalName;
			return this;
		}

<<<<<<< HEAD
		public Builder addElementTabs(@NonNull final List<DocumentLayoutElementGroupDescriptor.Builder> elementGroupBuilders)
=======
		public Builder addElementGroups(@NonNull final List<DocumentLayoutElementGroupDescriptor.Builder> elementGroupBuilders)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			elementGroupsBuilders.addAll(elementGroupBuilders);
			return this;
		}

		public Builder addElementGroup(@NonNull final DocumentLayoutElementGroupDescriptor.Builder elementGroupBuilder)
		{
			elementGroupsBuilders.add(elementGroupBuilder);
			return this;
		}

		public Stream<DocumentLayoutElementDescriptor.Builder> streamElementBuilders()
		{
			return elementGroupsBuilders.stream().flatMap(DocumentLayoutElementGroupDescriptor.Builder::streamElementBuilders);
		}
	}
}
