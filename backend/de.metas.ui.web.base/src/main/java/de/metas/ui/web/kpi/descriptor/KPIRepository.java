/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.kpi.descriptor;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.base.model.I_WEBUI_KPI;
import de.metas.ui.web.base.model.I_WEBUI_KPI_Field;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.kpi.KPITimeRangeDefaults;
import de.metas.ui.web.kpi.descriptor.elasticsearch.ElasticsearchDatasourceDescriptor;
import de.metas.ui.web.kpi.descriptor.elasticsearch.ElasticsearchDatasourceFieldDescriptor;
import de.metas.ui.web.kpi.descriptor.sql.SQLDatasourceDescriptor;
import de.metas.ui.web.kpi.descriptor.sql.SQLDatasourceFieldDescriptor;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.element.api.IADElementDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Element;
import org.compiere.util.DisplayType;
import org.elasticsearch.action.search.SearchType;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KPIRepository
{
	// services
	private static final Logger logger = LogManager.getLogger(KPIRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADElementDAO adElementDAO = Services.get(IADElementDAO.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private final CCache<Integer, KPIsMap> cache = CCache.<Integer, KPIsMap>builder()
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.tableName(I_WEBUI_KPI.Table_Name)
			.additionalTableNameToResetFor(I_WEBUI_KPI_Field.Table_Name)
			.build();

	public void invalidateCache()
	{
		cache.reset();
	}

	public Collection<KPI> getKPIs()
	{
		return getKPIsMap().toCollection();
	}

	private KPIsMap getKPIsMap()
	{
		return cache.getOrLoad(0, this::retrieveKPIsMap);
	}

	private KPIsMap retrieveKPIsMap()
	{
		final ImmutableListMultimap<KPIId, I_WEBUI_KPI_Field> kpiFieldDefsMap = queryBL.createQueryBuilder(I_WEBUI_KPI_Field.class)
				.addOnlyActiveRecordsFilter()
				// TODO: add SeqNo
				.orderBy(I_WEBUI_KPI_Field.COLUMN_WEBUI_KPI_Field_ID)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						kpiFieldDef -> KPIId.ofRepoId(kpiFieldDef.getWEBUI_KPI_ID()),
						kpiFieldDef -> kpiFieldDef));

		final KPILoadingContext loadingCtx = KPILoadingContext.builder()
				.kpiFieldDefsMap(kpiFieldDefsMap)
				.build();

		final ImmutableList<KPI> kpis = queryBL.createQueryBuilder(I_WEBUI_KPI.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream(I_WEBUI_KPI.class)
				.map(kpiDef -> createKPINoFail(kpiDef, loadingCtx))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		return new KPIsMap(kpis);
	}

	public KPI getKPI(final KPIId id)
	{
		final KPI kpi = getKPIOrNull(id);
		if (kpi == null)
		{
			throw new EntityNotFoundException("KPI (id=" + id + ")");
		}
		return kpi;
	}

	@Nullable
	KPI getKPIOrNull(@Nullable final KPIId kpiId)
	{
		return kpiId != null
				? getKPIsMap().getByIdOrNull(kpiId)
				: null;
	}

	public KPISupplier getKPISupplier(@NonNull final KPIId kpiId)
	{
		return new KPISupplier(kpiId, this);
	}

	@Nullable
	private KPI createKPINoFail(
			@NonNull final I_WEBUI_KPI kpiDef,
			@NonNull final KPILoadingContext loadingCtx)
	{
		try
		{
			return fromRecord(kpiDef, loadingCtx);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating KPI for {}", kpiDef, ex);
			return null;
		}
	}

	private KPI fromRecord(
			@NonNull final I_WEBUI_KPI kpiDef,
			@NonNull final KPILoadingContext loadingCtx)
	{
		final KPIId kpiId = KPIId.ofRepoId(kpiDef.getWEBUI_KPI_ID());
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(kpiDef);

		final boolean generateComparison = kpiDef.isGenerateComparation();
		final Duration compareOffset = generateComparison
				? Duration.parse(kpiDef.getCompareOffset())
				: null;

		final KPIDatasourceType datasourceType = extractKPIDatasourceType(kpiDef);
		final ElasticsearchDatasourceDescriptor elasticsearchDatasource;
		final SQLDatasourceDescriptor sqlDatasource;
		if (datasourceType == KPIDatasourceType.ELASTICSEARCH)
		{
			elasticsearchDatasource = extractElasticsearchDatasourceDescriptor(kpiDef, loadingCtx);
			sqlDatasource = null;
		}
		else if (datasourceType == KPIDatasourceType.SQL)
		{
			elasticsearchDatasource = null;
			sqlDatasource = extractSQLDatasourceDescriptor(kpiDef, loadingCtx);
		}
		else
		{
			throw new AdempiereException("Unknown datasource type: " + datasourceType);
		}

		return KPI.builder()
				.id(kpiId)
				.caption(trls.getColumnTrl(I_WEBUI_KPI.COLUMNNAME_Name, kpiDef.getName()))
				.description(trls.getColumnTrl(I_WEBUI_KPI.COLUMNNAME_Description, kpiDef.getDescription()))
				.chartType(KPIChartType.ofCode(kpiDef.getChartType()))
				.fields(loadingCtx
						.getFields(kpiId)
						.stream()
						.map(kpiFieldDef -> fromRecord(kpiFieldDef, generateComparison, loadingCtx))
						.collect(Collectors.toList()))
				//
				.compareOffset(compareOffset)
				//
				.timeRangeDefaults(KPITimeRangeDefaults.builder()
						.defaultTimeRange(parseDuration(kpiDef.getES_TimeRange()).orElse(null))
						.defaultTimeRangeEndOffset(parseDuration(kpiDef.getES_TimeRange_End()).orElse(null))
						.build())
				//
				.datasourceType(datasourceType)
				.elasticsearchDatasource(elasticsearchDatasource)
				.sqlDatasource(sqlDatasource)
				//
				.allowedStaleDuration(kpiDef.getKPI_AllowedStaledTimeInSec() > 0
						? Duration.ofSeconds(kpiDef.getKPI_AllowedStaledTimeInSec())
						: Duration.ofSeconds(0))
				//
				.build();
	}

	private static KPIDatasourceType extractKPIDatasourceType(final I_WEBUI_KPI kpiDef)
	{
		return KPIDatasourceType.ofCode(kpiDef.getKPI_DataSource_Type());
	}

	private static Optional<Duration> parseDuration(@Nullable final String durationStr)
	{
		return StringUtils.trimBlankToOptional(durationStr).map(Duration::parse);
	}

	private ElasticsearchDatasourceDescriptor extractElasticsearchDatasourceDescriptor(
			@NonNull final I_WEBUI_KPI kpiDef,
			@NonNull final KPILoadingContext loadingCtx)
	{
		return ElasticsearchDatasourceDescriptor.builder()
				.esSearchIndex(kpiDef.getES_Index())
				.esSearchTypes(SearchType.DEFAULT)
				.esQuery(kpiDef.getES_Query())
				.fields(loadingCtx
						.getFields(KPIId.ofRepoId(kpiDef.getWEBUI_KPI_ID()))
						.stream()
						.map(kpiFieldDef -> extractElasticsearchDatasourceFieldDescriptor(kpiFieldDef, loadingCtx))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private ElasticsearchDatasourceFieldDescriptor extractElasticsearchDatasourceFieldDescriptor(
			@NonNull final I_WEBUI_KPI_Field kpiFieldDef,
			@NonNull final KPILoadingContext loadingCtx)
	{
		return ElasticsearchDatasourceFieldDescriptor.builder()
				.fieldName(extractFieldName(kpiFieldDef, loadingCtx))
				.esPath(kpiFieldDef.getES_FieldPath())
				.valueType(KPIFieldValueType.fromDisplayType(kpiFieldDef.getAD_Reference_ID()))
				.build();
	}

	private SQLDatasourceDescriptor extractSQLDatasourceDescriptor(
			@NonNull final I_WEBUI_KPI kpiDef,
			@NonNull final KPILoadingContext loadingCtx)
	{
		final String sourceTableName = adTableDAO.retrieveTableName(kpiDef.getSource_Table_ID());
		assert sourceTableName != null;

		final AdWindowId targetWindowId = AdWindowId.ofRepoIdOrNull(kpiDef.getAD_Window_ID());

		return SQLDatasourceDescriptor.builder()
				.targetWindowId(WindowId.ofNullable(targetWindowId))
				.sourceTableName(sourceTableName)
				.sqlFrom(kpiDef.getSQL_From())
				.sqlWhereClause(kpiDef.getSQL_WhereClause())
				.sqlDetailsWhereClause(kpiDef.getSQL_Details_WhereClause())
				.applySecuritySettings(kpiDef.isApplySecuritySettings())
				.sqlGroupAndOrderBy(kpiDef.getSQL_GroupAndOrderBy())
				.fields(loadingCtx
						.getFields(KPIId.ofRepoId(kpiDef.getWEBUI_KPI_ID()))
						.stream()
						.map(kpiFieldDef -> extractSQLDatasourceFieldDescriptor(kpiFieldDef, loadingCtx))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private SQLDatasourceFieldDescriptor extractSQLDatasourceFieldDescriptor(
			@NonNull final I_WEBUI_KPI_Field kpiFieldDef,
			@NonNull final KPILoadingContext loadingCtx)
	{
		return SQLDatasourceFieldDescriptor.builder()
				.fieldName(extractFieldName(kpiFieldDef, loadingCtx))
				.sqlSelect(kpiFieldDef.getSQL_Select())
				.build();
	}

	private KPIField fromRecord(
			@NonNull final I_WEBUI_KPI_Field kpiFieldDef,
			final boolean isComputeOffset,
			@NonNull final KPILoadingContext loadingCtx)
	{
		final String fieldName = extractFieldName(kpiFieldDef, loadingCtx);
		final I_AD_Element adElement = loadingCtx.getAdElementById(kpiFieldDef.getAD_Element_ID(), adElementDAO);

		//
		// Extract field caption and description
		final IModelTranslationMap kpiFieldDefTrl = InterfaceWrapperHelper.getModelTranslationMap(kpiFieldDef);
		final ITranslatableString caption;
		final ITranslatableString description;
		if (Check.isBlank(kpiFieldDef.getName()))
		{
			final IModelTranslationMap adElementTrl = InterfaceWrapperHelper.getModelTranslationMap(adElement);
			caption = adElementTrl.getColumnTrl(I_AD_Element.COLUMNNAME_Name, adElement.getName());
			description = adElementTrl.getColumnTrl(I_AD_Element.COLUMNNAME_Description, adElement.getDescription());
		}
		else
		{
			caption = kpiFieldDefTrl.getColumnTrl(I_WEBUI_KPI_Field.COLUMNNAME_Name, kpiFieldDef.getName());
			description = TranslatableStrings.empty();
		}

		//
		// Extract offset field's caption and description
		final ITranslatableString offsetCaption;
		if (!isComputeOffset)
		{
			offsetCaption = TranslatableStrings.empty();
		}
		else if (Check.isEmpty(kpiFieldDef.getOffsetName(), true))
		{
			offsetCaption = caption;
		}
		else
		{
			offsetCaption = kpiFieldDefTrl.getColumnTrl(I_WEBUI_KPI_Field.COLUMNNAME_OffsetName, kpiFieldDef.getOffsetName());
		}

		return KPIField.builder()
				.fieldName(fieldName)
				.groupBy(kpiFieldDef.isGroupBy())
				//
				.caption(caption)
				.offsetCaption(offsetCaption)
				.description(description)
				.unit(kpiFieldDefTrl.getColumnTrl(I_WEBUI_KPI_Field.COLUMNNAME_UOMSymbol, kpiFieldDef.getUOMSymbol()))
				.valueType(KPIFieldValueType.fromDisplayType(kpiFieldDef.getAD_Reference_ID()))
				.numberPrecision(extractNumberPrecision(kpiFieldDef.getAD_Reference_ID()).orElse(null))
				.color(kpiFieldDef.getColor())
				.build();
	}

	private String extractFieldName(
			@NonNull final I_WEBUI_KPI_Field kpiFieldDef,
			@NonNull final KPILoadingContext loadingCtx)
	{
		final I_AD_Element adElement = loadingCtx.getAdElementById(kpiFieldDef.getAD_Element_ID(), adElementDAO);
		final String fieldName = adElement.getColumnName();
		if (Check.isBlank(fieldName))
		{
			throw new AdempiereException("AD_Element " + adElement + " does not have a column name set");
		}
		return fieldName;
	}

	private static Optional<Integer> extractNumberPrecision(final int displayType)
	{
		if (displayType == DisplayType.Integer)
		{
			return Optional.of(0);
		}
		else if (displayType == DisplayType.Amount
				|| displayType == DisplayType.CostPrice
				|| displayType == DisplayType.Quantity)
		{
			return Optional.of(2);
		}
		else
		{
			return Optional.empty();
		}
	}

	private static class KPIsMap
	{
		private final ImmutableMap<KPIId, KPI> byId;

		KPIsMap(final Collection<KPI> kpis)
		{
			byId = Maps.uniqueIndex(kpis, KPI::getId);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("size", byId.size())
					.toString();
		}

		public Collection<KPI> toCollection()
		{
			return byId.values();
		}

		@Nullable
		public KPI getByIdOrNull(@NonNull final KPIId kpiId)
		{
			return byId.get(kpiId);
		}
	}

	@Builder
	@ToString
	private static class KPILoadingContext
	{
		@NonNull private final ImmutableListMultimap<KPIId, I_WEBUI_KPI_Field> kpiFieldDefsMap;
		@NonNull private final HashMap<Integer, I_AD_Element> adElementsById = new HashMap<>();

		public ImmutableList<I_WEBUI_KPI_Field> getFields(@NonNull final KPIId kpiId)
		{
			return kpiFieldDefsMap.get(kpiId);
		}

		public I_AD_Element getAdElementById(final int adElementId, @NonNull final IADElementDAO adElementDAO)
		{
			return adElementsById.computeIfAbsent(adElementId, adElementDAO::getById);
		}
	}
}
