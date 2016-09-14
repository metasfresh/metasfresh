package de.metas.contracts.subscription.impl;

/*
 * #%L
 * de.metas.contracts
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import static de.metas.flatrate.model.I_C_SubscriptionProgress.COLUMNNAME_C_Flatrate_Term_ID;
import static de.metas.flatrate.model.I_C_SubscriptionProgress.COLUMNNAME_EventDate;
import static de.metas.flatrate.model.I_C_SubscriptionProgress.COLUMNNAME_EventType;
import static de.metas.flatrate.model.I_C_SubscriptionProgress.COLUMNNAME_SeqNo;
import static de.metas.flatrate.model.I_C_SubscriptionProgress.COLUMNNAME_Status;
import static de.metas.flatrate.model.X_C_SubscriptionProgress.EVENTTYPE_Lieferung;
import static de.metas.flatrate.model.X_C_SubscriptionProgress.STATUS_Ausgefuehrt;
import static de.metas.flatrate.model.X_C_SubscriptionProgress.STATUS_Ausgeliefert;
import static de.metas.flatrate.model.X_C_SubscriptionProgress.STATUS_Geplant;
import static de.metas.flatrate.model.X_C_SubscriptionProgress.STATUS_Verzoegert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.db.IDBService;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.flatrate.interfaces.I_C_OLCand;
import de.metas.flatrate.model.I_C_Contract_Term_Alloc;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_SubscriptionProgress;
import de.metas.flatrate.model.MSubscriptionProgress;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.logging.LogManager;

public class SubscriptionDAO implements ISubscriptionDAO
{

	static final String ORDER_SEQNO = COLUMNNAME_SeqNo + " DESC";

	static final String WHERE_FIRST_SP_AFTER_DATE = COLUMNNAME_C_Flatrate_Term_ID
			+ "=? AND " //
			+ COLUMNNAME_EventDate + "<=?";

	public static final String SUBSCRIPTION_NO_SP_AT_DATE_1P = "Subscription_NoSPAtDate_1P";

	private final transient Logger logger = LogManager.getLogger(getClass());

	public static final String SQL_SUBSCRIPTION = //
	"  SELECT c." + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID
			+ "  FROM    " //
			+ "    " + I_C_Flatrate_Term.Table_Name + " c" //
			+ "    LEFT JOIN " + I_C_Flatrate_Conditions.Table_Name + " s ON s.C_Flatrate_Conditions_ID=c.C_Flatrate_Conditions_ID" //
			+ "    LEFT JOIN M_PriceList pl ON s.M_PricingSystem_ID=pl.M_PricingSystem_ID" //
			+ "    LEFT JOIN C_Location l ON l.C_Country_ID=pl.C_Country_ID" //
			+ "    LEFT JOIN M_PriceList_Version plv ON plv.M_PriceList_ID=pl.M_PriceList_ID" //
			+ "    LEFT JOIN M_ProductPrice pp ON pp.M_PriceList_Version_ID=plv.M_PriceList_Version_ID" //
			+ "  WHERE   " //
			+ "    pl.IsSOPriceList='Y'" //
			+ "    AND c.IsActive='Y'" //
			+ "    AND s.IsActive='Y'" //
			+ "    AND pl.IsActive='Y'" //
			+ "    AND plv.IsActive='Y'" //
			+ "    AND pp.IsActive='Y'" //
			+ "    AND (?=0 OR l.C_Location_ID=? OR pl.C_Country_ID IS NULL)" //
			+ "    AND pp.M_Product_ID=?" //
			+ "    AND c." + I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID + "=?" //
			+ "    AND plv.ValidFrom<=?" //
			+ "    AND c." + I_C_Flatrate_Term.COLUMNNAME_ContractStatus + "='"
			+ X_C_Flatrate_Term.CONTRACTSTATUS_Laufend
			+ "'"
			+ "  ORDER BY c.StartDate, l.C_Location_ID DESC, plv.ValidFrom; "; //

	public static final String SQL_DELIVERIES_IL = //
	" SELECT * " //
			+ " FROM  " + I_C_SubscriptionProgress.Table_Name//
			+ " WHERE M_InOutLine=?";

	public static final String SQL_OPEN_DELIVERY_OL = //
	" SELECT sd.* " //
			+ " FROM C_SubscriptionProgress sd "
			+ " LEFT JOIN C_SubscriptionControl sc " //
			+ "   ON sc.C_SubscriptionControl_ID = sd.C_SubscriptionControl_ID" //
			+ " WHERE " //
			+ "   sd.Status=?"//
			+ "   AND sd." + COLUMNNAME_EventType + "='"
			+ EVENTTYPE_Lieferung
			+ "'" //
			+ "   AND sd.C_OrderLine_ID=?";

	public static final String SQL_RENUMBER = //
			" UPDATE C_Subscription_Delivery " //
					+ " SET SeqNo=(SeqNo+1) " //
					+ " WHERE SeqNo>? AND C_SubScriptionControl_ID=?";

	public I_C_SubscriptionProgress retrieveLastSP(
			final Properties ctx,
			final int termId,
			final int seqNo,
			final String trxName)
	{
		final String where =
				COLUMNNAME_C_Flatrate_Term_ID + "=? AND " + COLUMNNAME_SeqNo + ">=? ";

		return new Query(ctx, I_C_SubscriptionProgress.Table_Name, where, trxName)
				.setParameters(termId, seqNo)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(ORDER_SEQNO)
				.first(I_C_SubscriptionProgress.class);
	}

	@Override
	public I_C_SubscriptionProgress retrieveNextSP(final I_C_Flatrate_Term control, final Timestamp date, final int seqNo)
	{
		logger.debug("Parameters: date=" + date + ", seqNo=" + seqNo + ", control=" + control);

		final Properties ctx = InterfaceWrapperHelper.getCtx(control);
		final String trxName = InterfaceWrapperHelper.getTrxName(control);

		final String where =
				COLUMNNAME_C_Flatrate_Term_ID + "=? AND "
						+ COLUMNNAME_EventDate + ">=? AND "
						+ COLUMNNAME_SeqNo + ">=? AND "
						+ COLUMNNAME_Status + " not in ('" + STATUS_Ausgefuehrt + "','" + STATUS_Ausgeliefert + "')";

		final I_C_SubscriptionProgress result =
				new Query(ctx, I_C_SubscriptionProgress.Table_Name, where, trxName)
						.setParameters(control.getC_Flatrate_Term_ID(), date, seqNo)
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.setOrderBy(COLUMNNAME_SeqNo)
						.first(I_C_SubscriptionProgress.class);

		if (result != null)
		{
			return result;
		}

		return retrieveLastSP(ctx, control.getC_Flatrate_Term_ID(), seqNo, trxName);
	}

	@Override
	public List<I_C_SubscriptionProgress> retrievePlannedAndDelayedDeliveries(
			final Properties ctx,
			final Timestamp date,
			final String trxName)
	{

		final String where =
				COLUMNNAME_EventType + "='" + EVENTTYPE_Lieferung + "'"
						+ " AND " + I_C_SubscriptionProgress.COLUMNNAME_Status + " IN ('" + STATUS_Geplant + "', '" + STATUS_Verzoegert + "')"
						+ " AND " + COLUMNNAME_EventDate + "<=?";

		final String orderBy =
				COLUMNNAME_EventDate + ", " + COLUMNNAME_C_Flatrate_Term_ID + ", " + COLUMNNAME_SeqNo;

		return new Query(ctx, I_C_SubscriptionProgress.Table_Name, where, trxName)
				.setParameters(date)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(orderBy)
				.list(I_C_SubscriptionProgress.class);
	}

	@Override
	public I_C_Flatrate_Conditions retrieveSubscription(
			final Timestamp date,
			final int bPartnerId, final int productId, final int locationId,
			final String trxName)
	{

		final IDBService db = Services.get(IDBService.class);
		final PreparedStatement pstmt = db.mkPstmt(SQL_SUBSCRIPTION, trxName);
		ResultSet rs = null;

		try
		{
			pstmt.setInt(1, locationId);
			pstmt.setInt(2, locationId);
			pstmt.setInt(3, productId);
			pstmt.setInt(4, bPartnerId);
			pstmt.setTimestamp(5, date);

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final int subscriptionId = rs.getInt(1);
				return InterfaceWrapperHelper.create(Env.getCtx(), subscriptionId, I_C_Flatrate_Conditions.class, trxName);
			}
			return null;

		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	public List<I_C_SubscriptionProgress> retrieveDeliveriesForShipmentSchedule(
			final int inOutLineId, final String trxName)
	{

		final Query query = new Query(Env.getCtx(),
				I_C_SubscriptionProgress.Table_Name, "M_InOutLine_ID=?",
				trxName);
		query.setParameters(new Object[] { inOutLineId });
		query.setOrderBy("SeqNo");

		final List<MSubscriptionProgress> result = query.list();
		return new ArrayList<I_C_SubscriptionProgress>(result);
	}

	@Override
	public I_C_SubscriptionProgress insertNewDelivery(final I_C_SubscriptionProgress predecessor)
	{
		final Integer predecessorSeqNo = predecessor.getSeqNo();
		final Integer subscrControlId = predecessor.getC_Flatrate_Term_ID();

		final Properties ctx = InterfaceWrapperHelper.getCtx(predecessor);
		final String trxName = InterfaceWrapperHelper.getTrxName(predecessor);

		final int no = DB.executeUpdateEx(SQL_RENUMBER, new Object[] { predecessorSeqNo, subscrControlId }, trxName);

		logger.info("Renumbered "
				+ no
				+ " existing I_C_SubscriptionProgress entries with C_SubscriptionControl_ID "
				+ subscrControlId + " and SeqNo>"
				+ predecessorSeqNo);

		final I_C_SubscriptionProgress sdNew = InterfaceWrapperHelper.create(ctx, I_C_SubscriptionProgress.class, trxName);

		sdNew.setC_Flatrate_Term_ID(subscrControlId);

		sdNew.setQty(predecessor.getQty());
		sdNew.setStatus(predecessor.getStatus());
		sdNew.setM_ShipmentSchedule_ID(predecessor.getM_ShipmentSchedule_ID());
		sdNew.setEventDate(predecessor.getEventDate());
		sdNew.setSeqNo(predecessorSeqNo + 1);

		sdNew.setDropShip_BPartner_ID(predecessor.getDropShip_BPartner_ID());
		sdNew.setDropShip_Location_ID(predecessor.getDropShip_Location_ID());
		sdNew.setDropShip_User_ID(predecessor.getDropShip_User_ID());

		InterfaceWrapperHelper.save(sdNew);

		logger.info("Created new I_C_SubscriptionProgress " + sdNew);

		return sdNew;
	}

	@Override
	public I_C_Flatrate_Term retrieveTermForOl(final I_C_OrderLine ol)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final String trxName = InterfaceWrapperHelper.getTrxName(ol);

		return new Query(ctx, I_C_Flatrate_Term.Table_Name, I_C_Flatrate_Term.COLUMNNAME_C_OrderLine_Term_ID + "=?", trxName) //
				.setParameters(ol.getC_OrderLine_ID())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.firstOnly(I_C_Flatrate_Term.class);
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveTermsForOLCand(final I_C_OLCand olCand)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

		final String wc =
				I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + " IN (\n" +
						"  select " + I_C_Contract_Term_Alloc.COLUMNNAME_C_Flatrate_Term_ID + "\n" +
						"  from " + I_C_Contract_Term_Alloc.Table_Name + " cta \n" +
						"  where \n" +
						"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_IsActive + "=" + DB.TO_STRING("Y") + " AND \n" +
						"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_AD_Client_ID + "=" + I_C_Flatrate_Term.Table_Name + "." + I_C_Flatrate_Term.COLUMNNAME_AD_Client_ID + " AND \n" +
						"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_C_OLCand_ID + "=? \n" +
						")";

		return new Query(ctx, I_C_Flatrate_Term.Table_Name, wc, trxName) //
				.setParameters(olCand.getC_OLCand_ID())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.list(I_C_Flatrate_Term.class);

	}

	@Override
	public <T extends I_C_OLCand> List<T> retrieveOLCands(final I_C_Flatrate_Term term, final Class<T> clazz)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final String wc =
				I_C_OLCand.COLUMNNAME_C_OLCand_ID + " IN (\n" +
						"  select " + I_C_Contract_Term_Alloc.COLUMNNAME_C_OLCand_ID + "\n" +
						"  from " + I_C_Contract_Term_Alloc.Table_Name + " cta \n" +
						"  where \n" +
						"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_IsActive + "=" + DB.TO_STRING("Y") + " AND \n" +
						"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_AD_Client_ID + "=" + I_C_OLCand.Table_Name + "." + I_C_OLCand.COLUMNNAME_AD_Client_ID + " AND \n" +
						"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_C_Flatrate_Term_ID + "=? \n" +
						")";

		return new Query(ctx, I_C_OLCand.Table_Name, wc, trxName) //
				.setParameters(term.getC_Flatrate_Term_ID())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_OLCand.COLUMNNAME_C_OLCand_ID)
				.list(clazz);
	}

	@Override
	public List<I_C_SubscriptionProgress> retrieveSubscriptionProgress(final I_C_Flatrate_Term term)
	{
		Check.assumeNotNull(term, "Param 'term' not null");

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_SubscriptionProgress.class, term)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMNNAME_C_Flatrate_Term_ID, term.getC_Flatrate_Term_ID())
				.orderBy().addColumn(I_C_SubscriptionProgress.COLUMNNAME_SeqNo).endOrderBy()
				.create()
				.list(I_C_SubscriptionProgress.class);
	}

	@Override
	public List<I_C_SubscriptionProgress> retrieveNextSPs(
			final I_C_Flatrate_Term term,
			final Timestamp date)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final String where =
				I_C_SubscriptionProgress.COLUMNNAME_C_Flatrate_Term_ID + "=? AND " +
						I_C_SubscriptionProgress.COLUMNNAME_EventDate + ">=?";

		return new Query(ctx, I_C_SubscriptionProgress.Table_Name, where, trxName)
				.setParameters(term.getC_Flatrate_Term_ID(), date)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_SubscriptionProgress.COLUMNNAME_SeqNo)
				.list(I_C_SubscriptionProgress.class);
	}
}
