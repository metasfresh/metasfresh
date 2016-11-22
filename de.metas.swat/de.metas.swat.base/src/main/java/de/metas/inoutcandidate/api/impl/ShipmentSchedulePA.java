package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.db.IDBService;
import org.adempiere.db.IDatabaseBL;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.MOrderLine;
import org.compiere.model.Query;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.async.UpdateInvalidShipmentSchedulesWorkpackageProcessor;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.MMShipmentSchedule;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;
import de.metas.storage.IStorageAttributeSegment;
import de.metas.storage.IStorageSegment;

public class ShipmentSchedulePA implements IShipmentSchedulePA
{

	public static final String M_SHIPMENT_SCHEDULE_SHIPMENT_RUN = "M_ShipmentSchedule_ShipmentRun";

	private static final String M_SHIPMENT_SCHEDULE_RECOMPUTE = "M_ShipmentSchedule_Recompute";

	private static final String SELECT_OL_SCHED = " SELECT ol.* " //
			+ " FROM C_OrderLine ol " //
			+ "   LEFT JOIN M_ShipmentSchedule s ON s.C_OrderLine_ID=ol.C_OrderLine_ID";

	private static final String SELECT_SCHED_OL = " SELECT s.* " //
			+ " FROM M_ShipmentSchedule s " //
			+ "   LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID=s.C_OrderLine_ID " //
			+ "   LEFT JOIN C_Order o ON o.C_Order_ID=ol.C_Order_ID ";

	/**
	 * Order by clause used to fetch {@link I_M_ShipmentSchedule}s.
	 *
	 * NOTE: this ordering is VERY important because that's the order in which QtyOnHand will be alocated too.
	 */
	private static final String ORDER_CLAUSE = "\n ORDER BY " //
			//
			// Priority
			+ "\n   COALESCE(s." + I_M_ShipmentSchedule.COLUMNNAME_PriorityRule_Override + ", s." + I_M_ShipmentSchedule.COLUMNNAME_PriorityRule + ")," //
	//
	// QtyToDeliver_Override:
	// NOTE: (Mark) If we want to force deliverying something, that shall get higher priority,
	// so that's why QtyToDeliver_Override is much more important than PreparationDate, DeliveryDate etc
			+ "\n   COALESCE(s." + I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override + ", 0) DESC,"
			//
			// Preparation Date
			+ "\n   s." + I_M_ShipmentSchedule.COLUMNNAME_PreparationDate + ","
			//
			// Delivery Date
			// NOTE: stuff that shall be deivered first shall have a higher prio
			+ "\n   COALESCE(s." + I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate_Override + ", s." + I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate + ")," // stuff that shall be deivered first shall have
	// a higher prio
	//
	// Date Ordered
			+ "\n   s." + I_M_ShipmentSchedule.COLUMNNAME_DateOrdered + ", "
			//
			// Order Line
			+ "\n   s." + I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID;

	private static final String WHERE_INCOMPLETE =               //
	"\n   AND ("
			// if the param '?' is set to 0, only those entries are loaded that
			// don't have an inOutLine yet.
			+ "\n      ?=1" //
			+ "\n      OR ("//
			+ "\n         NOT EXISTS (" //
			+ "\n            SELECT * FROM M_InOutLine iol INNER JOIN M_InOut io ON (iol.M_InOut_ID=io.M_InOut_ID) "//
			+ "\n            WHERE iol.C_OrderLine_ID=ol.C_OrderLine_ID AND io.DocStatus IN ('IP','WC')"//
			+ "\n         )"//
			+ "\n      )"//
			+ "\n   )"//
			;

	/**
	 * Selects order lines that have a shipment schedule and have a given M_Product_ID
	 */
	private static final String SQL_SELECT_OLS_FOR_PRODUCT = " SELECT ol.* " //
			+ " FROM M_ShipmentSchedule s " //
			+ "   JOIN C_OrderLine ol ON ol.C_OrderLine_ID=s.C_OrderLine_ID " //
			+ " WHERE ol.M_Product_ID=? AND ol.AD_Client_ID=? "
			+ WHERE_INCOMPLETE;

	private final static Logger logger = LogManager.getLogger(ShipmentSchedulePA.class);

	private static final String SQL_ALL =               //
	" SELECT * FROM " + I_M_ShipmentSchedule.Table_Name
			+ " WHERE AD_Client_ID=?" //
			+ ORDER_CLAUSE;

	private static final String SQL_FOR_ORDER =               //
	" SELECT s.* FROM "
			+ I_M_ShipmentSchedule.Table_Name //
			+ " s LEFT JOIN C_OrderLine ol ON s.C_OrderLine_ID=ol.C_OrderLine_ID "
			+ " WHERE ol.C_Order_ID=? AND s.AD_Client_ID=?";

	private static final String SQL_SELECT_SCHEDS_FOR_PRODUCT =               //
	" SELECT s.* " //
			+ " FROM M_ShipmentSchedule s" //
			+ "   LEFT JOIN C_OrderLine ol ON s.C_OrderLine_ID=ol.C_OrderLine_ID " //
			+ " WHERE ol.M_Product_ID=? AND s.AD_Client_ID=? "
			+ WHERE_INCOMPLETE;

	private static final String SQL_SCHED =               //
	SELECT_SCHED_OL //
			+ "\n WHERE s.AD_Client_ID=? " //
			+ WHERE_INCOMPLETE //
			+ ORDER_CLAUSE;

	private static final String SQL_OL_SCHED =               //
	SELECT_OL_SCHED //
			+ "\n WHERE s.AD_Client_ID=?" //
			+ WHERE_INCOMPLETE;

	private static final String SQL_SCHED_INVALID_3P =               //
	SELECT_SCHED_OL
			+ "\n WHERE s.AD_Client_ID=? "
			+ "\n    AND EXISTS ( "
			+ "            select 1 from " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " sr "
			+ "            where sr." + COLUMNNAME_M_ShipmentSchedule_ID + "=s." + COLUMNNAME_M_ShipmentSchedule_ID + " AND sr.AD_PInstance_ID=? "
			+ "      )"
			+ WHERE_INCOMPLETE
			+ ORDER_CLAUSE;

	private static final String SQL_OL_SCHED_INVALID_3P =               //
	SELECT_OL_SCHED //
			+ "\n WHERE s.AD_Client_ID=?"//
			+ "\n    AND EXISTS ( "
			+ "            select 1 from " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " sr "
			+ "            where sr." + COLUMNNAME_M_ShipmentSchedule_ID + "=s." + COLUMNNAME_M_ShipmentSchedule_ID + " AND sr.AD_PInstance_ID=? "
			+ "      )"
			+ WHERE_INCOMPLETE;

