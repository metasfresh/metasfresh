package de.metas.ui.web.dashboard;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.Language;
import de.metas.i18n.po.POTrlInfo;
import de.metas.i18n.po.POTrlRepository;
import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.base.model.I_WEBUI_Dashboard;
import de.metas.ui.web.base.model.I_WEBUI_DashboardItem;
import de.metas.ui.web.base.model.I_WEBUI_KPI;
import de.metas.ui.web.dashboard.UserDashboardItemChangeResult.UserDashboardItemChangeResultBuilder;
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
	@Autowired
	private KPIRepository kpisRepo;

	private final CCache<UserDashboardKey, Integer> key2dashboardId = CCache.<UserDashboardKey, Integer> newLRUCache(I_WEBUI_Dashboard.Table_Name + "#key2DashboardId", Integer.MAX_VALUE, 0);

	private final CCache<Integer, UserDashboard> dashboadsCache = CCache.<Integer, UserDashboard> newLRUCache(I_WEBUI_Dashboard.Table_Name + "#UserDashboard", Integer.MAX_VALUE, 0)
			.addResetForTableName(I_WEBUI_DashboardItem.Table_Name);

	private UserDashboard getUserDashboardById(final int dashboardId)
	{
		return dashboadsCache.getOrLoad(dashboardId, () -> retrieveUserDashboard(dashboardId));
	}

	public UserDashboard getUserDashboard(final UserDashboardKey userDashboardKey)
	{
		final Integer dashboardId = key2dashboardId.getOrLoad(userDashboardKey, () -> retrieveUserDashboardId(userDashboardKey));
		if (dashboardId == null || dashboardId <= 0)
		{
			return UserDashboard.EMPTY;
		}

		return getUserDashboardById(dashboardId);
	}

	private void invalidateUserDashboard(final int dashboardId)
	{
		dashboadsCache.remove(dashboardId);
	}

	private UserDashboard retrieveUserDashboard(final int dashboardId)
	{
		final I_WEBUI_Dashboard webuiDashboard = queryBL
				.createQueryBuilder(I_WEBUI_Dashboard.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_WEBUI_Dashboard.COLUMN_WEBUI_Dashboard_ID, dashboardId)
				//
				.create()
				.firstOnlyNotNull(I_WEBUI_Dashboard.class);

		return createUserDashboard(webuiDashboard);
	}

	private int retrieveUserDashboardId(final UserDashboardKey key)
	{
		final int adClientId = key.getAdClientId();

		final int dashboardId = queryBL
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
				.firstId();

		return dashboardId > 0 ? dashboardId : -1;
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
				.map(this::createUserDashboardItem)
				.forEach(userDashboardBuilder::addItem);

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

	private UserDashboardItem createUserDashboardItem(final I_WEBUI_DashboardItem itemPO)
	{
		final IModelTranslationMap trlsMap = InterfaceWrapperHelper.getModelTranslationMap(itemPO);

		return UserDashboardItem.builder()
				.setId(itemPO.getWEBUI_DashboardItem_ID())
				.setCaption(trlsMap.getColumnTrl(I_WEBUI_DashboardItem.COLUMNNAME_Name, itemPO.getName()))
				.setUrl(itemPO.getURL())
				.setSeqNo(itemPO.getSeqNo())
				.setWidgetType(DashboardWidgetType.ofCode(itemPO.getWEBUI_DashboardWidgetType()))
				.setKPI(() -> kpisRepo.getKPIOrNull(itemPO.getWEBUI_KPI_ID()))
				//
				.setTimeRangeDefaults(KPITimeRangeDefaults.builder()
						.defaultTimeRangeFromString(itemPO.getES_TimeRange())
						.defaultTimeRangeEndOffsetFromString(itemPO.getES_TimeRange_End())
						.build())
				//
				.build();
	}

	private void changeUserDashboardItemAndSave(@NonNull final I_WEBUI_DashboardItem itemPO, @NonNull final UserDashboardItemChangeRequest request)
	{
		logger.trace("Changing {} from {}", itemPO, request);

		//
		// Caption -> Name
		final POTrlInfo trlInfo = InterfaceWrapperHelper.getStrictPO(itemPO).getPOInfo().getTrlInfo();
		final String adLanguage = request.getAdLanguage();
		String captionTrl = null;
		if (!Check.isEmpty(request.getCaption(), true))
		{
			final String caption = request.getCaption();
			if (Language.isBaseLanguage(adLanguage) || !trlInfo.isColumnTranslated(I_WEBUI_DashboardItem.COLUMNNAME_Name))
			{
				itemPO.setName(caption);
			}
			else
			{
				captionTrl = caption;
			}
		}

		//
		// Interval -> ES_TimeRange
		if (request.getInterval() != null)
		{
			itemPO.setES_TimeRange(request.getInterval().getEsTimeRange());
		}

		//
		// When -> ES_TimeRange_End
		if (request.getWhen() != null)
		{
			itemPO.setES_TimeRange_End(request.getWhen().getEsTimeRangeEnd());
		}

		//
		// Save
		InterfaceWrapperHelper.save(itemPO);

		//
		// Change translations (after save)
		if (captionTrl != null)
		{
			final int recordId = itemPO.getWEBUI_DashboardItem_ID();
			POTrlRepository.instance.updateTranslation(trlInfo, recordId, adLanguage, I_WEBUI_DashboardItem.COLUMNNAME_Name, captionTrl);
		}
	}

	private void changeUserDashboardItemAndSave(final UserDashboardItemChangeRequest request)
	{
		final I_WEBUI_DashboardItem itemPO = InterfaceWrapperHelper.load(request.getItemId(), I_WEBUI_DashboardItem.class);
		if (itemPO == null)
		{
			throw new AdempiereException("no item found for itemId=" + request.getItemId());
		}

		changeUserDashboardItemAndSave(itemPO, request);
	}

	/**
	 * Execute given changeActions in a transaction. If successful, the given dashboad will be invalidated.
	 */
	private void executeChangeActionsAndInvalidate(final int dashboardId, final List<Runnable> changeActions)
	{
		Services.get(ITrxManager.class).run(ITrx.TRXNAME_ThreadInherited, () -> {
			changeActions.forEach(Runnable::run);
		});

		invalidateUserDashboard(dashboardId);
	}

	private void executeChangeActionAndInvalidate(final int dashboardId, final Runnable changeAction)
	{
		executeChangeActionsAndInvalidate(dashboardId, ImmutableList.of(changeAction));
	}

	private <R> R executeChangeActionAndInvalidateAndReturn(final int dashboardId, final Callable<R> changeAction)
	{
		final Mutable<R> result = new Mutable<>();
		final Runnable changeActionRunnable = () -> {
			try
			{
				result.setValue(changeAction.call());
			}
			catch (final Exception ex)
			{
				throw AdempiereException.wrapIfNeeded(ex);
			}
		};

		executeChangeActionsAndInvalidate(dashboardId, ImmutableList.of(changeActionRunnable));
		return result.getValue();
	}

	private void changeDashboardItemsOrder(final int dashboardId, final DashboardWidgetType dashboardWidgetType, final List<Integer> requestOrderedItemIds)
	{
		// Retrieve all itemIds ordered
		final List<Integer> allItemIds = retrieveDashboardItemIdsOrdered(dashboardId, dashboardWidgetType);

		//
		// Start building the orderedIds list by adding all the IDs from the request
		final List<Integer> orderedIds = requestOrderedItemIds
				.stream()
				.filter(allItemIds::contains) // skip those IDs which are not present in our "all" ids list
				.collect(Collectors.toCollection(ArrayList::new)); // mutable list

		// At the end of orderedIds all all those IDs which where not present in provided request (those might exist only in database).
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
		updateUserDashboardItemsOrder(dashboardId, orderedIds);
	}

	private void updateUserDashboardItemsOrder(final int dashboardId, final List<Integer> allItemIdsOrdered)
	{
		final String sql = "UPDATE " + I_WEBUI_DashboardItem.Table_Name
				+ " SET " + I_WEBUI_DashboardItem.COLUMNNAME_SeqNo + "=?"
				+ " WHERE " + I_WEBUI_DashboardItem.COLUMNNAME_WEBUI_Dashboard_ID + "=?"
				+ " AND " + I_WEBUI_DashboardItem.COLUMNNAME_WEBUI_DashboardItem_ID + "=?";
		PreparedStatement pstmt = null;
		try
		{
			for (int newSeqNo = 0; newSeqNo < allItemIdsOrdered.size(); newSeqNo++)
			{
				final int itemId = allItemIdsOrdered.get(newSeqNo);

				if (pstmt == null)
				{
					pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
				}

				final int sqlNewSeqNo = newSeqNo * 10 + 10; // convert 0-based index to "10, 20, 30.." sequence number (starting from 10)
				DB.setParameters(pstmt, new Object[] { sqlNewSeqNo, dashboardId, itemId });
				pstmt.addBatch();
			}

			if (pstmt == null)
			{
				return;
			}

			pstmt.executeBatch();
		}
		catch (final SQLException ex)
		{
			throw DBException.wrapIfNeeded(ex);
		}
		finally
		{
			DB.close(pstmt);
		}
	}

	/** @return new itemId */
	public int addUserDashboardItem(final UserDashboard userDashboard, @NonNull final UserDashboardItemAddRequest request)
	{
		final int dashboardId = userDashboard.getId();
		final DashboardWidgetType dashboardWidgetType = request.getWidgetType();
		final Set<Integer> allItemIds = userDashboard.getItemIds(dashboardWidgetType);

		logger.trace("Adding to dashboard {}: type={}, request={}", dashboardId, dashboardWidgetType, request);
		logger.trace("Current ordered itemIds: {}", allItemIds);

		return executeChangeActionAndInvalidateAndReturn(dashboardId, () -> {
			//
			// Create dashboard item in database (will be added last).
			final int itemId = createUserDashboardItemAndSave(dashboardId, request);

			//
			// Calculate item's position
			final List<Integer> orderedItemIds = new ArrayList<>(allItemIds);
			if (request.getPosition() >= 0 && request.getPosition() < orderedItemIds.size())
			{
				orderedItemIds.add(request.getPosition(), itemId);
			}
			else
			{
				orderedItemIds.add(itemId);
			}
			logger.trace("New ordered itemIds: {}", allItemIds);

			//
			// Update dashboard items order
			changeDashboardItemsOrder(dashboardId, dashboardWidgetType, orderedItemIds);

			//
			return itemId;
		});
	}

	private int createUserDashboardItemAndSave(final int dashboardId, @NonNull final UserDashboardItemAddRequest request)
	{
		//
		// Get the KPI
		final int kpiId = request.getKpiId();
		if (kpiId <= 0)
		{
			throw new AdempiereException("kpiId is not set")
					.setParameter("request", request);
		}
		final I_WEBUI_KPI kpi = InterfaceWrapperHelper.loadOutOfTrx(kpiId, I_WEBUI_KPI.class);

		final DashboardWidgetType widgetType = request.getWidgetType();
		final int seqNo = retrieveLastSeqNo(dashboardId, widgetType) + 10;
		//
		final I_WEBUI_DashboardItem webuiDashboardItem = InterfaceWrapperHelper.newInstance(I_WEBUI_DashboardItem.class);
		webuiDashboardItem.setWEBUI_Dashboard_ID(dashboardId);
		webuiDashboardItem.setIsActive(true);
		webuiDashboardItem.setName(kpi.getName());
		webuiDashboardItem.setSeqNo(seqNo);
		webuiDashboardItem.setWEBUI_KPI_ID(kpiId);
		webuiDashboardItem.setWEBUI_DashboardWidgetType(widgetType.getCode());
		// will be set by change request:
		// webuiDashboardItem.setES_TimeRange(esTimeRange);
		// webuiDashboardItem.setES_TimeRange_End(esTimeRangeEnd);

		InterfaceWrapperHelper.save(webuiDashboardItem);
		logger.trace("Created {} for dashboard {}", webuiDashboardItem, dashboardId);
		// TODO: copy trl but also consider the request.getCaption() and use it only for the current language trl.

		//
		// Apply the change request
		if (request.getChangeRequest() != null)
		{
			changeUserDashboardItemAndSave(webuiDashboardItem, request.getChangeRequest());
		}

		final int itemId = webuiDashboardItem.getWEBUI_DashboardItem_ID();
		return itemId;
	}

	public void deleteUserDashboardItem(final UserDashboard dashboard, final DashboardWidgetType dashboardWidgetType, final int itemId)
	{
		dashboard.assertItemIdExists(dashboardWidgetType, itemId);

		executeChangeActionAndInvalidate(dashboard.getId(), () -> {
			final I_WEBUI_DashboardItem item = InterfaceWrapperHelper.load(itemId, I_WEBUI_DashboardItem.class);
			InterfaceWrapperHelper.delete(item);
		});
	}

	public static enum DashboardItemPatchPath
	{
		caption, interval, when, position
	}

	public UserDashboardItemChangeResult changeUserDashboardItem(final UserDashboard dashboard, final UserDashboardItemChangeRequest request)
	{
		final int dashboardId = dashboard.getId();
		final DashboardWidgetType dashboardWidgetType = request.getWidgetType();
		final int itemId = request.getItemId();

		dashboard.assertItemIdExists(dashboardWidgetType, itemId);

		//
		// Execute the change request
		return executeChangeActionAndInvalidateAndReturn(dashboardId, () -> {
			final UserDashboardItemChangeResultBuilder resultBuilder = UserDashboardItemChangeResult.builder()
					.dashboardId(dashboardId)
					.dashboardWidgetType(dashboardWidgetType)
					.itemId(itemId);

			//
			// Actually change the item content
			changeUserDashboardItemAndSave(request);

			//
			// Change item's position
			final int position = request.getPosition();
			if (position >= 0)
			{
				final List<Integer> allItemIdsOrdered = new ArrayList<>(retrieveDashboardItemIdsOrdered(dashboardId, dashboardWidgetType));

				if (position == Integer.MAX_VALUE || position > allItemIdsOrdered.size() - 1)
				{
					allItemIdsOrdered.remove((Object)itemId);
					allItemIdsOrdered.add(itemId);
				}
				else
				{
					allItemIdsOrdered.remove((Object)itemId);
					allItemIdsOrdered.add(position, itemId);
				}

				updateUserDashboardItemsOrder(dashboardId, allItemIdsOrdered);
				resultBuilder.dashboardOrderedItemIds(ImmutableList.copyOf(allItemIdsOrdered));
			}

			return resultBuilder.build();
		});
	}

	private List<Integer> retrieveDashboardItemIdsOrdered(final int dashboardId, final DashboardWidgetType dashboardWidgetType)
	{
		return retrieveWEBUI_DashboardItemsQuery(dashboardId)
				.addEqualsFilter(I_WEBUI_DashboardItem.COLUMN_WEBUI_DashboardWidgetType, dashboardWidgetType.getCode())
				//
				.orderBy()
				.addColumn(I_WEBUI_DashboardItem.COLUMN_SeqNo, Direction.Ascending, Nulls.First)
				.addColumn(I_WEBUI_DashboardItem.COLUMN_WEBUI_DashboardItem_ID)
				.endOrderBy()
				//
				.create()
				.listIds();
	}

	private final int retrieveLastSeqNo(final int dashboardId, final DashboardWidgetType dashboardWidgetType)
	{
		final Integer maxSeqNo = queryBL.createQueryBuilder(I_WEBUI_DashboardItem.class)
				.addEqualsFilter(I_WEBUI_DashboardItem.COLUMN_WEBUI_Dashboard_ID, dashboardId)
				.addEqualsFilter(I_WEBUI_DashboardItem.COLUMN_WEBUI_DashboardWidgetType, dashboardWidgetType.getCode())
				.create()
				.aggregate(I_WEBUI_DashboardItem.COLUMN_SeqNo, Aggregate.MAX, Integer.class);

		return maxSeqNo != null ? maxSeqNo : 0;
	}

	public Collection<KPI> getKPIsAvailableToAdd()
	{
		return kpisRepo.getKPIs();
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
