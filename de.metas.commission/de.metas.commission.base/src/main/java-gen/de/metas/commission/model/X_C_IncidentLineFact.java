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

/** Generated Model for C_IncidentLineFact
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_IncidentLineFact extends PO implements I_C_IncidentLineFact, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_IncidentLineFact (Properties ctx, int C_IncidentLineFact_ID, String trxName)
    {
      super (ctx, C_IncidentLineFact_ID, trxName);
      /** if (C_IncidentLineFact_ID == 0)
        {
			setAD_Table_ID (0);
			setC_AdvCommissionRelevantPO_ID (0);
			setC_BPartner_ID (0);
			setC_IncidentLineFact_ID (0);
			setC_IncidentLine_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_IncidentLineFact (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_C_IncidentLineFact[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionFactCand getC_AdvCommissionFactCand() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionFactCand)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionFactCand.Table_Name)
			.getPO(getC_AdvCommissionFactCand_ID(), get_TrxName());	}

	/** Set Provisionsdaten-Wartschlange.
		@param C_AdvCommissionFactCand_ID Provisionsdaten-Wartschlange	  */
	public void setC_AdvCommissionFactCand_ID (int C_AdvCommissionFactCand_ID)
	{
		if (C_AdvCommissionFactCand_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionFactCand_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionFactCand_ID, Integer.valueOf(C_AdvCommissionFactCand_ID));
	}

	/** Get Provisionsdaten-Wartschlange.
		@return Provisionsdaten-Wartschlange	  */
	public int getC_AdvCommissionFactCand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionFactCand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionRelevantPO getC_AdvCommissionRelevantPO() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionRelevantPO)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionRelevantPO.Table_Name)
			.getPO(getC_AdvCommissionRelevantPO_ID(), get_TrxName());	}

	/** Set C_AdvCommissionRelevantPO_ID.
		@param C_AdvCommissionRelevantPO_ID C_AdvCommissionRelevantPO_ID	  */
	public void setC_AdvCommissionRelevantPO_ID (int C_AdvCommissionRelevantPO_ID)
	{
		if (C_AdvCommissionRelevantPO_ID < 1) 
			set_Value (COLUMNNAME_C_AdvCommissionRelevantPO_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvCommissionRelevantPO_ID, Integer.valueOf(C_AdvCommissionRelevantPO_ID));
	}

	/** Get C_AdvCommissionRelevantPO_ID.
		@return C_AdvCommissionRelevantPO_ID	  */
	public int getC_AdvCommissionRelevantPO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionRelevantPO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set C_BPartner_ID.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get C_BPartner_ID.
		@return Bezeichnet einen Geschäftspartner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_IncidentLineFact_ID.
		@param C_IncidentLineFact_ID C_IncidentLineFact_ID	  */
	public void setC_IncidentLineFact_ID (int C_IncidentLineFact_ID)
	{
		if (C_IncidentLineFact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_IncidentLineFact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_IncidentLineFact_ID, Integer.valueOf(C_IncidentLineFact_ID));
	}

	/** Get C_IncidentLineFact_ID.
		@return C_IncidentLineFact_ID	  */
	public int getC_IncidentLineFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_IncidentLineFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_IncidentLine getC_IncidentLine() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_IncidentLine)MTable.get(getCtx(), de.metas.commission.model.I_C_IncidentLine.Table_Name)
			.getPO(getC_IncidentLine_ID(), get_TrxName());	}

	/** Set C_IncidentLine_ID.
		@param C_IncidentLine_ID C_IncidentLine_ID	  */
	public void setC_IncidentLine_ID (int C_IncidentLine_ID)
	{
		if (C_IncidentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_IncidentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_IncidentLine_ID, Integer.valueOf(C_IncidentLine_ID));
	}

	/** Get C_IncidentLine_ID.
		@return C_IncidentLine_ID	  */
	public int getC_IncidentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_IncidentLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Belegdatum.
		@param DateDoc 
		Datum des Belegs
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Belegdatum.
		@return Datum des Belegs
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}