package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.Getter;
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

@SuppressWarnings("serial")
public final class DocumentLayoutSectionDescriptor implements Serializable
{
	public enum ClosableMode
	{
		ALWAYS_OPEN,

		INITIALLY_OPEN,

		INITIALLY_CLOSED
	}

	public enum CaptionMode
	{
		DISPLAY_IN_ADV_EDIT,

		DISPLAY,

		DONT_DISPLAY;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	private final ITranslatableString caption;
	private final ITranslatableString description;
	private final String internalName;

	@Getter
	private final ClosableMode closableMode;
	@Getter
	private final CaptionMode captionMode;
	@Getter
	@Nullable private final String uiStyle;

	@Getter
	private final List<DocumentLayoutColumnDescriptor> columns;

	private DocumentLayoutSectionDescriptor(@NonNull final Builder builder)
	{
		caption = builder.caption;
		description = builder.description;
		internalName = builder.internalName;
		closableMode = builder.closableMode;
		captionMode = builder.captionMode;
		uiStyle = builder.uiStyle;
		columns = ImmutableList.copyOf(builder.buildColumns());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("internalName", internalName)
				.add("closableMode", closableMode)
				.add("columns", columns.isEmpty() ? null : columns)
				.toString();
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public boolean hasColumns()
	{
		return !columns.isEmpty();
	}

	public static final class Builder
	{
		private static final Logger logger = LogManager.getLogger(DocumentLayoutSectionDescriptor.Builder.class);

		private ITranslatableString caption = TranslatableStrings.empty();
		private ITranslatableString description = TranslatableStrings.empty();
		private String internalName;
		@Nullable
		private String uiStyle;
		private final List<DocumentLayoutColumnDescriptor.Builder> columnsBuilders = new ArrayList<>();

		@Getter
		private String invalidReason;

		private ClosableMode closableMode = ClosableMode.ALWAYS_OPEN;

		private CaptionMode captionMode = CaptionMode.DISPLAY_IN_ADV_EDIT;

		private Builder()
		{
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("internalName", internalName)
					.add("closableMode", closableMode)
					.add("invalidReason", invalidReason)
					.add("columns-count", columnsBuilders.size())
					.toString();
		}

		public DocumentLayoutSectionDescriptor build()
		{
			if (isInvalid())
			{
				throw new IllegalStateException("Builder is invalid: " + getInvalidReason());
			}
			return new DocumentLayoutSectionDescriptor(this);
		}

		private List<DocumentLayoutColumnDescriptor> buildColumns()
		{
			return columnsBuilders
					.stream()
					.map(columnBuilder -> columnBuilder.build())
					.filter(column -> checkValid(column))
					.collect(GuavaCollectors.toImmutableList());
		}

		private boolean checkValid(final DocumentLayoutColumnDescriptor column)
		{
			if (!column.hasElementGroups())
			{
				logger.trace("Skip adding {} to {} because it does not have elements groups", column, this);
				return false;
			}

			return true;
		}

		public Builder setCaption(@NonNull final ITranslatableString caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder setDescription(@NonNull final ITranslatableString description)
		{
			this.description = description;
			return this;
		}

		public Builder setInternalName(final String internalName)
		{
			this.internalName = internalName;
			return this;
		}

		public Builder setUIStyle(@Nullable final String uiStyle)
		{
			this.uiStyle = uiStyle;
			return this;
		}

		public Builder addColumn(final DocumentLayoutColumnDescriptor.Builder columnBuilder)
		{
			Check.assumeNotNull(columnBuilder, "Parameter columnBuilder is not null");
			columnsBuilders.add(columnBuilder);
			return this;
		}

		public Builder addColumn(final List<DocumentLayoutElementDescriptor.Builder> elementsBuilders)
		{
			if (elementsBuilders == null || elementsBuilders.isEmpty())
			{
				return this;
			}

			final DocumentLayoutElementGroupDescriptor.Builder elementsGroupBuilder = DocumentLayoutElementGroupDescriptor.builder();
			elementsBuilders.stream()
					.map(elementBuilder -> DocumentLayoutElementLineDescriptor.builder().addElement(elementBuilder))
					.forEach(elementLineBuilder -> elementsGroupBuilder.addElementLine(elementLineBuilder));

			final DocumentLayoutColumnDescriptor.Builder column = DocumentLayoutColumnDescriptor.builder().addElementGroup(elementsGroupBuilder);

			addColumn(column);
			return this;
		}

		public Builder setInvalid(final String invalidReason)
		{
			Check.assumeNotEmpty(invalidReason, "invalidReason is not empty");
			this.invalidReason = invalidReason;
			logger.trace("Layout section was marked as invalid: {}", this);
			return this;
		}

		public Builder setClosableMode(@NonNull final ClosableMode closableMode)
		{
			this.closableMode = closableMode;
			return this;
		}

		public Builder setCaptionMode(@NonNull final CaptionMode captionMode)
		{
			this.captionMode = captionMode;
			return this;
		}

		public boolean isValid()
		{
			return invalidReason == null;
		}

		public boolean isInvalid()
		{
			return invalidReason != null;
		}

		public boolean isNotEmpty()
		{
			return streamElementBuilders().findAny().isPresent();
		}

		private Stream<DocumentLayoutElementDescriptor.Builder> streamElementBuilders()
		{
			return columnsBuilders.stream().flatMap(DocumentLayoutColumnDescriptor.Builder::streamElementBuilders);
		}

		public Builder setExcludeSpecialFields()
		{
			streamElementBuilders().forEach(elementBuilder -> elementBuilder.setExcludeSpecialFields());
			return this;
		}
	}
}
