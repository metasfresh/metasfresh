package de.metas.ui.web.view.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.comparator.FixedOrderByKeyComparator;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.cache.ETag;
import de.metas.ui.web.cache.ETagAware;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.factory.standard.LayoutFactory;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import lombok.AccessLevel;
import lombok.NonNull;
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

	private static final Logger logger = LogManager.getLogger(ViewLayout.class);

	private final WindowId windowId;
	private final DetailId detailId;
	private final ViewProfileId profileId;
	private final ITranslatableString caption;
	private final ITranslatableString description;
	private final ITranslatableString emptyResultText;
	private final ITranslatableString emptyResultHint;

	private final ImmutableList<DocumentFilterDescriptor> filters;

	private final ImmutableList<DocumentQueryOrderBy> defaultOrderBys;

	private final ImmutableList<DocumentLayoutElementDescriptor> elements;

	private final String idFieldName;

	private final boolean hasAttributesSupport;
	private final IncludedViewLayout includedViewLayout;
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
		windowId = builder.windowId;
		detailId = builder.getDetailId();
		profileId = ViewProfileId.NULL;
		caption = builder.caption != null ? builder.caption : ImmutableTranslatableString.empty();
		description = builder.description != null ? builder.description : ImmutableTranslatableString.empty();
		emptyResultText = ImmutableTranslatableString.copyOfNullable(builder.emptyResultText);
		emptyResultHint = ImmutableTranslatableString.copyOfNullable(builder.emptyResultHint);

		elements = ImmutableList.copyOf(builder.buildElements());

		filters = ImmutableList.copyOf(builder.getFilters());

		defaultOrderBys = ImmutableList.copyOf(builder.getDefaultOrderBys());

		idFieldName = builder.getIdFieldName();

		hasAttributesSupport = builder.hasAttributesSupport;

		hasTreeSupport = builder.hasTreeSupport;
		treeCollapsible = builder.treeCollapsible;
		treeExpandedDepth = builder.treeExpandedDepth;

		includedViewLayout = builder.includedViewLayout;

		allowNewCaption = null;

		eTag = ETag.of(nextETagVersionSupplier.getAndIncrement(), extractETagAttributes(filters, allowNewCaption));
	}

	/**
	 * copy and override constructor
	 *
	 * @param elements
	 * @param defaultOrderBys
	 */
	private ViewLayout(final ViewLayout from,
			final WindowId windowId,
			final ViewProfileId profileId,
			final ImmutableList<DocumentFilterDescriptor> filters,
			final ImmutableList<DocumentQueryOrderBy> defaultOrderBys,
			final String allowNewCaption,
			final boolean hasTreeSupport, final boolean treeCollapsible, final int treeExpandedDepth,
			final ImmutableList<DocumentLayoutElementDescriptor> elements)
	{
		Check.assumeNotEmpty(elements, "elements is not empty");

		this.windowId = windowId;
		detailId = from.detailId;
		this.profileId = profileId;
		caption = from.caption;
		description = from.description;
		emptyResultText = from.emptyResultText;
		emptyResultHint = from.emptyResultHint;

		this.elements = elements;

		this.filters = filters;
		this.defaultOrderBys = defaultOrderBys;

		idFieldName = from.idFieldName;

		hasAttributesSupport = from.hasAttributesSupport;
		this.hasTreeSupport = hasTreeSupport;
		this.treeCollapsible = treeCollapsible;
		this.treeExpandedDepth = treeExpandedDepth;

		includedViewLayout = from.includedViewLayout;

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

	public ViewProfileId getProfileId()
	{
		return profileId;
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

	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return defaultOrderBys;
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

	@Nullable
	public IncludedViewLayout getIncludedViewLayout()
	{
		return includedViewLayout;
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
		private ViewProfileId profileId;
		private Collection<DocumentFilterDescriptor> filters;
		private String allowNewCaption;
		private Boolean hasTreeSupport;
		private Boolean treeCollapsible;
		private Integer treeExpandedDepth;

		private ArrayList<DocumentLayoutElementDescriptor> elements = null;
		private ArrayList<DocumentQueryOrderBy> defaultOrderBys = null;

		public ViewLayout build()
		{
			final WindowId windowIdEffective = windowId != null ? windowId : from.windowId;
			final ViewProfileId profileIdEffective = !ViewProfileId.isNull(profileId) ? profileId : from.profileId;
			final ImmutableList<DocumentFilterDescriptor> filtersEffective = ImmutableList.copyOf(filters != null ? filters : from.getFilters());
			final String allowNewCaptionEffective = allowNewCaption != null ? allowNewCaption : from.allowNewCaption;
			final boolean hasTreeSupportEffective = hasTreeSupport != null ? hasTreeSupport.booleanValue() : from.hasTreeSupport;
			final boolean treeCollapsibleEffective = treeCollapsible != null ? treeCollapsible.booleanValue() : from.treeCollapsible;
			final int treeExpandedDepthEffective = treeExpandedDepth != null ? treeExpandedDepth.intValue() : from.treeExpandedDepth;

			final ImmutableList<DocumentLayoutElementDescriptor> elementsEffective = elements != null ? ImmutableList.copyOf(elements) : from.elements;
			final ImmutableList<DocumentQueryOrderBy> defaultOrderBysEffective = defaultOrderBys != null ? ImmutableList.copyOf(defaultOrderBys) : from.defaultOrderBys;

			// If there will be no change then return this
			if (Objects.equals(from.windowId, windowIdEffective)
					&& Objects.equals(from.profileId, profileIdEffective)
					&& Objects.equals(from.filters, filtersEffective)
					&& Objects.equals(from.allowNewCaption, allowNewCaptionEffective)
					&& from.hasTreeSupport == hasTreeSupportEffective
					&& from.treeCollapsible == treeCollapsibleEffective
					&& from.treeExpandedDepth == treeExpandedDepthEffective
					&& Objects.equals(from.elements, elementsEffective)
					&& Objects.equals(from.defaultOrderBys, defaultOrderBysEffective))
			{
				return from;
			}

			return new ViewLayout(from,
					windowIdEffective,
					profileIdEffective,
					filtersEffective,
					defaultOrderBysEffective,
					allowNewCaptionEffective,
					hasTreeSupportEffective, treeCollapsibleEffective, treeExpandedDepthEffective,
					elementsEffective);
		}

		public ChangeBuilder windowId(final WindowId windowId)
		{
			this.windowId = windowId;
			return this;
		}

		public ChangeBuilder profileId(final ViewProfileId profileId)
		{
			this.profileId = profileId;
			return this;
		}

		public ChangeBuilder allowNewCaption(final String allowNewCaption)
		{
			this.allowNewCaption = allowNewCaption;
			return this;
		}

		public ChangeBuilder filters(final Collection<DocumentFilterDescriptor> filters)
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

		private ArrayList<DocumentLayoutElementDescriptor> getElementsToEdit()
		{
			if (elements == null)
			{
				elements = new ArrayList<>(from.elements);
			}
			return elements;
		}

		private void setElements(final ArrayList<DocumentLayoutElementDescriptor> elements)
		{
			this.elements = elements;
		}

		public ChangeBuilder element(@NonNull final DocumentLayoutElementDescriptor element)
		{
			getElementsToEdit().add(element);
			return this;
		}

		public ChangeBuilder elementsOrder(final String... fieldNames)
		{
			final List<String> fieldNamesList = ImmutableList.copyOf(fieldNames);

			final ArrayList<DocumentLayoutElementDescriptor> elementsNew = getElementsToEdit()
					.stream()
					.filter(element -> fieldNamesList.contains(element.getFirstFieldName()))
					.sorted(FixedOrderByKeyComparator.<DocumentLayoutElementDescriptor, String> builder()
							.fixedOrderKeys(fieldNamesList)
							.keyMapper(DocumentLayoutElementDescriptor::getFirstFieldName)
							.notMatchedMarkerIndex(Integer.MAX_VALUE)
							.build())
					.collect(Collectors.toCollection(ArrayList::new));
			setElements(elementsNew);

			return this;
		}

		private ArrayList<DocumentQueryOrderBy> getDefaultOrderBysToEdit()
		{
			if (defaultOrderBys == null)
			{
				defaultOrderBys = new ArrayList<>(from.defaultOrderBys);
			}
			return defaultOrderBys;
		}

		public ChangeBuilder clearDefaultOrderBys()
		{
			getDefaultOrderBysToEdit().clear();
			return this;
		}

		public ChangeBuilder defaultOrderBy(@NonNull final DocumentQueryOrderBy orderBy)
		{
			getDefaultOrderBysToEdit().add(orderBy);
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
		private List<DocumentQueryOrderBy> defaultOrderBys = null;

		private boolean hasAttributesSupport = false;
		private IncludedViewLayout includedViewLayout;

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

		public Builder setCaption(@Nullable final ITranslatableString caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder setCaption(@Nullable final String caption)
		{
			setCaption(ImmutableTranslatableString.constant(caption));
			return this;
		}

		public Builder setDescription(@Nullable final ITranslatableString description)
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

		public Builder addElements(final Collection<DocumentLayoutElementDescriptor.Builder> elementBuilders)
		{
			Check.assumeNotNull(elementBuilders, "Parameter elementBuilders is not null");
			elementBuilders.forEach(this::addElement);
			return this;
		}

		public Builder addElements(final Stream<DocumentLayoutElementDescriptor.Builder> elementBuilders)
		{
			Check.assumeNotNull(elementBuilders, "Parameter elementBuilders is not null");
			elementBuilders.forEach(this::addElement);
			return this;
		}

		public <T extends IViewRow> Builder addElementsFromViewRowClass(final Class<T> viewRowClass, final JSONViewDataType viewType)
		{
			final List<DocumentLayoutElementDescriptor.Builder> elements = ViewColumnHelper.createLayoutElementsForClass(viewRowClass, viewType);
			if (elements.isEmpty())
			{
				new AdempiereException("No elements found for viewRowClass=" + viewRowClass + " and viewType=" + viewType)
						.throwIfDeveloperModeOrLogWarningElse(logger);
			}

			addElements(elements);
			return this;
		}

		public <T extends IViewRow> Builder addElementsFromViewRowClassAndFieldNames(final Class<T> viewRowClass, final String... fieldNames)
		{
			final List<DocumentLayoutElementDescriptor.Builder> elements = ViewColumnHelper.createLayoutElementsForClassAndFieldNames(viewRowClass, fieldNames);
			Check.assumeNotEmpty(elements, "elements is not empty"); // shall never happen

			addElements(elements);
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

		public Builder addFilter(@NonNull final DocumentFilterDescriptor filter)
		{
			if (filters == null)
			{
				filters = new ArrayList<>();
			}
			filters.add(filter);
			return this;
		}

		private List<DocumentQueryOrderBy> getDefaultOrderBys()
		{
			return defaultOrderBys != null ? defaultOrderBys : ImmutableList.of();
		}

		public Builder setDefaultOrderBys(final List<DocumentQueryOrderBy> defaultOrderBys)
		{
			this.defaultOrderBys = defaultOrderBys;
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

		public Builder setIncludedViewLayout(final IncludedViewLayout includedViewLayout)
		{
			this.includedViewLayout = includedViewLayout;
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
