package de.metas.ui.web.handlingunits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Tab;
import org.compiere.util.CCache;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.Constants;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.handlingunits.SqlHUEditorViewRepository.SqlHUEditorViewRepositoryBuilder;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.SqlViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.factory.standard.LayoutFactory;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.NonNull;

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

public abstract class HUEditorViewFactoryTemplate implements IViewFactory
{
	private static final transient Logger logger = LogManager.getLogger(HUEditorViewFactoryTemplate.class);

	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;
	private final ImmutableListMultimap<String, HUEditorViewCustomizer> viewCustomizersByReferencingTableName;
	private final ImmutableMap<String, HUEditorRowIsProcessedPredicate> rowProcessedPredicateByReferencingTableName;
	private final ImmutableMap<String, Boolean> rowAttributesAlwaysReadonlyByReferencingTableName;

	private final transient CCache<Integer, SqlViewBinding> sqlViewBindingCache = CCache.newCache("SqlViewBinding", 1, 0);
	private final transient CCache<ArrayKey, ViewLayout> layouts = CCache.newLRUCache("HUEditorViewFactory#Layouts", 10, 0);

	protected HUEditorViewFactoryTemplate(final List<HUEditorViewCustomizer> viewCustomizers)
	{
		viewCustomizersByReferencingTableName = viewCustomizers.stream()
				.collect(GuavaCollectors.toImmutableListMultimap(HUEditorViewCustomizer::getReferencingTableNameToMatch));
		logger.info("Initialized view customizers: {}", viewCustomizersByReferencingTableName);

		rowProcessedPredicateByReferencingTableName = viewCustomizers.stream()
				.filter(viewCustomizer -> viewCustomizer.getHUEditorRowIsProcessedPredicate() != null)
				.distinct()
				.collect(ImmutableMap.toImmutableMap(HUEditorViewCustomizer::getReferencingTableNameToMatch, HUEditorViewCustomizer::getHUEditorRowIsProcessedPredicate));
		logger.info("Initialized row processed predicates: {}", rowProcessedPredicateByReferencingTableName);

		rowAttributesAlwaysReadonlyByReferencingTableName = viewCustomizers.stream()
				.filter(viewCustomizer -> viewCustomizer.isAttributesAlwaysReadonly() != null)
				.distinct()
				.collect(ImmutableMap.toImmutableMap(HUEditorViewCustomizer::getReferencingTableNameToMatch, HUEditorViewCustomizer::isAttributesAlwaysReadonly));
		logger.info("Initialized row attributes always readonly flags: {}", rowAttributesAlwaysReadonlyByReferencingTableName);
	}

	private List<HUEditorViewCustomizer> getViewCustomizers(final String referencingTableName)
	{
		return viewCustomizersByReferencingTableName.get(referencingTableName);
	}

	private HUEditorRowIsProcessedPredicate getRowProcessedPredicate(final String referencingTableName)
	{
		return rowProcessedPredicateByReferencingTableName.getOrDefault(referencingTableName, HUEditorRowIsProcessedPredicates.NEVER);
	}

	public final SqlViewBinding getSqlViewBinding()
	{
		final int key = 0; // not important
		return sqlViewBindingCache.getOrLoad(key, this::createSqlViewBinding);
	}

	/** @return HU's standard entity descriptor */
	private DocumentEntityDescriptor getHUEntityDescriptor()
	{
		return documentDescriptorFactory.getDocumentEntityDescriptor(WEBUI_HU_Constants.WEBUI_HU_Window_ID);
	}