	/**
	 * Similar to {@link #SQL_SCHED_INVALID_3P}, but does not retrieve scheds whose recompute records were were previously tagged with a certain AD_PInstance_ID, but instead retrieves scheds that
	 * <b>have any</b> recompute record <b>and</b> have a shipment-run lock with a certain <code>AD_PInstance_ID</code>.
	 */
	private static final String SQL_SCHED_INVALID_LOCKED_ONLY_3P =               //
	SELECT_SCHED_OL
			+ "\n WHERE s.AD_Client_ID=? "
			+ "\n    AND EXISTS ( "
			+ "            select 1 "
			+ "            from " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " sr "
			+ "                    inner join " + M_SHIPMENT_SCHEDULE_SHIPMENT_RUN + " ssr on sr." + COLUMNNAME_M_ShipmentSchedule_ID + " = ssr. " + COLUMNNAME_M_ShipmentSchedule_ID
			+ "            where sr." + COLUMNNAME_M_ShipmentSchedule_ID + "=s." + COLUMNNAME_M_ShipmentSchedule_ID + " AND ssr.AD_PInstance_ID=? "
			+ "      )"
			+ WHERE_INCOMPLETE
			+ ORDER_CLAUSE;

	/**
	 * See {@link #SQL_SCHED_INVALID_LOCKED_ONLY_3P}
	 */
	private static final String SQL_OL_SCHED_INVALID_LOCKED_ONLY_3P =               //
	SELECT_OL_SCHED //
			+ "\n WHERE s.AD_Client_ID=? "
			+ "\n    AND EXISTS ( "
			+ "            select 1 "
			+ "            from " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " sr "
			+ "                    inner join " + M_SHIPMENT_SCHEDULE_SHIPMENT_RUN + " ssr on sr." + COLUMNNAME_M_ShipmentSchedule_ID + " = ssr. " + COLUMNNAME_M_ShipmentSchedule_ID
			+ "            where sr." + COLUMNNAME_M_ShipmentSchedule_ID + "=s." + COLUMNNAME_M_ShipmentSchedule_ID + " AND ssr.AD_PInstance_ID=? "
			+ "      )"
			+ WHERE_INCOMPLETE;

	private static final String SQL_SCHED_BPARTNER =               //
	SELECT_SCHED_OL //
			+ " WHERE s.AD_Client_ID=? AND ol.C_Bpartner_ID=? " //
			+ WHERE_INCOMPLETE //
			+ ORDER_CLAUSE;

	private static final String SQL_OL_SCHED_BPARTNER =               //
	SELECT_OL_SCHED //
			+ " WHERE s.AD_Client_ID=? AND ol.C_Bpartner_ID=?" //
			+ WHERE_INCOMPLETE;

	private static final String SQL_BPARTNER =               //
	SELECT_SCHED_OL //
			+ " WHERE s.AD_Client_ID=? AND ol.C_Bpartner_ID=? "
			+ WHERE_INCOMPLETE;

	/**
	 * Marks shipment schedule records with a given product-id for update by {@link IShipmentScheduleUpdater#updateShipmentSchedule(int, int, int, String)}. This is done by creating
	 * M_ShipmentSchedule_Recompute-records which point to the schedule records in question.
	 *
	 * Note: It's not a problem if multiple clients execute this INSERT concurrently.
	 */
	private static final String SQL_RECOMPUTE_1P = "INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID) "
			+ " SELECT s." + COLUMNNAME_M_ShipmentSchedule_ID
			+ " FROM " + I_M_ShipmentSchedule.Table_Name + " s "
			+ "   INNER JOIN " + I_C_OrderLine.Table_Name + " ol ON ol." + COLUMNNAME_C_OrderLine_ID + "=s." + COLUMNNAME_C_OrderLine_ID
			+ " WHERE true "
			+ "   AND s." + I_M_ShipmentSchedule.COLUMNNAME_Processed + "='N' "
			+ "   AND NOT EXISTS (select 1 from M_ShipmentSchedule_Recompute e where e.AD_PInstance_ID is NULL and e.M_ShipmentSchedule_ID=s." + COLUMNNAME_M_ShipmentSchedule_ID + ")"
			+ "   AND ol.M_Product_ID=? ";

	/**
	 * Marks shipment schedule records starting at a given date for update by {@link IShipmentScheduleUpdater#updateShipmentSchedule(int, int, int, String)}. This is done by creating
	 * M_ShipmentSchedule_Recompute-records which point to the schedule records in question.
	 *
	 * Note: It's not a problem if multiple clients execute this INSERT concurrently.
	 */
	private static final String SQL_RECOMPUTE_DELIVERYDATE_1P =               //
	"INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID) "
			+ " SELECT s." + COLUMNNAME_M_ShipmentSchedule_ID
			+ " FROM " + I_M_ShipmentSchedule.Table_Name + " s "
			+ " WHERE true "
			+ "   AND s." + I_M_ShipmentSchedule.COLUMNNAME_Processed + "='N' "
			+ "   AND NOT EXISTS (select 1 from M_ShipmentSchedule_Recompute e where e.AD_PInstance_ID is NULL and e.M_ShipmentSchedule_ID=s." + COLUMNNAME_M_ShipmentSchedule_ID + ")"
			+ "   AND (s.DELIVERYDATE>=? OR s.DELIVERYDATE IS NULL)";

	private static final String SQL_RECOMPUTE_ALL =               //
	"INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID) "
			+ " SELECT " + COLUMNNAME_M_ShipmentSchedule_ID
			+ " FROM " + I_M_ShipmentSchedule.Table_Name
			+ " WHERE " + I_M_ShipmentSchedule.COLUMNNAME_AD_Client_ID + "=?"
			+ "   AND " + I_M_ShipmentSchedule.COLUMNNAME_Processed + "='N'";

	private static final String SQL_SET_DISPLAYED =               //
	"UPDATE M_ShipmentSchedule s " //
			+ " SET " + I_M_ShipmentSchedule.COLUMNNAME_IsDisplayed + "=?" //
			+ " FROM C_OrderLine ol " //
			+ " WHERE " //
			+ "   s.C_OrderLine_ID=ol.C_OrderLine_ID " //
			+ "   AND ol.M_Product_ID=? ";

	@Override
	public Collection<I_M_ShipmentSchedule> retrieveForOrder(final I_C_Order order, final String trxName)
	{
		if (order == null)
		{
			throw new NullPointerException("order");
		}
		final CPreparedStatement pstmt = DB.prepareStatement(SQL_FOR_ORDER, trxName);
		return collectResult(pstmt, trxName, order.getC_Order_ID(), Env.getAD_Client_ID(Env.getCtx()));
	}

	@Override
	public Collection<I_M_ShipmentSchedule> retrieveForProduct(final int productId, final String trxName)
	{
		final CPreparedStatement pstmt = DB.prepareStatement(SQL_SELECT_SCHEDS_FOR_PRODUCT, trxName);

		return collectResult(pstmt, trxName, productId, Env.getAD_Client_ID(Env.getCtx()));
	}

