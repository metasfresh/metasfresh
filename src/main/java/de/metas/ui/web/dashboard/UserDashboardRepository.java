package de.metas.ui.web.dashboard;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Element;
import org.compiere.util.CCache;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.base.model.I_WEBUI_Dashboard;
import de.metas.ui.web.base.model.I_WEBUI_DashboardItem;
import de.metas.ui.web.base.model.I_WEBUI_KPI;
import de.metas.ui.web.base.model.I_WEBUI_KPI_Field;
import de.metas.ui.web.base.model.X_WEBUI_KPI;
import de.metas.ui.web.dashboard.json.JsonUserDashboardItemAddRequest;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.json.JSONPatchEvent;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.ui.web.base
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

@Service
public class UserDashboardRepository
{
	//
	// Services
	private static final Logger logger = LogManager.getLogger(UserDashboardRepository.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<UserDashboardKey, UserDashboard> userDashboadCache = CCache.<UserDashboardKey, UserDashboard> newLRUCache(I_WEBUI_Dashboard.Table_Name + "#UserDashboard", Integer.MAX_VALUE, 0)
			.addResetForTableName(I_WEBUI_DashboardItem.Table_Name);
	private final CCache<Integer, KPI> kpisCache = CCache.<Integer, KPI> newLRUCache(I_WEBUI_KPI.Table_Name + "#KPIs", Integer.MAX_VALUE, 0)
			.addResetForTableName(I_WEBUI_KPI_Field.Table_Name);

	public UserDashboard getUserDashboard(final UserDashboardKey userDashboardKey)
	{
		return userDashboadCache.getOrLoad(userDashboardKey, () -> retrieveUserDashboard(userDashboardKey));
	}

	private void invalidateUserDashboard(final UserDashboard userDashboard)
	{
		userDashboadCache.remove(UserDashboardKey.of(userDashboard.getAdClientId()));
	}

	private UserDashboard retrieveUserDashboard(final UserDashboardKey key)
	{
		final int adClientId = key.getAdClientId();

		final I_WEBUI_Dashboard webuiDashboard = queryBL
				.createQueryBuilder(I_WEBUI_Dashboard.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_WEBUI_Dashboard.COLUMN_AD_Client_ID, Env.CTXVALUE_AD_Client_ID_System, adClientId)
				//
				.orderBy()
				.addColumn(I_WEBUI_Dashboard.COLUMN_AD_Client_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_WEBUI_Dashboard.COLUMN_IsDefault, Direction.Descending, Nulls.Last)
				.addColumn(I_WEBUI_Dashboard.COLUMN_WEBUI_Dashboard_ID)
				.endOrderBy()
				//
				.create()
				.first(I_WEBUI_Dashboard.class);

		if (webuiDashboard == null)
		{
			return UserDashboard.EMPTY;
		}

		return createUserDashboard(webuiDashboard);
	}

	private UserDashboard createUserDashboard(final I_WEBUI_Dashboard webuiDashboard)
	{
		final int webuiDashboardId = webuiDashboard.getWEBUI_Dashboard_ID();

		final UserDashboard.Builder userDashboardBuilder = UserDashboard.builder()
				.setId(webuiDashboardId)
				.setAdClientId(webuiDashboard.getAD_Client_ID());

		retrieveWEBUI_DashboardItemsQuery(webuiDashboardId)
				//
				.create()
				.list(I_WEBUI_DashboardItem.class)
				.stream()
				.map(webuiDashboardItem -> createUserDashboardItem(webuiDashboardItem))
				.forEach(userDashboardItem -> userDashboardBuilder.addItem(userDashboardItem));

		return userDashboardBuilder.build();
	}

	private IQueryBuilder<I_WEBUI_DashboardItem> retrieveWEBUI_DashboardItemsQuery(final int webuiDashboardId)
	{
		return queryBL
				.createQueryBuilder(I_WEBUI_DashboardItem.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_WEBUI_DashboardItem.COLUMN_WEBUI_Dashboard_ID, webuiDashboardId)
				//
				.orderBy()
				.addColumn(I_WEBUI_DashboardItem.COLUMN_SeqNo, Direction.Ascending, Nulls.First)
				.addColumn(I_WEBUI_DashboardItem.COLUMN_WEBUI_DashboardItem_ID)
				.endOrderBy()
		//
		;

	}

	private UserDashboardItem createUserDashboardItem(final I_WEBUI_DashboardItem webuiDashboardItem)
	{
		final IModelTranslationMap trlsMap = InterfaceWrapperHelper.getModelTranslationMap(webuiDashboardItem);

		return UserDashboardItem.builder()
				.setId(webuiDashboardItem.getWEBUI_DashboardItem_ID())
				.setCaption(trlsMap.getColumnTrl(I_WEBUI_DashboardItem.COLUMNNAME_Name, webuiDashboardItem.getName()))
				.setUrl(webuiDashboardItem.getURL())
				.setSeqNo(webuiDashboardItem.getSeqNo())
				.setWidgetType(DashboardWidgetType.ofCode(webuiDashboardItem.getWEBUI_DashboardWidgetType()))
				.setKPI(() -> getKPIOrNull(webuiDashboardItem.getWEBUI_KPI_ID()))
				//
				.setTimeRangeDefaults(KPITimeRangeDefaults.builder()
						.defaultTimeRangeFromString(webuiDashboardItem.getES_TimeRange())
						.defaultTimeRangeEndOffsetFromString(webuiDashboardItem.getES_TimeRange_End())
						.build())
				//
				.build();
	}

	public void invalidateKPI(final int id)
	{
		kpisCache.remove(id);
	}

	public Collection<KPI> getKPIsAvailableToAdd()
	{
		final List<Integer> kpiIds = queryBL.createQueryBuilder(I_WEBUI_KPI.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				.create()
				.listIds();

		return getKPIs(kpiIds);
	}

	public Collection<KPI> getTargetIndicatorsAvailableToAdd()
	{
		final List<Integer> kpiIds = queryBL.createQueryBuilder(I_WEBUI_KPI.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				.addEqualsFilter(I_WEBUI_KPI.COLUMN_ChartType, X_WEBUI_KPI.CHARTTYPE_Metric)
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

	private KPI getKPIOrNull(final int WEBUI_KPI_ID)
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
		final String fieldName = adElement.getColumnName();

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

	public static enum DashboardPatchPath
	{
		orderedItemIds
	};

	public void changeDashboardItems(@NonNull final UserDashboard userDashboard, @NonNull final DashboardWidgetType widgetType, @NonNull final List<JSONPatchEvent<DashboardPatchPath>> events)
	{
		if (events.isEmpty())
		{
			throw new AdempiereException("no events");
		}

		//
		// Extract change actions
		final List<Runnable> changeActions = new ArrayList<>(events.size());
		for (final JSONPatchEvent<DashboardPatchPath> event : events)
		{
			if (!event.isReplace())
			{
				throw new AdempiereException("Invalid event operation").setParameter("event", event);
			}

			final DashboardPatchPath path = event.getPath();
			if (DashboardPatchPath.orderedItemIds.equals(path))
			{
				final List<Integer> orderItemIds = event.getValueAsIntegersList();
				final Set<Integer> allItemIds = userDashboard.getItemIds(widgetType);
				changeActions.add(() -> changeDashboardItemsOrder(userDashboard, orderItemIds, allItemIds));
			}
			else
			{
				throw new AdempiereException("Unknown path").setParameter("event", event).setParameter("availablePaths", DashboardPatchPath.values());
			}
		}

		//
		// Execute the change actions
		Services.get(ITrxManager.class).run(ITrx.TRXNAME_ThreadInherited, () -> {
			changeActions.forEach(Runnable::run);
		});
	}

	private void changeDashboardItemsOrder(final UserDashboard userDashboard, final List<Integer> requestOrderedItemIds, final Collection<Integer> allItemIdsCollection)
	{
		final List<Integer> allItemIds = ImmutableList.copyOf(allItemIdsCollection); // convert it to list

		//
		// Start building the orderedIds list by adding all the IDs from the request
		final List<Integer> orderedIds = requestOrderedItemIds
				.stream()
				.filter(allItemIds::contains) // skip those IDs which are not present in our "all" ids list
				.collect(Collectors.toCollection(ArrayList::new));

		// At the end of orderedIds all all those ids which where not present in provided request.
		allItemIds.forEach(id -> {
			if (!orderedIds.contains(id))
			{
				orderedIds.add(id);
			}
		});

		// If there was no change then stop here
		if (allItemIds.equals(orderedIds))
		{
			return;
		}

		//
		// Persist changes
		Services.get(ITrxManager.class)
				.run((trxName) -> {
					int nextSeqNo = 10;
					for (final int itemId : orderedIds)
					{
						final I_WEBUI_DashboardItem webuiDashboardItem = InterfaceWrapperHelper.load(itemId, I_WEBUI_DashboardItem.class);
						webuiDashboardItem.setSeqNo(nextSeqNo);
						InterfaceWrapperHelper.save(webuiDashboardItem);

						nextSeqNo += 10;
					}
				});

		// Invalidate the dashboard
		invalidateUserDashboard(userDashboard);
	}

	/** @return itemId */
	public int addUserDashboardItem(final UserDashboard userDashboard, @NonNull final DashboardWidgetType dashboardWidgetType, @NonNull final JsonUserDashboardItemAddRequest request)
	{
		final int userDashboardId = userDashboard.getId();

		//
		// Get the KPI
		final int kpiId = request.getKpiId();
		if (kpiId <= 0)
		{
			throw new AdempiereException("kpiId is not set")
					.setParameter("request", request);
		}
		final I_WEBUI_KPI kpi = InterfaceWrapperHelper.loadOutOfTrx(kpiId, I_WEBUI_KPI.class);

		//
		// Create dashboard item in database
		final int itemId;
		{
			final int seqNo = retrieveLastSeqNo(userDashboardId, dashboardWidgetType) + 10;
			final String esTimeRange = request.getInterval() != null ? request.getInterval().getEsTimeRange() : null;
			final String esTimeRangeEnd = request.getWhen() != null ? request.getWhen().getEsTimeRangeEnd() : null;
			//
			final I_WEBUI_DashboardItem webuiDashboardItem = InterfaceWrapperHelper.newInstance(I_WEBUI_DashboardItem.class);
			webuiDashboardItem.setWEBUI_Dashboard_ID(userDashboardId);
			webuiDashboardItem.setIsActive(true);
			webuiDashboardItem.setName(kpi.getName());
			webuiDashboardItem.setSeqNo(seqNo);
			webuiDashboardItem.setWEBUI_KPI_ID(kpiId);
			webuiDashboardItem.setWEBUI_DashboardWidgetType(dashboardWidgetType.getCode());
			webuiDashboardItem.setES_TimeRange(esTimeRange);
			webuiDashboardItem.setES_TimeRange_End(esTimeRangeEnd);
			InterfaceWrapperHelper.save(webuiDashboardItem);

			itemId = webuiDashboardItem.getWEBUI_DashboardItem_ID();
		}

		// Cache invalidate
		invalidateUserDashboard(userDashboard);

		// Return the created dashboard itemId
		return itemId;
	}

	public void deleteUserDashboardItem(final UserDashboard dashboard, final DashboardWidgetType dashboardWidgetType, final int itemId)
	{
		dashboard.getItemById(dashboardWidgetType, itemId); // will fail if itemId does not exist

		final I_WEBUI_DashboardItem item = InterfaceWrapperHelper.load(itemId, I_WEBUI_DashboardItem.class);
		InterfaceWrapperHelper.delete(item);

		invalidateUserDashboard(dashboard);
	}

	private final int retrieveLastSeqNo(final int dashboardId, final DashboardWidgetType dashboardWidgetType)
	{
		final Integer maxSeqNo = queryBL.createQueryBuilder(I_WEBUI_DashboardItem.class)
				.addEqualsFilter(I_WEBUI_DashboardItem.COLUMN_WEBUI_Dashboard_ID, dashboardId)
				.addEqualsFilter(I_WEBUI_DashboardItem.COLUMN_WEBUI_DashboardWidgetType, dashboardWidgetType.getCode())
				.create()
				.aggregate(I_WEBUI_DashboardItem.COLUMN_SeqNo, IQuery.AGGREGATE_MAX, Integer.class);

		return maxSeqNo != null ? maxSeqNo : 0;
	}

	//
	//
	//
	@Immutable
	@SuppressWarnings("serial")
	@Value
	public static final class UserDashboardKey implements Serializable
	{
		public static final UserDashboardKey of(final int adClientId)
		{
			return new UserDashboardKey(adClientId);
		}

		private final int adClientId;

		private UserDashboardKey(final int adClientId)
		{
			super();
			this.adClientId = adClientId < 0 ? -1 : adClientId;
		}
	}

}
