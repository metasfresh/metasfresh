package de.metas.ui.web.dashboard;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Element;
import org.compiere.util.CCache;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.base.model.I_WEBUI_KPI;
import de.metas.ui.web.base.model.I_WEBUI_KPI_Field;
import de.metas.ui.web.exceptions.EntityNotFoundException;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class KPIRepository
{
	// services
	private static final Logger logger = LogManager.getLogger(KPIRepository.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, KPI> kpisCache = CCache.<Integer, KPI> newLRUCache(I_WEBUI_KPI.Table_Name + "#KPIs", Integer.MAX_VALUE, 0)
			.addResetForTableName(I_WEBUI_KPI_Field.Table_Name);

	public void invalidateKPI(final int id)
	{
		kpisCache.remove(id);
	}


	public Collection<KPI> getKPIs()
	{
		final List<Integer> kpiIds = queryBL.createQueryBuilder(I_WEBUI_KPI.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				.create()
				.listIds();

		return getKPIs(kpiIds);
	}
	
	private Collection<KPI> getKPIs(final Collection<Integer> kpiIds)
	{
		return kpisCache.getAllOrLoad(kpiIds, this::retrieveKPIs);
	}

	private final Map<Integer, KPI> retrieveKPIs(final Collection<Integer> kpiIds)
	{
		return queryBL.createQueryBuilder(I_WEBUI_KPI.class)
				.addInArrayFilter(I_WEBUI_KPI.COLUMN_WEBUI_KPI_ID, kpiIds)
				.create()
				.stream(I_WEBUI_KPI.class)
				.map(kpiDef -> {
					try
					{
						return createKPI(kpiDef);
					}
					catch (final Exception ex)
					{
						logger.warn("Failed creating KPI for {}", kpiDef, ex);
						return null;
					}
				})
				.filter(kpi -> kpi != null)
				.collect(GuavaCollectors.toImmutableMapByKey(KPI::getId));
	}

	public KPI getKPI(final int id)
	{
		final KPI kpi = getKPIOrNull(id);
		if (kpi == null)
		{
			throw new EntityNotFoundException("KPI (id=" + id + ")");
		}
		return kpi;
	}

	KPI getKPIOrNull(final int WEBUI_KPI_ID)
	{
		if (WEBUI_KPI_ID <= 0)
		{
			return null;
		}
		return kpisCache.getOrLoad(WEBUI_KPI_ID, () -> {
			final I_WEBUI_KPI kpiDef = InterfaceWrapperHelper.create(Env.getCtx(), WEBUI_KPI_ID, I_WEBUI_KPI.class, ITrx.TRXNAME_None);
			if (kpiDef == null)
			{
				return null;
			}

			return createKPI(kpiDef);
		});
	}

	private KPI createKPI(final I_WEBUI_KPI kpiDef)
	{
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(kpiDef);

		Duration compareOffset = null;
		if (kpiDef.isGenerateComparation())
		{
			final String compareOffetStr = kpiDef.getCompareOffset();
			compareOffset = Duration.parse(compareOffetStr);
		}

		return KPI.builder()
				.setId(kpiDef.getWEBUI_KPI_ID())
				.setCaption(trls.getColumnTrl(I_WEBUI_KPI.COLUMNNAME_Name, kpiDef.getName()))
				.setDescription(trls.getColumnTrl(I_WEBUI_KPI.COLUMNNAME_Description, kpiDef.getDescription()))
				.setChartType(KPIChartType.forCode(kpiDef.getChartType()))
				.setFields(retrieveKPIFields(kpiDef.getWEBUI_KPI_ID(), kpiDef.isGenerateComparation()))
				//
				.setCompareOffset(compareOffset)
				//
				.setTimeRangeDefaults(KPITimeRangeDefaults.builder()
						.defaultTimeRangeFromString(kpiDef.getES_TimeRange())
						.defaultTimeRangeEndOffsetFromString(kpiDef.getES_TimeRange_End())
						.build())
				//
				.setPollIntervalSec(kpiDef.getPollIntervalSec())
				//
				.setESSearchIndex(kpiDef.getES_Index())
				.setESSearchTypes(kpiDef.getES_Type())
				.setESQuery(kpiDef.getES_Query())
				//
				.build();
	}

	private List<KPIField> retrieveKPIFields(final int WEBUI_KPI_ID, final boolean isComputeOffset)
	{
		return queryBL.createQueryBuilder(I_WEBUI_KPI_Field.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_WEBUI_KPI_Field.COLUMN_WEBUI_KPI_ID, WEBUI_KPI_ID)
				.addOnlyActiveRecordsFilter()
				//
				.orderBy()
				// TODO: add SeqNo
				.addColumn(I_WEBUI_KPI_Field.COLUMN_WEBUI_KPI_Field_ID)
				.endOrderBy()
				//
				.create()
				.stream(I_WEBUI_KPI_Field.class)
				.map(kpiField -> createKPIField(kpiField, isComputeOffset))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static final KPIField createKPIField(final I_WEBUI_KPI_Field kpiFieldDef, final boolean isComputeOffset)
	{
		final I_AD_Element adElement = kpiFieldDef.getAD_Element();
		
		final String elementColumnName = adElement.getColumnName ();
		Check.assumeNotNull(elementColumnName, "The element {} does not have a column name set", adElement);
		
		
		final String fieldName = elementColumnName;

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
			description = ImmutableTranslatableString.empty();
		}

		//
		// Extract offset field's caption and description
		final ITranslatableString offsetCaption;
		if (!isComputeOffset)
		{
			offsetCaption = ImmutableTranslatableString.empty();
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
				.setFieldName(fieldName)
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

	private static final Integer extractNumberPrecision(final int displayType)
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

}