	private List<I_M_ShipmentSchedule> collectResult(
			final PreparedStatement pstmt,
			final String trxName,
			final int... params)
	{
		ResultSet rs = null;
		final List<I_M_ShipmentSchedule> result = new ArrayList<I_M_ShipmentSchedule>();
		try
		{
			for (int i = 0; i < params.length; i++)
			{
				pstmt.setInt(i + 1, params[i]);
			}
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				final MMShipmentSchedule shipmentSchedule = new MMShipmentSchedule(Env.getCtx(), rs, trxName);
				result.add(shipmentSchedule);
			}
			return result;
		}
		catch (SQLException e)
		{
			final StringBuilder msg = new StringBuilder("Error while retrieving entries for parameters ");

			for (int i = 0; i < params.length; i++)
			{
				if (i > 0)
				{
					msg.append(", ");
				}
				msg.append(params[i]);
			}
			MetasfreshLastError.saveError(logger, msg.toString(), e);

			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	/**
	 *
	 * @param orderLineId
	 * @param trxName may be <code>null</code>.
	 *
	 * @return <code>null</code> if no {@link MMShipmentSchedule} exists for the given orderLineId.
	 * @throws IllegalArgumentException if <code>orderLineId</code> is below 1
	 */
	@Override
	public I_M_ShipmentSchedule retrieveForOrderLine(
			final Properties ctx,
			final int orderLineId,
			final String trxName)
	{
		Check.assume(orderLineId > 0, "Param 'orderLineId'=" + orderLineId + " is >0");

		return new Query(ctx, I_M_ShipmentSchedule.Table_Name, I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID + "=?", trxName)
				.setParameters(orderLineId)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(I_M_ShipmentSchedule.class);
	}

	@Override
	public I_M_ShipmentSchedule retrieveForOrderLine(final org.compiere.model.I_C_OrderLine orderLine)
	{
		Check.assumeNotNull(orderLine, "orderLine not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderLine);
		final int orderLineId = orderLine.getC_OrderLine_ID();
		return retrieveForOrderLine(ctx, orderLineId, trxName);
	}

	@Override
	public List<I_M_ShipmentSchedule> retrieveUnprocessedForRecord(
			final Properties ctx, final int adTableId, final int recordId, final String trxName)
	{
		final String wc = I_M_ShipmentSchedule.COLUMNNAME_AD_Table_ID + "=? AND " +
				I_M_ShipmentSchedule.COLUMNNAME_Record_ID + "=?" +
				I_M_ShipmentSchedule.COLUMNNAME_Processed + "='N'";

		return new Query(ctx, I_M_ShipmentSchedule.Table_Name, wc, trxName)
				.setParameters(adTableId, recordId)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID)
				.list(I_M_ShipmentSchedule.class);
	}

	@Override
	public List<I_M_ShipmentSchedule> retrieveAll(final String trxName)
	{
		final PreparedStatement pstmt = DB.prepareStatement(SQL_ALL, trxName);
		return collectResult(pstmt, trxName, Env.getAD_Client_ID(Env.getCtx()));
	}

	@Override
	public List<OlAndSched> retrieveOlAndSchedsForBPartner(
			final int partnerId,
			final boolean includeUncompleted,
			final String trxName)
	{
		final Object[] params = {
				Env.getAD_Client_ID(Env.getCtx()),
				partnerId,
				includeUncompleted ? 1 : 0 };

		final IDatabaseBL db = Services.get(IDatabaseBL.class);
		final List<X_M_ShipmentSchedule> schedules = db.retrieveList(SQL_SCHED_BPARTNER, params, X_M_ShipmentSchedule.class, trxName);

		final Map<Integer, MOrderLine> orderLines = db.retrieveMap(SQL_OL_SCHED_BPARTNER, params, MOrderLine.class, trxName);

		return mkResult(schedules, orderLines);
	}

	/**
	 * Note: The {@link I_C_OrderLine}s contained in the {@link OlAndSched} instances are {@link MOrderLine}s.
	 */
	@Override
	public List<OlAndSched> retrieveInvalid(final int adClientId, final int adPinstanceId, final boolean retrieveOnlyLocked, final String trxName)
	{

		final String sqlSched;
		final String sqlOlSched;

		if (!retrieveOnlyLocked)
		{
			// 1.
			// Mark the M_ShipmentSchedule_Recompute records that point to the scheds which we will work with
			// This allows us to distinguish them from records created later

			// task 08727: Tag the recompute records out-of-trx.
			// This is crucial because the invalidation-SQL checks if there exist un-tagged recompute records to avoid creating too many unneeded records.
			// So if the tagging was in-trx, then the invalidation-SQL would still see them as un-tagged and therefore the invalidation would fail.
			final String sqlUpdate = " UPDATE " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " sr " +
					"SET AD_Pinstance_ID=" + adPinstanceId +
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
			final Object[] sqlUpdateParams = null;
			final int countTagged = DB.executeUpdateEx(sqlUpdate, sqlUpdateParams, ITrx.TRXNAME_None);
			logger.debug("Marked {} entries for AD_Pinstance_ID={}", countTagged, adPinstanceId);

			sqlSched = SQL_SCHED_INVALID_3P;
			sqlOlSched = SQL_OL_SCHED_INVALID_3P;
		}
		else
		{
			// we won't tag the scheds which we are about to process
			// otherwise we could cause troubles for a server-side update-process that is currently running and has already tagged them with a different AD_PInstance_ID
			sqlSched = SQL_SCHED_INVALID_LOCKED_ONLY_3P;
			sqlOlSched = SQL_OL_SCHED_INVALID_LOCKED_ONLY_3P;
		}

		// 2.
		// Load the scheds the are pointed to by our marked M_ShipmentSchedule_Recompute records
		final int alsoRetriveSchedsWhichHaveAnInOutLine = 1;
		final Object[] params = new Object[] { adClientId, adPinstanceId, alsoRetriveSchedsWhichHaveAnInOutLine };

		final IDatabaseBL db = Services.get(IDatabaseBL.class);
		final List<X_M_ShipmentSchedule> schedules = db.retrieveList(sqlSched, params, X_M_ShipmentSchedule.class, trxName);

		final Map<Integer, MOrderLine> orderLines = db.retrieveMap(sqlOlSched, params, MOrderLine.class, trxName);

		return mkResult(schedules, orderLines);
	}

	private List<OlAndSched> mkResult(
			final List<X_M_ShipmentSchedule> schedules,
			final Map<Integer, MOrderLine> orderLines)
	{
		final List<OlAndSched> result = new ArrayList<OlAndSched>();

		for (final I_M_ShipmentSchedule schedule : schedules)
		{
			result.add(new OlAndSched(InterfaceWrapperHelper.create(orderLines.get(schedule.getC_OrderLine_ID()), I_C_OrderLine.class), schedule));
		}
		return result;
	}

	@Override
	public List<OlAndSched> retrieveForShipmentRun(
			final boolean includeUncompleted,
			final String trxName)
	{
		final Object[] param = new Object[] { Env.getAD_Client_ID(Env.getCtx()), includeUncompleted ? 1 : 0 };

		final IDatabaseBL db = Services.get(IDatabaseBL.class);

		final List<X_M_ShipmentSchedule> schedules = db.retrieveList(SQL_SCHED, param, X_M_ShipmentSchedule.class, trxName);

		final Map<Integer, MOrderLine> orderLines = db.retrieveMap(SQL_OL_SCHED, param, MOrderLine.class, trxName);

		return mkResult(schedules, orderLines);
	}

	@Override
	public Collection<I_M_ShipmentSchedule> retrieveForBPartner(
			final int partnerId,
			final String trxName)
	{
		final Object[] params = new Object[] { Env.getAD_Client_ID(Env.getCtx()), partnerId, 1 }; // with "1" we also return all scheds (not filtering for those that have an incomplete InOut)

		final IDatabaseBL db = Services.get(IDatabaseBL.class);

		final List<X_M_ShipmentSchedule> schedules = db.retrieveList(
				SQL_BPARTNER, params, X_M_ShipmentSchedule.class, trxName);

		return new ArrayList<I_M_ShipmentSchedule>(schedules);
	}

	@Override
	public void invalidateAll(final Properties ctx)
	{
		final String trxName = null;
		final int clientId = Env.getAD_Client_ID(ctx);

		final IDBService dbService = Services.get(IDBService.class);
		final PreparedStatement pstmt = dbService.mkPstmt(SQL_RECOMPUTE_ALL, trxName);

		try
		{
			pstmt.setInt(1, clientId);
			final int result = pstmt.executeUpdate();
			logger.debug("Invalidated {} entries for AD_Client_ID={}", result, clientId);
			//
			if (result > 0)
			{
				UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(ctx, ITrx.TRXNAME_ThreadInherited);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			dbService.close(pstmt);
		}
	}

	@Override
	public void invalidateForProducts(Collection<Integer> productIds, String trxName)
	{
		for (Integer productId : productIds)
		{
			Check.assume(productId != null, "Param 'productIds'=" + productIds + " contains no NULL values");
			invalidateForProduct(productId, trxName);
		}
	}

	@Override
	public void invalidateForProduct(final int productId, final String trxName)
	{
		if (productId <= 0)
		{
			return;
		}

		final IDBService dbService = Services.get(IDBService.class);
		final PreparedStatement pstmt = dbService.mkPstmt(SQL_RECOMPUTE_1P, trxName);

		try
		{
			pstmt.setInt(1, productId);
			final int result = pstmt.executeUpdate();
			logger.debug("Invalidated {} entries for productId={} ", result, productId);

			//
			if (result > 0)
			{
				UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(Env.getCtx(), trxName);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(pstmt);
		}
	}

	@Override
	public void invalidateForHeaderAggregationKeys(final Collection<String> headerAggregationKeys, final String trxName)
	{
		if (headerAggregationKeys == null || headerAggregationKeys.isEmpty())
		{
			return;
		}

		final StringBuilder headerAggregationKeysWhereClause = new StringBuilder();
		final List<Object> headerAggregationKeysParams = new ArrayList<Object>();
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

		final List<Object> sqlParams = new ArrayList<Object>();
		sqlParams.addAll(headerAggregationKeysParams);
		final String sql = "INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID) "
				+ " SELECT " + COLUMNNAME_M_ShipmentSchedule_ID
				+ " FROM " + I_M_ShipmentSchedule.Table_Name
				+ " WHERE "
				// Only those which have our header aggregation keys
				+ "   (" + headerAggregationKeysWhereClause + ")"
				// Only those which are not processed
				+ "   AND " + I_M_ShipmentSchedule.COLUMNNAME_Processed + "=? "
				// Only those which were not already added
				+ "   AND NOT EXISTS (select 1 from " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " e where e.AD_PInstance_ID is NULL and e.M_ShipmentSchedule_ID=" + I_M_ShipmentSchedule.Table_Name + "."
				+ I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ")";
		sqlParams.add(false); // Processed=false

		final int count = DB.executeUpdateEx(sql, sqlParams.toArray(), trxName);
		logger.debug("Invalidated {} shipment schedules for headerAggregationKeys={}", count, headerAggregationKeys);
		//
		if (count > 0)
		{
			UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(Env.getCtx(), trxName);
		}
	}

	@Override
	public void invalidateForDeliveryDate(final Timestamp date1, String trxName)
	{
		if (date1 == null)
		{
			return;
		}

		final IDBService dbService = Services.get(IDBService.class);
		final PreparedStatement pstmt = dbService.mkPstmt(SQL_RECOMPUTE_DELIVERYDATE_1P, trxName);

		try
		{
			pstmt.setTimestamp(1, date1);

			final int result = pstmt.executeUpdate();
			logger.debug("Invalidated {} entries for DeliveryDate={}", result, date1);

			//
			if (result > 0)
			{
				UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(Env.getCtx(), trxName);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(pstmt);
		}

	}

	@Override
	public void invalidate(final Collection<I_M_ShipmentSchedule> shipmentSchedules, final String trxName)
	{
		if (shipmentSchedules == null || shipmentSchedules.isEmpty())
		{
			return;
		}

		final Set<Integer> shipmentScheduleIds = new HashSet<>();
		for (final I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			if (shipmentSchedule == null)
			{
				continue;
			}
			shipmentScheduleIds.add(shipmentSchedule.getM_ShipmentSchedule_ID());
		}

		final List<Object> sqlParams = new ArrayList<Object>();
		final String sqlInWhereClause = DB.buildSqlList(shipmentScheduleIds, sqlParams); // creates the string and fills the sqlParams list

		final String sql = "INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID) "
				+ " SELECT " + I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID
				+ " FROM " + I_M_ShipmentSchedule.Table_Name
				+ " WHERE "
				// Only our shipment schedule Ids
				+ I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + " IN " + sqlInWhereClause
				// Only those which were not already added (technically not necessary, but shall reduce unnecessary bloat)
				+ "   AND NOT EXISTS (select 1 from " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " e where e.AD_PInstance_ID is NULL and e.M_ShipmentSchedule_ID=" + I_M_ShipmentSchedule.Table_Name + "."
				+ I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ")";

		final int count = DB.executeUpdateEx(sql, sqlParams.toArray(), trxName);
		logger.debug("Invalidated {} shipment schedules for M_ShipmentSchedule_IDs={}", count, shipmentScheduleIds);
		//
		if (count > 0)
		{
			UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(Env.getCtx(), trxName);
		}
	}

	protected void invalidateSchedulesForSelection(final int adPInstanceId, final String trxName)
	{
		final String sql = "INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID) "
				+ "\n SELECT " + I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID
				+ "\n FROM " + I_M_ShipmentSchedule.Table_Name
				+ "\n WHERE " + I_M_ShipmentSchedule.COLUMNNAME_Processed + "='N'"
				+ "\n AND EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID = ? AND s.T_Selection_ID = M_ShipmentSchedule_ID)";

		final int count = DB.executeUpdateEx(sql, new Object[] { adPInstanceId }, trxName);
		logger.debug("Invalidated {} M_ShipmentSchedules for AD_PInstance_ID={}", count, adPInstanceId);
		//
		if (count > 0)
		{
			UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(Env.getCtx(), trxName);
		}
	}

	@Override
	public void invalidate(final Collection<IStorageSegment> storageSegments)
	{
		if (storageSegments == null || storageSegments.isEmpty())
		{
			return;
		}

		final String ssAlias = I_M_ShipmentSchedule.Table_Name + ".";
		final StringBuilder sqlWhereClause = new StringBuilder();
		final List<Object> sqlParams = new ArrayList<>();

		// Not Processed
		sqlWhereClause.append(ssAlias + I_M_ShipmentSchedule.COLUMNNAME_Processed).append("=?");
		sqlParams.add(false);

		// task 08749: don't invalidate "forced" scheds, because there QtyToDeliver won't change anyways
		sqlWhereClause.append(" AND COALESCE(")
				.append(ssAlias + I_M_ShipmentSchedule.COLUMNNAME_DeliveryRule_Override + ",")
				.append(ssAlias + I_M_ShipmentSchedule.COLUMNNAME_DeliveryRule)
				.append(") != ?");
		sqlParams.add(X_M_ShipmentSchedule.DELIVERYRULE_Force);

		//
		// Filter shipment schedules by segments
		final StringBuilder sqlWhereClause_AllSegments = new StringBuilder();
		for (final IStorageSegment storageSegment : storageSegments)
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
		final String sql = "INSERT INTO " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " (M_ShipmentSchedule_ID) "
				+ "\n SELECT " + I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID
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
			UpdateInvalidShipmentSchedulesWorkpackageProcessor.schedule(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
		}
	}

	/**
	 * Build {@link I_M_ShipmentSchedule} where clause based on given segment.
	 *
	 * @param ssAlias {@link I_M_ShipmentSchedule#Table_Name}'s alias
	 * @param storageSegment
	 * @param sqlParams output SQL parameters
	 * @return where clause or <code>null</code>
	 */
	private String buildShipmentScheduleWhereClause(final String ssAlias, final IStorageSegment storageSegment, final List<Object> sqlParams)
	{
		final Set<Integer> productIds = storageSegment.getM_Product_IDs();
		if (productIds == null || productIds.isEmpty())
		{
			return null;
		}

		final Set<Integer> bpartnerIds = storageSegment.getC_BPartner_IDs();
		if (bpartnerIds == null || bpartnerIds.isEmpty())
		{
			return null;
		}

		final Set<Integer> locatorIds = storageSegment.getM_Locator_IDs();
		if (locatorIds == null || locatorIds.isEmpty())
		{
			return null;
		}

		// final boolean debug = Services.get(IDeveloperModeBL.class).isEnabled();
		final StringBuilder whereClause = new StringBuilder();

		//
		// Products
		final boolean resetAllProducts = productIds.contains(0) || productIds.contains(-1) || productIds.contains(null);
		if (!resetAllProducts)
		{
			final String productColumnName = ssAlias + I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID;
			whereClause.append("\n\t AND ");
			whereClause.append("(").append(DB.buildSqlList(productColumnName, productIds, sqlParams)).append(")");
			// if (debug)
			// {
			// whereClause.append(" -- ").append(productIds);
			// }
		}

		//
		// BPartners
		// NOTE: If we were asked to reset for BPartner=none (i.e. value 0, -1 or null) then we shall reset for all of them,
		// because the QOH from this segment could be used by ALL
		final boolean resetAllBPartners = bpartnerIds.contains(0) || bpartnerIds.contains(-1) || bpartnerIds.contains(null);
		if (!resetAllBPartners)
		{
			final String bpartnerColumnName = "COALESCE(" + ssAlias + I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID + ", " + ssAlias + I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_ID + ")";
			whereClause.append("\n\t AND ");
			whereClause.append("(").append(DB.buildSqlList(bpartnerColumnName, bpartnerIds, sqlParams)).append(")");
			// if (debug)
			// {
			// whereClause.append(" -- ").append(bpartnerIds);
			// }
		}

		//
		// Locators
		// NOTE: same as for bPartners if no particular locator is specified, it means "all of them"
		final boolean resetAllLocators = locatorIds.contains(0) || locatorIds.contains(-1) || locatorIds.contains(null);
		if (!resetAllLocators)
		{
			final String warehouseColumnName = "COALESCE(" + ssAlias + I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID + ", " + ssAlias + I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID + ")";
			whereClause.append("\n\t AND ");
			whereClause.append(" EXISTS (select 1 from " + I_M_Locator.Table_Name + " loc"
					+ " WHERE"
					+ "\n\t\t loc." + I_M_Locator.COLUMNNAME_M_Warehouse_ID + "=" + warehouseColumnName
					+ " AND " + DB.buildSqlList("loc." + I_M_Locator.COLUMNNAME_M_Locator_ID, locatorIds, sqlParams)
					+ ")");
			// if (debug)
			// {
			// whereClause.append(" -- ").append(locatorIds);
			// }
		}

		//
		// Attributes (if any)
		final Set<IStorageAttributeSegment> attributeSegments = storageSegment.getAttributes();
		final String attributeSegmentsWhereClause = buildAttributeInstanceWhereClause(attributeSegments, sqlParams);
		if (!Check.isEmpty(attributeSegmentsWhereClause, true))
		{
			whereClause.append("\n\t AND ");
			whereClause.append("EXISTS (SELECT 1 FROM " + I_M_AttributeInstance.Table_Name + " ai "
					+ " WHERE ai." + I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID + "=" + ssAlias + I_M_ShipmentSchedule.COLUMNNAME_M_AttributeSetInstance_ID
					+ " AND (" + attributeSegmentsWhereClause + ")"
					+ ")");
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
	private String buildAttributeInstanceWhereClause(final Set<IStorageAttributeSegment> attributeSegments, final List<Object> sqlParams)
	{
		if (Check.isEmpty(attributeSegments))
		{
			return null;
		}

		final StringBuilder attributeSegmentsWhereClause = new StringBuilder();
		for (final IStorageAttributeSegment attributeSegment : attributeSegments)
		{
			final String attributeSegmentWhereClause = buildAttributeInstanceWhereClause(attributeSegment, sqlParams);
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
	private String buildAttributeInstanceWhereClause(final IStorageAttributeSegment attributeSegment, final List<Object> sqlParams)
	{
		if (attributeSegment == null)
		{
			return null;
		}

		final StringBuilder whereClause = new StringBuilder();

		//
		// Filter by M_AttributeSetInstance_ID
		final int attributeSetInstanceId = attributeSegment.getM_AttributeSetInstance_ID();
		if (attributeSetInstanceId > 0)
		{
			if (whereClause.length() > 0)
			{
				whereClause.append(" AND ");
			}
			whereClause.append("ai.").append(I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID).append("=?");
			sqlParams.add(attributeSetInstanceId);
		}

		//
		// Filter by M_Attribute_ID
		final int attributeId = attributeSegment.getM_Attribute_ID();
		if (attributeId > 0)
		{
			if (whereClause.length() > 0)
			{
				whereClause.append(" AND ");
			}
			whereClause.append("ai.").append(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID).append("=?");
			sqlParams.add(attributeId);
		}

		if (whereClause.length() <= 0)
		{
			return null;
		}

		return whereClause.toString();
	}

	@Override
	public void deleteRecomputeMarkers(final int adPInstanceId, final String trxName)
	{
		final String sql = "DELETE FROM " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " WHERE AD_Pinstance_ID=" + adPInstanceId;

		final int result = DB.executeUpdateEx(sql, trxName);
		logger.debug("Deleted {} {} entries for AD_Pinstance_ID={}", result, M_SHIPMENT_SCHEDULE_RECOMPUTE, adPInstanceId);
	}

	@Override
	public void releaseRecomputeMarker(final int adPInstanceId, final String trxName)
	{
		final String sql = "UPDATE " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " SET AD_PInstance_ID=NULL WHERE AD_PInstance_ID=" + adPInstanceId;

		final int result = DB.executeUpdateEx(sql, trxName);
		logger.debug("Updated {} {} entries for AD_Pinstance_ID={} and released the marker.", result, M_SHIPMENT_SCHEDULE_RECOMPUTE, adPInstanceId);
	}

	@Override
	public void deleteSchedulesWithOutOl(final String trxName)
	{
		final String sql = "DELETE FROM " + I_M_ShipmentSchedule.Table_Name + " s "
				+ "WHERE NOT EXISTS ("
				+ "   select 1 from " + I_C_OrderLine.Table_Name + " ol "
				+ "   where ol." + I_C_OrderLine.COLUMNNAME_C_OrderLine_ID + "=s." + I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID
				+ ")";

		final int delCnt = DB.executeUpdateEx(sql, trxName);

		logger.debug("Deleted {} shipment schedules whose C_OrderLine is already gone", delCnt);
	}

	@Override
	public void deleteProcessedShipmentRunIds(final List<Integer> processedShipmentRunIds, final String trxName)
	{
		final String sql = "DELETE FROM " + M_SHIPMENT_SCHEDULE_SHIPMENT_RUN + " WHERE M_ShipmentSchedule_ID=? AND Processed='Y'";

		final PreparedStatement pstmt = DB.prepareStatement(sql, trxName);

		try
		{
			for (final int id : processedShipmentRunIds)
			{
				pstmt.setInt(1, id);

				// commenting out this check,
				// because we can't be sure that the schedule wasn't already deleted in parallel by another client or by the server's scheduled update process
				/* final int deleteCnt = */
				pstmt.executeUpdate();
				// Check.assume(deleteCnt == 1, "M_ShipmentSchedule_ID=" + id + "; deleteCnt=" + deleteCnt);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(pstmt);
		}
	}

	@Override
	public List<Integer> retrieveProcessedShipmentRunIds(final String trxName)
	{
		final String sql = "SELECT M_ShipmentSchedule_ID FROM " + M_SHIPMENT_SCHEDULE_SHIPMENT_RUN + " WHERE Processed='Y'";

		final List<Integer> result = new ArrayList<Integer>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				result.add(rs.getInt(1));
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		return result;
	}

	@Override
	public void setIsDiplayedForProduct(final int productId, final boolean displayed, final String trxName)
	{
		final IDBService dbService = Services.get(IDBService.class);

		final PreparedStatement pstmt = dbService.mkPstmt(SQL_SET_DISPLAYED, trxName);
		final String strDisplayed = displayed ? "Y" : "N";
		try
		{
			pstmt.setString(1, strDisplayed);
			pstmt.setInt(2, productId);

			final int result = pstmt.executeUpdate();
			logger.debug("Invalidated {} entries for productId={}", result, productId);
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(pstmt);
		}
	}

	@Override
	public List<OlAndSched> retrieveOlAndSchedsForProduct(final Properties ctx, final int productId, final String trxName)
	{
		final Object[] params = { productId, Env.getAD_Client_ID(ctx), 0 };

		final IDatabaseBL db = Services.get(IDatabaseBL.class);
		final List<X_M_ShipmentSchedule> schedules = db.retrieveList(SQL_SELECT_SCHEDS_FOR_PRODUCT, params, X_M_ShipmentSchedule.class, trxName);

		final Map<Integer, MOrderLine> orderLines = db.retrieveMap(SQL_SELECT_OLS_FOR_PRODUCT, params, MOrderLine.class, trxName);

		return mkResult(schedules, orderLines);
	}

	@Override
	public List<OlAndSched> createLocksForShipmentRun(
			final List<OlAndSched> olsAndSchedsToLock,
			final int adPInstanceId,
			final int adUserId,
			final String trxName)
	{
		final List<OlAndSched> result = new ArrayList<OlAndSched>();

		// NOTE: we are not using trxName given as parameter because we will lock in an anonymous transaction to let the locks be visible to other processes too
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(new TrxRunnable2()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				aquireLocksForShipmentRun(olsAndSchedsToLock, adPInstanceId, adUserId, localTrxName);

				final List<OlAndSched> olsAndSchedsLocked = filterLockedScheds(olsAndSchedsToLock);

				if (olsAndSchedsLocked.size() != olsAndSchedsToLock.size())
				{
					throw new UnableToLockShipmentRunException();
				}

				result.addAll(olsAndSchedsLocked);
			}

			@Override
			public boolean doCatch(Throwable e) throws Throwable
			{
				if (e instanceof UnableToLockShipmentRunException)
				{
					throw e;
				}
				else
				{
					throw new UnableToLockShipmentRunException(e);
				}
			}

			@Override
			public void doFinally()
			{
				// nothing
			}
		});

		return result;
	}

	/**
	 * Filter and return only those {@link OlAndSched} objects which were locked for shipment run (i.e. {@link OlAndSched#isAvailForShipmentRun()} is true).
	 *
	 * @param allOlsAndScheds
	 * @return {@link OlAndSched} which are available for shipment run
	 */
	private List<OlAndSched> filterLockedScheds(final List<OlAndSched> allOlsAndScheds)
	{
		final List<OlAndSched> result = new ArrayList<OlAndSched>();

		for (final OlAndSched olAndSched : allOlsAndScheds)
		{
			if (olAndSched.isAvailForShipmentRun())
			{
				result.add(olAndSched);
			}
		}
		return result;
	}

	@Override
	public void markLocksForShipmentRunProcessed(
			final int adPInstanceId,
			final int adUserId,
			final String trxName)
	{
		final StringBuilder sql = new StringBuilder("UPDATE " + M_SHIPMENT_SCHEDULE_SHIPMENT_RUN
				+ " SET Processed='Y' "
				+ " WHERE ");

		sql.append(appendPInstanceIdAndUserId(adPInstanceId, adUserId));

		execUpdateEx(sql.toString(), trxName);
	}

	private String appendPInstanceIdAndUserId(final int adPInstanceId, final int adUserId)
	{
		final StringBuilder sql = new StringBuilder();

		sql.append("AD_PInstance_ID");

		// note: PreparedStatement.setNull() doesn't work
		if (adPInstanceId > 0)
		{
			sql.append("=" + adPInstanceId);
		}
		else
		{
			sql.append(" IS NULL ");
		}

		sql.append(" AND AD_User_ID ");

		if (adUserId > 0)
		{
			sql.append("=" + adUserId);
		}
		else
		{
			sql.append(" IS NULL ");
		}

		return sql.toString();
	}

	private void aquireLocksForShipmentRun(
			final List<OlAndSched> olsAndSchedsToProcess,
			final int adPinstanceId,
			final int adUserId,
			final String trxName) throws SQLException
	{
		Check.assume(!Services.get(ITrxManager.class).isNull(trxName), "Param 'trxName' shall be a not-null transaction: {}", trxName);

		final String sql = "INSERT INTO " + M_SHIPMENT_SCHEDULE_SHIPMENT_RUN
				+ " (M_ShipmentSchedule_ID, AD_PInstance_ID, AD_User_ID)"
				+ " SELECT ?, ?, ?"
				+ " WHERE "
				+ "   NOT EXISTS ("
				+ "     select 1 "
				+ "     from " + M_SHIPMENT_SCHEDULE_SHIPMENT_RUN + " sr1 "
				+ "     where sr1.M_ShipmentSchedule_ID=?"
				+ "   )";

		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			for (final OlAndSched olAndSched : olsAndSchedsToProcess)
			{
				final I_M_ShipmentSchedule sched = olAndSched.getSched();
				final int shipmentScheduleId = sched.getM_ShipmentSchedule_ID();

				//
				// SQL Parameters
				int paramIdx = 1;
				pstmt.setInt(paramIdx++, shipmentScheduleId);
				if (adPinstanceId > 0)
				{
					pstmt.setInt(paramIdx++, adPinstanceId);
				}
				else
				{
					pstmt.setNull(paramIdx++, Types.NUMERIC);
				}

				if (adUserId > 0)
				{
					pstmt.setInt(paramIdx++, adUserId);
				}
				else
				{
					pstmt.setNull(paramIdx++, Types.NUMERIC);
				}
				pstmt.setInt(paramIdx++, shipmentScheduleId);

				//
				// Execute lock
				final int insertCnt = pstmt.executeUpdate();
				Check.assume(insertCnt == 1 || insertCnt == 0, "insertCnt is either 0 or 1; sched={}, insertCnt={}", sched, insertCnt);
				final boolean aquired = insertCnt == 1;

				//
				// Update current olAndSched status
				olAndSched.setAvailForShipmentRun(aquired);
			}
		}
		finally
		{
			DB.close(pstmt);
		}
	}

	@Override
	public void updateInOutGentColumnPInstanceId(final int adPinstanceId, final int adUserId, final String trxName)
	{
		final String sql = "UPDATE " + ShipmentSchedulePA.M_SHIPMENT_SCHEDULE_SHIPMENT_RUN + " SET AD_Pinstance_ID=" + adPinstanceId + " WHERE AD_User_ID=" + adUserId;
		execUpdateEx(sql, trxName);
	}

	private void execUpdateEx(final String sql, final String trxName)
	{
		final PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
		try
		{
			final int updateCnt = pstmt.executeUpdate();
			logger.debug("SQL={}; updateCnt={}", sql, updateCnt);
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(pstmt);
		}
	}

	@Override
	public int deleteUnprocessedLocksForShipmentRun(final int adPInstanceId, final int adUserId, final String trxName)
	{
		final String sql = "DELETE FROM " + M_SHIPMENT_SCHEDULE_SHIPMENT_RUN
				+ " WHERE Processed='N' AND " + appendPInstanceIdAndUserId(adPInstanceId, adUserId);

		final int deleteCnt = DB.executeUpdateEx(sql, trxName);
		logger.debug("Deleted {} unprocessed records from " + M_SHIPMENT_SCHEDULE_SHIPMENT_RUN + " for AD_User_ID='{}' and AD_PInstance_ID='{}'", deleteCnt, M_SHIPMENT_SCHEDULE_SHIPMENT_RUN, adUserId, adPInstanceId);

		return deleteCnt;
	}

	@Override
	public int deleteLocksForShipmentRun(final int adPInstanceId, final int adUserId)
	{
		final String trxName = ITrx.TRXNAME_None;

		final String sql = "DELETE FROM " + M_SHIPMENT_SCHEDULE_SHIPMENT_RUN
				+ " WHERE " + appendPInstanceIdAndUserId(adPInstanceId, adUserId);

		final int deleteCnt = DB.executeUpdateEx(sql, trxName);
		logger.debug("Deleted {} all records from {} for AD_User_ID='{}' and AD_PInstance_ID='{}'", deleteCnt, M_SHIPMENT_SCHEDULE_SHIPMENT_RUN, adUserId, adPInstanceId);

		return deleteCnt;
	}

	@Override
	public boolean isInvalid(final I_M_ShipmentSchedule sched)
	{
		final String sql = " SELECT 1 "
				+ " FROM " + M_SHIPMENT_SCHEDULE_RECOMPUTE + " sr "
				+ " WHERE sr." + COLUMNNAME_M_ShipmentSchedule_ID + "=?";

		final int result = DB.getSQLValue(ITrx.TRXNAME_None, sql, sched.getM_ShipmentSchedule_ID());

		final boolean flaggedForRrecompute = result == 1;
		if (flaggedForRrecompute)
		{
			return true; // no need to dig further
		}
		return false; // sched is valid
	}

	@Override
	public Iterator<I_M_ShipmentSchedule> retrieveForBPartner(final I_C_BPartner bPartner)
	{
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule.class, bPartner)
				.addOnlyActiveRecordsFilter()
				.addCoalesceEqualsFilter(bPartner.getC_BPartner_ID(), I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID, I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_ID)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Processed, false);
		queryBuilder
				.orderBy()
				.addColumn(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID);

		return queryBuilder.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true) // because the Processed flag might change while we iterate
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_M_ShipmentSchedule.class);
	}

	/**
	 * Mass-update a given shipment schedule column.
	 *
	 * If there were any changes and the invalidate parameter is on true, those shipment schedules will be invalidated.
	 *
	 * @param inoutCandidateColumnName {@link I_M_ShipmentSchedule}'s column to update
	 * @param value value to set (you can also use {@link ModelColumnNameValue})
	 * @param updateOnlyIfNull if true then it will update only if column value is null (not set)
	 * @param selectionId ShipmentSchedule selection (AD_PInstance_ID)
	 * @param trxName
	 */
	private final <ValueType> void updateColumnForSelection(
			final String inoutCandidateColumnName,
			final ValueType value,
			final boolean updateOnlyIfNull,
			final int selectionId,
			final boolean invalidate,
			final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		//
		// Create the selection which we will need to update
		final Properties ctx = Env.getCtx();
		final IQueryBuilder<I_M_ShipmentSchedule> selectionQueryBuilder = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule.class, ctx, trxName)
				.setOnlySelection(selectionId)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Processed, false) // do not touch the processed shipment schedules
				;

		if (updateOnlyIfNull)
		{
			selectionQueryBuilder.addEqualsFilter(inoutCandidateColumnName, null);
		}
		final int selectionToUpdateId = selectionQueryBuilder.create().createSelection();
		if (selectionToUpdateId <= 0)
		{
			// nothing to update
			return;
		}

		//
		// Update our new selection
		queryBL.createQueryBuilder(I_M_ShipmentSchedule.class, ctx, trxName)
				.setOnlySelection(selectionToUpdateId)
				.create()
				.updateDirectly()
				.addSetColumnValue(inoutCandidateColumnName, value)
				.execute();

		//
		// Invalidate the inout candidates which we updated
		if (invalidate)
		{
			invalidateSchedulesForSelection(selectionToUpdateId, trxName);
		}
	}

