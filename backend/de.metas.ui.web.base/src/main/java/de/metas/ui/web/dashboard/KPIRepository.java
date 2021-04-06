package de.metas.ui.web.dashboard;

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
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.base.model.I_WEBUI_KPI;
import de.metas.ui.web.base.model.I_WEBUI_KPI_Field;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.IADElementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Element;
import org.compiere.util.DisplayType;
import org.elasticsearch.action.search.SearchType;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class KPIRepository
{
	// services
	private static final Logger logger = LogManager.getLogger(KPIRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADElementDAO adElementDAO = Services.get(IADElementDAO.class);

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

		final ImmutableList<KPI> kpis = queryBL.createQueryBuilder(I_WEBUI_KPI.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream(I_WEBUI_KPI.class)
				.map(kpiDef -> createKPINoFail(kpiDef, kpiFieldDefsMap))
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
			@NonNull final ImmutableListMultimap<KPIId, I_WEBUI_KPI_Field> kpiFieldDefsMap)
	{
		try
		{
			return createKPI(kpiDef, kpiFieldDefsMap);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating KPI for {}", kpiDef, ex);
			return null;
		}
	}

	private KPI createKPI(
			@NonNull final I_WEBUI_KPI kpiDef,
			@NonNull final ImmutableListMultimap<KPIId, I_WEBUI_KPI_Field> kpiFieldDefsMap)
	{
		final KPIId kpiId = KPIId.ofRepoId(kpiDef.getWEBUI_KPI_ID());
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(kpiDef);

		final boolean generateComparation = kpiDef.isGenerateComparation();
		final Duration compareOffset = generateComparation
				? Duration.parse(kpiDef.getCompareOffset())
				: null;

		return KPI.builder()
				.id(kpiId)
				.caption(trls.getColumnTrl(I_WEBUI_KPI.COLUMNNAME_Name, kpiDef.getName()))
				.description(trls.getColumnTrl(I_WEBUI_KPI.COLUMNNAME_Description, kpiDef.getDescription()))
				.chartType(KPIChartType.forCode(kpiDef.getChartType()))
				.fields(kpiFieldDefsMap.get(kpiId)
						.stream()
						.map(kpiFieldDef -> createKPIField(kpiFieldDef, generateComparation))
						.collect(Collectors.toList()))
				//
				.compareOffset(compareOffset)
				//
				.timeRangeDefaults(KPITimeRangeDefaults.builder()
						.defaultTimeRangeFromString(kpiDef.getES_TimeRange())
						.defaultTimeRangeEndOffsetFromString(kpiDef.getES_TimeRange_End())
						.build())
				//
				.pollIntervalSec(kpiDef.getPollIntervalSec())
				//
				.esSearchIndex(kpiDef.getES_Index())
				//.esSearchTypes(kpiDef.getES_Type())
				.esSearchTypes(SearchType.DEFAULT) // FIXME HARDCODED?!
				.esQuery(kpiDef.getES_Query())
				//
				.build();
	}

	private KPIField createKPIField(@NonNull final I_WEBUI_KPI_Field kpiFieldDef, final boolean isComputeOffset)
	{
		final I_AD_Element adElement = adElementDAO.getById(kpiFieldDef.getAD_Element_ID());

		final String elementColumnName = adElement.getColumnName();
		Check.assumeNotNull(elementColumnName, "The element {} does not have a column name set", adElement);

		//
		// Extract field caption and description
		final IModelTranslationMap kpiFieldDefTrl = InterfaceWrapperHelper.getModelTranslationMap(kpiFieldDef);
		final ITranslatableString caption;
		final ITranslatableString description;
		if (Check.isEmpty(kpiFieldDef.getName(), true))
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
				.setFieldName(elementColumnName)
				.setGroupBy(kpiFieldDef.isGroupBy())
				//
				.setCaption(caption)
				.setOffsetCaption(offsetCaption)
				.setDescription(description)
				.setUnit(kpiFieldDef.getUOMSymbol())
				.setValueType(KPIFieldValueType.fromDisplayType(kpiFieldDef.getAD_Reference_ID()))
				.setNumberPrecision(extractNumberPrecision(kpiFieldDef.getAD_Reference_ID()))
				.setColor(kpiFieldDef.getColor())
				//
				.setESPath(kpiFieldDef.getES_FieldPath())
				.build();
	}

	@Nullable
	private static Integer extractNumberPrecision(final int displayType)
	{
		if (displayType == DisplayType.Integer)
		{
			return 0;
		}
		else if (displayType == DisplayType.Amount
				|| displayType == DisplayType.CostPrice
				|| displayType == DisplayType.Quantity)
		{
			return 2;
		}
		else
		{
			return null;
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
}
