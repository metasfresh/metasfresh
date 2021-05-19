package de.metas.ui.web.dashboard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
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
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
public class UserDashboardRepository
{
	//
	// Services
	private static final Logger logger = LogManager.getLogger(UserDashboardRepository.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	@Autowired
	private KPIRepository kpisRepo;

	private final CCache<UserDashboardKey, Optional<UserDashboardId>> key2dashboardId = CCache.newLRUCache(I_WEBUI_Dashboard.Table_Name + "#key2DashboardId", Integer.MAX_VALUE, 0);

	private final CCache<UserDashboardId, UserDashboard> dashboadsCache = CCache.<UserDashboardId, UserDashboard> builder()
			.cacheName(I_WEBUI_Dashboard.Table_Name + "#UserDashboard")
			.tableName(I_WEBUI_Dashboard.Table_Name)
			.additionalTableNameToResetFor(I_WEBUI_DashboardItem.Table_Name)
			.build();

	public UserDashboard getUserDashboardById(final UserDashboardId dashboardId)
	{
		return dashboadsCache.getOrLoad(dashboardId, () -> retrieveUserDashboard(dashboardId));
	}

	public Optional<UserDashboard> getUserDashboard(@NonNull final UserDashboardKey userDashboardKey)
	{
		return getUserDashboardId(userDashboardKey).map(this::getUserDashboardById);
	}

	public Optional<UserDashboardId> getUserDashboardId(final @NonNull UserDashboardKey userDashboardKey)
	{
		return key2dashboardId.getOrLoad(userDashboardKey, this::retrieveUserDashboardId);
	}

	private void invalidateUserDashboard(final UserDashboardId dashboardId)
	{
		dashboadsCache.remove(dashboardId);
	}

	private UserDashboard retrieveUserDashboard(final UserDashboardId dashboardId)
	{
		final I_WEBUI_Dashboard webuiDashboard = queryBL
				.createQueryBuilder(I_WEBUI_Dashboard.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_WEBUI_Dashboard.COLUMN_WEBUI_Dashboard_ID, dashboardId)
				//
				.create()
				.firstOnlyNotNull(I_WEBUI_Dashboard.class);

		return fromRecord(webuiDashboard);
	}

	private Optional<UserDashboardId> retrieveUserDashboardId(@NonNull final UserDashboardKey key)
	{
		final ClientId adClientId = key.getAdClientId();

		final UserDashboardId dashboardId = queryBL
				.createQueryBuilder(I_WEBUI_Dashboard.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_WEBUI_Dashboard.COLUMN_AD_Client_ID, ClientId.SYSTEM, adClientId)
				//
				.orderBy()
				.addColumn(I_WEBUI_Dashboard.COLUMN_AD_Client_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_WEBUI_Dashboard.COLUMN_IsDefault, Direction.Descending, Nulls.Last)
				.addColumn(I_WEBUI_Dashboard.COLUMN_WEBUI_Dashboard_ID)
				.endOrderBy()
				//
				.create()
				.firstId(UserDashboardId::ofRepoIdOrNull);

		return Optional.ofNullable(dashboardId);
	}

	private UserDashboard fromRecord(final I_WEBUI_Dashboard webuiDashboard)
	{
		final UserDashboardId webuiDashboardId = UserDashboardId.ofRepoId(webuiDashboard.getWEBUI_Dashboard_ID());

		final UserDashboard.UserDashboardBuilder userDashboardBuilder = UserDashboard.builder()
				.id(webuiDashboardId)
				.adClientId(ClientId.ofRepoId(webuiDashboard.getAD_Client_ID()));

		retrieveWEBUI_DashboardItemsQuery(webuiDashboardId)
				//
				.create()
				.list(I_WEBUI_DashboardItem.class)
				.stream()
				.map(this::createUserDashboardItem)
				.forEach(userDashboardBuilder::item);

		return userDashboardBuilder.build();
	}

	private IQueryBuilder<I_WEBUI_DashboardItem> retrieveWEBUI_DashboardItemsQuery(@NonNull final UserDashboardId webuiDashboardId)
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

		final KPIId kpiId = KPIId.ofRepoIdOrNull(itemPO.getWEBUI_KPI_ID());
		final KPISupplier kpiSupplier = kpiId != null
				? kpisRepo.getKPISupplier(kpiId)
				: null;

		return UserDashboardItem.builder()
				.id(UserDashboardItemId.ofRepoId(itemPO.getWEBUI_DashboardItem_ID()))
				.caption(trlsMap.getColumnTrl(I_WEBUI_DashboardItem.COLUMNNAME_Name, itemPO.getName()))
				.url(itemPO.getURL())
				.seqNo(itemPO.getSeqNo())
				.widgetType(DashboardWidgetType.ofCode(itemPO.getWEBUI_DashboardWidgetType()))
				.kpiSupplier(kpiSupplier)
				//
				.timeRangeDefaults(KPITimeRangeDefaults.builder()
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
	private void executeChangeActionsAndInvalidate(final UserDashboardId dashboardId, final List<Runnable> changeActions)
	{
		Services.get(ITrxManager.class).run(ITrx.TRXNAME_ThreadInherited, () -> changeActions.forEach(Runnable::run));

		invalidateUserDashboard(dashboardId);
	}

	private void executeChangeActionAndInvalidate(final UserDashboardId dashboardId, final Runnable changeAction)
	{
		executeChangeActionsAndInvalidate(dashboardId, ImmutableList.of(changeAction));
	}

	@Nullable
	private <R> R executeChangeActionAndInvalidateAndReturn(final UserDashboardId dashboardId, final Callable<R> changeAction)
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

	private void changeDashboardItemsOrder(final UserDashboardId dashboardId, final DashboardWidgetType dashboardWidgetType, final List<UserDashboardItemId> requestOrderedItemIds)
	{
		// Retrieve all itemIds ordered
		final ImmutableSet<UserDashboardItemId> allItemIds = retrieveDashboardItemIdsOrdered(dashboardId, dashboardWidgetType);

		//
		// Start building the orderedIds list by adding all the IDs from the request
		final List<UserDashboardItemId> orderedIds = requestOrderedItemIds
				.stream()
				.filter(allItemIds::contains) // skip those IDs which are not present in our "all" ids list
				.collect(Collectors.toCollection(ArrayList::new)); // mutable list

		// At the end ofValueAndField orderedIds all all those IDs which where not present in provided request (those might exist only in database).
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

	private void updateUserDashboardItemsOrder(final UserDashboardId dashboardId, final List<UserDashboardItemId> allItemIdsOrdered)
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
				final UserDashboardItemId itemId = allItemIdsOrdered.get(newSeqNo);

				if (pstmt == null)
				{
					pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
				}

				final int sqlNewSeqNo = newSeqNo * 10 + 10; // convert 0-based index to "10, 20, 30.." sequence number (starting from 10)
				DB.setParameters(pstmt, sqlNewSeqNo, dashboardId, itemId);
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
	public UserDashboardItemId addUserDashboardItem(final UserDashboard userDashboard, @NonNull final UserDashboardItemAddRequest request)
	{
		final UserDashboardId dashboardId = userDashboard.getId();
		final DashboardWidgetType dashboardWidgetType = request.getWidgetType();
		final Set<UserDashboardItemId> allItemIds = userDashboard.getItemIds(dashboardWidgetType);

		logger.trace("Adding to dashboard {}: type={}, request={}", dashboardId, dashboardWidgetType, request);
		logger.trace("Current ordered itemIds: {}", allItemIds);

		//noinspection ConstantConditions
		return executeChangeActionAndInvalidateAndReturn(dashboardId, () -> {
			//
			// Create dashboard item in database (will be added last).
			final UserDashboardItemId itemId = createUserDashboardItemAndSave(dashboardId, request);

			//
			// Calculate item's position
			final List<UserDashboardItemId> orderedItemIds = new ArrayList<>(allItemIds);
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

	private UserDashboardItemId createUserDashboardItemAndSave(@NonNull final UserDashboardId dashboardId, @NonNull final UserDashboardItemAddRequest request)
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
		webuiDashboardItem.setWEBUI_Dashboard_ID(dashboardId.getRepoId());
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

		return UserDashboardItemId.ofRepoId(webuiDashboardItem.getWEBUI_DashboardItem_ID());
	}

	public void deleteUserDashboardItem(final UserDashboard dashboard, final DashboardWidgetType dashboardWidgetType, final UserDashboardItemId itemId)
	{
		dashboard.assertItemIdExists(dashboardWidgetType, itemId);

		executeChangeActionAndInvalidate(dashboard.getId(), () -> {
			final I_WEBUI_DashboardItem item = InterfaceWrapperHelper.load(itemId, I_WEBUI_DashboardItem.class);
			InterfaceWrapperHelper.delete(item);
		});
	}

	public enum DashboardItemPatchPath
	{
		caption, interval, when, position
	}

	public UserDashboardItemChangeResult changeUserDashboardItem(final UserDashboard dashboard, final UserDashboardItemChangeRequest request)
	{
		final UserDashboardId dashboardId = dashboard.getId();
		final DashboardWidgetType dashboardWidgetType = request.getWidgetType();
		final UserDashboardItemId itemId = request.getItemId();

		dashboard.assertItemIdExists(dashboardWidgetType, itemId);

		//
		// Execute the change request
		//noinspection ConstantConditions
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
				final List<UserDashboardItemId> allItemIdsOrdered = new ArrayList<>(retrieveDashboardItemIdsOrdered(dashboardId, dashboardWidgetType));

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

	private ImmutableSet<UserDashboardItemId> retrieveDashboardItemIdsOrdered(final UserDashboardId dashboardId, final DashboardWidgetType dashboardWidgetType)
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
				.listIds(UserDashboardItemId::ofRepoId);
	}

	private int retrieveLastSeqNo(final UserDashboardId dashboardId, final DashboardWidgetType dashboardWidgetType)
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
	@Value(staticConstructor = "of")
	public static class UserDashboardKey
	{
		@Nullable
		ClientId adClientId;
	}
}
