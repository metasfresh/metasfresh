package de.metas.ui.web.dashboard;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Element;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.MoreObjects;

import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.base.model.I_WEBUI_Dashboard;
import de.metas.ui.web.base.model.I_WEBUI_DashboardItem;
import de.metas.ui.web.base.model.I_WEBUI_KPI;
import de.metas.ui.web.base.model.I_WEBUI_KPI_Field;
import de.metas.ui.web.dashboard.json.JSONDashboardChanges;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.session.UserSession;

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
	@Autowired
	private UserSession userSession;
	@Autowired
	private Client elasticsearchClient;
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<UserDashboardKey, UserDashboard> userDashboadCache = CCache.<UserDashboardKey, UserDashboard> newLRUCache(I_WEBUI_Dashboard.Table_Name + "#UserDashboard", Integer.MAX_VALUE, 0)
			.addResetForTableName(I_WEBUI_DashboardItem.Table_Name);
	private final CCache<Integer, KPI> kpisCache = CCache.<Integer, KPI> newLRUCache(I_WEBUI_KPI.Table_Name + "#KPIs", Integer.MAX_VALUE, 0)
			.addResetForTableName(I_WEBUI_KPI_Field.Table_Name);

	public UserDashboard getUserDashboard()
	{
		final UserDashboardKey userDashboardKey = createUserDashboardKey();
		return userDashboadCache.getOrLoad(userDashboardKey, () -> retrieveUserDashboard(userDashboardKey));
	}

	public UserDashboard getUserDashboard(final UserDashboardKey userDashboardKey)
	{
		return userDashboadCache.getOrLoad(userDashboardKey, () -> retrieveUserDashboard(userDashboardKey));
	}

	public void invalidateUserDashboard(final UserDashboardKey userDashboardKey)
	{
		userDashboadCache.remove(userDashboardKey);
	}

	private UserDashboardKey createUserDashboardKey()
	{
		final UserDashboardKey userDashboardKey = UserDashboardKey.of(userSession.getCtx());
		return userDashboardKey;
	}

	private UserDashboard retrieveUserDashboard(final UserDashboardKey key)
	{
		final Properties ctx = userSession.getCtx();
		final int adClientId = key.getAD_Client_ID();

		final I_WEBUI_Dashboard webuiDashboard = queryBL
				.createQueryBuilder(I_WEBUI_Dashboard.class, ctx, ITrx.TRXNAME_None)
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

		return createUserDashboard(ctx, webuiDashboard);
	}

	private UserDashboard createUserDashboard(final Properties ctx, final I_WEBUI_Dashboard webuiDashboard)
	{
		final int webuiDashboardId = webuiDashboard.getWEBUI_Dashboard_ID();

		final UserDashboard.Builder userDashboardBuilder = UserDashboard.builder()
				.setId(webuiDashboardId);

		retrieveWEBUI_DashboardItemsQuery(ctx, webuiDashboardId)
				//
				.create()
				.list(I_WEBUI_DashboardItem.class)
				.stream()
				.map(webuiDashboardItem -> createUserDashboardItem(webuiDashboardItem))
				.forEach(userDashboardItem -> userDashboardBuilder.addItem(userDashboardItem));

		return userDashboardBuilder.build();
	}

	private IQueryBuilder<I_WEBUI_DashboardItem> retrieveWEBUI_DashboardItemsQuery(final Properties ctx, final int webuiDashboardId)
	{
		return queryBL
				.createQueryBuilder(I_WEBUI_DashboardItem.class, ctx, ITrx.TRXNAME_None)
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
				.build();
	}

	public void invalidateKPI(final int id)
	{
		kpisCache.remove(id);
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

			final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(kpiDef);

			final String timeRangeStr = kpiDef.getES_TimeRange();
			final Duration timeRange = Check.isEmpty(timeRangeStr, true) ? Duration.ZERO : Duration.parse(timeRangeStr);

			return KPI.builder()
					.setId(kpiDef.getWEBUI_KPI_ID())
					.setCaption(trls.getColumnTrl(I_WEBUI_KPI.COLUMNNAME_Name, kpiDef.getName()))
					.setDescription(trls.getColumnTrl(I_WEBUI_KPI.COLUMNNAME_Description, kpiDef.getDescription()))
					.setChartType(KPIChartType.forCode(kpiDef.getChartType()))
					.setFields(retrieveKPIFields(WEBUI_KPI_ID))
					//
					.setTimeRange(timeRange)
					//
					.setESSearchIndex(kpiDef.getES_Index())
					.setESSearchTypes(kpiDef.getES_Type())
					.setESQuery(kpiDef.getES_Query())
					.setElasticsearchClient(elasticsearchClient)
					//
					.build();
		});
	}

	private List<KPIField> retrieveKPIFields(final int WEBUI_KPI_ID)
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
				.map(kpiField -> createKPIField(kpiField))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static final KPIField createKPIField(final I_WEBUI_KPI_Field kpiFieldDef)
	{
		final I_AD_Element adElement = kpiFieldDef.getAD_Element();
		final String fieldName = adElement.getColumnName();

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
			caption = ImmutableTranslatableString.constant(kpiFieldDef.getName());
			description = ImmutableTranslatableString.empty();
		}

		return KPIField.builder()
				.setFieldName(fieldName)
				.setCaption(caption)
				.setDescription(description)
				.setValueType(KPIFieldValueType.fromDisplayType(kpiFieldDef.getAD_Reference_ID()))
				.setESPath(kpiFieldDef.getES_FieldPath())
				.setESTimeField(kpiFieldDef.isES_TimeField())
				.build();
	}

	public void changeUserDashboardKPIs(final JSONDashboardChanges jsonDashboardChanges)
	{
		final UserDashboardKey userDashboardKey = createUserDashboardKey();
		final UserDashboard userDashboard = getUserDashboard(userDashboardKey);

		Services.get(ITrxManager.class)
				.run((trxName) -> {
					final Properties ctx = userSession.getCtx();
					int nextSeqNo = 10;
					for (final int itemId : jsonDashboardChanges.getDashboardItemIdsOrder())
					{
						final UserDashboardItem item = userDashboard.getKPIItemById(itemId);
						if (item == null)
						{
							continue;
						}

						final I_WEBUI_DashboardItem webuiDashboardItem = InterfaceWrapperHelper.create(ctx, itemId, I_WEBUI_DashboardItem.class, ITrx.TRXNAME_ThreadInherited);
						webuiDashboardItem.setSeqNo(nextSeqNo);
						InterfaceWrapperHelper.save(webuiDashboardItem);

						nextSeqNo += 10;
					}
				});

		invalidateUserDashboard(userDashboardKey);
	}

	@Immutable
	@SuppressWarnings("serial")
	private static final class UserDashboardKey implements Serializable
	{
		public static final UserDashboardKey of(final Properties ctx)
		{
			final int adClientId = Env.getAD_Client_ID(ctx);
			return new UserDashboardKey(adClientId);
		}

		private final int adClientId;

		private UserDashboardKey(final int adClientId)
		{
			super();
			this.adClientId = adClientId < 0 ? -1 : adClientId;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("AD_Client_ID", adClientId)
					.toString();
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(adClientId);
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (obj == this)
			{
				return true;
			}
			if (obj instanceof UserDashboardKey)
			{
				final UserDashboardKey other = (UserDashboardKey)obj;
				return adClientId == other.adClientId;
			}
			return false;
		}

		public int getAD_Client_ID()
		{
			return adClientId;
		}
	}
}