	@Override
	public void updateDeliveryDate_Override(Timestamp deliveryDate, int ADPinstance_ID, String trxName)
	{
		// No need of invalidation after deliveryDate update because it is not used for anything else than preparation date calculation.
		// In case this calculation is needed, the invalidation will be done on preparation date updating
		// see de.metas.inoutcandidate.api.impl.ShipmentSchedulePA.updatePreparationDate_Override(Timestamp, int, String)

		final boolean invalidate = false;

		updateColumnForSelection(
				I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate_Override,               // inoutCandidateColumnName
				deliveryDate,               // value
				false,               // updateOnlyIfNull
				ADPinstance_ID,               // selectionId
				invalidate,               // invalidate schedules = false
				trxName // trxName
		);
	}

	@Override
	public void updatePreparationDate_Override(final Timestamp preparationDate, final int ADPinstance_ID, final String trxName)
	{
		// in case the preparation date is given, it will only be set. No Invalidation needed
		// in case it is not given (null) an invalidation is needed because it will be calculated based on the delivery date

		boolean invalidate = false;

		if (preparationDate == null)
		{
			invalidate = true;
		}
		updateColumnForSelection(
				I_M_ShipmentSchedule.COLUMNNAME_PreparationDate_Override,               // inoutCandidateColumnName
				preparationDate,               // value
				false,               // updateOnlyIfNull
				ADPinstance_ID,               // selectionId
				invalidate,               // invalidate schedules
				trxName // trxName
		);
	}

