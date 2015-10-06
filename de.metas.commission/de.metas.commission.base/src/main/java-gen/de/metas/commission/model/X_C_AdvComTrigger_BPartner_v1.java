/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package de.metas.commission.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for C_AdvComTrigger_BPartner_v1
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvComTrigger_BPartner_v1 extends PO implements I_C_AdvComTrigger_BPartner_v1, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvComTrigger_BPartner_v1 (Properties ctx, int C_AdvComTrigger_BPartner_v1_ID, String trxName)
    {
      super (ctx, C_AdvComTrigger_BPartner_v1_ID, trxName);
      /** if (C_AdvComTrigger_BPartner_v1_ID == 0)
        {
			setC_AdvComTrigger_BPartner_v1_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_AdvComTrigger_BPartner_v1 (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_AdvComTrigger_BPartner_v1[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public de.metas.commission.model.I_C_AdvComDoc getC_AdvComDoc() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvComDoc)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvComDoc.Table_Name)
			.getPO(getC_AdvComDoc_ID(), get_TrxName());	}

	/** Set Provisionsauslöser.
		@param C_AdvComDoc_ID Provisionsauslöser	  */
	public void setC_AdvComDoc_ID (int C_AdvComDoc_ID)
	{
		if (C_AdvComDoc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComDoc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComDoc_ID, Integer.valueOf(C_AdvComDoc_ID));
	}

	/** Get Provisionsauslöser.
		@return Provisionsauslöser	  */
	public int getC_AdvComDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionInstance getC_AdvCommissionInstance() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionInstance)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionInstance.Table_Name)
			.getPO(getC_AdvCommissionInstance_ID(), get_TrxName());	}

	/** Set Provisionsvorgang.
		@param C_AdvCommissionInstance_ID Provisionsvorgang	  */
	public void setC_AdvCommissionInstance_ID (int C_AdvCommissionInstance_ID)
	{
		if (C_AdvCommissionInstance_ID < 1) 
			set_Value (COLUMNNAME_C_AdvCommissionInstance_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvCommissionInstance_ID, Integer.valueOf(C_AdvCommissionInstance_ID));
	}

	/** Get Provisionsvorgang.
		@return Provisionsvorgang	  */
	public int getC_AdvCommissionInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionTerm getC_AdvCommissionTerm() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionTerm)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionTerm.Table_Name)
			.getPO(getC_AdvCommissionTerm_ID(), get_TrxName());	}

	/** Set Provisionsart.
		@param C_AdvCommissionTerm_ID Provisionsart	  */
	public void setC_AdvCommissionTerm_ID (int C_AdvCommissionTerm_ID)
	{
		if (C_AdvCommissionTerm_ID < 1) 
			set_Value (COLUMNNAME_C_AdvCommissionTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvCommissionTerm_ID, Integer.valueOf(C_AdvCommissionTerm_ID));
	}

	/** Get Provisionsart.
		@return Provisionsart	  */
	public int getC_AdvCommissionTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provisionsauslöser - Geschäftspartner.
		@param C_AdvComTrigger_BPartner_v1_ID Provisionsauslöser - Geschäftspartner	  */
	public void setC_AdvComTrigger_BPartner_v1_ID (int C_AdvComTrigger_BPartner_v1_ID)
	{
		if (C_AdvComTrigger_BPartner_v1_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComTrigger_BPartner_v1_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComTrigger_BPartner_v1_ID, Integer.valueOf(C_AdvComTrigger_BPartner_v1_ID));
	}

	/** Get Provisionsauslöser - Geschäftspartner.
		@return Provisionsauslöser - Geschäftspartner	  */
	public int getC_AdvComTrigger_BPartner_v1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComTrigger_BPartner_v1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner_Customer() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_Customer_ID(), get_TrxName());	}

	/** Set Kunde.
		@param C_BPartner_Customer_ID Kunde	  */
	public void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, Integer.valueOf(C_BPartner_Customer_ID));
	}

	/** Get Kunde.
		@return Kunde	  */
	public int getC_BPartner_Customer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Customer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner_SalesRep() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_SalesRep_ID(), get_TrxName());	}

	/** Set Vertriebsperson.
		@param C_BPartner_SalesRep_ID Vertriebsperson	  */
	public void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID)
	{
		if (C_BPartner_SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_SalesRep_ID, Integer.valueOf(C_BPartner_SalesRep_ID));
	}

	/** Get Vertriebsperson.
		@return Vertriebsperson	  */
	public int getC_BPartner_SalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provisionsauslöserdatum.
		@param CommissionDateFact Provisionsauslöserdatum	  */
	public void setCommissionDateFact (Timestamp CommissionDateFact)
	{
		set_Value (COLUMNNAME_CommissionDateFact, CommissionDateFact);
	}

	/** Get Provisionsauslöserdatum.
		@return Provisionsauslöserdatum	  */
	public Timestamp getCommissionDateFact () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CommissionDateFact);
	}

	/** Set Datum Vorgangsauslöser.
		@param DateInstanceTrigger Datum Vorgangsauslöser	  */
	public void setDateInstanceTrigger (Timestamp DateInstanceTrigger)
	{
		set_Value (COLUMNNAME_DateInstanceTrigger, DateInstanceTrigger);
	}

	/** Get Datum Vorgangsauslöser.
		@return Datum Vorgangsauslöser	  */
	public Timestamp getDateInstanceTrigger () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInstanceTrigger);
	}
}