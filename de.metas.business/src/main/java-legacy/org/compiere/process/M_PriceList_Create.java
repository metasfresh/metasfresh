/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 * Contributor(s): Layda Salas (globalqss)
 * Contributor(s): Carlos Ruiz (globalqss)
 *****************************************************************************/

package org.compiere.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.session.ISessionBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.pricing.api.IPriceListBL;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product_PO;
import org.compiere.model.MDiscountSchemaLine;
import org.compiere.model.X_M_DiscountSchemaLine;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.ValueNamePair;

import de.metas.adempiere.model.I_M_ProductPrice;
import de.metas.logging.MetasfreshLastError;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 * Create PriceList by copying purchase prices (M_Product_PO) and applying product category discounts (M_CategoryDiscount)
 *
 * @author Layda Salas (globalqss)
 * @version $Id: M_PriceList_Create,v 1.0 2005/10/09 22:19:00 globalqss Exp $
 * @author Carlos Ruiz (globalqss) Make T_Selection tables permanent
 * @author t.schoneberg@metas.de Extracted the actual price creation code in order to be able to hook in
 */
public class M_PriceList_Create extends SvrProcess
{
	// Services
	private final transient IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final transient ISessionBL sessionBL = Services.get(ISessionBL.class);

	/**
	 * Target Price List Version (where we will create the prices)
	 */
	private int p_PriceList_Version_ID = -1;
	private I_M_PriceList_Version p_PriceList_Version = null;

	private String p_DeleteOld;