	@Override
	public IQueryBuilder<I_M_ShipmentSchedule> createQueryForShipmentScheduleSelection(final Properties ctx, final IQueryFilter<I_M_ShipmentSchedule> userSelectionFilter)
	{
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule.class, ctx, ITrx.TRXNAME_None)
				.filter(userSelectionFilter)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Processed, false)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		return queryBuilder;
	}

	@Override
	public Set<I_M_ShipmentSchedule> retrieveForInvoiceCandidate(final I_C_Invoice_Candidate candidate)
	{
		Set<I_M_ShipmentSchedule> schedules = ImmutableSet.of();

		final int tableID = candidate.getAD_Table_ID();

		// invoice candidate references an orderline
		if (tableID == InterfaceWrapperHelper.getTableId(I_C_OrderLine.class))
		{
			final org.compiere.model.I_C_OrderLine orderLine = candidate.getC_OrderLine();
			if (orderLine != null)
			{
				I_M_ShipmentSchedule schedForOrderLine = retrieveForOrderLine(orderLine);
				if (schedForOrderLine != null)
				{
					schedules = ImmutableSet.of(schedForOrderLine);
				}
			}
		}

		// invoice candidate references an inoutline
		else if (tableID == InterfaceWrapperHelper.getTableId(I_M_InOutLine.class))
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(candidate);
			final String trxName = InterfaceWrapperHelper.getTrxName(candidate);

			final I_M_InOutLine inoutLine = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_M_InOutLine.class, trxName);

			if (inoutLine != null)
			{
				schedules = ImmutableSet.copyOf(retrieveForInOutLine(inoutLine));
			}
		}
		else
		{
			// No other tables are supported yet
			// Please add implementation if required
		}
		return schedules;
	}

	@Override
	public Set<I_M_ShipmentSchedule> retrieveForInOutLine(final I_M_InOutLine inoutLine)
	{
		final Map<Integer, I_M_ShipmentSchedule> schedules = new LinkedHashMap<>();

		// add all the shipment schedules from the QtyPicked entries
		final Map<Integer, I_M_ShipmentSchedule> schedulesForInOutLine = Services.get(IShipmentScheduleAllocDAO.class).retrieveSchedulesForInOutLineQuery(inoutLine)
				.create()
				.mapById(I_M_ShipmentSchedule.class);
		schedules.putAll(schedulesForInOutLine);

		// fallback to the case when the inoutline has an orderline set but has no Qty Picked entries
		// this happens when we create manual Shipments
		final org.compiere.model.I_C_OrderLine orderLine = inoutLine.getC_OrderLine();
		if (orderLine != null)
		{
			final I_M_ShipmentSchedule schedForOrderLine = retrieveForOrderLine(orderLine);
			if (schedForOrderLine != null)
			{
				schedules.put(schedForOrderLine.getM_ShipmentSchedule_ID(), schedForOrderLine);
			}
		}

		return ImmutableSet.copyOf(schedules.values());
	}

}
