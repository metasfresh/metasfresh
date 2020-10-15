package de.metas.inoutcandidate.invalidation.impl;

import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.async.UpdateInvalidShipmentSchedulesWorkpackageProcessor;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleAttributeSegment;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ShipmentScheduleInvalidateRepository implements IShipmentScheduleInvalidateRepository
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleInvalidateRepository.class);

	private static final String M_SHIPMENT_SCHEDULE_RECOMPUTE = "M_ShipmentSchedule_Recompute";

	/**
	 * Invalidate by M_Product_ID
	 */
	private static final String SQL_RECOMPUTE_BY_PRODUCT = "INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID, Description) "
			+ " SELECT "
			+ " s." + COLUMNNAME_M_ShipmentSchedule_ID
			+ " , ?"
			+ " FROM " + I_M_ShipmentSchedule.Table_Name + " s "
			+ "   INNER JOIN " + I_C_OrderLine.Table_Name + " ol ON ol." + COLUMNNAME_C_OrderLine_ID + "=s." + COLUMNNAME_C_OrderLine_ID
			+ " WHERE true "
			+ "   AND s.IsActive='Y' AND s." + I_M_ShipmentSchedule.COLUMNNAME_Processed + "='N' "
			+ "   AND NOT EXISTS (select 1 from M_ShipmentSchedule_Recompute e where e.AD_PInstance_ID is NULL and e.M_ShipmentSchedule_ID=s." + COLUMNNAME_M_ShipmentSchedule_ID + ")"
			+ "   AND ol.M_Product_ID=? ";

	private static final String SQL_RECOMPUTE_ALL =               //
			"INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID, Description) "
					+ " SELECT " + COLUMNNAME_M_ShipmentSchedule_ID + ", 'invalidate all'"
					+ " FROM " + I_M_ShipmentSchedule.Table_Name
					+ " WHERE IsActive='Y' AND " + I_M_ShipmentSchedule.COLUMNNAME_AD_Client_ID + "=?"
					+ "   AND " + I_M_ShipmentSchedule.COLUMNNAME_Processed + "='N'";

	@Override
	public boolean isFlaggedForRecompute(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final String sql = " SELECT 1 "
				+ " FROM " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " sr "
				+ " WHERE sr." + COLUMNNAME_M_ShipmentSchedule_ID + "=?"
				+ " LIMIT 1";

		final int result = DB.getSQLValueEx(ITrx.TRXNAME_None, sql, shipmentScheduleId);

		final boolean flaggedForRrecompute = result == 1;
		return flaggedForRrecompute;
	}

	@Override
	public void invalidateForProduct(@NonNull final ProductId productId)
	{
		final String description = truncInvalidateDescription("" + productId);
		final int count = DB.executeUpdateEx(SQL_RECOMPUTE_BY_PRODUCT, new Object[] { description, productId }, ITrx.TRXNAME_ThreadInherited);
		logger.debug("Invalidated {} entries for productId={} ", count, productId);

		if (count > 0)
		{
			UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule();
		}
	}

	@Override
	public void invalidateAll(final Properties ctx)
	{
		final String trxName = ITrx.TRXNAME_None;
		final int clientId = Env.getAD_Client_ID(ctx);

		final int count = DB.executeUpdateEx(SQL_RECOMPUTE_ALL, new Object[] { clientId }, trxName);
		logger.debug("Invalidated {} entries for AD_Client_ID={}", count, clientId);

		if (count > 0)
		{
			UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(ctx, trxName);
		}
	}

	private static final String truncInvalidateDescription(final String description)
	{
		return StringUtils.trunc(description, 2000);
	}

	@Override
	public void invalidateForHeaderAggregationKeys(@NonNull final Set<String> headerAggregationKeys)
	{
		Check.assumeNotEmpty(headerAggregationKeys, "headerAggregationKeys is not empty");

		final String description = truncInvalidateDescription("" + headerAggregationKeys.size() + " header agg. keys: " + headerAggregationKeys);

		final StringBuilder headerAggregationKeysWhereClause = new StringBuilder();
		final List<Object> headerAggregationKeysParams = new ArrayList<>();
		for (final String headerAggregationKey : headerAggregationKeys)
		{
			// Skip empty header aggregation keys
			if (Check.isEmpty(headerAggregationKey, true))
			{
				continue;
			}

			if (headerAggregationKeysWhereClause.length() > 0)
			{
				headerAggregationKeysWhereClause.append(" OR ");
			}
			headerAggregationKeysWhereClause.append(I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey).append("=?");
			headerAggregationKeysParams.add(headerAggregationKey);
		}

		if (headerAggregationKeysWhereClause.length() <= 0)
		{
			return;
		}

		final List<Object> sqlParams = new ArrayList<>();
		sqlParams.addAll(headerAggregationKeysParams);
		final String sql = "INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID, Description) "
				+ " SELECT " + COLUMNNAME_M_ShipmentSchedule_ID + ", ?"
				+ " FROM " + I_M_ShipmentSchedule.Table_Name
				+ " WHERE "
				// Only those which have our header aggregation keys
				+ "   (" + headerAggregationKeysWhereClause + ")"
				// Only those which are not processed
				+ "   AND " + I_M_ShipmentSchedule.COLUMNNAME_Processed + "=? "
				// Only those which were not already added
				+ "   AND NOT EXISTS (select 1 from " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " e where e.AD_PInstance_ID is NULL and e.M_ShipmentSchedule_ID=" + I_M_ShipmentSchedule.Table_Name + "."
				+ I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ")";
		sqlParams.add(description);
		sqlParams.add(false); // Processed=false

		final int count = DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Invalidated {} shipment schedules for headerAggregationKeys={}", count, headerAggregationKeys);
		//
		if (count > 0)
		{
			UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule();
		}
	}

	@Override
	public void invalidateShipmentSchedules(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return;
		}

		final String description = truncInvalidateDescription("" + shipmentScheduleIds.size() + " shipment schedules: " + shipmentScheduleIds);

		final List<Object> sqlParams = new ArrayList<>();
		sqlParams.add(description);
		final String sqlInWhereClause = DB.buildSqlList(shipmentScheduleIds, sqlParams); // creates the string and fills the sqlParams list

		final String sql = "INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID, Description) "
				+ " SELECT " + I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ", ?"
				+ " FROM " + I_M_ShipmentSchedule.Table_Name
				+ " WHERE "
				// Only our shipment schedule Ids
				+ I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + " IN " + sqlInWhereClause
				// Only those which were not already added (technically not necessary, but shall reduce unnecessary bloat)
				+ "   AND NOT EXISTS (select 1 from " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " e where e.AD_PInstance_ID is NULL and e.M_ShipmentSchedule_ID=" + I_M_ShipmentSchedule.Table_Name + "."
				+ I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ")";

		final int count = DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Invalidated {} shipment schedules for M_ShipmentSchedule_IDs={}", count, shipmentScheduleIds);

		if (count > 0)
		{
			UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule();
		}
	}

	@Override
	public void invalidateSchedulesForSelection(@NonNull final PInstanceId pinstanceId)
	{
		final String description = truncInvalidateDescription("from T_Selection: " + pinstanceId);

		final String sql = "INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID, Description) "
				+ "\n SELECT " + I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ", ?"
				+ "\n FROM " + I_M_ShipmentSchedule.Table_Name
				+ "\n WHERE " + I_M_ShipmentSchedule.COLUMNNAME_Processed + "='N'"
				+ "\n AND EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID = ? AND s.T_Selection_ID = M_ShipmentSchedule_ID)";

		final int count = DB.executeUpdateEx(sql, new Object[] { description, pinstanceId }, ITrx.TRXNAME_ThreadInherited);
		logger.debug("Invalidated {} M_ShipmentSchedules for AD_PInstance_ID={}", count, pinstanceId);
		//
		if (count > 0)
		{
			UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule();
		}
	}

	@Override
	public void invalidateStorageSegments(
			@Nullable final Collection<IShipmentScheduleSegment> storageSegments,
			@Nullable final PInstanceId addToSelectionId)
	{
		if (storageSegments == null || storageSegments.isEmpty())
		{
			return;
		}

		final String ssAlias = I_M_ShipmentSchedule.Table_Name + ".";
		final StringBuilder sqlWhereClause = new StringBuilder();
		final List<Object> sqlParams = new ArrayList<>();

		// SELECT part: Description and AD_PInstance_ID
		final String description = truncInvalidateDescription("" + storageSegments.size() + " storage segments: " + storageSegments);
		sqlParams.add(description);
		sqlParams.add(addToSelectionId);

		// Not Processed
		sqlWhereClause.append(ssAlias + I_M_ShipmentSchedule.COLUMNNAME_Processed).append("=?");
		sqlParams.add(false);

		//
		// Filter shipment schedules by segments
		final StringBuilder sqlWhereClause_AllSegments = new StringBuilder();
		for (final IShipmentScheduleSegment storageSegment : storageSegments)
		{
			final String sqlWhereClause_Segment = buildShipmentScheduleWhereClause(ssAlias, storageSegment, sqlParams);
			if (sqlWhereClause_Segment == null)
			{
				continue;
			}

			if (sqlWhereClause_AllSegments.length() > 0)
			{
				sqlWhereClause_AllSegments.append("\n OR ");
			}
			sqlWhereClause_AllSegments.append("(").append(sqlWhereClause_Segment).append(")");
		}
		// If there are no segments filters there is no point to proceed
		if (sqlWhereClause_AllSegments.length() <= 0)
		{
			return;
		}
		// Add to main sql where clause
		sqlWhereClause.append("\n AND (\n").append(sqlWhereClause_AllSegments).append("\n)");

		// Not already exists
		sqlWhereClause.append("\n AND ");
		sqlWhereClause.append(" NOT EXISTS (select 1 from M_ShipmentSchedule_Recompute e "
				+ " where "
				+ " e.AD_PInstance_ID is NULL"
				+ " and e.M_ShipmentSchedule_ID=" + ssAlias + "" + COLUMNNAME_M_ShipmentSchedule_ID + ")");

		//
		// Build INSERT SQL
		final String sql = "INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID, Description, AD_PInstance_ID) "
				+ "\n SELECT " + I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ", ?, ?"
				+ " FROM " + I_M_ShipmentSchedule.Table_Name
				+ " WHERE "
				+ "\n" + sqlWhereClause;

		//
		// Execute
		final String trxName = ITrx.TRXNAME_None;
		final int count = DB.executeUpdateEx(sql, sqlParams.toArray(), trxName);
		logger.debug("Invalidated {} shipment schedules for segments={}", count, storageSegments);

		//
		if (count > 0)
		{
			UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(Env.getCtx(), trxName);
		}
	}

	/**
	 * Build {@link I_M_ShipmentSchedule} where clause based on given segment.
	 *
	 * @param ssAlias {@link I_M_ShipmentSchedule#Table_Name}'s alias with trailing dot
	 * @param segment
	 * @param sqlParams output SQL parameters
	 * @return where clause or <code>null</code>
	 */
	private String buildShipmentScheduleWhereClause(
			final String ssAlias,
			final IShipmentScheduleSegment segment,
			final List<Object> sqlParams)
	{
		if (segment.isInvalid())
		{
			return null;
		}

		// final boolean debug = Services.get(IDeveloperModeBL.class).isEnabled();
		final StringBuilder whereClause = new StringBuilder();

		//
		// Products
		if (!segment.isAnyProduct())
		{
			final Set<Integer> productIds = segment.getProductIds();

			final String productColumnName = ssAlias + I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID;
			whereClause.append("\n\t AND ");
			whereClause.append("(").append(DB.buildSqlList(productColumnName, productIds, sqlParams)).append(")");
		}

		//
		// BPartners
		// NOTE: If we were asked to reset for BPartner=none (i.e. value 0, -1 or null) then we shall reset for all of them,
		// because the QOH from this segment could be used by ALL
		if (!segment.isAnyBPartner())
		{
			final Set<Integer> bpartnerIds = segment.getBpartnerIds();

			final String bpartnerColumnName = "COALESCE(" + ssAlias + I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID + ", " + ssAlias + I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_ID + ")";
			whereClause.append("\n\t AND ");
			whereClause.append("(").append(DB.buildSqlList(bpartnerColumnName, bpartnerIds, sqlParams)).append(")");
		}

		//
		// Bill BPartners
		if (!segment.isAnyBillBPartner())
		{
			final Set<Integer> billBPartnerIds = segment.getBillBPartnerIds();

			final String billBPartnerColumnName = ssAlias + I_M_ShipmentSchedule.COLUMNNAME_Bill_BPartner_ID;
			whereClause.append("\n\t AND ");
			whereClause.append("(").append(DB.buildSqlList(billBPartnerColumnName, billBPartnerIds, sqlParams)).append(")");
		}

		//
		// Locators
		// NOTE: same as for bPartners if no particular locator is specified, it means "all of them"
		if (!segment.isAnyLocator())
		{
			final Set<Integer> locatorIds = segment.getLocatorIds();

			final String warehouseColumnName = "COALESCE(" + ssAlias + I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID + ", " + ssAlias + I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID + ")";
			whereClause.append("\n\t AND ");
			whereClause.append(" EXISTS (select 1 from " + I_M_Locator.Table_Name + " loc"
					+ " WHERE"
					+ "\n\t\t loc." + I_M_Locator.COLUMNNAME_M_Warehouse_ID + "=" + warehouseColumnName
					+ " AND " + DB.buildSqlList("loc." + I_M_Locator.COLUMNNAME_M_Locator_ID, locatorIds, sqlParams)
					+ ")");
		}

		//
		// Attributes (if any)
		final Set<ShipmentScheduleAttributeSegment> attributeSegments = segment.getAttributes();
		final String attributeSegmentsWhereClause = buildAttributeInstanceWhereClause(attributeSegments, sqlParams);
		if (!Check.isEmpty(attributeSegmentsWhereClause, true))
		{
			whereClause.append("\n\t AND (");
			whereClause.append(ssAlias + I_M_ShipmentSchedule.COLUMNNAME_M_AttributeSetInstance_ID + " IS NULL");
			whereClause.append(" OR EXISTS (SELECT 1 FROM " + I_M_AttributeInstance.Table_Name + " ai "
					+ " WHERE ai." + I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID + "=" + ssAlias + I_M_ShipmentSchedule.COLUMNNAME_M_AttributeSetInstance_ID
					+ " AND (" + attributeSegmentsWhereClause + ")"
					+ ")");
			whereClause.append(")");
		}

		if (whereClause.length() <= 0)
		{
			return null;
		}

		// Add "true" to take care of the first "AND"
		whereClause.insert(0, "true");

		return whereClause.toString();
	}

	/**
	 * Build SQL where clause for given storage attribute segments.
	 *
	 * We assume following table aliases
	 * <ul>
	 * <li>ai - alias for {@link I_M_AttributeInstance#Table_Name}
	 * </ul>
	 *
	 * @param attributeSegments
	 * @param sqlParams
	 * @return where clause or <code>null</code>
	 */
	private String buildAttributeInstanceWhereClause(
			@Nullable final Set<ShipmentScheduleAttributeSegment> attributeSegments,
			@NonNull final List<Object> sqlParams)
	{
		if (Check.isEmpty(attributeSegments))
		{
			return null;
		}

		final Set<ShipmentScheduleAttributeSegment> attributeSegmentsEff = explodeToAttributeOnlySegments(attributeSegments);

		final StringBuilder attributeSegmentsWhereClause = new StringBuilder();
		for (final ShipmentScheduleAttributeSegment attributeSegment : attributeSegmentsEff)
		{
			final String attributeSegmentWhereClause = buildSingleAttributeInstanceWhereClause(attributeSegment.getAttributeId(), sqlParams);
			if (Check.isEmpty(attributeSegmentWhereClause, true))
			{
				continue;
			}

			if (attributeSegmentsWhereClause.length() > 0)
			{
				attributeSegmentsWhereClause.append(" OR ");
			}
			attributeSegmentsWhereClause.append("(").append(attributeSegmentWhereClause).append(")");
		}

		if (attributeSegmentsWhereClause.length() <= 0)
		{
			return null;
		}

		return attributeSegmentsWhereClause.toString();
	}

	/** convert the given {@code attributeSegments} to a set of segments that are attributeId-only and where the attributeIds are all storage-relevant. */
	private final Set<ShipmentScheduleAttributeSegment> explodeToAttributeOnlySegments(
			@NonNull final Set<ShipmentScheduleAttributeSegment> attributeSegments)
	{
		final ImmutableSet.Builder<ShipmentScheduleAttributeSegment> result = ImmutableSet.builder();
		for (final ShipmentScheduleAttributeSegment segment : attributeSegments)
		{
			final IAttributesBL attributesBL = Services.get(IAttributesBL.class);
			final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

			if (segment.getAttributeId() != null)
			{
				if (attributesBL.isStorageRelevant(segment.getAttributeId()))
				{
					result.add(segment);
				}
				continue;
			}
			if (!segment.getAttributeSetInstanceId().isRegular())
			{
				continue;
			}
			attributeDAO
					.getAttributeIdsByAttributeSetInstanceId(segment.getAttributeSetInstanceId())
					.stream()
					.filter(attributesBL::isStorageRelevant)
					.map(ShipmentScheduleAttributeSegment::ofAttributeId)
					.forEach(result::add);
		}
		return result.build();
	}

	/**
	 * Build SQL where clause for given storage attribute segment.
	 *
	 * We assume following table aliases
	 * <ul>
	 * <li>ai - alias for {@link I_M_AttributeInstance#Table_Name}
	 * </ul>
	 *
	 * @param attributeSegment
	 * @param sqlParams
	 * @return where clause or <code>null</code>
	 */
	private String buildSingleAttributeInstanceWhereClause(@Nullable final AttributeId attributeId, @NonNull final List<Object> sqlParams)
	{
		if (attributeId == null)
		{
			return null;
		}
		final StringBuilder whereClause = new StringBuilder();

		// Filter by M_Attribute_ID
		whereClause.append("ai.").append(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID).append("=?");
		sqlParams.add(attributeId);

		return whereClause.toString();
	}

	@Override
	public void markAllToRecomputeOutOfTrx(@NonNull final PInstanceId pinstanceId)
	{
		// task 08727: Tag the recompute records out-of-trx.
		// This is crucial because the invalidation-SQL checks if there exist un-tagged recompute records to avoid creating too many unneeded records.
		// So if the tagging was in-trx, then the invalidation-SQL would still see them as un-tagged and therefore the invalidation would fail.
		final String sqlUpdate = " UPDATE " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " sr " +
				"SET AD_Pinstance_ID=" + pinstanceId.getRepoId() +
				"FROM (" +
				"	SELECT s.M_ShipmentSchedule_ID " +
				"	FROM M_ShipmentSchedule s " +
				// task 08959: also retrieve locked records. The async processor is expected to wait until they are updated.
				// " LEFT JOIN T_Lock l ON l.Record_ID=s.M_ShipmentSchedule_ID AND l.AD_Table_ID=get_table_id('M_ShipmentSchedule') " +
				// " WHERE l.Record_ID Is NULL " +
				") data " +
				" WHERE data.M_ShipmentSchedule_ID=sr.M_ShipmentSchedule_ID "
				+ " AND AD_PInstance_ID IS NULL" // only those which were not already tagged
		;
		final int countTagged = DB.executeUpdateEx(sqlUpdate, ITrx.TRXNAME_None);
		logger.debug("Marked {} entries for {}", countTagged, pinstanceId);
	}

	@Override
	public void deleteRecomputeMarkersOutOfTrx(@NonNull final PInstanceId pinstanceId)
	{
		final String sql = "DELETE FROM " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " WHERE AD_Pinstance_ID=? RETURNING M_ShipmentSchedule_ID";

		final Object[] param = { pinstanceId };
		final Mutable<HashSet<Integer>> shipmentScheduleIds = new Mutable<>(new HashSet<>());
		DB.executeUpdateEx(
				sql,
				param,
				ITrx.TRXNAME_None,
				-1 /* timeout */,
				rs -> shipmentScheduleIds.getValue().add(rs.getInt("M_ShipmentSchedule_ID")) /* updateReturnProcessor */
		);
		logger.debug("Deleted {} {} entries for AD_Pinstance_ID={}", shipmentScheduleIds.getValue().size(), M_SHIPMENT_SCHEDULE_RECOMPUTE, pinstanceId);
		if (shipmentScheduleIds.getValue().isEmpty())
		{
			return;
		}
		// invalidate the shipment schedule cache after current transaction commit
		Services.get(ITrxManager.class)
				.getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited)
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> invalidateShipmentScheduleCache(shipmentScheduleIds.getValue()));
	}

	private void invalidateShipmentScheduleCache(@NonNull final Set<Integer> shipmentScheduleIds)
	{
		final IModelCacheInvalidationService modelCacheInvalidationService = Services.get(IModelCacheInvalidationService.class);

		final CacheInvalidateMultiRequest multiRequest = CacheInvalidateMultiRequest.fromTableNameAndRecordIds(I_M_ShipmentSchedule.Table_Name, shipmentScheduleIds);
		modelCacheInvalidationService.invalidate(multiRequest, ModelCacheInvalidationTiming.CHANGE);
	}

	@Override
	public void releaseRecomputeMarkerOutOfTrx(final PInstanceId pinstanceId)
	{
		final Object[] sqlParams = new Object[] { pinstanceId };
		final String sql = "UPDATE " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " SET AD_PInstance_ID=NULL WHERE AD_PInstance_ID=?";

		final int result = DB.executeUpdateEx(sql, sqlParams, ITrx.TRXNAME_None);
		logger.debug("Updated {} {} entries for AD_Pinstance_ID={} and released the marker.", result, M_SHIPMENT_SCHEDULE_RECOMPUTE, pinstanceId);
	}

	@Override
	public IQueryFilter<I_M_ShipmentSchedule> createInvalidShipmentSchedulesQueryFilter(@NonNull final PInstanceId pinstanceId)
	{
		final String sql = "EXISTS ( "
				+ " select 1 from " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " sr "
				+ " where sr." + COLUMNNAME_M_ShipmentSchedule_ID + "=M_ShipmentSchedule." + COLUMNNAME_M_ShipmentSchedule_ID + " AND sr.AD_PInstance_ID=? "
				+ " )";

		final List<Object> sqlParams = ImmutableList.of(pinstanceId);
		return TypedSqlQueryFilter.of(sql, sqlParams);
	}
	
	@Override
	public final void invalidateShipmentSchedulesFor(@NonNull final IQuery<I_M_ShipmentSchedule> shipmentScheduleQuery)
	{
		final int count = shipmentScheduleQuery.insertDirectlyInto(I_M_ShipmentSchedule_Recompute.class)
				.mapColumn(I_M_ShipmentSchedule_Recompute.COLUMNNAME_M_ShipmentSchedule_ID, 
						I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID)
				
				.execute()
				.getRowsInserted();

		logger.debug("Invalidated {} shipment schedules for {}", new Object[] { count, shipmentScheduleQuery });

		
		if (count > 0)
		{
			invalidateCacheForAllShipmentSchedules();
			UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule();
		}
	}
	
	private void invalidateCacheForAllShipmentSchedules()
	{
		final IModelCacheInvalidationService modelCacheInvalidationService = Services.get(IModelCacheInvalidationService.class);

		final CacheInvalidateMultiRequest multiRequest = CacheInvalidateMultiRequest.allRecordsForTable(I_M_ShipmentSchedule.Table_Name);
		modelCacheInvalidationService.invalidate(multiRequest,  ModelCacheInvalidationTiming.CHANGE);
	}
	

}