	private int m_AD_PInstance_ID = -1;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("DeleteOld"))
				p_DeleteOld = (String)para[i].getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}
		p_PriceList_Version_ID = getRecord_ID();
		m_AD_PInstance_ID = getAD_PInstance_ID();
	} // *prepare*/

	/**
	 * Process
	 *
	 * @return message
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		// Disabling change log creation because we will create and then update a huge amount of M_ProductPrice_Attribute[_Line] records.
		// To avoid this huge performance issue we are disabling for this thread (08125)
		sessionBL.setDisableChangeLogsForThread(true);

		String sql;

		String sqldel;

		int cntd = 0;

		int totu = 0;

		int totd = 0;

		int v_NextNo = 0;

		totu = prepareProductPO(totu);

		//
		// Delete Old Data
		//
		if (p_DeleteOld.equals("Y"))
		{
			sqldel = "DELETE FROM M_ProductPrice "
					+ " WHERE	M_PriceList_Version_ID = " + p_PriceList_Version_ID
					// 05832: Delete only those which are not fixed
					+ " AND " + I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice + "='N'";
			;
			cntd = DB.executeUpdateEx(sqldel, get_TrxName());
			if (cntd == -1)
				raiseError(" DELETE	M_ProductPrice ", sqldel);
			totd += cntd;
			final String Message = "@Deleted@=" + cntd + " - ";
			log.debug("Deleted " + cntd);
			addLog(Message);
		}
		//
		// Get PriceList Info
		//
		sql = "SELECT p.C_Currency_ID " + " , c.StdPrecision "
				+ " , v.AD_Client_ID " + " , v.AD_Org_ID " + " , v.UpdatedBy "
				+ " , v.M_DiscountSchema_ID "
				+ " , M_PriceList_Version_Base_ID "
				+ " FROM	M_PriceList p "
				+ "     ,M_PriceList_Version v "
				+ "     ,C_Currency c "
				+ " WHERE	p.M_PriceList_ID = v.M_PriceList_ID "
				+ " AND	    p.C_Currency_ID  = c.C_Currency_ID"
				+ " AND	v.M_PriceList_Version_ID = " + p_PriceList_Version_ID;

		PreparedStatement curgen = null;
		ResultSet v = null;

		try
		{
			curgen = DB.prepareStatement(sql, get_TrxName());
			v = curgen.executeQuery();
			while (v.next())
			{
				//
				// For All Discount Lines in Sequence
				//

				sql = "SELECT "
						+ " * "
// @formatter:off
//					+ "	   m_discountschemaline_id"
//					+ "         ,ad_client_id" + "         ,ad_org_id"
//					+ "         ,isactive" + "         ,created"
//					+ "         ,createdby" + "         ,updated"
//					+ "         ,updatedby" + "         ,m_discountschema_id"
//					+ "         ,seqno" + "         ,m_product_category_id"
//					+ "         ,c_bpartner_id" + "         ,m_product_id"
//					+ "         ,conversiondate" + "         ,list_base  "
//					+ "         ,list_addamt" + "         ,list_discount"
//					+ "         ,list_rounding" + "         ,list_minamt"
//					+ "         ,list_maxamt" + "         ,list_fixed"
//					+ "         ,std_base" + "         ,std_addamt"
//					+ "         ,std_discount" + "         ,std_rounding"
//					+ "         ,std_minamt" + "         ,std_maxamt"
//					+ "         ,std_fixed" + "         ,limit_base"
//					+ "         ,limit_addamt" + "         ,limit_discount"
//					+ "         ,limit_rounding" + "         ,limit_minamt"
//					+ "         ,limit_maxamt" + "         ,limit_fixed"
//					+ "         ,c_conversiontype_id"
// @formatter:on
						+ " FROM	M_DiscountSchemaLine"
						+ " WHERE	M_DiscountSchema_ID=" + v.getInt("M_DiscountSchema_ID")
						+ " AND IsActive='Y'"
						+ " ORDER BY SeqNo";

				PreparedStatement Cur_DiscountLine = null;
				ResultSet dl = null;
				try
				{
					Cur_DiscountLine = DB.prepareStatement(sql, get_TrxName());
					dl = Cur_DiscountLine.executeQuery();
					while (dl.next())
					{
						processDl(v, dl, v_NextNo);

						final int dslId = dl.getInt(1);
						final MDiscountSchemaLine dsl = new MDiscountSchemaLine(getCtx(), dslId, get_TrxName());
						Services.get(IPriceListBL.class).finishPlvCreation(
								new PlainContextAware(getCtx(), getTrxName()),
								retrieveSourceProductPrices(),
								getTargetPriceListVersion(),
								dsl,
								getAD_PInstance_ID());
					}
				}
				finally
				{
					DB.close(dl, Cur_DiscountLine);
				}

				// task 09458
				// 1: don't delete *all* T_Selections! If anything, delete your own
				// 2: don't delete *any* T_Selection, because that's a task for the generic housekeeping framework, and not for a particula business logic
				// @formatter:off
				//
				// Delete Temporary Selection
				//
//				sqldel = "DELETE FROM T_Selection ";
//				cntd = DB.executeUpdateEx(sqldel, get_TrxName());
//				if (cntd == -1)
//					raiseError(" DELETE	T_Selection ", sqldel);
//				totd += cntd;
//				log.debug("Deleted " + cntd);
				// @formatter:on

				//
				// commit;
				//
				// log.debug("Committing ...");
				// DB.commit(true, get_TrxName());

			}
		}
		finally
		{
			DB.close(v, curgen);
			sessionBL.setDisableChangeLogsForThread(false);
		}

		return "OK";

	} // del doIt

	private int prepareProductPO(int totu) throws Exception, SQLException
	{
		//
		// Checking Prerequisites
		// PO Prices must exists
		//
		String sql;
		String sqlupd;
		int cntu;
		sqlupd = "UPDATE M_Product_PO " + " SET	PriceList = 0  "
				+ " WHERE	PriceList IS NULL ";

		cntu = DB.executeUpdateEx(sqlupd, get_TrxName());
		if (cntu == -1)
			raiseError(
					"Update The PriceList to zero of M_Product_PO WHERE	PriceList IS NULL",
					sqlupd);
		totu += cntu;
		log.debug("Updated " + cntu);

		sqlupd = "UPDATE M_Product_PO " + " SET	PriceLastPO = 0  "
				+ " WHERE	PriceLastPO IS NULL ";

		cntu = DB.executeUpdateEx(sqlupd, get_TrxName());
		if (cntu == -1)
			raiseError(
					"Update  The PriceListPO to zero of  M_Product_PO WHERE	PriceLastPO IS NULL",
					sqlupd);
		totu += cntu;
		log.debug("Updated " + cntu);

		sqlupd = "UPDATE M_Product_PO "
				+ " SET	    PricePO = PriceLastPO  "
				+ " WHERE	(PricePO IS NULL OR PricePO = 0) AND PriceLastPO <> 0 ";

		cntu = DB.executeUpdateEx(sqlupd, get_TrxName());
		if (cntu == -1)
			raiseError(
					"Update  The PricePO to PriceLastPO of  M_Product_PO WHERE	(PricePO IS NULL OR PricePO = 0) AND PriceLastPO <> 0 ",
					sqlupd);
		totu += cntu;
		log.debug("Updated " + cntu);

		sqlupd = "UPDATE M_Product_PO " + " SET	    PricePO = 0  "
				+ " WHERE	PricePO IS NULL ";

		cntu = DB.executeUpdateEx(sqlupd, get_TrxName());
		if (cntu == -1)
			raiseError(
					"Update  The PricePO to Zero of  M_Product_PO WHERE	PricePO IS NULL",
					sqlupd);
		totu += cntu;
		log.debug("Updated " + cntu);
		//
		// Set default current vendor
		//
		sqlupd = "UPDATE M_Product_PO " + " SET	     IsCurrentVendor = 'Y'  "
				+ " WHERE	 IsCurrentVendor = 'N' " + " AND NOT   EXISTS "
				+ " (SELECT   pp.M_Product_ID " + "  FROM     M_Product_PO pp "
				+ "  WHERE    pp.M_Product_ID = M_Product_PO.M_Product_ID"
				+ "  GROUP BY pp.M_Product_ID HAVING COUNT(*) > 1) ";

		cntu = DB.executeUpdateEx(sqlupd, get_TrxName());
		if (cntu == -1)
			raiseError("Update  IsCurrentVendor to Y of  M_Product_PO ", sqlupd);
		totu += cntu;
		log.debug("Updated " + cntu);

		// let the commit for SvrProcess
		// DB.commit(true, get_TrxName());

		//
		// Make sure that we have only one active product
		//
		sql = "SELECT DISTINCT M_Product_ID FROM M_Product_PO po "
				+ " WHERE	 IsCurrentVendor='Y' AND IsActive='Y' "
				+ "   AND    EXISTS (SELECT   M_Product_ID "
				+ " FROM     M_Product_PO x  "
				+ " WHERE    x.M_Product_ID=po.M_Product_ID "
				+ "   AND    IsCurrentVendor='Y' AND IsActive='Y' "
				+ " GROUP BY M_Product_ID " + " HAVING COUNT(*) > 1 ) ";

		PreparedStatement Cur_Duplicates = null;
		Cur_Duplicates = DB.prepareStatement(sql, get_TrxName());
		ResultSet dupl = Cur_Duplicates.executeQuery();
		while (dupl.next())
		{
			sql = "SELECT	M_Product_ID " + "        ,C_BPartner_ID "
					+ " FROM	M_Product_PO " + " WHERE	IsCurrentVendor = 'Y'  "
					+ " AND     IsActive        = 'Y' "
					+ " AND	M_Product_ID    = " + dupl.getInt("M_Product_ID")
					+ " ORDER BY PriceList DESC";

			PreparedStatement Cur_Vendors = null;
			Cur_Vendors = DB.prepareStatement(sql, get_TrxName());
			ResultSet Vend = Cur_Vendors.executeQuery();

			//
			// Leave First
			//
			Vend.next();

			while (Vend.next())
			{
				sqlupd = "UPDATE M_Product_PO "
						+ " SET	IsCurrentVendor = 'N'  "
						+ " WHERE	M_Product_ID= " + Vend.getInt("M_Product_ID")
						+ " AND     C_BPartner_ID= "
						+ Vend.getInt("C_BPartner_ID");

				cntu = DB.executeUpdateEx(sqlupd, get_TrxName());
				if (cntu == -1)
					raiseError(
							"Update  IsCurrentVendor to N of  M_Product_PO for a M_Product_ID and C_BPartner_ID ingresed",
							sqlupd);
				totu += cntu;
				log.debug("Updated " + cntu);

			}
			Vend.close();
			Cur_Vendors.close();
			Cur_Vendors = null;

		}
		dupl.close();
		Cur_Duplicates.close();
		Cur_Duplicates = null;

		// DB.commit(true, get_TrxName());
		return totu;
	}

	/**
	 * Create/Update Product Prices for current M_DiscountSchemaLine.
	 *
	 * @param v result set containing current selected Price List and Price List Version columns
	 * @param dl result set containing current M_DiscountSchemaLine record
	 * @param v_NextNo
	 * @return
	 * @throws Exception
	 */
	private int processDl(final ResultSet v, final ResultSet dl, final int v_NextNo) throws Exception
	{

		int cntu = 0;
		int cnti = 0;
		int cntd = 0;

		int countInserted = 0;
		int countUpdated = 0;
		int countDeleted = 0;

		String Message = "";

		// 08115: at least for new additions, let's use a real instance, not the result set
		final I_M_DiscountSchemaLine dsl = InterfaceWrapperHelper.create(new X_M_DiscountSchemaLine(getCtx(), dl, getTrxName()), I_M_DiscountSchemaLine.class);

		//
		// Clear Temporary Table
		//
		{
			final String sqldel = "DELETE FROM T_Selection WHERE AD_PInstance_ID=" + m_AD_PInstance_ID;
			cntd = DB.executeUpdateEx(sqldel, get_TrxName());
			if (cntd == -1)
				raiseError(" DELETE	T_Selection ", sqldel);
			countDeleted += cntd;
			log.debug("Deleted " + cntd);
		}

		//
		// Create Selection in temporary table
		int V_temp = v.getInt("M_PriceList_Version_Base_ID");
		if (v.wasNull())
		{
			//
			// Create Selection from M_Product_PO
			//
			final String sqlins = "INSERT INTO T_Selection (AD_PInstance_ID, T_Selection_ID) "
					+ " SELECT DISTINCT " + m_AD_PInstance_ID + ", po.M_Product_ID "
					+ " FROM	M_Product p, M_Product_PO po"
					+ " WHERE	p.M_Product_ID=po.M_Product_ID "
					+ " AND	(p.AD_Client_ID=" + v.getInt("AD_Client_ID") + " OR p.AD_Client_ID=0)"
					+ " AND	p.IsActive='Y' AND po.IsActive='Y' AND po.IsCurrentVendor='Y' "
					//
					// Optional Restrictions
					//
					// globalqss - detected bug, JDBC returns zero for null values
					// so we're going to use NULLIF(value, 0)
					+ " AND (NULLIF(" + dl.getInt("M_Product_Category_ID") + ",0) IS NULL OR p.M_Product_Category_ID IN (" + getSubCategoryWhereClause(dl.getInt("M_Product_Category_ID")) + "))"
					+ " AND (NULLIF(" + dl.getInt("C_BPartner_ID") + ",0) IS NULL OR po.C_BPartner_ID=" + dl.getInt("C_BPartner_ID") + ")"
					+ " AND (NULLIF(" + dl.getInt("M_Product_ID") + ",0) IS NULL OR p.M_Product_ID=" + dl.getInt("M_Product_ID") + ")"
					+ " AND ";
			cnti = DB.executeUpdateEx(sqlins, get_TrxName());
			if (cnti == -1)
				raiseError(" INSERT INTO T_Selection ", sqlins);
			countInserted += cnti;
			log.debug("Inserted " + cnti);

		}
		else
		{
			//
			// Create Selection from existing PriceList Version
			//
			final String sqlins = "INSERT INTO T_Selection (AD_PInstance_ID, T_Selection_ID)"
					+ " SELECT	DISTINCT " + m_AD_PInstance_ID + ", p.M_Product_ID"
					+ " FROM	M_Product p, M_ProductPrice pp"
					+ " WHERE	p.M_Product_ID=pp.M_Product_ID"
					+ " AND	pp.M_PriceList_Version_ID = " + v.getInt("M_PriceList_Version_Base_ID")

					// task 08115: add the dsl's tax category (if set!) as another filter criterion
					+ " AND	(" + dsl.getC_TaxCategory_ID() + "=0 OR pp." + I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID
					+ " = "
					+ dsl.getC_TaxCategory_ID()
					+ ")"
					// + " AND	p.IsActive='Y' AND pp.IsActive='Y'" // copy also inactive prices (07102)
					//
					// Optional Restrictions
					//
					+ " AND (NULLIF(" + dl.getInt("M_Product_Category_ID") + ",0) IS NULL OR p.M_Product_Category_ID IN (" + getSubCategoryWhereClause(dl.getInt("M_Product_Category_ID")) + "))"
					+ " AND	(NULLIF(" + dl.getInt("C_BPartner_ID") + ",0) IS NULL OR EXISTS (SELECT 1 FROM M_Product_PO po WHERE po.M_Product_ID=p.M_Product_ID AND po.C_BPartner_ID="
					+ dl.getInt("C_BPartner_ID") + "))"
					+ " AND	(NULLIF(" + dl.getInt("M_Product_ID") + ",0) IS NULL OR p.M_Product_ID=" + dl.getInt("M_Product_ID") + ")";
			cnti = DB.executeUpdateEx(sqlins, get_TrxName());
			if (cnti < 0)
			{
				raiseError(" INSERT INTO T_Selection from existing PriceList", sqlins);
			}
			countInserted += cnti;
			log.debug("Inserted " + cnti);

		}

		Message = Message + "@Selected@=" + cnti;

		//
		// Delete Prices in Selection, so that we can insert
		//
		V_temp = v.getInt("M_PriceList_Version_Base_ID");
		if (v.wasNull() || V_temp != p_PriceList_Version_ID)
		{

			final String sqldel = "DELETE FROM M_ProductPrice pp"
					+ " WHERE pp.M_PriceList_Version_ID = " + p_PriceList_Version_ID
					+ " AND EXISTS (SELECT 1 FROM T_Selection s WHERE pp.M_Product_ID=s.T_Selection_ID AND s.AD_PInstance_ID=" + m_AD_PInstance_ID + ")";

			cntd = DB.executeUpdateEx(sqldel, get_TrxName());
			if (cntd == -1)
				raiseError(" DELETE	M_ProductPrice ", sqldel);
			countDeleted += cntd;
			Message = Message + ", @Deleted@=" + cntd;
			log.debug("Deleted " + cntd);
		}

		//
		// Copy (Insert) Prices
		//
		V_temp = v.getInt("M_PriceList_Version_Base_ID");
		if (V_temp == p_PriceList_Version_ID)
		{
			//
			// We have Prices already
			//
			;
		}
		else if (v.wasNull())
		//
		// Copy and Convert from Product_PO
		//
		{
			final String sqlins = "INSERT INTO M_ProductPrice "
					+ "(M_PriceList_Version_ID"
					+ " ,M_Product_ID "
					+ " ,AD_Client_ID"
					+ " , AD_Org_ID"
					+ " , IsActive"
					+ " , Created"
					+ " , CreatedBy"
					+ " , Updated"
					+ " , UpdatedBy"
					+ " , PriceList"
					+ " , PriceStd"
					+ " , PriceLimit) "
					+ "SELECT "
					+ p_PriceList_Version_ID
					+ "      ,po.M_Product_ID "
					+ "      ,"
					+ v.getInt("AD_Client_ID")
					+ "      ,"
					+ v.getInt("AD_Org_ID")
					+ "      ,'Y'"
					+ "      ,now(),"
					+ v.getInt("UpdatedBy")
					+ "      ,now(),"
					+ v.getInt("UpdatedBy")
					//
					// Price List
					+ " ,COALESCE(currencyConvert(po.PriceList, po.C_Currency_ID, "
					+ v.getInt("C_Currency_ID")
					+ ",  ? , "
					+ dl.getInt("C_ConversionType_ID")
					+ ", "
					+ v.getInt("AD_Client_ID")
					+ ", "
					+ v.getInt("AD_Org_ID")
					+ "),0)"
					//
					// Price Std
					+ " ,COALESCE(currencyConvert(po.PriceList, po.C_Currency_ID, "
					+ v.getInt("C_Currency_ID")
					+ ", ? , "
					+ dl.getInt("C_ConversionType_ID")
					+ ", "
					+ v.getInt("AD_Client_ID")
					+ ", "
					+ v.getInt("AD_Org_ID")
					+ "),0)"
					//
					// Price Limit
					+ " ,COALESCE(currencyConvert(po.PricePO ,po.C_Currency_ID, "
					+ v.getInt("C_Currency_ID")
					+ ",? , "
					+ dl.getInt("C_ConversionType_ID")
					+ ", "
					+ v.getInt("AD_Client_ID")
					+ ", "
					+ v.getInt("AD_Org_ID")
					+ "),0)"
					//
					+ " FROM " + I_M_Product_PO.Table_Name + " po "
					+ " WHERE EXISTS (SELECT 1 FROM T_Selection s WHERE po.M_Product_ID=s.T_Selection_ID" + " AND s.AD_PInstance_ID=" + m_AD_PInstance_ID + ") "
					+ " AND	po.IsCurrentVendor='Y' AND po.IsActive='Y'";

			PreparedStatement pstmt = DB.prepareStatement(sqlins,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE, get_TrxName());
			pstmt.setTimestamp(1, dl.getTimestamp("ConversionDate"));
			pstmt.setTimestamp(2, dl.getTimestamp("ConversionDate"));
			pstmt.setTimestamp(3, dl.getTimestamp("ConversionDate"));

			cnti = pstmt.executeUpdate();
			if (cnti == -1)
				raiseError(" INSERT INTO T_Selection from existing PriceList", sqlins);
			countInserted += cnti;
			log.debug("Inserted " + cnti);
		}
		//
		// Copy and Currency Convert from other PriceList_Version
		//
		else
		{
			final String sqlins = "INSERT INTO M_ProductPrice "
					+ " (M_PriceList_Version_ID, M_Product_ID,"
					+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, SeqNo, C_UOM_ID, "
					+ " PriceList, PriceStd, PriceLimit"
					+ ", " + I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice
					+ ")"
					//
					+ " SELECT "
					+ p_PriceList_Version_ID
					+ ", pp.M_Product_ID"
					+ ", " + v.getInt("AD_Client_ID") // AD_Client_ID
					+ ", " + v.getInt("AD_Org_ID") // AD_Org_ID
					+ ", pp.IsActive" // IsActive
					+ ", now()" // Created
					+ ", " + v.getInt("UpdatedBy") // CreatedBy
					+ ", now()" // Updated
					+ ", " + v.getInt("UpdatedBy") // UpdatedBy
					+ ", pp.SeqNo" // SeqNo
					+ ", pp.C_UOM_ID" // C_UOM_ID
					// Price List
					+ ", COALESCE(currencyConvert(pp.PriceList, pl.C_Currency_ID, "
					+ v.getInt("C_Currency_ID")
					+ ", ?, "
					+ dl.getInt("C_ConversionType_ID")
					+ ", "
					+ v.getInt("AD_Client_ID")
					+ ", "
					+ v.getInt("AD_Org_ID")
					+ "),0)"
					// Price Std
					+ ", COALESCE(currencyConvert(pp.PriceStd,pl.C_Currency_ID, "
					+ v.getInt("C_Currency_ID")
					+ " , ? ,  "
					+ dl.getInt("C_ConversionType_ID")
					+ ", "
					+ v.getInt("AD_Client_ID")
					+ ", "
					+ v.getInt("AD_Org_ID")
					+ "),0)"
					// Price Limit
					+ ", COALESCE(currencyConvert(pp.PriceLimit,pl.C_Currency_ID, "
					+ v.getInt("C_Currency_ID")
					+ " , ? , "
					+ dl.getInt("C_ConversionType_ID")
					+ ", "
					+ v.getInt("AD_Client_ID")
					+ ", "
					+ v.getInt("AD_Org_ID")
					+ "),0)"
					//
					+ ", pp." + I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice
					//
					+ " FROM M_ProductPrice pp"
					+ " INNER JOIN M_PriceList_Version plv ON (pp.M_PriceList_Version_ID=plv.M_PriceList_Version_ID)"
					+ " INNER JOIN M_PriceList pl ON (plv.M_PriceList_ID=pl.M_PriceList_ID)"
					+ " WHERE pp.M_PriceList_Version_ID=" + v.getInt("M_PriceList_Version_Base_ID")
					+ " AND EXISTS (SELECT 1 FROM T_Selection s WHERE pp.M_Product_ID=s.T_Selection_ID AND s.AD_PInstance_ID=" + m_AD_PInstance_ID + ")"
			// + " AND	pp.IsActive='Y'" // also copy the inactive prices (07102)
			;

			PreparedStatement pstmt = DB.prepareStatement(sqlins,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE, get_TrxName());
			pstmt.setTimestamp(1, dl.getTimestamp("ConversionDate"));
			pstmt.setTimestamp(2, dl.getTimestamp("ConversionDate"));
			pstmt.setTimestamp(3, dl.getTimestamp("ConversionDate"));

			cnti = pstmt.executeUpdate();
			if (cnti == -1)
				raiseError(" INSERT INTO T_Selection from existing PriceList", sqlins);
			countInserted += cnti;
			log.debug("Inserted " + cnti);

		}
		Message = Message + ", @Inserted@=" + cnti;

		//
		// Calculation
		//
		{
			String sqlupd = "UPDATE M_ProductPrice p "
					+ " SET	" // task 06853: translated DECODE to CASE
					// task 08136, additional requirement: if a product price is zero in the base/source PLV, then it shall remain zero, even if an *_AddAmt was specified
					+ " PriceList = (CASE '" + dl.getString("List_Base") + "' WHEN 'S' THEN PriceStd WHEN 'X' THEN PriceLimit ELSE PriceList END"
					+ " + CASE WHEN PriceList=0 THEN 0 ELSE ? END) * (1 - ?/100),"
					+ " PriceStd = (CASE '" + dl.getString("Std_Base") + "' WHEN 'L' THEN PriceList WHEN 'X' THEN PriceLimit ELSE PriceStd END "
					+ " + CASE WHEN PriceStd=0 THEN 0 ELSE ? END) * (1 - ?/100), "
					+ " PriceLimit = (CASE '" + dl.getString("Limit_Base") + "' WHEN 'L' THEN PriceList WHEN 'S' THEN PriceStd ELSE PriceLimit END "
					+ " + CASE WHEN PriceLimit=0 THEN 0 ELSE ? END) * (1 - ? /100) "

					+ " WHERE	M_PriceList_Version_ID = " + p_PriceList_Version_ID
					+ " AND EXISTS	(SELECT 1 FROM T_Selection s WHERE s.T_Selection_ID = p.M_Product_ID AND s.AD_PInstance_ID=" + m_AD_PInstance_ID + ")"
					// Don't touch those product prices which have season fixed price set (07082)
					+ " AND p." + I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice + "='N' ";
			PreparedStatement pstmu = DB.prepareStatement(sqlupd,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE, get_TrxName());

			pstmu.setDouble(1, dl.getDouble("List_AddAmt"));
			pstmu.setDouble(2, dl.getDouble("List_Discount"));
			pstmu.setDouble(3, dl.getDouble("Std_AddAmt"));
			pstmu.setDouble(4, dl.getDouble("Std_Discount"));
			pstmu.setDouble(5, dl.getDouble("Limit_AddAmt"));
			pstmu.setDouble(6, dl.getDouble("Limit_Discount"));

			cntu = pstmu.executeUpdate();

			if (cntu == -1)
				raiseError("Update  M_ProductPrice ", sqlupd);
			countUpdated += cntu;
			log.debug("Updated " + cntu);
		}

		//
		// Rounding (AD_Reference_ID=155)
		//
		{
			final String sqlupd = "UPDATE	M_ProductPrice p "
					+ " SET	" // task 06853: translated DECODE to CASE
					+ "PriceList = CASE '" + dl.getString("List_Rounding") + "' "
					+ " WHEN 'N' THEN PriceList "
					+ " WHEN '0' THEN ROUND(PriceList, 0)" // Even .00
					+ " WHEN 'D' THEN ROUND(PriceList, 1)" // Dime .10
					+ " WHEN 'T' THEN ROUND(PriceList, -1) " // Ten 10.00
					+ " WHEN '5' THEN ROUND(PriceList*20,0)/20" // Nickle .05
					+ " WHEN 'Q' THEN ROUND(PriceList*4,0)/4" // Quarter .25
					+ " WHEN '9' THEN CASE"  // Whole 9 or 5
					+ "    WHEN MOD(ROUND(PriceList),10)<=5 THEN ROUND(PriceList)+(5-MOD(ROUND(PriceList),10))"
					+ "    WHEN MOD(ROUND(PriceList),10)>5 THEN ROUND(PriceList)+(9-MOD(ROUND(PriceList),10)) END"
					+ " ELSE ROUND(PriceList, " + v.getInt("StdPrecision") + ")"
					+ " END,"

					+ " PriceStd = CASE '" + dl.getString("Std_Rounding") + "'"
					+ "  WHEN 'N' THEN PriceStd "
					+ "  WHEN '0' THEN ROUND(PriceStd, 0) " // Even .00
					+ "  WHEN 'D' THEN ROUND(PriceStd, 1) " // Dime .10
					+ "  WHEN 'T' THEN ROUND(PriceStd, -1)" // Ten 10.00
					+ "  WHEN '5' THEN ROUND(PriceStd*20,0)/20" // Nickle .05
					+ "  WHEN 'Q' THEN ROUND(PriceStd*4,0)/4" // Quarter .25
					+ "  WHEN '9' THEN CASE"  // Whole 9 or 5
					+ "   WHEN MOD(ROUND(PriceStd),10)<=5 THEN ROUND(PriceStd)+(5-MOD(ROUND(PriceStd),10))"
					+ "   WHEN MOD(ROUND(PriceStd),10)>5 THEN ROUND(PriceStd)+(9-MOD(ROUND(PriceStd),10)) "
					+ "   END"
					+ "  ELSE ROUND(PriceStd, " + v.getInt("StdPrecision") + ")"
					+ "  END,"

					+ "PriceLimit = CASE '" + dl.getString("Limit_Rounding") + "' "
					+ "		WHEN 'N' THEN PriceLimit "
					+ "		WHEN '0' THEN ROUND(PriceLimit, 0)	" // Even .00
					+ "		WHEN 'D' THEN ROUND(PriceLimit, 1)	" // Dime .10
					+ "		WHEN 'T' THEN ROUND(PriceLimit, -1)	" // Ten 10.00
					+ "		WHEN '5' THEN ROUND(PriceLimit*20,0)/20	" // Nickle .05
					+ "		WHEN 'Q' THEN ROUND(PriceLimit*4,0)/4		" // Quarter .25
					+ "     WHEN '9' THEN CASE"  // Whole 9 or 5
					+ "       WHEN MOD(ROUND(PriceLimit),10)<=5 THEN ROUND(PriceLimit)+(5-MOD(ROUND(PriceLimit),10))"
					+ "       WHEN MOD(ROUND(PriceLimit),10)>5 THEN ROUND(PriceLimit)+(9-MOD(ROUND(PriceLimit),10)) END"
					+ "		ELSE ROUND(PriceLimit, " + v.getInt("StdPrecision") + ") " // Currency
					+ "     END"
					+ " WHERE	M_PriceList_Version_ID="
					+ p_PriceList_Version_ID
					+ " AND EXISTS	(SELECT * FROM T_Selection s "
					+ " WHERE s.T_Selection_ID=p.M_Product_ID"
					+ " AND s.AD_PInstance_ID=" + m_AD_PInstance_ID + ")"
					// Don't touch those product prices which have season fixed price set (07082)
					+ " AND p." + I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice + "='N' ";
			cntu = DB.executeUpdate(sqlupd, get_TrxName());
			if (cntu == -1)
				raiseError("Update  M_ProductPrice ", sqlupd);
			countUpdated += cntu;
			log.debug("Updated " + cntu);

			Message = Message + ", @Updated@=" + cntu;
		}

		//
		// Fixed Price overwrite
		//
		{
			final String sqlupd = "UPDATE	M_ProductPrice p "
					+ " SET	" // task 06853: translated DECODE to CASE
					+ "      PriceList  = CASE '" + dl.getString("List_Base") + "' WHEN 'F' THEN " + dl.getDouble("List_Fixed") + " ELSE PriceList END, "
					+ "      PriceStd   = CASE '" + dl.getString("Std_Base") + "' WHEN 'F' THEN " + dl.getDouble("Std_Fixed") + " ELSE PriceStd END,"
					+ "      PriceLimit = CASE '" + dl.getString("Limit_Base") + "' WHEN 'F' THEN " + dl.getDouble("Limit_Fixed") + " ELSE PriceLimit END"
					+ " WHERE	M_PriceList_Version_ID=" + p_PriceList_Version_ID
					+ " AND EXISTS	(SELECT 1 FROM T_Selection s WHERE s.T_Selection_ID=p.M_Product_ID AND s.AD_PInstance_ID=" + m_AD_PInstance_ID + ")"
					// Don't touch those product prices which have season fixed price set (07082)
					+ " AND p." + I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice + "='N' ";
			cntu = DB.executeUpdate(sqlupd, get_TrxName());
			if (cntu == -1)
				raiseError("Update  M_ProductPrice ", sqlupd);
			countUpdated += cntu;
			log.debug("Updated " + cntu);
		}

		addLog(Message);

		return v_NextNo + 1;
	}

	private void raiseError(String string, String sql) throws Exception
	{

		// DB.rollback(false, get_TrxName());
		String msg = string;
		ValueNamePair pp = MetasfreshLastError.retrieveError();
		if (pp != null)
			msg = pp.getName() + " - ";
		msg += sql;
		throw new AdempiereUserError(msg);
	}

	/**
	 * Returns a sql where string with the given category id and all of its subcategory ids. It is used as restriction in MQuery.
	 *
	 * @param productCategoryId
	 * @return
	 */
	private String getSubCategoryWhereClause(int productCategoryId) throws SQLException, AdempiereSystemError
	{
		// if a node with this id is found later in the search we have a loop in the tree
		int subTreeRootParentId = 0;
		String retString = " ";
		String sql = " SELECT M_Product_Category_ID, M_Product_Category_Parent_ID FROM M_Product_Category";
		final Vector<SimpleTreeNode> categories = new Vector<SimpleTreeNode>(100);
		Statement stmt = DB.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			if (rs.getInt(1) == productCategoryId)
			{
				subTreeRootParentId = rs.getInt(2);
			}
			categories.add(new SimpleTreeNode(rs.getInt(1), rs.getInt(2)));
		}
		retString += getSubCategoriesString(productCategoryId, categories, subTreeRootParentId);
		rs.close();
		stmt.close();
		return retString;
	}

	public Iterator<I_M_ProductPrice> retrieveSourceProductPrices()
	{
		final I_M_PriceList_Version sourcePriceListVersion = getSourcePriceListVersion();

		final IQuery<I_M_ProductPrice> query = priceListDAO.retrieveProductPricesQuery(sourcePriceListVersion)
				// Only those M_ProducePrice records where M_ProductPrice.M_Product_ID is in our selection
				.filter(priceListDAO.createProductPriceQueryFilterForProductInSelection(m_AD_PInstance_ID))
				.create();

		if (log.isInfoEnabled())
		{
			log.info("Retrieving Source ProductPrices from T_Selection: {}", query);
			log.info("Found {} record(s)", query.count());
		}

		return query.iterate(I_M_ProductPrice.class);
	}

	public I_M_PriceList_Version getSourcePriceListVersion()
	{
		final I_M_PriceList_Version targetPriceListVersion = getTargetPriceListVersion();
		final I_M_PriceList_Version sourcePriceListVersion = targetPriceListVersion.getM_Pricelist_Version_Base();
		return sourcePriceListVersion;
	}

	public I_M_PriceList_Version getTargetPriceListVersion()
	{
		if (p_PriceList_Version == null)
		{
			p_PriceList_Version = InterfaceWrapperHelper.create(getCtx(), p_PriceList_Version_ID, I_M_PriceList_Version.class, getTrxName());
			Check.assumeNotNull(p_PriceList_Version, "price list version not null");
		}
		return p_PriceList_Version;
	}

	/**
	 * Recursive search for subcategories with loop detection.
	 *
	 * @param productCategoryId
	 * @param categories
	 * @param loopIndicatorId
	 * @return comma seperated list of category ids
	 * @throws AdempiereSystemError if a loop is detected
	 */
	private String getSubCategoriesString(int productCategoryId, Vector<SimpleTreeNode> categories, int loopIndicatorId) throws AdempiereSystemError
	{
		String ret = "";
		final Iterator<SimpleTreeNode> iter = categories.iterator();
		while (iter.hasNext())
		{
			SimpleTreeNode node = iter.next();
			if (node.getParentId() == productCategoryId)
			{
				if (node.getNodeId() == loopIndicatorId)
				{
					throw new AdempiereSystemError("The product category tree contains a loop on categoryId: " + loopIndicatorId);
				}
				ret = ret + getSubCategoriesString(node.getNodeId(), categories, loopIndicatorId) + ",";
			}
		}
		log.debug(ret);
		return ret + productCategoryId;
	}

	/**
	 * Simple tree node class for product category tree search.
	 *
	 * @author Karsten Thiemann, kthiemann@adempiere.org
	 *
	 */
	private class SimpleTreeNode
	{

		private int nodeId;

		private int parentId;

		public SimpleTreeNode(int nodeId, int parentId)
		{
			this.nodeId = nodeId;
			this.parentId = parentId;
		}

		public int getNodeId()
		{
			return nodeId;
		}

		public int getParentId()
		{
			return parentId;
		}
	}

} // M_PriceList_Create
