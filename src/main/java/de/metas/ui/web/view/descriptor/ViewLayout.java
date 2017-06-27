package de.metas.ui.web.view.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.cache.ETag;
import de.metas.ui.web.cache.ETagAware;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.factory.standard.LayoutFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ViewLayout implements ETagAware
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final WindowId windowId;
	private final DetailId detailId;
	private final ITranslatableString caption;
	private final ITranslatableString description;
	private final ITranslatableString emptyResultText;
	private final ITranslatableString emptyResultHint;

	private final ImmutableList<DocumentFilterDescriptor> filters;

	private final ImmutableList<DocumentLayoutElementDescriptor> elements;

	private final String idFieldName;

	private final boolean hasAttributesSupport;
	private final boolean hasIncludedViewSupport;
	private final String allowNewCaption;

	private final boolean hasTreeSupport;
	private final boolean treeCollapsible;
	private final int treeExpandedDepth;
	public static final int TreeExpandedDepth_AllCollapsed = 0;
	public static final int TreeExpandedDepth_ExpandedFirstLevel = 1;
	public static final int TreeExpandedDepth_AllExpanded = 100;

	// ETag support
	private static final AtomicInteger nextETagVersionSupplier = new AtomicInteger(1);
	private final ETag eTag;

	private ViewLayout(final Builder builder)
	{
		super();
		windowId = builder.windowId;
		detailId = builder.getDetailId();
		caption = builder.caption != null ? builder.caption : ImmutableTranslatableString.empty();
		description = builder.description != null ? builder.description : ImmutableTranslatableString.empty();
		emptyResultText = ImmutableTranslatableString.copyOfNullable(builder.emptyResultText);
		emptyResultHint = ImmutableTranslatableString.copyOfNullable(builder.emptyResultHint);

		elements = ImmutableList.copyOf(builder.buildElements());

		filters = ImmutableList.copyOf(builder.getFilters());

		idFieldName = builder.getIdFieldName();

		hasAttributesSupport = builder.hasAttributesSupport;

		hasTreeSupport = builder.hasTreeSupport;
		treeCollapsible = builder.treeCollapsible;
		treeExpandedDepth = builder.treeExpandedDepth;

		hasIncludedViewSupport = builder.hasIncludedViewSupport;
		allowNewCaption = null;

		eTag = ETag.of(nextETagVersionSupplier.getAndIncrement(), extractETagAttributes(filters, allowNewCaption));
	}

	/** copy and override constructor */
	private ViewLayout(final ViewLayout from,
			final WindowId windowId,
			final ImmutableList<DocumentFilterDescriptor> filters,
			final String allowNewCaption,
			final boolean hasTreeSupport, final boolean treeCollapsible, final int treeExpandedDepth)
	{
		super();
		this.windowId = windowId;
		detailId = from.detailId;
		caption = from.caption;
		description = from.description;
		emptyResultText = from.emptyResultText;
		emptyResultHint = from.emptyResultHint;

		elements = from.elements;

		this.filters = filters;

		idFieldName = from.idFieldName;

		hasAttributesSupport = from.hasAttributesSupport;
		this.hasTreeSupport = hasTreeSupport;
		this.treeCollapsible = treeCollapsible;
		this.treeExpandedDepth = treeExpandedDepth;
		hasIncludedViewSupport = from.hasIncludedViewSupport;
		this.allowNewCaption = allowNewCaption;

		eTag = from.eTag.overridingAttributes(extractETagAttributes(filters, allowNewCaption));
	}

	private static final ImmutableMap<String, String> extractETagAttributes(final ImmutableList<DocumentFilterDescriptor> filters, final String allowNewCaption)
	{
		final String filtersNorm = filters == null ? "0" : String.valueOf(filters.hashCode());
		final String allowNewCaptionNorm = allowNewCaption == null ? "0" : String.valueOf(allowNewCaption.hashCode());
		return ImmutableMap.of("filters", filtersNorm, "allowNew", allowNewCaptionNorm);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("detailId", detailId)
				.add("caption", caption)
				.add("elements", elements.isEmpty() ? null : elements)
				.add("eTag", eTag)
				.toString();
	}

	public ChangeBuilder toBuilder()
	{
		return new ChangeBuilder(this);
	}

	public WindowId getWindowId()
	{
		return windowId;
	}

	public DetailId getDetailId()
	{
		return detailId;
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public String getEmptyResultText(final String adLanguage)
	{
		return emptyResultText.translate(adLanguage);
	}

	public String getEmptyResultHint(final String adLanguage)
	{
		return emptyResultHint.translate(adLanguage);
	}

	public List<DocumentFilterDescriptor> getFilters()
	{
		return filters;
	}

	public ViewLayout withFiltersAndTreeSupport(final Collection<DocumentFilterDescriptor> filtersToSet,
			final boolean hasTreeSupportToSet, final Boolean treeCollapsibleToSet, final Integer treeExpandedDepthToSet)
	{
		return toBuilder()
				.filters(filtersToSet)
				.treeSupport(hasTreeSupportToSet, treeCollapsibleToSet, treeExpandedDepthToSet)
				.build();
	}

	public ViewLayout withAllowNewRecordIfPresent(final Optional<String> allowNewCaption)
	{
		if (!allowNewCaption.isPresent())
		{
			return this;
		}

		return toBuilder()
				.allowNewCaption(allowNewCaption.get())
				.build();
	}

	public List<DocumentLayoutElementDescriptor> getElements()
	{
		return elements;
	}

	public boolean hasElements()
	{
		return !elements.isEmpty();
	}

	public String getIdFieldName()
	{
		return idFieldName;
	}

	public boolean isAttributesSupport()
	{
		return hasAttributesSupport;
	}

	public boolean isTreeSupport()
	{
		return hasTreeSupport;
	}

	public boolean isTreeCollapsible()
	{
		return treeCollapsible;
	}

	public int getTreeExpandedDepth()
	{
		return treeExpandedDepth;
	}

	public boolean isIncludedViewSupport()
	{
		return hasIncludedViewSupport;
	}

	public boolean isAllowNew()
	{
		return allowNewCaption != null;
	}

	public String getAllowNewCaption()
	{
		return allowNewCaption;
	}

	@Override
	public ETag getETag()
	{
		return eTag;
	}

	//
	//
	//
	//
	//
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class ChangeBuilder
	{
		private final ViewLayout from;
		private WindowId windowId;
		private Collection<DocumentFilterDescriptor> filters;
		private String allowNewCaption;
		private Boolean hasTreeSupport;
		private Boolean treeCollapsible;
		private Integer treeExpandedDepth;

		public ViewLayout build()
		{
			final WindowId windowIdEffective = windowId != null ? windowId : from.windowId;
			final ImmutableList<DocumentFilterDescriptor> filtersEffective = ImmutableList.copyOf(filters != null ? filters : from.getFilters());
			final String allowNewCaptionEffective = allowNewCaption != null ? allowNewCaption : from.allowNewCaption;
			final boolean hasTreeSupportEffective = hasTreeSupport != null ? hasTreeSupport.booleanValue() : from.hasTreeSupport;
			final boolean treeCollapsibleEffective = treeCollapsible != null ? treeCollapsible.booleanValue() : from.treeCollapsible;
			final int treeExpandedDepthEffective = treeExpandedDepth != null ? treeExpandedDepth.intValue() : from.treeExpandedDepth;

			// If there will be no change then return this
			if (Objects.equals(from.windowId, windowIdEffective)
					&& Objects.equals(from.filters, filtersEffective)
					&& Objects.equals(from.allowNewCaption, allowNewCaptionEffective)
					&& from.hasTreeSupport == hasTreeSupportEffective
					&& from.treeCollapsible == treeCollapsibleEffective
					&& from.treeExpandedDepth == treeExpandedDepthEffective)
			{
				return from;
			}

			return new ViewLayout(from, windowIdEffective, filtersEffective, allowNewCaptionEffective, hasTreeSupportEffective, treeCollapsibleEffective, treeExpandedDepthEffective);
		}

		public ChangeBuilder windowId(WindowId windowId)
		{
			this.windowId = windowId;
			return this;
		}

		public ChangeBuilder allowNewCaption(final String allowNewCaption)
		{
			this.allowNewCaption = allowNewCaption;
			return this;
		}

		public ChangeBuilder filters(Collection<DocumentFilterDescriptor> filters)
		{
			this.filters = filters;
			return this;
		}

		public ChangeBuilder treeSupport(final boolean hasTreeSupport, final Boolean treeCollapsible, final Integer treeExpandedDepth)
		{
			this.hasTreeSupport = hasTreeSupport;
			this.treeCollapsible = treeCollapsible;
			this.treeExpandedDepth = treeExpandedDepth;
			return this;
		}

	}

	public static final class Builder
	{
		private WindowId windowId;
		private DetailId detailId;
		private ITranslatableString caption;
		private ITranslatableString description;
		private ITranslatableString emptyResultText = LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT;
		private ITranslatableString emptyResultHint = LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT;

		private Collection<DocumentFilterDescriptor> filters = null;

		private boolean hasAttributesSupport = false;
		private boolean hasIncludedViewSupport = false;

		private boolean hasTreeSupport = false;
		private boolean treeCollapsible = false;
		private int treeExpandedDepth = TreeExpandedDepth_AllExpanded;

		private final List<DocumentLayoutElementDescriptor.Builder> elementBuilders = new ArrayList<>();

		private String idFieldName;

		private Builder()
		{
			super();
		}

		public ViewLayout build()
		{
			return new ViewLayout(this);
		}

		private List<DocumentLayoutElementDescriptor> buildElements()
		{
			return elementBuilders
					.stream()
					.filter(elementBuilder -> elementBuilder.getFieldsCount() > 0) // have some field builders
					.map(elementBuilder -> elementBuilder.build())
					.collect(GuavaCollectors.toImmutableList());
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("detailId", detailId)
					.add("caption", caption)
					.add("elements-count", elementBuilders.size())
					.toString();
		}

		public Builder setWindowId(final WindowId windowId)
		{
			this.windowId = windowId;
			return this;
		}

		public Builder setDetailId(final DetailId detailId)
		{
			this.detailId = detailId;
			return this;
		}

		public DetailId getDetailId()
		{
			return detailId;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder setCaption(final String caption)
		{
			setCaption(ImmutableTranslatableString.constant(caption));
			return this;
		}

		public Builder setDescription(final ITranslatableString description)
		{
			this.description = description;
			return this;
		}

		public Builder setEmptyResultText(final ITranslatableString emptyResultText)
		{
			this.emptyResultText = emptyResultText;
			return this;
		}

		public Builder setEmptyResultHint(final ITranslatableString emptyResultHint)
		{
			this.emptyResultHint = emptyResultHint;
			return this;
		}

		public Builder addElement(final DocumentLayoutElementDescriptor.Builder elementBuilder)
		{
			Check.assumeNotNull(elementBuilder, "Parameter elementBuilder is not null");
			elementBuilders.add(elementBuilder);
			return this;
		}

		public Builder addElements(final Stream<DocumentLayoutElementDescriptor.Builder> elementBuilders)
		{
			Check.assumeNotNull(elementBuilders, "Parameter elementBuilders is not null");
			elementBuilders.forEach(this::addElement);
			return this;
		}

		public <T extends IViewRow> Builder addElementsFromViewRowClass(final Class<T> viewRowClass)
		{
			ViewColumnHelper.getLayoutElementsForClass(viewRowClass)
					.forEach(this::addElement);
			return this;
		}

		public boolean hasElements()
		{
			return !elementBuilders.isEmpty();
		}

		public List<DocumentLayoutElementDescriptor.Builder> getElements()
		{
			return elementBuilders;
		}

		private Collection<DocumentFilterDescriptor> getFilters()
		{
			if (filters == null || filters.isEmpty())
			{
				return ImmutableList.of();
			}
			return filters;
		}

		public Builder setFilters(final Collection<DocumentFilterDescriptor> filters)
		{
			this.filters = filters;
			return this;
		}

		public Set<String> getFieldNames()
		{
			return elementBuilders
					.stream()
					.flatMap(element -> element.getFieldNames().stream())
					.collect(GuavaCollectors.toImmutableSet());
		}

		public Builder setIdFieldName(final String idFieldName)
		{
			this.idFieldName = idFieldName;
			return this;
		}

		private String getIdFieldName()
		{
			return idFieldName;
		}

		public Builder setHasAttributesSupport(final boolean hasAttributesSupport)
		{
			this.hasAttributesSupport = hasAttributesSupport;
			return this;
		}

		public Builder setHasIncludedViewSupport(final boolean hasIncludedViewSupport)
		{
			this.hasIncludedViewSupport = hasIncludedViewSupport;
			return this;
		}

		public Builder setHasTreeSupport(final boolean hasTreeSupport)
		{
			this.hasTreeSupport = hasTreeSupport;
			return this;
		}

		public Builder setTreeCollapsible(final boolean treeCollapsible)
		{
			this.treeCollapsible = treeCollapsible;
			return this;
		}

		public Builder setTreeExpandedDepth(final int treeExpandedDepth)
		{
			this.treeExpandedDepth = treeExpandedDepth;
			return this;
		}

	}
}