	private SqlViewBinding createSqlViewBinding()
	{
		// Get HU's standard entity descriptor. We will needed all over.
		final DocumentEntityDescriptor huEntityDescriptor = getHUEntityDescriptor();

		//
		// Static where clause
		final StringBuilder sqlWhereClause = new StringBuilder();
		{
			sqlWhereClause.append(I_M_HU.COLUMNNAME_M_HU_Item_Parent_ID + " is null"); // top level

			// Consider window tab's where clause if any
			final I_AD_Tab huTab = Services.get(IADWindowDAO.class).retrieveFirstTab(WEBUI_HU_Constants.WEBUI_HU_Window_ID.toInt());
			if (!Check.isEmpty(huTab.getWhereClause(), true))
			{
				sqlWhereClause.append("\n AND (").append(huTab.getWhereClause()).append(")");
			}
		}

		//
		// Start preparing the sqlViewBinding builder
		final List<String> displayFieldNames = ImmutableList.of(I_M_HU.COLUMNNAME_M_HU_ID);

		final SqlViewBinding.Builder sqlViewBinding = SqlViewBinding.builder()
				.tableName(I_M_HU.Table_Name)
				.displayFieldNames(displayFieldNames)
				.sqlWhereClause(sqlWhereClause.toString())
				.rowIdsConverter(HUSqlViewRowIdsConverter.instance);

		//
		// View fields: from M_HU's entity descriptor
		{
			// NOTE: we need to add all HU's standard fields because those might be needed for some of the standard filters defined
			final SqlDocumentEntityDataBindingDescriptor huEntityBindings = SqlDocumentEntityDataBindingDescriptor.cast(huEntityDescriptor.getDataBinding());
			huEntityBindings.getFields()
					.stream()
					.map(huField -> SqlViewFactory.createViewFieldBindingBuilder(huField, displayFieldNames).build())
					.forEach(sqlViewBinding::field);
		}

		//
		// View field: BestBeforeDate
		{
			sqlViewBinding.field(SqlViewRowFieldBinding.builder()
					.fieldName(HUEditorRow.FIELDNAME_BestBeforeDate)
					.widgetType(DocumentFieldWidgetType.Date)
					.columnSql(Constants.sqlBestBeforeDate(sqlViewBinding.getTableAlias() + "." + I_M_HU.COLUMNNAME_M_HU_ID))
					.fieldLoader((rs, adLanguage) -> rs.getTimestamp(HUEditorRow.FIELDNAME_BestBeforeDate))
					.build());
		}

		//
		// View filters and converters
		{
			sqlViewBinding
					.filterDescriptors(createFilterDescriptorsProvider())
					.filterConverter(HUBarcodeSqlDocumentFilterConverter.FILTER_ID, HUBarcodeSqlDocumentFilterConverter.instance)
					.filterConverter(HUIdsFilterHelper.FILTER_ID, HUIdsFilterHelper.SQL_DOCUMENT_FILTER_CONVERTER);

			createFilterConvertersIndexedByFilterId().forEach(sqlViewBinding::filterConverter);
		}

		//
		return sqlViewBinding.build();
	}

	protected final DocumentFilterDescriptorsProvider getViewFilterDescriptors()
	{
		return getSqlViewBinding().getViewFilterDescriptors();
	}

	@OverridingMethodsMustInvokeSuper
	protected DocumentFilterDescriptorsProvider createFilterDescriptorsProvider()
	{
		final DocumentEntityDescriptor huEntityDescriptor = getHUEntityDescriptor();
		final Collection<DocumentFilterDescriptor> huStandardFilters = huEntityDescriptor.getFilterDescriptors().getAll();
		return ImmutableDocumentFilterDescriptorsProvider.builder()
				.addDescriptors(huStandardFilters)
				.addDescriptor(HUBarcodeSqlDocumentFilterConverter.createDocumentFilterDescriptor())
				.build();
	}

	protected Map<String, SqlDocumentFilterConverter> createFilterConvertersIndexedByFilterId()
	{
		return ImmutableMap.of();
	}

