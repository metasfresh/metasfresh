/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.handlingunits;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADReferenceService;
import de.metas.cache.CCache;
import de.metas.document.references.related_documents.RelatedDocumentsPermissionsFactory;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.process.BarcodeScannerType;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.references.WebuiDocumentReferenceId;
import de.metas.ui.web.document.references.service.WebuiDocumentReferencesService;
import de.metas.ui.web.handlingunits.SqlHUEditorViewRepository.SqlHUEditorViewRepositoryBuilder;
import de.metas.ui.web.handlingunits.filter.HUIdsFilterHelper;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewBindingFactory;
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
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.column.ColumnSql;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.dao.impl.InArrayQueryFilter;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeSourceDocument;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class HUEditorViewFactoryTemplate implements IViewFactory
{
	private static final Logger logger = LogManager.getLogger(HUEditorViewFactoryTemplate.class);

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private final IMsgBL msgBL = Services.get(IMsgBL.class);


	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;
	@Autowired
	private HUReservationService huReservationService;
	@Autowired
	private WebuiDocumentReferencesService webuiDocumentReferencesService;
	@Autowired
	private ADReferenceService adReferenceService;

	private static final String SYSCFG_AlwaysUseSameLayout = "de.metas.ui.web.handlingunits.HUEditorViewFactory.AlwaysUseSameLayout";

	private final ImmutableListMultimap<String, HUEditorViewCustomizer> viewCustomizersByReferencingTableName;
	private final ImmutableMap<String, HUEditorRowIsProcessedPredicate> rowProcessedPredicateByReferencingTableName;
	private final ImmutableMap<String, Boolean> rowAttributesAlwaysReadonlyByReferencingTableName;
	private WindowId windowId;

	private final transient CCache<Integer, SqlViewBinding> sqlViewBindingCache = CCache.newCache("SqlViewBinding", 1, 0);
	private final transient CCache<ViewLayoutKey, ViewLayout> layouts = CCache.newLRUCache("HUEditorViewFactory#Layouts", 10, 0);

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

	@Override
	public void setWindowId(final WindowId windowId)
	{
		this.windowId = windowId;
	}

	protected final WindowId getWindowId()
	{
		return windowId;
	}

	private List<HUEditorViewCustomizer> getViewCustomizers(@Nullable final String referencingTableName)
	{
		return viewCustomizersByReferencingTableName.get(referencingTableName);
	}

	private HUEditorRowIsProcessedPredicate getRowProcessedPredicate(@Nullable final String referencingTableName)
	{
		return rowProcessedPredicateByReferencingTableName.getOrDefault(referencingTableName, HUEditorRowIsProcessedPredicates.NEVER);
	}

	public final SqlViewBinding getSqlViewBinding()
	{
		final int key = 0; // not important
		return sqlViewBindingCache.getOrLoad(key, this::createSqlViewBinding);
	}

	/**
	 * @return HU's standard entity descriptor
	 */
	private DocumentEntityDescriptor getHUEntityDescriptor()
	{
		final WindowId windowId = getWindowId();
		if (windowId != null && documentDescriptorFactory.isWindowIdSupported(windowId))
		{
			return documentDescriptorFactory.getDocumentEntityDescriptor(windowId);
		}
		else
		{
			return documentDescriptorFactory.getDocumentEntityDescriptor(WEBUI_HU_Constants.WEBUI_HU_Window_ID);
		}
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
			final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
			final AdWindowId adWindowId = huEntityDescriptor.getWindowId().toAdWindowIdOrNull();
			if (adWindowId != null)
			{
				final String firstTabWhereClause = adWindowDAO.getFirstTabWhereClause(adWindowId);
				if (Check.isNotBlank(firstTabWhereClause))
				{
					sqlWhereClause.append("\n /* first tab */ AND (").append(firstTabWhereClause).append(")");
				}
			}

			//
			final String additionalSqlWhereClause = getAdditionalSqlWhereClause();
			if (Check.isNotBlank(additionalSqlWhereClause))
			{
				sqlWhereClause.append("\n /* ").append(getClass().getSimpleName()).append(".getAdditionalSqlWhereClause */")
						.append(" AND (").append(additionalSqlWhereClause).append(")");
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
			sqlViewBinding.fields(SqlViewBindingFactory.createViewFieldBindings(huEntityBindings, displayFieldNames));
		}

		//
		// View field: BestBeforeDate
		{
			final ColumnSql sqlBestBeforeDate = ColumnSql.ofSql(
					HUAttributeConstants.sqlBestBeforeDate(sqlViewBinding.getTableAlias() + "." + I_M_HU.COLUMNNAME_M_HU_ID),
					sqlViewBinding.getTableAlias());

			sqlViewBinding.field(SqlViewRowFieldBinding.builder()
					.fieldName(HUEditorRow.FIELDNAME_BestBeforeDate)
					.widgetType(DocumentFieldWidgetType.LocalDate)
					.sqlSelectValue(SqlSelectValue.builder()
							.virtualColumnSql(sqlBestBeforeDate)
							.columnNameAlias(HUEditorRow.FIELDNAME_BestBeforeDate)
							.build())
					.fieldLoader((rs, adLanguage) -> rs.getTimestamp(HUEditorRow.FIELDNAME_BestBeforeDate))
					.build());
		}

		//
		// View filters and converters
		{
			sqlViewBinding
					.filterDescriptors(createFilterDescriptorsProvider())
					.filterConverter(HUBarcodeSqlDocumentFilterConverter.instance)
					.filterConverter(HUIdsFilterHelper.SQL_DOCUMENT_FILTER_CONVERTER)
					.filterConverters(createFilterConverters());
		}

		//
		return sqlViewBinding.build();
	}

	@Nullable
	protected String getAdditionalSqlWhereClause()
	{
		return null;
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

	protected List<SqlDocumentFilterConverter> createFilterConverters()
	{
		return ImmutableList.of();
	}

	@Override
	public final ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId_NOTUSED)
	{
		final ViewLayoutKey key = ViewLayoutKey.of(windowId, viewDataType);
		return layouts.getOrLoad(key, this::createHUViewLayout);
	}

	private ViewLayout createHUViewLayout(final ViewLayoutKey key)
	{
		final WindowId windowId = key.getWindowId();
		final JSONViewDataType viewDataType = key.getViewDataType();

		final Collection<DocumentFilterDescriptor> all = getViewFilterDescriptors().getAll();

		final ViewLayout.Builder viewLayoutBuilder = ViewLayout.builder()
				.setWindowId(windowId)
				.setCaption("HU Editor")
				.setEmptyResultText(msgBL.getTranslatableMsgText(LayoutFactory.TAB_EMPTY_RESULT_TEXT))
				.setEmptyResultHint(msgBL.getTranslatableMsgText(LayoutFactory.TAB_EMPTY_RESULT_HINT))
				.setIdFieldName(HUEditorRow.FIELDNAME_M_HU_ID)
				.setFilters(all)
				//
				.setHasAttributesSupport(true)
				.setHasTreeSupport(true)
				//
				.addElementsFromViewRowClass(HUEditorRow.class, viewDataType);

		if (!isAlwaysUseSameLayout())
		{
			customizeViewLayout(viewLayoutBuilder, viewDataType);
		}

		return viewLayoutBuilder.build();
	}

	protected AttributeSourceDocument getAttributeSourceDocument()
	{
		return null;
	}

	@Override
	public final HUEditorView createView(final @NonNull CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();

		//
		// Referencing documentPaths and tableName (i.e. from where are we coming, e.g. receipt schedule)
		final String referencingTableName = extractReferencingTablename(request.getReferencingDocumentPaths());

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
												.serialNoFromSequence(false)
												.attributeSourceDocument(getAttributeSourceDocument())
												.build())
					.sqlViewBinding(sqlViewBinding)
					.huReservationService(huReservationService)
					.adReferenceService(adReferenceService);

			customizeHUEditorViewRepository(huEditorViewRepositoryBuilder);

			huEditorViewRepository = huEditorViewRepositoryBuilder.build();
		}

		//
		// HUEditorView
		{
			// Filters
			@SuppressWarnings("deprecation") // as long as the deprecated getFilterOnlyIds() is around we can't ignore it
			final DocumentFilterList stickyFilters = extractStickyFilters(request.getStickyFilters(), request.getFilterOnlyIds())
					.mergeWithNullable(
							extractReferencedDocumentFilter(
									request.getViewId().getWindowId(),
									request.getSingleReferencingDocumentPathOrNull(),
									request.getDocumentReferenceId())
					);
			final DocumentFilterDescriptorsProvider filterDescriptors = getViewFilterDescriptors();
			final DocumentFilterList userFilters = request.getFiltersUnwrapped(filterDescriptors);

			// Start building the HUEditorView
			final HUEditorViewBuilder huViewBuilder = HUEditorView.builder()
					.setParentViewId(request.getParentViewId())
					.setParentRowId(request.getParentRowId())
					.setViewId(viewId)
					.setViewType(request.getViewType())
					.setStickyFilters(stickyFilters)
					.setFilters(userFilters)
					.setFilterDescriptors(filterDescriptors)
					.setReferencingDocumentPaths(request.getReferencingDocumentPaths())
					.orderBys(sqlViewBinding.getDefaultOrderBys())
					.setActions(request.getActions())
					.addAdditionalRelatedProcessDescriptors(request.getAdditionalRelatedProcessDescriptors())
					.setHUEditorViewRepository(huEditorViewRepository)
					.setUseAutoFilters(request.isUseAutoFilters())
					.setParameters(request.getParameters());

			//
			// Call view customizers
			getViewCustomizers(referencingTableName).forEach(viewCustomizer -> viewCustomizer.beforeCreate(huViewBuilder));
			customizeHUEditorView(huViewBuilder);

			return huViewBuilder.build();
		}
	}

	/**
	 * @apiNote This method is called only if NOT {@link #isAlwaysUseSameLayout()}.
	 */
	protected void customizeViewLayout(final ViewLayout.Builder viewLayoutBuilder, final JSONViewDataType viewDataType)
	{
		// nothing on this level
	}

	protected void customizeHUEditorViewRepository(final SqlHUEditorViewRepository.SqlHUEditorViewRepositoryBuilder huEditorViewRepositoryBuilder)
	{
		// nothing on this level
	}

	protected void customizeHUEditorView(final HUEditorViewBuilder huViewBuilder)
	{
		// nothing on this level
	}

	@Nullable
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

	@Nullable
	private DocumentFilter extractReferencedDocumentFilter(
			@NonNull final WindowId targetWindowId,
			@Nullable final DocumentPath referencedDocumentPath,
			@Nullable final WebuiDocumentReferenceId documentReferenceId)
	{
		if (referencedDocumentPath == null
				|| referencedDocumentPath.isComposedKey())
		{
			return null;
		}

		// Skip if we are zooming into a non-standard HU window.
		// If we don't do this, it will fail below where we try finding out the related document reference info
		if (targetWindowId.toAdWindowIdOrNull() == null)
		{
			return null;
		}

		return webuiDocumentReferencesService.getDocumentReferenceFilter(
						referencedDocumentPath,
						targetWindowId,
						documentReferenceId,
						RelatedDocumentsPermissionsFactory.allowAll())
				.orElse(null);
	}

	/**
	 * @param filterOnlyIds {@code null} means "no restriction". Empty means "select none"
	 */
	private static DocumentFilterList extractStickyFilters(
			@NonNull final DocumentFilterList requestStickyFilters,
			@Nullable final Set<Integer> filterOnlyIds)
	{
		final DocumentFilter stickyFilter_HUIds_Existing = HUIdsFilterHelper.findExisting(requestStickyFilters).orElse(null);

		// Create the sticky filter by HUIds from builder's huIds (if any huIds)
		if (stickyFilter_HUIds_Existing == null && filterOnlyIds != null && !filterOnlyIds.isEmpty())
		{
			final DocumentFilter stickyFilter_HUIds_New = HUIdsFilterHelper.createFilter(HuId.ofRepoIds(filterOnlyIds));

			return DocumentFilterList.of(stickyFilter_HUIds_New)
					.mergeWith(requestStickyFilters);
		}
		else
		{
			return requestStickyFilters;
		}

	}

	/**
	 * HU's Barcode filter converter
	 */
	private static final class HUBarcodeSqlDocumentFilterConverter implements SqlDocumentFilterConverter
	{
		private static final String FILTER_ID = "barcode";

		public static final HUBarcodeSqlDocumentFilterConverter instance = new HUBarcodeSqlDocumentFilterConverter();

		public static DocumentFilterDescriptor createDocumentFilterDescriptor()
		{
			final ITranslatableString barcodeCaption = TranslatableStrings.adElementOrMessage("Barcode");
			return DocumentFilterDescriptor.builder()
					.setFilterId(FILTER_ID)
					.setDisplayName(barcodeCaption)
					.setParametersLayoutType(PanelLayoutType.SingleOverlayField)
					.setFrequentUsed(true)
					.addParameter(DocumentFilterParamDescriptor.builder()
							.fieldName(PARAM_Barcode)
							.operator(DocumentFilterParam.Operator.EQUAL)
							.displayName(barcodeCaption)
							.widgetType(DocumentFieldWidgetType.Text)
							.barcodeScannerType(BarcodeScannerType.QRCode))
					.build();
		}

		private static final String PARAM_Barcode = "Barcode";

		private HUBarcodeSqlDocumentFilterConverter()
		{
		}

		@Override
		public boolean canConvert(final String filterId)
		{
			return Objects.equals(filterId, FILTER_ID);
		}

		@Override
		public FilterSql getSql(
				@NonNull final DocumentFilter filter,
				final SqlOptions sqlOpts_NOTUSED,
				@NonNull final SqlDocumentFilterConverterContext context)
		{
			final String barcodeString = StringUtils.trimBlankToNull(filter.getParameterValueAsString(PARAM_Barcode, null));
			if (barcodeString == null)
			{
				// shall not happen
				throw new AdempiereException("Barcode parameter is not set: " + filter);
			}

			//
			// Get M_HU_IDs by barcode
			final ImmutableSet<HuId> huIds;
			final GlobalQRCode globalQRCode = GlobalQRCode.parse(barcodeString).orNullIfError();
			if (globalQRCode != null)
			{
				final HUQRCode huQRCode = HUQRCode.fromGlobalQRCode(globalQRCode);
				final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
				final HuId huId = huQRCodesService.getHuIdByQRCodeIfExists(huQRCode).orElse(null);
				huIds = huId != null ? ImmutableSet.of(huId) : ImmutableSet.of();
			}
			else
			{
				final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
				huIds = handlingUnitsDAO.createHUQueryBuilder()
						.setContext(PlainContextAware.newOutOfTrx())
						.onlyContextClient(false) // avoid enforcing context AD_Client_ID because it might be that we are not in a user thread (so no context)
						.setOnlyWithBarcode(barcodeString)
						.setOnlyTopLevelHUs(false)
						.createQueryBuilder()
						.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions)
						.create()
						.listIds(HuId::ofRepoId);
			}

			if (huIds.isEmpty())
			{
				return FilterSql.ALLOW_NONE;
			}

			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
			final ImmutableSet<HuId> topLevelHuIds = handlingUnitsBL.getTopLevelHUs(huIds);
			final ISqlQueryFilter sqlQueryFilter = new InArrayQueryFilter<>(I_M_HU.COLUMNNAME_M_HU_ID, topLevelHuIds);
			return FilterSql.ofWhereClause(SqlAndParams.of(sqlQueryFilter.getSql(), sqlQueryFilter.getSqlParams(Env.getCtx())));
		}
	}

	protected boolean isAlwaysUseSameLayout()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCFG_AlwaysUseSameLayout, false);
	}

	protected RelatedProcessDescriptor createProcessDescriptor(@NonNull final Class<?> processClass)
	{
		return RelatedProcessDescriptor.builder()
				.processId(adProcessDAO.retrieveProcessIdByClassIfUnique(processClass))
				.displayPlace(RelatedProcessDescriptor.DisplayPlace.ViewQuickActions)
				.build();
	}

	@Value(staticConstructor = "of")
	private static class ViewLayoutKey
	{
		@NonNull
		WindowId windowId;

		@NonNull
		JSONViewDataType viewDataType;
	}
}
