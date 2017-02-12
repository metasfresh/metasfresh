package org.adempiere.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.MInOut;
import org.compiere.model.MOrder;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import de.metas.adempiere.model.I_OrderOrInOut;
import de.metas.adempiere.service.impl.OrderBL;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Versandkostenermittlung/_-berechnung_(2009_0027_G28)'>DV-Konzept (2009_0027_G28)</a>"
 */
public class MFreightCost extends X_M_FreightCost
{

	public static final String SQL_FREIGHT_COST = //
	" SELECT c.* " //
			+ " FROM "
			+ Table_Name
			+ " c " //
			+ " LEFT JOIN M_FreightCostShipper s ON s.M_FreightCost_ID=c.M_FreightCost_ID" //
			+ " LEFT JOIN M_FreightCostDetail d ON d.M_FreightCostShipper_ID=s.M_FreightCostShipper_ID" //
			+ " LEFT JOIN C_Location l ON l.C_Country_ID=d.C_Country_ID" //
			+ " LEFT JOIN C_BPartner_Location pl ON pl.C_Location_ID=l.C_Location_ID" //
			+ " LEFT JOIN (" //
			+ " 		select 1 as level, M_FreightCost_ID from C_BPartner where C_BPartner_ID=?" //
			+ " 		union" //
			+ " 		select 2, bpg.M_FreightCost_ID from C_BP_Group bpg, C_BPartner bp where bpg.C_BP_Group_ID=bp.C_BP_Group_ID and bp.C_BPartner_ID=?" //
			+ " 		union" //
			+ " 		select 3, M_FreightCost_ID from M_FreightCost where IsDefault='Y'" // 
			+ " 	) bp ON bp.M_FreightCost_ID=s.M_FreightCost_ID" //
			+ " WHERE" //
			+ " 	bp.M_FreightCost_ID IS NOT NULL" //
			+ " 	AND pl.C_BPartner_Location_ID=?" //
			+ " 	AND s.M_Shipper_ID=?" //
			+ " 	AND s.ValidFrom<=?" //
			+ " 	AND c.AD_Org_ID=?" //
			+ " ORDER BY bp.level, bp.M_FreightCost_ID, d.ShipmentValueAmt";

	/**
	 * 
	 */
	private static final long serialVersionUID = -828372390456233509L;

	public MFreightCost(Properties ctx, int M_FreightCost_ID, String trxName)
	{
		super(ctx, M_FreightCost_ID, trxName);
	}

	public MFreightCost(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Attempts to load the freight costs for a given business partner and location (actually the location's country).
	 * Looks at different freight cost records in this order:
	 * <ul>
	 * <li>Freight cost attached to the bParter</li>
	 * <li>Freight cost attached to the bParter's bPartnerGroup</li>
	 * <li>Default freight cost</li>
	 * </ul>
	 * 
	 * @param bPartnerId
	 * @param countryId
	 * @param trxName
	 * @return the freight cost to be used for the given bPartner and country or <code>null</code> if there is none.
	 * @throws AdempiereException
	 *             if there is no freight cost record for the given inOut
	 */
	public static MFreightCost retrieveFor(final Properties ctx, final I_OrderOrInOut orderOrInOut, final Timestamp date, final String trxName)
	{
		final int cBPartnerID = orderOrInOut.getC_BPartner_ID();
		final int cBPartnerLocationID = orderOrInOut.getC_BPartner_Location_ID();
		final int mShipperID = orderOrInOut.getM_Shipper_ID();
		final String documentNo = orderOrInOut.getDocumentNo();
		final int orgId = orderOrInOut.getAD_Org_ID();

		return retrieveForOrderOrInOut(ctx, orgId, cBPartnerID, cBPartnerLocationID, mShipperID, documentNo, date, trxName);
	}

	public static MFreightCost retrieveFor(final MOrder order)
	{
		return retrieveFor(order.getCtx(), InterfaceWrapperHelper.create(order, I_OrderOrInOut.class), order.getDateOrdered(), order.get_TrxName());
	}

	public static MFreightCost retrieveFor(final MInOut inOut)
	{
		return retrieveFor(inOut.getCtx(), InterfaceWrapperHelper.create(inOut, I_OrderOrInOut.class), inOut.getMovementDate(), inOut.get_TrxName());
	}

	private static MFreightCost retrieveForOrderOrInOut(final Properties ctx,
			final int orgId, final int cBPartnerID, final int cBPartnerLocationID,
			final int mShipperID, final String documentNo,
			final Timestamp dateOrdered, final String trxName)
	{
		if (mShipperID <= 0)
		{
			// TODO -> AD_Message
			throw new AdempiereException("Beleg " + documentNo + " hat keinen Lieferweg (M_Shipper_ID=0)");
		}

		final MFreightCost freightCost = retrieveFor(ctx, cBPartnerID, cBPartnerLocationID, mShipperID, orgId, dateOrdered, trxName);

		if (freightCost == null)
		{
			throw new AdempiereException(Msg.getMsg(ctx, OrderBL.MSG_NO_FREIGHT_COST_DETAIL));
		}
		return freightCost;
	}

	public static MFreightCost retrieveFor(final Properties ctx,
			final int cBPartnerID, final int cBPartnerLocationID,
			final int mShipperID, final int mOrgID, final Timestamp date, final String trxName)
	{

		final PreparedStatement pstmt = DB.prepareStatement(SQL_FREIGHT_COST,
				trxName);

		ResultSet rs = null;
		try
		{
			pstmt.setInt(1, cBPartnerID);
			pstmt.setInt(2, cBPartnerID);
			pstmt.setInt(3, cBPartnerLocationID);
			pstmt.setInt(4, mShipperID);
			pstmt.setTimestamp(5, date);
			pstmt.setInt(6, mOrgID);

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				return new MFreightCost(ctx, rs, trxName);
			}
			return null;
		}
		catch (SQLException e)
		{
			throw new DBException(e, SQL_FREIGHT_COST);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

	}

	@Override
	public String toString()
	{

		final StringBuffer sb = new StringBuffer("MFreightCost[") //
				.append(get_ID()) //
				.append(", Name=").append(getName()) //
				.append(", (expense-) M_Product_ID=").append(getM_Product_ID()) //
				.append("]");
		return sb.toString();
	}

	@Cached(cacheName = MFreightCost.Table_Name)
	public static Collection<MFreightCost> retriveFor(
			final @CacheCtx Properties ctx,
			final int productId,
			final @CacheTrx String trxName)
	{
		final String wc = COLUMNNAME_M_Product_ID + "=?";

		return new Query(ctx, Table_Name, wc, trxName)
				.setParameters(productId)
				.setApplyAccessFilter(true)
				.setOnlyActiveRecords(true)
				.list();
	}
}