	@Override
	public final ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		final ArrayKey key = ArrayKey.of(windowId, viewDataType);
		return layouts.getOrLoad(key, () -> createHUViewLayout(windowId, viewDataType));
	}

	protected ViewLayout createHUViewLayout(final WindowId windowId, final JSONViewDataType viewDataType)
	{
		return ViewLayout.builder()
				.setWindowId(windowId)
				.setCaption("HU Editor")
				.setEmptyResultText(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(LayoutFactory.HARDCODED_TAB_EMPTY_RESULT_HINT)
				.setIdFieldName(HUEditorRow.FIELDNAME_M_HU_ID)
				.setFilters(getViewFilterDescriptors().getAll())
				//
				.setHasAttributesSupport(true)
				.setHasTreeSupport(true)
				//
				.addElementsFromViewRowClass(HUEditorRow.class, viewDataType)
				//
				.build();
	}

	@Override
	public final HUEditorView createView(final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();

		//
		// Referencing documentPaths and tableName (i.e. from where are we coming, e.g. receipt schedule)
		final Set<DocumentPath> referencingDocumentPaths = request.getReferencingDocumentPaths();
		final String referencingTableName = extractReferencingTablename(referencingDocumentPaths);

		final SqlViewBinding sqlViewBinding = getSqlViewBinding();

		//
		// HUEditorView rows repository
		final HUEditorViewRepository huEditorViewRepository;
		{
			final WindowId windowId = viewId.getWindowId();
			final boolean attributesAlwaysReadonly = rowAttributesAlwaysReadonlyByReferencingTableName.getOrDefault(referencingTableName, Boolean.TRUE);
			final SqlHUEditorViewRepositoryBuilder huEditorViewRepositoryBuilder = SqlHUEditorViewRepository.builder()
					.windowId(windowId)
					.rowProcessedPredicate(getRowProcessedPredicate(referencingTableName))
					.attributesProvider(HUEditorRowAttributesProvider.builder()
							.readonly(attributesAlwaysReadonly)
							.build())
					.sqlViewBinding(sqlViewBinding);
			customizeHUEditorViewRepository(huEditorViewRepositoryBuilder);
			huEditorViewRepository = huEditorViewRepositoryBuilder.build();
		}

		//
		// HUEditorView
		{
			// Filters
			@SuppressWarnings("deprecation") // as long as the deprecated getFilterOnlyIds() is around we can't ignore it
			final List<DocumentFilter> stickyFilters = extractStickyFilters(request.getStickyFilters(), request.getFilterOnlyIds());
			final DocumentFilterDescriptorsProvider filterDescriptors = getViewFilterDescriptors();
			final List<DocumentFilter> filters = request.getOrUnwrapFilters(filterDescriptors);

			// Start building the HUEditorView
			final HUEditorViewBuilder huViewBuilder = HUEditorView.builder()
					.setParentViewId(request.getParentViewId())
					.setParentRowId(request.getParentRowId())
					.setViewId(viewId)
					.setViewType(request.getViewType())
					.setStickyFilters(stickyFilters)
					.setFilters(filters)
					.setFilterDescriptors(filterDescriptors)
					.setReferencingDocumentPaths(referencingTableName, referencingDocumentPaths)
					.orderBys(sqlViewBinding.getDefaultOrderBys())
					.setActions(request.getActions())
					.setAdditionalRelatedProcessDescriptors(request.getAdditionalRelatedProcessDescriptors())
					.setHUEditorViewRepository(huEditorViewRepository);

			//
			// Call view customizers
			getViewCustomizers(referencingTableName).forEach(viewCustomizer -> viewCustomizer.beforeCreate(huViewBuilder));
			customizeHUEditorView(huViewBuilder);

			return huViewBuilder.build();
		}
	}

	protected void customizeHUEditorViewRepository(final SqlHUEditorViewRepository.SqlHUEditorViewRepositoryBuilder huEditorViewRepositoryBuilder)
	{
		// nothing on this level
	}

	protected void customizeHUEditorView(final HUEditorViewBuilder huViewBuilder)
	{
		// nothing on this level
	}

	private String extractReferencingTablename(final Set<DocumentPath> referencingDocumentPaths)
	{
		final String referencingTableName;
		if (!referencingDocumentPaths.isEmpty())
		{
			final WindowId referencingWindowId = referencingDocumentPaths.iterator().next().getWindowId(); // assuming all document paths have the same window
			referencingTableName = documentDescriptorFactory.getDocumentEntityDescriptor(referencingWindowId)
					.getTableNameOrNull();
		}
		else
		{
			referencingTableName = null;
		}
		return referencingTableName;
	}

	/**
	 *
	 * @param requestStickyFilters
	 * @param huIds {@code null} means "no restriction". Empty means "select none"
	 * @return
	 */
	private static List<DocumentFilter> extractStickyFilters(
			@NonNull final List<DocumentFilter> requestStickyFilters,
			@Nullable final Set<Integer> filterOnlyIds)
	{
		final List<DocumentFilter> stickyFilters = new ArrayList<>(requestStickyFilters);

		final DocumentFilter stickyFilter_HUIds_Existing = HUIdsFilterHelper.findExistingOrNull(stickyFilters);

		// Create the sticky filter by HUIds from builder's huIds (if any huIds)
		if (stickyFilter_HUIds_Existing == null && filterOnlyIds != null && !filterOnlyIds.isEmpty())
		{
			final DocumentFilter stickyFilter_HUIds_New = HUIdsFilterHelper.createFilter(filterOnlyIds);
			stickyFilters.add(0, stickyFilter_HUIds_New);
		}

		return ImmutableList.copyOf(stickyFilters);
	}

	/**
	 * HU's Barcode filter converter
	 */
	private static final class HUBarcodeSqlDocumentFilterConverter implements SqlDocumentFilterConverter
	{
		public static final String FILTER_ID = "barcode";

		public static final transient HUBarcodeSqlDocumentFilterConverter instance = new HUBarcodeSqlDocumentFilterConverter();

		public static DocumentFilterDescriptor createDocumentFilterDescriptor()
		{
			final ITranslatableString barcodeCaption = Services.get(IMsgBL.class).translatable("Barcode");
			return DocumentFilterDescriptor.builder()
					.setFilterId(FILTER_ID)
					.setDisplayName(barcodeCaption)
					.setParametersLayoutType(PanelLayoutType.SingleOverlayField)
					.setFrequentUsed(false)
					.addParameter(DocumentFilterParamDescriptor.builder()
							.setFieldName(PARAM_Barcode)
							.setDisplayName(barcodeCaption)
							.setWidgetType(DocumentFieldWidgetType.Text))
					.build();
		}

		private static final String PARAM_Barcode = "Barcode";

		private HUBarcodeSqlDocumentFilterConverter()
		{
		}

		@Override
		public String getSql(
				@NonNull final SqlParamsCollector sqlParamsOut,
				@NonNull final DocumentFilter filter,
				final SqlOptions sqlOpts_NOTUSED)
		{
			final Object barcodeObj = filter.getParameter(PARAM_Barcode).getValue();
			if (barcodeObj == null)
			{
				throw new IllegalArgumentException("Barcode parameter is null: " + filter);
			}

			final String barcode = barcodeObj.toString().trim();
			if (barcode.isEmpty())
			{
				throw new IllegalArgumentException("Barcode parameter is empty: " + filter);
			}

			final IQueryFilter<I_M_HU> queryFilter = Services.get(IHandlingUnitsDAO.class).createHUQueryBuilder()
					.setContext(PlainContextAware.newOutOfTrx())
					.setOnlyWithBarcode(barcode)
					.createQueryFilter();

			final ISqlQueryFilter sqlQueryFilter = ISqlQueryFilter.cast(queryFilter);
			final String sql = sqlQueryFilter.getSql();

			sqlParamsOut.collectAll(sqlQueryFilter);
			return sql;
		}
	}

}
