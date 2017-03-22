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
 *****************************************************************************/
package org.compiere.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MLocation;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MUser;
import org.compiere.model.X_I_Order;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Import Order from I_Order
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ImportOrder.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class ImportOrder extends JavaProcess
{
	/**	Client to be imported to		*/
	private int				m_AD_Client_ID = 0;
	/**	Organization to be imported to		*/
	private int				m_AD_Org_ID = 0;
	/**	Delete old Imported				*/
	private boolean			m_deleteOldImported = false;
	/**	Document Action					*/
	private String			m_docAction = MOrder.DOCACTION_Prepare;


	/** Effective						*/
	private Timestamp		m_DateValue = null;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("AD_Client_ID"))
				m_AD_Client_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("AD_Org_ID"))
				m_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DeleteOldImported"))
				m_deleteOldImported = "Y".equals(para[i].getParameter());
			else if (name.equals("DocAction"))
				m_docAction = (String)para[i].getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}
		if (m_DateValue == null)
			m_DateValue = new Timestamp (System.currentTimeMillis());
	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	@Override
	protected String doIt() throws java.lang.Exception
	{
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + m_AD_Client_ID;

		//	****	Prepare	****

		//	Delete Old Imported
		if (m_deleteOldImported)
		{
			sql = new StringBuffer ("DELETE FROM I_Order "
				  + "WHERE I_IsImported='Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.debug("Deleted Old Imported =" + no);
		}

		//	Set Client, Org, IsActive, Created/Updated
		sql = new StringBuffer ("UPDATE I_Order "
			  + "SET AD_Client_ID = COALESCE (AD_Client_ID,").append (m_AD_Client_ID).append ("),"
			  + " AD_Org_ID = COALESCE (AD_Org_ID,").append (m_AD_Org_ID).append ("),"
			  + " IsActive = COALESCE (IsActive, 'Y'),"
			  + " Created = COALESCE (Created, now()),"
			  + " CreatedBy = COALESCE (CreatedBy, 0),"
			  + " Updated = COALESCE (Updated, now()),"
			  + " UpdatedBy = COALESCE (UpdatedBy, 0),"
			  + " I_ErrorMsg = ' ',"
			  + " I_IsImported = 'N' "
			  + "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Reset=" + no);

		sql = new StringBuffer ("UPDATE I_Order o "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Org, '"
			+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0"
			+ " OR EXISTS (SELECT * FROM AD_Org oo WHERE o.AD_Org_ID=oo.AD_Org_ID AND (oo.IsSummary='Y' OR oo.IsActive='N')))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Org=" + no);

		//	Document Type - PO - SO
		sql = new StringBuffer ("UPDATE I_Order o "	//	PO Document Type Name
			  + "SET C_DocType_ID=(SELECT C_DocType_ID FROM C_DocType d WHERE d.Name=o.DocTypeName"
			  + " AND d.DocBaseType='POO' AND o.AD_Client_ID=d.AD_Client_ID) "
			  + "WHERE C_DocType_ID IS NULL AND IsSOTrx='N' AND DocTypeName IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set PO DocType=" + no);
		sql = new StringBuffer ("UPDATE I_Order o "	//	SO Document Type Name
			  + "SET C_DocType_ID=(SELECT C_DocType_ID FROM C_DocType d WHERE d.Name=o.DocTypeName"
			  + " AND d.DocBaseType='SOO' AND o.AD_Client_ID=d.AD_Client_ID) "
			  + "WHERE C_DocType_ID IS NULL AND IsSOTrx='Y' AND DocTypeName IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set SO DocType=" + no);
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET C_DocType_ID=(SELECT C_DocType_ID FROM C_DocType d WHERE d.Name=o.DocTypeName"
			  + " AND d.DocBaseType IN ('SOO','POO') AND o.AD_Client_ID=d.AD_Client_ID) "
			//+ "WHERE C_DocType_ID IS NULL AND IsSOTrx IS NULL AND DocTypeName IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
			  + "WHERE C_DocType_ID IS NULL AND DocTypeName IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set DocType=" + no);
		sql = new StringBuffer ("UPDATE I_Order "	//	Error Invalid Doc Type Name
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid DocTypeName, ' "
			  + "WHERE C_DocType_ID IS NULL AND DocTypeName IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid DocTypeName=" + no);
		//	DocType Default
		sql = new StringBuffer ("UPDATE I_Order o "	//	Default PO
			  + "SET C_DocType_ID=(SELECT MAX(C_DocType_ID) FROM C_DocType d WHERE d.IsDefault='Y'"
			  + " AND d.DocBaseType='POO' AND o.AD_Client_ID=d.AD_Client_ID) "
			  + "WHERE C_DocType_ID IS NULL AND IsSOTrx='N' AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set PO Default DocType=" + no);
		sql = new StringBuffer ("UPDATE I_Order o "	//	Default SO
			  + "SET C_DocType_ID=(SELECT MAX(C_DocType_ID) FROM C_DocType d WHERE d.IsDefault='Y'"
			  + " AND d.DocBaseType='SOO' AND o.AD_Client_ID=d.AD_Client_ID) "
			  + "WHERE C_DocType_ID IS NULL AND IsSOTrx='Y' AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set SO Default DocType=" + no);
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET C_DocType_ID=(SELECT MAX(C_DocType_ID) FROM C_DocType d WHERE d.IsDefault='Y'"
			  + " AND d.DocBaseType IN('SOO','POO') AND o.AD_Client_ID=d.AD_Client_ID) "
			  + "WHERE C_DocType_ID IS NULL AND IsSOTrx IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Default DocType=" + no);
		sql = new StringBuffer ("UPDATE I_Order "	// No DocType
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No DocType, ' "
			  + "WHERE C_DocType_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("No DocType=" + no);

		//	Set IsSOTrx
		sql = new StringBuffer ("UPDATE I_Order o SET IsSOTrx='Y' "
			  + "WHERE EXISTS (SELECT * FROM C_DocType d WHERE o.C_DocType_ID=d.C_DocType_ID AND d.DocBaseType='SOO' AND o.AD_Client_ID=d.AD_Client_ID)"
			  + " AND C_DocType_ID IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set IsSOTrx=Y=" + no);
		sql = new StringBuffer ("UPDATE I_Order o SET IsSOTrx='N' "
			  + "WHERE EXISTS (SELECT * FROM C_DocType d WHERE o.C_DocType_ID=d.C_DocType_ID AND d.DocBaseType='POO' AND o.AD_Client_ID=d.AD_Client_ID)"
			  + " AND C_DocType_ID IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set IsSOTrx=N=" + no);

		//	Price List
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET M_PriceList_ID=(SELECT MAX(M_PriceList_ID) FROM M_PriceList p WHERE p.IsDefault='Y'"
			  + " AND p.C_Currency_ID=o.C_Currency_ID AND p.IsSOPriceList=o.IsSOTrx AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE M_PriceList_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Default Currency PriceList=" + no);
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET M_PriceList_ID=(SELECT MAX(M_PriceList_ID) FROM M_PriceList p WHERE p.IsDefault='Y'"
			  + " AND p.IsSOPriceList=o.IsSOTrx AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE M_PriceList_ID IS NULL AND C_Currency_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Default PriceList=" + no);
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET M_PriceList_ID=(SELECT MAX(M_PriceList_ID) FROM M_PriceList p "
			  + " WHERE p.C_Currency_ID=o.C_Currency_ID AND p.IsSOPriceList=o.IsSOTrx AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE M_PriceList_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Currency PriceList=" + no);
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET M_PriceList_ID=(SELECT MAX(M_PriceList_ID) FROM M_PriceList p "
			  + " WHERE p.IsSOPriceList=o.IsSOTrx AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE M_PriceList_ID IS NULL AND C_Currency_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set PriceList=" + no);
		//
		sql = new StringBuffer ("UPDATE I_Order "
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No PriceList, ' "
			  + "WHERE M_PriceList_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("No PriceList=" + no);

		//	Payment Term
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET C_PaymentTerm_ID=(SELECT C_PaymentTerm_ID FROM C_PaymentTerm p"
			  + " WHERE o.PaymentTermValue=p.Value AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE C_PaymentTerm_ID IS NULL AND PaymentTermValue IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set PaymentTerm=" + no);
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET C_PaymentTerm_ID=(SELECT MAX(C_PaymentTerm_ID) FROM C_PaymentTerm p"
			  + " WHERE p.IsDefault='Y' AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE C_PaymentTerm_ID IS NULL AND o.PaymentTermValue IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Default PaymentTerm=" + no);
		//
		sql = new StringBuffer ("UPDATE I_Order "
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No PaymentTerm, ' "
			  + "WHERE C_PaymentTerm_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("No PaymentTerm=" + no);

		//	Warehouse
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET M_Warehouse_ID=(SELECT MAX(M_Warehouse_ID) FROM M_Warehouse w"
			  + " WHERE o.AD_Client_ID=w.AD_Client_ID AND o.AD_Org_ID=w.AD_Org_ID) "
			  + "WHERE M_Warehouse_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());	//	Warehouse for Org
		if (no != 0)
			log.debug("Set Warehouse=" + no);
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET M_Warehouse_ID=(SELECT M_Warehouse_ID FROM M_Warehouse w"
			  + " WHERE o.AD_Client_ID=w.AD_Client_ID) "
			  + "WHERE M_Warehouse_ID IS NULL"
			  + " AND EXISTS (SELECT AD_Client_ID FROM M_Warehouse w WHERE w.AD_Client_ID=o.AD_Client_ID GROUP BY AD_Client_ID HAVING COUNT(*)=1)"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.debug("Set Only Client Warehouse=" + no);
		//
		sql = new StringBuffer ("UPDATE I_Order "
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Warehouse, ' "
			  + "WHERE M_Warehouse_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("No Warehouse=" + no);

		//	BP from EMail
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET (C_BPartner_ID,AD_User_ID)=(SELECT C_BPartner_ID,AD_User_ID FROM AD_User u"
			  + " WHERE o.EMail=u.EMail AND o.AD_Client_ID=u.AD_Client_ID AND u.C_BPartner_ID IS NOT NULL "
			  //metas: u must be active
			  + " AND u.IsActive='Y' "
			  //metas end
			  + " ) "
			  + "WHERE C_BPartner_ID IS NULL AND EMail IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(DB.convertSqlToNative(sql.toString()), get_TrxName());
		log.debug("Set BP from EMail=" + no);
		//	BP from ContactName
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET (C_BPartner_ID,AD_User_ID)=(SELECT C_BPartner_ID,AD_User_ID FROM AD_User u"
			  + " WHERE o.ContactName=u.Name AND o.AD_Client_ID=u.AD_Client_ID AND u.C_BPartner_ID IS NOT NULL "
			  //metas: u must be active
			  + " AND u.IsActive='Y' "
			  //metas end
			  + " ) "
			  + "WHERE C_BPartner_ID IS NULL AND ContactName IS NOT NULL"
			  + " AND EXISTS (SELECT Name FROM AD_User u WHERE o.ContactName=u.Name AND o.AD_Client_ID=u.AD_Client_ID AND u.C_BPartner_ID IS NOT NULL GROUP BY Name HAVING COUNT(*)=1)"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(DB.convertSqlToNative(sql.toString()), get_TrxName());
		log.debug("Set BP from ContactName=" + no);
		//	BP from Value
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET C_BPartner_ID=(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp"
			  + " WHERE o.BPartnerValue=bp.Value AND o.AD_Client_ID=bp.AD_Client_ID"
			  //metas: bpl and l must be active
			  + " AND bp.IsActive='Y' "
			  //metas end
			  + " ) "
			  + "WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set BP from Value=" + no);
		//	Default BP
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET C_BPartner_ID=(SELECT C_BPartnerCashTrx_ID FROM AD_ClientInfo c"
			  + " WHERE o.AD_Client_ID=c.AD_Client_ID) "
			  + "WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NULL AND Name IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Default BP=" + no);

		//	Existing Location ? Exact Match
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET (BillTo_ID,C_BPartner_Location_ID)=(SELECT C_BPartner_Location_ID,C_BPartner_Location_ID"
			  + " FROM C_BPartner_Location bpl INNER JOIN C_Location l ON (bpl.C_Location_ID=l.C_Location_ID)"
			  + " WHERE o.C_BPartner_ID=bpl.C_BPartner_ID AND bpl.AD_Client_ID=o.AD_Client_ID"
			  + " AND DUMP(o.Address1)=DUMP(l.Address1) AND DUMP(o.Address2)=DUMP(l.Address2)"
			  + " AND DUMP(o.City)=DUMP(l.City) AND DUMP(o.Postal)=DUMP(l.Postal)"
			  //metas: bpl and l must be active
			  + " AND bpl.IsActive='Y' AND l.IsActive='Y'"
			  //metas end
			  + " AND o.C_Region_ID=l.C_Region_ID AND o.C_Country_ID=l.C_Country_ID) "
			  + "WHERE C_BPartner_ID IS NOT NULL AND C_BPartner_Location_ID IS NULL"
			  + " AND I_IsImported='N'").append (clientCheck);
		no = DB.executeUpdate(DB.convertSqlToNative(sql.toString()), get_TrxName());
		log.debug("Found Location=" + no);
		//	Set Bill Location from BPartner
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET BillTo_ID=(SELECT MAX(C_BPartner_Location_ID) FROM C_BPartner_Location l"
			  + " WHERE l.C_BPartner_ID=o.C_BPartner_ID AND o.AD_Client_ID=l.AD_Client_ID"
			  //metas: l must be active
			  + " AND l.IsActive='Y'"
			  //metas end
			  + " AND ((l.IsBillTo='Y' AND o.IsSOTrx='Y') OR (l.IsPayFrom='Y' AND o.IsSOTrx='N'))"
			  + ") "
			  + "WHERE C_BPartner_ID IS NOT NULL AND BillTo_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set BP BillTo from BP=" + no);
		//	Set Location from BPartner
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET C_BPartner_Location_ID=(SELECT MAX(C_BPartner_Location_ID) FROM C_BPartner_Location l"
			  + " WHERE l.C_BPartner_ID=o.C_BPartner_ID AND o.AD_Client_ID=l.AD_Client_ID"
			  + " AND ((l.IsShipTo='Y' AND o.IsSOTrx='Y') OR o.IsSOTrx='N')"
			  //metas: l must be active
			  + " AND l.IsActive='Y'"
			  //metas end
			  + ") "
			  + "WHERE C_BPartner_ID IS NOT NULL AND C_BPartner_Location_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set BP Location from BP=" + no);
		//
		sql = new StringBuffer ("UPDATE I_Order "
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No BP Location, ' "
			  + "WHERE C_BPartner_ID IS NOT NULL AND (BillTo_ID IS NULL OR C_BPartner_Location_ID IS NULL)"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("No BP Location=" + no);

		//	Set Country
		/**
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET CountryCode=(SELECT MAX(CountryCode) FROM C_Country c WHERE c.IsDefault='Y'"
			  + " AND c.AD_Client_ID IN (0, o.AD_Client_ID)) "
			  + "WHERE C_BPartner_ID IS NULL AND CountryCode IS NULL AND C_Country_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Country Default=" + no);
		**/
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET C_Country_ID=(SELECT C_Country_ID FROM C_Country c"
			  + " WHERE o.CountryCode=c.CountryCode AND c.AD_Client_ID IN (0, o.AD_Client_ID)) "
			  + "WHERE C_BPartner_ID IS NULL AND C_Country_ID IS NULL AND CountryCode IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Country=" + no);
		//
		sql = new StringBuffer ("UPDATE I_Order "
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Country, ' "
			  + "WHERE C_BPartner_ID IS NULL AND C_Country_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Country=" + no);

		//	Set Region
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "Set RegionName=(SELECT MAX(Name) FROM C_Region r"
			  + " WHERE r.IsDefault='Y' AND r.C_Country_ID=o.C_Country_ID"
			  + " AND r.AD_Client_ID IN (0, o.AD_Client_ID)) "
			  + "WHERE C_BPartner_ID IS NULL AND C_Region_ID IS NULL AND RegionName IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Region Default=" + no);
		//
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "Set C_Region_ID=(SELECT C_Region_ID FROM C_Region r"
			  + " WHERE r.Name=o.RegionName AND r.C_Country_ID=o.C_Country_ID"
			  + " AND r.AD_Client_ID IN (0, o.AD_Client_ID)) "
			  + "WHERE C_BPartner_ID IS NULL AND C_Region_ID IS NULL AND RegionName IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Region=" + no);
		//
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Region, ' "
			  + "WHERE C_BPartner_ID IS NULL AND C_Region_ID IS NULL "
			  + " AND EXISTS (SELECT * FROM C_Country c"
			  + " WHERE c.C_Country_ID=o.C_Country_ID AND c.HasRegion='Y')"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Region=" + no);

		//	Product
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET M_Product_ID=(SELECT MAX(M_Product_ID) FROM M_Product p"
			  + " WHERE o.ProductValue=p.Value AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE M_Product_ID IS NULL AND ProductValue IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Product from Value=" + no);
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET M_Product_ID=(SELECT MAX(M_Product_ID) FROM M_Product p"
			  + " WHERE o.UPC=p.UPC AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE M_Product_ID IS NULL AND UPC IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Product from UPC=" + no);
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET M_Product_ID=(SELECT MAX(M_Product_ID) FROM M_Product p"
			  + " WHERE o.SKU=p.SKU AND o.AD_Client_ID=p.AD_Client_ID) "
			  + "WHERE M_Product_ID IS NULL AND SKU IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Product fom SKU=" + no);
		sql = new StringBuffer ("UPDATE I_Order "
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Product, ' "
			  + "WHERE M_Product_ID IS NULL AND (ProductValue IS NOT NULL OR UPC IS NOT NULL OR SKU IS NOT NULL)"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Product=" + no);

		//	Charge
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET C_Charge_ID=(SELECT C_Charge_ID FROM C_Charge c"
			  + " WHERE o.ChargeName=c.Name AND o.AD_Client_ID=c.AD_Client_ID) "
			  + "WHERE C_Charge_ID IS NULL AND ChargeName IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Charge=" + no);
		sql = new StringBuffer ("UPDATE I_Order "
				  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Charge, ' "
				  + "WHERE C_Charge_ID IS NULL AND (ChargeName IS NOT NULL)"
				  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Charge=" + no);
		//
		
		sql = new StringBuffer ("UPDATE I_Order "
				  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Product and Charge, ' "
				  + "WHERE M_Product_ID IS NOT NULL AND C_Charge_ID IS NOT NULL "
				  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Product and Charge exclusive=" + no);

		//	Tax
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET C_Tax_ID=(SELECT MAX(C_Tax_ID) FROM C_Tax t"
			  + " WHERE o.TaxIndicator=t.TaxIndicator AND o.AD_Client_ID=t.AD_Client_ID) "
			  + "WHERE C_Tax_ID IS NULL AND TaxIndicator IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Tax=" + no);
		sql = new StringBuffer ("UPDATE I_Order "
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Tax, ' "
			  + "WHERE C_Tax_ID IS NULL AND TaxIndicator IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Tax=" + no);

		commitEx();
		
		//	-- New BPartner ---------------------------------------------------

		//	Go through Order Records w/o C_BPartner_ID
		sql = new StringBuffer ("SELECT * FROM I_Order "
			  + "WHERE I_IsImported='N' AND C_BPartner_ID IS NULL").append (clientCheck);
		try
		{
			PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				X_I_Order imp = new X_I_Order (getCtx (), rs, get_TrxName());
				if (imp.getBPartnerValue () == null)
				{
					if (imp.getEMail () != null)
						imp.setBPartnerValue (imp.getEMail ());
					else if (imp.getName () != null)
						imp.setBPartnerValue (imp.getName ());
					else
						continue;
				}
				if (imp.getName () == null)
				{
					if (imp.getContactName () != null)
						imp.setName (imp.getContactName ());
					else
						imp.setName (imp.getBPartnerValue ());
				}
				//	BPartner
				MBPartner bp = MBPartner.get (getCtx(), imp.getBPartnerValue());
				if (bp == null)
				{
					bp = new MBPartner (getCtx (), -1, get_TrxName());
					bp.setClientOrg (imp.getAD_Client_ID (), imp.getAD_Org_ID ());
					bp.setValue (imp.getBPartnerValue ());
					bp.setName (imp.getName ());
					if (!bp.save ())
						continue;
				}
				imp.setC_BPartner_ID (bp.getC_BPartner_ID ());
				
				//	BP Location
				MBPartnerLocation bpl = null; 
				MBPartnerLocation[] bpls = bp.getLocations(true);
				for (int i = 0; bpl == null && i < bpls.length; i++)
				{
					if (imp.getC_BPartner_Location_ID() == bpls[i].getC_BPartner_Location_ID())
						bpl = bpls[i];
					//	Same Location ID
					else if (imp.getC_Location_ID() == bpls[i].getC_Location_ID())
						bpl = bpls[i];
					//	Same Location Info
					else if (imp.getC_Location_ID() == 0)
					{
						MLocation loc = bpl.getLocation(false);
						if (loc.equals(imp.getC_Country_ID(), imp.getC_Region_ID(), 
								imp.getPostal(), "", imp.getCity(), 
								imp.getAddress1(), imp.getAddress2()))
							bpl = bpls[i];
					}
				}
				if (bpl == null)
				{
					//	New Location
					MLocation loc = new MLocation (getCtx (), 0, get_TrxName());
					loc.setAddress1 (imp.getAddress1 ());
					loc.setAddress2 (imp.getAddress2 ());
					loc.setCity (imp.getCity ());
					loc.setPostal (imp.getPostal ());
					if (imp.getC_Region_ID () != 0)
						loc.setC_Region_ID (imp.getC_Region_ID ());
					loc.setC_Country_ID (imp.getC_Country_ID ());
					if (!loc.save ())
						continue;
					//
					bpl = new MBPartnerLocation (bp);
					bpl.setC_Location_ID (imp.getC_Location_ID ());
					if (!bpl.save ())
						continue;
				}
				imp.setC_Location_ID (bpl.getC_Location_ID ());
				imp.setBillTo_ID (bpl.getC_BPartner_Location_ID ());
				imp.setC_BPartner_Location_ID (bpl.getC_BPartner_Location_ID ());
				
				//	User/Contact
				if (imp.getContactName () != null 
					|| imp.getEMail () != null 
					|| imp.getPhone () != null)
				{
					MUser[] users = bp.getContacts(true);
					MUser user = null;
					for (int i = 0; user == null && i < users.length;  i++)
					{
						String name = users[i].getName();
						if (name.equals(imp.getContactName()) 
							|| name.equals(imp.getName()))
						{
							user = users[i];
							imp.setAD_User_ID (user.getAD_User_ID ());
						}
					}
					if (user == null)
					{
						user = new MUser (bp);
						if (imp.getContactName () == null)
							user.setName (imp.getName ());
						else
							user.setName (imp.getContactName ());
						user.setEMail (imp.getEMail ());
						user.setPhone (imp.getPhone ());
						if (user.save ())
							imp.setAD_User_ID (user.getAD_User_ID ());
					}
				}
				imp.save ();
			}	//	for all new BPartners
			rs.close ();
			pstmt.close ();
			//
		}
		catch (SQLException e)
		{
			log.error("BP - " + sql.toString(), e);
		}
		sql = new StringBuffer ("UPDATE I_Order "
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No BPartner, ' "
			  + "WHERE C_BPartner_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("No BPartner=" + no);

		commitEx();
		
		//	-- New Orders -----------------------------------------------------

		int noInsert = 0;
		int noInsertLine = 0;

		//	Go through Order Records w/o
		sql = new StringBuffer ("SELECT * FROM I_Order "
			  + "WHERE I_IsImported='N'").append (clientCheck)
			.append(" ORDER BY C_BPartner_ID, BillTo_ID, C_BPartner_Location_ID, I_Order_ID");
		try
		{
			PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			//
			int oldC_BPartner_ID = 0;
			int oldBillTo_ID = 0;
			int oldC_BPartner_Location_ID = 0;
			String oldDocumentNo = "";
			//
			MOrder order = null;
			int lineNo = 0;
			while (rs.next ())
			{
				X_I_Order imp = new X_I_Order (getCtx (), rs, get_TrxName());
				String cmpDocumentNo = imp.getDocumentNo();
				if (cmpDocumentNo == null)
					cmpDocumentNo = "";
				//	New Order
				if (oldC_BPartner_ID != imp.getC_BPartner_ID() 
					|| oldC_BPartner_Location_ID != imp.getC_BPartner_Location_ID()
					|| oldBillTo_ID != imp.getBillTo_ID() 
					|| !oldDocumentNo.equals(cmpDocumentNo))
				{
					if (order != null)
					{
						if (m_docAction != null && m_docAction.length() > 0)
						{
							order.setDocAction(m_docAction);
							order.processIt (m_docAction);
						}
						order.save();
					}
					oldC_BPartner_ID = imp.getC_BPartner_ID();
					oldC_BPartner_Location_ID = imp.getC_BPartner_Location_ID();
					oldBillTo_ID = imp.getBillTo_ID();
					oldDocumentNo = imp.getDocumentNo();
					if (oldDocumentNo == null)
						oldDocumentNo = "";
					//
					order = new MOrder (getCtx(), 0, get_TrxName());
					order.setClientOrg (imp.getAD_Client_ID(), imp.getAD_Org_ID());
					order.setC_DocTypeTarget_ID(imp.getC_DocType_ID());
					order.setIsSOTrx(imp.isSOTrx());
					if (imp.getDocumentNo() != null)
						order.setDocumentNo(imp.getDocumentNo());
					//	Ship Partner
					order.setC_BPartner_ID(imp.getC_BPartner_ID());
					order.setC_BPartner_Location_ID(imp.getC_BPartner_Location_ID());
					if (imp.getAD_User_ID() != 0)
						order.setAD_User_ID(imp.getAD_User_ID());
					//	Bill Partner
					order.setBill_BPartner_ID(imp.getC_BPartner_ID());
					order.setBill_Location_ID(imp.getBillTo_ID());
					//
					if (imp.getDescription() != null)
						order.setDescription(imp.getDescription());
					order.setC_PaymentTerm_ID(imp.getC_PaymentTerm_ID());
					order.setM_PriceList_ID(imp.getM_PriceList_ID());
					order.setM_Warehouse_ID(imp.getM_Warehouse_ID());
					if (imp.getM_Shipper_ID() != 0)
						order.setM_Shipper_ID(imp.getM_Shipper_ID());
					//	SalesRep from Import or the person running the import
					if (imp.getSalesRep_ID() != 0)
						order.setSalesRep_ID(imp.getSalesRep_ID());
					// Default Sales Rep
					// NOTE: we shall not set the SalesRep from context if is not set.
					// This is not a mandatory field, so leave it like it is.
					// if (order.getSalesRep_ID() == 0)
					// order.setSalesRep_ID(getAD_User_ID());
					//
					if (imp.getAD_OrgTrx_ID() != 0)
						order.setAD_OrgTrx_ID(imp.getAD_OrgTrx_ID());
					if (imp.getC_Activity_ID() != 0)
						order.setC_Activity_ID(imp.getC_Activity_ID());
					if (imp.getC_Campaign_ID() != 0)
						order.setC_Campaign_ID(imp.getC_Campaign_ID());
					if (imp.getC_Project_ID() != 0)
						order.setC_Project_ID(imp.getC_Project_ID());
					//
					if (imp.getDateOrdered() != null)
						order.setDateOrdered(imp.getDateOrdered());
					if (imp.getDateAcct() != null)
						order.setDateAcct(imp.getDateAcct());
					//
					order.save();
					noInsert++;
					lineNo = 10;
				}
				imp.setC_Order_ID(order.getC_Order_ID());
				//	New OrderLine
				MOrderLine line = new MOrderLine (order);
				line.setLine(lineNo);
				lineNo += 10;
				if (imp.getM_Product_ID() != 0)
					line.setM_Product_ID(imp.getM_Product_ID(), true);
				if (imp.getC_Charge_ID() != 0)
					line.setC_Charge_ID(imp.getC_Charge_ID());
				line.setQty(imp.getQtyOrdered());
				line.setPrice();
				if (imp.getPriceActual().compareTo(Env.ZERO) != 0)
					line.setPrice(imp.getPriceActual());
				if (imp.getC_Tax_ID() != 0)
					line.setC_Tax_ID(imp.getC_Tax_ID());
				else
				{
					line.setTax();
					imp.setC_Tax_ID(line.getC_Tax_ID());
				}
				
				// 05129
				line.setC_TaxCategory_ID(line.getC_Tax().getC_TaxCategory_ID());
				
				if (imp.getFreightAmt() != null)
					line.setFreightAmt(imp.getFreightAmt());
				if (imp.getLineDescription() != null)
					line.setDescription(imp.getLineDescription());
				// metas: handle the case that line can't be saved
				if (!line.save()) {
					continue;
				}
				//metas: end
				imp.setC_OrderLine_ID(line.getC_OrderLine_ID());
				imp.setI_IsImported(true);
				imp.setProcessed(true);
				//
				if (imp.save()){
					noInsertLine++;
					commitEx(); // metas
				}
				
			}
			if (order != null)
			{
				if (m_docAction != null && m_docAction.length() > 0)
				{
					order.setDocAction(m_docAction);
					order.processIt (m_docAction);
				}
				order.save();
			}
			rs.close();
			pstmt.close();
		}
		catch (Exception e)
		{
			log.error("Order - " + sql.toString(), e);
		}

		//	Set Error to indicator to not imported
		sql = new StringBuffer ("UPDATE I_Order "
			+ "SET I_IsImported='N', Updated=now() "
			+ "WHERE I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		addLog (0, null, new BigDecimal (no), "@Errors@");
		//
		addLog (0, null, new BigDecimal (noInsert), "@C_Order_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (noInsertLine), "@C_OrderLine_ID@: @Inserted@");
		return "#" + noInsert + "/" + noInsertLine;
	}	//	doIt

}	//	ImportOrder
